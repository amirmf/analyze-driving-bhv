package com.taraan.dum.logic;

import com.taraan.dum.da.SosRequestDa;
import com.taraan.dum.da.UserDa;
import com.taraan.dum.dto.sosRequest.SosRequestDto;
import com.taraan.dum.dto.sosRequest.SosRequestPage;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.mappers.SosRequestMapper;
import com.taraan.dum.model.hibernate.DateRange;
import com.taraan.dum.model.hibernate.SosRequest;
import com.taraan.dum.model.hibernate.SosRequestState;
import com.taraan.dum.model.hibernate.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class SosRequestLogic {

    @Autowired
    private SosRequestDa sosRequestDa;

    @Autowired
    private UserDa userDa;

    public SosRequestDto createSosRequest(SosRequestDto sosRequestDto, Long user) {
        SosRequest SosRequest = new SosRequest();
        SosRequest.setDateRange(new DateRange(new Date()));
        SosRequest.setLatitude(sosRequestDto.getLatitude());
        SosRequest.setSosRequestState(SosRequestState.CREATE);
        SosRequest.setLongitude(sosRequestDto.getLongitude());
        SosRequest.setText(sosRequestDto.getText());
        SosRequest.setUser(userDa.get(user));
        SosRequest = sosRequestDa.save(SosRequest);
        return SosRequestMapper.getDto(SosRequest);
    }

    public SosRequestDto getSosRequest(Long id, Long userId) {
        SosRequest sosRequest = sosRequestDa.get(id);
        if (sosRequest == null)
            return null;
        if (sosRequest.getDateRange().getTo() != null)
            return null;
        if (sosRequest.getUser().getId().equals(userId))
            return SosRequestMapper.getDto(sosRequest);
        return null;
    }

    public void removeSosRequest(Long id) {
        SosRequest SosRequest = sosRequestDa.get(id);
        if (SosRequest == null)
            throw new EntityDoesNotExistException();
        SosRequest.setSosRequestState(SosRequestState.DELETE);
        SosRequest.getDateRange().setTo(new Date());
        sosRequestDa.update(SosRequest);
    }

    public void changeSosRequestState(Long id, SosRequestState sosRequestState) {
        SosRequest SosRequest = sosRequestDa.get(id);
        if (SosRequest == null)
            throw new EntityDoesNotExistException();
        SosRequest.setSosRequestState(sosRequestState);
        sosRequestDa.update(SosRequest);
    }

    public SosRequestPage getSosRequests(Long from, Long size) {
        List<SosRequest> SosRequests = sosRequestDa.get(from, size);
        List<SosRequestDto> dtos = SosRequestMapper.getDtos(SosRequests);
        Double count = Math.ceil(sosRequestDa.getCount() / 10d);
        return new SosRequestPage(dtos, count.longValue());
    }

    public SosRequestPage getSosRequests(String phoneNumber, String state, Long from, Long size) {
        Long userId = null;
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            final User user = userDa.getByPhoneNumber(phoneNumber);
            if (user == null)
                return new SosRequestPage(Collections.emptyList(), 0L);
            else
                userId = user.getId();
        }
        SosRequestState sosRequestState = null;
        if (state != null && !state.isEmpty()) {
            sosRequestState = SosRequestState.valueOf(state);
        }
        List<SosRequest> SosRequests = sosRequestDa.get(userId, sosRequestState, from, size);
        List<SosRequestDto> dtos = SosRequestMapper.getDtos(SosRequests);
        return new SosRequestPage(dtos, sosRequestDa.getCount());
    }
}
