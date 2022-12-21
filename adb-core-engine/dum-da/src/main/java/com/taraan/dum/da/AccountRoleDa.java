package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.Account;
import com.taraan.dum.model.hibernate.AccountRole;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class AccountRoleDa extends GenericDa<AccountRole> {


    public List<AccountRole> get(String name, String code, Long from, Long size) {
        String queryString = "select accountRole from AccountRole accountRole";
        String whereString = "";
        if (name != null && !name.isEmpty())
            whereString += " and accountRole.name = :NAME ";
        if (code != null && !code.isEmpty())
            whereString += " and accountRole.code = :CODE ";
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        final Query query = this.getSession().createQuery(queryString);
        if (name != null && !name.isEmpty())
            query.setParameter("NAME", name);
        if (code != null && !code.isEmpty())
            query.setParameter("CODE", code);
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());
        return query.list();
    }
    public Long getCount(String name, String code) {
        String queryString = "select count(accountRole.id) from AccountRole accountRole";
        String whereString = "";
        if (name != null && !name.isEmpty())
            whereString += " and accountRole.name = :NAME ";
        if (code != null && !code.isEmpty())
            whereString += " and accountRole.code = :CODE ";
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        final Query query = this.getSession().createQuery(queryString);
        if (name != null && !name.isEmpty())
            query.setParameter("NAME", name);
        if (code != null && !code.isEmpty())
            query.setParameter("CODE", code);
        return (Long) query.uniqueResult();
    }
}
