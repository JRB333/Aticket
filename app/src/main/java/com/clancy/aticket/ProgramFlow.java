package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Context;


public class ProgramFlow extends ActivityGroup {
	
	  static public String GetNextSetupForm(Context cntxt)
	  {
		int nRecord = 0;
		String TempString ;
		nRecord = WorkingStorage.GetLongVal(Defines.CurrentOffOrderVal, cntxt)+1;
		WorkingStorage.StoreLongVal(Defines.CurrentOffOrderVal, nRecord, cntxt);
        WorkingStorage.StoreCharVal(Defines.RefreshCalledBy, "", cntxt);  // ########

      	long OfforderExists = SearchData.GetFileSize("OFFORDER.A");    		
    	if (OfforderExists == 0) // missing OFForder.a
    	{
    		Messagebox.Message("OFFORDER.A File Missing!",cntxt);
    		return "ERROR";
    	}

    	TempString = SearchData.GetRecordNumberNoLength("OFFORDER.A", nRecord, cntxt).trim();
    	if (TempString.equals("ERROR")) {
    		Messagebox.Message("I don't know why there is an error.",cntxt);
    	}
    	else if (TempString.equals("GetBootForm")) {
    		Defines.clsClassName = GetBootForm.class ;
    		return "GetBootForm";
    	}
    	else if (TempString.equals("BadgeForm")) {
    		Defines.clsClassName = BadgeForm.class ;
    		return "BadgeForm";
    	}       	
    	else if (TempString.equals("BeatForm")) {
    		Defines.clsClassName = BeatForm.class ;
    		return "BeatForm";
    	}   
    	else if (TempString.equals("CourtForm")) {
    		Defines.clsClassName = CourtDateForm.class ;
    		return "CourtForm";
    	}      	
    	else if (TempString.equals("DeptForm")) {
    		Defines.clsClassName = DeptForm.class ;
    		return "DeptForm";
    	}  
    	else if (TempString.equals("EmailForm")) {
    		MustDoSetup.WriteMustDoSetupDate(cntxt);
    		Defines.clsClassName = EmailForm.class ;
    		return "EmailForm";
    	} 
    	else if (TempString.equals("LanguageForm") || TempString.equals("LanguageForn")) {
			//Both Form Names here to handle misspelling in menu.t on old handhelds
    		Defines.clsClassName = LanguageForm.class ;
    		return "LanguageForm";
    	}
    	else if (TempString.equals("MainForm")) {
			MustDoSetup.WriteMustDoSetupDate(cntxt);

			WorkingStorage.StoreCharVal(Defines.PreviousMenu, "", cntxt);
			Defines.clsClassName = SelFuncForm.class ;
			return "SelFuncForm";
    	}
    	else if (TempString.equals("NextLastForm")) {
    		Defines.clsClassName = NextLastForm.class ;
    		return "NextLastForm";
    	}
    	else if (TempString.equals("OfficerForm")) {
    		Defines.clsClassName = OfficerForm.class ;
    		return "OfficerForm";
    	}       	
    	else if (TempString.equals("ShowDateForm")) {
    		GetNextSetupForm(cntxt);
    		return "";
    	}
    	else if (TempString.equals("ShowTimeForm")) {
    		Defines.clsClassName = ShowTimeForm.class ;
    		return "ShowTimeForm";
    	}
		else if (TempString.equals("SignForm"))	{
			// Get Saved-off 'SG' value read in from CUSTOM.A
			String SignatureCapture = WorkingStorage.GetCharVal(Defines.CaptureSignature, cntxt).toString();
			if (SignatureCapture.equals("YES")) {
				// Get Signature Input
				Defines.clsClassName = SignatureCaptureForm.class ;
				return "SignatureCaptureForm";
			} else {
				// CUSTOM.A 'SG' parameter Absent or not set to 'YES'
				// Therefore Ignore and Go On To Next Routine
				GetNextSetupForm(cntxt);
				return "";
			}
		}
    	else if (TempString.equals("YearForm")) {
    		Defines.clsClassName = PlateYearForm.class ;
    		return "PlateYearForm";
    	} else {
    		Messagebox.Message("I don't know where to go next.",cntxt);
    	}
       	
    	return "ERROR";
	  }
	 
	  static public String GetNextForm(String NextForm, Context cntxt)
	  {
		int nRecord = 0;
		String TempString ;
		nRecord = WorkingStorage.GetLongVal(Defines.CurrentTicOrderVal, cntxt)+1;
		WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, nRecord, cntxt);

