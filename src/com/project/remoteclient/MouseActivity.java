package com.project.remoteclient;


import com.project.remoteclient.process.ClientSocket;
import com.project.remoteclient.process.MouseClientProcess;

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
import android.support.v7.*;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.project.remoteprotocol.global.Buttons;
import com.project.remoteprotocol.global.Events;
public class MouseActivity extends ActionBarActivity implements android.widget.AdapterView.OnItemClickListener  {
	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	private long mBackPressed;
	private DrawerLayout dl;
	private ListView lv;
	private String[] a;
	private ActionBarDrawerToggle drawerListener;
	ClientSocket client;
	MouseClientProcess mouseClientProcess;
	Button btnLeftClick,btnRightClick;
	ImageButton btnMousepad,btnToggleKeyboard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mouse);
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
		mouseClientProcess= new MouseClientProcess();
		btnMousepad=(ImageButton) findViewById(R.id.btnmouse);		
		btnToggleKeyboard=(ImageButton) findViewById(R.id.btnKeyBoard);
		btnLeftClick=(Button) findViewById(R.id.btnLeftClick);
		btnRightClick=(Button) findViewById(R.id.btnRightClick);
	
	
		btnLeftClick.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = (event.getAction() & MotionEvent.ACTION_MASK);
				switch(action)
				{
				case MotionEvent.ACTION_UP:
					 client.send(Events.MOUSE_BUTTON_UP +","+Buttons.MOUSE_BUTTON_LEFT);
					break;
				case MotionEvent.ACTION_DOWN:
					client.send(Events.MOUSE_BUTTON_DOWN +","+Buttons.MOUSE_BUTTON_LEFT);
					vibrate();
					break;
					
				}
				return false;
			}
		});
		btnRightClick.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = (event.getAction() & MotionEvent.ACTION_MASK);
				switch(action)
				{
				case MotionEvent.ACTION_UP:
					 client.send(Events.MOUSE_BUTTON_UP +","+Buttons.MOUSE_BUTTON_RIGHT);
					break;
				case MotionEvent.ACTION_DOWN:
					client.send(Events.MOUSE_BUTTON_DOWN +","+Buttons.MOUSE_BUTTON_RIGHT);
					SharedPreferences vib=PreferenceManager.getDefaultSharedPreferences(getBaseContext());			    	   
			    	vibrate();
					break;
					
				}
				return false;
			}
		});
		btnToggleKeyboard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, 0);
				
			}
		});
		btnMousepad.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent motionEvent) {
				
				int action = (motionEvent.getAction() & MotionEvent.ACTION_MASK);
				 
			mouseClientProcess.updateCoordinate( motionEvent.getX(),  motionEvent.getY());
				     switch (action){		
				     case MotionEvent.ACTION_DOWN:
				    	 vibrate();
				    	 break;
				     case MotionEvent.ACTION_MOVE:								    
				     case MotionEvent.ACTION_UP:								    
				     case MotionEvent.ACTION_POINTER_UP:								    
				     case MotionEvent.ACTION_CANCEL:		
				    	 
				    	 if (mouseClientProcess.shouldDataBeSend())				
				    	 client.send(Events.MOUSE_MOVE +","+Integer.toString(mouseClientProcess.getX_difference()) + ","+
				    			 		  Integer.toString(mouseClientProcess.getY_difference()));				    	 
				    	 
				    	 
				      break;
				     
				     }
				return false;
			}
		});
		
	}
	
	

	
	
	
	

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 vibrate();
		client.send(Events.KEYBORD_KEY_DOWN+","+keyCode);
		return super.onKeyDown(keyCode, event);
	}
	
	


	
		
			
		
		
	
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
			Intent a=new Intent(MouseActivity.this,MouseActivity.class);
			startActivity(a);
			break;
		case 1:
			Intent b=new Intent(MouseActivity.this,PowerPointRemoteActivity.class);
			startActivity(b);
			break;
		case 2:
			Intent c=new Intent(MouseActivity.this,VlcRemote.class);
			startActivity(c);
			break;
		case 3:
			Intent j=new Intent(MouseActivity.this,joypadactivity.class);
			startActivity(j);
			break;
		case 4:
			Intent d=new Intent(MouseActivity.this,Prefs.class);
			startActivity(d);
			break;
		case 5:
			Intent e=new Intent(MouseActivity.this,HelpActivity.class);
			startActivity(e);
			break;
		case 6:
			Intent f=new Intent(MouseActivity.this,About.class);
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
	
