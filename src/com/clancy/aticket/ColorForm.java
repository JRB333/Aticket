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


public class ColorForm extends ActivityGroup {
	//private static final int REQUEST_CODE = 1234;
	
	private void EnterClick()
	{
		
		String tempString = WorkingStorage.GetCharVal(Defines.EntireDataString,  getApplicationContext());
		if (tempString.length()>=9)
		{
			WorkingStorage.StoreCharVal(Defines.SaveColorVal,tempString.substring(8, 9),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintColorVal,tempString.substring(0, 8),getApplicationContext());
		}
		else
		{
			WorkingStorage.StoreCharVal(Defines.SaveColorVal,"X",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintColorVal,tempString,getApplicationContext());
		}
		
	   	if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
	    {
	  		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	   		startActivityForResult(myIntent, 0);
	   		finish();
	   		overridePendingTransition(0, 0);    
	    }		
	}
	private void ShowData(String ColorString)
	{
		WorkingStorage.StoreCharVal(Defines.EntireDataString, ColorString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.RotateValue, ColorString.substring(0,8), getApplicationContext());		
		
        TextView tvColor = (TextView) findViewById(R.id.widget1);        
        if (ColorString.length()>=9)
        {
        	tvColor.setText(ColorString.subSequence(0, 8));
        }
        else
        {
        	tvColor.setText(ColorString);
        }
	}
	
	private void LeftClick()
	{
		String ColorDesc = SearchData.ScrollDownThroughFile("COLOR.A", getApplicationContext());
		ShowData(ColorDesc);
		EditText color = (EditText) findViewById(R.id.editTextColor);
		color.setText("");
        //color.requestFocus();
	}

	private void RightClick()
	{
		String ColorDesc = SearchData.ScrollUpThroughFile("COLOR.A", getApplicationContext());
		ShowData(ColorDesc);
		EditText color = (EditText) findViewById(R.id.editTextColor);
		color.setText("");
        //color.requestFocus();

	}
	private void TextChange(String SearchString)
	{
		if (SearchString.trim().equals("")) //prevent spaces from not causing the search routine correctly
		{
		}
		else
		{
			String ColorString = SearchData.FindRecordLine(SearchString, SearchString.length(), "COLOR.A", getApplicationContext());
			TextView tvColor = (TextView) findViewById(R.id.widget1);
			if (ColorString.equals("NIF")) //Now We Will do additions file custom stuff
			{
				tvColor.setText(SearchString);	
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, SearchString, getApplicationContext());
	
			}else
			{
				tvColor.setText(ColorString.substring(0,8));
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, ColorString, getApplicationContext());
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorform);       
       
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterColor);
	        tvSpanish.setText("ENTRE COLOR:");
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
              
        String ColorDesc = "";
        String PreviousColor = "";
        if (savedInstanceState==null)
        {    	   
        	WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
        	PreviousColor = WorkingStorage.GetCharVal(Defines.AutoColorVal,getApplicationContext()).trim();
        	if(PreviousColor.equals("") || PreviousColor.equals("NIF"))
        	{
        		PreviousColor = WorkingStorage.GetCharVal(Defines.PrintColorVal, getApplicationContext());
        	}
        	if (PreviousColor.equals("") || PreviousColor.equals("NIF") )
    	    {
    		    WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		    ColorDesc = SearchData.ScrollUpThroughFile("COLOR.A", getApplicationContext()); 		   
    		    WorkingStorage.StoreCharVal(Defines.RotateValue, ColorDesc.substring(0,8), getApplicationContext());
    		    WorkingStorage.StoreCharVal(Defines.EntireDataString, ColorDesc, getApplicationContext());
    	    }
    	    else
    	    {
    		    ColorDesc = SearchData.FindRecordLine(PreviousColor, PreviousColor.length(), "COLOR.A", getApplicationContext());
    		    if (ColorDesc.length() >= 9)
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, ColorDesc.substring(0,8), getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, ColorDesc, getApplicationContext());
    		    }
    		    else
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, PreviousColor, getApplicationContext());	
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, PreviousColor, getApplicationContext());
    		    }    		    
    	    }        	
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   PreviousColor = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   ColorDesc = SearchData.FindRecordLine(PreviousColor, PreviousColor.length(), "COLOR.A", getApplicationContext()); 
        }
        TextView tvColor = (TextView) findViewById(R.id.widget1);        
        if (ColorDesc.length()>=9)
        {
        	tvColor.setText(ColorDesc.subSequence(0, 8));
        }
        else
        {
        	tvColor.setText(ColorDesc);
        }
        
        final EditText color = (EditText) findViewById(R.id.editTextColor);
        color.selectAll();
        color.requestFocus();
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        CustomKeyboard.PickAKeyboard(color, "FULL", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		//color.setTextColor(Color.parseColor("#ff0000CC"));
        }
        
    	color.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});
        /*color.setOnKeyListener(new View.OnKeyListener() {
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
        
        color.addTextChangedListener(new TextWatcher(){        
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
            final EditText Color = (EditText) findViewById(R.id.editTextColor);
            Color.setText(matches.get(0));
            //wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,	                    matches));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */