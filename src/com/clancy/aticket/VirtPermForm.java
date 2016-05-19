package com.clancy.aticket;

import java.util.Calendar;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class VirtPermForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  GetDate.GetDateTime(getApplicationContext());
		  EditText Permit = (EditText) findViewById(R.id.editTextPermit);   
		  TextView tvMessage = (TextView) findViewById(R.id.textMessage);
		  TextView tvMessage2 = (TextView) findViewById(R.id.textMessage2);
		  String FindPermit = "|"+ Permit.getText().toString().toUpperCase().trim();
          while (FindPermit.length() < 11)
        	  FindPermit += " ";
	      String TempString = SearchData.FindValueInRecord(FindPermit, FindPermit.length(), "VIRTPERM.A", getApplicationContext());
	      WorkingStorage.StoreCharVal(Defines.PrintBootListPlateVal, Permit.getText().toString().toUpperCase().trim(), getApplicationContext());
	      if (TempString.equals("NIF"))
	      {
	    	  if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("HUMBOLT"))
	    	  {
	    		  tvMessage.setText(Permit.getText().toString().toUpperCase().trim());
	    		  tvMessage2.setText("PERMIT OK");
	    		  //This doesn't mean that it is valid only that it is not Lost or Stolen
                  //Celina said that they are not keying in all valid permits this year, only the lost or stolen permits
                  //If it doesn't find it then no problem	    		  
	    	  }
	    	  else
	    	  {
	    		  tvMessage.setText(Permit.getText().toString().toUpperCase().trim()+" - NOT ON FILE! ");
	    		  tvMessage2.setText("ISSUE TICKET NOW");
	    		  Vibrator vibKey = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
	    	      vibKey.vibrate(500);		    		  
	    	  }
	    	  Permit.setText("");
	    	  Permit.requestFocus();
	      }
	      else
	      {
    		  tvMessage.setText(Permit.getText().toString().toUpperCase().trim()+" - In Lot: " + TempString.substring(12,15));
    		  tvMessage2.setText("");
    		  boolean doVibrate = false;
    		  
    		  int CurrentTime = Integer.valueOf(WorkingStorage.GetCharVal(Defines.SaveTimeVal,getApplicationContext()));
    		  int BeginTime = 0;
    		  int EndTime = 0;
    		  if (!TempString.substring(15,19).trim().equals(""))
    			  BeginTime = Integer.valueOf(TempString.substring(15,19));
    		  if (!TempString.substring(19,23).trim().equals(""))
    			  EndTime = Integer.valueOf(TempString.substring(19,23));

    		  
    		  if (TempString.substring(15,19).trim().equals(""))
    		  {
    			  tvMessage2.setText("NO TIME/DAY INFO");
    		  }
    		  else if (CurrentTime < BeginTime)
    		  {
    			  tvMessage2.setText("!!INVALID TIME!!");
    			  doVibrate = true;
    		  }
    		  else if (CurrentTime > EndTime)
    		  {
    			  tvMessage2.setText("!!INVALID TIME!!");
    			  doVibrate = true;
    		  } 
    		  else if(!TempString.substring(23,30).trim().equals(""))
    		  {
    			  Calendar cal = Calendar.getInstance(); 
    			  int dow = cal.get(Calendar.DAY_OF_WEEK); 
    			  
    			  switch (dow) {
    			    case 1:
   	    			  if (TempString.substring(23, 24).equals("S"))
   	    				  tvMessage2.setText("VALID TIME/DAY");
   	    			  else
   	    			  {
   	    				  tvMessage2.setText("!!INVALID DAY!!");
   	    			  	  doVibrate = true;
   	    			  }
   	    			  break;

    			    case 2:
     	    			  if (TempString.substring(24, 25).equals("M"))
     	    				  tvMessage2.setText("VALID TIME/DAY");
     	    			  else
       	    			  {
       	    				  tvMessage2.setText("!!INVALID DAY!!");
       	    			  	  doVibrate = true;
       	    			  }
     	    			  break;   	    			  

    			    case 3:
     	    			  if (TempString.substring(25, 26).equals("T"))
     	    				  tvMessage2.setText("VALID TIME/DAY");
     	    			  else
       	    			  {
       	    				  tvMessage2.setText("!!INVALID DAY!!");
       	    			  	  doVibrate = true;
       	    			  }
     	    			  break;
     	    			  
    			    case 4:
     	    			  if (TempString.substring(26, 27).equals("W"))
     	    				  tvMessage2.setText("VALID TIME/DAY");
     	    			  else
       	    			  {
       	    				  tvMessage2.setText("!!INVALID DAY!!");
       	    			  	  doVibrate = true;
       	    			  }
     	    			  break;
     	    			  
    			    case 5:
     	    			  if (TempString.substring(27, 28).equals("R"))
     	    				  tvMessage2.setText("VALID TIME/DAY");
     	    			  else
       	    			  {
       	    				  tvMessage2.setText("!!INVALID DAY!!");
       	    			  	  doVibrate = true;
       	    			  }
     	    			  break;
     	    			  
    			    case 6:
     	    			  if (TempString.substring(28, 29).equals("F"))
     	    				  tvMessage2.setText("VALID TIME/DAY");
     	    			  else
       	    			  {
       	    				  tvMessage2.setText("!!INVALID DAY!!");
       	    			  	  doVibrate = true;
       	    			  }
     	    			  break;
     	    			  
    			    case 7:
     	    			  if (TempString.substring(29,30).equals("U"))
     	    				  tvMessage2.setText("VALID TIME/DAY");
     	    			  else
       	    			  {
       	    				  tvMessage2.setText("!!INVALID DAY!!");
       	    			  	  doVibrate = true;
       	    			  }
     	    			  break;
    			    }
    		  }
    		  else
    		  {
    			  tvMessage2.setText("VALID TIME/DAY");
    		  }
    	      if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("HUMBOLT"))
    	      {
    	    	  tvMessage2.setText("LOST OR STOLEN PERMIT");
    	      }
	    	  Permit.setText("");
	    	  Permit.requestFocus();    
	    	  if(doVibrate)
	    	  {
	    		  Vibrator vibKey = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
	    	      vibKey.vibrate(500);	
	    	  }
	      }
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virtpermform);
       
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

	   EditText Permit = (EditText) findViewById(R.id.editTextPermit);

	   Permit.requestFocus();
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(Permit, "FULL", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Permit.selectAll();
       }
   	   Permit.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      		});   	   

	   
	}
}