package be.test.tomboek.listener;

import be.test.tomboek.message.JMSConnector;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MyServletContextListener.class);

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        LOGGER.debug("ServletContextListener started");
        //new JMSConnector().startListening();
        JMSConnector.startListening();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        LOGGER.debug("ServletContextListener destroyed");
    }
}
