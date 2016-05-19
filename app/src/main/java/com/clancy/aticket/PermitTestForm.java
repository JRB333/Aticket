package com.clancy.aticket;

//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PermitTestForm extends ActivityGroup {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permitform);

  /*     
      Button second = (Button) findViewById(R.id.buttonESC);
       second.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
           	Defines.clsClassName = SelFuncForm.class ;
               Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
               startActivityForResult(myIntent, 0);  
              finish();
           overridePendingTransition(0, 0);
          }
       });

       final EditText permit = (EditText) findViewById(R.id.editTextPermit);

       Button scan = (Button) findViewById(R.id.buttonScan);
       scan.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   IntentIntegrator integrator = new IntentIntegrator(PermitTestForm.this); 
        	   //integrator.initiateScan();
        	   integrator.initiateScan(IntentIntegrator.ONE_D_CODE_TYPES);
           }          
       });

       Button enter = (Button) findViewById(R.id.buttonEnter);
       enter.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   	String newString = permit.getText().toString();
        	   	WorkingStorage.StoreCharVal(Defines.PrintPermitVal, newString, getApplicationContext());
            	Defines.clsClassName = ViolateForm.class ;
                Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
                startActivityForResult(myIntent, 0);  
               finish();
            overridePendingTransition(0, 0);
           }
       });

  	   Runnable mFocusRunnable = new Runnable() {     
			public void run() 
			{         			    
				permit.requestFocus();    
			}
		}; 

		Handler mHandler = new Handler();
		mHandler.postDelayed(mFocusRunnable, 50); 
       
       permit.setOnKeyListener(new View.OnKeyListener() {     	  
       	  public boolean onKey(View arg0, int arg1, KeyEvent event) {
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&
     				  (arg1 == KeyEvent.KEYCODE_ENTER)   )
     			  { 
     			  	String newString = permit.getText().toString();
     			  	WorkingStorage.StoreCharVal(Defines.PrintPermitVal, newString, getApplicationContext());
                 	Defines.clsClassName = ViolateForm.class ;
                    Intent myIntent = new Intent(arg0.getContext(), SwitchForm.class);
                    startActivityForResult(myIntent, 0);
     			    finish();
     			    overridePendingTransition(0, 0);
     			    return true;
     			  }
   		  		return false;
       }
     } );
	}

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {   
 	   IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);   
 	   if (scanResult != null) {  
 		   String Danman = "";
 		   Danman = scanResult.getContents();
 		  //Messagebox.Message(Danman,getApplicationContext());
 		   EditText permit = (EditText) findViewById(R.id.editTextPermit);   
           permit.setText(Danman);
           TextView PermitMessage = (TextView) findViewById(R.id.TextViewPermit);
           if (Danman.equals("3842"))
           {
        	   PermitMessage.setText("    VALID - SOUTH LOT");
           }
           else
           {
        	   PermitMessage.setText("    !!INVALID PERMIT!!");
           }
 		   // handle scan result   
 		}   // else continue with any other code you need in the method   ... }
    }*/
	}
}