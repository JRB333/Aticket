package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class PlateHistoryForm extends ActivityGroup {

	  private void EnterClick()
	  {
		  EditText plate = (EditText) findViewById(R.id.editTextPlate);   
		  String newString = plate.getText().toString();
		  if (! newString.equals(""))
		  {
			  WorkingStorage.StoreCharVal(Defines.PrintPlateVal, newString, getApplicationContext());
			  Defines.clsClassName = VehicleHistoryForm.class ;
			  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
			  startActivityForResult(myIntent, 0);  
			  finish();
			  overridePendingTransition(0, 0);				  			  
		  }
		  else
		  {
			  Messagebox.Message("Plate Required",getApplicationContext());
			  plate.requestFocus();				  
		  }
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.platehistoryform);
       
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

       if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ValidLotLookupMenu))
       {
    	   if (!WorkingStorage.GetCharVal(Defines.LotCheckMessage, getApplicationContext()).trim().equals(""))
    	   {
    	       TextView tvTitle = (TextView) findViewById(R.id.widgetEnterPlate);
    	       tvTitle.setText(WorkingStorage.GetCharVal(Defines.LotCheckMessage, getApplicationContext()).trim());    		   
    	   }
       }
       
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

	}
}