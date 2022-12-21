package com.taraan.dum.logic;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.da.BadgeDa;
import com.taraan.dum.da.ReminderDa;
import com.taraan.dum.da.UserBadgeDa;
import com.taraan.dum.da.UserDa;
import com.taraan.dum.dto.reminder.ReminderDto;
import com.taraan.dum.dto.reminder.ReminderPage;
import com.taraan.dum.mappers.ReminderMapper;
import com.taraan.dum.model.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ReminderLogic {
    @Autowired
    private ReminderDa reminderDa;
    @Autowired
    private UserDa userDa;
    @Autowired
    private UserBadgeDa userBadgeDa;

    @Autowired
    private BadgeDa badgeDa;

    public ReminderDto getReminderByUser(Long userId) {
        Reminder reminder = reminderDa.getByUserId(userId);
        return ReminderMapper.getDto(reminder);
    }

    public ReminderDto updateReminder(ReminderDto reminderDto, Long userId) {
        User user = userDa.get(userId);
        Reminder reminder = reminderDa.getByUserId(userId);
        if (reminder == null) {
            reminder = new Reminder();
            reminder.setUser(userDa.get(userId));
        }
        boolean badge = true;
        reminder.setActive(reminderDto.isActive());
        if (reminderDto.getInsuranceExpirationDate() != null && !reminderDto.getInsuranceExpirationDate().isEmpty())
            reminder.setInsuranceExpirationDate(DateUtils.getDate(reminderDto.getInsuranceExpirationDate()));
        else {
            badge = false;
            reminder.setInsuranceExpirationDate(null);
        }

        if (reminderDto.getLastServiceDate() != null && !reminderDto.getLastServiceDate().isEmpty())
            reminder.setLastServiceDate(DateUtils.getDate(reminderDto.getLastServiceDate()));
        else {
            badge = false;
            reminder.setLastServiceDate(null);
        }
        if (reminderDto.getInsuranceRegisterOn() != null && !reminderDto.getInsuranceRegisterOn().isEmpty())
            reminder.setInsuranceRegisterOn(DateUtils.getDate(reminderDto.getInsuranceRegisterOn()));
        else {
            reminder.setInsuranceRegisterOn(null);
        }
        reminder.setServiceAfter(reminderDto.getServiceAfter());
        if (reminder.getLastServiceDate() != null && reminder.getServiceAfter() != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(reminder.getLastServiceDate());
            instance.add(Calendar.MONTH, reminder.getServiceAfter());
            reminder.setNextServiceDate(instance.getTime());
        }
        reminder.setRemindDayNotification(reminderDto.getRemindDayNotification());
        reminder = reminderDa.save(reminder);
        if (badge) {
            final Badge reminderBadge = badgeDa.get("REMINDER_BADGE");
            if (reminderBadge != null) {
                List<UserBadge> userBadges = userBadgeDa.getByUserAndBadge(user.getId(), reminderBadge.getId());
                if (userBadges.isEmpty()) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setBadge(reminderBadge);
                    userBadge.setUserBadgeState(UserBadgeState.SAVED);
                    userBadge.setUser(user);
                    userBadge.setDate(new Date());
                    userBadgeDa.save(userBadge);
                }

            }
        }
        return ReminderMapper.getDto(reminder);
    }

    public ReminderPage getReminders(String phoneNumber, String fromExpirationDate, String toExpirationDate, String fromRegisterDate, String toRegisterDate, Long from, Long size) {
        Date fromExpiration = null;
        Date toExpiration = null;
        Date fromRegister = null;
        Date toRegister = null;
        if (fromExpirationDate != null && !fromExpirationDate.isEmpty())
            fromExpiration = DateUtils.getDate(fromExpirationDate);
        if (toExpirationDate != null && !toExpirationDate.isEmpty())
            toExpiration = DateUtils.getDate(toExpirationDate);
        if (fromRegisterDate != null && !fromRegisterDate.isEmpty())
            fromRegister = DateUtils.getDate(fromRegisterDate);
        if (toRegisterDate != null && !toRegisterDate.isEmpty())
            toRegister = DateUtils.getDate(toRegisterDate);
        final List<Reminder> reminders = reminderDa.gets(phoneNumber, fromExpiration, toExpiration, fromRegister, toRegister, from, size);
        ReminderPage reminderPage = new ReminderPage();
        for (Reminder reminder : reminders) {
            reminderPage.getReminders().add(ReminderMapper.getAdminDto(reminder));
        }
        reminderPage.setCount(reminderDa.getCounts(phoneNumber, fromExpiration, toExpiration, fromRegister, toRegister));
        return reminderPage;

    }

    public List<Reminder> getRemindersByInsuranceExpirationDate(Date fromDate, Date toDate) {
        return reminderDa.getRemindersByInsuranceExpirationDate(fromDate, toDate);
    }

    public List<Reminder> getRemindersByNextServiceDate(Date fromDate, Date toDate) {
        return reminderDa.getRemindersByNextServiceDate(fromDate, toDate);
    }
}
