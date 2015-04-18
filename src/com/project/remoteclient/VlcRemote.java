package com.project.remoteclient;

import com.project.remoteclient.process.ClientSocket;
import com.project.remoteprotocol.global.Buttons;
import com.project.remoteprotocol.global.Events;

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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class VlcRemote extends ActionBarActivity implements OnItemClickListener {
	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	private long mBackPressed;
	private DrawerLayout dl;
	private ListView lv;
	private String[] a;
	private ActionBarDrawerToggle drawerListener;
	ClientSocket clientSocket;
	ImageButton play,prev,next,stop,volup,voldown,mute,fullscreen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vlc);
		dl=(DrawerLayout)findViewById(R.id.drawer_layout);
		lv=(ListView)findViewById(R.id.left_drawer);
		a=getResources().getStringArray(R.array.Menu);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,a));
		lv.setOnItemClickListener(this);
		drawerListener=new ActionBarDrawerToggle(this, dl, R.drawable.drawericon, R.string.drawer_open, R.string.drawer_close);
		dl.setDrawerListener(drawerListener);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		clientSocket=new ClientSocket();
		play=(ImageButton)findViewById(R.id.ibtnVLCPlay);
		prev=(ImageButton)findViewById(R.id.ibtnVLCPrevious);
		next=(ImageButton)findViewById(R.id.ibtnVLCNext);
		stop=(ImageButton)findViewById(R.id.ibtnVLCStop);
		volup=(ImageButton)findViewById(R.id.ibtnVLCVolup);
		voldown=(ImageButton)findViewById(R.id.ibtnVLCVoldown);
		mute=(ImageButton)findViewById(R.id.ibtnVLCmute);
		fullscreen=(ImageButton)findViewById(R.id.ibtnVLCFullscreen);
		play.setOnClickListener(oclBtns);
		prev.setOnClickListener(oclBtns);
		next.setOnClickListener(oclBtns);
		stop.setOnClickListener(oclBtns);
		voldown.setOnClickListener(oclBtns);
		volup.setOnClickListener(oclBtns);
		mute.setOnClickListener(oclBtns);
		fullscreen.setOnClickListener(oclBtns);
		
	}
	OnClickListener oclBtns = new OnClickListener() {
	       @Override
	       public void onClick(View v) {
	    	   vibrate();
	    	   switch( v.getId())
	   		{
	   		case R.id.ibtnVLCPlay:
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_VLC_PLAY);
	   			break;
	   		case R.id.ibtnVLCPrevious:
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_VLC_PREVIOUS);
	   			break;
	   		case R.id.ibtnVLCNext:
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_VLC_NEXT);
	   			break;
	   		case R.id.ibtnVLCStop:
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_VLC_STOP);
	   			break;
	   		case R.id.ibtnVLCVolup:
	   			clientSocket.send(Events.COMBINATION_BUTTON_PRESS +","+Buttons.KEY_VLC_CONTROL+","+Buttons.KEY_VLC_VOLUP);
	   			break;
	   		case R.id.ibtnVLCVoldown:
	   			clientSocket.send(Events.COMBINATION_BUTTON_PRESS +","+Buttons.KEY_VLC_CONTROL+","+Buttons.KEY_VLC_VOLDOWN);
	   			break;
	   		case R.id.ibtnVLCmute:
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_VLC_MUTE);
	   			break;
	   		case R.id.ibtnVLCFullscreen:
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_VLC_FULLSCREEN);
	   			break;
	   		}
	       }
	     };

	     


	 	
	 	//function for vibration
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
				Intent a=new Intent(this,MouseActivity.class);
				startActivity(a);
				break;
			case 1:
				Intent b=new Intent(this,PowerPointRemoteActivity.class);
				startActivity(b);
				break;
			case 2:
				Intent c=new Intent(this,VlcRemote.class);
				startActivity(c);
				break;
			//case 3:
				//Intent j=new Intent(this,joypadActivity.class);
				//startActivity(j);
				//break;
			case 4:
				Intent d=new Intent(this,Prefs.class);
				startActivity(d);
				break;
			case 5:
				Intent e=new Intent(this,HelpActivity.class);
				startActivity(e);
				break;
			case 6:
				Intent f=new Intent(this,About.class);
				startActivity(f);
				break;
			case 7:
				Intent g=new Intent(this,Intro.class);
				startActivity(g);
				break;
			case 8:
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



