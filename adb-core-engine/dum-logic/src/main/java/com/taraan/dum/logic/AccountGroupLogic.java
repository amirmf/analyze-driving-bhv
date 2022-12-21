package com.taraan.dum.logic;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.da.AccountGroupDa;
import com.taraan.dum.da.AccountRoleDa;
import com.taraan.dum.dto.account.*;
import com.taraan.dum.dto.user.LoginResponse;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.exceptions.user.InvalidUserPasswordException;
import com.taraan.dum.mappers.AccountGroupMapper;
import com.taraan.dum.mappers.AccountGroupMapper;
import com.taraan.dum.mappers.AccountRoleMapper;
import com.taraan.dum.model.hibernate.Account;
import com.taraan.dum.model.hibernate.AccountGroup;
import com.taraan.dum.model.hibernate.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountGroupLogic {

    @Autowired
    private AccountGroupDa accountGroupDa;

    @Autowired
    private AccountRoleDa accountRoleDa;

    public AccountGroupDto createAccountGroup(AccountGroupDto accountDto) throws IOException {
        if (accountDto.getName() == null || accountDto.getName().isEmpty())
            throw new NullPointerException("name");
        if (accountDto.getCode() == null || accountDto.getCode().isEmpty())
            throw new NullPointerException("code");
        AccountGroup accountGroup = new AccountGroup();
        accountGroup.setCode(accountDto.getCode());
        accountGroup.setName(accountDto.getName());
        accountGroupDa.save(accountGroup);
        return AccountGroupMapper.getDto(accountGroup);
    }

    public AccountGroupDto updateAccountGroup(AccountGroupDto accountDto) {
        if (accountDto.getId() == null)
            throw new NullPointerException("id");
        if (accountDto.getName() == null || accountDto.getName().isEmpty())
            throw new NullPointerException("name");
        if (accountDto.getCode() == null || accountDto.getCode().isEmpty())
            throw new NullPointerException("code");
        AccountGroup accountGroup = accountGroupDa.get(accountDto.getId());
        if (accountGroup == null)
            throw new EntityDoesNotExistException("AccountGroup");
        accountGroup.setCode(accountDto.getCode());
        accountGroup.setName(accountDto.getName());
        accountGroupDa.save(accountGroup);
        return AccountGroupMapper.getDto(accountGroup);
    }

    public AccountGroupDto getAccountGroup(Long id) {
        AccountGroup account = accountGroupDa.get(id);
        if (account == null)
            return null;

        final AccountGroupDto dto = AccountGroupMapper.getDto(account);
        dto.setAccountRoles(AccountRoleMapper.getDtos(account.getAccountRoles()));
        return dto;
    }

    public void removeAccountGroup(Long id) {
        AccountGroup account = accountGroupDa.get(id);
        if (account == null)
            throw new EntityDoesNotExistException();
        accountGroupDa.delete(account);
    }

    public AccountGroupPage getAccountGroups(String name, String code, Long from, Long size) {
        List<AccountGroup> accounts = accountGroupDa.get(name, code, from, size);
        List<AccountGroupDto> dtos = AccountGroupMapper.getDtos(accounts);
        Double count = Math.ceil(accountGroupDa.getCount(name, code));
        return new AccountGroupPage(dtos, count.longValue());
    }

    public void addRoleToAccountGroup(ActionAccountRoleRequest request) {
        if (request.getId() == null)
            throw new NullPointerException("id");
        if (request.getAccountRoles() == null || request.getAccountRoles().isEmpty())
            throw new NullPointerException("AccountRoles");
        AccountGroup accountGroup = accountGroupDa.get(request.getId());
        if (accountGroup == null)
            throw new EntityDoesNotExistException("AccountGroup");
        List<AccountRole> accountRoles = new ArrayList<>();
        for (Long accountRoleId : request.getAccountRoles()) {
            AccountRole accountRole = accountRoleDa.get(accountRoleId);
            if (accountRole == null)
                throw new EntityDoesNotExistException("AccountRole");
            accountRoles.add(accountRole);
        }
        accountGroup.setAccountRoles(accountRoles);
        accountGroupDa.update(accountGroup);
    }

}
