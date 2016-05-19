package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class SurveyDefaultStateForm extends ActivityGroup {
    private Spinner SpinState;
    private String DefaultState;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveydefaultstateform);

        SpinState = (Spinner)findViewById(R.id.spinDefaultState);
        addListenerOnSpinnerItemSelection();

        String chkState = WorkingStorage.GetCharVal(Defines.DefaultState, getApplicationContext());
        if (!chkState.equals("")) {
            String[] States = getResources().getStringArray(R.array.state_array);
            int j = 0;
            while (States.length > j) {
                SpinState.getItemAtPosition(j);
                String StateOnly = States[j].toString().substring(0,2);
                if (StateOnly.equals(chkState)) {
                    SpinState.setSelection(j);
                    break;
                }
                j++;
            }
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
                // Save off New Route Name/Number
                SpinState = (Spinner) findViewById(R.id.spinDefaultState);
                DefaultState = SpinState.getSelectedItem().toString();
                DefaultState = DefaultState.substring(0,2).trim();
                WorkingStorage.StoreCharVal(Defines.DefaultState, DefaultState, getApplicationContext());

                // Change GUI's
                Defines.clsClassName = SurveySelectRouteForm.class;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(0, 0);
            }
        });
    }

    public void addListenerOnSpinnerItemSelection() {
        SpinState = (Spinner) findViewById(R.id.spinDefaultState);
        SpinState.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
