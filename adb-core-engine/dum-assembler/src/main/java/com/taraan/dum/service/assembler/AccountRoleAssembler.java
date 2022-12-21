package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.account.AccountRoleDto;
import com.taraan.dum.dto.account.AccountRolePage;
import com.taraan.dum.logic.AccountRoleLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Component
@Transactional
public class AccountRoleAssembler {

    @Autowired
    private AccountRoleLogic accountRoleLogic;

    public AccountRoleDto createAccountRole(AccountRoleDto accountDto) throws IOException {
        return accountRoleLogic.createAccountRole(accountDto);
    }

    public AccountRoleDto getAccountRole(Long id) {
        return accountRoleLogic.getAccountRole(id);
    }

    public void removeAccountRole(Long id) {
        accountRoleLogic.removeAccountRole(id);
    }

    public AccountRolePage getAccountRoles(String name, String code, Long from, Long size) {
        return accountRoleLogic.getAccountRoles(name, code, from, size);
    }

    public AccountRoleDto updateAccountRole(AccountRoleDto accountDto) {
        return accountRoleLogic.updateAccountRole(accountDto);
    }
}
