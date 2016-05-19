package com.clancy.aticket;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;



import android.app.ActivityGroup;
import android.content.Context;
import android.widget.Toast;


public class SearchData extends ActivityGroup {
	 
	  static public int GetFileSize(String filename) 
	  {

	 	int ReturnSize = 0;
	 	File file = new File("/data/data/com.clancy.aticket/files/",filename);
	 	//File file = new File(filename);
		if (!file.exists() || !file.isFile()) 
		{	    	
			ReturnSize = 0;
			file = null;
			return ReturnSize;
		}
		ReturnSize = (int) file.length();
		file = null;
		return ReturnSize;
	  }
	  
	  public static String GetRecordNumberNoLength(String filename, int RecordNo, Context dan) 
	  {
			String ReturnString = "ERROR"; 
		  	File file = new File("/data/data/com.clancy.aticket/files/",filename);  
		  	if(file.exists())   // check if file exist  
		  	{   		   
		  		try {  
			         BufferedReader br = new BufferedReader(new FileReader(file));  
			         String line;
			         int i = 1;
			         while ((line = br.readLine()) != null && i <= RecordNo) 
			         {
			        	 ReturnString = line;
			        	 i++;
			         }  
			         br.close();
			         if (i != RecordNo+1) //then we did not make it to the record
			         {
			        	 ReturnString = "ERROR";
			         }
			         file = null;			         
			         
			     }catch (IOException e) 
			     {  
						Toast.makeText(dan, e.toString(), 2000);  
			     }  
	         }
	         else  
			 {  
	        	 file = null;
			 }
		  	 return ReturnString;
	  }	

	  public static String ScrollDownThroughFile(String filename, Context dan) 
	  {
		  	int LocalRecordPointer = WorkingStorage.GetLongVal(Defines.RecordPointer, dan);
		  	LocalRecordPointer --;
		  	
		  	String ReturnString = "ERROR"; 
		  	File file = new File("/data/data/com.clancy.aticket/files/",filename);  
		  	if(file.exists())   // check if file exist  
		  	{   		   
		  		try {  
			         BufferedReader br = new BufferedReader(new FileReader(file));  
			         String line;
					 if (LocalRecordPointer <= 0) // go until the last Record 
					 {
						 LocalRecordPointer = 0;
						 while ((line = br.readLine()) != null ) 
				         {
				        	 ReturnString = line;
				        	 LocalRecordPointer++;
				         }  					  		
					 }
					 else
					 {
				         int i = 1;
				         while ((line = br.readLine()) != null && i <= LocalRecordPointer ) 
				         {
				        	 ReturnString = line;
				        	 i++;
				         }  					 
					 }
			         br.close();
			         file = null;	
			         WorkingStorage.StoreLongVal(Defines.RecordPointer, LocalRecordPointer, dan);
			         
			     }catch (IOException e) 
			     {  
						Toast.makeText(dan, e.toString(), 2000);  
			     }  
	         }
	         else  
			 {  
	        	 file = null;
			 }
		  	 return ReturnString;
	  }	

	  public static String ScrollUpThroughFile(String filename, Context dan) 
	  {
		  	int LocalRecordPointer = WorkingStorage.GetLongVal(Defines.RecordPointer, dan);
		  	LocalRecordPointer ++;
		  	
		  	String ReturnString = "ERROR"; 
		  	File file = new File("/data/data/com.clancy.aticket/files/",filename);  
		  	if(file.exists())   // check if file exist  
		  	{   		   
		  		try {  
			         BufferedReader br = new BufferedReader(new FileReader(file));  
			         String line;
			         int i = 1;
			         while ((line = br.readLine()) != null && i <= LocalRecordPointer) 
			         {
			        	 ReturnString = line;
			        	 i++;
			         }  
			         br.close();
			         if (i != LocalRecordPointer+1 || ReturnString == null) //then we did not make it to the record so start over at the beginning of the file
			         {
			        	 LocalRecordPointer = 1;
			        	 ReturnString = GetRecordNumberNoLength( filename, LocalRecordPointer, dan);
			         }
			         file = null;			         
	
			         WorkingStorage.StoreLongVal(Defines.RecordPointer, LocalRecordPointer, dan);
			         
			     }catch (IOException e) 
			     {  
						Toast.makeText(dan, e.toString(), 2000);  
			     }  
	         }
	         else  
			 {  
	        	 file = null;
			 }
		  	 return ReturnString;
	  }	

	  
	  public static String FindRecordLine(String SearchedString, int CharactersToSearch, String filename, Context dan) 
	  {
			String ReturnString = "NIF"; //NOT IN FILE, this will be passed back unless located
		  	File file = new File("/data/data/com.clancy.aticket/files/",filename);
		  	Boolean FoundRecord = false;
		  	if(file.exists())   // check if file exist  
		  	{   		   
		  		try {  
			         BufferedReader br = new BufferedReader(new FileReader(file));  
			         String line;
			         
			         String SearchedStringPadded = SearchedString.toUpperCase();
			         while (SearchedStringPadded.length() < CharactersToSearch)
			        	 SearchedStringPadded += " ";
			         
			         int i = 1;
			         while ((line = br.readLine()) != null ) 
			         {
			        	 ReturnString = line;

			        	 if (ReturnString.length()>= CharactersToSearch)
			        	 {
			        		 if(ReturnString.substring(0,CharactersToSearch).equals(SearchedStringPadded))
			        		 {
			        			 WorkingStorage.StoreLongVal(Defines.RecordPointer, i, dan);
			        			 FoundRecord = true;
			        			 break;
			        		 }
			        	 }
			        	 i++;
			         }  
			         br.close();
			         file = null;
			         if (FoundRecord == false)
			         {
			        	 ReturnString = "NIF";
			         }
			         
			     }catch (IOException e) 
			     {  
						Toast.makeText(dan, e.toString(), 2000);  
			     }  
	         }
	         else  
			 {  
	        	 file = null;
			 }
		  	 return ReturnString;
	  }	
	  
