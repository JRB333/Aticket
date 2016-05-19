package com.clancy.aticket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class SendVitals {
	
	public static void UpdateVitals(String WhereAt, Context cntxt)
	{
		GetDate.GetDateTime(cntxt);
		String VitalsString = "";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PhoneNumberVal, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.ClientName, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.VersionNumberVal, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WhereAt;
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintDateVal, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintTimeVal, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "UI:" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "AI:" + WorkingStorage.GetCharVal(Defines.AlternateIPAddress, cntxt);;
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "S1:" + WorkingStorage.GetCharVal(Defines.SendVitalsIP1, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "S2:" + WorkingStorage.GetCharVal(Defines.SendVitalsIP2, cntxt);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "CI:" + WorkingStorage.GetCharVal(Defines.ClancyWebSiteIP, cntxt);
        VitalsString = VitalsString + "|";
	
        String FileNameString = "";
        FileNameString = "VITALS" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt) + ".R";
        
        String ClancyFileNameString = "";
        ClancyFileNameString = "CLANCY.J";
        
        File f;
        f=new File("/data/data/com.clancy.aticket/files/",FileNameString);
        if(f.exists())
        {
        	f.delete();
        }
        f = null;
        
        Boolean MadeNewFile = false;
        File n;
        n=new File("/data/data/com.clancy.aticket/files/",FileNameString);
        if(!n.exists()) //We deleted the vitals file successfully
        {
        	try {
				n.createNewFile();
				try  
				{
					BufferedWriter writer = new BufferedWriter(new FileWriter(n));
					writer.write(VitalsString);
					writer.flush();
					writer.close();
					MadeNewFile = true;
				} catch (IOException x) {
					n = null;
					Messagebox.Message(x.toString(), cntxt);
				}
				n = null;

			} catch (IOException e) {
				Messagebox.Message(e.toString(), cntxt);
			}        	
        }
        f = null;     
        if (MadeNewFile == true)
        {
    		Boolean FileSent = false;
    		String HTTPageFilesize = "";
        	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.SendVitalsIP1, cntxt)+"/vitals/vitals.asp", cntxt);
            if (HTTPageFilesize.length()==6)
            {
            	//Messagebox.Message("Writing Vitals", cntxt);
            	FileSent = HTTPFileTransfer.UploadFileBinary(FileNameString, "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP1,cntxt) + "/VITALS/VITALS" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt) + ".R", cntxt);
            	FileSent = HTTPFileTransfer.UploadFileBinary(ClancyFileNameString, "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP1,cntxt) + "/VITALS/CLANCY" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt) + ".R", cntxt);
            	String CurrentUnitNumber = WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, cntxt) + "_" + WorkingStorage.GetCharVal(Defines.CheckTimeVal,cntxt);
            	FileSent = HTTPFileTransfer.UploadFileBinary("PRIN.R", "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP1,cntxt) + "/VITALS/PRIN" + CurrentUnitNumber + ".R", cntxt);
            	SystemIOFile.Delete("PRIN.R"); // DELETE THE LOG BECUASE WE ARE SEARCHING FOR A PRINTER HANG ONLY
            }  
            else // now try the secondary server for updating
            {
            	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.SendVitalsIP2, cntxt)+"/vitals/vitals.asp", cntxt);
            	if (HTTPageFilesize.length()==6)
                {
                	FileSent = HTTPFileTransfer.UploadFileBinary(FileNameString, "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP2,cntxt) + "/VITALS/VITALS" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt) + ".R", cntxt);
                	FileSent = HTTPFileTransfer.UploadFileBinary(ClancyFileNameString, "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP2,cntxt) + "/VITALS/CLANCY" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt) + ".R", cntxt);
                	String CurrentUnitNumber = WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, cntxt) + "_" + WorkingStorage.GetCharVal(Defines.CheckTimeVal,cntxt);
                	FileSent = HTTPFileTransfer.UploadFileBinary("PRIN.R", "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP2,cntxt) + "/VITALS/PRIN" + CurrentUnitNumber + ".R", cntxt);
                	SystemIOFile.Delete("PRIN.R"); // DELETE THE LOG BECUASE WE ARE SEARCHING FOR A PRINTER HANG ONLY
                }
            }
            if(FileSent==false)
            {
            	//leave this test in here to prevent Eclipse from throwing a warning
            	//since we never do anything with FileSent
            }
        }
	}
	
	public static void GetPhoneNumber(Context cntxt)
	{
		String mPhoneNumber = "";
		//TelephonyManager tMgr =(TelephonyManager) cntxt.getSystemService(Context.TELEPHONY_SERVICE);
		TelephonyManager tMgr =(TelephonyManager) cntxt.getSystemService("phone");
		mPhoneNumber = tMgr.getLine1Number();
		if(mPhoneNumber == null)
		{
			WorkingStorage.StoreCharVal(Defines.PhoneNumberVal, "TABLET?", cntxt);
		}else
		{
			if (mPhoneNumber.equals("")) // get the serial number of the sim as a second best thing
			{
				mPhoneNumber = tMgr.getSimSerialNumber();			
			}
		//Messagebox.Message(mPhoneNumber, cntxt);
			if (mPhoneNumber.equals(""))
			{
				WorkingStorage.StoreCharVal(Defines.PhoneNumberVal, "UNKNOWN", cntxt);
			}
			else
			{
				WorkingStorage.StoreCharVal(Defines.PhoneNumberVal, mPhoneNumber, cntxt);
			}
		}
	}

	public static void GetPhoneModel(Context cntxt)
	{
		String PhoneModel = "";
		PhoneModel = Build.MODEL; 
		if (PhoneModel.equals(""))
		{
			WorkingStorage.StoreCharVal(Defines.PhoneModelVal, "UNKNOWN", cntxt);
		}
		else
		{
			WorkingStorage.StoreCharVal(Defines.PhoneModelVal, PhoneModel, cntxt);
		}
	}
}
