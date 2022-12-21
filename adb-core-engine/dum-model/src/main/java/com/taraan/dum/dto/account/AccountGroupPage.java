package com.taraan.dum.dto.account;

import java.util.List;

public class AccountGroupPage {
    private List<AccountGroupDto> accountGroups;
    private Long count;

    public List<AccountGroupDto> getAccountGroups() {
        return accountGroups;
    }

    public void setAccountGroups(List<AccountGroupDto> accountGroups) {
        this.accountGroups = accountGroups;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public AccountGroupPage(List<AccountGroupDto> accounts, Long count) {
        this.accountGroups = accounts;
        this.count = count;
    }

    @Override
    public String toString() {
        return "AccountPage{" +
                "accountGroups=" + accountGroups +
                ", count=" + count +
                '}';
    }
}
