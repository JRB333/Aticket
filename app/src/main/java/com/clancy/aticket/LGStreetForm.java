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


public class LGStreetForm extends ActivityGroup {
	//private static final int REQUEST_CODE = 1234;
	
	private void EnterClick()
	{
		
        TextView tvLGStreet1 = (TextView) findViewById(R.id.textViewDesc1);        
        if (tvLGStreet1.getText().toString().trim().equals(""))
        {
        	Messagebox.Message("Location Required!",getApplicationContext());
        	return;
        }
        TextView tvLGStreet2 = (TextView) findViewById(R.id.textViewDesc2);
        TextView tvLGStreet3 = (TextView) findViewById(R.id.textViewDesc3);

        WorkingStorage.StoreCharVal(Defines.PrintLGStreet1Val,tvLGStreet1.getText().toString().trim(),getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.PrintLGStreet2Val,tvLGStreet2.getText().toString().trim(),getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.PrintLGStreet3Val,tvLGStreet3.getText().toString().trim(),getApplicationContext());

	   	if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
	    {
	  		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	   		startActivityForResult(myIntent, 0);
	   		finish();
	   		overridePendingTransition(0, 0);    
	    }					
	}
	
	private void ShowData(String LGStreetString)
	{
		WorkingStorage.StoreCharVal(Defines.EntireDataString, LGStreetString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.RotateValue, LGStreetString.substring(0,90), getApplicationContext());		
		
        TextView tvLGStreet1 = (TextView) findViewById(R.id.textViewDesc1);        
        TextView tvLGStreet2 = (TextView) findViewById(R.id.textViewDesc2);
        TextView tvLGStreet3 = (TextView) findViewById(R.id.textViewDesc3);
        if (LGStreetString.length()>=90)
        {
        	tvLGStreet1.setText(LGStreetString.subSequence(0, 30));
        	tvLGStreet2.setText(LGStreetString.subSequence(30, 60));
        	tvLGStreet3.setText(LGStreetString.subSequence(60, 90));  
        }
        else
        {
        	tvLGStreet1.setText(LGStreetString);
			tvLGStreet2.setText(""); //blank out 2 and 3 for custom entry, only allow field 1
			tvLGStreet3.setText("");        	
        }
	}
	
	private void LeftClick()
	{
		String LGStreetDesc = SearchData.ScrollDownThroughFile("LGSTREET.A", getApplicationContext());
		if(LGStreetDesc.length()> 10)
		{
			ShowData(LGStreetDesc);
			EditText LGStreet = (EditText) findViewById(R.id.editTextLGStreet);
			LGStreet.setText("");
			LGStreet.requestFocus();
		}
		else
		{
			LeftClick();
		}
	}

	private void RightClick()
	{
		String LGStreetDesc = SearchData.ScrollUpThroughFile("LGSTREET.A", getApplicationContext());
		if(LGStreetDesc.length()> 10)
		{
			ShowData(LGStreetDesc);
			EditText LGStreet = (EditText) findViewById(R.id.editTextLGStreet);
			LGStreet.setText("");
			LGStreet.requestFocus();
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
			String LGStreetString = SearchData.FindRecordLine(SearchString, SearchString.length(), "LGSTREET.A", getApplicationContext());
			TextView tvLGStreet1 = (TextView) findViewById(R.id.textViewDesc1);
			TextView tvLGStreet2 = (TextView) findViewById(R.id.textViewDesc2);
			TextView tvLGStreet3 = (TextView) findViewById(R.id.textViewDesc3);
			if (LGStreetString.equals("NIF")) //Now We Will do additions file custom stuff
			{
				tvLGStreet1.setText(SearchString);
				tvLGStreet2.setText(""); //blank out 2 and 3 for custom entry, only allow field 1
				tvLGStreet3.setText("");
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, SearchString, getApplicationContext());
	
			}else
			{
            	tvLGStreet1.setText(LGStreetString.subSequence(0, 30));
            	tvLGStreet2.setText(LGStreetString.subSequence(30, 60));
            	tvLGStreet3.setText(LGStreetString.subSequence(60, 90));  
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, LGStreetString, getApplicationContext());
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lgstreetform);       
              
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
              
        if (savedInstanceState==null)
        {    	   
            String LGStreetDesc = "";
            String PreviousLGStreet = "";
            
            TextView tvLGStreet1 = (TextView) findViewById(R.id.textViewDesc1);        
            TextView tvLGStreet2 = (TextView) findViewById(R.id.textViewDesc2);
            TextView tvLGStreet3 = (TextView) findViewById(R.id.textViewDesc3);
            
            WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
       		PreviousLGStreet = WorkingStorage.GetCharVal(Defines.PrintLGStreet1Val, getApplicationContext());
        	if (PreviousLGStreet.equals(""))
    	    {
    		    WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		    LGStreetDesc = SearchData.ScrollUpThroughFile("LGSTREET.A", getApplicationContext()); 		   
    		    WorkingStorage.StoreCharVal(Defines.RotateValue, LGStreetDesc.substring(0, 90), getApplicationContext());
    		    WorkingStorage.StoreCharVal(Defines.EntireDataString, LGStreetDesc, getApplicationContext());
            	tvLGStreet1.setText(LGStreetDesc.subSequence(0, 30));
            	tvLGStreet2.setText(LGStreetDesc.subSequence(30, 60));
            	tvLGStreet3.setText(LGStreetDesc.subSequence(60, 90));    		    
    	    }
    	    else
    	    {
    		    LGStreetDesc = SearchData.FindRecordLine(PreviousLGStreet, PreviousLGStreet.length(), "LGSTREET.A", getApplicationContext());
    		    if (LGStreetDesc.length() > 90)
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, LGStreetDesc.substring(0,90), getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, LGStreetDesc, getApplicationContext());
                	tvLGStreet1.setText(LGStreetDesc.subSequence(0, 30));
                	tvLGStreet2.setText(LGStreetDesc.subSequence(30, 60));
                	tvLGStreet3.setText(LGStreetDesc.subSequence(60, 90));
    		    }
    		    else
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, PreviousLGStreet, getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, PreviousLGStreet, getApplicationContext());
    		    	tvLGStreet1.setText(LGStreetDesc); 
                	tvLGStreet2.setText("");
                	tvLGStreet3.setText("");    		    	
    		    }    		    
    	    }        	
        } 

        final EditText LGStreet = (EditText) findViewById(R.id.editTextLGStreet);
        LGStreet.selectAll();
        LGStreet.requestFocus();
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        CustomKeyboard.PickAKeyboard(LGStreet, "FULL", getApplicationContext(), keyboardView);

        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
        }
        
        
        LGStreet.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString().trim());

			}     
        });
	}
}

