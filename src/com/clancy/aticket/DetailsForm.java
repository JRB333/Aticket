package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DetailsForm extends ActivityGroup {
	private void EnterClick()
	{
		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			  if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
		      {
				  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				  startActivityForResult(myIntent, 0);
				  finish();
				  overridePendingTransition(0, 0);    
		      }	
		}
		else if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.TicketIssuanceMenu))
		{
			if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
			{
		 		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		   		startActivityForResult(myIntent, 0);
		   		finish();
		   		overridePendingTransition(0, 0);    
			}
		}
		else
		{
			Defines.clsClassName = StateForm.class ;	
			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		   	startActivityForResult(myIntent, 0);
		   	finish();
		   	overridePendingTransition(0, 0);    
		}		
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsform);      
       
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

		TextView tvDesc1 = (TextView) findViewById(R.id.TextViewDesc1);
		tvDesc1.setText("Plate:");
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.TextViewDesc1);
	        tvSpanish.setText("TABILLA:");
	    }		
		TextView tvDesc2 = (TextView) findViewById(R.id.TextViewDesc2);
		tvDesc2.setText("Reason: ");
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.TextViewDesc2);
	        tvSpanish.setText("RAZÓN:");
	    }	
		TextView tvDesc3 = (TextView) findViewById(R.id.TextViewDesc3);
		tvDesc3.setText("Reason Here");
		TextView tvDesc4 = (TextView) findViewById(R.id.TextViewDesc4);
		tvDesc4.setText("");

        String PlateTwoEnd = WorkingStorage.GetCharVal(Defines.PrintBootListPlateVal,getApplicationContext());
        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.PermPlatMenu))
        {
        	tvDesc1.setText("Permit:");
        	tvDesc3.setText(WorkingStorage.GetCharVal(Defines.PrintPermitVal,getApplicationContext()));
        }
        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.PlatPermMenu))
        {
        	tvDesc3.setText(WorkingStorage.GetCharVal(Defines.PrintPermitVal,getApplicationContext()));
        }
        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.VirtPermMenu))
        {
        	if (WorkingStorage.GetCharVal(Defines.PrintBootListReasonVal, getApplicationContext()).trim().equals( "ISSUE TICKET NOW"))
        	{
        		tvDesc1.setText("NOT ON FILE:");
        	}
        	else
        	{
        		tvDesc1.setText("PERMIT:");
        		tvDesc3.setText("IN LOT:");
        		tvDesc4.setText(WorkingStorage.GetCharVal(Defines.VirtPermDOWVal,getApplicationContext()));
        		PlateTwoEnd = PlateTwoEnd.substring(1);
        	}
        }
        else
        {
        	if (PlateTwoEnd.substring(1,2).equals("X"))
        	{
        		tvDesc1.setText("Permit Number:");
        		PlateTwoEnd = PlateTwoEnd.substring(2);
        	}
        	else
        	{
        		PlateTwoEnd = PlateTwoEnd.substring(1,3) + "  " + PlateTwoEnd.substring(3);	
        	}
        }
        tvDesc1.setText(tvDesc1.getText().toString().trim()+" "+PlateTwoEnd);
        tvDesc3.setText(WorkingStorage.GetCharVal(Defines.PrintBootListReasonVal,getApplicationContext()));
        if (WorkingStorage.GetCharVal(Defines.PrintBootListReasonVal, getApplicationContext()).trim().equals( ">PLATE CLEAR<"))
        {
        	SaveTicket.SaveChecFile(getApplicationContext());
        }
        else
        {
        	SaveTicket.SaveHittFile(getApplicationContext());
        }
        
        
  /*      String CommentDesc = "";
        String CommentCode = "";
        if (savedInstanceState==null)
        {    	   
           WorkingStorage.StoreCharVal(Defines.EntireDataString, "", getApplicationContext());
           
           CommentCode = WorkingStorage.GetCharVal(Defines.SaveCommentVal, getApplicationContext());
    	   if (CommentCode.equals(""))
    	   {
    		   WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
    		   CommentDesc = SearchData.ScrollUpThroughFile("COMMENT.T", getApplicationContext());
    		   ShowData(CommentDesc);    		   
    	   }
    	   else
    	   {
    		   CommentDesc = SearchData.FindRecordLine(CommentCode, 3, "COMMENT.T", getApplicationContext());
    		   ShowData(CommentDesc);   
    	   }
           WorkingStorage.StoreCharVal(Defines.RotateValue, CommentDesc.substring(0,3), getApplicationContext());
        } //Must do this to ensure that rotation doesn't jack everything up
        else
        {
    	   CommentCode = WorkingStorage.GetCharVal(Defines.RotateValue, getApplicationContext());
    	   CommentDesc = SearchData.FindRecordLine(CommentCode, 3, "COMMENT.T", getApplicationContext());
    	   ShowData(CommentDesc);   
        }
       */
        Button enter = (Button) findViewById(R.id.buttonEnter);
        enter.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   EnterClick();
           }          
        });
        enter.requestFocus();
        enter.setEnabled(false);
        Handler mHandler = new Handler(); 
		mHandler.postDelayed(mWaitRunnable, 2000);
		Vibrator vibKey = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
   		vibKey.vibrate(1000);	
	}
	private Runnable mWaitRunnable = new Runnable() {     
		public void run() 
		{         			    
			Button enter = (Button) findViewById(R.id.buttonEnter);
			enter.setEnabled(true);
		}
	};

}