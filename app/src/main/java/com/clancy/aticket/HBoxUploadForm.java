package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HBoxUploadForm extends ActivityGroup {

	private Handler mHandler = new Handler();
	int NumberOfPasses = 0;

	Handler handler = new Handler()
	{
		public void handleMessage(Message message) {
			switch (message.what) {
				case HttpConnection.DID_START:
					//text.setText("Starting connection...");
					// Toast.makeText(getApplicationContext(), "Starting connection...", 2000).show();
					break;

				case HttpConnection.DID_SUCCEED:
					String response = (String) message.obj;
					//text.setText(response);
					//Toast.makeText(getApplicationContext(), response, 500).show();
					StringTokenizer tokens = new StringTokenizer(response, "|");
					String ParseString1 = "";
					String ParseString2 = "";

					if (tokens.hasMoreTokens() == true)
					{
						ParseString1 = tokens.nextToken();
					}

					if (tokens.hasMoreTokens() == true)
					{
						ParseString2 = tokens.nextToken();
					}

					if (!ParseString2.equals(""))
					{
						//Toast.makeText(getApplicationContext(), ParseString1 + " and " + ParseString2, 500).show();
						int OriginalFileSize = Integer.valueOf(ParseString1);
						if(OriginalFileSize > 0)
						{
							if (!ParseString2.equals(""))
							{
								if(OriginalFileSize == SearchData.GetFileSize(ParseString2))
								{
									SystemIOFile.Delete(ParseString2);
									//Toast.makeText(getApplicationContext(), "Deleted: " + ParseString2, Toast.LENGTH_LONG).show();
								}
							}
						}

						if (WorkingStorage.GetCharVal(Defines.LastFileUploadedVal, getApplicationContext()).equals(ParseString2))
						{
							String URLString = "http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext())+ "/unload.asp";
							new HttpConnection(handler).get(URLString);

							CustomVibrate.VibrateButton(getApplicationContext());
							Defines.clsClassName = SelFuncForm.class ;
							Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
							startActivityForResult(myIntent, 0);
							finish();
							overridePendingTransition(0, 0);
						}
					}
					else
					{
						//Toast.makeText(getApplicationContext(), "ParseString: " + ParseString1, 500).show();
					}
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

	private Runnable mHTTPConnectRunnable = new Runnable() {
		public void run()
		{

			String HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext()) + "/demotickets/whbox/Honorbox.htm", getApplicationContext());
			if (HTTPageFilesize.length()==8)
			{
				mHandler.postDelayed(mWaitRunnable, 100);
			}
			else
			{
				TextView tvMsg1 = (TextView) findViewById(R.id.textViewMsg1);
				TextView tvMsg2 = (TextView) findViewById(R.id.textViewMsg2);
				TextView tvLot = (TextView) findViewById(R.id.textViewLotNumber);
				tvMsg1.setText("Unable to connect");
				tvMsg2.setText("to website.");
				tvLot.setText("Please Retry");

				Button retry = (Button) findViewById(R.id.buttonRetry);
				retry.setVisibility(1);
			}
		}
	};

	private Runnable mWaitRunnable = new Runnable() {
		public void run()
		{
			String TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext()).trim();

			File dir = new File("/data/data/com.clancy.aticket/files/");
			String[] dirList = dir.list();
			int blah = dirList.length;
			int i;
			String tempString = "";
			for(i = 0;  i < blah; i++)
			{
				if (dirList[i].toUpperCase().contains(".HON"))
				{
					tempString = dirList[i];
					if (SystemIOFile.exists(tempString))
					{
						String UploadString = ReturnFileContents(tempString);
						if (!UploadString.equals("ERROR") && UploadString.length() > 0)
						{
							String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/WHBOX/" + tempString;
							String FileSize = Integer.toString(SearchData.GetFileSize(tempString)); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
							new HttpConnection(handler).put(URLString, UploadString, FileSize + "|" + tempString);
						}
						if (UploadString.length() == 0)
						{
							SystemIOFile.Delete(tempString); // get rid of zero byte files
						}
					}
				}

				if (dirList[i].toUpperCase().contains(".RTE"))
				{
					tempString = dirList[i];
					if (SystemIOFile.exists(tempString))
					{
						String UploadString = ReturnFileContents(tempString);
						if (!UploadString.equals("ERROR") && UploadString.length() > 0)
						{
							String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/WHBOX/" + tempString;
							String FileSize = Integer.toString(SearchData.GetFileSize(tempString)); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
							new HttpConnection(handler).put(URLString, UploadString, FileSize + "|" + tempString);
						}
						if (UploadString.length() == 0)
						{
							SystemIOFile.Delete(tempString); // get rid of zero byte files
						}
					}
				}

				if (dirList[i].toUpperCase().contains(".HPG"))
				{
					byte[] byteBuffer;
					tempString = dirList[i];
					if (SystemIOFile.exists(tempString))
					{
						byteBuffer = ReturnByteArray(tempString);
						if (byteBuffer != null)
						{
							String RenameHPG = tempString.subSequence(0, tempString.length()-3) + "JPG";
							String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/" + RenameHPG;
							String FileSize = Integer.toString(SearchData.GetFileSize(tempString)); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
							new HttpConnection(handler).putBitmap(URLString, "filler", byteBuffer,FileSize + "|" + tempString);
							//new HttpConnection(handler).put(URLString, UploadString, FileSize + "|" + tempString);
						}
					}
				}
			}
			WorkingStorage.StoreCharVal(Defines.LastFileUploadedVal, tempString, getApplicationContext());
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hboxuploadform);

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

		final TextView tvLot = (TextView) findViewById(R.id.textViewLotNumber);
		tvLot.setText("Lot # " + WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal, getApplicationContext()));

		final Button retry = (Button) findViewById(R.id.buttonRetry);
		retry.setVisibility(View.INVISIBLE);
		retry.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				TextView tvMsg1 = (TextView) findViewById(R.id.textViewMsg1);
				TextView tvMsg2 = (TextView) findViewById(R.id.textViewMsg2);
				tvMsg1.setText("Retrieving Previous");
				tvMsg2.setText("Honorbox Checks for");
				tvLot.setText("Lot # " + WorkingStorage.GetCharVal(Defines.SaveHBoxCodeVal, getApplicationContext()));
				retry.setVisibility(View.INVISIBLE);
				mHandler.postDelayed(mHTTPConnectRunnable, 40);
			}
		});

		mHandler.postDelayed(mHTTPConnectRunnable, 3000);
	}

	public String ReturnFileContents(String fileName)
	{
		String ReturnString = "ERROR";
		if (!SystemIOFile.exists(fileName))
			return ReturnString;

		try {
			FileInputStream in = openFileInput(fileName);
			if (in != null)
			{
				InputStreamReader tmp = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(tmp);
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\r\n");
						//sb.append(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				ReturnString = sb.toString();
			}
		}

		catch(Throwable t) {
			Toast.makeText(getApplicationContext(), "Ex: " + t.toString(), 2000).show();
		}
		return ReturnString;
	}

	public byte[] ReturnByteArray(String fileName)
	{
		byte[] ReturnByteArray = null;
		if (!SystemIOFile.exists(fileName))
			return null;

		int bytesRead, bytesAvailable, bufferSize;
		//byte[] buffer;
		int maxBufferSize = 1*1024*1024;

		try {
			FileInputStream fileInputStream = new FileInputStream(new File("/data/data/com.clancy.aticket/files/" + fileName) );
			if (fileInputStream!=null)
			{
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				ReturnByteArray = new byte[bufferSize];
				bytesRead = fileInputStream.read( ReturnByteArray, 0, bufferSize);
				fileInputStream.close();
			}
		}
		catch(Throwable t) {
			Toast.makeText(getApplicationContext(), "Ex: " + t.toString(), 2000).show();
		}
		return ReturnByteArray;
	}
}