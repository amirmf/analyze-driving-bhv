package com.taraan.dum.da;

import com.taraan.dum.dto.reminder.ReminderPage;
import com.taraan.dum.model.hibernate.Reminder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class ReminderDa extends GenericDa<Reminder> {
    public Reminder getByUserId(Long userId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return this.executeNamedQuerySingleResult("reminder-by-userid", params);
    }

    public List<Reminder> getRemindersByInsuranceExpirationDate(Date from, Date to) {
        return this.getSession().createQuery("select distinct reminder from Reminder reminder where reminder.insuranceExpirationDate >= :FROM_DATE and reminder.insuranceExpirationDate < :TO_DATE and reminder.active=true")
                .setParameter("FROM_DATE", from).setParameter("TO_DATE", to).getResultList();
    }

    public List<Reminder> getRemindersByNextServiceDate(Date from, Date to) {
        return this.getSession().createQuery("select distinct reminder from Reminder reminder where reminder.nextServiceDate >= :FROM_DATE" +
                " and reminder.nextServiceDate < :TO_DATE and reminder.active=true")
                .setParameter("FROM_DATE", from).setParameter("TO_DATE", to).getResultList();
    }

    public List<Reminder> gets(String phoneNumber, Date fromExpiration, Date toExpiration, Date fromRegister, Date toRegister, Long from, Long size) {
        String queryString = "select reminder from Reminder reminder inner join reminder.user user ";
        String whereString = "";
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            whereString += " and user.phoneNumber = :PHONE_NUMBER ";
        }
        if (fromExpiration != null) {
            whereString += " and reminder.insuranceExpirationDate >= :FROM_EXPIRATION_DATE ";
        }
        if (toExpiration != null) {
            whereString += " and reminder.insuranceExpirationDate <= :TO_EXPIRATION_DATE ";
        }
        if (fromRegister != null) {
            whereString += " and reminder.insuranceRegisterOn >= :FROM_REGISTER_DATE ";
        }
        if (toRegister != null) {
            whereString += " and reminder.insuranceRegisterOn <= :TO_REGISTER_DATE ";
        }
        if (!whereString.isEmpty()) {
            queryString += " where " + whereString.substring(4);
        }
        final Query query = this.getSession().createQuery(queryString);

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            query.setParameter("PHONE_NUMBER", phoneNumber);
        }
        if (fromExpiration != null) {
            query.setParameter("FROM_EXPIRATION_DATE", fromExpiration);
        }
        if (toExpiration != null) {
            query.setParameter("TO_EXPIRATION_DATE", toExpiration);
        }
        if (fromRegister != null) {
            query.setParameter("FROM_REGISTER_DATE", fromRegister);
        }
        if (toRegister != null) {
            query.setParameter("TO_REGISTER_DATE", toRegister);
        }
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());
        return query.list();
    }

    public Long getCounts(String phoneNumber, Date fromExpiration, Date toExpiration, Date fromRegister, Date toRegister) {
        String queryString = "select count(reminder.id) from Reminder reminder inner join reminder.user user ";
        String whereString = "";
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            whereString += " and user.phoneNumber = :PHONE_NUMBER ";
        }
        if (fromExpiration != null) {
            whereString += " and reminder.insuranceExpirationDate >= :FROM_EXPIRATION_DATE ";
        }
        if (toExpiration != null) {
            whereString += " and reminder.insuranceExpirationDate <= :TO_EXPIRATION_DATE ";
        }
        if (fromRegister != null) {
            whereString += " and reminder.insuranceRegisterOn >= :FROM_REGISTER_DATE ";
        }
        if (toRegister != null) {
            whereString += " and reminder.insuranceRegisterOn <= :TO_REGISTER_DATE ";
        }
        if (!whereString.isEmpty()) {
            queryString += " where " + whereString.substring(4);
        }
        final Query query = this.getSession().createQuery(queryString);

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            query.setParameter("PHONE_NUMBER", phoneNumber);
        }
        if (fromExpiration != null) {
            query.setParameter("FROM_EXPIRATION_DATE", fromExpiration);
        }
        if (toExpiration != null) {
            query.setParameter("TO_EXPIRATION_DATE", toExpiration);
        }
        if (fromRegister != null) {
            query.setParameter("FROM_REGISTER_DATE", fromExpiration);
        }
        if (toRegister != null) {
            query.setParameter("TO_REGISTER_DATE", toRegister);
        }
        return (Long) query.getSingleResult();
    }
}
