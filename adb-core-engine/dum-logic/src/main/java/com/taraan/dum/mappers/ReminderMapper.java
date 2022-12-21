package com.taraan.dum.mappers;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.dto.reminder.ReminderDto;
import com.taraan.dum.model.hibernate.Reminder;

public class ReminderMapper {
    public static ReminderDto getDto(Reminder reminder) {
        ReminderDto reminderDto = new ReminderDto();
        if (reminder == null)
            return reminderDto;
        reminderDto.setActive(reminder.isActive());
        reminderDto.setRemindDayNotification(reminder.getRemindDayNotification());
        if (reminder.getInsuranceRegisterOn() != null)
            reminderDto.setInsuranceRegisterOn(DateUtils.getDate(reminder.getInsuranceRegisterOn()));
        if (reminder.getInsuranceExpirationDate() != null)
            reminderDto.setInsuranceExpirationDate(DateUtils.getDate(reminder.getInsuranceExpirationDate()));
        if (reminder.getLastServiceDate() != null)
            reminderDto.setLastServiceDate(DateUtils.getDate(reminder.getLastServiceDate()));
        reminderDto.setServiceAfter(reminder.getServiceAfter());
        return reminderDto;
    }

    public static ReminderDto getAdminDto(Reminder reminder) {
        ReminderDto reminderDto = new ReminderDto();
        if (reminder == null)
            return reminderDto;
        reminderDto.setId(reminder.getId());
        if (reminder.getUser() != null) {
            reminderDto.setPhoneNumber(reminder.getUser().getPhoneNumber());
            reminderDto.setUserId(reminder.getUser().getId());
        }
        reminderDto.setActive(reminder.isActive());
        reminderDto.setRemindDayNotification(reminder.getRemindDayNotification());
        if (reminder.getInsuranceRegisterOn() != null)
            reminderDto.setInsuranceRegisterOn(DateUtils.getDate(reminder.getInsuranceRegisterOn()));
        if (reminder.getInsuranceExpirationDate() != null)
            reminderDto.setInsuranceExpirationDate(DateUtils.getDate(reminder.getInsuranceExpirationDate()));
        if (reminder.getLastServiceDate() != null)
            reminderDto.setLastServiceDate(DateUtils.getDate(reminder.getLastServiceDate()));
        reminderDto.setServiceAfter(reminder.getServiceAfter());
        return reminderDto;
    }

}
