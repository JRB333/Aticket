package com.clancy.aticket;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences; 

public class WorkingStorage {
	
	  public static String GetCharVal(String VariableName, Context cntxt) {
		  SharedPreferences AticketGetPrefs = cntxt.getSharedPreferences("dbHoldValues",Activity.MODE_WORLD_READABLE);
		  return AticketGetPrefs.getString(VariableName, "");
	 }
	  
	  public static void StoreCharVal(String VariableName, String WhatToWrite, Context cntxt) {
		  SharedPreferences AticketPutPrefs = cntxt.getSharedPreferences("dbHoldValues", Activity.MODE_WORLD_READABLE);
		  SharedPreferences.Editor prefEditor = AticketPutPrefs.edit();
		  prefEditor.putString(VariableName, WhatToWrite);
		  //prefEditor.putBoolean("PaidUser", false);
		  prefEditor.commit(); 
	 }

	  public static void StoreLongVal(String VariableName, int WhatToWrite, Context cntxt) {
		  SharedPreferences AticketPutPrefs = cntxt.getSharedPreferences("dbLongValues", 0);
		  SharedPreferences.Editor prefEditor = AticketPutPrefs.edit();
		  prefEditor.putInt(VariableName, WhatToWrite);
		  prefEditor.commit(); 
	 } 
	  
	  public static int GetLongVal(String VariableName, Context cntxt) {
		  SharedPreferences AticketGetPrefs = cntxt.getSharedPreferences("dbLongValues",0);
		  return AticketGetPrefs.getInt(VariableName, 0);
	 }
	  
