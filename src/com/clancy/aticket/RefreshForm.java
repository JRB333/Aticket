package com.clancy.aticket;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class RefreshForm extends ActivityGroup {
	private Handler mHandler = new Handler(); 
	private TextView TextUpdate; 
	private int i; 
	private String [] dbHoldValues;
	private HTTPFileTransfer writetest;
	//String InstallParam = "application/vnd.android.package-archive";
	String InstallParam = "397745924592442843054059397747564305455145101927483845104100188639774510410046744551430541001886459239774059438739774223414118453977467440594264430548384141"; //application/vnd.android.package-archive - Perfect Match 
	
	private Runnable mHTTPConnectRunnable = new Runnable() {     
		public void run() 
		{         			    
	        
			String HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+"/connected.asp", getApplicationContext());
	        if (HTTPageFilesize.length()==4)
	        {
        		String WhatEver = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+"/unload.asp", getApplicationContext());
	        	mHandler.postDelayed(mWaitRunnable, 100);
	        }
	        else
	        {
        		Messagebox.Message("Unable to Connect to Site", getApplicationContext());
	        	writetest = null;
        		finish();
        		overridePendingTransition(0, 0);
	        }
		}
	}; 
	
	private Runnable mWaitRunnable = new Runnable() {     
		public void run() 
		{         			    
	        	if (dbHoldValues[i].equals("DONE")){
	        		writetest = null;
	        		TextUpdate.setText("Checking Aticket: ");
	        		mHandler.postDelayed(mCheckVersion, 50); 	
	        		//finish();
	        		//overridePendingTransition(0, 0);
	        	}else
	        	{		    

	        		writetest.DownloadFileBinary(dbHoldValues[i], dbHoldValues[i], getApplicationContext());

	        		TextUpdate.setText("Downloading: " + dbHoldValues[i]);
	        		i++;
	        		mHandler.postDelayed(this, 20);  		
	        	}	        
		}
	}; 
	
	private Runnable mCheckVersion = new Runnable() {     
		public void run() 
		{         			    
			TextUpdate.setText("Checking Aticket... ");
			//String AndroidBuild = HTTPFileTransfer.HTTPGetPageContent("HTTP://107.1.38.34/AndroidBuild.htm",getApplicationContext()).trim();
			String AndroidBuild = HTTPFileTransfer.HTTPGetPageContent("HTTP://www.clancyapp.com/AndroidBuild.htm",getApplicationContext()).trim();
			//AndroidBuild = "204"; //testing
			int CurrentBuild = 0;
			PackageInfo pinfo;
			try {
				pinfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
				CurrentBuild = pinfo.versionCode;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if (CurrentBuild > 0 && !AndroidBuild.equals("FAILED"))
			{
				int iAndroidBuild = Integer.valueOf(AndroidBuild);
				//int MaxBuildDiff = Integer.valueOf(WorkingStorage.GetCharVal(Defines.AndroidBuildsVal, getApplicationContext()));
				if (iAndroidBuild - CurrentBuild > 0) // force the client to update our server ;)
				{
	        		//TextUpdate.setText("Due to a change in Google Android policy, all apps must now update through Google Play. In a few seconds you will be redirected to the Clancy app on Google Play. Simply press the Update or Open button to get the latest version of the app.");
	        		//mHandler.postDelayed(mNewUpdate, 15000);
					//Ask liz to translate this: Hasa Diga Eebowai Google!
					DownloadLatestVersion(); // Put this routine back in pull our apks from our own damn servers again! Just encrypted the URL strings to get by their detector ;)
				}
				else
				{
	        		finish();
	        		overridePendingTransition(0, 0);
				}
			}
			else
			{
        		finish();
        		overridePendingTransition(0, 0);
			}
		        
		}
	}; 	
	
	
	public void DownloadLatestVersion() 
    {
		
		//String URLString = "HTTP://107.1.38.34/Aticket.apk"; !very old string
		//String URLString = "HTTP://www.clancyapp.com/Aticket.apk";
		//encrypt program is v:\foxpro5\AndroidCredentials
		//Note: Proguard doesn't compile comments into apk
		//String URLString = "295234443444328023781927192748794879487918864059442839774510405949613977459245921886405945514469192726654756430540594387414147561886397745924387"; //HTTP://www.clancyapp.com/Aticket.apk - Perfect Match
		String URLString = WorkingStorage.GetCharVal(Defines.AticketAPKDownloadValue, getApplicationContext());
        try {
        		URL url = new URL(URLString);
                URLConnection ucon = url.openConnection();
                ucon.setUseCaches(false); 
                ucon.setRequestProperty("Cache-Control", "no-cache");

                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                }
                //FileOutputStream fos = new FileOutputStream(file);
                String PATH = Environment.getExternalStorageDirectory() + "/download/";
                File file = new File(PATH);
                file.mkdirs();
                //File outputFile = new File(file, "Aticket.apk");
                File outputFile = new File(file, WorkingStorage.GetCharVal(Defines.AticketNameValue, getApplicationContext()));
                FileOutputStream fos = new FileOutputStream(outputFile);                
                fos.write(baf.toByteArray());
                fos.close(); 
        } 
		catch(FileNotFoundException e) { 
			Toast.makeText(getApplicationContext(), "Ex: "+e.toString(), 2000).show();
			//Do not display anything for this exception
		}               
		catch(Throwable t) { 
			Toast.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000).show();
		}   
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		//intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "Aticket.apk")), "application/vnd.android.package-archive");
		//intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + WorkingStorage.GetCharVal(Defines.AticketNameValue, getApplicationContext()))), "application/vnd.android.package-archive");
		//String blah = GarbleyGoop.DansRoutine(InstallParam);
		intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + 
				WorkingStorage.GetCharVal(Defines.AticketNameValue, getApplicationContext()))), 
				GarbleyGoop.DansRoutine(InstallParam));
		startActivity(intent);  
		finish();
    }
	/*Old google update routine for the Facebook/google fiasco
	 * private Runnable mNewUpdate = new Runnable() {     
		public void run() 
		{         			    
			  try 
			  {
				  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.clancy.aticket")));
			  } 
			  catch (android.content.ActivityNotFoundException anfe) 
			  {
			  } 
			finish();
	        overridePendingTransition(0, 0);
		}
	};*/ 	
	




	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refreshform);
        if (savedInstanceState==null) //Must do this to ensure that rotation doesn't jack everything up
        {
        	writetest = new HTTPFileTransfer();
        	dbHoldValues = new String[100];  
        	dbHoldValues[0] = new String("ADDRESS.A");
        	dbHoldValues[1] = new String("ALAYOUT.A"); 
        	dbHoldValues[2] = new String("ALLBOOT.A");
        	dbHoldValues[3] = new String("BODY.A");
        	dbHoldValues[4] = new String("BOUNDDIR.T");
        	dbHoldValues[5] = new String("CHAORDER.A");
        	dbHoldValues[6] = new String("COLOR.A");
        	dbHoldValues[7] = new String("COMMENT.T");
        	dbHoldValues[8] = new String("CUSTOM.A");
        	dbHoldValues[9] = new String("DEPT.T");
        	dbHoldValues[10] = new String("DIRECT.T");
        	dbHoldValues[11] = new String("DUE.A");
        	dbHoldValues[12] = new String("EMAIL.T");
        	dbHoldValues[13] = new String("EXTDTYPE.A");
        	dbHoldValues[14] = new String("HONORLOT.A");
        	dbHoldValues[15] = new String("IOUORDER.A");
        	dbHoldValues[16] = new String("IOULAYOUT.A");
        	dbHoldValues[17] = new String("LICCOLOR.A");
        	dbHoldValues[18] = new String("LOT.T");
        	dbHoldValues[19] = new String("MAKE.A");
        	dbHoldValues[20] = new String("MENU.T");
        	dbHoldValues[21] = new String("METORDER.A");
        	dbHoldValues[22] = new String("MODEL.A");
        	dbHoldValues[23] = new String("OFFICER.T");
        	dbHoldValues[24] = new String("OFFORDER.A");
        	dbHoldValues[25] = new String("PCOMMENT.A");
        	dbHoldValues[26] = new String("PERMPLAT.A");
        	dbHoldValues[27] = new String("PINFO.A");
        	dbHoldValues[28] = new String("PLATPERM.A");
        	dbHoldValues[29] = new String("PLAYOUT.A");
        	dbHoldValues[30] = new String("PROBLEM.A");
        	dbHoldValues[31] = new String("REASON.T");
        	dbHoldValues[32] = new String("REMLOT.A");
        	dbHoldValues[33] = new String("SIDEDIR.T");
        	dbHoldValues[34] = new String("SPACE.T");
        	dbHoldValues[35] = new String("STATE.T");
        	dbHoldValues[36] = new String("STREET.A");
        	dbHoldValues[37] = new String("STREET.T");
        	dbHoldValues[38] = new String("STRTTYPE.A");
        	dbHoldValues[39] = new String("TICORDER.A");
        	dbHoldValues[40] = new String("TIMERANG.A");
        	dbHoldValues[41] = new String("TIMING.A");
        	dbHoldValues[42] = new String("TYPE.T");
        	dbHoldValues[43] = new String("VBOOT.T");
        	dbHoldValues[44] = new String("VIOLATE.A");
        	dbHoldValues[45] = new String("VIRTPERM.A");
        	dbHoldValues[46] = new String("VMULTI.T");
        	dbHoldValues[47] = new String("XMETER.T");   
        	dbHoldValues[48] = new String("PLATDATA.T");  
        	dbHoldValues[49] = new String("LGSTREET.A");
        	dbHoldValues[50] = new String("VIOLATEL.A");
        	dbHoldValues[51] = new String("PERMITS.T");
        	dbHoldValues[52] = new String("DONE"); //always have this one as the last value in the array       
       
        	mHandler.postDelayed(mHTTPConnectRunnable, 20);
        	
        	//CLEAR OUT THE STREET NUMBER, DIRECTION, AND STREET NAME BECAUSE OF THE NEW LGSTREET TABLE
	        WorkingStorage.StoreCharVal(Defines.SaveNumberVal, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.PrintNumberVal, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.PrintDirectionVal, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.SaveDirectionVal, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.PrintStreetVal, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.SaveStreetVal, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.SaveYearVal, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.SaveMonthVal, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.PrintLGStreet1Val, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.PrintLGStreet2Val, "", getApplicationContext());
	        WorkingStorage.StoreCharVal(Defines.PrintLGStreet3Val, "", getApplicationContext());	        
        	
            TextUpdate = (TextView)findViewById(R.id.widgetRefresh);
            TextUpdate.setText("Connecting to site...");        	        	
        }


	}
}



