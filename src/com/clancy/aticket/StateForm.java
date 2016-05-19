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


public class StateForm extends ActivityGroup {
	
	
	private void EnterClick()
	{
		TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DescString = tvDesc.getText().toString().trim();
		if (DescString.equals("NOT FOUND"))
		{
			EditText state = (EditText) findViewById(R.id.editTextstate);
	        state.selectAll();
	        state.requestFocus();
		}
		else
		{
            WorkingStorage.StoreCharVal(Defines.SaveStateVal, WorkingStorage.GetCharVal(Defines.RotateValue,getApplicationContext()),getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.PrintStateVal, DescString, getApplicationContext());            
           // Messagebox.Message(WorkingStorage.GetCharVal(Defines.SaveStateVal,getApplicationContext()), getApplicationContext());
           // Messagebox.Message(WorkingStorage.GetCharVal(Defines.PrintStateVal,getApplicationContext()), getApplicationContext());
            
            if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.BootLookupMenu))
            {
           		Defines.clsClassName = BootForm.class ;
				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				startActivityForResult(myIntent, 0);
				finish();
				overridePendingTransition(0, 0);   
            }
            else if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
			  {
				   WorkingStorage.StoreCharVal(Defines.SaveChalkStateVal, WorkingStorage.GetCharVal(Defines.RotateValue,getApplicationContext()),getApplicationContext());
		     	   if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
		    	   {
					   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
					   startActivityForResult(myIntent, 0);
					   finish();
					   overridePendingTransition(0, 0);    
		    	   }		

			  }else
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
	
	private void LeftClick()
	{
        EditText state = (EditText) findViewById(R.id.editTextstate);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
   		//state.selectAll();
   		//state.setTextColor(Color.parseColor("#ff0000CC"));
        
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String StateDesc = SearchData.ScrollDownThroughFile("STATE.T", getApplicationContext());
		if (StateDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
			if (StateDesc.length()>=8)
			{
				tvDesc.setText(StateDesc.substring(2));
				WorkingStorage.StoreCharVal(Defines.RotateValue, StateDesc.substring(0,2), getApplicationContext());
				state.setText(StateDesc.substring(0,2));
				state.selectAll();
				state.requestFocus();
			}
			else
			{
				LeftClick();
			}
		}
		
	}
	
	private void RightClick()
	{
        EditText state = (EditText) findViewById(R.id.editTextstate);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
   		//state.selectAll();
   		//state.setTextColor(Color.parseColor("#ff0000CC"));

        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String StateDesc = SearchData.ScrollUpThroughFile("STATE.T", getApplicationContext());
		if (StateDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
			if (StateDesc.length()>=8)
			{
				tvDesc.setText(StateDesc.substring(2));
				WorkingStorage.StoreCharVal(Defines.RotateValue, StateDesc.substring(0,2), getApplicationContext());
				state.setText(StateDesc.substring(0,2));
				state.selectAll();
				state.requestFocus();
			}
			else
			{
				RightClick();
			}
		}
	}	
	
	private void TextChange(String SearchString)
	{
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
        String StateDesc = SearchData.FindRecordLine(SearchString, SearchString.length(), "STATE.T", getApplicationContext());
		if (StateDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
	        tvDesc.setText(StateDesc.substring(2));
	        WorkingStorage.StoreCharVal(Defines.RotateValue, StateDesc.substring(0,2), getApplicationContext());
		}        
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stateform);      
       
        
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterstate);
	        tvSpanish.setText("EL ESTADO:");
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
       
        EditText state = (EditText) findViewById(R.id.editTextstate);
        
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
        String StateCode ="";
        if (savedInstanceState==null)
        {
            
        	StateCode = WorkingStorage.GetCharVal(Defines.DefaultState, getApplicationContext()).trim();
        	if (!WorkingStorage.GetCharVal(Defines.AutoStateVal,getApplicationContext()).equals(""))
        	{
        		StateCode = WorkingStorage.GetCharVal(Defines.AutoStateVal, getApplicationContext()).trim();
        	}
            WorkingStorage.StoreCharVal(Defines.RotateValue, StateCode, getApplicationContext());
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
        	StateCode = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
        }
        String StateDesc = SearchData.FindRecordLine(StateCode, 2, "STATE.T", getApplicationContext());
        tvDesc.setText(StateDesc.substring(2));
        state.setText(StateCode);
        state.requestFocus();
        state.selectAll();
        
        //state.setCursorVisible(false);
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	    CustomKeyboard.PickAKeyboard(state, "LICENSE", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		state.selectAll();
       		//state.setTextColor(Color.parseColor("#ff0000CC"));
        }
               
    	state.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});

       
       /* state.setOnKeyListener(new View.OnKeyListener() {
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
        
        state.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString());
				//Messagebox.Message(s.toString(), getApplicationContext());
			}     
        });
        
        ImageButton left = (ImageButton) findViewById(R.id.button1);
        left.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   LeftClick();
           }          
        });
        
        ImageButton right = (ImageButton) findViewById(R.id.button2);
        right.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   RightClick();
           }          
        });
	}
	
}