package com.clancy.aticket;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class HonorCashForm extends ActivityGroup {

    int CurrentSpace = 0;
    int MaxSpaces = 0;
    int StartSpaceNumber = 1;
    int CurrentPass = 0;
    Boolean CashChosen  = true;
    
	  private void EnterClick()
	  {
			  Defines.clsClassName = HonorLotForm.class ;
			  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		   	  startActivityForResult(myIntent, 0);
		   	  finish();
		   	  overridePendingTransition(0, 0);    
			  
	  }
	
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.honorcashform);
       
        WorkingStorage.StoreCharVal(Defines.CurrentPassTimeVal, WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext()), getApplicationContext());
        GetDate.GetDateTime(getApplicationContext());
        
        MaxSpaces = WorkingStorage.GetLongVal(Defines.NumberOfSpacesVal, getApplicationContext());
        StartSpaceNumber = WorkingStorage.GetLongVal(Defines.NumStartSpaceVal, getApplicationContext());
        CurrentSpace = StartSpaceNumber;        
        
        if (WorkingStorage.GetLongVal(Defines.EditHonorBoxVal, getApplicationContext() ) > 0)
        {
            CurrentPass = WorkingStorage.GetLongVal(Defines.EditHonorBoxVal, getApplicationContext()) - 1;        	
        }
        else
        {
            if (StampStartTime(getApplicationContext()) == false)
            {
                //MsgBox("Problem with chip.")
  			  Defines.clsClassName = SelFuncForm.class ;
			  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		   	  startActivityForResult(myIntent, 0);
		   	  finish();
		   	  overridePendingTransition(0, 0);              	
            }        
        }
        if( GetLotNumber(getApplicationContext()) == false)
        {
            //MsgBox("Problem with chip.")
			  Defines.clsClassName = SelFuncForm.class ;
			  Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
		   	  startActivityForResult(myIntent, 0);
		   	  finish();
		   	  overridePendingTransition(0, 0);            	
        }

        Button Previous = (Button) findViewById(R.id.buttonPrevious);
        Previous.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
               if(CurrentSpace == StartSpaceNumber)
               {
                   CurrentSpace = (MaxSpaces + StartSpaceNumber - 1);            	   
               }
               else
               {
            	   CurrentSpace--;;
               }
               GetLotNumber(getApplicationContext());
          }          
        });
        
        Button Forward = (Button) findViewById(R.id.buttonForward);
        Forward.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
               if(CurrentSpace == (MaxSpaces + StartSpaceNumber - 1))
               {
                   CurrentSpace = StartSpaceNumber;            	   
               }
               else
               {
            	   CurrentSpace++;;
               }
               GetLotNumber(getApplicationContext());
          }          
        });        
        
        
        Button bDone = (Button) findViewById(R.id.buttonDONE);
        bDone.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   PopIt("Cashbox Complete?", "Have You Dumped the Cash?");
          }          
        });
      
      

	   final EditText Number = (EditText) findViewById(R.id.editTextNumber);
	   final EditText Cash = (EditText) findViewById(R.id.EditTextCash);
       final Button bGo = (Button) findViewById(R.id.ButtonGo);
      
       Button Store = (Button) findViewById(R.id.buttonStore);
       Store.setOnClickListener(new View.OnClickListener() {
	           public void onClick(View view) {
	        	   CustomVibrate.VibrateButton(getApplicationContext());
	        	   SaveSpaceInfo(Cash.getText().toString(), getApplicationContext());
	               if (CurrentSpace == (MaxSpaces + StartSpaceNumber - 1))
	               {
	            	   CurrentSpace = StartSpaceNumber;
	               }
	               else
	               {
	            	   CurrentSpace = CurrentSpace + 1;
	               }
	               GetLotNumber(getApplicationContext());        	   
	        	   Cash.selectAll();	        	   
	           }	          
	   });
       
       final Button One = (Button) findViewById(R.id.buttonOne);
       if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut1Val,getApplicationContext()).equals(""))
       {
    	   One.setVisibility(View.INVISIBLE);
       }
       else
       {
           One.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF00FF00));
           if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut1Val,getApplicationContext()).substring(0,1).equals("0"))
           {
        	   One.setText("$"+WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut1Val,getApplicationContext()).substring(1));
           }
           else
           {
        	   One.setText("$"+WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut1Val,getApplicationContext()));   
           }           
       }
       One.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   Cash.setText(((String) One.getText()).substring(1));
        	   SaveSpaceInfo(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut1Val,getApplicationContext()),getApplicationContext());
        	   
               if (CurrentSpace == (MaxSpaces + StartSpaceNumber - 1))
               {
            	   CurrentSpace = StartSpaceNumber;
               }
               else
               {
            	   CurrentSpace = CurrentSpace + 1;
               }
               GetLotNumber(getApplicationContext());        	   
        	   Cash.selectAll();
           }	          
       }); 
       
       final Button Two = (Button) findViewById(R.id.buttonTwo);
       if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut2Val,getApplicationContext()).equals(""))
       {
    	   Two.setVisibility(View.INVISIBLE);
       }
       else
       {
    	   Two.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF00FF00));
           if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut2Val,getApplicationContext()).substring(0,1).equals("0"))
           {
        	   Two.setText("$"+WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut2Val,getApplicationContext()).substring(1));
           }
           else
           {
        	   Two.setText("$"+WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut2Val,getApplicationContext()));   
           }           

       }
       Two.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   Cash.setText(((String) Two.getText()).substring(1));
        	   SaveSpaceInfo(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut2Val,getApplicationContext()),getApplicationContext());
        	   
               if (CurrentSpace == (MaxSpaces + StartSpaceNumber - 1))
               {
            	   CurrentSpace = StartSpaceNumber;
               }
               else
               {
            	   CurrentSpace = CurrentSpace + 1;
               }
               GetLotNumber(getApplicationContext());    
        	   Cash.selectAll();
           }	          
       });       
       final Button Three = (Button) findViewById(R.id.buttonThree);
       if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut3Val,getApplicationContext()).equals(""))
       {
    	   Three.setVisibility(View.INVISIBLE);
       }
       else
       {
    	   Three.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF00FF00));
           if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut3Val,getApplicationContext()).substring(0,1).equals("0"))
           {
        	   Three.setText("$"+WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut3Val,getApplicationContext()).substring(1));
           }
           else
           {
        	   Three.setText("$"+WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut3Val,getApplicationContext()));   
           } 
       }
       Three.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   Cash.setText(((String) Three.getText()).substring(1));
        	   SaveSpaceInfo(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut3Val,getApplicationContext()),getApplicationContext());
        	   
               if (CurrentSpace == (MaxSpaces + StartSpaceNumber - 1))
               {
            	   CurrentSpace = StartSpaceNumber;
               }
               else
               {
            	   CurrentSpace = CurrentSpace + 1;
               }
               GetLotNumber(getApplicationContext());            	   
        	   Cash.selectAll();
           }	          
       });
       final Button Four = (Button) findViewById(R.id.buttonFour);
       if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut4Val,getApplicationContext()).equals(""))
       {
    	   Four.setVisibility(View.INVISIBLE);
       }
       else
       {
    	   Four.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFF00FF00));
           if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut4Val,getApplicationContext()).substring(0,1).equals("0"))
           {
        	   Four.setText("$"+WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut4Val,getApplicationContext()).substring(1));
           }
           else
           {
        	   Four.setText("$"+WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut4Val,getApplicationContext()));   
           } 
       }
       Four.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   Cash.setText(((String) Four.getText()).substring(1));
        	   SaveSpaceInfo(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut4Val,getApplicationContext()),getApplicationContext());
        	   
               if (CurrentSpace == (MaxSpaces + StartSpaceNumber - 1))
               {
            	   CurrentSpace = StartSpaceNumber;
               }
               else
               {
            	   CurrentSpace = CurrentSpace + 1;
               }
               GetLotNumber(getApplicationContext());            	   
        	   Cash.selectAll();
           }	          
       });
       final Button Clear = (Button) findViewById(R.id.buttonClear);
       Clear.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
        	   CustomVibrate.VibrateButton(getApplicationContext());
        	   Cash.setText("0.00");
        	   Cash.selectAll();
        	   SaveSpaceInfo("0000", getApplicationContext());
           }	          
       });

	   Cash.selectAll();
       Cash.requestFocus();	 
       Cash.setOnFocusChangeListener(new OnFocusChangeListener() {
    	   @Override
    	   public void onFocusChange(View v, boolean hasFocus) {
    	       if(hasFocus){
    	    	   bGo.setVisibility(View.INVISIBLE);
    	    	   Cash.selectAll();
    	       }else {
    	    	   bGo.setVisibility(View.VISIBLE);
    	       }
    	      }
    	   });


       bGo.setOnClickListener(new View.OnClickListener() {
	           public void onClick(View view) {
	        	   CustomVibrate.VibrateButton(getApplicationContext());
	        	   String SpaceVal = Number.getText().toString().trim();
	        	   Double dSpaceVal = Double.valueOf(SpaceVal);
	        	   int iMax = (MaxSpaces + StartSpaceNumber - 1);
	        	   if (dSpaceVal > (MaxSpaces + StartSpaceNumber - 1))
	        	   {
	        		   Messagebox.Message("Maximum space for this lot is " + Integer.toString(iMax), getApplicationContext());
	        		   Number.setText(Integer.toString(iMax));
	        		   CurrentSpace = MaxSpaces + StartSpaceNumber - 1;
	        	   }
	        	   else if (dSpaceVal < StartSpaceNumber)
	        	   {
	        		   Messagebox.Message("Minimum space for this lot is " + Integer.toString(StartSpaceNumber) , getApplicationContext());
	        		   Number.setText(Integer.toString(StartSpaceNumber));
	        		   CurrentSpace = StartSpaceNumber;
	        	   }
	        	   else
	        	   {
	        		   CurrentSpace = Integer.valueOf(SpaceVal);
	        	   }
	        	   Cash.selectAll();
	               Cash.requestFocus();	
	               GetLotNumber(getApplicationContext());
	           }	          
	   });    
       bGo.setVisibility(View.INVISIBLE);
       
       
       
	   TextView tvLot = (TextView) findViewById(R.id.textViewLotName);
	   tvLot.setText(WorkingStorage.GetCharVal(Defines.SaveHBoxLotVal, getApplicationContext()));
	   
	   TextView tvTime = (TextView) findViewById(R.id.textViewTime);
	   tvTime.setText(WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()));
	   
   	   
	   TextView tvRates = (TextView) findViewById(R.id.widgetEnterNumber);
	   
  /* 	if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut1Val,getApplicationContext()).equals(""))
	{
		WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut1Val, cRates, getApplicationContext());
	}
	else if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut2Val,getApplicationContext()).equals(""))
	{
		WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut2Val, cRates, getApplicationContext());
	}
	else if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut3Val,getApplicationContext()).equals(""))
	{
		WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut3Val, cRates, getApplicationContext());
	}
	else if(WorkingStorage.GetCharVal(Defines.SaveHBoxShortCut4Val,getApplicationContext()).equals(""))
	{
		WorkingStorage.StoreCharVal(Defines.SaveHBoxShortCut4Val, cRates, getApplicationContext());
	}*/
	   		   
	}
	  
	    public void PopIt( String title, String message ){
	        new AlertDialog.Builder(this)
	        .setTitle( title )
	        .setMessage( message )
	        .setPositiveButton("YES", new DialogInterface.OnClickListener() {            
	        	public void onClick(DialogInterface arg0, int arg1) 
	        	{        	
	        		EnterClick();
	            }
	        })
	        .setNegativeButton("NO", new DialogInterface.OnClickListener() {            
	        	public void onClick(DialogInterface arg0, int arg1) 
	        	{
	            }
	        }).show();

	    }

		  public Boolean StampStartTime(Context dan) 
		  {
		        int HowFarOver = 0;
		        CurrentPass = IOHonorFile.HowManyPasses("", dan);
		        if (CurrentPass == 20)
		        {
		        	return false;
		        }
		        HowFarOver = (CurrentPass * 8) + 15;
		        IOHonorFile.WriteVirtualFile(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext()), HowFarOver, getApplicationContext());
		        return true;
		  }
		  
		  public void SaveSpaceInfo(String HowMuchMoney, Context dan) 
		  {
		        int HowFarOver = 0;
		        HowFarOver = (CurrentPass * 8) + 19;
		        HowFarOver = ((CurrentSpace - StartSpaceNumber + 1) * 177) + HowFarOver;
		        // adding a new routine in here to remove any decimals and zerofill
		        String ModString = "";
		        for(int i=1; i<=HowMuchMoney.length(); i++)
		        {
		        	if (!HowMuchMoney.substring(i-1, i).equals("."))
		        	{
		        		ModString = ModString + HowMuchMoney.substring(i-1, i);
		        	}
		        }
		        if (ModString.length() < 4)
		        {
		        	while (ModString.length() < 4)
		        		ModString = "0"+ModString;
		        }

		        IOHonorFile.WriteVirtualFile(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), ModString, HowFarOver, getApplicationContext());
		  }
		  
		  public Boolean GetLotNumber(Context dan) 
		  {
		        String RecNumBuffer = "";

	            RecNumBuffer = SearchData.GetRecordNumberNoLength(WorkingStorage.GetCharVal(Defines.HonorFileNameVal, getApplicationContext()), CurrentSpace + 1 - (StartSpaceNumber - 1), getApplicationContext());
	            
		        if (RecNumBuffer.equals("ERROR"))
		        {
		        	return false;
		        }
	            String SpaceBuffer = "";
		        SpaceBuffer = RecNumBuffer.substring(11, 15);
		        EditText Number = (EditText) findViewById(R.id.editTextNumber);
		        Number.setText(SpaceBuffer.trim());
	            String CashBuffer = "";
		        CashBuffer = RecNumBuffer.substring((CurrentPass * 8) + 19,(CurrentPass * 8) + 23);
		         
		        final EditText Cash = (EditText) findViewById(R.id.EditTextCash);
		        if (CashBuffer.substring(0,1).equals("0"))
		        {
		        	Cash.setText(CashBuffer.substring(1,2)+"."+CashBuffer.substring(2));
		        }
		        else
		        {
		        	Cash.setText(CashBuffer.substring(0,2)+"."+CashBuffer.substring(2));
		        }
		        Cash.selectAll();
	            return true;
		  }		  
}