package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class HBoxDownloadForm extends ActivityGroup {

	private Handler mHandler = new Handler(); 
	private HTTPFileTransfer writetest;
	int NumberOfPasses = 0;
	
	private Runnable mHTTPConnectRunnable = new Runnable() {     
		public void run() 
		{         			    
	        
			String HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+"/connected.asp", getApplicationContext());			
	        if (HTTPageFilesize.length()==4)
	        {
        		String WhatEver = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+"/unload.asp", getApplicationContext());
	        	mHandler.postDelayed(mWaitRunnable, 100);
	        }
	        else
	        {
	        	TextView tvMsg1 = (TextView) findViewById(R.id.textViewMsg1);
	        	TextView tvMsg2 = (TextView) findViewById(R.id.textViewMsg2);
	        	TextView tvLot = (TextView) findViewById(R.id.textViewLotNumber);
	        	tvMsg1.setText("Unable to connect");
	        	tvMsg2.setText("to website.");
	        	tvLot.setText("Please Retry");
	            Button retry = (Button) findViewById(R.id.buttonRetry);
	            retry.setVisibility(1);
	        }
		}
	}; 
	
	private Runnable mWaitRunnable = new Runnable() {     
		public void run() 
		{         			    
            GetDate.GetDateTime(getApplicationContext());
            String CurrentLotNumber = WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal,getApplicationContext()).trim() + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal,getApplicationContext()) + ".HON";
            String CurrentRatesNumber = WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal,getApplicationContext()).trim() + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal,getApplicationContext()) + ".RTE";
            //String CurrentRatesNumber = WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal,getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal,getApplicationContext()) + ".HON";
            String Downloaded = "NOPE";
            
			//Downloaded = writetest.DownloadHBoxFile(CurrentLotNumber, "whbox/" + CurrentLotNumber, getApplicationContext());
            Downloaded = writetest.DownloadHBoxFile(CurrentRatesNumber, "whbox/" + CurrentRatesNumber, getApplicationContext());
            Downloaded = writetest.DownloadHBoxFile(CurrentLotNumber, "whbox/" + CurrentLotNumber, getApplicationContext());
			//Toast.makeText(getApplicationContext(), Downloaded, Toast.LENGTH_LONG).show();
			if (Downloaded.equals("NEW"))
			{
				NumberOfPasses = IOHonorFile.HowManyPasses(CurrentLotNumber, getApplicationContext());
				WorkingStorage.StoreLongVal(Defines.EditHonorBoxVal, 0, getApplicationContext());
			   	if (ProgramFlow.GetNextRatesForm(getApplicationContext()) != "HBOXWTF")
			    {
			  		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			   		startActivityForResult(myIntent, 0);
			   		finish();
			   		overridePendingTransition(0, 0);    
			    }
			}
			else if (Downloaded.equals("SUCCESS"))
			{
				NumberOfPasses = IOHonorFile.HowManyPasses(CurrentLotNumber, getApplicationContext());
				WorkingStorage.StoreLongVal(Defines.EditHonorBoxVal, 0, getApplicationContext());
			   	if (ProgramFlow.GetNextRatesForm(getApplicationContext()) != "HBOXWTF")
			    {
			  		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			   		startActivityForResult(myIntent, 0);
			   		finish();
			   		overridePendingTransition(0, 0);    
			    }
			}
			else
			{
				TextView tvMsg1 = (TextView) findViewById(R.id.textViewMsg1);
	        	TextView tvMsg2 = (TextView) findViewById(R.id.textViewMsg2);
	        	TextView tvLot = (TextView) findViewById(R.id.textViewLotNumber);
	        	tvMsg1.setText("Unable to connect");
	        	tvMsg2.setText("to website.");
	        	tvLot.setText("Please Retry");
	            Button retry = (Button) findViewById(R.id.buttonRetry);
	            retry.setVisibility(1);
			}
			//writetest.DownloadFileBinary(CurrentRatesNumber, "whbox/" + CurrentRatesNumber, getApplicationContext());
	        
		}
	}; 

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hboxdownloadform);      
       
        Button esc = (Button) findViewById(R.id.buttonESC);
        esc.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	    CustomVibrate.VibrateButton(getApplicationContext());
           		Defines.clsClassName = HBoxSelectLotForm.class ;
           		Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
           		startActivityForResult(myIntent, 0);  
           		finish();
           		overridePendingTransition(0, 0);
           }          
        });

        final TextView tvLot = (TextView) findViewById(R.id.textViewLotNumber);
        tvLot.setText("Lot # "+WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal, getApplicationContext()));

        
        final Button retry = (Button) findViewById(R.id.buttonRetry);
        retry.setVisibility(View.INVISIBLE);
        retry.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
	        	TextView tvMsg1 = (TextView) findViewById(R.id.textViewMsg1);
	        	TextView tvMsg2 = (TextView) findViewById(R.id.textViewMsg2);
	        	tvMsg1.setText("Retrieving Previous");
	        	tvMsg2.setText("Honorbox Checks for");
	        	tvLot.setText("Lot # "+WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal, getApplicationContext()));
	            retry.setVisibility(View.INVISIBLE);
	            mHandler.postDelayed(mHTTPConnectRunnable, 40);
           }          
        }); 
        
        writetest = new HTTPFileTransfer();
        mHandler.postDelayed(mHTTPConnectRunnable, 40);
	}
}