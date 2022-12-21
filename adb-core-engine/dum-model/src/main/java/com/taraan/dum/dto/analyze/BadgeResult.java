package com.taraan.dum.dto.analyze;

import com.taraan.dum.model.hibernate.AnalyzeFormula;

public class BadgeResult {
    private AnalyzeFormula analyzeFormula;
    private String code;

    public void setAnalyzeFormula(AnalyzeFormula analyzeFormula) {
        this.analyzeFormula = analyzeFormula;
    }

    public AnalyzeFormula getAnalyzeFormula() {
        return analyzeFormula;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BadgeResult{" +
                "analyzeFormula=" + analyzeFormula +
                ", code='" + code + '\'' +
                '}';
    }
}
