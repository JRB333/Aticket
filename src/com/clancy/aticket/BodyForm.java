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
import android.widget.ImageButton;
import android.widget.TextView;
//import android.speech.RecognizerIntent;


public class BodyForm extends ActivityGroup {
	//private static final int REQUEST_CODE = 1234;
	
	private void EnterClick()
	{
		
		String tempString = WorkingStorage.GetCharVal(Defines.EntireDataString,  getApplicationContext());
//		Messagebox.Message("TempString: "+tempString,getApplicationContext());
		if (tempString.length()>=13)
		{
			WorkingStorage.StoreCharVal(Defines.SaveBodyVal,tempString.substring(12, 13),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintBodyVal,tempString.substring(0, 12),getApplicationContext());
		}
		else
		{
			WorkingStorage.StoreCharVal(Defines.SaveBodyVal,"X",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintBodyVal,tempString,getApplicationContext());
		}
		
	   	if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
	    {
	  		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	   		startActivityForResult(myIntent, 0);
	   		finish();
	   		overridePendingTransition(0, 0);    
	    }		
	}
	private void ShowData(String BodyString)
	{
		WorkingStorage.StoreCharVal(Defines.EntireDataString, BodyString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.RotateValue, BodyString.substring(0,12), getApplicationContext());		
		
        TextView tvBody = (TextView) findViewById(R.id.widget1);        
        if (BodyString.length()>=13)
        {
        	tvBody.setText(BodyString.subSequence(0, 12));
        }
        else
        {
        	tvBody.setText(BodyString);
        }
	}
	
	private void LeftClick()
	{
		String BodyDesc = SearchData.ScrollDownThroughFile("BODY.A", getApplicationContext());
		ShowData(BodyDesc);
		EditText Body = (EditText) findViewById(R.id.editTextBody);
		Body.setText("");
        //Body.requestFocus();
	}

	private void RightClick()
	{
		String BodyDesc = SearchData.ScrollUpThroughFile("BODY.A", getApplicationContext());
		ShowData(BodyDesc);
		EditText Body = (EditText) findViewById(R.id.editTextBody);
		Body.setText("");
        //Body.requestFocus();

	}
	private void TextChange(String SearchString)
	{
		if (SearchString.trim().equals("")) //prevent spaces from not causing the search routine correctly
		{
		}
		else
		{
			String BodyString = SearchData.FindRecordLine(SearchString, SearchString.length(), "BODY.A", getApplicationContext());
			TextView tvBody = (TextView) findViewById(R.id.widget1);
			if (BodyString.equals("NIF")) //Now We Will do additions file custom stuff
			{
				tvBody.setText(SearchString);	
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, SearchString, getApplicationContext());
	
			}else
			{
				tvBody.setText(BodyString.substring(0,12));
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, BodyString, getApplicationContext());
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodyform);       
       
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterBody);
	        tvSpanish.setText("ENTRE CUERPO:");
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
              
        String BodyDesc = "";
        String PreviousBody = "";
        if (savedInstanceState==null)
        {    	   
        	WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
        	PreviousBody = WorkingStorage.GetCharVal(Defines.AutoBodyVal,getApplicationContext()).trim();
        	if(PreviousBody.equals(""))
        	{
        		PreviousBody = WorkingStorage.GetCharVal(Defines.PrintBodyVal, getApplicationContext());
        	}
        	if (PreviousBody.equals(""))
    	    {
    		    WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		    BodyDesc = SearchData.ScrollUpThroughFile("BODY.A", getApplicationContext()); 		   
    		    WorkingStorage.StoreCharVal(Defines.RotateValue, BodyDesc.substring(0,12), getApplicationContext());
    		    WorkingStorage.StoreCharVal(Defines.EntireDataString, BodyDesc, getApplicationContext());
    	    }
    	    else
    	    {
    		    BodyDesc = SearchData.FindRecordLine(PreviousBody, PreviousBody.length(), "BODY.A", getApplicationContext());
    		    if (BodyDesc.length() >= 13)
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, BodyDesc.substring(0,12), getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, BodyDesc, getApplicationContext());
    		    }
    		    else
    		    {
    		    	WorkingStorage.StoreCharVal(Defines.RotateValue, PreviousBody, getApplicationContext());
    		    	WorkingStorage.StoreCharVal(Defines.EntireDataString, PreviousBody, getApplicationContext());
    		    }    		    
    	    }        	
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   PreviousBody = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   BodyDesc = SearchData.FindRecordLine(PreviousBody, PreviousBody.length(), "BODY.A", getApplicationContext()); 
        }
        TextView tvBody = (TextView) findViewById(R.id.widget1);        
        if (BodyDesc.length()>=13)
        {
        	tvBody.setText(BodyDesc.subSequence(0, 12));
        }
        else
        {
        	tvBody.setText(BodyDesc);
        }
        
        final EditText Body = (EditText) findViewById(R.id.editTextBody);
        Body.selectAll();
        Body.requestFocus();
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        //final Vibrator vibKey = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
   	    CustomKeyboard.PickAKeyboard(Body, "FULL", getApplicationContext(), keyboardView);
        if (savedInstanceState == null) 
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		//Body.setTextColor(Color.parseColor("#ff0000CC"));
        }
       
    	Body.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});
       /* Body.setOnKeyListener(new View.OnKeyListener() {
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
        
        Body.addTextChangedListener(new TextWatcher(){        
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
            final EditText Body = (EditText) findViewById(R.id.editTextBody);
            Body.setText(matches.get(0));
            //wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,	                    matches));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */