package com.taraan.dum.analyzer.query;

import com.taraan.dum.model.hibernate.Trip;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class QueryBuilder<T> extends HibernateDaoSupport {

    public QueryBuilder() {
    }

    public void defineSessionFactory(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    String baseQuery;
    String whereString = "";
    String orderQueryString = "";
    Integer firstResult = null;
    Integer maxResult = null;
    Map<String, Object> parameters = new HashMap<>();

    public QueryBuilder(String baseQuery) {
        this.baseQuery = baseQuery;
    }

    public List<T> result() {
        String queryString = baseQuery;

        if (whereString != null && !whereString.isEmpty()) {
            whereString = whereString.substring(4);
            queryString = baseQuery + " where " + whereString;
        }
        if (orderQueryString != null && !orderQueryString.isEmpty()) {
            queryString = queryString + " order by " + orderQueryString;
        }
        final Query query = this.getSessionFactory().getCurrentSession().createQuery(queryString);
        if (parameters != null) {
            parameters.forEach(query::setParameter);
        }

        if (maxResult != null)
            query.setMaxResults(maxResult);
        if (firstResult != null)
            query.setFirstResult(firstResult);

        return query.list();
    }

    public Long count()
    {
        return Long.valueOf(this.result().size());
    }
}
