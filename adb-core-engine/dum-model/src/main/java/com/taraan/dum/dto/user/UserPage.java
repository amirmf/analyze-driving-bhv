package com.taraan.dum.dto.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPage {
    private List<UserItem> items= new ArrayList<>();
    private Long count;

    public List<UserItem> getItems() {
        return items;
    }

    public void setItems(List<UserItem> items) {
        this.items = items;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
