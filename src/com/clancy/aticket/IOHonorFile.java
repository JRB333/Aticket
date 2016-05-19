package com.clancy.aticket;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import android.content.Context;

public class IOHonorFile {
	
	 public static int HowManyPasses(String OptionalLine, Context dan) 
	 {
		 String LineBuffer = "";
		 int ReturnSpaces = 0;
		 String deleteme1 = "";
		 String deleteme2 = "";
		 if (OptionalLine.equals(""))
		 {
			 LineBuffer = SearchData.GetRecordNumberNoLength(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, dan), 1, dan);
		 }
		 else
		 {
			 LineBuffer = SearchData.GetRecordNumberNoLength(OptionalLine, 1, dan);
			 WorkingStorage.StoreCharVal(Defines.HonorFileNameVal, OptionalLine, dan);
		 }
		 if (LineBuffer.equals("ERROR"))
		 {
			 return 0;
		 }
		 //Write the MULTI routine later if anybody uses it.
		 GetDate.GetDateTime(dan);
		 deleteme1 = WorkingStorage.GetCharVal(Defines.SaveDateVal , dan);
		 deleteme2 = LineBuffer.substring(5,11);
		 if (!deleteme1.equals(deleteme2))
		 {
			 return 20;
		 }
		 if(LineBuffer.substring(15, 19).equals("    "))
			 ReturnSpaces = 0;
		 else if(LineBuffer.substring(23, 27).equals("    "))
			 ReturnSpaces = 1;
		 else if(LineBuffer.substring(31, 35).equals("    "))
			 ReturnSpaces = 2;
		 else if(LineBuffer.substring(39, 43).equals("    "))
			 ReturnSpaces = 3;
		 else if(LineBuffer.substring(47, 51).equals("    "))
			 ReturnSpaces = 4;
		 else if(LineBuffer.substring(55, 59).equals("    "))
			 ReturnSpaces = 5;
		 else if(LineBuffer.substring(63, 67).equals("    "))
			 ReturnSpaces = 6;
		 else if(LineBuffer.substring(71, 75).equals("    "))
			 ReturnSpaces = 7;
		 else if(LineBuffer.substring(79, 83).equals("    "))
			 ReturnSpaces = 8;
		 else if(LineBuffer.substring(87, 91).equals("    "))
			 ReturnSpaces = 9;
		 else if(LineBuffer.substring(95, 99).equals("    "))
			 ReturnSpaces = 10;
		 else if(LineBuffer.substring(103, 107).equals("    "))
			 ReturnSpaces = 11;
		 else if(LineBuffer.substring(111, 115).equals("    "))
			 ReturnSpaces = 12;
		 else if(LineBuffer.substring(119, 123).equals("    "))
			 ReturnSpaces = 13;
		 else if(LineBuffer.substring(127, 131).equals("    "))
			 ReturnSpaces = 14;
		 else if(LineBuffer.substring(135, 139).equals("    "))
			 ReturnSpaces = 15;
		 else if(LineBuffer.substring(143, 147).equals("    "))
			 ReturnSpaces = 16;
		 else if(LineBuffer.substring(151, 155).equals("    "))
			 ReturnSpaces = 17;
		 else if(LineBuffer.substring(159, 163).equals("    "))
			 ReturnSpaces = 18;
		 else if(LineBuffer.substring(167, 171).equals("    "))
			 ReturnSpaces = 19;
		 else 
			 ReturnSpaces = 20;
		 
		 return ReturnSpaces;
	 }
	 
	 public static int ExpandHonorList(String WhatToWrite, Context dan) 
	 {
	        int NumSpaces  = 0;
	        int NumStartSpace  = 0;
	        String ChrSpaces  = "";
	        String ChrStartSpace  = "";
	        String WriteBuffer  = "";
	        String Tempi = "";
	        int f  = 0;
	        String LotFileName  = "";
	        
	        LotFileName = WorkingStorage.GetCharVal(Defines.HonorFileNameVal, dan);
	        
	        NumSpaces = Integer.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBoxSpacesVal,dan).trim());
	        WorkingStorage.StoreLongVal(Defines.NumberOfSpacesVal, NumSpaces, dan);
	        
	        NumStartSpace = Integer.valueOf(WorkingStorage.GetCharVal(Defines.SaveHBoxEBirdTimeVal, dan).trim());
	        if (NumStartSpace < 1)
	        {
	        	NumStartSpace = 1;
	        }
	        WorkingStorage.StoreLongVal(Defines.NumStartSpaceVal, NumStartSpace, dan);
	        
	        if (SystemIOFile.exists(LotFileName)) //'file already on card, no need to re-create it
	        {
	        	return 0; 
	        }
	        GetDate.GetDateTime(dan);
	        
	        for (int i = NumStartSpace; i <= (NumSpaces + NumStartSpace - 1); i++) 
	        {
	        	if (f==0)
	        	{
	        		WriteBuffer = WhatToWrite;
	        		while (WriteBuffer.length() < 5)
	                	WriteBuffer += " ";
	        		WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveDateVal,dan);
	        		WriteBuffer = WriteBuffer + "0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000\r";
	        		f = 1;	 
	        		SaveToFile(LotFileName, WriteBuffer, dan);
	        	}
	        	WriteBuffer = WhatToWrite;
        		while (WriteBuffer.length() < 5)
                	WriteBuffer += " ";
        		WriteBuffer = WriteBuffer + WorkingStorage.GetCharVal(Defines.SaveDateVal,dan);
        		Tempi = Integer.toString(i);
        		while (Tempi.length() < 4)
        			Tempi += " ";
        		WriteBuffer = WriteBuffer + Tempi;
        		WriteBuffer = WriteBuffer + "    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000    0000\r";
        		SaveToFile(LotFileName, WriteBuffer, dan);
	        }
	        return 0;
	 }
	 
	 public static Boolean SaveToFile(String FileName, String WriteThis, Context dan)
	 {
		 Boolean CreatedOK = false;      	          
	        try 
	        {
	             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/"+FileName, true)); //true will cause the file to be created if not there or just append if it is
	             out.write(WriteThis);
	             out.newLine();
	             out.flush();
	             out.close();
	             CreatedOK = true;
	        } 
	        catch (IOException e) 
	        {
	        	Messagebox.Message("File Creation Exception Occured: " + e, dan);     
	        }		 
		 
		 return CreatedOK;
	 }
	 
	 public static Boolean WriteVirtualFile(String cFileName, String cLineBuffer, int StartPos, Context dan)
	 {
		 Boolean CreatedOK = false;
		 
	     if (cFileName.equals(""))
	     {	    	 
	    	return false; 
	     }
	     if (cLineBuffer.equals(""))
	     {	    	 
	    	return false; 
	     }	     
	        try 
	        {
	        	RandomAccessFile aFile = new RandomAccessFile("/data/data/com.clancy.aticket/files/"+cFileName, "rw");
	        	FileChannel inChannel = aFile.getChannel();
	        	inChannel.position(StartPos);

	        	ByteBuffer buf = ByteBuffer.allocate(cLineBuffer.length());
	        	buf.clear();
	        	buf.put(cLineBuffer.getBytes());
	        	buf.flip();

	        	while(buf.hasRemaining()) {
	        		inChannel.write(buf);
	        	}       	
	        	inChannel.close();    
	            CreatedOK = true;
	        } 
	        catch (IOException e) 
	        {
	        	Messagebox.Message("File Creation Exception Occured: " + e, dan);     
	        }		 
		 
		 return CreatedOK;
	 }

}
