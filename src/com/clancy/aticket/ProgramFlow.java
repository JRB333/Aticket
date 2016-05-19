package com.clancy.aticket;



import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;


public class ProgramFlow extends ActivityGroup {
	
	  static public String GetNextSetupForm(Context dan) 
	  {
		int nRecord = 0;
		String TempString ;
		nRecord = WorkingStorage.GetLongVal(Defines.CurrentOffOrderVal, dan)+1;
		WorkingStorage.StoreLongVal(Defines.CurrentOffOrderVal, nRecord, dan);
		
      	long OfforderExists = SearchData.GetFileSize("OFFORDER.A");    		
    	if (OfforderExists == 0) // missing OFForder.a
    	{
    		Messagebox.Message("OFFORDER.A File Missing!",dan);
    		return "ERROR";
    	}
    	
    	TempString = SearchData.GetRecordNumberNoLength("OFFORDER.A", nRecord, dan).trim();
    	if (TempString.equals("ERROR"))
    	{
    		Messagebox.Message("I don't know why there is an error.",dan);
    	}
    	else if (TempString.equals("GetBootForm"))
    	{
    		Defines.clsClassName = GetBootForm.class ;
    		return "GetBootForm";
    	}
    	else if (TempString.equals("BadgeForm"))
    	{
    		Defines.clsClassName = BadgeForm.class ;
    		return "BadgeForm";
    	}       	
    	else if (TempString.equals("BeatForm"))
    	{
    		Defines.clsClassName = BeatForm.class ;
    		return "BeatForm";
    	}   
    	else if (TempString.equals("CourtForm"))
    	{
    		Defines.clsClassName = CourtDateForm.class ;
    		return "CourtForm";
    	}      	
    	else if (TempString.equals("DeptForm"))
    	{
    		Defines.clsClassName = DeptForm.class ;
    		return "DeptForm";
    	}  
    	else if (TempString.equals("EmailForm"))
    	{
    		MustDoSetup.WriteMustDoSetupDate(dan);
    		Defines.clsClassName = EmailForm.class ;
    		return "EmailForm";
    	} 
    	else if (TempString.equals("LanguageForm") || TempString.equals("LanguageForn")) //misspelling in menu.t on old handhelds
    	{
    		Defines.clsClassName = LanguageForm.class ;
    		return "LanguageForm";
    	}      	
    	else if (TempString.equals("MainForm"))
    	{
    		MustDoSetup.WriteMustDoSetupDate(dan);
    		Defines.clsClassName = SelFuncForm.class ;
    		return "SelFuncForm";
    	}
    	else if (TempString.equals("NextLastForm"))
    	{
    		Defines.clsClassName = NextLastForm.class ;
    		return "NextLastForm";
    	}
    	else if (TempString.equals("OfficerForm"))
    	{
    		Defines.clsClassName = OfficerForm.class ;
    		return "OfficerForm";
    	}       	
    	else if (TempString.equals("ShowDateForm"))
    	{
    		GetNextSetupForm(dan);
    		return "";
    	}
    	else if (TempString.equals("ShowTimeForm"))
    	{
    		Defines.clsClassName = ShowTimeForm.class ;
    		return "ShowTimeForm";
    	}
    	else if (TempString.equals("YearForm"))
    	{
    		Defines.clsClassName = PlateYearForm.class ;
    		return "PlateYearForm";
    	}
    	else
    	{
    		Messagebox.Message("I don't know where to go next.",dan);
    	}
       	
    	return "ERROR";
	  }
	 
	  static public String GetNextForm(String NextForm, Context dan) 
	  {
		int nRecord = 0;
		String TempString ;
		nRecord = WorkingStorage.GetLongVal(Defines.CurrentTicOrderVal, dan)+1;
		WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, nRecord, dan);
		
      	long TicorderExists = SearchData.GetFileSize("TICORDER.A");    		
    	if (TicorderExists == 0) // missing ticorder.a
    	{
    		Messagebox.Message("Unable to find TICORDER.A",dan);
    		return "ERROR";
    	}
    	
