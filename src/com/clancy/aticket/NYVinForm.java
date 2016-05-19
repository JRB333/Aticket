package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class NYVinForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText vin = (EditText) findViewById(R.id.editTextVin);   
		  String newString = vin.getText().toString();
		  
		  if (newString.length() != 17)
		  {
			  Messagebox.Message("VIN length is too short",getApplicationContext());
			  //vin.requestFocus();
		  }
//		  else
//		  {			 
	            WorkingStorage.StoreCharVal(Defines.PrintNYVinVal, newString, getApplicationContext());
	            WorkingStorage.StoreCharVal(Defines.SaveNYVinVal, newString, getApplicationContext());
 			    if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
			    {
				   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				   startActivityForResult(myIntent, 0);
				   finish();
				   overridePendingTransition(0, 0);           			
			    }
	//	  }
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nyvinform);
       
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

	   EditText vin = (EditText) findViewById(R.id.editTextVin);   
	   //vin.requestFocus();
	   //vin.setCursorVisible(false);
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(vin, "LICENSE", getApplicationContext(), keyboardView);

   	   vin.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      		});
	}
}