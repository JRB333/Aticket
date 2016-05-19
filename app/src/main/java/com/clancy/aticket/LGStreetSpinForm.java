package com.clancy.aticket;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
//import android.speech.RecognizerIntent;
import android.widget.Toast;


public class LGStreetSpinForm extends ActivityGroup {
	
	private void EnterClick()
	{
		if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("PHXAIRPORT"))
		{
			PopIt("Save Ticket", "Are you sure?");	
		}
		else
		{
    		Spinner spinner = (Spinner)findViewById(R.id.spinnerLocation);
    		String spinnerText = (String) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
    		int spinnerPosition = spinner.getSelectedItemPosition();
    		WorkingStorage.StoreLongVal(Defines.LocationSpinnerVal,spinnerPosition, getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.PrintLGStreet1Val,spinnerText.substring(0, 30),getApplicationContext());
    		WorkingStorage.StoreCharVal(Defines.PrintLGStreet2Val,spinnerText.substring(30, 60),getApplicationContext());
    		WorkingStorage.StoreCharVal(Defines.PrintLGStreet3Val,spinnerText.substring(60, 90),getApplicationContext());

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
        setContentView(R.layout.lgstreetspinform);       
              
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
        
              
        final Spinner spinner = (Spinner)findViewById(R.id.spinnerLocation);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
         
	  	File file = new File("/data/data/com.clancy.aticket/files/LGSTREET.A");  
	  	if(file.exists())   
	  	{   		   
	  		try {  
		         BufferedReader br = new BufferedReader(new FileReader(file));  
		         String line;
		         int i = 1;
		         while ((line = br.readLine()) != null ) 
		         {
		        	 spinnerAdapter.add(line.substring(0, 90));
		        	 i++;
		         }  
		         br.close();
		         file = null;			         
		         
		     }catch (IOException e) 
		     {  
					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();  
		     }  
        }
        else  
        {
        	file = null;        	
        }
	  	
        spinner.setSelection(WorkingStorage.GetLongVal(Defines.LocationSpinnerVal, getApplicationContext()), true);
                
	}
	
	
    public void PopIt( String title, String message ){
        new AlertDialog.Builder(this)
        .setTitle( title )
        .setMessage( message )
        .setPositiveButton("YES", new DialogInterface.OnClickListener() {            
        	public void onClick(DialogInterface arg0, int arg1) {
                //do stuff onclick of YES
        		Spinner spinner = (Spinner)findViewById(R.id.spinnerLocation);
        		String spinnerText = (String) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
        		int spinnerPosition = spinner.getSelectedItemPosition();
        		WorkingStorage.StoreLongVal(Defines.LocationSpinnerVal,spinnerPosition, getApplicationContext());
        	    //WorkingStorage.StoreCharVal(Defines.PrintLGStreet1Val, spinnerText, getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.PrintLGStreet1Val,spinnerText.substring(0, 30),getApplicationContext());
        		WorkingStorage.StoreCharVal(Defines.PrintLGStreet2Val,spinnerText.substring(30, 60),getApplicationContext());
        		WorkingStorage.StoreCharVal(Defines.PrintLGStreet3Val,spinnerText.substring(60, 90),getApplicationContext());

        		if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
        	    {
        	  		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        	   		startActivityForResult(myIntent, 0);
        	   		finish();
        	   		overridePendingTransition(0, 0);    
        	    }	
            }
        })
        .setNegativeButton("NO", new DialogInterface.OnClickListener() {            
        	public void onClick(DialogInterface arg0, int arg1) {
                //do stuff onclick of CANCEL
        		
            }
        }).show();

    }

}

