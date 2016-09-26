package com.donn.rootvpn;

import android.util.Log;

public class L {
	
	private static String appContextName = "RootVPN";
	
	public static void log(Object sourceClass, String stringToLog) {
		Log.d(appContextName , sourceClass.getClass().getSimpleName() + ": " + stringToLog);
	}
	
	public static void err(Object sourceClass, String errToLog) {
		Log.e(appContextName , sourceClass.getClass().getSimpleName() + ": " + errToLog);
	}
}
