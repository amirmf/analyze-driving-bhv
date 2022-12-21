package com.taraan.dum.dto.voucher;

import java.util.List;

/**
 * Created on 6/27/2018 12:07 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
public class CreateMultipleVoucherDto {
    private Long reward;
    private List<String> codes;

    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}
