package com.taraan.dum.admin.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.taraan.dum.logic.TripLocationRepository;
import com.taraan.dum.logic.TripTrackerInfoRepository;
import com.taraan.dum.service.logger.AuditLog;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collections;
import java.util.Properties;

/**
 * @author Amir Mansouri Fard (amf.fard@gmail.com) on 3/30/2018
 */

@Configuration
@ComponentScan({"com.taraan.dum", "com.taraan.dum.admin"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@PropertySource(value = {"classpath:config.properties"})
public class ApplicationContextConfig {
    @Autowired
    private Environment environment;

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder =
                new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("com.taraan.dum.model")
                .addProperties(getHibernateProperties());

        return builder.buildSessionFactory();
    }


    @Bean
    public AuditLog auditLog() {
        AuditLog auditLog = new AuditLog();
        auditLog.setLogTypes(null);
        return auditLog;
    }


    private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.jdbc.use_get_generated_keys", "true");
        prop.put("hibernate.transaction.auto_close_session", "true");
        prop.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.showsql"));
        prop.put("hibernate.dialect",
                environment.getRequiredProperty("hibernate.dialect"));
        prop.put("hibernate.hbm2ddl.auto",
                environment.getRequiredProperty("hibernate.hbm2ddl"));
        return prop;
    }


    public HikariConfig getHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("springHikariCP");
        hikariConfig.setDriverClassName(environment.getRequiredProperty("hibernate.driver"));
        hikariConfig.setJdbcUrl(environment.getRequiredProperty("hibernate.url"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getRequiredProperty("hibernate.maximumPoolSize")));
        hikariConfig.setIdleTimeout(Long.parseLong(environment.getRequiredProperty("hibernate.idleTimeOut")));
        Properties dsProperties = new Properties();
        dsProperties.setProperty("url", environment.getRequiredProperty("hibernate.url"));
        dsProperties.setProperty("user", environment.getRequiredProperty("hibernate.user"));
        dsProperties.setProperty("password", environment.getRequiredProperty("hibernate.password"));
        hikariConfig.setDataSourceProperties(dsProperties);
        return hikariConfig;
    }


    @Bean(name = "dataSource")
    public HikariDataSource dataSource() {
        return new HikariDataSource(getHikariConfig());
    }


    @Bean
    public HibernateTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

    @Bean
    public TripTrackerInfoRepository tripTrackerInfoRepository() {
        return new TripTrackerInfoRepository(mongoTemplate());
    }

    @Bean
    public TripLocationRepository tripLocationRepository() {
        return new TripLocationRepository(mongoTemplate());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        final String host = environment.getRequiredProperty("mongo.host");
        final int port = Integer.parseInt(environment.getRequiredProperty("mongo.port"));
        if(environment.getRequiredProperty("mongo.user").isEmpty())
        {
            MongoClient mongoClient  = new MongoClient(new ServerAddress(host, port));
            SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, "AuditTrip");
            return new MongoTemplate(mongoDbFactory);
        }
        MongoCredential credential = MongoCredential.createCredential(environment.getRequiredProperty("mongo.user"),
                "AuditTrip", environment.getRequiredProperty("mongo.password").toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress(host,
                Integer.parseInt(environment.getRequiredProperty("mongo.port"))),
                Collections.singletonList(credential));
        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, "AuditTrip");
        return new MongoTemplate(mongoDbFactory);
    }


}