	  public static void ClearAllVariables(Context cntxt){
		  	StoreCharVal(Defines.PrintStateVal, "", cntxt);
	        StoreCharVal(Defines.PrintProblemVal, "", cntxt);
	        StoreCharVal(Defines.SaveProblemVal, " ", cntxt);
	        StoreCharVal(Defines.PrintYearVal, "", cntxt);
	        StoreCharVal(Defines.PrintVehicleYearVal, "", cntxt);
	        StoreCharVal(Defines.PrintMonthVal, "", cntxt);
	        StoreCharVal(Defines.SaveMonthVal, "", cntxt);
	        StoreCharVal(Defines.PrintDayVal, "", cntxt);
	        StoreCharVal(Defines.SaveDayVal, "", cntxt);
	        StoreCharVal(Defines.PrintChalkVal, "", cntxt);
	        StoreCharVal(Defines.SaveChalkVal, "", cntxt);
	        //' StoreCharVal(Defines.PrintViolateVal, "", cntxt);
	        StoreCharVal(Defines.SaveViolateVal, "", cntxt);
	        StoreCharVal(Defines.PrintFine1Val, "", cntxt);
	        StoreCharVal(Defines.PrintFine2Val, "", cntxt);
	        StoreCharVal(Defines.PrintFine3Val, "", cntxt);
	        StoreCharVal(Defines.PrintFine1LargeVal, "", cntxt);
	        StoreCharVal(Defines.PrintFine2LargeVal, "", cntxt);
	        StoreCharVal(Defines.PrintFine3LargeVal, "", cntxt);
	        StoreCharVal(Defines.PrintOrdinanceVal, "", cntxt);
	        StoreCharVal(Defines.PrintViolCodeVal, "", cntxt);
	        StoreCharVal(Defines.PrintViolDesc1Val, "", cntxt);
	        StoreCharVal(Defines.PrintViolDesc2Val, "", cntxt);
	        StoreCharVal(Defines.PrintComment1Val, "", cntxt);
	        StoreCharVal(Defines.PrintComment2Val, "", cntxt);
	        StoreCharVal(Defines.PrintComment3Val, "", cntxt);
	        StoreCharVal(Defines.PrintTimeRang1Val, "", cntxt);
	        StoreCharVal(Defines.PrintTimeRang2Val, "", cntxt);
	        StoreCharVal(Defines.PrintTimeRang3Val, "", cntxt);
	        StoreCharVal(Defines.PrintNote1Val, "", cntxt);
	        StoreCharVal(Defines.PrintNote2Val, "", cntxt);
	        StoreCharVal(Defines.PrintBoundVal, "", cntxt);
	        StoreCharVal(Defines.PrintSideVal, "", cntxt);
	        StoreCharVal(Defines.SaveVinVal, "", cntxt);

	        StoreCharVal(Defines.PrintNYVinVal, "", cntxt);
	        StoreCharVal(Defines.SaveNYVinVal, "", cntxt);
	        StoreCharVal(Defines.PrintMakeVal, "", cntxt);
	        StoreCharVal(Defines.SaveMakeVal, "", cntxt);
	        StoreCharVal(Defines.PrintModelVal, "", cntxt);
	        StoreCharVal(Defines.PrintLotVal, "", cntxt);
	        StoreCharVal(Defines.SaveLotVal, "", cntxt);
	        StoreCharVal(Defines.PrintMeterVal, "", cntxt);
	        StoreCharVal(Defines.PrintDallasMeterVal, "", cntxt);
	        StoreCharVal(Defines.SaveDallasMeterVal, "", cntxt);
	        StoreCharVal(Defines.PrintDurangoMeterVal, "", cntxt);
	        StoreCharVal(Defines.SaveDurangoMeterVal, "", cntxt);
	        StoreCharVal(Defines.PrintStickerVal, "", cntxt);
	        StoreCharVal(Defines.SaveStickerVal, "", cntxt);
	        StoreCharVal(Defines.PrintColorVal, "", cntxt);
	        StoreCharVal(Defines.SaveColorVal, "", cntxt);
	        StoreCharVal(Defines.PrintColorTwoVal, "", cntxt);
	        StoreCharVal(Defines.SaveColorTwoVal, "", cntxt);
	        StoreCharVal(Defines.PrintBodyVal, "", cntxt);
	        StoreCharVal(Defines.SaveBodyVal, " ", cntxt);
	        StoreCharVal(Defines.PrintTypeVal, "", cntxt);
	        StoreCharVal(Defines.SaveTypeVal, " ", cntxt);
	        StoreCharVal(Defines.PrintAddressVal, "", cntxt);
	        StoreCharVal(Defines.SaveNumberVal, "", cntxt);
	        StoreCharVal(Defines.PrintNumberVal, "", cntxt);
	        StoreCharVal(Defines.SavePermitVal, "", cntxt);
	        StoreCharVal(Defines.PrintPermitVal, "", cntxt);
	        StoreCharVal(Defines.SaveLastFourVal, "", cntxt);
	        StoreCharVal(Defines.PrintLastFourVal, "", cntxt);
	        StoreCharVal(Defines.PrintDirectionVal, "", cntxt);
	        StoreCharVal(Defines.SaveDirectionVal, " ", cntxt);
	        StoreCharVal(Defines.PrintStreetVal, " ", cntxt);
	        StoreCharVal(Defines.SaveStreetVal, " ", cntxt);
	        StoreCharVal(Defines.PrintCrossStreetVal, " ", cntxt);
	        StoreCharVal(Defines.SaveCrossStreetVal, " ", cntxt);

	        StoreCharVal(Defines.SaveCommentVal, "", cntxt);
	        StoreCharVal(Defines.PlateMonthFlagVal, "", cntxt);
	        StoreCharVal(Defines.MeterFlagVal, "", cntxt);
	        StoreCharVal(Defines.ChalkFlagVal, "", cntxt);
	        StoreCharVal(Defines.CommentFlagVal, "", cntxt);
	        StoreCharVal(Defines.StickerFlagVal, "", cntxt);
	        StoreCharVal(Defines.OverTimeFlagVal, "", cntxt);
	        StoreCharVal(Defines.BeginTimeFlagVal, "", cntxt);
	        StoreCharVal(Defines.EndTimeFlagVal, "", cntxt);
	        StoreCharVal(Defines.OnBootListVal, "", cntxt);
	        StoreCharVal(Defines.SaveScanRegVal, "", cntxt);
	        StoreCharVal(Defines.SaveStemVal, "", cntxt);
	        StoreCharVal(Defines.PrintMiniTowVal, "", cntxt);
	        StoreCharVal(Defines.PrintCurrentDueVal, "", cntxt);
	        StoreCharVal(Defines.SaveShowSpaceVal, "", cntxt);
	        StoreCharVal(Defines.EllensBurg010Val, "", cntxt);
		    StoreCharVal(Defines.SavingTickFile, "", cntxt);

		  StoreLongVal(Defines.CurrentPrintVal, 3, cntxt);
	  }

