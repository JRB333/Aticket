package com.clancy.aticket;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

public class PrintPictureFast {

	public static void PrintPicturesFast(String PictureName, Context dan, OutputStream outputStream)
	{
    	BitmapFactory.Options opts = new BitmapFactory.Options();
    	opts.inSampleSize = 2;
		Bitmap bm = BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+PictureName, opts);
		Bitmap newbm = getResizedBitmap(bm, 108, 144);
		bm.recycle();
		/*try {        
			FileOutputStream out = new FileOutputStream("/data/data/com.clancy.aticket/files/"+"DAN.JPG");
			newbm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			//newbm.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			out = null;
		} 
		catch (Exception e) 
		{        e.printStackTrace(); } */
		ByteBuffer bb = ByteBuffer.allocate(62208); // we should only need 62,208 bytes because of the ARGB_8888 format = 32-bit image. I only had a RGB 24-bit image on windows mobile, so I will be ignoring the A - alpha.
		newbm.copyPixelsToBuffer(bb);
		newbm.recycle();

		int[] GreyBuffer = new int[15552];
		byte[] ColorBuffer = bb.array();
		int GreyBufferPosition = 0;
		int bbBufferPosition = 0;
		int RGBTotal = 0;
		int PixelColor = 0;

		for (bbBufferPosition = 0; bbBufferPosition < 62208; bbBufferPosition += 4 )
		{
			RGBTotal = (int) ColorBuffer[bbBufferPosition]; //only look at the first of four bytes all rgb bytes will be the same after converting image to grayscale
				
			if (RGBTotal >= -128 && RGBTotal <= -113)
				PixelColor = 8;
			else if (RGBTotal > -113 && RGBTotal <= -98 )
				PixelColor = 7;
			else if (RGBTotal > -98 && RGBTotal <= -83)
				PixelColor = 6;
			else if (RGBTotal > -83 && RGBTotal <= -68)
				PixelColor = 5;
			else if (RGBTotal > -68 && RGBTotal <= -53)
				PixelColor = 4;
			else if (RGBTotal > -53 && RGBTotal <= -38 )
				PixelColor = 3;
			else if (RGBTotal > -38 && RGBTotal <= -23)
				PixelColor = 2;
			else if (RGBTotal > -23 && RGBTotal <= -1)
				PixelColor = 1;
			else if (RGBTotal > -1 && RGBTotal <= 7)
				PixelColor = 16;
			else if (RGBTotal > 7 && RGBTotal <= 22)
				PixelColor = 16;
			else if (RGBTotal > 22 && RGBTotal <= 37)
				PixelColor = 15;
			else if (RGBTotal > 37 && RGBTotal <= 52)
				PixelColor = 14;
			else if (RGBTotal > 52 && RGBTotal <= 67)
				PixelColor = 13;
			else if (RGBTotal > 67 && RGBTotal <= 82)
				PixelColor = 12;
			else if (RGBTotal > 82 && RGBTotal <= 97)
				PixelColor = 11;
			else if (RGBTotal > 97 && RGBTotal <= 112)
				PixelColor = 10;
			else if (RGBTotal > 112 && RGBTotal <= 127)
				PixelColor = 9;

			GreyBuffer[GreyBufferPosition] = PixelColor;
			GreyBufferPosition ++;		
		}	
		
