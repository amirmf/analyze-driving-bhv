package com.taraan.dum.dto.group;


import java.util.ArrayList;
import java.util.List;

public class GroupInvitePage {
    private List<GroupInviteItem> items = new ArrayList<>();
    private Long count;

    public List<GroupInviteItem> getItems() {
        return items;
    }

    public void setItems(List<GroupInviteItem> items) {
        this.items = items;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

