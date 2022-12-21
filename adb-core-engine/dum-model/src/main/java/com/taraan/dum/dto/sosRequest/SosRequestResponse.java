package com.taraan.dum.dto.sosRequest;

public class SosRequestResponse {
    private Long id;

    public SosRequestResponse(Long id) {
        this.id = id;
    }

    public SosRequestResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SosRequestResponse{" +
                "id=" + id +
                '}';
    }
}
