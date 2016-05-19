package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SurveySelFuncForm extends ActivityGroup {

    private int CurrentRecordPointer = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveyselfuncform);

        Button UploadFiles = (Button) findViewById(R.id.btnUploadFiles);
        if (!WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE")) {
            UploadFiles.setEnabled(true);
            UploadFiles.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    UploadFilesClick(view);
                }

            });

            // Check If Upload Needed First
            CheckForUpload();
        } else {
            UploadFiles.setEnabled(false);
            UploadFiles.setTextColor(getResources().getColor(R.color.grey_400));
        }

        WorkingStorage.StoreCharVal(Defines.RouteName, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.StreetName, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.MeterSpace, "", getApplicationContext());

        WorkingStorage.StoreCharVal(Defines.SurveyPlate, "", getApplicationContext());
        //WorkingStorage.StoreCharVal(Defines.DefaultState, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.SurveyState, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.PassCounter, "0", getApplicationContext());
        WorkingStorage.StoreLongVal(Defines.ResumeItemCounter, -1, getApplicationContext());

        Button btnESC = (Button) findViewById(R.id.btnESC);
        btnESC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CustomVibrate.VibrateButton(getApplicationContext());

                WorkingStorage.StoreCharVal(Defines.PreviousMenu, "", getApplicationContext());

                Defines.clsClassName = SelFuncForm.class;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(0, 0);
            }
        });

        Button UpdateRoutes = (Button) findViewById(R.id.btnUpdateRoutes);
        UpdateRoutes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UpdateRoutesClick(view);
            }

        });

        Button Survey = (Button) findViewById(R.id.btnSurvey);
        Survey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SurveyClick(view);
            }

        });


        // Check if Any Routes Been Created?
        CheckRoutes();
    }

    private void UpdateRoutesClick(View view) {
        WorkingStorage.StoreCharVal(Defines.IntegrateSurveyData, "", getApplicationContext());

        Defines.clsClassName = SurveyUpdateRoutesForm.class;
        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        startActivityForResult(myIntent, 0);
        overridePendingTransition(0, 0);
        finish();
    }

    private void SurveyClick(View view) {
        Defines.clsClassName = SurveySelectRouteForm.class;
        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        startActivityForResult(myIntent, 0);
        overridePendingTransition(0, 0);
        finish();
    }

    private void UploadFilesClick(View view) {
        Defines.clsClassName = SurveyUploadRouteFilesForm.class;
        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        startActivityForResult(myIntent, 0);
        overridePendingTransition(0, 0);
        finish();
    }

    private void CheckRoutes() {
        // Have Any Routes Been Created?
        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
        String RouteList = dbh.getAllRouteNames();

        // Search List of Existing Routes For Duplicates
        if (RouteList.length() == 0) {
            Button ModifyRoutes = (Button) findViewById(R.id.btnUpdateRoutes);
            ModifyRoutes.setFocusable(true);
            ModifyRoutes.setFocusableInTouchMode(true);
            ModifyRoutes.requestFocus();
        } else {
            Button Survey = (Button) findViewById(R.id.btnSurvey);
            Survey.setFocusableInTouchMode(true);
            Survey.requestFocus();
        }
    }

    private void CheckForUpload() {
        // Store Off Default Value
        WorkingStorage.StoreCharVal(Defines.LastUpdate, "", getApplicationContext());
        File FileDir = new File(WorkingStorage.GetCharVal(Defines.FileDir, getApplicationContext()));

        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());

        // ##### For TESTING ONLY #####
        // 'Kills previous Table, Re-Creates it, And Displays Field names for verification
        //dbh.kill_n_create_Database();
        //dbh.checktables();
        //dbh.deleteAllRoutes();
        //dbh.clearAllRouteRecords();
        //dbh.InitPassTable();
        //dbh.ReadTableContents();
        //deleteDir(FileDir);
        // ############################

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today0 = Calendar.getInstance().getTime();
        Date today = new Date(today0.getYear(), today0.getMonth(), Integer.valueOf(String.valueOf(android.text.format.DateFormat.format("dd", today0))));

        String LastUpdateStr = dbh.getLastUpdate();
        LastUpdateStr = LastUpdateStr.replace("^", "");
        if (LastUpdateStr.length() > 0) {
            // Check LastUpdate
            if (LastUpdateStr.length() > 0) {
                // Get Date of Last Route Data Pass Write
                Date LastDate0 = null;
                try {
                    LastDate0 = formatter.parse(LastUpdateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date LastDate = new Date(LastDate0.getYear(), LastDate0.getMonth(), Integer.valueOf(String.valueOf(android.text.format.DateFormat.format("dd", LastDate0))));

                // Compare with Today
                // compareTo() -- Returns an int < 0 if this Date is less than the specified Date, 0 if they are equal, and an int > 0 if this Date is greater.
                if (LastDate.before(today)) {
                    // If LastUpdate is earlier than Today Upload the files
                    WorkingStorage.StoreCharVal(Defines.LastUpdate, LastUpdateStr, getApplicationContext());

                    // Force to Update
                    Defines.clsClassName = SurveyUploadRouteFilesForm.class;
                    Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                    startActivityForResult(myIntent, 0);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        } else {
            Button UploadFiles = (Button) findViewById(R.id.btnUploadFiles);
            UploadFiles.setEnabled(false);
            UploadFiles.setTextColor(getResources().getColor(R.color.grey_400));
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
}
