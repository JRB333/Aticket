package com.clancy.aticket;

import java.io.File;

import android.content.Context;

public class UploadData { 
	
	public static void UploadDataFilesObsolete( Context dan)
	{
		Messagebox.Message("Beginning Upload", dan);
		Boolean FileSent = false;
   		String HTTPageFilesize = "";
   		String TempUploadIpAddress = "";
       	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan)+"/connected.asp", dan);
        if (HTTPageFilesize.length()==4)
        {
        	TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan).trim();
        }	
        else
        {
        	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.AlternateIPAddress, dan)+"/connected.asp", dan);
            if (HTTPageFilesize.length()==4)
            {
            	TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.AlternateIPAddress, dan).trim();
            }            
        }
        if (TempUploadIpAddress.equals(""))
        {
        	Messagebox.Message("Could not Connect to Upload.", dan);
        	return;
        }
        
        GetDate.GetDateTime(dan);
        String CurrentUnitNumber = WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, dan) + "_" + WorkingStorage.GetCharVal(Defines.CheckTimeVal,dan);
        FileSent = HTTPFileTransfer.UploadFileBinary("TICKET.R", "http://" + TempUploadIpAddress + "/DemoTickets/TICK" + CurrentUnitNumber.toUpperCase() + ".R", dan);
        if (FileSent == true)
        {
            SystemIOFile.Delete("TICKET.R");
            FileSent = HTTPFileTransfer.UploadFileBinary("CLANCY.J", "http://" + TempUploadIpAddress + "/DemoTickets/CLAN" + CurrentUnitNumber.toUpperCase() + ".R", dan);
        }
        FileSent = HTTPFileTransfer.UploadFileBinary("ADDI.R", "http://" + TempUploadIpAddress + "/DemoTickets/ADDI" + CurrentUnitNumber.toUpperCase() + ".R", dan);
        if (FileSent == true)
        {
            SystemIOFile.Delete("ADDI.R");            
        } 

        FileSent = HTTPFileTransfer.UploadFileBinary("LOCA.R", "http://" + TempUploadIpAddress + "/DemoTickets/LOCA" + CurrentUnitNumber.toUpperCase() + ".R", dan);
        if (FileSent == true)
        {
            SystemIOFile.Delete("LOCA.R");            
        } 

        FileSent = HTTPFileTransfer.UploadFileBinary("VOID.R", "http://" + TempUploadIpAddress + "/DemoTickets/VOID" + CurrentUnitNumber.toUpperCase() + ".R", dan);
        if (FileSent == true)
        {
            SystemIOFile.Delete("VOID.R");            
        }         
        FileSent = HTTPFileTransfer.UploadFileBinary("HITT.R", "http://" + TempUploadIpAddress + "/DemoTickets/HITT" + CurrentUnitNumber.toUpperCase() + ".R", dan);
        if (FileSent == true)
        {
            SystemIOFile.Delete("HITT.R");            
        } 
        FileSent = HTTPFileTransfer.UploadFileBinary("CHEC.R", "http://" + TempUploadIpAddress + "/DemoTickets/CHEC" + CurrentUnitNumber.toUpperCase() + ".R", dan);
        if (FileSent == true)
        {
            SystemIOFile.Delete("CHEC.R");            
        } 
        FileSent = HTTPFileTransfer.UploadFileBinary("PICT.R", "http://" + TempUploadIpAddress + "/DemoTickets/PICT" + CurrentUnitNumber.toUpperCase() + ".R", dan);
        if (FileSent == true)
        {
            SystemIOFile.Delete("PICT.R");            
        }         
        Messagebox.Message("Done uploading .r", dan);
        //todo to-do picture upload, chalk upload,
  		File dir = new File("/data/data/com.clancy.aticket/files/");
  		String[] dirList = dir.list();
  		int blah = dirList.length;
  		int i;
  		String tempString = ""; 
  		Messagebox.Message("Starting Loop", dan);
  		for(i=0;  i < blah; i++)
  		{   
  			if (dirList[i].toUpperCase().contains("JPG"))
  			{
  				tempString = dirList[i];
  				Messagebox.Message("uploading "+tempString, dan);
    		    FileSent = HTTPFileTransfer.UploadFileBinary(tempString, "http://" + TempUploadIpAddress + "/DemoTickets/pictures/"+tempString, dan);
    		    if (FileSent == true)
    		    {
    		    	EraseFile("/data/data/com.clancy.aticket/files/"+tempString);
    		    }
    		    	
  			}
  		} 	
  		Messagebox.Message("Running unload.asp", dan);
        HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+ TempUploadIpAddress +"/unload.asp", dan);
	}
	
	  static public int EraseFile(String filename) 
	  {

	 	int ReturnSize = 0;
	 	File file = new File(filename);
	 	//File file = new File(filename);
		if (!file.exists() || !file.isFile()) 
		{	    	
			ReturnSize = 0;
			file = null;
			return ReturnSize;
		}
		file.delete();
		file = null;
		return ReturnSize;
	  }	 
}
