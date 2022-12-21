package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "AnalyzeFormula")
@NamedQueries(
        @NamedQuery(name = "analyzeFormula-by-formulaEvent", query = "select analyzeFormula from AnalyzeFormula analyzeFormula where analyzeFormula.formulaEvent=:FORMULA_EVENT and analyzeFormula.dateRange.to is null ")
)
public class AnalyzeFormula implements BaseEntity,Effectivate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "analyzeformula_id_seq")
    @SequenceGenerator(sequenceName = "analyzeformula_id_seq", allocationSize = 1, name = "analyzeformula_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "formula")
    private String formula;
    @Enumerated(EnumType.STRING)
    @Column(name = "formulaEvent")
    private FormulaEvent formulaEvent;
    @Enumerated(EnumType.STRING)
    @Column(name = "formulaType")
    private FormulaType formulaType;
    @Embedded
    private DateRange dateRange;
    @Column(name = "formulaOrder")
    private Integer order;
    @Version
    private Timestamp version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public FormulaEvent getFormulaEvent() {
        return formulaEvent;
    }

    public void setFormulaEvent(FormulaEvent formulaEvent) {
        this.formulaEvent = formulaEvent;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    @Override
    public DateRange getDateRange() {
        return dateRange;
    }

    @Override
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public FormulaType getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(FormulaType formulaType) {
        this.formulaType = formulaType;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
