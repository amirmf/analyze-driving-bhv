package com.taraan.dum.dto.statistic;

import java.util.List;

public class RewardPage {
    private List<RewardDto> rewards;

    public RewardPage(List<RewardDto> rewardDtos) {
        rewards = rewardDtos;
    }

    public RewardPage() {
    }

    public List<RewardDto> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardDto> rewards) {
        this.rewards = rewards;
    }
}
