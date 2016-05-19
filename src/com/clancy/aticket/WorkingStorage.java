package com.clancy.aticket;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences; 


public class WorkingStorage {
	
	  public static String GetCharVal(String VariableName, Context dan) {
		  SharedPreferences AticketGetPrefs = dan.getSharedPreferences("dbHoldValues",Activity.MODE_WORLD_READABLE);
		  return AticketGetPrefs.getString(VariableName, "");
	 }
	  
	  public static void StoreCharVal(String VariableName, String WhatToWrite, Context dan) {
		  SharedPreferences AticketPutPrefs = dan.getSharedPreferences("dbHoldValues", Activity.MODE_WORLD_READABLE);
		  SharedPreferences.Editor prefEditor = AticketPutPrefs.edit();
		  prefEditor.putString(VariableName, WhatToWrite);
		  //prefEditor.putBoolean("PaidUser", false);
		  prefEditor.commit(); 
	 }  
	  public static void StoreLongVal(String VariableName, int WhatToWrite, Context dan) {
		  SharedPreferences AticketPutPrefs = dan.getSharedPreferences("dbLongValues", 0);
		  SharedPreferences.Editor prefEditor = AticketPutPrefs.edit();
		  prefEditor.putInt(VariableName, WhatToWrite);
		  prefEditor.commit(); 
	 } 
	  
	  public static int GetLongVal(String VariableName, Context dan) {
		  SharedPreferences AticketGetPrefs = dan.getSharedPreferences("dbLongValues",0);
		  return AticketGetPrefs.getInt(VariableName, 0);
	 }
	  
