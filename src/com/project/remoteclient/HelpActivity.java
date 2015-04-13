package com.project.remoteclient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
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
	

}
