package be.test.tomboek.message;

import be.test.tomboek.listener.MyServletContextListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSConnector {

    static Context context;
    static QueueConnectionFactory factory;
    
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MyServletContextListener.class);

    static {
        LOGGER.debug("JMS Connector: static initialization");
        try {
            context = new InitialContext();
            factory = (QueueConnectionFactory) context.lookup("java:comp/env/jms/ConnectionFactory");
            LOGGER.debug("JMS Connector: Context and Factory available");
        } catch (NamingException ex) {
            Logger.getLogger(JMSConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void sendMessage(String responseMessage) throws Exception {      

        Connection connection = factory.createConnection();
        Queue queue = (javax.jms.Queue) context.lookup("java:comp/env/jms/Queue");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);
        
        TextMessage msg = session.createTextMessage();
        msg.setText(responseMessage);
        LOGGER.debug("JMS Connector: send message = {}", responseMessage );

        producer.send(msg);
        connection.close();
    }

    public static void startListening() {
        LOGGER.debug("JMS Connector: START LISTENING");

        try {
            Connection connection = factory.createConnection();
            Queue queue = (javax.jms.Queue) context.lookup("java:comp/env/jms/Queue");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer consumer = session.createConsumer(queue);

            LOGGER.debug("JMS Connector: CREATE Message Listener");
            MyMessageListener messageListener = new MyMessageListener();
            consumer.setMessageListener(messageListener);
            connection.start();

            // Start connection or nothing will happen!!!
            connection.start();
        } catch (Exception ex) {
            System.out.println("JMS REMOTE PRODUCER: EXCEPTION");
        }

        System.out.println("JMS REMOTE CONSUMER: finished");
    }

}
