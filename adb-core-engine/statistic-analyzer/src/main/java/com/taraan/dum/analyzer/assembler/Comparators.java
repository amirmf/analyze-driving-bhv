package com.taraan.dum.analyzer.assembler;

import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import com.taraan.dum.model.hibernate.AnalyzeFormula;
import com.taraan.dum.model.hibernate.FormulaType;

import java.util.Comparator;


public class Comparators {
    private Comparators() {
    }
    public final static Comparator<AnalyzeFormula> ANALYZE_FORMULA_COMPARATOR =
            (AnalyzeFormula o1, AnalyzeFormula o2) -> {
                if (o1.getFormulaType().equals(FormulaType.SCORE) && o2.getFormulaType().equals(FormulaType.BADGE)) {
                    return 1;
                }
                if (o1.getFormulaType().equals(FormulaType.BADGE) && o2.getFormulaType().equals(FormulaType.SCORE)) {
                    return -1;
                }
                if (o1.getFormulaType().equals(FormulaType.SCORE) && o2.getFormulaType().equals(FormulaType.SCORE)) {
                    if (o1.getOrder() == null && o2.getOrder() == null) {
                        return 0;
                    }
                    if (o2.getOrder() == null) {
                        return 1;
                    }
                    if (o1.getOrder() == null) {
                        return -1;
                    }
                }
                if (o1.getFormulaType().equals(FormulaType.BADGE) && o2.getFormulaType().equals(FormulaType.BADGE)) {
                    if (o1.getOrder() == null && o2.getOrder() == null) {
                        return 0;
                    }
                    if (o2.getOrder() == null) {
                        return 1;
                    }
                    if (o1.getOrder() == null) {
                        return -1;
                    }
                }
                return 0;
            };


}
