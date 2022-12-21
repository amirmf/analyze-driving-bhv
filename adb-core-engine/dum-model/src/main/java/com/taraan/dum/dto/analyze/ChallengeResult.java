package com.taraan.dum.dto.analyze;

import com.taraan.dum.model.hibernate.AnalyzeFormula;
import com.taraan.dum.model.hibernate.Challenge;

public class ChallengeResult {
    private AnalyzeFormula analyzeFormula;
    private Long score;

    public AnalyzeFormula getAnalyzeFormula() {
        return analyzeFormula;
    }

    public void setAnalyzeFormula(AnalyzeFormula analyzeFormula) {
        this.analyzeFormula = analyzeFormula;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
