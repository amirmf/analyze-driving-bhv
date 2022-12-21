package com.taraan.dum.dto.voucher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 6/29/2018 1:49 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
public class VoucherPage {
    private List<VoucherItemDto> vouchers = new ArrayList<>();
    private Long count;

    public List<VoucherItemDto> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<VoucherItemDto> vouchers) {
        this.vouchers = vouchers;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
