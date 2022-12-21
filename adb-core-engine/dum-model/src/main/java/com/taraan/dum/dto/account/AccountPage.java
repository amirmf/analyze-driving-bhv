package com.taraan.dum.dto.account;

import java.util.List;

public class AccountPage {
    private List<AccountDto> accounts;
    private Long count;

    public List<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDto> accounts) {
        this.accounts = accounts;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public AccountPage(List<AccountDto> accounts, Long count) {
        this.accounts = accounts;
        this.count = count;
    }

    @Override
    public String toString() {
        return "AccountPage{" +
                "accounts=" + accounts +
                ", count=" + count +
                '}';
    }
}
