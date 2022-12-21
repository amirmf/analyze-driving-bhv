package com.taraan.dum.mappers;

import com.taraan.dum.dto.badge.BadgeDto;
import com.taraan.dum.model.hibernate.Badge;

import java.util.ArrayList;
import java.util.List;

public class BadgeMapper {
    public static BadgeDto getDto(Badge badge) {
        BadgeDto badgeDto = new BadgeDto();
        badgeDto.setId(badge.getId());
        badgeDto.setCode(badge.getCode());
        badgeDto.setDescription(badge.getDescription());
        badgeDto.setIcon(badge.getIcon());
        badgeDto.setName(badge.getName());
        return badgeDto;
    }

    public static List<BadgeDto> getDtos(List<Badge> badgees) {
        List<BadgeDto> result = new ArrayList<>();
        for (Badge badge : badgees) {
            result.add(getDto(badge));
        }
        return result;
    }

}
