package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.parentalcontrol.ParentalControlPage;
import com.taraan.dum.logic.ParentalControlLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created on 7/6/2018 10:52 AM.
 * User: Reza
 * Project : driving-usage-miner
 */
@Component
@Transactional
public class ParentalControlAssembler {
    @Autowired
    private ParentalControlLogic parentalControlLogic;

    public void createParentalControl(Long userId, String phoneNumber) {
        parentalControlLogic.createParentalControl(userId, phoneNumber);
    }

    public ParentalControlPage getChildOfParentalControl(Long userId) {
        return parentalControlLogic.getChildOfParentalControl(userId);
    }

    public ParentalControlPage getParentOfParentalControl(Long userId) {
        return parentalControlLogic.getParentOfParentalControl(userId);
    }

    public void removeParentOfParentalControl(Long parentalControlId, Long userId) {
        parentalControlLogic.removeParentOfParentalControl(parentalControlId, userId);
    }
}
