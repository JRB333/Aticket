package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.StringTokenizer;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class UploadBackgroundForm extends ActivityGroup {
    File SigFile;
    private String TempUploadIpAddress;
    private String CurrentUnitNumber;
    private String SendFileName;
    private Integer OrigFileSize = 0;

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
                    String cServerFileSize = "";
                    String BaseFileName = "";

                    if (response.toUpperCase().contains("SUCCESS")) {
                      // --- Successful HTTP.POST ---
                        response = response.toUpperCase().replace("SUCCESS","").trim();
                        StringTokenizer tokens = new StringTokenizer(response, "|");

                        if (tokens.hasMoreTokens() == true) {
                            cServerFileSize = tokens.nextToken();
                        }

                        if (tokens.hasMoreTokens() == true) {
                            BaseFileName = tokens.nextToken();
                            if (BaseFileName.contains(CurrentUnitNumber.toUpperCase())) {
                                BaseFileName = BaseFileName.replace(CurrentUnitNumber.toUpperCase(),"");
                                // Change From Abbreviated SEND File Names
                                //    Back to Original File Names if Necessary
                                if (BaseFileName.equals("TICK.R")) {
                                    BaseFileName = "TICKET.R";
                                } else if (BaseFileName.equals("TICK_MUL.R")) {
                                    BaseFileName = "TICKTEMP.R";
                                } else if (BaseFileName.equals("CLAN.R")) {
                                    BaseFileName = "CLANCY.J";
                                } else if (BaseFileName.equals("TICK_SEV.R")) {
                                        BaseFileName = "TICKSEVE.T";
                                }
                            }
                        }
                    } else {
                        // --- Successful HTTP.PUT ---
                        StringTokenizer tokens = new StringTokenizer(response, "|");

                        if (tokens.hasMoreTokens() == true) {
                            cServerFileSize = tokens.nextToken();
                        }

                        if (tokens.hasMoreTokens() == true) {
                            BaseFileName = tokens.nextToken();
                        }
                    }

                    if (!BaseFileName.equals("")) {
                        if (MiscFunctions.validInteger(cServerFileSize)) {
                            //Toast.makeText(getApplicationContext(), ServerFileSize + " and " + BaseFileName, 500).show();
                            int nServerFileSize = Integer.valueOf(cServerFileSize);
                            if(nServerFileSize > 0) {
                                if (!BaseFileName.equals("")) {
                                    int nOriginalFileSize = SearchData.GetFileSize(BaseFileName);
                                    if(nServerFileSize == nOriginalFileSize) {
                                        if (!BaseFileName.toUpperCase().trim().contains("SIG")
                                                && !BaseFileName.toUpperCase().trim().contains("PNG")
                                                && !BaseFileName.toUpperCase().trim().contains("CLANCY.J")) {

                                            // DELETE File After Send to 'Backend' Server
                                            SystemIOFile.Delete(BaseFileName);
                                            //Toast.makeText(getApplicationContext(), "Deleted: " + BaseFileName, 500).show();

                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //Toast.makeText(getApplicationContext(), "ParseString: " + ServerFileSize, 500).show();
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadform);

        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
        TextUpdate.setText("Uploading Data...");

        TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext()).trim();
        CurrentUnitNumber = WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.CheckTimeVal,getApplicationContext());

        String UploadString = ReturnFileContents("TICKET.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
            SendFileName = "TICK" + CurrentUnitNumber.toUpperCase() + ".R";
            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
            //String FileSize = Integer.toString(SearchData.GetFileSize("TICKET.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
            //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|TICKET.R");
            FilePutPost (URLString, "TICKET.R");
        }

        UploadString = ReturnFileContents("TICKTEMP.R");
        if (SearchData.GetFileSize("TICKTEMP.R") > 1100) {
            if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
                SendFileName = "TICK" + CurrentUnitNumber.toUpperCase() + "_MUL.R";
                String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
                //String FileSize = Integer.toString(SearchData.GetFileSize("TICKTEMP.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
                //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|TICKTEMP.R");
                FilePutPost (URLString, "TICKTEMP.R");
            }
        }

        UploadString = ReturnFileContents("TICKSEVE.R");
        if (SearchData.GetFileSize("TICKSEVE.R") > 1900) {
            if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
                SendFileName = "TICK" + CurrentUnitNumber.toUpperCase() + "_SEV.R";
                String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
                //String FileSize = Integer.toString(SearchData.GetFileSize("TICKSEVE.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
                //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|TICKSEVE.R");
                FilePutPost (URLString, "TICKSEVE.R");
            }
        }

        UploadString = ReturnFileContents("CLANCY.J");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
            SendFileName = "CLAN" + CurrentUnitNumber.toUpperCase() + ".R";
            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
            //new HttpConnection(handler).put(URLString, UploadString, "5|CLANCY.J"); // HARDCODED 5 FOR THE FILE SIZE SO THE CLANCY.J NEVER GETS ERASED ON THE BACKGROUND UPLOAD!
            FilePutPost (URLString, "CLANCY.J");
        }

        UploadString = ReturnFileContents("ADDI.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
            SendFileName = "ADDI" + CurrentUnitNumber.toUpperCase() + ".R";
            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
            //String FileSize = Integer.toString(SearchData.GetFileSize("ADDI.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
            //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|ADDI.R");
            FilePutPost (URLString, "ADDI.R");
        }

        UploadString = ReturnFileContents("LOCA.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
            SendFileName = "LOCA" + CurrentUnitNumber.toUpperCase() + ".R";
            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
            //String FileSize = Integer.toString(SearchData.GetFileSize("LOCA.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
            //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|LOCA.R");
            FilePutPost (URLString, "LOCA.R");
        }

        UploadString = ReturnFileContents("VOID.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
            SendFileName = "VOID" + CurrentUnitNumber.toUpperCase() + ".R";
            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
            //String FileSize = Integer.toString(SearchData.GetFileSize("VOID.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
            //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|VOID.R");
            FilePutPost (URLString, "VOID.R");
        }

        UploadString = ReturnFileContents("HITT.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
            SendFileName = "HITT" + CurrentUnitNumber.toUpperCase() + ".R";
            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
            //String FileSize = Integer.toString(SearchData.GetFileSize("HITT.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
            //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|HITT.R");
            FilePutPost (URLString, "HITT.R");
        }

        UploadString = ReturnFileContents("CHEC.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
            SendFileName = "CHEC" + CurrentUnitNumber.toUpperCase() + ".R";
            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
            //String FileSize = Integer.toString(SearchData.GetFileSize("CHEC.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
            //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|CHEC.R");
            FilePutPost (URLString, "CHEC.R");
        }

        UploadString = ReturnFileContents("WIFI.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
            SendFileName = "WIFI" + CurrentUnitNumber.toUpperCase() + ".R";
            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
            //String FileSize = Integer.toString(SearchData.GetFileSize("WIFI.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
            //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|WIFI.R");
            FilePutPost (URLString, "WIFI.R");
        }

        UploadString = ReturnFileContents("PICT.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0) {
            SendFileName = "PICT" + CurrentUnitNumber.toUpperCase() + ".R";
            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
            //String FileSize = Integer.toString(SearchData.GetFileSize("PICT.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
            //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|PICT.R");
            FilePutPost (URLString, "PICT.R");
        }

        File dirChalk = new File("/data/data/com.clancy.aticket/files/");
        String[] dirListChalk = dirChalk.list();
        int blahChalk = dirListChalk.length;
        int iChalk;
        String tempStringChalk = "";
        int ChalkCounter = 0;
        for(iChalk=0;  iChalk < blahChalk; iChalk++)
        {
            if (dirListChalk[iChalk].toUpperCase().contains("CHAL")) {
                ChalkCounter = ChalkCounter + 1;
                if (ChalkCounter > 5) // Chalk files were not being sent up until 4-8-2013 - Send 5 max at a time to prevent massive influx
                {
                    break;
                }
                tempStringChalk = dirListChalk[iChalk];
                if (tempStringChalk.equals("CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R") )
                {
                    // DON'T SEND THE CURRENT DAYS CHALK FILE
                }
                else
                {
                    if (SystemIOFile.exists(tempStringChalk))
                    {
                        UploadString = ReturnFileContents(tempStringChalk);
                        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
                        {
                            SendFileName = tempStringChalk;
                            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + SendFileName;
                            //String FileSize = Integer.toString(SearchData.GetFileSize(tempStringChalk)); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
                            //new HttpConnection(handler).put(URLString, UploadString, FileSize+"|"+ tempStringChalk);
                            FilePutPost (URLString, tempStringChalk);
                        }
                    }
                }
            }
        }

        File dir = new File("/data/data/com.clancy.aticket/files/");
        String[] dirList = dir.list();
        int blah = dirList.length;
        int i;
        String tempString = "";
        for(i = 0;  i < blah; i++)
        {
            if (dirList[i].toUpperCase().contains("JPG"))
            {
                byte[] byteBuffer;
                tempString = dirList[i];
                if (tempString.equals("REPRINT1.JPG") || tempString.equals("REPRINT2.JPG") || tempString.equals("REPRINT3.JPG") )
                {
                    // DON'T SEND THE REPRINT JPGS
                }
                else
                {
                    if (SystemIOFile.exists(tempString))
                    {
                        byteBuffer = ReturnByteArray(tempString);
                        if (byteBuffer != null)
                        {
                            SendFileName = tempString;
                            String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/pictures/" + SendFileName;
                            //String FileSize = Integer.toString(SearchData.GetFileSize(tempString)); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
                            //new HttpConnection(handler).putBitmap(URLString, UploadString, byteBuffer, FileSize+"|"+ tempString);
                            BitmapPutPost (URLString, tempString, byteBuffer);
                        }
                    }
                }
            }
        }

        // Optional Signature File Send
        if (WorkingStorage.GetCharVal(Defines.CaptureSignature, getApplicationContext()).equals("YES")) {
            // Check to see if Officer's Signature File Exists
            String BadgeNo = WorkingStorage.GetCharVal(Defines.PrintBadgeVal,getApplicationContext()).trim();
            String SigFileName = WorkingStorage.GetCharVal(Defines.SignatureFile, getApplicationContext());   // Fixed Signature Image file name
            String FileDir = getApplicationContext().getFilesDir().getPath();  // "/data/data/com.clancy.aticket/files/"
            SigFile = new File(FileDir, SigFileName);

            if (SigFileName.contains(BadgeNo) && SystemIOFile.exists(SigFileName)) {
                // Signature File for this Officer Exists, Send It
                byte[] byteBuffer;
                byteBuffer = ReturnByteArray(SigFileName);
                if (byteBuffer != null) {
                    String UploadFileName = WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim() + "_SIG.PNG";
                    SendFileName = UploadFileName;
                    String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/pictures/" + SendFileName;
                    //String FileSize = Integer.toString(SearchData.GetFileSize(SigFileName)); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
                    //new HttpConnection(handler).putBitmap(URLString, UploadString, byteBuffer,FileSize+"|"+ SigFileName);
                    BitmapPutPost (URLString, SigFileName, byteBuffer);
                }
            }
        }

        String URLString = "http://"+ TempUploadIpAddress +"/unload.asp";
        new HttpConnection(handler).get(URLString);

        if (WorkingStorage.GetCharVal(Defines.ForceUploadVal, getApplicationContext()).equals("SELFUNC"))
        {
            if (WorkingStorage.GetCharVal(Defines.ReturnToChalk, getApplicationContext()).equals("TRUE")) {
                WorkingStorage.StoreCharVal(Defines.ReturnToChalk, "", getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.ReturnToSurvey, "", getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.MenuProgramVal, Defines.ChalkMenu, getApplicationContext());
                WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, 0, getApplicationContext());
                WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0, getApplicationContext());

                if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR") {
                    Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                    startActivityForResult(myIntent, 0);
                }
            }  else if (WorkingStorage.GetCharVal(Defines.ReturnToSurvey, getApplicationContext()).equals("TRUE")) {
                returnToSurvey(false);
            }  else if (WorkingStorage.GetCharVal(Defines.HonorTicketIssued,getApplicationContext()).equals("YES")) {
                returnToHonorLot(false);
            }  else {
                Defines.clsClassName = SelFuncForm.class ;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
            }
        }
        finish();
        overridePendingTransition(0, 0);
    }

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

    public byte[] ReturnByteArray(String fileName)
    {
        byte[] ReturnByteArray = null;
        if (!SystemIOFile.exists(fileName))
            return null;

        int bytesRead, bytesAvailable, bufferSize;
        //byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File("/data/data/com.clancy.aticket/files/" + fileName) );
            if (fileInputStream!=null)
            {
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                ReturnByteArray = new byte[bufferSize];
                bytesRead = fileInputStream.read( ReturnByteArray, 0, bufferSize);
                fileInputStream.close();
            }
        }
        catch(Throwable t) {
            Toast.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000).show();
        }
        return ReturnByteArray;
    }

    private void returnToSurvey(Boolean closeForm) {
        WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrStateVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrDirVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrNumVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, "", getApplicationContext());

        WorkingStorage.StoreCharVal(Defines.ReturnToSurvey, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.MenuProgramVal, WorkingStorage.GetCharVal(Defines.ChalkMenu, getApplicationContext()), getApplicationContext());
        WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, 0, getApplicationContext());
        WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0, getApplicationContext());

        String ResumeRoute = WorkingStorage.GetCharVal(Defines.ResumeRoute, getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.RouteName, ResumeRoute, getApplicationContext());

        // Go Back To Survey
        //Defines.clsClassName = SurveySelFuncForm.class ;
        Defines.clsClassName = SurveyPassDataForm.class ;
        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        startActivityForResult(myIntent, 0);
        finish();
        overridePendingTransition(0, 0);
    }

    private void returnToHonorLot(Boolean closeForm) {
        WorkingStorage.StoreCharVal(Defines.HonorTicketIssued, "YES", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.ReprintHonorTicket, "YES", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrStateVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrDirVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrNumVal, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, "", getApplicationContext());

        WorkingStorage.StoreCharVal(Defines.ReturnToSurvey, "", getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.MenuProgramVal, WorkingStorage.GetCharVal(Defines.ChalkMenu, getApplicationContext()), getApplicationContext());
        WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, 0, getApplicationContext());
        WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, 0, getApplicationContext());

        String ResumeRoute = WorkingStorage.GetCharVal(Defines.ResumeRoute, getApplicationContext());
        WorkingStorage.StoreCharVal(Defines.RouteName, ResumeRoute, getApplicationContext());

        // Go Back To HonorLot Form
        Defines.clsClassName = HonorLotForm.class ;
        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        startActivityForResult(myIntent, 0);
        finish();
        overridePendingTransition(0, 0);
    }

    // --- Send the specified file to the 'backend' server ---
    private void FilePutPost (String url, String fileName) {
        String UploadString = ReturnFileContents(fileName);
        OrigFileSize = SearchData.GetFileSize(fileName);
        String FileSize = Integer.toString(OrigFileSize); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine

        if (TempUploadIpAddress.substring(0,11).equals("107.1.38.45")) {
            // --- Server 2008 or Server 2012 ---
            // --- Modify URL To Meet This Server's Standard ---
            url = "http://" + TempUploadIpAddress + "/DemoTickets/load.aspx?f=" + SendFileName + "&r=" + FileSize;
            new HttpConnection(handler).post(url, UploadString);  //(URLString, UploadString, FileSize + "|TESTSRVR2012.R");
        } else {
            // --- Server 2003 ---
            if (fileName.equals("CLANCY.J")) {
                FileSize = "5";   // 'Fake' the FileSize so as to Prevent DELETE of file: CLANCY.J
            }
            new HttpConnection(handler).put(url, UploadString, FileSize + "|" + fileName);
        }

        return;
    }

    // --- Send the specified file to the 'backend' server ---
    private void BitmapPutPost (String url, String ImgFile, byte[] byteBuffer) {
        String UploadString = "";
        OrigFileSize = SearchData.GetFileSize(ImgFile);
        String FileSize = Integer.toString(OrigFileSize); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine

        if (TempUploadIpAddress.substring(0,11).equals("107.1.38.45")) {
            // --- Server 2008 or Server 2012 ---
            url = "http://" + TempUploadIpAddress + "/DemoTickets/load.aspx?f=" + SendFileName + "&r=" + FileSize;
            new HttpConnection(handler).postBitmap(url, "", byteBuffer, SendFileName);
        } else {
            // --- Server 2003 ---
            new HttpConnection(handler).putBitmap(url, UploadString, byteBuffer, FileSize+"|"+ ImgFile);
        }

        return;
    }
}
