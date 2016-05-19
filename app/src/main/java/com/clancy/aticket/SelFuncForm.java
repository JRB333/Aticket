package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SelFuncForm extends ActivityGroup {

	private int CurrentRecordPointer = 1;

	private void EnterClick(View view)
	{
		CustomVibrate.VibrateButton(getApplicationContext());
		String blah;
		blah = WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext());
		if (blah.trim().equals(Defines.BootLookupMenu ))
		{
			WorkingStorage.ClearAllVariables(getApplicationContext());
			Defines.clsClassName = StateForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.TimeOfDayMenu ))
		{
			Defines.clsClassName = ShowTimeForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.CountOfTicketsMenu ))
		{
			Defines.clsClassName = CountForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.OfficerDataMenu ))
		{
			//Messagebox.Message("OFFICER DATA PGM",getApplicationContext());
			WorkingStorage.StoreLongVal(Defines.CurrentOffOrderVal, 0, getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.MenuReturnVal, "T", getApplicationContext());
			GetDate.GetDateTime(getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TLogTimeVal, WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext()), getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TLogDateVal, WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()), getApplicationContext());

			if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
			{
				Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
				startActivityForResult(myIntent, 0);
				finish();
				overridePendingTransition(0, 0);
			}
		}
		else if (blah.trim().equals(Defines.ChalkMenu ))
		{
			//Messagebox.Message("CHALK MENU",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TxfrStateVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TxfrDirVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TxfrNumVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TxfrSpace, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SavingTickFile, "", getApplicationContext());

			Boolean DoSetup = MustDoSetup.MustDoSetupCheck(getApplicationContext());
			if (DoSetup == true)
			{
				WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0, getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintMeterVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.SaveDirectionVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.SaveStreetVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.SaveNumberVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.SaveStemVal, "", getApplicationContext());
				if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
				{
					Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				}
			}
			else
			{
				TextView message = (TextView) findViewById(R.id.TextMessage);
				message.setText("!Must Do Setup!");
				if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
				{
					TextView tvSpanish = (TextView) findViewById(R.id.TextMessage);
					tvSpanish.setText("Por favor disposici�n");
				}

				GetMenuItem(2);
			}
		}
		else if (blah.equals(Defines.HonorBoxMenu ) )//&& true == false uncomment true == false live build until honorbox is complete.
		{
			WorkingStorage.StoreCharVal(Defines.ReprintHonorTicket, "", getApplicationContext());			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}
			WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "DONE", getApplicationContext());
			WorkingStorage.ClearAllVariables(getApplicationContext());
			GetDate.GetDateTime(getApplicationContext());
			if (NextCiteNum.GetNextCitationNumber(0,getApplicationContext()) == true) //if true the unit is out of citations
			{
				Messagebox.Message("Out Of Electronic Citation Numbers, Call Clancy 303-759-4276", getApplicationContext());
			}
			else
			{
				Boolean DoSetup = MustDoSetup.MustDoSetupCheck(getApplicationContext());
				if (DoSetup == true)
				{
					WorkingStorage.StoreCharVal(Defines.SecondTicketVal, "NO", getApplicationContext());

					Defines.clsClassName = HBoxSelectLotForm.class ;
					Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				}
				else
				{
					TextView message = (TextView) findViewById(R.id.TextMessage);
					message.setText("!Must Do Setup!");
					if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
					{
						TextView tvSpanish = (TextView) findViewById(R.id.TextMessage);
						tvSpanish.setText("Por favor disposici�n");
					}
					GetMenuItem(2);
				}
			}
		}
		else if (blah.trim().equals(Defines.TicketIssuanceMenu ))
		{
			//Messagebox.Message("TICKET ISSUANCE",getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.ReprintHonorTicket, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "DONE", getApplicationContext());

			WorkingStorage.StoreLongVal(Defines.PictureTakenVal, 0, getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.LatitudeVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.LongitudeVal, "", getApplicationContext());

			GetDate.GetDateTime(getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveTimeStartVal, WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext()), getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintTimeStartVal, WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()), getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SavePermitVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintPermitVal, "", getApplicationContext());

			WorkingStorage.StoreCharVal(Defines.SaveVMultiErrorVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PrintFeeVal, "", getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.PlateTempMessageVal, "", getApplicationContext());

			WorkingStorage.ClearSomeVariables(getApplicationContext());

			if (NextCiteNum.GetNextCitationNumber(0,getApplicationContext()) == true) //if true the unit is out of citations
			{
				Messagebox.Message("Out Of Electronic Citation Numbers, Call Clancy 303-759-4276", getApplicationContext());
			}
			else
			{
				if (WorkingStorage.GetCharVal(Defines.GPSSurveyVal, getApplicationContext()).trim().equals("GPS"))
				{
					// GPS survey - no officer data program
					MustDoSetup.WriteMustDoSetupDate(getApplicationContext());

					WorkingStorage.StoreCharVal(Defines.PrintPlateVal, "GPS", getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.SavePlateVal, "GPS", getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.PrintStateVal, WorkingStorage.GetCharVal(Defines.DefaultState, getApplicationContext()), getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.SaveStateVal, WorkingStorage.GetCharVal(Defines.DefaultState, getApplicationContext()), getApplicationContext());
				}

				Boolean DoSetup = MustDoSetup.MustDoSetupCheck(getApplicationContext());
				if (DoSetup == true)
				{
					try
					{
						if (!Defines.locationManager.equals(null))
						{
							if (!Defines.locationListener.equals(null))
							{
								Defines.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1.0f, Defines.locationListener); //start GPS to obtain signal
							}
						}
					} catch(NullPointerException e)
					{
					}
					finally
					{
					}
					//Defines.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1.0f, Defines.locationListener); //start GPS to obtain signal
					WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, 0, getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.SecondTicketVal, "NO", getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.PreviousPrintVal, "*START", getApplicationContext());
					WorkingStorage.StoreLongVal(Defines.TicketVoidFlag, 1, getApplicationContext());
					//MiscFunctions.EraseVoidedPictures(getApplicationContext());
					if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
					{
						Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
						startActivityForResult(myIntent, 0);
						finish();
						overridePendingTransition(0, 0);
					}
				}
				else
				{
					TextView message = (TextView) findViewById(R.id.TextMessage);
					message.setText("!Must Do Setup!");
					//Messagebox.Message("!Must Do Setup!", getApplicationContext());
					if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
					{
						TextView tvSpanish = (TextView) findViewById(R.id.TextMessage);
						tvSpanish.setText("Por favor disposici�n");
					}
					GetMenuItem(2);
				}
			}
		}
		else if (blah.trim().equals(Defines.ParkByPhoneMenu ))
		{
			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}
			CustomVibrate.VibrateButton(getApplicationContext());
			Defines.clsClassName = ParkByPhoneForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.LAMTAPermitMenu ))
		{
			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}
			CustomVibrate.VibrateButton(getApplicationContext());
			Defines.clsClassName = LAMTAPermitForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.ReprintMenu ))
		{
			WorkingStorage.StoreCharVal(Defines.ReprintHonorTicket, "", getApplicationContext());

			//	Messagebox.Message("REPRINT TICKET",getApplicationContext());
			//SaveTicket.ReadReprintFile(getApplicationContext()); move this to the print form
			WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "DONE", getApplicationContext());
			Defines.clsClassName = PrintSelectForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.SurveyMenu)
				&& WorkingStorage.GetCharVal(Defines.SurveyOption, getApplicationContext()).equals("YES"))
		{
			GetDate.GetDateTime(getApplicationContext());
			Boolean DoSetup = MustDoSetup.MustDoSetupCheck(getApplicationContext());
			if (DoSetup == true)
			{
				WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.TxfrStateVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.TxfrDirVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.TxfrNumVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.TxfrSpace, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.ReprintHonorTicket, "", getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());

				// Launch OLD Survey Utility
				Defines.clsClassName = SurveySelFuncForm.class ;
				Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				startActivityForResult(myIntent, 0);
				finish();
				overridePendingTransition(0, 0);
			} else {
				TextView message = (TextView) findViewById(R.id.TextMessage);
				message.setText("!Must Do Setup!");
				if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
				{
					TextView tvSpanish = (TextView) findViewById(R.id.TextMessage);
					tvSpanish.setText("Por favor disposici�n");
				}
			}
		}
		else if (blah.trim().equals(Defines.TicketSpeechMenu ))
		{
			CustomVibrate.VibrateButton(getApplicationContext());
			Defines.clsClassName = TicketSpeechForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.VirtPermMenu ))
		{
			CustomVibrate.VibrateButton(getApplicationContext());
			Defines.clsClassName = VirtPermForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.VehicleHistoryMenu ))
		{
			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}
			CustomVibrate.VibrateButton(getApplicationContext());
			Defines.clsClassName = PlateHistoryForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.PermitHistoryMenu ))
		{
			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}

			WorkingStorage.StoreCharVal(Defines.MenuProgramVal, Defines.PermitHistoryMenu, getApplicationContext());

			CustomVibrate.VibrateButton(getApplicationContext());
			Defines.clsClassName = PermitForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.ValidLotLookupMenu ))
		{
			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}
			CustomVibrate.VibrateButton(getApplicationContext());
			Defines.clsClassName = PlateHistoryForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else if (blah.trim().equals(Defines.VehiclePrintoutMenu ))
		{
			WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "DONE", getApplicationContext());
			if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
			{
				Messagebox.Message("Function not available in OFFLINE mode!", getApplicationContext());
				return;
			}
			CustomVibrate.VibrateButton(getApplicationContext());
			Defines.clsClassName = PlateHistoryForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selfuncform);
		if (savedInstanceState == null) //Must do this to ensure that rotation doesn't jack everything up
		{
			GetMenuItem(1);
			if (WorkingStorage.GetLongVal(Defines.TicketVoidFlag, getApplicationContext()) == 1 )
			{
				MiscFunctions.VoidTheTicket(getApplicationContext());
			}
		}

		WorkingStorage.StoreCharVal(Defines.ForceUploadVal, "SELFUNC", getApplicationContext());
		try
		{
			if (!Defines.locationManager.equals(null))
			{
				if (!Defines.locationListener.equals(null))
				{
					Defines.locationManager.removeUpdates(Defines.locationListener); //stop the GPS from trying to obtain a signal
				}
			}
		} catch(NullPointerException e)
		{
		}
		finally
		{
		}
		Defines.contextGlobal = getApplicationContext();

		if (WorkingStorage.GetCharVal(Defines.HBoxFlowVal, getApplicationContext()).equals("YES"))
		{
			Defines.clsClassName = HonorLotForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}

		Button btnESC = (Button) findViewById(R.id.buttonESC);
		btnESC.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CustomVibrate.VibrateButton(getApplicationContext());
				if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("HARRISBURG"))
				{
					finish();
					overridePendingTransition(0, 0);
				}
				else
				{
					Defines.clsClassName = PasswordForm.class ;
					Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				}
			}
		});

		TextView message = (TextView) findViewById(R.id.TextMessage);
		message.setText("");

		final Button enter = (Button) findViewById(R.id.buttonEnter);
		enter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				EnterClick(view);
			}
		});

		enter.requestFocus();

		//Button btnLeft = (Button) findViewById(R.id.button1);
		ImageButton btnLeft = (ImageButton)findViewById(R.id.button1);
		btnLeft.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CustomVibrate.VibrateButton(getApplicationContext());
				ScrollMenuFile(-1);
			}
		});

		//Button btnRight = (Button) findViewById(R.id.button2);
		ImageButton btnRight = (ImageButton)findViewById(R.id.button2);
		btnRight.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CustomVibrate.VibrateButton(getApplicationContext());
				ScrollMenuFile(1);
			}
		});

		if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
		{
			TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterPassword);
			tvSpanish.setText("SELECCIONE FUNCI�N:");
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//Toast.makeText(getApplicationContext(), Integer.toString(keyCode), 200).show();
		switch (keyCode) {
			case 59: // 59 is the lower left key on the G2
			{
				//Messagebox.Message("A Pressed", getApplicationContext());
				ScrollMenuFile(-1);
				return true;
			}
			case 60: // 60 is the lower left key on the G2
			{
				//Messagebox.Message("A Pressed", getApplicationContext());
				ScrollMenuFile(1);
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	}

	public void GetMenuItem(int Flags) {

		String MenuOptionLine = "";
		CurrentRecordPointer = 1;

		long MenuTExists = SearchData.GetFileSize("MENU.T");
		if (MenuTExists > 0)
		{
			String PreviousMenu = WorkingStorage.GetCharVal(Defines.PreviousMenu,getApplicationContext());
			String CurrentMenu = "";
			if (PreviousMenu.equals("")) {
				CurrentMenu = Defines.TicketIssuanceMenu;
			} else {
				CurrentMenu = WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext());
			}
			if (PreviousMenu.equals("TICKET") && CurrentMenu.equals("HONORBOX")) {
				WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "YES", getApplicationContext());
			} else {
			    WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());
		    }
			WorkingStorage.StoreCharVal(Defines.PreviousMenu, CurrentMenu ,getApplicationContext());

			String FoundLine = "";
			try {
				FileInputStream in = openFileInput("MENU.T");
				if (in!=null)
				{
					InputStreamReader tmp = new InputStreamReader(in);
					BufferedReader reader = new BufferedReader(tmp);
					while ((FoundLine = reader.readLine())!= null)
					{
						if (FoundLine.length() >= 26)
						{
							if (Flags == 1)
							{
								//if (FoundLine.regionMatches(17, Defines.TicketIssuanceMenu, 0, Defines.TicketIssuanceMenu.length()))
								if (FoundLine.regionMatches(17, CurrentMenu, 0, CurrentMenu.length()))
								{
									MenuOptionLine = FoundLine.substring(1, 16);
									break ;
								}
							}

							if (Flags == 2)
							{
								if (FoundLine.regionMatches(17, Defines.OfficerDataMenu , 0, Defines.OfficerDataMenu.length()))
								{
									MenuOptionLine = FoundLine.substring(1, 17);
									break ;
								}
							}
							CurrentRecordPointer = CurrentRecordPointer + 1;
						}
					}
					in.close();
					TextView TextUpdate = (TextView)findViewById(R.id.widget1);
					TextUpdate.setText(MenuOptionLine);
					WorkingStorage.StoreCharVal(Defines.MenuCodeVal, FoundLine.substring(0,1),getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.MenuDescriptionVal, FoundLine.substring(1, 17),getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.MenuProgramVal, FoundLine.substring(17, 25),getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.MenuReturnVal, FoundLine.substring(25, 26),getApplicationContext());
				}
			}
			catch(Throwable t) {
				Toast
						.makeText(getApplicationContext(), "Ex: " + t.toString(), 2000)
						.show();
			}

		} else
		{
			Messagebox.Message("MISSING MENU.T", getApplicationContext());
		}
	}

	public void ScrollMenuFile(int UpDown) {

		String MenuOptionLine = "EERROR                  ";
		int TempRecordPointer = 1;

		long FileSize = SearchData.GetFileSize("MENU.T");
		if (FileSize > 0)
		{
			long NumberOfRecords = FileSize / 28;
			String FoundLine = "";
			if (CurrentRecordPointer == NumberOfRecords && UpDown == 1)
				CurrentRecordPointer = 1;
			else if (CurrentRecordPointer == 1 && UpDown == -1)
				CurrentRecordPointer = (int) NumberOfRecords ;
			else
				CurrentRecordPointer = CurrentRecordPointer + UpDown;

			try {
				FileInputStream in = openFileInput("MENU.T");
				if (in!=null)
				{
					InputStreamReader tmp = new InputStreamReader(in);
					BufferedReader reader = new BufferedReader(tmp);
					while ((FoundLine = reader.readLine())!= null)
					{
						if (FoundLine.length() >= 26)
						{
							if (TempRecordPointer == CurrentRecordPointer)
							{
								MenuOptionLine = FoundLine.substring(1, 17);
								break ;
							}
							TempRecordPointer = TempRecordPointer + 1;
						}
					}
					in.close();
					TextView TextUpdate = (TextView)findViewById(R.id.widget1);
					TextUpdate.setText(MenuOptionLine);
					WorkingStorage.StoreCharVal(Defines.MenuCodeVal, FoundLine.substring(0,1),getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.MenuDescriptionVal, FoundLine.substring(1, 17),getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.MenuProgramVal, FoundLine.substring(17, 25),getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.MenuReturnVal, FoundLine.substring(25, 26),getApplicationContext());
				}
			}
			catch(Throwable t) {
				Toast
						.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
						.show();
			}
		}else
		{
			Messagebox.Message("MISSING MENU.T",getApplicationContext());
		}
	}
}


