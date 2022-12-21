package com.taraan.dum.dto.message;

import java.util.ArrayList;
import java.util.List;

public class AdminMessagePage {
    private List<AdminMessageDto> messages = new ArrayList<>();
    private Long count;

    public AdminMessagePage() {
    }

    public AdminMessagePage(List<AdminMessageDto> messages, Long count) {
        this.messages = messages;
        this.count = count;
    }

    public List<AdminMessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<AdminMessageDto> messages) {
        this.messages = messages;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
