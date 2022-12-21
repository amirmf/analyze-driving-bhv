package com.taraan.dum.model.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "TripOfHour")
public class TripOfHour {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripOfHour_id_seq")
    @SequenceGenerator(sequenceName = "tripOfHour_id_seq", allocationSize = 1, name = "tripOfHour_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "TripHour", nullable = false)
    private int hour;
    @Column(name = "TripDistance", nullable = false)
    private double distance;
    @Column(name = "TripTime", nullable = false)
    private double time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public TripOfHour(int hour, double distance, double time) {
        this.hour = hour;
        this.distance = distance;
        this.time = time;
    }

    public TripOfHour() {
    }
}
