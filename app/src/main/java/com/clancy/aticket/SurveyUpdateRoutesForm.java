package com.clancy.aticket;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SurveyUpdateRoutesForm extends ActivityGroup {
    private Handler mHandler = new Handler();
    private Handler uiHandler = new UIHandler();
    Thread UIUpdateThread;

    private String crlf = System.getProperty("line.separator");
    private String RoutesFileName = "";
    private String UpdateRoute = "";
    private String UpdateSequence = "";
    private Integer nUpdateSequence = 0;
    private String RefreshUpdateMsg = "";
    private String NewUpdateMsg = "";
    Activity mActivity;

    TextView txtRefresh;
    File RoutesFile;

    private Runnable mLaunchIntegration = new Runnable() {
        public void run() {
            IntegrateNewData();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveyupdateroutesform);

        mActivity = this;
        RefreshUpdateMsg = "Updating Route Data " + crlf + "Please Be Patient...";
        NewUpdateMsg = RefreshUpdateMsg;

        Button btnEsc = (Button) findViewById(R.id.btnESC);
        btnEsc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WorkingStorage.StoreCharVal(Defines.IntegrateSurveyData, "", getApplicationContext());

                Defines.clsClassName = SurveySelFuncForm.class;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(0, 0);
            }
        });

        txtRefresh = (TextView) findViewById(R.id.txtRefreshing);
        txtRefresh.setVisibility(View.INVISIBLE);
        //mUpdateTextView = new Handler();

        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ConfirmUpdate();
            }
        });

        RoutesFileName = "SURVEYROUTES.A";
        Boolean CheckRoute = false;
        String FileDir = getApplicationContext().getFilesDir().getPath();  // "/data/data/com.clancy.aticket/files/"
        File RoutesFile = new File(FileDir, RoutesFileName);
        String IntegrateData = WorkingStorage.GetCharVal(Defines.IntegrateSurveyData, getApplicationContext());
        if (IntegrateData.equals("YES") && RoutesFile.exists()) {
            btnUpdate.setEnabled(false);
            btnUpdate.setTextColor(getResources().getColor(R.color.grey_400));

            //txtRefresh = (TextView) findViewById(R.id.txtRefreshing);
            txtRefresh.setVisibility(View.VISIBLE);
            txtRefresh.setText(RefreshUpdateMsg);

            mHandler.postDelayed(mLaunchIntegration,1000);
        }
    }


    protected void ConfirmUpdate() {
        // setup a dialog window
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SurveyUpdateRoutesForm.this);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setTitle("Update Route Data...")
                .setMessage("Replace All Existing Route Data?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateRouteData();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Go Back to Survey Function Select
                        // Now Go Back and do the next Street-Meter combination
                        WorkingStorage.StoreCharVal(Defines.IntegrateSurveyData, "", getApplicationContext());

                        Defines.clsClassName = SurveySelFuncForm.class;
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

    private void UpdateRouteData() {
        txtRefresh.setVisibility(View.VISIBLE);

        RoutesFileName = "SURVEYROUTES.A";
        String FileDir = getApplicationContext().getFilesDir().getPath();  // "/data/data/com.clancy.aticket/files/"
        File RoutesFile = new File(FileDir, RoutesFileName);
        if (RoutesFile.exists()) {
            RoutesFile.delete();
        }

        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
        dbh.deleteAllRoutes();

        WorkingStorage.StoreCharVal(Defines.RefreshCalledBy, "SURVEY", getApplicationContext());

        Defines.clsClassName = RefreshForm.class;
        Intent myIntent = new Intent(this, SwitchForm.class);
        startActivityForResult(myIntent, 0);
        overridePendingTransition(0, 0);
        finish();
    }


    private void IntegrateNewData() {
        Context cntxt = Defines.contextGlobal;

        // Temporarily Disable Screen Timeout
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(cntxt);

        Boolean CheckRoute = false;

        String RoutesFileName = "SURVEYROUTES.A";
        String FileDir = cntxt.getFilesDir().getPath();  // "/data/data/com.clancy.aticket/files/"
        File RoutesFile = new File(FileDir, RoutesFileName);
        if (RoutesFile.exists()) {
            String LastRoute = "x-x";
            try {
                FileInputStream fis = cntxt.openFileInput(RoutesFileName);
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.length() > 0) {
                        final String SPLIT_STR = "|";
                        final StringTokenizer stToken = new StringTokenizer(line, SPLIT_STR);
                        final String[] splitStr = new String[stToken.countTokens()];
                        int index = 0;
                        while(stToken.hasMoreElements()) {
                            splitStr[index] = stToken.nextToken();

                            // If more to get, increment Index
                            if (stToken.hasMoreElements()) {
                                index++;
                            }
                        }

                        if (splitStr.length > 0) {
                            UpdateRoute = splitStr[0].toString().trim();
                            UpdateSequence = splitStr[3].toString().trim();
                            nUpdateSequence = Integer.valueOf(UpdateSequence);
                            if (!UpdateRoute.equals(LastRoute) || (nUpdateSequence % 100) == 0) {
                                if (!UpdateRoute.equals(LastRoute)) {
                                    // Route # Changed, Set Flag
                                    CheckRoute = true;
                                    LastRoute = UpdateRoute;
                                }

                               /*RefreshUpdateMsg = "Updating Data " + crlf + " " + " For Route: " + UpdateRoute + " - Sequence: " + UpdateSequence + "...";
                               txtRefresh.setText(RefreshUpdateMsg);
                               txtRefresh.invalidate();*/

                                NewUpdateMsg = "Updating Data... " + crlf + " " + "   Route: " + UpdateRoute + crlf + "    Sequence: " + UpdateSequence + "...";
                                RefreshUpdateMsg = NewUpdateMsg;

                                // ####################
                                UIUpdateThread = new Thread() {
                                    public void run() {
                                        try {
                                            // simulate long running operation
                                            Thread.sleep(1000);

                                            // create message which will be send to handler
                                            Message msg = Message.obtain(uiHandler);
                                            msg.obj = RefreshUpdateMsg;
                                            uiHandler.sendMessage(msg);

                                        } catch (InterruptedException e) {
                                            //Log.e(TAG, "run in thread", e);
                                        }
                                    }
                                };
                                UIUpdateThread.start();
                                // ####################
                            }

                            WorkingStorage.StoreCharVal(Defines.RouteName, UpdateRoute, getApplicationContext());
                            WorkingStorage.StoreCharVal(Defines.StreetName, splitStr[1].toString().trim(), getApplicationContext());
                            WorkingStorage.StoreCharVal(Defines.MeterSpace, splitStr[2].toString().trim(), getApplicationContext());
                            WorkingStorage.StoreCharVal(Defines.RouteSequence, splitStr[3].toString().trim(), getApplicationContext());

                            dbh.addStreetMeterRecord();

                            if (CheckRoute) {
                                String Result = dbh.getPassTableRoute();
                                if (Result.equals("")) {
                                    // Route Not Yet in RoutePassLog table
                                    // Add it now
                                    dbh.addPassTableRouteRecord();
                                }
                                CheckRoute = false;
                            }
                        }
                    }
                    sb.append(line).append("\n");
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }

            // Now Delete File until Next Time
            RoutesFile.delete();

            // Re-Enable Screen Timeout
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            // Leave routine
            Defines.clsClassName = SurveySelFuncForm.class;
            Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
            startActivityForResult(myIntent, 0);
            overridePendingTransition(0, 0);
        }
    }

    /*Runnable updateTextView = new Runnable() {

        @Override
        public void run() {
            //TextView tv = (TextView) findViewById(R.id.txtRefreshing);//Text To be edited
            txtRefresh.setText(RefreshUpdateMsg);//Set the Text
        }
    };*/

    class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // a message is received; update UI text view
            txtRefresh.setText(msg.obj.toString());
            super.handleMessage(msg);
        }
    }
}
