package com.taraan.dum.service.rest;

import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import com.taraan.dum.common.DateUtils;
import com.taraan.dum.dto.tracker.TripTrackerInfoDto;
import com.taraan.dum.dto.tracker.TripTrackerInfoDtos;
import com.taraan.dum.logic.TripTrackerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("tripTrackerInfo")
@Component
public class RestTripTrackerInfoService {

    @Autowired
    private TripTrackerInfoRepository tripTrackerInfoRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/one")
    public Response createBadge(TripTrackerInfoDto tripTrackerInfoDto) {
        TripTrackerInfo trackerInfo = getTripTrackerInfo(tripTrackerInfoDto);
        tripTrackerInfoRepository.save(trackerInfo);
        return Response.ok().build();
    }

    private TripTrackerInfo getTripTrackerInfo(TripTrackerInfoDto tripTrackerInfoDto) {
        TripTrackerInfo trackerInfo = new TripTrackerInfo();
        trackerInfo.setDeviceId(tripTrackerInfoDto.getDeviceId());
        trackerInfo.setOrder(tripTrackerInfoDto.getOrder());
        trackerInfo.setDeviceId(tripTrackerInfoDto.getDeviceId());
        trackerInfo.setLongitude(tripTrackerInfoDto.getLongitude());
        trackerInfo.setLatitude(tripTrackerInfoDto.getLatitude());
        trackerInfo.setX(tripTrackerInfoDto.getX());
        trackerInfo.setY(tripTrackerInfoDto.getY());
        trackerInfo.setZ(tripTrackerInfoDto.getZ());
        trackerInfo.setRealSpeed(tripTrackerInfoDto.getRealSpeed());
        trackerInfo.setDirection(tripTrackerInfoDto.getDirection());
        final Date date = new Date();
        trackerInfo.setPostDateTimeStamp(date.getTime());
        trackerInfo.setPostDate(date);
        final Date userDate = DateUtils.getDate(tripTrackerInfoDto.getPostDate());
        trackerInfo.setUserPostDate(userDate);
        trackerInfo.setUserPostDateTimeStamp(userDate.getTime());
        return trackerInfo;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBadge(TripTrackerInfoDtos tripTrackerInfoDto) {
        for (TripTrackerInfoDto trackerInfoDto : tripTrackerInfoDto.getTripTrackerInfos()) {
            System.out.println("create TripTrackerInfoDto. : " + trackerInfoDto);
            tripTrackerInfoRepository.save(getTripTrackerInfo(trackerInfoDto));
        }
        return Response.ok().build();
    }

}
