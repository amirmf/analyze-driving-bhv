package com.taraan.dum.dto.analyze;

import com.taraan.dum.model.hibernate.AnalyzeFormula;

public class ScoreResult {
    private AnalyzeFormula analyzeFormula;
    private Long score;

    public void setAnalyzeFormula(AnalyzeFormula analyzeFormula) {
        this.analyzeFormula = analyzeFormula;
    }

    public AnalyzeFormula getAnalyzeFormula() {
        return analyzeFormula;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ScoreResult{" +
                "analyzeFormula=" + analyzeFormula +
                ", score=" + score +
                '}';
    }
}
