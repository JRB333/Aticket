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
import android.widget.TextView;


public class AddFeeForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText Number = (EditText) findViewById(R.id.editTextNumber);   
		  String newString = Number.getText().toString();
		  
	      WorkingStorage.StoreCharVal(Defines.PrintFeeVal, newString,getApplicationContext());
		  if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
		  {
			  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		   	  startActivityForResult(myIntent, 0);
		   	  finish();
		   	  overridePendingTransition(0, 0);    
		  }				  
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfeeform);
       
        Button esc = (Button) findViewById(R.id.buttonESC);
        esc.setOnClickListener(new View.OnClickListener() {
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

	   EditText Number = (EditText) findViewById(R.id.editTextNumber);
       if (savedInstanceState==null)
       {    	   
    	   Number.setText("0.00");
       }
	   Number.requestFocus();
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(Number, "NUMBERS", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Number.selectAll();
       }
   	   Number.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
       });   
   	   
   	   if (!WorkingStorage.GetCharVal(Defines.HBoxFlowVal, getApplicationContext()).equals("DONE"))
   	   {
   		   //Large Fine Change 3-8-2013
   		   //if (WorkingStorage.GetCharVal(Defines.PrintFine1Val, getApplicationContext()).equals("0.00"))
   		   if (WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, getApplicationContext()).equals("0.00"))
   		   {
   			   WorkingStorage.StoreCharVal(Defines.PrintFeeVal, "", getApplicationContext());
   		   }
 		   if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
		   {
			   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		   	   startActivityForResult(myIntent, 0);
		   	   finish();
		   	   overridePendingTransition(0, 0);    
		   }	
   	   }
	}
}