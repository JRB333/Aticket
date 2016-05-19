package com.clancy.aticket;

import android.content.Context;

public class BootRoutines {
		public static int GetBootList(String VariableVal , int SearchComplete, int Occurance, Context cntxt)
		{
	        String FullStatePlate = "";
	        String TempChar = "";
	        String PlateInfo = "";
	        int FoundFlag  = 2;
	        String BootString  = "";
			
	        WorkingStorage.StoreCharVal(Defines.OnBootListVal, "NO",cntxt);
	        FullStatePlate = VariableVal;
	        if (SearchComplete == 1)
	        {
	            while (FullStatePlate.length() < 11)
	            	FullStatePlate += " ";
	        }
	        BootString = SearchData.FindValueInRecord(FullStatePlate, VariableVal.length(), "ALLBOOT.A", cntxt);
	        if (BootString.equals("NIF"))
	        {
	        	WorkingStorage.StoreCharVal(Defines.PrintBootListPlateVal, VariableVal,cntxt);
	        	if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, cntxt).trim().equals(Defines.PermitLookupMenu))
	        	{
	        		WorkingStorage.StoreCharVal(Defines.PrintBootListReasonVal, ">PERMIT CLEAR<",cntxt);
	        	}
	        	else
	        	{
	        		WorkingStorage.StoreCharVal(Defines.PrintBootListReasonVal, ">PLATE CLEAR<",cntxt);
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
	        	WorkingStorage.StoreCharVal(Defines.PrintBootListPlateVal, VariableVal,cntxt);
	        	WorkingStorage.StoreCharVal(Defines.OnBootListVal, "YES", cntxt);
	        	if (SearchComplete == 1)
	        	{
	        		SeekTheReason(PlateInfo, cntxt);
	        	}
	        	FoundFlag = 1;
	        }        	        
			return FoundFlag;
		}
		
	    public static void SeekTheReason(String ReasonPassed, Context cntxt)
	    {
	        String DescripBuffer = "";
	        DescripBuffer = SearchData.FindRecordLine(ReasonPassed, ReasonPassed.length(), "REASON.T", cntxt);
	        
	        if (DescripBuffer.equals("NIF"))
	        {
	        	WorkingStorage.StoreCharVal(Defines.PrintBootListReasonVal, "UNKNOWN REASON", cntxt);
	        }
	        else
	        {
	        	WorkingStorage.StoreCharVal(Defines.PrintBootListReasonVal, DescripBuffer.substring(1), cntxt);
	        }	                	                	    	
	    }

}
