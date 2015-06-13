package com.project.remoteclient;

import com.project.remoteclient.process.ClientSocket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class HelpActivity extends ActionBarActivity implements android.widget.AdapterView.OnItemClickListener {
	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	private long mBackPressed;
	private DrawerLayout dl;
	private ListView lv;
	private String[] a;
	private ActionBarDrawerToggle drawerListener;
	//check if pressing can cause click
	private Boolean canClick=false;
	private int time;
	ClientSocket client;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		dl=(DrawerLayout)findViewById(R.id.drawer_layout);
		lv=(ListView)findViewById(R.id.left_drawer);
		a=getResources().getStringArray(R.array.MenuException);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,a));
		lv.setOnItemClickListener(this);
		drawerListener=new ActionBarDrawerToggle(this, dl, R.drawable.drawericon, R.string.drawer_open, R.string.drawer_close);
		dl.setDrawerListener(drawerListener);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TabHost th=(TabHost)findViewById(R.id.tabhost);
		th.setup();
		TabSpec specs=th.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Mouse And Keyboard");
		th.addTab(specs);
		specs=th.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("PowerPoint Remote");
		th.addTab(specs);
		specs=th.newTabSpec("tag3");
		specs.setContent(R.id.tab3);
		specs.setIndicator("VLC Remote");
		th.addTab(specs);
	}

	private void vibrate(){
 		SharedPreferences vib=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
 	   boolean vibenable=vib.getBoolean("vibrate", true);
 	   if(vibenable==true){
 		   Vibrator vibe=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);	
  			vibe.vibrate(100);
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
		//Toast.makeText(this, a[position], Toast.LENGTH_LONG).show();
		selectItem(position);
		
	}
	public void selectItem(int position) {
		lv.setItemChecked(position, true);
		//setTitle(a[position]);
		switch(position){
		case 0:
			Intent a=new Intent(HelpActivity.this,MouseActivity.class);
			startActivity(a);
			break;
		case 1:
			Intent b=new Intent(HelpActivity.this,PowerPointRemoteActivity.class);
			startActivity(b);
			break;
		case 2:
			Intent c=new Intent(HelpActivity.this,VlcRemote.class);
			startActivity(c);
			break;
		case 3:
			Intent j=new Intent(HelpActivity.this,joypadactivity.class);
			startActivity(j);
			break;
		case 4:
			Intent d=new Intent(HelpActivity.this,Prefs.class);
			startActivity(d);
			break;
		case 5:
			Intent e=new Intent(HelpActivity.this,HelpActivity.class);
			startActivity(e);
			break;
		case 6:
			Intent f=new Intent(HelpActivity.this,About.class);
			startActivity(f);
			break;
		case 7:
			Intent intent = new Intent(getApplicationContext(), Intro.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("EXIT", true);
			startActivity(intent);
			break;
			default:
		
			
		}
		dl.closeDrawer(lv);
		
	}
	
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}
	
	
	@Override
	public void onBackPressed()
	{
	    if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) 
	    { 
	        super.onBackPressed(); 
	        Intent intent = new Intent(getApplicationContext(), Intro.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("EXIT", true);
			startActivity(intent);
	        return;
	    }
	    else { Toast.makeText(getBaseContext(), "Tap back button again in order to exit", Toast.LENGTH_SHORT).show(); }

	    mBackPressed = System.currentTimeMillis();
	}
	

}