		// ####### NEW Code Here - JRB #######
		String PreviousForm = Defines.clsClassName.toString();
		String NumericOnlyRegEx = "^[0-9]{1,8}$";  // RegEx for Numeric Only (8 Char Max)
		String PlateNum = WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt);

      	long TicorderExists = SearchData.GetFileSize("TICORDER.A");
    	if (TicorderExists == 0) // missing ticorder.a
    	{
    		Messagebox.Message("Unable to find TICORDER.A",cntxt);
    		return "ERROR";
    	}

		if (NextForm.equals(""))
    	{
        	TempString = SearchData.GetRecordNumberNoLength("TICORDER.A", nRecord, cntxt).trim();
        	if (TempString.equals("ERROR"))
        	{
        		Messagebox.Message("I don't know why there is an error.",cntxt);
        	}
        	else if (TempString.substring(0, 1).equals("*")) //we don't print as we go anymore
        	{
        		GetNextForm("",cntxt);
        		return "";
			}
			else if (PreviousForm.contains("StateForm")
					&& WorkingStorage.GetCharVal(Defines.DefaultState, cntxt).trim().equals("VA")
					&& PlateNum.matches(NumericOnlyRegEx))
			{
			// ####### NEW Code Here - JRB #######
			// PreviousForm = StateForm & Default State = VA & Plate Number is Numeric Only --> Display PlateType Form
			//      It is like Inserting a new line into  TicOrder.A   for the 'Flow' to conditionally follow
			//      and then get 'back on track'
			   WorkingStorage.StoreLongVal(Defines.CurrentTicOrderVal, nRecord-1, cntxt);  // Set Record Pointer To Resume Flow on Next Pass

			   Defines.clsClassName = PlateTypeForm.class ;
			   return "PlateTypeForm";
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
        		if(WorkingStorage.GetCharVal(Defines.ChalkFlagVal, cntxt).equals("0"))
        		{
            		GetNextForm("",cntxt);
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
        		if (WorkingStorage.GetCharVal(Defines.StrictDirectionFlag, cntxt).equals("NO"))
        		{	
        			Defines.clsClassName = DirectionForm.class ;
        			return "DirectionForm";
        		}
        		else if (AllowDirection(cntxt)== true)
        		{	
        			Defines.clsClassName = DirectionForm.class ;
        			return "DirectionForm";
        		}
        		else
        		{
        			GetNextForm("", cntxt);
        			return "";
        		}        			
        	} 
        	else if (TempString.equals("DisplaySpaceForm"))
        	{
        		if(WorkingStorage.GetCharVal(Defines.ChalkFlagVal, cntxt).equals("0"))
        		{
            		GetNextForm("",cntxt);
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
            		if (WorkingStorage.GetCharVal(Defines.PrintViolDesc1Val, cntxt).substring(0,3).equals("GPS")
            			|| WorkingStorage.GetCharVal(Defines.GPSSurveyVal, cntxt).trim().equals("GPS"))
            		{	
                		Defines.clsClassName = GPSForm.class ;
                		return "GPSForm";
            		}
            		else
            		{
            			GetNextForm("", cntxt);
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
        		if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("PHXAIRPORT"))
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
        		if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("RICHMOND"))
        		{
            		Defines.clsClassName = RichmondNumberForm.class ;
            		return "RichmondNumberForm";        			
        		}
        		else if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("HUNTWV"))
        		{
        			if (WorkingStorage.GetCharVal(Defines.MeterFlagVal, cntxt).equals("1"))
        			{
       			      	WorkingStorage.StoreCharVal(Defines.PrintNumberVal, "", cntxt);
        			    WorkingStorage.StoreCharVal(Defines.SaveNumberVal, "", cntxt);
        			    WorkingStorage.StoreCharVal(Defines.PrintStreetVal, "", cntxt);
        			    WorkingStorage.StoreCharVal(Defines.SaveStreetVal, "", cntxt);
        				GetNextForm("", cntxt);
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
        			GetNextForm("", cntxt);
        			return "";       			
        	} 
        	else if (TempString.equals("PerformFeedBack"))
        	{
        			GetNextForm("", cntxt);
        			return "";       			
        	}        	
        	else if (TempString.equals("PerformCheckPermit"))
        	{
                String PrintBoot = "";
                int OnList = 0;
                PrintBoot = "|X" + WorkingStorage.GetCharVal(Defines.SavePermitVal, cntxt);
                OnList = BootRoutines.GetBootList(PrintBoot, 1, 1,cntxt);
                if (OnList == 1)
                {
 	           	    Defines.clsClassName = DetailsForm.class ;
 	           	    return "DetailsForm";
                }
                else
                {
            		GetNextForm("",cntxt);
                }
        		return "";
        	}        	
        	else if (TempString.equals("PerformCheckBoot"))
        	{
                String PrintBoot = "";
                int OnList = 0;
                PrintBoot = "|" + WorkingStorage.GetCharVal(Defines.SaveStateVal,cntxt) + WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt);
                OnList = BootRoutines.GetBootList(PrintBoot, 1, 1,cntxt);
                if (OnList == 1)
                {
 	           	    Defines.clsClassName = DetailsForm.class ;
 	           	    return "DetailsForm";
                }
                else
                {
            		SaveTicket.SaveChecFile(cntxt);
            		GetNextForm("", cntxt);
                }

				return "";
        	}
        	else if (TempString.equals("PerformNewPermit"))
        	{
                String SearchPermit = "";
                int OnList = 0;
                SearchPermit = WorkingStorage.GetCharVal(Defines.SavePermitVal,cntxt).trim();
                if (SearchPermit.equals(""))
                {
                	GetNextForm("",cntxt);
                	return "";
                }
                OnList = PermitRoutines.GetPermitList(SearchPermit,cntxt);
                if (OnList == 1)
                {
 	           	    Defines.clsClassName = NewPermitForm.class ;
 	           	    return "NewPermitForm";
                }
                else
                {
            		GetNextForm("",cntxt);
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
				WorkingStorage.StoreCharVal(Defines.SavePlateVal, "xxxxx",cntxt);  // Clear Any Previous Values

        		Defines.clsClassName = PlateForm.class ;
        		return "PlateForm";
        	}
        	else if (TempString.equals("PrintForm"))
        	{
        		if (WorkingStorage.GetCharVal(Defines.DefaultState, cntxt).equals("CA"))
        		{
/*        			if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("PCSCALIF")
        				|| WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("SANTACRUZ")
        				|| WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))*/
        			if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("PCSCALIF")
            				|| WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))
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
        		if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("HUNTWV"))
        		{
        			if (WorkingStorage.GetCharVal(Defines.MeterFlagVal, cntxt).equals("1"))
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
        		if (WorkingStorage.GetCharVal(Defines.ChalkFlagVal, cntxt).equals("0"))
        		{
            		GetNextForm("",cntxt);
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
        		if (WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt).equals("TEMPTAG"))
        		{
            		GetNextForm("",cntxt);
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
        		Messagebox.Message("I don't know where to go next.",cntxt);
        	}
    	}
    	else
    	{
    		Messagebox.Message("Do the routine for opening up a specific form",cntxt);
    	}
    	return "ERROR";    
	  }

	  static public String GetNextChalkingForm(Context cntxt)
	  {
		int nRecord = 0;
		String TempString ="";	
		nRecord = WorkingStorage.GetLongVal(Defines.CurrentChaOrderVal, cntxt) + 1;
		if (nRecord == 1) // 1st time through clear out the stored chalk values
		{
			WorkingStorage.StoreCharVal(Defines.SaveChalkPlateVal, "",cntxt);
            WorkingStorage.StoreCharVal(Defines.SaveChalkStreetVal, "",cntxt);
            WorkingStorage.StoreCharVal(Defines.SaveChalkDirectionVal, "",cntxt);
            WorkingStorage.StoreCharVal(Defines.SaveChalkMeterVal, "",cntxt);
            WorkingStorage.StoreCharVal(Defines.SaveChalkStateVal, "",cntxt);
            WorkingStorage.StoreCharVal(Defines.SaveChalkStemVal, "",cntxt);
            WorkingStorage.StoreCharVal(Defines.SaveChalkNumberVal, "",cntxt);
            WorkingStorage.StoreCharVal(Defines.PrintChalkStreetVal, "",cntxt);
            WorkingStorage.StoreCharVal(Defines.PrintChalkDirectionVal, "",cntxt);

			WorkingStorage.StoreCharVal(Defines.MenuProgramVal,Defines.ChalkMenu, cntxt);

			WorkingStorage.StoreCharVal(Defines.TxfrPlateVal, "",cntxt);
			WorkingStorage.StoreCharVal(Defines.TxfrStateVal, "",cntxt);
			WorkingStorage.StoreCharVal(Defines.TxfrDirVal, "",cntxt);
			WorkingStorage.StoreCharVal(Defines.TxfrDirCodeVal, "",cntxt);
			WorkingStorage.StoreCharVal(Defines.TxfrNumVal, "",cntxt);
			WorkingStorage.StoreCharVal(Defines.TxfrStreetVal, "",cntxt);
			WorkingStorage.StoreCharVal(Defines.TxfrChalkTime, "", cntxt);
			WorkingStorage.StoreCharVal(Defines.TxfrSpace, "", cntxt);
			WorkingStorage.StoreCharVal(Defines.ReturnToChalk, "", cntxt);
			WorkingStorage.StoreCharVal(Defines.ReturnToSurvey, "", cntxt);
			WorkingStorage.StoreCharVal(Defines.RefreshCalledBy, "", cntxt);  // ########
		}
		WorkingStorage.StoreLongVal(Defines.CurrentChaOrderVal, nRecord, cntxt);
		
      	long ChaorderExists = SearchData.GetFileSize("CHAORDER.A");    		
    	if (ChaorderExists == 0) // missing CHAorder.a
    	{
    		Messagebox.Message("CHAORDER.A File Missing!",cntxt);
    		return "ERROR";
    	}
    	
    	TempString = SearchData.GetRecordNumberNoLength("CHAORDER.A", nRecord, cntxt).trim();
    	if (TempString.equals("ERROR"))
    	{
    		Messagebox.Message("I don't know why there is an error.",cntxt);
    	}
    	else if (TempString.equals("DirectionForm"))
    	{
    		if (WorkingStorage.GetCharVal(Defines.StrictDirectionFlag, cntxt).equals("NO"))
    		{	
    			Defines.clsClassName = DirectionForm.class ;
    			return "DirectionForm";
    		}
    		else if (AllowDirection(cntxt)== true)
    		{	
    			Defines.clsClassName = DirectionForm.class ;
    			return "DirectionForm";
    		}
    		else
    		{
    			GetNextChalkingForm(cntxt);
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
            PrintBoot = "|" + WorkingStorage.GetCharVal(Defines.SaveStateVal,cntxt) + WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt);
            OnList = BootRoutines.GetBootList(PrintBoot, 1, 1,cntxt);
            if (OnList == 1)
            {
	           	Defines.clsClassName = DetailsForm.class ;
	           	return "DetailsForm";
            }
            else
            {
        		SaveTicket.SaveChecFile(cntxt);
        		GetNextChalkingForm(cntxt);
            }
    		return "";
    	}    	
    	else if (TempString.equals("PlateForm"))
    	{
    		MustDoSetup.WriteMustDoSetupDate(cntxt);
    		Defines.clsClassName = PlateForm.class ;
    		return "PlateForm";
    	}
    	else if (TempString.equals("StreetForm"))
    	{
    		MustDoSetup.WriteMustDoSetupDate(cntxt);
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
    		MustDoSetup.WriteMustDoSetupDate(cntxt);
    		Defines.clsClassName = ChalkTimeForm.class ;
    		return "ChalkTimeForm";
    	}
    	else
    	{
    		Messagebox.Message("I don't know where to go next.",cntxt);
    	}
       	
    	return "ERROR";
	  }

	  static public String GetNextRatesForm(Context cntxt)
	  {
		int RecordNumber = 17;
		String TempString ;

		RecordNumber = WorkingStorage.GetLongVal(Defines.CurrentRatesOrderVal, cntxt);
		RecordNumber = RecordNumber - 1;
		WorkingStorage.StoreLongVal(Defines.CurrentRatesOrderVal, RecordNumber, cntxt);
		
		String ReturnString = "ERROR";
		
		switch (RecordNumber) {
		  case 16: // daily rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBoxDailyVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 15: // 9 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox9HoursVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 14: // 8 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox8HoursVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		        
		  case 13: // 7 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox7HoursVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 12: // 6 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox6HoursVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 11: // 5 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox5HoursVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 10: // 4 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox4HoursVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 9: // 3 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox3HoursVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 8: // 2 hour rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox2HoursVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 7: // 100 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox100MinVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 6: // 90 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox90MinVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 5: // 80 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox80MinVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 4: // 60 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox60MinVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 3: // 40 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox40MinVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 2: // 30 min rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox30MinVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  case 1: // 20 Minute rate
			  	if(WorkingStorage.GetCharVal(Defines.SaveHBox20MinVal, cntxt).equals("1"))
			  	{
		    		Defines.clsClassName = RatesForm.class ;
		    		ReturnString =  "RatesForm";
			  	}
			  	else
			  	{
			  		GetNextRatesForm(cntxt);
			  	}
		        break;
		        
		  default:
	    		Defines.clsClassName = BuildingForm.class ;
	    		ReturnString =  "BuildingForm";
		}
		
		return "GOOD";
       	
	  }
	  
	  public static Boolean AllowDirection(Context cntxt)
	  {
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetNVal, cntxt).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetSVal, cntxt).equals("Y"))
			  return true;

		  if (WorkingStorage.GetCharVal(Defines.SaveStreetEVal, cntxt).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetWVal, cntxt).equals("Y"))
			  return true;

		  if (WorkingStorage.GetCharVal(Defines.SaveStreetNWVal, cntxt).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetNEVal, cntxt).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetSWVal, cntxt).equals("Y"))
			  return true;
		  
		  if (WorkingStorage.GetCharVal(Defines.SaveStreetSEVal, cntxt).equals("Y"))
			  return true;
		  
		  return false;
	  }
}
