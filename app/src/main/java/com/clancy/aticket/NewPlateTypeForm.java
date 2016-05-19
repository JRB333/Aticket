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


public class NewPlateTypeForm extends ActivityGroup {
	//private static final int REQUEST_CODE = 1234;
	
	private void EnterClick()
	{
		
		String tempString = WorkingStorage.GetCharVal(Defines.EntireDataString,  getApplicationContext());
		TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DescString = tvDesc.getText().toString().trim();
		if (DescString.equals("NOT FOUND"))
		{
			EditText etNewPlateType = (EditText) findViewById(R.id.editTextnewplatetype);
			etNewPlateType.selectAll();
			etNewPlateType.requestFocus();
		}
		else
		{
			if (tempString.length()>=18)
			{
				WorkingStorage.StoreCharVal(Defines.SaveTypeVal,tempString.substring(14, 15),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintTypeVal,tempString.substring(0, 14),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PreviousTypeCodeVal,tempString.substring(0, 14),getApplicationContext());
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
	private void ShowData(String NewPlateTypeString)
	{
		WorkingStorage.StoreCharVal(Defines.EntireDataString, NewPlateTypeString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.RotateValue, NewPlateTypeString.substring(0,14), getApplicationContext());		
		
		TextView tvNewPlateType = (TextView) findViewById(R.id.widget1);        
		if (NewPlateTypeString.equals("NIF"))
		{
			tvNewPlateType.setText("NOT FOUND");			
		}else
		{
			if (NewPlateTypeString.length()>=18)
			{
				tvNewPlateType.setText(NewPlateTypeString.subSequence(0, 14));
			}
			else
			{
				tvNewPlateType.setText(NewPlateTypeString);
			}
		}
	}
	
	private void LeftClick()
	{
		String NewPlateTypeDesc = SearchData.ScrollDownThroughFile("TYPE.T", getApplicationContext());
		if(NewPlateTypeDesc.length()> 14)
		{
			ShowData(NewPlateTypeDesc);
			EditText NewPlateType = (EditText) findViewById(R.id.editTextnewplatetype);
			NewPlateType.setText("");
		}
		else
		{
			LeftClick();
		}
	}

	private void RightClick()
	{
		String NewPlateTypeDesc = SearchData.ScrollUpThroughFile("TYPE.T", getApplicationContext());
		if(NewPlateTypeDesc.length()> 14)
		{
			ShowData(NewPlateTypeDesc);
			EditText NewPlateType = (EditText) findViewById(R.id.editTextnewplatetype);
			NewPlateType.setText("");
		}
		else
		{
			RightClick();
		}
        //NewPlateType.requestFocus();

	}
	private void TextChange(String SearchString)
	{
		if (SearchString.trim().equals("")) //prevent spaces from not causing the search routine correctly
		{
		}
		else
		{
			String NewPlateTypeString = SearchData.FindRecordLine(SearchString, SearchString.length(), "TYPE.T", getApplicationContext());
			TextView tvNewPlateType = (TextView) findViewById(R.id.widget1);
			if (NewPlateTypeString.equals("NIF")) //Now We Will do additions file custom stuff
			{
				tvNewPlateType.setText("NOT FOUND");
				WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
	
			}else
			{
				tvNewPlateType.setText(NewPlateTypeString.substring(0,14));
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, NewPlateTypeString, getApplicationContext());
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newplatetypeform);       
       
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
              
        String NewPlateTypeDesc = "";
        String PreviousNewPlateType = "";
        if (savedInstanceState==null)
        {    	   
        	WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
       		PreviousNewPlateType = WorkingStorage.GetCharVal(Defines.PreviousTypeCodeVal, getApplicationContext());
       		if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("MENOMONIE") ||
       				WorkingStorage.GetCharVal(Defines.DefaultState, getApplicationContext()).equals("WI") ||      				
       				WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("LACROSSE"))
       		{
       			PreviousNewPlateType = "AUTOMOBILE";
       		}
        	if (PreviousNewPlateType.equals(""))
    	    {
    		    WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		    NewPlateTypeDesc = SearchData.ScrollUpThroughFile("TYPE.T", getApplicationContext()); 		   
    		    WorkingStorage.StoreCharVal(Defines.RotateValue, NewPlateTypeDesc.substring(0,14), getApplicationContext());
    		    WorkingStorage.StoreCharVal(Defines.EntireDataString, NewPlateTypeDesc , getApplicationContext());
    	    }
    	    else
    	    {
    		    NewPlateTypeDesc = SearchData.FindRecordLine(PreviousNewPlateType, PreviousNewPlateType.length(), "TYPE.T", getApplicationContext());
    		    if (NewPlateTypeDesc.length() > 14)
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, NewPlateTypeDesc.substring(0,14), getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, NewPlateTypeDesc , getApplicationContext());
    		    }
    		    else
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, PreviousNewPlateType, getApplicationContext());	
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, PreviousNewPlateType , getApplicationContext());
    		    }    		    
    	    }        	
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   PreviousNewPlateType = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   NewPlateTypeDesc = SearchData.FindRecordLine(PreviousNewPlateType, PreviousNewPlateType.length(), "TYPE.T", getApplicationContext()); 
        }
        TextView tvNewPlateType = (TextView) findViewById(R.id.widget1);        
        if (NewPlateTypeDesc.length()>=18)
        {
        	tvNewPlateType.setText(NewPlateTypeDesc.subSequence(0, 14));
        }
        else
        {
        	tvNewPlateType.setText(NewPlateTypeDesc);
        }
        if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("HARRISBURG"))
        {
        	TextView tvTitle = (TextView) findViewById(R.id.widgetEnternewplatetype);
        	tvTitle.setText("D.J. CODE");
        }
        
        final EditText NewPlateType = (EditText) findViewById(R.id.editTextnewplatetype);
        NewPlateType.selectAll();
        NewPlateType.requestFocus();
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        CustomKeyboard.PickAKeyboard(NewPlateType, "FULL", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
        }
        
    	NewPlateType.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});
       
        
        NewPlateType.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString().trim());
				//Messagebox.Message(s.toString(), getApplicationContext());
			}     
        });
	}
}

