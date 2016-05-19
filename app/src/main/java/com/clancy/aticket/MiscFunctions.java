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
	
	  public static void VoidTheTicket(Context cntxt)
	  {
		  WorkingStorage.StoreLongVal(Defines.TicketVoidFlag, 1, cntxt);
		  SaveTicket.SaveVoidTFile(cntxt);
		  EraseVoidedPictures(cntxt);
		  WorkingStorage.StoreLongVal(Defines.TicketVoidFlag, 0, cntxt);
		  String tmpPlate = WorkingStorage.GetCharVal(Defines.PrintPlateVal,cntxt);
		  SystemIOFile.Delete(tmpPlate+".T");
	  }
	  
	  public static void AddFeeFine1(Context cntxt)
	  {
		  if (WorkingStorage.GetCharVal(Defines.NoFeeFlag, cntxt).equals("SKIP"))
		  {
			  WorkingStorage.StoreCharVal(Defines.PrintFeeVal, "00.00", cntxt);
			  return;
		  }
		  if (Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt)) == 0 )
		  {
			  return;
		  }
		  double nFine1 = 0.00;
		  //nFine1 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine1Val, cntxt)) +
		  //Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt));

		  nFine1 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, cntxt)) +
		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt));
		  int blah = (int) (nFine1*100);
		  String TempString = Integer.toString(blah);
		  TempString = TempString.substring(0, TempString.length()-2)+"."+TempString.substring(TempString.length()-2);
		  //WorkingStorage.StoreCharVal(Defines.PrintFine1Val, TempString, cntxt);
		  WorkingStorage.StoreCharVal(Defines.PrintFine1LargeVal, TempString, cntxt);
	  }

	  public static void AddFeeFine2(Context cntxt)
	  {
		  if (WorkingStorage.GetCharVal(Defines.NoFeeFlag, cntxt).equals("SKIP"))
		  {
			  WorkingStorage.StoreCharVal(Defines.PrintFeeVal, "00.00", cntxt);
			  return;
		  }
		  if (Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt)) == 0 )
		  {
			  return;
		  }
		  double nFine2 = 0.00;
//		  nFine2 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine2Val, cntxt)) +
//		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt));
		  nFine2 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine2LargeVal, cntxt)) +
		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt));
		  
		  int blah = (int) (nFine2*100);
		  String TempString = Integer.toString(blah);
		  TempString = TempString.substring(0, TempString.length()-2)+"."+TempString.substring(TempString.length()-2);
		  //WorkingStorage.StoreCharVal(Defines.PrintFine2Val, TempString, cntxt);
		  WorkingStorage.StoreCharVal(Defines.PrintFine2LargeVal, TempString, cntxt);
	  }

	  public static void AddFeeFine3(Context cntxt)
	  {
		  if (WorkingStorage.GetCharVal(Defines.NoFeeFlag, cntxt).equals("SKIP"))
		  {
			  WorkingStorage.StoreCharVal(Defines.PrintFeeVal, "00.00", cntxt);
			  return;
		  }
		  if (Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt)) == 0 )
		  {
			  return;
		  }
		  double nFine3 = 0.00;
//		  nFine3 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine3Val, cntxt)) +
//		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt));
		  nFine3 = Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFine3LargeVal, cntxt)) +
		  Double.valueOf(WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt));
		  
		  int blah = (int) (nFine3*100);
		  String TempString = Integer.toString(blah);
		  TempString = TempString.substring(0, TempString.length()-2)+"."+TempString.substring(TempString.length()-2);
		  //WorkingStorage.StoreCharVal(Defines.PrintFine3Val, TempString, cntxt);
		  WorkingStorage.StoreCharVal(Defines.PrintFine3LargeVal, TempString, cntxt);
	  }	  

	  
	  public static void EraseVoidedPictures(Context cntxt)
	  {
		  String FileNameString = "";
		  File f;
		  
		  FileNameString = WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt)+"-1.JPG";
	      f=new File("/data/data/com.clancy.aticket/files/",FileNameString);
	      if(f.exists())
	      {
	    	  f.delete();
	      }
	      f = null;
	      
		  FileNameString = WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt)+"-2.JPG";
	      f=new File("/data/data/com.clancy.aticket/files/",FileNameString);
	      if(f.exists())
	      {
	    	  f.delete();
	      }
	      f = null;
	      
		  FileNameString = WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt)+"-3.JPG";
	      f=new File("/data/data/com.clancy.aticket/files/",FileNameString);
	      if(f.exists())
	      {
	    	  f.delete();
	      }
	      f = null;

	  }

}
