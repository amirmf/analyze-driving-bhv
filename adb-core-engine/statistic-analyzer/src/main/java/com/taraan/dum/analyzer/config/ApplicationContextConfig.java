package com.taraan.dum.analyzer.config;

import com.taraan.dum.analyzer.listener.JmsMessageListener;
import com.taraan.dum.analyzer.logger.AuditLog;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * @author Amir Mansouri Fard (amf.fard@gmail.com) on 3/30/2018
 */

@Configuration
@ComponentScan("com.taraan.dum")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@PropertySource(value = {"classpath:config.properties"})
@ImportResource("classpath:spring.xml")
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

    @Bean(name = "jmsMessageListener")
    public JmsMessageListener jmsMessageListener(){

        return new JmsMessageListener();
    }

    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(){
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(new ActiveMQConnectionFactory(environment.getRequiredProperty("analyze.qm")));
        container.setDestination(new ActiveMQQueue(environment.getRequiredProperty("analyze.queue")));
        container.setMessageListener(jmsMessageListener());
        container.setConcurrentConsumers(200);//config
        container.setAcceptMessagesWhileStopping(false);//config
        container.setRecoveryInterval(10000);//config
        container.setCacheLevelName("CACHE_CONSUMER");//config

        return container;
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


}