	  public static void ClearSomeVariables(Context cntxt){
		  	StoreCharVal(Defines.PrintAdditionalComment1Val, "", cntxt);
		  	StoreCharVal(Defines.PrintAdditionalComment2Val, "", cntxt);
		  	StoreCharVal(Defines.PrintAdditionalComment3Val, "", cntxt);
		    StoreCharVal(Defines.PrintTempExpireVal, "", cntxt);
		    StoreCharVal(Defines.SaveTempPlateVal, "", cntxt);
	        StoreCharVal(Defines.PrintMonthVal, "", cntxt);
	        StoreCharVal(Defines.SaveMonthVal, "", cntxt);
	        StoreCharVal(Defines.PrintYearVal, "", cntxt);
	        StoreCharVal(Defines.SaveYearVal, "", cntxt);
	        StoreCharVal(Defines.PrintDayVal, "", cntxt);
	        StoreCharVal(Defines.SaveDayVal, "", cntxt);
	        StoreCharVal(Defines.SaveVinVal, "", cntxt);
	        StoreCharVal(Defines.PrintNYVinVal, "", cntxt);
	        StoreCharVal(Defines.SaveNYVinVal, "", cntxt);
	        StoreCharVal(Defines.PrintStickerVal, "", cntxt);
	        StoreCharVal(Defines.SaveStickerVal, "", cntxt);
	        StoreCharVal(Defines.SaveLastFourVal, "", cntxt);
	        StoreCharVal(Defines.PrintLastFourVal, "", cntxt);
	        StoreCharVal(Defines.SaveCrossStreetVal, " ", cntxt);
	        StoreCharVal(Defines.PlateMonthFlagVal, "", cntxt);
	        StoreCharVal(Defines.MeterFlagVal, "", cntxt);
	        StoreCharVal(Defines.CommentFlagVal, "", cntxt);
	        StoreCharVal(Defines.StickerFlagVal, "", cntxt);
	        StoreCharVal(Defines.OverTimeFlagVal, "", cntxt);
	        StoreCharVal(Defines.BeginTimeFlagVal, "", cntxt);
	        StoreCharVal(Defines.EndTimeFlagVal, "", cntxt);
	        StoreCharVal(Defines.OnBootListVal, "", cntxt);
	        StoreCharVal(Defines.SaveStemVal, "", cntxt);
	        StoreCharVal(Defines.PrintMeterVal, "", cntxt);
	        StoreCharVal(Defines.SaveDirectionVal, "", cntxt);
	        StoreCharVal(Defines.PrintDirectionVal, "", cntxt);
		    StoreCharVal(Defines.PrintChalkVal, "", cntxt);
		    StoreCharVal(Defines.SaveChalkVal, "", cntxt);
		    StoreCharVal(Defines.ChalkFlagVal, "", cntxt);
		    StoreCharVal(Defines.ReturnToChalk, "", cntxt);
            StoreCharVal(Defines.SavingTickFile, "", cntxt);
	  }

	  public static void ClearIOUVariables(Context cntxt){
	        StoreCharVal(Defines.PrintYearVal, "", cntxt);
	        StoreCharVal(Defines.SaveYearVal, "", cntxt);
	        StoreCharVal(Defines.PrintMakeVal, "", cntxt);
	        StoreCharVal(Defines.SaveMakeVal, "", cntxt);
	        StoreCharVal(Defines.SaveColorVal, "", cntxt);
	        StoreCharVal(Defines.SaveColorTwoVal, "", cntxt);
	        StoreCharVal(Defines.PrintBodyVal, "", cntxt);
	        StoreCharVal(Defines.SaveBodyVal, "", cntxt);
	        StoreCharVal(Defines.SaveNumberVal, "", cntxt);
	        StoreCharVal(Defines.SaveDirectionVal, "", cntxt);
	        StoreCharVal(Defines.SaveStreetVal, " ", cntxt);
		    StoreCharVal(Defines.SavingTickFile, "", cntxt);
	  }
}
