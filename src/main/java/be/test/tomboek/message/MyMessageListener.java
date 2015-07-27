package be.test.tomboek.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener{
    
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MyMessageListener.class);

    @Override
    public void onMessage(Message message) {
        LOGGER.debug("MyMessageListener: Message Received.");
        TextMessage msg;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                String txtMessage = msg.getText();
                LOGGER.debug("Message = {}", txtMessage);
                //TODO: Calling Processing Methods
            } else {
                LOGGER.debug("Message is not a text message - do nothing (type = {})", message.getClass().getName());
            }
        } catch (JMSException e) {
            LOGGER.error("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            LOGGER.error("Exception in onMessage():" + t.getMessage());
        }
    }
}
