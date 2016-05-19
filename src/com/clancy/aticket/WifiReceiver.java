package com.clancy.aticket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		SaveTicket.SaveWiFiFile(context, "DETECTED WI-FI CHANGE");
		//Toast.makeText(context, "Clancy Detected Wi-fi change", Toast.LENGTH_LONG).show();
		if(intent.getAction().equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)){ 
	          boolean connected = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);
	          if(!connected) {
	        	  SaveTicket.SaveWiFiFile(context, "WI-FI DISCONNECTED");
	          }
	      }

	      else if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
	          NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
	          if( netInfo.isConnected() )
	          {
	        	  SaveTicket.SaveWiFiFile(context, "WI-FI CONNECTED");
	          }   
	      }
		
	}

}
