package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.Account;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class AccountDa extends GenericDa<Account> {
    public Account getByUsername(String email) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(getType());
        Root<Account> root = criteria.from(getType());
        criteria.select(root);
        Predicate where = builder.equal(root.get("username"), email);
        criteria.where(where);
        try {
            return getSession().createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Account> getActiveAccount(String username, Boolean enabled, Long from, Long size) {
        String queryString = "select account from Account account where account.dateRange.to is null ";
        if (username != null && !username.isEmpty())
            queryString += " and account.username = :USERNAME ";
        if (enabled != null)
            queryString += " and account.enabled = :ENABLED ";

        final Query query = this.getSession().createQuery(queryString);
        if (username != null && !username.isEmpty())
            query.setParameter("USERNAME", username);
        if (enabled != null)
            query.setParameter("ENABLED", enabled);
        if (size != null)
            query.setMaxResults(size.intValue());
        if (from != null)
            query.setFirstResult(from.intValue());
        return ((org.hibernate.query.Query) query).list();
    }

    public Long getCountActiveAccount(String username, Boolean enabled) {
        String queryString = "select count(account.id) from Account account where account.dateRange.to is null ";
        if (username != null && !username.isEmpty())
            queryString += " and account.username = :USERNAME ";
        if (enabled != null)
            queryString += " and account.enabled = :ENABLED ";

        final Query query = this.getSession().createQuery(queryString);
        if (username != null && !username.isEmpty())
            query.setParameter("USERNAME", username);
        if (enabled != null)
            query.setParameter("ENABLED", enabled);
        return (Long) ((org.hibernate.query.Query) query).getSingleResult();
    }

    public Long getActiveAccountCount() {
        String queryString = "select count(account.id) from Account account where account.dateRange.to is null";
        return (Long) this.getSession().createQuery(queryString).getSingleResult();
    }


}
