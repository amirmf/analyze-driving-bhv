package com.taraan.dum.model.hibernate;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Reward")
public class Reward implements BaseEntity, Effectivate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Reward_id_seq")
    @SequenceGenerator(sequenceName = "Reward_id_seq", allocationSize = 1, name = "Reward_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "Reward_Id")
    @OrderBy("Id")
    private List<RequiredBadge> requiredBadges = new ArrayList<>();
    @Column(name = "name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "RewardType")
    private RewardType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "RewardState")
    private RewardState rewardState;
    @Embedded
    private DateRange dateRange;
    @Column(name = "description")
    private String description;
    @Column(name = "RewardText")
    private String rewardText;
    @Column(name = "RewardImage")
    private String rewardImage;
    @Version
    private Timestamp version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public DateRange getDateRange() {
        return dateRange;
    }

    @Override
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public List<RequiredBadge> getRequiredBadges() {
        return requiredBadges;
    }

    public void setRequiredBadges(List<RequiredBadge> requiredBadges) {
        this.requiredBadges = requiredBadges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public RewardType getType() {
        return type;
    }

    public void setType(RewardType type) {
        this.type = type;
    }

    public String getRewardText() {
        return rewardText;
    }

    public void setRewardText(String rewardText) {
        this.rewardText = rewardText;
    }

    public String getRewardImage() {
        return rewardImage;
    }

    public void setRewardImage(String rewardImage) {
        this.rewardImage = rewardImage;
    }

    public RewardState getRewardState() {
        return rewardState;
    }

    public void setRewardState(RewardState rewardState) {
        this.rewardState = rewardState;
    }
}
