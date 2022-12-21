package com.taraan.dum.mappers;

import com.taraan.dum.dto.account.AccountRoleDto;
import com.taraan.dum.model.hibernate.AccountRole;

import java.util.ArrayList;
import java.util.List;

public class AccountRoleMapper {
    public static AccountRoleDto getDto(AccountRole account) {
        AccountRoleDto accountDto = new AccountRoleDto();
        accountDto.setId(account.getId());
        accountDto.setCode(account.getCode());
        accountDto.setName(account.getName());
        return accountDto;
    }

    public static List<AccountRoleDto> getDtos(List<AccountRole> accountes) {
        List<AccountRoleDto> result = new ArrayList<>();
        for (AccountRole account : accountes) {
            result.add(getDto(account));
        }
        return result;
    }

}
