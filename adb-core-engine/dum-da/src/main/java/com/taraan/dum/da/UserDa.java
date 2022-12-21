package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.User;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Component
public class UserDa extends GenericDa<User> {
    public User getByEmail(String email) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(getType());
        Root<User> root = criteria.from(getType());
        criteria.select(root);
        Predicate where = builder.equal(root.get("email"), email);
        criteria.where(where);
        try {
            return getSession().createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User getByPhoneNumber(String email) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(getType());
        Root<User> root = criteria.from(getType());
        criteria.select(root);
        Predicate where = builder.equal(root.get("phoneNumber"), email);
        criteria.where(where);
        try {
            return getSession().createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Long> getDrivingUserIds(Date from, Date to) {
        return this.getSession().createQuery("select distinct trip.user.id from Trip trip where trip.tripEndDate > :FROM_DATE and trip.tripEndDate <= :TO_DATE and trip.valid=true")
                .setParameter("FROM_DATE", from).setParameter("TO_DATE", to).getResultList();
    }

    public List<User> get(String phoneNumber, String email, String firstName, String lastName, Long from, Long size) {
        String queryString = "select user from User user";
        String whereString = "";
        if (phoneNumber != null && !phoneNumber.isEmpty())
            whereString = "and user.phoneNumber =:PHONE_NUMBER";
        if (email != null && !email.isEmpty())
            whereString = "and user.email =:EMAIL";
        if (firstName != null && !firstName.isEmpty())
            whereString = "and user.firstName  like :FIRST_NAME";
        if (lastName != null && !lastName.isEmpty())
            whereString = "and user.lastName like :LAST_NAME";

        if (!whereString.isEmpty())
            queryString += whereString.substring(4);
        Query query = getSession().createQuery(queryString);
        if (phoneNumber != null && !phoneNumber.isEmpty())
            query.setParameter("PHONE_NUMBER", phoneNumber);
        if (email != null && !email.isEmpty())
            query.setParameter("EMAIL", email);
        if (firstName != null && !firstName.isEmpty())
            query.setParameter("FIRST_NAME", firstName.replace("*", "%"));
        if (lastName != null && !lastName.isEmpty())
            query.setParameter("LAST_NAME", lastName.replace("*", "%"));
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());
        return query.getResultList();
    }

    public Long getCount(String phoneNumber, String email, String firstName, String lastName) {
        String queryString = "select count(user.id) from User user";
        String whereString = "";
        if (phoneNumber != null && !phoneNumber.isEmpty())
            whereString = "and user.phoneNumber =:PHONE_NUMBER";
        if (email != null && !email.isEmpty())
            whereString = "and user.email =:EMAIL";
        if (firstName != null && !firstName.isEmpty())
            whereString = "and user.firstName  like :FIRST_NAME";
        if (lastName != null && !lastName.isEmpty())
            whereString = "and user.lastName like :LAST_NAME";

        if (!whereString.isEmpty())
            queryString += " where " +whereString.substring(4);
        Query query = getSession().createQuery(queryString);
        if (phoneNumber != null && !phoneNumber.isEmpty())
            query.setParameter("PHONE_NUMBER", phoneNumber);
        if (email != null && !email.isEmpty())
            query.setParameter("EMAIL", email);
        if (firstName != null && !firstName.isEmpty())
            query.setParameter("FIRST_NAME", firstName.replace("*", "%"));
        if (lastName != null && !lastName.isEmpty())
            query.setParameter("LAST_NAME", lastName.replace("*", "%"));
        return (Long) query.getSingleResult();
    }
}
