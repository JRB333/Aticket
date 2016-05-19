package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MultipleChalksForm extends ActivityGroup {

	static EditText eChalks;
	
	private void SaveTheChalkTime()
	{
		String RecordBuffer = "";
        
		RecordBuffer = WorkingStorage.GetCharVal(Defines.SaveChalkPlateVal, getApplicationContext());
        while (RecordBuffer.length() < 8)
        	RecordBuffer += " ";		
	    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkStreetVal, getApplicationContext());
        while (RecordBuffer.length() < 11)
        	RecordBuffer += " ";
	    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkDirectionVal, getApplicationContext());
        while (RecordBuffer.length() < 12)
        	RecordBuffer += " ";
	    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkMeterVal, getApplicationContext());
        while (RecordBuffer.length() < 20)
        	RecordBuffer += " "; 
	    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkStateVal, getApplicationContext());
        while (RecordBuffer.length() < 22)
        	RecordBuffer += " ";        
	    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext());
        while (RecordBuffer.length() < 26)
        	RecordBuffer += " ";        
	    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, getApplicationContext());
        while (RecordBuffer.length() < 31)
        	RecordBuffer += " ";   
	    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveDateVal, getApplicationContext());
        while (RecordBuffer.length() < 37)
        	RecordBuffer += " ";   
	    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkStemVal, getApplicationContext());
        while (RecordBuffer.length() < 39)
        	RecordBuffer += " ";           
	    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkNumberVal, getApplicationContext());
        while (RecordBuffer.length() < 45)
        	RecordBuffer += " ";
        // all new here
        while (RecordBuffer.length() < 60) // put in an extra 15 characters of spaces to ensure no unload issues
        	RecordBuffer += " ";   
        RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.PrintChalkStreetVal, getApplicationContext());
        while (RecordBuffer.length() < 80) // put in an extra 15 characters of spaces to ensure no unload issues
        	RecordBuffer += " ";  
        RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.PrintChalkDirectionVal, getApplicationContext());
        while (RecordBuffer.length() < 95) // put in an extra 15 characters of spaces to ensure no unload issues
        	RecordBuffer += " ";          
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(RecordBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("Chalk File Creation Exception Occured: " + e, getApplicationContext());     
        }

	}
	private void GetChalkTimes(String PlatePassed)
	{
        String TimeBuffer = "NONE";
        WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());
        
        if (PlatePassed.equals(""))
        {
        	return;
        }
        String line = "";
        String ChalkFileName = "/data/data/com.clancy.aticket/files/CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R";
	  	File file = new File(ChalkFileName);
	  	String ChalkString = "";
	  	if(file.exists())   // check if file exist  
	  	{   		   
	  		try {  
		         BufferedReader br = new BufferedReader(new FileReader(file));  
		         
		         int i = 1;
		         while ((line = br.readLine()) != null ) 
		         {
		        	 if (line.length() >= 45)
		        	 {
		        		 if (line.substring(0,8).trim().equals(PlatePassed.trim()))
		        		 {
		        			 if (line.length()<95)// old layout 
		        			 {
			        			 ChalkString = ChalkString + PlatePassed.trim() + "  " + line.substring(20,22).trim() +"   "+ line.substring(8,11).trim() +"   " + line.substring(22,24).trim() + ":"+ line.substring(24,26).trim() + "  " + line.substring(12,20).trim() +"\n";		        				 
		        			 }
		        			 else
		        			 {
			        			 ChalkString = ChalkString + line.substring(22,24).trim() + ":"+ line.substring(24,26).trim() 
			        			 + " " + line.substring(12,20).trim() 
			        			 + line.substring(39,45).trim()
			        			 + " " + line.substring(80,95).trim() 
			        			 + " " + line.substring(60,80).trim() 
			        			 +"\n";		        				 
		        			 }

		        		 }
		        	 }
		        	 i++;
		         }  
		         br.close();
		         file = null;		         
		     }catch (IOException e) 
		     {  
					Toast.makeText(getApplicationContext(), e.toString(), 2000);  
		     }  
         }
         else  
		 {  
        	 file = null;
		 }
	  	eChalks.setText(ChalkString);
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplechalksform);

        eChalks = (EditText) findViewById(R.id.editTextPlate);
        
        GetDate.GetDateTime(getApplicationContext());
        TextView tvTime = (TextView) findViewById(R.id.textPlate);
        tvTime.setText("Current Time: " + WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()));
        SaveTheChalkTime();
        TextView tvPlate = (TextView) findViewById(R.id.textViewPlate);
        tvPlate.setText("Plate: " + WorkingStorage.GetCharVal(Defines.SaveChalkPlateVal, getApplicationContext()));        
        GetChalkTimes(WorkingStorage.GetCharVal(Defines.SaveChalkPlateVal,getApplicationContext()));


		Button enter = (Button) findViewById(R.id.buttonEnter);
	    enter.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View view) {
	    	   CustomVibrate.VibrateButton(getApplicationContext());
	    	   WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0,getApplicationContext());
	     	   if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
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