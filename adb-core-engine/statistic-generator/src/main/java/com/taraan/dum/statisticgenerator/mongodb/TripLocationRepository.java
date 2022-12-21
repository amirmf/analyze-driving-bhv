package com.taraan.dum.statisticgenerator.mongodb;

import com.taraan.dum.audit.mongodb.TripLocation;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.List;


public class TripLocationRepository {
    private MongoTemplate mongoTemplate;

    public TripLocationRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(List<TripLocation> tripLocations) {
        mongoTemplate.insertAll(tripLocations);
    }
}
