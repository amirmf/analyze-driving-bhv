package com.taraan.dum.logic;

import com.taraan.dum.da.AnalyzeFormulaDa;
import com.taraan.dum.dto.AnalyzeFormulaDto;
import com.taraan.dum.dto.AnalyzeFormulaPage;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.mappers.AnalyzeFormulaMapper;
import com.taraan.dum.model.hibernate.AnalyzeFormula;
import com.taraan.dum.model.hibernate.DateRange;
import com.taraan.dum.model.hibernate.FormulaEvent;
import com.taraan.dum.model.hibernate.FormulaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AnalyzeFormulaLogic {
    @Autowired
    private AnalyzeFormulaDa analyzeFormulaDa;

    public AnalyzeFormulaLogic() {
        System.out.println();
    }

    public AnalyzeFormulaDto createAnalyzeFormula(AnalyzeFormulaDto analyzeFormulaDto) {
        if (analyzeFormulaDto.getFormula() == null || analyzeFormulaDto.getFormula().isEmpty())
            throw new NullPointerException("Formula");
        if (analyzeFormulaDto.getFormulaEvent() == null || analyzeFormulaDto.getFormulaEvent().isEmpty())
            throw new NullPointerException("FormulaEvent");
        if (analyzeFormulaDto.getFormulaType() == null || analyzeFormulaDto.getFormulaType().isEmpty())
            throw new NullPointerException("FormulaType");
        AnalyzeFormula analyzeFormula = new AnalyzeFormula();
        analyzeFormula.setFormula(analyzeFormulaDto.getFormula());
        analyzeFormula.setName(analyzeFormulaDto.getName());
        analyzeFormula.setFormulaEvent(FormulaEvent.valueOf(analyzeFormulaDto.getFormulaEvent()));
        analyzeFormula.setFormulaType(FormulaType.valueOf(analyzeFormulaDto.getFormulaType()));
        analyzeFormula.setDateRange(new DateRange(new Date()));
        analyzeFormula.setOrder(analyzeFormulaDto.getOrder());
        return AnalyzeFormulaMapper.getDto(analyzeFormulaDa.save(analyzeFormula));
    }

    public AnalyzeFormulaDto updateAnalyzeFormula(AnalyzeFormulaDto analyzeFormulaDto) {
        if (analyzeFormulaDto.getFormula() == null || analyzeFormulaDto.getFormula().isEmpty())
            throw new NullPointerException("Formula");
        if (analyzeFormulaDto.getFormulaEvent() == null || analyzeFormulaDto.getFormulaEvent().isEmpty())
            throw new NullPointerException("FormulaEvent");
        if (analyzeFormulaDto.getFormulaType() == null || analyzeFormulaDto.getFormulaType().isEmpty())
            throw new NullPointerException("FormulaType");
        AnalyzeFormula analyzeFormula = analyzeFormulaDa.get(analyzeFormulaDto.getId());
        if (analyzeFormula == null)
            throw new EntityDoesNotExistException("AnalyzeFormula");
        analyzeFormula.setFormula(analyzeFormulaDto.getFormula());
        analyzeFormula.setName(analyzeFormulaDto.getName());
        analyzeFormula.setFormulaEvent(FormulaEvent.valueOf(analyzeFormulaDto.getFormulaEvent()));
        analyzeFormula.setFormulaType(FormulaType.valueOf(analyzeFormulaDto.getFormulaType()));
        analyzeFormula.setOrder(analyzeFormulaDto.getOrder());
        return AnalyzeFormulaMapper.getDto(analyzeFormulaDa.update(analyzeFormula));
    }

    public void deleteAnalyzeFormula(Long id) {
        AnalyzeFormula analyzeFormula = analyzeFormulaDa.get(id);
        analyzeFormula.getDateRange().setTo(new Date());
        analyzeFormulaDa.update(analyzeFormula);
    }

    public AnalyzeFormulaDto getAnalyzeFormula(Long id) {
        AnalyzeFormula analyzeFormula = analyzeFormulaDa.get(id);
        return AnalyzeFormulaMapper.getDto(analyzeFormula);
    }

    public List<AnalyzeFormulaDto> getAnalyzeFormulas() {
        List<AnalyzeFormula> analyzeFormulas = analyzeFormulaDa.get();
        return AnalyzeFormulaMapper.getDtos(analyzeFormulas);
    }

    public AnalyzeFormulaPage getAnalyzeFormulas(String name, String formulaEvent, String formulaType, Long from, Long size) {
        List<AnalyzeFormula> analyzeFormulas = analyzeFormulaDa.getActive(name, formulaEvent, formulaType, from, size);
        Long count = analyzeFormulaDa.getCountActive(name, formulaEvent, formulaType);
        return new AnalyzeFormulaPage(AnalyzeFormulaMapper.getDtos(analyzeFormulas), count);
    }

}
