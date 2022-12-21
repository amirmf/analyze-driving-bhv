package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.AccountGroup;
import com.taraan.dum.model.hibernate.AccountRole;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountGroupDa extends GenericDa<AccountGroup> {

    public List<AccountGroup> get(String name, String code, Long from, Long size) {
        String queryString = "select accountGroup from AccountGroup accountGroup";
        String whereString = "";
        if (name != null && !name.isEmpty())
            whereString += " and accountGroup.name = :NAME ";
        if (code != null && !code.isEmpty())
            whereString += " and accountGroup.code = :CODE ";
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
        String queryString = "select count(accountGroup.id) from AccountGroup accountGroup";
        String whereString = "";
        if (name != null && !name.isEmpty())
            whereString += " and accountGroup.name = :NAME ";
        if (code != null && !code.isEmpty())
            whereString += " and accountGroup.code = :CODE ";
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
