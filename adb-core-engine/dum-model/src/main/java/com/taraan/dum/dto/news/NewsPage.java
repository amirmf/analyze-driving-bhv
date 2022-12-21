package com.taraan.dum.dto.news;

import java.util.List;

public class NewsPage {
    private List<NewsDto> newses;
    private Long count;

    public List<NewsDto> getNewses() {
        return newses;
    }

    public void setNewses(List<NewsDto> newses) {
        this.newses = newses;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public NewsPage(List<NewsDto> newses, Long count) {
        this.newses = newses;
        this.count = count;
    }

    @Override
    public String toString() {
        return "NewsPage{" +
                "newses=" + newses +
                ", count=" + count +
                '}';
    }
}
