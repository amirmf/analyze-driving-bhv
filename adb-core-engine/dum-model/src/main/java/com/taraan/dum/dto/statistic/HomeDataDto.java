package com.taraan.dum.dto.statistic;

import com.taraan.dum.dto.challenge.ChallengeDto;
import com.taraan.dum.dto.challenge.ChallengeItem;
import com.taraan.dum.dto.news.NewsDto;
import com.taraan.dum.model.hibernate.Reward;

import java.util.ArrayList;
import java.util.List;

public class HomeDataDto {
    private Long score;
    private Long tripCount;
    private Long averageSpeed;
    private Long sumTimeDuration;
    private Long unreadMessageCount;
    private double sumDistance;
    private double weekPercentage;
    private double weekendPercentage;
    private double urbanPercentage;
    private double highwayPercentage;
    private double otherPercentage;
    private List<RewardDto> rewards = new ArrayList<>();
    private List<ChallengeItem> challengeItems = new ArrayList<>();
    private NewsDto news;

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getTripCount() {
        return tripCount;
    }

    public void setTripCount(Long tripCount) {
        this.tripCount = tripCount;
    }

    public Long getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Long averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Long getSumTimeDuration() {
        return sumTimeDuration;
    }

    public void setSumTimeDuration(Long sumTimeDuration) {
        this.sumTimeDuration = sumTimeDuration;
    }

    public double getWeekPercentage() {
        return weekPercentage;
    }

    public void setWeekPercentage(double weekPercentage) {
        this.weekPercentage = weekPercentage;
    }

    public double getWeekendPercentage() {
        return weekendPercentage;
    }

    public void setWeekendPercentage(double weekendPercentage) {
        this.weekendPercentage = weekendPercentage;
    }

    public double getUrbanPercentage() {
        return urbanPercentage;
    }

    public void setUrbanPercentage(double urbanPercentage) {
        this.urbanPercentage = urbanPercentage;
    }

    public double getHighwayPercentage() {
        return highwayPercentage;
    }

    public void setHighwayPercentage(double highwayPercentage) {
        this.highwayPercentage = highwayPercentage;
    }

    public double getOtherPercentage() {
        return otherPercentage;
    }

    public void setOtherPercentage(double otherPercentage) {
        this.otherPercentage = otherPercentage;
    }

    public List<RewardDto> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardDto> rewards) {
        this.rewards = rewards;
    }

    public NewsDto getNews() {
        return news;
    }

    public void setNews(NewsDto news) {
        this.news = news;
    }

    public double getSumDistance() {
        return sumDistance;
    }

    public void setSumDistance(double sumDistance) {
        this.sumDistance = sumDistance;
    }

    public Long getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(Long unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

    public List<ChallengeItem> getChallengeItems() {
        return challengeItems;
    }

    public void setChallengeItems(List<ChallengeItem> challengeItems) {
        this.challengeItems = challengeItems;
    }

    @Override
    public String toString() {
        return "HomeDataDto{" +
                "score=" + score +
                ", tripCount=" + tripCount +
                ", averageSpeed=" + averageSpeed +
                ", sumTimeDuration=" + sumTimeDuration +
                ", sumDistance=" + sumDistance +
                ", weekPercentage=" + weekPercentage +
                ", weekendPercentage=" + weekendPercentage +
                ", urbanPercentage=" + urbanPercentage +
                ", highwayPercentage=" + highwayPercentage +
                ", otherPercentage=" + otherPercentage +
                ", rewards=" + rewards +
                ", news=" + news +
                '}';
    }

}
