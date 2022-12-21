package com.taraan.dum.dto.message;

public class MessageCountResponse {
    private Long count;


    public MessageCountResponse(Long count) {
        this.count = count;
    }

    public MessageCountResponse() {
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
