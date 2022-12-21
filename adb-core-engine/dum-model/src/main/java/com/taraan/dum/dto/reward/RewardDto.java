package com.taraan.dum.dto.reward;

import java.util.ArrayList;
import java.util.List;

public class RewardDto {
    private String name;
    private String description;
    private String type;
    private String rewardState;
    private String rewardText;
    private String rewardImage;
    private List<RequiredBadgeItemDto> badges = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setRewardState(String rewardState) {
        this.rewardState = rewardState;
    }

    public String getRewardState() {
        return rewardState;
    }

    public void setRewardText(String rewardText) {
        this.rewardText = rewardText;
    }

    public String getRewardText() {
        return rewardText;
    }

    public void setRewardImage(String rewardImage) {
        this.rewardImage = rewardImage;
    }

    public String getRewardImage() {
        return rewardImage;
    }

    public List<RequiredBadgeItemDto> getBadges() {
        return badges;
    }

    public void setBadges(List<RequiredBadgeItemDto> badges) {
        this.badges = badges;
    }
}