	  public static String FindValueInRecord(String SearchedString, int CharactersToSearch, String filename, Context dan) 
	  {
		    String line ="";
	        int Location = 0;
	        String ReturnRecordLine = "NIF"; //NOT IN FILE, this will be passed back unless located
		  	File file = new File("/data/data/com.clancy.aticket/files/",filename);
		  	if(file.exists())   // check if file exist  
		  	{   		   
		  		try {  
			         BufferedReader br = new BufferedReader(new FileReader(file));  			         			         
			         while ((line = br.readLine()) != null ) 
			         {
			        	 if (line.length() > CharactersToSearch)
			        	 {
			        		 Location = line.indexOf(SearchedString.toUpperCase());
			        		 if (Location >= 0)
			        		 {
			        			 if (line.length() < 140)
			        			 {
			        				 ReturnRecordLine = line.substring(Location);
			        			 }
			        			 else
			        			 {
			        				 if (line.length() - Location >= 120)
			        				 {
			        					 if (Location+120 > line.length())
			        						 ReturnRecordLine = line.substring(Location, line.length()); 
			        					 else
			        						 ReturnRecordLine = line.substring(Location, Location+120); // 120 characters should return large enough
			        				 }
			        				 else
			        				 {
			        					 ReturnRecordLine = line.substring(Location, line.length());
			        				 }
			        			 }
			        			 break;
			        		 }
			        	 }
			         }  
			         br.close();
			         file = null;
			         
			     }catch (IOException e) 
			     {  
						Toast.makeText(dan, e.toString(), 2000);  
			     }  
	         }
	         else  
			 {  
	        	 file = null;
			 }
		  	 return ReturnRecordLine;
	  }	
	 
	  public static String SearchVBootT(String SearchedString, int CharactersToSearch, String FileName, Context dan) 
	  {
			String TempLine = "";
			String ReturnRecordLine = "NIF"; //NOT IN FILE, this will be passed back unless located
		  	int Location = 0;
		  	if (SystemIOFile.exists(FileName))
		  	{   		   
		  		int FileLength = GetFileSize(FileName);
		  		int NumberOfRecords = FileLength / 14;
		  		if (WorkingStorage.GetCharVal(Defines.ClientName,
						dan).equals("BYUID")) {
		  			NumberOfRecords = FileLength / 13; //WTF! Changed this on 8-9-2012 BYUID vboot record length is 13!
		  		}
		  		Boolean NotInList = false;
		  		int Halfway = Math.round(NumberOfRecords / 2);
		  		String ShortLine = "";
		  		int UpperBound = NumberOfRecords;
		  		int LowerBound = 0;
		  		int PreviousHalfway = 0;
		  		
		        while (NotInList == false ) 
		        {
			  		if (Halfway == 0) // added on 4-17 to prevent Alaska plates from looping back prior to first plate and exception at record 0
			  		{
			  			NotInList = true;
			  			break;
			  		}
		        	if (WorkingStorage.GetCharVal(Defines.ClientName,dan).equals("BYUID")) 
			  		{
			  			ShortLine = GetRecordNumber( FileName, Halfway, 13, dan); //WTF! Changed this on 8-9-2012 BYUID vboot record length is 13!
			  		}
			  		else
			  		{
			  			ShortLine = GetRecordNumber( FileName, Halfway, 14, dan);
			  		}
		        	if (SearchedString.equals(ShortLine.substring(0, SearchedString.length())))
                    {
                    	ReturnRecordLine = ShortLine;
                    	break;
                    }
		        	if (SearchedString.compareTo(ShortLine) < 0)
		        	{
		        		UpperBound = Halfway;
		        		Halfway = (int)(Math.round((UpperBound - LowerBound) / 2) + LowerBound);
		        	}
		        	else
		        	{
                        LowerBound = Halfway;
                        Halfway = (int)(Math.round((UpperBound - LowerBound) / 2) + LowerBound);
		        	}
		        	if (PreviousHalfway == Halfway)
		        	{
		        		NotInList = true;
		        	}
		        	else
		        	{
		        		PreviousHalfway = Halfway;
		        	}
		        		
		        }  
	         }
		  	 return ReturnRecordLine;
	  }	
	  
	  public static String GetRecordNumber( String filename, int RecordNo, int LineLength, Context dan) 
	  {
			if (SystemIOFile.exists(filename)==false)
			{
				return "ERROR";
			}

		    int NumberOfRecords = 0;
		    String ReturnLine = "ERROR";
				
		    int FileSize = GetFileSize(filename);
		    NumberOfRecords = FileSize / LineLength;
		    if (RecordNo > NumberOfRecords)
		    {
		    	return "ERROR";
		    }
		    
			File file = new File("/data/data/com.clancy.aticket/files/",filename);		  	
		  	if(file.exists())   // check if file exist  
		  	{   		   
		  		
		  		try {  		  					  			
			         BufferedReader br = new BufferedReader(new FileReader(file));  		         
			         br.skip((RecordNo-1)*LineLength);
			         ReturnLine = br.readLine();
			         br.close();
			         file = null;
			         
			     }catch (IOException e) 
			     {  
						Toast.makeText(dan, e.toString(), 2000);  
			     }  
	         }
	         else  
			 {  
	        	 file = null;
			 }
		  	 if (ReturnLine == null)
		  	 {
		  		 return "ERROR";
		  	 }
		  	 return ReturnLine;
	  }	
}
