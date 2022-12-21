package com.taraan.dum.statisticgenerator.assembler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class MessageProducer {
    private Log log = LogFactory.getLog(this.getClass());
    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(Object o) {
        send((Serializable) o);
    }

    public void send(final Serializable o) {
        if (jmsTemplate == null) {
            log.info("jmsTemplate is null");
            return;
        }
        jmsTemplate.send(session -> session.createObjectMessage(o));
    }
}
