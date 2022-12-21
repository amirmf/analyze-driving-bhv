package com.taraan.dum.dto.offer;

import java.util.ArrayList;
import java.util.List;

public class OfferPage {
    private Long count;
    private List<OfferDto> offers = new ArrayList<>();

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<OfferDto> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferDto> offers) {
        this.offers = offers;
    }
}
