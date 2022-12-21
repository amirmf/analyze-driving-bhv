package com.taraan.dum.dto.voucher;

/**
 * Created on 6/29/2018 7:36 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
public class VoucherItemDto {
    private Long id;
    private String date;
    private String state;
    private String rewardName;
    private Long rewardId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
    }

    public Long getRewardId() {
        return rewardId;
    }
}
