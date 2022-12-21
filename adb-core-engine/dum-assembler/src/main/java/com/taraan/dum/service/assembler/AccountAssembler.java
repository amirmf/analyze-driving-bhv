package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.account.*;
import com.taraan.dum.dto.user.LoginResponse;
import com.taraan.dum.logic.AccountLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Component
@Transactional
public class AccountAssembler {

    @Autowired
    private AccountLogic accountLogic;

    public AccountDto createAccount(AccountDto accountDto) throws IOException {
        return accountLogic.createAccount(accountDto);
    }

    public AccountDto updateAccount(AccountDto accountDto) {
        return accountLogic.updateAccount(accountDto);
    }
    public void addGroupToAccount(ActionAccountGroupRequest accountDto) {
        accountLogic.addGroupToAccount(accountDto);
    }

    public void addRoleToAccount(ActionAccountRoleRequest accountDto) {
        accountLogic.addRoleToAccount(accountDto);
    }
    public AccountDto getAccount(Long id) {
        return accountLogic.getAccount(id);
    }

    public void removeAccount(Long id) {
        accountLogic.removeAccount(id);
    }

    public AccountPage getAccounts(String username, Boolean isEnabled, Long from, Long size) {
        return accountLogic.getAccounts(username, isEnabled, from, size);
    }

    public LoginResponse login(AcountLoginRequest loginRequest) {
        return accountLogic.login(loginRequest);
    }

}
