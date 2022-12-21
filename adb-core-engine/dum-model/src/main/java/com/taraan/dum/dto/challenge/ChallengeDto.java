package com.taraan.dum.dto.challenge;

import java.util.ArrayList;
import java.util.List;

public class ChallengeDto {
    private String name;
    private String description;
    private boolean active;
    private int teamCount;
    private Long participantCount;
    private int dayLeft;
    private List<ChallengeMemberDto> challengeTopMembers = new ArrayList<>();
    private List<ChallengeMemberDto> teamTopMembers = new ArrayList<>();
    private List<ChallengeMemberDto> winners = new ArrayList<>();
    private String prize;
    private String prizeDescription;
    private String prizeImage;
    private String icon;
    private String image;
    private String startDate;
    private String endDate;
    private List<ChallengeMemberDto> scoreChallengeMembers = new ArrayList<>();
    private List<ChallengeMemberDto> allChallengeMembers = new ArrayList<>();

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public Long getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Long participantCount) {
        this.participantCount = participantCount;
    }

    public int getDayLeft() {
        return dayLeft;
    }

    public void setDayLeft(int dayLeft) {
        this.dayLeft = dayLeft;
    }

    public List<ChallengeMemberDto> getChallengeTopMembers() {
        return challengeTopMembers;
    }

    public void setChallengeTopMembers(List<ChallengeMemberDto> challengeTopMembers) {
        this.challengeTopMembers = challengeTopMembers;
    }

    public List<ChallengeMemberDto> getAllChallengeMembers() {
        return allChallengeMembers;
    }

    public void setAllChallengeMembers(List<ChallengeMemberDto> allChallengeMembers) {
        this.allChallengeMembers = allChallengeMembers;
    }

    public List<ChallengeMemberDto> getTeamTopMembers() {
        return teamTopMembers;
    }

    public void setTeamTopMembers(List<ChallengeMemberDto> teamTopMembers) {
        this.teamTopMembers = teamTopMembers;
    }

    public List<ChallengeMemberDto> getWinners() {
        return winners;
    }

    public void setWinners(List<ChallengeMemberDto> winners) {
        this.winners = winners;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    public String getPrizeImage() {
        return prizeImage;
    }

    public void setPrizeImage(String prizeImage) {
        this.prizeImage = prizeImage;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setScoreChallengeMembers(List<ChallengeMemberDto> scoreChallengeMembers) {
        this.scoreChallengeMembers = scoreChallengeMembers;
    }

    public List<ChallengeMemberDto> getScoreChallengeMembers() {
        return scoreChallengeMembers;
    }
}
