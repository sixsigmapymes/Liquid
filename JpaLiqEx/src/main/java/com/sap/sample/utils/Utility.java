/**
 * 
 */
package com.sap.sample.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.sample.model.CommentsVO;

/**
 * @author I070883
 * Utility Class for Entity Manager factory creation
 *
 */
public class Utility {

	final static Logger LOGGER = LoggerFactory.getLogger(Utility.class);
	/**
	 * The {@link EntityManagerFactory} instance.
	 */
	private static EntityManagerFactory emf;

	/**
	 * The static method to return the {@link EntityManagerFactory} instance.
	 * @return
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			throw new IllegalArgumentException(
					"EntityManagerfactory is not initialized!!!");
		}
		return emf;
	}


	/**
	 * Setter for the {@link EntityManagerFactory}
	 * @param emf - The {@link EntityManagerFactory} to set.
	 */
	public static void setEntityManagerFactory(EntityManagerFactory emf) {
		Utility.emf = emf;
	}

	//Close Entity Manager
	public static void CloseEntityManager(EntityManager em)
	{
		if ((em != null) && (em.isOpen()==true))
		{
			em.close();
		}
	}

	//Split the String and send it as collection
	/*public static Collection<String> splitStringToCollection(List<EntityDetails> enIdLst) {
		//Collection<String> list = Arrays.asList(s.split(","));
		Collection<String> ret = new ArrayList<String>();
		for (EntityDetails enDtls : enIdLst) {
			String entityIds = enDtls.getEntityId();
			ret.add(entityIds.trim());
		}
		return ret;
	}*/

	/*//Split the String and send it as collection
	public static Collection<String> splitEntitytypeStringToCollection(List<EntitytypeDetails> enIdLst) {
		//Collection<String> list = Arrays.asList(s.split(","));
		Collection<String> ret = new ArrayList<String>();
		for (EntitytypeDetails enDtls : enIdLst) {
			String entityIds = enDtls.getEntitytype();
			ret.add(entityIds.trim());
		}
		return ret;
	}*/
	
	/*
	 *@Method: To Check, if tokenId exists in database or not.
	 *@return : return tokenId if find tokenId exists else null value.
	 */
	@SuppressWarnings("unchecked")
	public static String checkIfTokenExistsAlready(String sesionID, String entityId, String entityType, String profile,String tokenID ,boolean flag){/*
		
		emf = Utility.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		AccessToken tokenObject = null;
		List<String>localProfileList;
		String newTokenId = null;
		try{
			em.getTransaction().begin();
			if(flag){
				AccessTokenPK pk = new AccessTokenPK();
				pk.setEntityId(entityId);
				pk.setEntityType(entityType);
				//pk.setProfile(profile);
				pk.setSessionId(sesionID);
				pk.setTokenId(tokenID);
				tokenObject = em.find(AccessToken.class, pk);
				
				if(profile.equals(Constants.ADMIN)){
					boolean isAdmin = tokenObject.getProfile().equalsIgnoreCase(profile);
					return ( (tokenObject != null) ? ( (isAdmin)? tokenObject.getTokenId() : null ): null);
				}
				
				newTokenId = ( (tokenObject != null) ? tokenObject.getTokenId(): null);
			}
			else{
				String sqlQuery = "SELECT a.tokenId from AccessToken a WHERE a.sessionId = :inpSId"
							+ " AND a.entityId = :inpEnId AND a.entityType = :inpEnType AND a.profile IN :inpProfile";
				Query query = em.createQuery(sqlQuery);
				query.setParameter("inpSId",sesionID );
				query.setParameter("inpEnId",entityId );
				query.setParameter("inpEnType", entityType);
				if(profile.equals(Constants.ADMIN)){
					localProfileList = Arrays.asList(Constants.ADMIN);
					query.setParameter("inpProfile", localProfileList);
				}else{
					localProfileList = Constants.checkProfile;
					query.setParameter("inpProfile", localProfileList);
				}
				
				List<String> accesTokenList = query.getResultList();
				for(int index = 0; index<accesTokenList.size();index++){
					newTokenId = accesTokenList.get(index);
				}
			}
			em.getTransaction().commit();
		//return ( (tokenObject != null)? tokenObject.getTokenId(): null);
		return newTokenId;
		}catch (Exception e) {
			LOGGER.error("Exception : "+e.getMessage());
			return null;
		}finally {
			CloseEntityManager(em);
		}
	*/
		return null;
		}
	
	 /*
	  * Method to convert String of JSON to Comment VO Object
	  */
	 public static Hashtable<String, String> convertStringifyJsonToObject(String jsonString) throws JsonParseException, JsonMappingException, IOException{/*

		 	
		 	Hashtable<String, String> hashSet = new Hashtable<String, String>();
		 	CommentsVO  commentObj = new ObjectMapper().readValue(jsonString, CommentsVO.class);
			LOGGER.error("EntityId:  "+commentObj.getEntityId());
			hashSet.put("entityId", commentObj.getEntityId());
			hashSet.put("entityType", commentObj.getEntityType());

	return hashSet;	
	*/
		 return null;
		 }

	
}