package com.clancy.aticket;


import java.io.File;

import android.content.Context;

public class MiscFunctions {
	public static boolean validInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}	
	
	  public static void VoidTheTicket(Context dan) 
	  {
		  WorkingStorage.StoreLongVal(Defines.TicketVoidFlag, 1, dan);
		  SaveTicket.SaveVoidTFile(dan);
		  EraseVoidedPictures(dan);
		  WorkingStorage.StoreLongVal(Defines.TicketVoidFlag, 0, dan);
		  String tmpPlate = WorkingStorage.GetCharVal(Defines.PrintPlateVal,dan);
		  SystemIOFile.Delete(tmpPlate+".T");

	  }
	  
	  public static void AddFeeFine1(Context dan)
	  {
		  if (WorkingStorage.GetCharVal(Defines.NoFeeFlag, dan).equals("SKIP"))
		  {
			  WorkingStorage.StoreCharVal(Defines.PrintFeeVal, "00.00", dan);
			  return;
		  }
		  if (Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan)) == 0 )
		  {
			  return;
		  }
		  double nFine1 = 0.00;
		  //nFine1 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine1Val, dan)) +
		  //Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan));

		  nFine1 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, dan)) +
		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan));
		  int blah = (int) (nFine1*100);
		  String TempString = Integer.toString(blah);
		  TempString = TempString.substring(0, TempString.length()-2)+"."+TempString.substring(TempString.length()-2);
		  //WorkingStorage.StoreCharVal(Defines.PrintFine1Val, TempString, dan);		  
		  WorkingStorage.StoreCharVal(Defines.PrintFine1LargeVal, TempString, dan);
	  }
	  public static void AddFeeFine2(Context dan)
	  {
		  if (WorkingStorage.GetCharVal(Defines.NoFeeFlag, dan).equals("SKIP"))
		  {
			  WorkingStorage.StoreCharVal(Defines.PrintFeeVal, "00.00", dan);
			  return;
		  }
		  if (Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan)) == 0 )
		  {
			  return;
		  }
		  double nFine2 = 0.00;
//		  nFine2 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine2Val, dan)) +
//		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan));
		  nFine2 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine2LargeVal, dan)) +
		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan));
		  
		  int blah = (int) (nFine2*100);
		  String TempString = Integer.toString(blah);
		  TempString = TempString.substring(0, TempString.length()-2)+"."+TempString.substring(TempString.length()-2);
		  //WorkingStorage.StoreCharVal(Defines.PrintFine2Val, TempString, dan);
		  WorkingStorage.StoreCharVal(Defines.PrintFine2LargeVal, TempString, dan);
	  }
	  public static void AddFeeFine3(Context dan)
	  {
		  if (WorkingStorage.GetCharVal(Defines.NoFeeFlag, dan).equals("SKIP"))
		  {
			  WorkingStorage.StoreCharVal(Defines.PrintFeeVal, "00.00", dan);
			  return;
		  }
		  if (Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan)) == 0 )
		  {
			  return;
		  }
		  double nFine3 = 0.00;
//		  nFine3 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine3Val, dan)) +
//		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan));
		  nFine3 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine3LargeVal, dan)) +
		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, dan));
		  
		  int blah = (int) (nFine3*100);
		  String TempString = Integer.toString(blah);
		  TempString = TempString.substring(0, TempString.length()-2)+"."+TempString.substring(TempString.length()-2);
		  //WorkingStorage.StoreCharVal(Defines.PrintFine3Val, TempString, dan);		  
		  WorkingStorage.StoreCharVal(Defines.PrintFine3LargeVal, TempString, dan);
	  }	  

	  
	  public static void EraseVoidedPictures(Context dan)
	  {
		  String FileNameString = "";
		  File f;
		  
		  FileNameString = WorkingStorage.GetCharVal(Defines.PrintCitationVal, dan)+"-1.JPG";
	      f=new File("/data/data/com.clancy.aticket/files/",FileNameString);
	      if(f.exists())
	      {
	    	  f.delete();
	      }
	      f = null;
	      
		  FileNameString = WorkingStorage.GetCharVal(Defines.PrintCitationVal, dan)+"-2.JPG";
	      f=new File("/data/data/com.clancy.aticket/files/",FileNameString);
	      if(f.exists())
	      {
	    	  f.delete();
	      }
	      f = null;
	      
		  FileNameString = WorkingStorage.GetCharVal(Defines.PrintCitationVal, dan)+"-3.JPG";
	      f=new File("/data/data/com.clancy.aticket/files/",FileNameString);
	      if(f.exists())
	      {
	    	  f.delete();
	      }
	      f = null;

	  }

}
