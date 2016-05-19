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
//import android.speech.RecognizerIntent;


public class LicColorForm extends ActivityGroup {
	//private static final int REQUEST_CODE = 1234;
	
	private void EnterClick()
	{
		
		String tempString = WorkingStorage.GetCharVal(Defines.EntireDataString,  getApplicationContext());
		TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DescString = tvDesc.getText().toString().trim();
		if (DescString.equals("NOT FOUND"))
		{
			EditText etLicColor = (EditText) findViewById(R.id.editTextliccolor);
			etLicColor.selectAll();
			etLicColor.requestFocus();
		}
		else
		{
			if (tempString.length()>=23)
			{
				WorkingStorage.StoreCharVal(Defines.SaveLicColorVal,tempString.substring(20, 23),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintLicColorVal,tempString.substring(0, 20),getApplicationContext());
			}	
		   	if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
		    {
		  		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		   		startActivityForResult(myIntent, 0);
		   		finish();
		   		overridePendingTransition(0, 0);    
		    }		
		}
	}
	private void ShowData(String LicColorString)
	{
		WorkingStorage.StoreCharVal(Defines.EntireDataString, LicColorString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.RotateValue, LicColorString.substring(0,20), getApplicationContext());		
		
		TextView tvLicColor = (TextView) findViewById(R.id.widget1);        
		if (LicColorString.equals("NIF"))
		{
			tvLicColor.setText("NOT FOUND");			
		}else
		{
			if (LicColorString.length()>=23)
			{
				tvLicColor.setText(LicColorString.subSequence(0, 20));
			}
			else
			{
				tvLicColor.setText(LicColorString);
			}
		}
	}
	
	private void LeftClick()
	{
		String LicColorDesc = SearchData.ScrollDownThroughFile("LICCOLOR.A", getApplicationContext());
		ShowData(LicColorDesc);
		EditText LicColor = (EditText) findViewById(R.id.editTextliccolor);
		LicColor.setText("");
        //LicColor.requestFocus();
	}

	private void RightClick()
	{
		String LicColorDesc = SearchData.ScrollUpThroughFile("LICCOLOR.A", getApplicationContext());
		ShowData(LicColorDesc);
		EditText LicColor = (EditText) findViewById(R.id.editTextliccolor);
   		LicColor.setText("");
        //LicColor.requestFocus();

	}
	private void TextChange(String SearchString)
	{
		if (SearchString.trim().equals("")) //prevent spaces from not causing the search routine correctly
		{
		}
		else
		{
			String LicColorString = SearchData.FindRecordLine(SearchString, SearchString.length(), "LICCOLOR.A", getApplicationContext());
			TextView tvLicColor = (TextView) findViewById(R.id.widget1);
			if (LicColorString.equals("NIF")) //Now We Will do additions file custom stuff
			{
				tvLicColor.setText("NOT FOUND");
				WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
	
			}else
			{
				tvLicColor.setText(LicColorString.substring(0,20));
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, LicColorString, getApplicationContext());
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liccolorform);       
       
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
        
        ImageButton previous = (ImageButton) findViewById(R.id.buttonPrevious);
        previous.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   LeftClick();
           }          
        });

        ImageButton next = (ImageButton) findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   RightClick();
           }          
        });
              
        String LicColorDesc = "";
        String PreviousLicColor = "";
        if (savedInstanceState==null)
        {    	   
        	WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
       		PreviousLicColor = WorkingStorage.GetCharVal(Defines.PreviousLicColorCodeVal, getApplicationContext());
        	if (PreviousLicColor.equals(""))
    	    {
    		    WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		    LicColorDesc = SearchData.ScrollUpThroughFile("LICCOLOR.A", getApplicationContext()); 		   
    		    WorkingStorage.StoreCharVal(Defines.RotateValue, LicColorDesc.substring(0,20), getApplicationContext());
    		    WorkingStorage.StoreCharVal(Defines.EntireDataString, LicColorDesc , getApplicationContext());
    	    }
    	    else
    	    {
    		    LicColorDesc = SearchData.FindRecordLine(PreviousLicColor, PreviousLicColor.length(), "LICCOLOR.A", getApplicationContext());
    		    if (LicColorDesc.length() > 20)
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, LicColorDesc.substring(0,20), getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, LicColorDesc , getApplicationContext());
    		    }
    		    else
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, PreviousLicColor, getApplicationContext());	
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, PreviousLicColor , getApplicationContext());
    		    }    		    
    	    }        	
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   PreviousLicColor = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   LicColorDesc = SearchData.FindRecordLine(PreviousLicColor, PreviousLicColor.length(), "LICCOLOR.A", getApplicationContext()); 
        }
        TextView tvLicColor = (TextView) findViewById(R.id.widget1);        
        if (LicColorDesc.length()>=23)
        {
        	tvLicColor.setText(LicColorDesc.subSequence(0, 20));
        }
        else
        {
        	tvLicColor.setText(LicColorDesc);
        }
        
        final EditText LicColor = (EditText) findViewById(R.id.editTextliccolor);
        LicColor.selectAll();
        LicColor.requestFocus();
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        CustomKeyboard.PickAKeyboard(LicColor, "FULL", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
        }
        
    	LicColor.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});
       
        
        LicColor.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString().trim());
				//Messagebox.Message(s.toString(), getApplicationContext());
			}     
        });
	}
}

