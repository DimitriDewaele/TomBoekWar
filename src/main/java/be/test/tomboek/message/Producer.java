package be.test.tomboek.message;

import javax.jms.JMSException;

public class Producer {
    
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Producer.class);

    public static void Produce(String message, Long count) throws JMSException {
        LOGGER.debug("JMS PRODUCER");

        try {
            int total = 0;

            for (int i = 0; i < count; i++) {
                String messageText = "#" + (i + 1) + ":" + message;
                JMSConnector.sendMessage(messageText);
                total += 1;
            }
            LOGGER.debug("JMS PRODUCER - total messages sent: {}", total);

        } catch (Exception ex) {
            LOGGER.error("JMS PRODUCER ERROR: {}", ex.toString());
        }
    }
}
