/**
 * 
 */
package com.sap.sample.fnd.odatafactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.account.TenantContext;
import com.sap.sample.utils.Constants;
import com.sap.sample.utils.MessageUtil;
import com.sap.sample.utils.Utility;
import com.sap.security.um.user.User;
import com.sap.security.um.user.UserProvider;


/**
 * @author I070883 
 * This is a Singleton Utility class that is used to Configure an
 * {@link EntityManagerFactory}.
 * <p>
 * Refer to the Documentation <i>
 * (http://docs.oracle.com/javaee/7/api/javax/persistence/EntityManagerFactory.html)
 * </i> for more information on how to configure an EntityManager.
 * <p>
 * For more information regarding the Model classes and other JPA related
 * configuration details, refer to the "META-INF/persistence.xml" file in the
 * resources folder.
 * 
 */

public class CustomJpaEntityManagerFactory  {

	final static Logger LOGGER = LoggerFactory.getLogger(CustomJpaEntityManagerFactory.class);
	/**
	 * The JNDI name of the DataSource.
	 */
	public static final String DATA_SOURCE_NAME = "java:comp/env/jdbc/DefaultDB";
    public static final String TENANT_CTX_NAME = "java:comp/env/TenantContext";


	
	/**
	 * The package name which contains all the model classes.
	 */
	public static final String PERSISTENCE_UNIT_NAME = "liquibasePerist";

	/**
	 * The static {@link EntityManagerFactory}
	 */
	private static EntityManagerFactory entityManagerFactory = null;

	/**
	 * Returns the singleton EntityManagerFactory instance for accessing the
	 * default database.
	 * 
	 * @return the singleton EntityManagerFactory instance
	 * @throws NamingException
	 *             if a naming exception occurs during initialization
	 * @throws SQLException
	 *             if a database occurs during initialization
	 */
	public static synchronized EntityManagerFactory getEntityManagerFactory()
			throws NamingException, SQLException {
		
		String tenantId = null;
		ResourceBundle msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);
		
		if (entityManagerFactory == null) {
			Map<String, Object> properties = new HashMap<String, Object>();
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(DATA_SOURCE_NAME);
			TenantContext tenantctx = (TenantContext) ctx.lookup(TENANT_CTX_NAME);
			properties.put("tenant_id", tenantctx.getTenant().getId());
			tenantId = tenantctx.getTenant().getId();
			LOGGER.info(MessageUtil.getMessage(msgBundleLog, "ipd.odatafactory.tenantid") + tenantId);

            if (tenantId == null || tenantId.isEmpty()) {
            	LOGGER.error(MessageUtil.getMessage(msgBundleLog, "ipd.odatafactory.tenantcreationfailed"));
            }
            
		
			properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
			properties.put(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT,tenantId);
			properties.put(PersistenceUnitProperties.MULTITENANT_SHARED_EMF, "false");
			properties.put(PersistenceUnitProperties.SESSION_NAME, tenantId+"-MT-Session" );                 

			entityManagerFactory = Persistence.createEntityManagerFactory(
					PERSISTENCE_UNIT_NAME, properties);
			Utility.setEntityManagerFactory(entityManagerFactory);
		}
		
		return entityManagerFactory;
		
	}

	public static synchronized String getUserProvderInfo() {
		
		User user = null;
		String currentUserId = null;
		String userName = null;
		try {
			InitialContext usrctx = new InitialContext();
			UserProvider userProvider = (UserProvider) usrctx.lookup("java:comp/env/user/Provider");
			user = userProvider.getCurrentUser();
			if (user != null) {
				currentUserId = user.getName();
				/*currentUserId = user.getAttribute(("firstName")) + user.getAttribute(("lastName")) ;
				if(currentUserId == null){
					return user.getName();
				}*/
				LOGGER.info("User Name" + currentUserId);
				LOGGER.info(("email" + user.getAttribute("email")));
			}
		} catch (Exception e) {
			LOGGER.error("Exception : " + e.getMessage());
			e.printStackTrace();
		}

		return currentUserId;
	}
	
public static synchronized String getUserProvderInfoEmailID() {
		
		User user = null;
		String currentUserId = null;
		String emailId = null;
		try {
			InitialContext usrctx = new InitialContext();
			UserProvider userProvider = (UserProvider) usrctx.lookup("java:comp/env/user/Provider");
			user = userProvider.getCurrentUser();
			if (user != null) {
				currentUserId = user.getName();
				/*currentUserId = user.getAttribute(("firstName")) + user.getAttribute(("lastName")) ;
				if(currentUserId == null){
					return user.getName();
				}*/
				
				emailId = user.getAttribute("email");
				LOGGER.info("User Name" + currentUserId);
				LOGGER.info(("email" + user.getAttribute("email")));
			}
		} catch (Exception e) {
			LOGGER.error("Exception : " + e.getMessage());
			e.printStackTrace();
		}

		return emailId;
	}	

}
