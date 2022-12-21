package com.taraan.dum.dto.analyze;

public class AnalyzeTripRequest {
    private String formula;
    private Long userId;
    private Long tripId;
    private Long dailyTripInfoId;

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    @Override
    public String toString() {
        return "AnalyzeTripRequest{" +
                "formula='" + formula + '\'' +
                ", userId=" + userId +
                ", tripId=" + tripId +
                ", dailyTripInfoId=" + dailyTripInfoId +
                '}';
    }

    public void setDailyTripInfoId(Long dailyTripInfoId) {
        this.dailyTripInfoId = dailyTripInfoId;
    }

    public Long getDailyTripInfoId() {
        return dailyTripInfoId;
    }
}
