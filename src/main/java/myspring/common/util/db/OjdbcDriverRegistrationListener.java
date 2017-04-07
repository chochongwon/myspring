package myspring.common.util.db;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import oracle.jdbc.OracleDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * To prevent a memory leak, the JDBC Driver has been forcibly unregistered
 */
public class OjdbcDriverRegistrationListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(OjdbcDriverRegistrationListener.class);
    
	@Override
	public void contextInitialized(ServletContextEvent arg0) { }
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	    logger.info("Deregistering JDBC driver {}");
	    Enumeration<Driver> drivers = DriverManager.getDrivers();
	    while (drivers.hasMoreElements()) {
	        Driver driver = drivers.nextElement();
	        ClassLoader driverclassLoader = driver.getClass().getClassLoader();
	        ClassLoader thisClassLoader = this.getClass().getClassLoader();
	        if (driverclassLoader != null && thisClassLoader != null &&  driverclassLoader.equals(thisClassLoader)) {
	            try {
	                logger.warn("Deregistering: " + driver);
	                DriverManager.deregisterDriver(driver);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        for(Thread t:threadArray) {
            if(t.getName().contains("Abandoned connection cleanup thread")) {
                synchronized(t) {
                    t.stop(); //don't complain, it works
                }
            }
        }	    
	}
}
