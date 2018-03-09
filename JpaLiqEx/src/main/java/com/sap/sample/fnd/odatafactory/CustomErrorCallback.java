package com.sap.sample.fnd.odatafactory;

import org.apache.olingo.odata2.api.ODataDebugCallback;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author I070883
 * Class for Debug Callback with Error
 *
 */
public final class CustomErrorCallback implements ODataDebugCallback {

	@Override
	public boolean isDebugEnabled() {
		return true;
	}


}