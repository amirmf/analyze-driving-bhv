package com.taraan.dum.dto.account;

import java.util.List;

public class ActionAccountGroupRequest {
    private Long id;
    private List<Long> accountGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getAccountGroups() {
        return accountGroups;
    }

    public void setAccountGroups(List<Long> accountGroups) {
        this.accountGroups = accountGroups;
    }
}
