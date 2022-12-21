package com.taraan.dum.dto.parentalcontrol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/6/2018 2:17 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
public class ParentalControlPage {
    private List<ParentalControlItem> items = new ArrayList<>();
    private Long count;
    public List<ParentalControlItem> getItems() {
        return items;
    }

    public void setItems(List<ParentalControlItem> items) {
        this.items = items;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
