package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MeterForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText meter = (EditText) findViewById(R.id.editTextmeter);   
		  String newString = meter.getText().toString();
		  
	      WorkingStorage.StoreCharVal(Defines.PrintMeterVal, newString,getApplicationContext());	  
		  if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		  {
			  if (newString.equals(""))
			  {
				  WorkingStorage.StoreCharVal(Defines.SaveChalkMeterVal, "NA", getApplicationContext()); 
			  }
			  else
			  {
				  WorkingStorage.StoreCharVal(Defines.SaveChalkMeterVal, newString, getApplicationContext());  
			  }
			  if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
		      {
				  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				  startActivityForResult(myIntent, 0);
				  finish();
				  overridePendingTransition(0, 0);    
		      }		
		  }else
		  {
	    	  if (WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES")
	        	  && !WorkingStorage.GetCharVal(Defines.SecondTicketVal, getApplicationContext()).trim().equals("YES"))    		   
	          {
				  if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
				  {
					  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				   	  startActivityForResult(myIntent, 0);
				   	  finish();
				   	  overridePendingTransition(0, 0);    
				  }	
	          }
		      else if (WorkingStorage.GetCharVal(Defines.ChalkFlagVal,getApplicationContext()).equals("1")
					  && !WorkingStorage.GetCharVal(Defines.ChalkData,getApplicationContext()).equals("CHALKDATA"))
			  {
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
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meterform);
       
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEntermeter);
	        tvSpanish.setText("PARQUÍMETRO:");
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
	        	   EnterClick();	        	   
	           }	          
	   });

	   EditText meter = (EditText) findViewById(R.id.editTextmeter);
	   TextView tvMessage = (TextView) findViewById(R.id.widgetEntermeter);
       if (savedInstanceState==null)
       {    	   
    	   if (!WorkingStorage.GetCharVal(Defines.MeterNumberMessage,getApplicationContext()).trim().equals(""))
    	   {
    		   tvMessage.setText(WorkingStorage.GetCharVal(Defines.MeterNumberMessage,getApplicationContext()));
    	   }
    	   if (WorkingStorage.GetCharVal(Defines.HBoxFlowVal,getApplicationContext()).equals("YES"))
    	   {
    		   meter.setText(WorkingStorage.GetCharVal(Defines.HBoxMeterVal,getApplicationContext()));
    	   }
    	   if (WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES")
    		   && WorkingStorage.GetCharVal(Defines.SpecialMeterFlag,getApplicationContext()).equals("YES")
    		   && !WorkingStorage.GetCharVal(Defines.MeterFlagVal,getApplicationContext()).equals("1")
    		   && !WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.MeterLogMenu))
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
       
       if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("GRANDRAPIDS"))
       {
    	   WorkingStorage.StoreCharVal(Defines.PrintMeterVal, "", getApplicationContext());
    	   if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
			{
			  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			  startActivityForResult(myIntent, 0);
			  finish();
			  overridePendingTransition(0, 0);    
			}	    	   
       }
	   meter.requestFocus();
	   
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(meter, "FULL", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		meter.selectAll();
       }
   	   meter.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      		});  
   	   
       if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).equals("EVANSVILLE")
    	   && WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))  	   
       {
    	   EvansvilleDisplayChalkSpace(WorkingStorage.GetCharVal(Defines.SaveChalkPlateVal, getApplicationContext()), WorkingStorage.GetCharVal(Defines.SaveChalkStreetVal, getApplicationContext()), WorkingStorage.GetCharVal(Defines.SaveChalkStateVal, getApplicationContext()));    	   
       }
	   
	}
	  
		private void EvansvilleDisplayChalkSpace(String PlatePassed, String StreetPassed, String StatePassed)
		{
	        String TimeBuffer = "NONE";
	        WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());
	        
	        if (PlatePassed.equals(""))
	        {
	        	return;
	        }
	        String SomeOneElseParkedInThisMeter = "";
	        String line = "";
	        String ChalkFileName = "/data/data/com.clancy.aticket/files/CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R";
		  	File file = new File(ChalkFileName);
		  	String MeterString = "";
		  	if(file.exists())   // check if file exist  
		  	{   		   
		  		try {  
			         BufferedReader br = new BufferedReader(new FileReader(file));  
			         
			         int i = 1;
			         while ((line = br.readLine()) != null ) 
			         {
			        	 if (line.length() >= 45)
			        	 {
			        		 if (line.substring(0,8).trim().equals(PlatePassed.trim()) && line.substring(8,11).trim().equals(StreetPassed.trim()))
			        		 {
			        			 MeterString = line.substring(12,20).trim() ;
			        			 SomeOneElseParkedInThisMeter = MeterString;
			        		 }
			        		 else
		        			 {
			        			 //Now check to see if someone else parked in that same spot there after
			        			 if ( ! SomeOneElseParkedInThisMeter.equals("")) 
			        			 {
				        			 if ( line.substring(12,20).trim().equals(SomeOneElseParkedInThisMeter)
			        					 && line.substring(8,11).trim().equals(StreetPassed.trim()))
				        			 {
				        				 MeterString = "";		        					 				        				
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
 
			     }  
	         }
	         else  
			 {  
	        	 file = null;
			 }
		  	EditText meter = (EditText) findViewById(R.id.editTextmeter);
		    meter.setText(MeterString);
		}

}