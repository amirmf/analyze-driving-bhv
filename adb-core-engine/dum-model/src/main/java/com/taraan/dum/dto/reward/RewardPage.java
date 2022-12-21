package com.taraan.dum.dto.reward;

import java.util.ArrayList;
import java.util.List;

public class RewardPage {
    private List<RewardItem> rewards = new ArrayList<>();
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<RewardItem> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardItem> rewards) {
        this.rewards = rewards;
    }
}
