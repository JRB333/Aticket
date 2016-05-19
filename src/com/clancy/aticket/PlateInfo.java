package com.clancy.aticket;

import android.content.Context;

public class PlateInfo {
	public static void GetPlateInfo(Context dan)
	{
        String MakeSeek = "";
        String BodySeek  = "";
        String ColorSeek  = "";
        String FullPlate  = "";
        FullPlate = WorkingStorage.GetCharVal(Defines.SavePlateVal, dan);
        while (FullPlate.length() < 8)
        	FullPlate += " ";

        String MakeBodyColorString = "";
        MakeBodyColorString = SearchData.FindRecordLine(FullPlate, 8, "PINFO.A", dan);
        if (!MakeBodyColorString.equals("NIF"))
        {
        	MakeSeek = MakeBodyColorString.substring(8, 11);
            ColorSeek = MakeBodyColorString.substring(11, 14);
            BodySeek = MakeBodyColorString.substring(14);
        }
        WorkingStorage.StoreCharVal(Defines.AutoMakeVal, MakeSeek,dan);
        WorkingStorage.StoreCharVal(Defines.AutoColorVal, ColorSeek, dan);
        WorkingStorage.StoreCharVal(Defines.AutoBodyVal, BodySeek, dan);
	}
	
	public static void GetSecondaryPlateInfo(Context dan)
	{
        if (!SystemIOFile.exists("PLATDATA.T"))
        	return;
        
		String MakeSeek = "";
		MakeSeek = WorkingStorage.GetCharVal(Defines.AutoMakeVal, dan); // preload it from the normal GetPlateInfo
        String BodySeek  = "";
        String ColorSeek  = "";
        ColorSeek = WorkingStorage.GetCharVal(Defines.AutoColorVal, dan); // preload it from the normal GetPlateInfo
        String StateSeek = "";
        String PermitSeek = "";
        String PermitTypeSeek = "";
        String FullPlate  = "";
        FullPlate = WorkingStorage.GetCharVal(Defines.SavePlateVal, dan);
        while (FullPlate.length() < 8)
        	FullPlate += " ";

        String PlateDataString = "";
        PlateDataString = SearchData.FindRecordLine(FullPlate, 8, "PLATDATA.T", dan);
        if (PlateDataString.equals("NIF"))
        {
         	WorkingStorage.StoreCharVal(Defines.AutoStateVal, "", dan);
         	WorkingStorage.StoreCharVal(Defines.AutoPermitVal, "", dan);
         	WorkingStorage.StoreCharVal(Defines.AutoPermitTypeVal, "", dan);
        }
        else
        {
        	StateSeek = PlateDataString.substring(8, 10);
        	PermitSeek = PlateDataString.substring(10, 20).trim();
        	PermitTypeSeek = PlateDataString.substring(35, 60).trim();
        	WorkingStorage.StoreCharVal(Defines.AutoStateVal, StateSeek, dan);
        	WorkingStorage.StoreCharVal(Defines.AutoPermitVal, PermitSeek, dan);
        	WorkingStorage.StoreCharVal(Defines.AutoPermitTypeVal, PermitTypeSeek, dan);
        	if (MakeSeek.equals("")) // go ahead and fill it in with a value... we don't want to overwrite the value we got from pinfo.a
        	{
        		MakeSeek = PlateDataString.substring(60, 76).trim();
        		WorkingStorage.StoreCharVal(Defines.AutoMakeVal, MakeSeek, dan);
        	}
        	if (ColorSeek.equals("")) // go ahead and fill it in with a value... we don't want to overwrite the value we got from pinfo.a
        	{
        		ColorSeek = PlateDataString.substring(76, 92).trim();
        		WorkingStorage.StoreCharVal(Defines.AutoColorVal, ColorSeek, dan);
        	}        	
        }
	}
	
}
