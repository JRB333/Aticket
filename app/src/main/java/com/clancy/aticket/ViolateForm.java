package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ViolateForm extends ActivityGroup {
	// NOTE Re: VBOOT
	//      Must have associated entries in Custom.A
	//      AND must have Build-VBoot file flag ('Buildvboot' field in ClientLoc) set TRUE
	String ViolateFilename = "";

	private void EnterClick() {
		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
		String tempString = tvDesc1.getText().toString().trim();
		CheckBox chkEscalateOverride = (CheckBox) findViewById(R.id.checkBox1);

		if (chkEscalateOverride.isChecked())
		{
			WorkingStorage.StoreCharVal(Defines.OverridePerformedVal, "VBOOT OVERRIDE", getApplicationContext());
		}
		else
		{
			WorkingStorage.StoreCharVal(Defines.OverridePerformedVal, "", getApplicationContext());
		}

		if (WorkingStorage.GetCharVal(Defines.UseVBoot, getApplicationContext()).trim().equals("VBOOT")
				&& !chkEscalateOverride.isChecked())
		{
			Button enter = (Button) findViewById(R.id.buttonEnter);
			enter.setEnabled(false);
			String ParseString = WorkingStorage.GetCharVal(Defines.EntireDataString, getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveViolateVal,ParseString.substring(0, 3), getApplicationContext());
			if (WorkingStorage.GetCharVal(Defines.NormalVBoot, getApplicationContext()).trim().equals("MULTIPLE"))
			{
				LiveMultipleCheck();
				//Messagebox.Message("LIVE TEST Multi Check!",getApplicationContext());
			} else {
				if (WorkingStorage.GetCharVal(Defines.SavePlateVal,getApplicationContext()).trim().equals("NOPLATE")
					&& WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("FTLEWIS")) {
					// do nothing
				} else {
					int Tier = Vboot.GetVBootList(getApplicationContext());
					PerformChangeViolation(Tier);
				}
			}
			enter.setEnabled(true);
		}

		if (tempString.equals("NOT FOUND")) {
			EditText violateText = (EditText) findViewById(R.id.editTextViolate);
			violateText.selectAll();
			violateText.requestFocus();
		} else {
			String ParseString = WorkingStorage.GetCharVal(
					Defines.EntireDataString, getApplicationContext());

			String ValidBeginTime = "";
			String ValidEndTime = "";
			String CurrentTime = "";
			ValidBeginTime = ParseString.substring(100, 102);
			ValidEndTime = ParseString.substring(102, 104);
			CurrentTime = WorkingStorage.GetCharVal(Defines.SaveTimeVal,
					getApplicationContext());
			if (!ValidBeginTime.equals("00") && !ValidBeginTime.equals("  ")) {
				ValidBeginTime = ValidBeginTime + "00";
				if (Integer.valueOf(CurrentTime) < Integer
						.valueOf(ValidBeginTime)) {
					Messagebox.Message("Invalid Time for Violation",
							getApplicationContext());
					return;
				}
			}
			if (!ValidEndTime.equals("00") && !ValidEndTime.equals("  ")) {
				ValidEndTime = ValidEndTime + "00";
				if (Integer.valueOf(CurrentTime) >= Integer
						.valueOf(ValidEndTime)) {
					Messagebox.Message("Invalid Time for Violation",
							getApplicationContext());
					return;
				}
			}

			if (ParseString.length() >= 156) {
				WorkingStorage.StoreCharVal(Defines.SaveViolateVal,ParseString.substring(0, 3), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintOrdinanceVal,ParseString.substring(3, 28), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintViolDesc1Val,ParseString.substring(44, 69), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintViolDesc2Val,ParseString.substring(69, 94), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PlateMonthFlagVal,ParseString.substring(94, 95), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.MeterFlagVal,ParseString.substring(95, 96), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.ChalkFlagVal,ParseString.substring(96, 97), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.CommentFlagVal,ParseString.substring(97, 98), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.StickerFlagVal,ParseString.substring(98, 99), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.OverTimeFlagVal,ParseString.substring(99, 100),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.BeginTimeFlagVal,ParseString.substring(100, 102),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.EndTimeFlagVal,ParseString.substring(102, 104),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.FootBallFlagVal,ParseString.substring(110, 111),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintDaysEffectVal,ParseString.substring(114, 127),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintComputilVal,ParseString.substring(127, 138),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintFine1Val,ParseString.substring(138, 144),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintFine2Val,ParseString.substring(144, 150),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintFine3Val,ParseString.substring(150, 156),getApplicationContext());
				///Now Copy all fine1, fine2 and fine3 variables to fine1Large etc.
				WorkingStorage.StoreCharVal(Defines.PrintFine1LargeVal,
						WorkingStorage.GetCharVal(Defines.PrintFine1Val, getApplicationContext()),getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintFine2LargeVal,
						WorkingStorage.GetCharVal(Defines.PrintFine2Val, getApplicationContext()),
						getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.PrintFine3LargeVal,
						WorkingStorage.GetCharVal(Defines.PrintFine3Val, getApplicationContext()),
						getApplicationContext());
				/// Now check for violatel.a to overwrite the large amounts
				if(ViolateFilename.equals("VIOLATEL.A"))
				{
					if(ParseString.length()>=180)
					{
						WorkingStorage.StoreCharVal(Defines.PrintFine1LargeVal, ParseString.substring(156, 164), getApplicationContext());
						WorkingStorage.StoreCharVal(Defines.PrintFine2LargeVal, ParseString.substring(164, 172), getApplicationContext());
						WorkingStorage.StoreCharVal(Defines.PrintFine3LargeVal, ParseString.substring(172, 180), getApplicationContext());
					}
				}

				// Messagebox.Message("Need to do the mvboot routine",
				// getApplicationContext());
				if (WorkingStorage.GetCharVal(Defines.CommentFlagVal,getApplicationContext()).equals("0")) {
					WorkingStorage.StoreCharVal(Defines.PrintComment1Val, "",getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.PrintComment2Val, "",getApplicationContext());
					WorkingStorage.StoreCharVal(Defines.PrintComment3Val, "",getApplicationContext());
				}
				if (WorkingStorage.GetCharVal(Defines.CommentFlagVal, getApplicationContext()).equals("1")
						&& ! WorkingStorage.GetCharVal(Defines.SpecialComment, getApplicationContext()).equals("YES"))
				{
					Defines.clsClassName = CommentForm.class;
					Intent myIntent = new Intent(getApplicationContext(),
							SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				} else if (WorkingStorage.GetCharVal(Defines.MeterFlagVal,getApplicationContext()).equals("1")
						&& WorkingStorage.GetCharVal(Defines.TxfrSpace,getApplicationContext()).equals("")
						&& !WorkingStorage.GetCharVal(Defines.SecondTicketVal,getApplicationContext()).equals("YES")
						&& !WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES")) {
					Defines.clsClassName = MeterForm.class;
					Intent myIntent = new Intent(getApplicationContext(),
							SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				} else if (WorkingStorage.GetCharVal(Defines.MeterFlagVal,getApplicationContext()).equals("1")
						&& WorkingStorage.GetCharVal(Defines.TxfrSpace,getApplicationContext()).equals("")
						&& !WorkingStorage.GetCharVal(Defines.ExpandXMeter,	getApplicationContext()).equals("XMETER")
						&& !WorkingStorage.GetCharVal(Defines.SpecialMeterFlag,getApplicationContext()).equals("YES")
						&& !WorkingStorage.GetCharVal(Defines.MeterLastPrompt,getApplicationContext()).equals("YES")) {
					Defines.clsClassName = MeterForm.class;
					Intent myIntent = new Intent(getApplicationContext(),
							SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				} else if (WorkingStorage.GetCharVal(Defines.ChalkFlagVal,getApplicationContext()).equals("1")
						&& !WorkingStorage.GetCharVal(Defines.ChalkData,getApplicationContext()).equals("CHALKDATA")) {
					Defines.clsClassName = ChalkForm.class;
					Intent myIntent = new Intent(getApplicationContext(),
							SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				} else if (WorkingStorage.GetCharVal(Defines.StickerFlagVal,getApplicationContext()).equals("1")) {
					Defines.clsClassName = StickerForm.class;
					Intent myIntent = new Intent(getApplicationContext(),
							SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				} else {
					if (ProgramFlow.GetNextForm("", getApplicationContext()) != "ERROR") {
						Intent myIntent = new Intent(getApplicationContext(),
								SwitchForm.class);
						startActivityForResult(myIntent, 0);
						finish();
						overridePendingTransition(0, 0);
					}
				}
			} else {
				Messagebox.Message(
						"Something wrong with the violate record length!",
						getApplicationContext());
			}
		}
	}

	private void LiveMultipleCheck()
	{
		String tmpPlate = WorkingStorage.GetCharVal(Defines.PrintPlateVal,getApplicationContext());
		if (SystemIOFile.exists(tmpPlate + ".T"))
		{
			String TempViolCode = WorkingStorage.GetCharVal(Defines.SaveViolateVal,	getApplicationContext());
			String EligibleViolCode = "";
			EligibleViolCode = SearchData.FindRecordLine(TempViolCode, TempViolCode.length(), "VMULTI.T", getApplicationContext());
			if (EligibleViolCode.trim().equals(TempViolCode))
			{
				String DisplayInfo = SearchData.FindRecordLine(TempViolCode, TempViolCode.length(), tmpPlate+".T", getApplicationContext());
				if (DisplayInfo.length() == 6) // we got a good return from the website
				{
					if (DisplayInfo.substring(1, 3).equals(	TempViolCode.substring(1, 3)))
					{
						//String ViolateDesc = SearchData.FindRecordLine(DisplayInfo.substring(3, 6),	3, "VIOLATE.A", getApplicationContext());
						String ViolateDesc = SearchData.FindRecordLine(DisplayInfo.substring(3, 6),	3, ViolateFilename, getApplicationContext());
						ShowData(ViolateDesc);
						Messagebox.Message(DisplayInfo.substring(3, 6), getApplicationContext());
					}
					else
					{
						WorkingStorage.StoreCharVal(Defines.SaveVMultiErrorVal,	"FAILED WEB ACCESS", getApplicationContext());
						if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("LACROSSE"))
						{
							TempViolCode = "2" + TempViolCode.substring(1, 3);
						}
						//String ViolateDesc = SearchData.FindRecordLine(	TempViolCode, 3, "VIOLATE.A", getApplicationContext());
						String ViolateDesc = SearchData.FindRecordLine(	TempViolCode, 3, ViolateFilename, getApplicationContext());
						ShowData(ViolateDesc);
					}
				}
				else
				{
					WorkingStorage.StoreCharVal(Defines.SaveVMultiErrorVal,	"FAILED WEB ACCESS", getApplicationContext());
					if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("LACROSSE"))
					{
						TempViolCode = "2" + TempViolCode.substring(1, 3);
					}
					if (SystemIOFile.exists(TempViolCode.substring(1, 3)+".A"))
					{
						WorkingStorage.StoreCharVal(Defines.SaveVMultiErrorVal,	"SEARCHED BACKUP", getApplicationContext());
						String PlateToSearch = WorkingStorage.GetCharVal(Defines.PrintPlateVal,getApplicationContext());
						while (PlateToSearch.length() < 8)
							PlateToSearch += " ";
						String FoundBackupPlateString = SearchData.FindRecordLine(	PlateToSearch, 8, TempViolCode.substring(1, 3)+".A", getApplicationContext());
						if (FoundBackupPlateString.length() > 10)
						{
							if (FoundBackupPlateString.substring(0,8).equals(PlateToSearch))
							{
								TempViolCode = FoundBackupPlateString.substring(8,11);
							}
						}

					}
					//String ViolateDesc = SearchData.FindRecordLine(TempViolCode, 3,	"VIOLATE.A", getApplicationContext());
					String ViolateDesc = SearchData.FindRecordLine(TempViolCode, 3,	ViolateFilename, getApplicationContext());
					ShowData(ViolateDesc);
				}
			}
		}
		else
		{
			String TempViolCode = WorkingStorage.GetCharVal(Defines.SaveViolateVal,	getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveVMultiErrorVal,	"FAILED WEB ACCESS", getApplicationContext());
			if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("LACROSSE"))
			{
				TempViolCode = "2" + TempViolCode.substring(1, 3);
			}
			if (SystemIOFile.exists(TempViolCode.substring(1, 3)+".A"))
			{
				WorkingStorage.StoreCharVal(Defines.SaveVMultiErrorVal,	"SEARCHED BACKUP", getApplicationContext());
				String PlateToSearch = WorkingStorage.GetCharVal(Defines.PrintPlateVal,getApplicationContext());
				while (PlateToSearch.length() < 8)
					PlateToSearch += " ";
				String FoundBackupPlateString = SearchData.FindRecordLine(	PlateToSearch, 8, TempViolCode.substring(1, 3)+".A", getApplicationContext());
				if (FoundBackupPlateString.length() > 10)
				{
					if (FoundBackupPlateString.substring(0,8).equals(PlateToSearch))
					{
						TempViolCode = FoundBackupPlateString.substring(8,11);
					}
				}

			}
			//String ViolateDesc = SearchData.FindRecordLine(TempViolCode, 3,	"VIOLATE.A", getApplicationContext());
			String ViolateDesc = SearchData.FindRecordLine(TempViolCode, 3,	ViolateFilename, getApplicationContext());
			ShowData(ViolateDesc);
		}
	}

	private void ShowData(String ViolateString) {
		WorkingStorage.StoreCharVal(Defines.EntireDataString, ViolateString, getApplicationContext());

		EditText violateText = (EditText) findViewById(R.id.editTextViolate);
		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
		tvDesc1.setText(ViolateString.substring(44, 69));
		TextView tvDesc2 = (TextView) findViewById(R.id.TextViewDesc2);
		tvDesc2.setText(ViolateString.substring(69, 94));
		TextView tvOrdinance = (TextView) findViewById(R.id.textViewOrdinance);
		tvOrdinance.setText(ViolateString.substring(3, 28));
		WorkingStorage.StoreCharVal(Defines.RotateValue,
				ViolateString.substring(0, 3), getApplicationContext());
		violateText.setText(ViolateString.substring(0, 3));
		violateText.selectAll();
		violateText.requestFocus();
	}

	private void LeftClick() {
		EditText violateText = (EditText) findViewById(R.id.editTextViolate);
		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR",
				getApplicationContext());
		violateText.selectAll();
		// violateText.setTextColor(Color.parseColor("#ff0000CC"));
		//String ViolateDesc = SearchData.ScrollUpThroughFile("VIOLATE.A", getApplicationContext());
		String ViolateDesc = SearchData.ScrollUpThroughFile(ViolateFilename, getApplicationContext());
		if (ViolateDesc.length() > 3) {
			ShowData(ViolateDesc);
		}
	}

	private void RightClick() {
		EditText violateText = (EditText) findViewById(R.id.editTextViolate);
		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR",
				getApplicationContext());
		violateText.selectAll();
		// violateText.setTextColor(Color.parseColor("#ff0000CC"));
		//String ViolateDesc = SearchData.ScrollDownThroughFile("VIOLATE.A", getApplicationContext());
		String ViolateDesc = SearchData.ScrollDownThroughFile(ViolateFilename, getApplicationContext());
		if (ViolateDesc.length() > 3) {
			ShowData(ViolateDesc);
		}
	}

	private void TextChange(String SearchString) {
		//String ViolateString = SearchData.FindRecordLine(SearchString,	SearchString.length(), "VIOLATE.A", getApplicationContext());
		String ViolateString = SearchData.FindRecordLine(SearchString,	SearchString.length(), ViolateFilename, getApplicationContext());
		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
		TextView tvDesc2 = (TextView) findViewById(R.id.TextViewDesc2);
		TextView tvOrdinance = (TextView) findViewById(R.id.textViewOrdinance);
		if (ViolateString.equals("NIF")) {
			tvDesc1.setText("NOT FOUND");
			tvDesc2.setText("");
		} else {
			tvDesc1.setText(ViolateString.substring(44, 69));
			tvDesc2.setText(ViolateString.substring(69, 94));
			tvOrdinance.setText(ViolateString.substring(3, 28));
			WorkingStorage.StoreCharVal(Defines.RotateValue,
					ViolateString.substring(0, 3), getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.EntireDataString,
					ViolateString, getApplicationContext());
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.violateform);

		if (SystemIOFile.exists("VIOLATEL.A"))
		{
			ViolateFilename = "VIOLATEL.A";
		}
		else
		{
			ViolateFilename = "VIOLATE.A";
		}

		Display display = getWindowManager().getDefaultDisplay();
		int ScreenWidth = display.getWidth();
		int ScreenHeight = display.getHeight();

		CheckBox chkEscalateOverride = (CheckBox) findViewById(R.id.checkBox1);

		if (ScreenHeight < 550) {
			// --- Galaxy S2 - Shorter Screen Height ---
			chkEscalateOverride.setText("");
		}
		//if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).equals("OCALA"))
		if (WorkingStorage.GetCharVal(Defines.OfficerOverrideVBootVal, getApplicationContext()).equals("YES")
			 && WorkingStorage.GetCharVal(Defines.UseVBoot, getApplicationContext()).trim().equals("VBOOT"))
		{
			// --- VBoot Used AND Officer Override Option Enabled ---
			chkEscalateOverride.setVisibility(View.VISIBLE);
			chkEscalateOverride.setEnabled(true);
		}
		else
		{
			// --- Either VBoot NOT Used OR Officer Override Option NOT Enabled ---
			chkEscalateOverride.setVisibility(View.INVISIBLE);
			chkEscalateOverride.setEnabled(false);
		}

		if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
		{
			TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterViolate);
			tvSpanish.setText("VIOLACIÓN:");
		}

		if (WorkingStorage.GetCharVal(Defines.GPSSurveyVal, getApplicationContext()).trim().equals("GPS"))
		{
			TextView tvGPS = (TextView) findViewById(R.id.widgetEnterViolate);
			tvGPS.setText("LOGGING:");
		}

		Button second = (Button) findViewById(R.id.buttonESC);
		second.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CustomVibrate.VibrateButton(getApplicationContext());
				Defines.clsClassName = SelFuncForm.class;
				Intent myIntent = new Intent(view.getContext(),
						SwitchForm.class);
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

		final EditText violateText = (EditText) findViewById(R.id.editTextViolate);
		violateText.requestFocus();
		// violateText.setCursorVisible(false);

		KeyboardView keyboardView = null;
		keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
		CustomKeyboard.PickAKeyboard(violateText, "LICENSE", getApplicationContext(), keyboardView);
		/*if (ScreenHeight < 550) {
			// --- Galaxy S2 - Shorter Screen Height ---
			CustomKeyboard.PickAKeyboard(violateText, "LICENSE_SMALL", getApplicationContext(), keyboardView);
		} else {
			// --- Galaxy S3, S4, etc - Taller Screen Height ---
			CustomKeyboard.PickAKeyboard(violateText, "LICENSE", getApplicationContext(), keyboardView);
		}*/
		if (savedInstanceState == null) {
			WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR",
					getApplicationContext());
			violateText.selectAll();
			// violateText.setTextColor(Color.parseColor("#ff0000CC"));
		}

		String ViolateDesc = "";
		String ViolateCode = "";
		if (savedInstanceState == null) {
			WorkingStorage.StoreCharVal(Defines.EntireDataString, "",
					getApplicationContext());

			ViolateCode = WorkingStorage.GetCharVal(Defines.SaveViolateVal,
					getApplicationContext());
			if (ViolateCode.equals("")) {
				WorkingStorage.StoreLongVal(Defines.RecordPointer, 0,
						getApplicationContext());
				//ViolateDesc = SearchData.ScrollUpThroughFile("VIOLATE.A", getApplicationContext());
				ViolateDesc = SearchData.ScrollUpThroughFile(ViolateFilename, getApplicationContext());
				ShowData(ViolateDesc);
			} else {
				//ViolateDesc = SearchData.FindRecordLine(ViolateCode, 3,	"VIOLATE.A", getApplicationContext());
				ViolateDesc = SearchData.FindRecordLine(ViolateCode, 3,	ViolateFilename, getApplicationContext());
				if (ViolateDesc.equals("NIF")) {
					WorkingStorage.StoreLongVal(Defines.RecordPointer, 0,
							getApplicationContext());
					//ViolateDesc = SearchData.ScrollUpThroughFile("VIOLATE.A", getApplicationContext());
					ViolateDesc = SearchData.ScrollUpThroughFile(ViolateFilename, getApplicationContext());
				}
				ShowData(ViolateDesc);
			}
			WorkingStorage.StoreCharVal(Defines.RotateValue,
					ViolateDesc.substring(0, 3), getApplicationContext());
		} // Must do this to ensure that rotation doesn't jack everything up
		else {
			ViolateCode = WorkingStorage.GetCharVal(Defines.RotateValue,
					getApplicationContext());
			//ViolateDesc = SearchData.FindRecordLine(ViolateCode, 3,	"VIOLATE.A", getApplicationContext());
			ViolateDesc = SearchData.FindRecordLine(ViolateCode, 3,	ViolateFilename, getApplicationContext());
			ShowData(ViolateDesc);
		}

		violateText.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				return true;
			}
		});
		violateText.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
			}

			public void afterTextChanged(Editable s) {
				TextChange(s.toString());
				// Messagebox.Message(s.toString(), getApplicationContext());
			}
		});
	}

	private void PerformChangeViolation(int VBootReason) {
		String NotFoundCode = WorkingStorage.GetCharVal(Defines.NotOnVBoot,	getApplicationContext());
		String TempViolCode = WorkingStorage.GetCharVal(Defines.SaveViolateVal,	getApplicationContext());
		String FoundLevel1 = WorkingStorage.GetCharVal(Defines.OnVBootLevel1, getApplicationContext());
		String FoundLevel2 = WorkingStorage.GetCharVal(Defines.OnVBootLevel2, getApplicationContext());
		String FoundLevel3 = WorkingStorage.GetCharVal(Defines.OnVBootLevel3, getApplicationContext());
		String FoundLevel4 = WorkingStorage.GetCharVal(Defines.OnVBootLevel4, getApplicationContext());
		String FoundLevel5 = WorkingStorage.GetCharVal(Defines.OnVBootLevel5, getApplicationContext());
		String FoundLevel6 = WorkingStorage.GetCharVal(Defines.OnVBootLevel6, getApplicationContext());
		String FoundLevel7 = WorkingStorage.GetCharVal(Defines.OnVBootLevel7, getApplicationContext());
		String FoundLevel8 = WorkingStorage.GetCharVal(Defines.OnVBootLevel8, getApplicationContext());
		String FoundLevel9 = WorkingStorage.GetCharVal(Defines.OnVBootLevel9,getApplicationContext());
		Boolean Changed = false;

		if (WorkingStorage.GetCharVal(Defines.NormalVBoot,getApplicationContext()).equals("NORMAL"))
		{
			if (VBootReason == 0) {
				TempViolCode = NotFoundCode + TempViolCode.substring(1);
			} else {
				if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("HOWARDCOUNTY"))
				{
					Messagebox.Message("Violation Escalated!",getApplicationContext());
				}
				String ViolationPrefix = "1";
				if (!FoundLevel1.equals(""))
					ViolationPrefix = FoundLevel1;
				if (!FoundLevel2.equals(""))
					ViolationPrefix = FoundLevel2;
				if (!FoundLevel3.equals(""))
					ViolationPrefix = FoundLevel3;
				if (!FoundLevel4.equals(""))
					ViolationPrefix = FoundLevel4;
				if (!FoundLevel5.equals(""))
					ViolationPrefix = FoundLevel5;
				if (!FoundLevel6.equals(""))
					ViolationPrefix = FoundLevel6;
				if (!FoundLevel7.equals(""))
					ViolationPrefix = FoundLevel7;
				if (!FoundLevel8.equals(""))
					ViolationPrefix = FoundLevel8;
				if (!FoundLevel9.equals(""))
					ViolationPrefix = FoundLevel9;

				if (VBootReason == 1 && !FoundLevel1.equals(""))
					ViolationPrefix = FoundLevel1;
				if (VBootReason == 2 && !FoundLevel2.equals(""))
					ViolationPrefix = FoundLevel2;
				if (VBootReason == 3 && !FoundLevel3.equals(""))
					ViolationPrefix = FoundLevel3;
				if (VBootReason == 4 && !FoundLevel4.equals(""))
					ViolationPrefix = FoundLevel4;
				if (VBootReason == 5 && !FoundLevel5.equals(""))
					ViolationPrefix = FoundLevel5;
				if (VBootReason == 6 && !FoundLevel6.equals(""))
					ViolationPrefix = FoundLevel6;
				if (VBootReason == 7 && !FoundLevel7.equals(""))
					ViolationPrefix = FoundLevel7;
				if (VBootReason == 8 && !FoundLevel8.equals(""))
					ViolationPrefix = FoundLevel8;
				if (VBootReason == 9 && !FoundLevel9.equals(""))
					ViolationPrefix = FoundLevel9;

				TempViolCode = ViolationPrefix + TempViolCode.substring(1);

				// ##### JRB #####
				// Check That New Violation Code Exists
				// If NOT, Decrement Prefix by 1
				String  chkViolate = SearchData.FindRecordLine(TempViolCode, TempViolCode.length(),ViolateFilename, getApplicationContext());
				while(chkViolate.equals("NIF")) {
					int NewViolationPrefix = Integer.parseInt(ViolationPrefix) - 1;
					ViolationPrefix = Integer.toString(NewViolationPrefix);
					TempViolCode = ViolationPrefix + TempViolCode.substring(1);
					chkViolate = SearchData.FindRecordLine(TempViolCode, TempViolCode.length(), ViolateFilename, getApplicationContext());
				}
			}
			Changed = true;
		} else {
			if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("NAPERVILLE"))
			{
				if (VBootReason == 0) {
					TempViolCode = NotFoundCode + TempViolCode.substring(1);
					WorkingStorage.StoreCharVal(Defines.NapervilleVBootVal,	"NO", getApplicationContext());
				}
				if (VBootReason >= 1 && VBootReason <= 9) {
					TempViolCode = FoundLevel1 + TempViolCode.substring(1);
					WorkingStorage.StoreCharVal(Defines.NapervilleVBootVal,	"YES", getApplicationContext());
				}
				if (VBootReason == 17) // 17 why?
				{
					TempViolCode = FoundLevel2 + TempViolCode.substring(1);
					WorkingStorage.StoreCharVal(Defines.NapervilleVBootVal,	"YES", getApplicationContext());
				}
				Changed = true;

			} else if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("ATLANTIC"))
			{
				if (VBootReason >= 0 && VBootReason <= 2) {
					TempViolCode = NotFoundCode + TempViolCode.substring(1);
				}
				if (VBootReason == 3)
					TempViolCode = FoundLevel1 + TempViolCode.substring(1);
				if (VBootReason == 4)
					TempViolCode = FoundLevel2 + TempViolCode.substring(1);
				if (VBootReason == 5)
					TempViolCode = FoundLevel3 + TempViolCode.substring(1);
				if (VBootReason == 6)
					TempViolCode = FoundLevel4 + TempViolCode.substring(1);
				if (VBootReason >= 7)
					TempViolCode = FoundLevel5 + TempViolCode.substring(1);

				Changed = true;
			} else if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("BYUID"))
			{
				if (VBootReason == 0)
					TempViolCode = NotFoundCode + TempViolCode.substring(1);
				if (VBootReason == 1)
					TempViolCode = FoundLevel1 + TempViolCode.substring(1);
				if (VBootReason == 2)
					TempViolCode = FoundLevel2 + TempViolCode.substring(1);
				if (VBootReason == 3)
					TempViolCode = FoundLevel3 + TempViolCode.substring(1);
				if (VBootReason >= 4)
					TempViolCode = FoundLevel4 + TempViolCode.substring(1);

				Changed = true;
			}
			else if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("STEAMBOAT"))
			{
				if( TempViolCode.substring(1, 2).equals("0")
						&& ((TempViolCode.substring(2, 3).equals("1")
						|| TempViolCode.substring(2, 3).equals("2")
						|| TempViolCode.substring(2, 3).equals("6")
						|| TempViolCode.substring(2, 3).equals("7"))))
				{
					if (VBootReason == 0)
						TempViolCode = NotFoundCode + TempViolCode.substring(1);
					if (VBootReason == 1)
						TempViolCode = FoundLevel1 + TempViolCode.substring(1);
					if (VBootReason == 2)
						TempViolCode = FoundLevel2 + TempViolCode.substring(1);
					if (VBootReason == 3)
						TempViolCode = FoundLevel3 + TempViolCode.substring(1);
					if (VBootReason >= 4)
						TempViolCode = FoundLevel4 + TempViolCode.substring(1);
					Changed = true;
				}
				else
				{
					Changed = false;
				}
			}
			else if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("LEXINGTON")
					&& TempViolCode.substring(1, 2).equals("0")
					&& TempViolCode.substring(2, 3).equals("1"))
			{
				if (VBootReason == 0)
					TempViolCode = NotFoundCode + TempViolCode.substring(1);
				if (VBootReason == 1)
					TempViolCode = FoundLevel1 + TempViolCode.substring(1);
				if (VBootReason == 2)
					TempViolCode = FoundLevel2 + TempViolCode.substring(1);
				if (VBootReason == 3)
					TempViolCode = FoundLevel3 + TempViolCode.substring(1);
				if (VBootReason >= 4)
					TempViolCode = FoundLevel4 + TempViolCode.substring(1);
				Changed = true;
			}
			else if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("CCCPITTSBURG")
					&& TempViolCode.substring(1, 2).equals("7")
					&& TempViolCode.substring(2, 3).equals("6"))
			{
				if (VBootReason == 0)
					TempViolCode = NotFoundCode + TempViolCode.substring(1);
				if (VBootReason == 1)
					TempViolCode = FoundLevel1 + TempViolCode.substring(1);
				if (VBootReason == 2)
					TempViolCode = FoundLevel2 + TempViolCode.substring(1);
				if (VBootReason == 3)
					TempViolCode = FoundLevel3 + TempViolCode.substring(1);
				if (VBootReason >= 4)
					TempViolCode = FoundLevel4 + TempViolCode.substring(1);
				Changed = true;
			}
			else if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("PONTIAC")
					&& TempViolCode.substring(1, 2).equals("0")
					&& TempViolCode.substring(2, 3).equals("1"))
			{
				if (VBootReason == 0)
					TempViolCode = NotFoundCode + TempViolCode.substring(1);
				if (VBootReason == 1)
					TempViolCode = FoundLevel1 + TempViolCode.substring(1);
				if (VBootReason == 2)
					TempViolCode = FoundLevel2 + TempViolCode.substring(1);
				if (VBootReason == 3)
					TempViolCode = FoundLevel3 + TempViolCode.substring(1);
				if (VBootReason >= 4)
					TempViolCode = FoundLevel4 + TempViolCode.substring(1);
				Changed = true;
			}
			else if (WorkingStorage.GetCharVal(Defines.ClientName,	getApplicationContext()).equals("TELLURIDE")
			    && VBootReason == 0
				&& (TempViolCode.equals("901") || TempViolCode.equals("948")))
			{
				// ##### JRB - TELLURIDE MOD #####
				// Telluride request - "No previous 801 or 848 violation then 901 and 948 automatically changed to Warning Only"
 			    String NewViolationPrefix = "8";
				TempViolCode = NewViolationPrefix + TempViolCode.substring(1);
				Toast.makeText(getApplicationContext(), "First Violation For This Plate", Toast.LENGTH_LONG).show();
				Changed = true;
			}
			else if (WorkingStorage.GetCharVal(Defines.ClientName,	getApplicationContext()).equals("ELLENSBURG")
					&& VBootReason > 0
					&& (TempViolCode.equals("010") || TempViolCode.equals("028")))
			{
				// ##### JRB - ELLENSBURG MOD #####
				// Only affects Violations 010 or 028
				String Reason = String.valueOf(VBootReason);
				if (VBootReason < 10) {
					// If less than 10, then first digit in VBoot string was 0
					// Put it back in
					Reason = "0" + Reason;
				}
				Integer PrevViolCnt = 0;
				// 2-Character VBoot Values
				if (TempViolCode.equals("010")) {
					// Get for VBoot Value for 010-type citations (1st Character)
					PrevViolCnt = Integer.valueOf(Reason.substring(0,1));
				} else {
					// Get for VBoot Value for 028-type citations (2nd Character)
					PrevViolCnt = Integer.valueOf(Reason.substring(1,2));
				}

				if (TempViolCode.equals("010") && PrevViolCnt > 0) {
					// Current Violation 010
					String NewViolationPrefix = "1";
					TempViolCode = NewViolationPrefix + TempViolCode.substring(1);
					Changed = true;
				} else {
					// Current Violation 028
					if (PrevViolCnt >= 1) {
						if (PrevViolCnt == 1) {
							String NewViolationPrefix = "1";
							TempViolCode = NewViolationPrefix + TempViolCode.substring(1);
						} else {
							String NewViolationPrefix = "2";
							TempViolCode = NewViolationPrefix + TempViolCode.substring(1);
						}
						Changed = true;
					}
				}
				if (Changed) {
					Toast.makeText(getApplicationContext(), "Violation Escalated For This Plate", Toast.LENGTH_LONG).show();
				}
			}
			else if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).equals("PHXAIRPORT")
					&& (TempViolCode.substring(1, 2).equals("4")
					|| TempViolCode.substring(1, 2).equals("5")))
			{
				if (VBootReason == 0)
					TempViolCode = NotFoundCode + TempViolCode.substring(1);
				if (VBootReason == 1)
					TempViolCode = FoundLevel1 + TempViolCode.substring(1);
				if (VBootReason == 2)
					TempViolCode = FoundLevel2 + TempViolCode.substring(1);
				if (VBootReason == 3)
					TempViolCode = FoundLevel3 + TempViolCode.substring(1);
				if (VBootReason == 4)
					TempViolCode = FoundLevel4 + TempViolCode.substring(1);
				if (VBootReason == 5)
					TempViolCode = FoundLevel5 + TempViolCode.substring(1);
				if (VBootReason >= 6)
					TempViolCode = FoundLevel6 + TempViolCode.substring(1);

				Changed = true;
			}
		} // FROM THE MAIN ELSE

		if (Changed == true) {
			//String ViolateString = SearchData.FindRecordLine(TempViolCode, TempViolCode.length(),"VIOLATE.A", getApplicationContext());
			String ViolateString = SearchData.FindRecordLine(TempViolCode, TempViolCode.length(),ViolateFilename, getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.EntireDataString,
					ViolateString, getApplicationContext());

			EditText violateText = (EditText) findViewById(R.id.editTextViolate);
			TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
			tvDesc1.setText(ViolateString.substring(44, 69));
			TextView tvDesc2 = (TextView) findViewById(R.id.TextViewDesc2);
			tvDesc2.setText(ViolateString.substring(69, 94));
			TextView tvOrdinance = (TextView) findViewById(R.id.textViewOrdinance);
			tvOrdinance.setText(ViolateString.substring(3, 28));
			WorkingStorage.StoreCharVal(Defines.RotateValue,
					ViolateString.substring(0, 3), getApplicationContext());
			violateText.setText(ViolateString.substring(0, 3));
		}
	}

	  /*@Override
	    protected void onPause() {
	        super.onPause();
      	Defines.clsClassName = SelFuncForm.class ;
      	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
      	startActivityForResult(myIntent, 0);
	        finish();
	        // The activity is about to be destroyed.
	    }*/

    /*@Override
    protected void onStop() {
        super.onStop();
    	Defines.clsClassName = SelFuncForm.class ;
    	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
    	startActivityForResult(myIntent, 0);
        finish();
 		overridePendingTransition(0, 0);
    }*/
}