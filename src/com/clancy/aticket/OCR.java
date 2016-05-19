package com.clancy.aticket;

import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;

public class OCR {
	
	public static final byte[] VerticalOCRBZero = new byte[] {
		(byte) 0x07, (byte) 0xFF, (byte) 0x80, 
		(byte) 0x3F, (byte) 0xFF, (byte) 0xF0, 
		(byte) 0x7F, (byte) 0xFF, (byte) 0xFC, 
		(byte) 0x78, (byte) 0x00, (byte) 0x3C, 
		(byte) 0xE0, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x00, (byte) 0x0E, 								
		(byte) 0x78, (byte) 0x00, (byte) 0x3C, 
		(byte) 0x7F, (byte) 0xFF, (byte) 0xFC, 
		(byte) 0x3F, (byte) 0xFF, (byte) 0xF0, 
		(byte) 0x07, (byte) 0xFF, (byte) 0x80, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
		}; //
	
	public static final byte[] VerticalOCRBOne = new byte[] {
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x01, (byte) 0xE0, 
		(byte) 0x00, (byte) 0x00, (byte) 0xF0, 
		(byte) 0x00, (byte) 0x00, (byte) 0x78, 
		(byte) 0x00, (byte) 0x00, (byte) 0x38, 
		(byte) 0x00, (byte) 0x00, (byte) 0x3C, 
		(byte) 0x00, (byte) 0x00, (byte) 0x1E, 
		(byte) 0xFF, (byte) 0xFF, (byte) 0xFE, 
		(byte) 0xFF, (byte) 0xFF, (byte) 0xFE, 
		(byte) 0xFF, (byte) 0xFF, (byte) 0xFE, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
		}; //
	
