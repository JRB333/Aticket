package com.clancy.aticket;


import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class PlateForm extends ActivityGroup {
	
	  Handler handler = new Handler() 
	  {
		  public void handleMessage(Message message) {
		    switch (message.what) {
		    case HttpConnection.DID_START:
		      break;
		      
		    case HttpConnection.DID_SUCCEED:
		      String response = (String) message.obj;
		      SaveTicket.SaveVMultiPlate(response, getApplicationContext());
			  /*StringTokenizer tokens = new StringTokenizer(response, "|");
			  String ParseString1 = "";
			  String ParseString2 = "";
			  if (tokens.hasMoreTokens() == true)
			  {
				ParseString1 = tokens.nextToken();
			  }
			  if (tokens.hasMoreTokens() == true)
			  {
				ParseString2 = tokens.nextToken();
			  }	*/
		      break;
		      
		    case HttpConnection.DID_ERROR:
		      Exception e = (Exception) message.obj;
		      e.printStackTrace();
		      //  text.setText("Connection failed.");
		     // Toast.makeText(getApplicationContext(), "Connection failed.", 2000).show();
		    break;
		    }
		  }
	  };

	  private void EnterClick()
	  {
		  EditText plate = (EditText) findViewById(R.id.editTextPlate);   
		  String newString = plate.getText().toString();
		  if (! newString.equals(""))
		  {
			  WorkingStorage.StoreCharVal(Defines.PrintPlateVal, newString, getApplicationContext());
			  WorkingStorage.StoreCharVal(Defines.SavePlateVal, newString, getApplicationContext());
			  
			  if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.BootLookupMenu))
			  {
	           	   Defines.clsClassName = SelFuncForm.class ;
	               Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	               startActivityForResult(myIntent, 0);  
	               finish();
	               overridePendingTransition(0, 0);
 
			  }else if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
			  {
				   WorkingStorage.StoreCharVal(Defines.SaveChalkPlateVal, newString, getApplicationContext());
		     	   if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
		    	   {
					   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
					   startActivityForResult(myIntent, 0);
					   finish();
					   overridePendingTransition(0, 0);    
		    	   }		

			  }else
			  {
				  
				  if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("LACROSSE"))
				  {
					  String tmpPlate = WorkingStorage.GetCharVal(Defines.PrintPlateVal,getApplicationContext());
					  SystemIOFile.Delete(tmpPlate+".T");
					  String URLString = "http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress,getApplicationContext()).trim()	+ "/VMULTINEW.asp?plate="+ tmpPlate + "&extra=NOTHING";       	
				      new HttpConnection(handler).get(URLString);					  
				  }
			    	
				  PlateInfo.GetPlateInfo( getApplicationContext());
				  PlateInfo.GetSecondaryPlateInfo( getApplicationContext());
				  if (newString.equals("TEMPTAG"))
				  {
					  Defines.clsClassName = TempPlateForm.class ;  
				  }
				  else
				  {
					  Defines.clsClassName = StateForm.class ;					  
				  }
				  
				  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				  startActivityForResult(myIntent, 0);  
				  finish();
				  overridePendingTransition(0, 0);				  
			  }
			  
		  }
		  else
		  {
			  if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.BootLookupMenu))
			  {
				  Messagebox.Message("PlateRequired",getApplicationContext());
				  plate.requestFocus();				  
			  }else if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
			  {
				  Messagebox.Message("PlateRequired",getApplicationContext());
				  plate.requestFocus();				  
			  }else
			  {
				  Defines.clsClassName = VinForm.class ;
				  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				  startActivityForResult(myIntent, 0);  
				  finish();
				  overridePendingTransition(0, 0);	
			  }
		  }
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plateform);
       
        
	    if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
	    {
	    	TextView tvSpanish = (TextView) findViewById(R.id.widgetEnterPlate);
	        tvSpanish.setText("NÚMERO DE TABLILLA:");
	    }
	    
        Button second = (Button) findViewById(R.id.buttonESC);
        second.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
           	   Defines.clsClassName = SelFuncForm.class ;
               Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
               startActivityForResult(myIntent, 0);  
               finish();
               overridePendingTransition(0, 0);
          }          
        });
      
       Button enter = (Button) findViewById(R.id.buttonEnter);
       enter.setOnClickListener(new View.OnClickListener() {
	           public void onClick(View view) {
	        	   CustomVibrate.VibrateButton(getApplicationContext());
	        	   EnterClick();	        	   
	           }	          
	   });

	   EditText plate = (EditText) findViewById(R.id.editTextPlate);   
	   plate.requestFocus();
       
       KeyboardView keyboardView = null; 
       keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	   CustomKeyboard.PickAKeyboard(plate, "LICENSE", getApplicationContext(), keyboardView);

   	   plate.setOnTouchListener(new View.OnTouchListener(){ 
		@Override
		public boolean onTouch(View v, MotionEvent event) {
    		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
    		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    		return true;
		} 
   		});

	  /* plate.setOnKeyListener(new View.OnKeyListener() {     	  
       	  public boolean onKey(View arg0, int arg1, KeyEvent event) {
     		  if ( (event.getAction() == KeyEvent.ACTION_UP  ) &&
     				  (arg1 == KeyEvent.KEYCODE_ENTER)   )
     			  { 
     			  	EnterClick();
     			  	return true;     			  	
     			  }
   		  		return false;
       	  }     	   
	   });
	   */
	}
	  
	  @Override
	    protected void onResume() {
	        super.onResume();
	        //Toast.makeText(getApplicationContext(), "Woke Up, Stamping Time... ", Toast.LENGTH_SHORT).show();
	        if (WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("EASTLANSING")
	        	|| WorkingStorage.GetCharVal(Defines.ClientName,getApplicationContext()).trim().equals("MANITOWOC"))
	        {	  
	        	Toast.makeText(getApplicationContext(), WorkingStorage.GetCharVal(Defines.SaveDateVal, getApplicationContext() ), Toast.LENGTH_SHORT).show();	        	
	        }
	        else
	        {
	        	GetDate.GetDateTime(getApplicationContext());
	        	WorkingStorage.StoreCharVal(Defines.SaveTimeStartVal, WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext()), getApplicationContext());
	        	WorkingStorage.StoreCharVal(Defines.PrintTimeStartVal, WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()), getApplicationContext());
	        }
	    }
	  
	   /* @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        finish();
	        // The activity is about to be destroyed.
	    }*/  
	  /*@Override
	    protected void onStop() {
	        super.onStop();
        	//Defines.clsClassName = SelFuncForm.class ;
        	//Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        	//startActivityForResult(myIntent, 0);
	        finish(); 
     		overridePendingTransition(0, 0);
	    }*/
}