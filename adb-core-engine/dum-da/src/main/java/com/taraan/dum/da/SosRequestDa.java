package com.taraan.dum.da;


import com.taraan.dum.model.hibernate.SosRequest;
import com.taraan.dum.model.hibernate.SosRequestState;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SosRequestDa extends GenericDa<SosRequest> {
    public List<SosRequest> get(Long from, Long size) {
        return getSession().createQuery("select sosRequest from SosRequest sosRequest where sosRequest.dateRange.to is  null ").setFirstResult(from.intValue()).setMaxResults(size.intValue()).getResultList();
    }

    public Long getCount() {
        return (Long) getSession().createQuery("select count(sosRequest.id) from SosRequest sosRequest where sosRequest.dateRange.to is  null ").getSingleResult();
    }


    public List<SosRequest> get(Long userId, SosRequestState sosRequestState, Long from, Long size) {
        String queryString = "select sosRequest from SosRequest sosRequest where sosRequest.dateRange.to is  null";
        if (userId != null) {
            queryString += " and sosRequest.user.id = :USER_ID";
        }
        if (sosRequestState != null) {
            queryString += " and sosRequest.sosRequestState = :STATE";
        }

        final Query query = getSession().createQuery(queryString);
        if (userId != null) {
            query.setParameter("USER_ID", userId);
        }

        if (sosRequestState != null) {
            query.setParameter("STATE", sosRequestState.name());
        }
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());

        return query.getResultList();
    }

    public Long getCount(Long userId, SosRequestState sosRequestState) {
        String queryString = "select count(sosRequest.id) from SosRequest sosRequest where sosRequest.dateRange.to is  null ";
        if (userId != null) {
            queryString += " and sosRequest.user.id = :USER_ID";
        }
        if (sosRequestState != null) {
            queryString += " and sosRequest.sosRequestState = :STATE";
        }

        final Query query = getSession().createQuery(queryString);
        if (userId != null) {
            query.setParameter("USER_ID", userId);
        }

        if (sosRequestState != null) {
            query.setParameter("STATE", sosRequestState.name());
        }
        return (Long) query.getSingleResult();
    }

}
