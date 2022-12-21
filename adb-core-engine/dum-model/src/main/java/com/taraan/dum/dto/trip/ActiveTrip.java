package com.taraan.dum.dto.trip;

public class ActiveTrip {
    private boolean result;

    public ActiveTrip(Boolean active) {
        result = active;
    }

    public ActiveTrip() {
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
