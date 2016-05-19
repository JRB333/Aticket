package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.StringTokenizer;

public class SurveySelectRouteForm extends ActivityGroup {

    ArrayAdapter<String> dataAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveyselectrouteform);

        WorkingStorage.StoreCharVal(Defines.RouteName, "", getApplicationContext());
        String ResumeRoute = WorkingStorage.GetCharVal(Defines.ResumeRoute, getApplicationContext());

        Spinner RouteSpin = (Spinner) findViewById(R.id.spinRoutes);
        PopulateSpinner();
        if (!ResumeRoute.equals("")) {
            RouteSpin.setSelection(dataAdapter.getPosition(ResumeRoute));
        }

        Button btnEsc = (Button) findViewById(R.id.btnESC);
        btnEsc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Defines.clsClassName = SurveySelFuncForm.class;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(0, 0);
            }
        });

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Confirm Existing Route
                Spinner RouteSpin = (Spinner) findViewById(R.id.spinRoutes);
                String SelectedRoute = RouteSpin.getSelectedItem().toString();

                // Save off New Route Name/Number
                WorkingStorage.StoreCharVal(Defines.RouteName, SelectedRoute, getApplicationContext());
                // Check against any previous RESUME route
                String ResumeRoute = WorkingStorage.GetCharVal(Defines.ResumeRoute, getApplicationContext());
                WorkingStorage.StoreLongVal(Defines.ResumeItemCounter, -1, getApplicationContext());

                if (!SelectedRoute.equals(ResumeRoute) && !ResumeRoute.equals("")) {
                    // Route Changed, Eliminate RESUME Values
                    WorkingStorage.StoreCharVal(Defines.ResumeRoute, "", getApplicationContext());
                }

                // Change GUI's
                Defines.clsClassName = SurveyPassForm.class;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void PopulateSpinner() {
        Spinner RouteSpin = (Spinner) findViewById(R.id.spinRoutes);
        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
        String RouteList = dbh.getAllRouteNames();
        if (RouteList.length() > 0) {
            // Parse Route Names to Build Spinner List
            final String SPLIT_STR = "^";
            final StringTokenizer stToken = new StringTokenizer(RouteList, SPLIT_STR);
            final String[] splitStr = new String[stToken.countTokens()];
            int index = 0;
            while(stToken.hasMoreElements()) {
                splitStr[index++] = stToken.nextToken();
            }
            // Creating adapter for spinner
            dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, splitStr);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            RouteSpin.setAdapter(dataAdapter);
        } else {
            // No Routes Left To Delete
            RouteSpin.setVisibility(View.INVISIBLE);

            CustomVibrate.VibrateButton(getApplicationContext());
            Messagebox.Message("No Routes Created", getApplicationContext());
        }
    }
}