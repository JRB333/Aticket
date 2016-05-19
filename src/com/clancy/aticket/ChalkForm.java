package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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


public class ChalkForm extends ActivityGroup {
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
			  String newString = Hours.getText().toString() + Minutes.getText().toString();	  
			  WorkingStorage.StoreCharVal(Defines.SaveChalkVal, newString ,getApplicationContext());
		      WorkingStorage.StoreCharVal(Defines.DefaultChalkVal, newString ,getApplicationContext());
		      WorkingStorage.StoreCharVal(Defines.PrintChalkVal, Hours.getText().toString() +":"+ Minutes.getText().toString() ,getApplicationContext());
              if ( WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).equals("EVANSVILLE"))
              {
            	  EvansvilleChalkWhileTicketing();
              }
		      if (WorkingStorage.GetCharVal(Defines.StickerFlagVal,getApplicationContext()).equals("1")
		    		  && !WorkingStorage.GetCharVal(Defines.ChalkData, getApplicationContext()).equals("CHALKDATA"))
			  {
		    	  Defines.clsClassName = StickerForm.class ;
		    	  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		    	  startActivityForResult(myIntent, 0);  
		    	  finish();
		    	  overridePendingTransition(0, 0);
			  }
		      else if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
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
        setContentView(R.layout.chalkform);
       
        
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterHours);
	        tvSpanish.setText("LA HORA SE MARCÓ CON TIZA:");
	    } 
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
        if (WorkingStorage.GetCharVal(Defines.RememberChalk, getApplicationContext()).equals("STORECHALK"))
        {
        	String DefaultChalk = WorkingStorage.GetCharVal(Defines.DefaultChalkVal, getApplicationContext()).trim();
        	if (!DefaultChalk.equals("NONE"))
        	{
        		if (DefaultChalk.length() >= 4)
        		{
        			Hours.setText(DefaultChalk.substring(0,2));
        			Minutes.setText(DefaultChalk.substring(2,4));
        		}
        	}
        }
        if (WorkingStorage.GetCharVal(Defines.ChalkData, getApplicationContext()).equals("CHALKDATA"))
        {
			SeekChalkTicketPlate(WorkingStorage.GetCharVal(Defines.SavePlateVal, getApplicationContext()),
					WorkingStorage.GetCharVal(Defines.SaveStreetVal, getApplicationContext()), 
					WorkingStorage.GetCharVal(Defines.SaveDirectionVal, getApplicationContext()), 
					WorkingStorage.GetCharVal(Defines.PrintMeterVal, getApplicationContext()), 
					WorkingStorage.GetCharVal(Defines.SaveStateVal, getApplicationContext()), 
					WorkingStorage.GetCharVal(Defines.SaveStemVal, getApplicationContext()), 
					WorkingStorage.GetCharVal(Defines.SaveNumberVal, getApplicationContext()));
        	String DefaultChalk = WorkingStorage.GetCharVal(Defines.ChalkTimeVal, getApplicationContext()).trim();
        	if (!DefaultChalk.equals("NONE"))
        	{
        		if (DefaultChalk.length() >= 4)
        		{
        			Hours.setText(DefaultChalk.substring(0,2));
        			Minutes.setText(DefaultChalk.substring(2,4));
        		}
        	}
        }
        if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).equals("EVANSVILLE"))
        {
        	EvansvilleChalkRoutine(WorkingStorage.GetCharVal(Defines.SavePlateVal, getApplicationContext()),
					WorkingStorage.GetCharVal(Defines.SaveStreetVal, getApplicationContext()), 
					WorkingStorage.GetCharVal(Defines.SaveStateVal, getApplicationContext()));
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
	  
		private void SeekChalkTicketPlate(String PlatePassed, String StreetPassed, String DirectionPassed, String MeterPassed, String StatePassed, String StemPassed, String NumberPassed)
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
				        		// String tempString = line.substring(38,39).trim();
				        		 //Messagebox.Message(tempString, getApplicationContext());
			        			 if ( line.substring(20,22).trim().equals(StatePassed.trim())
					        	  && line.substring(8,11).trim().equals(StreetPassed.trim()))
			        			 {
		        					 Boolean TestDirection = true;
		        					 Boolean TestMeter = true;
		        					 Boolean TestNumber = true;
		        					 Boolean TestStem = true;
			        				 if (!line.substring(11,12).trim().equals(""))
			        				 {
			        					 if (!line.substring(11,12).trim().equals(DirectionPassed.trim()))
			        					 {
			        						 TestDirection = false;
			        					 }
			        				 }
			        				 if (!line.substring(12,20).trim().equals(""))
			        				 {
			        					 if (!line.substring(12,20).trim().equals(MeterPassed.trim()))
			        					 {
			        						 TestMeter = false;
			        					 }
			        					 if (line.substring(12,20).trim().equals("NA") && MeterPassed.equals(""))
			        					 {
			        						 TestMeter = true;
			        					 }			        					 
			        				 }
			        				 if (!line.substring(39,45).trim().equals(""))
			        				 {
			        					 if (!line.substring(39,45).trim().equals(NumberPassed.trim()))
			        					 {
			        						 TestNumber = false;
			        					 }
			        					 if (line.substring(39,45).trim().equals("NA") && NumberPassed.equals(""))
			        					 {
			        						 TestNumber = true;
			        					 }			        					 
			        				 }			        				 
			        				 if (!line.substring(38,39).trim().equals(""))
			        				 {
			        					 if (!line.substring(38,39).trim().equals(StemPassed.trim()))
			        					 {
			        						 TestDirection = false;
			        					 }
			        				 }
			        				 if ( TestDirection == true
			        					  && TestMeter == true
			        					  && TestNumber == true
			        					  && TestStem == true) // found the damn thing
			        				 {
			        					 TimeBuffer = line.substring(22, 26);
			        					 WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());
			        					 break;
			        				 }
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
		}
		
		private void EvansvilleChalkRoutine(String PlatePassed, String StreetPassed, String StatePassed)
		{        
	        if (PlatePassed.equals(""))
	        {
	        	return;
	        }
	        String SomeOneElseParkedInThisMeter = "";
	        String line = "";
	        String ChalkFileName = "/data/data/com.clancy.aticket/files/CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R";
		  	File file = new File(ChalkFileName);
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
			        			 if ( line.substring(20,22).trim().equals(StatePassed.trim())
					        	  && line.substring(8,11).trim().equals(StreetPassed.trim()))
			        			 {
			        				 EditText Minutes = (EditText) findViewById(R.id.editTextMinutes);
			        			     EditText Hours = (EditText) findViewById(R.id.editTextHours);
			        				 Hours.setText(line.substring(22, 24));
			        				 Minutes.setText(line.substring(24, 26));
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
		}	
		
		private void EvansvilleChalkWhileTicketing()
		{
			String RecordBuffer = "";
	       // String ChalkSave = WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext());
	        
			RecordBuffer = WorkingStorage.GetCharVal(Defines.SavePlateVal, getApplicationContext());
	        while (RecordBuffer.length() < 8)
	        	RecordBuffer += " ";		
		    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveStreetVal, getApplicationContext());
	        while (RecordBuffer.length() < 11)
	        	RecordBuffer += " ";
		    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveDirectionVal, getApplicationContext());
	        while (RecordBuffer.length() < 12)
	        	RecordBuffer += " ";
		    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveNumberVal, getApplicationContext()); // this is normally Meter, but Evansville uses the street number for some odd reason to match the chalking program
	        while (RecordBuffer.length() < 20)
	        	RecordBuffer += " "; 
		    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveStateVal, getApplicationContext());
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
		    RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.PrintMeterVal, getApplicationContext()); //this is normally Street Number, but Evansville uses the street number for some odd reason to match the chalking program
	        while (RecordBuffer.length() < 45)
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


}