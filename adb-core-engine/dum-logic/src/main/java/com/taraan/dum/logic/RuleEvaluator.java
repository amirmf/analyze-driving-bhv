package com.taraan.dum.logic;

import com.taraan.dum.dto.analyze.AnalyzeTripRequest;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

@Component
public class RuleEvaluator {
    private ParserContext context = ParserContext.create();
    private Hashtable<String, Serializable> cacheCompile = new Hashtable<String, Serializable>();

    @PostConstruct
    public void initContext() {
        this.context.addPackageImport("java.util");
        this.context.addPackageImport("java.lang");
        this.context.addPackageImport("java.math");
        this.context.addPackageImport("com.taraan.dum.model.hibernate");
    }

    public Boolean evaluateFormula(String expression, Map vars) {
        ParserContext context = this.context.createSubcontext();
        Serializable compile = cacheCompile.get(expression);
        if (compile == null) {
            compile = MVEL.compileExpression(expression, context);
            cacheCompile.put(expression, compile);
        }
        return (Boolean) MVEL.executeExpression(compile, vars);
    }


}
