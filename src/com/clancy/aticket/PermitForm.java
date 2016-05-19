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


public class PermitForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText Permit = (EditText) findViewById(R.id.editTextPermit);   
		  String newString = Permit.getText().toString();
		  
	      WorkingStorage.StoreCharVal(Defines.PrintPermitVal, newString,getApplicationContext());
	      WorkingStorage.StoreCharVal(Defines.SavePermitVal, newString, getApplicationContext());
		  
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
        setContentView(R.layout.permitform);
       
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

	   TextView tvPermitType = (TextView) findViewById(R.id.TextViewPermitType);
	   tvPermitType.setText("");
       EditText Permit = (EditText) findViewById(R.id.editTextPermit);
	   Permit.requestFocus();
	   
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(Permit, "FULL", getApplicationContext(), keyboardView);

   	   WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
   	   String PermitCode = "";
   	   String PermitTypeCode = "";
   	   if (!WorkingStorage.GetCharVal(Defines.AutoPermitVal,getApplicationContext()).equals(""))
   	   {
   		   PermitCode = WorkingStorage.GetCharVal(Defines.AutoPermitVal, getApplicationContext()).trim();
   		   Permit.setText(PermitCode);
   		   PermitTypeCode = WorkingStorage.GetCharVal(Defines.AutoPermitTypeVal, getApplicationContext()).trim();
   		   tvPermitType.setText(PermitTypeCode);
   	   }

       Permit.selectAll();
      		//state.setTextColor(Color.parseColor("#ff0000CC"));
   	   Permit.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      		});   	   

	   
	}
}