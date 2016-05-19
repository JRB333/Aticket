package com.clancy.aticket;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class OfflinePasswordForm extends ActivityGroup {
	
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offlinepasswordform);
        
        if (WorkingStorage.GetCharVal(Defines.UseOfflinePasswordVal, getApplicationContext()).equals(""))
   		 	WorkingStorage.StoreCharVal(Defines.UseOfflinePasswordVal, "TOWERDOWN", getApplicationContext()); // default to towerdown unless there is something else in custom.a 
        
        Button btnESC = (Button) findViewById(R.id.buttonESC);
        btnESC.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	CustomVibrate.VibrateButton(getApplicationContext());
     	   	Defines.clsClassName = AticketActivity.class ;
     	   	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
     	   	startActivityForResult(myIntent, 0);  
            finish();
            overridePendingTransition(0, 0);           	
           }
          
        });
        
        TextView message = (TextView) findViewById(R.id.TextMessage);
        message.setText("");
       
        final EditText password = (EditText) findViewById(R.id.editTextPassword);
        Button enter = (Button) findViewById(R.id.buttonEnter);
        enter.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) 
           {
       		String newString = password.getText().toString().toUpperCase();
	  		if (newString.equals(""))
		  	{
	  			TextView message = (TextView) findViewById(R.id.TextMessage);
	  	        message.setText("Password Required");
		  		password.setText("");	
        		Vibrator vibKey = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
           		vibKey.vibrate(500);		  		
		  	} 
	  		else
	  		{
	  			String tempme = WorkingStorage.GetCharVal(Defines.UseOfflinePasswordVal, getApplicationContext());
	  			if (WorkingStorage.GetCharVal(Defines.UseOfflinePasswordVal, getApplicationContext()).equals(newString))
	  			{
	  				WorkingStorage.StoreCharVal(Defines.UseOfflineVal, "OFFLINE", getApplicationContext());
	  	        	CustomVibrate.VibrateButton(getApplicationContext());
	  	     	   	Defines.clsClassName = AticketActivity.class ;
	  	     	   	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	  	     	   	startActivityForResult(myIntent, 0);  
	  	            finish();
	  	            overridePendingTransition(0, 0);   	  				
	  			}
	  			else
	  			{
		  			TextView message = (TextView) findViewById(R.id.TextMessage);
		  	        message.setText("Invalid Password");
			  		password.setText("");	
	        		Vibrator vibKey = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
	           		vibKey.vibrate(500);		  				
	  			}
	  		}
           }          
        });
        
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
    	CustomKeyboard.PickAKeyboard(password, "FULL", getApplicationContext(), keyboardView);

    	password.setOnTouchListener(new View.OnTouchListener(){ 
		@Override
		public boolean onTouch(View v, MotionEvent event) {
    		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
    		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    		return true;
		} 
   		});
	}
}