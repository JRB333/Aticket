package com.clancy.aticket;

import java.util.HashMap;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.widget.EditText;

public class CustomKeyboard extends ActivityGroup  implements OnKeyboardActionListener, OnKeyListener {

	static EditText GlobalTextHandle;
	static Context GlobalContext;

	public static void PickAKeyboard(EditText txtHandle, String KeyboardName, Context dan, KeyboardView keyboardView){

		GlobalTextHandle = txtHandle;
		GlobalContext  = dan;

		if (KeyboardName.equals("FULL"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.full);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("SURVEYKB"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.surveykb);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("ALPHAONLY"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.alphaonly);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("NSEW"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.nsew);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("SBPDSTREET"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.sbpdstreet);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("SECONDARY"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.secondary);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("NUMBERS"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.numbers);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("NUMONLY"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.numonly);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("LICENSE"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.license);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("LICENSE_SMALL"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.license_small);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
		if (KeyboardName.equals("IPKEY"))
		{
			CustomKeyboard tmpKeyboard = new CustomKeyboard();
			Keyboard keyboard = new Keyboard(dan, R.xml.ipkey);
			keyboardView.setKeyboard(keyboard);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(true);
			keyboardView.setOnKeyboardActionListener(tmpKeyboard);
		}
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onKey(int primaryCode, int[] keyCodes) {

		HashMap<String, String> keyCodeMap = new HashMap<String, String>();
		keyCodeMap.put("97", "A");
		keyCodeMap.put("98", "B");
		keyCodeMap.put("99", "C");
		keyCodeMap.put("100", "D");
		keyCodeMap.put("101", "E");
		keyCodeMap.put("102", "F");
		keyCodeMap.put("103", "G");
		keyCodeMap.put("104", "H");
		keyCodeMap.put("105", "I");
		keyCodeMap.put("106", "J");
		keyCodeMap.put("107", "K");
		keyCodeMap.put("108", "L");
		keyCodeMap.put("109", "M");
		keyCodeMap.put("110", "N");
		keyCodeMap.put("111", "O");
		keyCodeMap.put("112", "P");
		keyCodeMap.put("113", "Q");
		keyCodeMap.put("114", "R");
		keyCodeMap.put("115", "S");
		keyCodeMap.put("116", "T");
		keyCodeMap.put("117", "U");
		keyCodeMap.put("118", "V");
		keyCodeMap.put("119", "W");
		keyCodeMap.put("120", "X");
		keyCodeMap.put("121", "Y");
		keyCodeMap.put("122", "Z");
		keyCodeMap.put("32", " ");
		keyCodeMap.put("45", "-");
		keyCodeMap.put("47", "/");
		keyCodeMap.put("48", "0");
		keyCodeMap.put("49", "1");
		keyCodeMap.put("50", "2");
		keyCodeMap.put("51", "3");
		keyCodeMap.put("52", "4");
		keyCodeMap.put("53", "5");
		keyCodeMap.put("54", "6");
		keyCodeMap.put("55", "7");
		keyCodeMap.put("56", "8");
		keyCodeMap.put("57", "9");
		keyCodeMap.put("46", ".");
		keyCodeMap.put("150", "173.164.42.142/");
		keyCodeMap.put("151", "107.1.38.34/");
		keyCodeMap.put("152", ".");
		keyCodeMap.put("153", "/");
		keyCodeMap.put("154", "CALLE ");
		keyCodeMap.put("155", "CPL ");
		keyCodeMap.put("156", "DE LA ");
		keyCodeMap.put("157", "DEL ");
		keyCodeMap.put("158", "EL ");
		keyCodeMap.put("159", "LA ");
		keyCodeMap.put("160", "LAS ");
		keyCodeMap.put("161", "LOS ");
		keyCodeMap.put("162", "SAN ");
		keyCodeMap.put("163", "SANTA ");
		keyCodeMap.put("164", "MARINA ");
		//keyCodeMap.put("165", "107.1.38.45/"); //take out MWTF3 FOR NOW
		keyCodeMap.put("58", ":");
		keyCodeMap.put("59", ";");
		keyCodeMap.put("64", "@");
		keyCodeMap.put("38", "&");
		keyCodeMap.put("35", "#");
		keyCodeMap.put("36", "$");

		if (WorkingStorage.GetCharVal(Defines.ClearExitBoxVal, GlobalContext).equals("CLEAR"))
		{
			GlobalTextHandle.setText("");
			//GlobalTextHandle.setTextColor(Color.parseColor("#ff000000"));
			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "DONE", GlobalContext);
		}

		String c = keyCodeMap.get(String.valueOf(primaryCode));
		if(!(c == null)){
			GlobalTextHandle.append(c);
		}
		else{
			switch(primaryCode){
				case -5:
					if(GlobalTextHandle.getText().toString().length() > 0)
						GlobalTextHandle.setText(GlobalTextHandle.getText().toString().substring(0, GlobalTextHandle.getText().toString().length() - 1));
					GlobalTextHandle.setSelection(GlobalTextHandle.getText().toString().length());
	                
	               /*case 152:
	                   Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	                   intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
	                           RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	                   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Park By Phone Tag Number...");
	                   startActivityForResult(intent, 5647);*/
			}
		}

		Vibrator vibKey = (Vibrator) GlobalContext.getSystemService(GlobalContext.VIBRATOR_SERVICE);
		vibKey.vibrate(10);
		// TODO Auto-generated method stub
	}

	@Override
	public void onPress(int primaryCode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRelease(int primaryCode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onText(CharSequence text) {
		// TODO Auto-generated method stub
	}

	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub
	}

	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub
	}

	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub
	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub
	}
}
