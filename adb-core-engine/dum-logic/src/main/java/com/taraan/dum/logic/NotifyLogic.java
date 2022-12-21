package com.taraan.dum.logic;

import com.adpdigital.www.services.messaging.JaxRpcMessagingServiceServiceLocator;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;

@Component
public class NotifyLogic {
    private static final String URL = "http://ws2.adpdigital.com/url/multisend?username=$UserName&password=$password&srcaddress=$FromNumber&dstaddress0=$ToNumber&body0=$Text&unicode0=2";
    //    private static final String URL = "http://www.sibsms.com/APISend.aspx?Username=$UserName&Password=$password&From=$FromNumber&To=$ToNumber&Text=$Text";
    @Value("${sms.username}")
    private String USERNAME;
    @Value("${sms.password}")
    private String PASSWORD;
    @Value("${sms.fromNumber}")
    private String FROM_NUMBER;
    JaxRpcMessagingServiceServiceLocator locator = new JaxRpcMessagingServiceServiceLocator();

    public void sendSms(String toNumber, String text) {
//        ClientConfig config = new ClientConfig();
        if (toNumber.startsWith("0"))
            toNumber = "98" + toNumber.substring(1);

        Short messageType = 1;
        Short unicode = 2;
        try {
            locator.getMessagingService().send(USERNAME, PASSWORD,
                    FROM_NUMBER, new String[]{toNumber}, (String) null, (String) null, (String[]) null, messageType, unicode, true, new GregorianCalendar(), text);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            text = new String(text.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        final String sendUrl = URL.replace("$UserName", USERNAME).replace("$password", PASSWORD)
//                .replace("$FromNumber", FROM_NUMBER).replace("$ToNumber", toNumber).replace("$Text", text);
//        Client client = ClientBuilder.newClient(config);
//        WebTarget target = client.target(sendUrl.replace(" ", "%20"));
//        Response response = target.request(MediaType.APPLICATION_JSON).get();
    }

}
