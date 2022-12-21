package com.taraan.dum.service.assembler;

import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import com.taraan.dum.logic.TripTrackerInfoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;


public class TripTrackerInfoAssembler {
    private TripTrackerInfoRepository repository;


    public void save(TripTrackerInfo tripTrackerInfo) {
        repository.save(tripTrackerInfo);
    }

    public void saveAll(TripTrackerInfo[] tripTrackerInfos) {
        repository.saveAll(tripTrackerInfos);
    }
}
