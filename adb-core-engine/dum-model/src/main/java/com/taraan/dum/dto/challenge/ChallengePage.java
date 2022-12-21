package com.taraan.dum.dto.challenge;

import java.util.ArrayList;
import java.util.List;

public class ChallengePage {
    private List<ChallengeItem> challengeItems = new ArrayList<>();

    public List<ChallengeItem> getChallengeItems() {
        return challengeItems;
    }

    public void setChallengeItems(List<ChallengeItem> challengeItems) {
        this.challengeItems = challengeItems;
    }
}
