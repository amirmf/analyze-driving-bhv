package com.taraan.dum.da;


import com.taraan.dum.model.hibernate.Challenge;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Component
public class ChallengeDa extends GenericDa<Challenge> {
    public List<Challenge> get(String name, boolean active, Long from, Long size) {
        String queryString = "select challenge from Challenge challenge";
        String whereString = "";
        if (name != null && !name.isEmpty())
            whereString += " and challenge.name = :NAME";
        whereString += " and challenge.active = :ACTIVE";

        if (!whereString.isEmpty())
            queryString = queryString + "where" + whereString.substring(4);
        final Query query = this.getSession().createQuery(queryString);
        if (name != null && !name.isEmpty())
            query.setParameter("NAME", name);
        query.setParameter("ACTIVE", active);
        return query.list();
    }

    public List<Challenge> getByAnalyzeFormula(Long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("ANALYZE_FORMULA_ID", id);
        return this.executeNamedQuery("challenge-by-analyzeFormula", params);
    }

    public List<Challenge> getDisplayChallenge(Long from, Long size) {
        Query query = this.getSession().createQuery("select challenge from Challenge challenge where challenge.display = true order by challenge.id desc");
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());
        return query.list();
    }

    public List<Challenge> get(String name, Date fStartDate, Date tStartDate, Date fEndDate, Date tEndDate, Long from, Long size) {
        String queryString = "select challenge from Challenge challenge  ";
        String whereString = "";
        if (name != null && !name.isEmpty()) {
            whereString += " and challenge.name = :NAME ";
        }
        if (fStartDate != null) {
            whereString += " and challenge.dateRange.from >= :FROM_START_DATE ";
        }
        if (tStartDate != null) {
            whereString += " and challenge.dateRange.from <= :TO_START_DATE ";
        }
        if (fEndDate != null) {
            whereString += " and challenge.dateRange.to >= :FROM_END_DATE ";
        }
        if (tEndDate != null) {
            whereString += " and challenge.dateRange.to <= :TO_END_DATE ";
        }
        if (!whereString.isEmpty()) {
            queryString += " where " + whereString.substring(4);
        }
        final Query query = this.getSession().createQuery(queryString);

        if (name != null && !name.isEmpty()) {
            query.setParameter("NAME", name);
        }
        if (fStartDate != null) {
            query.setParameter("FROM_START_DATE", fStartDate);
        }
        if (tStartDate != null) {
            query.setParameter("TO_START_DATE", tStartDate);
        }
        if (fEndDate != null) {
            query.setParameter("FROM_END_DATE", fEndDate);
        }
        if (tEndDate != null) {
            query.setParameter("TO_END_DATE", tEndDate);
        }
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());
        return query.list();
    }

    public Long getCount(String name, Date fStartDate, Date tStartDate, Date fEndDate, Date tEndDate) {
        String queryString = "select count(challenge.id) from Challenge challenge  ";
        String whereString = "";
        if (name != null && !name.isEmpty()) {
            whereString += " and challenge.name = :NAME ";
        }
        if (fStartDate != null) {
            whereString += " and challenge.dateRange.from >= :FROM_START_DATE ";
        }
        if (tStartDate != null) {
            whereString += " and challenge.dateRange.from <= :TO_START_DATE ";
        }
        if (fEndDate != null) {
            whereString += " and challenge.dateRange.to >= :FROM_END_DATE ";
        }
        if (tEndDate != null) {
            whereString += " and challenge.dateRange.to <= :TO_END_DATE ";
        }
        if (!whereString.isEmpty()) {
            queryString += " where " + whereString.substring(4);
        }
        final Query query = this.getSession().createQuery(queryString);

        if (name != null && !name.isEmpty()) {
            query.setParameter("NAME", name);
        }
        if (fStartDate != null) {
            query.setParameter("FROM_START_DATE", fStartDate);
        }
        if (tStartDate != null) {
            query.setParameter("TO_START_DATE", tStartDate);
        }
        if (fEndDate != null) {
            query.setParameter("FROM_END_DATE", fEndDate);
        }
        if (tEndDate != null) {
            query.setParameter("TO_END_DATE", tEndDate);
        }
        return (Long) query.getSingleResult();
    }
}
