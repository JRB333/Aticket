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


public class LanguageForm extends ActivityGroup {

	  private void EnterClick()
	  {
		    EditText Language = (EditText) findViewById(R.id.editTextLanguage);   
		    String newString = Language.getText().toString();
		    TextView tvMessage = (TextView) findViewById(R.id.textMessage);   
		    boolean ValidLanguage = false;
			if (!newString.trim().equals("")) 
			{
				if (newString.equals("S")) 				
				{
					WorkingStorage.StoreCharVal(Defines.LanguageType, "SPANISH", getApplicationContext());
			    	   if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
			    	   {
						   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
						   startActivityForResult(myIntent, 0);
						   finish();
						   overridePendingTransition(0, 0);    
			    	   }					
				}
				else if (newString.equals("E")) 				
				{
					WorkingStorage.StoreCharVal(Defines.LanguageType, "ENGLISH", getApplicationContext());
			    	   if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
			    	   {
						   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
						   startActivityForResult(myIntent, 0);
						   finish();
						   overridePendingTransition(0, 0);    
			    	   }
				}
				else 
				{
					tvMessage.setText("S or E only");
					Language.selectAll();
				}
			}
			else
			{
				tvMessage.setText("S or E only");
				Language.selectAll();
			}
	  }
	
	  
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languageform);
       
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

	   EditText Language = (EditText) findViewById(R.id.editTextLanguage);
	   Language.requestFocus();
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(Language, "LICENSE", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Language.selectAll();
       }
       
   	   Language.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      	});
   	   

	   
	}
}