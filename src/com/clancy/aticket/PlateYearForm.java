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


public class PlateYearForm extends ActivityGroup {
    private void EnterClick()
    {    	
    		EditText plateyear = (EditText) findViewById(R.id.editTextPlateYear);
    		String tempString = plateyear.getText().toString();
    		if (Integer.valueOf(tempString) != null)
    		{
    			if (Integer.valueOf(tempString) > 1950 && Integer.valueOf(tempString) < 2099)
    			{
    		    	if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.OfficerDataMenu))
    		    	{
        				WorkingStorage.StoreCharVal(Defines.DefaultYearVal, tempString, getApplicationContext());		
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
    		    		WorkingStorage.StoreCharVal(Defines.PrintYearVal, tempString, getApplicationContext());
    	                WorkingStorage.StoreCharVal(Defines.SaveYearVal, tempString.substring(2,4), getApplicationContext());
    	                //Messagebox.Message(tempString.substring(2,4), getApplicationContext());
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
        			Messagebox.Message("Invalid Year!",getApplicationContext());
        			plateyear.selectAll();    			
        		}  
    		}
    		else
    		{
    			Messagebox.Message("Invalid Year!",getApplicationContext());
    			plateyear.selectAll();    			
    		}    		    		
    }
    
    private void YearDown()
    {
    	EditText plateyear = (EditText) findViewById(R.id.editTextPlateYear);
		String tempString = plateyear.getText().toString();
		if (Integer.valueOf(tempString) != null)
		{
			if (Integer.valueOf(tempString) > 1950 && Integer.valueOf(tempString) < 2099)
			{
				int i = Integer.valueOf(tempString)-1;
				plateyear.setText(Integer.toString(i));
				WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
				plateyear.selectAll();
			}
		}
		
    }
    private void YearUp()
    {
    	EditText plateyear = (EditText) findViewById(R.id.editTextPlateYear);
		String tempString = plateyear.getText().toString();
		if (Integer.valueOf(tempString) != null)
		{
			if (Integer.valueOf(tempString) > 1950 && Integer.valueOf(tempString) < 2099)
			{
				int i = Integer.valueOf(tempString)+1;
				plateyear.setText(Integer.toString(i));
				WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
				plateyear.selectAll();
			}
		}  	
    }
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plateyearform);
        
       
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
        
        Button less = (Button) findViewById(R.id.buttonLess);
        less.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   YearDown();
           }         
        });
        
        Button more = (Button) findViewById(R.id.buttonMore);
        more.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   YearUp();
           }         
        });        
        
        Button btnNone = (Button) findViewById(R.id.buttonNone);
        btnNone.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
               WorkingStorage.StoreCharVal(Defines.PrintYearVal, "NONE", getApplicationContext());
               WorkingStorage.StoreCharVal(Defines.SaveYearVal, "  ", getApplicationContext());
			   if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
   			   {
   			   		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
   			   		startActivityForResult(myIntent, 0);
   			   		finish();
   			   		overridePendingTransition(0, 0);           			
   			   }
           }         
        });
        

        EditText plateyear = (EditText) findViewById(R.id.editTextPlateYear);
        plateyear.requestFocus();
       // plateyear.setCursorVisible(false);
       
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	    CustomKeyboard.PickAKeyboard(plateyear, "NUMBERS", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		plateyear.selectAll();
       		//plateyear.setTextColor(Color.parseColor("#ff0000CC"));
        }

    	plateyear.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});
       /* plateyear.setOnKeyListener(new View.OnKeyListener() {     	  
       	   public boolean onKey(View arg0, int arg1, KeyEvent event) {
         		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&  (arg1 == KeyEvent.KEYCODE_ENTER)   )
         		  { 
                   		EnterClick();
                   		return true;
         		  }
         		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&  (arg1 == 59)   )
         		  { 
                   		YearDown();
                   		return true;
         		  }   	
         		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&  (arg1 == 60)   )
         		  { 
                   		YearUp();
                   		return true;
         		  }   
         		  return false;
           	  }    	   
        });*/
        
        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.OfficerDataMenu))
        {
        	btnNone.setEnabled(false);
        	TextView tvMessage = (TextView) findViewById(R.id.widgetEnterPlateYear);
            tvMessage.setText("Default Plate Year:");
            plateyear.setText(WorkingStorage.GetCharVal(Defines.CurrentYearVal, getApplicationContext()));
            plateyear.selectAll();
        }
        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.TicketIssuanceMenu))
        {
            plateyear.setText(WorkingStorage.GetCharVal(Defines.DefaultYearVal, getApplicationContext()));
            plateyear.selectAll();

        	if (WorkingStorage.GetCharVal(Defines.SavePlateVal, getApplicationContext()).equals("NOPLATE"))
            {
                WorkingStorage.StoreCharVal(Defines.PrintYearVal, "", getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.SaveYearVal, "",getApplicationContext());
    			if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
    			{
    			   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
    			   startActivityForResult(myIntent, 0);
    			   finish();
    			   overridePendingTransition(0, 0);           			
    			}
            }
        	if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("PGCOUNTY"))
            {
        		if (WorkingStorage.GetCharVal(Defines.PlateMonthFlagVal, getApplicationContext()).trim().equals("0"))
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
}