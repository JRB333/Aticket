package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class NumberForm extends ActivityGroup {

	private void EnterClick()
	{
		EditText Number = (EditText) findViewById(R.id.editTextNumber);
		String newString = Number.getText().toString();

		WorkingStorage.StoreCharVal(Defines.PrintNumberVal, newString,getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.SaveNumberVal, newString, getApplicationContext());

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			if (newString.equals(""))
			{
				WorkingStorage.StoreCharVal(Defines.SaveChalkNumberVal, "NA", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "NA", getApplicationContext());
			}
			else
			{
				WorkingStorage.StoreCharVal(Defines.SaveChalkNumberVal, newString, getApplicationContext());
			}
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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numberform);

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			TextView functionLabel = (TextView) findViewById(R.id.functionLabel);
			functionLabel.setText("Chalking " );
			functionLabel.setVisibility(View.VISIBLE);
		}

		if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
		{
			TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterNumber);
			tvSpanish.setText("ENTRE N�MERO:");
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

		EditText Number = (EditText) findViewById(R.id.editTextNumber);
		TextView tvMessage = (TextView) findViewById(R.id.widgetEnterNumber);
		if (savedInstanceState==null)
		{
			WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
			String tempString = WorkingStorage.GetCharVal(Defines.PrintNumberVal,getApplicationContext()).trim();
			if(!tempString.equals(""))
			{
				Number.setText(tempString);
			}
			tempString = WorkingStorage.GetCharVal(Defines.StreetNumberMessage,getApplicationContext()).trim();
			if(!tempString.equals(""))
			{
				tvMessage.setText(tempString);
			}
		}

		if (!WorkingStorage.GetCharVal(Defines.TxfrNumVal, getApplicationContext()).equals("")) {
			Number.setText(WorkingStorage.GetCharVal(Defines.TxfrNumVal, getApplicationContext()).trim());
			WorkingStorage.StoreCharVal(Defines.TxfrNumVal, "", getApplicationContext());
		}

		Number.requestFocus();
		//Number.setCursorVisible(false);

		KeyboardView keyboardView = null;
		keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
		CustomKeyboard.PickAKeyboard(Number, "FULL", getApplicationContext(), keyboardView);
		if (savedInstanceState == null)
		{
			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
			Number.selectAll();
			//state.setTextColor(Color.parseColor("#ff0000CC"));
		}
		Number.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				return true;
			}
		});
	   /*Number.setOnKeyListener(new View.OnKeyListener() {     	  
       	  public boolean onKey(View arg0, int arg1, KeyEvent event) {
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&
     				  (arg1 == KeyEvent.KEYCODE_ENTER)   )
     			  { 
     			  	EnterClick();
     			  	return true;     			  	
     			  }
   		  		return false;
       	  }     	   
	   });*/

	}
}