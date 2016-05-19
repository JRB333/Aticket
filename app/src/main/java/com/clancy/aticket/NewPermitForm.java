package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class NewPermitForm extends ActivityGroup {
	private void EnterClick()
	{
		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.TicketIssuanceMenu))
		{
			if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
			{
		 		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		   		startActivityForResult(myIntent, 0);
		   		finish();
		   		overridePendingTransition(0, 0);    
			}
		}	
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpermitform);      
       
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
		tvDesc1.setText("Permit: "+ WorkingStorage.GetCharVal(Defines.SavePermitVal,getApplicationContext()));
		TextView tvDesc2 = (TextView) findViewById(R.id.TextViewDesc2);
		tvDesc2.setText(WorkingStorage.GetCharVal(Defines.PermitMessageVal,getApplicationContext()));
		TextView tvDesc3 = (TextView) findViewById(R.id.TextViewDesc3);
		tvDesc3.setText("");		
		TextView tvDesc4 = (TextView) findViewById(R.id.TextViewDesc4);
		tvDesc4.setText("");

        
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