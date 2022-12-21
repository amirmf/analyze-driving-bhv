package com.taraan.dum.dto.account;

import java.util.List;

public class AccountGroupDto {
    private Long id;
    private String name;
    private String code;
    private List<AccountRoleDto> accountRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<AccountRoleDto> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountRoles(List<AccountRoleDto> accountRoles) {
        this.accountRoles = accountRoles;
    }
}
