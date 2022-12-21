package com.taraan.dum.analyzer.listener;

import com.taraan.dum.analyzer.assembler.AnalyzeAssembler;
import com.taraan.dum.dto.analyze.JmsAnalyzeRequest;
import com.taraan.dum.dto.analyze.JmsDailyAnalyzeRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class JmsMessageListener implements MessageListener {
    private AnalyzeAssembler analyzeAssembler;

    public void setAnalyzeAssembler(AnalyzeAssembler analyzeAssembler) {
        this.analyzeAssembler = analyzeAssembler;
    }

    private Log log = LogFactory.getLog(this.getClass());

    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Thread.sleep(1000);
            if (objectMessage.getObject() instanceof JmsAnalyzeRequest) {
                JmsAnalyzeRequest jmsAnalyzeRequest = (JmsAnalyzeRequest) objectMessage.getObject();
                analyzeAssembler.analyzeData(jmsAnalyzeRequest);
            } else if (objectMessage.getObject() instanceof JmsDailyAnalyzeRequest){
                JmsDailyAnalyzeRequest jmsAnalyzeRequest = (JmsDailyAnalyzeRequest) objectMessage.getObject();
                analyzeAssembler.analyzeDailyData(jmsAnalyzeRequest);
            }
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
