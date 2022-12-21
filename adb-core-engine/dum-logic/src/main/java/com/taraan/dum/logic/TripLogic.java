package com.taraan.dum.logic;

import com.taraan.dum.audit.mongodb.TripLocation;
import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import com.taraan.dum.common.DateUtils;
import com.taraan.dum.da.*;
import com.taraan.dum.dto.analyze.*;
import com.taraan.dum.dto.trip.*;
import com.taraan.dum.mappers.TripMapper;
import com.taraan.dum.model.hibernate.*;
import com.taraan.dum.producer.MessageProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.persistence.NoResultException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Component
public class TripLogic {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private TripDa tripDa;

    @Autowired
    private UserDa userDa;

    @Autowired
    private UserScoreDa userScoreDa;

    @Autowired
    private TripLocationRepository tripLocationRepository;

    @Autowired
    private TripTrackerInfoRepository tripTrackerInfoRepository;

    @Autowired
    private MessageProducer messageProducer;

    @Value("${tripInfoStatisticUrl}")
    private String tripInfoStatisticPath;

    @Value("${analyzeTripPath}")
    private String analyzeTripPath;


    public Long startTrip(StartTripDto startTripDto, Long userId) {
        final Boolean activeTrip = isActiveTrip(userId);
        if (activeTrip) {
            final Trip lastTripByUser = tripDa.getLastTripByUser(userId);
            final List<TripTrackerInfo> tripTrackerInfoList = tripTrackerInfoRepository.getTripTrackerInfoList(lastTripByUser.getDeviceId(), lastTripByUser.getTripStartDate(), new Date());
            final Date lastTripDate = !tripTrackerInfoList.isEmpty() ? new Date(tripTrackerInfoList.get(0).getPostDateTimeStamp()) : new Date();
            final EndTripDto endTripDto = new EndTripDto();
            endTripDto.setUserId(userId);
            endTrip(endTripDto, lastTripDate);
        }

        Trip trip = new Trip();
        trip.setDeviceId(startTripDto.getDeviceId());
        trip.setUser(userDa.get(userId));
        trip.setTripStartDate(new Date());
        trip = tripDa.save(trip);
        return trip.getId();
    }

    public boolean endTrip(EndTripDto endTripDto, Date endDate) {
        log.info("start end trip.");
        final User user = userDa.get(endTripDto.getUserId());
        log.info("user load:" + user.getId());
        Trip trip = tripDa.getLastTripByUser(user.getId());
        if (trip.getTripEndDate() != null)
            return false;
        log.info("end trip. user : " + user.getId() + " trip : " + trip.getId());
        if (endDate == null)
            trip.setTripEndDate(new Date());
        else
            trip.setTripEndDate(endDate);

        if (endTripDto.getName() != null && !endTripDto.getName().isEmpty())
            trip.setName(endTripDto.getName());
        else
            trip.setName(String.valueOf(trip.getId()));

        log.info("forward message for generator. user : " + user.getId() + " trip : " + trip.getId());
        this.setTripInfo(trip);
        trip.setDuration((trip.getTripEndDate().getTime() - trip.getTripStartDate().getTime()) / 1000);
        if (trip.getDistance() > 0.5) {
            log.info("invalid trip;trip : " + trip.getId());
            trip.setValid(true);
        }
        log.info("Trip : " + trip);
        tripDa.update(trip);

        if (trip.isValid()) {
            this.analyzeData(user, trip);
            log.info("analyze formula : " + trip.getId());
        }
        return trip.isValid();
    }

    private void analyzeData(User user, Trip trip) {
        JmsAnalyzeRequest jmsAnalyzeRequest = new JmsAnalyzeRequest();
        jmsAnalyzeRequest.setTripId(trip.getId());
        jmsAnalyzeRequest.setUserId(user.getId());
        messageProducer.send(jmsAnalyzeRequest);
    }

    private void setTripInfo(Trip trip) {
        final TripInfoDto tripInfoDto = getTripInfoResult(trip);
        log.info("trip info result : trip :" + trip.getId() + ".info" + tripInfoDto);
        setTripInfo(trip, tripInfoDto);
    }

    private void setTripInfo(Trip trip, TripInfoDto tripInfoDto) {
        trip.setSpeedDistributionUnderLimit(tripInfoDto.getSpeedDistributionUnderLimit());
        trip.setSpeedDistributionOverSpeed(tripInfoDto.getSpeedDistributionOverSpeed());
        trip.setSpeedDistributionHighOverSpeed(tripInfoDto.getSpeedDistributionHighOverSpeed());
        trip.setAverageSpeed(tripInfoDto.getAverageSpeed());
        trip.setMaxSpeed(tripInfoDto.getMaxSpeed());
        trip.setCorneringCount(tripInfoDto.getCorneringCount());
        trip.setBrakingCount(tripInfoDto.getBrakingCount());
        trip.setAccelerationCount(tripInfoDto.getAccelerationCount());
        trip.setDistance(tripInfoDto.getDistance());
        trip.setOtherDistance(tripInfoDto.getOtherDistance());
        trip.setUrbanDistance(tripInfoDto.getUrbanDistance());
        trip.setHighwayDistance(tripInfoDto.getHighwayDistance());
        trip.setIdleTime(tripInfoDto.getIdleTime());
        trip.setDuration(tripInfoDto.getDuration());
        trip.setBadDirection(tripInfoDto.getBadDirection());
        trip.setMinIdleTime(tripInfoDto.getMinIdleTime());
        trip.setMaxIdleTime(tripInfoDto.getMaxIdleTime());
        trip.setMovementTime(tripInfoDto.getMovementTime());
        trip.setMinMovementTime(tripInfoDto.getMinMovementTime());
        trip.setMaxMovementTime(tripInfoDto.getMaxMovementTime());
        trip.setWeekend(tripInfoDto.isWeekend());
        if (tripInfoDto.getTripOfHours() != null)
            for (TripOfHourDto tripOfHourDto : tripInfoDto.getTripOfHours()) {
                trip.getTripOfHours().add(new TripOfHour(tripOfHourDto.getHour(), tripOfHourDto.getDistance(), tripOfHourDto.getTime()));

            }
    }

