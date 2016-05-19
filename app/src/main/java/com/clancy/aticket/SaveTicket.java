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
	
	
	public static Boolean SaveTickFile(Context cntxt)
	{
        WorkingStorage.StoreCharVal(Defines.SavingTickFile, "YES", cntxt);

		// changed to a boolean return  on 11-11/2013 for honorbox voiding issue
		if (WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt).equals("NOPLATE"))
		{
            WorkingStorage.StoreCharVal(Defines.SaveStateVal, "  ", cntxt);
            WorkingStorage.StoreCharVal(Defines.PrintStateVal, "", cntxt);

		}

        String WriteBuffer = "";
        String CitationNumber = "";
        NextCiteNum.GetNextCitationNumber(0, cntxt);
        
        if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("RICHMOND")
        	&& WorkingStorage.GetCharVal(Defines.FootBallFlagVal,cntxt).equals("1"))
        {
        	WorkingStorage.StoreCharVal(Defines.PrintPermitVal, WorkingStorage.GetCharVal(Defines.PrintPlateVal, cntxt),cntxt);
        	String TempString = WorkingStorage.GetCharVal(Defines.PrintDateVal,cntxt);
        	TempString = "TOW-" + TempString.substring(0, 2) + TempString.substring(8,10);
        	WorkingStorage.StoreCharVal(Defines.SavePlateVal, TempString,cntxt);
        }
        
        CitationNumber = WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt);
		WriteBuffer = CitationNumber;
        while (WriteBuffer.length() < 8)
        	WriteBuffer += " ";
	    //WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintBeatVal, cntxt);
        while (WriteBuffer.length() < 12)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, cntxt);
        while (WriteBuffer.length() < 24)
        	WriteBuffer += " ";
        WriteBuffer = WriteBuffer.substring(0, 23); //do this to trim officer length of greater than 12        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, cntxt);
        while (WriteBuffer.length() < 29)
        	WriteBuffer += " "; 
        //WriteBuffer = WriteBuffer.substring(0, 29); //added this on 11/20/2012 to compensate for Richmond badge overrunning the length
        WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveDateVal, cntxt);
        while (WriteBuffer.length() < 35)
        	WriteBuffer += " ";        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveTimeStartVal, cntxt);
        while (WriteBuffer.length() < 39)
        	WriteBuffer += " ";        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt);
        while (WriteBuffer.length() < 56)
        	WriteBuffer += " ";    
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveTypeVal, cntxt);
        while (WriteBuffer.length() < 57)
        	WriteBuffer += " ";    
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveYearVal, cntxt);
        while (WriteBuffer.length() < 59)
        	WriteBuffer += " ";    
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveMonthVal, cntxt);
        while (WriteBuffer.length() < 61)
        	WriteBuffer += " ";  
        if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("RICHMOND"))
        {
        	WorkingStorage.StoreCharVal(Defines.SaveStickerVal, WorkingStorage.GetCharVal(Defines.SaveDeptVal, cntxt),cntxt);
        }
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStickerVal, cntxt);
        while (WriteBuffer.length() < 65)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveMakeVal, cntxt);
        while (WriteBuffer.length() < 67)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStateVal, cntxt);
        while (WriteBuffer.length() < 69)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveColorVal, cntxt);
        while (WriteBuffer.length() < 70)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveColorTwoVal, cntxt);
        while (WriteBuffer.length() < 71)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveBodyVal, cntxt);
        while (WriteBuffer.length() < 72)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt);
        while (WriteBuffer.length() < 81)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveChalkVal, cntxt);
        while (WriteBuffer.length() < 85)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveNumberVal, cntxt);
        while (WriteBuffer.length() < 91)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveDirectionVal, cntxt);
        while (WriteBuffer.length() < 92)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStreetVal, cntxt);
        while (WriteBuffer.length() < 100)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintMeterVal, cntxt);
        while (WriteBuffer.length() < 108)
        	WriteBuffer += " ";  
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveCourtVal, cntxt);
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
        	Messagebox.Message("TICK File Creation Exception Occured: " + e, cntxt);
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	SaveTickFile(cntxt); // Just Loop until it can create it... This will cause the phone to lock up and not print the citation versus having an exact copy of the citation get voided by error - Will cause Lock lockup at the last prompt
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
        	Messagebox.Message("TICKTEMP File Creation Exception Occured: " + e, cntxt);
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
        	Messagebox.Message("TICKSEVE File Creation Exception Occured: " + e, cntxt);
        }
        
        WorkingStorage.StoreCharVal(Defines.ReprintCitationVal, CitationNumber, cntxt);
        SaveReprintFile(cntxt);
        SaveLocaTFile(CitationNumber, cntxt); // LGStreet Location file
        //Messagebox.Message("Need to write the save reprint routine", cntxt);
        SaveAddiFile(CitationNumber, "CO1", WorkingStorage.GetCharVal(Defines.PrintComment1Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "CO2", WorkingStorage.GetCharVal(Defines.PrintComment2Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "CO3", WorkingStorage.GetCharVal(Defines.PrintComment3Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "PC1", WorkingStorage.GetCharVal(Defines.PrivateComment1Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "PC2", WorkingStorage.GetCharVal(Defines.PrivateComment2Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "PC3", WorkingStorage.GetCharVal(Defines.PrivateComment3Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "SIG", WorkingStorage.GetCharVal(Defines.SignalStrengthVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "STM", WorkingStorage.GetCharVal(Defines.SaveStemVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "TR1", WorkingStorage.GetCharVal(Defines.PrintTimeRang1Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "TR2", WorkingStorage.GetCharVal(Defines.PrintTimeRang2Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "TR3", WorkingStorage.GetCharVal(Defines.PrintTimeRang3Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "VIN", WorkingStorage.GetCharVal(Defines.SaveVinVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "VER", WorkingStorage.GetCharVal(Defines.SaveVMultiErrorVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "DEC", WorkingStorage.GetCharVal(Defines.PrintDecalVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "VIN", WorkingStorage.GetCharVal(Defines.SaveNYVinVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "SCN", WorkingStorage.GetCharVal(Defines.SaveScanRegVal, cntxt), cntxt);
        if (WorkingStorage.GetCharVal(Defines.OnBootListVal,cntxt).equals("YES"))
        {
        	SaveAddiFile(CitationNumber, "HIT", WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt), cntxt);
        }
        if (WorkingStorage.GetCharVal(Defines.SaveMakeVal,cntxt).equals("XX"))
        {
        	SaveAddiFile(CitationNumber, "MAK", WorkingStorage.GetCharVal(Defines.PrintMakeVal, cntxt), cntxt);
        }
        if (WorkingStorage.GetCharVal(Defines.SaveDirectionVal,cntxt).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "DIR", WorkingStorage.GetCharVal(Defines.PrintDirectionVal, cntxt), cntxt);
        }  
        if (WorkingStorage.GetCharVal(Defines.SaveColorVal,cntxt).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "CLR", WorkingStorage.GetCharVal(Defines.PrintColorVal, cntxt), cntxt);
        }         
        if (WorkingStorage.GetCharVal(Defines.SaveColorTwoVal,cntxt).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "CL2", WorkingStorage.GetCharVal(Defines.PrintColorTwoVal, cntxt), cntxt);
        }         
        if (WorkingStorage.GetCharVal(Defines.SaveStreetVal,cntxt).equals("XXX"))
        {
        	SaveAddiFile(CitationNumber, "STR", WorkingStorage.GetCharVal(Defines.PrintStreetVal, cntxt), cntxt);
        }  
        if (WorkingStorage.GetCharVal(Defines.SaveBodyVal,cntxt).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "BOD", WorkingStorage.GetCharVal(Defines.PrintBodyVal, cntxt), cntxt);
        } 
        SaveAddiFile(CitationNumber, "BEA", WorkingStorage.GetCharVal(Defines.PrintBeatVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "XST", WorkingStorage.GetCharVal(Defines.PrintCrossStreetVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "ST3", WorkingStorage.GetCharVal(Defines.PrintThirdStreetVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "OVR", WorkingStorage.GetCharVal(Defines.PrintOvertimeVal, cntxt), cntxt);
        if (WorkingStorage.GetCharVal(Defines.SaveTypeVal,cntxt).equals("X"))
        {
        	SaveAddiFile(CitationNumber, "TYP", WorkingStorage.GetCharVal(Defines.SaveTypeVal, cntxt), cntxt);
        }     
        SaveAddiFile(CitationNumber, "TPL", WorkingStorage.GetCharVal(Defines.SaveTempPlateVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "TEX", WorkingStorage.GetCharVal(Defines.PrintTempExpireVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "AC1", WorkingStorage.GetCharVal(Defines.PrintAdditionalComment1Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "AC2", WorkingStorage.GetCharVal(Defines.PrintAdditionalComment2Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "AC3", WorkingStorage.GetCharVal(Defines.PrintAdditionalComment3Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "DTY", WorkingStorage.GetCharVal(Defines.PrintTypeXrefVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "VIL", WorkingStorage.GetCharVal(Defines.PrintLastFourVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "BND", WorkingStorage.GetCharVal(Defines.PrintBoundVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "SID", WorkingStorage.GetCharVal(Defines.PrintSideVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "PER", WorkingStorage.GetCharVal(Defines.PrintPermitVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "MTR", WorkingStorage.GetCharVal(Defines.SaveDallasMeterVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "LCL", WorkingStorage.GetCharVal(Defines.PrintLicColorVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "STY", WorkingStorage.GetCharVal(Defines.PrintStreetTypeVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "EXT", WorkingStorage.GetCharVal(Defines.PrintExtdTypeVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "MOD", WorkingStorage.GetCharVal(Defines.PrintModelVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "DUE", WorkingStorage.GetCharVal(Defines.PrintCurrentDueVal, cntxt), cntxt);
        if (!WorkingStorage.GetCharVal(Defines.NoFeeFlag,cntxt).equals("SKIP"))
        {
        	SaveAddiFile(CitationNumber, "FE2", WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt), cntxt);
        }  
        SaveAddiFile(CitationNumber, "DRL", WorkingStorage.GetCharVal(Defines.SaveDriversLicVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "FNM", WorkingStorage.GetCharVal(Defines.SaveFirstNameVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "LNM", WorkingStorage.GetCharVal(Defines.SaveLastNameVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "AD1", WorkingStorage.GetCharVal(Defines.SaveAddress1Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "AD2", WorkingStorage.GetCharVal(Defines.SaveAddress2Val, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "ADC", WorkingStorage.GetCharVal(Defines.SaveAddrCityVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "ADS", WorkingStorage.GetCharVal(Defines.SaveAddrStateVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "ADZ", WorkingStorage.GetCharVal(Defines.SaveAddrZipVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "LAT", WorkingStorage.GetCharVal(Defines.LatitudeVal, cntxt), cntxt);
        if (!WorkingStorage.GetCharVal(Defines.LatitudeVal, cntxt).equals(""))
        {
        	WorkingStorage.StoreCharVal(Defines.LastLatitudeVal, WorkingStorage.GetCharVal(Defines.LatitudeVal, cntxt),  cntxt); //copy the last latitude for the GPS survey stuff
        }
        SaveAddiFile(CitationNumber, "LNG", WorkingStorage.GetCharVal(Defines.LongitudeVal, cntxt), cntxt);
        if (!WorkingStorage.GetCharVal(Defines.LongitudeVal, cntxt).equals(""))
        {
        	WorkingStorage.StoreCharVal(Defines.LastLongitudeVal, WorkingStorage.GetCharVal(Defines.LongitudeVal, cntxt),  cntxt); //copy the last LongitudeVal for the GPS survey stuff
        }
        
        SaveAddiFile(CitationNumber, "OVB", WorkingStorage.GetCharVal(Defines.OverridePerformedVal, cntxt), cntxt);
        SaveAddiFile(CitationNumber, "PHN", WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt) + WorkingStorage.GetCharVal(Defines.PhoneNumberVal, cntxt), cntxt);
        WriteNextCitationNumber(cntxt);

        WorkingStorage.StoreLongVal(Defines.TicketVoidFlag, 0, cntxt);
		String tmpPlate = WorkingStorage.GetCharVal(Defines.PrintPlateVal,cntxt);
		SystemIOFile.Delete(tmpPlate+".T");

        WorkingStorage.StoreCharVal(Defines.SavingTickFile, "", cntxt);
		return true;
	}

	public static void WriteNextCitationNumber(Context cntxt)
	{
        String TempCitation = "";
        String ClancyRecord = "";
        NextCiteNum.GetNextCitationNumber(1, cntxt);
        TempCitation = WorkingStorage.GetCharVal(Defines.NextCitationVal, cntxt);
        ClancyRecord = SearchData.GetRecordNumberNoLength("CLANCY.J", 1, cntxt);
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
        	Messagebox.Message("CLANCY File Creation Exception Occured: " + e, cntxt);
        }
	}

	public static void SaveVMultiPlate(String returnString, Context cntxt)
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
		String tmpPlate = WorkingStorage.GetCharVal(Defines.PrintPlateVal,cntxt);
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
            	Messagebox.Message("VMultiPlate File Creation Exception Occured: " + e, cntxt);
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
        	Messagebox.Message("ADDI File Creation Exception Occured: " + e, cntxt);
        }*/
		
	}

	
	public static void SaveAddiFile(String cCitation, String cId, String cDetail, Context cntxt)
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
        	Messagebox.Message("ADDI File Creation Exception Occured: " + e, cntxt);
        }
	}

	public static void SaveLocaTFile(String cCitation,Context cntxt)
	{	           
		if (WorkingStorage.GetCharVal(Defines.PrintLGStreet1Val, cntxt).trim().equals(""))
		{
			return; //don't write empty record
		}
		
		String WriteBuffer = cCitation;
        while (WriteBuffer.length() < 8)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintLGStreet1Val, cntxt);
        while (WriteBuffer.length() < 38)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintLGStreet2Val, cntxt);
        while (WriteBuffer.length() < 68)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintLGStreet3Val, cntxt);
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
        	Messagebox.Message("File Creation Exception Occured: " + e, cntxt);
        }
	}	

	public static void SaveVoidTFile(Context cntxt)
	{	           
		String WriteBuffer = "     VOID     ";
	    //GetNextCitationNumber(0)    
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt);
        while (WriteBuffer.length() < 27)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintDateVal, cntxt);
        while (WriteBuffer.length() < 40)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintTimeVal, cntxt);
        while (WriteBuffer.length() < 50)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, cntxt);
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
        	Messagebox.Message("File Creation Exception Occured: " + e, cntxt);
        }
	}

	public static void SaveChecFile(Context cntxt)
	{	           
		if ( WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("EASTLANSING")
			|| WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("MANITOWOC")) // DO NOT REFRESH THE DATE AND TIME FOR EAST LANSING - THEY MANUALLY SET THE DATE AND TIME
		{					
		}
		else
		{
			GetDate.GetDateTime(cntxt);
		}
		String WriteBuffer = "";
		WriteBuffer = WorkingStorage.GetCharVal(Defines.SaveDateVal, cntxt);
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.CheckTimeVal, cntxt);
        while (WriteBuffer.length() < 12)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintPlateVal, cntxt);
        while (WriteBuffer.length() < 20)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStateVal, cntxt);
        while (WriteBuffer.length() < 22)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, cntxt);
        while (WriteBuffer.length() < 27)
        	WriteBuffer += " ";        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, cntxt);
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
        	Messagebox.Message("CHEC File Creation Exception Occured: " + e, cntxt);
        }
	}

	public static void SaveHittFile(Context cntxt)
	{	           
		if ( WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("EASTLANSING")
				|| WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("MANITOWOC")) // DO NOT REFRESH THE DATE AND TIME FOR EAST LANSING - THEY MANUALLY SET THE DATE AND TIME
			{					
			}
			else
			{
				GetDate.GetDateTime(cntxt);
			}
		String WriteBuffer = "";
		WriteBuffer = WorkingStorage.GetCharVal(Defines.HittDateVal, cntxt);
        while (WriteBuffer.length() < 11)
        	WriteBuffer += " ";
		WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.HittTimeVal, cntxt);
        while (WriteBuffer.length() < 19)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, cntxt);
        while (WriteBuffer.length() < 36)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintBadgeVal, cntxt);
        while (WriteBuffer.length() < 41)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveStateVal, cntxt);
        while (WriteBuffer.length() < 46)
        	WriteBuffer += " ";        
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintPlateVal, cntxt);
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
        	Messagebox.Message("HITT File Creation Exception Occured: " + e, cntxt);
        }
	}

	public static void SaveWiFiFile(Context cntxt, String message)
	{	           
		if ( WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("EASTLANSING")
				|| WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("MANITOWOC")) // DO NOT REFRESH THE DATE AND TIME FOR EAST LANSING - THEY MANUALLY SET THE DATE AND TIME
			{					
			}
			else
			{
				GetDate.GetDateTime(cntxt);
			}
		String WriteBuffer = "";
		WriteBuffer = WorkingStorage.GetCharVal(Defines.SaveDateVal, cntxt);
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.CheckTimeVal, cntxt);
        while (WriteBuffer.length() < 12)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt);
        while (WriteBuffer.length() < 16)
        	WriteBuffer += " ";
	    WriteBuffer = WriteBuffer + message;
        while (WriteBuffer.length() < 50)
        	WriteBuffer += " "; 
	    WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.PrintOfficerVal, cntxt);
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
        	Messagebox.Message("WIFI File Creation Exception Occured: " + e, cntxt);
        }
	}

	public static void SaveReprintFile(Context cntxt)
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
            	 out.write(WorkingStorage.GetCharVal(Integer.toString(rRecord), cntxt));
                 out.newLine();
             }
             out.flush();
             out.close();
        } 
        catch (IOException e) 
        {
        	Messagebox.Message("Reprint File Creation Exception Occured: " + e, cntxt);
        }
	}
	
	public static void ReadReprintFile(Context cntxt)
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
			        	 WorkingStorage.StoreCharVal(Integer.toString(rRecord),line, cntxt);
			        	 rRecord++;
			         }  
			         br.close();
			         file = null;			         
			         
			     }catch (IOException e) 
			     {  
						Toast.makeText(cntxt, e.toString(), 2000);
			     }  
	         }
	         else  
			 {  
	        	 file = null;
			 }
        }
        WorkingStorage.StoreCharVal(Defines.MenuProgramVal, Defines.ReprintMenu, cntxt);
	}	

}
