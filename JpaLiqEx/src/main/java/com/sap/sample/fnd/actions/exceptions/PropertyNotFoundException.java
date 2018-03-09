package com.sap.sample.fnd.actions.exceptions;

import java.util.ResourceBundle;

import com.sap.sample.utils.Constants;
import com.sap.sample.utils.MessageUtil;

public class PropertyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String propertyName;
	private String propertyType;
	public PropertyNotFoundException(String propertyName, String propertyType) {
		this.propertyName = propertyName;
		this.propertyType = propertyType;
	}
	public PropertyNotFoundException(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String toString() {
		ResourceBundle msgBundleLog = MessageUtil.getMessageBundle(Constants.LOG_MESSAGES);	
		if(this.propertyName!=null && this.propertyName.contains(",")) {
			return MessageUtil.getMessage(msgBundleLog, "ipd.exception.properties") + this.propertyName + MessageUtil.getMessage(msgBundleLog, "ipd.exception.arenotfound");	
		}else if(this.propertyType!=null){
			return MessageUtil.getMessage(msgBundleLog, "ipd.exception.property") + this.propertyName + MessageUtil.getMessage(msgBundleLog, "ipd.exception.oftype") +this.propertyType + MessageUtil.getMessage(msgBundleLog, "ipd.exception.isnotfound");
		}else {
			return MessageUtil.getMessage(msgBundleLog, "ipd.exception.property") + this.propertyName + MessageUtil.getMessage(msgBundleLog, "ipd.exception.isnotfound");
		}
		
	}

}
