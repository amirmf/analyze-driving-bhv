package com.taraan.dum.dto.reward;

import java.util.ArrayList;
import java.util.List;

public class UserRewardPage {
    List<UserRewardItem> rewards = new ArrayList<>();
    private Long count;

    public List<UserRewardItem> getRewards() {
        return rewards;
    }

    public void setRewards(List<UserRewardItem> rewards) {
        this.rewards = rewards;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
