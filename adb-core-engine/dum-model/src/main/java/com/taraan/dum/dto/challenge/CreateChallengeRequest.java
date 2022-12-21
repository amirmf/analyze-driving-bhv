package com.taraan.dum.dto.challenge;

import java.util.List;

public class CreateChallengeRequest {
    private Long id;
    private String name;
    private String description;
    private String prizeName;
    private String prizeDescription;
    private boolean isDisplay;
    private long startScore;
    private String startDate;
    private String endDate;
    private Long analyzeFormula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean display) {
        isDisplay = display;
    }

    public long getStartScore() {
        return startScore;
    }

    public void setStartScore(long startScore) {
        this.startScore = startScore;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getAnalyzeFormula() {
        return analyzeFormula;
    }

    public void setAnalyzeFormula(Long analyzeFormula) {
        this.analyzeFormula = analyzeFormula;
    }
}
