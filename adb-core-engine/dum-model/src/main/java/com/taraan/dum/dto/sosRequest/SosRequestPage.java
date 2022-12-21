package com.taraan.dum.dto.sosRequest;

import com.taraan.dum.dto.news.NewsDto;
import com.taraan.dum.model.hibernate.SosRequest;

import java.util.List;

public class SosRequestPage {
    private List<SosRequestDto> sosRequests;
    private Long count;

    public List<SosRequestDto> getSosRequests() {
        return sosRequests;
    }

    public void setSosRequests(List<SosRequestDto> sosRequests) {
        this.sosRequests = sosRequests;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public SosRequestPage(List<SosRequestDto> sosRequests, Long count) {
        this.sosRequests = sosRequests;
        this.count = count;
    }

    @Override
    public String toString() {
        return "SosRequestPage{" +
                "sosRequests=" + sosRequests +
                ", count=" + count +
                '}';
    }
}
