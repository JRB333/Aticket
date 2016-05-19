package com.clancy.aticket;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;


public class ReadLayout {

	public static String PrintALittleBit(String LayoutFileName, Context cntxt, OutputStream outputStream)
	{
		String PrintCompleteTicket = "";
		int SpacesOver = 0;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("RICHMOND"))
		{
			FeedPaperDots2(1, outputStream);
			OCR.PrintTheBarCode(WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt),outputStream, cntxt);
			OCR.PrintFeedBack(outputStream, cntxt);
			PrintThis("      ", outputStream);
			FeedPaperDots2(1, outputStream);
		}

		File file = new File("/data/data/com.clancy.aticket/files/",LayoutFileName);
		if(file.exists())   // check if file exist
		{
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String Line;
				int i = 1;
				int ReadRecord = 1;
				while ((Line = br.readLine()) != null )
				{
					if (ReadRecord == 1)
					{
						WorkingStorage.StoreCharVal(Defines.PictureCitationDotsVal, Line.substring(1).trim(),cntxt);
						ReadRecord++;
					}
					if (Line.substring(0, 1).equals("*"))
					{
						// These are Printed Data Elements
						if (Line.substring(1).trim().equals("Citation"))
						{
							if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, cntxt).trim().equals(Defines.ReprintMenu))
							{
								PrintThisTrimmed(".", outputStream);
							}
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))
							{
								PrintThisTrimmed("6", outputStream);
							}
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("SBPD"))
							{
								PrintThisTrimmed("P", outputStream);
							}
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("SBWATERFRONT"))
							{
								PrintThisTrimmed("B", outputStream);
							}

							if (!WorkingStorage.GetCharVal(Defines.PrefixVal, cntxt).equals(""))
							{
								PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrefixVal, cntxt), outputStream);
							}

							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("PAY2PARK"))
							{
								String SplitCitation = "";
								if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, cntxt).trim().equals(Defines.ReprintMenu))
								{
									SplitCitation = WorkingStorage.GetCharVal(Defines.ReprintCitationVal, cntxt);
								}
								else
								{
									SplitCitation = WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt);
								}
								SplitCitation = SplitCitation.substring(0, 3)+ " "+SplitCitation.substring(3);
								SpacesOver = SpacesOver + PrintThisTrimmed(SplitCitation, outputStream);
							}
							else if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("RICHMOND"))
							{
								String SplitCitation = "";
								if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, cntxt).trim().equals(Defines.ReprintMenu))
								{
									SplitCitation = WorkingStorage.GetCharVal(Defines.ReprintCitationVal, cntxt);
								}
								else
								{
									SplitCitation = WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt);
								}
								SplitCitation = SplitCitation.substring(1);
								SpacesOver = SpacesOver + PrintThisTrimmed(SplitCitation, outputStream);
							}
							else if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("CENTRALVABEACH"))
							{
								String SplitCitation = "";
								if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, cntxt).trim().equals(Defines.ReprintMenu))
								{
									SplitCitation = WorkingStorage.GetCharVal(Defines.ReprintCitationVal, cntxt);
								}
								else
								{
									SplitCitation = WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt);
								}
								SplitCitation = "T"+SplitCitation.substring(1);
								SpacesOver = SpacesOver + PrintThisTrimmed(SplitCitation, outputStream);
							}
							else
							{
								if (WorkingStorage.GetCharVal(Defines.MenuProgramVal, cntxt).trim().equals(Defines.ReprintMenu))
								{
									SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.ReprintCitationVal, cntxt), outputStream);
								}
								else
								{
									SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt), outputStream);
								}
							}
						} // end Line.substring(1).trim().equals("Citation")
						if (Line.substring(1).trim().equals("Signature")
								&& WorkingStorage.GetCharVal(Defines.CaptureSignature, cntxt).toString().equals("YES")) {
							String BadgeNo = WorkingStorage.GetCharVal(Defines.PrintBadgeVal,cntxt).trim();
							String SigFileName = WorkingStorage.GetCharVal(Defines.SignatureFile, cntxt);
							if (SigFileName.contains(BadgeNo.trim())) {
								File SigFile = new File(cntxt.getFilesDir().getPath(), SigFileName);
								if (SigFile.exists()) {
									// Signature Image File Exists For This Officer's BadgeNo, Print It
									PrintSignatureFast.PrintSignature(SigFileName, cntxt, outputStream);
								}
							}
						} // end Line.substring(1).trim().equals("Signature")
						if (Line.substring(1).trim().equals("Date"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintDateVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Time"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintTimeStartVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Officer"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintOfficerVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Badge"))
						{
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))
								PrintThis("      ", outputStream);
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintBadgeVal, cntxt), outputStream);
						}
						if (Line.substring(1).trim().equals("Ordinance"))
						{
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintOrdinanceVal, cntxt), outputStream);
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("GRANDRAPIDS"))
							{
								FeedPaperDots2(1, outputStream);
								OCR.PrintTheBarCode(WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt),outputStream, cntxt);
								OCR.PrintFeedBack(outputStream, cntxt);
								PrintThis("      ", outputStream);
								FeedPaperDots2(1, outputStream);
							}
						}
						if (Line.substring(1).trim().equals("ViolDesc1"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintViolDesc1Val, cntxt), outputStream);
						if (Line.substring(1).trim().equals("ViolDesc2"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintViolDesc2Val, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Comment1"))
						{
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))
							{
								outputStream.write(0x1B);
								outputStream.write(0x77);
								outputStream.write(0x01); // CHAR SPACING 7-9
							}
							if (WorkingStorage.GetCharVal(Defines.PrintComment1Val, cntxt).equals("NO COMMENT"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(" ", outputStream);
							}
							else
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintComment1Val, cntxt), outputStream);
							}
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))
							{
								outputStream.write(0x1B);
								outputStream.write(0x77);
								outputStream.write(0x00); // CHAR SPACING 7-9
							}
						}
						if (Line.substring(1).trim().equals("Comment2"))
						{
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))
							{
								outputStream.write(0x1B);
								outputStream.write(0x77);
								outputStream.write(0x01); // CHAR SPACING 7-9
							}
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintComment2Val, cntxt), outputStream);
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))
							{
								outputStream.write(0x1B);
								outputStream.write(0x77);
								outputStream.write(0x00); // CHAR SPACING 7-9
							}
						}
						if (Line.substring(1).trim().equals("Comment3"))
						{
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))
							{
								outputStream.write(0x1B);
								outputStream.write(0x77);
								outputStream.write(0x01); // CHAR SPACING 7-9
							}
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintComment3Val, cntxt), outputStream);
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("REGPS"))
							{
								outputStream.write(0x1B);
								outputStream.write(0x77);
								outputStream.write(0x00); // CHAR SPACING 7-9
							}
						}
						if (Line.substring(1).trim().equals("Fine1"))
						{
							if (!WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt).trim().equals(""))
							{
								//Toast.makeText(cntxt, "Need to do Add Fee Routine", 2000);
								MiscFunctions.AddFeeFine1(cntxt);
							}
							//if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("STEAMBOAT") && WorkingStorage.GetCharVal(Defines.PrintFine1Val, cntxt).trim().equals("0.00"))
							if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("STEAMBOAT") && WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, cntxt).trim().equals("0.00"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed("WARNING", outputStream);
							}
							else if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("LONGVIEW") && WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, cntxt).trim().equals("0.00"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed("NO PENALTY IS DUE", outputStream);
							}
							else if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("WINCHESTER"))
							{
								if(!WorkingStorage.GetCharVal(Defines.MeterFlagVal, cntxt).equals("1"))
								{
									//SpacesOver = SpacesOver + PrintThisTrimmed("$ " + WorkingStorage.GetCharVal(Defines.PrintFine1Val, cntxt), outputStream);
									SpacesOver = SpacesOver + PrintThisTrimmed("$ " + WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, cntxt).trim(), outputStream);
								}
							}
							else
							{
								//SpacesOver = SpacesOver + PrintThisTrimmed("$ " + WorkingStorage.GetCharVal(Defines.PrintFine1Val, cntxt), outputStream);
								SpacesOver = SpacesOver + PrintThisTrimmed("$ " + WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, cntxt).trim(), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("Fine2"))
						{

							if (!WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt).trim().equals(""))
							{
								//Toast.makeText(cntxt, "Need to do Add Fee Routine", 2000);
								MiscFunctions.AddFeeFine2(cntxt);
							}
							//SpacesOver = SpacesOver + PrintThisTrimmed("$ " + WorkingStorage.GetCharVal(Defines.PrintFine2Val, cntxt), outputStream);
							if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("COLUMBIA"))
							{
								if(WorkingStorage.GetCharVal(Defines.PrintFine2LargeVal, cntxt).trim().equals("0.00"))
								{
									SpacesOver = SpacesOver + PrintThisTrimmed("", outputStream);
								}
								else
								{
									SpacesOver = SpacesOver + PrintThisTrimmed("If paid after 15 days: $ "+WorkingStorage.GetCharVal(Defines.PrintFine2LargeVal, cntxt).trim(), outputStream);
								}
							}
							else if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("LONGVIEW") && WorkingStorage.GetCharVal(Defines.PrintFine2LargeVal, cntxt).trim().equals("0.00"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(" ", outputStream);
							}
							else if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("HUNTWV"))
							{
								Calendar c = Calendar.getInstance();
								c.add(Calendar.DATE, Integer.valueOf(8)); // 8 days
								SimpleDateFormat df;
								df = new SimpleDateFormat("MM/dd/yyyy");
								String DefaultDate = df.format(c.getTime());
								SpacesOver = SpacesOver + PrintThisTrimmed("On or After "+ DefaultDate + ": $ " + WorkingStorage.GetCharVal(Defines.PrintFine2LargeVal, cntxt).trim(), outputStream);
							}
							else if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("PGCOUNTY"))
							{
								Calendar c = Calendar.getInstance();
								c.add(Calendar.DATE, Integer.valueOf(30)); // 30 days
								SimpleDateFormat df;
								df = new SimpleDateFormat("MM/dd/yyyy");
								String DefaultDate = df.format(c.getTime());
								SpacesOver = SpacesOver + PrintThisTrimmed("After " + DefaultDate + ": $ " + WorkingStorage.GetCharVal(Defines.PrintFine2LargeVal, cntxt).trim(), outputStream);
							}
							else
							{
								SpacesOver = SpacesOver + PrintThisTrimmed("$ " + WorkingStorage.GetCharVal(Defines.PrintFine2LargeVal, cntxt).trim(), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("Fine3"))
						{
							if (!WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt).trim().equals(""))
							{
								//Toast.makeText(cntxt, "Need to do Add Fee Routine", 2000);
								MiscFunctions.AddFeeFine3(cntxt);
							}
							//SpacesOver = SpacesOver + PrintThisTrimmed("$ " + WorkingStorage.GetCharVal(Defines.PrintFine3Val, cntxt), outputStream);
							if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("LONGVIEW") && WorkingStorage.GetCharVal(Defines.PrintFine3LargeVal, cntxt).trim().equals("0.00"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(" ", outputStream);
							}
							else if (WorkingStorage.GetCharVal(Defines.ClientName,cntxt).equals("PGCOUNTY"))
							{
								Calendar c = Calendar.getInstance();
								c.add(Calendar.DATE, Integer.valueOf(60)); // 60 days
								SimpleDateFormat df;
								df = new SimpleDateFormat("MM/dd/yyyy");
								String DefaultDate = df.format(c.getTime());
								SpacesOver = SpacesOver + PrintThisTrimmed("After " + DefaultDate + ": $ " + WorkingStorage.GetCharVal(Defines.PrintFine3LargeVal, cntxt).trim(), outputStream);
							}
							else
							{
								SpacesOver = SpacesOver + PrintThisTrimmed("$ "+WorkingStorage.GetCharVal(Defines.PrintFine3LargeVal, cntxt).trim(), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("DOW"))
						{
							SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
							Date d = new Date();
							String dayOfTheWeek = sdf.format(d);
							SpacesOver = SpacesOver + PrintThisTrimmed(dayOfTheWeek, outputStream);
						}
						if (Line.substring(1).trim().equals("Warning"))
						{
							//SpacesOver = SpacesOver + PrintThisTrimmed("WARNING ONLY", outputStream);
						}
						if (Line.substring(1).trim().equals("Special"))
						{
							String ViolRTwoChars = "";
							ViolRTwoChars = WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt);
							ViolRTwoChars = ViolRTwoChars.substring(1);
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("PAY2PARK") && ViolRTwoChars.equals("56"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed("Today at Meter: $ 40.00", outputStream);
							}
						}
						if (Line.substring(1).trim().equals("Fee"))
							SpacesOver = SpacesOver + PrintThisTrimmed("Fee: $ "+WorkingStorage.GetCharVal(Defines.PrintFeeVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Plate"))
						{
							if (WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt).equals("TEMPTAG"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintPlateVal, cntxt).trim()+
										": "+WorkingStorage.GetCharVal(Defines.SaveTempPlateVal, cntxt).trim(), outputStream);
							}
							else
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintPlateVal, cntxt), outputStream);
							}
						}

						if (Line.substring(1).trim().equals("State"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintStateVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("PlateMonth"))
						{
							if (WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt).equals("TEMPTAG"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintTempExpireVal, cntxt), outputStream);
							}
							else
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintMonthVal, cntxt)+"/", outputStream);
							}
						}
						if (Line.substring(1).trim().equals("PlateYear"))
						{
							if (!WorkingStorage.GetCharVal(Defines.SavePlateVal, cntxt).equals("TEMPTAG"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintYearVal, cntxt), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("Beat"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintBeatVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Color"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintColorVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Make"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintMakeVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Body"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintBodyVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Number"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintNumberVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Permit"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintPermitVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Street"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintStreetVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("LGStreet1"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintLGStreet1Val, cntxt), outputStream);
						if (Line.substring(1).trim().equals("LGStreet2"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintLGStreet2Val, cntxt), outputStream);
						if (Line.substring(1).trim().equals("LGStreet3"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintLGStreet3Val, cntxt), outputStream);
						if (Line.substring(1).trim().equals("CrossStreet"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintCrossStreetVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Direction"))
						{
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("RICHMOND")
									&& WorkingStorage.GetCharVal(Defines.PrintDirectionVal, cntxt).trim().equals(""))
							{
								PrintThisTrimmed("BLOCK OF ", outputStream);
							}
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintDirectionVal, cntxt), outputStream);
						}
						if (Line.substring(1).trim().equals("Type"))
						{
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("HARRISBURG"))
							{
								if (WorkingStorage.GetCharVal(Defines.SaveTypeVal, cntxt).trim().equals("P"))
								{
									PrintThisTrimmed("12-2-5", outputStream);
								}
								else
								{
									PrintThisTrimmed("12-1-0", outputStream);
								}
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.SaveTypeVal, cntxt), outputStream);
							}
							else
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintTypeVal, cntxt), outputStream);
							}
						}

						if (Line.substring(1).trim().equals("ExtdType"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintExtdTypeVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("LicColor"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintLicColorVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("StreetType"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintStreetTypeVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Decal"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintDecalVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("UnitNumber"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintUnitNumber, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Dept"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.SaveDeptVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("NYVin"))
						{
							if (!WorkingStorage.GetCharVal(Defines.PrintNYVinVal, cntxt).trim().equals(""))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintNYVinVal, cntxt), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("Meter"))
						{
							if (!WorkingStorage.GetCharVal(Defines.PrintMeterVal, cntxt).trim().equals(""))
							{
								if (!WorkingStorage.GetCharVal(Defines.MeterNumberMessage, cntxt).trim().equals(""))
								{
									PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.MeterNumberMessage, cntxt), outputStream);
								}
								else
								{
									if (WorkingStorage.GetCharVal(Defines.LanguageType,cntxt).equals("SPANISH"))
									{
										PrintThisTrimmed("Numero del parquimetro: ", outputStream);
									}
									else
									{
										PrintThisTrimmed("Meter Number: ", outputStream);
									}
								}
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintMeterVal, cntxt), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("Chalk"))
						{
							if (!WorkingStorage.GetCharVal(Defines.PrintChalkVal, cntxt).trim().equals(""))
							{
								PrintThisTrimmed("Stamp/Chalk Time: ", outputStream);
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintChalkVal, cntxt), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("Stem"))
						{
							if (!WorkingStorage.GetCharVal(Defines.SaveStemVal, cntxt).trim().equals(""))
							{
								PrintThisTrimmed("Tire Stem: ", outputStream);
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.SaveStemVal, cntxt), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("LastFour"))
						{
							if (!WorkingStorage.GetCharVal(Defines.PrintLastFourVal, cntxt).trim().equals(""))
							{
								PrintThisTrimmed("Last Four of VIN: ", outputStream);
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintLastFourVal, cntxt), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("ViolCode"))
							SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintViolCodeVal, cntxt), outputStream);
						if (Line.substring(1).trim().equals("Court"))
						{
							//if (!WorkingStorage.GetCharVal(Defines.PrintFine1Val, cntxt).trim().equals("0.00"))
							if (!WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, cntxt).trim().equals("0.00"))
							{
								SpacesOver = SpacesOver + PrintThisTrimmed(WorkingStorage.GetCharVal(Defines.PrintCourtVal, cntxt), outputStream);
							}
						}
						if (Line.substring(1).trim().equals("HCode39BarCode"))
						{
							if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("LACROSSE"))
							{
								BarCodes.HorizontalCode39("512"+WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt),outputStream);
							}
							else
							{
								BarCodes.HorizontalCode39(WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt),outputStream);
							}
						}
						if (Line.substring(1).trim().equals("PerformBarCode"))
						{
							OCR.PrintTheBarCode(WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt),outputStream, cntxt);
						}
						if (Line.substring(1).trim().equals("PerformFeedBack"))
						{
							OCR.PrintFeedBack(outputStream, cntxt);
						}

						if (Line.substring(1).trim().equals("HCode39Plate"))
							Toast.makeText(cntxt, "Need to do HCode39 Plate Routine", 2000);
					} // end line.substring(0, 1).equals("*")

					if (Line.substring(0, 1).equals("!"))
					{
						// Paper Feeds
						FeedPaperDots2(Integer.valueOf(Line.substring(1).trim()), outputStream);
					}
					if (Line.substring(0, 1).equals("$"))
					{
						// Space Fillers
						String SpaceFiller = "";
						while (SpaceFiller.length() < Integer.valueOf(Line.substring(1).trim()))
							SpaceFiller += " ";
						SpacesOver = Integer.valueOf(Line.substring(1).trim());
						PrintThis(SpaceFiller, outputStream);
						SpaceFiller = "";
					}

					if (Line.substring(0, 1).equals(">"))
					{
						if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("SBPD"))
						{
							if (Line.substring(1).trim().equals("AND PAYMENT OF A $25.00"))
							{
								// See email from Lori Pedersen on 11/26/2012
								if (WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().equals("002") ||
										WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().equals("008") ||
										WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().equals("026") ||
										WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().equals("029") ||
										WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().equals("030") ||
										WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().equals("107"))
								{
									PrintThis("AND PAYMENT OF A $10.00", outputStream);
								}
								if (WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().equals("003") ||
										WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().equals("023") ||
										WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().equals("058"))
								{
									PrintThis("AND PAYMENT OF A $25.00", outputStream);
								}
							}
							else if (WorkingStorage.GetCharVal(Defines.FootBallFlagVal, cntxt).trim().equals("1"))
							{
								PrintThis(Line.substring(1).trim(), outputStream);
							}
							else if (Line.substring(1).trim().equals("REG EXPIRES"))
							{
								PrintThis(Line.substring(1).trim(), outputStream);
							}
							else
							{
								PrintThis(" ", outputStream);
							}
						}
						else if(WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("WINCHESTER"))
						{
							if (WorkingStorage.GetCharVal(Defines.MeterFlagVal, cntxt).equals("1"))
							{
								PrintThis(Line.substring(1).trim(), outputStream);
							}
							else
							{
								PrintThis(" ", outputStream);
							}
						}
						else if(WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("LONGVIEW"))
						{
							if (Line.substring(1,8).trim().equals("IF PAID"))
							{
								if (WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, cntxt).trim().equals("0.00"))
								{
									PrintThis(" ", outputStream);
								}
								else
								{
									PrintThis(Line.substring(1).trim(), outputStream);
								}
							}
							else
							{
								PrintThis(Line.substring(1).trim(), outputStream);
							}
						}
						else if(WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("ORLANDO"))
						{
							//if (WorkingStorage.GetCharVal(Defines.PrintFine1Val, cntxt).trim().equals("0.00")
							if (WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, cntxt).trim().equals("0.00")
									&& (Line.substring(1).trim().equals("$7.00 of each Fine supports")
									||	Line.substring(1).trim().equals("School Crossing Guards")) )
							{
								PrintThis(" ", outputStream);
							}
							else
							{
								PrintThis(Line.substring(1).trim(), outputStream);
							}
						}
						else if(WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("CENTRALRTD"))
						{
							//if (WorkingStorage.GetCharVal(Defines.PrintFine1Val, cntxt).trim().equals("0.00")
							if (WorkingStorage.GetCharVal(Defines.PrintFine1LargeVal, cntxt).trim().equals("0.00")
									&& (Line.substring(1).trim().equals("$20.00 FEE WILL BE APPLIED")
									||	Line.substring(1).trim().equals("AFTER 30 DAYS FOR ALL")
									||	Line.substring(1).trim().equals("UNPAID CITATIONS") ))
							{
								PrintThis(" ", outputStream);
							}
							else
							{
								PrintThis(Line.substring(1).trim(), outputStream);
							}
						}
						else if(WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("ALLPROROCHESTER")
								&& Line.equals(">(585) 263-1800"))
						{
							// ##### JRB Mod 2-12-16 #####
							if (WorkingStorage.GetCharVal(Defines.PrintStreetVal, cntxt).trim().equals("COLLEGE TOWN")) {
								Line = ">(585) 271-5616";
							}
							PrintThis(Line.substring(1).trim(), outputStream);
						}
						else if (WorkingStorage.GetCharVal(Defines.ClientName, cntxt).trim().equals("ASHLAND")) {
							if (WorkingStorage.GetCharVal(Defines.SaveViolateVal, cntxt).trim().substring(0,1).equals("0")) {
								// ##### JRB Mod 1-13-16 ####
								// Only Print This Line from PLAYOUT.A If Violation Begins With '0'
								PrintThis(Line.substring(1).trim(), outputStream);
							} else {
								// Print Nothing
								PrintThis(" ", outputStream);
							}
						}
						else
						{
							PrintThis(Line.substring(1).trim(), outputStream);
						}
					}

					if (Line.substring(0, 1).equals("~"))
					{
						String SpaceFiller = "";
						int NumberOfSpaces = 0;
						NumberOfSpaces = Integer.valueOf(Line.substring(1).trim());
						NumberOfSpaces = NumberOfSpaces - SpacesOver ;
						if (NumberOfSpaces < 1)
						{
							//NumberOfSpaces = 0; commented on 11/13/2012 need at least 2 spaces between fields
							PrintThis("  ", outputStream);
						}
						else
						{
							while (SpaceFiller.length() < NumberOfSpaces)
								SpaceFiller += " ";
							PrintThis(SpaceFiller, outputStream);
						}
						SpacesOver = Integer.valueOf(Line.substring(1).trim());
					}
					i++;
				}
				br.close();
				file = null;

			}catch (IOException e)
			{
				Toast.makeText(cntxt, e.toString(), 2000);
			}
		}
		else
		{
			file = null;
		}

		
	/*	PrintCompleteTicket += "          " + WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt);
		PrintCompleteTicket += "\r\r\r\r\r";				   

		PrintCompleteTicket +=  "Test\r" + "Line 2\r";
		PrintCompleteTicket += "Line 3\r" + "Line 4\r";
		try {
			outputStream.write(PrintCompleteTicket.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

		try {
			outputStream.write(0x12);
			outputStream.write(0x6D);
			outputStream.write(0x01);
			outputStream.write(0x3C);
			outputStream.write(0x06); // Mark Position Detect

			outputStream.write(0x1B);
			outputStream.write(0x4A);
			outputStream.write(0x26); // FEED DOT LINES 0X26 = 38 DOT LINES
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return PrintCompleteTicket ;
	}

	public static int PrintThis(String WhatToPrint, OutputStream outputStream )
	{
		try {
			outputStream.write(WhatToPrint.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return WhatToPrint.length();

	}

	public static int PrintThisTrimmed(String WhatToPrint, OutputStream outputStream )
	{
		try {
			outputStream.write(WhatToPrint.trim().getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return WhatToPrint.trim().length();
	}

	public static void FeedPaperDots2(int NumLines, OutputStream outputStream)
	{
		if (NumLines > 200)
		{
			if (NumLines > 1000)
			{
				NumLines = NumLines - 200;
				try {
					outputStream.write(0x1B);
					outputStream.write(0x4A);
					outputStream.write(200);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (NumLines > 800)
			{
				NumLines = NumLines - 200;
				try {
					outputStream.write(0x1B);
					outputStream.write(0x4A);
					outputStream.write(200);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (NumLines > 600)
			{
				NumLines = NumLines - 200;
				try {
					outputStream.write(0x1B);
					outputStream.write(0x4A);
					outputStream.write(200);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (NumLines > 400)
			{
				NumLines = NumLines - 200;
				try {
					outputStream.write(0x1B);
					outputStream.write(0x4A);
					outputStream.write(200);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (NumLines > 200)
			{
				NumLines = NumLines - 200;
				try {
					outputStream.write(0x1B);
					outputStream.write(0x4A);
					outputStream.write(200);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (NumLines <= 200)
			{
				try {
					outputStream.write(0x1B);
					outputStream.write(0x4A);
					outputStream.write(NumLines);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else
		{
			try {
				outputStream.write(0x1B);
				outputStream.write(0x4A);
				outputStream.write(NumLines);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

/*public class ReadLayout {
	
	protected static  BluetoothAdapter mBluetoothAdapter = null;
	public static String BTAddress = "";
	static BluetoothDevice blueToothDevice = null;
	protected static BluetoothSocket socket = null;  
	
	public static String PrintALittleBit(String LayoutFileName, Context cntxt)
	{
	
		String PrintComplete = "COMPLETE";
		if (SystemIOFile.exists("PRINTER.TXT")==false)
		{
			return "NOPAIRING";
		}
		
      		try 
      		{
      			 File file = new File("/data/data/com.clancy.aticket/files/","PRINTER.TXT"); 
		         BufferedReader br = new BufferedReader(new FileReader(file));  
		         String line;
		         StringBuffer buf= new StringBuffer();
		         while ((line = br.readLine()) != null ) 
		         {
 					buf.append(line+"\n");        				
					BTAddress = line;		        	 
		         }  
		         br.close();

            	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            	if (mBluetoothAdapter == null) 
            	{
            		PrintComplete = "BTUNAVAILABLE";
	      		} // This will find the remote device given the bluetooth hardware 
	      		blueToothDevice = mBluetoothAdapter.getRemoteDevice(BTAddress);
      		    for (Integer port = 1; port <= 3; port++) 
      		    {
	      			  PrintComplete = simpleComm(Integer.valueOf(port), cntxt);
	      		}        			
    		}  
    		catch(Throwable t) { 
    			PrintComplete = "GENERALERROR";
    		} 
 
		
		return PrintComplete ;
	}
	
	   protected static String simpleComm(Integer port, final Context cntxt) {
		   // The documents tell us to cancel the discovery process.
		   String SocketString ="COMPLETE";
		   mBluetoothAdapter.cancelDiscovery();
		   try 
		   {
			   // This is a hack to access "createRfcommSocket which is does not 
			   // have public access in the current api.
			   // Note: BlueToothDevice.createRfcommSocketToServiceRecord (UUID
			   // uuid) does not work in this type of application. .
			   Method m = blueToothDevice.getClass().getMethod( "createRfcommSocket", new Class[] { int.class }); 
			   socket = (BluetoothSocket) m.invoke(blueToothDevice, port);
			   // debug check to ensure socket was set.
			   assert (socket != null) : "Socket is Null";
			   // attempt to connect to device
			   socket.connect();
			   try 
			   {
				   String message = "";
				   OutputStream outputStream = socket.getOutputStream();
				   outputStream.write(0x1B);
				   outputStream.write(0x41);
				   outputStream.write(0x0); // 7-8 N-DOT LINE SPACING
				   
				   outputStream.write(0x1B);
				   outputStream.write(0x20);
				   outputStream.write(0x04); // CHAR SPACING 7-9
				   
				   outputStream.write(0x12);
				   outputStream.write(0x70);
				   outputStream.write(0x0); // 7-59 SET PAPER EMPTY OFF
				   
				   message = "          " + WorkingStorage.GetCharVal(Defines.PrintCitationVal, cntxt);
				   outputStream.write(message.getBytes());
				   message = "\r\r\r\r\r";				   
				   outputStream.write(message.getBytes());
				   
			        Calendar c = Calendar.getInstance();  
			        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			        String formattedDate = df.format(c.getTime());
			        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
			        String formattedTime = tf.format(c.getTime()); 
			        
			       // Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
			       // Toast.makeText(this, formattedTime, Toast.LENGTH_SHORT).show(); 

				   message = "  "+formattedDate+"        "+formattedTime;
				   //message = "  11/22/2011       11:35";				   
				   outputStream.write(message.getBytes());				   
				   message = "\r\r\r";				   
				   outputStream.write(message.getBytes());
				   //message = "  "Clancy           12345";				   
				   message = "  "+WorkingStorage.GetCharVal(Defines.PrintOfficerVal, cntxt);
				   outputStream.write(message.getBytes());
				   //message = "  "Clancy           12345";				   
				   message = "      "+WorkingStorage.GetCharVal(Defines.PrintBadgeVal, cntxt);
				   outputStream.write(message.getBytes());
				   message = "\r\r\r";				   
				   outputStream.write(message.getBytes());
				   message = "  DISABLED VEHICLE";				   
				   outputStream.write(message.getBytes());
				   message = "\r\r\r\r\r\r\r\r\r\r";				   
				   outputStream.write(message.getBytes());
				   message = "                       25.00";				   
				   outputStream.write(message.getBytes());
				   message = "\r\r\r\r";				   
				   outputStream.write(message.getBytes());					   
				   message = "                       45.00";				   
				   outputStream.write(message.getBytes());
				   message = "\r\r\r\r";				   
				   outputStream.write(message.getBytes());					   
				   message = "        " + WorkingStorage.GetCharVal(Defines.PrintPlateVal, cntxt);
				   //message = "       ABC123";
				   outputStream.write(message.getBytes());
				   message = "\r\r";				   
				   outputStream.write(message.getBytes());
				   message = "   COLORADO  12/2011 ";				   
				   outputStream.write(message.getBytes());
				   message = "\r\r\r\r\r";				   
				   outputStream.write(message.getBytes());	
				   message = " BLUE CHEVROLET\r";				   
				   outputStream.write(message.getBytes());
				   message = " 2 DOOR ";				   
				   outputStream.write(message.getBytes());
				   message = "\r\r\r\r";				   
				   outputStream.write(message.getBytes());					   
				   message = " 2149 S GRAPE STREET ";				   
				   outputStream.write(message.getBytes());
				   message = "\r";	
				   outputStream.write(message.getBytes());					   
				   message = " Permit: " + WorkingStorage.GetCharVal(Defines.PrintPermitVal, cntxt);
				   outputStream.write(message.getBytes());	
				   message = "\r\r\r";	
				   outputStream.write(message.getBytes());					   
				   outputStream.write(0x12);
				   outputStream.write(0x6D);
				   outputStream.write(0x01);
				   outputStream.write(0x3C);
				   outputStream.write(0x06); // Mark Position Detect
				   
				   outputStream.write(0x1B);
				   outputStream.write(0x4A);
				   outputStream.write(0x26); // FEED DOT LINES 0X26 = 38 DOT LINES
				   
				   
			   } 
			   finally 
			   {
			  	   Runnable mPauseBTClose = new Runnable() {     
						public void run() 
						{         			    
							try 
							{
								socket.close();
								Messagebox.Message("Socket Close",cntxt);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	   
						}
					}; 
					
					Handler mHandler = new Handler(); 
					mHandler.postDelayed(mPauseBTClose, 6000);  
				} 
			   // IOExcecption is thrown if connect fails.
		   } 
		   catch (IOException ex) 
		   {
				//Log.e(this.toString(), "IOException " + ex.getMessage());
			   SocketString = "IOException " + ex.getMessage();
				// NoSuchMethodException IllegalAccessException
				// InvocationTargetException
				// are reflection exceptions.
			} 
			catch (NoSuchMethodException ex) 
			{
					//Log.e(this.toString(), "NoSuchMethodException " + ex.getMessage());
				SocketString = "NoSuchMethodException " + ex.getMessage();
			} 
			catch (IllegalAccessException ex) 
			{
			   	//Log.e(this.toString(), "IllegalAccessException " + ex.getMessage());
			   	SocketString = "IllegalAccessException " + ex.getMessage();
			} 
			catch (InvocationTargetException ex) 
			{
			  	//Log.e(this.toString(),"InvocationTargetException " + ex.getMessage());
			   	SocketString = "InvocationTargetException " + ex.getMessage();
		 	}   
			return SocketString;
	   }

}*/
