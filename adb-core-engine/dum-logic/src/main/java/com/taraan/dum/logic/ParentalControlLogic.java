package com.taraan.dum.logic;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.da.ParentalControlDa;
import com.taraan.dum.da.UserDa;
import com.taraan.dum.da.UserScoreDa;
import com.taraan.dum.dto.parentalcontrol.ParentalControlItem;
import com.taraan.dum.dto.parentalcontrol.ParentalControlPage;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.exceptions.UserAlreadyJoinException;
import com.taraan.dum.exceptions.user.InvalidUserException;
import com.taraan.dum.model.hibernate.DateRange;
import com.taraan.dum.model.hibernate.ParentalControl;
import com.taraan.dum.model.hibernate.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

/**
 * Created on 7/6/2018 10:53 AM.
 * User: Reza
 * Project : driving-usage-miner
 */
@Component
public class ParentalControlLogic {
    @Autowired
    private ParentalControlDa parentalControlDa;
    @Autowired
    private UserDa userDa;
    @Autowired
    private UserScoreDa userScoreDa;

    public void createParentalControl(Long userId, String phoneNumber) {
        User fromUser = userDa.getByPhoneNumber(phoneNumber);
        if (fromUser == null)
            throw new EntityDoesNotExistException("user");
        ParentalControl parentalControl = null;
        try {
            parentalControl = parentalControlDa.getByFromUserAndToUser(fromUser.getId(), userId);
        } catch (NoResultException ignore) {

        }
        if (parentalControl != null && parentalControl.getRange().getTo() == null)
            throw new UserAlreadyJoinException();
        parentalControl = new ParentalControl();
        parentalControl.setFromUser(fromUser);
        parentalControl.setToUser(userDa.get(userId));
        parentalControl.setRange(new DateRange(new Date()));
        parentalControlDa.save(parentalControl);
    }

    public ParentalControlPage getChildOfParentalControl(Long userId) {
        ParentalControlPage parentalControlPage = new ParentalControlPage();
        List<ParentalControl> parentalControl = parentalControlDa.getChildOfParentalControl(userId);
        Long count = parentalControlDa.getCountChildOfParentalControl(userId);
        for (ParentalControl control : parentalControl) {
            ParentalControlItem e = new ParentalControlItem();
            e.setId(control.getId());
            e.setName(control.getToUser().getCompleteName());
            e.setScore(userScoreDa.getAverageScore(control.getFromUser().getId()).longValue());
            parentalControlPage.getItems().add(e);
        }
        parentalControlPage.setCount(count);
        return parentalControlPage;
    }

    public ParentalControlPage getParentOfParentalControl(Long userId) {
        ParentalControlPage parentalControlPage = new ParentalControlPage();
        List<ParentalControl> parentalControl = parentalControlDa.getParentOfParentalControl(userId);
        Long count = parentalControlDa.getCountParentOfParentalControl(userId);
        for (ParentalControl control : parentalControl) {
            ParentalControlItem e = new ParentalControlItem();
            e.setId(control.getId());
            e.setName(control.getFromUser().getCompleteName());
            parentalControlPage.getItems().add(e);
        }
        parentalControlPage.setCount(count);
        return parentalControlPage;
    }


    public void removeParentOfParentalControl(Long parentalControlId, Long userId) {
        ParentalControl parentalControl = parentalControlDa.get(parentalControlId);
        if (!(parentalControl.getFromUser().getId().equals(userId) || !parentalControl.getToUser().getId().equals(userId)))
            throw new InvalidUserException();
        parentalControl.getRange().setTo(new Date());
        parentalControlDa.save(parentalControl);
    }
}
