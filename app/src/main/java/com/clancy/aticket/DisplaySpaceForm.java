package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DisplaySpaceForm extends ActivityGroup {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displayspaceform);


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
				if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
				{
					Intent myIntent = new Intent(view.getContext(), SwitchForm.class);
					startActivityForResult(myIntent, 0);
					finish();
					overridePendingTransition(0, 0);
				}
			}
		});
		enter.requestFocus();

		ShowTheSpace(WorkingStorage.GetCharVal(Defines.SavePlateVal, getApplicationContext()), WorkingStorage.GetCharVal(Defines.SaveStreetVal, getApplicationContext()), WorkingStorage.GetCharVal(Defines.SaveStateVal, getApplicationContext()));
	}

	private void ShowTheSpace(String PlatePassed, String StreetPassed, String StatePassed)
	{
		String TimeBuffer = "NONE";
		WorkingStorage.StoreCharVal(Defines.ChalkTimeVal, TimeBuffer, getApplicationContext());

		if (PlatePassed.equals(""))
		{
			return;
		}
		String line = "";
		String ChalkFileName = "/data/data/com.clancy.aticket/files/CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R";
		File file = new File(ChalkFileName);
		String ChalkString = "";
		if(file.exists())   // check if file exist
		{
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));

				int i = 1;
				while ((line = br.readLine()) != null )
				{
					if (line.length() >= 45)
					{
						if (line.substring(0,8).trim().equals(PlatePassed.trim()) && line.substring(8,11).trim().equals(StreetPassed.trim()))
						{
							ChalkString = line.substring(12,20).trim() ;

						}
					}
					i++;
				}
				br.close();
				file = null;
			}catch (IOException e)
			{

			}
		}
		else
		{
			file = null;
		}

		if (!WorkingStorage.GetCharVal(Defines.TxfrSpace,getApplicationContext()).equals("")) {
			ChalkString = WorkingStorage.GetCharVal(Defines.TxfrSpace,getApplicationContext());
			WorkingStorage.StoreCharVal(Defines.TxfrSpace, "", getApplicationContext());
		}

		TextView tvLast = (TextView) findViewById(R.id.TextView2);
		tvLast.setText(ChalkString);

		//eChalks.setText(ChalkString);
	}
}