		try {
			outputStream.write(0x1B);
			outputStream.write(0x41);
			outputStream.write(0x0); // 7-8 N-DOT LINE SPACING
			
			outputStream.write(0x1B);
			outputStream.write(0x20);
			outputStream.write(0x04); // CHAR SPACING 7-9
			   
			outputStream.write(0x12);
			outputStream.write(0x70);
			outputStream.write(0x0); // 7-59 SET PAPER EMPTY OFF		
			
			ReadLayout.FeedPaperDots2(Integer.valueOf(WorkingStorage.GetCharVal(Defines.PictureCitationDotsVal,dan)), outputStream);
			if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, dan).trim().equals(Defines.ReprintMenu))
			{
				ReadLayout.PrintThis("           ." + WorkingStorage.GetCharVal(Defines.ReprintCitationVal,dan), outputStream);			}
			else
			{
				ReadLayout.PrintThis("            " + WorkingStorage.GetCharVal(Defines.PrintCitationVal,dan), outputStream);
			}
			ReadLayout.FeedPaperDots2(50, outputStream);
			
			outputStream.write(0x1B);
			outputStream.write(0x3D);
			outputStream.write(0x0); // 7-58 SET LSB TO RIGHT
			
			outputStream.write(0x1B);
			outputStream.write(0x56);
			outputStream.write(0xB0);
			outputStream.write(0x1); // 7-38 108*4 = 177+255
			// print picture routine here
	        byte[] OneFortyFourChars = new byte[144];
	        int x = 0;
	        byte[] DanBuffer = new byte[62208];
	        int DanBufferPos = 0;
	        int OneFortyFourCounter = 0;
	        for (x=0; x <= 15551; x++) //5760 = 144*108
	        {
	        	OneFortyFourChars[OneFortyFourCounter] = (byte) GreyBuffer[x];
	        	OneFortyFourCounter++;
	        	if (OneFortyFourCounter == 144)
	        	{
	        		int v = 0;
	        		for (v=1; v<=4; v++)
	        		{
    					int b = 0;
                        int HalfByte = 0;
                        int ByteBuffer = 0;
	        			switch (v) 
	        			{
	        				case 1: 
	        					b = 0;
	                            HalfByte = 0;
	                            ByteBuffer = 0;
	                            for (b=0; b<=143; b++)
	                            {
	                            	switch (OneFortyFourChars[b])
	                            	{
	                            		case 0:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;
	                            			
	                            		case 1:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;

	                            		case 2:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;
	                            			
	                            		case 3:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 16;
	                            			else
	                            				ByteBuffer += 1;	
	                            			break;
	                            			
	                            		case 4:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 16;
	                            			else
	                            				ByteBuffer += 1;
	                            			break;
	                            			
	                            		case 5:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 6:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 7:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 8:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 9:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 208;
	                            			else
	                            				ByteBuffer += 13;
	                            			break;
	                            			
	                            		case 10:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 208;
	                            			else
	                            				ByteBuffer += 13;
	                            			break;
	                            			
	                            		case 11:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 208;
	                            			else
	                            				ByteBuffer += 13;
	                            			break;
	                            			
	                            		case 12:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 208;
	                            			else
	                            				ByteBuffer += 13;
	                            			break;
	                            			
	                            		case 13:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		case 14:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		case 15:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		case 16:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		default:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;	                            			
	                            	}
	                            	if (HalfByte == 1)
	                            	{
	                            		//outputStream.write((byte) ByteBuffer); //!!! uncomment this after lunch
	                            		//outputStream.write(ByteBuffer); //!!! uncomment this after lunch
	                            		DanBuffer[DanBufferPos] = (byte) ByteBuffer;
	                            		HalfByte = 0;
	                            		ByteBuffer = 0;
	                            		DanBufferPos ++;
	                            	}
	                            	else
	                            	{
	                            		HalfByte ++;
	                            	}
	                            }// end for (b=0; b<=143; b++)
	                            break;
	                            // end case v = 1
	        				case 2: 
	        					b = 0;
	                            HalfByte = 0;
	                            ByteBuffer = 0;
	                            for (b=0; b<=143; b++)
	                            {
	                            	switch (OneFortyFourChars[b])
	                            	{
	                            		case 0:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;
	                            			
	                            		case 1:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 64;
	                            			else
	                            				ByteBuffer += 4;	
	                            			break;
	                            			
	                            		case 2:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 64;
	                            			else
	                            				ByteBuffer += 4;
	                            			break;
	                            			
	                            		case 3:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 64;
	                            			else
	                            				ByteBuffer += 4;	 
	                            			break;
	                            			
	                            		case 4:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 64;
	                            			else
	                            				ByteBuffer += 4;
	                            			break;
	                            			
	                            		case 5:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 64;
	                            			else
	                            				ByteBuffer += 4;
	                            			break;
	                            			
	                            		case 6:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 64;
	                            			else
	                            				ByteBuffer += 4;
	                            			break;
	                            			
	                            		case 7:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 96;
	                            			else
	                            				ByteBuffer += 6;
	                            			break;
	                            			
	                            		case 8:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 96;
	                            			else
	                            				ByteBuffer += 6;
	                            			break;
	                            			
	                            		case 9:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 96;
	                            			else
	                            				ByteBuffer += 6;
	                            			break;
	                            			
	                            		case 10:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 112;
	                            			else
	                            				ByteBuffer += 7;
	                            			break;
	                            			
	                            		case 11:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 112;
	                            			else
	                            				ByteBuffer += 7;
	                            			break;
	                            			
	                            		case 12:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 112;
	                            			else
	                            				ByteBuffer += 7;
	                            			break;
	                            			
	                            		case 13:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 112;
	                            			else
	                            				ByteBuffer += 7;
	                            			break;
	                            			
	                            		case 14:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 112;
	                            			else
	                            				ByteBuffer += 7;
	                            			break;
	                            			
	                            		case 15:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 112;
	                            			else
	                            				ByteBuffer += 7;
	                            			break;
	                            			
	                            		case 16:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		default:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;	                            			
	                            	}
	                            	if (HalfByte == 1)
	                            	{
	                            		//SendString[SendStringPos]=(byte) ByteBuffer;
	                            		//outputStream.write((byte) ByteBuffer); //!!! uncomment this after lunch
	                            		//outputStream.write(ByteBuffer); //!!! uncomment this after lunch
	                            		DanBuffer[DanBufferPos] = (byte) ByteBuffer;
	                            		HalfByte = 0;
	                            		ByteBuffer = 0;
	                            		DanBufferPos ++;
	                            	}
	                            	else
	                            	{
	                            		HalfByte ++;
	                            	}
	                            }// end for (b=0; b<=143; b++)
	                            break;
	                            // end case v = 2
	        				case 3: 
	        					b = 0;
	                            HalfByte = 0;
	                            ByteBuffer = 0;
	                            for (b=0; b<=143; b++)
	                            {
	                            	switch (OneFortyFourChars[b])
	                            	{
	                            		case 0:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;
	                            			
	                            		case 1:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;	
	                            			break;
	                            			
	                            		case 2:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;
	                            			
	                            		case 3:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;	 
	                            			break;
	                            			
	                            		case 4:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;
	                            			
	                            		case 5:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;
	                            			
	                            		case 6:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 32;
	                            			else
	                            				ByteBuffer += 2;
	                            			break;
	                            			
	                            		case 7:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 32;
	                            			else
	                            				ByteBuffer += 2;
	                            			break;
	                            			
	                            		case 8:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 96;
	                            			else
	                            				ByteBuffer += 6;
	                            			break;
	                            			
	                            		case 9:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 96;
	                            			else
	                            				ByteBuffer += 6;
	                            			break;
	                            			
	                            		case 10:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 96;
	                            			else
	                            				ByteBuffer += 6;
	                            			break;
	                            			
	                            		case 11:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 96;
	                            			else
	                            				ByteBuffer += 6;
	                            			break;
	                            			
	                            		case 12:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 224;
	                            			else
	                            				ByteBuffer += 14;
	                            			break;
	                            			
	                            		case 13:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 224;
	                            			else
	                            				ByteBuffer += 14;
	                            			break;
	                            			
	                            		case 14:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		case 15:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		case 16:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		default:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;	                            			
	                            	}
	                            	if (HalfByte == 1)
	                            	{
	                            		//SendString[SendStringPos]=(byte) ByteBuffer;
	                            		//outputStream.write((byte) ByteBuffer); //!!! uncomment this after lunch
	                            		//outputStream.write(ByteBuffer); //!!! uncomment this after lunch
	                            		DanBuffer[DanBufferPos] = (byte) ByteBuffer;
	                            		HalfByte = 0;
	                            		ByteBuffer = 0;
	                            		DanBufferPos ++;
		                            	}
	                            	else
	                            	{
	                            		HalfByte ++;
	                            	}
	                            }// end for (b=0; b<=143; b++)
	                            break;
	                            // end case v = 3
	        				case 4: 
	        					b = 0;
	                            HalfByte = 0;
	                            ByteBuffer = 0;
	                            for (b=0; b<=143; b++)
	                            {
	                            	switch (OneFortyFourChars[b])
	                            	{
	                            		case 0:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;
	                            			
	                            		case 1:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;	
	                            			break;
	                            			
	                            		case 2:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 0;
	                            			else
	                            				ByteBuffer += 0;
	                            			break;
	                            			
	                            		case 3:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 16;
	                            			else
	                            				ByteBuffer += 1;	 
	                            			break;
	                            			
	                            		case 4:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 5:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 6:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 7:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 8:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 9:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 10:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 144;
	                            			else
	                            				ByteBuffer += 9;
	                            			break;
	                            			
	                            		case 11:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 176;
	                            			else
	                            				ByteBuffer += 11;
	                            			break;
	                            			
	                            		case 12:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 176;
	                            			else
	                            				ByteBuffer += 11;
	                            			break;
	                            			
	                            		case 13:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 176;
	                            			else
	                            				ByteBuffer += 11;
	                            			break;
	                            			
	                            		case 14:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 176;
	                            			else
	                            				ByteBuffer += 11;
	                            			break;
	                            			
	                            		case 15:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		case 16:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;
	                            			break;
	                            			
	                            		default:
	                            			if (HalfByte == 1 )
	                            				ByteBuffer += 240;
	                            			else
	                            				ByteBuffer += 15;	                            			
	                            	}
	                            	if (HalfByte == 1)
	                            	{
	                            		//SendString[SendStringPos]=(byte) ByteBuffer;
	                            		//outputStream.write((byte) ByteBuffer); //!!! uncomment this after lunch
	                            		//outputStream.write(ByteBuffer); //!!! uncomment this after lunch
	                            		DanBuffer[DanBufferPos] = (byte) ByteBuffer;
	                            		DanBufferPos ++;
	                            		HalfByte = 0;
	                            		ByteBuffer = 0;
	                            	}
	                            	else
	                            	{
	                            		HalfByte ++;
	                            	}
	                            }// end for (b=0; b<=143; b++)
	                            break;
	                            // end case v = 4
	                            
	        			}// end switch (v)
	        		} // end for (v=1; v<=4; v++)
        			OneFortyFourCounter = 0;
        			OneFortyFourChars = new byte[144];
	        	}// end if OneFortyFourCounter == 144	        	
	        }
	        outputStream.write(DanBuffer, 0, DanBufferPos);
	        DanBufferPos ++;
	        outputStream.write(0x1B);
			outputStream.write(0x56);
			outputStream.write(0x0);
			outputStream.write(0x0); // 7-38 108*4 = 177+255
	        // end print picture routine
			
			ReadLayout.FeedPaperDots2(20, outputStream);
	        ReadLayout.PrintThisTrimmed("The actual picture stored with", outputStream);
	        ReadLayout.FeedPaperDots2(8, outputStream);
	        ReadLayout.PrintThisTrimmed("this citation is full color", outputStream);
	        ReadLayout.FeedPaperDots2(8, outputStream);
	        ReadLayout.PrintThisTrimmed("and high resolution.", outputStream);
	        ReadLayout.FeedPaperDots2(20, outputStream);
	        
	        if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("MEDSTAR"))
	        {
	        	ReadLayout.FeedPaperDots2(200, outputStream);
	        }
	        
			outputStream.write(0x12);
			outputStream.write(0x6D);
			outputStream.write(0x1); 
			outputStream.write(0x3C);
			outputStream.write(0x6);// Page 7 - 59 Mark Position Detect
			ReadLayout.FeedPaperDots2(40, outputStream);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) 
	{
		 int width = bm.getWidth();
		 int height = bm.getHeight();
		 float scaleWidth = ((float) newWidth) / width;
		 float scaleHeight = ((float) newHeight) / height;
		 Matrix matrix = new Matrix();
         matrix.postScale(scaleWidth, scaleHeight);
		 Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		 Bitmap blahblee = resizedBitmap.copy(Config.ARGB_8888, true);
		 Bitmap blahblahbleeblee = toGrayscale(blahblee);
		 blahblee.recycle();
		 resizedBitmap.recycle();
		 return blahblahbleeblee;		
	}
	
	public static Bitmap toGrayscale(Bitmap bmpOriginal)
	{        
	    int width, height;
	    height = bmpOriginal.getHeight();
	    width = bmpOriginal.getWidth();    

	    Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    Canvas c = new Canvas(bmpGrayscale);
	    Paint paint = new Paint();
	    ColorMatrix cm = new ColorMatrix();
	    cm.setSaturation(0);
	    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
	    paint.setColorFilter(f);
	    c.drawBitmap(bmpOriginal, 0, 0, paint);
	    return bmpGrayscale;
	}
	
}
