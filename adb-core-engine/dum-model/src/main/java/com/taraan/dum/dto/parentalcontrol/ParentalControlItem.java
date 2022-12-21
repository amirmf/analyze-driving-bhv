package com.taraan.dum.dto.parentalcontrol;

/**
 * Created on 7/6/2018 2:18 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
public class ParentalControlItem {
    private Long id;
    private String name;
    private Long score;

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

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getScore() {
        return score;
    }
}
