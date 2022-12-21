package com.taraan.dum.analyzer.query;

import com.taraan.dum.model.hibernate.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserBadgeQueryBuilder extends QueryBuilder<User> implements ApplicationContextAware {

    public UserBadgeQueryBuilder() {
    }

    public static UserBadgeQueryBuilder createInstance() {
        return new UserBadgeQueryBuilder("Select userBadge from UserBadge userBadge inner join userBadge.badge badge ");
    }

    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserBadgeQueryBuilder.applicationContext = applicationContext;
        this.defineSessionFactory(UserBadgeQueryBuilder.applicationContext.getBean(SessionFactory.class));
    }

    private UserBadgeQueryBuilder(String baseQuery) {
        super(baseQuery);
        this.defineSessionFactory(UserBadgeQueryBuilder.applicationContext.getBean(SessionFactory.class));
    }

    /**
     * sort result by id desc
     *
     * @return UserBadgeQueryBuilder
     */
    public UserBadgeQueryBuilder setOrderIdDesc() {
        this.orderQueryString = "userBadge.id desc";
        return this;
    }

    /**
     * @param date filter badge.date &lt; date
     * @return UserBadgeQueryBuilder
     */
    public UserBadgeQueryBuilder dateBefore(Date date) {
        this.whereString += " and userBadge.date < :USER_BADGE_DATE_BEFORE";
        this.parameters.put("USER_BADGE_DATE_BEFORE", date);
        return this;
    }

    /**
     * @param date filter badge.date &gt; date
     * @return UserBadgeQueryBuilder
     */
    public UserBadgeQueryBuilder dateAfter(Date date) {
        this.whereString += " and userBadge.date > :USER_BADGE_DATE_AFTER";
        this.parameters.put("USER_BIRTH_DATE_AFTER", date);
        return this;
    }

    /**
     * @param code filter badge.code = code
     * @return UserBadgeQueryBuilder
     */
    public UserBadgeQueryBuilder badgeCodeEqual(String code) {
        this.whereString += " and badge.code = :BADGE_CODE_EQUAL";
        this.parameters.put("BADGE_CODE_EQUAL", code);
        return this;
    }

    /**
     * @param id filter user.id = id
     * @return UserBadgeQueryBuilder
     */
    public UserBadgeQueryBuilder userIdEqual(Long id) {
        this.whereString += " and userBadge.user.id = :USER_ID_EQUAL";
        this.parameters.put("USER_ID_EQUAL", id);
        return this;
    }

    /**
     * @param first set first index of result = first
     * @return UserBadgeQueryBuilder
     */
    public UserBadgeQueryBuilder setFirstResult(int first) {
        this.firstResult = first;
        return this;
    }

    /**
     * @param maxResult set result size = maxResult
     * @return UserBadgeQueryBuilder
     */
    public UserBadgeQueryBuilder setMaxResult(int maxResult) {
        this.maxResult = maxResult;
        return this;
    }

}
