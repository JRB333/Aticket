package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class ChalkTimeForm extends ActivityGroup {

	private Boolean chalkMatchFound = false;
	private String SaveChalkTime = "";

	private void DrawTheChalk()
	{
		String RealTime = WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext());
		if ( SystemIOFile.exists("CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R"))
		{
//			if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("EVANSVILLE")
//					&& WorkingStorage.GetCharVal(Defines.MenuProgramVal,getApplicationContext()).equals(Defines.ChalkMenu))
			if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("EVANSVILLE"))
			{
				EvansvilleSeekChalkPlate(WorkingStorage.GetCharVal(Defines.SaveChalkPlateVal, getApplicationContext()),
						WorkingStorage.GetCharVal(Defines.SaveChalkStreetVal, getApplicationContext()),
						WorkingStorage.GetCharVal(Defines.SaveChalkStateVal, getApplicationContext()),
						WorkingStorage.GetCharVal(Defines.SaveChalkMeterVal, getApplicationContext()));
			}
			else
			{
				SeekChalkPlate(WorkingStorage.GetCharVal(Defines.SaveChalkPlateVal, getApplicationContext()),
						WorkingStorage.GetCharVal(Defines.SaveChalkStreetVal, getApplicationContext()),
						WorkingStorage.GetCharVal(Defines.SaveChalkDirectionVal, getApplicationContext()),
						WorkingStorage.GetCharVal(Defines.SaveChalkMeterVal, getApplicationContext()),
						WorkingStorage.GetCharVal(Defines.SaveChalkStateVal, getApplicationContext()),
						WorkingStorage.GetCharVal(Defines.SaveChalkStemVal, getApplicationContext()),
						WorkingStorage.GetCharVal(Defines.SaveChalkNumberVal, getApplicationContext()));
			}
			if (WorkingStorage.GetCharVal(Defines.ChalkTimeVal,getApplicationContext()).equals("NONE"))
			{
				WriteChalkRecord();
				TextView tvNext = (TextView) findViewById(R.id.TextView01);
				tvNext.setText(RealTime);
				TextView tvLast = (TextView) findViewById(R.id.TextView02);
				tvLast.setText("NEW");
			}
			else
			{
				String tempTime = WorkingStorage.GetCharVal(Defines.ChalkTimeVal,getApplicationContext());
				TextView tvNext = (TextView) findViewById(R.id.TextView01);
				tvNext.setText(tempTime.substring(0,2)+":"+tempTime.substring(2,4));
				TextView tvLast = (TextView) findViewById(R.id.TextView02);
				tvLast.setText(WorkingStorage.GetCharVal(Defines.PrintTimeVal,getApplicationContext()));
			}
		}
		else // Chalk file doesn't exist for this day so write the Chalk Record and be done
		{
			WriteChalkRecord();
			TextView tvNext = (TextView) findViewById(R.id.TextView01);
			tvNext.setText(RealTime);
			TextView tvLast = (TextView) findViewById(R.id.TextView02);
			tvLast.setText("NEW");
		}
	}

	private void WriteChalkRecord()
	{
		String RecordBuffer = "";
		String ChalkSave = WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext());

		RecordBuffer = WorkingStorage.GetCharVal(Defines.SaveChalkPlateVal, getApplicationContext());
		while (RecordBuffer.length() < 8)
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkStreetVal, getApplicationContext());
		while (RecordBuffer.length() < 11)
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkDirectionVal, getApplicationContext());
		while (RecordBuffer.length() < 12)
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkMeterVal, getApplicationContext());
		while (RecordBuffer.length() < 20)
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkStateVal, getApplicationContext());
		while (RecordBuffer.length() < 22)
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext());
		while (RecordBuffer.length() < 26)
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, getApplicationContext());
		while (RecordBuffer.length() < 31)
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveDateVal, getApplicationContext());
		while (RecordBuffer.length() < 37)
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkStemVal, getApplicationContext());
		while (RecordBuffer.length() < 39)
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkNumberVal, getApplicationContext());
		while (RecordBuffer.length() < 45)
			RecordBuffer += " ";
		// all new here
		while (RecordBuffer.length() < 60) // put in an extra 15 characters of spaces to ensure no unload issues
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.PrintChalkStreetVal, getApplicationContext());
		while (RecordBuffer.length() < 80) // put in an extra 15 characters of spaces to ensure no unload issues
			RecordBuffer += " ";
		RecordBuffer = RecordBuffer + WorkingStorage.GetCharVal(Defines.PrintChalkDirectionVal, getApplicationContext());
		while (RecordBuffer.length() < 95) // put in an extra 15 characters of spaces to ensure no unload issues
			RecordBuffer += " ";
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R", true)); //true will cause the file to be created if not there or just append if it is
			out.write(RecordBuffer);
			out.newLine();
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			Messagebox.Message("Chalk File Creation Exception Occured: " + e, getApplicationContext());
		}
	}

	private void SeekChalkPlate(String PlatePassed, String StreetPassed, String DirectionPassed, String MeterPassed, String StatePassed, String StemPassed, String NumberPassed)
	{
		String TimeBuffer = "NONE";
		WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());

		if (PlatePassed.equals(""))
		{
			return;
		}

		chalkMatchFound = false;

		String MatchLine = "";
		String thisLine = "";
		String ChalkString = "";

		String ChalkFileName = "/data/data/com.clancy.aticket/files/CHAL" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R";
		File file = new File(ChalkFileName);
		if (file.exists())   // check if file exist
		{
			try {
				Scanner s = new Scanner(new File(ChalkFileName)).useDelimiter("\n");
				ArrayList<String> list = new ArrayList<String>();
				while (s.hasNext()) {
					list.add(s.next());
				}
				s.close();

				// Only check for this specific PlateState
				String ThisPlateStateNumberStreetDirection = PlatePassed.trim() + "-" + StatePassed.trim() + "-" + NumberPassed.trim() + "-" + StreetPassed.trim() + "-" + DirectionPassed.trim();

				for (int i = 0; i < list.size(); i++) {
					thisLine = list.get(i);
					ChalkString = thisLine;

					String Plate1 = thisLine.substring(0, 8);
					String StreetCode1 = thisLine.substring(8,11);
					String DirectionCode1 = thisLine.substring(11,12);
					String Meter1 = thisLine.substring(11,20);
					String StateCode1 = thisLine.substring(20,22);
					String Time1 = thisLine.substring(22, 26);
					String Date1 = thisLine.substring(32,38);
					String Number1 = thisLine.substring(39,45);
					String StreetDesc1 = "";
					String Direction1 = "";
					if (thisLine.length() > 45) {
						StreetDesc1 = thisLine.substring(60,80);
						Direction1 = thisLine.substring(80,95);
					}
					String chkPlateStateNumberStreetDirection = Plate1.trim() + "-" + StateCode1.trim() + "-" + Number1.trim() + "-" + StreetCode1.trim() + "-" + DirectionCode1.trim();

					if (chkPlateStateNumberStreetDirection.equals(ThisPlateStateNumberStreetDirection)) {
						// Match Found
						MatchLine = thisLine;
						chalkMatchFound = true;

						WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, PlatePassed, getApplicationContext());
						WorkingStorage.StoreCharVal(Defines.TxfrStateVal, StatePassed, getApplicationContext());
						WorkingStorage.StoreCharVal(Defines.TxfrNumVal, NumberPassed, getApplicationContext());
						WorkingStorage.StoreCharVal(Defines.TxfrDirVal, DirectionCode1, getApplicationContext());
						WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, DirectionCode1, getApplicationContext());
						if (!StreetDesc1.equals("")) {
							WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, StreetDesc1, getApplicationContext());
						} else {
							WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, StreetCode1, getApplicationContext());
						}

						TimeBuffer = MatchLine.substring(22, 26);
						WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());
						WorkingStorage.StoreCharVal(Defines.TxfrChalkTime, TimeBuffer, getApplicationContext());

						// Now Look For Any Earlier Match Line

						String ShortThisLine = StateCode1 + Plate1 + DirectionCode1 + Number1 + StreetCode1;
						String compareLine = "";
						break;
					} else {
						// Do Nothing For Other PlateState Combinations
					}
				}

				file = null;
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), e.toString(), 2000);
			}
		} else {
			file = null;
		}
	}

	private void EvansvilleSeekChalkPlate(String PlatePassed, String StreetPassed, String StatePassed, String MeterPassed)
	{
		String TimeBuffer = "NONE";
		WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());

		if (PlatePassed.equals(""))
		{
			return;
		}
		String line = "";
		String ChalkFileName = "/data/data/com.clancy.aticket/files/CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R";
		File file = new File(ChalkFileName);
		if(file.exists())   // check if file exist
		{
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));

				int i = 1;
				while ((line = br.readLine()) != null )
				{
					if (line.length() >= 45)
					{
						// The routine in the windows mobile software could have
						// never worked! basically we just want to find the last
						// chalk time and then check to see if a different
						// plate has parked in the same space sometime thereafter
						// if so blank out the last chalk time.
						// the whole SomeOneElseParkedInThisMeter var is worthless
						if (line.substring(0,8).trim().equals(PlatePassed.trim()))
						{
							if ( line.substring(20,22).trim().equals(StatePassed.trim())
									&& line.substring(8,11).trim().equals(StreetPassed.trim())
									&& line.substring(12,20).trim().equals(MeterPassed.trim()))
							{
								TimeBuffer = line.substring(22, 26);
								WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());
							}
						}
		        		 /*else
	        			 {
		        			 //Now check to see if someone else parked in that same spot there after
		        			 if ( line.substring(12,20).trim().equals(MeterPassed.trim())
		        					 && line.substring(8,11).trim().equals(StreetPassed.trim()))
		        			 {
			        			 TimeBuffer = "NONE";		        					 
			        			 WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());
		        			 }		        			 
	        			 }*/
					}
					i++;
				}
				br.close();
				file = null;
			}catch (IOException e)
			{
				Toast.makeText(getApplicationContext(), e.toString(), 2000);
			}
		}
		else
		{
			file = null;
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chalktimeform);

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			TextView functionLabel = (TextView) findViewById(R.id.functionLabel);
			functionLabel.setText("Chalking ");
			functionLabel.setVisibility(View.VISIBLE);
		}

		chalkMatchFound = false;
		GetDate.GetDateTime(getApplicationContext());
		SaveChalkTime = WorkingStorage.GetCharVal(Defines.ChalkTimeVal,getApplicationContext()).toString();
		DrawTheChalk();

		Button IssueTicket = (Button) findViewById(R.id.chalkIssueTicket);
		if (chalkMatchFound == true) {
			IssueTicket.setVisibility(View.VISIBLE);
		} else {
			// Ensure Txfr Data is Cleared Out
			clearTxfrData();
		}
		IssueTicket.setOnClickListener(new View.OnClickListener() {
										   public void onClick(View view) {

											   if (NextCiteNum.GetNextCitationNumber(0, getApplicationContext())== true) //if true the unit is out of citations
											   {
												   Messagebox.Message("Out Of Electronic Citation Numbers, Call Clancy 303-759-4276", getApplicationContext());
											   }
											   else {
												   WorkingStorage.StoreCharVal(Defines.ReturnToChalk, "TRUE", getApplicationContext());

												   String EarliestChalkTime = WorkingStorage.GetCharVal(Defines.ChalkTimeVal,getApplicationContext());
												   WorkingStorage.StoreCharVal(Defines.SaveChalkVal, EarliestChalkTime, getApplicationContext());
												   WorkingStorage.StoreCharVal(Defines.PrintChalkVal, EarliestChalkTime, getApplicationContext());

												   WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0, getApplicationContext());
												   WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, 0, getApplicationContext());

												   WorkingStorage.StoreCharVal(Defines.MenuProgramVal, Defines.TicketIssuanceMenu, getApplicationContext());
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
		);

		if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
		{
			TextView tvSpanish = (TextView) findViewById(R.id.TextViewTime);
			tvSpanish.setText("MARCAR");
			TextView tvSpanish2 = (TextView) findViewById(R.id.TextViewCurrent);
			tvSpanish2.setText("ACTUAL");
		}
        /*String ClancyRecord = SearchData.GetRecordNumberNoLength("CLANCY.J", 1, getApplicationContext());
		String NextNum = ClancyRecord.substring(113, 121);
		String LastNum = ClancyRecord.substring(121, 129);   
	    TextView tvNext = (TextView) findViewById(R.id.TextViewNext);
	    tvNext.setText("Next: " + NextNum);
	    TextView tvLast = (TextView) findViewById(R.id.TextViewLast);
	    tvLast.setText("Last:  " + LastNum);*/

		Button enter = (Button) findViewById(R.id.buttonEnter);
		enter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CustomVibrate.VibrateButton(getApplicationContext());
				WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0,getApplicationContext());
				if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
				{
					Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				}
			}
		});
		enter.requestFocus();
	}

	public void clearTxfrData() {
		WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrStateVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrNumVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrDirVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrChalkTime, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrSpace, "", getApplicationContext());
	}
}