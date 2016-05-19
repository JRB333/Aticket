package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class DirectionForm extends ActivityGroup {

	private void EnterClick()
	{
		TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DescString = tvDesc.getText().toString().trim();
		if (DescString.equals("NOT FOUND")|| DescString.equals("INVALID DIRECTION"))
		{
			EditText direction = (EditText) findViewById(R.id.editTextdirection);
	        direction.selectAll();
	        direction.requestFocus();
		}
		else
		{
			WorkingStorage.StoreCharVal(Defines.PreviousDirectCodeVal, WorkingStorage.GetCharVal(Defines.RotateValue,getApplicationContext()),getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.SaveDirectionVal, WorkingStorage.GetCharVal(Defines.RotateValue,getApplicationContext()),getApplicationContext());
            WorkingStorage.StoreCharVal(Defines.PrintDirectionVal, DescString, getApplicationContext());            
            
            if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
			{
			   TextView DirCode = (TextView) findViewById(R.id.editTextdirection);

			   WorkingStorage.StoreCharVal(Defines.SaveChalkDirectionVal, WorkingStorage.GetCharVal(Defines.RotateValue,getApplicationContext()),getApplicationContext());
			   WorkingStorage.StoreCharVal(Defines.PrintChalkDirectionVal, DescString,getApplicationContext());
		       if (ProgramFlow.GetNextChalkingForm(getApplicationContext()) != "ERROR")
		       {
				   Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
				   startActivityForResult(myIntent, 0);
				   finish();
				   overridePendingTransition(0, 0);    
		       }		
		    }
            else
			{
   			    if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
   			   	{
   			   		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
   			   		startActivityForResult(myIntent, 0);
   			   		finish();
   			   		overridePendingTransition(0, 0);           			
   			   	}
			}
		}
	}
	
	private void LeftClick()
	{
        EditText direction = (EditText) findViewById(R.id.editTextdirection);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DirectionDesc = SearchData.ScrollDownThroughFile("DIRECT.T", getApplicationContext());
        if (WorkingStorage.GetCharVal(Defines.StrictDirectionFlag, getApplicationContext()).equals("STRICTDIRECTION"))
        {
        	if (VerifyDirectionCode(DirectionDesc.substring(0, 1)) == false) //'invalid direction, find the next valid one
        	{
        		for(int iVar = 0; iVar < 20; iVar++)
        		{
        			DirectionDesc = SearchData.ScrollDownThroughFile("DIRECT.T", getApplicationContext());
        			if( VerifyDirectionCode(DirectionDesc.substring(0, 1)) == true )
        			{
        				break;
        			}
        		}
        	}
        }		
		if (DirectionDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
			tvDesc.setText(DirectionDesc.substring(1));
	        WorkingStorage.StoreCharVal(Defines.RotateValue, DirectionDesc.substring(0,1), getApplicationContext());
	        direction.setText(DirectionDesc.substring(0,1));
	        direction.selectAll();
	        direction.requestFocus();			
		}
		if (DirectionDesc.length() < 3)
			LeftClick();
	}
	
	private void RightClick()
	{
        EditText direction = (EditText) findViewById(R.id.editTextdirection);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());

        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DirectionDesc = SearchData.ScrollUpThroughFile("DIRECT.T", getApplicationContext());
        if (WorkingStorage.GetCharVal(Defines.StrictDirectionFlag, getApplicationContext()).equals("STRICTDIRECTION"))
        {
        	if (VerifyDirectionCode(DirectionDesc.substring(0, 1)) == false) //'invalid direction, find the next valid one
        	{
        		for(int iVar = 0; iVar < 20; iVar++)
        		{
        			DirectionDesc = SearchData.ScrollUpThroughFile("DIRECT.T", getApplicationContext());
        			if( VerifyDirectionCode(DirectionDesc.substring(0, 1)) == true )
        			{
        				break;
        			}
        		}
        	}
        }
		if (DirectionDesc.equals("NIF") || DirectionDesc.length() < 3)
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
			tvDesc.setText(DirectionDesc.substring(1));
	        WorkingStorage.StoreCharVal(Defines.RotateValue, DirectionDesc.substring(0,1), getApplicationContext());
	        direction.setText(DirectionDesc.substring(0,1));
	        direction.selectAll();
	        direction.requestFocus();			
		}
		
		if (DirectionDesc.length() < 3)
			RightClick();
	}	
	
	private void TextChange(String SearchString)
	{
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
        EditText direction = (EditText) findViewById(R.id.editTextdirection);
		if (!WorkingStorage.GetCharVal(Defines.TxfrDirCodeVal, getApplicationContext()).equals("")) {
			SearchString = WorkingStorage.GetCharVal(Defines.TxfrDirCodeVal, getApplicationContext()).trim();
			WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "", getApplicationContext());
		}
        String DirectionDesc = SearchData.FindRecordLine(SearchString, SearchString.length(), "DIRECT.T", getApplicationContext());
        if (WorkingStorage.GetCharVal(Defines.StrictDirectionFlag, getApplicationContext()).equals("STRICTDIRECTION"))
        {
        	if (VerifyDirectionCode(DirectionDesc.substring(0, 1)) == false) 
        	{
        		DirectionDesc = "INVALID";
        	}
        }
		if (DirectionDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}
		else if (DirectionDesc.equals("INVALID"))
		{
			tvDesc.setText("INVALID DIRECTION");			
		}
		else
		{
	        tvDesc.setText(DirectionDesc.substring(1));
	        WorkingStorage.StoreCharVal(Defines.RotateValue, DirectionDesc.substring(0,1), getApplicationContext());
		}        
		direction.selectAll();
		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directionform);

		if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ChalkMenu))
		{
			TextView functionLabel = (TextView) findViewById(R.id.functionLabel);
			functionLabel.setText("Chalking " );
			functionLabel.setVisibility(View.VISIBLE);
		}

        if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("LACROSSE") ||
        		WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("SBPD"))
        {
        	// force off strict direction for lacrosse temporarily. I changed the code instead of the custom.a because they are still running Windows Mobile Units
        	WorkingStorage.StoreCharVal(Defines.StrictDirectionFlag,"IGNORE",getApplicationContext());
        }
        Button esc = (Button) findViewById(R.id.buttonESC);
        esc.setOnClickListener(new View.OnClickListener() {
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
       
        EditText direction = (EditText) findViewById(R.id.editTextdirection);
        
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
        String DirectionCode ="";
		if (!WorkingStorage.GetCharVal(Defines.TxfrDirCodeVal, getApplicationContext()).equals("")) {
			DirectionCode = WorkingStorage.GetCharVal(Defines.TxfrDirCodeVal, getApplicationContext()).trim();
			WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "", getApplicationContext());
		} else {
			DirectionCode = WorkingStorage.GetCharVal(Defines.PreviousDirectCodeVal, getApplicationContext());
		}
		String DirectionDesc = "";
		DirectionDesc = SearchData.FindRecordLine(DirectionCode, 1, "DIRECT.T", getApplicationContext());

        if (DirectionDesc.equals("NIF"))
        {
        	RightClick();
        }
        else
        {
        	WorkingStorage.StoreCharVal(Defines.RotateValue, DirectionDesc.substring(0,1), getApplicationContext());
        	tvDesc.setText(DirectionDesc.substring(1));
        	direction.setText(DirectionCode);
        	direction.requestFocus();
        	direction.selectAll();
        }
        if (WorkingStorage.GetCharVal(Defines.StrictDirectionFlag, getApplicationContext()).equals("STRICTDIRECTION"))
        {
        	if (VerifyDirectionCode(DirectionDesc.substring(0, 1)) == false) //'invalid direction, find the next valid one
        	{
        		for(int iVar = 0; iVar < 20; iVar++)
        		{
        			DirectionDesc = SearchData.ScrollUpThroughFile("DIRECT.T", getApplicationContext());
        			if( VerifyDirectionCode(DirectionDesc.substring(0, 1)) == true )
        			{
        	        	WorkingStorage.StoreCharVal(Defines.RotateValue, DirectionDesc.substring(0,1), getApplicationContext());
        	        	tvDesc.setText(DirectionDesc.substring(1));
        	        	direction.setText(DirectionCode);
        	        	direction.requestFocus();
        	        	direction.selectAll();
        				break;
        			}
        		}
        	}
        }
               
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        if (WorkingStorage.GetCharVal(Defines.ClientName, getApplicationContext()).trim().equals("SBPD"))
        {
       	    CustomKeyboard.PickAKeyboard(direction, "NSEW", getApplicationContext(), keyboardView);
        }
        else
        {
       	    CustomKeyboard.PickAKeyboard(direction, "LICENSE", getApplicationContext(), keyboardView);        	
        }

        if (savedInstanceState == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		direction.selectAll();
        }
               
    	direction.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});

        direction.addTextChangedListener(new TextWatcher(){
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString());
			}     
        });
        
        ImageButton left = (ImageButton) findViewById(R.id.button1);
        left.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   LeftClick();
           }          
        });
        
        ImageButton right = (ImageButton) findViewById(R.id.button2);
        right.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   RightClick();
           }          
        });
	}
	
	private Boolean VerifyDirectionCode(String CodeToCheck)
	{
        
        String AllowNorth = WorkingStorage.GetCharVal(Defines.SaveStreetNVal,getApplicationContext());
        String AllowSouth = WorkingStorage.GetCharVal(Defines.SaveStreetSVal,getApplicationContext());
        String AllowEast = WorkingStorage.GetCharVal(Defines.SaveStreetEVal,getApplicationContext());
        String AllowWest = WorkingStorage.GetCharVal(Defines.SaveStreetWVal,getApplicationContext());
        String AllowNorthWest = WorkingStorage.GetCharVal(Defines.SaveStreetNWVal,getApplicationContext());
        String AllowNorthEast = WorkingStorage.GetCharVal(Defines.SaveStreetNEVal,getApplicationContext());
        String AllowSouthWest = WorkingStorage.GetCharVal(Defines.SaveStreetSWVal,getApplicationContext());
        String AllowSouthEast = WorkingStorage.GetCharVal(Defines.SaveStreetSEVal,getApplicationContext());

        if (CodeToCheck.equals("8") || CodeToCheck.equals("N") )
        	if (AllowNorth.equals("N"))
        		return false;

        if (CodeToCheck.equals("2") || CodeToCheck.equals("S") )
        	if (AllowSouth.equals("N"))
        		return false;

        if (CodeToCheck.equals("6") || CodeToCheck.equals("E") )
        	if (AllowEast.equals("N"))
        		return false;
        
        if (CodeToCheck.equals("4") || CodeToCheck.equals("2") )
        	if (AllowWest.equals("N"))
        		return false;

        if (CodeToCheck.equals("7"))
        	if (AllowNorthWest.equals("N"))
        		return false;
        
        if (CodeToCheck.equals("9"))
        	if (AllowNorthEast.equals("N"))
        		return false;

        if (CodeToCheck.equals("1"))
        	if (AllowSouthWest.equals("N"))
        		return false;

        if (CodeToCheck.equals("3"))
        	if (AllowSouthEast.equals("N"))
        		return false;
        
        if (CodeToCheck.equals("5"))
       		return false;
        
		return true;
	}
	
}