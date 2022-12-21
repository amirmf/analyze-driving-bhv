package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.group.GroupDrivingDto;
import com.taraan.dum.dto.group.GroupInvitePage;
import com.taraan.dum.dto.group.GroupInviteRequest;
import com.taraan.dum.dto.message.AdminMessagePage;
import com.taraan.dum.dto.message.MessageCountResponse;
import com.taraan.dum.dto.message.MessagePage;
import com.taraan.dum.dto.message.SendMessageRequest;
import com.taraan.dum.logic.GroupLogic;
import com.taraan.dum.logic.MessageLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class MessageAssembler {
    @Autowired
    private MessageLogic messageLogic;


    public MessagePage getMessages(Long userId, Long page) {
        long from = page * 10;
        long size = from + 10;
        return messageLogic.getMessages(userId,from, size);
    }

    public void readMessages(Long userId) {
        messageLogic.readMessages(userId);
    }

    public MessageCountResponse getUnreadMessageCount(Long userId) {
        return messageLogic.getUnreadMessageCount(userId);
    }

    public void sendMessage(SendMessageRequest sendMessageRequest) {
        messageLogic.sendMessage(sendMessageRequest);
    }

    public AdminMessagePage getMessages(String phoneNumber, String fromDate, String toDate, Long from, Long size) {
        return messageLogic.getMessages(phoneNumber,fromDate,toDate,from,size);
    }
}