package com.taraan.dum.dto.challenge;

import java.util.ArrayList;
import java.util.List;

public class AdminChallengePage {
    private List<AdminChallengeItem> challengeItems = new ArrayList<>();
    private Long count;
    public List<AdminChallengeItem> getChallengeItems() {
        return challengeItems;
    }

    public void setChallengeItems(List<AdminChallengeItem> challengeItems) {
        this.challengeItems = challengeItems;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
