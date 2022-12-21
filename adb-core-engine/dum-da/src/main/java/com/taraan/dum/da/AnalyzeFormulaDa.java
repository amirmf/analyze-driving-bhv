package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.AnalyzeFormula;
import com.taraan.dum.model.hibernate.DailyTripInfo;
import com.taraan.dum.model.hibernate.FormulaEvent;
import com.taraan.dum.model.hibernate.FormulaType;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalyzeFormulaDa extends GenericDa<AnalyzeFormula> {

    public List<AnalyzeFormula> getByFormulaEvent(FormulaEvent formulaEvent) {
        return this.getSession().createNamedQuery("analyzeFormula-by-formulaEvent").setParameter("FORMULA_EVENT", formulaEvent).list();
    }

    public List<AnalyzeFormula> getActive(Long from, Long size) {
        String queryString = "select analyzeFormula from AnalyzeFormula analyzeFormula where analyzeFormula.dateRange.to is null";
        return this.getSession().createQuery(queryString).setMaxResults(size.intValue()).setFirstResult(from.intValue()).list();
    }

    public Long getCountActive() {
        String queryString = "select count(analyzeFormula.id) from AnalyzeFormula analyzeFormula where analyzeFormula.dateRange.to is null";
        return (Long) this.getSession().createQuery(queryString).getSingleResult();
    }

    public List<AnalyzeFormula> getActive(String name, String formulaEvent, String formulaType, Long from, Long size) {
        String queryString = "select analyzeFormula from AnalyzeFormula analyzeFormula where analyzeFormula.dateRange.to is null";
        String whereString = "";
        if (name != null && !name.isEmpty())
            whereString += " and analyzeFormula.name = :NAME";
        if (formulaEvent != null && !formulaEvent.isEmpty())
            whereString += " and analyzeFormula.formulaEvent = :FORMULA_EVENT";
        if (formulaType != null && !formulaType.isEmpty())
            whereString += " and analyzeFormula.formulaType = :FORMULA_TYPE";
        queryString += whereString;
        final Query query = this.getSession().createQuery(queryString);
        if (name != null && !name.isEmpty())
            query.setParameter("NAME", name);
        if (formulaEvent != null && !formulaEvent.isEmpty())
            query.setParameter("FORMULA_EVENT", FormulaEvent.valueOf(formulaEvent));
        if (formulaType != null && !formulaType.isEmpty())
            query.setParameter("FORMULA_TYPE", FormulaType.valueOf(formulaType));
        if (size != null)
            query.setMaxResults(size.intValue());
        if (from != null)
            query.setFirstResult(from.intValue());
        return query.list();

    }

    public Long getCountActive(String name, String formulaEvent, String formulaType) {
        String queryString = "select count(analyzeFormula.id) from AnalyzeFormula analyzeFormula where analyzeFormula.dateRange.to is null";
        String whereString = "";
        if (name != null && !name.isEmpty())
            whereString += " and analyzeFormula.name = :NAME";
        if (formulaEvent != null && !formulaEvent.isEmpty())
            whereString += " and analyzeFormula.formulaEvent = :FORMULA_EVENT";
        if (formulaType != null && !formulaType.isEmpty())
            whereString += " and analyzeFormula.formulaType = :FORMULA_TYPE";
        queryString += whereString;
        final Query query = this.getSession().createQuery(queryString);
        if (name != null && !name.isEmpty())
            query.setParameter("NAME", name);
        if (formulaEvent != null && !formulaEvent.isEmpty())
            query.setParameter("FORMULA_EVENT", FormulaEvent.valueOf(formulaEvent));
        if (formulaType != null && !formulaType.isEmpty())
            query.setParameter("FORMULA_TYPE", FormulaType.valueOf(formulaType));
        return (Long) query.getSingleResult();
    }
}
