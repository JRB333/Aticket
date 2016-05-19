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


public class CrossStreetForm extends ActivityGroup {
	//private static final int REQUEST_CODE = 1234;
	
	private void EnterClick()
	{
		TextView tvStreet = (TextView) findViewById(R.id.widget1);
		String tempString = tvStreet.getText().toString().trim();
		if (tempString.length()>=1)
		{
			WorkingStorage.StoreCharVal(Defines.PrintCrossStreetVal,tempString,getApplicationContext());
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
			Messagebox.Message("Cross Street Required", getApplicationContext());
			EditText Street = (EditText) findViewById(R.id.editTextCrossStreet);
	        Street.selectAll();
	        Street.requestFocus();
			
		}
	}
	
	private void ShowData(String StreetString)
	{
		WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.RotateValue, StreetString.substring(0,16), getApplicationContext());		
		
        TextView tvStreet = (TextView) findViewById(R.id.widget1);        
        if (StreetString.length()>=34)
        {
        	tvStreet.setText(StreetString.subSequence(0, 16));
        }
        else
        {
        	tvStreet.setText(StreetString);
        }
	}
	
	private void LeftClick()
	{
		String StreetDesc = SearchData.ScrollDownThroughFile("STREET.T", getApplicationContext());
		if(StreetDesc.length()> 10)
		{
			ShowData(StreetDesc);
			EditText Street = (EditText) findViewById(R.id.editTextCrossStreet);
			Street.setText("");
			Street.requestFocus();
		}
		else
		{
			LeftClick();
		}
	}

	private void RightClick()
	{
		String StreetDesc = SearchData.ScrollUpThroughFile("STREET.T", getApplicationContext());
		if(StreetDesc.length()> 10)
		{
			ShowData(StreetDesc);
			EditText Street = (EditText) findViewById(R.id.editTextCrossStreet);
			Street.setText("");
			Street.requestFocus();
		}
		else
		{
			RightClick();
		}

	}
	private void TextChange(String SearchString)
	{
		if (SearchString.trim().equals("")) //prevent spaces from not causing the search routine correctly
		{
		}
		else
		{
			String StreetString = SearchData.FindRecordLine(SearchString, SearchString.length(), "STREET.T", getApplicationContext());
			TextView tvStreet = (TextView) findViewById(R.id.widget1);
			if (StreetString.equals("NIF")) //Now We Will do additions file custom stuff
			{
				tvStreet.setText(SearchString);	
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, SearchString, getApplicationContext());
	
			}else
			{
				tvStreet.setText(StreetString.substring(0,16));
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetString, getApplicationContext());
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crossstreetform);       
       
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
              
        String StreetDesc = "";
        String PreviousStreet = "";
        if (savedInstanceState==null)
        {    	   
        	WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
       		PreviousStreet = WorkingStorage.GetCharVal(Defines.PrintStreetVal, getApplicationContext());
        	if (PreviousStreet.equals(""))
    	    {
    		    WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		    StreetDesc = SearchData.ScrollUpThroughFile("STREET.T", getApplicationContext()); 		   
    		    WorkingStorage.StoreCharVal(Defines.RotateValue, StreetDesc.substring(0,16), getApplicationContext());
    		    WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetDesc, getApplicationContext());
    	    }
    	    else
    	    {
    		    StreetDesc = SearchData.FindRecordLine(PreviousStreet, PreviousStreet.length(), "STREET.T", getApplicationContext());
    		    if (StreetDesc.length() > 16)
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, StreetDesc.substring(0,16), getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetDesc, getApplicationContext());
    		    }
    		    else
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, PreviousStreet, getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, PreviousStreet, getApplicationContext());
    		    }    		    
    	    }        	
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   PreviousStreet = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   StreetDesc = SearchData.FindRecordLine(PreviousStreet, PreviousStreet.length(), "STREET.T", getApplicationContext()); 
        }
        TextView tvStreet = (TextView) findViewById(R.id.widget1);        
        if (StreetDesc.length()>=34)
        {
        	tvStreet.setText(StreetDesc.subSequence(0, 16));
        }
        else
        {
        	tvStreet.setText(StreetDesc);
        }
        
        final EditText Street = (EditText) findViewById(R.id.editTextCrossStreet);
        Street.selectAll();
        Street.requestFocus();
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	    CustomKeyboard.PickAKeyboard(Street, "FULL", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		//Street.setTextColor(Color.parseColor("#ff0000CC"));
        }
        
    	Street.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});
       
        
        Street.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString().trim());
				//Messagebox.Message(s.toString(), getApplicationContext());
			}     
        });
	}
}
