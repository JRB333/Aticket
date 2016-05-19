package com.clancy.aticket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
import java.io.OutputStreamWriter;

//import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
//import android.content.ContextWrapper;


public class MiscUnUsed extends ActivityGroup {
    private static final String TAG = "Main";   
	static public void MakeDirectory() {
		
		
	      //File folder = new File(Environment.getRootDirectory() + "Clancy2");         
		//File folder = new File("/Clancy2/");
		  File folder=new File(Environment.getRootDirectory(),"files");
	      boolean success = false;         
	      if(!folder.exists())
	      {             
	    	  success = folder.mkdirs();         
	      }         
	      if (!success)
	      {              
	    	  Log.d(TAG,"Folder not created.");         
	      }         
	      else
	      {             
	    	  Log.d(TAG,"Folder created!"); 
	      } 
	};

	
	 public void MakeFile(Context dan) {
		try {
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput("dan.txt",0));
			out.write("dan the man");
			out.close();
		}  
		catch(Throwable t) { 
			Toast
			.makeText(dan, "Ex: "+t.toString(), 2000)
			.show();
		}      

		
   		try {
   			FileInputStream in = openFileInput("CUSTOM.A");
			if (in!=null)
			{
				InputStreamReader tmp = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(tmp);
				String str;
				StringBuffer buf= new StringBuffer();
				while ((str = reader.readLine())!= null)
				{
					buf.append(str+"\n");
				}
				in.close();
	  			Toast
    			.makeText(getApplicationContext(), buf.toString(), 2000)
    			.show();
			}

		}  
		catch(Throwable t) { 
			Toast
			.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
			.show();
		}   
	}
	 
	/*	try {
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput("dan.txt",0));
			out.write("dan the man");
			out.close();
		}  
		catch(Throwable t) { 
			Toast
			.makeText(getApplicationContext(), "Ex: "+t.toString(), 2000)
			.show();
		} 	*/
/*		File root = new File(Environment.getDataDirectory(), "matches"); 
		if (!root.exists()) { 
			root.mkdirs(); 
		} 
		if (root.canWrite()) 
		{ 
			File f = new File(root, "dan.txt"); 
			FileWriter writer = null; 
			try { 
				writer = new FileWriter(f); 
				writer.write("Some content"); 
				writer.close();   
		} catch (IOException e) { 
		e.printStackTrace();   
  
		} 
		}
	};*/
}
