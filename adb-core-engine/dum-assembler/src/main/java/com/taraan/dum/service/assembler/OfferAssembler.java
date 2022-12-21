package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.offer.AdminOfferDto;
import com.taraan.dum.dto.offer.OfferDto;
import com.taraan.dum.dto.offer.OfferPage;
import com.taraan.dum.logic.OfferLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Component
@Transactional
public class OfferAssembler {

    @Autowired
    private OfferLogic offerLogic;

    public OfferDto createOffer(OfferDto offerDto, Long user) throws IOException {
        return offerLogic.createOffer(offerDto, user);
    }

    public OfferDto getOffer(Long id) {
        return offerLogic.getOffer(id);
    }

    public void removeOffer(Long id) {
        offerLogic.removeOffer(id);
    }

    public OfferPage getOffers(Long user, Long page) {
        long from = page * 10;
        long size = from + 10;
        return offerLogic.getOffers(user, from, size);
    }

    public AdminOfferDto getAdminOffer(Long id) {
        return offerLogic.getAdminOffer(id);
    }

    public OfferPage getOffers(String title, String phoneNumber, String fromDate, String toDate, Long from, Long size) {
        return offerLogic.getOffers(title, phoneNumber, fromDate, toDate, from, size);
    }
}
