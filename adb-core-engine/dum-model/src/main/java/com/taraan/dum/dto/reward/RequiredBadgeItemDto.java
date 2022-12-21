package com.taraan.dum.dto.reward;

public class RequiredBadgeItemDto {
    private String name;
    private Long id;
    private Long count;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
