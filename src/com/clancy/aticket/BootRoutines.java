package com.clancy.aticket;

import android.content.Context;

public class BootRoutines {
		public static int GetBootList(String VariableVal , int SearchComplete, int Occurance, Context dan)
		{
	        String FullStatePlate = "";
	        String TempChar = "";
	        String PlateInfo = "";
	        int FoundFlag  = 2;
	        String BootString  = "";
			
	        WorkingStorage.StoreCharVal(Defines.OnBootListVal, "NO",dan);
	        FullStatePlate = VariableVal;
	        if (SearchComplete == 1)
	        {
	            while (FullStatePlate.length() < 11)
	            	FullStatePlate += " ";
	        }
	        BootString = SearchData.FindValueInRecord(FullStatePlate, VariableVal.length(), "ALLBOOT.A", dan);
	        if (BootString.equals("NIF"))
	        {
	        	WorkingStorage.StoreCharVal(Defines.PrintBootListPlateVal, VariableVal,dan);
	        	if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, dan).trim().equals(Defines.PermitLookupMenu))
	        	{
	        		WorkingStorage.StoreCharVal(Defines.PrintBootListReasonVal, ">PERMIT CLEAR<",dan);
	        	}
	        	else
	        	{
	        		WorkingStorage.StoreCharVal(Defines.PrintBootListReasonVal, ">PLATE CLEAR<",dan);
	        	}
	        }
	        else
	        {
	        	if (Occurance > 1)
	        	{
	        		if (((Occurance - 1) * 13) > BootString.length())
	        		{
	        			return FoundFlag;
	        		}
	        		else
	        		{
	        			TempChar = BootString.substring((Occurance - 1) * 13, (Occurance - 1) * 13+13);
	        		}	        			
	        	}
	        	else
	        	{
	        		TempChar = BootString.substring(0, 13);
	        	}
	        	PlateInfo = TempChar.substring(12, 13);
	        	WorkingStorage.StoreCharVal(Defines.PrintBootListPlateVal, VariableVal,dan);
	        	WorkingStorage.StoreCharVal(Defines.OnBootListVal, "YES", dan);
	        	if (SearchComplete == 1)
	        	{
	        		SeekTheReason(PlateInfo, dan);
	        	}
	        	FoundFlag = 1;
	        }        	        
			return FoundFlag;
		}
		
	    public static void SeekTheReason(String ReasonPassed, Context dan)
	    {
	        String DescripBuffer = "";
	        DescripBuffer = SearchData.FindRecordLine(ReasonPassed, ReasonPassed.length(), "REASON.T", dan);
	        
	        if (DescripBuffer.equals("NIF"))
	        {
	        	WorkingStorage.StoreCharVal(Defines.PrintBootListReasonVal, "UNKNOWN REASON", dan);
	        }
	        else
	        {
	        	WorkingStorage.StoreCharVal(Defines.PrintBootListReasonVal, DescripBuffer.substring(1), dan);
	        }	                	                	    	
	    }

}
