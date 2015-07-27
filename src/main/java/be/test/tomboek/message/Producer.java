package be.test.tomboek.message;

import javax.jms.JMSException;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Producer {

    public static void Produce() throws JMSException {

        System.out.println("JMS REMOTE PRODUCER: start");

        try {
            // create a new intial context, which loads from jndi.properties file
            System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 1");
            Context ctx = new InitialContext();
            System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 2");
            //QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("jms/ConnectionFactory");
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("java:comp/env/jms/ConnectionFactory");
            System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 3");
            Connection connection = factory.createConnection();           
            System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 4");
            //Queue queue = (javax.jms.Queue) ctx.lookup("jms/FooQueue");
            Queue queue = (javax.jms.Queue) ctx.lookup("java:comp/env/jms/Queue");
            System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 5");
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            System.out.println("XXXXXXXXXXXXXXXXXXXX - Step 6");
            MessageProducer producer = session.createProducer(queue);

            System.out.println("JMS REMOTE PRODUCER: connected");

            int count = 0;

            for (int i = 0; i < 5; i++) {
                String messageText = "This is message " + (i + 1);
                Message message = session.createTextMessage(messageText);
                System.out.println("JMS REMOTE PRODUCER: sending - " + messageText);
                producer.send(message);
                count += 1;
            }
            System.out.println("JMS REMOTE PRODUCER: total messages sent: " + count);

            // Clean up
            session.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println("JMS REMOTE PRODUCER: EXCEPTION:" + ex.toString());
        }
        System.out.println("JMS REMOTE PRODUCER: finished");
    }
}
