package com.taraan.dum.dto.challenge;

import com.taraan.dum.model.hibernate.ChallengeMember;

import java.util.ArrayList;
import java.util.List;

public class AdminChallengeDto {
    private Long id;
    private String icon;
    private String name;
    private String description;
    private String image;
    private String prizeDescription;
    private String prizeName;
    private Long startScore;
    private Boolean display;
    private String prizeImage;
    private List<ChallengeMemberDto> members = new ArrayList<>();
    private Long analyzeFormula;
    private int dayLeft;
    private String startDate;
    private String endDate;

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

    public int getDayLeft() {
        return dayLeft;
    }

    public void setDayLeft(int dayLeft) {
        this.dayLeft = dayLeft;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Long getStartScore() {
        return startScore;
    }

    public void setStartScore(Long startScore) {
        this.startScore = startScore;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public List<ChallengeMemberDto> getMembers() {
        return members;
    }

    public void setMembers(List<ChallengeMemberDto> members) {
        this.members = members;
    }

    public Long getAnalyzeFormula() {
        return analyzeFormula;
    }

    public void setAnalyzeFormula(Long analyzeFormula) {
        this.analyzeFormula = analyzeFormula;
    }
}
