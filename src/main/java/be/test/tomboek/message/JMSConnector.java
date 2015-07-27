package be.test.tomboek.message;

import be.test.tomboek.listener.MyServletContextListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSConnector {

//    static Properties properties = new Properties();
    static Context context;
    static QueueConnectionFactory factory;
    
    private static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MyServletContextListener.class);

    static {
        System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 1");
        try {
            context = new InitialContext();
            System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 2");
            //QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("jms/ConnectionFactory");
            factory = (QueueConnectionFactory) context.lookup("java:comp/env/jms/ConnectionFactory");
            System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 3");
        } catch (NamingException ex) {
            Logger.getLogger(JMSConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendMessage(String responseMessage) throws Exception {

        Connection connection = factory.createConnection();
        System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 4");
        //Queue queue = (javax.jms.Queue) ctx.lookup("jms/FooQueue");
        Queue queue = (javax.jms.Queue) context.lookup("java:comp/env/jms/Queue");
        System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 5");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 6");
        MessageProducer producer = session.createProducer(queue);

        TextMessage msg = session.createTextMessage();
        msg.setText(responseMessage);
        System.out.println("Sending the message: " + msg.getText());

        producer.send(msg);
        connection.close();
    }

    public void startListening() {

        try {
            Connection connection = factory.createConnection();
            Queue queue = (javax.jms.Queue) context.lookup("java:comp/env/jms/Queue");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer consumer = session.createConsumer(queue);

            MyMessageListener requestListener = new MyMessageListener();
            consumer.setMessageListener(requestListener);
            connection.start();

            // Start connection or nothing will happen!!!
            connection.start();

            //Code not reached
            //session.close();
            //connection.close();
        } catch (Exception ex) {
            System.out.println("JMS REMOTE PRODUCER: EXCEPTION");
        }

        System.out.println("JMS REMOTE CONSUMER: finished");
    }

}
