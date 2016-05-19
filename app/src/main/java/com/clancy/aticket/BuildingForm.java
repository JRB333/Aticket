package com.clancy.aticket;


import java.io.File;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class BuildingForm extends ActivityGroup {
	private Handler mHandler = new Handler(); 
	
	private Runnable mExpand = new Runnable() {     
		public void run() 
		{         			    
			IOHonorFile.ExpandHonorList(WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal, getApplicationContext()), getApplicationContext());
	  		Defines.clsClassName = HBoxCameraForm.class ;
	  		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	  		startActivityForResult(myIntent, 0);
    	    finish(); //Don't call finish for UnitNumber Form
		}
	}; 
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildingform);
        
       	mHandler.postDelayed(mExpand, 20);

	}
}



