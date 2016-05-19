package com.clancy.aticket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PermitHistoryForm extends ActivityGroup {

    public static String PrintPermitHistory(Context cntxt, OutputStream outputStream)
    {
        String PrintCompleteTicket = "";
        String ParseString = "";
        String ReturnMessage = "";
        ReturnMessage = HTTPFileTransfer.HTTPGetPageContent("http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, cntxt) + "/lookup.asp?permit=" + WorkingStorage.GetCharVal(Defines.PrintPermitVal,cntxt) + "&status=OPEN&lp=PRINT", cntxt);
        try {
            outputStream.write(0x1B);
            outputStream.write(0x41);
            outputStream.write(0x0); // 7-8 N-DOT LINE SPACING

            outputStream.write(0x1B);
            outputStream.write(0x20);
            outputStream.write(0x04); // CHAR SPACING 7-9

            outputStream.write(0x12);
            outputStream.write(0x70);
            outputStream.write(0x0); // 7-59 SET PAPER EMPTY OFF
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ReadLayout.PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintPermitVal, cntxt), outputStream);
        ReadLayout.FeedPaperDots2(5, outputStream);
        ReadLayout.PrintThisTrimmed("DATE       CITATION   AMOUNT DUE", outputStream);
        ReadLayout.FeedPaperDots2(2, outputStream);
        ReadLayout.PrintThisTrimmed("--------------------------------", outputStream);
        ReadLayout.FeedPaperDots2(5, outputStream);
        //ReadLayout.PrintThisTrimmed(ParseString, outputStream);
        StringTokenizer tokens = new StringTokenizer(ReturnMessage, "|");
        while (tokens.hasMoreTokens() == true)
        {
            //ParseString = ParseString + tokens.nextToken() + "\n";
            ReadLayout.PrintThisTrimmed(tokens.nextToken(), outputStream);
            ReadLayout.FeedPaperDots2(2, outputStream);
        }

        try {
            outputStream.write(0x12);
            outputStream.write(0x6D);
            outputStream.write(0x01);
            outputStream.write(0x3C);
            outputStream.write(0x06); // Mark Position Detect

            outputStream.write(0x1B);
            outputStream.write(0x4A);
            outputStream.write(0x26); // FEED DOT LINES 0X26 = 38 DOT LINES
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return PrintCompleteTicket ;
    }

    Handler mHandler = new Handler();

    private Runnable mGetInfo = new Runnable() {
        public void run()
        {
            String URLString = "";
            String ParseString = "";
            String ReturnMessage = "";
            if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ValidLotLookupMenu))
            {
                URLString = "http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+"/lookup.asp?permit=" + WorkingStorage.GetCharVal(Defines.PrintPermitVal,getApplicationContext()) + "&status=LOTS&lp=LOOKUP";
                ReturnMessage = HTTPFileTransfer.HTTPGetPageContent(URLString, getApplicationContext());
            }

            if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.PermitHistoryMenu))
            {
                URLString = "http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext()) + "/plookup.asp?permit=" + WorkingStorage.GetCharVal(Defines.PrintPermitVal,getApplicationContext()) + "&status=ALL&lp=LOOKUP";
                ReturnMessage = HTTPFileTransfer.HTTPGetPageContent(URLString, getApplicationContext());
            }

            if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.VehiclePrintoutMenu))
            {
                //ReturnMessage = HTTPFileTransfer.HTTPGetPageContent("http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext()) + "/lookup.asp?permit=" + WorkingStorage.GetCharVal(Defines.PrintPermitVal,getApplicationContext()) + "&status=OPEN&lp=PRINT", getApplicationContext());
                // do this later
            }

            EditText eMessage = (EditText) findViewById(R.id.editTextPermit);
            StringTokenizer tokens = new StringTokenizer(ReturnMessage, "|");
            while (tokens.hasMoreTokens() == true)
            {
                if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.PermitHistoryMenu))
                {
                    ParseString = ParseString + tokens.nextToken()+ "<br>" ;
                }
                else
                {
                    ParseString = ParseString + tokens.nextToken() + "\n";
                }
            }

            if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.PermitHistoryMenu))
            {
                //eMessage.setText(ParseString);
                eMessage.setText(Html.fromHtml(ParseString));
                eMessage.setMovementMethod(LinkMovementMethod.getInstance());
            }

            if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ValidLotLookupMenu))
            {
                eMessage.setText(ParseString);
            }

            if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.VehiclePrintoutMenu))
            {
                Defines.clsClassName = PrintForm.class ;
                Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                finish();
                overridePendingTransition(0, 0);
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permithistoryform);

        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).equals(Defines.PermitHistoryMenu))
        {
            TextView functionLabel = (TextView) findViewById(R.id.functionLabel);
            functionLabel.setText("Permit History " );
            functionLabel.setVisibility(View.INVISIBLE);
        }

        Button second = (Button) findViewById(R.id.buttonESC);
        second.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CustomVibrate.VibrateButton(getApplicationContext());
                Defines.clsClassName = SelFuncForm.class ;
                Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        TextView tvMessage = (TextView) findViewById(R.id.textPermit);
        tvMessage.setText("Permit Number: " + WorkingStorage.GetCharVal(Defines.PrintPermitVal,getApplicationContext()));

        EditText eMessage = (EditText) findViewById(R.id.editTextPermit);
        eMessage.setText("Getting Info...");

        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ValidLotLookupMenu))
        {
            if (!WorkingStorage.GetCharVal(Defines.LotCheckMessage, getApplicationContext()).trim().equals(""))
            {
                tvMessage.setText(WorkingStorage.GetCharVal(Defines.LotCheckMessage, getApplicationContext()).trim());
            }
        }

        mHandler.postDelayed(mGetInfo, 100);
    }
}