package com.clancy.aticket;

import android.content.Context;

public class Vboot {
	public static int GetVBootList( Context cntxt)
	{
		int FoundFlag = 0;
		String FullStatePlate = "";
		FullStatePlate += WorkingStorage.GetCharVal(Defines.SaveStateVal, cntxt);
		FullStatePlate += WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt);
        while (FullStatePlate.length() < 8)
        	FullStatePlate += " ";
        
        String BootString = "";
        //BootString = SearchData.SearchVBootT(FullStatePlate, FullStatePlate.length(), "VBOOT.T");
        //We'll try the Find Record Line in Android to see how fast it works on large files.
        //If it is fast then I'll retire the SearchVBoot function.
        //BootString = SearchData.FindRecordLine(FullStatePlate, FullStatePlate.length(), "VBOOT.T", cntxt);
        BootString = SearchData.SearchVBootT(FullStatePlate, FullStatePlate.length(), "VBOOT.T", cntxt);
        if (!BootString.trim().equals("NIF"))
		{
			if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("ELLENSBURG")) {
				// This Specific Client has 2 Citation Counts per Plate
				// Return BOTH Characters
				FoundFlag = Integer.valueOf(BootString.substring(10, 12));
			} else {
				FoundFlag = Integer.valueOf(BootString.substring(11, 12));
			}
		}

		return FoundFlag;
	}

}
