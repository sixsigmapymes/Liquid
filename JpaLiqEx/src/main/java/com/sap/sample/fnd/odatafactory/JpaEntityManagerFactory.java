package com.sap.sample.fnd.odatafactory;

import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.sample.utils.Constants;
import com.sap.sample.utils.MessageUtil;

/**
 * @author I070883
 * Class for OData JPA Entity Service Factory Manager Creation
 *
 */
public class JpaEntityManagerFactory {
	private static EntityManagerFactory entityManagerFactory = null;
	final static Logger LOGGER = LoggerFactory.getLogger(JpaEntityManagerFactory.class);
	
	   /**
	   * getEntityManagerFactory method creates and returns Enttity manager factory. 
	   * @throws NamingException if NamingException related exception occurs
	   * @throws SQLException if SQL Exception related exception occurs
	   * @return EntityManagerFactory This returns data in ojbject of type EntityManagerFactory.
	   */
	public static synchronized EntityManagerFactory getEntityManagerFactory() throws NamingException, SQLException {
		ResourceBundle msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);
		LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.jpamanager.outside") + entityManagerFactory);
		
		if (entityManagerFactory == null) {
			LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.jpamanager.in") + entityManagerFactory);
			entityManagerFactory = Persistence.createEntityManagerFactory("awardsRecognition");
		}
		return entityManagerFactory;
	}

}
