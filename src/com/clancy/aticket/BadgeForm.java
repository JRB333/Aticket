package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class BadgeForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText Badge = (EditText) findViewById(R.id.editTextBadge);   
		  String newString = Badge.getText().toString();
		  if (newString.equals(""))
		  {
  			Messagebox.Message("Badge Name Required!",getApplicationContext());
			return;
		  }		  
	      //while (newString.length() < 5)
	        //	newString += " ";		  
	      WorkingStorage.StoreCharVal(Defines.PrintBadgeVal, " "+newString, getApplicationContext());
   	   	  if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
	      {
   	   		  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
   	   		  startActivityForResult(myIntent, 0);
   	   		  finish();
   	   		  overridePendingTransition(0, 0);    
	      }		  	      
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badgeform);
       
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

	   EditText Badge = (EditText) findViewById(R.id.editTextBadge);
       if (savedInstanceState==null)
       {    	   
       		WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
       		String tempString = WorkingStorage.GetCharVal(Defines.PrintBadgeVal,getApplicationContext()).trim();
       		if(!tempString.equals(""))
       		{
       			Badge.setText(tempString);
       		}
       }
	   Badge.requestFocus();

       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(Badge, "FULL", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Badge.selectAll();
      		//state.setTextColor(Color.parseColor("#ff0000CC"));
       }
   	   Badge.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      		});   	   

	   
	}
}