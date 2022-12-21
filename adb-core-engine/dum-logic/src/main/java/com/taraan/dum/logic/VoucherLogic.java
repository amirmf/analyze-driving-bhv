package com.taraan.dum.logic;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.da.RewardDa;
import com.taraan.dum.da.VoucherDa;
import com.taraan.dum.dto.voucher.*;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.exceptions.InvalidStateException;
import com.taraan.dum.model.hibernate.Reward;
import com.taraan.dum.model.hibernate.Voucher;
import com.taraan.dum.model.hibernate.VoucherState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 6/27/2018 12:08 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
@Component
public class VoucherLogic {
    @Autowired
    private VoucherDa voucherDa;
    @Autowired
    private RewardDa rewardDa;

    public Long createVoucher(CreateVoucherDto voucherDto) {
        Reward reward = rewardDa.get(voucherDto.getReward());
        Voucher voucher = getTransientVoucher(voucherDto.getCode(), new Date(), reward);
        voucher = voucherDa.save(voucher);
        return voucher.getId();
    }

    private Voucher getTransientVoucher(String code, Date date, Reward reward) {
        Voucher voucher = new Voucher();
        voucher.setRegisterDate(date);
        voucher.setReward(reward);
        voucher.setVoucherState(VoucherState.PRODUCE);
        voucher.setCode(code);
        return voucher;
    }

    public void createVouchers(CreateMultipleVoucherDto voucherDto) {
        Reward reward = rewardDa.get(voucherDto.getReward());
        Date date = new Date();
        List<Voucher> vouchers = new ArrayList<>();
        for (String code : voucherDto.getCodes()) {
            vouchers.add(getTransientVoucher(code, date, reward));
        }
        voucherDa.save(vouchers);
    }

    public void terminateVoucher(Long reward, String code) {
        if (reward == null)
            throw new NullPointerException("reward");
        if (code == null || code.isEmpty())
            throw new NullPointerException("code");

        Voucher voucher = voucherDa.get(code, reward);
        if (voucher == null)
            throw new EntityDoesNotExistException("voucher");
        if (voucher.getVoucherState().equals(VoucherState.CONSUME))
            throw new InvalidStateException();
        voucher.setVoucherState(VoucherState.TERMINATE);
        voucherDa.update(voucher);
    }

    public void terminateVouchersByReward(Long reward) {
        List<Voucher> vouchers = voucherDa.get(reward, VoucherState.PRODUCE);
        if (vouchers == null || vouchers.isEmpty())
            throw new EntityDoesNotExistException("voucher");
        for (Voucher voucher : vouchers) {
            voucher.setVoucherState(VoucherState.TERMINATE);
            voucherDa.update(voucher);
        }
    }

    public VoucherPage getVouchers(Long reward, String state, String code, String strFromDate, String strToDate, Long from, Long size) {
        Date fromDate = null;
        Date toDate = null;
        if (strFromDate != null && !strFromDate.isEmpty())
            fromDate = DateUtils.getDate(strFromDate);
        if (strToDate != null && !strToDate.isEmpty())
            toDate = DateUtils.getDate(strToDate);
        List<Voucher> vouchers = voucherDa.get(reward, state, code, fromDate, toDate, from, size);
        VoucherPage voucherPage = new VoucherPage();
        for (Voucher voucher : vouchers) {
            VoucherItemDto voucherDto = new VoucherItemDto();
            voucherDto.setId(voucher.getId());
            voucherDto.setState(voucher.getVoucherState().name());
            if (voucher.getDate() != null)
                voucherDto.setDate(DateUtils.getDate(voucher.getDate()));
            voucherDto.setRewardName(voucher.getReward().getName());
            voucherDto.setRewardId(voucher.getReward().getId());
            voucherPage.getVouchers().add(voucherDto);
        }
        voucherPage.setCount(voucherDa.getCount(reward, state, code, fromDate, toDate));
        return voucherPage;
    }

    public VoucherDto getVoucher(Long id) {
        if (id == null)
            throw new NullPointerException("id");
        Voucher voucher = voucherDa.get(id);
        if (voucher == null)
            throw new EntityDoesNotExistException("voucher");

        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setId(voucher.getId());
        voucherDto.setState(voucher.getVoucherState().name());
        if (voucher.getDate() != null)
            voucherDto.setDate(DateUtils.getDate(voucher.getDate()));
        if (voucher.getRegisterDate() != null)
            voucherDto.setRegisterDate(DateUtils.getDate(voucher.getRegisterDate()));
        if (voucher.getUser() != null) {
            voucherDto.setUserId(voucher.getUser().getId());
            voucherDto.setUserPhoneNumber(voucher.getUser().getPhoneNumber());
            voucherDto.setUserFirstName(voucher.getUser().getFirstName());
            voucherDto.setUserLastName(voucher.getUser().getLastName());
        }
        voucherDto.setRewardName(voucher.getReward().getName());
        voucherDto.setRewardId(voucher.getReward().getId());
        return voucherDto;
    }

}
