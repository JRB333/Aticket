package com.clancy.aticket;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;

public class GetDate {
	
	public static void GetDateTime(Context dan)
	{
        String PrintDate = "";
        String SaveDate = "";
        String PrintTime = "";
        String SaveTime = "";
        String PrintAmPmTime = "";
        String HittDate = "";
        String CheckTime = "";
        String StampDate  = "";
        String DayOfWeek = "";
        String CurrentYear = "";
        
        Calendar c = Calendar.getInstance();  
        SimpleDateFormat df;
        df = new SimpleDateFormat("MM/dd/yyyy");
        PrintDate = df.format(c.getTime());
        
        df = new SimpleDateFormat("HH:mm");
        PrintTime = df.format(c.getTime()); 
        
        df = new SimpleDateFormat("yyMMdd");
        SaveDate = df.format(c.getTime()); 

        df = new SimpleDateFormat("HHmm");
        SaveTime = df.format(c.getTime()); 

        df = new SimpleDateFormat("yyyy");
        CurrentYear = df.format(c.getTime()); 

        
        df = new SimpleDateFormat("MMddyy");
        StampDate = df.format(c.getTime());
        
        df = new SimpleDateFormat("HHmmss");
        CheckTime = df.format(c.getTime());
        
        df = new SimpleDateFormat("hh:mm a");
        PrintAmPmTime = df.format(c.getTime()); 

        df = new SimpleDateFormat("yyyyMMdd");
        HittDate = df.format(c.getTime()); 
        
        df = new SimpleDateFormat("E");
        DayOfWeek = df.format(c.getTime());         
        DayOfWeek = DayOfWeek.toUpperCase().substring(0,3);        
        
        WorkingStorage.StoreCharVal(Defines.PrintDateVal, PrintDate, dan);
        WorkingStorage.StoreCharVal(Defines.PrintTimeVal, PrintTime, dan);       
        WorkingStorage.StoreCharVal(Defines.SaveDateVal, SaveDate, dan);
        WorkingStorage.StoreCharVal(Defines.SaveTimeVal, SaveTime, dan);
        WorkingStorage.StoreCharVal(Defines.HittTimeVal, SaveTime, dan); // Savetime and HittTime are the save format
        WorkingStorage.StoreCharVal(Defines.StampDateVal, StampDate, dan);        
        WorkingStorage.StoreCharVal(Defines.CheckTimeVal, CheckTime, dan);       
        WorkingStorage.StoreCharVal(Defines.PrintAmPmTimeVal, PrintAmPmTime, dan);        
        WorkingStorage.StoreCharVal(Defines.HittDateVal, HittDate, dan);
        WorkingStorage.StoreCharVal(Defines.DOWVal, DayOfWeek, dan);
        WorkingStorage.StoreCharVal(Defines.CurrentYearVal, CurrentYear, dan);
        
	}

}
