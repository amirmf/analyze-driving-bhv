package com.taraan.dum.logic;

import com.mongodb.client.MongoCollection;
import com.taraan.dum.audit.mongodb.TripLocation;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class TripLocationRepository {
    private MongoTemplate mongoTemplate;
    public TripLocationRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(List<TripLocation> tripLocations) {
        mongoTemplate.save(tripLocations);
    }

    public List<TripLocation> getByTripId(Long tripId) {
        final MongoCollection<Document> tripLocation = mongoTemplate.getCollection("TripLocation");
        Query query = new Query(Criteria.where("tripId").is(tripId));
        return mongoTemplate.find(query, TripLocation.class);
    }
}