    private TripInfoDto getTripInfoResult(Trip trip) {
        CreateTripInfoRequest request = new CreateTripInfoRequest();
        request.setDeviceId(trip.getDeviceId());
        request.setFromDate(trip.getTripStartDate());
        request.setToDate(trip.getTripEndDate());
        request.setTripId(trip.getId());
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(this.tripInfoStatisticPath);
        final Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(request, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 200)
            return response.readEntity(TripInfoDto.class);
        else
            return new TripInfoDto();
    }

    public TripPage getTrips(Long userId, int from, int size) {
        List<Trip> trips = tripDa.getExists(userId, from, size);
        Double count = Math.ceil(tripDa.getCount(userId) / 10d);
        return new TripPage(TripMapper.getDtos(trips), count.longValue());
    }

    public TripDto getTrip(Long userId, Long tripId) {
        Trip trip = tripDa.get(tripId);
        if (trip == null)
            return null;
        if (trip.getUser().getId().equals(userId)) {
            final TripDto dto = TripMapper.getDto(trip);
            dto.setScore(trip.getScore());
            return dto;
        }
        return null;
    }

    public TripDto getTrip(Long tripId) {
        Trip trip = tripDa.get(tripId);
        if (trip == null)
            return null;
        final TripDto dto = TripMapper.getDto(trip);
        List<UserScore> userScore = userScoreDa.getByTrip(tripId);
        Long sumScore = 0L;
        for (UserScore score : userScore) {
            sumScore += score.getScore();
        }
        dto.setScore(sumScore);
        return dto;
    }

    public List<TripGeoLocation> getTripGeoLocations(Long userId, Long id) {
        List<TripGeoLocation> tripGeoLocations = new ArrayList<>();
        for (TripLocation tripLocation : tripLocationRepository.getByTripId(id)) {
            tripGeoLocations.add(new TripGeoLocation(tripLocation.getLongitude(), tripLocation.getLatitude(), tripLocation.getEvent() == null ? 0 : tripLocation.getEvent()));
        }
        return tripGeoLocations;
    }

    public List<TripGeoLocation> getTripGeoLocations(Long id) {
        List<TripGeoLocation> tripGeoLocations = new ArrayList<>();
        for (TripLocation tripLocation : tripLocationRepository.getByTripId(id)) {
            tripGeoLocations.add(new TripGeoLocation(tripLocation.getLongitude(), tripLocation.getLatitude(), tripLocation.getEvent() == null ? 0 : tripLocation.getEvent()));
        }
        return tripGeoLocations;
    }

    public Boolean isActiveTrip(Long userId) {
        try {
            final Trip lastTripByUser = tripDa.getLastTripByUser(userId);
            if (lastTripByUser == null)
                return false;
            if (lastTripByUser.getTripEndDate() == null) {
                final List<TripTrackerInfo> tripTrackerInfoList = tripTrackerInfoRepository.getTripTrackerInfoList(lastTripByUser.getDeviceId(), lastTripByUser.getTripStartDate(), new Date());
                if (!tripTrackerInfoList.isEmpty()) {
                    final Date lastTripDate = new Date(tripTrackerInfoList.get(0).getPostDateTimeStamp());
                    final int hourDifferent = DateUtils.getHourDateDiffrent(new Date(), lastTripDate);
                    if (hourDifferent >= 1) {
                        final EndTripDto endTripDto = new EndTripDto();
                        endTripDto.setUserId(userId);
                        endTrip(endTripDto, lastTripDate);
                        return false;
                    }
                } else {
                    final EndTripDto endTripDto = new EndTripDto();
                    endTripDto.setUserId(userId);
                    endTrip(endTripDto, new Date());
                    return false;
                }
            }
            return lastTripByUser.getTripEndDate() == null;
        } catch (NoResultException nre) {
            return false;
        }
    }

    public void removeTrip(Long userId, Long id) {
        final Trip trip = tripDa.get(id);
        if (userId.equals(trip.getUser().getId())) {
            trip.setRemoved(true);
            tripDa.update(trip);
        }
    }

    public TripPage getTrips(String phoneNumber, String fromStartDate, String toStartDate, Double fromDistance,
                             Double toDistance, Double fromDuration, Double toDuration, Long from, Long size) {
        Date fromStart = null;
        Date toStart = null;
        if (fromStartDate != null && !fromStartDate.isEmpty())
            fromStart = DateUtils.getDate(fromStartDate);
        if (toStartDate != null && !toStartDate.isEmpty())
            toStart = DateUtils.getDate(toStartDate);
        TripPage tripPage = new TripPage();
        List<Trip> trips = tripDa.get(phoneNumber, fromStart, toStart, fromDistance, toDistance, fromDuration, toDuration, from, size);
        for (Trip trip : trips) {
            final TripItemDto e = new TripItemDto();
            e.setId(trip.getId());
            e.setName(trip.getName());
            e.setTripStartDate(DateUtils.getDate(trip.getTripStartDate()));
            e.setTripEndDate(DateUtils.getDate(trip.getTripEndDate()));
            tripPage.getTrips().add(e);
        }
        tripPage.setCount(tripDa.getCount(phoneNumber, fromStart, toStart, fromDistance, toDistance, fromDuration, toDuration));
        return tripPage;
    }
}
