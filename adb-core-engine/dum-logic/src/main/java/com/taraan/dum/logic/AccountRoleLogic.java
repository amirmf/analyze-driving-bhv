package com.taraan.dum.logic;

import com.taraan.dum.da.AccountRoleDa;
import com.taraan.dum.dto.account.AccountRoleDto;
import com.taraan.dum.dto.account.AccountRolePage;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.mappers.AccountRoleMapper;
import com.taraan.dum.model.hibernate.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AccountRoleLogic {

    @Autowired
    private AccountRoleDa accountRoleDa;

    public AccountRoleDto createAccountRole(AccountRoleDto accountDto) throws IOException {
        if (accountDto.getName() == null || accountDto.getName().isEmpty())
            throw new NullPointerException("name");
        if (accountDto.getCode() == null || accountDto.getCode().isEmpty())
            throw new NullPointerException("code");
        AccountRole accountRole = new AccountRole();
        accountRole.setCode(accountDto.getCode());
        accountRole.setName(accountDto.getName());
        accountRoleDa.save(accountRole);
        return AccountRoleMapper.getDto(accountRole);
    }

    public AccountRoleDto updateAccountRole(AccountRoleDto accountDto) {
        if (accountDto.getId() == null)
            throw new NullPointerException("id");
        if (accountDto.getName() == null || accountDto.getName().isEmpty())
            throw new NullPointerException("name");
        if (accountDto.getCode() == null || accountDto.getCode().isEmpty())
            throw new NullPointerException("code");
        AccountRole accountRole = this.accountRoleDa.get(accountDto.getId());
        if (accountRole == null)
            throw new EntityDoesNotExistException("AccountRole");
        accountRole.setCode(accountDto.getCode());
        accountRole.setName(accountDto.getName());
        accountRoleDa.update(accountRole);
        return AccountRoleMapper.getDto(accountRole);
    }

    public AccountRoleDto getAccountRole(Long id) {
        AccountRole account = accountRoleDa.get(id);
        if (account == null)
            return null;
        return AccountRoleMapper.getDto(account);
    }

    public void removeAccountRole(Long id) {
        AccountRole account = accountRoleDa.get(id);
        if (account == null)
            throw new EntityDoesNotExistException("AccountRole");
        accountRoleDa.delete(account);
    }

    public AccountRolePage getAccountRoles(String name, String code, Long from, Long size) {
        List<AccountRole> accounts = accountRoleDa.get(name, code, from, size);
        List<AccountRoleDto> dtos = AccountRoleMapper.getDtos(accounts);
        return new AccountRolePage(dtos, accountRoleDa.getCount(name, code));
    }

}
