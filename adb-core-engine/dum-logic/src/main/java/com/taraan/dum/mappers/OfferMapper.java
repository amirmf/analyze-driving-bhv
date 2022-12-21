package com.taraan.dum.mappers;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.dto.offer.OfferDto;
import com.taraan.dum.model.hibernate.Offer;

import java.util.ArrayList;
import java.util.List;

public class OfferMapper {
    public static OfferDto getDto(Offer offer) {
        OfferDto offerDto = new OfferDto();
        offerDto.setId(offer.getId());
        offerDto.setDescription(offer.getDescription());
        offerDto.setTitle(offer.getTitle());
        offerDto.setDate(DateUtils.getDate(offer.getDate()));
        return offerDto;
    }

    public static List<OfferDto> getDtos(List<Offer> offers) {
        List<OfferDto> result = new ArrayList<>();
        for (Offer offer : offers) {
            result.add(getDto(offer));
        }
        return result;
    }

}
