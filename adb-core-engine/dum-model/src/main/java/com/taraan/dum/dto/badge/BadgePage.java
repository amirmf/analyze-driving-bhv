package com.taraan.dum.dto.badge;

import java.util.ArrayList;
import java.util.List;

public class BadgePage {
    private List<BadgeDto> badges = new ArrayList<>();
    private Long count;

    public BadgePage(List<BadgeDto> badges, Long count) {
        this.badges = badges;
        this.count = count;
    }

    public BadgePage() {
    }

    public List<BadgeDto> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgeDto> badges) {
        this.badges = badges;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
