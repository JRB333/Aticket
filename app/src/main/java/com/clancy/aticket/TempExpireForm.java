package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TempExpireForm extends ActivityGroup {
	public KeyboardView keyboardView = null; 
	  private void EnterClick()
	  {
		  EditText Month = (EditText) findViewById(R.id.editTextMonth); //
		  EditText Day = (EditText) findViewById(R.id.editTextDay);   
		  EditText Year = (EditText) findViewById(R.id.editTextYear);
		  String tempMonth = Month.getText().toString();
		  String tempDay = Day.getText().toString();
		  String tempYear = Year.getText().toString();
		  if (tempMonth.equals(""))
		  {
  			Messagebox.Message("Invalid Month!",getApplicationContext());
			return;
		  }
		  if (tempDay.equals(""))
		  {
  			Messagebox.Message("Invalid Day!",getApplicationContext());  
			return;
		  }	
		  if (tempYear.equals(""))
		  {
  			Messagebox.Message("Invalid Year!",getApplicationContext());  
			return;
		  }		  
		  if (Integer.valueOf(tempMonth) < 1 || Integer.valueOf(tempMonth) > 12)
		  {
	  		Messagebox.Message("Invalid Month!",getApplicationContext());
			return;
		  }
		  if (Integer.valueOf(tempYear) < 1940 || Integer.valueOf(tempMonth) > 2075)
		  {
	  		Messagebox.Message("Invalid Year!",getApplicationContext());
			return;
		  }		  
		  if (Integer.valueOf(tempDay) < 1 || Integer.valueOf(tempDay) > 31)
		  {
	  		Messagebox.Message("Invalid Day!",getApplicationContext());
			return;
		  }	
		  if (Integer.valueOf(tempDay) == 29)
		  {
			  if (Integer.valueOf(tempMonth) == 2)
			  {
				  if((Integer.valueOf(tempYear) % 4) > 0)
				  {
					  Messagebox.Message("Invalid Day!",getApplicationContext());
					  return;				  					  
				  }
			  }
		  }	
		  if (Integer.valueOf(tempDay) == 30 && Integer.valueOf(tempMonth) == 2 )
		  {
			  Messagebox.Message("Invalid Day!",getApplicationContext());
			  return;				  					  
		  }	
		  if (Integer.valueOf(tempDay) == 31)
		  {
			  if (Integer.valueOf(tempMonth) == 2 ||
				  Integer.valueOf(tempMonth) == 4 ||
				  Integer.valueOf(tempMonth) == 6 ||
				  Integer.valueOf(tempMonth) == 9 ||
				  Integer.valueOf(tempMonth) == 11)
			  {
				  Messagebox.Message("Invalid Day!",getApplicationContext());
				  return;				  					  
			  }
		  }			  
		  // Made it this far, must be valid Date!
		  
		  WorkingStorage.StoreCharVal(Defines.PrintTempExpireVal, tempMonth+"/"+tempDay+"/"+tempYear ,getApplicationContext());

		  Intent myIntent = new Intent(getApplicationContext(), NYVinForm.class);
		  startActivityForResult(myIntent, 0);
		  finish();
		  overridePendingTransition(0, 0);    
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempexpireform);
       
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

 	    TextView tvMessage = (TextView) findViewById(R.id.widgetEnterMonth);
   
       
	    final EditText Day = (EditText) findViewById(R.id.editTextDay);
        final EditText Month = (EditText) findViewById(R.id.editTextMonth);
        final EditText Year = (EditText) findViewById(R.id.editTextYear);
               	
        Month.requestFocus();
       
	    if (keyboardView == null)
	    {   
		   keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
	    }
   	    CustomKeyboard.PickAKeyboard(Month, "NUMONLY", getApplicationContext(), keyboardView);

        if (savedInstanceState == null)
        {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Month.selectAll();
        }
        
   	    Month.setOnTouchListener(new View.OnTouchListener(){ 
   		 @Override
   		 public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		  } 
        });   
   	    
    	Day.setOnTouchListener(new View.OnTouchListener(){ 
         @Override
         public boolean onTouch(View v, MotionEvent event) {
         	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
         	imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
         	return true;
         } 
        });    
    	
    	Year.setOnTouchListener(new View.OnTouchListener(){ 
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
            	imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            	return true;
            } 
        });    	
   	    
   	    Month.addTextChangedListener(new TextWatcher()
   	    {
   		   @Override
   		   public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
      	        if(Month.getText().length() == 2)
      	        {
      	        	Day.setText("");
      	        	Day.requestFocus();
       	        	Day.selectAll();
       			    keyboardView = (KeyboardView) findViewById(R.id.keyboardView);      	        	
       	        	CustomKeyboard.PickAKeyboard(Day, "NUMONLY", getApplicationContext(), keyboardView);
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

   	   Day.addTextChangedListener(new TextWatcher()
   	   {
   		   @Override
   		   public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
     	       if(Day.getText().length() == 2)
      	       {
      	        	Year.setText("");
      	        	Year.requestFocus();
       	        	Year.selectAll();
       			    keyboardView = (KeyboardView) findViewById(R.id.keyboardView);      	        	
       	        	CustomKeyboard.PickAKeyboard(Year, "NUMONLY", getApplicationContext(), keyboardView);
      	       }   			   
 	           String BlahBlah = s.toString();
 	           if (BlahBlah.equals("")) // backspace key in this case
 	           {
     	           Month.requestFocus();
       	           Month.selectAll();
       			   keyboardView = (KeyboardView) findViewById(R.id.keyboardView);      	        	
       	           CustomKeyboard.PickAKeyboard(Month, "NUMONLY", getApplicationContext(), keyboardView);
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

   	   Year.addTextChangedListener(new TextWatcher()
   	   {
   		   @Override
   		   public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
 	           String BlahBlah = s.toString();
 	           if (BlahBlah.equals("")) // backspace key in this case
 	           {
     	           Day.requestFocus();
       	           Day.selectAll();
       			   keyboardView = (KeyboardView) findViewById(R.id.keyboardView);      	        	
       	           CustomKeyboard.PickAKeyboard(Day, "NUMONLY", getApplicationContext(), keyboardView);
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
   	      	   

	}	  
}