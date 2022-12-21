package com.taraan.dum.da;


import com.taraan.dum.model.hibernate.UserScore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserScoreDa extends GenericDa<UserScore> {
    public Double getAverageScore(Long userId) {
        final Double result = (Double) this.getSession().createQuery("select avg(userScore.score) from UserScore userScore where userScore.user.id=:USER_ID").setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return result;
        else
            return 0D;
    }

    public Double getSumScore(Long userId) {
        final Long result = (Long) this.getSession().createQuery("select sum(userScore.score) from UserScore userScore where userScore.user.id=:USER_ID").setParameter("USER_ID", userId).uniqueResult();
        if (result != null)
            return Double.valueOf(result);
        else
            return 0D;
    }

    public List<UserScore> getByTrip(Long tripId) {
        return this.getSession().createQuery("select userScore from UserScore userScore where userScore.trip.id=:TRIP_ID").setParameter("TRIP_ID", tripId).list();
    }
}
