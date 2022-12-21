package com.taraan.dum.analyzer.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Amir Mansouri Fard (amf.fard@gmail.com) on 8/22/2018
 */
@Aspect
@Service
public class AuditLog {
    private Log log = LogFactory.getLog(this.getClass());
    private List<String> logTypes;

    public void setLogTypes(List<String> logTypes) {
        this.logTypes = logTypes;
    }

    @Around("execution(* com.taraan.dum.analyzer.assembler.AnalyzeAssembler.*(..))")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        long duration;

        String methodName = pjp.getSignature().getName();


        Object[] args = pjp.getArgs();
        String facadeName = pjp.getSignature().getDeclaringTypeName();
        StringBuilder argsString = new StringBuilder();
        Object retVal;
        if (args.length > 0) {
            for (Object arg : args) {
                if (arg != null)
                    argsString.append(",").append(arg.getClass().getSimpleName()).append(":").append(arg.toString());
                else
                    argsString.append(",null");
            }
            argsString = new StringBuilder(argsString.substring(1));
        }
        String userIdentifier = "";
        String version = "";
        String host = "";
        try {
        } catch (SecurityException e) {
            log.info(e.getMessage());
        }
        try {
            retVal = pjp.proceed();
            String retValString = "";
            if (retVal != null) {
                retValString += ":" + retVal.toString();
            }
            duration = System.currentTimeMillis() - start;

            if (this.writeLog(methodName)) {
                log.info("duration : [" + duration + " milliseconds] user: '" + userIdentifier + "' host : '" + host + "' appVersion : '" + version + "' [" + retValString + "] " + facadeName + "." + methodName + "(" + argsString + ")");
                if (duration > 10000)
                    log.warn("duration : [" + duration + " milliseconds] user: '" + userIdentifier + "' host : '" + host + "' appVersion : '" + version + "' [" + retValString + "] " + facadeName + "." + methodName + "(" + argsString + ")");
            }

        } catch (Exception ex) {
            duration = System.currentTimeMillis() - start;
            log.error("duration : [" + duration + " milliseconds] user: '" + userIdentifier + "' host : '" + host + "' appVersion : '" + version + " [" + ex.getClass().getSimpleName() + ":" + ex.getMessage() + "] " + facadeName + "." + methodName + "(" + argsString + ")", ex);
            throw ex;
        }
        return retVal;
    }

    private boolean writeLog(String methodName) {
        if (this.logTypes != null) {
            for (String logType : logTypes) {
                if (methodName.startsWith(logType)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }
}
