package com.clancy.aticket;


import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PrintSelectForm extends ActivityGroup {
	boolean OneChecked = false;
	boolean TwoChecked = false;
	boolean ThreeChecked = false;
	boolean IsPicOne = false;
	boolean IsPicTwo = false;
	boolean IsPicThree = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printselectform);
        if (savedInstanceState == null)
        {
        	WorkingStorage.StoreCharVal(Defines.PrintPic1Val, "NO", getApplicationContext());
        	WorkingStorage.StoreCharVal(Defines.PrintPic2Val, "NO", getApplicationContext());
        	WorkingStorage.StoreCharVal(Defines.PrintPic3Val, "NO", getApplicationContext());
        }  
        if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, getApplicationContext()).trim().equals(Defines.ReprintMenu))
        {
        	BitmapFactory.Options opts = new BitmapFactory.Options();
        	opts.inSampleSize = 2;
        	ImageView pic1View = (ImageView) findViewById(R.id.imageView1);
        	if (SystemIOFile.exists("REPRINT1.JPG"))
        	{
        		pic1View.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+"REPRINT1.JPG", opts));
        		IsPicOne = true;
        	}  
        	else
        	{
        		pic1View.setImageResource(R.drawable.white);
        	}
    		ImageView pic2View = (ImageView) findViewById(R.id.imageView2);
        	if (SystemIOFile.exists("REPRINT2.JPG"))
        	{
        		pic2View.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+"REPRINT2.JPG", opts));
        		IsPicTwo = true;
        	} 
        	else
        	{
        		pic2View.setImageResource(R.drawable.white);
        	}
    		ImageView pic3View = (ImageView) findViewById(R.id.imageView3);
        	if (SystemIOFile.exists("REPRINT3.JPG"))
        	{
        		pic3View.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+"REPRINT3.JPG", opts)); 
        		IsPicThree = true;
        	}  
        	else
        	{
        		pic3View.setImageResource(R.drawable.white);
        	}        	
        }
        else
        {
        	BitmapFactory.Options opts = new BitmapFactory.Options();
        	opts.inSampleSize = 4;
        	ImageView pic1View = (ImageView) findViewById(R.id.imageView1);
        	if (SystemIOFile.exists(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-1.JPG"))
        	{
        		pic1View.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-1.JPG", opts));
        		IsPicOne = true;
        	} 
        	else
        	{
        		pic1View.setImageResource(R.drawable.white);
        	}   
    		ImageView pic2View = (ImageView) findViewById(R.id.imageView2);
        	if (SystemIOFile.exists(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-2.JPG"))
        	{
        		pic2View.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-2.JPG", opts));
        		IsPicTwo = true;
        	}   
        	else
        	{
        		pic2View.setImageResource(R.drawable.white);
        	}   
    		ImageView pic3View = (ImageView) findViewById(R.id.imageView3);
        	if (SystemIOFile.exists(WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-3.JPG"))
        	{
        		pic3View.setImageBitmap(BitmapFactory.decodeFile("/data/data/com.clancy.aticket/files/"+WorkingStorage.GetCharVal(Defines.PrintCitationVal, getApplicationContext()).trim()+"-3.JPG", opts));
        		IsPicThree = true;
        	}   
        	else
        	{
        		pic3View.setImageResource(R.drawable.white);
        	}   
        }
        
        final ImageView pic5View = (ImageView) findViewById(R.id.imageView5);
        if (OneChecked == false)
        	pic5View.setVisibility(View.INVISIBLE); //invisible
        else
        	pic5View.setVisibility(View.VISIBLE); //visible
        
        final ImageView pic6View = (ImageView) findViewById(R.id.imageView6);
        if (TwoChecked == false)
        	pic6View.setVisibility(View.INVISIBLE); //invisible
        else
        	pic6View.setVisibility(View.VISIBLE); //visible
        
        final ImageView pic7View = (ImageView) findViewById(R.id.imageView7);
        if (ThreeChecked == false)
        	pic7View.setVisibility(View.INVISIBLE); //invisible
        else
        	pic7View.setVisibility(View.VISIBLE); //visible
        
        pic5View.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (IsPicOne == true) // we have a picture 1
            	{
            		if (OneChecked == false)
            		{
            			OneChecked = true;
            			pic5View.setVisibility(View.VISIBLE); //Visible
            		}
            		else
            		{
            			OneChecked = false;	
            			pic5View.setVisibility(View.INVISIBLE); //invisible
            		}
            	}
            }
        }           
        );  
        ImageView pic1View = (ImageView) findViewById(R.id.imageView1);
        pic1View.setOnClickListener(
                new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	if (IsPicOne == true) // we have a picture 1
                	{
                		if (OneChecked == false)
                		{
                			OneChecked = true;
                			pic5View.setVisibility(View.VISIBLE); //Visible
                		}
                		else
                		{
                			OneChecked = false;	
                			pic5View.setVisibility(View.INVISIBLE); //invisible
                		}
                	}
                }
           }           
        );  

        pic6View.setOnClickListener( 
                new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	if (IsPicTwo == true) // we have a picture 2
                	{
                		if (TwoChecked == false)
                		{
                			TwoChecked = true;
                			pic6View.setVisibility(View.VISIBLE); //Visible
                		}
                		else
                		{
                			TwoChecked = false;	
                			pic6View.setVisibility(View.INVISIBLE); //invisible
                		}
                	}
                }
            }           
        );  
        
        ImageView pic2View = (ImageView) findViewById(R.id.imageView2);
        pic2View.setOnClickListener(
                    new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	if (IsPicTwo == true) // we have a picture 2
                    	{
                    		if (TwoChecked == false)
                    		{
                    			TwoChecked = true;
                    			pic6View.setVisibility(View.VISIBLE); //Visible
                    		}
                    		else
                    		{
                    			TwoChecked = false;	
                    			pic6View.setVisibility(View.INVISIBLE); //invisible
                    		}
                    	}
                    }
                }           
        );  

        pic7View.setOnClickListener(
               new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	if (IsPicThree == true) // we have a picture 3
                    	{
                    		if (ThreeChecked == false)
                    		{
                    			ThreeChecked = true;
                    			pic7View.setVisibility(View.VISIBLE); //Visible
                    		}
                    		else
                    		{
                    			ThreeChecked = false;	
                    			pic7View.setVisibility(View.INVISIBLE); //invisible
                    		}
                    	}
                    }
                }           
        );  
        
        ImageView pic3View = (ImageView) findViewById(R.id.imageView3);
             pic3View.setOnClickListener(
                        new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        	if (IsPicThree == true) // we have a picture 3
                        	{
                        		if (ThreeChecked == false)
                        		{
                        			ThreeChecked = true;
                        			pic7View.setVisibility(View.VISIBLE); //Visible
                        		}
                        		else
                        		{
                        			ThreeChecked = false;	
                        			pic7View.setVisibility(View.INVISIBLE); //invisible
                        		}
                        	}
                        }
                    }           
        );  
        
        final ImageView pic4View = (ImageView) findViewById(R.id.imageView4);
        pic4View.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
            	pic4View.setImageResource(R.drawable.printablue);
            	if (OneChecked == true)
            		WorkingStorage.StoreCharVal(Defines.PrintPic1Val, "YES", getApplicationContext());

            	if (TwoChecked == true)
            		WorkingStorage.StoreCharVal(Defines.PrintPic2Val, "YES", getApplicationContext());

            	if (ThreeChecked == true)
            		WorkingStorage.StoreCharVal(Defines.PrintPic3Val, "YES", getApplicationContext());
            	
            	Defines.clsClassName = PrintForm.class ;
        		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        		startActivityForResult(myIntent, 0);  
        		finish();
        		overridePendingTransition(0, 0);	
            }
        }           
        );
        
        if (IsPicOne == false && IsPicTwo == false && IsPicThree == false ) //No Pictures so just move on to the printing
        {
				Defines.clsClassName = PrintForm.class ;
        		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
        		startActivityForResult(myIntent, 0);  
        		finish();
        		overridePendingTransition(0, 0);	
        }
        
        if (WorkingStorage.GetCharVal(Defines.GPSSurveyVal, getApplicationContext()).trim().equals("GPS"))
        {
			Defines.clsClassName = PrintForm.class ;
    		Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
    		startActivityForResult(myIntent, 0);  
    		finish();
    		overridePendingTransition(0, 0);
        }        
	}
}

