package com.clancy.aticket;

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


public class LastFourForm extends ActivityGroup {

	  private void EnterClick()
	  {
		    EditText LastFour = (EditText) findViewById(R.id.editTextLastFour);   
		    String newString = LastFour.getText().toString();
		    TextView tvMessage = (TextView) findViewById(R.id.textMessage);   
		    boolean ValidLastFour = false;
			if (!newString.trim().equals("")) 
			{
				if (newString.equals("I")) 				
				{
	   		        WorkingStorage.StoreCharVal(Defines.PrintLastFourVal, "ILLEGIBLE",getApplicationContext());
	   		        WorkingStorage.StoreCharVal(Defines.SaveLastFourVal, "ILLEGIBLE", getApplicationContext());
	   		        ValidLastFour = true;
				}
				else if (newString.equals("C")) 				
				{
	   		        WorkingStorage.StoreCharVal(Defines.PrintLastFourVal, "COVERED",getApplicationContext());
	   		        WorkingStorage.StoreCharVal(Defines.SaveLastFourVal, "COVERED", getApplicationContext());
	   		        ValidLastFour = true;
				}
				else if (newString.equals("D")) 				
				{
	   		        WorkingStorage.StoreCharVal(Defines.PrintLastFourVal, "DRIVE AWAY",getApplicationContext());
	   		        WorkingStorage.StoreCharVal(Defines.SaveLastFourVal, "DRIVE AWAY", getApplicationContext());
	   		        ValidLastFour = true;
				}	
				else if (newString.equals("N")) 				
				{
	   		        WorkingStorage.StoreCharVal(Defines.PrintLastFourVal, "VIN NOT FOUND",getApplicationContext());
	   		        WorkingStorage.StoreCharVal(Defines.SaveLastFourVal, "VIN NOT FOUND", getApplicationContext());
	   		        ValidLastFour = true;
				}				
				else
				{
	   		        WorkingStorage.StoreCharVal(Defines.PrintLastFourVal, newString, getApplicationContext());
	   		        WorkingStorage.StoreCharVal(Defines.SaveLastFourVal, newString, getApplicationContext());					
				}
				if (newString.length() < 4 && ValidLastFour == false)
				{
					tvMessage.setText("Not Enough Digits");
		      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
					LastFour.selectAll();
				}
				else
				{
					Defines.clsClassName = PrintSelectForm.class ;
					Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				   	startActivityForResult(myIntent, 0);
				   	finish();
				   	overridePendingTransition(0, 0);    			 					
				}
			}
			else
			{
				tvMessage.setText("Last Four Required");
	      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
				LastFour.selectAll();
			}
	  }
	
		private void TextChange(String SearchString)
		{
			if (SearchString.trim().equals("")) //prevent spaces from not causing the search routine correctly
			{
			}
			else
			{
				TextView tvMessage = (TextView) findViewById(R.id.textMessage);   
				EditText LastFour = (EditText) findViewById(R.id.editTextLastFour);   
				if (SearchString.equals("I")) 				
				{
					tvMessage.setText("ILLEGIBLE");
				}
				else if (SearchString.equals("C")) 				
				{
					tvMessage.setText("COVERED");
				}
				else if (SearchString.equals("D")) 				
				{
					tvMessage.setText("DRIVE AWAY");
				}	
				else if (SearchString.equals("N")) 				
				{
					tvMessage.setText("VIN NOT FOUND");
				}				
				else
				{
					tvMessage.setText("");
				}
			}
		}
	  
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lastfourform);
       
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

	   EditText LastFour = (EditText) findViewById(R.id.editTextLastFour);
	   LastFour.requestFocus();
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(LastFour, "LICENSE", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		LastFour.selectAll();
       }
       
   	   LastFour.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      	});
   	   
   	LastFour.addTextChangedListener(new TextWatcher(){        
       	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
       	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString().trim());
			}     
       });
   	   if (WorkingStorage.GetCharVal(Defines.SavePlateVal, getApplicationContext()).equals("NOPLATE"))
   	   {
   		   String TempVin = WorkingStorage.GetCharVal(Defines.SaveVinVal, getApplicationContext()).trim();
   		   if (TempVin.length() > 4)
   		   {
   			   	TempVin = TempVin.substring(TempVin.length()-4);
   		        WorkingStorage.StoreCharVal(Defines.PrintLastFourVal, TempVin,getApplicationContext());
   		        WorkingStorage.StoreCharVal(Defines.SaveLastFourVal, TempVin, getApplicationContext());
   				Defines.clsClassName = PrintSelectForm.class ;
   				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
   			   	startActivityForResult(myIntent, 0);
   			   	finish();
   			   	overridePendingTransition(0, 0);    	
   		   }
   	   }
	   
	}
}