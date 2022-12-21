package com.taraan.dum.dto.analyze;

import java.io.Serializable;

public class JmsDailyAnalyzeRequest implements Serializable {
    private Long userId;
    private Long dailyTripInfo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDailyTripInfo() {
        return dailyTripInfo;
    }

    public void setDailyTripInfo(Long dailyTripInfo) {
        this.dailyTripInfo = dailyTripInfo;
    }
}
