package com.clancy.aticket;

import android.content.Context;

public class Vboot {
	public static int GetVBootList( Context dan)
	{
		int FoundFlag = 0;
		String FullStatePlate = "";
		FullStatePlate += WorkingStorage.GetCharVal(Defines.SaveStateVal, dan);
		FullStatePlate += WorkingStorage.GetCharVal(Defines.SavePlateVal, dan);
        while (FullStatePlate.length() < 8)
        	FullStatePlate += " ";
        
        String BootString = "";
        //BootString = SearchData.SearchVBootT(FullStatePlate, FullStatePlate.length(), "VBOOT.T");
        //We'll try the Find Record Line in Android to see how fast it works on large files.
        //If it is fast then I'll retire the SearchVBoot function.
        //BootString = SearchData.FindRecordLine(FullStatePlate, FullStatePlate.length(), "VBOOT.T", dan);
        BootString = SearchData.SearchVBootT(FullStatePlate, FullStatePlate.length(), "VBOOT.T", dan);
        if (!BootString.trim().equals("NIF"))
        {
        	FoundFlag = Integer.valueOf(BootString.substring(11, 12));
        }
		
		return FoundFlag;
	}

}
