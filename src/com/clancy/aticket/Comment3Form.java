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
import android.widget.EditText;
import android.widget.TextView;


public class Comment3Form extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText comment = (EditText) findViewById(R.id.editTextcomment);   
		  String newString = comment.getText().toString();		  
	      WorkingStorage.StoreCharVal(Defines.PrintComment3Val, newString,getApplicationContext());
	      WorkingStorage.StoreCharVal(Defines.XXXComment3Val, newString, getApplicationContext());
	      
	      if (WorkingStorage.GetCharVal(Defines.CommentLastPrompt,getApplicationContext()).equals("YES"))
	      {
	    	  if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
	    	  {
	    		  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	    		  startActivityForResult(myIntent, 0);
	    		  finish();
	    		  overridePendingTransition(0, 0);    
	    	  }
	      }
	      else if (WorkingStorage.GetCharVal(Defines.PlateMonthFlagVal,getApplicationContext()).equals("1")
				  && !WorkingStorage.GetCharVal(Defines.SavePlateVal,getApplicationContext()).equals("NOPLATE")
				  && !WorkingStorage.GetCharVal(Defines.YearMonthTogether,getApplicationContext()).equals("YES"))
		  {
				Defines.clsClassName = MonthForm.class ;
		        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		        startActivityForResult(myIntent, 0);  
		        finish();
		        overridePendingTransition(0, 0);
		  }	      
	      else if (WorkingStorage.GetCharVal(Defines.MeterFlagVal,getApplicationContext()).equals("1")
			  && !WorkingStorage.GetCharVal(Defines.SecondTicketVal,getApplicationContext()).equals("YES")
			  && !WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES"))
		  {
				Defines.clsClassName = MeterForm.class ;
		        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		        startActivityForResult(myIntent, 0);  
		        finish();
		        overridePendingTransition(0, 0);	
		  }
	      else if (WorkingStorage.GetCharVal(Defines.MeterFlagVal,getApplicationContext()).equals("1")
					&& !WorkingStorage.GetCharVal(Defines.ExpandXMeter,getApplicationContext()).equals("XMETER")
					&& !WorkingStorage.GetCharVal(Defines.SpecialMeterFlag,getApplicationContext()).equals("YES")
					&& !WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES"))
		  {
	    	  	Defines.clsClassName = MeterForm.class ;
				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				startActivityForResult(myIntent, 0);  
				finish();
				overridePendingTransition(0, 0);
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
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment3form);
       
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEntercomment);
	        tvSpanish.setText("COMENTARIO LÍNEA 3:");
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

	   final EditText comment = (EditText) findViewById(R.id.editTextcomment);
       if (savedInstanceState==null)
       {    	   
       		if (WorkingStorage.GetCharVal(Defines.RetainXComment, getApplicationContext()).equals("YES"))
       		{
       			String tempString = WorkingStorage.GetCharVal(Defines.XXXComment3Val,getApplicationContext()).trim();
       			comment.setText(tempString);
       		}
       }
	   comment.requestFocus();
	   
       final Button secondary = (Button) findViewById(R.id.ButtonSecondary);
       secondary.setOnClickListener(new View.OnClickListener() {
	           public void onClick(View view) {
	        	   String KeyTitle = (String) secondary.getText();
	        	   if (KeyTitle.equals("Extended Keyboard"))
	        	   {
	        		   secondary.setText("Main Keyboard");
	        		   KeyboardView keyboardView = null; 
	        		   keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
	        		   CustomKeyboard.PickAKeyboard(comment, "SECONDARY", getApplicationContext(), keyboardView);
	        	   }
	        	   else
	        	   {
	        		   secondary.setText("Extended Keyboard");
	        		   KeyboardView keyboardView = null; 
	        		   keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
	        		   CustomKeyboard.PickAKeyboard(comment, "FULL", getApplicationContext(), keyboardView);
	        	   }
	           }	          
	   });	   
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(comment, "FULL", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		comment.selectAll();
       }
   	   comment.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      		});   	   

	   
	}
}