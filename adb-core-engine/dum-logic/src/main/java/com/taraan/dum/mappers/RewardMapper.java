package com.taraan.dum.mappers;

import com.taraan.dum.dto.statistic.RewardDto;
import com.taraan.dum.model.hibernate.Badge;
import com.taraan.dum.model.hibernate.UserBadge;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RewardMapper {

    public static RewardDto getDto(Badge reward, Long count, String iconPath) {
        RewardDto rewardDto = new RewardDto();
        rewardDto.setCount(count);
        rewardDto.setIcon(iconPath + reward.getIcon());
        rewardDto.setDescription(reward.getDescription());
        rewardDto.setTitle(reward.getName());
        return rewardDto;
    }

    public static List<RewardDto> getDtos(List<Badge> badges, String iconPath) {
        List<RewardDto> result = new ArrayList<>();
        for (Badge reward : badges) {
            result.add(getDto(reward, 0L, iconPath));
        }
        return result;
    }

    public static RewardDto getDto(UserBadge badge, Long count, String iconPath) {
        return getDto(badge.getBadge(), count, iconPath);
    }

}
