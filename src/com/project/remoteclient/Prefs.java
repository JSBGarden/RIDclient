package com.project.remoteclient;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class Prefs extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
	}
	@Override
	public void onBackPressed()
	{
	    super.onBackPressed();
	    finish();
	}
}
