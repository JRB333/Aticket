package com.clancy.aticket;

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


public class StickerForm extends ActivityGroup {
	   public KeyboardView keyboardView = null; 
	  private void EnterClick()
	  {
		  EditText Month = (EditText) findViewById(R.id.editTextMonth); //
		  EditText Year = (EditText) findViewById(R.id.editTextYear);   
		  String tempMonth = Month.getText().toString();
		  String tempYear = Year.getText().toString();
		  if (tempMonth.equals(""))
		  {
  			Messagebox.Message("Invalid month!",getApplicationContext());
			Month.selectAll();   
			return;
		  }
		  if (tempYear.equals(""))
		  {
  			Messagebox.Message("Invalid year!",getApplicationContext());
			Year.selectAll();   
			return;
		  }		  
		  if (Integer.valueOf(tempMonth) >= 1 && Integer.valueOf(tempMonth) <= 12
			  && Integer.valueOf(tempYear) >= 0 && Integer.valueOf(tempYear) <= 99)
		  {
			  String newString = Month.getText().toString() + Year.getText().toString();	  
			  WorkingStorage.StoreCharVal(Defines.SaveStickerVal, newString ,getApplicationContext());
		      WorkingStorage.StoreCharVal(Defines.PrintStickerVal, Month.getText().toString() +"/"+ Year.getText().toString() ,getApplicationContext());
		      if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
			  {
				  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			   	  startActivityForResult(myIntent, 0);
			   	  finish();
			   	  overridePendingTransition(0, 0);    
			  }				  			  
		  }
		  else
		  {
  			Messagebox.Message("Invalid Entry!",getApplicationContext());
  			Year.selectAll();
		  }
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stickerform);
       
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
        
        Button none = (Button) findViewById(R.id.ButtonNone);
        none.setOnClickListener(new View.OnClickListener() {
	           public void onClick(View view) {
	        	  CustomVibrate.VibrateButton(getApplicationContext());
	 			  WorkingStorage.StoreCharVal(Defines.SaveStickerVal, "NONE" ,getApplicationContext());
			      WorkingStorage.StoreCharVal(Defines.PrintStickerVal, "    ",getApplicationContext());
			      if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
				  {
					  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				   	  startActivityForResult(myIntent, 0);
				   	  finish();
				   	  overridePendingTransition(0, 0);    
				  }	      	   
	           }	          
	    });        

	    final EditText Year = (EditText) findViewById(R.id.editTextYear);
        final EditText Month = (EditText) findViewById(R.id.editTextMonth);
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
   	    
   	   Month.addTextChangedListener(new TextWatcher()
   	   {
   		   @Override
   		   public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
      	        if(Month.getText().length() == 2)
      	        {
      	        	Year.setText("");
      	        	Year.requestFocus();
       	        	Year.selectAll();
       			    keyboardView = (KeyboardView) findViewById(R.id.keyboardView);      	        	
       	        	CustomKeyboard.PickAKeyboard(Year, "NUMONLY", getApplicationContext(), keyboardView);
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
   	   
   	   
   	   Year.setOnTouchListener(new View.OnTouchListener(){ 
      		@Override
      		public boolean onTouch(View v, MotionEvent event) {
          		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
          		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
          		return true;
      		 } 
       });     	   

	}
}