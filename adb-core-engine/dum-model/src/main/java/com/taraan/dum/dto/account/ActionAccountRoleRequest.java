package com.taraan.dum.dto.account;

import java.util.List;

public class ActionAccountRoleRequest {
    private Long id;
    private List<Long> accountRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountRoles(List<Long> accountRoles) {
        this.accountRoles = accountRoles;
    }
}
