package com.taraan.dum.da;


import com.taraan.dum.dto.message.MessageCountResponse;
import com.taraan.dum.model.hibernate.Message;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class MessageDa extends GenericDa<Message> {

    public List<Message> getByUser(Long userId, int from, int size) {
        return this.getSession().createQuery("select message from Message message where message.user.id=:User_ID order by id desc")
                .setParameter("User_ID", userId).setMaxResults(size).setFirstResult(from).list();
    }

    public Long getCountByUser(Long userId) {
        return (Long) this.getSession().createQuery("select count(message.id) from Message message where message.user.id=:User_ID")
                .setParameter("User_ID", userId).getSingleResult();
    }

    public Long getUnreadCountByUser(Long userId) {
        return (Long) this.getSession().createQuery("select count(message.id) from Message message where message.user.id=:User_ID and message.read=false")
                .setParameter("User_ID", userId).getSingleResult();
    }

    public void updateUnreadMessagees(Long userId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("USER_ID", userId);
        this.executeNamedUpdate("update-unread-messages", params);
    }

    public List<Message> get(String phoneNumber, Date fromDate, Date toDate, Long from, Long size) {
        String queryString = "select message from Message message inner join message.user user";
        String whereString = "";
        if (phoneNumber != null && !phoneNumber.isEmpty())
            whereString += " and user.phoneNumber = :PHONE_NUMBER";
        if (fromDate != null)
            whereString += " and message.date > :FROM_DATE";
        if (toDate != null)
            whereString += " and message.date < :TO_DATE";
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        Query query = this.getSession().createQuery(queryString);
        if (phoneNumber != null && !phoneNumber.isEmpty())
            query.setParameter("PHONE_NUMBER", phoneNumber);
        if (fromDate != null)
            query.setParameter("FROM_DATE", fromDate);
        if (toDate != null)
            query.setParameter("TO_DATE", toDate);
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());
        return query.list();
    }

    public Long getCount(String phoneNumber, Date fromDate, Date toDate) {
        String queryString = "select count(message.id) from Message message inner join message.user user";
        String whereString = "";
        if (phoneNumber != null && !phoneNumber.isEmpty())
            whereString += " and user.phoneNumber = :PHONE_NUMBER";
        if (fromDate != null)
            whereString += " and message.date > :FROM_DATE";
        if (toDate != null)
            whereString += " and message.date < :TO_DATE";
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        Query query = this.getSession().createQuery(queryString);
        if (phoneNumber != null && !phoneNumber.isEmpty())
            query.setParameter("PHONE_NUMBER", phoneNumber);
        if (fromDate != null)
            query.setParameter("FROM_DATE", fromDate);
        if (toDate != null)
            query.setParameter("TO_DATE", toDate);

        return (Long) query.uniqueResult();
    }
}
