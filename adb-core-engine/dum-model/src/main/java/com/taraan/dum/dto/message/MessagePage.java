package com.taraan.dum.dto.message;

import java.util.ArrayList;
import java.util.List;

public class MessagePage {
    private List<MessageDto> messages = new ArrayList<>();
    private Long count;
    private Long unreadCount;

    public MessagePage() {
    }

    public MessagePage(List<MessageDto> messages, Long count,Long unreadCount) {
        this.messages = messages;
        this.count = count;
        this.unreadCount = unreadCount;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Long unreadCount) {
        this.unreadCount = unreadCount;
    }
}
