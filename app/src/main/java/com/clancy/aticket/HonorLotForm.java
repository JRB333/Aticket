package com.clancy.aticket;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class HonorLotForm extends ActivityGroup {
	int CurrentSpace = 0;
	int MaxSpaces = 0;
	int StartSpaceNumber = 1;
	int CurrentPass = 0;
	Boolean ShowOptions  = true;
	Boolean LetterCounter  = false;
	Button RePrintTicket;

	private void EnterClick()
	{
		WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "NO", getApplicationContext());
		StampEndTime(getApplicationContext());
		Defines.clsClassName = HBoxUploadForm.class ;
		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		startActivityForResult(myIntent, 0);
		finish();
		overridePendingTransition(0, 0);
	}

	private void RePrintClick() {
		WorkingStorage.StoreCharVal(Defines.ReprintHonorTicket, "YES", getApplicationContext());

		WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "DONE", getApplicationContext());
		Defines.clsClassName = PrintSelectForm.class ;
		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		startActivityForResult(myIntent, 0);
		finish();
		overridePendingTransition(0, 0);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.honorlotform);

		GetDate.GetDateTime(getApplicationContext());
		MaxSpaces = WorkingStorage.GetLongVal(Defines.NumberOfSpacesVal, getApplicationContext());
		StartSpaceNumber = WorkingStorage.GetLongVal(Defines.NumStartSpaceVal, getApplicationContext());

		if( WorkingStorage.GetCharVal(Defines.HBoxFlowVal, getApplicationContext()).equals("DONE"))
		{
			CurrentSpace = StartSpaceNumber;
		}
		else
		{
			CurrentSpace = WorkingStorage.GetLongVal(Defines.HBoxCurrentSpaceVal, getApplicationContext());
		}

		if( WorkingStorage.GetLongVal(Defines.EditHonorBoxVal, getApplicationContext()) > 0)
		{
			CurrentPass = WorkingStorage.GetLongVal(Defines.EditHonorBoxVal, getApplicationContext()) - 1;
		}
		else
		{
			CurrentPass = IOHonorFile.HowManyPasses("", getApplicationContext()) - 1;
		}

		if (WorkingStorage.GetCharVal(Defines.HBoxReasonVal, getApplicationContext()).equals("M") ||
				WorkingStorage.GetCharVal(Defines.HBoxReasonVal, getApplicationContext()).equals("O") ||
				WorkingStorage.GetCharVal(Defines.HBoxReasonVal, getApplicationContext()).equals("B") ||
				WorkingStorage.GetCharVal(Defines.HBoxReasonVal, getApplicationContext()).equals("Z") ||
				WorkingStorage.GetCharVal(Defines.HBoxReasonVal, getApplicationContext()).equals("1") )
		{
			StoreNoTicketReason(WorkingStorage.GetCharVal(Defines.HBoxReasonVal, getApplicationContext()), getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.HBoxReasonVal, "", getApplicationContext());
		}

		if (GetLotPlateNumber(getApplicationContext()).equals(false))
		{
			//MsgBox("Problem with chip.")
			Defines.clsClassName = SelFuncForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}

		TextView tvLot = (TextView) findViewById(R.id.textViewLotName);
		tvLot.setText(WorkingStorage.GetCharVal(Defines.SaveHBoxLotVal, getApplicationContext()));

		TextView tvTime = (TextView) findViewById(R.id.textViewTime);
		tvTime.setText(WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()));

		final Button bGo = (Button) findViewById(R.id.ButtonGo);
		final EditText Plate = (EditText) findViewById(R.id.editTextPlate);
		final EditText Number = (EditText) findViewById(R.id.editTextNumber);
		final Button Three = (Button) findViewById(R.id.buttonPlateThree);

		Three.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view)
			{
				RePrintTicket.setVisibility(View.INVISIBLE);
				RePrintTicket.setEnabled(false);

				CustomVibrate.VibrateButton(getApplicationContext());
				SaveSpaceInformation(Three.getText().toString().trim(), getApplicationContext());
				WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());
				if (CurrentSpace == (MaxSpaces + StartSpaceNumber - 1))
				{
					CurrentSpace = StartSpaceNumber;
				}
				else
				{
					CurrentSpace = CurrentSpace + 1 ;
				}
				GetLotPlateNumber(getApplicationContext());

				if (ShowOptions.equals(true)) //SaveSpaceInformation routine determined that ticket prompt is needed
				{
					WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());
					WorkingStorage.StoreLongVal(Defines.HBoxCurrentSpaceVal, CurrentSpace, getApplicationContext());

					Defines.clsClassName = TicketPromptForm.class ;
					Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				}
			}
		});

		final Button Clear = (Button) findViewById(R.id.buttonClear);
		Clear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view)
			{
				WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());
				RePrintTicket.setVisibility(View.INVISIBLE);
				RePrintTicket.setEnabled(false);

				CustomVibrate.VibrateButton(getApplicationContext());
				Plate.setText("");
				Plate.requestFocus();
				SaveSpaceInformation("   ", getApplicationContext());
			}
		});

		final Button Previous = (Button) findViewById(R.id.buttonPrevious);
		Previous.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());
				RePrintTicket.setVisibility(View.INVISIBLE);
				RePrintTicket.setEnabled(false);

				if (CurrentSpace == StartSpaceNumber)
				{
					CurrentSpace = (MaxSpaces + StartSpaceNumber - 1);
				} else {
					CurrentSpace = CurrentSpace - 1;
				}
				GetLotPlateNumber(getApplicationContext());
			}
		});

		final Button Next = (Button) findViewById(R.id.ButtonNext);
		Next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());
				RePrintTicket.setVisibility(View.INVISIBLE);
				RePrintTicket.setEnabled(false);

				if (CurrentSpace == (MaxSpaces + StartSpaceNumber - 1))
				{
					CurrentSpace = StartSpaceNumber;
				} else {
					CurrentSpace = CurrentSpace + 1;
				}
				GetLotPlateNumber(getApplicationContext());
			}
		});

		RePrintTicket = (Button) findViewById(R.id.buttonRePrintTicket);
		if (WorkingStorage.GetCharVal(Defines.HonorTicketIssued, getApplicationContext()).toString().equals("YES")) {
			RePrintTicket.setVisibility(View.VISIBLE);
			RePrintTicket.setEnabled(true);
		} else {
			WorkingStorage.StoreCharVal(Defines.ReprintHonorTicket, "", getApplicationContext());
			RePrintTicket.setVisibility(View.INVISIBLE);
			RePrintTicket.setEnabled(false);
		}
		RePrintTicket.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// ##### JRB #####
				RePrintClick();
			}
		});

		Plate.selectAll();
		Plate.requestFocus();
		Plate.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					bGo.setVisibility(View.INVISIBLE);
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
					Plate.selectAll();
				}else {
					bGo.setVisibility(View.VISIBLE);
				}
			}
		});

		Plate.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(Plate.getText().length() == 3)
				{
					if (LetterCounter.equals(true))
					{
						ButtonStore(getApplicationContext());
					}
					LetterCounter = false;
				}

				if(Plate.getText().length() == 2)
				{
					LetterCounter = true;
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub
			}
		});

		bGo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CustomVibrate.VibrateButton(getApplicationContext());
				String SpaceVal = Number.getText().toString().trim();
				Double dSpaceVal = Double.valueOf(SpaceVal);
				int iMax = (MaxSpaces + StartSpaceNumber - 1);

				if (dSpaceVal > (MaxSpaces + StartSpaceNumber - 1))
				{
					Messagebox.Message("Maximum space for this lot is " + Integer.toString(iMax), getApplicationContext());
					Number.setText(Integer.toString(iMax));
					CurrentSpace = MaxSpaces + StartSpaceNumber - 1;
				}
				else if (dSpaceVal < StartSpaceNumber)
				{
					Messagebox.Message("Minimum space for this lot is " + Integer.toString(StartSpaceNumber) , getApplicationContext());
					Number.setText(Integer.toString(StartSpaceNumber));
					CurrentSpace = StartSpaceNumber;
				}
				else
				{
					CurrentSpace = Integer.valueOf(SpaceVal);
				}

				//Plate.selectAll();
				Plate.requestFocus();
				GetLotPlateNumber(getApplicationContext());
				//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			}
		});

		bGo.setVisibility(View.INVISIBLE);

		Button done = (Button) findViewById(R.id.buttonDONE);
		done.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				CustomVibrate.VibrateButton(getApplicationContext());
				//EnterClick();
				PopIt("Lot Check", "Lot Check Complete?");
			}
		});

		EditText plate = (EditText) findViewById(R.id.editTextPlate);
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
		plate.requestFocus();
	}

	public void PopIt( String title, String message ){
		new AlertDialog.Builder(this)
				.setTitle( title )
				.setMessage( message )
				.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1)
					{
						WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());
						RePrintTicket.setVisibility(View.INVISIBLE);
						RePrintTicket.setEnabled(false);

						EnterClick();
					}
				})
				.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1)
					{
					}
				}).show();
	}

	public void ButtonStore(Context cntxt)
	{
		EditText Plate = (EditText) findViewById(R.id.editTextPlate);
		if (!Plate.getText().toString().trim().equals(""))
		{
			String StorePlate = Plate.getText().toString().trim();
			while (StorePlate.length() < 3)
				StorePlate = StorePlate + " ";
			SaveSpaceInformation(StorePlate, getApplicationContext());
		}

		if (CurrentSpace == (MaxSpaces + StartSpaceNumber - 1))
		{
			CurrentSpace = StartSpaceNumber;
		}
		else
		{
			CurrentSpace = CurrentSpace + 1;
		}

		if (ShowOptions.equals(true)) //SaveSpaceInformation routine determined that ticket prompt is needed
		{
			WorkingStorage.StoreLongVal(Defines.HBoxCurrentSpaceVal, CurrentSpace, getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "", getApplicationContext());
			RePrintTicket.setVisibility(View.INVISIBLE);
			RePrintTicket.setEnabled(false);

			Defines.clsClassName = TicketPromptForm.class ;
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			startActivityForResult(myIntent, 0);
			finish();
			overridePendingTransition(0, 0);
		}
		else
		{
			GetLotPlateNumber(getApplicationContext());
		}
	}

	public void SaveSpaceInformation(String NewPlateNumber, Context cntxt)
	{
		int HowFarOver = 0;
		String PlateStatus = "";
		String cStatus = "";

		HowFarOver = (CurrentPass * 8) + 15;
		HowFarOver = ((CurrentSpace - StartSpaceNumber + 1) * 177) + HowFarOver;

		cStatus = CheckDollarAmount(HowFarOver + 4, NewPlateNumber,getApplicationContext());
		PlateStatus = NewPlateNumber;
		while (PlateStatus.length() < 3)
			PlateStatus = PlateStatus + " ";
		PlateStatus = PlateStatus + cStatus;

		WorkingStorage.StoreLongVal(Defines.NoTicketReasonSpaceVal, CurrentSpace, getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.HBoxMeterVal, Integer.toString(CurrentSpace), getApplicationContext());
		IOHonorFile.WriteVirtualFile(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), PlateStatus, HowFarOver, getApplicationContext());

		if (cStatus.equals("T"))
		{
			ShowOptions = true;
		}
		else
		{
			ShowOptions = false;
		}
	}

	public String CheckDollarAmount(int MemoryLocation, String Platenumber, Context cntxt)
	{
		String CMoney = "";
		String cStatus = "";
		double SpaceValue  = 0;
		double TwentyMinValue = 0;
		double HalfValue = 0;
		double FortyMinValue = 0;
		double HourValue = 0;
		double EightyMinValue = 0;
		double NinetyMinValue = 0;
		double OneHundredMinValue = 0;
		double TwoHoursValue = 0;
		double ThreeHoursValue = 0;
		double FourHoursValue = 0;
		double FiveHoursValue = 0;
		double SixHoursValue = 0;
		double SevenHoursValue = 0;
		double EightHoursValue = 0;
		double NineHoursValue = 0;
		double DayValue = 0;
		double EBirdValue = 0;
		String RecNumBuffer = "";

		RecNumBuffer = SearchData.GetRecordNumberNoLength(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), CurrentSpace + 1 - (StartSpaceNumber - 1), getApplicationContext());
		CMoney = RecNumBuffer.substring((CurrentPass * 8) + 19, (CurrentPass * 8) + 19 + 4);
		SpaceValue = Double.valueOf(CMoney)/100;

		if (Platenumber.trim().equals(""))
		{
			if (SpaceValue > 0)
			{
				return "$";
			}
			else
			{
				return "E";
			}
		}

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox20MinAmtVal, getApplicationContext()).trim().equals(""))
			TwentyMinValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox20MinAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBoxHalfAmtVal, getApplicationContext()).trim().equals(""))
			HalfValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBoxHalfAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox40MinAmtVal, getApplicationContext()).trim().equals(""))
			FortyMinValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox40MinAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBoxHourAmtVal, getApplicationContext()).trim().equals(""))
			HourValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBoxHourAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox80MinAmtVal, getApplicationContext()).trim().equals(""))
			EightyMinValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox80MinAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox90MinAmtVal, getApplicationContext()).trim().equals(""))
			NinetyMinValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox90MinAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox100MinAmtVal, getApplicationContext()).trim().equals(""))
			OneHundredMinValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox100MinAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox2HoursAmtVal, getApplicationContext()).trim().equals(""))
			TwoHoursValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox2HoursAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox3HoursAmtVal, getApplicationContext()).trim().equals(""))
			ThreeHoursValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox3HoursAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox4HoursAmtVal, getApplicationContext()).trim().equals(""))
			FourHoursValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox4HoursAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox5HoursAmtVal, getApplicationContext()).trim().equals(""))
			FiveHoursValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox5HoursAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox6HoursAmtVal, getApplicationContext()).trim().equals(""))
			SixHoursValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox6HoursAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox7HoursAmtVal, getApplicationContext()).trim().equals(""))
			SevenHoursValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox7HoursAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox8HoursAmtVal, getApplicationContext()).trim().equals(""))
			EightHoursValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox8HoursAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBox9HoursAmtVal, getApplicationContext()).trim().equals(""))
			NineHoursValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBox9HoursAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBoxDayAmtVal, getApplicationContext()).trim().equals(""))
			DayValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBoxDayAmtVal, getApplicationContext()));

		if (!WorkingStorage.GetCharVal(Defines.SaveHBoxEBirdAmtVal, getApplicationContext()).trim().equals(""))
			EBirdValue = Double.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBoxEBirdAmtVal, getApplicationContext()));

		if (SpaceValue >= DayValue && DayValue > 0)
			cStatus = "D";
		else if (SpaceValue >= NineHoursValue && NineHoursValue > 0)
			cStatus = "9";
		else if (SpaceValue >= EightHoursValue && EightHoursValue > 0)
			cStatus = "8";
		else if (SpaceValue >= SevenHoursValue && SevenHoursValue > 0)
			cStatus = "7";
		else if (SpaceValue >= SixHoursValue && SixHoursValue > 0)
			cStatus = "6";
		else if (SpaceValue >= FiveHoursValue && FiveHoursValue > 0)
			cStatus = "5";
		else if (SpaceValue >= FourHoursValue && FourHoursValue > 0)
			cStatus = "4";
		else if (SpaceValue >= ThreeHoursValue && ThreeHoursValue > 0)
			cStatus = "3";
		else if (SpaceValue >= TwoHoursValue && TwoHoursValue > 0)
			cStatus = "2";
		else if (SpaceValue >= OneHundredMinValue && OneHundredMinValue > 0)
			cStatus = "Q";
		else if (SpaceValue >= NinetyMinValue && NinetyMinValue > 0)
			cStatus = "J";
		else if (SpaceValue >= EightyMinValue && EightyMinValue > 0)
			cStatus = "K";
		else if (SpaceValue >= HourValue && HourValue > 0)
			cStatus = "H";
		else if (SpaceValue >= FortyMinValue && FortyMinValue > 0)
			cStatus = "V";
		else if (SpaceValue >= HalfValue && HalfValue > 0)
			cStatus = "A";
		else if (SpaceValue >= TwentyMinValue && TwentyMinValue > 0)
			cStatus = "W";
		else
			cStatus = "T";

		if (cStatus.equals("T") && CurrentPass >= 1)
		{
			String TimeRecNumBuffer = "";
			TimeRecNumBuffer = SearchData.GetRecordNumberNoLength(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), 1, getApplicationContext());
			String RecNumberBuffer = "";
			RecNumberBuffer = SearchData.GetRecordNumberNoLength(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), (CurrentSpace - StartSpaceNumber + 1), getApplicationContext());
			int i = 0;
			String PriorBuffer = "";
			String StampTime = "";

			for(i = CurrentPass; i >= 1; i--)
			{
				PriorBuffer = RecNumBuffer.substring(((i - 1) * 8) + 15, ((i - 1) * 8) + 15 + 4);
				if( PriorBuffer.substring(0, 3).equals(Platenumber))
				{
					CMoney = RecNumBuffer.substring(((i - 1) * 8) + 19,((i - 1) * 8) + 19 + 4);
					SpaceValue = SpaceValue + (Double.valueOf(CMoney) / 100);  // keep running count of total payments
					StampTime = TimeRecNumBuffer.substring(((i - 1) * 8) + 15, ((i - 1) * 8) + 15 + 4);

					if( PriorBuffer.substring(3, 4).equals("T")) //  T is ticketed
					{
						return "S";
					}

					if( PriorBuffer.substring(3, 4).equals("D")) //  Paid already don't ticket
					{
						return "P";
					}

					if( PriorBuffer.substring(3, 4).equals("M")) //  Monthly Parker
					{
						return PriorBuffer.substring(3, 4);
					}

					if( PriorBuffer.substring(3, 4).equals("O")) //  no ticket OTHER reason
					{
						return PriorBuffer.substring(3, 4);
					}

					if( PriorBuffer.substring(3, 4).equals("Z")) //  no ticket Valet reason
					{
						return PriorBuffer.substring(3, 4);
					}

					if( PriorBuffer.substring(3, 4).equals("1")) //   Misc Permit
					{
						return PriorBuffer.substring(3, 4);
					}

					if( PriorBuffer.substring(3, 4).equals("B")) //   Park-by-phone
					{
						return PriorBuffer.substring(3, 4);
					}
				}
			}

			if( SpaceValue > 0 && !StampTime.trim().equals(""))// check rates against total time elapsed
			{
				Double Minutes = 0.00;
				Double TempCalc = 0.00;
				Minutes = GetTimeElapsed(StampTime);

				if (NineHoursValue > 0 && SpaceValue >= NineHoursValue)
				{
					TempCalc = (Minutes / 540) * NineHoursValue;
					if (TempCalc > SpaceValue)// Check 9 Hours total
					{
						cStatus = "T";   // Ticket Vehicle time elapsed
					}
					else
					{
						cStatus = "L";    // Time Left, don't ticket
					}
				}
				else
				{
					cStatus = "T";  // Set Ticket flag to check all other rates.
				}

				if (cStatus.equals("T") && EightHoursValue > 0 && SpaceValue >= EightHoursValue) // 9 Hours Failed, check 8 Hours
				{
					TempCalc = (Minutes / 480) * EightHoursValue;
					if (TempCalc > SpaceValue) // Check 8 Hour total
						cStatus = "T";      // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && SevenHoursValue > 0 && SpaceValue >= SevenHoursValue) // 8 Hours Failed, check 7 Hours
				{
					TempCalc = (Minutes / 420) * SevenHoursValue;
					if (TempCalc > SpaceValue)// Check 7 Hour total
						cStatus = "T";   // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && SixHoursValue > 0 && SpaceValue >= SixHoursValue) // 7 Hours Failed, check 6 Hours
				{
					TempCalc = (Minutes / 360) * SixHoursValue;
					if (TempCalc > SpaceValue)// Check 6 Hour total
						cStatus = "T";       // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && FiveHoursValue > 0 && SpaceValue >= FiveHoursValue) // 6 Hours Failed, check 5 Hours
				{
					TempCalc = (Minutes / 300) * FiveHoursValue;
					if (TempCalc > SpaceValue) // Check 5 Hour total
						cStatus = "T";      // Ticket Vehicle time elapsed
					else
						cStatus = "L"; // Time Left, don't ticket
				}

				if (cStatus.equals("T") && FourHoursValue > 0 && SpaceValue >= FourHoursValue) // 5 Hours Failed, check 4 Hours
				{
					TempCalc = (Minutes / 240) * FourHoursValue;
					if (TempCalc > SpaceValue)// Check 4 Hour total
						cStatus = "T";       // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && ThreeHoursValue > 0 && SpaceValue >= ThreeHoursValue) // 4 Hours Failed, check 3 Hours
				{
					TempCalc = (Minutes / 180) * ThreeHoursValue;
					if (TempCalc > SpaceValue) // Check 3 Hour total
						cStatus = "T";       // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && TwoHoursValue > 0 && SpaceValue >= TwoHoursValue)// 3 Hours Failed, check 2 Hours
				{
					TempCalc = (Minutes / 120) * TwoHoursValue;
					if (TempCalc > SpaceValue) // Check 2 Hour total
						cStatus = "T";      // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && OneHundredMinValue > 0 && SpaceValue >= OneHundredMinValue) // 2 Hours Failed, check 100 Minutes
				{
					TempCalc = (Minutes / 100) * OneHundredMinValue;
					if (TempCalc > SpaceValue)// Check 100 Minutes total
						cStatus = "T"; // Ticket Vehicle time elapsed
					else
						cStatus = "L"; //Time Left, don't ticket
				}

				if (cStatus.equals("T") && NinetyMinValue > 0 && SpaceValue >= NinetyMinValue) // 100 Minutes Failed, check 90 Minutes
				{
					TempCalc = (Minutes / 90) * NinetyMinValue;
					if (TempCalc > SpaceValue) // Check 90 Minutes total
						cStatus = "T";      // Ticket Vehicle time elapsed
					else
						cStatus = "L";	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && EightyMinValue > 0 && SpaceValue >= EightyMinValue) // 90 Minutes Failed, check 80 Minutes
				{
					TempCalc = (Minutes / 80) * EightyMinValue;
					if (TempCalc > SpaceValue) // Check 80 Minutes total
						cStatus = "T";     // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && HourValue > 0 && SpaceValue >= HourValue) // 80 Minutes Failed, check 60 Minutes
				{
					TempCalc = (Minutes / 60) * HourValue;
					if (TempCalc > SpaceValue) // Check 60 Minutes total
						cStatus = "T";      // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && FortyMinValue > 0 && SpaceValue >= FortyMinValue) // 60 Minutes Failed, check 40 Minutes
				{
					TempCalc = (Minutes / 40) * FortyMinValue;
					if (TempCalc > SpaceValue) // Check 40 Minutes total
						cStatus = "T";       // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && HalfValue > 0 && SpaceValue >= HalfValue) // 40 Minutes Failed, check 30 Minutes
				{
					TempCalc = (Minutes / 30) * HalfValue;
					if (TempCalc > SpaceValue) // Check 30 Minutes total
						cStatus = "T";       // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}

				if (cStatus.equals("T") && TwentyMinValue > 0 && SpaceValue >= TwentyMinValue) // 30 Minutes Failed, check 20 Minutes
				{
					TempCalc = (Minutes / 20) * TwentyMinValue;
					if (TempCalc > SpaceValue) // Check 20 Minutes total
						cStatus = "T";      // Ticket Vehicle time elapsed
					else
						cStatus = "L"; 	 // Time Left, don't ticket
				}
			}
		}
		CMoney = "";
		double dTemp = DayValue - SpaceValue;
		dTemp = Math.round(dTemp * 100);
		dTemp =dTemp/100;
		CMoney = String.format("%.2f", dTemp);

		WorkingStorage.StoreCharVal(Defines.PrintFeeVal, CMoney, getApplicationContext());

		return cStatus;
	}

	public double GetTimeElapsed(String StampTime )
	{
		String CurrentPassTime = "";
		String TempBuffer = "";
		double StampHour = 0.00;
		double CurrentHour = 0.00;
		double StampMinute = 0.00;
		double CurrentMinute = 0.00;
		double TotalMinutes = 0.00;

		CurrentPassTime = WorkingStorage.GetCharVal(Defines.CurrentPassTimeVal, getApplicationContext());

		StampHour = Double.valueOf(StampTime.substring(0, 2));
		StampMinute = Double.valueOf(StampTime.substring(2, 4));

		CurrentHour = Double.valueOf(CurrentPassTime.substring(0, 2));
		CurrentMinute = Double.valueOf(CurrentPassTime.substring(2, 4));

		TotalMinutes = 0;

		if (StampHour > CurrentHour || (StampHour == CurrentHour && StampMinute > CurrentMinute))
		{
			return 1;
		}
		if (CurrentMinute >= StampMinute)
		{
			TotalMinutes = (CurrentHour - StampHour) * 60;
			TotalMinutes = TotalMinutes + (CurrentMinute - StampMinute);
		}
		else
		{
			TotalMinutes = (CurrentHour - StampHour - 1) * 60;
			TotalMinutes = TotalMinutes + ((CurrentMinute + 60) - StampMinute);
		}

		return TotalMinutes;
	}

	public Boolean StampEndTime(Context cntxt)
	{
		int HowFarOver = 0;
		GetDate.GetDateTime(getApplicationContext());
		CurrentPass = IOHonorFile.HowManyPasses("", cntxt);
		if (CurrentPass == 20)
		{
			return false;
		}
		HowFarOver = ((CurrentPass - 1)* 8) + 19;
		IOHonorFile.WriteVirtualFile(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext()), HowFarOver, getApplicationContext());
		return true;
	}

	public Boolean GetLotPlateNumber(Context cntxt)
	{
		String RecNumBuffer = "";
		LetterCounter = false;

		RecNumBuffer = SearchData.GetRecordNumberNoLength(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), CurrentSpace + 1 - (StartSpaceNumber - 1), getApplicationContext());
		if( RecNumBuffer.equals("ERROR"))
		{
			return false;
		}

		String SpaceBuffer = "";
		SpaceBuffer = RecNumBuffer.substring(11, 15);
		EditText Number = (EditText) findViewById(R.id.editTextNumber);
		Number.setText(SpaceBuffer);
		EditText Plate = (EditText) findViewById(R.id.editTextPlate);
		Plate.setText(RecNumBuffer.substring(((CurrentPass * 8) + 15), ((CurrentPass * 8) + 15)+3));
		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
		//Plate.setText("");
		Plate.selectAll();
		Button Three = (Button) findViewById(R.id.buttonPlateThree);

		if (CurrentPass >= 1)
		{
			Three.setText(  RecNumBuffer.substring(((CurrentPass - 1) * 8) + 15, ((CurrentPass - 1) * 8) + 15+3));

			if(Three.getText().toString().trim().equals(""))
			{
				Three.setVisibility(View.INVISIBLE);
			}
			else
			{
				Three.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			Three.setVisibility(View.INVISIBLE);
		}

		return true;
	}

	public void StoreNoTicketReason(String NoTicketReason, Context cntxt)
	{
		int HowFarOver = 0;
		HowFarOver = ((CurrentPass + 1) * 8) + 10;
		HowFarOver = ((WorkingStorage.GetLongVal(Defines.NoTicketReasonSpaceVal,getApplicationContext()) - StartSpaceNumber + 1) * 177) + HowFarOver;

		IOHonorFile.WriteVirtualFile(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), NoTicketReason, HowFarOver, getApplicationContext());
	}
}