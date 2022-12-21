package com.taraan.dum.mappers;

import com.taraan.dum.dto.account.AccountDto;
import com.taraan.dum.model.hibernate.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {
    public static AccountDto getDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setUsername(account.getUsername());
        accountDto.setFirstName(account.getFirstName());
        accountDto.setLastName(account.getLastName());
        accountDto.setEnabled(account.isEnabled());
        return accountDto;
    }

    public static List<AccountDto> getDtos(List<Account> accountes) {
        List<AccountDto> result = new ArrayList<>();
        for (Account account : accountes) {
            result.add(getDto(account));
        }
        return result;
    }

}
