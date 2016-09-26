package com.donn.rootvpn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class VPNRequestReceiver extends BroadcastReceiver {

	public static final String ON_INTENT = "com.donn.rootvpn.ON";
	public static final String CONNECTED_INTENT = "com.donn.rootvpn.CONNECTED";
	public static final String COULD_NOT_CONNECT_INTENT = "com.donn.rootvpn.COULDNOTCONNECT";
	public static final String OFF_INTENT = "com.donn.rootvpn.OFF";
	public static final String DISCONNECTED_INTENT = "com.donn.rootvpn.DISCONNECTED";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		L.log(this, "Received Intent: " + intent.toString());
		
		intent.setClass(context, RootVPNService.class);
        context.startService(intent);
	}
}
