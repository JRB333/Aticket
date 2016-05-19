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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MultipleChalksForm extends ActivityGroup {

	static EditText eChalks;
	private Boolean chalkMatchFound = false;
	private String EarliestChalkTime = "";

	private void SaveTheChalkTime() {
		String RecordBuffer = "";

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
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/CHAL" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R", true)); //true will cause the file to be created if not there or just append if it is
			out.write(RecordBuffer);
			out.newLine();
			out.flush();
			out.close();
		} catch (IOException e) {
			Messagebox.Message("Chalk File Creation Exception Occured: " + e, getApplicationContext());
		}
	}

	private void GetChalkTimes(String PlatePassed) {
		String TimeBuffer = "NONE";
		WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());

		if (PlatePassed.equals("")) {
			return;
		}

		chalkMatchFound = false;

		String fullLine = "";
		String thisLine = "";
		String ChalkString = "";
		// Only check for this specific PlateState
		String ThisPlateState = PlatePassed + "-" + WorkingStorage.GetCharVal(Defines.SaveChalkStateVal, getApplicationContext());

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

				for (int i = 0; i < list.size(); i++) {
					thisLine = list.get(i);
					String chkPlateState = thisLine.substring(0, 8).trim() + "-" + thisLine.substring(20, 22);

					if (chkPlateState.equals(ThisPlateState)) {
						// This Buffer Line PlateState same as Current PlateState
						fullLine = thisLine;
						thisLine = thisLine.substring(0, 8) + "-" + thisLine.substring(20, 22) + "-" + thisLine.substring(39, 45) + "-" + thisLine.substring(80, 95) + "-" + thisLine.substring(60, 80);
						String compareLine = "";

						if (chalkMatchFound == false) {
							for (int j = 0; j < list.size(); j++) {
								if (j != i) {
									compareLine = list.get(j);
									compareLine = compareLine.substring(0, 8) + "-" + compareLine.substring(20, 22) + "-" + compareLine.substring(39, 45) + "-" + compareLine.substring(80, 95) + "-" + compareLine.substring(60, 80);

									if (thisLine.equals(compareLine)) {
										// Match Has Been Found
										chalkMatchFound = true;
										WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, thisLine.substring(0, 8), getApplicationContext());
										WorkingStorage.StoreCharVal(Defines.TxfrStateVal, thisLine.substring(9, 11), getApplicationContext());
										WorkingStorage.StoreCharVal(Defines.TxfrNumVal, thisLine.substring(12, 18).trim(), getApplicationContext());
										WorkingStorage.StoreCharVal(Defines.TxfrDirVal, thisLine.substring(19, 34).trim(), getApplicationContext());
										WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, fullLine.substring(11, 12).trim(), getApplicationContext());
										WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, thisLine.substring(35, thisLine.length()).trim(), getApplicationContext());
										break;
									}
								}
							}
						}

						// Create Display Line(s)
						if (EarliestChalkTime.equals("")) {
							EarliestChalkTime = fullLine.substring(22, 24).trim() + fullLine.substring(24, 26).trim();
						}
						ChalkString = ChalkString
								+ fullLine.substring(22, 24).trim() + ":" + fullLine.substring(24, 26).trim()
								+ " " + fullLine.substring(12, 20).trim()
								+ fullLine.substring(39, 45).trim()
								+ " " + fullLine.substring(80, 95).trim()
								+ " " + fullLine.substring(60, 80).trim()
								+ "\n";
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
		eChalks.setText(ChalkString);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multiplechalksform);

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			TextView functionLabel = (TextView) findViewById(R.id.functionLabel);
			functionLabel.setText("Chalking " );
			functionLabel.setVisibility(View.VISIBLE);
		}

		eChalks = (EditText) findViewById(R.id.editTextPlate);

		GetDate.GetDateTime(getApplicationContext());
		TextView tvTime = (TextView) findViewById(R.id.textPlate);
		tvTime.setText("Current Time: " + WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()));

		SaveTheChalkTime();

		TextView tvPlate = (TextView) findViewById(R.id.textViewPlate);
		tvPlate.setText("Plate: " + WorkingStorage.GetCharVal(Defines.SaveChalkPlateVal, getApplicationContext()) + " - " + WorkingStorage.GetCharVal(Defines.SaveChalkStateVal, getApplicationContext()));

		GetChalkTimes(WorkingStorage.GetCharVal(Defines.SaveChalkPlateVal, getApplicationContext()));

		Button enter = (Button) findViewById(R.id.buttonEnter);
		enter.setOnClickListener(new View.OnClickListener() {
									 public void onClick(View view) {
										 CustomVibrate.VibrateButton(getApplicationContext());
										 WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0, getApplicationContext());

										 // Clear Out Transfer Data
										 clearTxfrData();

										 if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
										 {
											 Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
											 startActivityForResult(myIntent, 0);
											 finish();
											 overridePendingTransition(0, 0);
										 }
									 }
								 }
		);

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
												   // Issue Ticket For This Plate
												   WorkingStorage.StoreCharVal(Defines.ReturnToChalk, "TRUE", getApplicationContext());
												   WorkingStorage.StoreCharVal(Defines.MenuProgramVal, Defines.TicketIssuanceMenu, getApplicationContext());
												   WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0, getApplicationContext());
												   WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, 0, getApplicationContext());

												   WorkingStorage.StoreCharVal(Defines.SaveChalkVal, EarliestChalkTime, getApplicationContext());

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

		enter.requestFocus();
	}

	public void clearTxfrData() {
		WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrStateVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrNumVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrDirVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "", getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, "", getApplicationContext());
	}
}