package com.clancy.aticket;


import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
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


public class PlateForm extends ActivityGroup {

	String PlateStatus = "";
	String newString = "";
	String UseParkMobile = "NO";

	Handler handler = new Handler()
	{
		public void handleMessage(Message message) {
			switch (message.what) {
				case HttpConnection.DID_START:
					break;

				case HttpConnection.DID_SUCCEED:
					String response = (String) message.obj;
					SaveTicket.SaveVMultiPlate(response, getApplicationContext());
			  /*StringTokenizer tokens = new StringTokenizer(response, "|");
			  String ParseString1 = "";
			  String ParseString2 = "";
			  if (tokens.hasMoreTokens() == true)
			  {
				ParseString1 = tokens.nextToken();
			  }
			  if (tokens.hasMoreTokens() == true)
			  {
				ParseString2 = tokens.nextToken();
			  }	*/
					break;

				case HttpConnection.DID_ERROR:
					Exception e = (Exception) message.obj;
					e.printStackTrace();
					//  text.setText("Connection failed.");
					// Toast.makeText(getApplicationContext(), "Connection failed.", 2000).show();
					break;
			}
		}
	};

	private void EnterClick() {
		PlateStatus = "";
		EditText plate = (EditText) findViewById(R.id.editTextPlate);
		newString = plate.getText().toString();
		if (!newString.equals("")) {
			// If in TICKET MENU
			//  ####### New Code - JRB #######
			if (WorkingStorage.GetCharVal(Defines.ParkMobile, getApplicationContext()).toString().equals("YES")
					&& WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("ONLINE")) {
				// Must be PM-YES & ONLINE to Allow
				UseParkMobile = "YES";
			} else {
				// If Not, go with default "NO"
				UseParkMobile = "NO";
			}

			if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.TicketIssuanceMenu)) {
				//  ####### New Code - JRB #######
				//   Check If Plate PAID status should be checked at ParkMobile
				if (UseParkMobile.equals("YES")) {
					PlateStatus = checkParkMobile(newString);
				}
			}

