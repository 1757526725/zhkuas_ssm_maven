package com.zhku.utils;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Log {
	
	static Logger logger = Logger.getLogger(Log.class.getName());
	
    public static void logDebug(String message) {
    	logger.log(Level.INFO, message);
	}

	public static void logInfo(String message) {
		logger.log(Level.INFO, message);
	}


}