	public static final byte[] VerticalOCRBTwo = new byte[] {	
		(byte) 0xFC, (byte) 0x00, (byte) 0x38, 
		(byte) 0xFF, (byte) 0x00, (byte) 0x1C, 
		(byte) 0xFF, (byte) 0x80, (byte) 0x1C, 
		(byte) 0xCF, (byte) 0xC0, (byte) 0x0E, 	
		(byte) 0xE1, (byte) 0xE0, (byte) 0x0E, 
		(byte) 0xE3, (byte) 0xF0, (byte) 0x0E, 								
		(byte) 0xE0, (byte) 0xF0, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0xE8, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x3C, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x3E, (byte) 0x1E, 								
		(byte) 0xE0, (byte) 0x1F, (byte) 0xFC, 								
		(byte) 0xE0, (byte) 0x0F, (byte) 0xFC, 
		(byte) 0xE0, (byte) 0x07, (byte) 0xF8, 
		(byte) 0xE0, (byte) 0x01, (byte) 0xE0,  								
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static final byte[] VerticalOCRBThree = new byte[] {	
		(byte) 0x70, (byte) 0x00, (byte) 0x0E, 
		(byte) 0x70, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x0E, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x0F, (byte) 0x0E, 						
		(byte) 0xE0, (byte) 0x0F, (byte) 0x8E, 
		(byte) 0xE0, (byte) 0x0F, (byte) 0xCE, 
		(byte) 0xE0, (byte) 0x1D, (byte) 0xEE, 
		(byte) 0xF0, (byte) 0x1C, (byte) 0xFE, 								
		(byte) 0x78, (byte) 0x3C, (byte) 0x7E, 								
		(byte) 0x7F, (byte) 0xF8, (byte) 0x3E, 
		(byte) 0x3F, (byte) 0xF0, (byte) 0x1E, 
		(byte) 0x1F, (byte) 0xE0, (byte) 0x0E,  								
		(byte) 0x03, (byte) 0x80, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static final byte[] VerticalOCRBFour = new byte[] {	
		(byte) 0x07, (byte) 0x80, (byte) 0x00, 
		(byte) 0x07, (byte) 0xE0, (byte) 0x00, 
		(byte) 0x07, (byte) 0xF8, (byte) 0x00, 
		(byte) 0x07, (byte) 0x7E, (byte) 0x00, 	
		(byte) 0x07, (byte) 0x1F, (byte) 0xC0, 
		(byte) 0x07, (byte) 0x07, (byte) 0xF0, 								
		(byte) 0x07, (byte) 0x01, (byte) 0xFC, 
		(byte) 0x07, (byte) 0x00, (byte) 0x7E, 	
		(byte) 0x07, (byte) 0x00, (byte) 0x1E, 
		(byte) 0xFF, (byte) 0xF8, (byte) 0x06, 								
		(byte) 0xFF, (byte) 0xF8, (byte) 0x00, 								
		(byte) 0xFF, (byte) 0xF8, (byte) 0x00, 
		(byte) 0x07, (byte) 0x00, (byte) 0x00, 
		(byte) 0x07, (byte) 0x00, (byte) 0x00,  								
		(byte) 0x07, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static final byte[] VerticalOCRBFive = new byte[] {	
		(byte) 0xE0, (byte) 0x07, (byte) 0x80, 
		(byte) 0xE0, (byte) 0x07, (byte) 0xFE, 
		(byte) 0xE0, (byte) 0x07, (byte) 0xFE, 
		(byte) 0xE0, (byte) 0x07, (byte) 0x7E, 	
		(byte) 0xE0, (byte) 0x07, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x07, (byte) 0x0E, 								
		(byte) 0x70, (byte) 0x07, (byte) 0x0E, 
		(byte) 0x70, (byte) 0x0E, (byte) 0x0E, 
		(byte) 0x38, (byte) 0x1E, (byte) 0x0E, 
		(byte) 0x3E, (byte) 0x3C, (byte) 0x0E, 								
		(byte) 0x1F, (byte) 0xFC, (byte) 0x0E, 								
		(byte) 0x0F, (byte) 0xF8, (byte) 0x0E, 
		(byte) 0x03, (byte) 0xE0, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00,  								
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static final byte[] VerticalOCRBSix = new byte[] {	
		(byte) 0x0F, (byte) 0xF0, (byte) 0x00, 
		(byte) 0x1F, (byte) 0xFC, (byte) 0x00, 
		(byte) 0x3F, (byte) 0xFF, (byte) 0x00, 
		(byte) 0x7C, (byte) 0x7F, (byte) 0x80, 	
		(byte) 0x70, (byte) 0x1F, (byte) 0xC0, 
		(byte) 0xE0, (byte) 0x0F, (byte) 0xE0, 								
		(byte) 0xE0, (byte) 0x0E, (byte) 0xF8, 
		(byte) 0xE0, (byte) 0x0E, (byte) 0x7C, 
		(byte) 0xE0, (byte) 0x0E, (byte) 0x3E, 
		(byte) 0xE0, (byte) 0x0E, (byte) 0x0E, 								
		(byte) 0x70, (byte) 0x1C, (byte) 0x06, 								
		(byte) 0x7C, (byte) 0x7C, (byte) 0x02, 
		(byte) 0x3F, (byte) 0xF8, (byte) 0x00, 
		(byte) 0x1F, (byte) 0xF0, (byte) 0x00,  								
		(byte) 0x07, (byte) 0xC0, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static final byte[] VerticalOCRBSeven = new byte[] {	
		(byte) 0x00, (byte) 0x00, (byte) 0x0E, 
		(byte) 0x00, (byte) 0x00, (byte) 0x0E, 
		(byte) 0x00, (byte) 0x00, (byte) 0x0E, 
		(byte) 0xFE, (byte) 0x00, (byte) 0x0E, 	
		(byte) 0xFF, (byte) 0xE0, (byte) 0x0E, 
		(byte) 0xFF, (byte) 0xF0, (byte) 0x0E, 								
		(byte) 0x01, (byte) 0xFC, (byte) 0x0E, 
		(byte) 0x00, (byte) 0x3E, (byte) 0x0E, 
		(byte) 0x00, (byte) 0x0F, (byte) 0x8E, 
		(byte) 0x00, (byte) 0x07, (byte) 0xCE, 								
		(byte) 0x00, (byte) 0x03, (byte) 0xEE, 								
		(byte) 0x00, (byte) 0x01, (byte) 0xFE, 
		(byte) 0x00, (byte) 0x00, (byte) 0x7E, 
		(byte) 0x00, (byte) 0x00, (byte) 0x3E,  								
		(byte) 0x00, (byte) 0x00, (byte) 0x0E, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static final byte[] VerticalOCRBEight = new byte[] {	
		(byte) 0x0F, (byte) 0x80, (byte) 0x00, 
		(byte) 0x3F, (byte) 0xC0, (byte) 0xF0, 
		(byte) 0x7F, (byte) 0xE1, (byte) 0xF8, 
		(byte) 0x78, (byte) 0xF3, (byte) 0xFC, 	
		(byte) 0xF0, (byte) 0x7F, (byte) 0x1C, 
		(byte) 0xE0, (byte) 0x3E, (byte) 0x0E, 								
		(byte) 0xE0, (byte) 0x1E, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x1C, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x1E, (byte) 0x0E, 
		(byte) 0xE0, (byte) 0x3E, (byte) 0x0E, 								
		(byte) 0xF0, (byte) 0x7F, (byte) 0x1E, 								
		(byte) 0x78, (byte) 0xF3, (byte) 0xFC, 
		(byte) 0x7F, (byte) 0xE3, (byte) 0xF8, 
		(byte) 0x3F, (byte) 0xC0, (byte) 0xF0,  								
		(byte) 0x1F, (byte) 0x80, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static final byte[] VerticalOCRBNine = new byte[] {	
		(byte) 0x00, (byte) 0x0F, (byte) 0xC0, 
		(byte) 0x00, (byte) 0x1F, (byte) 0xF0, 
		(byte) 0x00, (byte) 0x3F, (byte) 0xF8, 
		(byte) 0x80, (byte) 0x7C, (byte) 0x7C, 	
		(byte) 0xC0, (byte) 0x70, (byte) 0x1C, 
		(byte) 0xE0, (byte) 0xE0, (byte) 0x0E, 								
		(byte) 0xF8, (byte) 0xE0, (byte) 0x0E, 
		(byte) 0x7C, (byte) 0xE0, (byte) 0x0E, 
		(byte) 0x3E, (byte) 0xE0, (byte) 0x0E, 
		(byte) 0x0F, (byte) 0xE0, (byte) 0x0E, 								
		(byte) 0x07, (byte) 0xF0, (byte) 0x1C, 								
		(byte) 0x03, (byte) 0xF8, (byte) 0x3C, 
		(byte) 0x00, (byte) 0xFF, (byte) 0xF8, 
		(byte) 0x00, (byte) 0x7F, (byte) 0xF0,  								
		(byte) 0x00, (byte) 0x0F, (byte) 0xC0, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};
	public static final byte[] VerticalOCRBX = new byte[] {	
		(byte) 0xC0, (byte) 0x00, (byte) 0x03, 
		(byte) 0xF0, (byte) 0x00, (byte) 0x0F, 
		(byte) 0xFC, (byte) 0x00, (byte) 0x3F, 
		(byte) 0x7F, (byte) 0x00, (byte) 0xFF, 	
		(byte) 0x1F, (byte) 0xC1, (byte) 0xFC, 
		(byte) 0x07, (byte) 0xF7, (byte) 0xF0, 								
		(byte) 0x01, (byte) 0xFF, (byte) 0xC0, 
		(byte) 0x00, (byte) 0x7F, (byte) 0x00, 
		(byte) 0x00, (byte) 0xFF, (byte) 0x80, 
		(byte) 0x03, (byte) 0xFF, (byte) 0xE0, 								
		(byte) 0x0F, (byte) 0xE3, (byte) 0xF0, 								
		(byte) 0x3F, (byte) 0x80, (byte) 0xFC, 
		(byte) 0xFE, (byte) 0x00, (byte) 0x7F, 
		(byte) 0xFC, (byte) 0x00, (byte) 0x1F,  								
		(byte) 0xF0, (byte) 0x00, (byte) 0x07, 
		(byte) 0xC0, (byte) 0x00, (byte) 0x01, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00, 
		(byte) 0x00, (byte) 0x00, (byte) 0x00
	};

	public static final byte[] VerticalOCRAZero = new byte[] {	
		(byte) 0x7F, (byte) 0xFF, (byte) 0xFC,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0x7F, (byte) 0xFF, (byte) 0xFC,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0
	};	
	
	public static final byte[] VerticalOCRAOne = new byte[] {	
		(byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0x0,
	    (byte) 0xE0, (byte) 0x0, (byte) 0x0,
	    (byte) 0xE0, (byte) 0x0, (byte) 0x0,
	    (byte) 0xFF, (byte) 0xC0, (byte) 0x0,
	    (byte) 0xFF, (byte) 0xE0, (byte) 0x0,
	    (byte) 0x7F, (byte) 0xC0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0
	};	
	
	public static final byte[] VerticalOCRATwo = new byte[] {	
		(byte) 0xFF, (byte) 0xF0, (byte) 0xE,
	    (byte) 0xFF, (byte) 0xF8, (byte) 0xE,
	    (byte) 0xFF, (byte) 0xF8, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x3F, (byte) 0xFE,
	    (byte) 0xE0, (byte) 0x1F, (byte) 0xFC,
	    (byte) 0x40, (byte) 0xF, (byte) 0xF8,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0
	};	

	public static final byte[] VerticalOCRAThree = new byte[] {	
		(byte) 0x40, (byte) 0x0, (byte) 0x4,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x10, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xE0, (byte) 0x38, (byte) 0xE,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0x7F, (byte) 0xFF, (byte) 0xFC,
	    (byte) 0x3F, (byte) 0xC7, (byte) 0xF8,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0
	};	
	
	public static final byte[] VerticalOCRAFour = new byte[] {	
		(byte) 0x1, (byte) 0xFF, (byte) 0xFC,
        (byte) 0x1, (byte) 0xFF, (byte) 0xFC,
        (byte) 0x1, (byte) 0xFF, (byte) 0xFC,
        (byte) 0x1, (byte) 0xC0, (byte) 0x0,
        (byte) 0x1, (byte) 0xC0, (byte) 0x0,
        (byte) 0x1, (byte) 0xC0, (byte) 0x0,
        (byte) 0x1, (byte) 0xC0, (byte) 0x0,
        (byte) 0x1, (byte) 0xC0, (byte) 0x0,
        (byte) 0x1, (byte) 0xC0, (byte) 0x0,
        (byte) 0xFF, (byte) 0xFF, (byte) 0xF0,
        (byte) 0xFF, (byte) 0xFF, (byte) 0xF0,
        (byte) 0x7F, (byte) 0xFF, (byte) 0xF0,
        (byte) 0x1, (byte) 0xC0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0
	};	
	
	public static final byte[] VerticalOCRAFive = new byte[] {	
		(byte) 0x30, (byte) 0x0, (byte) 0x0,
        (byte) 0x70, (byte) 0x0, (byte) 0x0,
        (byte) 0x70, (byte) 0x0, (byte) 0x0,
        (byte) 0xE0, (byte) 0x3F, (byte) 0xFE,
        (byte) 0xE0, (byte) 0x3F, (byte) 0xFE,
        (byte) 0xE0, (byte) 0x3F, (byte) 0xFE,
        (byte) 0xE0, (byte) 0x38, (byte) 0xE,
        (byte) 0xE0, (byte) 0x38, (byte) 0xE,
        (byte) 0xE0, (byte) 0x38, (byte) 0xE,
        (byte) 0xE0, (byte) 0x38, (byte) 0xE,
        (byte) 0xE0, (byte) 0x38, (byte) 0xE,
        (byte) 0xE0, (byte) 0x38, (byte) 0xE,
        (byte) 0xFF, (byte) 0xF8, (byte) 0xE,
        (byte) 0x7F, (byte) 0xF0, (byte) 0xE,
        (byte) 0x3F, (byte) 0xE0, (byte) 0x4,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0
	};	
	
	public static final byte[] VerticalOCRASix = new byte[] {	
		(byte) 0x7F, (byte) 0xFF, (byte) 0xFC,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
	    (byte) 0xE0, (byte) 0xE0, (byte) 0xE,
	    (byte) 0xE0, (byte) 0xE0, (byte) 0x4,
	    (byte) 0xE0, (byte) 0xE0, (byte) 0x0,
	    (byte) 0xE0, (byte) 0xE0, (byte) 0x0,
	    (byte) 0xE0, (byte) 0xE0, (byte) 0x0,
	    (byte) 0xE0, (byte) 0xE0, (byte) 0x0,
	    (byte) 0xE0, (byte) 0xE0, (byte) 0x0,
	    (byte) 0xE0, (byte) 0xE0, (byte) 0x0,
	    (byte) 0xE0, (byte) 0xE0, (byte) 0x0,
	    (byte) 0xFF, (byte) 0xE0, (byte) 0x0,
	    (byte) 0xFF, (byte) 0xE0, (byte) 0x0,
	    (byte) 0x7F, (byte) 0xC0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0
	};
	
	public static final byte[] VerticalOCRASeven = new byte[] {	
		(byte) 0x0, (byte) 0x0, (byte) 0x1C,
	    (byte) 0x0, (byte) 0x0, (byte) 0x3E,
	    (byte) 0x0, (byte) 0x0, (byte) 0x1E,
	    (byte) 0x0, (byte) 0x0, (byte) 0xE,
	    (byte) 0x0, (byte) 0x0, (byte) 0xE,
	    (byte) 0x0, (byte) 0x0, (byte) 0xE,
	    (byte) 0xFF, (byte) 0xC0, (byte) 0xE,
	    (byte) 0xFF, (byte) 0xE0, (byte) 0xE,
	    (byte) 0x7F, (byte) 0xF0, (byte) 0xE,
	    (byte) 0x0, (byte) 0xF0, (byte) 0xE,
	    (byte) 0x0, (byte) 0x78, (byte) 0xE,
	    (byte) 0x0, (byte) 0x3C, (byte) 0xE,
	    (byte) 0x0, (byte) 0x1E, (byte) 0xE,
	    (byte) 0x0, (byte) 0xF, (byte) 0xFE,
	    (byte) 0x0, (byte) 0xF, (byte) 0xFE,
	    (byte) 0x0, (byte) 0x7, (byte) 0xFE,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0,
	    (byte) 0x0, (byte) 0x0, (byte) 0x0
	};	

	public static final byte[] VerticalOCRAEight = new byte[] {	
		(byte) 0x7F, (byte) 0xF0, (byte) 0x0,
        (byte) 0xFF, (byte) 0xF8, (byte) 0x0,
        (byte) 0xFF, (byte) 0xF8, (byte) 0x0,
        (byte) 0xE0, (byte) 0x3F, (byte) 0xFC,
        (byte) 0xE0, (byte) 0x3F, (byte) 0xFE,
        (byte) 0xE0, (byte) 0x3F, (byte) 0xFE,
        (byte) 0xE0, (byte) 0x38, (byte) 0xE,
        (byte) 0xE0, (byte) 0x38, (byte) 0xE,
        (byte) 0xE0, (byte) 0x38, (byte) 0xE,
        (byte) 0xE0, (byte) 0x3F, (byte) 0xFE,
        (byte) 0xE0, (byte) 0x3F, (byte) 0xFE,
        (byte) 0xE0, (byte) 0x3F, (byte) 0xFC,
        (byte) 0xFF, (byte) 0xF8, (byte) 0x0,
        (byte) 0xFF, (byte) 0xF8, (byte) 0x0,
        (byte) 0x7F, (byte) 0xF0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0
	};
	
	public static final byte[] VerticalOCRANine = new byte[] {	
		(byte) 0x0, (byte) 0x7, (byte) 0xFC,
        (byte) 0x0, (byte) 0xF, (byte) 0xFE,
        (byte) 0x0, (byte) 0xF, (byte) 0xFE,
        (byte) 0x0, (byte) 0xE, (byte) 0xE,
        (byte) 0x0, (byte) 0xE, (byte) 0xE,
        (byte) 0x0, (byte) 0xE, (byte) 0xE,
        (byte) 0x0, (byte) 0xE, (byte) 0xE,
        (byte) 0x0, (byte) 0xE, (byte) 0xE,
        (byte) 0x0, (byte) 0xE, (byte) 0xE,
        (byte) 0x0, (byte) 0xE, (byte) 0xE,
        (byte) 0x40, (byte) 0xE, (byte) 0xE,
        (byte) 0xE0, (byte) 0xE, (byte) 0xE,
        (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
        (byte) 0xFF, (byte) 0xFF, (byte) 0xFE,
        (byte) 0x7F, (byte) 0xFF, (byte) 0xFC,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0
	};
	
	public static final byte[] VerticalOCRAX = new byte[] {	
		(byte) 0x60, (byte) 0x0, (byte) 0xE,
        (byte) 0xF0, (byte) 0x0, (byte) 0x1E,
        (byte) 0x7C, (byte) 0x0, (byte) 0x7C,
        (byte) 0x3F, (byte) 0x1, (byte) 0xF8,
        (byte) 0xF, (byte) 0x83, (byte) 0xE0,
        (byte) 0x3, (byte) 0xEF, (byte) 0xC0,
        (byte) 0x1, (byte) 0xFF, (byte) 0x0,
        (byte) 0x0, (byte) 0xFE, (byte) 0x0,
        (byte) 0x1, (byte) 0xFF, (byte) 0x0,
        (byte) 0x7, (byte) 0xEF, (byte) 0xC0,
        (byte) 0x1F, (byte) 0x83, (byte) 0xF0,
        (byte) 0x3E, (byte) 0x0, (byte) 0xF8,
        (byte) 0xFC, (byte) 0x0, (byte) 0x3E,
        (byte) 0xF0, (byte) 0x0, (byte) 0x1E,
        (byte) 0x40, (byte) 0x0, (byte) 0x4,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0,
        (byte) 0x0, (byte) 0x0, (byte) 0x0
	};	
	
    public static void PrintTheBarCode(String ParseString, OutputStream outputStream, Context dan)
    {
    	String DistinctChars = "";
    	int z = 0;
    	int p = 0;
    	if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("GRANDRAPIDS"))
    	{
    		String AppendString = "";
    		//String Fine1String = WorkingStorage.GetCharVal(Defines.PrintFine1Val, dan).trim();
    		String Fine1String = WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, dan).trim();
    		if (Fine1String.trim().length()== 4 ) //1.00
    		{
    			AppendString = "00"+Fine1String.substring(0, 1);
    		}else if (Fine1String.trim().length()== 5 ) //10.00
    		{
    			AppendString = "0"+Fine1String.substring(0, 2);
    		}else if (Fine1String.trim().length()== 6 ) //100.00
    		{
    			AppendString = Fine1String.substring(0, 3);
    		}else if (Fine1String.trim().length()== 7 ) //1000.00 added for large fine
    		{
    			AppendString = Fine1String.substring(0, 4);
    		}else if (Fine1String.trim().length()== 8 ) //10000.00 added for large fine
    		{
    			AppendString = Fine1String.substring(0, 5);
    		}
    		
    		ParseString = ParseString+AppendString;   
    		String FirstHalf = ParseString.substring(0, 5);
    		int SummedNumber = Integer.valueOf(FirstHalf);
    		SummedNumber = SummedNumber % 7;
    		String SecondHalf = Integer.toString(SummedNumber);
    		SecondHalf = SecondHalf + ParseString.substring(5, 11);
    		SummedNumber = Integer.valueOf(SecondHalf);
    		SummedNumber = SummedNumber % 7;    		    		
    		ParseString = ParseString+Integer.toString(SummedNumber); 		
    	}
    	if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("RICHMOND"))
    	{
    		ParseString = ParseString.substring(1);
    		int i = 0;
    		int TempNumber = 0;
    		int WeightNumber = 0;
    		for (i=0; i < 7; i++)
    		{
    			TempNumber = Integer.valueOf(ParseString.substring(i,i+1));
    			TempNumber = TempNumber * (7-i);
    			if (TempNumber > 0)
    			{
    				WeightNumber = WeightNumber + TempNumber;
    			}
    		}
    		WeightNumber = WeightNumber % 7;
    		ParseString = ParseString + Integer.toString(WeightNumber);
    		//String Fine1String = WorkingStorage.GetCharVal(Defines.PrintFine1Val, dan).trim();
    		String Fine1String = WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, dan).trim();
    		String AppendString = "";
    		if (Fine1String.trim().length()== 8 ) //10000.00
    		{
    			AppendString = Fine1String.substring(0, 5) + Fine1String.substring(6, 8);
    		}else if (Fine1String.trim().length()== 7 ) //1000.00
    		{
    			AppendString = Fine1String.substring(0, 4) + Fine1String.substring(5, 7);
    		}else if (Fine1String.trim().length()== 6 ) //100.00
    		{
    			AppendString = Fine1String.substring(0, 3) + Fine1String.substring(4, 6);
    		}
    		else if (Fine1String.trim().length()== 5 ) //10.00
    		{
    			AppendString = "0"+ Fine1String.substring(0, 2) + Fine1String.substring(3, 5);
    		}
    		else if (Fine1String.trim().length()== 4 ) //1.00
    		{
    			AppendString = "00"+ Fine1String.substring(0, 1) + Fine1String.substring(2, 4);
    		}
    		else if (Fine1String.trim().length()== 3 ) //.50
    		{
    			AppendString = "000"+ Fine1String.substring(1, 3);
    		}  
    		else 
    		{
    			AppendString = "00000";
    		}     		
    		ParseString = ParseString + AppendString ;
    	}

    	for (p=0; p < ParseString.length(); p++)
		{
			ReadLayout.PrintThis(" ", outputStream);
			if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("GRANDRAPIDS"))
			{
				PrintOCRBCharacter(ParseString.substring(p, p+1), outputStream);				
			}
			if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("RICHMOND"))
			{
				PrintOCRACharacter(ParseString.substring(p, p+1), outputStream);				
			}
			//ReadLayout.FeedPaperDots2(1, outputStream);
			try {
				outputStream.write(0x1B);
				outputStream.write(0x4A);
				outputStream.write(0x0); // FEED DOT LINES 0X0 = 0 DOT LINES like a carriage return LF
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
    }
      
    static void PrintOCRBCharacter(String CharToPrint, OutputStream outputStream)
    {
        
		try {
			//SetTheSerialBuffer2("\x01B\x041\x000",serRefNum,3); //LINE SPACING 7-8
			outputStream.write(0x1B);
			outputStream.write(0x41);
			outputStream.write(0x0);
			//SetTheSerialBuffer2("\x01B\x03D\x001",serRefNum,3); //page  7-58 SET LSB TO RIGHT
			outputStream.write(0x1B);
			outputStream.write(0x3D);
			outputStream.write(0x1);
			//SetTheSerialBuffer2("\x012\x050\x0B0\x0B0\x018\x018",serRefNum,6); //page  7-26
			outputStream.write(0x12);
			outputStream.write(0x50);
			outputStream.write(0xB0);
			outputStream.write(0xB0);
			outputStream.write(0x18);
			outputStream.write(0x18);
			//SetTheSerialBuffer2(VerticalOCRBZero,serRefNum,72);
			if (CharToPrint.endsWith("0"))
				outputStream.write(VerticalOCRBZero);
			if (CharToPrint.endsWith("1"))
				outputStream.write(VerticalOCRBOne);
			if (CharToPrint.endsWith("2"))
				outputStream.write(VerticalOCRBTwo);
			if (CharToPrint.endsWith("3"))
				outputStream.write(VerticalOCRBThree);
			if (CharToPrint.endsWith("4"))
				outputStream.write(VerticalOCRBFour);
			if (CharToPrint.endsWith("5"))
				outputStream.write(VerticalOCRBFive);
			if (CharToPrint.endsWith("6"))
				outputStream.write(VerticalOCRBSix);
			if (CharToPrint.endsWith("7"))
				outputStream.write(VerticalOCRBSeven);
			if (CharToPrint.endsWith("8"))
				outputStream.write(VerticalOCRBEight);
			if (CharToPrint.endsWith("9"))
				outputStream.write(VerticalOCRBNine);
			if (CharToPrint.endsWith("X"))
				outputStream.write(VerticalOCRBX);
			//SetTheSerialBuffer2("\x012\x04F\x001",serRefNum,3); 	//7 -28 Dowload Character Select
			outputStream.write(0x12);
			outputStream.write(0x4F);
			outputStream.write(0x1);
			//SetTheSerialBuffer(" \x0B0\n",serRefNum);
			outputStream.write(0xB0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    }
    
    static void PrintOCRACharacter(String CharToPrint, OutputStream outputStream)
    {
        
		try {
			//SetTheSerialBuffer2("\x01B\x041\x000",serRefNum,3); //LINE SPACING 7-8
			outputStream.write(0x1B);
			outputStream.write(0x41);
			outputStream.write(0x0);
			//SetTheSerialBuffer2("\x01B\x03D\x001",serRefNum,3); //page  7-58 SET LSB TO RIGHT
			outputStream.write(0x1B);
			outputStream.write(0x3D);
			outputStream.write(0x1);
			//SetTheSerialBuffer2("\x012\x050\x0B0\x0B0\x018\x018",serRefNum,6); //page  7-26
			outputStream.write(0x12);
			outputStream.write(0x50);
			outputStream.write(0xB0);
			outputStream.write(0xB0);
			outputStream.write(0x18);
			outputStream.write(0x18);
			//SetTheSerialBuffer2(VerticalOCRBZero,serRefNum,72);
			if (CharToPrint.endsWith("0"))
				outputStream.write(VerticalOCRAZero);
			if (CharToPrint.endsWith("1"))
				outputStream.write(VerticalOCRAOne);
			if (CharToPrint.endsWith("2"))
				outputStream.write(VerticalOCRATwo);
			if (CharToPrint.endsWith("3"))
				outputStream.write(VerticalOCRAThree);
			if (CharToPrint.endsWith("4"))
				outputStream.write(VerticalOCRAFour);
			if (CharToPrint.endsWith("5"))
				outputStream.write(VerticalOCRAFive);
			if (CharToPrint.endsWith("6"))
				outputStream.write(VerticalOCRASix);
			if (CharToPrint.endsWith("7"))
				outputStream.write(VerticalOCRASeven);
			if (CharToPrint.endsWith("8"))
				outputStream.write(VerticalOCRAEight);
			if (CharToPrint.endsWith("9"))
				outputStream.write(VerticalOCRANine);
			if (CharToPrint.endsWith("X"))
				outputStream.write(VerticalOCRAX);
			//SetTheSerialBuffer2("\x012\x04F\x001",serRefNum,3); 	//7 -28 Dowload Character Select
			outputStream.write(0x12);
			outputStream.write(0x4F);
			outputStream.write(0x1);
			//SetTheSerialBuffer(" \x0B0\n",serRefNum);
			outputStream.write(0xB0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    }
    
    public static void PrintFeedBack(OutputStream outputStream, Context dan)
    {
    	if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("GRANDRAPIDS"))
        {   
			try {
				outputStream.write(0x1B);
				outputStream.write(0x6A);
				outputStream.write(0xF0); // FEED DOT LINES 0X0 = 0 DOT LINES like a carriage return LF
				outputStream.write(0x1B);
				outputStream.write(0x6A);
				outputStream.write(0x6A); // FEED DOT LINES 0X0 = 0 DOT LINES like a carriage return LF

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }
    	if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("RICHMOND"))
        {   
			try {
				outputStream.write(0x1B);
				outputStream.write(0x6A);
				outputStream.write(0xF0); // FEED DOT LINES 0X0 = 0 DOT LINES like a carriage return LF
				outputStream.write(0x1B);
				outputStream.write(0x6A);
				outputStream.write(0x9B); // FEED DOT LINES 0X0 = 0 DOT LINES like a carriage return LF

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }
   }
}
