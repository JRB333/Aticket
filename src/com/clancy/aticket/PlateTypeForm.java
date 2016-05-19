package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.TextView;


public class PlateTypeForm extends ActivityGroup {
	
	
	private void EnterClick()
	{
		TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DescString = tvDesc.getText().toString().trim();
		EditText platetype = (EditText) findViewById(R.id.editTextplatetype);		
		if (DescString.equals("NOT FOUND") || platetype.getText().toString().trim().equals(""))
		{			
	        platetype.selectAll();
	        platetype.requestFocus();
		}
		else
		{
            WorkingStorage.StoreCharVal(Defines.SaveTypeVal, platetype.getText().toString().trim(),getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.PrintTypeVal, DescString, getApplicationContext());            
            
            if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")   			   		
            {
   			 	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
   			 	startActivityForResult(myIntent, 0);
   			 	finish();
   			 	overridePendingTransition(0, 0);           			
   			}
		}
	}
	
	private void LeftClick()
	{
        EditText platetype = (EditText) findViewById(R.id.editTextplatetype);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());

        
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String platetypeDesc = SearchData.ScrollDownThroughFile("TYPE.T", getApplicationContext());
		if (platetypeDesc.equals("NIF") )
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
			if(platetypeDesc.length()> 10)
			{
				tvDesc.setText(platetypeDesc.substring(3));
		        WorkingStorage.StoreCharVal(Defines.RotateValue, platetypeDesc.substring(0,1), getApplicationContext());
		        platetype.setText(platetypeDesc.substring(0,1));
		        platetype.selectAll();
		        platetype.requestFocus();	
			}
			else
			{
				LeftClick();
			}	
		}

	}
	
	private void RightClick()
	{
        EditText platetype = (EditText) findViewById(R.id.editTextplatetype);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());

        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String platetypeDesc = SearchData.ScrollUpThroughFile("TYPE.T", getApplicationContext());
		if (platetypeDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
			if(platetypeDesc.length()> 10)
			{
				tvDesc.setText(platetypeDesc.substring(3));
		        WorkingStorage.StoreCharVal(Defines.RotateValue, platetypeDesc.substring(0,1), getApplicationContext());
		        platetype.setText(platetypeDesc.substring(0,1));
		        platetype.selectAll();
		        platetype.requestFocus();	
			}
			else
			{
				RightClick();
			}			
		}
	}	
	
	private void TextChange(String SearchString)
	{
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
        EditText platetype = (EditText) findViewById(R.id.editTextplatetype);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
        String platetypeDesc = SearchData.FindRecordLine(SearchString, SearchString.length(), "TYPE.T", getApplicationContext());
		if (platetypeDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
	        tvDesc.setText(platetypeDesc.substring(3));
	        WorkingStorage.StoreCharVal(Defines.RotateValue, platetypeDesc.substring(0,1), getApplicationContext());
	        platetype.selectAll();
	        platetype.requestFocus();	
		} 

	}
	
	public void onCreate(Bundle savedInstanceplatetype) {
        super.onCreate(savedInstanceplatetype);
        setContentView(R.layout.platetypeform);      
       
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
       
        EditText platetype = (EditText) findViewById(R.id.editTextplatetype);
        
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
        if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("HUNTWV"))
        {
        	WorkingStorage.StoreLongVal(Defines.RecordPointer,4, getApplicationContext()); 
        	// 4 is regular license for Huntington
        }
        else
        {
            WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());        	
        }
        String platetypeDesc = SearchData.ScrollUpThroughFile("TYPE.T", getApplicationContext());
        String platetypeCode = platetypeDesc.substring(0,1);
        tvDesc.setText(platetypeDesc.substring(3));
        platetype.setText(platetypeCode);
        platetype.requestFocus();
        platetype.selectAll();
        
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	    CustomKeyboard.PickAKeyboard(platetype, "LICENSE", getApplicationContext(), keyboardView);
        if (savedInstanceplatetype == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		platetype.selectAll();
        }
               
    	platetype.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});

       
        
        platetype.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString());
			}     
        });
        
        ImageButton left = (ImageButton) findViewById(R.id.buttonPrevious);
        left.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   LeftClick();
           }          
        });
        
        ImageButton right = (ImageButton) findViewById(R.id.buttonNext);
        right.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   RightClick();
           }          
        });
	}
	
}