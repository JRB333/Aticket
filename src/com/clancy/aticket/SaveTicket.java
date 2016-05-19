package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import android.content.Context;
import android.widget.Toast;


public class SaveTicket {
	static int MODE_APPEND = 32768; //USE THIS PARAMETER FOR A FILE THAT EXISTS
	static int MODE_PRIVATE = 0; //USE THIS PARAMETER FOR A NEW FILE
	
	
	public static Boolean SaveTickFile(Context dan)
	{	           		
		// changed to a boolean return  on 11-11/2013 for honorbox voiding issue
		if (WorkingStorage.GetCharVal(Defines.SavePlateVal, dan).equals("NOPLATE"))
		{
            WorkingStorage.StoreCharVal(Defines.SaveStateVal, "  ", dan);
            WorkingStorage.StoreCharVal(Defines.PrintStateVal, "", dan);            

		}

		String WriteBuffer = "";
        String CitationNumber = "";
        NextCiteNum.GetNextCitationNumber(0, dan);
        
        if (WorkingStorage.GetCharVal(Defines.ClientName,dan).equals("RICHMOND")
        	&& WorkingStorage.GetCharVal(Defines.FootBallFlagVal,dan).equals("1"))
        {
        	WorkingStorage.StoreCharVal(Defines.PrintPermitVal, WorkingStorage.GetCharVal(Defines.PrintPlateVal, dan),dan);
        	String TempString = WorkingStorage.GetCharVal(Defines.PrintDateVal,dan);
        	TempString = "TOW-" + TempString.substring(0, 2) + TempString.substring(8,10);
        	WorkingStorage.StoreCharVal(Defines.SavePlateVal, TempString,dan);
        }
        
        CitationNumber = WorkingStorage.GetCharVal(Defines.PrintCitationVal, dan);
		WriteBuffer = CitationNumber;
        while (WriteBuffer.length() < 8)
        	WriteBuffer += " ";
	    //WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintBeatVal, dan);
        while (WriteBuffer.length() < 12)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, dan);
        while (WriteBuffer.length() < 24)
        	WriteBuffer += " ";
        WriteBuffer = WriteBuffer.substring(0, 23); //do this to trim officer length of greater than 12        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, dan);
        while (WriteBuffer.length() < 29)
        	WriteBuffer += " "; 
        //WriteBuffer = WriteBuffer.substring(0, 29); //added this on 11/20/2012 to compensate for Richmond badge overrunning the length
        WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveDateVal, dan);
        while (WriteBuffer.length() < 35)
        	WriteBuffer += " ";        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveTimeStartVal, dan);
        while (WriteBuffer.length() < 39)
        	WriteBuffer += " ";        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SavePlateVal, dan);
        while (WriteBuffer.length() < 56)
        	WriteBuffer += " ";    
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveTypeVal, dan);
        while (WriteBuffer.length() < 57)
        	WriteBuffer += " ";    
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveYearVal, dan);
        while (WriteBuffer.length() < 59)
        	WriteBuffer += " ";    
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveMonthVal, dan);
        while (WriteBuffer.length() < 61)
        	WriteBuffer += " ";  
        if (WorkingStorage.GetCharVal(Defines.ClientName,dan).equals("RICHMOND"))
        {
        	WorkingStorage.StoreCharVal(Defines.SaveStickerVal, WorkingStorage.GetCharVal(Defines.SaveDeptVal, dan),dan);
        }
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStickerVal, dan);
        while (WriteBuffer.length() < 65)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveMakeVal, dan);
        while (WriteBuffer.length() < 67)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStateVal, dan);
        while (WriteBuffer.length() < 69)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveColorVal, dan);
        while (WriteBuffer.length() < 70)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveColorTwoVal, dan);
        while (WriteBuffer.length() < 71)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveBodyVal, dan);
        while (WriteBuffer.length() < 72)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveViolateVal, dan);
        while (WriteBuffer.length() < 81)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkVal, dan);
        while (WriteBuffer.length() < 85)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveNumberVal, dan);
        while (WriteBuffer.length() < 91)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveDirectionVal, dan);
        while (WriteBuffer.length() < 92)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStreetVal, dan);
        while (WriteBuffer.length() < 100)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintMeterVal, dan);
        while (WriteBuffer.length() < 108)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveCourtVal, dan);
        while (WriteBuffer.length() < 114)
        	WriteBuffer += " ";  
                        
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/TICKET.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("TICK File Creation Exception Occured: " + e, dan);
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	SaveTickFile(dan); // Just Loop until it can create it... This will cause the phone to lock up and not print the citation versus having an exact copy of the citation get voided by error - Will cause Lock lockup at the last prompt 
        }
        // Create a copy of the last 10 citations in a ticktemp.r file
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/TICKTEMP.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("TICKTEMP File Creation Exception Occured: " + e, dan);     
        }    
        // Create a copy of the last 17 citations in a tickseve.r file
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/TICKSEVE.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("TICKSEVE File Creation Exception Occured: " + e, dan);     
        }
        
        WorkingStorage.StoreCharVal(Defines.ReprintCitationVal, CitationNumber, dan);
        SaveReprintFile(dan);
        SaveLocaTFile(CitationNumber, dan); // LGStreet Location file
        //Messagebox.Message("Need to write the save reprint routine", dan);
        SaveAddiFile(CitationNumber, "CO1", WorkingStorage.GetCharVal(Defines.PrintComment1Val, dan), dan);
        SaveAddiFile(CitationNumber, "CO2", WorkingStorage.GetCharVal(Defines.PrintComment2Val, dan), dan);
        SaveAddiFile(CitationNumber, "CO3", WorkingStorage.GetCharVal(Defines.PrintComment3Val, dan), dan);
        SaveAddiFile(CitationNumber, "PC1", WorkingStorage.GetCharVal(Defines.PrivateComment1Val, dan), dan);
        SaveAddiFile(CitationNumber, "PC2", WorkingStorage.GetCharVal(Defines.PrivateComment2Val, dan), dan);
        SaveAddiFile(CitationNumber, "PC3", WorkingStorage.GetCharVal(Defines.PrivateComment3Val, dan), dan);
        SaveAddiFile(CitationNumber, "SIG", WorkingStorage.GetCharVal(Defines.SignalStrengthVal, dan), dan);
        SaveAddiFile(CitationNumber, "STM", WorkingStorage.GetCharVal(Defines.SaveStemVal, dan), dan);
        SaveAddiFile(CitationNumber, "TR1", WorkingStorage.GetCharVal(Defines.PrintTimeRang1Val, dan), dan);
        SaveAddiFile(CitationNumber, "TR2", WorkingStorage.GetCharVal(Defines.PrintTimeRang2Val, dan), dan);
        SaveAddiFile(CitationNumber, "TR3", WorkingStorage.GetCharVal(Defines.PrintTimeRang3Val, dan), dan);        
        SaveAddiFile(CitationNumber, "VIN", WorkingStorage.GetCharVal(Defines.SaveVinVal, dan), dan);
        SaveAddiFile(CitationNumber, "VER", WorkingStorage.GetCharVal(Defines.SaveVMultiErrorVal, dan), dan);
        SaveAddiFile(CitationNumber, "DEC", WorkingStorage.GetCharVal(Defines.PrintDecalVal, dan), dan);
        SaveAddiFile(CitationNumber, "VIN", WorkingStorage.GetCharVal(Defines.SaveNYVinVal, dan), dan);
        SaveAddiFile(CitationNumber, "SCN", WorkingStorage.GetCharVal(Defines.SaveScanRegVal, dan), dan);
        if (WorkingStorage.GetCharVal(Defines.OnBootListVal,dan).equals("YES"))
        {
        	SaveAddiFile(CitationNumber, "HIT", WorkingStorage.GetCharVal(Defines.SavePlateVal, dan), dan);
        }
        if (WorkingStorage.GetCharVal(Defines.SaveMakeVal,dan).equals("XX"))
        {
        	SaveAddiFile(CitationNumber, "MAK", WorkingStorage.GetCharVal(Defines.PrintMakeVal, dan), dan);
        }
        if (WorkingStorage.GetCharVal(Defines.SaveDirectionVal,dan).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "DIR", WorkingStorage.GetCharVal(Defines.PrintDirectionVal, dan), dan);
        }  
        if (WorkingStorage.GetCharVal(Defines.SaveColorVal,dan).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "CLR", WorkingStorage.GetCharVal(Defines.PrintColorVal, dan), dan);
        }         
        if (WorkingStorage.GetCharVal(Defines.SaveColorTwoVal,dan).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "CL2", WorkingStorage.GetCharVal(Defines.PrintColorTwoVal, dan), dan);
        }         
        if (WorkingStorage.GetCharVal(Defines.SaveStreetVal,dan).equals("XXX"))
        {
        	SaveAddiFile(CitationNumber, "STR", WorkingStorage.GetCharVal(Defines.PrintStreetVal, dan), dan);
        }  
        if (WorkingStorage.GetCharVal(Defines.SaveBodyVal,dan).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "BOD", WorkingStorage.GetCharVal(Defines.PrintBodyVal, dan), dan);
        } 
        SaveAddiFile(CitationNumber, "BEA", WorkingStorage.GetCharVal(Defines.PrintBeatVal, dan), dan);
        SaveAddiFile(CitationNumber, "XST", WorkingStorage.GetCharVal(Defines.PrintCrossStreetVal, dan), dan);
        SaveAddiFile(CitationNumber, "ST3", WorkingStorage.GetCharVal(Defines.PrintThirdStreetVal, dan), dan);
        SaveAddiFile(CitationNumber, "OVR", WorkingStorage.GetCharVal(Defines.PrintOvertimeVal, dan), dan);
        if (WorkingStorage.GetCharVal(Defines.SaveTypeVal,dan).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "TYP", WorkingStorage.GetCharVal(Defines.SaveTypeVal, dan), dan);
        }     
        SaveAddiFile(CitationNumber, "TPL", WorkingStorage.GetCharVal(Defines.SaveTempPlateVal, dan), dan);
        SaveAddiFile(CitationNumber, "TEX", WorkingStorage.GetCharVal(Defines.PrintTempExpireVal, dan), dan);
        SaveAddiFile(CitationNumber, "AC1", WorkingStorage.GetCharVal(Defines.PrintAdditionalComment1Val, dan), dan);
        SaveAddiFile(CitationNumber, "AC2", WorkingStorage.GetCharVal(Defines.PrintAdditionalComment2Val, dan), dan);
        SaveAddiFile(CitationNumber, "AC3", WorkingStorage.GetCharVal(Defines.PrintAdditionalComment3Val, dan), dan);
        SaveAddiFile(CitationNumber, "DTY", WorkingStorage.GetCharVal(Defines.PrintTypeXrefVal, dan), dan);
        SaveAddiFile(CitationNumber, "VIL", WorkingStorage.GetCharVal(Defines.PrintLastFourVal, dan), dan);
        SaveAddiFile(CitationNumber, "BND", WorkingStorage.GetCharVal(Defines.PrintBoundVal, dan), dan);
        SaveAddiFile(CitationNumber, "SID", WorkingStorage.GetCharVal(Defines.PrintSideVal, dan), dan);
        SaveAddiFile(CitationNumber, "PER", WorkingStorage.GetCharVal(Defines.PrintPermitVal, dan), dan);
        SaveAddiFile(CitationNumber, "MTR", WorkingStorage.GetCharVal(Defines.SaveDallasMeterVal, dan), dan);
        SaveAddiFile(CitationNumber, "LCL", WorkingStorage.GetCharVal(Defines.PrintLicColorVal, dan), dan);
        SaveAddiFile(CitationNumber, "STY", WorkingStorage.GetCharVal(Defines.PrintStreetTypeVal, dan), dan);
        SaveAddiFile(CitationNumber, "EXT", WorkingStorage.GetCharVal(Defines.PrintExtdTypeVal, dan), dan);
        SaveAddiFile(CitationNumber, "MOD", WorkingStorage.GetCharVal(Defines.PrintModelVal, dan), dan);
        SaveAddiFile(CitationNumber, "DUE", WorkingStorage.GetCharVal(Defines.PrintCurrentDueVal, dan), dan);
        if (!WorkingStorage.GetCharVal(Defines.NoFeeFlag,dan).equals("SKIP"))
        {
        	SaveAddiFile(CitationNumber, "FE2", WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan), dan);
        }  
        SaveAddiFile(CitationNumber, "DRL", WorkingStorage.GetCharVal(Defines.SaveDriversLicVal, dan), dan);
        SaveAddiFile(CitationNumber, "FNM", WorkingStorage.GetCharVal(Defines.SaveFirstNameVal, dan), dan);
        SaveAddiFile(CitationNumber, "LNM", WorkingStorage.GetCharVal(Defines.SaveLastNameVal, dan), dan);
        SaveAddiFile(CitationNumber, "AD1", WorkingStorage.GetCharVal(Defines.SaveAddress1Val, dan), dan);
        SaveAddiFile(CitationNumber, "AD2", WorkingStorage.GetCharVal(Defines.SaveAddress2Val, dan), dan);
        SaveAddiFile(CitationNumber, "ADC", WorkingStorage.GetCharVal(Defines.SaveAddrCityVal, dan), dan);
        SaveAddiFile(CitationNumber, "ADS", WorkingStorage.GetCharVal(Defines.SaveAddrStateVal, dan), dan);
        SaveAddiFile(CitationNumber, "ADZ", WorkingStorage.GetCharVal(Defines.SaveAddrZipVal, dan), dan);
        SaveAddiFile(CitationNumber, "LAT", WorkingStorage.GetCharVal(Defines.LatitudeVal, dan), dan);
        if (!WorkingStorage.GetCharVal(Defines.LatitudeVal, dan).equals(""))
        {
        	WorkingStorage.StoreCharVal(Defines.LastLatitudeVal, WorkingStorage.GetCharVal(Defines.LatitudeVal, dan),  dan); //copy the last latitude for the GPS survey stuff
        }
        SaveAddiFile(CitationNumber, "LNG", WorkingStorage.GetCharVal(Defines.LongitudeVal, dan), dan);
        if (!WorkingStorage.GetCharVal(Defines.LongitudeVal, dan).equals(""))
        {
        	WorkingStorage.StoreCharVal(Defines.LastLongitudeVal, WorkingStorage.GetCharVal(Defines.LongitudeVal, dan),  dan); //copy the last LongitudeVal for the GPS survey stuff
        }
        
        SaveAddiFile(CitationNumber, "OVB", WorkingStorage.GetCharVal(Defines.OverridePerformedVal, dan), dan);
        SaveAddiFile(CitationNumber, "PHN", WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan) + WorkingStorage.GetCharVal(Defines.PhoneNumberVal, dan), dan);
        WriteNextCitationNumber(dan);
        WorkingStorage.StoreLongVal(Defines.TicketVoidFlag, 0, dan);
		String tmpPlate = WorkingStorage.GetCharVal(Defines.PrintPlateVal,dan);
		SystemIOFile.Delete(tmpPlate+".T");
		return true;
	}

	public static void WriteNextCitationNumber(Context dan)
	{
        String TempCitation = "";
        String ClancyRecord = "";
        NextCiteNum.GetNextCitationNumber(1, dan);
        TempCitation = WorkingStorage.GetCharVal(Defines.NextCitationVal, dan);
        ClancyRecord = SearchData.GetRecordNumberNoLength("CLANCY.J", 1, dan);
        ClancyRecord = ClancyRecord.substring(0, 113) + TempCitation + ClancyRecord.substring(121, 246);
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/CLANCY.J", false)); //true will cause the file to be created if not there or just append if it is
             out.write(ClancyRecord);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("CLANCY File Creation Exception Occured: " + e, dan);     
        }
        
	}

	public static void SaveVMultiPlate(String returnString, Context dan)
	{
		if (returnString.trim().equals(""))
		{
			return ; //Simply return since we don't want to write a blank record			
		}
		if (returnString.length()<7) // we got a bad read, do not write the file
		{
			return ;
		}
		if (!returnString.substring(6,7).equals("|")) // we got a bad read, do not write the file
		{
			
		}
		StringTokenizer tokens = new StringTokenizer(returnString, "|");
		String tmpPlate = WorkingStorage.GetCharVal(Defines.PrintPlateVal,dan);
        while (tokens.hasMoreTokens() == true)
        {
        	String WriteBuffer = tokens.nextToken();
            try 
            {
                 BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/"+tmpPlate+".T", true)); //true will cause the file to be created if not there or just append if it is
                 out.write(WriteBuffer);
                 out.newLine();
                 out.flush();
                 out.close();
            } 
            catch (IOException e) 
            {
            	Messagebox.Message("VMultiPlate File Creation Exception Occured: " + e, dan);     
            }
        }
        /*			String ParseString1 = "";
		String ParseString2 = "";
		if (tokens.hasMoreTokens() == true)
		{
			ParseString1 = tokens.nextToken();
		}
		if (tokens.hasMoreTokens() == true)
		{
			ParseString2 = tokens.nextToken();
		}	
		return;
	String WriteBuffer = cCitation + cId + cDetail;
        while (WriteBuffer.length() < 31)
        	WriteBuffer += " ";  
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/ADDI.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("ADDI File Creation Exception Occured: " + e, dan);     
        }*/
		
	}

	
	public static void SaveAddiFile(String cCitation, String cId, String cDetail, Context dan)
	{
		if (cDetail.trim().equals(""))
		{
			return ; //Simply return since we don't want to write a blank record			
		}
		String WriteBuffer = cCitation + cId + cDetail;
        while (WriteBuffer.length() < 31)
        	WriteBuffer += " ";  
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/ADDI.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("ADDI File Creation Exception Occured: " + e, dan);     
        }
		
	}
	public static void SaveLocaTFile(String cCitation,Context dan)
	{	           
		if (WorkingStorage.GetCharVal(Defines.PrintLGStreet1Val, dan).trim().equals(""))
		{
			return; //don't write empty record
		}
		
		String WriteBuffer = cCitation;
        while (WriteBuffer.length() < 8)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintLGStreet1Val, dan);
        while (WriteBuffer.length() < 38)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintLGStreet2Val, dan);
        while (WriteBuffer.length() < 68)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintLGStreet3Val, dan);
        while (WriteBuffer.length() < 98)
        	WriteBuffer += " ";        
           
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/LOCA.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("File Creation Exception Occured: " + e, dan);     
        }
	}	

	public static void SaveVoidTFile(Context dan)
	{	           
		String WriteBuffer = "     VOID     ";
	    //GetNextCitationNumber(0)    
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintCitationVal, dan);
        while (WriteBuffer.length() < 27)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintDateVal, dan);
        while (WriteBuffer.length() < 40)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintTimeVal, dan);
        while (WriteBuffer.length() < 50)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, dan);
        while (WriteBuffer.length() < 63)
        	WriteBuffer += " ";        
           
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/VOID.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("File Creation Exception Occured: " + e, dan);     
        }
	}
	public static void SaveChecFile(Context dan)
	{	           
		if ( WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("EASTLANSING")
			|| WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("MANITOWOC")) // DO NOT REFRESH THE DATE AND TIME FOR EAST LANSING - THEY MANUALLY SET THE DATE AND TIME
		{					
		}
		else
		{
			GetDate.GetDateTime(dan);
		}
		String WriteBuffer = "";
		WriteBuffer = WorkingStorage.GetCharVal(Defines.SaveDateVal, dan);
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.CheckTimeVal, dan);
        while (WriteBuffer.length() < 12)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintPlateVal, dan);
        while (WriteBuffer.length() < 20)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStateVal, dan);
        while (WriteBuffer.length() < 22)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, dan);
        while (WriteBuffer.length() < 27)
        	WriteBuffer += " ";        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, dan);
        while (WriteBuffer.length() < 39)
        	WriteBuffer += " ";        

        
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/CHEC.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("CHEC File Creation Exception Occured: " + e, dan);     
        }
	}
	public static void SaveHittFile(Context dan)
	{	           
		if ( WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("EASTLANSING")
				|| WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("MANITOWOC")) // DO NOT REFRESH THE DATE AND TIME FOR EAST LANSING - THEY MANUALLY SET THE DATE AND TIME
			{					
			}
			else
			{
				GetDate.GetDateTime(dan);
			}
		String WriteBuffer = "";
		WriteBuffer = WorkingStorage.GetCharVal(Defines.HittDateVal, dan);
        while (WriteBuffer.length() < 11)
        	WriteBuffer += " ";
		WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.HittTimeVal, dan);
        while (WriteBuffer.length() < 19)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, dan);
        while (WriteBuffer.length() < 36)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, dan);
        while (WriteBuffer.length() < 41)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStateVal, dan);
        while (WriteBuffer.length() < 46)
        	WriteBuffer += " ";        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintPlateVal, dan);
        while (WriteBuffer.length() < 67)
        	WriteBuffer += " ";        

        
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/HITT.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("HITT File Creation Exception Occured: " + e, dan);     
        }
	}

	public static void SaveWiFiFile(Context dan, String message)
	{	           
		if ( WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("EASTLANSING")
				|| WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("MANITOWOC")) // DO NOT REFRESH THE DATE AND TIME FOR EAST LANSING - THEY MANUALLY SET THE DATE AND TIME
			{					
			}
			else
			{
				GetDate.GetDateTime(dan);
			}
		String WriteBuffer = "";
		WriteBuffer = WorkingStorage.GetCharVal(Defines.SaveDateVal, dan);
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.CheckTimeVal, dan);
        while (WriteBuffer.length() < 12)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, dan);
        while (WriteBuffer.length() < 16)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + message;
        while (WriteBuffer.length() < 50)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, dan);
        while (WriteBuffer.length() < 65)
        	WriteBuffer += " ";        
        
        try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/WIFI.R", true)); //true will cause the file to be created if not there or just append if it is
             out.write(WriteBuffer);
             out.newLine();
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("WIFI File Creation Exception Occured: " + e, dan);     
        }
	}
	
	
	public static void SaveReprintFile(Context dan)
	{
        if (SystemIOFile.exists("Reprint.txt"))
        {
        	SystemIOFile.Delete("Reprint.txt");
        }
        
		try 
        {
             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/Reprint.txt", true)); //true will cause the file to be created if not there or just append if it is
             int rRecord = 0;
             for (rRecord = 0; rRecord <550; rRecord++)
             {
            	 out.write(WorkingStorage.GetCharVal(Integer.toString(rRecord), dan));
                 out.newLine();
             }
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("Reprint File Creation Exception Occured: " + e, dan);     
        }

	}
	
	public static void ReadReprintFile(Context dan)
	{
        if (SystemIOFile.exists("Reprint.txt"))
        {
		  	File file = new File("/data/data/com.clancy.aticket/files/","Reprint.txt");  
		  	if(file.exists())   // check if file exist  
		  	{   		   
		  		try {  
			         BufferedReader br = new BufferedReader(new FileReader(file));  
			         String line;
			         int rRecord = 0;
			         while ((line = br.readLine()) != null ) 
			         {
			        	 WorkingStorage.StoreCharVal(Integer.toString(rRecord),line, dan);
			        	 rRecord++;
			         }  
			         br.close();
			         file = null;			         
			         
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
        WorkingStorage.StoreCharVal(Defines.MenuProgramVal, Defines.ReprintMenu, dan);
	}	

}
