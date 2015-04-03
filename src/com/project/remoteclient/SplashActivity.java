package com.project.remoteclient;
import java.util.Timer;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
public class SplashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread timer=new Thread(){
        	public void run(){
        		try{
        			sleep(1000);
        		}
        		catch(Exception e){
        			e.printStackTrace();
        		}
        		finally{
        			Intent openmain= new Intent("com.project.remoteclient.INTRO");
        			startActivity(openmain);
        			
        		}
        		
        	}
        };
        timer.start();
        }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
    

}
