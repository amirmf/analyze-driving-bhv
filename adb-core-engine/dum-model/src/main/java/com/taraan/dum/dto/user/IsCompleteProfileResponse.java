package com.taraan.dum.dto.user;

public class IsCompleteProfileResponse {
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public IsCompleteProfileResponse(boolean result) {
        this.result = result;
    }
}
