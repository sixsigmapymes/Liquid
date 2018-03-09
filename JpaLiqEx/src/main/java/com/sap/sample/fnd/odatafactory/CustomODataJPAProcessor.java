package com.sap.sample.fnd.odatafactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.persistence.EntityTransaction;

import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPADefaultProcessor;
//import org.apache.olingo.odata2.jpa.processor.core.ODataJPAProcessorDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author I070883
 * Class for OData JPA Entity Service Factory Creation
 *
 */
public abstract class CustomODataJPAProcessor extends ODataJPADefaultProcessor {

	final static Logger LOGGER = LoggerFactory.getLogger(CustomODataJPAProcessor.class);

	public CustomODataJPAProcessor(ODataJPAContext oDataJPAContext) {
		// TODO Auto-generated constructor stub
		super(oDataJPAContext);
	}

	   /**
	   * createEntity method is used to Create Entity. 
	   * @param PostUriInfo URI Information for the Post operation
	   * @param InputStream  Input Stream to handle input data
	   * @param String this parameter will contain content type information
	   * @throws ODataException if odata access related exception occurs
	   * @return ODataResponse This returns data in ojbject of type ODataResponse.
	   */
	@Override
	public ODataResponse createEntity(PostUriInfo uriParserResultView, InputStream content, String requestContentType,
			String contentType) throws ODataException {// TODO Auto-generated method stub

		EntityTransaction etx = null;
		etx = oDataJPAContext.getEntityManager().getTransaction();
		boolean isTransactionStartedHere = false; 

		//etx.begin();
		//To check if transaction is active or not
		if(!etx.isActive()) {
			etx.begin();
			isTransactionStartedHere = true; 
		}

		// Preprocess Method
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(content));
			String temp = br.readLine();
			String data = "";
			while(null != temp){
				data += temp;
				temp = br.readLine();
			}
			content = new ByteArrayInputStream(data.getBytes());
			preprocess(uriParserResultView, content, oDataJPAContext);
			content = new ByteArrayInputStream(data.getBytes());
			// etx.commit();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			/*byte[] buffer = new byte[1024];
			int len;
			
			while ((len = content.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();

			// Open new InputStreams using the recorded bytes
			// Can be repeated as many times as you wish
			InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
			InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
*/
			Object createdJpaEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
			ODataResponse oDataResponse = responseBuilder.build(uriParserResultView, createdJpaEntity, contentType);

			// Post Process Method: Again reading the content,so that data reference won't get corrupt
			content = new ByteArrayInputStream(data.getBytes());
			postprocess(uriParserResultView, content, oDataJPAContext);

			//etx.commit();
			if(isTransactionStartedHere) {
				etx.commit();
			}
			
			return oDataResponse;
		} catch(ODataRuntimeException e){
			LOGGER.error("\nException:"+e.getMessage());
			throw e;
		}catch (Exception e) {
			LOGGER.error("\nException:"+e.getMessage());
			return null;
		}
	}

	public abstract void preprocess(PostUriInfo uriParserResultView, InputStream content,
			ODataJPAContext oDataJPAContext);

	public abstract void postprocess(PostUriInfo uriParserResultView, InputStream content,
			ODataJPAContext oDataJPAContext);
}
