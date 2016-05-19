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


public class CommentForm extends ActivityGroup {
	private void EnterClick()
	{
		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
		String tempString = tvDesc1.getText().toString().trim();
		if (tempString.equals("NOT FOUND"))
		{
			EditText CommentText = (EditText) findViewById(R.id.editTextComment);
			String tempCode = CommentText.getText().toString().trim();
			if (tempCode.equals("XXX"))
			{
	           	Defines.clsClassName = Comment1Form.class ;
	            Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	            startActivityForResult(myIntent, 0);  
	            finish();
	            overridePendingTransition(0, 0);
			}else
			{	
				CommentText.selectAll();
				CommentText.requestFocus();
			}
		}
		else
		{
			  String ParseString = WorkingStorage.GetCharVal(Defines.EntireDataString, getApplicationContext());
	          while (ParseString.length() < 53)
	        	ParseString += " ";

			  WorkingStorage.StoreCharVal(Defines.SaveCommentVal, ParseString.substring(0,3), getApplicationContext());
			  WorkingStorage.StoreCharVal(Defines.PrintComment1Val, ParseString.substring(3, 19), getApplicationContext());
			  WorkingStorage.StoreCharVal(Defines.PrintComment2Val, ParseString.substring(19, 35), getApplicationContext());
			  WorkingStorage.StoreCharVal(Defines.PrintComment3Val, ParseString.substring(35,51), getApplicationContext());
						
			  if (WorkingStorage.GetCharVal(Defines.CommentLastPrompt,getApplicationContext()).equals("YES"))				
			  {
		     	if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
			    {
			     		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			     		startActivityForResult(myIntent, 0);
			     		finish();
			     		overridePendingTransition(0, 0);    
			    }	
			  }
		      if (WorkingStorage.GetCharVal(Defines.CommentLastPrompt,getApplicationContext()).equals("YES"))
		      {
		    	  if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
		    	  {
		    		  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		    		  startActivityForResult(myIntent, 0);
		    		  finish();
		    		  overridePendingTransition(0, 0);    
		    	  }
		      }
		      else if (WorkingStorage.GetCharVal(Defines.PlateMonthFlagVal,getApplicationContext()).equals("1")
					  && !WorkingStorage.GetCharVal(Defines.SavePlateVal,getApplicationContext()).equals("NOPLATE")
					  && !WorkingStorage.GetCharVal(Defines.YearMonthTogether,getApplicationContext()).equals("YES"))
			  {
					String blahblah  = WorkingStorage.GetCharVal(Defines.YearMonthTogether,getApplicationContext());				
		    	  	Defines.clsClassName = MonthForm.class ;
			        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			        startActivityForResult(myIntent, 0);  
			        finish();
			        overridePendingTransition(0, 0);
			  }	      
		      else if (WorkingStorage.GetCharVal(Defines.MeterFlagVal,getApplicationContext()).equals("1")
				  && !WorkingStorage.GetCharVal(Defines.SecondTicketVal,getApplicationContext()).equals("YES")
				  && !WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES"))
			  {
					Defines.clsClassName = MeterForm.class ;
			        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			        startActivityForResult(myIntent, 0);  
			        finish();
			        overridePendingTransition(0, 0);
			  }
		      else if (WorkingStorage.GetCharVal(Defines.MeterFlagVal,getApplicationContext()).equals("1")
						&& !WorkingStorage.GetCharVal(Defines.ExpandXMeter,getApplicationContext()).equals("XMETER")
						&& !WorkingStorage.GetCharVal(Defines.SpecialMeterFlag,getApplicationContext()).equals("YES")
						&& !WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES"))
			  {
		    	  	Defines.clsClassName = MeterForm.class ;
		    	  	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		    	  	startActivityForResult(myIntent, 0);  
		    	  	finish();
		    	  	overridePendingTransition(0, 0);
			  }
		      else if (WorkingStorage.GetCharVal(Defines.ChalkFlagVal,getApplicationContext()).equals("1")
					  && !WorkingStorage.GetCharVal(Defines.ChalkData,getApplicationContext()).equals("CHALKDATA"))
			  {
  					Defines.clsClassName = ChalkForm.class ;
  					Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
  					startActivityForResult(myIntent, 0);  
  					finish();
  					overridePendingTransition(0, 0);
			  }	 
		      else if (WorkingStorage.GetCharVal(Defines.StickerFlagVal,getApplicationContext()).equals("1"))
			  {
		    	  	Defines.clsClassName = StickerForm.class ;
		    	  	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		    	  	startActivityForResult(myIntent, 0);  
		    	  	finish();
		    	  	overridePendingTransition(0, 0);
			  }	
		      else
		      {
		    	  if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
		    	  {
		    		  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		    		  startActivityForResult(myIntent, 0);
		    		  finish();
		    		  overridePendingTransition(0, 0);    
		    	  }
		      }

			
		}
	}

	private void EnterClickPlus()
	{
		String ParseString = WorkingStorage.GetCharVal(Defines.EntireDataString, getApplicationContext());
        while (ParseString.length() < 53)
        	ParseString += " ";

		WorkingStorage.StoreCharVal(Defines.SaveCommentVal, ParseString.substring(0,3), getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.PrintComment1Val, ParseString.substring(3, 19), getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.PrintComment2Val, ParseString.substring(19, 35), getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.PrintComment3Val, ParseString.substring(35,51), getApplicationContext());
						
		TextView tvDesc2 = (TextView) findViewById(R.id.TextViewDesc2);
		//TextView tvDesc3 = (TextView) findViewById(R.id.TextViewDesc3);
	    if(tvDesc2.getText().toString().trim().equals(""))
	    {
	    	Defines.clsClassName = Comment2Form.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);  
			finish();
			overridePendingTransition(0, 0);
	    }
	    else
	    {
	    	Defines.clsClassName = Comment3Form.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);  
			finish();
			overridePendingTransition(0, 0);
	    }
		  
	}

