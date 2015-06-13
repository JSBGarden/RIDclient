package com.project.remoteclient;

import com.project.remoteclient.process.ClientSocket;
import com.project.remoteprotocol.global.Buttons;
import com.project.remoteprotocol.global.Events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;


public class joypadactivity extends ActionBarActivity implements android.widget.AdapterView.OnItemClickListener {

	int controlNumber;

	ClientSocket client;
	ToggleButton tb;
	ImageView ibtnUP,ibtnCross,ibtnStart,ibtnSelect,ibtnSquare,ibtnTriangle, ibtnDown,ibtnLeft,ibtnRight,ibtnCircle;

	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	private long mBackPressed;
	private DrawerLayout dl;
	private ListView lv;
	private String[] a;
	private ActionBarDrawerToggle drawerListener;
	//check if pressing can cause click
	private Boolean canClick=false;
	private int time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joypad);
		dl=(DrawerLayout)findViewById(R.id.drawer_layout);
		lv=(ListView)findViewById(R.id.left_drawer);
		a=getResources().getStringArray(R.array.Menu);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,a));
		lv.setOnItemClickListener(this);
		drawerListener=new ActionBarDrawerToggle(this, dl, R.drawable.drawericon, R.string.drawer_open, R.string.drawer_close);
		dl.setDrawerListener(drawerListener);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		client= new ClientSocket ();
		ibtnUP=(ImageView)findViewById(R.id.btnUp);
		ibtnCross=(ImageView)findViewById(R.id.btnCross);
		ibtnStart=(ImageView)findViewById(R.id.btnStart);
		ibtnSelect=(ImageView)findViewById(R.id.btnSelect);
		ibtnSquare=(ImageView)findViewById(R.id.btnSquare);
		ibtnTriangle=(ImageView)findViewById(R.id.btnTriangle);
		ibtnDown=(ImageView)findViewById(R.id.btnDown);
		ibtnLeft=(ImageView)findViewById(R.id.btnLeft);
		ibtnRight=(ImageView)findViewById(R.id.btnRight);
		ibtnCircle=(ImageView)findViewById(R.id.btnCircle);
		tb=(ToggleButton)findViewById(R.id.toggleButton1);
		ibtnUP.setOnTouchListener(otl);
		ibtnCross.setOnTouchListener(otl);
		ibtnStart.setOnTouchListener(otl);
		ibtnSelect.setOnTouchListener(otl);
		ibtnSquare.setOnTouchListener(otl);
		ibtnTriangle.setOnTouchListener(otl);
		ibtnDown.setOnTouchListener(otl);
		ibtnLeft.setOnTouchListener(otl);
		ibtnRight.setOnTouchListener(otl);
		ibtnCircle.setOnTouchListener(otl);
	}
	
	OnTouchListener otl=new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = (event.getAction() & MotionEvent.ACTION_MASK);
			int button=0;
			controlNumber=Integer.parseInt( tb.getText().toString())-1;

			if (controlNumber==0){
				
				switch(v.getId())
				{
				case R.id.btnUp:
					button=Buttons.JOY_PAD_UP;
					break;
				case R.id.btnDown:
					button=Buttons.JOY_PAD_DOWN;
					break;
				case R.id.btnCircle:
					button=Buttons.JOY_PAD_CIRCLE;
					break;
				case R.id.btnCross:
					button=Buttons.JOY_PAD_CROSS;
					break;
				case R.id.btnSquare:
					button=Buttons.JOY_PAD_SQUARE;
					break;
				case R.id.btnTriangle:
					button=Buttons.JOY_PAD_TRIANGLE;
					break;
				case R.id.btnStart:
					button=Buttons.JOY_PAD_START;
					break;
				case R.id.btnSelect:
					button=Buttons.JOY_PAD_SELECT;
					break;
				case R.id.btnRight:
					button=Buttons.JOY_PAD_RIGHT;
					break;
				case R.id.btnLeft:
					button=Buttons.JOY_PAD_LEFT;
					break;

				}
			}
			else
			{
				switch(v.getId())
				{
				case R.id.btnUp:
					button=Buttons.JOY_PAD_UP2;
					break;
				case R.id.btnDown:
					button=Buttons.JOY_PAD_DOWN2;
					break;
				case R.id.btnCircle:
					button=Buttons.JOY_PAD_CIRCLE2;
					break;
				case R.id.btnCross:
					button=Buttons.JOY_PAD_CROSS2;
					break;
				case R.id.btnSquare:
					button=Buttons.JOY_PAD_SQUARE2;
					break;
				case R.id.btnTriangle:
					button=Buttons.JOY_PAD_TRIANGLE2;
					break;
				case R.id.btnStart:
					button=Buttons.JOY_PAD_START2;
					break;
				case R.id.btnSelect:
					button=Buttons.JOY_PAD_SELECT2;
					break;
				case R.id.btnRight:
					button=Buttons.JOY_PAD_RIGHT2;
					break;
				case R.id.btnLeft:
					button=Buttons.JOY_PAD_LEFT2;
					break;

				}
			}




			switch(action)

			{
			case MotionEvent.ACTION_UP:
				
				client.send(Events.BUTTON_RELEASE+","+button);
				break;
			case MotionEvent.ACTION_DOWN:
				vibrate();
				client.send(Events.BUTTON_PRESS+","+button);
				break;
			}
			return false;

		}};



		private void vibrate(){
	 		SharedPreferences vib=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	 	   boolean vibenable=vib.getBoolean("vibrate", true);
	 	   if(vibenable==true){
	 		   Vibrator vibe=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);	
	  			vibe.vibrate(100);}

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
				Intent a=new Intent(joypadactivity.this,MouseActivity.class);
				startActivity(a);
				break;
			case 1:
				Intent b=new Intent(joypadactivity.this,PowerPointRemoteActivity.class);
				startActivity(b);
				break;
			case 2:
				Intent c=new Intent(joypadactivity.this,VlcRemote.class);
				startActivity(c);
				break;
			case 3:
				Intent j=new Intent(joypadactivity.this,joypadactivity.class);
				startActivity(j);
				break;
			case 4:
				Intent d=new Intent(joypadactivity.this,Prefs.class);
				startActivity(d);
				break;
			case 5:
				Intent e=new Intent(joypadactivity.this,HelpActivity.class);
				startActivity(e);
				break;
			case 6:
				Intent f=new Intent(joypadactivity.this,About.class);
				startActivity(f);
				break;
			case 7:
				Intent g=new Intent(this,Intro.class);
				client.disconnect();
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

