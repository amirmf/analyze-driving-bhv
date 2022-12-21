package com.taraan.dum.da;


import com.taraan.dum.model.hibernate.Badge;
import com.taraan.dum.model.hibernate.SosRequest;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BadgeDa extends GenericDa<Badge> {
    public Badge get(String code) {
        return (Badge) this.getSession().createNamedQuery("badge-by-code").setParameter("CODE", code).getSingleResult();
    }

    public List<Badge> get(Boolean all, String name, String code, Long from, Long size) {
        String queryString = "select badge from Badge badge";
        String whereString = "";
        if (!all)
            whereString += " and badge.dateRange.to is null ";
        if (code != null && !code.isEmpty())
            whereString += " and badge.code = :CODE";
        if (name != null && !name.isEmpty())
            whereString += " and badge.name = :NAME";
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4) + " order by badge.id";
        else
            queryString += " order by badge.id";

        final Query query = this.getSession().createQuery(queryString);
        if (code != null && !code.isEmpty())
            query.setParameter("CODE", code);
        if (name != null && !name.isEmpty())
            query.setParameter("NAME", name);
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());
        return query.list();
    }

    public Long getCount(Boolean all, String name, String code) {
        String queryString = "select count(badge.id) from Badge badge";
        String whereString = "";
        if (!all)
            whereString += " and badge.dateRange.to is null ";
        if (code != null && !code.isEmpty())
            whereString += " and badge.code = :CODE";
        if (name != null && !name.isEmpty())
            whereString += " and badge.name = :NAME";
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        final Query query = this.getSession().createQuery(queryString);
        if (code != null && !code.isEmpty())
            query.setParameter("CODE", code);
        if (name != null && !name.isEmpty())
            query.setParameter("NAME", name);
        return (Long) query.getSingleResult();
    }

    public List<Badge> getByNotIds(List<Long> badgeIds, int from, int size) {
        return this.getSession().createNamedQuery("badge-by-notIds").setParameter("IDS", badgeIds).setFirstResult(from).setMaxResults(size).list();
    }
}
