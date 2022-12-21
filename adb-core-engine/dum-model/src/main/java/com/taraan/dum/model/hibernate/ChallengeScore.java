package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "ChallengeScore")
public class ChallengeScore implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challengeScore_id_seq")
    @SequenceGenerator(sequenceName = "ChallengeScore_id_seq", allocationSize = 1, name = "challengeScore_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    private ChallengeMember challengeMember;
    @ManyToOne
    private Challenge challenge;
    @Column(name = "Score")
    private Long score;
    @Column(name = "scoreDate")
    private Date date;
    @ManyToOne
    private User user;
    @ManyToOne
    private Trip trip;
    @ManyToOne
    private DailyTripInfo dailyTripInfo;
    @Version
    private Timestamp version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ChallengeMember getChallengeMember() {
        return challengeMember;
    }

    public void setChallengeMember(ChallengeMember challengeMember) {
        this.challengeMember = challengeMember;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setDailyTripInfo(DailyTripInfo dailyTripInfo) {
        this.dailyTripInfo = dailyTripInfo;
    }

    public DailyTripInfo getDailyTripInfo() {
        return dailyTripInfo;
    }
}
