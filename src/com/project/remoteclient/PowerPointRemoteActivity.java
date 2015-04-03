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

public class PowerPointRemoteActivity extends Activity{
	
	
	ClientSocket clientSocket;
	ImageButton previous, next, home,exit,fullScreen,toggleBlackScreen,ok;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.power_point_remote);
		
		
		clientSocket=new ClientSocket();
		
		previous=(ImageButton) findViewById(R.id.ibtnPrevious);
		next=(ImageButton) findViewById(R.id.ibtnNext);
		home=(ImageButton) findViewById(R.id.ibtnHome);
		exit=(ImageButton) findViewById(R.id.ibtnExit);
		fullScreen=(ImageButton) findViewById(R.id.ibtnFullScreen);
		toggleBlackScreen=(ImageButton) findViewById(R.id.ibtnToggleBlack);
		ok=(ImageButton) findViewById(R.id.ibtnOk);
		
		previous.setOnClickListener(oclBtns);
		next.setOnClickListener(oclBtns);
		home.setOnClickListener(oclBtns);
		exit.setOnClickListener(oclBtns);
		fullScreen.setOnClickListener(oclBtns);
		toggleBlackScreen.setOnClickListener(oclBtns);
		ok.setOnClickListener(oclBtns);
		
		
	}
	
	
	
	OnClickListener oclBtns = new OnClickListener() {
	       @Override
	       public void onClick(View v) {	         
	    	   switch( v.getId())
	   		{
	   		case R.id.ibtnExit:
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_END);
	   			break;
	   		case R.id.ibtnHome:	   			
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_HOME);
	   			break;
	   		case R.id.ibtnPrevious:	   			
	   				clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_PREVIOUS);
	   			break;
	   		case R.id.ibtnNext:
	   				clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_NEXT);
	   			break;
	   		case R.id.ibtnFullScreen:
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_FULL_SCREEN);
	   			break;
	   		case R.id.ibtnToggleBlack:
	   			clientSocket.send(Events.SINGLE_BUTTON_PRESS +","+Buttons.KEY_TOGGLE_BLACK);
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
