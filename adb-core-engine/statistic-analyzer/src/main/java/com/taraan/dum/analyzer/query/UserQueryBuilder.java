package com.taraan.dum.analyzer.query;

import com.taraan.dum.model.hibernate.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserQueryBuilder extends QueryBuilder<User> implements ApplicationContextAware {

    public UserQueryBuilder() {
    }

    public static UserQueryBuilder createInstance() {
        return new UserQueryBuilder("Select user from User user ");
    }

    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserQueryBuilder.applicationContext = applicationContext;
        this.defineSessionFactory(UserQueryBuilder.applicationContext.getBean(SessionFactory.class));
    }

    private UserQueryBuilder(String baseQuery) {
        super(baseQuery);
        this.defineSessionFactory(this.applicationContext.getBean(SessionFactory.class));
    }

    /**
     * sort result by id desc
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder setOrderIdDesc() {
        this.orderQueryString = "user.id desc";
        return this;
    }

    /**
     * @param id filter user.id == id
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder idEqual(Long id) {
        this.whereString += " and user.id = :ID_EQUAL";
        this.parameters.put("ID_EQUAL", id);
        return this;
    }

    /**
     * @param id filter user.id != id
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder idNotEqual(Long id) {
        this.whereString += " and user.id <> :ID_NOT_EQUAL";
        this.parameters.put("ID_NOT_EQUAL", id);
        return this;
    }

    /**
     * @param id filter user.id &lt; id
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder idBefore(Long id) {
        this.whereString += " and user.id < :ID_BEFORE";
        this.parameters.put("ID_BEFORE", id);
        return this;
    }

    /**
     * @param id filter user.id &gt; id
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder idAfter(Long id) {
        this.whereString += " and user.id > :ID_AFTER";
        this.parameters.put("ID_AFTER", id);
        return this;
    }

    /**
     * @param mail filter user.email == mail
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder emailEqual(String mail) {
        this.whereString += " and user.email = :EMAIL_EQUAL";
        this.parameters.put("EMAIL_EQUAL", mail);
        return this;
    }

    /**
     * @param phoneNumber filter user.phoneNumber == phoneNumber
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder phoneNumberEqual(String phoneNumber) {
        this.whereString += " and user.phoneNumber = :PHONE_NUMBER_EQUAL";
        this.parameters.put("PHONE_NUMBER_EQUAL", phoneNumber);
        return this;
    }

    /**
     * @param name filter user.name == name
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder nameEqual(String name) {
        this.whereString += " and user.name = :NAME_EQUAL";
        this.parameters.put("NAME_EQUAL", name);
        return this;
    }

    /**
     * @param name filter user.name contain name
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder nameContain(String name) {
        this.whereString += " and user.name like :NAME_CONTAIN";
        this.parameters.put("NAME_CONTAIN", "%" + name + "%");
        return this;
    }

    /**
     * @param firstName filter user.firstName == firstName
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder firstNameEqual(String firstName) {
        this.whereString += " and user.firstName = :FIRST_NAME_EQUAL";
        this.parameters.put("FIRST_NAME_EQUAL", firstName);
        return this;
    }
    /**
     * @param firstName filter user.firstName contain firstName
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder firstNameContain(String firstName) {
        this.whereString += " and user.firstName like :FIRST_NAME_CONTAIN";
        this.parameters.put("FIRST_NAME_CONTAIN", "%" + firstName + "%");
        return this;
    }

    /**
     * @param lastName filter user.lastName == lastName
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder lastNameEqual(String lastName) {
        this.whereString += " and user.lastName = :LAST_NAME_EQUAL";
        this.parameters.put("LAST_NAME_EQUAL", lastName);
        return this;
    }
    /**
     * @param lastName filter user.lastName contain lastName
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder lastNameContain(String lastName) {
        this.whereString += " and user.firstName like :LAST_NAME_CONTAIN";
        this.parameters.put("LAST_NAME_CONTAIN", "%" + lastName + "%");
        return this;
    }

    /**
     * @param city filter user.city == city
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder cityEqual(String city) {
        this.whereString += " and user.city = :CITY_EQUAL";
        this.parameters.put("CITY_EQUAL", city);
        return this;
    }

    /**
     * @param birthDate filter user.birthDate &lt; birthDate
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder birthDateBefore(Date birthDate) {
        this.whereString += " and user.birthDate < :USER_BIRTH_DATE_BEFORE";
        this.parameters.put("USER_BIRTH_DATE_BEFORE", birthDate);
        return this;
    }

    /**
     * @param birthDate filter user.birthDate &gt; birthDate
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder birthDateAfter(Date birthDate) {
        this.whereString += " and user.birthDate > :USER_BIRTH_DATE_AFTER";
        this.parameters.put("USER_BIRTH_DATE_AFTER", birthDate);
        return this;
    }

    /**
     * @param first set first index of result = first
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder setFirstResult(int first) {
        this.firstResult = first;
        return this;
    }

    /**
     * @param maxResult set result size = maxResult
     *
     * @return UserQueryBuilder
     */
    public UserQueryBuilder setMaxResult(int maxResult) {
        this.maxResult = maxResult;
        return this;
    }
}
