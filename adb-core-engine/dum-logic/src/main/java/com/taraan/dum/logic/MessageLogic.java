package com.taraan.dum.logic;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.da.BadgeDa;
import com.taraan.dum.da.MessageDa;
import com.taraan.dum.da.UserDa;
import com.taraan.dum.dto.badge.BadgeDto;
import com.taraan.dum.dto.badge.BadgePage;
import com.taraan.dum.dto.message.*;
import com.taraan.dum.dto.news.NewsPage;
import com.taraan.dum.mappers.BadgeMapper;
import com.taraan.dum.model.hibernate.Badge;
import com.taraan.dum.model.hibernate.DateRange;
import com.taraan.dum.model.hibernate.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MessageLogic {
    @Autowired
    private MessageDa messageDa;
    @Autowired
    private UserDa userDa;


    public AdminMessagePage getMessages(String phoneNumber, String strFromDate, String strToDate, Long from, Long size) {
        Date fromDate = null;
        Date toDate = null;
        if(strFromDate !=null  && !strFromDate.isEmpty())
            fromDate = DateUtils.getDate(strFromDate);
        if(strToDate !=null  && !strToDate.isEmpty())
            toDate = DateUtils.getDate(strToDate);
        List<Message> messages = messageDa.get(phoneNumber, fromDate, toDate, from, size);
        List<AdminMessageDto> dtos = new ArrayList<>();
        messages.forEach(message -> {
            final AdminMessageDto e = new AdminMessageDto();
            e.setDate(DateUtils.getDate(message.getDate()));
            e.setText(message.getText());
            e.setId(message.getId());
            e.setRead(message.isRead());
            e.setPhoneNumber(message.getUser().getPhoneNumber());
            dtos.add(e);
        });
        Long count = messageDa.getCount(phoneNumber, fromDate, toDate);
        return new AdminMessagePage(dtos, count);
    }

    public MessagePage getMessages(Long userId, Long from, Long size) {
        List<Message> messages = messageDa.getByUser(userId, from.intValue(), size.intValue());
        List<MessageDto> dtos = new ArrayList<>();
        for (Message message : messages) {
            final MessageDto e = new MessageDto();
            e.setDate(DateUtils.getDate(message.getDate()));
            e.setText(message.getText());
            e.setRead(message.isRead());
            dtos.add(e);
        }
        Double count = Math.ceil(messageDa.getCountByUser(userId) / 10d);
        Double unreadCount = Math.ceil(messageDa.getUnreadCountByUser(userId));
        return new MessagePage(dtos, count.longValue(), unreadCount.longValue());
    }

    public void readMessages(Long userId) {
        messageDa.updateUnreadMessagees(userId);
    }

    public MessageCountResponse getUnreadMessageCount(Long userId) {
        Double unreadCount = Math.ceil(messageDa.getUnreadCountByUser(userId));
        return new MessageCountResponse(unreadCount.longValue());
    }

    public void sendMessage(SendMessageRequest sendMessageRequest) {
        Message message = new Message();
        message.setDate(new Date());
        message.setText(sendMessageRequest.getText());
        message.setUser(userDa.getByPhoneNumber(sendMessageRequest.getPhoneNumber()));
        messageDa.save(message);
    }

}
