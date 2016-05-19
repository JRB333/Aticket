package com.clancy.aticket;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;

public class CheckBTDeviceWithOldSupport {
	public static String ScanDeviceWithOldSupport( String DevID, Context dan)
	{
		String ReturnString = "FAILURE";
		
		//Step 1. Make sure the device has a Bluetooth module.
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) 
		{   
			// Device does not support Bluetooth
			return "No Bluetooth Support";
		}
		
		//Step 2. Check to see if the bluetooth is turned on.
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    mBluetoothAdapter.enable();
		    return "Bluetooth is turned off, attempting to turn on...";
		}
		
		//Step 3. Check to see if the device is paired.
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
			ReturnString = "Device Not Paired";
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		        //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		    	String TempString = device.getAddress();
		    	if (TempString.equals(DevID))
		    	{
		    		mBluetoothAdapter.cancelDiscovery();
		    		ReturnString = "Paired";
		    		//Step 4. Check to see if we can detect the printer.
		    		BluetoothSocket tmp = null;
		    		BluetoothSocket mmSocket;

		    		UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

		            // Get a BluetoothSocket to connect with the given BluetoothDevice
		            try {
		                // MY_UUID is the app's UUID string, also used by the server code
		                //tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
		            	tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID); //changed on 6-8-2012 for the ice cream sandwich
		            	} catch (IOException e) 
		            	{ 
		            		ReturnString = "Couldn't Find Printer";
		            	}
		            	mmSocket = tmp;
		            try {
		                // Connect the device through the socket. This will block
		                // until it succeeds or throws an exception
		                mmSocket.connect();
		                ReturnString = "SUCCESS";
			            // SOCKET OPENED - NOW CLOSE IT AND WE CAN CALL THE ACTUAL PRINT ROUTINE.
		                try {
		                    mmSocket.close();
		                    ReturnString = "SUCCESS";
		                } 
		                catch (IOException closeException) 
		                { ReturnString = "Bluetooth Error"; }

		            } catch (IOException connectException) {

		            	//Failed with the createRfcommSocketToServiceRecord(MY_UUID); call now try the old primitive crap!
		            	Method m;
						try {
							m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
			            	tmp = (BluetoothSocket) m.invoke(device, 1);
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							ReturnString = "Could not connect to the printer.";
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							ReturnString = "Could not connect to the printer.";
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							ReturnString = "Could not connect to the printer.";
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							ReturnString = "Could not connect to the printer.";
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							ReturnString = "Could not connect to the printer.";
							// TODO Auto-generated catch block
							e.printStackTrace();
						}          
						if (ReturnString.equals("Could not connect to the printer."))// we failed both methods! close the socket and get out!
						{
							try {
			                    mmSocket.close();
			                    mBluetoothAdapter.cancelDiscovery();
			                    mBluetoothAdapter = null;
			                    pairedDevices = null;
			                    tmp = null;
			                    mmSocket = null;
			                } 
			                catch (IOException closeException) 
			                { ReturnString = "Bluetooth Error"; }							
						}
						else // we suceeded on the second old method, now connect try the connect
						{
				            try 
				            {
				            	mmSocket = tmp;
				            	mmSocket.connect();
				            	ReturnString = "SUCCESS";
				            	// SOCKET OPENED - NOW CLOSE IT AND WE CAN CALL THE ACTUAL PRINT ROUTINE.
				            	try {
				            		mmSocket.close();
				            		ReturnString = "SUCCESS";
				            	} 
				            	catch (IOException closeException) 
				            	{ 
				            		ReturnString = "Bluetooth Error"; 
				            	}
				            } catch (IOException connectException1) 
				            {
				            	ReturnString = "Could not connect to the printer.";
				            	try {
				                    mmSocket.close();
				                    mBluetoothAdapter.cancelDiscovery();
				                    mBluetoothAdapter = null;
				                    pairedDevices = null;
				                    tmp = null;
				                    mmSocket = null;
				                } 
				                catch (IOException closeException) 
				                { ReturnString = "Bluetooth Error"; }	
				            }
						}
		            	//ReturnString = "Could not connect to the printer.";

		            }
		    		
		    	}
		    }
		}
		else
		{
			ReturnString = "Device Not Paired";
		}
		
		
		return ReturnString;
	}
}

