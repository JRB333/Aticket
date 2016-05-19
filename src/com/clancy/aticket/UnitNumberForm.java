package com.clancy.aticket;

import java.io.OutputStreamWriter;

import android.app.ActivityGroup;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.bluetooth.BluetoothDevice; 
import android.content.BroadcastReceiver;
import android.inputmethodservice.KeyboardView;


public class UnitNumberForm extends ActivityGroup {
	protected  BluetoothAdapter mBluetoothAdapter = null;
	private static final int REQUEST_ENABLE_BT = 3;
	private Handler mHandler = new Handler(); 
	BroadcastReceiver mReceiver = null;
	public String newString = "";
	public String BTAddress = "";	
    public Boolean FoundUnit = false; 

	

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unitnumberform);

       
      Button second = (Button) findViewById(R.id.buttonESC);
       second.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   if (mBluetoothAdapter != null)
        		   if (mBluetoothAdapter.isDiscovering())
        			   mBluetoothAdapter.cancelDiscovery();        		   
        	  
        	   if ( mReceiver != null )
            	   unregisterReceiver(mReceiver);        		   
        	   
           	   Defines.clsClassName = PasswordForm.class ;
               Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
               startActivityForResult(myIntent, 0);
        	   finish();
               overridePendingTransition(0, 0);     
           }          
       });

   	   
	   final TextView UpdateMessage = (TextView)findViewById(R.id.textView1);
	      
	   
 	   final Runnable mDiscoveryRunnable = new Runnable() {     
			public void run() 
			{         			    
				if (mBluetoothAdapter.isDiscovering())
				{
					if (FoundUnit.equals(true))
					{
						UpdateMessage.setText("Pairing Successful");
						mBluetoothAdapter.cancelDiscovery();
						mHandler.postDelayed(this, 200); // cycle until Discovery is done  
					}	
					else
					{
						UpdateMessage.setText("Attempting to pair");
						mHandler.postDelayed(this, 200); // cycle again  
					}
				}
				else
				{
					if (FoundUnit.equals(true))
					{
						UpdateMessage.setText("Pairing Successful");
			        	if ( mReceiver != null )
			               unregisterReceiver(mReceiver);      
			        	   
						Messagebox.Message("Pairing Successful",getApplicationContext()) ;
		            	finish(); // Password form should still be visible at this point
		                overridePendingTransition(0, 0);
					}
					else
					{
						UpdateMessage.setText("Unable to Locate Printer");
					}
				}
			}
		}; 


		   final EditText NewUnitNumber = (EditText)findViewById(R.id.editTextUnit);
		   NewUnitNumber.selectAll();
		   
	       KeyboardView keyboardView = null; 
	       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
	   	   CustomKeyboard.PickAKeyboard(NewUnitNumber, "LICENSE", getApplicationContext(), keyboardView);

	   	   NewUnitNumber.setOnTouchListener(new View.OnTouchListener(){ 
			@Override
			public boolean onTouch(View v, MotionEvent event) {
	    		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
	    		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	    		return true;
			} 
	   		});

		final Button enter = (Button) findViewById(R.id.buttonEnter);
        enter.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {       	            	  
        	   UpdateMessage.setText("");
        	   newString = NewUnitNumber.getText().toString();
        	   if (newString.equals(""))
        	   {               	                 
        		   Messagebox.Message("Unit Number Required.",getApplicationContext()) ;
        		   NewUnitNumber.selectAll();
        	   }
        	   else
        	   {
        		   String KeepGoing = "";
        		   KeepGoing = CheckForBTEnabled();        		   
        		   if (KeepGoing.equals("GOOD")) // Device in BT capable and it is turned on
        		   {
        			   SetBTReceiverMethod();
        			   mBluetoothAdapter.startDiscovery();   
        			   mHandler.postDelayed(mDiscoveryRunnable, 200); //loop through checking discovery process
        		   }
        		   else
        		   {
        			   UpdateMessage.setText(KeepGoing);
        			   enter.setText("Try Again");
        		   }
       		   
        	   }
             }
          
       });
             
 
	}

	
	
	protected String CheckForBTEnabled()
	   {
		   mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		   if (mBluetoothAdapter == null) {    
			   //Toast.makeText(getApplicationContext(), "Device Doesn't Support Bluetooth", Toast.LENGTH_SHORT).show();
			   return "No Bluetooth on device";
		   }
		   
		   if (!mBluetoothAdapter.isEnabled()) {
			   //Toast.makeText(getApplicationContext(), "Bluetooth Not Enabled, Please Enable! )", Toast.LENGTH_SHORT).show();
			   Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			   startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			   return "!!Please Enable Bluetooth!!";
		   }
		   return "GOOD";
	   }
	
	   protected boolean SetBTReceiverMethod()
	   {
		// Create a BroadcastReceiver for ACTION_FOUND
		   //final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		    mReceiver = new BroadcastReceiver() {
			   public void onReceive(Context context, Intent intent) {
				   String action = intent.getAction();        
				   // When discovery finds a device        
				   if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					   // Get the BluetoothDevice object from the Intent
					   BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					   if (device.getName().equals(newString)){
						   	Toast.makeText(getApplicationContext(), device.getName() + "-" + device.getAddress(), Toast.LENGTH_SHORT).show();
							try {
								OutputStreamWriter out = new OutputStreamWriter(openFileOutput("PRINTER.TXT",0));
								out.write(device.getName()+"\n");
								out.write(device.getAddress());
								out.close();
								FoundUnit = true;
							}  
							catch(Throwable t) { 
								Toast
								.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
								.show();
							}  

					   }
					
					   }   
				   }
			   };
			   
			   // Register the BroadcastReceiver
			   IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			   registerReceiver(mReceiver, filter); 
			   // Don't forget to unregister during onDestroy  !!!!
			   
		   return true;
	   }	
}