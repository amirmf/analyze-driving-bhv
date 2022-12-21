package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.voucher.CreateMultipleVoucherDto;
import com.taraan.dum.dto.voucher.CreateVoucherDto;
import com.taraan.dum.dto.voucher.VoucherDto;
import com.taraan.dum.dto.voucher.VoucherPage;
import com.taraan.dum.logic.VoucherLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created on 6/27/2018 12:08 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
@Component
@Transactional
public class VoucherAssembler {
    @Autowired
    private VoucherLogic voucherLogic;

    public Long createVoucher(CreateVoucherDto voucherDto) {
        return voucherLogic.createVoucher(voucherDto);
    }

    public void createVouchers(CreateMultipleVoucherDto voucherDto) {
        voucherLogic.createVouchers(voucherDto);
    }

    public void terminateVoucher(Long reward, String code) {
        voucherLogic.terminateVoucher(reward, code);
    }

    public void terminateVouchersByReward(Long reward) {
        voucherLogic.terminateVouchersByReward(reward);
    }

    public VoucherPage getVouchers(Long reward, String state, String code, String fromDate, String toDate, Long from, Long size) {
        return voucherLogic.getVouchers(reward, state, code, fromDate, toDate, from, size);
    }

    public VoucherDto getVoucher(Long id) {
        return voucherLogic.getVoucher(id);
    }
}
