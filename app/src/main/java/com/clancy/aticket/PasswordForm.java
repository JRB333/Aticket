package com.clancy.aticket;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class PasswordForm extends ActivityGroup {

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
					Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
					break;
				case HttpConnection.DID_ERROR:
					Exception e = (Exception) message.obj;
					e.printStackTrace();
					//  text.setText("Connection failed.");
					Toast.makeText(getApplicationContext(), "Connection failed.", Toast.LENGTH_LONG).show();
					break;
			}
		}
	};

	private void EnterClick()
	{
		CustomVibrate.VibrateButton(getApplicationContext());
		String WholeLine = "";
		final EditText password = (EditText) findViewById(R.id.editTextPassword);
		String newString = password.getText().toString().toUpperCase();
		if (newString.equals(""))
		{
			TextView message = (TextView) findViewById(R.id.TextMessage);
			message.setText("Password Required");
			password.setText("");
			Vibrator vibKey = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
			vibKey.vibrate(500);
		} else if (newString.toUpperCase().equals("CUSTOM")) {
			try {
				FileInputStream in = openFileInput("CUSTOM.A");
				if (in!=null)
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
					Toast.makeText(getApplicationContext(), buf.toString(), Toast.LENGTH_LONG ).show();
				}

			}
			catch(Throwable t) {
				Toast
						.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
						.show();
			}
		} else if (newString.toUpperCase().equals("JPG")) {
			File dir = new File("/data/data/com.clancy.aticket/files/");
			String[] dirList = dir.list();
			int blah = dirList.length;
			int i;
			String tempString = "";
			String str= "";
			for(i=0;  i < blah; i++)
			{
				if (dirList[i].toUpperCase().contains("JPG"))
				{
					byte[] byteBuffer;
					tempString = dirList[i];
					str = str + tempString+"\n";
				}
			}
			Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG ).show();
		} else if (newString.toUpperCase().equals("IP")) {
			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}
			Defines.clsClassName = IPAddressForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
		} else if (newString.toUpperCase().equals("PAIR")) {
			password.setText("");
			Defines.clsClassName = UnitNumberForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish(); //Don't call finish for UnitNumber Form
		} else if (newString.toUpperCase().equals("DELCLANCYJ")) {
			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}
			String URLString = "HTTP://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress,getApplicationContext())+"/DemoTickets/SURF/CLANCY.J";
			new HttpConnection(handler).delete(URLString);
			CustomVibrate.VibrateButton(getApplicationContext());

		} else if (newString.toUpperCase().equals("UPLOAD")) {
			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}
			password.setText("");
			//Now try to upload any files if there is a ticket.r
			//if(SystemIOFile.exists("TICKET.R"))
			//{
			Defines.clsClassName = UploadBackgroundForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			overridePendingTransition(0, 0);
			//}
			WorkingStorage.StoreCharVal(Defines.ForceUploadVal, "MAIN", getApplicationContext());
			finish(); //Don't call finish for UnitNumber Form
		} else {
			if (newString.toUpperCase().equals("NEWBOOK")) {
				if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE")) {
					Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
					return;
				}
				password.setText("");
				//Now try to upload any files if there is a ticket.r
				SystemIOFile.Delete("CUSTOM.A");
				Defines.clsClassName = ClancyJForm.class;
				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				startActivityForResult(myIntent, 0);
				finish(); //Don't call finish for UnitNumber Form
				overridePendingTransition(0, 0);
			} else {
				if (newString.equals("ACXZ")) {
					WholeLine = "ACXZ            CLANCY          12345Y";
				} else {
					WholeLine = SearchData.FindRecordLine(newString, 16, "OFFICER.T", getApplicationContext());
				}

				if (WholeLine.equals("NIF")) {
					TextView message = (TextView) findViewById(R.id.TextMessage);
					message.setText("!!Invalid Password!!");
					password.setText("");
					Vibrator vibKey = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
					vibKey.vibrate(500);
				} else {
					//String TempStr = WholeLine.substring(16,30);
					//TempStr = WholeLine.substring(31,37);
					WorkingStorage.StoreCharVal(Defines.PrintOfficerVal, WholeLine.substring(16, 30), getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.PrintBadgeVal, WholeLine.substring(31, 37), getApplicationContext());

					if (WorkingStorage.GetCharVal(Defines.CaptureSignature, getApplicationContext()).toString().equals("YES")) {
						// Signature Capture is YES
						// Check to see if Officer's Signature File Already Exists
						String FileDir = getApplicationContext().getFilesDir().getPath();  // "/data/data/com.clancy.aticket/files/"
						String BadgeNo = WorkingStorage.GetCharVal(Defines.PrintBadgeVal, getApplicationContext()).toString().trim();
						String SigFileName = "Sig" + BadgeNo.trim() + ".png";   // Fixed Signature Image file name
						File SigFile = new File(FileDir, SigFileName);

						if (!SigFile.exists()) {
							// No Signature File Exists for This Badge Number

							// Delete any other BadgeNo-specific Signature Files
							/*File dir = new File("/data/data/com.clancy.aticket/files/");
							String[] dirList = dir.list();
							int DirFileCnt = dirList.length;
							int i;
							for (i = 0; i < DirFileCnt; i++) {
								String ThisFileName = dirList[i].toUpperCase();
								if (ThisFileName.contains("SIG") && ThisFileName.contains("PNG")) {
									SigFileName = dirList[i];
									if (SystemIOFile.exists(SigFileName)) {
										SystemIOFile.Delete(SigFileName);
									}
								}
							}*/

							// Now Set up to FORCE Signature Entry
							WorkingStorage.StoreCharVal(Defines.SignatureFile, "", getApplicationContext());
						} else {
							// File Already Exists, No Forced Entry Required
							WorkingStorage.StoreCharVal(Defines.SignatureFile, SigFileName, getApplicationContext());
						}
					}

					Defines.clsClassName = SelFuncForm.class;
					Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
				}
			}
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passwordform);

		Button btnESC = (Button) findViewById(R.id.buttonESC);
		btnESC.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CustomVibrate.VibrateButton(getApplicationContext());
				finish();
				overridePendingTransition(0, 0);
			}

		});

		TextView message = (TextView) findViewById(R.id.TextMessage);
		message.setText("");

		Button enter = (Button) findViewById(R.id.buttonEnter);
		enter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view)
			{
				EnterClick();
			}
		});

		final EditText password = (EditText) findViewById(R.id.editTextPassword);
		//password.requestFocus();
		//password.setCursorVisible(false);

		KeyboardView keyboardView = null;
		keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
		CustomKeyboard.PickAKeyboard(password, "FULL", getApplicationContext(), keyboardView);

		password.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				return true;
			}
		});

		if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
		{
			TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterPassword);
			tvSpanish.setText("C�DIGO DE ACCESO:");
		}
        
        /*password.setOnKeyListener(new View.OnKeyListener() {     	  
       	  public boolean onKey(View arg0, int arg1, KeyEvent event) {
     		  if ( (event.getAction() == KeyEvent.ACTION_UP ) && (arg1 == KeyEvent.KEYCODE_ENTER))
     		  { 
     			  EnterClick();
     			  return true;
     		  }
   		  	  return false;
       	  }     	   
        });*/

		CheckAppVersion();
	}

	public void CheckAppVersion() {
		// Check Current HHU App version against Current Available App version
		String VersionNumStr = String.valueOf(BuildConfig.VERSION_CODE).trim();

		if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("ONLINE")) {
			// Only execute if working ONLINE
			Integer AppVersionNo = Integer.valueOf(VersionNumStr);

			String CurrentVersion = HTTPFileTransfer.HTTPGetPageContent("HTTP://www.clancyapp.com/AndroidBuild.htm",getApplicationContext()).trim();
			if (!CurrentVersion.equals("FAILED")) {
				Integer CurrVerNo = Integer.valueOf(CurrentVersion);

				if (CurrVerNo > AppVersionNo) {
					ConfirmUpdate(CurrentVersion);
				}
			}
		}
	}

	protected void ConfirmUpdate(String CurrentVersion) {
		// setup a dialog window
		String AlertTitle = "";
		String AlertMessage = "";
		String AlertYES = "Yes";

		if (!WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH")) {
			AlertTitle = "New Version: " + CurrentVersion + " Available";
			AlertMessage = "Get Latest Version ?";
		}	else {
			AlertTitle = "Nueva Versión: " + CurrentVersion + " Disponibles";
			AlertMessage = "Obtener la última versión ?";
			AlertYES = "Si";
		}

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PasswordForm.this);

		// setup a dialog window
		alertDialogBuilder.setCancelable(false)
				.setTitle(AlertTitle)
				.setMessage(AlertMessage)
				.setCancelable(false)
				.setPositiveButton(AlertYES, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						GetLatestVersion();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		// create an alert dialog
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	private void GetLatestVersion() {
		// Update Application Version
		Defines.clsClassName = RefreshForm.class;
		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		startActivityForResult(myIntent, 0);

		// Now Go Back To Start
		/*Defines.clsClassName = AticketActivity.class ;
		myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		startActivityForResult(myIntent, 0);
		finish();
		overridePendingTransition(0, 0);*/
	}
}