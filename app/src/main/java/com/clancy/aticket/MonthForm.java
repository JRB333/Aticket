package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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


public class MonthForm extends ActivityGroup {
    private void EnterClick()
    {    	
    		EditText platemonth = (EditText) findViewById(R.id.editTextPlatemonth);
    		Editable tempS = platemonth.getText();
    		String tempString = tempS.toString();
    		if (tempString.equals(""))
    		{
    			//tempString ="0";
    			Messagebox.Message("Invalid month!",getApplicationContext());
    			platemonth.selectAll();   
    			return;
    		}
    		if (Integer.valueOf(tempString) != null)
    		{
    			if (Integer.valueOf(tempString) >= 0 && Integer.valueOf(tempString) <= 12)
    			{
    				WorkingStorage.StoreCharVal(Defines.PrintMonthVal, tempString, getApplicationContext());
    				if (Integer.valueOf(tempString) < 10)
    				{
    					if (tempString.length() < 2)
    						WorkingStorage.StoreCharVal(Defines.SaveMonthVal, "0"+tempString, getApplicationContext());
    					else
    						WorkingStorage.StoreCharVal(Defines.SaveMonthVal, tempString, getApplicationContext());
    				}
    				else
    				{
    					WorkingStorage.StoreCharVal(Defines.SaveMonthVal, tempString, getApplicationContext());
    				}
    		    	
					if (WorkingStorage.GetCharVal(Defines.YearMonthTogether,getApplicationContext()).equals("YES"))
					{
					     if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
					     {
					    	 Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
					    	 startActivityForResult(myIntent, 0);
					    	 finish();
					    	 overridePendingTransition(0, 0);    
					     }
					}	
					else if (WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES")
    				    && WorkingStorage.GetCharVal(Defines.SecondTicketVal, getApplicationContext()).trim().equals("YES")
    				    && WorkingStorage.GetCharVal(Defines.MeterFlagVal, getApplicationContext()).trim().equals("1"))    		   
    				{
    					Defines.clsClassName = MeterForm.class ;
    					Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
    					startActivityForResult(myIntent, 0);  
    					finish();
    					overridePendingTransition(0, 0);
    				}
					else if (WorkingStorage.GetCharVal(Defines.MeterFlagVal, getApplicationContext()).trim().equals("1")
						&& !WorkingStorage.GetCharVal(Defines.ExpandXMeter, getApplicationContext()).trim().equals("XMETER")
						&& !WorkingStorage.GetCharVal(Defines.SpecialMeterFlag, getApplicationContext()).trim().equals("YES")
						&& !WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES"))    		   
	    			{
	    				Defines.clsClassName = MeterForm.class ;
	    				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	    				startActivityForResult(myIntent, 0);  
	    				finish();
	    				overridePendingTransition(0, 0);
	    			}
  					else if (WorkingStorage.GetCharVal(Defines.ChalkFlagVal,getApplicationContext()).equals("1")
    					&& !WorkingStorage.GetCharVal(Defines.ChalkData,getApplicationContext()).equals("CHALKDATA"))
    				{
    					 //Messagebox.Message("Need to do the Chalk Form Routine", getApplicationContext());
	    				Defines.clsClassName = ChalkForm.class ;
	    				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	    				startActivityForResult(myIntent, 0);  
	    				finish();
	    				overridePendingTransition(0, 0);
  						
    				}	
    				else if (WorkingStorage.GetCharVal(Defines.StickerFlagVal,getApplicationContext()).equals("1"))
    				{
    			    	  Defines.clsClassName = StickerForm.class ;
    			    	  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
    			    	  startActivityForResult(myIntent, 0);  
    			    	  finish();
    			    	  overridePendingTransition(0, 0);
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
        		else
        		{
        			Messagebox.Message("Invalid month!",getApplicationContext());
        			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
        			platemonth.selectAll();    			
        		}  
    		}
    		else
    		{
    			Messagebox.Message("Invalid month!",getApplicationContext());
    			platemonth.selectAll();    			
    		}    		    		
    }
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthform);
        
       
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
               
        EditText platemonth = (EditText) findViewById(R.id.editTextPlatemonth);
        platemonth.requestFocus();
       
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	    CustomKeyboard.PickAKeyboard(platemonth, "NUMONLY", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		platemonth.selectAll();
        }

    	platemonth.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       	});

        
        if (WorkingStorage.GetCharVal(Defines.MonthFlagRequired, getApplicationContext()).trim().equals("YES")
        	&& WorkingStorage.GetCharVal(Defines.PlateMonthFlagVal, getApplicationContext()).trim().equals("0"))
        {
	     		if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
		    	{
		     	   	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		     	   	startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);    
		    	}	
        }
       // if (WorkingStorage.GetCharVal(Defines.SavePlateVal, getApplicationContext()).trim().equals("NOPLATE")
       //     	&& WorkingStorage.GetCharVal(Defines.PlateMonthFlagVal, getApplicationContext()).trim().equals("0"))
        if (WorkingStorage.GetCharVal(Defines.SavePlateVal, getApplicationContext()).trim().equals("NOPLATE")
        	|| WorkingStorage.GetCharVal(Defines.SavePlateVal, getApplicationContext()).trim().equals("TEMPTAG"))               
        {
        	 WorkingStorage.StoreCharVal(Defines.PrintMonthVal, "", getApplicationContext());
             WorkingStorage.StoreCharVal(Defines.SaveMonthVal, "00", getApplicationContext());
            
	    	 if (WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES")
		      	 && WorkingStorage.GetCharVal(Defines.SecondTicketVal, getApplicationContext()).trim().equals("YES")
		      	 && WorkingStorage.GetCharVal(Defines.MeterFlagVal, getApplicationContext()).trim().equals("1"))    		   
		     {
				 Defines.clsClassName = MeterForm.class ;
			     Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			     startActivityForResult(myIntent, 0);  
			     finish();
			     overridePendingTransition(0, 0);
		     }
	    	 else if (WorkingStorage.GetCharVal(Defines.MeterFlagVal, getApplicationContext()).trim().equals("1")
	    		 &&	!WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES"))
			 {
				 Defines.clsClassName = MeterForm.class ;
				 Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				 startActivityForResult(myIntent, 0);  
				 finish();
				 overridePendingTransition(0, 0);
			 }
			 else if (WorkingStorage.GetCharVal(Defines.ChalkFlagVal,getApplicationContext()).equals("1")
				 && !WorkingStorage.GetCharVal(Defines.ChalkData,getApplicationContext()).equals("CHALKDATA"))
			 {
			 	 Messagebox.Message("Need to do the Chalk Form Routine", getApplicationContext());
		     }	
			 else if (WorkingStorage.GetCharVal(Defines.StickerFlagVal,getApplicationContext()).equals("1"))
			 {
			 	 Messagebox.Message("Need to do the Sticker Form Routine", getApplicationContext());
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

        

	}
}