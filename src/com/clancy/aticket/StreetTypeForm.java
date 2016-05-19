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
import android.widget.ImageButton;
import android.widget.TextView;


public class StreetTypeForm extends ActivityGroup {
	//private static final int REQUEST_CODE = 1234;
	
	private void EnterClick()
	{
		
		String tempString = WorkingStorage.GetCharVal(Defines.EntireDataString,  getApplicationContext());
		TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DescString = tvDesc.getText().toString().trim();
		if (DescString.equals("NOT FOUND"))
		{
			EditText etStreetType = (EditText) findViewById(R.id.editTextstreettype);
			etStreetType.selectAll();
			etStreetType.requestFocus();
		}
		else
		{
			if (tempString.length()>=23)
			{
				WorkingStorage.StoreCharVal(Defines.SaveStreetTypeVal,tempString.substring(20, 23),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintStreetTypeVal,tempString.substring(0, 20),getApplicationContext());
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
	private void ShowData(String StreetTypeString)
	{
		WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetTypeString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.RotateValue, StreetTypeString.substring(0,20), getApplicationContext());		
		
		TextView tvStreetType = (TextView) findViewById(R.id.widget1);        
		if (StreetTypeString.equals("NIF"))
		{
			tvStreetType.setText("NOT FOUND");			
		}else
		{
			if (StreetTypeString.length()>=23)
			{
				tvStreetType.setText(StreetTypeString.subSequence(0, 20));
			}
			else
			{
				tvStreetType.setText(StreetTypeString);
			}
		}
	}
	
	private void LeftClick()
	{
		String StreetTypeDesc = SearchData.ScrollDownThroughFile("STRTTYPE.A", getApplicationContext());
		ShowData(StreetTypeDesc);
		EditText StreetType = (EditText) findViewById(R.id.editTextstreettype);
		StreetType.setText("");
        //StreetType.requestFocus();
	}

	private void RightClick()
	{
		String StreetTypeDesc = SearchData.ScrollUpThroughFile("STRTTYPE.A", getApplicationContext());
		ShowData(StreetTypeDesc);
		EditText StreetType = (EditText) findViewById(R.id.editTextstreettype);
   		StreetType.setText("");
        //StreetType.requestFocus();

	}
	private void TextChange(String SearchString)
	{
		if (SearchString.trim().equals("")) //prevent spaces from not causing the search routine correctly
		{
		}
		else
		{
			String StreetTypeString = SearchData.FindRecordLine(SearchString, SearchString.length(), "STRTTYPE.A", getApplicationContext());
			TextView tvStreetType = (TextView) findViewById(R.id.widget1);
			if (StreetTypeString.equals("NIF")) //Now We Will do additions file custom stuff
			{
				tvStreetType.setText("NOT FOUND");
				WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
	
			}else
			{
				tvStreetType.setText(StreetTypeString.substring(0,20));
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetTypeString, getApplicationContext());
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.streettypeform);       
       
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
              
        String StreetTypeDesc = "";
        String PreviousStreetType = "";
        if (savedInstanceState==null)
        {    	   
        	WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
       		PreviousStreetType = WorkingStorage.GetCharVal(Defines.PreviousStreetTypeCodeVal, getApplicationContext());
        	if (PreviousStreetType.equals(""))
    	    {
    		    WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		    StreetTypeDesc = SearchData.ScrollUpThroughFile("STRTTYPE.A", getApplicationContext()); 		   
    		    WorkingStorage.StoreCharVal(Defines.RotateValue, StreetTypeDesc.substring(0,20), getApplicationContext());
    		    WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetTypeDesc , getApplicationContext());
    	    }
    	    else
    	    {
    		    StreetTypeDesc = SearchData.FindRecordLine(PreviousStreetType, PreviousStreetType.length(), "STRTTYPE.A", getApplicationContext());
    		    if (StreetTypeDesc.length() > 20)
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, StreetTypeDesc.substring(0,20), getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetTypeDesc , getApplicationContext());
    		    }
    		    else
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, PreviousStreetType, getApplicationContext());	
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, PreviousStreetType , getApplicationContext());
    		    }    		    
    	    }        	
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   PreviousStreetType = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   StreetTypeDesc = SearchData.FindRecordLine(PreviousStreetType, PreviousStreetType.length(), "STRTTYPE.A", getApplicationContext()); 
        }
        TextView tvStreetType = (TextView) findViewById(R.id.widget1);        
        if (StreetTypeDesc.length()>=23)
        {
        	tvStreetType.setText(StreetTypeDesc.subSequence(0, 20));
        }
        else
        {
        	tvStreetType.setText(StreetTypeDesc);
        }
        
        final EditText StreetType = (EditText) findViewById(R.id.editTextstreettype);
        StreetType.selectAll();
        StreetType.requestFocus();
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        CustomKeyboard.PickAKeyboard(StreetType, "FULL", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
        }
        
    	StreetType.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});
       
        
        StreetType.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString().trim());
				//Messagebox.Message(s.toString(), getApplicationContext());
			}     
        });
	}
}

