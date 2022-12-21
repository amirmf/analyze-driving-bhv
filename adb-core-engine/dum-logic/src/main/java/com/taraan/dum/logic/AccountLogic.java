package com.taraan.dum.logic;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.da.AccountDa;
import com.taraan.dum.da.AccountGroupDa;
import com.taraan.dum.da.AccountRoleDa;
import com.taraan.dum.dto.account.*;
import com.taraan.dum.dto.user.LoginResponse;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.exceptions.user.InActiveUserPasswordException;
import com.taraan.dum.exceptions.user.InvalidUserPasswordException;
import com.taraan.dum.mappers.AccountGroupMapper;
import com.taraan.dum.mappers.AccountMapper;
import com.taraan.dum.mappers.AccountRoleMapper;
import com.taraan.dum.model.hibernate.AccountGroup;
import com.taraan.dum.model.hibernate.AccountRole;
import com.taraan.dum.model.hibernate.DateRange;
import com.taraan.dum.model.hibernate.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AccountLogic {

    @Autowired
    private AccountDa accountDa;
    @Autowired
    private AccountRoleDa accountRoleDa;
    @Autowired
    private AccountGroupDa accountGroupDa;

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = new Account();
        if (accountDto.getUsername() == null || accountDto.getUsername().isEmpty())
            throw new NullPointerException("username");
        if (accountDto.getPassword() == null || accountDto.getPassword().isEmpty())
            throw new NullPointerException("password");
        account.setUsername(accountDto.getUsername());
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setPassword(accountDto.getPassword());
        account.setEnabled(accountDto.isEnabled());

        account.setDateRange(new DateRange(new Date()));
        account.encryptCardNumber();
        return AccountMapper.getDto(accountDa.save(account));
    }

    public AccountDto updateAccount(AccountDto accountDto) {
        if (accountDto.getId() == null)
            throw new NullPointerException("username");
        Account account = accountDa.get(accountDto.getId());
        if (account == null)
            throw new EntityDoesNotExistException("Account");
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setEnabled(accountDto.isEnabled());
        return AccountMapper.getDto(accountDa.update(account));
    }

    public void addRoleToAccount(ActionAccountRoleRequest request) {
        if (request.getId() == null)
            throw new NullPointerException("id");
        if (request.getAccountRoles() == null || request.getAccountRoles().isEmpty())
            throw new NullPointerException("AccountRoles");
        Account account = accountDa.get(request.getId());
        if (account == null)
            throw new EntityDoesNotExistException("Account");
        List<AccountRole> accountRoles = new ArrayList<>();
        for (Long accountRoleId : request.getAccountRoles()) {
            AccountRole accountRole = accountRoleDa.get(accountRoleId);
            if (accountRole == null)
                throw new EntityDoesNotExistException("AccountRole");
            accountRoles.add(accountRole);
        }
        account.setAccountRoles(accountRoles);
        accountDa.update(account);
    }

    public void addGroupToAccount(ActionAccountGroupRequest request) {
        if (request.getId() == null)
            throw new NullPointerException("id");
        if (request.getAccountGroups() == null || request.getAccountGroups().isEmpty())
            throw new NullPointerException("AccountGroups");
        Account account = accountDa.get(request.getId());
        if (account == null)
            throw new EntityDoesNotExistException("Account");
        List<AccountGroup> accountGroups = new ArrayList<>();
        for (Long accountRoleId : request.getAccountGroups()) {
            AccountGroup accountRole = accountGroupDa.get(accountRoleId);
            if (accountRole == null)
                throw new EntityDoesNotExistException("AccountRole");
            accountGroups.add(accountRole);
        }
        account.setAccountGroups(accountGroups);
        accountDa.update(account);
    }

    public AccountDto getAccount(Long id) {
        Account account = accountDa.get(id);
        if (account == null)
            return null;
        final AccountDto dto = AccountMapper.getDto(account);
        dto.setAccountRoles(AccountRoleMapper.getDtos(account.getAccountRoles()));
        dto.setAccountGroups(AccountGroupMapper.getDtos(account.getAccountGroups()));
        return dto;
    }

    public void removeAccount(Long id) {
        Account account = accountDa.get(id);
        if (account == null)
            throw new EntityDoesNotExistException();
        account.getDateRange().setTo(new Date());
        accountDa.update(account);
    }

    public AccountPage getAccounts(Long from, Long size) {
        List<Account> accounts = accountDa.get(from, size);
        List<AccountDto> dtos = AccountMapper.getDtos(accounts);
        Double count = Math.ceil(accountDa.getCount() / 10d);
        return new AccountPage(dtos, count.longValue());
    }

    public AccountPage getAccounts(String username, Boolean enabled, Long from, Long size) {
        List<Account> accounts = accountDa.getActiveAccount(username, enabled, from, size);
        List<AccountDto> dtos = AccountMapper.getDtos(accounts);
        return new AccountPage(dtos, accountDa.getCountActiveAccount(username, enabled));
    }

    public LoginResponse login(AcountLoginRequest loginRequest) {
        final Account account = accountDa.getByUsername(loginRequest.getUsername());
        if (account == null)
            throw new InvalidUserPasswordException();
        if (!account.getPassword().equals(loginRequest.getPassword()))
            throw new InvalidUserPasswordException();
        if (account.isEnabled())
            throw new InActiveUserPasswordException();
        return new LoginResponse(TokenUtils.generateToken(String.valueOf(account.getId()), loginRequest.getUsername(), 30));
    }

}
