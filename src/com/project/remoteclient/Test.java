package com.project.remoteclient;

import java.text.BreakIterator;

import com.project.remoteclient.process.ClientSocket;
import com.project.remoteprotocol.global.Buttons;
import com.project.remoteprotocol.global.Events;

import android.app.Activity;
import android.app.Notification.Action;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Test extends Activity{
	ImageView img1,img2;
	ClientSocket clientSocket;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		clientSocket= new ClientSocket ();
		img1= (ImageView) findViewById(R.id.imageView1);
		img2= (ImageView) findViewById(R.id.imageView2);
		img1.setOnTouchListener(otl );
		img2.setOnTouchListener(otl );
			
		
	}
	OnTouchListener otl=new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = (event.getAction() );
			int a;
			
			if (v.getId()==R.id.imageView1)
			{a=Buttons.KEY_LEFT;}
			else
			{a=Buttons.KEY_UP;}
				switch (action) {
			/*	case MotionEvent.ACTION_DOWN:
				//case MotionEvent.ACTION_POINTER_DOWN:
					clientSocket.send(Events.BUTTON_PRESS+","+a);
					break;*/
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
				//case MotionEvent.ACTION_POINTER_UP:
					clientSocket.send("asdfadsf");//Events.BUTTON_RELEASE+","+a);
					break;
				}
			
			
			return false;
		}
	};
	
	

}
