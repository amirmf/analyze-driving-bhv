package com.taraan.dum.dto.challenge;

public class RemoveTeamFromChallengeRequest {
    private Long id;
    private Long challengeTeamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChallengeTeamId() {
        return challengeTeamId;
    }

    public void setChallengeTeamId(Long challengeTeamId) {
        this.challengeTeamId = challengeTeamId;
    }
}
