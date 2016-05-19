package com.clancy.aticket;

import android.content.Context;

public class MustDoSetup {
	
	public static void WriteMustDoSetupDate(Context dan)
	{
		WorkingStorage.StoreCharVal(Defines.MustDoSetupTodayVal, WorkingStorage.GetCharVal(Defines.PrintDateVal, dan) ,dan);
	}
	public static Boolean MustDoSetupCheck(Context dan)
	{
		if (WorkingStorage.GetCharVal(Defines.PrintDateVal, dan).equals(WorkingStorage.GetCharVal(Defines.MustDoSetupTodayVal, dan)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
