package be.test.tomboek.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener{

    public void onMessage(Message message) {
        System.out.println("Message Received");
        TextMessage msg = null;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                String requestXml = msg.getText();
                System.out.println("Message = " + requestXml);
                //Calling Processing Methods
                //new JMSConnector().sendMessage(responseXml);
            } else {
                System.out.println("Message of wrong type: "
                        + message.getClass().getName());
            }
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" + t.getMessage());
        }
    }
}
