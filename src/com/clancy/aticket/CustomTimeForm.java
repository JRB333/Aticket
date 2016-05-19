package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
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


public class CustomTimeForm extends ActivityGroup {
	public KeyboardView keyboardView = null; 
	  private void EnterClick()
	  {
		  EditText Hours = (EditText) findViewById(R.id.editTextHours); //
		  EditText Minutes = (EditText) findViewById(R.id.editTextMinutes);   
		  String tempHour = Hours.getText().toString();
		  String tempMinutes = Minutes.getText().toString();
		  if (tempHour.equals(""))
		  {
  			Messagebox.Message("Invalid hour!",getApplicationContext());
			Hours.selectAll();   
			return;
		  }
		  if (tempMinutes.equals(""))
		  {
  			Messagebox.Message("Invalid minute!",getApplicationContext());
			Minutes.selectAll();   
			return;
		  }	
		  
		  if (Integer.valueOf(tempHour) >= 0 && Integer.valueOf(tempHour) <= 23
			  && Integer.valueOf(tempMinutes) >= 0 && Integer.valueOf(tempMinutes) <= 59)
		  {
			  WorkingStorage.StoreCharVal(Defines.SaveCustomTimeVal, tempHour + tempMinutes ,getApplicationContext());
			  WorkingStorage.StoreCharVal(Defines.SaveTimeVal, tempHour + tempMinutes ,getApplicationContext());
			  WorkingStorage.StoreCharVal(Defines.SaveTimeStartVal, tempHour + tempMinutes ,getApplicationContext());
		      WorkingStorage.StoreCharVal(Defines.PrintCustomTimeVal, tempHour +":"+ tempMinutes ,getApplicationContext());
		      WorkingStorage.StoreCharVal(Defines.PrintTimeVal, tempHour +":"+ tempMinutes ,getApplicationContext());
		      WorkingStorage.StoreCharVal(Defines.PrintTimeStartVal, tempHour +":"+ tempMinutes ,getApplicationContext());
			  
		      if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
			  {
				  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			   	  startActivityForResult(myIntent, 0);
			   	  finish();
			   	  overridePendingTransition(0, 0);    
			  }				  			  
		  }
		  else
		  {
  			Messagebox.Message("Invalid Time!",getApplicationContext());
  			Minutes.selectAll();
		  }
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customtimeform);
       
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

	    final EditText Minutes = (EditText) findViewById(R.id.editTextMinutes);
        final EditText Hours = (EditText) findViewById(R.id.editTextHours);
        
       	String DefaultTime = WorkingStorage.GetCharVal(Defines.SaveCustomTimeVal, getApplicationContext()).trim();
       	if (!DefaultTime.equals("NONE"))
       	{
       		if (DefaultTime.length() >= 4)
       		{
       			Hours.setText(DefaultTime.substring(0,2));
       			Minutes.setText(DefaultTime.substring(2,4));
       		}
       	}
       
	    Hours.requestFocus();
       
	    if (keyboardView == null)
	    {   
		   keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
	    }
   	    CustomKeyboard.PickAKeyboard(Hours, "NUMONLY", getApplicationContext(), keyboardView);

        if (savedInstanceState == null)
        {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Hours.selectAll();
        }
        
   	    Hours.setOnTouchListener(new View.OnTouchListener(){ 
   		 @Override
   		 public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		  } 
        });   
   	    Hours.addTextChangedListener(new TextWatcher()
   	    {
   		   @Override
   		   public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
      	        if(Hours.getText().length() == 2)
      	        {
      	        	Minutes.setText("");
      	        	Minutes.requestFocus();
       	        	Minutes.selectAll();
       			    keyboardView = (KeyboardView) findViewById(R.id.keyboardView);      	        	
       	        	CustomKeyboard.PickAKeyboard(Minutes, "NUMONLY", getApplicationContext(), keyboardView);
      	        }
   		   }

   		   @Override
			public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
   		   }
   		   @Override
   		   public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
   		   }   		  
   	   });

   	   Minutes.addTextChangedListener(new TextWatcher()
   	   {
   		   @Override
   		   public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
 	           String BlahBlah = s.toString();
 	           if (BlahBlah.equals("")) // backspace key in this case
 	           {
     	           Hours.requestFocus();
       	           Hours.selectAll();
       			   keyboardView = (KeyboardView) findViewById(R.id.keyboardView);      	        	
       	           CustomKeyboard.PickAKeyboard(Hours, "NUMONLY", getApplicationContext(), keyboardView);
 	           }
   		   }

   		   @Override
			public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
   				   
			// TODO Auto-generated method stub
			
   		   }
   		   @Override
   		   public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
   		   }   		  
   	   });
   	   
   	   
   	   Minutes.setOnTouchListener(new View.OnTouchListener(){ 
      		@Override
      		public boolean onTouch(View v, MotionEvent event) {
          		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
          		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
          		return true;
      		 } 
       });     	   

	}
	  
}