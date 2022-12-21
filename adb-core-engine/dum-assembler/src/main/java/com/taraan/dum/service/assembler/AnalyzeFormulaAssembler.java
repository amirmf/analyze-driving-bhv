package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.AnalyzeFormulaDto;
import com.taraan.dum.dto.AnalyzeFormulaPage;
import com.taraan.dum.logic.AnalyzeFormulaLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class AnalyzeFormulaAssembler {
    @Autowired
    private AnalyzeFormulaLogic analyzeFormulaLogic;

    public AnalyzeFormulaDto createAnalyzeFormula(AnalyzeFormulaDto analyzeFormulaDto) {
        return analyzeFormulaLogic.createAnalyzeFormula(analyzeFormulaDto);
    }

    public AnalyzeFormulaDto getAnalyzeFormula(Long id) {
        return analyzeFormulaLogic.getAnalyzeFormula(id);
    }

    public AnalyzeFormulaDto updateAnalyzeFormula(AnalyzeFormulaDto analyzeFormulaDto) {
        return analyzeFormulaLogic.updateAnalyzeFormula(analyzeFormulaDto);
    }

    public void deleteAnalyzeFormula(Long id) {
        analyzeFormulaLogic.deleteAnalyzeFormula(id);
    }

    public List<AnalyzeFormulaDto> getAnalyzeFormulas() {
        return analyzeFormulaLogic.getAnalyzeFormulas();
    }

    public AnalyzeFormulaPage getAnalyzeFormulas(String name, String formulaEvent, String formulaType, Long from, Long size) {
        return analyzeFormulaLogic.getAnalyzeFormulas(name, formulaEvent, formulaType, from, size);
    }
}