			if (PlateStatus.equals("PAID")) {
				// IF PAID, pop Alert "Continue" or "Abort"
				PopIt("ParkMobile", "PAID at ParkMobile");
			} else if (PlateStatus.equals("SERVER INTERNAL ERROR")) {
				// IF SERVER NOT REACHABLE, pop Alert "Continue" or "Abort"
				PopIt("ParkMobile", "ParkMobile Server Problem");
			} else {
				// NO TICKET Menu - OR - NO ParkMobile - OR - YES ParkMobile and NOT PAID or NOT FOUND
				processThisPlate(newString);
			}
		} else {
			// Plate Text BLANK
			if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.BootLookupMenu))
			{
				Messagebox.Message("PlateRequired", getApplicationContext());
				plate.requestFocus();
			}else if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
			{
				Messagebox.Message("PlateRequired",getApplicationContext());
				plate.requestFocus();
			}else
			{
				Defines.clsClassName = VinForm.class ;
				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				startActivityForResult(myIntent, 0);
				finish();
				overridePendingTransition(0, 0);
			}
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plateform);

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			TextView functionLabel = (TextView) findViewById(R.id.functionLabel);
			functionLabel.setText("Chalking " );
			functionLabel.setVisibility(View.VISIBLE);
		}

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).contains("BOOT"))
		{
			TextView functionLabel = (TextView) findViewById(R.id.functionLabel);
			functionLabel.setText("Hot List " );
			functionLabel.setVisibility(View.VISIBLE);
		}

		if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
		{
			TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterPlate);
			tvSpanish.setText("N�MERO DE TABLILLA:");
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

		EditText plate = (EditText) findViewById(R.id.editTextPlate);
		if (!WorkingStorage.GetCharVal(Defines.TxfrPlateVal, getApplicationContext()).equals("")) {
			// ###### JRB #####
			//WorkingStorage.StoreCharVal(Defines.MenuProgramVal, WorkingStorage.GetCharVal(Defines.TicketIssuanceMenu, getApplicationContext()), getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveChalkPlateVal, "", getApplicationContext());
			plate.setText(WorkingStorage.GetCharVal(Defines.TxfrPlateVal, getApplicationContext()).trim());
			WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "", getApplicationContext());

			if (WorkingStorage.GetCharVal(Defines.PlateRepeat,getApplicationContext()).trim().equals("YES")) {
				plate.setSelectAllOnFocus(true);
			}
			plate.selectAll();
		}
		plate.requestFocus();

		KeyboardView keyboardView = null;
		keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
		CustomKeyboard.PickAKeyboard(plate, "LICENSE", getApplicationContext(), keyboardView);

		plate.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				return true;
			}
		});

	  /* plate.setOnKeyListener(new View.OnKeyListener() {     	  
       	  public boolean onKey(View arg0, int arg1, KeyEvent event) {
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&
     				  (arg1 == KeyEvent.KEYCODE_ENTER)   )
     			  { 
     			  	EnterClick();
     			  	return true;     			  	
     			  }
   		  		return false;
       	  }     	   
	   });
	   */
	}

	@Override
	protected void onResume() {
		super.onResume();
		//Toast.makeText(getApplicationContext(), "Woke Up, Stamping Time... ", Toast.LENGTH_SHORT).show();
		if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("EASTLANSING")
				|| WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("MANITOWOC"))
		{
			Toast.makeText(getApplicationContext(), WorkingStorage.GetCharVal(Defines.SaveDateVal, getApplicationContext() ), Toast.LENGTH_SHORT).show();
		}
		else
		{
			GetDate.GetDateTime(getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveTimeStartVal, WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext()), getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintTimeStartVal, WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()), getApplicationContext());
		}
	}
	  
	   /* @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        finish();
	        // The activity is about to be destroyed.
	    }*/  
	  /*@Override
	    protected void onStop() {
	        super.onStop();
        	//Defines.clsClassName = SelFuncForm.class ;
        	//Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        	//startActivityForResult(myIntent, 0);
	        finish(); 
     		overridePendingTransition(0, 0);
	    }*/

	protected void processThisPlate(String newString) {
		WorkingStorage.StoreCharVal(Defines.PrintPlateVal, newString, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.SavePlateVal, newString, getApplicationContext());

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.BootLookupMenu))
		{
			Defines.clsClassName = SelFuncForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);

		} else if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			WorkingStorage.StoreCharVal(Defines.SaveChalkPlateVal, newString, getApplicationContext());
			if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
			{
				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				startActivityForResult(myIntent, 0);
				finish();
				overridePendingTransition(0, 0);
			}
		} else {
			if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("LACROSSE"))
			{
				String tmpPlate = WorkingStorage.GetCharVal(Defines.PrintPlateVal,getApplicationContext());
				SystemIOFile.Delete(tmpPlate+".T");
				String URLString = "http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress,getApplicationContext()).trim()+ "/VMULTINEW.asp?plate="+ tmpPlate + "&extra=NOTHING";
				new HttpConnection(handler).get(URLString);
			}

			if (WorkingStorage.GetCharVal(Defines.PlateRepeat,getApplicationContext()).trim().equals("YES")
					&& WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.TicketIssuanceMenu))
			{
				WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, newString.trim(), getApplicationContext());
			}

			if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).equals(Defines.ChalkMenu)) {
				WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, newString.trim(), getApplicationContext());
			}

			PlateInfo.GetPlateInfo( getApplicationContext());
			PlateInfo.GetSecondaryPlateInfo( getApplicationContext());
			if (newString.equals("TEMPTAG"))
			{
				Defines.clsClassName = TempPlateForm.class ;
			} else {
				Defines.clsClassName = StateForm.class ;
			}

			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
	}

	protected String checkParkMobile(String plate) {
		// Make Web Service call to ParkMobile to get JSON response.
		// However Parsing of JSON response data is Not required
		String URLString = "https://api.parkmobile.us/nforceapi/parkingrights/vehicle/" + plate.trim() ;
		String JSONResponse = "";
		JSONResponse = HTTPFileTransfer.GetJSONString(URLString, getApplicationContext());

		if (JSONResponse.length() > 25) {
			PlateStatus = "PAID";
		} else if (JSONResponse.equals("SERVER INTERNAL ERROR")) {
			PlateStatus = "SERVER INTERNAL ERROR";
		} else {
			PlateStatus = "";
		}

		return PlateStatus;
	}

	public void PopIt( String title, String message ){
		// Set Flag to Stay In Alert Window
		String positiveBtnText = "";
		String negativeBtnText = "";
		if (! WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH")) {
			// ENGLISH
			positiveBtnText = "CONTINUE";
			negativeBtnText = "ABORT";
		} else {
			// SPANISH
			positiveBtnText = "CONTINUAR";
			negativeBtnText = "ANULAR";
		}

		new AlertDialog.Builder(this)
				.setTitle( title)
				.setMessage(message)
				.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						//do stuff onclick of POSITIVE
						processThisPlate(newString.trim());
					}
				})
				.setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						//do stuff onclick of NEGATIVE
						// Go Back To Beginning
						//finish();
						Defines.clsClassName = SelFuncForm.class ;
						Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
						startActivityForResult(myIntent, 0);
						finish();
						overridePendingTransition(0, 0);
					}
				}).show();
	}
}