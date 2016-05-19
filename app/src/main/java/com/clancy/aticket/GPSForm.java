package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class GPSForm extends ActivityGroup {
	private Handler mHandler = new Handler(); 
	
	private Runnable mWaitRunnable = new Runnable() {     
				
		public void run() 
		{         			    
			TextView tvLat = (TextView) findViewById(R.id.TextViewLat);
		    TextView tvLong = (TextView) findViewById(R.id.TextViewLong);
			Button enter = (Button) findViewById(R.id.buttonEnter);
		    Button mapIt = (Button) findViewById(R.id.ButtonMap);
		    Button storeLast = (Button) findViewById(R.id.ButtonLast);
		    
			if (WorkingStorage.GetCharVal(Defines.IsGPSOnOrOFFVal, getApplicationContext()).equals("OFF"))
			{
				tvLat.setText("GPS Hardware is OFF");
				tvLong.setText("Turn Back ON to Continue!");
		    	enter.setEnabled(false);
		    	mapIt.setEnabled(false);
		    	storeLast.setEnabled(false);
			}
			else
			{

			    tvLat.setText("Lat: "+WorkingStorage.GetCharVal(Defines.LatitudeVal, getApplicationContext()));	     
			    tvLong.setText("Long:  "+WorkingStorage.GetCharVal(Defines.LongitudeVal, getApplicationContext()));
			    if (WorkingStorage.GetCharVal(Defines.LatitudeVal, getApplicationContext()).equals(""))
			    {
					tvLat.setText("Getting Satellite Lock...");
					tvLong.setText("");
			    }

			    if (WorkingStorage.GetCharVal(Defines.LatitudeVal, getApplicationContext()).equals(""))
			    {
			    	enter.setEnabled(false);
			    	mapIt.setEnabled(false);
			    	if (WorkingStorage.GetCharVal(Defines.LastLatitudeVal, getApplicationContext()).equals(""))
			    	{
			    		storeLast.setEnabled(false);	
			    	}	
			    	else
			    	{
			    		storeLast.setEnabled(true);
			    	}
			    }
			    else
			    {
			    	enter.setEnabled(true);
			    	mapIt.setEnabled(true);
			    	storeLast.setEnabled(false);
			    }
			}

		    


			mHandler.postDelayed(this, 500);  		        
		}
	}; 
	 
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpsform);

       
        Button second = (Button) findViewById(R.id.buttonESC);
        second.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   Defines.clsClassName = SelFuncForm.class ;
               Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
               startActivityForResult(myIntent, 0);  
               finish();
               overridePendingTransition(0, 0);
          }          
        });

		Button mapIt = (Button) findViewById(R.id.ButtonMap);
		mapIt.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View view) {
				  Intent myIntent = new Intent(getApplicationContext(), GoogleMapsForm.class);
			   	  startActivityForResult(myIntent, 0);
	        }	          
	     });
		
		Button enter = (Button) findViewById(R.id.buttonEnter);
	    enter.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View view) {
	    	   CustomVibrate.VibrateButton(getApplicationContext());
				  if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
				  {
					  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				   	  startActivityForResult(myIntent, 0);
				   	  finish();
				   	  overridePendingTransition(0, 0);    
				  }	
	        }	          
	     });
	    
		Button storelast = (Button) findViewById(R.id.ButtonLast);
		storelast.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View view) {
	    	   CustomVibrate.VibrateButton(getApplicationContext());
	    	   WorkingStorage.StoreCharVal(Defines.LatitudeVal, WorkingStorage.GetCharVal(Defines.LastLatitudeVal, getApplicationContext()),  getApplicationContext()); 
	    	   WorkingStorage.StoreCharVal(Defines.LongitudeVal, WorkingStorage.GetCharVal(Defines.LastLongitudeVal, getApplicationContext()),  getApplicationContext());
				  if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
				  {
					  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				   	  startActivityForResult(myIntent, 0);
				   	  finish();
				   	  overridePendingTransition(0, 0);    
				  }	
	        }	          
	     });
		
	     enter.requestFocus();
	     
		 TextView tvCiteKey = (TextView) findViewById(R.id.TextViewKey);
		 tvCiteKey.setText("Key #: "+WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()));

		 
	     mHandler.postDelayed(mWaitRunnable, 20);
	 }

}