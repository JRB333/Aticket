package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TempPlateForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText TempPlate = (EditText) findViewById(R.id.editTextPlate);   
		  String newString = TempPlate.getText().toString();
		  
		  if (newString.equals(""))
		  {			  
			  Messagebox.Message("Temporary Tag Number Required.",getApplicationContext());
		  }
		  else
		  {			 
	            WorkingStorage.StoreCharVal(Defines.SaveTempPlateVal, newString, getApplicationContext());
	            Intent myIntent = new Intent(getApplicationContext(), TempExpireForm.class);
				startActivityForResult(myIntent, 0);
				finish();
				overridePendingTransition(0, 0);           			
		  }
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempplateform);
       
        
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
      
       Button enter = (Button) findViewById(R.id.buttonEnter);
       enter.setOnClickListener(new View.OnClickListener() {
	           public void onClick(View view) {
	        	   CustomVibrate.VibrateButton(getApplicationContext());
	        	   EnterClick();	        	   
	           }	          
	   });

	   final EditText TempPlate = (EditText) findViewById(R.id.editTextPlate);   
	   TempPlate.requestFocus();
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(TempPlate, "LICENSE", getApplicationContext(), keyboardView);

   	   TempPlate.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      		});
	  
	}
}