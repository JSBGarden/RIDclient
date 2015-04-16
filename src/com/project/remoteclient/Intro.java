package com.project.remoteclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.project.remoteclient.process.ClientSocket;
import com.project.remoteclient.process.MouseClientProcess;
import com.project.remoteclient.process.Status;
import com.project.remoteprotocol.global.Buttons;
import com.project.remoteprotocol.global.Events;


public class Intro extends ActionBarActivity implements OnItemClickListener {
	private DrawerLayout dl;
	private ListView lv;
	private String[] a;
	private ActionBarDrawerToggle drawerListener;
	ClientSocket client;
	int port=8081;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		dl=(DrawerLayout)findViewById(R.id.drawer_layout);
		lv=(ListView)findViewById(R.id.left_drawer);
		a=getResources().getStringArray(R.array.Menu);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,a));
		lv.setOnItemClickListener(this);
		drawerListener=new ActionBarDrawerToggle(this, dl, R.drawable.drawericon, R.string.drawer_open, R.string.drawer_close);
		dl.setDrawerListener(drawerListener);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		client=new ClientSocket();
		Button connect;
		final EditText ipa,pass;
		connect=(Button)findViewById(R.id.btnConnectPC);
		ipa=(EditText)findViewById(R.id.txtIpAddress);
		pass=(EditText)findViewById(R.id.txtPassword);

		connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// open other activity only if connected to wifi or not
				//if (isWifiConnected())
				{
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
/*				else
				{
					Toast.makeText(getApplicationContext(), "Wifi Not connected", Toast.LENGTH_SHORT).show();
				}*/

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
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(drawerListener.onOptionsItemSelected(item))
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerListener.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onMenuOpened(int featureId, android.view.Menu menu) {
		// TODO Auto-generated method stub
		return super.onMenuOpened(featureId, menu);

	}



	//check if the device connected to wifinetwork or not	
	private Boolean isWifiConnected(){
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected(); 


	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		Toast.makeText(this, a[position], Toast.LENGTH_LONG).show();
		selectItem(position);
		
	}
	public void selectItem(int position) {
		lv.setItemChecked(position, true);
		setTitle(a[position]);
		switch(position){
		case 0:
			Intent a=new Intent(Intro.this,MouseActivity.class);
			startActivity(a);
			break;
		case 1:
			Intent b=new Intent(Intro.this,PowerPointRemoteActivity.class);
			startActivity(b);
			break;
		case 2:
			Intent c=new Intent(Intro.this,VlcRemote.class);
			startActivity(c);
			break;
		case 3:
			Intent d=new Intent(Intro.this,Prefs.class);
			startActivity(d);
			break;
		case 4:
			Intent e=new Intent(Intro.this,HelpActivity.class);
			startActivity(e);
			break;
		case 5:
			Intent f=new Intent(Intro.this,About.class);
			startActivity(f);
			break;
		case 6:
			finish();
			break;
			default:
		}
		
	}
	public void setTitle(String title){
		getSupportActionBar().setTitle(title);
	
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}
	
	
}