    	if (NextForm.equals(""))
    	{
        	TempString = SearchData.GetRecordNumberNoLength("TICORDER.A", nRecord, dan).trim();
        	if (TempString.equals("ERROR"))
        	{
        		Messagebox.Message("I don't know why there is an error.",dan);
        	}
        	else if (TempString.substring(0, 1).equals("*")) //we don't print as we go anymore
        	{
        		GetNextForm("",dan);
        		return "";
        	}
        	else if (TempString.equals("AddFeeForm"))
        	{
        		Defines.clsClassName = AddFeeForm.class ;
        		return "AddFeeForm";
        	} 
        	else if (TempString.equals("AddCO1Form"))
        	{
        		Defines.clsClassName = AdditionalComment1Form.class ;
        		return "AddCO1Form";
        	} 
        	else if (TempString.equals("AddCO2Form"))
        	{
        		Defines.clsClassName = AdditionalComment2Form.class ;
        		return "AddCO1Form"; 
        	}
        	else if (TempString.equals("AddCO3Form"))
        	{
        		Defines.clsClassName = AdditionalComment3Form.class ;
        		return "AddCO1Form";
        	}        	
        	else if (TempString.equals("BeatForm"))
        	{
        		Defines.clsClassName = BeatForm.class ;
        		return "AddFeeForm";
        	} 
        	else if (TempString.equals("BodyForm"))
        	{
        		Defines.clsClassName = BodyForm.class ;
        		return "BodyForm";
        	}
        	else if (TempString.equals("CameraForm"))
        	{
        		Defines.clsClassName = CameraForm.class ;
        		return "CameraForm";
        	}
        	else if (TempString.equals("ColorForm"))
        	{
        		Defines.clsClassName = ColorForm.class ;
        		return "ColorForm";
        	} 
        	else if (TempString.equals("CommentForm"))
        	{
        		Defines.clsClassName = CommentForm.class ;
        		return "CommentForm";
        	}         	
        	else if (TempString.equals("CustomDateForm"))
        	{
        		Defines.clsClassName = CustomDateForm.class ;
        		return "CustomDateForm";
        	} 
        	else if (TempString.equals("CustomTimeForm"))
        	{
        		Defines.clsClassName = CustomTimeForm.class ;
        		return "CustomTimeForm";
        	}         	
        	else if (TempString.equals("CrossStreetForm"))
        	{
        		Defines.clsClassName = CrossStreetForm.class ;
        		return "CrossStreetForm";
        	}        	
        	else if (TempString.equals("ChalkForm"))
        	{
        		if(WorkingStorage.GetCharVal(Defines.ChalkFlagVal, dan).equals("0"))
        		{
            		GetNextForm("",dan);
            		return "";
        		}
        		else
        		{
            		Defines.clsClassName = ChalkForm.class ;
            		return "ChalkForm";
        			
        		}
        	}  
        	else if (TempString.equals("DirectionForm"))
        	{
        		if (WorkingStorage.GetCharVal(Defines.StrictDirectionFlag, dan).equals("NO"))
        		{	
        			Defines.clsClassName = DirectionForm.class ;
        			return "DirectionForm";
        		}
        		else if (AllowDirection(dan)== true)
        		{	
        			Defines.clsClassName = DirectionForm.class ;
        			return "DirectionForm";
        		}
        		else
        		{
        			GetNextForm("", dan);
        			return "";
        		}        			
        	} 
        	else if (TempString.equals("DisplaySpaceForm"))
        	{
        		if(WorkingStorage.GetCharVal(Defines.ChalkFlagVal, dan).equals("0"))
        		{
            		GetNextForm("",dan);
            		return "";
        		}
        		else
        		{
            		Defines.clsClassName = DisplaySpaceForm.class ;
            		return "DisplaySpaceForm";
        		}
        	} 
        	else if (TempString.equals("GPSForm"))
        	{       		
            		if (WorkingStorage.GetCharVal(Defines.PrintViolDesc1Val, dan).substring(0,3).equals("GPS") 
            			|| WorkingStorage.GetCharVal(Defines.GPSSurveyVal, dan).trim().equals("GPS"))
            		{	
                		Defines.clsClassName = GPSForm.class ;
                		return "GPSForm";
            		}
            		else
            		{
            			GetNextForm("", dan);
            			return "";
            		}
        	}         	
        	else if (TempString.equals("LicColorForm"))
        	{
        		Defines.clsClassName = LicColorForm.class ;
        		return "LicColorForm";
        	} 
        	else if (TempString.equals("LGStreetSpinForm"))
        	{
       			Defines.clsClassName = LGStreetSpinForm.class ;
        		return "LGStreetSpinForm";
        	}          	
        	else if (TempString.equals("LGStreetForm"))
        	{
        		if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("PHXAIRPORT"))
        		{
        			Defines.clsClassName = LGStreetSpinForm.class ;
        		}
        		else
        		{
        			Defines.clsClassName = LGStreetForm.class ;
        		}
        		return "LGStreetForm";
        	} 
        	else if (TempString.equals("MakeForm"))
        	{
        		Defines.clsClassName = MakeForm.class ;
        		return "MakeForm";
        	}
        	else if (TempString.equals("MeterForm"))
        	{
        		Defines.clsClassName = MeterForm.class ;
        		return "MeterForm";
        	}           	
        	else if (TempString.equals("MonthForm"))
        	{
        		Defines.clsClassName = MonthForm.class ;
        		return "MonthForm";
        	} 
        	else if (TempString.equals("NewTypeForm"))
        	{
        		Defines.clsClassName = NewPlateTypeForm.class ;
        		return "NewPlateTypeForm";
        	} 
        	else if (TempString.equals("NumberForm"))
        	{
        		if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("RICHMOND"))
        		{
            		Defines.clsClassName = RichmondNumberForm.class ;
            		return "RichmondNumberForm";        			
        		}
        		else if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("HUNTWV"))
        		{
        			if (WorkingStorage.GetCharVal(Defines.MeterFlagVal, dan).equals("1"))
        			{
       			      	WorkingStorage.StoreCharVal(Defines.PrintNumberVal, "", dan);
        			    WorkingStorage.StoreCharVal(Defines.SaveNumberVal, "", dan);
        			    WorkingStorage.StoreCharVal(Defines.PrintStreetVal, "", dan);
        			    WorkingStorage.StoreCharVal(Defines.SaveStreetVal, "", dan);
        				GetNextForm("", dan);
            			return "";           			        				
        			}
        			else
        			{
        				Defines.clsClassName = NumberForm.class ;
                		return "NumberForm"; 
        			}
        		}        		
        		else
        		{
            		Defines.clsClassName = NumberForm.class ;
            		return "NumberForm";        			
        		}
        	} 
        	else if (TempString.equals("NYVinForm"))
        	{
        		Defines.clsClassName = NYVinForm.class ;
        		return "NYVinForm";
        	} 
        	else if (TempString.equals("PerformBarCode"))
        	{
        			GetNextForm("", dan);
        			return "";       			
        	} 
        	else if (TempString.equals("PerformFeedBack"))
        	{
        			GetNextForm("", dan);
        			return "";       			
        	}        	
        	else if (TempString.equals("PerformCheckPermit"))
        	{
                String PrintBoot = "";
                int OnList = 0;
                PrintBoot = "|X" + WorkingStorage.GetCharVal(Defines.SavePermitVal, dan);
                OnList = BootRoutines.GetBootList(PrintBoot, 1, 1,dan);
                if (OnList == 1)
                {
 	           	    Defines.clsClassName = DetailsForm.class ;
 	           	    return "DetailsForm";
                }
                else
                {
            		GetNextForm("",dan);
                }
        		return "";
        	}        	
        	else if (TempString.equals("PerformCheckBoot"))
        	{
                String PrintBoot = "";
                int OnList = 0;
                PrintBoot = "|" + WorkingStorage.GetCharVal(Defines.SaveStateVal,dan) + WorkingStorage.GetCharVal(Defines.SavePlateVal, dan);
                OnList = BootRoutines.GetBootList(PrintBoot, 1, 1,dan);
                if (OnList == 1)
                {
 	           	    Defines.clsClassName = DetailsForm.class ;
 	           	    return "DetailsForm";
                }
                else
                {
            		SaveTicket.SaveChecFile(dan);
            		GetNextForm("",dan);
                }
        		return "";
        	}
        	else if (TempString.equals("PerformNewPermit"))
        	{
                String SearchPermit = "";
                int OnList = 0;
                SearchPermit = WorkingStorage.GetCharVal(Defines.SavePermitVal,dan).trim();
                if (SearchPermit.equals(""))
                {
                	GetNextForm("",dan);
                	return "";
                }
                OnList = PermitRoutines.GetPermitList(SearchPermit,dan);
                if (OnList == 1)
                {
 	           	    Defines.clsClassName = NewPermitForm.class ;
 	           	    return "NewPermitForm";
                }
                else
                {
            		GetNextForm("",dan);
                }
        		return "";
        	}        	
        	else if (TempString.equals("PermitForm"))
        	{
        		Defines.clsClassName = PermitForm.class ;
        		return "PermitForm";
        	}         	
        	else if (TempString.equals("PlateForm"))
        	{
        		Defines.clsClassName = PlateForm.class ;
        		return "PlateForm";
        	}
        	else if (TempString.equals("PrintForm"))
        	{
        		if (WorkingStorage.GetCharVal(Defines.DefaultState, dan).equals("CA"))
        		{
/*        			if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("PCSCALIF")
        				|| WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("SANTACRUZ")
        				|| WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("REGPS"))*/
        			if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("PCSCALIF")
            				|| WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("REGPS"))        				
        			{
        				Defines.clsClassName = PrintSelectForm.class ;
            			return "PrintSelectForm";
        			}
        			else
        			{
        				Defines.clsClassName = LastFourForm.class ;
            			return "LastFourForm";
        			}
        		}
        		else
        		{
        			Defines.clsClassName = PrintSelectForm.class ;
        			return "PrintSelectForm";
        		}
        	}        	
        	else if (TempString.equals("StreetForm"))
        	{
        		if (WorkingStorage.GetCharVal(Defines.ClientName, dan).trim().equals("HUNTWV"))
        		{
        			if (WorkingStorage.GetCharVal(Defines.MeterFlagVal, dan).equals("1"))
        			{
            			Defines.clsClassName = MeterForm.class ;
            			return "MeterForm";  	
        			}
        			else
        			{
            			Defines.clsClassName = StreetForm.class ;
            			return "StreetForm";        				
        			}        			
        		}
        		else
        		{
        			Defines.clsClassName = StreetForm.class ;
        			return "StreetForm";
        		}
        	}
        	else if (TempString.equals("StreetTypeForm"))
        	{
        		Defines.clsClassName = StreetTypeForm.class ;
        		return "StreetTypeForm";
        	}         	
        	else if (TempString.equals("StemForm"))
        	{
        		if (WorkingStorage.GetCharVal(Defines.ChalkFlagVal, dan).equals("0"))
        		{
            		GetNextForm("",dan);
            		return "";
        		}
        		else
        		{	
        			Defines.clsClassName = StemForm.class ;
        			return "StemForm";
        		}
        	}
        	else if (TempString.equals("TypeForm"))
        	{
        		Defines.clsClassName = PlateTypeForm.class ;
        		return "PlateTypeForm";
        	}         	
        	else if (TempString.equals("YearForm"))
        	{
        		if (WorkingStorage.GetCharVal(Defines.SavePlateVal, dan).equals("TEMPTAG"))
        		{
            		GetNextForm("",dan);
            		return "";
        		}
        		else
        		{
            		Defines.clsClassName = PlateYearForm.class ;
            		return "PlateYearForm";	
        		}
        	}
        	else if (TempString.equals("ViolateForm"))
        	{
        		Defines.clsClassName = ViolateForm.class ;
        		return "ViolateForm";
        	}        	
        	else
        	{
        		Messagebox.Message("I don't know where to go next.",dan);
        	}
    	}
    	else
    	{
    		Messagebox.Message("Do the routine for opening up a specific form",dan);
    	}
    	return "ERROR";    
	  }

	  static public String GetNextChalkingForm(Context dan) 
	  {
		int nRecord = 0;
		String TempString ="";	
		nRecord = WorkingStorage.GetLongVal(Defines.CurrentChaOrderVal, dan)+1;
		if (nRecord == 1) // 1st time through clear out the stored chalk values
		{
			WorkingStorage.StoreCharVal(Defines.SaveChalkPlateVal, "",dan);
            WorkingStorage.StoreCharVal(Defines.SaveChalkStreetVal, "",dan);
            WorkingStorage.StoreCharVal(Defines.SaveChalkDirectionVal, "",dan);
            WorkingStorage.StoreCharVal(Defines.SaveChalkMeterVal, "",dan);
            WorkingStorage.StoreCharVal(Defines.SaveChalkStateVal, "",dan);
            WorkingStorage.StoreCharVal(Defines.SaveChalkStemVal, "",dan);
            WorkingStorage.StoreCharVal(Defines.SaveChalkNumberVal, "",dan);
            WorkingStorage.StoreCharVal(Defines.PrintChalkStreetVal, "",dan);
            WorkingStorage.StoreCharVal(Defines.PrintChalkDirectionVal, "",dan);
		}		
		WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, nRecord, dan);
		
      	long ChaorderExists = SearchData.GetFileSize("CHAORDER.A");    		
    	if (ChaorderExists == 0) // missing CHAorder.a
    	{
    		Messagebox.Message("CHAORDER.A File Missing!",dan);
    		return "ERROR";
    	}
    	
    	TempString = SearchData.GetRecordNumberNoLength("CHAORDER.A", nRecord, dan).trim();
    	if (TempString.equals("ERROR"))
    	{
    		Messagebox.Message("I don't know why there is an error.",dan);
    	}
    	else if (TempString.equals("DirectionForm"))
    	{
    		if (WorkingStorage.GetCharVal(Defines.StrictDirectionFlag, dan).equals("NO"))
    		{	
    			Defines.clsClassName = DirectionForm.class ;
    			return "DirectionForm";
    		}
    		else if (AllowDirection(dan)== true)
    		{	
    			Defines.clsClassName = DirectionForm.class ;
    			return "DirectionForm";
    		}
    		else
    		{
    			GetNextChalkingForm(dan);
    			return "";
    		}        			
    	}   
    	else if (TempString.equals("MeterForm"))
    	{
    		Defines.clsClassName = MeterForm.class ;
    		return "MeterForm";
    	}    	
    	else if (TempString.equals("NumberForm"))
    	{
    		Defines.clsClassName = NumberForm.class ;
    		return "NumberForm";
    	}  
    	else if (TempString.equals("PerformCheckBoot"))
    	{
            String PrintBoot = "";
            int OnList = 0;
            PrintBoot = "|" + WorkingStorage.GetCharVal(Defines.SaveStateVal,dan) + WorkingStorage.GetCharVal(Defines.SavePlateVal, dan);
            OnList = BootRoutines.GetBootList(PrintBoot, 1, 1,dan);
            if (OnList == 1)
            {
	           	Defines.clsClassName = DetailsForm.class ;
	           	return "DetailsForm";
            }
            else
            {
        		SaveTicket.SaveChecFile(dan);
        		GetNextChalkingForm(dan);
            }
    		return "";
    	}    	
    	else if (TempString.equals("PlateForm"))
    	{
    		MustDoSetup.WriteMustDoSetupDate(dan);
    		Defines.clsClassName = PlateForm.class ;
    		return "PlateForm";
    	}
    	else if (TempString.equals("StreetForm"))
    	{
    		MustDoSetup.WriteMustDoSetupDate(dan);
    		Defines.clsClassName = StreetForm.class ;
    		return "StreetForm";
    	}
    	else if (TempString.equals("StateForm"))
    	{
    		Defines.clsClassName = StateForm.class ;
    		return "StateForm";
    	}
    	else if (TempString.equals("StemForm"))
    	{
    		Defines.clsClassName = StemForm.class ;
    		return "StemForm";
    	}    	
    	else if (TempString.equals("MultipleChalksForm"))
    	{
    		Defines.clsClassName = MultipleChalksForm.class ;
    		return "MultipleChalksForm";
    	}    	
    	else if (TempString.equals("ChalkTimeForm"))
    	{
    		MustDoSetup.WriteMustDoSetupDate(dan);
    		Defines.clsClassName = ChalkTimeForm.class ;
    		return "ChalkTimeForm";
    	}
    	else
    	{
    		Messagebox.Message("I don't know where to go next.",dan);
    	}
       	
    	return "ERROR";
	  }

	  static public String GetNextRatesForm(Context dan) 
	  {
		int RecordNumber = 17;
		String TempString ;
		RecordNumber = WorkingStorage.GetLongVal(Defines.CurrentRatesOrderVal, dan);
		RecordNumber = RecordNumber - 1;
		WorkingStorage.StoreLongVal(Defines.CurrentRatesOrderVal, RecordNumber, dan);
		
		String ReturnString = "ERROR";
		
		switch (RecordNumber) {
		  case 16: // daily rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBoxDailyVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);
			  	}
		        break;
		        
		  case 15: // 9 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox9HoursVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 14: // 8 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox8HoursVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		        
		  case 13: // 7 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox7HoursVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 12: // 6 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox6HoursVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 11: // 5 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox5HoursVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 10: // 4 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox4HoursVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 9: // 3 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox3HoursVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 8: // 2 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox2HoursVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 7: // 100 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox100MinVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 6: // 90 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox90MinVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 5: // 80 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox80MinVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 4: // 60 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox60MinVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 3: // 40 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox40MinVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 2: // 30 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox30MinVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  case 1: // 20 Minute rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox20MinVal, dan).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(dan);			  		
			  	}
		        break;
		        
		  default:
	    		Defines.clsClassName = BuildingForm.class ;
	    		ReturnString =  "BuildingForm";
		}
		
		return "GOOD";
       	
	  }
	  
	  public static Boolean AllowDirection(Context dan)
	  {
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetNVal, dan).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetSVal, dan).equals("Y"))
			  return true;

		  if (WorkingStorage.GetCharVal(Defines.SaveStreetEVal, dan).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetWVal, dan).equals("Y"))
			  return true;

		  if (WorkingStorage.GetCharVal(Defines.SaveStreetNWVal, dan).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetNEVal, dan).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetSWVal, dan).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetSEVal, dan).equals("Y"))
			  return true;
		  
		  return false;
	  }
	

}
