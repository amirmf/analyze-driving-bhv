package com.taraan.dum.dto;

public class AnalyzeFormulaDto {
    private Long id;
    private String name;
    private String formula;
    private String formulaEvent;
    private Integer order;
    private String formulaType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormulaEvent() {
        return formulaEvent;
    }

    public void setFormulaEvent(String formulaEvent) {
        this.formulaEvent = formulaEvent;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }

    @Override
    public String toString() {
        return "AnalyzeFormulaDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", formula='" + formula + '\'' +
                ", formulaEvent='" + formulaEvent + '\'' +
                ", order=" + order +
                ", formulaType='" + formulaType + '\'' +
                '}';
    }
}
