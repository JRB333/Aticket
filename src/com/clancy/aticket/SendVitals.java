package com.clancy.aticket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class SendVitals {
	
	public static void UpdateVitals(String WhereAt, Context dan)
	{
		GetDate.GetDateTime(dan);
		String VitalsString = "";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PhoneNumberVal, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.ClientName, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.VersionNumberVal, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WhereAt;
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintDateVal, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + WorkingStorage.GetCharVal(Defines.PrintTimeVal, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "UI:" + WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "AI:" + WorkingStorage.GetCharVal(Defines.AlternateIPAddress, dan);;
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "S1:" + WorkingStorage.GetCharVal(Defines.SendVitalsIP1, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "S2:" + WorkingStorage.GetCharVal(Defines.SendVitalsIP2, dan);
        VitalsString = VitalsString + "|";
        VitalsString = VitalsString + "CI:" + WorkingStorage.GetCharVal(Defines.ClancyWebSiteIP, dan);
        VitalsString = VitalsString + "|";
	
        String FileNameString = "";
        FileNameString = "VITALS" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan) + ".R";
        
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
					Messagebox.Message(x.toString(), dan);
				}
				n = null;

			} catch (IOException e) {
				Messagebox.Message(e.toString(), dan);
			}        	
        }
        f = null;     
        if (MadeNewFile == true)
        {
    		Boolean FileSent = false;
    		String HTTPageFilesize = "";
        	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.SendVitalsIP1, dan)+"/vitals/vitals.asp", dan);
            if (HTTPageFilesize.length()==6)
            {
            	//Messagebox.Message("Writing Vitals", dan);
            	FileSent = HTTPFileTransfer.UploadFileBinary(FileNameString, "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP1,dan) + "/VITALS/VITALS" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan) + ".R", dan);
            	FileSent = HTTPFileTransfer.UploadFileBinary(ClancyFileNameString, "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP1,dan) + "/VITALS/CLANCY" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan) + ".R", dan);
            	String CurrentUnitNumber = WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, dan) + "_" + WorkingStorage.GetCharVal(Defines.CheckTimeVal,dan);
            	FileSent = HTTPFileTransfer.UploadFileBinary("PRIN.R", "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP1,dan) + "/VITALS/PRIN" + CurrentUnitNumber + ".R", dan);
            	SystemIOFile.Delete("PRIN.R"); // DELETE THE LOG BECUASE WE ARE SEARCHING FOR A PRINTER HANG ONLY
            }  
            else // now try the secondary server for updating
            {
            	HTTPageFilesize = HTTPFileTransfer.HTTPGetPageContent("http://"+WorkingStorage.GetCharVal(Defines.SendVitalsIP2, dan)+"/vitals/vitals.asp", dan);
            	if (HTTPageFilesize.length()==6)
                {
                	FileSent = HTTPFileTransfer.UploadFileBinary(FileNameString, "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP2,dan) + "/VITALS/VITALS" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan) + ".R", dan);
                	FileSent = HTTPFileTransfer.UploadFileBinary(ClancyFileNameString, "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP2,dan) + "/VITALS/CLANCY" + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan) + ".R", dan);
                	String CurrentUnitNumber = WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan) + "_" + WorkingStorage.GetCharVal(Defines.StampDateVal, dan) + "_" + WorkingStorage.GetCharVal(Defines.CheckTimeVal,dan);
                	FileSent = HTTPFileTransfer.UploadFileBinary("PRIN.R", "http://" + WorkingStorage.GetCharVal(Defines.SendVitalsIP2,dan) + "/VITALS/PRIN" + CurrentUnitNumber + ".R", dan);
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
	
	public static void GetPhoneNumber(Context dan)
	{
		String mPhoneNumber = "";
		//TelephonyManager tMgr =(TelephonyManager) dan.getSystemService(Context.TELEPHONY_SERVICE);
		TelephonyManager tMgr =(TelephonyManager) dan.getSystemService("phone");
		mPhoneNumber = tMgr.getLine1Number();
		if(mPhoneNumber == null)
		{
			WorkingStorage.StoreCharVal(Defines.PhoneNumberVal, "TABLET?", dan);
		}else
		{
			if (mPhoneNumber.equals("")) // get the serial number of the sim as a second best thing
			{
				mPhoneNumber = tMgr.getSimSerialNumber();			
			}
		//Messagebox.Message(mPhoneNumber, dan);
			if (mPhoneNumber.equals(""))
			{
				WorkingStorage.StoreCharVal(Defines.PhoneNumberVal, "UNKNOWN", dan);
			}
			else
			{
				WorkingStorage.StoreCharVal(Defines.PhoneNumberVal, mPhoneNumber, dan);
			}
		}
	}
	public static void GetPhoneModel(Context dan)
	{
		String PhoneModel = "";
		PhoneModel = Build.MODEL; 
		if (PhoneModel.equals(""))
		{
			WorkingStorage.StoreCharVal(Defines.PhoneModelVal, "UNKNOWN", dan);
		}
		else
		{
			WorkingStorage.StoreCharVal(Defines.PhoneModelVal, PhoneModel, dan);
		}
	}
	
}
