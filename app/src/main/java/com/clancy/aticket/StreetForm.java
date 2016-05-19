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


public class StreetForm extends ActivityGroup {
	//private static final int REQUEST_CODE = 1234;

	private void EnterClick()
	{
		String tempString = WorkingStorage.GetCharVal(Defines.EntireDataString,  getApplicationContext());
		if (tempString.length()>=34)
		{
			WorkingStorage.StoreCharVal(Defines.PrintStreetVal,tempString.substring(0, 16),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetVal,tempString.substring(16, 19),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetNVal, tempString.substring(26, 27),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetSVal, tempString.substring(27, 28),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetEVal, tempString.substring(28, 29),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetWVal, tempString.substring(29, 30),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetNWVal, tempString.substring(30, 31),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetNEVal, tempString.substring(31, 32),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetSWVal, tempString.substring(32, 33),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetSEVal, tempString.substring(33, 34),getApplicationContext());
		} else {
			EditText Street = (EditText) findViewById(R.id.editTextStreet);
			if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
			{
				Messagebox.Message("Cannot custom enter a street during the chalking program.", getApplicationContext());
				Street.selectAll();
				Street.requestFocus();
				return;
			}
			WorkingStorage.StoreCharVal(Defines.SaveStreetVal,"XXX",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintStreetVal,Street.getText().toString().toUpperCase().trim(),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetNVal, " ",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetSVal, " ",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetEVal, " ",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetWVal, " ",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetNWVal, " ",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetNEVal, " ",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetSWVal, " ",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveStreetSEVal, " ",getApplicationContext());
		}

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			WorkingStorage.StoreCharVal(Defines.SaveChalkStreetVal,tempString.substring(16, 19),getApplicationContext());
			EditText Street = (EditText) findViewById(R.id.editTextStreet);
			WorkingStorage.StoreCharVal(Defines.PrintChalkStreetVal,tempString.substring(0, 16),getApplicationContext());
			if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
			{
				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				startActivityForResult(myIntent, 0);
				finish();
				overridePendingTransition(0, 0);
			}
		} else {
			if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
			{
				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				startActivityForResult(myIntent, 0);
				finish();
				overridePendingTransition(0, 0);
			}
		}
	}

	private void ShowData(String StreetString)
	{
		WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.RotateValue, StreetString.substring(0,16), getApplicationContext());

		TextView tvStreet = (TextView) findViewById(R.id.widget1);
		if (StreetString.length()>=34)
		{
			tvStreet.setText(StreetString.subSequence(0, 16));
		} else {
			tvStreet.setText(StreetString);
		}
	}

	private void LeftClick()
	{
		String StreetDesc = SearchData.ScrollDownThroughFile("STREET.T", getApplicationContext());
		StreetDesc = FixStreetDesc(StreetDesc);
		if(StreetDesc.length()> 10)
		{
			ShowData(StreetDesc);
			EditText Street = (EditText) findViewById(R.id.editTextStreet);
			Street.setText("");
			Street.requestFocus();
		} else {
			LeftClick();
		}
	}

	private void RightClick()
	{
		String StreetDesc = SearchData.ScrollUpThroughFile("STREET.T", getApplicationContext());
		StreetDesc = FixStreetDesc(StreetDesc);
		if(StreetDesc.length()> 10)
		{
			ShowData(StreetDesc);
			EditText Street = (EditText) findViewById(R.id.editTextStreet);
			Street.setText("");
			Street.requestFocus();
		} else {
			RightClick();
		}
	}

	private void TextChange(String SearchString)
	{
		if (SearchString.trim().equals("")) //prevent spaces from not causing the search routine correctly
		{
		} else {
			String StreetString = SearchData.FindRecordLine(SearchString, SearchString.length(), "STREET.T", getApplicationContext());
			TextView tvStreet = (TextView) findViewById(R.id.widget1);
			if (StreetString.equals("NIF")) //Now We Will do additions file custom stuff
			{
				tvStreet.setText(SearchString);
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, SearchString, getApplicationContext());
			} else {
				StreetString = FixStreetDesc(StreetString);
				tvStreet.setText(StreetString.substring(0,16));
				WorkingStorage.StoreCharVal(Defines.RotateValue, SearchString, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetString, getApplicationContext());
			}
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.streetform);

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			TextView functionLabel = (TextView) findViewById(R.id.functionLabel);
			functionLabel.setText("Chalking " );
			functionLabel.setVisibility(View.VISIBLE);
		}

		if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
		{
			TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterStreet);
			tvSpanish.setText("CALLE:");
		}
		if (WorkingStorage.GetCharVal(Defines.GPSSurveyVal, getApplicationContext()).trim().equals("GPS"))
		{
			TextView tvGPS = (TextView) findViewById(R.id.widgetEnterStreet);
			tvGPS.setText("LOCATION:");
		}

		if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("GRANDRAPIDS")
				&& WorkingStorage.GetCharVal(Defines.MeterFlagVal, getApplicationContext()).trim().equals("1")
				&& !WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			CustomVibrate.VibrateButton(getApplicationContext());
			Defines.clsClassName = XMeterForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
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

		String StreetDesc = "";
		String PreviousStreet = "";
		if (savedInstanceState==null)
		{
			WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
			PreviousStreet = WorkingStorage.GetCharVal(Defines.PrintStreetVal, getApplicationContext());
			if (PreviousStreet.equals(""))
			{
				WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
				StreetDesc = SearchData.ScrollUpThroughFile("STREET.T", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.RotateValue, StreetDesc.substring(0,16), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetDesc, getApplicationContext());
			} else {
				StreetDesc = SearchData.FindRecordLine(PreviousStreet, PreviousStreet.length(), "STREET.T", getApplicationContext());
				if (StreetDesc.length() > 16)
				{
					WorkingStorage.StoreCharVal(Defines.RotateValue, StreetDesc.substring(0,16), getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetDesc, getApplicationContext());
				} else {
					WorkingStorage.StoreCharVal(Defines.RotateValue, PreviousStreet, getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.EntireDataString, PreviousStreet, getApplicationContext());
				}
			}
		} //Must do this to ensure that rotation doesn't jack everything up
		else
		{
			PreviousStreet = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
			StreetDesc = SearchData.FindRecordLine(PreviousStreet, PreviousStreet.length(), "STREET.T", getApplicationContext());
		}

		StreetDesc = FixStreetDesc(StreetDesc);

		TextView tvStreet = (TextView) findViewById(R.id.widget1);
		if (StreetDesc.length()>=34)
		{
			tvStreet.setText(StreetDesc.subSequence(0, 16));
		} else {
			tvStreet.setText(StreetDesc);
		}

		final EditText Street = (EditText) findViewById(R.id.editTextStreet);
		if (!WorkingStorage.GetCharVal(Defines.TxfrStreetVal, getApplicationContext()).equals(""))
		{
			String txfrStreet = WorkingStorage.GetCharVal(Defines.TxfrStreetVal, getApplicationContext()).trim();
			WorkingStorage.StoreCharVal(Defines.RotateValue, txfrStreet, getApplicationContext());
			Street.setText(txfrStreet);
			TextChange(txfrStreet);
			WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, "", getApplicationContext());
		}
		Street.selectAll();
		Street.requestFocus();
		KeyboardView keyboardView = null;
		keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
		CustomKeyboard.PickAKeyboard(Street, "FULL", getApplicationContext(), keyboardView);
        /*if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("SBPD"))
        {
       	    CustomKeyboard.PickAKeyboard(Street, "SBPDSTREET", getApplicationContext(), keyboardView);
        } else {
       	    CustomKeyboard.PickAKeyboard(Street, "FULL", getApplicationContext(), keyboardView);
        }*/
		if (savedInstanceState == null)
		{
			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
			//Street.setTextColor(Color.parseColor("#ff0000CC"));
		}

		final Button secondary = (Button) findViewById(R.id.ButtonSecondary);
		if (!WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("SBPD"))
		{
			secondary.setVisibility(View.INVISIBLE);
		}

		secondary.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String KeyTitle = (String) secondary.getText();
				if (KeyTitle.equals("Shortcut Keyboard"))
				{
					secondary.setText("Main Keyboard");
					KeyboardView keyboardView = null;
					keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
					CustomKeyboard.PickAKeyboard(Street, "SBPDSTREET", getApplicationContext(), keyboardView);
				} else {
					secondary.setText("Shortcut Keyboard");
					KeyboardView keyboardView = null;
					keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
					CustomKeyboard.PickAKeyboard(Street, "FULL", getApplicationContext(), keyboardView);
				}
			}
		});


		Street.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				return true;
			}
		});

        /*Street.setOnKeyListener(new View.OnKeyListener() {
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

		Street.addTextChangedListener(new TextWatcher(){
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString().trim());
				String KeyTitle = (String) secondary.getText();
				if (KeyTitle.equals("Main Keyboard"))
				{
					secondary.setText("Shortcut Keyboard");
					KeyboardView keyboardView = null;
					keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
					CustomKeyboard.PickAKeyboard(Street, "FULL", getApplicationContext(), keyboardView);
				}

				//Messagebox.Message(s.toString(), getApplicationContext());
			}
		});
	}

	private String FixStreetDesc(String StreetDesc) {
		if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("WILTONMANOR")) {
			if (StreetDesc.length() > 30) {
				String tempStr = StreetDesc;
				String tempCode = tempStr.substring(0, 3);
				String tempStreet = tempStr.substring(3, 19);
				String tempLast = tempStr.substring(19);
				StreetDesc = tempStreet + tempCode + tempLast;

				WorkingStorage.StoreCharVal(Defines.RotateValue, StreetDesc.substring(0, 16), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EntireDataString, StreetDesc, getApplicationContext());
			}
		}
		return StreetDesc;
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
            final EditText Street = (EditText) findViewById(R.id.editTextStreet);
            Street.setText(matches.get(0));
            //wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,	                    matches));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */