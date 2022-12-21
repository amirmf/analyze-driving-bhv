package com.taraan.dum.dto.analyze;

public class AnalyzeTripResult {
    private String type;
    private BadgeResult badgeResult;
    private ScoreResult scoreResult;
    private ChallengeResult challengeResult;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BadgeResult getBadgeResult() {
        return badgeResult;
    }

    public void setBadgeResult(BadgeResult badgeResult) {
        this.badgeResult = badgeResult;
    }

    public ScoreResult getScoreResult() {
        return scoreResult;
    }

    public void setScoreResult(ScoreResult scoreResult) {
        this.scoreResult = scoreResult;
    }

    public AnalyzeTripResult(String type) {
        this.type = type;
    }

    public AnalyzeTripResult() {
    }

    public ChallengeResult getChallengeResult() {
        return challengeResult;
    }

    public void setChallengeResult(ChallengeResult challengeResult) {
        this.challengeResult = challengeResult;
    }

    @Override
    public String toString() {
        return "AnalyzeTripResult{" +
                "type='" + type + '\'' +
                ", badgeResult=" + badgeResult +
                ", scoreResult=" + scoreResult +
                ", challengeResult=" + challengeResult +
                '}';
    }
}
