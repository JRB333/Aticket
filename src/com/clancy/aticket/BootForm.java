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


public class BootForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText plate = (EditText) findViewById(R.id.editTextPlate);   
		  String newString = plate.getText().toString();
		  if (!newString.equals(""))
		  {
			  WorkingStorage.StoreCharVal(Defines.PrintPlateVal, newString, getApplicationContext());
			  
	            String PrintBoot = "";
	            int OnList = 0;
	            PrintBoot = "|" + WorkingStorage.GetCharVal(Defines.SaveStateVal,getApplicationContext()) + newString ;
	            OnList = BootRoutines.GetBootList(PrintBoot, 1, 1, getApplicationContext());
	            if (OnList > 0)
	            {
		           	if(!SystemIOFile.exists("ALLBOOT.A"))
		           	{
		           		Messagebox.Message("UNABLE TO SEARCH BOOT LIST", getApplicationContext());
		           		Defines.clsClassName = SelFuncForm.class ;
						Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
						startActivityForResult(myIntent, 0);  
						finish();
						overridePendingTransition(0, 0);	
		           	}
		           	else
		           	{
		           		Defines.clsClassName = DetailsForm.class ;
						Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
						startActivityForResult(myIntent, 0);  
						finish();
						overridePendingTransition(0, 0);	
		           	}
	            }		  
		  }
	  }
	  
		private void TextChange(String SearchString)
		{
            String PrintBoot = "";
            int OnList = 0;
            PrintBoot = "|" + WorkingStorage.GetCharVal(Defines.SaveStateVal,getApplicationContext()) + SearchString ;
            OnList = BootRoutines.GetBootList(PrintBoot, 0, 1, getApplicationContext());
            if (OnList == 2)
            {
	           	  Defines.clsClassName = DetailsForm.class ;
				  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				  startActivityForResult(myIntent, 0);  
				  finish();
				  overridePendingTransition(0, 0);	
            }
		}	  
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bootform);
       
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterPlate);
	        tvSpanish.setText("NÚMERO DE TABLILLA:");
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

	   EditText plate = (EditText) findViewById(R.id.editTextPlate);   
	   plate.requestFocus();
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(plate, "LICENSE", getApplicationContext(), keyboardView);

   	   plate.setOnTouchListener(new View.OnTouchListener(){ 
		@Override
		public boolean onTouch(View v, MotionEvent event) {
    		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
    		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    		return true;
		} 
   		});
   	   
       plate.addTextChangedListener(new TextWatcher(){        
       	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
       	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString());
			}     
       });   	   

	}
}