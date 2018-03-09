package com.sap.sample.fnd.odatafactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sap.sample.model.CommentsVO;
import com.sap.sample.utils.Constants;
import com.sap.sample.utils.MessageUtil;
import com.sap.sample.utils.Utility;


/**
 * @author I070883
 * Class for OData JPA Entity processor with exits
 *
 */
public class CustomODataJPAPreProcessor extends CustomODataJPAProcessor {
	ResourceBundle msgBundle, msgBundleLog;
	final static Logger LOGGER = LoggerFactory.getLogger(CustomODataJPAPreProcessor.class);
	public CustomODataJPAPreProcessor(ODataJPAContext oDataJPAContext) {
		// TODO Auto-generated constructor stub
		super(oDataJPAContext);
	}
	
	@Override
	public void preprocess(PostUriInfo uriParserResultView, InputStream content, ODataJPAContext oDataJPAContext) {

		EntityManagerFactory emf;	
		EntityManager em = null; 
		try {
			
			//To create a copy of Object
			
			BufferedReader br = new BufferedReader(new InputStreamReader(content));
			String temp = br.readLine();
			String data = "";
			while(null != temp){
				data += temp;
				temp = br.readLine();
			}

			Gson g = new Gson();
			CommentsVO commentsVO = g.fromJson(data, CommentsVO.class);
			String id = commentsVO.getEntityId();
			
			
			EdmEntitySet edmEntitySet = uriParserResultView.getTargetEntitySet();
			emf = getEntityManagerFactory();

			String entity =  edmEntitySet.getName().toString();
			
			msgBundle = MessageUtil.getMessageBundle(Constants.MESSAGES_CORE);
			msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);
			
			LOGGER.info(MessageUtil.getMessage(msgBundleLog, "ipd.odatajpapreprocessor.in") + MessageUtil.getMessage(msgBundleLog, "ipd.odataprocessor.preprocess") + entity);
			if (edmEntitySet.getName().toString().equals("CommentsSet")) {
				Query query = null;
			    	em=emf.createEntityManager();
			    	em.getTransaction().begin();
			    	String sqlQuery =  " SELECT COUNT(c) FROM Comment c  where c.entityId = :entityId  ";
			    	query = em.createQuery(sqlQuery);
			    	query.setParameter("entityId",id);
			    	
			    	long rowCount = ( (Long) query.getSingleResult() ).longValue();
			    	em.getTransaction().commit();
			    	if(rowCount >= Constants.COMMENT_ALLOWED){
			    		throw new ODataRuntimeException(entity + MessageUtil.getMessage(msgBundle, "ipd.odataprocessor.commentexceederror") + rowCount);	
			    		}
					}
					
				} catch (NamingException | SQLException e) {
					LOGGER.error("Exception: "+e.getMessage());
				} catch (EdmException e) {
					LOGGER.error("Exception: "+e.getMessage());
				}catch (IOException e) {
					LOGGER.error("Exception: "+e.getMessage());
				}
				finally {
					Utility.CloseEntityManager(em);
				}
	}

		@Override
		public void postprocess(PostUriInfo uriParserResultView, InputStream content,ODataJPAContext oDataJPAContext) {
			// TODO Auto-generated method stub
			msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);
			LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.odatajpapreprocessor.in") + MessageUtil.getMessage(msgBundleLog, "ipd.odataprocessor.postprocess"));

		}
	

	public EntityManagerFactory getEntityManagerFactory() throws NamingException, SQLException{
		
		EntityManagerFactory emf = CustomJpaEntityManagerFactory.getEntityManagerFactory();
		//EntityManager em = emf.createEntityManager();
		return emf;
	}
	
	

	 /*
	  * Method to convert String of JSON to Comment VO Object
	  */
	 public static String convertStringifyJsonToObject(String jsonString) throws JsonParseException, JsonMappingException, IOException{

		 	CommentsVO  commentObj = new ObjectMapper().readValue(jsonString, CommentsVO.class);
			System.out.println("Comment   "+commentObj);
			LOGGER.error(" Entiy Id "+commentObj.getEntityId());

	return commentObj.getEntityId();	
	}

	 
	 
}
