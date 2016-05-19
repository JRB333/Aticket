package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class RatesForm extends ActivityGroup {
    int CurrentRateCheck = 0;
    int TextLength = 0;
	  private void EnterClick()
	  {
	      int nRates = 0;
	      String cRates = "";
	      String RateString  = "";
		  
		  EditText Number = (EditText) findViewById(R.id.editTextNumber);   
		  cRates = Number.getText().toString().trim();
		 /* if (cRates.length() == 1)
		  {
			  cRates = "00.0"+cRates;
		  }
		  else if (cRates.length() == 2)
		  {
			  cRates = "00."+cRates;
		  }
		  else if (cRates.length() == 3)
		  {
			  cRates = "00"+cRates;
		  }
		  else if (cRates.length() == 4)
		  {
			  cRates = "0"+cRates;
		  }*/
		  // need to pad value
		  double dTemp = Double.valueOf(cRates);
          dTemp = Math.round(dTemp * 100);
          dTemp =dTemp/100;
          cRates = String.format("%.2f", dTemp);
		  if (Double.valueOf(cRates) >= 100)
		  {
			  TextView tvRates = (TextView) findViewById(R.id.textViewMsg);
			  tvRates.setText("Must be less than $100.00");
			  return;
		  }
		  if (Double.valueOf(cRates) > 0)
		  {
		      Button enter = (Button) findViewById(R.id.buttonEnter);
		     // enter.setVisibility(View.INVISIBLE);
		      
			  switch (CurrentRateCheck) 
			   {
				  case 1: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox20MinAmtVal, cRates, getApplicationContext());
	                  RateString = "20MN";
					  break;
				  case 2: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBoxHalfAmtVal, cRates, getApplicationContext());
	                  RateString = "30MN";
					  break;
				  case 3: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox40MinAmtVal, cRates, getApplicationContext());
	                  RateString = "40MN";
					  break;
				  case 4: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBoxHourAmtVal, cRates, getApplicationContext());
	                  RateString = "HOUR";
					  break;
				  case 5: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox80MinAmtVal, cRates, getApplicationContext());
	                  RateString = "80MN";
					  break;
				  case 6: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox90MinAmtVal, cRates, getApplicationContext());
	                  RateString = "90MN";
					  break;
				  case 7: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox100MinAmtVal, cRates, getApplicationContext());
	                  RateString = "100MN";
					  break;
				  case 8: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox2HoursAmtVal, cRates, getApplicationContext());
	                  RateString = "2 HR";
					  break;
				  case 9: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox3HoursAmtVal, cRates, getApplicationContext());
	                  RateString = "3 HR";
					  break;
				  case 10: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox4HoursAmtVal, cRates, getApplicationContext());
	                  RateString = "4 HR";
					  break;
				  case 11: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox5HoursAmtVal, cRates, getApplicationContext());
	                  RateString = "5 HR";
					  break;
				  case 12: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox6HoursAmtVal, cRates, getApplicationContext());
	                  RateString = "6 HR";
					  break;
				  case 13: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox7HoursAmtVal, cRates, getApplicationContext());
	                  RateString = "7 HR";
					  break;
				  case 14: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox8HoursAmtVal, cRates, getApplicationContext());
	                  RateString = "8 HR";
					  break;
				  case 15: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBox9HoursAmtVal, cRates, getApplicationContext());
	                  RateString = "9 HR";
					  break;
				  case 16: 
	                  WorkingStorage.StoreCharVal(Defines.SaveHBoxDayAmtVal, cRates, getApplicationContext());
	                  RateString = "DAY ";
					  break;     		        		        
				  default:
					  //tvRates.setText("SOMETHING WRONG");
					  break;
			   }	
	            RateString = RateString + WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal, getApplicationContext());
	            while (RateString.length() < 9)
	            	RateString += " "; 	            
	            RateString = RateString + WorkingStorage.GetCharVal(Defines.PrintDateVal, getApplicationContext());
	            RateString = RateString + "-";
	            RateString = RateString + WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext());
	            RateString = RateString + "    ";
	            RateString = RateString + cRates;

	            IOHonorFile.SaveToFile(WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal, getApplicationContext()).trim() + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".RTE", RateString, getApplicationContext());
	            
	            CheckBox cb1 = (CheckBox)findViewById(R.id.checkBox1);
	            if (cb1.isChecked())
	            {
	            	if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut1Val,getApplicationContext()).equals(""))
	            	{
	            		WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut1Val, cRates, getApplicationContext());
	            	}
	            	else if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut2Val,getApplicationContext()).equals(""))
	            	{
	            		WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut2Val, cRates, getApplicationContext());
	            	}
	            	else if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut3Val,getApplicationContext()).equals(""))
	            	{
	            		WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut3Val, cRates, getApplicationContext());
	            	}
	            	else if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut4Val,getApplicationContext()).equals(""))
	            	{
	            		WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut4Val, cRates, getApplicationContext());
	            	}

	            }
	            
	            // need to do checkbox shortcut stuff
	            String DoItAgain = ProgramFlow.GetNextRatesForm(getApplicationContext());
	  		  if (!DoItAgain.equals("ERROR"))
			  {
				  //Defines.clsClassName = HBoxDownloadForm.class ;
				  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			   	  startActivityForResult(myIntent, 0);
			   	  finish();
			   	  overridePendingTransition(0, 0);    
			  }	
	  		  String dan = "";
	  		  String blah = dan;
		  }
		  else
		  {
			  TextView tvRates = (TextView) findViewById(R.id.textViewMsg);
			  tvRates.setText("Must be more than $0.00");
		  }
		  
			  
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratesform);
       
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

	   EditText Number = (EditText) findViewById(R.id.editTextNumber);
       if (savedInstanceState==null)
       {    	   
    	   Number.setText("0.00");
       }
	   Number.requestFocus();
       
	   
	   
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(Number, "NUMBERS", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Number.selectAll();
       }
   	   Number.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
       });   
   	   
	   TextView tvRates = (TextView) findViewById(R.id.widgetEnterNumber);
	   CurrentRateCheck = WorkingStorage.GetLongVal(Defines.CurrentRatesOrderVal, getApplicationContext());
	   
	   switch (CurrentRateCheck) 
	   {
		  case 1: 
			  tvRates.setText("20 MINUTE RATE:");
			  break;
		  case 2: 
			  tvRates.setText("30 MINUTE RATE:");
			  break;
		  case 3: 
			  tvRates.setText("40 MINUTE RATE:");
			  break;
		  case 4: 
			  tvRates.setText("HOURLY RATE:");
			  break;
		  case 5: 
			  tvRates.setText("80 MINUTE RATE:");
			  break;
		  case 6: 
			  tvRates.setText("90 MINUTE RATE:");
			  break;
		  case 7: 
			  tvRates.setText("100 MINUTE RATE:");
			  break;
		  case 8: 
			  tvRates.setText("2 HOUR RATE:");
			  break;
		  case 9: 
			  tvRates.setText("3 HOUR RATE:");
			  break;
		  case 10: 
			  tvRates.setText("4 HOUR RATE:");
			  break;
		  case 11: 
			  tvRates.setText("5 HOUR RATE:");
			  break;
		  case 12: 
			  tvRates.setText("6 HOUR RATE:");
			  break;
		  case 13: 
			  tvRates.setText("7 HOUR RATE:");
			  break;
		  case 14: 
			  tvRates.setText("8 HOUR RATE:");
			  break;
		  case 15: 
			  tvRates.setText("9 HOUR RATE:");
			  break;
		  case 16: 
			  tvRates.setText("ALL DAY RATE:");
			  break;     		        		        
		  default:
			  tvRates.setText("SOMETHING WRONG");
			  break;
	   }	
	   
	   CheckBox cb1 = (CheckBox)findViewById(R.id.checkBox1);
	   cb1.setVisibility(View.INVISIBLE); //invisible
	   cb1.setChecked(false);
	   if (WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut1Val, getApplicationContext()).equals(""))
	   {
		   cb1.setVisibility(View.VISIBLE); //visible
		   cb1.setChecked(true);
	   }else if (WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut2Val, getApplicationContext()).equals(""))
	   {
		   cb1.setVisibility(View.VISIBLE); //visible
		   cb1.setChecked(true);
	   }else if (WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut3Val, getApplicationContext()).equals(""))
	   {
		   cb1.setVisibility(View.VISIBLE); //visible
		   cb1.setChecked(true);
	   }else if (WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut4Val, getApplicationContext()).equals(""))
	   {
		   cb1.setVisibility(View.VISIBLE); //visible
		   cb1.setChecked(true);
	   }
	   
		   
	}
}