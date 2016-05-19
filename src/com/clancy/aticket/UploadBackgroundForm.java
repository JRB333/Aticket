package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class UploadBackgroundForm extends ActivityGroup {
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
				  if (MiscFunctions.validInteger(ParseString1))
				  {
					  //Toast.makeText(getApplicationContext(), ParseString1+" and "+ParseString2, 500).show();
					  int OriginalFileSize = Integer.valueOf(ParseString1);
					  if(OriginalFileSize > 0)
					  {
						  if (!ParseString2.equals(""))
						  {
							  if(OriginalFileSize == SearchData.GetFileSize(ParseString2))
							  {
								  SystemIOFile.Delete(ParseString2);
								  //Toast.makeText(getApplicationContext(), "Deleted: "+ParseString2, 500).show();
							  }
						  }
					  }					  
				  }
			  }
			  else
			  {
				  //Toast.makeText(getApplicationContext(), "ParseString: "+ParseString1, 500).show();
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
	  
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadform);              
       	
               	
        TextView TextUpdate = (TextView)findViewById(R.id.widgetPrint);
        TextUpdate.setText("Uploading Data...");  
        
        String TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.UploadIPAddress, getApplicationContext()).trim();
        String CurrentUnitNumber = WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.CheckTimeVal,getApplicationContext());
        
        String UploadString = ReturnFileContents("TICKET.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
        {
        	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/TICK" + CurrentUnitNumber.toUpperCase() + ".R";
        	String FileSize = Integer.toString(SearchData.GetFileSize("TICKET.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
        	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|TICKET.R");
        }
        
        UploadString = ReturnFileContents("TICKTEMP.R");
        if (SearchData.GetFileSize("TICKTEMP.R") > 1100)
        {
            if (!UploadString.equals("ERROR") && UploadString.length() > 0)
            {
               	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/TICK" + CurrentUnitNumber.toUpperCase() + "_MUL.R";
               	String FileSize = Integer.toString(SearchData.GetFileSize("TICKTEMP.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
               	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|TICKTEMP.R");        		
            }                	
        }
        
        UploadString = ReturnFileContents("TICKSEVE.R");
        if (SearchData.GetFileSize("TICKSEVE.R") > 1900)
        {
            if (!UploadString.equals("ERROR") && UploadString.length() > 0)
            {
               	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/TICK" + CurrentUnitNumber.toUpperCase() + "_SEV.R";
               	String FileSize = Integer.toString(SearchData.GetFileSize("TICKSEVE.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
               	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|TICKSEVE.R");        		
            }                	
        }        
        
        UploadString = ReturnFileContents("CLANCY.J");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
        {
        	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/CLAN" + CurrentUnitNumber.toUpperCase() + ".R";
        	new HttpConnection(handler).put(URLString, UploadString, "5|CLANCY.J"); // HARDCODED 5 FOR THE FILE SIZE SO THE CLANCY.J NEVER GETS ERASED ON THE BACKGROUND UPLOAD!
        }
        
        UploadString = ReturnFileContents("ADDI.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
        {
        	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/ADDI" + CurrentUnitNumber.toUpperCase() + ".R";
        	String FileSize = Integer.toString(SearchData.GetFileSize("ADDI.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
        	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|ADDI.R");
        }   
                
        UploadString = ReturnFileContents("LOCA.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
        {
        	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/LOCA" + CurrentUnitNumber.toUpperCase() + ".R";
        	String FileSize = Integer.toString(SearchData.GetFileSize("LOCA.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
        	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|LOCA.R");
        }  
        
        UploadString = ReturnFileContents("VOID.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
        {
        	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/VOID" + CurrentUnitNumber.toUpperCase() + ".R";
        	String FileSize = Integer.toString(SearchData.GetFileSize("VOID.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine        	
        	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|VOID.R");
        }   
        
        UploadString = ReturnFileContents("HITT.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
        {
        	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/HITT" + CurrentUnitNumber.toUpperCase() + ".R";
        	String FileSize = Integer.toString(SearchData.GetFileSize("HITT.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
        	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|HITT.R");
        }      

        UploadString = ReturnFileContents("CHEC.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
        {
        	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/CHEC" + CurrentUnitNumber.toUpperCase() + ".R";
        	String FileSize = Integer.toString(SearchData.GetFileSize("CHEC.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine        	
        	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|CHEC.R");
        } 
        
        UploadString = ReturnFileContents("WIFI.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
        {
        	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/WIFI" + CurrentUnitNumber.toUpperCase() + ".R";
        	String FileSize = Integer.toString(SearchData.GetFileSize("WIFI.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine        	
        	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|WIFI.R");
        }         
        
        UploadString = ReturnFileContents("PICT.R");
        if (!UploadString.equals("ERROR") && UploadString.length() > 0)
        {
        	String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/PICT" + CurrentUnitNumber.toUpperCase() + ".R";
        	String FileSize = Integer.toString(SearchData.GetFileSize("PICT.R")); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine        	
        	new HttpConnection(handler).put(URLString, UploadString, FileSize+"|PICT.R");
        } 
        
	  		File dirChalk = new File("/data/data/com.clancy.aticket/files/");
  	  		String[] dirListChalk = dirChalk.list();
  	  		int blahChalk = dirListChalk.length;
  	  		int iChalk;
  	  		String tempStringChalk = ""; 
  	  		int ChalkCounter = 0;
  	  		for(iChalk=0;  iChalk < blahChalk; iChalk++)
  	  		{   
  	  			if (dirListChalk[iChalk].toUpperCase().contains("CHAL"))
  	  			{
  	  				ChalkCounter = ChalkCounter + 1;
  	  				if (ChalkCounter > 5) // Chalk files were not being sent up until 4-8-2013 - Send 5 max at a time to prevent massive influx
  	  				{
  	  					break;
  	  				}
  	  				tempStringChalk = dirListChalk[iChalk];
  	  				if (tempStringChalk.equals("CHAL"+ WorkingStorage.GetCharVal(Defines.PrintUnitNumber, getApplicationContext()) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, getApplicationContext()) + ".R") )
  	  				{
  	  					// DON'T SEND THE CURRENT DAYS CHALK FILE
  	  				}
  	  				else
  	  				{
  	  					if (SystemIOFile.exists(tempStringChalk))
  	  					{
  	  						UploadString = ReturnFileContents(tempStringChalk);
  	  						if (!UploadString.equals("ERROR") && UploadString.length() > 0)
  	  						{
  	  							String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/"+tempStringChalk;
  	  							String FileSize = Integer.toString(SearchData.GetFileSize(tempStringChalk)); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
 	  							new HttpConnection(handler).put(URLString, UploadString, FileSize+"|"+ tempStringChalk);
  	  							
  	  						} 
  	  					}	
  	  				}
  	  			}
  	  		}
        
  		File dir = new File("/data/data/com.clancy.aticket/files/");
  		String[] dirList = dir.list();
  		int blah = dirList.length;
  		int i;
  		String tempString = ""; 
  		for(i=0;  i < blah; i++)
  		{   
  			if (dirList[i].toUpperCase().contains("JPG"))
  			{
  				byte[] byteBuffer;
  				tempString = dirList[i];
  				if (tempString.equals("REPRINT1.JPG") || tempString.equals("REPRINT2.JPG") || tempString.equals("REPRINT3.JPG") )
  				{
  					// DON'T SEND THE REPRINT JPGS
  				}
  				else
  				{
  					if (SystemIOFile.exists(tempString))
  					{
  						byteBuffer = ReturnByteArray(tempString);
  						if (byteBuffer != null)
  						{
  							String URLString = "http://" + TempUploadIpAddress + "/DemoTickets/pictures/"+tempString;
  							String FileSize = Integer.toString(SearchData.GetFileSize(tempString)); //Note filesize here will be different than actual uploaded file size because of the line feed that is added in during the ReturnFileContents routine
  							new HttpConnection(handler).putBitmap(URLString, UploadString, byteBuffer,FileSize+"|"+ tempString);
  						} 
  					}	
  				}
  			}
  			
  			
  		} 	
        
  		
    	String URLString = "http://"+ TempUploadIpAddress +"/unload.asp";       	
    	new HttpConnection(handler).get(URLString);
  		
    	if (WorkingStorage.GetCharVal(Defines.ForceUploadVal, getApplicationContext()).equals("SELFUNC"))
    	{
    		Defines.clsClassName = SelFuncForm.class ;
    		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
    		startActivityForResult(myIntent, 0);
    	}
    	finish();
        overridePendingTransition(0, 0);	
	}

	public String ReturnFileContents(String fileName)
	{
   		String ReturnString = "ERROR";
   		if (!SystemIOFile.exists(fileName))
   			return ReturnString;
   			
		try {
   			FileInputStream in = openFileInput(fileName);
			if (in!=null)
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
			Toast.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000).show();
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
			FileInputStream fileInputStream = new FileInputStream(new File("/data/data/com.clancy.aticket/files/"+fileName) );
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
			Toast.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000).show();
		}
		return ReturnByteArray;
	}



}
