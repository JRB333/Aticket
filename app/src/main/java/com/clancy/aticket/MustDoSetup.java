package com.clancy.aticket;

import android.content.Context;

public class MustDoSetup {

	public static void WriteMustDoSetupDate(Context cntxt)
	{
		WorkingStorage.StoreCharVal(Defines.MustDoSetupTodayVal, WorkingStorage.GetCharVal(Defines.PrintDateVal, cntxt) ,cntxt);
	}

	public static Boolean MustDoSetupCheck(Context cntxt)
	{
		Boolean RetVal = true;
		if (!WorkingStorage.GetCharVal(Defines.PrintDateVal, cntxt).equals(WorkingStorage.GetCharVal(Defines.MustDoSetupTodayVal, cntxt)))
		{
			RetVal = false;
		}

		// Is the Signature to be Captured?
		if (WorkingStorage.GetCharVal(Defines.CaptureSignature, cntxt).equals("YES")) {
			// If so, then check that the Officer's Signature File exists (done in SignatureCaptureForm)
			String BadgeNo = WorkingStorage.GetCharVal(Defines.PrintBadgeVal,cntxt).trim();
			if (!WorkingStorage.GetCharVal(Defines.SignatureFile, cntxt).equals("Sig" + BadgeNo + ".png")) {
				RetVal = false;
			}
		}
		return RetVal;
	}
}
