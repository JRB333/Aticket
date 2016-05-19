package com.clancy.aticket;

import java.io.File;

import android.content.Context;

public class UploadData { 
	
	public static void UploadDataFilesObsolete( Context cntxt)
	{
		Messagebox.Message("Beginning Upload", cntxt);
		Boolean FileSent = false;
   		String HTTPageFilesize = "";
   		String TempUploadIpAddress = "";
       	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, cntxt) + "/connected.asp", cntxt);

        if (HTTPageFilesize.length() == 4) {
        	TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.UploadIPAddress, cntxt).trim();
        } else {
        	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://" + WorkingStorage.GetCharVal(Defines.AlternateIPAddress, cntxt) + "/connected.asp", cntxt);
            if (HTTPageFilesize.length() == 4)
            {
            	TempUploadIpAddress = WorkingStorage.GetCharVal(Defines.AlternateIPAddress, cntxt).trim();
            }            
        }

        if (TempUploadIpAddress.equals(""))
        {
        	Messagebox.Message("Could not Connect to Upload.", cntxt);
        	return;
        }
        
        GetDate.GetDateTime(cntxt);
        String CurrentUnitNumber = WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, cntxt) + "_" + WorkingStorage.GetCharVal(Defines.CheckTimeVal,cntxt);
        FileSent = HTTPFileTransfer.UploadFileBinary("TICKET.R", "http://" + TempUploadIpAddress + "/DemoTickets/TICK" + CurrentUnitNumber.toUpperCase() + ".R", cntxt);
        if (FileSent == true)
        {
            SystemIOFile.Delete("TICKET.R");
            FileSent = HTTPFileTransfer.UploadFileBinary("CLANCY.J", "http://" + TempUploadIpAddress + "/DemoTickets/CLAN" + CurrentUnitNumber.toUpperCase() + ".R", cntxt);
        }
        FileSent = HTTPFileTransfer.UploadFileBinary("ADDI.R", "http://" + TempUploadIpAddress + "/DemoTickets/ADDI" + CurrentUnitNumber.toUpperCase() + ".R", cntxt);
        if (FileSent == true)
        {
            SystemIOFile.Delete("ADDI.R");            
        } 

        FileSent = HTTPFileTransfer.UploadFileBinary("LOCA.R", "http://" + TempUploadIpAddress + "/DemoTickets/LOCA" + CurrentUnitNumber.toUpperCase() + ".R", cntxt);
        if (FileSent == true)
        {
            SystemIOFile.Delete("LOCA.R");            
        } 

        FileSent = HTTPFileTransfer.UploadFileBinary("VOID.R", "http://" + TempUploadIpAddress + "/DemoTickets/VOID" + CurrentUnitNumber.toUpperCase() + ".R", cntxt);
        if (FileSent == true)
        {
            SystemIOFile.Delete("VOID.R");            
        }         
        FileSent = HTTPFileTransfer.UploadFileBinary("HITT.R", "http://" + TempUploadIpAddress + "/DemoTickets/HITT" + CurrentUnitNumber.toUpperCase() + ".R", cntxt);
        if (FileSent == true)
        {
            SystemIOFile.Delete("HITT.R");            
        } 
        FileSent = HTTPFileTransfer.UploadFileBinary("CHEC.R", "http://" + TempUploadIpAddress + "/DemoTickets/CHEC" + CurrentUnitNumber.toUpperCase() + ".R", cntxt);
        if (FileSent == true)
        {
            SystemIOFile.Delete("CHEC.R");            
        } 
        FileSent = HTTPFileTransfer.UploadFileBinary("PICT.R", "http://" + TempUploadIpAddress + "/DemoTickets/PICT" + CurrentUnitNumber.toUpperCase() + ".R", cntxt);
        if (FileSent == true)
        {
            SystemIOFile.Delete("PICT.R");            
        }         
        Messagebox.Message("Done uploading .r", cntxt);
        //todo to-do picture upload, chalk upload,
  		File dir = new File("/data/data/com.clancy.aticket/files/");
  		String[] dirList = dir.list();
  		int blah = dirList.length;
  		int i;
  		String tempString = ""; 
  		Messagebox.Message("Starting Loop", cntxt);
  		for(i=0;  i < blah; i++)
  		{   
  			if (dirList[i].toUpperCase().contains("JPG"))
  			{
  				tempString = dirList[i];
  				Messagebox.Message("uploading "+tempString, cntxt);
    		    FileSent = HTTPFileTransfer.UploadFileBinary(tempString, "http://" + TempUploadIpAddress + "/DemoTickets/pictures/"+tempString, cntxt);
    		    if (FileSent == true)
    		    {
    		    	EraseFile("/data/data/com.clancy.aticket/files/"+tempString);
    		    }
    		    	
  			}
  		} 	
  		Messagebox.Message("Running unload.asp", cntxt);
        HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+ TempUploadIpAddress +"/unload.asp", cntxt);
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
