package com.project.remoteclient;

import com.project.remoteclient.process.ClientSocket;
import com.project.remoteprotocol.global.Buttons;
import com.project.remoteprotocol.global.Events;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;


public class joypadactivity extends Activity {

	int controlNumber;

	ClientSocket client;
	ToggleButton tb;
	ImageView ibtnUP,ibtnCross,ibtnStart,ibtnSelect,ibtnSquare,ibtnTriangle, ibtnDown,ibtnLeft,ibtnRight,ibtnCircle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joypad);
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
				client.send(Events.BUTTON_PRESS+","+button);
				break;
			}
			return false;

		}};





}

