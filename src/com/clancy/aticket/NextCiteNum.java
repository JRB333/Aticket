package com.clancy.aticket;

import android.content.Context;

public class NextCiteNum {
	
	public static Boolean GetNextCitationNumber(int AddHowMany, Context dan)	
	{
        String ClancyRecord  = "";
        String PrintCitation  = "";
        String PrintPrefix  = "";
        String WhichCheckDigit  = "";
        String ShowLastCitation  = "";
        String CitationValue  = "";
        
        ClancyRecord = SearchData.GetRecordNumberNoLength("CLANCY.J", 1, dan);
		
        PrintPrefix = ClancyRecord.substring(32, 34);
        PrintCitation = ClancyRecord.substring(113, 121);
        WhichCheckDigit = ClancyRecord.substring(43, 46);
        ShowLastCitation = ClancyRecord.substring(121, 129); 
        WorkingStorage.StoreCharVal(Defines.PrintPrefixVal, PrintPrefix, dan);

        if (Integer.valueOf(PrintCitation) >= Integer.valueOf(ShowLastCitation))
        {
        	return true;
        }
        
        if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("PHXAIRPORT"))
        {
        	WhichCheckDigit = "NONE";
        }        

        CitationValue = ValidateCheckDigit(PrintCitation, WhichCheckDigit, AddHowMany, dan);
        if (AddHowMany == 0)
        {
        	WorkingStorage.StoreCharVal(Defines.PrintCitationVal, CitationValue, dan);
        }
        else
        {
        	WorkingStorage.StoreCharVal(Defines.NextCitationVal, CitationValue, dan);
        }
        WorkingStorage.StoreCharVal(Defines.PrintLastCitationVal, ShowLastCitation, dan);
        
        return false;
	}
	public static String ValidateCheckDigit(String CitationPassed, String CDType, int AddHowMany, Context dan)
	{
        int NextCiteSeven  = 0;
        String NewSeven  = "";
        int NewDigit  = 0;
        String CheckDigit = "";
        String TempCitation  = "";
       
        	
        if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("PGCOUNTY"))
        {
        	NextCiteSeven = Integer.valueOf(CitationPassed.substring(0,7));
            NextCiteSeven = NextCiteSeven + AddHowMany;
            TempCitation = Integer.toString(NextCiteSeven);
        	
        	int TempNumber = 0;
    		int WeightNumber = 0;
    		String tmpCD = "";
   			TempNumber = Integer.valueOf(TempCitation.substring(0,1));
   			TempNumber = TempNumber * (1);
   			WeightNumber += TempNumber;
   			TempNumber = Integer.valueOf(TempCitation.substring(1,2));
   			TempNumber = TempNumber * (3);
   			WeightNumber += TempNumber;
   			TempNumber = Integer.valueOf(TempCitation.substring(2,3));
   			TempNumber = TempNumber * (7);
   			WeightNumber += TempNumber;
   			TempNumber = Integer.valueOf(TempCitation.substring(3,4));
   			TempNumber = TempNumber * (1);
   			WeightNumber += TempNumber;
   			TempNumber = Integer.valueOf(TempCitation.substring(4,5));
   			TempNumber = TempNumber * (3);
   			WeightNumber += TempNumber;
   			TempNumber = Integer.valueOf(TempCitation.substring(5,6));
   			TempNumber = TempNumber * (7);
   			WeightNumber += TempNumber;
   			TempNumber = Integer.valueOf(TempCitation.substring(6,7));
   			TempNumber = TempNumber * (1);
   			WeightNumber += TempNumber;   			
   			tmpCD = Integer.toString(WeightNumber);
   			tmpCD = tmpCD.substring(tmpCD.length()-1);
   			tmpCD = Integer.toString(10-Integer.valueOf(tmpCD));
   			if (tmpCD.equals("10"))
   			{
   				tmpCD = "0";
   			}
        	
   			TempCitation += tmpCD;
            
    		return TempCitation;
        }
        
        if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("HARRISBURG"))
        {
            NextCiteSeven = Integer.valueOf(CitationPassed);
            NextCiteSeven = NextCiteSeven + AddHowMany;
            TempCitation = Integer.toString(NextCiteSeven);
            while (TempCitation.length() < 8)
            	TempCitation = "0" + TempCitation;
            
    		return TempCitation;
        }        
        
        if (CDType.trim().equals("7"))
        {
        	NextCiteSeven = Integer.valueOf(CitationPassed.substring(0,7));
        	NextCiteSeven = NextCiteSeven + AddHowMany;
        	NewDigit = NextCiteSeven % 7;
        	
        	NewSeven = Integer.toString(NextCiteSeven);
            while (NewSeven.length() < 7)
            	NewSeven = "0" + NewSeven;
            NewSeven = NewSeven + Integer.toString(NewDigit);
            return NewSeven;
        }
        if (WorkingStorage.GetCharVal(Defines.ClientName,dan).equals("VAIL"))
        {
        	NextCiteSeven = Integer.valueOf(CitationPassed.substring(0,7));
        	NextCiteSeven = NextCiteSeven + AddHowMany;
        	NewDigit = NextCiteSeven % 11;
        	
        	if (NewDigit == 10)
        	{
        		CheckDigit = "X";
        	}
        	else if (NewDigit == 11)
        	{
        		CheckDigit = "0";
        	}
        	else
        	{
        		CheckDigit = Integer.toString(NewDigit);
        	}
        	NewSeven = Integer.toString(NextCiteSeven);
            while (NewSeven.length() < 7)
            	NewSeven = "0" + NewSeven;
            NewSeven = NewSeven + CheckDigit;
            return NewSeven;
        }
        if (CDType.trim().equals("110"))
        {
        	NextCiteSeven = Integer.valueOf(CitationPassed.substring(1,6));
        	NextCiteSeven = NextCiteSeven + AddHowMany;
        	NewDigit = NextCiteSeven % 11;
        	NewDigit = 11- NewDigit;
        	
        	if (NewDigit == 10)
        	{
        		CheckDigit = "X";
        	}
        	else if (NewDigit == 11)
        	{
        		CheckDigit = "0";
        	}
        	else
        	{
        		CheckDigit = Integer.toString(NewDigit);
        	}
        	
        	NewSeven = Integer.toString(NextCiteSeven);
            while (NewSeven.length() < 6)
            	NewSeven = "0" + NewSeven;
            
            TempCitation = CitationPassed.substring(0, 1);
            TempCitation = TempCitation + NewSeven + CheckDigit;
            return TempCitation;
        }  
        NextCiteSeven = Integer.valueOf(CitationPassed);
        NextCiteSeven = NextCiteSeven + AddHowMany;
        TempCitation = Integer.toString(NextCiteSeven);
        while (TempCitation.length() < 8)
        	TempCitation = "0" + TempCitation;
        
		return TempCitation;
	}
}
