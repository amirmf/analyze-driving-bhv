package com.taraan.dum.statisticgenerator.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;


public class TripTrackerInfoRepository {
    private MongoTemplate mongoTemplate;

    public TripTrackerInfoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<TripTrackerInfo> getTripTrackerInfoeList(String deviceId, Date from, Date to) {
        Query query = new
                Query(Criteria.where("deviceId").is(deviceId).andOperator(
                Criteria.where("postDateTimeStamp").gt(from.getTime()),
                Criteria.where("postDateTimeStamp").lt(to.getTime())));
//        Query query = new Query(Criteria.where("deviceId").is(deviceId));

        return mongoTemplate.find(query, TripTrackerInfo.class);
    }
}
