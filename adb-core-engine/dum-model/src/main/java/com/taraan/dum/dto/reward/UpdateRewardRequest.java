package com.taraan.dum.dto.reward;

import java.util.List;

public class UpdateRewardRequest {
    private Long id;
    private String name;
    private String type;//CONTEST, PRIZE
    private String rewardState;//ACTIVE,DISABLE
    private String description;
    private String rewardText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRewardText() {
        return rewardText;
    }

    public void setRewardText(String rewardText) {
        this.rewardText = rewardText;
    }

}
