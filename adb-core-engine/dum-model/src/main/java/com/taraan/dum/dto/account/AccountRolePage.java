package com.taraan.dum.dto.account;

import java.util.List;

public class AccountRolePage {
    private List<AccountRoleDto> accountRoles;
    private Long count;

    public List<AccountRoleDto> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountRoles(List<AccountRoleDto> accountRoles) {
        this.accountRoles = accountRoles;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public AccountRolePage(List<AccountRoleDto> accounts, Long count) {
        this.accountRoles = accounts;
        this.count = count;
    }

    @Override
    public String toString() {
        return "AccountPage{" +
                "accountRoles=" + accountRoles +
                ", count=" + count +
                '}';
    }
}
