package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.app.ActivityGroup;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class PrintForm extends ActivityGroup {
	private TextView TextUpdate;
	private TextView TextCitation; 
	
	BluetoothSocket tmp = null;
	Boolean BTConnected = false;
	Handler mHandler = new Handler();
	String message = "";
	int PicturesPrinted = 0;
	
	Runnable mConnectBT = new Runnable() 
	{     
		public void run() 
	 	{         			    
			if (BTConnected == false)
        	{
				SystemIOFile.SavePrintLog("Location 101", getApplicationContext());
				String BTAddress = "";
        		try 
        		{
           			FileInputStream in = openFileInput("PRINTER.TXT");
           			SystemIOFile.SavePrintLog("Location 102", getApplicationContext());
        			if (in!=null)
        			{
        				InputStreamReader tmp = new InputStreamReader(in);
        				BufferedReader reader = new BufferedReader(tmp);
        				String str;
        				StringBuffer buf= new StringBuffer();
        				while ((str = reader.readLine())!= null)
        				{
        					buf.append(str+"\n");
        					BTAddress = str;
        				}
        				in.close();
        				SystemIOFile.SavePrintLog("Location 103", getApplicationContext());
        	  			//Toast.makeText(getApplicationContext(), buf.toString(), 2000).show();
        	  			//Toast.makeText(getApplicationContext(), BTAddress, 2000).show();
        			}

        		}  
        		catch(Throwable t) 
        		{ 
        			SystemIOFile.SavePrintLog("Location 104", getApplicationContext());
        			Toast.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000).show();
        		}   
        		
        		//String BTAddress = "00:12:F3:11:B9:40";
        		//String BTAddress = "00:12:F3:0A:BC:CC";
        		SystemIOFile.SavePrintLog("Location 105", getApplicationContext());
        		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        		SystemIOFile.SavePrintLog("Location 106", getApplicationContext());
        		UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        		SystemIOFile.SavePrintLog("Location 107", getApplicationContext());
        		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BTAddress);
        		SystemIOFile.SavePrintLog("Location 108", getApplicationContext());
        		try 
        		{
        			tmp   = InsecureBluetooth.createRfcommSocketToServiceRecord(device, MY_UUID, true);
        			SystemIOFile.SavePrintLog("Location 109", getApplicationContext());
        		} 
        		catch (IOException e) 
        		{
        			// TODO Auto-generated catch block
        			SystemIOFile.SavePrintLog("Location 110", getApplicationContext());
        			e.printStackTrace();
        		} 
        		try 
        		{
        			SystemIOFile.SavePrintLog("Location 111", getApplicationContext());
        			tmp.connect();
        			SystemIOFile.SavePrintLog("Location 112", getApplicationContext());
        			BTConnected = true;
        			SystemIOFile.SavePrintLog("Location 113", getApplicationContext());
        			mHandler.postDelayed(mPrintThis, 1000);
        			SystemIOFile.SavePrintLog("Location 114", getApplicationContext());

        		} 
        		catch (IOException e) 
        		{
        			// TODO Auto-generated catch block
        			SystemIOFile.SavePrintLog("Location 115", getApplicationContext());
        			SystemIOFile.SavePrintLog(e.getLocalizedMessage(), getApplicationContext());
        			SystemIOFile.SavePrintLog(e.getMessage(), getApplicationContext());
        			//Messagebox.Message("!Printing Error Detected! Sending error log to Clancy. Phone will most likely freeze-up. Please reboot the phone by holding down the power button. Please reboot the printer by holding down the power button as well.", getApplicationContext());
        			e.printStackTrace();	
            		SystemIOFile.SavePrintLog("Location Debug 1", getApplicationContext());
            		TextUpdate.setText("Could not connect to the printer.");
            		Button tryAgain = (Button) findViewById(R.id.buttonTryAgain);
            		tryAgain.setVisibility(View.VISIBLE);
            		Button skip = (Button) findViewById(R.id.buttonSkip);
            		skip.setVisibility(View.VISIBLE);
            	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
            	    {
            	    	TextUpdate.setText("No se pudo conectar a la impresora.");
            	    	tryAgain.setText("Intentar de nuevo");
            	    	skip.setText("Omitir");
            	    }             		
            		SystemIOFile.SavePrintLog("Location Debug 2", getApplicationContext());

        		}
        	}//if (BTConnected == false)
		}
	}; 

	Runnable mConnectBTWithOldSupport = new Runnable() {     
		public void run() 
	 	{         			    
			SystemIOFile.SavePrintLog("Location 201", getApplicationContext());
			if (BTConnected == false)
        	{
				SystemIOFile.SavePrintLog("Location 202", getApplicationContext());
				String BTAddress = "";
        		try {
        			SystemIOFile.SavePrintLog("Location 203", getApplicationContext());
           			FileInputStream in = openFileInput("PRINTER.TXT");
        			if (in!=null)
        			{
        				InputStreamReader tmp = new InputStreamReader(in);
        				BufferedReader reader = new BufferedReader(tmp);
        				String str;
        				StringBuffer buf= new StringBuffer();
        				while ((str = reader.readLine())!= null)
        				{
        					buf.append(str+"\n");
        					BTAddress = str;
        				}
        				in.close();
        				SystemIOFile.SavePrintLog("Location 204", getApplicationContext());
        	  			//Toast.makeText(getApplicationContext(), buf.toString(), 2000).show();
        	  			//Toast.makeText(getApplicationContext(), BTAddress, 2000).show();
        			}

        		}  
        		catch(Throwable t) { 
        			SystemIOFile.SavePrintLog("Location 205", getApplicationContext());
        			Toast
        			.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
        			.show();
        		}   
        		
        	//String BTAddress = "00:12:F3:11:B9:40";
            //String BTAddress = "00:12:F3:0A:BC:CC";
        	SystemIOFile.SavePrintLog("Location 206", getApplicationContext());
        	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        	SystemIOFile.SavePrintLog("Location 207", getApplicationContext());
//        	UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        	SystemIOFile.SavePrintLog("Location 208", getApplicationContext());
        	BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BTAddress);
        	SystemIOFile.SavePrintLog("Location 209", getApplicationContext());
        	Method m;
			try {
				SystemIOFile.SavePrintLog("Location 210", getApplicationContext());
				m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
				SystemIOFile.SavePrintLog("Location 211", getApplicationContext());
	           	tmp = (BluetoothSocket) m.invoke(device, 1);
	           	SystemIOFile.SavePrintLog("Location 212", getApplicationContext());
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				SystemIOFile.SavePrintLog("Location 213", getApplicationContext());
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				SystemIOFile.SavePrintLog("Location 214", getApplicationContext());
				e1.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				SystemIOFile.SavePrintLog("Location 215", getApplicationContext());
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				SystemIOFile.SavePrintLog("Location 216", getApplicationContext());
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				SystemIOFile.SavePrintLog("Location 217", getApplicationContext());
				e.printStackTrace();
			}
           	
			try {
				SystemIOFile.SavePrintLog("Location 218", getApplicationContext());
				tmp.connect();
				SystemIOFile.SavePrintLog("Location 219", getApplicationContext());
				BTConnected = true;
				SystemIOFile.SavePrintLog("Location 220", getApplicationContext());
				mHandler.postDelayed(mPrintThis, 1000);
				SystemIOFile.SavePrintLog("Location 221", getApplicationContext());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				SystemIOFile.SavePrintLog("Location 222", getApplicationContext());
				e.printStackTrace();
			}
        	}


		}
	}; 

	Runnable mPrintThis = new Runnable() {     
		public void run() 
	 	{         			    
			SystemIOFile.SavePrintLog("Location 301", getApplicationContext());
			if (BTConnected == true)
        	{
        		OutputStream outputStream = null;
				try {
					SystemIOFile.SavePrintLog("Location 302", getApplicationContext());
					outputStream = tmp.getOutputStream();
					SystemIOFile.SavePrintLog("Location 303", getApplicationContext());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					SystemIOFile.SavePrintLog("Location 304", getApplicationContext());
					e.printStackTrace();
				}  
		        
				//message = ReadLayout.PrintALittleBit("PLAYOUT.A", getApplicationContext());
				if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.VehiclePrintoutMenu))
				{
					SystemIOFile.SavePrintLog("Location 305", getApplicationContext());
					message = VehicleHistoryForm.PrintVehicleHistory(getApplicationContext(), outputStream);
				}
				else
				{
					SystemIOFile.SavePrintLog("Location 306", getApplicationContext());
					message = ReadLayout.PrintALittleBit("PLAYOUT.A", getApplicationContext(), outputStream);
				}
				if (WorkingStorage.GetCharVal(Defines.PrintPic1Val, getApplicationContext()).equals("YES"))
				{
					SystemIOFile.SavePrintLog("Location 307", getApplicationContext());
					if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ReprintMenu))
					{
						SystemIOFile.SavePrintLog("Location 308", getApplicationContext());
						if (SystemIOFile.exists("REPRINT1.JPG"))
		    		  	{
							SystemIOFile.SavePrintLog("Location 309", getApplicationContext());
							PrintPictureFast.PrintPicturesFast("REPRINT1.JPG", getApplicationContext(), outputStream);
							SystemIOFile.SavePrintLog("Location 310", getApplicationContext());
		    		  	}
					}
					else
					{
		        		if (SystemIOFile.exists(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-1.JPG"))
		    		  	{
		        			SystemIOFile.SavePrintLog("Location 311", getApplicationContext());
		        			PrintPictureFast.PrintPicturesFast(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-1.JPG", getApplicationContext(), outputStream);
		        			SystemIOFile.SavePrintLog("Location 312", getApplicationContext());
		    		  	}
					}
				}
				String tempstring = WorkingStorage.GetCharVal(Defines.PrintPic2Val, getApplicationContext());
				SystemIOFile.SavePrintLog("Location 313", getApplicationContext());
				if (WorkingStorage.GetCharVal(Defines.PrintPic2Val, getApplicationContext()).equals("YES"))
				{
					SystemIOFile.SavePrintLog("Location 314", getApplicationContext());
					if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ReprintMenu))
					{
		        		if (SystemIOFile.exists("REPRINT2.JPG"))
		    		  	{
		        			SystemIOFile.SavePrintLog("Location 315", getApplicationContext());
		        			PrintPictureFast.PrintPicturesFast("REPRINT2.JPG", getApplicationContext(), outputStream);
		    		  	}
					}
					else
					{
		        		if (SystemIOFile.exists(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-2.JPG"))
		    		  	{
		        			SystemIOFile.SavePrintLog("Location 316", getApplicationContext());
		        			PrintPictureFast.PrintPicturesFast(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-2.JPG", getApplicationContext(), outputStream);
		        			SystemIOFile.SavePrintLog("Location 317", getApplicationContext());
		    		  	}
					}
				}
				if (WorkingStorage.GetCharVal(Defines.PrintPic3Val, getApplicationContext()).equals("YES"))
				{
					SystemIOFile.SavePrintLog("Location 318", getApplicationContext());
					if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ReprintMenu))
					{
		        		if (SystemIOFile.exists("REPRINT3.JPG"))
		    		  	{
		        			//PrintPictureFast.PrintPicturesFast("REPRINT3.JPG", getApplicationContext(), outputStream);
		        			SystemIOFile.SavePrintLog("Location 319", getApplicationContext());
		        			PrintPictureFast.PrintPicturesFast("REPRINT3.JPG", getApplicationContext(), outputStream);
		        			SystemIOFile.SavePrintLog("Location 320", getApplicationContext());
		    		  	}
					}
					else
					{
		        		if (SystemIOFile.exists(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-3.JPG"))
		    		  	{
		        			SystemIOFile.SavePrintLog("Location 321", getApplicationContext());
		        			PrintPictureFast.PrintPicturesFast(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-3.JPG", getApplicationContext(), outputStream);
		        			SystemIOFile.SavePrintLog("Location 322", getApplicationContext());
		    		  	}
					}
				}
        		//if (SystemIOFile.exists(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-3.JPG"))
    		  	//{
    		  		//PrintPicture.PrintPictures(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-3.JPG", getApplicationContext(), outputStream);
    		  	//}
        		//try {
				//	outputStream.write(message.getBytes());
				//} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
        	}
        	if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.VehiclePrintoutMenu))
        		mHandler.postDelayed(mCloseBT, 10000);
        	else
        	{
	    		//increase the close delay if multiple pictures are being printed
        		String PrintPic1 = WorkingStorage.GetCharVal(Defines.PrintPic1Val, getApplicationContext()); 
	    		String PrintPic2 = WorkingStorage.GetCharVal(Defines.PrintPic2Val, getApplicationContext()); 
	    		String PrintPic3 = WorkingStorage.GetCharVal(Defines.PrintPic3Val, getApplicationContext());
	    		int TotalDelay = 6500;
	    		if (PrintPic1.equals("YES"))
	    		{
	    			PicturesPrinted++;
	    		}
	    		if (PrintPic2.equals("YES"))
	    		{
	    			PicturesPrinted++;
	    		}
	    		if (PrintPic3.equals("YES"))
	    		{
	    			PicturesPrinted++;
	    		}	    		
	    		if (PicturesPrinted == 1)	    			
	    			TotalDelay += 8500;
	    		if (PicturesPrinted == 2)	    			
	    			TotalDelay += 20000;
	    		if (PicturesPrinted == 3)	    			
	    			TotalDelay += 21000;
	    		
	    		if (TotalDelay > 6500)
	    			Toast.makeText(getApplicationContext(), Integer.toString(TotalDelay), Toast.LENGTH_LONG).show();
	    		
	    		mHandler.postDelayed(mCloseBT, TotalDelay);
	    		
        	}
		}
	};

	Runnable mCloseBT = new Runnable() {     
		public void run() 
	 	{         			    
			SystemIOFile.SavePrintLog("Location 401", getApplicationContext());
			if (BTConnected == true)
        	{
			try {
				SystemIOFile.SavePrintLog("Location 401", getApplicationContext());
				tmp.close();
				SystemIOFile.SavePrintLog("Location 402", getApplicationContext());
				tmp = null;
				SystemIOFile.SavePrintLog("Location 403", getApplicationContext());
				BTConnected = false;
				SystemIOFile.SavePrintLog("Location 404", getApplicationContext());
				if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.VehiclePrintoutMenu))
				{
					SystemIOFile.SavePrintLog("Location 405", getApplicationContext());
					mHandler.postDelayed(mExitForm, 10000);
				}
				else if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ReprintMenu))
		        {
					SystemIOFile.SavePrintLog("Location 406", getApplicationContext());
					mHandler.postDelayed(mExitSelFunc, 1000);
		        }
		        else
		        {
		        	SystemIOFile.SavePrintLog("Location 407", getApplicationContext());
		        	mHandler.postDelayed(mExitForm, 1000);
		        }

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}
		}
	};
 	
	
	private Runnable mExitForm = new Runnable() {     
		public void run() 
		{         			    								
			//UploadData.UploadDataFiles(getApplicationContext());
			SystemIOFile.Delete("PRIN.R"); // DELETE THE LOG BECUASE WE ARE SEARCHING FOR A PRINTER HANG ONLY
			getWindow().clearFlags(LayoutParams.FLAG_KEEP_SCREEN_ON); 
	        if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("ONLINE"))
	        {
				Defines.clsClassName = UploadBackgroundForm.class ;
		        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		        startActivityForResult(myIntent, 0);  
		    }	
	        else
	        {
				Defines.clsClassName = SelFuncForm.class ;
		        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		        startActivityForResult(myIntent, 0);  
	        }
	        finish();
	        overridePendingTransition(0, 0);	        	
		}
	}; 
	
	private Runnable mExitSelFunc = new Runnable() {     
		public void run() 
		{         			    								
			//UploadData.UploadDataFiles(getApplicationContext());
	        SystemIOFile.Delete("PRIN.R"); // DELETE THE LOG BECUASE WE ARE SEARCHING FOR A PRINTER HANG ONLY
			getWindow().clearFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
			Defines.clsClassName = SelFuncForm.class ;
	        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	        startActivityForResult(myIntent, 0);  
	        finish();
	        overridePendingTransition(0, 0);	        	
		}
	}; 	
	
	private Runnable mDetectBT = new Runnable() {     
		public void run() 
		{         			    								
    		String BTAddress = "";
    		try {
        		SystemIOFile.SavePrintLog("Location 2", getApplicationContext());
    			FileInputStream in = openFileInput("PRINTER.TXT");
    			if (in!=null)
    			{
    				InputStreamReader tmp = new InputStreamReader(in);
    				BufferedReader reader = new BufferedReader(tmp);
    				String str;
    				StringBuffer buf= new StringBuffer();
    				while ((str = reader.readLine())!= null)
    				{
    					buf.append(str+"\n");
    					BTAddress = str;
    				}
    				in.close();
    			}
    			SystemIOFile.SavePrintLog("Location 3", getApplicationContext());
    		}  
    		catch(Throwable t) { 
    			SystemIOFile.SavePrintLog("Location 4", getApplicationContext());
    			Toast
    			.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
    			.show();
    		}   
    		String DevAvailable = "";
    		String DevName = android.os.Build.MODEL;
        	if (android.os.Build.MODEL.equals("SpringBoard"))
        	{
        		SystemIOFile.SavePrintLog("Location 5", getApplicationContext());
        		DevAvailable = CheckBTDeviceWithOldSupport.ScanDeviceWithOldSupport(BTAddress, getApplicationContext());
        	}
        	else
        	{
        		SystemIOFile.SavePrintLog("Location 6", getApplicationContext());
        		//10-10-2012 Changing routine of ScanDevice to not call the Connect and Disconnect Routines. 
        		//The feedback from the Prin files shows that they do connect and disconnect just fine, then the 
        		//hang occurs occasionally when attempting to connect again. Originally this ensured that we 
        		//new we could communicate with the printer, but it the occasional print issues are not good.
        		//In the CheckBTDevice class it is step 4 where the issue arises.
        		//I will need to add some traps in this program instead of the CheckBTDevice class related to the Connect and Disconnect.
        		DevAvailable = CheckBTDevice.ScanDevice(BTAddress, getApplicationContext());
        		SystemIOFile.SavePrintLog("Location 6g", getApplicationContext());
        	}
 
       		//tvMessage.setText(DevAvailable);
        	if (DevAvailable.equals("SUCCESS"))
        	{
        		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
        		SystemIOFile.SavePrintLog("Location 7", getApplicationContext());
        		TextUpdate.setText("Printing Citation...");
        		if (android.os.Build.MODEL.equals("SpringBoard"))
        		{
        			SystemIOFile.SavePrintLog("Location 8", getApplicationContext());
        			mHandler.postDelayed(mConnectBTWithOldSupport, 20);
        		}
        		else
        		{
        			SystemIOFile.SavePrintLog("Location 9", getApplicationContext());
            		mHandler.postDelayed(mConnectBT, 20);        			
        		}
        	}
        	else
        	{
        		SystemIOFile.SavePrintLog("Location 10", getApplicationContext());
        		TextUpdate.setText(DevAvailable);
        		Button tryAgain = (Button) findViewById(R.id.buttonTryAgain);
        		tryAgain.setVisibility(View.VISIBLE);
        		Button skip = (Button) findViewById(R.id.buttonSkip);
        		skip.setVisibility(View.VISIBLE);
        		SystemIOFile.SavePrintLog("Location 11", getApplicationContext());
        		//mHandler.postDelayed(mExitSelFunc, 6000);
        		
        	}
      	
		}
	}; 
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printform);              

        Boolean SavedTicket = false;
        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.TicketIssuanceMenu))
        {
        	SavedTicket = SaveTicket.SaveTickFile(getApplicationContext()); 
        }   
        if (WorkingStorage.GetCharVal(Defines.HBoxFlowVal, getApplicationContext()).equals("YES"))
        {
        	if (SavedTicket == false)
        	{
            	SaveTicket.SaveTickFile(getApplicationContext()); 
        	}
        }
        SystemIOFile.Delete("PRIN.R");
        
        TextUpdate = (TextView)findViewById(R.id.widgetPrint);
        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.VehiclePrintoutMenu))
        	TextUpdate.setText("Printing Open Citations...");
        else if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ReprintMenu))
        	TextUpdate.setText("Getting Reprint Data...");
        else
        	TextUpdate.setText("Detecting Printer...");
        
        TextCitation = (TextView)findViewById(R.id.TextCitation);
        TextCitation.setText(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()));
        
        final Button skip = (Button) findViewById(R.id.buttonSkip);
        
        final Button tryAgain = (Button) findViewById(R.id.buttonTryAgain);
        tryAgain.setVisibility(View.INVISIBLE);
        tryAgain.setOnClickListener(new View.OnClickListener() {
 	           public void onClick(View view) 
 	           {
 	        	  TextUpdate.setText("Detecting Printer...");
 	        	  tryAgain.setVisibility(View.INVISIBLE);
 	        	  skip.setVisibility(View.INVISIBLE);
 	        	  mHandler.postDelayed(mDetectBT, 100);
 	           }	          
 	    });
        

        skip.setVisibility(View.INVISIBLE);
        skip.setOnClickListener(new View.OnClickListener() {
 	           public void onClick(View view) 
 	           {
 	        	  skip.setVisibility(View.INVISIBLE);
 	        	  tryAgain.setVisibility(View.INVISIBLE);
 	        	  if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.TicketIssuanceMenu))
 	        	  {
 	        		 mHandler.postDelayed(mExitForm, 100);
 	        	  }
 	        	  else
 	        	  {
  	        		 mHandler.postDelayed(mExitSelFunc, 100);
  	        	  }  
 	           }	          
 	    });        
        
        if (SystemIOFile.exists("PRINTER.TXT")==true)
		{	     
	        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ReprintMenu))
	        {
	    		String PrintPic1 = WorkingStorage.GetCharVal(Defines.PrintPic1Val, getApplicationContext()); 
	    		String PrintPic2 = WorkingStorage.GetCharVal(Defines.PrintPic2Val, getApplicationContext()); 
	    		String PrintPic3 = WorkingStorage.GetCharVal(Defines.PrintPic3Val, getApplicationContext()); 
	        	SaveTicket.ReadReprintFile(getApplicationContext());
	    		WorkingStorage.StoreCharVal(Defines.MenuProgramVal,Defines.ReprintMenu,getApplicationContext());
	    		WorkingStorage.StoreCharVal(Defines.PrintPic1Val,PrintPic1,getApplicationContext());
	    		WorkingStorage.StoreCharVal(Defines.PrintPic2Val,PrintPic2,getApplicationContext());
	    		WorkingStorage.StoreCharVal(Defines.PrintPic3Val,PrintPic3,getApplicationContext());
	        }
	        SystemIOFile.SavePrintLog("Printer Debug Version 4", getApplicationContext());
        	mHandler.postDelayed(mDetectBT, 100);
		}
		else
		{
	        TextUpdate.setText("No Paired Printer Detected!");
	        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ReprintMenu))
	        {
	        	mHandler.postDelayed(mExitSelFunc, 6000);
	        }
	        else
	        {
	        	if (WorkingStorage.GetCharVal(Defines.GPSSurveyVal, getApplicationContext()).trim().equals("GPS"))
	        	{
	        		mHandler.postDelayed(mExitForm, 6);
	        	}
	        	else
	        	{
	        		mHandler.postDelayed(mExitForm, 6000);	
	        	}
	        	
	        }
		}
	}
}