package com.taraan.dum.analyzer.evaluate;

import com.taraan.dum.dto.analyze.AnalyzeTripRequest;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Component
public class FormulaEvaluator {
    private ParserContext context = ParserContext.create();
    private Hashtable<String, Serializable> cacheCompile = new Hashtable<String, Serializable>();

    @PostConstruct
    public void initContext() {
        this.context.addPackageImport("java.util");
        this.context.addPackageImport("java.lang");
        this.context.addPackageImport("java.math");
        this.context.addPackageImport("com.taraan.dum.analyzer.utils");
        this.context.addPackageImport("com.taraan.dum.dto.analyze");
        this.context.addPackageImport("com.taraan.dum.analyzer.query");
        this.context.addPackageImport("com.taraan.dum.model.hibernate");
    }

    private Object evaluateFormula(String expression, Map vars, ParserContext context) {
        Serializable compile = cacheCompile.get(expression);
        if (compile == null) {
            compile = MVEL.compileExpression(expression, context);
            cacheCompile.put(expression, compile);
        }
        return MVEL.executeExpression(compile, vars);
    }

    public Object evaluateFormula(AnalyzeTripRequest analyzeTripRequest, Map vars) {
        if (analyzeTripRequest.getTripId() != null)
            vars.put("tripId", analyzeTripRequest.getTripId());
        if (analyzeTripRequest.getDailyTripInfoId() != null)
            vars.put("dailyTripId", analyzeTripRequest.getDailyTripInfoId());
        vars.put("userId", analyzeTripRequest.getUserId());
        return this.evaluateFormula(analyzeTripRequest.getFormula(), vars, this.context.createSubcontext());
    }
}
