package com.taraan.dum.mappers;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.dto.sosRequest.SosRequestDto;
import com.taraan.dum.model.hibernate.SosRequest;

import java.util.ArrayList;
import java.util.List;

public class SosRequestMapper {
    public static SosRequestDto getDto(SosRequest sosRequest) {
        SosRequestDto sosRequestDto = new SosRequestDto();
        sosRequestDto.setId(sosRequest.getId());
        sosRequestDto.setLongitude(sosRequest.getLongitude());
        sosRequestDto.setLatitude(sosRequest.getLatitude());
        sosRequestDto.setText(sosRequest.getText());
        sosRequestDto.setDate(DateUtils.getDate(sosRequest.getDateRange().getFrom()));
        return sosRequestDto;
    }

    public static List<SosRequestDto> getDtos(List<SosRequest> sosRequestes) {
        List<SosRequestDto> result = new ArrayList<>();
        for (SosRequest sosRequest : sosRequestes) {
            result.add(getDto(sosRequest));
        }
        return result;
    }

}
