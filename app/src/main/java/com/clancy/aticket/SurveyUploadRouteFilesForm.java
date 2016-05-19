package com.clancy.aticket;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class SurveyUploadRouteFilesForm extends ActivityGroup {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveyuploadroutefiles);

        TextView UpdtStatus = (TextView) findViewById(R.id.txtUpdateStatus);
        UpdtStatus.setVisibility(View.INVISIBLE);

        Button btnEsc = (Button) findViewById(R.id.btnESC);
        btnEsc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(Defines.contextGlobal, "No Files Uploaded!", Toast.LENGTH_LONG).show();

                ReturnToMainSurveyMenu();
            }
        });

        CheckForUploadData();

        Button btnUpload = (Button) findViewById(R.id.btnUploadFiles);
        btnUpload.setEnabled(true);
        String LastDateStr = WorkingStorage.GetCharVal(Defines.LastUpdate, getApplicationContext());
        if (LastDateStr.length() > 0) {
            LastDateStr = LastDateStr.substring(0,10);
            btnUpload.setText("Upload Files From: " + LastDateStr);
            btnUpload.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // Determine if the Pass is Complete
                    UploadFilesDialog();
                }
            });
        } else {
            btnUpload.setText("Nothing to Upload");
            btnUpload.setEnabled(false);
        }
    }

    protected void UploadFilesDialog() {
        // setup a dialog window
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SurveyUploadRouteFilesForm.this);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setTitle("Upload Files...")
                .setMessage("Upload the Route Files?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SendRouteFiles();

                        ReturnToMainSurveyMenu();;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Stay In GUI
                    }
                });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void SendRouteFiles() {
        // Get A List of All Routes
        // Get List of Existing Routes
        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
        String RouteList = dbh.getAllRouteNames();

        // Search List of Existing Routes For Duplicates
        String ThisRoute = "";
        if (RouteList.length() > 0) {
            final String SPLIT_STR = "^";
            final StringTokenizer stToken = new StringTokenizer(RouteList, SPLIT_STR);
            final String[] splitStr = new String[stToken.countTokens()];
            int index = 0;
            while(stToken.hasMoreElements()) {
                ThisRoute = stToken.nextToken().toString();
                SendThisRouteFile(ThisRoute);
            }

            TextView UpdtStatus = (TextView) findViewById(R.id.txtUpdateStatus);
            UpdtStatus.setVisibility(View.VISIBLE);
            UpdtStatus.setText("Upload Done!");
        }
        //dbh.clearAllRouteRecords();
    }

    private void CheckForUploadData() {
        WorkingStorage.StoreCharVal(Defines.LastUpdate, "", getApplicationContext());

        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date tmpToday = Calendar.getInstance().getTime();
        Date today = new Date(tmpToday.getYear(), tmpToday.getMonth(), Integer.valueOf(String.valueOf(android.text.format.DateFormat.format("dd", tmpToday))));

        String LastUpdateStr = dbh.getLastUpdate();
        LastUpdateStr = LastUpdateStr.replace("^", "");
        if (LastUpdateStr.length() > 0) {
            // Check LastUpdate
            if (LastUpdateStr.length() > 0) {
                // Get Date of Last Route Data Pass Write
                Date tmpLastDate = null;
                try {
                    tmpLastDate = formatter.parse(LastUpdateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date LastDate = new Date(tmpLastDate.getYear(), tmpLastDate.getMonth(), Integer.valueOf(String.valueOf(android.text.format.DateFormat.format("dd", tmpLastDate))));

                // Compare with Today
                // compareTo() -- Returns an int < 0 if this Date is less than the specified Date, 0 if they are equal, and an int > 0 if this Date is greater.
                if (LastDate.equals(today) || LastDate.before(today)) {
                    // Route PassData Exists For Today
                    WorkingStorage.StoreCharVal(Defines.LastUpdate, LastUpdateStr, getApplicationContext());
                }
            }
        }
    }

    private void SendThisRouteFile(String ThisRoute) {
        // Get All Street, Meter, & PassData for Route = ThisRoute
        WorkingStorage.StoreCharVal(Defines.RouteName, ThisRoute, getApplicationContext());

        SurveyDatabaseHelper dbh = new SurveyDatabaseHelper(getApplicationContext());
        String RouteDataStr = dbh.getAllStreetMeterPassData();

        // Build File From SQLite Data for Route = ThisRoute
        Boolean FileSent = false;
        String RouteRecord = "";
        if (RouteDataStr.length() > 0) {
            TextView UpdtStatus = (TextView) findViewById(R.id.txtUpdateStatus);
            UpdtStatus.setVisibility(View.VISIBLE);

            // Create Route Upload File
            String LastUpdate = WorkingStorage.GetCharVal(Defines.LastUpdate, getApplicationContext());
            String LastDate = LastUpdate.substring(0, 10);
            LastDate = LastDate.substring(5,7) + LastDate.substring(8,LastDate.length()) + LastDate.substring(0,4);
            String LastTime = LastUpdate.substring(11,LastUpdate.length());
            LastTime = LastTime.replace(":","");

            String Filename = "Survey_Route_" + ThisRoute + "_" + LastDate + "_" + LastTime + ".R";
            String FileDir = WorkingStorage.GetCharVal(Defines.FileDir, Defines.contextGlobal);
            File file = new File(FileDir, Filename);

            // Delete Any Previous File With This Name
            if (file.exists()) {
                file.delete();
            }

            if (!file.exists()) {
                try {
                    file.createNewFile();

                    FileOutputStream fos = null;
                    fos = new FileOutputStream(file);
                    PrintWriter pw = new PrintWriter(fos);

                    // Populate Route Upload File with 'Records'
                    final String SPLIT_STR = "^";  // Record 'Token'
                    final StringTokenizer stToken = new StringTokenizer(RouteDataStr, SPLIT_STR);
                    final String[] splitStr = new String[stToken.countTokens()];
                    int index = 0;
                    while(stToken.hasMoreElements()) {
                        // Get this Individual Record
                        RouteRecord = stToken.nextToken().toString();
                        RouteRecord = RouteRecord.replace("^","");

                        // Remove Field & PassData 'Tokens'
                        RouteRecord = RouteRecord.replace("#","");
                        RouteRecord = RouteRecord.replace("++","");

                        // Write Out Record
                        pw.println(RouteRecord);
                    }

                    pw.flush();
                    pw.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (file.exists()) {
                // Send ThisRoute's File
                String HTTPageFilesize = "";
                String TempUploadIpAddress = "";
                String UploadString = ReturnFileContents(Filename);
                if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
                    HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, Defines.contextGlobal) + "/connected.asp", Defines.contextGlobal);

                    if (HTTPageFilesize.length() == 4) {
                        TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.UploadIPAddress, Defines.contextGlobal).trim();
                    } else {
                        HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://" + WorkingStorage.GetCharVal(Defines.AlternateIPAddress, Defines.contextGlobal) + "/connected.asp", Defines.contextGlobal);
                        if (HTTPageFilesize.length() == 4)
                        {
                            TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.AlternateIPAddress, Defines.contextGlobal).trim();
                        }
                    }

                    if (TempUploadIpAddress.equals(""))
                    {
                        Messagebox.Message("Could not Connect to Upload.", getApplicationContext());
                        return;
                    }

                    FileSent = HTTPFileTransfer.UploadFileBinary(Filename, "http://" + TempUploadIpAddress + "/DemoTickets/" + Filename, Defines.contextGlobal);
                    if (FileSent == true)
                    {
                        // File SEND was Successful
                        // So Delete the Route's PassData 'R' File
                        SystemIOFile.Delete(Filename);
                    }
                }
            }
        } else {
            // No Data Present To Send
            // Regardless, Initialize This Route as Though SENT
            FileSent = true;
        }

        if (FileSent) {
            // Clear This Route's Data
            dbh.clearRouteRecords();
            // Clear This Route's PassDataLog
            WorkingStorage.StoreCharVal(Defines.PassCounter, "-1", getApplicationContext());
            //Boolean Result = dbh.FinalizeRoutePass();
        }
    }

    public void ReturnToMainSurveyMenu() {
        WorkingStorage.StoreCharVal(Defines.LastUpdate, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.RouteName, "", getApplicationContext());

        // Go Back and Start Over
        Defines.clsClassName = SurveySelFuncForm.class;
        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        startActivityForResult(myIntent, 0);
        overridePendingTransition(0, 0);
    }

    // ##########################################################
    // Duplicate Routine from UploadBackgroundForm
    // Copied here for Stand-alone testing
    public String ReturnFileContents(String fileName)
    {
        String ReturnString = "ERROR";
        if (!SystemIOFile.exists(fileName))
            return ReturnString;

        try {
            FileInputStream in = openFileInput(fileName);
            if (in!=null)
            {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder sb = new StringBuilder();
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\r\n");
                        //sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ReturnString = sb.toString();
            }
        }
        catch(Throwable t) {
            Toast.makeText(getApplicationContext(), "Ex: " + t.toString(), 2000).show();
        }
        return ReturnString;
    }

    // Duplicate Routine from UploadBackgroundForm
    // Copied here for Stand-alone testing
    Handler handler = new Handler()
    {
        public void handleMessage(Message message) {
            switch (message.what) {
                case HttpConnection.DID_START:
                    //text.setText("Starting connection...");
                    // Toast.makeText(getApplicationContext(), "Starting connection...", 2000).show();
                    break;

                case HttpConnection.DID_SUCCEED:
                    String response = (String) message.obj;
                    //text.setText(response);
                    //Toast.makeText(getApplicationContext(), response, 500).show();
                    StringTokenizer tokens = new StringTokenizer(response, "|");
                    String ParseString1 = "";
                    String ParseString2 = "";
                    if (tokens.hasMoreTokens() == true)
                    {
                        ParseString1 = tokens.nextToken();
                    }
                    if (tokens.hasMoreTokens() == true)
                    {
                        ParseString2 = tokens.nextToken();
                    }
                    if (!ParseString2.equals(""))
                    {
                        if (MiscFunctions.validInteger(ParseString1))
                        {
                            //Toast.makeText(getApplicationContext(), ParseString1 + " and " + ParseString2, 500).show();
                            int OriginalFileSize = Integer.valueOf(ParseString1);
                            if(OriginalFileSize > 0)
                            {
                                if (!ParseString2.equals(""))
                                {
                                    if(OriginalFileSize == SearchData.GetFileSize(ParseString2))
                                    {
                                        if (!ParseString2.toUpperCase().trim().contains("SIG") && !ParseString2.toUpperCase().trim().contains("PNG")) {
                                            SystemIOFile.Delete(ParseString2);
                                            //Toast.makeText(getApplicationContext(), "Deleted: " + ParseString2, 500).show();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        //Toast.makeText(getApplicationContext(), "ParseString: " + ParseString1, 500).show();
                    }
                    break;

                case HttpConnection.DID_ERROR:
                    Exception e = (Exception) message.obj;
                    e.printStackTrace();
                    //  text.setText("Connection failed.");
                    // Toast.makeText(getApplicationContext(), "Connection failed.", 2000).show();
                    break;
            }
        }
    };
}