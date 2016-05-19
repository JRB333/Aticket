package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class IPAddressForm extends ActivityGroup {
	
	   private void EnterClick()
	    {
    	   EditText NewIpAddress = (EditText)findViewById(R.id.editTextIP);
    	   String cNewIpAddress = NewIpAddress.getText().toString();
    	   if (cNewIpAddress != ""){
			   // --- Save Off New Client Name for 'Change Client' Check (in RefreshForm) ---
			   int nSlashLoc = cNewIpAddress.indexOf("/",0);
			   String NewClientName = cNewIpAddress.substring((nSlashLoc + 1),cNewIpAddress.length());
			   WorkingStorage.StoreCharVal(Defines.NewClientName, NewClientName, getApplicationContext());

			   // --- Save Off Current Client Name for 'Change Client' Check (in RefreshForm) ---
			   String CurrentIP = WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext());
			   nSlashLoc = CurrentIP.indexOf("/",0);
			   String CurrentClientName = CurrentIP.substring((nSlashLoc + 1),CurrentIP.length());
			   WorkingStorage.StoreCharVal(Defines.PreviousClientName, CurrentClientName, getApplicationContext());

			   WorkingStorage.StoreCharVal(Defines.UploadIPAddress, cNewIpAddress, getApplicationContext());
           	   Defines.clsClassName = RefreshForm.class ;
			   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			   startActivityForResult(myIntent, 0);
			   finish();
			   overridePendingTransition(0, 0);         		   
    	   }
	    }
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipaddressform); 
       
        Button btnESC = (Button) findViewById(R.id.buttonESC);
        btnESC.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	CustomVibrate.VibrateButton(getApplicationContext());
            finish();
            overridePendingTransition(0, 0);
           }           
        });
        
        Button enter = (Button) findViewById(R.id.buttonEnter);
        enter.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) 
             {       	    
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   EnterClick();
             }          
        });

        EditText NewIpAddress = (EditText)findViewById(R.id.editTextIP);
        
        if (savedInstanceState == null) //Must do this to ensure that rotation doesn't jack everything up
        {
    	   String TempString = WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext());
    	   NewIpAddress.setText(TempString);
    	   NewIpAddress.selectAll();
    	   NewIpAddress.requestFocus();
        }
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
    	CustomKeyboard.PickAKeyboard(NewIpAddress, "IPKEY", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		NewIpAddress.selectAll();
        }
        NewIpAddress.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});   	   

        /*NewIpAddress.setOnKeyListener(new View.OnKeyListener() {     	  
         	  public boolean onKey(View arg0, int arg1, KeyEvent event) {
       		  if ( (event.getAction() == KeyEvent.ACTION_UP ) && (arg1 == KeyEvent.KEYCODE_ENTER))
       		  { 
       			  EnterClick(arg0);
       			  return true;
       		  }
     		  	  return false;
         	  }     	   
         } );*/  
     
	}
}