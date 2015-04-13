package com.project.remoteclient;

import com.project.remoteclient.process.status;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MenuActivity extends ListActivity{
	
	String list[][]={{"MouseActivity","PowerPointRemoteActivity","VlcRemote"},{"Mouse and Keyboard","Power-Point Remote","Vlc Remote"}};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1 ,list[1]));
		if (status.isconnected==true)		
			Toast.makeText(getApplicationContext(), "Connection successful", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
			
	}
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String loadlist=list[0][position];
		try{	
		Class myclass=Class.forName("com.project.remoteclient."+loadlist);
		Intent myIntent=new Intent(MenuActivity.this,myclass);
		startActivity(myIntent);
		//startActivityForResult(myIntent, 6);
			
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
			}
		}


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
		case R.id.Help:
			Intent h=new Intent("com.project.remoteclient.HELPACTIVITY");
			startActivity(h);
		case R.id.exit:
			finish();
			break;	
		
		}
		return false;
	}
		

	

}
