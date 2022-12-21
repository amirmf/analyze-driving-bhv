package com.taraan.dum.dto.account;

import java.util.List;

public class AccountDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enabled;
    private List<AccountRoleDto> accountRoles;
    private List<AccountGroupDto> accountGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountRoles(List<AccountRoleDto> accountRoles) {
        this.accountRoles = accountRoles;
    }

    public List<AccountRoleDto> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountGroups(List<AccountGroupDto> accountGroups) {
        this.accountGroups = accountGroups;
    }

    public List<AccountGroupDto> getAccountGroups() {
        return accountGroups;
    }
}
