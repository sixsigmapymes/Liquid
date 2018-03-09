package com.sap.sample.fnd.odatafactory;

import org.apache.olingo.odata2.api.ODataDebugCallback;

import com.sap.sample.utils.Constants;



/**
 * @author I070883 
 * Class for Debug Callback
 * 
 */
public class CustomDebugCallback implements ODataDebugCallback {

	@Override
	public boolean isDebugEnabled() {
		boolean isDebug = true;// true|configuration|user role check
	    return isDebug;
	}
	  
	}
