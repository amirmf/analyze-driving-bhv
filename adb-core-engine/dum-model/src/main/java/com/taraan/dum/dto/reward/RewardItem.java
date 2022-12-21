package com.taraan.dum.dto.reward;

public class RewardItem {
    private String name;
    private String type;
    private String rewardState;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRewardState() {
        return rewardState;
    }

    public void setRewardState(String rewardState) {
        this.rewardState = rewardState;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
