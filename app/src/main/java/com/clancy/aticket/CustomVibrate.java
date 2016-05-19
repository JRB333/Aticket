package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;
import android.os.Vibrator;

public class CustomVibrate extends ActivityGroup{	
	public static void VibrateButton(Context dan)
	{
		Vibrator vibKey = (Vibrator) dan.getSystemService(dan.VIBRATOR_SERVICE);
		vibKey.vibrate(10);
	}

}
