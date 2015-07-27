package be.test.tomboek.listener;

import be.test.tomboek.message.JMSConnector;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {

    private static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MyServletContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        LOGGER.debug("ServletContextListener destroyed");
        System.out.println("ServletContextListener destroyed");
    }

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        LOGGER.debug("ServletContextListener started");
        System.out.println("ServletContextListener started");
        new JMSConnector().startListening();
        System.out.println("Starting the JMS Listener");
    }
}
