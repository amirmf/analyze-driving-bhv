package com.taraan.dum.dto.voucher;

/**
 * Created on 6/27/2018 12:07 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
public class CreateVoucherDto {
    private Long reward;
    private String code;


    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
