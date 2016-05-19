package com.clancy.aticket;


import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LAMTAPermitForm extends ActivityGroup implements OnInitListener {

	TextToSpeech mTts = null;
	
	 private static final int REQUEST_CODE = 5647;
	private Runnable mRunnable = new Runnable() 
	 	{     
	 		public void run() 
	 		{     
	 			EnterClick();
	 		}
	 	};
	
	  private void EnterClick()
	  {
		  GetDate.GetDateTime(getApplicationContext());
		  EditText Tag = (EditText) findViewById(R.id.editTextTag);   
		  TextView tvMessage = (TextView) findViewById(R.id.textMessage);

		  String FindTag = Tag.getText().toString().toUpperCase().trim();
		  FindTag = FindTag.replace(" ", "-"); // added this to prevent URI exception with spaces in the return translation
		  String ReturnMessage = "";
		  String PermitClientFolder = WorkingStorage.GetCharVal(Defines.MonthlyPermitClientVal,getApplicationContext());
		  ReturnMessage = HTTPFileTransfer.HTTPGetPageContent("http://www.permit-sales.com/" + PermitClientFolder + "/mtapl.asp?permit=" + FindTag , getApplicationContext());

		  ReturnMessage = ReturnMessage.replace("<br>", "\n"); 
		  ReturnMessage = ReturnMessage.replace("-", " "); 
		  if (ReturnMessage.equals("Failed"))
			  tvMessage.setText("Connection Failed");
		  else
		  {
			  if( ReturnMessage.length() > 4)
				  ReturnMessage = ReturnMessage.substring(0,ReturnMessage.length()-3);
			  
			  tvMessage.setText(ReturnMessage);
			  

			  String myText1 = "Valid";
			  mTts.speak(ReturnMessage, TextToSpeech.QUEUE_FLUSH, null);
		  }
		  EditText tag = (EditText) findViewById(R.id.editTextTag);
    	  WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      	  Tag.selectAll();
      	  Button enterb = (Button) findViewById(R.id.buttonEnter);
		  enterb.setEnabled(true);      	  
		  
	  }

	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        mTts.shutdown();
	        // The activity is about to be destroyed.
	    }
	  
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lamtapermitform);
        
		mTts = new TextToSpeech(this, this);
       
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
        final TextView tvMessage = (TextView) findViewById(R.id.textMessage);
        final Button enter = (Button) findViewById(R.id.buttonEnter);
        enter.setOnClickListener(new View.OnClickListener() {
	           public void onClick(View view) {
	        	   
               	enter.setEnabled(false);
               	tvMessage.setText("Checking...");
    			Handler mHandler = new Handler(); 
    			mHandler.postDelayed(mRunnable, 100);
	        	CustomVibrate.VibrateButton(getApplicationContext());
	        //	   EnterClick();	        	   
	           }	          
	   });

	   EditText Tag = (EditText) findViewById(R.id.editTextTag);

	   Tag.requestFocus();
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(Tag, "NUMONLY", getApplicationContext(), keyboardView);
       if (savedInstanceState == null)
       {
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Tag.selectAll();
       }
   	   
       Button speak = (Button) findViewById(R.id.buttonTalk);
       speak.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   // Check to see if a recognition activity is present
        	   PackageManager pm = getPackageManager();
        	   List activities = pm.queryIntentActivities(
        	     new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        	   if (activities.size() != 0) {
                 	enter.setEnabled(false);
                   	tvMessage.setText("Checking...");
            	    startVoiceRecognitionActivity();
        	   } 
        	   else 
        	   {
        		   tvMessage.setText("Voice Recognition not present on this device!");
        	   }
          }
          
       });	
       Tag.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
      	});   	   
   
	}

      private void startVoiceRecognitionActivity()
      {
          Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
          intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                  RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
          intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Park By Phone Tag Number...");
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
              EditText Tag = (EditText) findViewById(R.id.editTextTag); 
              Tag.setText(matches.get(0));
  			  Handler mHandler = new Handler(); 
			  mHandler.postDelayed(mRunnable, 100);

              //wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,	                    matches));
          }
          super.onActivityResult(requestCode, resultCode, data);
      }

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
	}

}