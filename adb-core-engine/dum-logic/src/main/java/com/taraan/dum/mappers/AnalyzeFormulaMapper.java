package com.taraan.dum.mappers;

import com.taraan.dum.dto.AnalyzeFormulaDto;
import com.taraan.dum.model.hibernate.AnalyzeFormula;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeFormulaMapper {
    public static AnalyzeFormulaDto getDto(AnalyzeFormula analyzeFormula) {
        AnalyzeFormulaDto analyzeFormulaDto = new AnalyzeFormulaDto();
        analyzeFormulaDto.setId(analyzeFormula.getId());
        analyzeFormulaDto.setFormula(analyzeFormula.getFormula());
        analyzeFormulaDto.setFormulaEvent(analyzeFormula.getFormulaEvent().name());
        analyzeFormulaDto.setName(analyzeFormula.getName());
        analyzeFormulaDto.setFormulaType(analyzeFormula.getFormulaType().name());
        analyzeFormulaDto.setOrder(analyzeFormula.getOrder());
        return analyzeFormulaDto;
    }

    public static List<AnalyzeFormulaDto> getDtos(List<AnalyzeFormula> analyzeFormulaes) {
        List<AnalyzeFormulaDto> result = new ArrayList<>();
        for (AnalyzeFormula analyzeFormula : analyzeFormulaes) {
            result.add(getDto(analyzeFormula));
        }
        return result;
    }


}
