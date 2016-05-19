package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SurveyPassForm extends ActivityGroup {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveypassform);

        TextView FuncTitle = (TextView) findViewById(R.id.lblGUITitle);
        FuncTitle.setText("Enter Route: " + WorkingStorage.GetCharVal(Defines.RouteName, getApplicationContext()) + " Pass Data");

        TextView txtPassesEntered = (TextView) findViewById(R.id.txtPassesEntered);

        // Determine Number of Passes Entered for this specific Route
        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
        //String PassData = dbh.getAllStreetMeterPassData();
        String PassData = dbh.getPassCount();

        if (PassData.length() == 0 || PassData.equals("-1")) {
            // First Pass for this Route
            PassData = "0";
        }

        //Integer PassesEntered = Integer.valueOf(WorkingStorage.GetCharVal(Defines.PassCounter, getApplicationContext()));
        Integer PassesEntered = Integer.valueOf(PassData);
        txtPassesEntered.setText(String.valueOf(PassesEntered));
        Integer CurrentPass = PassesEntered + 1;

        TextView CurrentPassNo = (TextView) findViewById(R.id.txtPassNumber);
        CurrentPassNo.setText(String.valueOf(CurrentPass));

        Button btnEsc = (Button) findViewById(R.id.btnESC);
        btnEsc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Defines.clsClassName = SurveySelFuncForm.class;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(0, 0);
            }
        });

        String cResumeItemCounter = WorkingStorage.GetCharVal(Defines.ResumeItemCounter, getApplicationContext());
        Button btnResume = (Button) findViewById(R.id.btnResume);
        if (PassesEntered == 0 || cResumeItemCounter.equals("-1")) {
            btnResume.setVisibility(View.INVISIBLE);
            btnResume.setEnabled(false);
        } else {
            btnResume.setText("Resume Pass: " + String.valueOf(PassesEntered));
        }
        btnResume.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Save off New Route Name/Number
                TextView PassesEntered = (TextView) findViewById(R.id.txtPassesEntered);
                String PreviousPass = PassesEntered.getText().toString().trim();
                WorkingStorage.StoreCharVal(Defines.PassCounter, PreviousPass, getApplicationContext());

                String cResumeItemCounter = WorkingStorage.GetCharVal(Defines.ResumeItemCounter, getApplicationContext());

                WorkingStorage.StoreCharVal(Defines.PassItemCounter, cResumeItemCounter, getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.ItemsRetrieved, "-1", getApplicationContext());

                // Change GUI's
                Defines.clsClassName = SurveyPassDataForm.class;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(0, 0);
            }
        });

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Save off New Route Name/Number
                WorkingStorage.StoreCharVal(Defines.PassItemCounter, "-1", getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.ItemsRetrieved, "-1", getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.ResumeItemCounter, "-1", getApplicationContext());

                TextView CurrentPassNo = (TextView) findViewById(R.id.txtPassNumber);
                String ThisPass = CurrentPassNo.getText().toString().trim();
                WorkingStorage.StoreCharVal(Defines.PassCounter, ThisPass, getApplicationContext());

                // Starting New Pass, Finalize any Previous Passes
                SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
                dbh.FinalizeRoutePass();

                // Change GUI's
                Defines.clsClassName = SurveyPassDataForm.class;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(0, 0);
            }
        });
    }
}