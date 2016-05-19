package com.clancy.aticket;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class SurveyPassDataForm extends ActivityGroup {
    Boolean initialDisplay = true;

    private String PriorStreet = "";
    private String PriorPlate = "";
    private String PriorState = "";
    private String ElapsedHrMin = "";
    private String ThisRoute = "";
    private String ThisSpaceAllData = "";
    private String TimeStr = "";
    private String ItemCntrStr = "";
    private Integer StartEntryHr = 0;
    private Integer StartEntryMin = 0;
    private String[] CycleItems = null;
    private Integer ItemsRetrieved = -1;
    private Integer ItemCounter = 0;
    private Boolean EscFlag = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveypassdataform);
        EscFlag = false;

        // Get All Street-Meter items for this Route
        ItemsRetrieved = Integer.valueOf(WorkingStorage.GetCharVal(Defines.ItemsRetrieved, getApplicationContext()));

        GetCycleItems();

        final Spinner SpinState = (Spinner) findViewById(R.id.spinEnterState);
        SpinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!initialDisplay) {
                    // your code here
                    String chkState = SpinState.getSelectedItem().toString().trim().substring(0, 2);
                    FindState(chkState);
                }
                initialDisplay = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                if (!initialDisplay) {
                    // your code here
                }
                initialDisplay = false;
            }
        });

        Button btnEsc = (Button) findViewById(R.id.btnESC);
        btnEsc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EscFlag = true;
                EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
                String NewPlate = Plate.getText().toString().toUpperCase().trim();
                if (NewPlate.length() > 0) {
                    // Save off New Route Name/Number
                    SaveOffData();
                }

                PassCompleteDialog();
            }
        });

        Date today = Calendar.getInstance().getTime();
        StartEntryHr = today.getHours();
        StartEntryMin = today.getMinutes();

        Button btnPassComplete = (Button) findViewById(R.id.btnPassComplete);
        btnPassComplete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EscFlag = false;

                Date today = Calendar.getInstance().getTime();
                Integer EndEntryHr = today.getHours();
                Integer EndEntryMin = today.getMinutes();

                Integer ElapsedSeconds = ((EndEntryHr * 3600) + (EndEntryMin * 60))
                        - ((StartEntryHr * 3600) + (StartEntryMin * 60));

                /*if (ElapsedSeconds >= 120) {
                    EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
                    String NewPlate = Plate.getText().toString().toUpperCase().trim();
                    if (NewPlate.length() > 0) {
                        SaveOffData();
                    }
                }*/

                EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
                String NewPlate = Plate.getText().toString().toUpperCase().trim();
                if (NewPlate.length() > 0) {
                    SaveOffData();
                }

                PassCompleteDialog();
            }
        });

        Button btnIssueTicket = (Button) findViewById(R.id.btnIssueTicket);
        btnIssueTicket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextView LastPlate = (TextView) findViewById(R.id.txtPriorPlate);
                PriorPlate = LastPlate.getText().toString().toUpperCase().trim();

                if (PriorPlate.length() > 0) {
                    // Save Off For Probable RESUME of Survey
                    WorkingStorage.StoreCharVal(Defines.ReturnToSurvey, "TRUE", getApplicationContext());
                    WorkingStorage.StoreCharVal(Defines.ReturnToChalk, "", getApplicationContext());

                    String ResumeRoute = WorkingStorage.GetCharVal(Defines.RouteName, getApplicationContext());
                    ;
                    WorkingStorage.StoreCharVal(Defines.ResumeRoute, ResumeRoute, getApplicationContext());

                    TextView LastStreet = (TextView) findViewById(R.id.txtStreet);
                    String ThisStreet = LastStreet.getText().toString().toUpperCase().trim();
                    //WorkingStorage.StoreCharVal(Defines.ResumeStreet, ThisStreet, getApplicationContext());

                    TextView LastMeter = (TextView) findViewById(R.id.txtSpaceMeter);
                    String ThisMeter = LastMeter.getText().toString().toUpperCase().trim();
                    WorkingStorage.StoreCharVal(Defines.TxfrSpace, ThisMeter, getApplicationContext());

                    /*Date today = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
                    String ThisTime = formatter1.format(today);
                    WorkingStorage.StoreCharVal(Defines.TxfrChalkTime, ThisTime, getApplicationContext());*/

                    TextView PrevTime = (TextView) findViewById(R.id.txtFirstRecordedTime);
                    String FirstTimeStr = PrevTime.getText().toString();
                    WorkingStorage.StoreCharVal(Defines.TxfrChalkTime, FirstTimeStr, getApplicationContext());

                    // Set Up for Ticketing
                    // Issue Ticket For This Plate
                    WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, PriorPlate, getApplicationContext());
                    WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, ThisStreet, getApplicationContext());
                    WorkingStorage.StoreCharVal(Defines.TxfrStateVal, PriorState, getApplicationContext());

                    WorkingStorage.StoreCharVal(Defines.MenuProgramVal, Defines.TicketIssuanceMenu, getApplicationContext());
                    WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0, getApplicationContext());
                    WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, 0, getApplicationContext());

                    Integer ItemIndexMax = CycleItems.length - 1;  // Item Index Starts at 0
                    String cResumeItemCounter = "-1";
                    if (ItemCounter < ItemIndexMax) {
                        cResumeItemCounter = String.valueOf(ItemCounter);
                    }
                    WorkingStorage.StoreCharVal(Defines.ResumeItemCounter, cResumeItemCounter, getApplicationContext());

                    if (ProgramFlow.GetNextForm("", getApplicationContext()) != "ERROR") {
                        // --- Save Off This Plate Info As if Entered ---
                        TextView PriorPlate = (TextView) findViewById(R.id.txtPriorPlate);
                        String cPriorPlate = PriorPlate.getText().toString();
                        EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
                        Plate.setText(cPriorPlate);
                        String NewPlate = Plate.getText().toString().toUpperCase().trim();
                        if (NewPlate.length() == 0) {
                            // Empty Parking Meter Plates recorded as Dash String
                            String DashStr = "-";
                            Character DashChar = DashStr.charAt(0);
                            NewPlate = StringUtilities.rightPad(NewPlate.trim(), 8, DashChar);
                            Plate.setText(NewPlate);
                        }

                        // --- Save Off This State Info As if Entered ---
                        // --- State Spinner Value to Prior State ---
                        FindState(PriorState);

                        SaveOffData();

                        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                        startActivityForResult(myIntent, 0);
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }
            }
        });

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
                String NewPlate = Plate.getText().toString().toUpperCase().trim();
                if (NewPlate.length() == 0) {
                    // Empty Parking Meter Plates recorded as Dash String
                    String DashStr = "-";
                    Character DashChar = DashStr.charAt(0);
                    NewPlate = StringUtilities.rightPad(NewPlate.trim(),8,DashChar);
                    Plate.setText(NewPlate);
                }
                SaveOffData();

                if (ItemCounter < (ItemsRetrieved - 1)) {
                    // Save Off Where in Street-Meter array you last did
                    WorkingStorage.StoreCharVal(Defines.PassItemCounter, ItemCounter.toString(), getApplicationContext());

                    // Now Go Back and do the next Street-Meter combination
                    /*Defines.clsClassName = SurveyPassDataForm.class;
                    Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                    startActivityForResult(myIntent, 0);
                    overridePendingTransition(0, 0);
                    finish();*/
                    DrawScreen();
                } else {
                    PassCompleteDialog();
                }
            }
        });

        Button btnPriorPlate = (Button) findViewById(R.id.btnPriorPlate);
        btnPriorPlate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Repeat Prior Plate
                EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
                Plate.setText(PriorPlate);
                FindState(PriorState);
            }
        });

        EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
        Plate.requestFocus();

        KeyboardView keyboardView = null;
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        CustomKeyboard.PickAKeyboard(Plate, "SURVEYKB", getApplicationContext(), keyboardView);
        //CustomKeyboard.PickAKeyboard(Plate, "FULL", getApplicationContext(), keyboardView);

        Plate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
        });

        DrawScreen();
    }

    protected void PassCompleteDialog() {
        Integer ItemIndexMax = CycleItems.length - 1;  // Item Index Starts at 0
        String cResumeItemCounter = "-1";
        if (ItemCounter < ItemIndexMax) {
            cResumeItemCounter = String.valueOf(ItemCounter);
        }
        WorkingStorage.StoreCharVal(Defines.PassItemCounter, cResumeItemCounter, getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.ResumeItemCounter, cResumeItemCounter, getApplicationContext());

        // setup a dialog window
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SurveyPassDataForm.this);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setTitle("Pass Completed...")
                .setMessage("Are you finished with this Pass?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WorkingStorage.StoreCharVal(Defines.PassItemCounter, "-1", getApplicationContext());
                        WorkingStorage.StoreCharVal(Defines.ResumeItemCounter, "-1", getApplicationContext());

                        // Finalize This Routes PassDataLog Record
                        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
                        Boolean Result = dbh.FinalizeRoutePass();

                        if (!EscFlag) {
                            // Route Finalized - Go Back and Start Over
                            Defines.clsClassName = SurveySelFuncForm.class;
                        } else {
                            // Came Here From ESCAPE Button
                            // Go Out To 'Main' Menu
                            Defines.clsClassName = SelFuncForm.class;
                        }
                        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                        startActivityForResult(myIntent, 0);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!EscFlag) {
                            // Came Here From PASS-COMPLETE Button
                            // Stay In GUI and Get New Entries
                            TextView txtStreet = (TextView) findViewById(R.id.txtStreet);
                            txtStreet.setText(WorkingStorage.GetCharVal(Defines.StreetName, getApplicationContext()));
                            TextView txtSpaceMeter = (TextView) findViewById(R.id.txtSpaceMeter);
                            txtSpaceMeter.setText(WorkingStorage.GetCharVal(Defines.MeterSpace, getApplicationContext()));
                            TextView txtPriorPlate = (TextView) findViewById(R.id.txtPriorPlate);
                            txtPriorPlate.setText(WorkingStorage.GetCharVal(Defines.SurveyPlate, getApplicationContext()));
                            EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
                            Plate.setText("");

                            // Now Go Back and do the next Street-Meter combination
                            Defines.clsClassName = SurveyPassDataForm.class;
                        } else {
                            // Came Here From ESCAPE Button
                            // Go Out To 'Main' Menu
                            Defines.clsClassName = SelFuncForm.class;
                        }
                        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                        startActivityForResult(myIntent, 0);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void DrawScreen() {
        TextView GUITitle = (TextView) findViewById(R.id.lblGUITitle);
        ThisRoute = WorkingStorage.GetCharVal(Defines.RouteName, getApplicationContext());;
        String PassCntr = WorkingStorage.GetCharVal(Defines.PassCounter, getApplicationContext());
        GUITitle.setText("Route: " + ThisRoute.trim() + " - Pass: " + PassCntr.trim());

        ItemCntrStr = WorkingStorage.GetCharVal(Defines.PassItemCounter, getApplicationContext());
        ItemCounter = Integer.valueOf(ItemCntrStr);
        Integer ResumeItemCntr = WorkingStorage.GetLongVal(Defines.ResumeItemCounter, getApplicationContext());
        ItemCounter = Math.max(ItemCounter,ResumeItemCntr);

        // Determine Which Street-Meter combination to work with
        Integer ItemIndexMax = CycleItems.length - 1;  // Item Index Starts at 0
        if (ItemCounter < ItemIndexMax) {
            // Go to Next Item in Array
            ItemCounter = ItemCounter + 1;
        }
        String CurrentStreetMeter = CycleItems[ItemCounter];

        CurrentStreetMeter = CurrentStreetMeter.replace("^","");
        Integer HashLoc = CurrentStreetMeter.indexOf("#");
        String CurrentStreet = CurrentStreetMeter.substring(0, HashLoc);
        String CurrentMeter = CurrentStreetMeter.substring(HashLoc + 1, CurrentStreetMeter.length());

        // Get Previous Plate, State, Time Data
        GetPreviousData(CurrentStreet, CurrentMeter);

        //initialDisplay = true;

        TextView txtStreet = (TextView) findViewById(R.id.txtStreet);
        txtStreet.setText(CurrentStreet);
        WorkingStorage.StoreCharVal(Defines.StreetName, CurrentStreet, getApplicationContext());
        PriorStreet = CurrentStreet;
        /*PriorStreet =  WorkingStorage.GetCharVal(Defines.StreetName, getApplicationContext());
        txtStreet.setText(PriorStreet);*/

        TextView txtSpaceMeter = (TextView) findViewById(R.id.txtSpaceMeter);
        txtSpaceMeter.setText(CurrentMeter);
        WorkingStorage.StoreCharVal(Defines.MeterSpace, CurrentMeter, getApplicationContext());
        //txtSpaceMeter.setText(WorkingStorage.GetCharVal(Defines.MeterSpace, getApplicationContext()));

        EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
        InputMethodManager imm1 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); imm1.hideSoftInputFromWindow(Plate.getWindowToken(), 0);

        TextView ShowState = (TextView) findViewById(R.id.txtState);

        Button btnIssueTicket = (Button) findViewById(R.id.btnIssueTicket);
        TextView LastPlate = (TextView) findViewById(R.id.txtPriorPlate);
        PriorPlate = LastPlate.getText().toString().toUpperCase().trim();
        Button btnPriorPlate = (Button) findViewById(R.id.btnPriorPlate);
        if (PriorPlate.equals("")) {
            btnPriorPlate.setVisibility(View.INVISIBLE);
            btnPriorPlate.setEnabled(false);
            btnIssueTicket.setVisibility(View.INVISIBLE);
            btnIssueTicket.setEnabled(false);
        } else {
            btnPriorPlate.setVisibility(View.VISIBLE);
            btnPriorPlate.setEnabled(true);
            btnIssueTicket.setVisibility(View.VISIBLE);
            btnIssueTicket.setEnabled(true);
        }

        final Spinner SpinState = (Spinner) findViewById(R.id.spinEnterState);
        String chkState = WorkingStorage.GetCharVal(Defines.DefaultState, getApplicationContext());
        if (!chkState.equals("")) {
            FindState(chkState);
        }

        clearTxfrData();

        Plate.requestFocus();
    }

    public void FindState(String FindThisState) {
        Spinner SpinState = (Spinner) findViewById(R.id.spinEnterState);
        String StateName = "";
        String[] States = getResources().getStringArray(R.array.state_array);
        int j = 0;
        while (States.length > j) {
            SpinState.getItemAtPosition(j);
            String ThisState = States[j].toString();
            String StateAbbrev = ThisState.substring(0,2);
            if (StateAbbrev.equals(FindThisState)) {
                StateName = ThisState.substring(4,ThisState.length()).trim();
                SpinState.setSelection(j);
                break;
            }
            j++;
        }

        if (!StateName.equals("")) {
            TextView ShowState = (TextView) findViewById(R.id.txtState);
            ShowState.setText(StateName);
        }
    }

    private void GetPreviousData(String ThisStreet, String ThisMeter) {
        // Create Empty ArrayList To Process Plate info (looking for First Occurrance
        ArrayList<String> PlateList = new ArrayList<String>();

        PriorPlate = "";
        PriorState = "";
        PriorStreet = "";
        ElapsedHrMin = "";
        String PriorTimeStr = "";
        String PriorDate = "";

        WorkingStorage.StoreCharVal(Defines.StreetName, ThisStreet, getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.MeterSpace, ThisMeter, getApplicationContext());

        // Get List of Existing Routes
        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
        String PassData = dbh.getStreetMeterPassData();

        String FirstRecordedTime = "";
        if (PassData.length() > 0) {
            // Parse Route Names to Build Spinner List
            ThisSpaceAllData = PassData;

            final String SPLIT_STR = "++";
            final StringTokenizer stToken = new StringTokenizer(PassData, SPLIT_STR);
            final String[] splitStr = new String[stToken.countTokens()];
            int index = 0;
            while(stToken.hasMoreElements()) {
                splitStr[index] = stToken.nextToken();
                PlateList.add(splitStr[index].toString());

                // If more to get, increment Index
                if (stToken.hasMoreElements()) {
                    index++;
                }
            }

            // Now Parse Out Previous Pass Data
            String FinalPassData = splitStr[index];
            PriorPlate = FinalPassData.substring(0,8);
            if (PriorPlate.contains("++++") || PriorPlate.contains("----")) {
                PriorPlate = "";
            }
            PriorState = FinalPassData.substring(8,10);
            PriorTimeStr = FinalPassData.substring(10,15);
            PriorDate = FinalPassData.substring(15,FinalPassData.length()).trim();

            // Now Find FIRST Recorded Time For Final PriorPlate
            if (PlateList.size() > 0 && !PriorPlate.equals("")) {
                for (int l = 0; l < PlateList.size(); l++) {
                    String ChkPlateData = PlateList.get(l);
                    String ChkPlate = ChkPlateData.substring(0,8).trim();
                    if (ChkPlate.equals(PriorPlate.trim())) {
                        if (FirstRecordedTime.equals("")) {
                            FirstRecordedTime =  ChkPlateData.substring(10,15);
                        }
                    } else {
                        FirstRecordedTime = "";
                    }
                }
            }
        }

        TextView PrevPlate = (TextView) findViewById(R.id.txtPriorPlate);
        PrevPlate.setText(PriorPlate);

        if (PriorPlate.equals("")) {
            // If No Prior Plate, Nothing to Issue Ticket against
            Button IssueTicket = (Button) findViewById(R.id.btnIssueTicket);
            IssueTicket.setEnabled(false);
            IssueTicket.setVisibility(View.INVISIBLE);
        }

        if (!FirstRecordedTime.equals("")) {
            ElapsedHrMin = CalcElapsedTime(FirstRecordedTime);

            PriorTimeStr = FirstRecordedTime;
        } else {
            // No Recorded Time For No Plate
            PriorTimeStr = "";
        }

        TextView FirstTime = (TextView) findViewById(R.id.txtFirstRecordedTime);
        FirstTime.setText(PriorTimeStr);
    }

    private void GetCycleItems() {
        WorkingStorage.StoreCharVal(Defines.RouteItemCount, "0", getApplicationContext());
        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
        //String ResultStr = dbh.getAllStreetMeter();
        String ResultStr = dbh.getRemainingStreetMeter();

        if (ResultStr.length() > 0) {
            // Parse Route Names to Build Spinner List
            final String SPLIT_STR = "^";
            final StringTokenizer stToken = new StringTokenizer(ResultStr, SPLIT_STR);
            CycleItems = new String[stToken.countTokens()];

            int index = 0;
            while(stToken.hasMoreElements()) {
                CycleItems[index++] = stToken.nextToken();
            }
            ItemsRetrieved = index;

            WorkingStorage.StoreCharVal(Defines.ItemsRetrieved, ItemsRetrieved.toString(), getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.RouteItemCount, ItemsRetrieved.toString(), getApplicationContext());
        }
    }

    private void SaveOffData() {
        // Pad Plate Number out Right to 8 Characters
        EditText Plate = (EditText) findViewById(R.id.editEnterPlate);
        String NewPlate = Plate.getText().toString().toUpperCase().trim();
        String PadStr = "";
        if (NewPlate.length() == 0) {
            PadStr = "-";
        } else {
            PadStr = " ";
        }
        Character PadChar = PadStr.charAt(0);
        NewPlate = com.clancy.aticket.StringUtilities.rightPad(NewPlate.trim(), 8, PadChar);
        WorkingStorage.StoreCharVal(Defines.SurveyPlate, NewPlate, getApplicationContext());

        // Get Plate's State
        Spinner SpinState = (Spinner) findViewById(R.id.spinEnterState);
        String NewState = SpinState.getSelectedItem().toString();
        NewState = NewState.substring(0,2);
        WorkingStorage.StoreCharVal(Defines.SurveyState, NewState, getApplicationContext());

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        String ThisTime = formatter1.format(today);
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
        String ThisDate = formatter2.format(today);

        String NewPassData = NewPlate + NewState + ThisTime + " " + ThisDate;

        // Save Off Where in Street-Meter array you last did
        WorkingStorage.StoreCharVal(Defines.PassItemCounter, ItemCounter.toString(), getApplicationContext());

        // Save Data to Route-specific File and Update the PassDataLog
        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
        Boolean Result = dbh.updateStreetMeterPassData(NewPassData);

        if (ItemCounter == (ItemsRetrieved - 1)) {
            WorkingStorage.StoreCharVal(Defines.PassItemCounter, "-1", getApplicationContext());
        }

        if (Result) {
            Plate.setText("");
        }
    }

    public void clearTxfrData() {
        WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrStateVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrNumVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrDirVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, "", getApplicationContext());
    }


    // Calculate Elapsed Time In Hours & Min
    // Between Passed Time and Now
    public String CalcElapsedTime(String FirstTime) {
        // First Time arrives as HH:MM
        // Determine Seconds for First Time
        Integer FirstTimeSecs = (Integer.valueOf(FirstTime.substring(0, 2)) * 3600)
                + (Integer.valueOf(FirstTime.substring(3,5)) * 60);
        // Determine Seconds for Now
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        String ThisTime = formatter1.format(today);
        Integer NowSecs = (Integer.valueOf(ThisTime.substring(0, 2)) * 3600)
                + (Integer.valueOf(ThisTime.substring(3,5)) * 60);
        // Determine Elapsed Seconds
        Integer Diff = NowSecs - FirstTimeSecs;
        // Build This Back into Elapsed Time String (HH MM)
        Integer nElapsedHrs = Diff/3600;
        Integer nElapsedMin = (Diff - (nElapsedHrs * 3600)) / 60;

        String ElapsedHrs = String.valueOf(nElapsedHrs);

        String RetVal = ElapsedHrs + " H";
        if (nElapsedMin > 0) {
            String ElapsedMin = String.valueOf(nElapsedMin);
            RetVal = ElapsedHrs + " H / " + ElapsedMin + " M";
        }
        return RetVal;
    }
}
