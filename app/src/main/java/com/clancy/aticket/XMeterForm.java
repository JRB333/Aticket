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


public class XMeterForm extends ActivityGroup {
	
	
	private void EnterClick()
	{
		TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DescString = tvDesc.getText().toString().trim();
		EditText XMeter = (EditText) findViewById(R.id.editTextXMeter);
		if (DescString.equals("NOT FOUND"))
		{

	        XMeter.selectAll();
	        XMeter.requestFocus();
		}
		else
		{
			WorkingStorage.StoreCharVal(Defines.PrintMeterVal, XMeter.getText().toString().trim(),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintStreetVal, DescString, getApplicationContext());            
			WorkingStorage.StoreCharVal(Defines.SaveStreetVal, "XXX", getApplicationContext());
   			if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
   			{
   			   	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
   			   	startActivityForResult(myIntent, 0);
   			   	finish();
   			   	overridePendingTransition(0, 0);           			
   			}
		}
	}
	
	private void LeftClick()
	{
        EditText XMeter = (EditText) findViewById(R.id.editTextXMeter);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());

        
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String XMeterDesc = SearchData.ScrollDownThroughFile("XMETER.T", getApplicationContext());
		if(XMeterDesc.length()> 6)
		{
			if (XMeterDesc.equals("NIF"))
			{
				tvDesc.setText("NOT FOUND");			
			}else
			{
				tvDesc.setText(XMeterDesc.substring(6));
		        WorkingStorage.StoreCharVal(Defines.RotateValue, XMeterDesc.substring(0,6), getApplicationContext());
		        XMeter.setText(XMeterDesc.substring(0,6));
		        XMeter.selectAll();
		        XMeter.requestFocus();			
			}
		}
		else
		{
			LeftClick();
		}

	}
	
	private void RightClick()
	{
        EditText XMeter = (EditText) findViewById(R.id.editTextXMeter);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
   		//XMeter.selectAll();
   		//XMeter.setTextColor(Color.parseColor("#ff0000CC"));

        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String XMeterDesc = SearchData.ScrollUpThroughFile("XMETER.T", getApplicationContext());
		if(XMeterDesc.length()> 6)
		{
			if (XMeterDesc.equals("NIF"))
			{
				tvDesc.setText("NOT FOUND");			
			}else
			{
				tvDesc.setText(XMeterDesc.substring(6));
		        WorkingStorage.StoreCharVal(Defines.RotateValue, XMeterDesc.substring(0,6), getApplicationContext());
		        XMeter.setText(XMeterDesc.substring(0,6));
		        XMeter.selectAll();
		        XMeter.requestFocus();			
			}
		}
		else
		{
			RightClick();
		}		

	}	
	
	private void TextChange(String SearchString)
	{
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
        String XMeterDesc = SearchData.FindRecordLine(SearchString, SearchString.length(), "XMETER.T", getApplicationContext());
		if (XMeterDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
	        tvDesc.setText(XMeterDesc.substring(6));
	        WorkingStorage.StoreCharVal(Defines.RotateValue, XMeterDesc.substring(0,6), getApplicationContext());
		}        
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xmeterform);      
       
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
       
        EditText XMeter = (EditText) findViewById(R.id.editTextXMeter);
        
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
        WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
        String XMeterCode = SearchData.ScrollUpThroughFile("XMETER.T", getApplicationContext());
        tvDesc.setText(XMeterCode.substring(6));
        XMeter.setText(XMeterCode.substring(0,6));
        XMeter.requestFocus();
        XMeter.selectAll();
        
        //XMeter.setCursorVisible(false);
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	    CustomKeyboard.PickAKeyboard(XMeter, "LICENSE", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		XMeter.selectAll();
       		//XMeter.setTextColor(Color.parseColor("#ff0000CC"));
        }
               
    	XMeter.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});

              
        XMeter.addTextChangedListener(new TextWatcher(){        
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