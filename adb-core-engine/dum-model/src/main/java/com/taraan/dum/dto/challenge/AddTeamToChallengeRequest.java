package com.taraan.dum.dto.challenge;

import java.util.List;

public class AddTeamToChallengeRequest {
    private Long id;
    private ChallengeTeamDto challengeTeam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChallengeTeamDto getChallengeTeam() {
        return challengeTeam;
    }

    public void setChallengeTeam(ChallengeTeamDto challengeTeam) {
        this.challengeTeam = challengeTeam;
    }
}
