package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.sosRequest.SosRequestDto;
import com.taraan.dum.dto.sosRequest.SosRequestPage;
import com.taraan.dum.logic.SosRequestLogic;
import com.taraan.dum.model.hibernate.SosRequestState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class SosRequestAssembler {
    @Autowired
    private SosRequestLogic sosRequestLogic;

    public SosRequestDto createSosRequest(SosRequestDto sosRequestDto, Long userId) {
        return sosRequestLogic.createSosRequest(sosRequestDto, userId);
    }

    public SosRequestDto getSosRequest(Long id, Long userId) {
        return sosRequestLogic.getSosRequest(id, userId);
    }


    public void removeSosRequest(Long id) {
        sosRequestLogic.removeSosRequest(id);
    }

    public SosRequestPage getSosRequests(Long page) {
        long from = page * 10;
        long size = from + 10;
        return sosRequestLogic.getSosRequests(from, size);
    }

    public SosRequestPage getSosRequests(String phoneNumber, String state, Long from, Long size) {
        return sosRequestLogic.getSosRequests(phoneNumber,state, from, size);
    }

    public void changeSosRequestState(Long id, SosRequestState sosRequestState) {
        sosRequestLogic.changeSosRequestState(id, sosRequestState);
    }
}
