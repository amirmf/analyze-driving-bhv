package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "UserBadge")
public class UserBadge implements BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserBadge_id_seq")
    @SequenceGenerator(sequenceName = "UserBadge_id_seq", allocationSize = 1, name = "UserBadge_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Badge badge;
    @ManyToOne
    private DailyTripInfo dailyTripInfo;
    @ManyToOne
    private AnalyzeFormula analyzeFormula;
    @ManyToOne
    private Trip trip;
    @Column(name = "UserBadgeDate")
    private Date date;
    @Version
    private Timestamp version;
    @Enumerated
    @Column(name = "userBadgeState")
    private UserBadgeState userBadgeState;

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

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
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

    public UserBadgeState getUserBadgeState() {
        return userBadgeState;
    }

    public void setUserBadgeState(UserBadgeState userBadgeState) {
        this.userBadgeState = userBadgeState;
    }

    public void setDailyTripInfo(DailyTripInfo dailyTripInfo) {
        this.dailyTripInfo = dailyTripInfo;
    }

    public DailyTripInfo getDailyTripInfo() {
        return dailyTripInfo;
    }
}
