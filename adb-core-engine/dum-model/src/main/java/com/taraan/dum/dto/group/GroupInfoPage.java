package com.taraan.dum.dto.group;

import java.util.ArrayList;
import java.util.List;

public class GroupInfoPage {
    private List<UserGroupItem> userGroupItems = new ArrayList<>();
    private String name;
    private String description;
    private boolean owner;

    public List<UserGroupItem> getUserGroupItems() {
        return userGroupItems;
    }

    public void setUserGroupItems(List<UserGroupItem> userGroupItems) {
        this.userGroupItems = userGroupItems;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean isOwner() {
        return owner;
    }
}
