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

public class CheckBTDevice {
	public static String ScanDevice( String DevID, Context dan)
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
		if (!mBluetoothAdapter.isEnabled()) 
		{
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    mBluetoothAdapter.enable();
		    return "Bluetooth is turned off, attempting to turn on...";
		}
		
		//Step 3. Check to see if the device is paired.
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) 
		{
			ReturnString = "Device Not Paired";
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) 
		    {
		        // Add the name and address to an array adapter to show in a ListView
		        //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		    	String TempString = device.getAddress();
		    	if (TempString.equals(DevID))
		    	{
		    		mBluetoothAdapter.cancelDiscovery();
		    		ReturnString = "SUCCESS"; // added 10-10-2012 force success here.
		    		return ReturnString; // added 10-10-2012
		    		//10-10-2012 Commenting entire step 4 due to occasional hang when connecting for the second time after
		    		//Connect and Disconnect has occured sucessfully - see details in PrintForm Class
		    		//Step 4. Check to see if we can detect the printer.
		    		/* //added this comment line on 10-10-2012
		    		BluetoothSocket tmp = null;
		    		BluetoothSocket mmSocket;

		    		UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

		            // Get a BluetoothSocket to connect with the given BluetoothDevice
		            try 
		            {
		                // MY_UUID is the app's UUID string, also used by the server code
		                //tmp = device.createRfcommSocketToServiceRecord(MY_UUID); changed on 6-8-2012 for the ice cream sandwich
		            	tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID); //changed on 6-8-2012 for the ice cream sandwich
		            } 
		            catch (IOException e) 
		            { 
		            	ReturnString = "Couldn't Find Printer";
		            }
		            mmSocket = tmp;
		            try 
		            {
		                // Connect the device through the socket. This will block
		                // until it succeeds or throws an exception
		                mmSocket.connect();
		                SystemIOFile.SavePrintLog("Location 6b", dan);
		                ReturnString = "SUCCESS";
			            // SOCKET OPENED - NOW CLOSE IT AND WE CAN CALL THE ACTUAL PRINT ROUTINE.
		                try 
		                {
		                	SystemIOFile.SavePrintLog("Location 6c", dan);
		                    mmSocket.close();
		                    SystemIOFile.SavePrintLog("Location 6d", dan);
		                    ReturnString = "SUCCESS";
		                } 
		                catch (IOException closeException) 
		                { 
		                	ReturnString = "Bluetooth Error"; 
		                }

		            } 
		            catch (IOException connectException) 
		            {

						ReturnString = "Could not connect to the printer.";
						try 
						{
							SystemIOFile.SavePrintLog("Location 6e", dan);
							mmSocket.close();
							SystemIOFile.SavePrintLog("Location 6f", dan);
		                    mBluetoothAdapter.cancelDiscovery();
		                    mBluetoothAdapter = null;
		                    pairedDevices = null;
		                    tmp = null;
		                    mmSocket = null;
		                } 
		                catch (IOException closeException) 
		                { 
		                	ReturnString = "Bluetooth Error"; 
		                }							
		            	//ReturnString = "Could not connect to the printer.";
		            }	*/ //added this comment line on 10-10-2012
		    		
		    	} //if (TempString.equals(DevID))
		    }//for (BluetoothDevice device : pairedDevices) 
		}
		else
		{
			ReturnString = "Device Not Paired";
		}

		return ReturnString;
	}
}

