package com.taraan.dum.dto.reward;

import java.util.ArrayList;
import java.util.List;

public class UserRewardItem {
    private Long id;
    private List<RewardBadgeItem> requiredBadges = new ArrayList<>();
    private String name;
    private String type;
    private String description;
    private String rewardText;
    private String rewardImage;
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RewardBadgeItem> getRequiredBadges() {
        return requiredBadges;
    }

    public void setRequiredBadges(List<RewardBadgeItem> requiredBadges) {
        this.requiredBadges = requiredBadges;
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

    public String getRewardImage() {
        return rewardImage;
    }

    public void setRewardImage(String rewardImage) {
        this.rewardImage = rewardImage;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
