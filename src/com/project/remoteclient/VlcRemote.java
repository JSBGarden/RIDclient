package com.project.remoteclient;

import com.project.remoteclient.process.ClientSocket;
import com.project.remoteprotocol.global.Buttons;
import com.project.remoteprotocol.global.Events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class VlcRemote extends Activity {
	ClientSocket clientSocket;
	ImageButton play,prev,next,stop,volup,voldown,mute,fullscreen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vlc);
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
	 		case R.id.exit:
	 			finish();
	 			break;	
	 		
	 			
	 		
	 		}
	 		return false;
	 	}
}
