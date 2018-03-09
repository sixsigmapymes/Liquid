/**
 * 
 */
package com.sap.sample.fnd.odatafactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.io.IOUtils;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.eclipse.persistence.tools.schemaframework.PackageDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
//import com.sap.s4idea.assistanceRR.web.MultiReadHttpServletRequest;
import com.sap.sample.model.CommentsVO;
import com.sap.sample.utils.Constants;
import com.sap.sample.utils.MessageUtil;
import com.sap.sample.utils.Utility;

/**
 * @author I070883
 *
 */
public class ProjectCollaborationFactoryFilter implements Filter {

	/**
	 * 
	 */
	final static Logger LOGGER = LoggerFactory.getLogger(ProjectCollaborationFactoryFilter.class);

	ResourceBundle msgBundle, msgBundleLog;

	public ProjectCollaborationFactoryFilter() {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		msgBundle = MessageUtil.getMessageBundle(Constants.MESSAGES_CORE);
		msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);
		String getAccessToken = null;
		String entityId = null;
		String entityType = null;
		String sessionId = null;
		String xCsrfToken = null;
		boolean isValidate = false;
		String currentProfile = null;
		try {

			if (request instanceof HttpServletRequest) {

				HttpServletRequest oCntxt = (HttpServletRequest) request;
//				HttpSession session = ((HttpServletRequest) request).getSession();
//				sessionId = session.getId();
				String remoteUser = ((HttpServletRequest) request).getRemoteUser();
				// Principal p = ((HttpServletRequest)
				// request).getUserPrincipal();//TODO remove code
				// String name = p.getName();//TODO remove code
				// LOGGER.info("remoteuser: "+remoteUser+"\t\t\t\tname :
				// "+name); //TODO remove code
				Enumeration<String> headerNames = ((HttpServletRequest) request).getHeaderNames();
				if (headerNames != null) {
					getAccessToken = ((HttpServletRequest) request).getHeader((Constants.FILTER_ACCESS_TOKEN));
					entityId = ((HttpServletRequest) request).getHeader((Constants.FILTER_ENTITYID));
					entityType = ((HttpServletRequest) request).getHeader((Constants.FILTER_ENTITY_TYPE));
					xCsrfToken = ((HttpServletRequest) request).getHeader((Constants.FILTER_X_CSRF_TOKEN));
					//currentProfile = ((HttpServletRequest) request).getHeader((Constants.FILTER_PROFILE));
					sessionId = ((HttpServletRequest) request).getHeader((Constants.FILTER_SESSION_ID));
					//LOGGER.debug("headerNames: " + headerNames.toString());
					LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.token.debug")+ getAccessToken);
					//LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.entityid.debug")+ entityId);
				//	LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.entitytype.debug")+ entityType);
					LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.xCsrfToken.debug") + xCsrfToken);
					//LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.currentProfile.debug") + currentProfile);
					
				}
				
				String url = oCntxt.getRequestURI().toString();
				if (url.contains("/ipd.svc/v1/CommentsSet")) {// &&
																// isPathRestricted(oCntxt)
					boolean isAuthorized = ((xCsrfToken == null || xCsrfToken.isEmpty()) ? true
							: (xCsrfToken.equalsIgnoreCase(Constants.FILTER_FETCH)) ? false : true);
					if (!isCommentsPathRestricted(oCntxt) && isAuthorized) {
						HttpServletResponse httpResp = (HttpServletResponse) response;
						httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								MessageUtil.getMessage(msgBundle, "ipd.filter.valid.unauthorized.comment"));
					} else {

						// Method: To validate the token
						/// isCorrectToken = validateInputToken(sessionId,
						// entityId, entityType, getAccessToken);

						// isTokenNullCheck = (isCorrectToken == null) ? false :
						// (isCorrectToken.isEmpty() ? false : true) ;
						/*
						 * if(isTokenNullCheck){ isValidate =
						 * !isCorrectToken.equalsIgnoreCase(getAccessToken);
						 * LOGGER.debug("isValidate Value: "+ isValidate); }
						 */
						// LOGGER.info("Token validation value " +
						// isAuthorized);
						String path = oCntxt.getMethod();
						/*
						 * wrap the request in order to read the inputstream  multiple times
						 */
						//MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest)request);
						
						if(path.equalsIgnoreCase("POST") || path.equalsIgnoreCase("PUT")){
							
							String result = null;//IOUtils.toString(multiReadRequest.getInputStream(), StandardCharsets.UTF_8);
							 
							Hashtable<String, String> hashTable = Utility.convertStringifyJsonToObject(result);
								entityId = hashTable.get("entityId");
								entityType = hashTable.get("entityType");
						} else if(path.equalsIgnoreCase("GET")){
							String queryString = ((HttpServletRequest)request).getQueryString();
							String decoded = URLDecoder.decode(queryString, "UTF-8");
							LOGGER.info(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.decoded.string") + decoded);
							if(decoded != null && decoded.contains("filter") && decoded.contains("entityId") && decoded.contains("entityType")){
								Hashtable<String, String> hashValue = getRequestResultFromInputParams(decoded);
								entityId = hashValue.get("entityId");
								entityType = hashValue.get("entityType");
								LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.entityid.debug")+ entityId);
								LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.entitytype.debug")+ entityType);
							}else {
								HttpServletResponse httpResp = (HttpServletResponse) response;
								httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
										MessageUtil.getMessage(msgBundle, "ipd.filter.valid.unauthorized.parameter.incorrect"));
							}
						}

						currentProfile = getMethodType(path);
						if (isAuthorized) {
							isValidate = validateInputToken(sessionId, entityId, entityType, getAccessToken,
									currentProfile);
							LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.unauthorized.value")+ isAuthorized);
							if (!isValidate) {
								HttpServletResponse httpResp = (HttpServletResponse) response;
								httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
										MessageUtil.getMessage(msgBundle, "ipd.filter.valid.unauthorized.access.token"));
								LOGGER.error(MessageUtil.getMessage(msgBundle, "ipd.filter.valid.unauthorized.access.token"));
							} else {
							chain.doFilter(request, response);
							}
						}
					}
				}else if(url.contains("/rest/api/v1/deleteById") || url.contains("/rest/api/v1/deleteByEntityType")){
					LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.rest.token.debug"));

					if (!isEntityDeleteByRestricted(oCntxt)) {
						HttpServletResponse httpResp = (HttpServletResponse) response;
						httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								MessageUtil.getMessage(msgBundle, "ipd.filter.valid.unauthorized.rest"));
								LOGGER.error(MessageUtil.getMessage(msgBundle, "ipd.filter.valid.unauthorized.rest"));
					} else {
							String path = oCntxt.getMethod();
							currentProfile = path.equalsIgnoreCase("POST") ? (Constants.ADMIN) : null;
							isValidate = validateInputToken(sessionId, entityId, entityType, getAccessToken,
								currentProfile);
							LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.rest.value")+ isValidate);
							if (!isValidate) {
								HttpServletResponse httpResp = (HttpServletResponse) response;
								httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
										MessageUtil.getMessage(msgBundle, "ipd.filter.valid.unauthorized.access.token"));
								LOGGER.error(MessageUtil.getMessage(msgBundle, "ipd.filter.valid.unauthorized.access.token"));
							} else {
								chain.doFilter(request, response);
							}
					}
				}
				else {
					chain.doFilter(request, response);
				}
			}
		} catch (IOException | ODataException e) {
			LOGGER.error(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 * Checks if triggered path is restricted for anonymous users
	 * 
	 * @param oCntxt
	 *            OData Context
	 * @return true if path is restricted else false
	 * @throws ODataException
	 */
	private boolean isCommentsPathRestricted(HttpServletRequest oCntxt) throws ODataException {
		boolean status;
		String path = oCntxt.getRequestURI().toString();
		if ((path.contains("CommentsSet")) &&
						   ((oCntxt.getMethod().equals("GET"))
						|| (oCntxt.getMethod().equals("POST"))
						|| (oCntxt.getMethod().equals("PUT")))) {

			status = true;
		} else {
			/*if (currentProfile != null && currentProfile.equalsIgnoreCase(Constants.ADMIN)) {
				status = true;
			} else*/
				status = false;
		}
		return status;
	}

	private String getMethodType(String path){
		
		String profile;
		switch (path){
		
		case "GET": 
			 profile = Constants.READ_ONLY;
			 break;
		case "POST": 
			 profile = Constants.WRITE_ONLY;
			 break;
		case "PUT": 
			 profile = Constants.WRITE_ONLY;
			 break;
		default:
			 throw new IllegalArgumentException("Invalid method Type: "+path);
		}
		
		return profile;
	}
	/**
	 * Checks if token is valid
	 * 
	 * @param sesionID,
	 *            entityId, entityType, accessTokenId, profile
	 * @return token value else null or empty value
	 * @throws catched
	 *             in parent method
	 */
	private boolean validateInputToken(String sesionID, String entityId, String entityType, String accesstokenId,
			String profile) {
		ResourceBundle msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);
		String tokenValidate = null;
		boolean isCorrectToken = false;
		try {

			LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.validateInputToken.debug"));
		
			tokenValidate = Utility.checkIfTokenExistsAlready(sesionID, entityId, entityType, profile, accesstokenId, true);
			LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.validateInputToken.value.debug")+ tokenValidate);
			isCorrectToken = (tokenValidate == null || tokenValidate.isEmpty()) ? false : true;
			
			/*if (tokenValidate != null && accesstokenId != null) {
				isCorrectToken = accesstokenId.equalsIgnoreCase(tokenValidate);
			}*/
			
		} catch (Exception e) {
			LOGGER.error(MessageUtil.getMessage(msgBundleLog, "ipd.filter.valid.validateInputToken.exception") + e.getMessage());
			return false;
		}
		return isCorrectToken;
	}


	/**
	 * Checks if triggered path is restricted for anonymous users
	 * 
	 * @param oCntxt, currentProfile
	 *            
	 * @return true if path is restricted else false
	 * @throws Exception
	 */
	private boolean isEntityDeleteByRestricted(HttpServletRequest oCntxt) throws Exception {

		// boolean isProfile = Constants.profileList.contains(currentProfile);
		boolean status;
		String path = oCntxt.getRequestURI().toString();
		if ((path.contains("deleteById") || path.contains("deleteByEntityType")) && (oCntxt.getMethod().equals("POST")) ) {
			status = true;
		} else {
			status = false;
		}
		return status;
	}
	
	private final String getRequestResult(InputStream inp){
		
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		String localResult = null;
		byte[] buffer = new byte[1024];
		int length;
		try {
			while ((length = inp.read(buffer)) != -1) {
			    result.write(buffer, 0, length);
			}
			localResult = result.toString("UTF-8"); 
			
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return localResult;
	}
	
	
	
// uri = "filter=entityId eq '0DE82517-5713' &entityType eq 'workflow'"
	private  Hashtable<String, String> getRequestResultFromInputParams(String uri){
		
		Hashtable<String,String> map = new Hashtable<String,String>();

		String truncatedUri = uri.replaceAll("\\s","");
		Pattern pattern = Pattern.compile("entityIdeq'(.*?)'", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(truncatedUri);
		if(matcher.find()){
			LOGGER.debug(matcher.group(1));
			map.put("entityId", matcher.group(1));
		}
	    pattern = Pattern.compile("entityTypeeq'(.*?)'", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(truncatedUri);
		if(matcher.find()){
			LOGGER.debug(matcher.group(1));
			map.put("entityType", matcher.group(1));
		} 		
		return map;
	}
	
}