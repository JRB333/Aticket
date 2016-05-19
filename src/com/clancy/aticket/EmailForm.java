package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.StringTokenizer;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EmailForm extends ActivityGroup {
	  
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emailform);
       
        Button second = (Button) findViewById(R.id.buttonESC);
        second.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
	     	   if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
	    	   {
				   Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
				   startActivityForResult(myIntent, 0);
				   finish();
				   overridePendingTransition(0, 0);    
	    	   }
          }          
        });
        String MessageText = "";       
        
   		try {
   			FileInputStream in = openFileInput("EMAIL.T");
			if (in!=null)
			{
				InputStreamReader tmp = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(tmp);
				String str;
				StringBuffer buf= new StringBuffer();
				while ((str = reader.readLine())!= null)
				{
					buf.append(str+"\n");
				}
				in.close();
				MessageText = buf.toString();
	  			//Toast.makeText(getApplicationContext(), buf.toString(), Toast.LENGTH_LONG ).show();
			}

		}  
		catch(Throwable t) { 
			Toast
			.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
			.show();
		}   
        
        EditText eMessage = (EditText) findViewById(R.id.editTextEmail);
        eMessage.setText(MessageText);
        

	}
}