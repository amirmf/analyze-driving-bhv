package com.taraan.dum.model.hibernate;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "UserReward")
public class UserReward implements BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserReward_id_seq")
    @SequenceGenerator(sequenceName = "UserReward_id_seq", allocationSize = 1, name = "UserReward_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Reward reward;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "UserReward_Id")
    @OrderBy("Id")
    private List<UserBadge> userBadges = new ArrayList<>();
    @Column(name = "userRewardDate")
    private Date date;
    @Enumerated
    @Column(name = "userRewardType")
    private UserRewardType userRewardType;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public List<UserBadge> getUserBadges() {
        return userBadges;
    }

    public void setUserBadges(List<UserBadge> userBadges) {
        this.userBadges = userBadges;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserRewardType getUserRewardType() {
        return userRewardType;
    }

    public void setUserRewardType(UserRewardType userRewardType) {
        this.userRewardType = userRewardType;
    }
}
