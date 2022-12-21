package com.taraan.dum.dto.statistic;

public class RewardDto {

    private String title;
    private String icon;
    private String description;
    private Long count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public RewardDto(String title, String icon, String description, Long count) {
        this.title = title;
        this.icon = icon;
        this.description = description;
        this.count = count;
    }

    public RewardDto() {
    }

    @Override
    public String toString() {
        return "RewardDto{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", count=" + count +
                '}';
    }
}


