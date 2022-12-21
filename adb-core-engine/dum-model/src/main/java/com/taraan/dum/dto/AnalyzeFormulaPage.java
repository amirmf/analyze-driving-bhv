package com.taraan.dum.dto;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeFormulaPage {
    private List<AnalyzeFormulaDto> analyzeFormulas= new ArrayList<>();
    private Long count;

    public AnalyzeFormulaPage() {
    }

    public AnalyzeFormulaPage(List<AnalyzeFormulaDto> analyzeFormulas, Long count) {
        this.analyzeFormulas = analyzeFormulas;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<AnalyzeFormulaDto> getAnalyzeFormulas() {
        return analyzeFormulas;
    }

    public void setAnalyzeFormulas(List<AnalyzeFormulaDto> analyzeFormulas) {
        this.analyzeFormulas = analyzeFormulas;
    }
}
