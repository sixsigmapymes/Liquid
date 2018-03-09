package com.sap.sample.fnd.odatafactory;

import java.util.ResourceBundle;

import javax.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.sample.utils.Constants;
import com.sap.sample.utils.MessageUtil;
//import org.eclipse.persistence.Version;.
/**
 * @author I070883
 * Class for OData JPA Service Handling
 *
 */
public class SampleOdataJpaServiceFactory extends CustomOdataServiceFactory {
	
	final static Logger LOGGER = LoggerFactory.getLogger(SampleOdataJpaServiceFactory.class);
	
	public static final String PERSISTENCE_UNIT_NAME = "liquibasePerist";
	   /**
	   * initializeODataJPAContext method Initialize JPA Context  and returns ODataJPAContext. 
	   * @throws ODataJPARuntimeException if ODataJPARuntimeException related exception occurs
	   * @return ODataJPAContext This returns data in object of type ODataJPAContext.
	   */
	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {

		ODataJPAContext oDataJPAContext = this.getODataJPAContext();
		ResourceBundle msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);
		try{

			LOGGER.info(MessageUtil.getMessage(msgBundleLog, "ipd.pcfactory.started"));
			EntityManagerFactory emf = CustomJpaEntityManagerFactory.getEntityManagerFactory();
			oDataJPAContext.setEntityManagerFactory(emf);
			oDataJPAContext.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);

			//To remove Entity being accessed by OData
			//oDataJPAContext.setJPAEdmExtension((JPAEdmExtension) new CustomAnnotationProcessor());
			
		//	Reading the JPA Mapping file for changed column names in Request/Response
			oDataJPAContext.setJPAEdmMappingModel("JPA_Mapping.xml");
	
			boolean error = Boolean.parseBoolean((String) Constants.CONFIG.getObject("odata.enableError"));
			LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.pcfactory.error"),error);
			
			LOGGER.error(MessageUtil.getMessage(msgBundleLog, "ipd.pcfactory.errorlog"));

			setDetailErrors(error);
			
			return oDataJPAContext;
		}catch(Throwable e){
			LOGGER.error(MessageUtil.getMessage(msgBundleLog, "ipd.pcfactory.exceptionlog"),(Object[])e.getStackTrace());
//			e.printStackTrace();
			return null;
		}
	}
}
