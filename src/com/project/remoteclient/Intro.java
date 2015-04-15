package com.project.remoteclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.project.remoteclient.process.ClientSocket;
import com.project.remoteclient.process.MouseClientProcess;
import com.project.remoteclient.process.Status;
import com.project.remoteprotocol.global.Buttons;
import com.project.remoteprotocol.global.Events;


public class Intro extends Activity {
	ClientSocket client;
	int port=8081;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		client=new ClientSocket();
		Button connect;
		final EditText ipa,pass;
		connect=(Button)findViewById(R.id.btnConnectPC);
		ipa=(EditText)findViewById(R.id.txtIpAddress);
		pass=(EditText)findViewById(R.id.txtPassword);
		
		connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {	
				
				SharedPreferences setData=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				setData.edit().putString("ip", ipa.getText().toString()).commit();


				client.connect(ipa.getText().toString(), port,pass.getText().toString());
				try{
					if (Status.isconnected==true)		
						Toast.makeText(getApplicationContext(), "Connection successful", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
					}
					catch(Exception e){
						e.printStackTrace();
						//Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
					}

				Intent i=new Intent(Intro.this,MenuActivity.class);
				startActivity(i);

			}
		});
		
		SharedPreferences getData=PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		String et=getData.getString("ip", "");
		
		boolean remember=getData.getBoolean("checkbox", true);
		if(remember==true){
			ipa.setText(et);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.aboutUs:
			Intent i=new Intent("com.project.remoteclient.ABOUT");
			startActivity(i);
			break;
		case R.id.preferences:
			Intent p=new Intent("com.project.remoteclient.PREFS");
			startActivity(p);
			break;
		case R.id.Help:
			Intent h=new Intent("com.project.remoteclient.HELPACTIVITY");
			startActivity(h);
		case R.id.exit:
			finish();
			break;	



		}
		return false;
	}

	@Override
	public boolean onMenuOpened(int featureId, android.view.Menu menu) {
		// TODO Auto-generated method stub
		return super.onMenuOpened(featureId, menu);

	}

}