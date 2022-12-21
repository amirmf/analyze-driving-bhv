package com.taraan.dum.mappers;

import com.taraan.dum.dto.account.AccountDto;
import com.taraan.dum.dto.account.AccountGroupDto;
import com.taraan.dum.model.hibernate.Account;
import com.taraan.dum.model.hibernate.AccountGroup;

import java.util.ArrayList;
import java.util.List;

public class AccountGroupMapper {
    public static AccountGroupDto getDto(AccountGroup account) {
        AccountGroupDto accountDto = new AccountGroupDto();
        accountDto.setId(account.getId());
        accountDto.setCode(account.getCode());
        accountDto.setName(account.getName());
        return accountDto;
    }

    public static List<AccountGroupDto> getDtos(List<AccountGroup> accountes) {
        List<AccountGroupDto> result = new ArrayList<>();
        for (AccountGroup account : accountes) {
            result.add(getDto(account));
        }
        return result;
    }

}
