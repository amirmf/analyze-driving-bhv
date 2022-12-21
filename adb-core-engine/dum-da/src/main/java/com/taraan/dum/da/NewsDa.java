package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.News;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsDa extends GenericDa<News> {

    public List<News> getActiveNews(Long from, Long size) {
        String queryString = "select news from News news where news.dateRange.to is null";
        return this.getSession().createQuery(queryString).setMaxResults(size.intValue()).setFirstResult(from.intValue()).list();
    }
    public Long getActiveNewsCount() {
        String queryString = "select count(news.id) from News news where news.dateRange.to is null";
        return (Long) this.getSession().createQuery(queryString).getSingleResult();
    }
}