	private void ShowData(String CommentString)
	{
        while (CommentString.length() < 53)
        	CommentString += " ";
		WorkingStorage.StoreCharVal(Defines.EntireDataString, CommentString, getApplicationContext());
		
		EditText CommentText = (EditText) findViewById(R.id.editTextComment);
		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
		tvDesc1.setText(CommentString.substring(3,19));
		TextView tvDesc2 = (TextView) findViewById(R.id.TextViewDesc2);
		tvDesc2.setText(CommentString.substring(19,35));
		TextView tvDesc3 = (TextView) findViewById(R.id.TextViewDesc3);
		tvDesc3.setText(CommentString.substring(35,51));
		WorkingStorage.StoreCharVal(Defines.RotateValue, CommentString.substring(0,3), getApplicationContext());
		CommentText.setText(CommentString.substring(0,3));
		CommentText.selectAll();
		CommentText.requestFocus();
		Button enterPlus = (Button) findViewById(R.id.buttonPlus);
        if (tvDesc1.getText().toString().trim().equals("NO COMMENT"))
        {
        	enterPlus.setVisibility(View.INVISIBLE);
        }
        else
        {
	        if(tvDesc2.getText().toString().trim().equals("") || tvDesc3.getText().toString().trim().equals(""))
	        {
	        	enterPlus.setVisibility(View.VISIBLE);
	        }
	        else
	        {
	        	enterPlus.setVisibility(View.INVISIBLE);
	        }	        
        	
        }		
	}
	
	private void LeftClick()
	{
		EditText CommentText = (EditText) findViewById(R.id.editTextComment);
		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
		CommentText.selectAll();
   		//CommentText.setTextColor(Color.parseColor("#ff0000CC"));

		String CommentDesc = SearchData.ScrollUpThroughFile("COMMENT.T", getApplicationContext());
		ShowData(CommentDesc);
	}

