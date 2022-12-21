package com.taraan.dum.logic;

import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


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

    public List<TripTrackerInfo> getTripTrackerInfoList(String deviceId, Date from, Date to) {
        Query query = new
                Query(Criteria.where("deviceId").is(deviceId).andOperator(
                Criteria.where("longitude").ne(0d),
                Criteria.where("latitude").ne(0d),
                Criteria.where("postDateTimeStamp").gt(from.getTime()),
                Criteria.where("postDateTimeStamp").lt(to.getTime())))
                .with(Sort.by(new Sort.Order(Sort.Direction.DESC, "order")))
                .with(PageRequest.of(1, 1));
        return mongoTemplate.find(query, TripTrackerInfo.class);
    }
}
