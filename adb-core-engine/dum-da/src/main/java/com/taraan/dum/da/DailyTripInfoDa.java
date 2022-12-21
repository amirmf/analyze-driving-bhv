package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.DailyTripInfo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DailyTripInfoDa extends GenericDa<DailyTripInfo> {

    public List<DailyTripInfo> get(Long user, Date fromDate, Date toDate) {
        return this.getSession()
                .createNamedQuery("dailyTrip-by-rangeDate")
                .setParameter("FROM_DATE", fromDate)
                .setParameter("USER", user)
                .setParameter("TO_DATE", toDate).list();
    }
}
