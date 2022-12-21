package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "UserScore")
public class UserScore implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserScore_id_seq")
    @SequenceGenerator(sequenceName = "UserScore_id_seq", allocationSize = 1, name = "UserScore_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    private User user;
    @Column(name = "score")
    private Long score;
    @ManyToOne
    private Trip trip;
    @ManyToOne
    private DailyTripInfo dailyTripInfo;
    @ManyToOne
    private AnalyzeFormula analyzeFormula;
    @Column(name = "UserScoreDate")
    private Date date;
    @Version
    private Timestamp version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public AnalyzeFormula getAnalyzeFormula() {
        return analyzeFormula;
    }

    public void setAnalyzeFormula(AnalyzeFormula analyzeFormula) {
        this.analyzeFormula = analyzeFormula;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public void setDailyTripInfo(DailyTripInfo dailyTripInfo) {
        this.dailyTripInfo = dailyTripInfo;
    }

    public DailyTripInfo getDailyTripInfo() {
        return dailyTripInfo;
    }
}
