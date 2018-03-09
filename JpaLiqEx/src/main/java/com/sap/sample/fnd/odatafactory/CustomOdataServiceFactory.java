package com.sap.sample.fnd.odatafactory;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATransaction;
import org.apache.olingo.odata2.jpa.processor.api.OnJPAWriteContent;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAAccessFactory;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory;
//import org.apache.olingo.odata2.jpa.processor.core.ODataJPAProcessorDefault;

public abstract class CustomOdataServiceFactory extends ODataServiceFactory {

	private ODataJPAContext oDataJPAContext;
	private ODataContext oDataContext;
	private boolean setDetailErrors = false;
//	private OnJPAWriteContent onJPAWriteContent = null;
//	private ODataJPATransaction oDataJPATransaction = null;

	/**
	 * Implement this method and initialize OData JPA Context. It is mandatory
	 * to set an instance of type {@link javax.persistence.EntityManagerFactory}
	 * into the context. An exception of type
	 * {@link org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException}
	 * is thrown if EntityManagerFactory is not initialized. <br>
	 * <br>
	 * <b>Sample Code:</b> <code>
	 * <p>public class JPAReferenceServiceFactory extends ODataJPAServiceFactory{</p>
	 * 
	 * <blockquote>private static final String PUNIT_NAME = "punit";
	 * <br>
	 * public ODataJPAContext initializeODataJPAContext() {
	 * <blockquote>ODataJPAContext oDataJPAContext = this.getODataJPAContext();
	 * <br>
	 * EntityManagerFactory emf = Persistence.createEntityManagerFactory(PUNIT_NAME);
	 * <br>
	 * oDataJPAContext.setEntityManagerFactory(emf);
	 * oDataJPAContext.setPersistenceUnitName(PUNIT_NAME);
	 * <br> return oDataJPAContext;</blockquote>
	 * }</blockquote>
	 * } </code>
	 * <p>
	 * 
	 * @return an instance of type
	 *         {@link org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext}
	 * @throws ODataJPARuntimeException
	 */
	public abstract ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException;

	/**
	 * Creates an OData Service based on the values set in
	 * @param ODataContext Context Information
	 * @throws ODataException if odata access related exception occurs
	 * @return ODataService This returns data in ojbject of type ODataService.
	 */
	@Override
	public final ODataService createService(final ODataContext ctx) throws ODataException {

		oDataContext = ctx;

		// Initialize OData JPA Context
		oDataJPAContext = initializeODataJPAContext();

		validatePreConditions();

		ODataJPAFactory factory = ODataJPAFactory.createFactory();
		ODataJPAAccessFactory accessFactory = factory.getODataJPAAccessFactory();

		// OData JPA Processor
		if (oDataJPAContext.getODataContext() == null) {
			oDataJPAContext.setODataContext(ctx);
		}

		 ODataSingleProcessor odataJPAProcessor =
		 accessFactory.createODataProcessor(oDataJPAContext);
		 //To invoke the preprocessor method
	//	ODataSingleProcessor odataJPAProcessor = new CustomODataJPAPreProcessor(oDataJPAContext);

		// OData Entity Data Model Provider based on JPA
		EdmProvider edmProvider = accessFactory.createJPAEdmProvider(oDataJPAContext);

		return createODataSingleProcessorService(edmProvider, odataJPAProcessor);
	}

	/**
	 * Method retrurns Odata context infromation
	 * @return an instance of type {@link ODataJPAContext}
	 * @throws ODataJPARuntimeException
	 */
	public final ODataJPAContext getODataJPAContext() throws ODataJPARuntimeException {
		if (oDataJPAContext == null) {
			oDataJPAContext = ODataJPAFactory.createFactory().getODataJPAAccessFactory().createODataJPAContext();
		}
		if (oDataContext != null) {
			oDataJPAContext.setODataContext(oDataContext);
		}
		return oDataJPAContext;

	}

	private OnJPAWriteContent onJPAWriteContent = null;
	private ODataJPATransaction oDataJPATransaction = null;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ODataCallback> T getCallback(final Class<T> callbackInterface) {

		if (callbackInterface.isAssignableFrom(CustomDebugCallback.class)) {
			return (T) new CustomDebugCallback();
		} else if (setDetailErrors == true) {
			if (callbackInterface.isAssignableFrom(CustomErrorCallback.class)) {
				return (T) new CustomErrorCallback();
			}
		}
		if (onJPAWriteContent != null) {
			if (callbackInterface.isAssignableFrom(OnJPAWriteContent.class)) {
				return (T) onJPAWriteContent;
			}
		}
		if (oDataJPATransaction != null) {
			if (callbackInterface.isAssignableFrom(ODataJPATransaction.class)) {
				return (T) oDataJPATransaction;
			}
		}
		return null;
	}

	/**
	 * The methods sets the context with a callback implementation for JPA
	 * provider specific content. For details refer to
	 * {@link org.apache.olingo.odata2.jpa.processor.api.OnJPAWriteContent}
	 * 
	 * @param onJPAWriteContent
	 *            is an instance of type
	 *            {@link org.apache.olingo.odata2.jpa.processor.api.OnJPAWriteContent}
	 */
	protected void setOnWriteJPAContent(final OnJPAWriteContent onJPAWriteContent) {
		this.onJPAWriteContent = onJPAWriteContent;
	}

	/**
	 * The methods sets the context with a callback implementation for JPA
	 * transaction specific content. For details refer to
	 * {@link ODataJPATransaction}
	 * 
	 * @param oDataJPATransaction
	 *            is an instance of type
	 *            {@link org.apache.olingo.odata2.jpa.processor.api.ODataJPATransaction}
	 */
	protected void setODataJPATransaction(final ODataJPATransaction oDataJPATransaction) {
		this.oDataJPATransaction = oDataJPATransaction;
	}

	/**
	 * The method sets the context whether a detail error message should be
	 * thrown or a less detail error message should be thrown by the library.
	 * 
	 * @param setDetailErrors
	 *            takes
	 *            <ul>
	 *            <li>true - to indicate that library should throw a detailed
	 *            error message</li>
	 *            <li>false - to indicate that library should not throw a
	 *            detailed error message</li>
	 *            </ul>
	 * 
	 */
	protected void setDetailErrors(final boolean setDetailErrors) {
		this.setDetailErrors = setDetailErrors;
	}

	private void validatePreConditions() throws ODataJPARuntimeException {

		if (oDataJPAContext.getEntityManagerFactory() == null) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ENTITY_MANAGER_NOT_INITIALIZED,
					null);
		}

	}
}
