package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;


//The only purpose of this activity is to simply call another activity
public class SwitchForm extends ActivityGroup{
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = new Intent(getApplicationContext(), Defines.clsClassName);
        startActivityForResult(myIntent, 0);
        finish();
        overridePendingTransition(0, 0);
    }
}
