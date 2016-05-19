package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class ShowTimeForm extends ActivityGroup {


	 
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showtimeform); 

	    TextView tvCurrent = (TextView) findViewById(R.id.TextViewDisplay);
	    tvCurrent.setText(WorkingStorage.GetCharVal(Defines.PrintDateVal, getApplicationContext())+" "+WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()));
       
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.TextViewCurrentTime);
	        tvSpanish.setText("Fecha y hora actuales");
	    }	    
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
	    	   if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.TimeOfDayMenu))
	    	   {
	        	   Defines.clsClassName = SelFuncForm.class ;
	               Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
	               startActivityForResult(myIntent, 0);  
	               finish();
	               overridePendingTransition(0, 0);
	    	   }
	    	   else
	    	   {
	    		   if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
	    		   {
	    			   Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
	    			   startActivityForResult(myIntent, 0);
	    			   finish();
	    			   overridePendingTransition(0, 0);    
	    		   }
	    	   }
	        }	          
	     });
	    
	     
	 }

}