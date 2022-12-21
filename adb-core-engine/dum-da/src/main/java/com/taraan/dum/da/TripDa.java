package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.Trip;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TripDa extends GenericDa<Trip> {
    public List<Trip> gets(Long userId, Date from, Date to) {
        return this.getSession().createQuery("select distinct trip from Trip trip where trip.tripEndDate > :FROM_DATE and trip.tripEndDate <= :TO_DATE and trip.user.id=:USER_ID  and trip.valid=true  order by id desc")
                .setParameter("FROM_DATE", from).setParameter("TO_DATE", to).setParameter("USER_ID", userId).getResultList();
    }

    public List<Trip> gets(Long userId, int from, int size) {
        return this.getSession().createQuery("select distinct trip from Trip trip where trip.user.id=:USER_ID and trip.valid = true order by id desc")
                .setParameter("USER_ID", userId).setMaxResults(size).setFirstResult(from).getResultList();
    }

    public List<Trip> getExists(Long userId, int from, int size) {
        return this.getSession().createQuery("select distinct trip from Trip trip where trip.user.id=:USER_ID and trip.valid = true and trip.removed = false  order by id desc")
                .setParameter("USER_ID", userId).setMaxResults(size).setFirstResult(from).getResultList();
    }

    public Trip getLastTripByUser(Long userId) {
        return (Trip) this.getSession().createQuery("select trip from Trip trip where trip.user.id=:USER_ID order by id desc")
                .setParameter("USER_ID", userId).setMaxResults(1).getSingleResult();
    }

    public Long getCount(Long userId) {
        return (Long) this.getSession().createQuery("select count(trip.id) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).getSingleResult();
    }

    public Double getAverageSpeed(Long userId) {
        final Double result = (Double) this.getSession().createQuery("select avg(trip.averageSpeed) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        return 0D;
    }

    public Double getSumDistance(Long userId) {
        final Double result = (Double) this.getSession().createQuery("select sum(trip.distance) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        return 0D;
    }

    public Double getMaxSpeed(Long userId) {
        final Double result = (Double) this.getSession().createQuery("select max(trip.maxSpeed) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        return 0D;
    }

    public Long getSumDuration(Long userId) {
        final Long result = (Long) this.getSession().createQuery("select sum(trip.duration) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        return 0L;
    }

    public Double getSumHighwayDistance(Long userId) {
        final Double result = (Double) this.getSession().createQuery("select sum(trip.highwayDistance) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        return 0D;
    }

    public Double getSumUrbanDistance(Long userId) {
        final Double result = (Double) this.getSession().createQuery("select sum(trip.urbanDistance) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        return 0D;

    }

    public Double getSumOtherDistance(Long userId) {
        final Double result = (Double) this.getSession().createQuery("select sum(trip.otherDistance) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        return 0D;

    }

    public Long getSumTime(Long userId, boolean weekend) {
        final Long result = (Long) this.getSession().createQuery("select sum(trip.duration) from Trip trip where trip.user.id=:USER_ID and trip.weekend=:WEEKEDN and trip.valid = true")
                .setParameter("USER_ID", userId).setParameter("WEEKEDN", weekend).uniqueResult();
        if (result != null)
            return result;
        return 0L;
    }

    public Long getSumTime(Long userId) {
        final Long result = (Long) this.getSession().createQuery("select sum(trip.duration) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        return 0L;
    }

    public Long getSumTrip(Long userId) {
        return null;
    }

    public Long getSumScore(Long userId) {
        final Long result = (Long) this.getSession().createQuery("select sum(trip.score) from Trip trip where trip.user.id=:USER_ID and trip.valid = true")
                .setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        return 0L;
    }

    public List<Trip> get(String phoneNumber, Date fromStart, Date toStart, Double fromDistance, Double toDistance, Double fromDuration, Double toDuration, Long from, Long size) {
        String queryString = "select trip from Trip trip inner join trip.user user ";
        String whereString = getWhereString(phoneNumber, fromStart, toStart, fromDistance, toDistance, fromDuration, toDuration);
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        final Query query = this.getSession().createQuery(queryString);
        setQueryParameter(query, phoneNumber, fromStart, toStart, fromDistance, toDistance, fromDuration, toDuration);
        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());
        return query.list();
    }

    public Long getCount(String phoneNumber, Date fromStart, Date toStart, Double fromDistance, Double toDistance, Double fromDuration, Double toDuration) {
        String queryString = "select count(trip.id) from Trip trip inner join trip.user user ";
        String whereString = getWhereString(phoneNumber, fromStart, toStart, fromDistance, toDistance, fromDuration, toDuration);
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        final Query query = this.getSession().createQuery(queryString);
        setQueryParameter(query, phoneNumber, fromStart, toStart, fromDistance, toDistance, fromDuration, toDuration);
        return (Long) query.uniqueResult();
    }

    private String getWhereString(String phoneNumber, Date fromStart, Date toStart, Double fromDistance, Double toDistance, Double fromDuration, Double toDuration) {
        String whereString = "";
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            whereString += " user.phoneNumber = :PHONE_NUMBER";
        }
        if (fromStart != null) {
            whereString += " trip.tripStartDate >= :FROM_START";
        }
        if (toStart != null) {
            whereString += " trip.tripStartDate <= :TO_START";
        }
        if (fromDistance != null) {
            whereString += " trip.distance >= :FROM_DISTANCE";
        }
        if (toDistance != null) {
            whereString += " trip.distance <= :TO_DISTANCE";
        }
        if (fromDuration != null) {
            whereString += " trip.duration >= :FROM_DURATION";
        }
        if (toDuration != null) {
            whereString += " trip.duration <= :TO_DURATION";
        }
        return whereString;
    }

    private void setQueryParameter(Query query, String phoneNumber, Date fromStart, Date toStart, Double fromDistance, Double toDistance, Double fromDuration, Double toDuration) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            query.setParameter("PHONE_NUMBER", phoneNumber);
        }
        if (fromStart != null) {
            query.setParameter("FROM_START", fromStart);
        }
        if (toStart != null) {
            query.setParameter("TO_START", toStart);
        }
        if (fromDistance != null) {
            query.setParameter("FROM_DISTANCE", fromDistance);
        }
        if (toDistance != null) {
            query.setParameter("TO_DISTANCE", toDistance);
        }
        if (fromDuration != null) {
            query.setParameter("FROM_DURATION", fromDuration);
        }
        if (toDuration != null) {
            query.setParameter("TO_DURATION", toDuration);
        }
    }
}
