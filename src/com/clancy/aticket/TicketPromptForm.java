package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TicketPromptForm extends ActivityGroup {
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticketpromptform);

        final EditText Reason = (EditText) findViewById(R.id.editTextReason);
        Reason.requestFocus();
             
        String FormatDue = "";
        FormatDue = WorkingStorage.GetCharVal(Defines.PrintFeeVal, getApplicationContext());
        
        TextView tvMessage = (TextView) findViewById(R.id.labelMessage);
        tvMessage.setText("Unpaid: $" +FormatDue + " - Write Ticket?");
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   	CustomKeyboard.PickAKeyboard(Reason, "FULL", getApplicationContext(), keyboardView);
   	   	if (savedInstanceState == null)
   	   	{
      		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
      		Reason.selectAll();
   	   	}
   	   	
   	   	Reason.setOnTouchListener(new View.OnTouchListener(){ 
   		@Override
   		public boolean onTouch(View v, MotionEvent event) {
       		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
       		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
       		return true;
   		} 
   		});   	
   	   	
    	   Reason.addTextChangedListener(new TextWatcher()
       	   {
       		   @Override
       		   public void afterTextChanged(Editable s) {
    			// TODO Auto-generated method stub
       			   String WhereToGo = "";
         	       if(Reason.getText().toString().equals("Y"))
          	       {
         	    	  WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "YES", getApplicationContext());
         	          WhereToGo = "TICKET";
          	       }   	
         	       else if(Reason.getText().toString().equals("M"))
          	       {
         	    	  WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "NO", getApplicationContext());
         	    	  WorkingStorage.StoreCharVal(Defines.HBoxReasonVal, "M", getApplicationContext());
         	          WhereToGo = "BACK";
          	       }     
         	       else if(Reason.getText().toString().equals("O"))
         	       {
        	    	  WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "NO", getApplicationContext());
        	    	  WorkingStorage.StoreCharVal(Defines.HBoxReasonVal, "O", getApplicationContext());
        	          WhereToGo = "BACK";
         	       } 
         	       else if(Reason.getText().toString().equals("B"))
        	       {
         	    	   WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "NO", getApplicationContext());
         	    	   WorkingStorage.StoreCharVal(Defines.HBoxReasonVal, "B", getApplicationContext());
         	    	   WhereToGo = "BACK";
        	       } 
         	       else if(Reason.getText().toString().equals("V"))
         	       {
         	    	   WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "NO", getApplicationContext());
         	    	   WorkingStorage.StoreCharVal(Defines.HBoxReasonVal, "Z", getApplicationContext());
         	    	   WhereToGo = "BACK";
         	       } 
         	       else if(Reason.getText().toString().equals("P"))
         	       {
        	    	  WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "NO", getApplicationContext());
        	    	  WorkingStorage.StoreCharVal(Defines.HBoxReasonVal, "1", getApplicationContext());
        	          WhereToGo = "BACK";
         	       } 
         	       if (WhereToGo.equals("BACK"))
         	       {
         			  Defines.clsClassName = HonorLotForm.class ;
        			  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        		   	  startActivityForResult(myIntent, 0);
        		   	  finish();
        		   	  overridePendingTransition(0, 0);         	    	   
         	       }
         	       if (WhereToGo.equals("TICKET"))
         	       {
                       IssueHonorTicket(getApplicationContext());
         	       }
       		   }

       		   @Override
    			public void beforeTextChanged(CharSequence s, int start, int count,
    				int after) {
       				   
    			// TODO Auto-generated method stub
    			
       		   }
       		   @Override
       		   public void onTextChanged(CharSequence s, int start, int before,
    				int count) {
    			// TODO Auto-generated method stub
    			
       		   }   		  
       	   });
	   
	}
	  
	public void IssueHonorTicket(Context dan) 
	{
		GetDate.GetDateTime(dan);  
		  
   	   	if (NextCiteNum.GetNextCitationNumber(0,getApplicationContext())== true) //if true the unit is out of citations
   	   	{
   	   		Messagebox.Message("Out Of Electronic Citation Numbers, Call Clancy 303-759-4276", getApplicationContext());
   	   	}
   	   	else
   	   	{
     	   	WorkingStorage.StoreLongVal(Defines.PictureTakenVal, 0, getApplicationContext());
    		WorkingStorage.StoreCharVal(Defines.LatitudeVal, "", getApplicationContext());
    	    WorkingStorage.StoreCharVal(Defines.LongitudeVal, "", getApplicationContext());
    	      
       	   	WorkingStorage.StoreCharVal(Defines.SaveTimeStartVal, WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext()), getApplicationContext());
       	   	WorkingStorage.StoreCharVal(Defines.PrintTimeStartVal, WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()), getApplicationContext());
       	   	WorkingStorage.StoreCharVal(Defines.SavePermitVal, "", getApplicationContext());
       	   	WorkingStorage.StoreCharVal(Defines.PrintPermitVal, "", getApplicationContext());
    	       	       	   
       	   	WorkingStorage.StoreCharVal(Defines.SaveVMultiErrorVal, "", getApplicationContext());
       	   	WorkingStorage.StoreCharVal(Defines.PlateTempMessageVal, "", getApplicationContext());
	        try
	        {
	            if (!Defines.locationManager.equals(null))
	            {
	            	if (!Defines.locationListener.equals(null))
	            	{
	            		Defines.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1.0f, Defines.locationListener); //start GPS to obtain signal            		
	            	}
	            }
	        } catch(NullPointerException e)
	        {
	        }
	        finally
	        {
	        	
	        }
		   //Defines.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1.0f, Defines.locationListener); //start GPS to obtain signal
		   WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, 0, getApplicationContext());
		   WorkingStorage.StoreCharVal(Defines.SecondTicketVal, "NO", getApplicationContext());
		   WorkingStorage.StoreCharVal(Defines.PreviousPrintVal, "*START", getApplicationContext());
		   WorkingStorage.StoreLongVal(Defines.TicketVoidFlag, 1, getApplicationContext());
		   //MiscFunctions.EraseVoidedPictures(getApplicationContext());
		   if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
		   {
			   Intent myIntent = new Intent(dan, SwitchForm.class);
			   startActivityForResult(myIntent, 0);
			   finish();
			   overridePendingTransition(0, 0);           			
		   }
	   
   	   	}
	  }	
}