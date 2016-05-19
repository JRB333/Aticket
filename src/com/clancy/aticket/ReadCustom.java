package com.clancy.aticket;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;
import android.widget.Toast;


public class ReadCustom {
	public static void ReadCustomLayout(Context dan)
	{
		 File file = new File("/data/data/com.clancy.aticket/files/","CUSTOM.A");  
         if(file.exists())   // check if file exist  
		 {  
        	 if (file.length()<5)
        	 {
        		 return;
        	 }
        	 WorkingStorage.StoreCharVal(Defines.SpecialComment, "NO", dan);
        	 WorkingStorage.StoreCharVal(Defines.ChalkData, "", dan);
        	 WorkingStorage.StoreCharVal(Defines.MeterLastPrompt, "", dan);
        	 WorkingStorage.StoreCharVal(Defines.MeterNumberMessage, "", dan);
    		 WorkingStorage.StoreCharVal(Defines.LotCheckMessage,"", dan);
        	 WorkingStorage.StoreCharVal(Defines.NewServerFlagVal, "", dan);
        	 WorkingStorage.StoreCharVal(Defines.GPSSurveyVal, "NO", dan);
        	 
        	 //StringBuilder text = new StringBuilder();  		   
		     try {  
		         BufferedReader br = new BufferedReader(new FileReader(file));  
		         String line;  		   
		         while ((line = br.readLine()) != null) 
		         {
						if (line.length()>= 3)
						{
							//String ShortLine = line.substring(2);
							if (line.substring(0,2).equals("AB"))
								WorkingStorage.StoreCharVal(Defines.AndroidBuildsVal, line.substring(2), dan);							
							if (line.substring(0,2).equals("AI"))
								WorkingStorage.StoreCharVal(Defines.AlternateIPAddress, line.substring(2).trim(), dan);
							if (line.substring(0,2).equals("AM"))
								WorkingStorage.StoreCharVal(Defines.AddFeeMessage, line.substring(2), dan);
							if (line.substring(0,2).equals("AP"))
								WorkingStorage.StoreCharVal(Defines.PrintAmPmFlag, line.substring(2), dan);
							if (line.substring(0,2).equals("BM"))
								WorkingStorage.StoreCharVal(Defines.BeatMessage, line.substring(2), dan);
							if (line.substring(0,2).equals("CC"))
								WorkingStorage.StoreCharVal(Defines.CacheCourtDateVal, line.substring(2), dan);
							if (line.substring(0,2).equals("CD"))
								WorkingStorage.StoreCharVal(Defines.ChalkData, line.substring(2), dan);
							if (line.substring(0,2).equals("CI"))
								WorkingStorage.StoreCharVal(Defines.ClancyWebSiteIP, line.substring(2), dan);
							if (line.substring(0,2).equals("CL"))
								WorkingStorage.StoreCharVal(Defines.CommentLastPrompt, line.substring(2), dan);
							if (line.substring(0,2).equals("CM"))
								WorkingStorage.StoreCharVal(Defines.CourtFormMessage, line.substring(2), dan);
							if (line.substring(0,2).equals("CP"))
								WorkingStorage.StoreCharVal(Defines.CustomCitationPrefix, line.substring(2), dan);
							if (line.substring(0,2).equals("CS"))
								WorkingStorage.StoreCharVal(Defines.SpecialComment, line.substring(2), dan);
							if (line.substring(0,2).equals("DC"))
								WorkingStorage.StoreCharVal(Defines.CanadaDateFlag, line.substring(2), dan);
							if (line.substring(0,2).equals("DD"))
								WorkingStorage.StoreCharVal(Defines.CourtDueDays, line.substring(2), dan);
							if (line.substring(0,2).equals("DR"))
								WorkingStorage.StoreCharVal(Defines.DefaultDailyRate, line.substring(2), dan);
							if (line.substring(0,2).equals("EX"))
								WorkingStorage.StoreCharVal(Defines.ExpandXMeter, line.substring(2), dan);
							if (line.substring(0,2).equals("ER"))
								WorkingStorage.StoreCharVal(Defines.ExpiredRegistration, line.substring(2), dan);
							if (line.substring(0,2).equals("GS"))
								WorkingStorage.StoreCharVal(Defines.GPSSurveyVal, line.substring(2), dan);
							if (line.substring(0,2).equals("H2"))
								WorkingStorage.StoreCharVal(Defines.HBoxEnterRate, line.substring(2), dan);
							if (line.substring(0,2).equals("HC"))
								WorkingStorage.StoreCharVal(Defines.HittAndCheckFlag, line.substring(2), dan);
							if (line.substring(0,2).equals("HD"))
								WorkingStorage.StoreCharVal(Defines.HonorMultiDays, line.substring(2), dan);
							if (line.substring(0,2).equals("HS"))
								WorkingStorage.StoreCharVal(Defines.DefaultState, line.substring(2), dan);
							if (line.substring(0,2).equals("LA"))
								WorkingStorage.StoreCharVal(Defines.LanguageType, line.substring(2), dan);
							if (line.substring(0,2).equals("LM"))
								WorkingStorage.StoreCharVal(Defines.LotCheckMessage, line.substring(2), dan);							
							if (line.substring(0,2).equals("MC"))
								WorkingStorage.StoreCharVal(Defines.MonthlyPermitClientVal, line.substring(2), dan);
							if (line.substring(0,2).equals("ML"))
								WorkingStorage.StoreCharVal(Defines.MeterLastPrompt, line.substring(2), dan);
							if (line.substring(0,2).equals("MN"))
								WorkingStorage.StoreCharVal(Defines.MeterNumberMessage, line.substring(2), dan);
							if (line.substring(0,2).equals("MO"))
								WorkingStorage.StoreCharVal(Defines.MultipleBootOccurance, line.substring(2), dan);
							if (line.substring(0,2).equals("MR"))
								WorkingStorage.StoreCharVal(Defines.MonthFlagRequired, line.substring(2), dan);
							if (line.substring(0,2).equals("MS"))
								WorkingStorage.StoreCharVal(Defines.TicketMessage, line.substring(2), dan);
							if (line.substring(0,2).equals("NF"))
								WorkingStorage.StoreCharVal(Defines.NoFeeFlag, line.substring(2), dan);
							if (line.substring(0,2).equals("NS"))
								WorkingStorage.StoreCharVal(Defines.NewServerFlagVal, line.substring(2), dan);
							if (line.substring(0,2).equals("NT"))
								WorkingStorage.StoreCharVal(Defines.UseNYType, line.substring(2), dan);
							if (line.substring(0,2).equals("OV"))
								WorkingStorage.StoreCharVal(Defines.OfficerOverrideVBootVal, line.substring(2), dan);
							if (line.substring(0,2).equals("PF"))
								WorkingStorage.StoreCharVal(Defines.PrefixVal, line.substring(2), dan);
							if (line.substring(0,2).equals("PL"))
								WorkingStorage.StoreCharVal(Defines.PrintLongViolationVal, line.substring(2), dan);
							if (line.substring(0,2).equals("PP"))
								WorkingStorage.StoreCharVal(Defines.PinPointVal, line.substring(2), dan);
							if (line.substring(0,2).equals("RU"))
								WorkingStorage.StoreCharVal(Defines.RemoteUsernameVal, line.substring(2), dan);
							if (line.substring(0,2).equals("RP"))
								WorkingStorage.StoreCharVal(Defines.RemotePasswordVal, line.substring(2), dan);
							if (line.substring(0,2).equals("S1"))
								WorkingStorage.StoreCharVal(Defines.SendVitalsIP1, line.substring(2).trim(), dan);
							if (line.substring(0,2).equals("S2"))
								WorkingStorage.StoreCharVal(Defines.SendVitalsIP2, line.substring(2).trim(), dan);
							if (line.substring(0,2).equals("SC"))
								WorkingStorage.StoreCharVal(Defines.RememberChalk, line.substring(2), dan);
							if (line.substring(0,2).equals("SD"))
								WorkingStorage.StoreCharVal(Defines.StrictDirectionFlag, line.substring(2), dan);
							if (line.substring(0,2).equals("SM"))
								WorkingStorage.StoreCharVal(Defines.SpecialMeterFlag, line.substring(2), dan);
							if (line.substring(0,2).equals("SN"))
								WorkingStorage.StoreCharVal(Defines.StreetNumberMessage, line.substring(2), dan);
							if (line.substring(0,2).equals("SP"))
								WorkingStorage.StoreCharVal(Defines.SecretPassword, line.substring(2), dan);
							if (line.substring(0,2).equals("ST"))
								WorkingStorage.StoreCharVal(Defines.AllowSecondTicket, line.substring(2), dan);
							if (line.substring(0,2).equals("TP"))
								WorkingStorage.StoreCharVal(Defines.TakeHBoxPhoto, line.substring(2), dan);
							if (line.substring(0,2).equals("UI"))
								WorkingStorage.StoreCharVal(Defines.UploadIPAddress, line.substring(2).trim(), dan);
							if (line.substring(0,2).equals("UO"))
								WorkingStorage.StoreCharVal(Defines.UseOfflinePasswordVal, line.substring(2), dan);							
							if (line.substring(0,2).equals("US"))
								WorkingStorage.StoreCharVal(Defines.ClientName, line.substring(2).trim(), dan);
							if (line.substring(0,2).equals("UT"))
								WorkingStorage.StoreCharVal(Defines.UploadPrompt, line.substring(2), dan);
							if (line.substring(0,2).equals("VB"))
								WorkingStorage.StoreCharVal(Defines.UseVBoot, line.substring(2), dan);
							if (line.substring(0,2).equals("VN"))
								WorkingStorage.StoreCharVal(Defines.NotOnVBoot, line.substring(2), dan);
							if (line.substring(0,2).equals("VX"))
								WorkingStorage.StoreCharVal(Defines.NormalVBoot, line.substring(2), dan);
							if (line.substring(0,2).equals("V1"))
								WorkingStorage.StoreCharVal(Defines.OnVBootLevel1, line.substring(2), dan);
							if (line.substring(0,2).equals("V2"))
								WorkingStorage.StoreCharVal(Defines.OnVBootLevel2, line.substring(2), dan);
							if (line.substring(0,2).equals("V3"))
								WorkingStorage.StoreCharVal(Defines.OnVBootLevel3, line.substring(2), dan);
							if (line.substring(0,2).equals("V4"))
								WorkingStorage.StoreCharVal(Defines.OnVBootLevel4, line.substring(2), dan);
							if (line.substring(0,2).equals("V5"))
								WorkingStorage.StoreCharVal(Defines.OnVBootLevel5, line.substring(2), dan);
							if (line.substring(0,2).equals("V6"))
								WorkingStorage.StoreCharVal(Defines.OnVBootLevel6, line.substring(2), dan);
							if (line.substring(0,2).equals("V7"))
								WorkingStorage.StoreCharVal(Defines.OnVBootLevel7, line.substring(2), dan);
							if (line.substring(0,2).equals("V8"))
								WorkingStorage.StoreCharVal(Defines.OnVBootLevel8, line.substring(2), dan);
							if (line.substring(0,2).equals("V9"))
								WorkingStorage.StoreCharVal(Defines.OnVBootLevel9, line.substring(2), dan);
							if (line.substring(0,2).equals("XC"))
								WorkingStorage.StoreCharVal(Defines.RetainXComment, line.substring(2), dan);
							if (line.substring(0,2).equals("YM"))
								WorkingStorage.StoreCharVal(Defines.YearMonthTogether, line.substring(2), dan);
							if (line.substring(0,2).equals("WH"))
								WorkingStorage.StoreCharVal(Defines.WirelessHonorboxVal, line.substring(2), dan);							
						}
		         }  
		         br.close();
		         file = null;
		         
		         if (WorkingStorage.GetCharVal(Defines.AndroidBuildsVal, dan).equals(""))
	        		 WorkingStorage.StoreCharVal(Defines.AndroidBuildsVal,"5", dan); // default to 5, in a pinch we can up it in the custom.a file 
		         if (WorkingStorage.GetCharVal(Defines.AlternateIPAddress, dan).equals(""))
		        	 WorkingStorage.StoreCharVal(Defines.AlternateIPAddress, WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan).trim(), dan); 
		         if (WorkingStorage.GetCharVal(Defines.PinPointVal, dan).equals(""))
	        		 WorkingStorage.StoreCharVal(Defines.PinPointVal,"NO", dan); 
		         if (WorkingStorage.GetCharVal(Defines.MonthlyPermitClientVal, dan).equals(""))
	        		 WorkingStorage.StoreCharVal(Defines.MonthlyPermitClientVal,"LAMTA", dan); 
		         if (WorkingStorage.GetCharVal(Defines.SendVitalsIP1, dan).equals(""))
	        		 WorkingStorage.StoreCharVal(Defines.SendVitalsIP1,"107.1.38.34", dan); 
		         if (WorkingStorage.GetCharVal(Defines.SendVitalsIP2, dan).equals(""))
	        		 WorkingStorage.StoreCharVal(Defines.SendVitalsIP2,"173.164.42.142", dan); 
		         if (WorkingStorage.GetCharVal(Defines.ClancyWebSiteIP, dan).equals(""))
	        		 WorkingStorage.StoreCharVal(Defines.ClancyWebSiteIP,"173.164.42.142", dan); 
		         if (WorkingStorage.GetCharVal(Defines.PinPointVal, dan).equals(""))
	        		 WorkingStorage.StoreCharVal(Defines.PinPointVal,"NO", dan); 
		         if (WorkingStorage.GetCharVal(Defines.PinPointVal, dan).equals(""))
	        		 WorkingStorage.StoreCharVal(Defines.PinPointVal,"NO", dan); 
		         if (WorkingStorage.GetCharVal(Defines.RetainXComment, dan).equals(""))
	        		 WorkingStorage.StoreCharVal(Defines.RetainXComment,"YES", dan); 
 

		         
		     }catch (IOException e) 
		     {  
					Toast.makeText(dan, e.toString(), 2000);  
		     }  
         }
         else  
		 {  
        	 file = null;
		 }  	
	}	 
}