	  public static void ClearAllVariables(Context dan){
		  	StoreCharVal(Defines.PrintStateVal, "", dan);
	        StoreCharVal(Defines.PrintProblemVal, "", dan);
	        StoreCharVal(Defines.SaveProblemVal, " ", dan);
	        StoreCharVal(Defines.PrintYearVal, "", dan);
	        StoreCharVal(Defines.PrintVehicleYearVal, "", dan);
	        StoreCharVal(Defines.PrintMonthVal, "", dan);
	        StoreCharVal(Defines.SaveMonthVal, "", dan);
	        StoreCharVal(Defines.PrintDayVal, "", dan);
	        StoreCharVal(Defines.SaveDayVal, "", dan);
	        StoreCharVal(Defines.PrintChalkVal, "", dan);
	        StoreCharVal(Defines.SaveChalkVal, "", dan);
	        //' StoreCharVal(Defines.PrintViolateVal, "", dan); 
	        StoreCharVal(Defines.SaveViolateVal, "", dan);
	        StoreCharVal(Defines.PrintFine1Val, "", dan);
	        StoreCharVal(Defines.PrintFine2Val, "", dan);
	        StoreCharVal(Defines.PrintFine3Val, "", dan);
	        StoreCharVal(Defines.PrintFine1LargeVal, "", dan);
	        StoreCharVal(Defines.PrintFine2LargeVal, "", dan);
	        StoreCharVal(Defines.PrintFine3LargeVal, "", dan);	        
	        StoreCharVal(Defines.PrintOrdinanceVal, "", dan);
	        StoreCharVal(Defines.PrintViolCodeVal, "", dan);
	        StoreCharVal(Defines.PrintViolDesc1Val, "", dan);
	        StoreCharVal(Defines.PrintViolDesc2Val, "", dan);
	        StoreCharVal(Defines.PrintComment1Val, "", dan);
	        StoreCharVal(Defines.PrintComment2Val, "", dan);
	        StoreCharVal(Defines.PrintComment3Val, "", dan);
	        StoreCharVal(Defines.PrintTimeRang1Val, "", dan);
	        StoreCharVal(Defines.PrintTimeRang2Val, "", dan);
	        StoreCharVal(Defines.PrintTimeRang3Val, "", dan);
	        StoreCharVal(Defines.PrintNote1Val, "", dan);
	        StoreCharVal(Defines.PrintNote2Val, "", dan);
	        StoreCharVal(Defines.PrintBoundVal, "", dan);
	        StoreCharVal(Defines.PrintSideVal, "", dan);
	        StoreCharVal(Defines.SaveVinVal, "", dan);

	        StoreCharVal(Defines.PrintNYVinVal, "", dan);
	        StoreCharVal(Defines.SaveNYVinVal, "", dan);
	        StoreCharVal(Defines.PrintMakeVal, "", dan);
	        StoreCharVal(Defines.SaveMakeVal, "", dan);
	        StoreCharVal(Defines.PrintModelVal, "", dan);
	        StoreCharVal(Defines.PrintLotVal, "", dan);
	        StoreCharVal(Defines.SaveLotVal, "", dan);
	        StoreCharVal(Defines.PrintMeterVal, "", dan);
	        StoreCharVal(Defines.PrintDallasMeterVal, "", dan);
	        StoreCharVal(Defines.SaveDallasMeterVal, "", dan);
	        StoreCharVal(Defines.PrintDurangoMeterVal, "", dan);
	        StoreCharVal(Defines.SaveDurangoMeterVal, "", dan);
	        StoreCharVal(Defines.PrintStickerVal, "", dan);
	        StoreCharVal(Defines.SaveStickerVal, "", dan);
	        StoreCharVal(Defines.PrintColorVal, "", dan);
	        StoreCharVal(Defines.SaveColorVal, "", dan);
	        StoreCharVal(Defines.PrintColorTwoVal, "", dan);
	        StoreCharVal(Defines.SaveColorTwoVal, "", dan);
	        StoreCharVal(Defines.PrintBodyVal, "", dan);
	        StoreCharVal(Defines.SaveBodyVal, " ", dan);
	        StoreCharVal(Defines.PrintTypeVal, "", dan);
	        StoreCharVal(Defines.SaveTypeVal, " ", dan);
	        StoreCharVal(Defines.PrintAddressVal, "", dan);
	        StoreCharVal(Defines.SaveNumberVal, "", dan);
	        StoreCharVal(Defines.PrintNumberVal, "", dan);
	        StoreCharVal(Defines.SavePermitVal, "", dan);
	        StoreCharVal(Defines.PrintPermitVal, "", dan);
	        StoreCharVal(Defines.SaveLastFourVal, "", dan);
	        StoreCharVal(Defines.PrintLastFourVal, "", dan);
	        StoreCharVal(Defines.PrintDirectionVal, "", dan);
	        StoreCharVal(Defines.SaveDirectionVal, " ", dan);
	        StoreCharVal(Defines.PrintStreetVal, " ", dan);
	        StoreCharVal(Defines.SaveStreetVal, " ", dan);
	        StoreCharVal(Defines.PrintCrossStreetVal, " ", dan);
	        StoreCharVal(Defines.SaveCrossStreetVal, " ", dan);

	        StoreCharVal(Defines.SaveCommentVal, "", dan);
	        StoreCharVal(Defines.PlateMonthFlagVal, "", dan);
	        StoreCharVal(Defines.MeterFlagVal, "", dan);
	        StoreCharVal(Defines.ChalkFlagVal, "", dan);
	        StoreCharVal(Defines.CommentFlagVal, "", dan);
	        StoreCharVal(Defines.StickerFlagVal, "", dan);
	        StoreCharVal(Defines.OverTimeFlagVal, "", dan);
	        StoreCharVal(Defines.BeginTimeFlagVal, "", dan);
	        StoreCharVal(Defines.EndTimeFlagVal, "", dan);
	        StoreCharVal(Defines.OnBootListVal, "", dan);
	        StoreCharVal(Defines.SaveScanRegVal, "", dan);
	        StoreCharVal(Defines.SaveStemVal, "", dan);
	        StoreCharVal(Defines.PrintMiniTowVal, "", dan);
	        StoreCharVal(Defines.PrintCurrentDueVal, "", dan);
	        StoreCharVal(Defines.SaveShowSpaceVal, "", dan);
	        StoreCharVal(Defines.EllensBurg010Val, "", dan);

	        StoreLongVal(Defines.CurrentPrintVal, 3, dan);		  
	  }
	  public static void ClearSomeVariables(Context dan){
		  	StoreCharVal(Defines.PrintAdditionalComment1Val, "", dan);
		  	StoreCharVal(Defines.PrintAdditionalComment2Val, "", dan);
		  	StoreCharVal(Defines.PrintAdditionalComment3Val, "", dan);
		    StoreCharVal(Defines.PrintTempExpireVal, "", dan);
		    StoreCharVal(Defines.SaveTempPlateVal, "", dan);
	        StoreCharVal(Defines.PrintMonthVal, "", dan);
	        StoreCharVal(Defines.SaveMonthVal, "", dan);
	        StoreCharVal(Defines.PrintYearVal, "", dan);
	        StoreCharVal(Defines.SaveYearVal, "", dan);	        
	        StoreCharVal(Defines.PrintDayVal, "", dan);
	        StoreCharVal(Defines.SaveDayVal, "", dan);
	        StoreCharVal(Defines.PrintChalkVal, "", dan);
	        StoreCharVal(Defines.SaveChalkVal, "", dan);
	        StoreCharVal(Defines.SaveVinVal, "", dan);
	        StoreCharVal(Defines.PrintNYVinVal, "", dan);
	        StoreCharVal(Defines.SaveNYVinVal, "", dan);
	        StoreCharVal(Defines.PrintStickerVal, "", dan);
	        StoreCharVal(Defines.SaveStickerVal, "", dan);
	        StoreCharVal(Defines.SaveLastFourVal, "", dan);
	        StoreCharVal(Defines.PrintLastFourVal, "", dan);
	        StoreCharVal(Defines.SaveCrossStreetVal, " ", dan);
	        StoreCharVal(Defines.PlateMonthFlagVal, "", dan);
	        StoreCharVal(Defines.MeterFlagVal, "", dan);
	        StoreCharVal(Defines.ChalkFlagVal, "", dan);
	        StoreCharVal(Defines.CommentFlagVal, "", dan);
	        StoreCharVal(Defines.StickerFlagVal, "", dan);
	        StoreCharVal(Defines.OverTimeFlagVal, "", dan);
	        StoreCharVal(Defines.BeginTimeFlagVal, "", dan);
	        StoreCharVal(Defines.EndTimeFlagVal, "", dan);
	        StoreCharVal(Defines.OnBootListVal, "", dan);
	        StoreCharVal(Defines.SaveStemVal, "", dan);
	        StoreCharVal(Defines.PrintMeterVal, "", dan);
	        StoreCharVal(Defines.SaveDirectionVal, "", dan);
	        StoreCharVal(Defines.PrintDirectionVal, "", dan);

	  }
	  public static void ClearIOUVariables(Context dan){
	        StoreCharVal(Defines.PrintYearVal, "", dan);
	        StoreCharVal(Defines.SaveYearVal, "", dan);
	        StoreCharVal(Defines.PrintMakeVal, "", dan);
	        StoreCharVal(Defines.SaveMakeVal, "", dan);
	        StoreCharVal(Defines.SaveColorVal, "", dan);
	        StoreCharVal(Defines.SaveColorTwoVal, "", dan);
	        StoreCharVal(Defines.PrintBodyVal, "", dan);
	        StoreCharVal(Defines.SaveBodyVal, "", dan);
	        StoreCharVal(Defines.SaveNumberVal, "", dan);
	        StoreCharVal(Defines.SaveDirectionVal, "", dan);
	        StoreCharVal(Defines.SaveStreetVal, " ", dan);
	  }

}
