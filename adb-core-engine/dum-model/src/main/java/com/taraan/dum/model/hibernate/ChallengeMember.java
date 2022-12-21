package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "ChallengeMember")
public class ChallengeMember implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challengeMember_id_seq")
    @SequenceGenerator(sequenceName = "challengeMember_id_seq", allocationSize = 1, name = "challengeMember_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "teamId")
    private ChallengeTeam team;
    @ManyToOne
    @JoinColumn(name = "challengeId")
    private Challenge challenge;
    @Column(name = "Score")
    private Long score;
    @Column(name = "joinDate")
    private Date joinDate;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
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

    public void setTeam(ChallengeTeam team) {
        this.team = team;
    }

    public ChallengeTeam getTeam() {
        return team;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }
}
