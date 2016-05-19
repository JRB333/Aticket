package com.clancy.aticket;

// This is a copy of Dan's PrintPictureFast.java
// Modifications here to work on smaller Signature Image file
// and to print within existing citation data (not on separate page)
// while not compromising the printing of larger Picture Images.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;

public class PrintSignatureFast {

    public static double WhtSpWidth = 0;
    public static Bitmap bm;

    public static void PrintSignature(String PictureName, Context dan, OutputStream outputStream)
    {
        String BadgeNo = WorkingStorage.GetCharVal(Defines.PrintBadgeVal, dan).trim();
        PictureName = "Sig" + BadgeNo.trim() + ".png";   // Fixed Signature Image file name

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;  // returns a smaller image (in this case 1/2) to save memory
        bm = BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/" + PictureName, opts);

        // Create All White Bitmaps to 'pad' original Signature Image Bitmap (Left & Right)
        // and Fill for Full printer Width
        WhtSpWidth = bm.getWidth() * .50;
        Bitmap LftWhtbm = createWhiteBitmap(bm);  // LeftPad - Get All White Copy of Signature Bitmap
        Bitmap mergedBm1 = mergeBitmaps(LftWhtbm, bm);  // Merge Bitmaps Side-By-Side
        WhtSpWidth = bm.getWidth() * .50;
        Bitmap RtWhtbm = createWhiteBitmap(bm);  // RightPad - Get All White Copy of Signature Bitmap
        Bitmap mergedBm = mergeBitmaps(mergedBm1,RtWhtbm);  // Merge Bitmaps Side-By-Side

        // Picture Output Size
        //int NewImageHeight = 108;
        //int NewImageWidth = 144;

        // New Printable Signature Image Output Size
        int NewImageHeight = 14;
        int NewImageWidth = 144; // Printer line width = 576 so this is 1/4 * width
        int ImageHxW = NewImageHeight * NewImageWidth;

        // Resize Signature Bitmap for Printing
        Bitmap newbm = getResizedBitmap(mergedBm, NewImageHeight, NewImageWidth);
        // 'Clean House'
        RtWhtbm.recycle();
        LftWhtbm.recycle();
        bm.recycle();
        mergedBm.recycle();

        // we should only need 8064 (Height * Width * 4) bytes because of the ARGB_8888 format = 32-bit image.
        // I only had a RGB 24-bit image on windows mobile, so I will be ignoring the A - alpha.
        ByteBuffer bb = ByteBuffer.allocate(ImageHxW * 4);
        newbm.copyPixelsToBuffer(bb);
        newbm.recycle();

        int[] GreyBuffer = new int[ImageHxW];  //ImageHxW = NewImageHeight * NewImageWidth
        byte[] ColorBuffer = bb.array();
        int GreyBufferPosition = 0;
        int bbBufferPosition = 0;
        int RGBTotal = 0;
        int PixelColor = 0;

        for (bbBufferPosition = 0; bbBufferPosition < (ImageHxW * 4); bbBufferPosition += 4 )
        {
            //only look at the first of four bytes all rgb bytes will be the same after converting image to grayscale
            RGBTotal = (int) ColorBuffer[bbBufferPosition];

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

            if (bbBufferPosition >= ((ImageHxW * 4)-30)) {
                boolean HOLD = true;
                HOLD = true;
            }
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

            outputStream.write(0x1B);
            outputStream.write(0x3D);
            outputStream.write(0x0); // 7-58 SET LSB TO RIGHT

            outputStream.write(0x1B);
            outputStream.write(0x56);
            //outputStream.write(0xB0);  // OLD LINE: 7-38 108*4 = 177+255
            outputStream.write(0x38);  // Low byte (nl)
            outputStream.write(0x0); // High byte (nh) -> 14 (height) * 4 = 56  => 0x0038

            // print picture routine here
            byte[] NewImageWidthChars = new byte[NewImageWidth];
            int x = 0;
            byte[] DanBuffer = new byte[ImageHxW * 4];
            int DanBufferPos = 0;

            int NewImageWidthCounter = 0;

            for (x = 0; x < ImageHxW; x++) // ImageHxW = NewImageHeight * NewImageWidth  Formerly:  5760 = 144*108
            {
                NewImageWidthChars[NewImageWidthCounter] = (byte) GreyBuffer[x];
                NewImageWidthCounter++;
                if (NewImageWidthCounter == NewImageWidth)
                {
                    int v = 0;
                    for (v = 1; v <= 4; v++)
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
                                for (b = 0; b < NewImageWidth; b++)
                                {
                                    switch (NewImageWidthChars[b])
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
                                        DanBuffer[DanBufferPos] = (byte) ByteBuffer;
                                        HalfByte = 0;
                                        ByteBuffer = 0;
                                        DanBufferPos ++;
                                    }
                                    else
                                    {
                                        HalfByte ++;
                                    }
                                }// end for (b=0; b<144; b++)
                                break;
                            // end case v = 1

                            case 2:
                                b = 0;
                                HalfByte = 0;
                                ByteBuffer = 0;
                                for (b = 0; b < NewImageWidth; b++)
                                {
                                    switch (NewImageWidthChars[b])
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
                                        DanBuffer[DanBufferPos] = (byte) ByteBuffer;
                                        HalfByte = 0;
                                        ByteBuffer = 0;
                                        DanBufferPos ++;
                                    }
                                    else
                                    {
                                        HalfByte ++;
                                    }
                                }// end for (b=0; b<144; b++)
                                break;
                            // end case v = 2

                            case 3:
                                b = 0;
                                HalfByte = 0;
                                ByteBuffer = 0;
                                for (b = 0; b < NewImageWidth; b++)
                                {
                                    switch (NewImageWidthChars[b])
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
                                        DanBuffer[DanBufferPos] = (byte) ByteBuffer;
                                        HalfByte = 0;
                                        ByteBuffer = 0;
                                        DanBufferPos ++;
                                    }
                                    else
                                    {
                                        HalfByte ++;
                                    }
                                }// end for (b=0; b<144; b++)
                                break;
                            // end case v = 3

                            case 4:
                                b = 0;
                                HalfByte = 0;
                                ByteBuffer = 0;
                                for (b=0; b < NewImageWidth; b++)
                                {
                                    switch (NewImageWidthChars[b])
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
                                        DanBuffer[DanBufferPos] = (byte) ByteBuffer;
                                        DanBufferPos ++;
                                        HalfByte = 0;
                                        ByteBuffer = 0;
                                    }
                                    else
                                    {
                                        HalfByte ++;
                                    }
                                }// end for (b = 0; b < 144; b++)
                                break;
                            // end case v = 4

                        }// end switch (v)
                    } // end for (v = 1; v <= 4; v++)

                    NewImageWidthCounter = 0;
                    NewImageWidthChars = new byte[NewImageWidth];
                }  // end of: if (NewImageWidthCounter == NewImageWidth)
            }  // end of:  for (x = 0; x < ImageHxW; x++)

            outputStream.write(DanBuffer, 0, DanBufferPos);
            DanBufferPos ++;

            outputStream.write(0x1B);
            outputStream.write(0x56);
            outputStream.write(0x0);
            outputStream.write(0x0); //
            // End of print picture routine

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

    // Create All White Duplicate of Original Bitmap (Signature)
    public static Bitmap createWhiteBitmap(Bitmap origBitmap) {
        // Create new bitmap based on the size and config of the old
        int newWidth = (int) WhtSpWidth;
        Bitmap whiteBitmap = Bitmap.createBitmap(newWidth, origBitmap.getHeight(), origBitmap.getConfig());

        // Instantiate a canvas and prepare it to paint to the new bitmap
        Canvas canvas = new Canvas(whiteBitmap);

        // Paint it white (or whatever color you want)
        canvas.drawColor(Color.WHITE);

        // Draw the old bitmap ontop of the new white one
        canvas.drawBitmap(whiteBitmap, 0, 0, null);

        return whiteBitmap;
    }

    // Merge 2 Bitmaps Side-by-Side (Signature + White)
    public static Bitmap mergeBitmaps(Bitmap sig, Bitmap wht) {
        Bitmap comboBitmap = null;

        int width, height = 0;

        if(sig.getWidth() > wht.getWidth()) {
            width = sig.getWidth() + wht.getWidth();
            height = sig.getHeight();
        } else {
            width = sig.getWidth() + wht.getWidth();
            height = sig.getHeight();
        }

        comboBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(comboBitmap);

        comboImage.drawBitmap(sig, 0f, 0f, null);
        comboImage.drawBitmap(wht, sig.getWidth(), 0f, null);

        // The following is for DEBUG ONLY - make 2nd JPG copy of Image File    ##########
        /*if (WhtSpWidth == (bm.getWidth() * .55)) {
            File debugFile = new File(Environment.getExternalStorageDirectory(), "CheckSig.JPG");
            FileOutputStream mDebugOutStream = null;
            try {
                mDebugOutStream = new FileOutputStream(debugFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            comboBitmap.compress(Bitmap.CompressFormat.JPEG, 100, mDebugOutStream);
        }*/
        // ###############

       return comboBitmap;
    }
}
