package com.clancy.aticket;



public class GarbleyGoop {

	public static String DansRoutine(String EncryptedString) // wrote this for my personal apps pub ids, will work great for credentials as well 
	  {		  
		  // the source to generate the Encrypted String is in v:\foxpro5\androidcredentials.prg
		  int basenumber = 9881;
		  int squareroot = (int) Math.sqrt(basenumber);
		  int i = 0;
		  for (i=10; i<=squareroot; i++)
		  {
			  if (basenumber % i == 0) // We have a our key(i) which will be 41 by the way, but proguard won't show the comments!
			  {
				  break;
			  }
		  }
		  String eStr = "";
		  StringBuilder sb = new StringBuilder();
		  for (int z = 0; z < EncryptedString.length(); z++)
		  {
			  eStr += EncryptedString.substring(z,z+1);
			  int blah = Integer.valueOf(eStr);
			  if (blah % i == 0) // we have our value let's convert it to ascii!
			  {
				  if (blah/i > 32) // number or letter
				  {
					  sb.append((char)(blah/i));
					  eStr = "";
				  }
			  }
		  }
		  
		  String ReturnKey = sb.toString();
		  return ReturnKey;
	  }

}
