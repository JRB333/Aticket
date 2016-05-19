package com.clancy.aticket;


import java.io.File;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class GetBootForm extends ActivityGroup {
	private Handler mHandler = new Handler(); 
	private TextView TextUpdate; 
	private int i; 
	private String [] dbHoldValues;
	private HTTPFileTransfer writetest;

	private Runnable mHTTPConnectRunnable = new Runnable() {     
		public void run() 
		{         			    
	        
			String HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+"/connected.asp", getApplicationContext());
	        if (HTTPageFilesize.length()==4)
	        {
        		mHandler.postDelayed(mWaitRunnable, 100); 	
	        }
	        else
	        {
	        	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+"/connected.asp", getApplicationContext());
	        	if (HTTPageFilesize.length()==4)
	        	{
	        		mHandler.postDelayed(mWaitRunnable, 100);
	        	}else
	        	{
		        	Messagebox.Message("Unable to Connect to Site", getApplicationContext());
		        	writetest = null;	        		
			     	if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
			    	{
					   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
					   startActivityForResult(myIntent, 0);
					   finish();
					   overridePendingTransition(0, 0);    
			    	}
			     	else
			     	{
			     		finish();
						overridePendingTransition(0, 0); 
			     	}
	        	}
	        }
		}
	}; 
	
	private Runnable mWaitRunnable = new Runnable() {     
		public void run() 
		{         			    
	        	if (dbHoldValues[i].equals("DONE")){
	        		writetest = null;
	        		//Now try to upload any files if there is a ticket.r
	                File f;
	                //f=new File("/data/data/com.clancy.aticket/files/","VOID.R");
	                f=new File("/data/data/com.clancy.aticket/files/","TICKET.R" ); //CHANGE TO TICKET.R AFTER TESTING
	                if(f.exists())
	                {
	                	f = null;
	                	//UploadData.UploadDataFiles(getApplicationContext());
	        			Defines.clsClassName = UploadBackgroundForm.class ;
	        	        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	        	        startActivityForResult(myIntent, 0);  
	        	        overridePendingTransition(0, 0);		                	
	                }
	                // Done with the upload... move on.
			     	if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
			    	{
					   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
					   startActivityForResult(myIntent, 0);
					   finish();
					   overridePendingTransition(0, 0);    
			    	}
			     	else
			     	{
			     		finish();
						overridePendingTransition(0, 0); 
			     	}
	        	}else
	        	{		    
	        		writetest.DownloadFileBinary(dbHoldValues[i], dbHoldValues[i], getApplicationContext());

	        		TextUpdate.setText("Downloading: " + dbHoldValues[i]);
	        		i++;
	        		mHandler.postDelayed(this, 20);  		
	        	}	        
		}
	}; 
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getbootform);
        
        if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("ONLINE"))
        {
            if (savedInstanceState==null) //Must do this to ensure that rotation doesn't jack everything up
            {
                File f;
                f=new File("/data/data/com.clancy.aticket/files/","ALL.A");
                if(f.exists())
                {
                	f.delete();
                }
                f = null;
                
            	writetest = new HTTPFileTransfer();
            	dbHoldValues = new String[100];  
            	dbHoldValues[0] = new String("ALLBOOT.A");
            	dbHoldValues[1] = new String("VBOOT.T"); 
            	dbHoldValues[2] = new String("VIRTPERM.A");
            	dbHoldValues[3] = new String("CUSTOM.A");        	
            	dbHoldValues[4] = new String("PLAYOUT.A");
            	dbHoldValues[5] = new String("REASON.T");
            	dbHoldValues[6] = new String("PINFO.A");       	
            	dbHoldValues[7] = new String("VMULTI.T");
            	dbHoldValues[8] = new String("ALL.A");
            	dbHoldValues[9] = new String("EMAIL.T");
            	dbHoldValues[10] = new String("PLATDATA.T");
            	dbHoldValues[11] = new String("PERMITS.T"); 

            	if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("LACROSSE"))
            	{
            		dbHoldValues[12] = new String("19.A");
            		dbHoldValues[13] = new String("20.A");
            		dbHoldValues[14] = new String("22.A");
            		dbHoldValues[15] = new String("24.A");
                	dbHoldValues[16] = new String("DONE"); //always have this one as the last value in the array
            	}
            	else
            	{
                	dbHoldValues[12] = new String("DONE"); //always have this one as the last value in the array        		
            	}

            	mHandler.postDelayed(mHTTPConnectRunnable, 20);
            	
                TextUpdate = (TextView)findViewById(R.id.widgetRefresh);
                TextUpdate.setText("Connecting to site...");        	        	
            }        	
        }
        else
        {
	     	if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
	    	{
			   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			   startActivityForResult(myIntent, 0);
			   finish();
			   overridePendingTransition(0, 0);    
	    	}
	     	else
	     	{
	     		finish();
				overridePendingTransition(0, 0); 
	     	}
        }
	}
}

