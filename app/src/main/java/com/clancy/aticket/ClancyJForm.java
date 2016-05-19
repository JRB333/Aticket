package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

import android.app.ActivityGroup;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ClancyJForm extends ActivityGroup {

	Handler handler = new Handler()
	{
		public void handleMessage(Message message) {
			switch (message.what) {
				case HttpConnection.DID_START:
					//text.setText("Starting connection...");
					// Toast.makeText(getApplicationContext(), "Starting connection...", 2000).show();
					break;

				case HttpConnection.DID_SUCCEED:
					String response = (String) message.obj;
					//text.setText(response);
					Toast.makeText(getApplicationContext(), response, 2000).show();
					break;

				case HttpConnection.DID_ERROR:
					Exception e = (Exception) message.obj;
					e.printStackTrace();
					//  text.setText("Connection failed.");
					Toast.makeText(getApplicationContext(), "Connection failed.", 2000).show();
					break;
			}
		}
	};

	private void EnterClick() {
		EditText Number = (EditText) findViewById(R.id.editTextNumber);
		String newString = Number.getText().toString();
		TextView tvMessage = (TextView) findViewById(R.id.widgetEnterPassword);

		if (newString.equals("TEST") && WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).equals("CLANCYDEMO")) //special flag for setting up a test unit without the clancy backoffice  - Only do this if the clientid = CLANCYDEMO
		{
			SystemIOFile.Delete("CLANCY.J");
			try { //do not remove this routine
				Random rand = new Random();
				int randomNum = rand.nextInt((9000 - 1) + 1) + 1;
				String RandomNumber = Integer.toString(randomNum);
				while (RandomNumber.length() < 5)
					RandomNumber = "0" + RandomNumber;
				OutputStreamWriter out = new OutputStreamWriter(openFileOutput("CLANCY.J",0));
				out.write("TEST                                                                                                     55500001555"+RandomNumber+"55599900                                                                                                                    D");
				out.close();
			}
			catch(Throwable t) {
				Toast
						.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
						.show();
			}
			finish();
		}
		else if (newString.length() == 4)
		{
			HTTPFileTransfer writetest;
			writetest = new HTTPFileTransfer();
			writetest.GetNewClancyJ(newString, getApplicationContext());
			writetest = null;

			try {
				FileInputStream in = openFileInput("CLANCY.J");
				if (in != null)
				{
					InputStreamReader tmp = new InputStreamReader(in);
					BufferedReader reader = new BufferedReader(tmp);
					String str;
					StringBuffer buf= new StringBuffer();
					while ((str = reader.readLine())!= null)
					{
						buf.append(str+"\n");
					}
					in.close();
					// made it this far, we have a new book
					if (SearchData.GetFileSize("CLANCY.J") == 248)
					{
						//Toast.makeText(getApplicationContext(), buf.toString(), 2000).show();
						//HTTPFileTransfer.DeleteClancyJ(newString, getApplicationContext());
						String URLString = "HTTP://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress,getApplicationContext()) + "/DemoTickets/" + newString + "/CLANCY.J";
						new HttpConnection(handler).delete(URLString);
						CustomVibrate.VibrateButton(getApplicationContext());
						finish();
						overridePendingTransition(0, 0);
					}
					else
					{
						tvMessage.setText("!INVALID LENGTH FOR CLANCY.J, ERROR! TRY AGAIN!");
						WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
						Number.selectAll();
						SystemIOFile.Delete("CLANCY.J");
					}
				}
			}

			catch(Throwable t) {
				tvMessage.setText("!UNKNOWN ERROR ATTEMPTING NEW CITATION RANGE, TRY AGAIN!");
				WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
				Number.selectAll();
				SystemIOFile.Delete("CLANCY.J");
			}
		}
		else
		{
			tvMessage.setText("UNIT NUMBER LENGTH MUST BE FOUR CHARACTERS, TRY AGAIN.");
			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
			Number.selectAll();
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clancydotjform);

		Button esc = (Button) findViewById(R.id.buttonESC);
		esc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CustomVibrate.VibrateButton(getApplicationContext());
				finish();
				overridePendingTransition(0, 0);
			}

		});

		Button enter = (Button) findViewById(R.id.buttonEnter);
		enter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				EnterClick();
			}

		});

		EditText Number = (EditText) findViewById(R.id.editTextNumber);
		Number.requestFocus();
		KeyboardView keyboardView = null;
		keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
		CustomKeyboard.PickAKeyboard(Number, "FULL", getApplicationContext(), keyboardView);
		if (savedInstanceState == null)
		{
			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
			Number.selectAll();
		}

		Number.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				return true;
			}
		});
	}
}