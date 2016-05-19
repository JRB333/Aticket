package com.clancy.aticket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CameraForm extends ActivityGroup {
	
	private Camera mCamera;
	private Handler mHandler = new Handler();
	
	//CameraPreviewClass mPreview;
	public static String PictureName = "Error.jpg";
	public boolean Processing = false; 

	private Runnable mCopyImages = new Runnable() {     
		public void run() 
		{         			    
	        
    		if (SystemIOFile.exists("REPRINT1.JPG"))
    			SystemIOFile.Delete("REPRINT1.JPG");
    		if (SystemIOFile.exists("REPRINT2.JPG"))
    			SystemIOFile.Delete("REPRINT2.JPG");
    		if (SystemIOFile.exists("REPRINT3.JPG"))
    			SystemIOFile.Delete("REPRINT3.JPG");
    			
    		String PicName = "";
        	BitmapFactory.Options opts = new BitmapFactory.Options();
        	opts.inSampleSize = 4;
    		PicName = WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-1.JPG"; 
    		if (SystemIOFile.exists(PicName))
    	  	{
    			
    			Bitmap bm = BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+PicName, opts);
    			
    			/*Bitmap dest = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
    			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    	        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system

    	        Canvas cs = new Canvas(dest);
    	        Paint tPaint = new Paint();
    	        tPaint.setTextSize(15);
    	        tPaint.setColor(Color.GREEN);
    	        tPaint.setStyle(Style.FILL);
    	        cs.drawBitmap(bm, 0f, 0f, null);
    	        float height = tPaint.measureText("yY");
    	        cs.drawText(dateTime, 10f, height+15f, tPaint);
    	        /*try {
    	            dest.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream("/data/data/com.clancysystems.ticketdemo/files/"+"REPRINT1.JPG"));
    	        } catch (FileNotFoundException e) {
    	            // TODO Auto-generated catch block
    	            e.printStackTrace();
    	        } */   			
        		try {        
        			FileOutputStream out = new FileOutputStream("/data/data/com.clancy.aticket/files/"+"REPRINT1.JPG");
        			bm.compress(Bitmap.CompressFormat.JPEG, 92, out);
        			//dest.compress(Bitmap.CompressFormat.JPEG, 100, out);
        			out.flush();
        			out.close();
        			out = null;            			
        		} 
        		catch (Exception e) 
        		{        e.printStackTrace(); } 
        		bm.recycle();
    	  	}   

    		PicName = WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-2.JPG"; 
    		if (SystemIOFile.exists(PicName))
    	  	{
        		/*try {
					SystemIOFile.copyFile("/data/data/com.clancy.aticket/files/"+PicName, "/data/data/com.clancy.aticket/files/"+"REPRINT2.JPG");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
    			Bitmap bm = BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+PicName, opts);
        		try {        
        			FileOutputStream out = new FileOutputStream("/data/data/com.clancy.aticket/files/"+"REPRINT2.JPG");
        			bm.compress(Bitmap.CompressFormat.JPEG, 92, out);
        			out.flush();
        			out.close();
        			out = null;            			
        		} 
        		catch (Exception e) 
        		{        e.printStackTrace(); } 
        		bm.recycle();
    	  	} 

    		PicName = WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-3.JPG"; 
    		if (SystemIOFile.exists(PicName))
    	  	{
        		/*try {
					SystemIOFile.copyFile("/data/data/com.clancy.aticket/files/"+PicName, "/data/data/com.clancy.aticket/files/"+"REPRINT3.JPG");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
    			Bitmap bm = BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+PicName, opts);
        		try {        
        			FileOutputStream out = new FileOutputStream("/data/data/com.clancy.aticket/files/"+"REPRINT3.JPG");
        			bm.compress(Bitmap.CompressFormat.JPEG, 92, out);
        			out.flush();
        			out.close();
        			out = null;            			
        		} 
        		catch (Exception e) 
        		{        e.printStackTrace(); } 
        		bm.recycle();
    	  	}
    		    		
    		if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
    	    {
    			WorkingStorage.StoreCharVal(Defines.CameraNextPressVal, "TRUE", getApplicationContext());
    			Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
    	   		startActivityForResult(myIntent, 0);
    	   		finish();
    	   		overridePendingTransition(0, 0);    
    	    }
		}
	}; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cameraform);
        
        WorkingStorage.StoreCharVal(Defines.CameraNextPressVal, "FALSE", getApplicationContext());
        final ImageView pic1View = (ImageView) findViewById(R.id.imageView1);
        pic1View.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (Processing == false)
            	{
            		pic1View.setImageResource(R.drawable.pic1blue);
            		CustomVibrate.VibrateButton(getApplicationContext());
            		Processing = true; 
            		PictureName = WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-1.JPG";
                	mCamera.autoFocus(autoFocusCallback);	
            	}            		            	
            }
            }           
        );      
        final ImageView pic2View = (ImageView) findViewById(R.id.imageView2);
        pic2View.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (Processing == false)
            	{
            		pic2View.setImageResource(R.drawable.pic2blue);
            		CustomVibrate.VibrateButton(getApplicationContext());
            		Processing = true; 
            		PictureName = WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-2.JPG";
                	mCamera.autoFocus(autoFocusCallback);	
            	} 
            }
            }           
        );  
        final ImageView pic3View = (ImageView) findViewById(R.id.imageView3);
        pic3View.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (Processing == false)
            	{
            		pic3View.setImageResource(R.drawable.pic3blue);
            		CustomVibrate.VibrateButton(getApplicationContext());
            		Processing = true; 
            		PictureName = WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-3.JPG";
            		//Messagebox.Message(PictureName, getApplicationContext());
                	mCamera.autoFocus(autoFocusCallback);	
            	} 
            }
            }           
        );  
        final ImageView pic4View = (ImageView) findViewById(R.id.imageView4);
        pic4View.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (Processing == false)
            	{
            		pic4View.setImageResource(R.drawable.nextblue);
            		mHandler.postDelayed(mCopyImages, 50);
            		SystemIOFile.Delete("tempcite.jpg");
            	} 
            }
            }           
        );                   
	}
	   @Override
	    protected void onStart() {
	        super.onStart();
	    	initCamera();
	        // The activity is about to become visible.
	    }
	    @Override
	    protected void onResume() {
	        super.onResume();
	        // The activity has become visible (it is now "resumed").
	    }

	    @Override
	    protected void onStop() {
	        super.onStop();
	        releaseCamera();
	        if (WorkingStorage.GetCharVal(Defines.CameraNextPressVal, getApplicationContext()).equals("FALSE"))
	        {
	        	Defines.clsClassName = SelFuncForm.class ;
	        	Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
	        	startActivityForResult(myIntent, 0);
	        }
	        finish(); //kill the app because the camera object will be stuck in memory at this point
	        // The activity is no longer visible (it is now "stopped")
       		overridePendingTransition(0, 0);
	    }
	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        releaseCamera(); 
	        // The activity is about to be destroyed.
	    }

	    

	    AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
	    	  @Override
	    	  public void onAutoFocus(boolean success, Camera camera) {
	    		  mCamera.takePicture(null, null, mPicture);
	    		  //initCamera();
	    	  }
	    	};
	    
	    private void initCamera(){
	    	releaseCamera();
	        CameraPreviewClass mPreview;
	    	mCamera = getCameraInstance();
	    	mPreview = new CameraPreviewClass(this, mCamera);
	    	FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	    	preview.removeAllViews();
	    	preview.addView(mPreview);
	    	//preview.bringToFront();
	    	//preview.clearAnimation();
	    	//preview.destroyDrawingCache();
	    	//preview.addView(mPreview, 0);
	    	Processing = false; 
	    }

	    private void releaseCamera(){
	        if (mCamera != null){
	            mCamera.release();        // release the camera for other applications
	            mCamera = null;
	        }
	    }
		
	    public static Camera getCameraInstance(){
	        Camera c = null;
	        try {
	            c = Camera.open(); // attempt to get a Camera instance   
	        	Camera.Parameters params = c.getParameters();
	        	//List blah1 = params.getSupportedPictureSizes();
	        	try
	        	{
		        	String FlashSupported = params.getFlashMode(); //this test is for the ASUS 0 inch tablet
		        	if (FlashSupported != null)
		        		params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);	        		
	        	}catch (Exception e){}
	        	params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
	        	if (android.os.Build.MODEL.equals("SGH-M919")) //Galaxy S4, the bug is that if the flash flashes then the onPictureTaken will never execute if 640x480  
	            {
		        	params.setPictureSize(1280, 720);
	            }
	            else
	            {
		        	params.setPictureSize(640, 480);	            	
	            }
	        	params.setJpegQuality(80);
	        	c.setParameters(params);  
	        }
	        catch (Exception e){
	            // Camera is not available (in use or does not exist)
	        	//String dan1 = "";
	        }
	        return c; // returns null if camera is unavailable
	    }
	    
	    private PictureCallback mPicture = new PictureCallback() {

	        @Override
	        public void onPictureTaken(byte[] data, Camera camera) {

	            File pictureFile = getOutputMediaFile();
	            if (pictureFile == null){
	                return;
	            }

	            try {
	                FileOutputStream fos = new FileOutputStream(pictureFile);
	                fos.write(data);
	                fos.close();
	            } catch (FileNotFoundException e) {
	            } catch (IOException e) {
	                
	            }
	            //5-24
    			Bitmap bm = BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/tempcite.jpg");
    			
    			Bitmap dest = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
    			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    	        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system

    	        Canvas cs = new Canvas(dest);
    	        Paint tPaint = new Paint();
    	        tPaint.setTextSize(25);
    	        tPaint.setColor(Color.BLUE);
    	        tPaint.setStyle(Style.FILL);
    	        cs.drawBitmap(bm, 0f, 0f, null);
    	        //float height = tPaint.measureText("yY");
    	        //cs.drawText(dateTime, 10f, height+15f, tPaint);
    	        cs.drawText(dateTime, 10f, 35f, tPaint);
		
        		try {        
        			FileOutputStream out = new FileOutputStream("/data/data/com.clancy.aticket/files/"+PictureName);
        			//bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
        			dest.compress(Bitmap.CompressFormat.JPEG, 92, out);
        			out.flush();
        			out.close();
        			out = null;            			
        		} 
        		catch (Exception e) 
        		{        e.printStackTrace(); } 
        		bm.recycle();	
        		dest.recycle();
	            //5-24
            	BitmapFactory.Options opts = new BitmapFactory.Options();
            	opts.inSampleSize = 4;
	            if (PictureName.contains("-1"))
	            {
	            	ImageView pic1View = (ImageView) findViewById(R.id.imageView1);
	            	pic1View.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+PictureName, opts)); 
	            }
	            if (PictureName.contains("-2"))
	            {
	            	ImageView pic2View = (ImageView) findViewById(R.id.imageView2);
//	            	long allocNativeHeap = Debug.getNativeHeapAllocatedSize(); 
	            	pic2View.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+PictureName, opts)); 
	            }
	            if (PictureName.contains("-3"))
	            {
	            	ImageView pic3View = (ImageView) findViewById(R.id.imageView3);
	            	pic3View.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+PictureName, opts)); 
	            }
	            initCamera();	            
	        }
	    };

	  /** Create a File for saving an image or video */
	  private static File getOutputMediaFile(){
	      File mediaFile;
	   	  //mediaFile = new File("/data/data/com.clancy.aticket/files/"+PictureName); 5-24
	      mediaFile = new File("/data/data/com.clancy.aticket/files/tempcite.jpg");
	      return mediaFile;
	  }
}

