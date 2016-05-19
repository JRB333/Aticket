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


public class HBoxSelectLotForm extends ActivityGroup {
	private void EnterClick()
	{
		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
		String tempString = tvDesc1.getText().toString().trim();
		if (tempString.equals("NOT FOUND"))
		{
			EditText SelectLotText = (EditText) findViewById(R.id.editTextSelectLot);
			String tempCode = SelectLotText.getText().toString().trim();
			SelectLotText.selectAll();
			SelectLotText.requestFocus();
		}
		else
		{
			String HBoxMainDesc = WorkingStorage.GetCharVal(Defines.EntireDataString, getApplicationContext());
			
	        while (HBoxMainDesc.length() < 97)
	        	HBoxMainDesc += " ";
	        
            WorkingStorage.StoreCharVal(Defines.SaveHBoxCodeVal, HBoxMainDesc.substring(0, 5), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBoxLotVal, HBoxMainDesc.substring(5, 25), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox20MinVal, HBoxMainDesc.substring(25, 26), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox30MinVal, HBoxMainDesc.substring(26, 27), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox40MinVal, HBoxMainDesc.substring(27, 28), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox60MinVal, HBoxMainDesc.substring(28, 29), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox80MinVal, HBoxMainDesc.substring(29, 30), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox90MinVal, HBoxMainDesc.substring(30, 31), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox100MinVal, HBoxMainDesc.substring(31, 32), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox2HoursVal, HBoxMainDesc.substring(32, 33), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox3HoursVal, HBoxMainDesc.substring(33, 34), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox4HoursVal, HBoxMainDesc.substring(34, 35), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox5HoursVal, HBoxMainDesc.substring(35, 36), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox6HoursVal, HBoxMainDesc.substring(36, 37), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox7HoursVal, HBoxMainDesc.substring(37, 38), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox8HoursVal, HBoxMainDesc.substring(38, 39), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBox9HoursVal, HBoxMainDesc.substring(39, 40), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBoxDailyVal, HBoxMainDesc.substring(40, 41), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBoxSpacesVal, HBoxMainDesc.substring(41, 45), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBoxDayAmtVal, "0.00", getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBoxHourAmtVal, "0.00", getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBoxHalfAmtVal, "0.00", getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBoxResrvAmtVal, "0.00", getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBoxEBirdAmtVal, "0.00", getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.SaveHBoxEBirdTimeVal, HBoxMainDesc.substring(90, 95), getApplicationContext());
            
            
			Defines.clsClassName = HBoxDownloadForm.class ;
       		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
       		startActivityForResult(myIntent, 0);  
       		finish();
       		overridePendingTransition(0, 0);			
		}
	}
	
	private void ShowData(String SelectLotString)
	{
        while (SelectLotString.length() < 97)
        	SelectLotString += " ";
		WorkingStorage.StoreCharVal(Defines.EntireDataString, SelectLotString, getApplicationContext());
		
		EditText SelectLotText = (EditText) findViewById(R.id.editTextSelectLot);
		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
		tvDesc1.setText(SelectLotString.substring(5,25));
		WorkingStorage.StoreCharVal(Defines.RotateValue, SelectLotString.substring(0,5), getApplicationContext());
		SelectLotText.setText(SelectLotString.substring(0,5));
		SelectLotText.selectAll();
		SelectLotText.requestFocus();
	}
	
	private void LeftClick()
	{
		EditText SelectLotText = (EditText) findViewById(R.id.editTextSelectLot);
		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
		SelectLotText.selectAll();
		String SelectLotDesc = SearchData.ScrollUpThroughFile("HONORLOT.A", getApplicationContext());
		ShowData(SelectLotDesc);
	}

	private void RightClick()
	{
		EditText SelectLotText = (EditText) findViewById(R.id.editTextSelectLot);
		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
		SelectLotText.selectAll();
		String SelectLotDesc = SearchData.ScrollDownThroughFile("HONORLOT.A", getApplicationContext());
		ShowData(SelectLotDesc);
	}
	private void TextChange(String SearchString)
	{
        String SelectLotString = SearchData.FindRecordLine(SearchString, SearchString.length(), "HONORLOT.A", getApplicationContext());
		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
        if (SelectLotString.equals("NIF"))
		{
			tvDesc1.setText("NOT FOUND");			
		}else
		{
			tvDesc1.setText(SelectLotString.substring(5,25));
	        WorkingStorage.StoreCharVal(Defines.RotateValue, SelectLotString.substring(0,3), getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.EntireDataString, SelectLotString, getApplicationContext());
		}        
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hboxselectlotform);      
       
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

        
        final EditText SelectLotText = (EditText) findViewById(R.id.editTextSelectLot);
        SelectLotText.requestFocus();
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        CustomKeyboard.PickAKeyboard(SelectLotText, "LICENSE", getApplicationContext(), keyboardView);
        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		SelectLotText.selectAll();
        }

        
        
        String SelectLotDesc = "";
        String SelectLotCode = "";
        GetDate.GetDateTime(getApplicationContext());
        WorkingStorage.StoreLongVal(Defines.CurrentRatesOrderVal, 17, getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox20MinAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox40MinAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox80MinAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox90MinAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox100MinAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox2HoursAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox3HoursAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox4HoursAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox5HoursAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox6HoursAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox7HoursAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox8HoursAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBox9HoursAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBoxDayAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBoxHalfAmtVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBoxHourAmtVal, "", getApplicationContext());

        WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut1Val, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut2Val, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut3Val, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut4Val, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "DONE", getApplicationContext());
        WorkingStorage.StoreLongVal(Defines.EditHonorBoxVal, 0, getApplicationContext());
        if (savedInstanceState==null)
        {    	   
           WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
           
           SelectLotCode = WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal, getApplicationContext());
    	   if (SelectLotCode.equals(""))
    	   {
    		   WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		   SelectLotDesc = SearchData.ScrollUpThroughFile("HONORLOT.A", getApplicationContext());
    		   ShowData(SelectLotDesc);    		   
    	   }
    	   else
    	   {
    		   SelectLotDesc = SearchData.FindRecordLine(SelectLotCode, 5, "HONORLOT.A", getApplicationContext());
    		   if (SelectLotDesc.equals("NIF"))
    		   {
        		   WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
        		   SelectLotDesc = SearchData.ScrollUpThroughFile("HONORLOT.A", getApplicationContext());    			   
    		   }
    		   ShowData(SelectLotDesc);   
    	   }
           WorkingStorage.StoreCharVal(Defines.RotateValue, SelectLotDesc.substring(0,5), getApplicationContext());
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   SelectLotCode = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   SelectLotDesc = SearchData.FindRecordLine(SelectLotCode, 5, "HONOROT.A", getApplicationContext());
    	   ShowData(SelectLotDesc);   
        }
  
    	SelectLotText.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});  
    	
        SelectLotText.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString());
				//Messagebox.Message(s.toString(), getApplicationContext());
			}     
        });
	}
}