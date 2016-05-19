package com.clancy.aticket;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GPSStats implements LocationListener {


	@Override
	public void onLocationChanged(Location location) {
		double dLat = location.getLatitude();
		double dLong = location.getLongitude();
	    //Messagebox.Message(String.valueOf(dLat)+" "+String.valueOf(dLong), Defines.contextGlobal);
		WorkingStorage.StoreCharVal(Defines.LatitudeVal, String.valueOf(dLat),  Defines.contextGlobal);
		WorkingStorage.StoreCharVal(Defines.LongitudeVal, String.valueOf(dLong),  Defines.contextGlobal);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		//Messagebox.Message("onProviderDisabled", Defines.contextGlobal);
		WorkingStorage.StoreCharVal(Defines.IsGPSOnOrOFFVal, "OFF",  Defines.contextGlobal);
		WorkingStorage.StoreCharVal(Defines.LatitudeVal, "",  Defines.contextGlobal);
		WorkingStorage.StoreCharVal(Defines.LongitudeVal, "",  Defines.contextGlobal);		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		//Messagebox.Message("onProviderEnabled", Defines.contextGlobal);
		WorkingStorage.StoreCharVal(Defines.IsGPSOnOrOFFVal, "ON",  Defines.contextGlobal);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		//Messagebox.Message("onStatusChanged", Defines.contextGlobal);
	}

}
