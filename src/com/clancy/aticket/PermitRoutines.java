package com.clancy.aticket;

import android.content.Context;

public class PermitRoutines {
	
	
    public static int GetPermitList(String ReasonPassed, Context dan)
    {
        String DescripBuffer = "";
        int returnVal = 0;
        DescripBuffer = SearchData.FindRecordLine(ReasonPassed, ReasonPassed.length(), "PERMITS.T", dan);
        
        if (DescripBuffer.equals("NIF"))
        {
        	WorkingStorage.StoreCharVal(Defines.PermitMessageVal, "UNKNOWN REASON", dan);
        }
        else
        {
        	WorkingStorage.StoreCharVal(Defines.PermitMessageVal, DescripBuffer.substring(104, 129), dan);
        	returnVal = 1;
        	/* PERMITS.T STRUCTURE - TO DO ADD IN LATER
        	 * Permit c(20), Loc1 c(15), Loc2 c(15), Loc3 c(15), Plate1 c(8), Plate2 c(8), Plate3 c(8), ;
			 * VFrom c(4), VTo c(4), VSun c(1), VMon c(1), VTue c(1), VWed c(1), VThu c(1), VFri c(1), VSat c(1), ;
			 * vmessage c(25)
        	 */
        }
        return returnVal;
        
    }	

}
