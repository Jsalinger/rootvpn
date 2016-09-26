package com.donn.rootvpn;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

public class RootVPNActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RootVPNPreferences preferencesFragment = new RootVPNPreferences();
		
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().add(android.R.id.content, preferencesFragment, "Preferences").commit();
		
		Intent intent = new Intent();
		intent.setClass(this, RootVPNService.class);
        startService(intent);
        
        setContentView(R.layout.main);
	}
	
}
