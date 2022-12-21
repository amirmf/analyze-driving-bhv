package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.account.*;
import com.taraan.dum.dto.user.LoginResponse;
import com.taraan.dum.logic.AccountGroupLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Component
@Transactional
public class AccountGroupAssembler {

    @Autowired
    private AccountGroupLogic accountGroupLogic;

    public AccountGroupDto createAccountGroup(AccountGroupDto accountDto) throws IOException {
        return accountGroupLogic.createAccountGroup(accountDto);
    }

    public AccountGroupDto updateAccountGroup(AccountGroupDto accountDto) {
        return accountGroupLogic.updateAccountGroup(accountDto);
    }
    public AccountGroupDto getAccountGroup(Long id) {
        return accountGroupLogic.getAccountGroup(id);
    }

    public void removeAccountGroup(Long id) {
        accountGroupLogic.removeAccountGroup(id);
    }

    public AccountGroupPage getAccountGroups(String name,String code,Long from,Long size) {
        return accountGroupLogic.getAccountGroups(name,code,from, size);
    }

    public void addRoleToAccountGroup(ActionAccountRoleRequest request) {
        accountGroupLogic.addRoleToAccountGroup(request);
    }
}
