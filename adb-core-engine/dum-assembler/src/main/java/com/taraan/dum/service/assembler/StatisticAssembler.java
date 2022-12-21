package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.statistic.HomeDataDto;
import com.taraan.dum.dto.statistic.RewardDto;
import com.taraan.dum.dto.statistic.StatisticDto;
import com.taraan.dum.logic.StatisticLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class StatisticAssembler {
    @Autowired
    private StatisticLogic statisticLogic;


    public HomeDataDto getHomeDate(Long userId) {
        return statisticLogic.getHomeDate(userId);
    }
    public List<RewardDto> getRewards(Long userId) {
        return statisticLogic.getRewards(userId);
    }

    public StatisticDto getStatistics(Long userId, String from, String to) {
        return statisticLogic.getStatistics(userId,from,to);
    }
}
