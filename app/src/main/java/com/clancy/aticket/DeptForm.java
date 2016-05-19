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


public class DeptForm extends ActivityGroup {
	
	
	private void EnterClick()
	{
		TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String DescString = tvDesc.getText().toString().trim();
		EditText dept = (EditText) findViewById(R.id.editTextDept);		
		if (DescString.equals("NOT FOUND") || dept.getText().toString().trim().equals(""))
		{			
	        dept.selectAll();
	        dept.requestFocus();
		}
		else
		{
            WorkingStorage.StoreCharVal(Defines.SaveDeptVal, DescString.substring(16, 17),getApplicationContext());
            //Messagebox.Message(DescString.substring(16, 17), getApplicationContext());
            //WorkingStorage.StoreCharVal(Defines.PrintDeptVal, dept.getText().toString().trim(), getApplicationContext());            
            //Messagebox.Message(dept.getText().toString().trim(), getApplicationContext());
            if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")   			   		
            {
   			 	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
   			 	startActivityForResult(myIntent, 0);
   			 	finish();
   			 	overridePendingTransition(0, 0);           			
   			}
		}
	}
	
	private void LeftClick()
	{
        EditText dept = (EditText) findViewById(R.id.editTextDept);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());

        
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String deptDesc = SearchData.ScrollDownThroughFile("DEPT.T", getApplicationContext());
		if (deptDesc.equals("NIF") )
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
			if(deptDesc.length()> 10)
			{
				tvDesc.setText(deptDesc.substring(2));
		        WorkingStorage.StoreCharVal(Defines.RotateValue, deptDesc.substring(0,1), getApplicationContext());
		        dept.setText(deptDesc.substring(0,2));
		        dept.selectAll();
		        dept.requestFocus();	
			}
			else
			{
				LeftClick();
			}	
		}

	}
	
	private void RightClick()
	{
        EditText dept = (EditText) findViewById(R.id.editTextDept);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());

        TextView tvDesc = (TextView) findViewById(R.id.widget1);
		String deptDesc = SearchData.ScrollUpThroughFile("DEPT.T", getApplicationContext());
		if (deptDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
			if(deptDesc.length()> 10)
			{
				tvDesc.setText(deptDesc.substring(2));
		        WorkingStorage.StoreCharVal(Defines.RotateValue, deptDesc.substring(0,1), getApplicationContext());
		        dept.setText(deptDesc.substring(0,2));
		        dept.selectAll();
		        dept.requestFocus();	
			}
			else
			{
				RightClick();
			}			
		}
	}	
	
	private void TextChange(String SearchString)
	{
        TextView tvDesc = (TextView) findViewById(R.id.widget1);
        EditText dept = (EditText) findViewById(R.id.editTextDept);
   		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
        String deptDesc = SearchData.FindRecordLine(SearchString, SearchString.length(), "DEPT.T", getApplicationContext());
		if (deptDesc.equals("NIF"))
		{
			tvDesc.setText("NOT FOUND");			
		}else
		{
	        tvDesc.setText(deptDesc.substring(2));
	        WorkingStorage.StoreCharVal(Defines.RotateValue, deptDesc.substring(0,1), getApplicationContext());
	        dept.selectAll();
	        dept.requestFocus();	
		} 

	}
	
	public void onCreate(Bundle savedInstancedept) {
        super.onCreate(savedInstancedept);
        setContentView(R.layout.deptform);      
       
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
       
        EditText dept = (EditText) findViewById(R.id.editTextDept);
        
        TextView tvDesc = (TextView) findViewById(R.id.widget1);

        WorkingStorage.StoreLongVal(Defines.RecordPointer,0, getApplicationContext());
        String deptDesc = SearchData.ScrollUpThroughFile("DEPT.T", getApplicationContext());
        String deptCode = deptDesc.substring(0,2);
        tvDesc.setText(deptDesc.substring(2));
        dept.setText(deptCode);
        dept.requestFocus();
        dept.selectAll();
        
        
        KeyboardView keyboardView = null; 
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
   	    CustomKeyboard.PickAKeyboard(dept, "LICENSE", getApplicationContext(), keyboardView);
        if (savedInstancedept == null)
        {
       		WorkingStorage.StoreCharVal(Defines.ClearExitBoxVal, "CLEAR", getApplicationContext());
       		dept.selectAll();
        }
               
    	dept.setOnTouchListener(new View.OnTouchListener(){ 
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
        		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        		return true;
    		} 
       		});

       
        
        dept.addTextChangedListener(new TextWatcher(){        
        	public void beforeTextChanged(CharSequence s, int start, int count, int after){}        
        	public void onTextChanged(CharSequence s, int start, int before, int count){}
			public void afterTextChanged(Editable s) {
				TextChange(s.toString());
			}     
        });
        
        ImageButton left = (ImageButton) findViewById(R.id.buttonPrevious);
        left.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   LeftClick();
           }          
        });
        
        ImageButton right = (ImageButton) findViewById(R.id.buttonNext);
        right.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   RightClick();
           }          
        });
	}
	
}