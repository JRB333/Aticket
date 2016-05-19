package com.clancy.aticket;

import java.io.File;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class UploadForm extends ActivityGroup {
	
	private Handler mHandler = new Handler(); 
	private Boolean FileSent = false;
	private	String HTTPageFilesize = "";
	private	String TempUploadIpAddress = "";
	private String CurrentUnitNumber = "";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadform);              
       	
        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
        TextUpdate.setText("Uploading Data...");       
        if (savedInstanceState==null) //Must do this to ensure that rotation doesn't jack everything up
        {
            mHandler.postDelayed(mCheckWebsite, 20);   	
        }
	}
	
	private Runnable mCheckWebsite = new Runnable() {     
		public void run() 
		{         	
	        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
	        TextUpdate.setText("Checking Website");
	       	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+"/connected.asp",getApplicationContext());
	        if (HTTPageFilesize.length()==4)
	        {
	        	TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext()).trim();
	        }	
	        else
	        {
	        	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.AlternateIPAddress, getApplicationContext())+"/connected.asp", getApplicationContext());
	            if (HTTPageFilesize.length()==4)
	            {
	            	TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.AlternateIPAddress, getApplicationContext()).trim();
	            }            
	        }
	        if (TempUploadIpAddress.equals(""))
	        {
	        	 mHandler.postDelayed(mCheckFailed, 20);
	        }
	        else
	        {
	        	GetDate.GetDateTime(getApplicationContext());
	        	CurrentUnitNumber = WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.CheckTimeVal,getApplicationContext());
	        	mHandler.postDelayed(mUploadTickFile, 20);
	        }

		}
	}; 
	
	private Runnable mCheckFailed= new Runnable() {     
		public void run() 
		{         	
	        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
	        TextUpdate.setText("Could Not Upload.");
	        mHandler.postDelayed(mExit, 2000);
		}
	}; 

	private Runnable mUploadTickFile= new Runnable() {     
		public void run() 
		{         	
	        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
	        TextUpdate.setText("Uploading Ticket.r");
	        FileSent = HTTPFileTransfer.UploadFileBinary("TICKET.R", "http://" + TempUploadIpAddress + "/DemoTickets/TICK" + CurrentUnitNumber.toUpperCase() + ".R", getApplicationContext());
	        if (FileSent == true)
	        {
	            SystemIOFile.Delete("TICKET.R");
	            FileSent = HTTPFileTransfer.UploadFileBinary("CLANCY.J", "http://" + TempUploadIpAddress + "/DemoTickets/CLAN" + CurrentUnitNumber.toUpperCase() + ".R", getApplicationContext());
	        }
	        mHandler.postDelayed(mUploadAddiFile, 20);
		}
	}; 
	
	private Runnable mUploadAddiFile= new Runnable() {     
		public void run() 
		{         	
	        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
	        TextUpdate.setText("Uploading Addi.r");
	        FileSent = HTTPFileTransfer.UploadFileBinary("ADDI.R", "http://" + TempUploadIpAddress + "/DemoTickets/ADDI" + CurrentUnitNumber.toUpperCase() + ".R", getApplicationContext());
	        if (FileSent == true)
	        {
	            SystemIOFile.Delete("ADDI.R");            
	        } 
	        mHandler.postDelayed(mUploadLocaFile, 20);
		}
	};	

	private Runnable mUploadLocaFile= new Runnable() {     
		public void run() 
		{         	
	        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
	        TextUpdate.setText("Uploading Loca.r");
	        FileSent = HTTPFileTransfer.UploadFileBinary("LOCA.R", "http://" + TempUploadIpAddress + "/DemoTickets/LOCA" + CurrentUnitNumber.toUpperCase() + ".R", getApplicationContext());
	        if (FileSent == true)
	        {
	            SystemIOFile.Delete("LOCA.R");            
	        } 
	        mHandler.postDelayed(mUploadVoidFile, 20);
		}
	};	
	private Runnable mUploadVoidFile= new Runnable() {     
		public void run() 
		{         	
	        if (SystemIOFile.exists("VOID.R"))
	        {
	        	TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
	        	TextUpdate.setText("Uploading Void.r");
	        	FileSent = HTTPFileTransfer.UploadFileBinary("VOID.R", "http://" + TempUploadIpAddress + "/DemoTickets/VOID" + CurrentUnitNumber.toUpperCase() + ".R", getApplicationContext());
	        	if (FileSent == true)
	        	{
	        		SystemIOFile.Delete("VOID.R");            
	        	}   
	        }
	        mHandler.postDelayed(mUploadHittFile, 20);
		}
	};	

	private Runnable mUploadHittFile= new Runnable() {     
		public void run() 
		{         	
			if (SystemIOFile.exists("HITT.R"))
			{
				TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
				TextUpdate.setText("Uploading Hitt.r");
				FileSent = HTTPFileTransfer.UploadFileBinary("HITT.R", "http://" + TempUploadIpAddress + "/DemoTickets/HITT" + CurrentUnitNumber.toUpperCase() + ".R", getApplicationContext());
				if (FileSent == true)
				{
					SystemIOFile.Delete("HITT.R");            
				} 
			}
	        mHandler.postDelayed(mUploadChecFile, 20);
		}
	};	
	
	private Runnable mUploadChecFile= new Runnable() {     
		public void run() 
		{         	
			if (SystemIOFile.exists("CHEC.R"))
			{
				TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
				TextUpdate.setText("Uploading Check.r");
				FileSent = HTTPFileTransfer.UploadFileBinary("CHEC.R", "http://" + TempUploadIpAddress + "/DemoTickets/CHEC" + CurrentUnitNumber.toUpperCase() + ".R", getApplicationContext());
				if (FileSent == true)
				{
					SystemIOFile.Delete("CHEC.R");            
				} 
			}
	        mHandler.postDelayed(mUploadPictFile, 20);
		}
	};
	
	private Runnable mUploadPictFile= new Runnable() {     
		public void run() 
		{         	
			if (SystemIOFile.exists("PICT.R"))
			{
				TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
				TextUpdate.setText("Uploading Pict.r");
				FileSent = HTTPFileTransfer.UploadFileBinary("PICT.R", "http://" + TempUploadIpAddress + "/DemoTickets/PICT" + CurrentUnitNumber.toUpperCase() + ".R", getApplicationContext());
				if (FileSent == true)
				{
					SystemIOFile.Delete("PICT.R");            
				}
			}
	        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
	        TextUpdate.setText("Uploading Pictures");
	        mHandler.postDelayed(mUploadJPGFile, 20);
		}
	};
	
	private Runnable mUploadJPGFile= new Runnable() {     
		public void run() 
		{         	
	        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
	        TextUpdate.setText("Uploading Pictures");
			File dir = new File("/data/data/com.clancy.aticket/files/");
	  		String[] dirList = dir.list();
	  		int blah = dirList.length;
	  		int i;
	  		String tempString = ""; 
	  		for(i=0;  i < blah; i++)
	  		{   
	  			if (dirList[i].toUpperCase().contains("JPG"))
	  			{
	  				tempString = dirList[i];
	    		    FileSent = HTTPFileTransfer.UploadJPGBinary(tempString, "http://" + TempUploadIpAddress + "/DemoTickets/pictures/"+tempString, getApplicationContext());
	    		    if (FileSent == true)
	    		    {
	    		    	EraseFile("/data/data/com.clancy.aticket/files/"+tempString);
	    		    }
	    		    	
	  			}
	  		} 	
	  		mHandler.postDelayed(mForceUnload, 20);
		}
	};	
	
	private Runnable mForceUnload= new Runnable() {     
		public void run() 
		{         	
	        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
	        TextUpdate.setText("Processing Citations...");
			HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+ TempUploadIpAddress +"/unload.asp", getApplicationContext());
			mHandler.postDelayed(mExit, 20);
		}
	}; 		
	
	private Runnable mExit= new Runnable() {     
		public void run() 
		{         	
			Defines.clsClassName = SelFuncForm.class ;
	        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	        startActivityForResult(myIntent, 0);  
	        finish();
	        overridePendingTransition(0, 0);	
		}
	}; 	
	
	  static public int EraseFile(String filename) 
	  {

	 	int ReturnSize = 0;
	 	File file = new File(filename);
	 	//File file = new File(filename);
		if (!file.exists() || !file.isFile()) 
		{	    	
			ReturnSize = 0;
			file = null;
			return ReturnSize;
		}
		file.delete();
		file = null;
		return ReturnSize;
	  }	 

}
