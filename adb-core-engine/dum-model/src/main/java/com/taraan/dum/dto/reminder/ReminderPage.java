package com.taraan.dum.dto.reminder;

import java.util.ArrayList;
import java.util.List;

public class ReminderPage {
    private List<ReminderDto> reminders = new ArrayList<>();
    private Long count;

    public List<ReminderDto> getReminders() {
        return reminders;
    }

    public void setReminders(List<ReminderDto> reminders) {
        this.reminders = reminders;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ReminderPage{" +
                "reminders=" + reminders +
                ", count=" + count +
                '}';
    }
}
