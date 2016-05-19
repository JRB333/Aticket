package com.clancy.aticket;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WiFiOnForm extends ActivityGroup {

	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifionform);
       
        Button second = (Button) findViewById(R.id.buttonESC);
        second.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               finish();
               overridePendingTransition(0, 0);
          }          
        });
      
       Button enter = (Button) findViewById(R.id.buttonEnter);
       enter.setOnClickListener(new View.OnClickListener() {
	           public void onClick(View view) {
	               finish();
	               overridePendingTransition(0, 0);        	   
	           }	          
	   });

	}
}