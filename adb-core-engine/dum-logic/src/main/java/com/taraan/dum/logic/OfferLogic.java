package com.taraan.dum.logic;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.taraan.dum.common.DateUtils;
import com.taraan.dum.common.FileTypesUtils;
import com.taraan.dum.da.OfferDa;
import com.taraan.dum.da.UserDa;
import com.taraan.dum.dto.offer.AdminOfferDto;
import com.taraan.dum.dto.offer.OfferDto;
import com.taraan.dum.dto.offer.OfferPage;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.mappers.OfferMapper;
import com.taraan.dum.model.hibernate.DateRange;
import com.taraan.dum.model.hibernate.Offer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class OfferLogic {
    @Autowired
    private OfferDa offerDa;

    @Autowired
    private UserDa userDa;

    public OfferDto createOffer(OfferDto offerDto, Long user) {
        Offer offer = new Offer();
        offer.setDescription(offerDto.getDescription());
        offer.setTitle(offerDto.getTitle());
        offer.setDate(new Date());
        offer.setUser(userDa.get(user));
        offer = offerDa.save(offer);
        return OfferMapper.getDto(offer);
    }

    public OfferDto getOffer(Long id) {
        Offer offer = offerDa.get(id);
        if (offer == null)
            return null;
        return OfferMapper.getDto(offer);
    }

    public void removeOffer(Long id) {
        if (id == null)
            throw new NullPointerException("id");
        Offer offer = offerDa.get(id);
        if (offer == null)
            throw new EntityDoesNotExistException();
        offer.setRemoved(true);
        offerDa.update(offer);
    }

    public OfferPage getOffers(Long user, Long from, Long size) {
        List<Offer> offers = offerDa.get(user, from, size);
        Long count = offerDa.getCount(user);
        List<OfferDto> dtos = OfferMapper.getDtos(offers);
        OfferPage offerPage = new OfferPage();
        offerPage.setOffers(dtos);
        offerPage.setCount(count);
        return offerPage;
    }


    public AdminOfferDto getAdminOffer(Long id) {
        if (id == null)
            throw new NullPointerException("id");
        Offer offer = offerDa.get(id);
        if (offer == null)
            throw new EntityDoesNotExistException();
        AdminOfferDto adminOfferDto = new AdminOfferDto();
        adminOfferDto.setId(offer.getId());
        adminOfferDto.setDescription(offer.getDescription());
        adminOfferDto.setDate(DateUtils.getDate(offer.getDate()));
        adminOfferDto.setTitle(offer.getTitle());
        return adminOfferDto;
    }

    public OfferPage getOffers(String title, String phoneNumber, String strFromDate, String strToDate, Long from, Long size) {
        Date fromDate = null;
        Date toDate = null;
        if (strFromDate != null && !strFromDate.isEmpty())
            fromDate = DateUtils.getDate(strFromDate);
        if (strToDate != null && !strToDate.isEmpty())
            toDate = DateUtils.getDate(strToDate);

        List<Offer> offers = offerDa.get(title, phoneNumber, fromDate, toDate, from, size);
        Long count = offerDa.getCount(title, phoneNumber, fromDate, toDate);
        List<OfferDto> dtos = OfferMapper.getDtos(offers);
        OfferPage offerPage = new OfferPage();
        offerPage.setOffers(dtos);
        offerPage.setCount(count);
        return offerPage;
    }
}
