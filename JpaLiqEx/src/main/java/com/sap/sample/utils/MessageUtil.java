package com.sap.sample.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;



public class MessageUtil {
	public static ResourceBundle getMessageBundle() {
		
		Locale locale = Locale.getDefault();
		ResourceBundle messageBundle = ResourceBundle.getBundle("i18n.messagesCore", locale);
		return messageBundle;
	}
	
	public static ResourceBundle getMessageBundle(String baseName) {
		
		Locale locale = Locale.getDefault();
		ResourceBundle messageBundle = ResourceBundle.getBundle(baseName, locale);
		return messageBundle;
	}

	public static String getMessage(ResourceBundle msgBundle, String key) {
		return msgBundle.getString(key);
	}

	public static String getMessage(ResourceBundle msgBundle, String key, Object... args) {
		return MessageFormat.format(msgBundle.getString(key), args);
	}
}
