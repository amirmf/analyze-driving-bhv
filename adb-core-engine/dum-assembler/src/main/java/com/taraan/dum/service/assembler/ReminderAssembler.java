package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.reminder.ReminderDto;
import com.taraan.dum.dto.reminder.ReminderPage;
import com.taraan.dum.logic.ReminderLogic;
import com.taraan.dum.model.hibernate.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class ReminderAssembler {
    @Autowired
    private ReminderLogic reminderLogic;

    public ReminderDto getReminderByUser(Long userId) {
        return reminderLogic.getReminderByUser(userId);
    }

    public ReminderDto updateReminder(ReminderDto reminderDto, Long userId) {
        return reminderLogic.updateReminder(reminderDto, userId);
    }

    public ReminderPage getReminders(String phoneNumber, String fromExpirationDate, String toExpirationDate, String fromRegisterDate, String toRegisterDate, Long from, Long size) {
        return reminderLogic.getReminders(phoneNumber, fromExpirationDate, toExpirationDate, fromRegisterDate, toRegisterDate, from, size);
    }

    public List<Reminder> getRemindersByInsuranceExpirationDate(Date fromDate, Date toDate) {
        return reminderLogic.getRemindersByInsuranceExpirationDate(fromDate, toDate);
    }

    public List<Reminder> getRemindersByNextServiceDate(Date fromDate, Date toDate) {
        return reminderLogic.getRemindersByNextServiceDate(fromDate, toDate);
    }
}
