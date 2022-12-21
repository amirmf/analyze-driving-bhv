package com.taraan.dum.logger.mongodb;

import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;



public class TripTrackerInfoRepository {
    private MongoTemplate mongoTemplate;

    public TripTrackerInfoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(TripTrackerInfo tripTrackerInfo) {
        mongoTemplate.insert(tripTrackerInfo);
    }

    public void saveAll(TripTrackerInfo[] tripTrackerInfos) {
        mongoTemplate.insertAll(Arrays.asList(tripTrackerInfos));
    }
}
