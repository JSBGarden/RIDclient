package com.project.remoteclient;


import com.project.remoteclient.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SetPreferences extends PreferenceActivity {

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  super.onCreate(savedInstanceState);
  addPreferencesFromResource(R.xml.prefs);
 }
}