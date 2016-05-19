package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class BeatForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText Beat = (EditText) findViewById(R.id.editTextbeat); 
  		  Editable tempS = Beat.getText();
		  String newString = tempS.toString();		  
		  
		  if( WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("READING"))
		  {
	    		if (newString.equals("1") || newString.equals("3") || newString.equals("4") || newString.equals("5") || newString.equals("6") )
	    		{
	    			//do nothing valid DJ
	    		}
	    		else
	    		{
	    			Messagebox.Message("Invalid DJ (1,3-6) only.",getApplicationContext());
	    			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
	    			Beat.selectAll();   
	    			return;
	    		}
		  }
    	  WorkingStorage.StoreCharVal(Defines.PrintBeatVal, newString.toUpperCase(),getApplicationContext());
		  if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.OfficerDataMenu))
	      {	
			  if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
			  {
				  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				  startActivityForResult(myIntent, 0);
				  finish();
				  overridePendingTransition(0, 0);    
			  }		
	      }
	      else
	      {
	    	  if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
	    	  {
	    		  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	    		  startActivityForResult(myIntent, 0);
	    		  finish();
	    		  overridePendingTransition(0, 0);    
	    	  }
	      }
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beatform);
       
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

	   EditText Beat = (EditText) findViewById(R.id.editTextbeat);
	   TextView tvMessage = (TextView) findViewById(R.id.widgetEnterbeat);
       if (savedInstanceState==null)
       {    	   
       		WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
       		String tempString = WorkingStorage.GetCharVal(Defines.PrintBeatVal,getApplicationContext()).trim();
       		if(!tempString.equals(""))
       		{
       			Beat.setText(tempString);
       		}
       		if( WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("READING"))
       		{
       			tvMessage.setText("DJ (1,3-6)");
       		}
       }
	   Beat.requestFocus();

       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(Beat, "FULL", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Beat.selectAll();
       }
       
   	   Beat.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      		});   	   
	   
	}
}