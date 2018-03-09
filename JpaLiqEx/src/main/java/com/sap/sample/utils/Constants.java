package com.sap.sample.utils;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public final class Constants {

	public static final String PERSISTENCE_UNIT_NAME = "awardsRecognition";
	public static final String CONFIG_FILE = "application";
	public static final ResourceBundle CONFIG = ResourceBundle.getBundle(CONFIG_FILE);


/**************************************************************
 * Table name and String length Constants defined
 *
 **************************************************************/



/**************************END******************************/


/***********************************************
 * String length defined as	per the DB files
 *
 *
 **********************************************/
	public static final int	STR1			=	1;
	public static final int	STR100			=	100;
	public static final int	STR80			=	80;
	public static final int	STR30			=	30;
	public static final int	STR50			=	50;
	public static final int CH_FLAG			=	1;
	public static final int STR255			=	255;
	public static final int STR500			=	500;
	public static final int STR1000			=	1000;
	public static final int STR60			=	60;
	public static final int	STR32			=	32;
	public static final int	STR36			=	36;
	public static final int STR40			=	40;
	public static final int STR10			=	10;
	public static final int STR2			=	2;
	public static final int STR3			=	3;
	public static final int STR15			=	15;
	public static final int STR5			=	5;
	public static final int STR12			=	12;
	public static final int STR20			=	20;

/************END*******************************/

/******************START*************************/

/************************************************
*
*Entity class Constants for Entity Class
*
*/
	public static final String ENTITY_ID 			= "Entity Id";
	public static final String DESC					= "Comment Description";
	public static final String ENTITY_SET			= "CommentsSet";
	public static final int    COMMENT_ALLOWED		= 100;	
/************END*******************************/
	

	


/************END*******************************/
	
	/************************************************
	*
	*Filter class Constants
	*
	*/
	public static final String ADMIN		= "admin";
	public static final String READ_ONLY	= "readonly";
	public static final String WRITE_ONLY	= "writeonly";
	public static final String DELETE_ONLY	= "deleteonly";
	
	public static final String FILTER_ACCESS_TOKEN		= "accesstoken";
	public static final String FILTER_ENTITYID			= "entityid";
	public static final String FILTER_ENTITY_TYPE		= "entitytype";
	public static final String FILTER_X_CSRF_TOKEN		= "x-csrf-token";
	public static final String FILTER_FETCH				= "fetch";
	public static final String FILTER_PROFILE			= "profile";
	public static final String FILTER_SESSION_ID		= "x-token-sessionid";
	
	public static final List<String> profileList = Arrays.asList(ADMIN, READ_ONLY, WRITE_ONLY);
	public static final List<String> checkProfile = Arrays.asList(READ_ONLY, WRITE_ONLY);
	
	//run scheduler every 24 hours
	public static final long SCHEDULE_TIMER = 86400000;	
	//Token access time greater than 2 hours should be cleaned from db
	public static final long TOKEN_ACESSTIME = 7200000;
	
	/************************************************
 	*
	*Message Bundle Constants
	*
	*/
	public static final String MESSAGES_CORE = "i18n.messagesCore";
	public static final String LOG_MESSAGES = "application";
	/************END*******************************/
}
