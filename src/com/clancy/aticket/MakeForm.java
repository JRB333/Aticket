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


public class MakeForm extends ActivityGroup {
	//private static final int REQUEST_CODE = 1234;
	
	private void EnterClick()
	{
		
		String tempString = WorkingStorage.GetCharVal(Defines.EntireDataString,  getApplicationContext());
		if (tempString.length()>=18)
		{
			WorkingStorage.StoreCharVal(Defines.SaveMakeVal,tempString.substring(16, 18),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintMakeVal,tempString.substring(0, 16),getApplicationContext());
		}
		else
		{
			WorkingStorage.StoreCharVal(Defines.SaveMakeVal,"XX",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintMakeVal,tempString,getApplicationContext());
		}
		
	   	if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
	    {
	  		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	   		startActivityForResult(myIntent, 0);
	   		finish();
	   		overridePendingTransition(0, 0);    
	    }		
	}
	private void ShowData(String MakeString)
	{
		WorkingStorage.StoreCharVal(Defines.EntireDataString, MakeString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.RotateValue, MakeString.substring(0,16), getApplicationContext());		
		
        TextView tvMake = (TextView) findViewById(R.id.widget1);        
        if (MakeString.length()>=18)
        {
        	tvMake.setText(MakeString.subSequence(0, 16));
        }
        else
        {
        	tvMake.setText(MakeString);
        }
	}
	
	private void LeftClick()
	{
		String MakeDesc = SearchData.ScrollDownThroughFile("MAKE.A", getApplicationContext());
		ShowData(MakeDesc);
		EditText make = (EditText) findViewById(R.id.editTextMake);
		make.setText("");
        //make.requestFocus();
	}

	private void RightClick()
	{
		String MakeDesc = SearchData.ScrollUpThroughFile("MAKE.A", getApplicationContext());
		ShowData(MakeDesc);
		EditText make = (EditText) findViewById(R.id.editTextMake);
   		make.setText("");
        //make.requestFocus();

	}
	private void TextChange(String SearchString)
	{
		if (SearchString.trim().equals("")) //prevent spaces from not causing the search routine correctly
		{
		}
		else
		{
			String MakeString = SearchData.FindRecordLine(SearchString, SearchString.length(), "MAKE.A", getApplicationContext());
			TextView tvMake = (TextView) findViewById(R.id.widget1);
			if (MakeString.equals("NIF")) //Now We Will do additions file custom stuff
			{
				tvMake.setText(SearchString);	
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, SearchString, getApplicationContext());
	
			}else
			{
				tvMake.setText(MakeString.substring(0,16));
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, MakeString, getApplicationContext());
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeform);       
       
        
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterMake);
	        tvSpanish.setText("ENTRE MARCA:");
	    } 
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
              
        String MakeDesc = "";
        String PreviousMake = "";
        if (savedInstanceState==null)
        {    	   
        	WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
        	PreviousMake = WorkingStorage.GetCharVal(Defines.AutoMakeVal,getApplicationContext()).trim();
        	if(PreviousMake.equals(""))
        	{
        		PreviousMake = WorkingStorage.GetCharVal(Defines.PrintMakeVal, getApplicationContext());
        	}
        	if (PreviousMake.equals(""))
    	    {
    		    WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		    MakeDesc = SearchData.ScrollUpThroughFile("MAKE.A", getApplicationContext()); 		   
    		    WorkingStorage.StoreCharVal(Defines.RotateValue, MakeDesc.substring(0,16), getApplicationContext());
    		    WorkingStorage.StoreCharVal(Defines.EntireDataString, MakeDesc , getApplicationContext());
    	    }
    	    else
    	    {
    		    MakeDesc = SearchData.FindRecordLine(PreviousMake, PreviousMake.length(), "MAKE.A", getApplicationContext());
    		    if (MakeDesc.length() > 16)
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, MakeDesc.substring(0,16), getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, MakeDesc , getApplicationContext());
    		    }
    		    else
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, PreviousMake, getApplicationContext());	
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, PreviousMake , getApplicationContext());
    		    }    		    
    	    }        	
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   PreviousMake = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   MakeDesc = SearchData.FindRecordLine(PreviousMake, PreviousMake.length(), "MAKE.A", getApplicationContext()); 
        }
        TextView tvMake = (TextView) findViewById(R.id.widget1);        
        if (MakeDesc.length()>=18)
        {
        	tvMake.setText(MakeDesc.subSequence(0, 16));
        }
        else
        {
        	tvMake.setText(MakeDesc);
        }
        
        final EditText make = (EditText) findViewById(R.id.editTextMake);
        make.selectAll();
        make.requestFocus();
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        CustomKeyboard.PickAKeyboard(make, "FULL", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		//make.setTextColor(Color.parseColor("#ff0000CC"));
        }
        
    	make.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});
       
       /* make.setOnKeyListener(new View.OnKeyListener() {
       	  public boolean onKey(View arg0, int arg1, KeyEvent event) {
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&  (arg1 == KeyEvent.KEYCODE_ENTER)   )
     		  { 
               		EnterClick();
               		return true;
     		  }
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&  (arg1 == 59)   )
     		  { 
               		LeftClick();
               		return true;
     		  }   	
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&  (arg1 == 60)   )
     		  { 
               		RightClick();
               		return true;
     		  }   
     		  return false;
       	  }     	   
        });  */
        
        make.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString().trim());
				//Messagebox.Message(s.toString(), getApplicationContext());
			}     
        });
	}
}

/*
Button speak = (Button) findViewById(R.id.buttonSpeak);
speak.setOnClickListener(new View.OnClickListener() {
    public void onClick(View view) {
 	   startVoiceRecognitionActivity();
   }
   
});	
private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, REQUEST_CODE);
    }
 

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
        	
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            //Messagebox.Message(matches.get(0), getApplicationContext());
            final EditText make = (EditText) findViewById(R.id.editTextMake);
            make.setText(matches.get(0));
            //wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,	                    matches));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */