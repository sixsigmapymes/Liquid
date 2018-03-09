package com.sap.sample.utils;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class TokenManager {
	private HashMap<TokenKey,String> tokenMap = new HashMap<>();
	private static TokenManager INSTANCE = new TokenManager();
	static Logger LOGGER = LoggerFactory.getLogger(TokenManager.class);
	
	private TokenManager() {
		
	}
	public static TokenManager getInstance() {
			return INSTANCE;
	}
	public void addToken(TokenKey key, String token) {
		if(tokenMap == null) {
			tokenMap = new HashMap<TokenKey,String>();
		}
		tokenMap.put(key, token);
		LOGGER.debug("S");
		
	}
	public String getToken(TokenKey key) {
		LOGGER.debug("Setting scheduler in QuartzServiceManager");
		return tokenMap.get(key);
	}
}

