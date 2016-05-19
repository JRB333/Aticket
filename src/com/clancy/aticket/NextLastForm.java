package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class NextLastForm extends ActivityGroup {


	 
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextlastform);

       
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

        String ClancyRecord = SearchData.GetRecordNumberNoLength("CLANCY.J", 1, getApplicationContext());
		String NextNum = ClancyRecord.substring(113, 121);
		String LastNum = ClancyRecord.substring(121, 129);   
	    TextView tvNext = (TextView) findViewById(R.id.TextViewNext);
	    tvNext.setText("Next: "+NextNum);	     
	    TextView tvLast = (TextView) findViewById(R.id.TextViewLast);
	    tvLast.setText("Last:  "+LastNum);
	    
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	tvNext.setText("Promio: "+NextNum);
	    	tvLast.setText("Último:  "+LastNum);
	    }	    

		Button enter = (Button) findViewById(R.id.buttonEnter);
	    enter.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View view) {
	    	   CustomVibrate.VibrateButton(getApplicationContext());
	     	   if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
	    	   {
				   Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
				   startActivityForResult(myIntent, 0);
				   finish();
				   overridePendingTransition(0, 0);    
	    	   }
	        }	          
	     });
	     enter.requestFocus();
	 }

}