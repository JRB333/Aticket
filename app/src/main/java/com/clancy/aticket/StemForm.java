package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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


public class StemForm extends ActivityGroup {
    private void EnterClick()
    {    	
    		EditText stem = (EditText) findViewById(R.id.editTextTirestem);
    		String tempString = stem.getText().toString();
    		if (tempString.equals(""))
    		{
    			Messagebox.Message("Invalid Stem Position",getApplicationContext());
    			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
    			stem.selectAll();  
    			return;
    		}
    		if (Integer.valueOf(tempString) != null)
    		{
    			if (Integer.valueOf(tempString) >= 1 && Integer.valueOf(tempString) <= 12)
    			{
					WorkingStorage.StoreCharVal(Defines.SaveStemVal,tempString,getApplicationContext());
    				if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
    				{
    					WorkingStorage.StoreCharVal(Defines.SaveChalkStemVal,tempString,getApplicationContext());
    					if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
    				    {
    						Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
    						startActivityForResult(myIntent, 0);
    						finish();
    						overridePendingTransition(0, 0);    
    				    }	
    				}else
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
        		else
        		{
        			Messagebox.Message("Invalid Stem Position",getApplicationContext());
        			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
        			stem.selectAll();    			
        		}  
    		}
    		else
    		{
    			Messagebox.Message("Invalid Stem Position",getApplicationContext());
    			stem.selectAll();    			
    		}   
    }
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stemform);
        
       
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
               
        EditText stem = (EditText) findViewById(R.id.editTextTirestem);
        stem.requestFocus();
       
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	    CustomKeyboard.PickAKeyboard(stem, "NUMBERS", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		stem.selectAll();
        }

    	stem.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       	});

        
        if ( WorkingStorage.GetCharVal(Defines.ChalkFlagVal, getApplicationContext()).trim().equals("0"))
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
}