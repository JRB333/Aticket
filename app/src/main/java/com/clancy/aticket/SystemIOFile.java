package com.clancy.aticket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.Context;


public class SystemIOFile {

	  static public void Delete(String filename) 
	  {
          File f;
          f=new File("/data/data/com.clancy.aticket/files/",filename);
          if(f.exists())
          {
          	f.delete();
          }
          f = null;
	  }
	  static public Boolean exists(String filename) 
	  {
          Boolean isFileFound = false;
		  File f;
          f=new File("/data/data/com.clancy.aticket/files/",filename);
          if(f.exists())
          {
          	isFileFound = true;
          }
          f = null;
          return isFileFound;
	  }
	  
	  public static void copyFile(String sourceFile, String destFile) throws IOException 
	  {
		  File fSource, fDest;
          fSource=new File(sourceFile);
          fDest=new File(destFile);
		  
		    if(!fDest.exists()) {
		    	fDest.createNewFile();
		    }

		    FileChannel source = null;
		    FileChannel destination = null;

		    try {
		        source = new FileInputStream(sourceFile).getChannel();
		        destination = new FileOutputStream(destFile).getChannel();
		        destination.transferFrom(source, 0, source.size());
		    }
		    finally {
		        if(source != null) {
		            source.close();
		        }
		        if(destination != null) {
		            destination.close();
		        }
		    }
		}

		public static void SavePrintLog(String cWriteLine,Context dan)
		{
			if (cWriteLine.trim().equals(""))
			{
				return ; //Simply return since we don't want to write a blank record			
			}
			String WriteBuffer = cWriteLine;
	        try 
	        {
	             BufferedWriter out = new BufferedWriter(new FileWriter("/data/data/com.clancy.aticket/files/PRIN.R", true)); //true will cause the file to be created if not there or just append if it is
	             out.write(WriteBuffer);
	             out.newLine();
	             out.flush();
	             out.close();
	        } 
	        catch (IOException e) 
	        {
	        	Messagebox.Message("PRIN File Creation Exception Occured: " + e, dan);     
	        }
			
		}


}
