package com.sap.sample.fnd.odatafactory;


import java.io.InputStream;
import java.io.StringWriter;
import java.util.ResourceBundle;

import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.sample.utils.Constants;
import com.sap.sample.utils.MessageUtil;


/**
 * @author I070883
 * Class for OData JPA Entity processor with exits
 *
 */
public class CustomOdataProcessorWithExits extends CustomODataJPAProcessor {
	final static Logger LOGGER = LoggerFactory.getLogger(CustomOdataProcessorWithExits.class);
	public CustomOdataProcessorWithExits(ODataJPAContext oDataJPAContext) {
		// TODO Auto-generated constructor stub
		super(oDataJPAContext);
	}

	@Override
	public void preprocess(PostUriInfo uriParserResultView, InputStream content, ODataJPAContext oDataJPAContext) {
		// TODO Auto-generated method stub
		ResourceBundle msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);
		LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.odataprocessor.preprocess"));
	}

	@Override
	public void postprocess(PostUriInfo uriParserResultView, InputStream content,ODataJPAContext oDataJPAContext) {
		// TODO Auto-generated method stub
		ResourceBundle msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);
		LOGGER.debug(MessageUtil.getMessage(msgBundleLog, "ipd.odataprocessor.postprocess"));

		//Chopping off the Stream event handler as we are using rest API Going forward

		//		try {
		//			StringWriter writer = new StringWriter();
		//			IOUtils.copy(content, writer);
		//			String theString = writer.toString();
		//			JSONObject obj = new JSONObject(theString);


		//EdmEntitySet edmEntitySet = uriParserResultView.getTargetEntitySet();
		//			HashMap<Object, Object> map = new HashMap<Object, Object>();
		//			if (edmEntitySet.getName().toString().equals("DeviceData")) {
		//				map.put("ENTITY_NAME", DEVICE_DATA);
		//				map.put("ID", obj.get("DeviceId").toString());
		//				map.put("oDataJPAContext", oDataJPAContext);
		//			} else if (edmEntitySet.getName().toString().equals("Device")) {
		//				map.put("ENTITY_NAME", DEVICE);
		//				map.put("ID", obj.get("DeviceId").toString());
		//				map.put("oDataJPAContext", oDataJPAContext);
		//			}
		//StreamEventHandler handler = StreamEventHandler.getInstance();
		//handler.process(map);

		//		} catch (JSONException e) {
		//			e.printStackTrace();
		//		} catch (Exception ex) {
		//			ex.printStackTrace();
		//		}
	}
}