	private void RightClick()
	{
		EditText CommentText = (EditText) findViewById(R.id.editTextComment);
		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
		CommentText.selectAll();
		//CommentText.setTextColor(Color.parseColor("#ff0000CC"));

		String CommentDesc = SearchData.ScrollDownThroughFile("COMMENT.T", getApplicationContext());
		ShowData(CommentDesc);
	}
	private void TextChange(String SearchString)
	{
        Button enterPlus = (Button) findViewById(R.id.buttonPlus);
		String CommentString = SearchData.FindRecordLine(SearchString, SearchString.length(), "COMMENT.T", getApplicationContext());
		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
		TextView tvDesc2 = (TextView) findViewById(R.id.TextViewDesc2);
		TextView tvDesc3 = (TextView) findViewById(R.id.TextViewDesc3);
        if (CommentString.equals("NIF"))
		{
			tvDesc1.setText("NOT FOUND");			
			tvDesc2.setText("");
			tvDesc3.setText("");
	        enterPlus.setVisibility(View.INVISIBLE);
		}else
		{
			tvDesc1.setText(CommentString.substring(3,19));
			tvDesc2.setText(CommentString.substring(19,35));
			tvDesc3.setText(CommentString.substring(35,51));
	        WorkingStorage.StoreCharVal(Defines.RotateValue, CommentString.substring(0,3), getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.EntireDataString, CommentString, getApplicationContext());
	        if (tvDesc1.getText().toString().trim().equals("NO COMMENT"))
	        {
	        	enterPlus.setVisibility(View.INVISIBLE);
	        }
	        else
	        {
		        if(tvDesc2.getText().toString().trim().equals("") || tvDesc3.getText().toString().trim().equals(""))
		        {
		        	enterPlus.setVisibility(View.VISIBLE);
		        }
		        else
		        {
		        	enterPlus.setVisibility(View.INVISIBLE);
		        }	        
	        	
	        }
		}        
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentform);      
       
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterComment);
	        tvSpanish.setText("COMENTARIOS:");
	    }        
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

        Button enter = (Button) findViewById(R.id.buttonEnter);
        enter.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   EnterClick();
           }          
        });
        
        Button enterPlus = (Button) findViewById(R.id.buttonPlus);
        enterPlus.setVisibility(View.INVISIBLE);
        enterPlus.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   EnterClickPlus();
           }          
        });  

        
        ImageButton previous = (ImageButton) findViewById(R.id.buttonPrevious);
        previous.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   RightClick();
           }          
        });

        ImageButton next = (ImageButton) findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   LeftClick();
           }          
        });

        
        final EditText CommentText = (EditText) findViewById(R.id.editTextComment);
        CommentText.requestFocus();
        //CommentText.setCursorVisible(false);
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        CustomKeyboard.PickAKeyboard(CommentText, "FULL", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		CommentText.selectAll();
       		//CommentText.setTextColor(Color.parseColor("#ff0000CC"));
        }

        String CommentDesc = "";
        String CommentCode = "";
        if (savedInstanceState==null)
        {    	   
           WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
           
           CommentCode = WorkingStorage.GetCharVal(Defines.SaveCommentVal, getApplicationContext());
    	   if (CommentCode.equals(""))
    	   {
    		   WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		   CommentDesc = SearchData.ScrollUpThroughFile("COMMENT.T", getApplicationContext());
    		   ShowData(CommentDesc);    		   
    	   }
    	   else
    	   {
    		   CommentDesc = SearchData.FindRecordLine(CommentCode, 3, "COMMENT.T", getApplicationContext());
    		   ShowData(CommentDesc);   
    	   }
           WorkingStorage.StoreCharVal(Defines.RotateValue, CommentDesc.substring(0,3), getApplicationContext());
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   CommentCode = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   CommentDesc = SearchData.FindRecordLine(CommentCode, 3, "COMMENT.T", getApplicationContext());
    	   ShowData(CommentDesc);   
        }
       
       /* CommentText.setOnKeyListener(new View.OnKeyListener() {     	  
       	  public boolean onKey(View arg0, int arg1, KeyEvent event) {
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&  (arg1 == KeyEvent.KEYCODE_ENTER)   )
     		  { 
               		EnterClick();
               		return true;
     		  }
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&  (arg1 == 59)   )
     		  { 
               		RightClick();
               		return true;
     		  }   	
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&  (arg1 == 60)   )
     		  { 
               		LeftClick();
               		return true;
     		  }   
     		  return false;
       	  }     	   
        });  */
    	CommentText.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});  
    	
        CommentText.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString());
				//Messagebox.Message(s.toString(), getApplicationContext());
			}     
        });
	}
}