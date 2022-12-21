package com.taraan.dum.statisticgenerator.trigger;

import com.taraan.dum.statisticgenerator.assembler.StatisticGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

//@Component
public class CronTrigger {
    @Autowired
    private StatisticGenerator statisticGenerator;

    public CronTrigger() {
        System.out.println();
    }

    @Scheduled(cron="${time.expression}")
    public void cronCreateDailyStatistics()
    {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minus(Period.ofDays(1));
        LocalDateTime fromLocalDateTime = yesterday.atStartOfDay();
        LocalDateTime toLocalDateTime = now.atStartOfDay();
        Date fromDate = Date.from(fromLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(toLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date currentDate = new Date();
        List<Long> userIds = statisticGenerator.getDrivingUserIds(fromDate, toDate);
        for (Long userId : userIds) {
            statisticGenerator.createDailyStatistics(userId,fromDate,toDate);
        }
    }

}
