package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.Offer;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
public class OfferDa extends GenericDa<Offer> {
    public List<Offer> get(Long user, Long from, Long size) {
        Query query = getSession().createQuery("select offer from Offer offer where offer.user.id = :userId");
        query.setParameter("userId", user);
        query.setFirstResult(from.intValue());
        query.setMaxResults(size.intValue());
        return query.getResultList();
    }

    public Long getCount(Long user) {
        Query query = getSession().createQuery("select count(offer.id) from Offer offer where offer.user.id = :userId");
        query.setParameter("userId", user);
        return (Long) query.getSingleResult();
    }

    public List<Offer> get(String title, String phoneNumber, Date fromDate, Date toDate, Long from, Long size) {
        String queryString = "select offer from Offer offer inner join offer.user user ";
        String whereString = "";
        if (title != null && !title.isEmpty()) {
            whereString += " and offer.title = :TITLE";
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            whereString += " and user.phoneNumber = :PHONE_NUMBER";
        }
        if (fromDate != null) {
            whereString += " and offer.date > :FROM_DATE";
        }
        if (toDate != null) {
            whereString += " and offer.date < :TO_DATE";
        }
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        Query query = getSession().createQuery(queryString);

        if (title != null && !title.isEmpty()) {
            query.setParameter("TITLE", title);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            query.setParameter("PHONE_NUMBER", title);
        }
        if (fromDate != null) {
            query.setParameter("FROM_DATE", title);

        }
        if (toDate != null) {
            query.setParameter("TO_DATE", title);
        }
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());
        return query.getResultList();

    }

    public Long getCount(String title, String phoneNumber, Date fromDate, Date toDate) {
        String queryString = "select count(offer.id) from Offer offer inner join offer.user user ";
        String whereString = "";
        if (title != null && !title.isEmpty()) {
            whereString += " and offer.title = :TITLE";
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            whereString += " and user.phoneNumber = :PHONE_NUMBER";
        }
        if (fromDate != null) {
            whereString += " and offer.date > :FROM_DATE";
        }
        if (toDate != null) {
            whereString += " and offer.date < :TO_DATE";
        }
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        Query query = getSession().createQuery(queryString);

        if (title != null && !title.isEmpty()) {
            query.setParameter("TITLE", title);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            query.setParameter("PHONE_NUMBER", title);
        }
        if (fromDate != null) {
            query.setParameter("FROM_DATE", title);

        }
        if (toDate != null) {
            query.setParameter("TO_DATE", title);
        }
        return (Long) query.getSingleResult();
    }
}
