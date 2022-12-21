package com.taraan.dum.statisticgenerator;

import com.taraan.dum.audit.mongodb.TripTrackerInfo;

import java.util.Comparator;


public class Comparators {
    private Comparators() {
    }

    public final static Comparator<TripTrackerInfo> TRIP_TRACKER_INFO_COMPARATOR =
            (TripTrackerInfo o1, TripTrackerInfo o2) -> {
                if (o1.getOrder() == o2.getOrder())
                    return (o1.getUserPostDateTimeStamp() <= o2.getUserPostDateTimeStamp()) ? 1 : -1;
                return o1.getOrder() > o2.getOrder() ? 1 : -1;
            };

}
