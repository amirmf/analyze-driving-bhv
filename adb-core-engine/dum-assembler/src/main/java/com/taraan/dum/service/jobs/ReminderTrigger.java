package com.taraan.dum.service.jobs;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.common.globalization.DateTimeFormatter;
import com.taraan.dum.dto.message.SendMessageRequest;
import com.taraan.dum.logic.NotifyLogic;
import com.taraan.dum.model.hibernate.Reminder;
import com.taraan.dum.model.hibernate.User;
import com.taraan.dum.service.assembler.MessageAssembler;
import com.taraan.dum.service.assembler.ReminderAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

//@Component
public class ReminderTrigger {

    @Autowired
    private ReminderAssembler reminderAssembler;
    @Autowired
    private NotifyLogic notifyLogic;
    @Autowired
    private MessageAssembler messageAssembler;
    @Value("${reminder.serviceText}")
    private String serviceText;
    @Value("${reminder.insuranceText}")
    private String insuranceText;

    public ReminderTrigger() {
        System.out.println();
    }

    @Scheduled(cron = "${reminder.time.expression}")
    public void cronCheckInsuranceExpirationDate() {
        LocalDate now = LocalDate.now();
        LocalDate fromLocalDate = now.plus(Period.ofDays(1));
        LocalDate toLocalDate = fromLocalDate.plus(Period.ofDays(1));
        LocalDateTime fromLocalDateTime = fromLocalDate.atStartOfDay();
        LocalDateTime toLocalDateTime = toLocalDate.atStartOfDay();
        Date fromDate = Date.from(fromLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(toLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        List<Reminder> reminders = reminderAssembler.getRemindersByInsuranceExpirationDate(fromDate, toDate);
        for (Reminder reminder : reminders) {
            try {
                User user = reminder.getUser();
                SendMessageRequest sendMessageRequest = new SendMessageRequest();
                sendMessageRequest.setPhoneNumber(user.getPhoneNumber());
                String text = insuranceText.replace("${date}", DateTimeFormatter.toShortDate(reminder.getInsuranceExpirationDate()));
                sendMessageRequest.setText(text);
                messageAssembler.sendMessage(sendMessageRequest);
                notifyLogic.sendSms(user.getPhoneNumber(),text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "${reminder.service.time.expression}")
    public void cronCreateDailyStatistics() {
        LocalDate now = LocalDate.now();
        LocalDate fromLocalDate = now.plus(Period.ofDays(1));
        LocalDate toLocalDate = fromLocalDate.plus(Period.ofDays(1));
        LocalDateTime fromLocalDateTime = fromLocalDate.atStartOfDay();
        LocalDateTime toLocalDateTime = toLocalDate.atStartOfDay();
        Date fromDate = Date.from(fromLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(toLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        List<Reminder> reminders = reminderAssembler.getRemindersByNextServiceDate(fromDate, toDate);
        for (Reminder reminder : reminders) {
            try {

                User user = reminder.getUser();
                SendMessageRequest sendMessageRequest = new SendMessageRequest();
                sendMessageRequest.setPhoneNumber(user.getPhoneNumber());
                String text = serviceText.replace("${date}", DateTimeFormatter.toShortDate(reminder.getNextServiceDate()));
                sendMessageRequest.setText(text);
                messageAssembler.sendMessage(sendMessageRequest);
                notifyLogic.sendSms(user.getPhoneNumber(),text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
