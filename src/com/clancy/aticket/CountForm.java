package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class CountForm extends ActivityGroup {


	 
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countform); 

        
       
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
	        	   Defines.clsClassName = SelFuncForm.class ;
	               Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
	               startActivityForResult(myIntent, 0);  
	               finish();
	               overridePendingTransition(0, 0);
	        }	          
	     });
	    
	     enter.requestFocus();
	    
	     TextView tvCount = (TextView) findViewById(R.id.TextViewCount);
	     tvCount.setText("Issued: "+Integer.toString(SearchData.GetFileSize("TICKET.R")/115));
		    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
		    {
		    	TextView tvSpanish = (TextView) findViewById(R.id.TextViewNext);
		        tvSpanish.setText("CANTIDAD DE BOLETOS");
		        tvCount.setText("EMITIDO: "+Integer.toString(SearchData.GetFileSize("TICKET.R")/115));
		    } 	     
	 }

}