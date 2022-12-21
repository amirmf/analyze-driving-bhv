package com.taraan.dum.dto.analyze;

import com.taraan.dum.model.hibernate.FormulaEvent;

import java.io.Serializable;

public class JmsAnalyzeRequest implements Serializable {
    private Long tripId;
    private Long userId;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
