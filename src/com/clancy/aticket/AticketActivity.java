package com.clancy.aticket;



import java.io.OutputStreamWriter;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


public class AticketActivity extends ActivityGroup {
     //proguard ignores comments "Mobile\\PUTandDELETEUser" + ":" + "1234PpUuTt!@#"
	 //run v:\foxpro5\AndroidCredentials to build the string
	 String EncryptedCredentials = "31574551401843054428414137723772328034853444397745104100278828293116282934442829348547154141467423782009205020912132328045923485479734444756135326241435"; //Mobile\\PUTandDELETEUser:1234PpUuTt!@# - Perfect Match
	 //tring URLString = "HTTP://www.clancyapp.com/Aticket.apk";
	 String URLString = "295234443444328023781927192748794879487918864059442839774510405949613977459245921886405945514469192726654756430540594387414147561886397745924387"; //HTTP://www.clancyapp.com/Aticket.apk - Perfect Match
	 //String AticketName = "Aticket.apk";
	 String AticketName = "26654756430540594387414147561886397745924387"; //Aticket.apk - Perfect Match       
	 
	 TelephonyManager        Tel;
	 ClancyPhoneStateListener    MyListener;
	 //LocationManager locationManager;
	 //LocationListener locationListener;
	 
	/* 
	 private Runnable mRunnable = new Runnable() {     
 		public void run() 
 		{         			    
 			//WorkingStorage.StoreCharVal(Defines.MobilePutDelVal, "Mobile\\PUTandDELETEUser" + ":" + "1234PpUuTt!@#", getApplicationContext());
 	        //proguard will put runnable code in another file - do this to add another layer of confusion.
 			//WorkingStorage.StoreCharVal(Defines.MobilePutDelVal, GarbleyGoop.DansRoutine(EncryptedCredentials), getApplicationContext());
 			
 			ReadCustom.ReadCustomLayout(getApplicationContext()); //read the file so the program knows where to go
     		//if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("ONLINE"))     		
     		//{
     		//	HTTPFileTransfer.RefreshFile("CUSTOM.A", getApplicationContext()); //Go get the latest custom.a
     		//	ReadCustom.ReadCustomLayout(getApplicationContext()); //re-load the custom.a flags
     		//} //Moved this routine into the BackgroundRefreshCustomA.java
     		Intent myIntent1 = new Intent(getApplicationContext(), BackgroundRefreshCustomA.class);
     		startActivity(myIntent1);
     		overridePendingTransition(0, 0);
 			//SendVitals.GetPhoneNumber(getApplicationContext()); //Moved this routine into the BackgroundRefreshCustomA.java
     		//SendVitals.GetPhoneModel(getApplicationContext()); //Moved this routine into the BackgroundRefreshCustomA.java
     		Intent myIntent2 = new Intent(getApplicationContext(), BackgroundSendVitals.class);
     		startActivity(myIntent2);
     		overridePendingTransition(0, 0);  
     		
     		WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "DONE", getApplicationContext());
     		TextView versionNum = (TextView) findViewById(R.id.widget34);
     		WorkingStorage.StoreCharVal(Defines.VersionNumberVal, versionNum.getText().toString(),getApplicationContext());
     		String ClancyRecord = "";
     		ClancyRecord = SearchData.GetRecordNumberNoLength("CLANCY.J",1, getApplicationContext()); 
     		if (ClancyRecord.length() > 10)
     		{
     			WorkingStorage.StoreCharVal(Defines.PrintUnitNumber, ClancyRecord.substring(0,4), getApplicationContext());
     		}
     		
     		//if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("ONLINE"))
     		//{
         	//	SendVitals.UpdateVitals("Startup", getApplicationContext());     			
     		//} // SendVitals.UpdateVitals("Startup", getApplicationContext());  

			
     		Button begin = (Button) findViewById(R.id.buttonBegin);
     		begin.setEnabled(true);
     		TextView tv02 = (TextView) findViewById(R.id.TextView02);
     		tv02.setText("");
			
     		if (SearchData.GetFileSize("OFFICER.T")> 0) // We have an officer.t
     		{
     			Defines.clsClassName = PasswordForm.class ;				
     		}
     		else
     		{
     			Defines.clsClassName = SelFuncForm.class ;				
     		}
			
  	        if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("HARRISBURG"))
  	        {
  	        	   Defines.clsClassName = SelFuncForm.class ;
  	        }
  	        
     		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
     		startActivityForResult(myIntent, 0);
     		overridePendingTransition(0, 0);
     		
            //ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            //NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            //if (mWifi.isConnected()) 
            //if (mWifi.isAvailable())
            //{
     		WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
     		if (wifi.isWifiEnabled())
     		{
            	myIntent = new Intent(getApplicationContext(), WiFiOnForm.class);
         		startActivityForResult(myIntent, 0);
         		overridePendingTransition(0, 0);
     		}

            //}
		   // }
 		}
 	}; */

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        	}

        if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals(""))
   		 	WorkingStorage.StoreCharVal(Defines.UseOfflineVal, "ONLINE", getApplicationContext()); // default to ONLINE FOR THE FIRST LOAD IF NOTHING HAS BEEN SET 
        
        WorkingStorage.StoreCharVal(Defines.MobilePutDelVal, GarbleyGoop.DansRoutine(EncryptedCredentials), getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.AticketAPKDownloadValue, GarbleyGoop.DansRoutine(URLString), getApplicationContext());
		WorkingStorage.StoreCharVal(Defines.AticketNameValue, GarbleyGoop.DansRoutine(AticketName), getApplicationContext());

        //WorkingStorage.StoreCharVal(Defines.CredentialsValue, "Mobile\\PUTandDELETEUser" + ":" + "1234PpUuTt!@#", getApplicationContext());
        
        Defines.contextGlobal = getApplicationContext();
        Defines.locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Defines.locationListener = new GPSStats();        
        //Defines.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1.0f, Defines.locationListener);
        
    	Button settings = (Button) findViewById(R.id.ButtonSettings);        
        if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("HARRISBURG"))
        {
        	settings.setVisibility(View.VISIBLE);
        }
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
         		if (SearchData.GetFileSize("OFFICER.T")> 0) // We have an officer.t
         		{
         			Defines.clsClassName = PasswordForm.class ;				
         		}
         		else
         		{
         			Defines.clsClassName = SelFuncForm.class ;				
         		}
    					
         		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
         		startActivityForResult(myIntent, 0);
         		overridePendingTransition(0, 0);
            }

        });
        
        Button exit = (Button) findViewById(R.id.buttonExit);
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	CustomVibrate.VibrateButton(getApplicationContext());
                finish();
            }

        });
        
        final Button begin = (Button) findViewById(R.id.buttonBegin);
        
        String GetLatestVersion = "Get Latest Version";
        if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
        {
        	GetLatestVersion = "Obtenga Ultima Version";
        	exit.setText("    Salir    ");
        	begin.setText("Comenzar");
        }
        		
        SpannableString contentUnderline = new SpannableString(GetLatestVersion);
        contentUnderline.setSpan(new UnderlineSpan(), 0, contentUnderline.length(), 0);
        
        final TextView tv03 = (TextView) findViewById(R.id.TextView03);
        tv03.setText(contentUnderline);
        tv03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
            	   Defines.clsClassName = RefreshForm.class ;
                   Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
                   startActivityForResult(myIntent, 0);
				
			}
		});
        
        final TextView tv02 = (TextView) findViewById(R.id.TextView02);
        
        TextView unit = (TextView) findViewById(R.id.TextViewUnitNumber);
        unit.setText(WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + " - " + WorkingStorage.GetCharVal(Defines.PhoneNumberVal, getApplicationContext()) );

        TextView client = (TextView) findViewById(R.id.TextViewClient);
        client.setText("Licensed: "+WorkingStorage.GetCharVal(Defines.ClientName , getApplicationContext()));
        
        
        begin.requestFocus();                
        
        begin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            	CustomVibrate.VibrateButton(getApplicationContext());
            	//Write this file to create the /files/ subdirectory
        		try { //do not remove this routine
        			OutputStreamWriter out = new OutputStreamWriter(openFileOutput("dan.txt",0)); //do not remove this routine
        			out.write("dan the man"); //do not remove this routine
        			out.close(); //do not remove this routine
        		}  
        		catch(Throwable t) { 
        			Toast
        			.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
        			.show();
        		}   
        		
            	//Check to see if we have a custom.a 
            	//If not then go to the IP Address entry form to set up a client to download from
            	long CustomExists = SearchData.GetFileSize("CUSTOM.A");

            	if (CustomExists > 0) //we have a custom.a file so we are good to proceed with processing
            	{   
            		                        	
            		long ClancyJExists = SearchData.GetFileSize("CLANCY.J"); 
            		if (ClancyJExists> 0) //we have a CLANCY.J file so we are good to proceed with processing
            		{
                			ReadCustom.ReadCustomLayout(getApplicationContext()); //read the file so the program knows where to go
             			                 		
                 		WorkingStorage.StoreCharVal(Defines.HBoxFlowVal, "DONE", getApplicationContext());
                 		TextView versionNum = (TextView) findViewById(R.id.widget34);
                 		WorkingStorage.StoreCharVal(Defines.VersionNumberVal, versionNum.getText().toString(),getApplicationContext());
                 		String ClancyRecord = "";
                 		ClancyRecord = SearchData.GetRecordNumberNoLength("CLANCY.J",1, getApplicationContext()); 
                 		if (ClancyRecord.length() > 10)
                 		{
                 			WorkingStorage.StoreCharVal(Defines.PrintUnitNumber, ClancyRecord.substring(0,4), getApplicationContext());
                 		}
                 		if (SearchData.GetFileSize("OFFICER.T")> 0) // We have an officer.t
                 		{
                 			Defines.clsClassName = PasswordForm.class ;				
                 		}
                 		else
                 		{
                 			Defines.clsClassName = SelFuncForm.class ;				
                 		}
            			
              	        if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("HARRISBURG"))
              	        {
              	        	   Defines.clsClassName = SelFuncForm.class ;
              	        }
              	        
                 		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                 		startActivityForResult(myIntent, 0);
                 		overridePendingTransition(0, 0);
                 		
                 		WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                 		if (wifi.isWifiEnabled())
                 		{
                        	myIntent = new Intent(getApplicationContext(), WiFiOnForm.class);
                     		startActivityForResult(myIntent, 0);
                     		overridePendingTransition(0, 0);
                 		}
            		}else
            		{
            			//Messagebox.Message("Unable to find CLANCY.J",getApplicationContext());
                       	Defines.clsClassName = ClancyJForm.class ;
                       	
                        Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
                        startActivityForResult(myIntent, 0);
                        overridePendingTransition(0, 0);
                        //IMeantToDestroyTheActvity = true;
            			
            		}
           		    new Thread(new Runnable() {
           		        public void run() {
           		        	
          	 		      if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("ONLINE"))     		
        	 		      {
          	 				String HTTPageContent = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+"/DemoTickets/CUSTOM.A", getApplicationContext());
          	 		        if (HTTPageContent.length()>2)
          	 		        {
          	 		        	if (HTTPageContent.substring(0, 2).equals("US"))
          	 		        	{
            	 		    	  HTTPFileTransfer.RefreshFile("CUSTOM.A", getApplicationContext()); //Go get the latest custom.a
            	 		    	  ReadCustom.ReadCustomLayout(getApplicationContext()); //re-load the custom.a flags
            	 		    	  SendVitals.GetPhoneNumber(getApplicationContext()); 
            	 		   		  SendVitals.GetPhoneModel(getApplicationContext()); 
            	 		   		  SendVitals.UpdateVitals("Startup", getApplicationContext()); 
          	 		        	}
          	 		        }
        	 		      }
           		        }
           		    }).start();
                }
            	else
            	{
            		//Messagebox.Message("Unable to find CUSTOM.A",getApplicationContext());
             	   //Messagebox.Message(WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext()),getApplicationContext()) ;            		
                   	Defines.clsClassName = IPAddressForm.class ;
                    Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
                    startActivityForResult(myIntent, 0);
                    overridePendingTransition(0, 0);
                    //IMeantToDestroyTheActvity = true;
                   	
            	}
            	
            }
        });
        final CheckBox Offline = (CheckBox) findViewById(R.id.checkBoxOffine);
        if (WorkingStorage.GetCharVal(Defines.UseOfflineVal, getApplicationContext()).equals("OFFLINE"))
        {
        	Offline.setChecked(true);
        	Offline.setText("Use Offline - Tickets will not upload.");
        	Offline.setTextColor(Color.rgb(255,0,0));
        }
        else
        {
        	Offline.setChecked(false);
        	Offline.setText("Use Offline");
        	Offline.setTextColor(Color.rgb(0,0,0));
        }       
        Offline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
        {

        	   @Override
        	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
        	   {
        	        if ( isChecked )
        	        {
        	        	//WorkingStorage.StoreCharVal(Defines.UseOfflineVal, "OFFLINE", getApplicationContext()); set this value if the password is correct
        	        	//Messagebox.Message("OFFLINE", getApplicationContext());
        	        	//Offline.setText("Use Offline - Tickets will not upload.");
        	        	//Offline.setTextColor(Color.rgb(255,0,0));
                 	   	Defines.clsClassName = OfflinePasswordForm.class ;
                 	   	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                 	   	startActivityForResult(myIntent, 0);  
                        finish();
                        overridePendingTransition(0, 0);                 	   	
        	        }
        	        else
        	        {
        	        	WorkingStorage.StoreCharVal(Defines.UseOfflineVal, "ONLINE", getApplicationContext());
        	        	Messagebox.Message("ONLINE", getApplicationContext());
        	        	Offline.setText("Use Offline");
        	        	Offline.setTextColor(Color.rgb(0,0,0));
        	        }
        	   }
        });

        MyListener   = new ClancyPhoneStateListener();
        Tel       = ( TelephonyManager )getSystemService(Context.TELEPHONY_SERVICE);
        Tel.listen(MyListener ,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        
        
    }
    @Override
    protected void onPause()
     {
       super.onPause();
       Tel.listen(MyListener, PhoneStateListener.LISTEN_NONE);
    }
     
    @Override
    protected void onResume()
    {
       super.onResume();
       Tel.listen(MyListener,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }
    @Override
    protected void onDestroy()
    {
       super.onDestroy();
       Defines.locationManager.removeUpdates(Defines.locationListener);
    }    
    
    private class ClancyPhoneStateListener extends PhoneStateListener
    {
      /* Get the Signal strength from the provider, each time there is an update */
      @Override
      public void onSignalStrengthsChanged(SignalStrength signalStrength)
      {
         super.onSignalStrengthsChanged(signalStrength);
         //Toast.makeText(getApplicationContext(), "Clancy = "
         //   + GetNetwork(getApplicationContext()) +"|"+ String.valueOf(signalStrength.getGsmSignalStrength()*3)+"|AUTO", Toast.LENGTH_SHORT).show();
         WorkingStorage.StoreCharVal(Defines.SignalStrengthVal, GetNetwork(getApplicationContext()) +"|"+ String.valueOf(signalStrength.getGsmSignalStrength()*4)+"|AUTO", getApplicationContext());
      }

    };
    
    public String GetNetwork(Context dan)
    {
 	   TelephonyManager tm = (TelephonyManager) getSystemService(getApplicationContext().TELEPHONY_SERVICE);
	   int nt = tm.getNetworkType();

	    switch (nt) {
	            case 1: return "GPRS";
	            case 2: return "EDGE";
	            case 3: return "UMTS";
	            case 8: return "HSDPA";
	            case 9: return "HSUPA";
	            case 10:return "HSPA";
	            default:return "UNKNOWN";
	            }
    }    
}