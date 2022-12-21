package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericDa<T extends BaseEntity> extends HibernateDaoSupport {
    @Autowired
    public void defineSessionFactory(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }


    public T save(T t) {
        try {
            this.currentSession().persist(t);
            this.currentSession().flush();
        } catch (Exception e) {
            this.currentSession().clear();
            throw e;
        }
        return t;
    }

    public List<T> save(List<T> ts) {
        List<T> list = new ArrayList<>();
        for (T t : ts) {
            list.add(save(t));
        }
        return list;
    }

    public T update(T t) {
        final Session session = this.currentSession();
        try {
            this.currentSession().merge((session).get(t.getClass(),
                    ((BaseEntity) t).getId()));
            session.merge(t);
            session.flush();
        } catch (Exception e) {
            session.clear();
            throw e;
        }
        return t;
    }

    public List<T> update(List<T> ts) {
        List<T> list = new ArrayList<>();
        for (T t : ts) {
            list.add(update(t));
        }
        return list;
    }

    public T delete(T t) {
        final Session session = this.currentSession();
        try {
            session.delete(session.get(t.getClass(),
                    ((BaseEntity) t).getId()));
            session.flush();
        } catch (Exception e) {
            session.clear();
            throw e;
        }
        return t;
    }

    public void delete(Long id) {
        final Session session = this.currentSession();
        try {
            session.delete(session.get(getType(), id));
            session.flush();
        } catch (Exception e) {
            session.clear();
            throw e;
        }
    }

    public List<T> delete(List<T> ts) {
        List<T> list = new ArrayList<>();
        for (T t : ts) {
            list.add(delete(t));
        }
        return list;
    }

    public T selectSingle(String queryName, Map<String, Object> params) {
        Query query = this.currentSession().getNamedQuery(queryName);
        if (params != null) {
            params.entrySet().forEach(
                    e -> query.setParameter(e.getKey(), e.getValue()));
        }
        T t = (T) query.uniqueResult();
        this.currentSession().flush();
        this.currentSession().clear();
        return t;
    }

    public T select(T t) {
        return (T) this.currentSession().get(t.getClass(),
                (t).getId());
    }

    public List get() {
        return this.getSession().createQuery("select entity from " + getStringType() + " entity order by entity.id")
                .list();
    }

    public Long getCount() {
        return (Long) this.getSession().createQuery("select count(entity.id) from " + getStringType() + " entity")
                .uniqueResult();
    }

    public String getStringType() {
        return this.getType().getName();
    }

    public List<T> executeNamedQuery(String queryName,
                                     Map<String, Object> params) {
        Query query = this.currentSession().getNamedQuery(queryName);
        if (params != null) {
            params.entrySet().forEach(
                    e -> query.setParameter(e.getKey(), e.getValue()));
        }
        List<T> result = query.list();
        this.currentSession().flush();
        this.currentSession().clear();
        return result;
    }

    public List<T> executeQuery(String queryString,
                                Map<String, Object> params, Integer from, Integer size) {
        Query query = this.currentSession().createQuery(queryString);
        if (params != null) {
            params.entrySet().forEach(
                    e -> query.setParameter(e.getKey(), e.getValue()));
        }
        if (from != null)
            query.setFirstResult(from);
        if (size != null)
            query.setMaxResults(size);
        List<T> result = query.list();
        this.currentSession().flush();
        this.currentSession().clear();
        return result;
    }

    public <V> V executeQuerySingleResult(String queryString,
                                          Map<String, Object> params, Class<V> vClass) {
        Query query = this.currentSession().createQuery(queryString);
        if (params != null) {
            params.entrySet().forEach(
                    e -> query.setParameter(e.getKey(), e.getValue()));
        }
        V result = (V) query.getSingleResult();
        this.currentSession().flush();
        this.currentSession().clear();
        return result;
    }

    public <X> List<X> executeNamedQuery(String queryName,
                                         Map<String, Object> params, Class<X> xClass) {
        Query query = this.currentSession().getNamedQuery(queryName);
        if (params != null) {
            params.entrySet().forEach(
                    e -> query.setParameter(e.getKey(), e.getValue()));
        }
        List<X> result = query.list();
        this.currentSession().flush();
        this.currentSession().clear();
        return result;
    }

    public List<T> executeNamedQuery(String queryName,
                                     Map<String, Object> params, Integer first, Integer max) {
        Query query = this.currentSession().getNamedQuery(queryName);
        if (params != null) {
            params.entrySet().forEach(
                    e -> query.setParameter(e.getKey(), e.getValue()));
        }
        if (first != null)
            query.setFirstResult(first);
        if (max != null)
            query.setMaxResults(max);
        List<T> result = query.getResultList();
        this.currentSession().flush();
        this.currentSession().clear();
        return result;
    }

    public T executeNamedQuerySingleResult(String queryName,
                                           Map<String, Object> params) {
        Query query = this.currentSession().getNamedQuery(queryName);
        if (params != null) {
            params.entrySet().forEach(
                    e -> query.setParameter(e.getKey(), e.getValue()));
        }
        return (T) query.uniqueResult();
    }

    public <X> X executeNamedQuerySingleResult(String queryName,
                                               Map<String, Object> params, Class<X> xClass) {
        Query query = this.currentSession().getNamedQuery(queryName);
        if (params != null) {
            params.entrySet().forEach(
                    e -> query.setParameter(e.getKey(), e.getValue()));
        }
        return (X) query.uniqueResult();
    }

    public int executeNamedUpdate(String queryName, Map<String, Object> params) {
        Query query = this.currentSession().getNamedQuery(queryName);
        if (params != null) {
            params.entrySet().forEach(
                    e -> query.setParameter(e.getKey(), e.getValue()));
        }

        return query.executeUpdate();
    }

    public boolean exists(Long idValue) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getType());
        Root<T> root = criteria.from(getType());
        criteria.select(root);
        Predicate where = builder.equal(root.get("id"), idValue);
        criteria.where(where);
        return getSession().createQuery(criteria).getSingleResult() != null;
    }

    public List<T> get(Long from, Long size) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getType());
        Root<T> root = criteria.from(getType());
        criteria.select(root);
        return getSession().createQuery(criteria).setFirstResult(from.intValue()).setMaxResults(size.intValue()).getResultList();
    }

    public List<T> get(Long from, Long size, String order) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getType());
        Root<T> root = criteria.from(getType());
        criteria.select(root);
        /*
        List<Order> orderList = new ArrayList<>();
        orderList.add(builder.desc(root.get(order)));
        criteria.orderBy(orderList);*/
        return getSession().createQuery(criteria).getResultList();
    }

    public T get(Long id) {
        return this.getSessionFactory().getCurrentSession().get(getType(), id);
    }

    public Class<T> getType() {
        Type tClass = null;
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            tClass = paramType.getActualTypeArguments()[0];
        }

        return (Class<T>) tClass;
    }

    public Session getSession() {
        return this.currentSession();
    }

}
