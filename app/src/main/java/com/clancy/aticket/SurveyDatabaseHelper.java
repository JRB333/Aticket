package com.clancy.aticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class SurveyDatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ClancySurveyDB";

    // Table Names
    private static final String KEY_ROUTETABLE = "ROUTEDATA";
    private static final String KEY_PASSLOGTABLE = "PASSDATALOG";

    // Route Table - column names
    private static final String KEY_ID = "id";
    private static final String KEY_ROUTE = "route";
    private static final String KEY_STREET = "street";
    private static final String KEY_METER = "meter";
    private static final String KEY_SEQUENCE = "sequence";
    private static final String KEY_PASSDATA = "passdata";
    private static final String KEY_LASTUPDATE = "last_update";

    // PassDataLog Table - column names
    //private static final String KEY_ROUTE = "route";
    private static final String KEY_PASSCOUNT = "pass_count";
    // NOTE - Despite the field names,
    //    the Purpose of the next 2 fields has been changed to represent Last Pass Item Index
    //    So that the user may go to Any Route and optionally pick up where they left off
    //    As long as that Pass was Not COMPLETED
    private static final String KEY_LASTSTREET = "last_street";
    private static final String KEY_LASTMETER = "last_meter";

    public SurveyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        // Used to capture Survey Pass Data
        String CreateTable1 = "CREATE TABLE IF NOT EXISTS "
                + KEY_ROUTETABLE
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ROUTE + " TEXT,"
                + KEY_STREET + " TEXT,"
                + KEY_METER + " TEXT,"
                + KEY_SEQUENCE + " INTEGER,"
                + KEY_PASSDATA + " TEXT,"
                + KEY_LASTUPDATE + " DATETIME"
                + ")";
        db.execSQL(CreateTable1);

        // Table to Track Pass Data (used to identify where left off for Survey Resume)
        String CreateTable2 = "CREATE TABLE IF NOT EXISTS "
                + KEY_PASSLOGTABLE
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ROUTE + " TEXT,"
                + KEY_PASSCOUNT + " TEXT,"
                + KEY_LASTSTREET + " TEXT,"
                + KEY_LASTMETER + " TEXT"
                + ")";
        db.execSQL(CreateTable2);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + KEY_ROUTETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_PASSLOGTABLE);

        // create new tables
        onCreate(db);
    }


    /*  ------------------------------------------------
    *  Add New Street-Meter Record (Empty) To RouteTable
    */
    public Boolean addStreetMeterRecord() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        String SpaceStr = " ";
        Character SpaceChar = SpaceStr.charAt(0);
        String Street = WorkingStorage.GetCharVal(Defines.StreetName, cntxt);
        Street = StringUtilities.rightPad(Street.trim(), 12, SpaceChar);
        String Meter = WorkingStorage.GetCharVal(Defines.MeterSpace, cntxt);
        Meter = StringUtilities.rightPad(Meter.trim(),8,SpaceChar);
        String cSequence = WorkingStorage.GetCharVal(Defines.RouteSequence, cntxt);
        Integer nSequence = Integer.valueOf(cSequence);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ROUTE, RouteName); // Route
        values.put(KEY_STREET, Street); // Street
        values.put(KEY_METER, Meter); // Meter
        values.put(KEY_SEQUENCE, nSequence); // Meter

        // Insert Route-Street-Meter Record into RouteTable
        long id = db.insert(KEY_ROUTETABLE, null, values);
        db.close(); // Closing database connection

        if (id > -1) {
            return true;
        } else {
            return false;
        }
    }


    /*  ------------------------------------------------
    * Getting Single Street-Meter Record for Given Route
    */
    public String getStreetMeterPassData() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        String SpaceStr = " ";
        Character SpaceChar = SpaceStr.charAt(0);
        String Street = WorkingStorage.GetCharVal(Defines.StreetName, cntxt);
        if (Street.length() < 12) {
            Street = StringUtilities.rightPad(Street.trim(), 12, SpaceChar);
        }
        String Meter = WorkingStorage.GetCharVal(Defines.MeterSpace, cntxt);
        Meter = StringUtilities.rightPad(Meter.trim(),8,SpaceChar);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT passdata "
                + "FROM " + KEY_ROUTETABLE
                + " WHERE "
                + KEY_ROUTE + " = '" + RouteName + "'"
                + " AND "
                + KEY_STREET + " = '" + Street + "'"
                + " AND "
                + KEY_METER + " = '" + Meter + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        String ResultStr = "";
        if (cursor.moveToFirst()) {
            String ThisPassData = cursor.getString(cursor.getColumnIndex(KEY_PASSDATA));
            if (ThisPassData != null && ThisPassData.length() > 0 && !ThisPassData.equals("null")) {
                ResultStr = cursor.getString(cursor.getColumnIndex(KEY_PASSDATA));
            }
            /*ResultStr = cursor.getString(c.getColumnIndex(KEY_STREET))
                    + cursor.getString(c.getColumnIndex(KEY_METER))
                    + cursor.getString(c.getColumnIndex(KEY_PASSDATA)); */
        }
        cursor.close();

        // return Record String
        return ResultStr;
    }


    /*  ------------------------------------------------
    *  Update single Street-Meter Record for Given Route in both RouteTable & PassTable
    */
    public Boolean updateStreetMeterPassData(String NewPassData) {
        // Get Existing PassData
        String PassData = getStreetMeterPassData();

        Integer RowsUpdated = 0;
        if (NewPassData.length() > 0) {
            if (PassData == null || PassData.trim().equals("null") || PassData.trim().length() <= 0) {
                PassData = NewPassData;
            } else {
                // Add NewPassData To Previous
                PassData = PassData + "++" + NewPassData;
            }

            // Update RouteTable Record
            Context cntxt = Defines.contextGlobal;
            String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

            String SpaceStr = " ";
            Character SpaceChar = SpaceStr.charAt(0);
            String Street = WorkingStorage.GetCharVal(Defines.StreetName, cntxt);
            if (Street.length() < 12) {
                Street = StringUtilities.rightPad(Street.trim(), 12, SpaceChar);
            }
            String Meter = WorkingStorage.GetCharVal(Defines.MeterSpace, cntxt);
            Meter = StringUtilities.rightPad(Meter.trim(),8,SpaceChar);

            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ThisUpdate = formatter.format(today);

            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = KEY_ROUTE + " = '" + RouteName + "'"
                    + " AND "
                    + KEY_STREET + " = '" + Street + "'"
                    + " AND "
                    + KEY_METER + " = '" + Meter + "'";

            ContentValues data = new ContentValues();
            data.put(KEY_PASSDATA,PassData);
            data.put(KEY_LASTUPDATE, ThisUpdate);
            RowsUpdated = db.update(KEY_ROUTETABLE, data, selectQuery, null);

            // Now Update PassTable Record for Specific Route
            selectQuery = KEY_ROUTE + " = '" + RouteName + "'";
            String PassItemCounter = WorkingStorage.GetCharVal(Defines.PassItemCounter, cntxt);

            String ThisPass = WorkingStorage.GetCharVal(Defines.PassCounter, cntxt);
            data = new ContentValues();
            data.put(KEY_PASSCOUNT, ThisPass);
            data.put(KEY_LASTSTREET, PassItemCounter);
            data.put(KEY_LASTMETER, PassItemCounter);
            Integer RowsUpdated2 = db.update(KEY_PASSLOGTABLE, data, selectQuery, null);
        }

        if (RowsUpdated == 1) {
            return true;
        } else {
            return false;
        }
    }


    /*  ------------------------------------------------
    * get List of All Streets for Given Route
    */
    public String getAllStreets() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select distinct street "
                + " from " + KEY_ROUTETABLE
                + " Where "
                + KEY_ROUTE + " = '" + RouteName + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        String ResultStr = "";
        if (cursor.moveToFirst()) {
            ResultStr = "^";
            do {
                ResultStr = ResultStr
                        + cursor.getString(cursor.getColumnIndex(KEY_STREET))
                        + "^";
            } while (cursor.moveToNext());
        }
        cursor.close();

        return ResultStr;
    }


    /*  ------------------------------------------------
    * get List of All Street-Meters for Given Route
    */
    public String getAllStreetMeter() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select distinct " + KEY_STREET + ", " + KEY_METER
                + " from " + KEY_ROUTETABLE
                + " Where "
                + " " + KEY_ROUTE + " = '" + RouteName + "'"
                + " Order By " + KEY_SEQUENCE ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        String ResultStr = "";
        if (cursor.moveToFirst()) {
            ResultStr = "^";
            do {
                ResultStr = ResultStr
                        + cursor.getString(cursor.getColumnIndex(KEY_STREET))
                        + "#"
                        + cursor.getString(cursor.getColumnIndex(KEY_METER))
                        + "^";
            } while (cursor.moveToNext());
        }
        cursor.close();

        return ResultStr;
    }


    /*  ------------------------------------------------
    * get List of Remaining Street-Meters for Given Route
    */
    public String getRemainingStreetMeter() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select distinct " + KEY_STREET + ", " + KEY_METER
                + " from " + KEY_ROUTETABLE
                + " Where "
                + " " + KEY_ROUTE + " = '" + RouteName + "'"
                + " Order By " + KEY_SEQUENCE ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        Boolean lStartSaving = false;
        Integer nCursorItems = -1;
        String ResultStr = "";
        if (cursor.moveToFirst()) {
            ResultStr = "^";
            do {
                nCursorItems = nCursorItems + 1;

                lStartSaving = true;
                if (lStartSaving) {
                    ResultStr = ResultStr
                            + cursor.getString(cursor.getColumnIndex(KEY_STREET))
                            + "#"
                            + cursor.getString(cursor.getColumnIndex(KEY_METER))
                            + "^";
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return ResultStr;
    }


    /*  ------------------------------------------------
    * Get List of All Street, Meter, & PassData for Given Route
    */
    public String getAllStreetMeterPassData() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select distinct " + KEY_STREET + ", " + KEY_METER + ", " + KEY_PASSDATA
                + " from " + KEY_ROUTETABLE
                + " Where "
                + " " + KEY_ROUTE + " = '" + RouteName + "'"
                + " Order By " + KEY_SEQUENCE ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        String ResultStr = "";
        Integer MaxPasses = 0;
        if (cursor.moveToFirst()) {
            ResultStr = "^";
            do {
                Integer Passes = 0;
                String ThisPassData = cursor.getString(cursor.getColumnIndex(KEY_PASSDATA));

                if (ThisPassData != null && ThisPassData.length() > 0 && !ThisPassData.equals("null")) {
                    ResultStr = ResultStr
                            + cursor.getString(cursor.getColumnIndex(KEY_STREET))
                            + "#"
                            + cursor.getString(cursor.getColumnIndex(KEY_METER))
                            + "#"
                            + ThisPassData
                            + "^";

                    // Determine Number of Passes Completed
                    if (ThisPassData.contains("++")) {
                        final String SPLIT_STR = "++";
                        final StringTokenizer stToken = new StringTokenizer(ThisPassData, SPLIT_STR);
                        final String[] splitStr = new String[stToken.countTokens()];
                        int index = 0;
                        while(stToken.hasMoreElements()) {
                            splitStr[index] = stToken.nextToken();

                            // If more to get, increment Index
                            if (stToken.hasMoreElements()) {
                                index++;
                            }
                        }
                        Passes = index + 1;
                    } else {
                        Passes = 1;
                    }
                    MaxPasses = Math.max(Passes, MaxPasses);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        WorkingStorage.StoreCharVal(Defines.PassCounter, MaxPasses.toString(), cntxt);

        return ResultStr;
    }


    /*  ------------------------------------------------
    * get List of All Routes
    */
    public String getAllRouteNames() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select distinct " + KEY_ROUTE
                + " from " + KEY_ROUTETABLE ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        String ResultStr = "";
        if (cursor.moveToFirst()) {
            ResultStr = "^";
            do {
                ResultStr = ResultStr + cursor.getString(cursor.getColumnIndex(KEY_ROUTE)) + "^";
            } while (cursor.moveToNext());
        }
        cursor.close();

        return ResultStr;
    }


    /*  ------------------------------------------------
    * get LastUpdate
    */
    public String getLastUpdate() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select " + KEY_LASTUPDATE
                + " from " + KEY_ROUTETABLE
                + " where "
                + " " + KEY_LASTUPDATE + " IS NOT NULL"
                + " AND "
                + " " + KEY_LASTUPDATE + " != ''"
                + " ORDER BY " + KEY_LASTUPDATE + " desc LIMIT 1";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        String ResultStr = "";
        if (cursor.moveToFirst()) {
            ResultStr = "^";
            do {
                String UpdtStr = cursor.getString(cursor.getColumnIndex(KEY_LASTUPDATE));
                if ((UpdtStr != null && !UpdtStr.isEmpty())) {
                    ResultStr = ResultStr + UpdtStr + "^";
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return ResultStr;
    }


    /*  ------------------------------------------------
    * DELETE Records for Specific Route in RouteTable & PassTable
    */
    public Boolean deleteRouteRecords() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = KEY_ROUTE + " = '" + RouteName + "'";

        // Do RouteTable
        Integer RowsDeleted = db.delete(KEY_ROUTETABLE, selectQuery, null);

        // Now do PassTable
        Integer RowsDeleted2 = db.delete(KEY_PASSLOGTABLE, selectQuery, null);

        if (RowsDeleted > 0) {
            return true;
        } else {
            return false;
        }
    }

    /*  ------------------------------------------------
    * CLEAR Route Records for Specific Route
    */
    public Boolean clearRouteRecords() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = KEY_ROUTE + " = '" + RouteName + "'";

        ContentValues data = new ContentValues();
        data.put(KEY_PASSDATA, "");
        data.putNull(KEY_LASTUPDATE);
        // Clear Route Records in RouteTable
        Integer RowsUpdated = db.update(KEY_ROUTETABLE, data, selectQuery, null);

        // Clear Route Record in PassTable
        data = new ContentValues();
        data.put(KEY_PASSCOUNT, "-1");
        data.put(KEY_LASTSTREET, "-1");
        data.put(KEY_LASTMETER, "-1");
        Integer RowsUpdated2 = db.update(KEY_PASSLOGTABLE, data, selectQuery, null);

        if (RowsUpdated >= 1) {
            return true;
        } else {
            return false;
        }
    }


    /*  ------------------------------------------------
    * CLEAR ALL Route Records in RouteTable & PassTable
    */
    public Boolean clearAllRouteRecords() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = KEY_ID + " > -1";

        ContentValues data = new ContentValues();
        data.put(KEY_PASSDATA, "");
        data.putNull(KEY_LASTUPDATE);
        // Do RouteTable
        Integer RowsUpdated = db.update(KEY_ROUTETABLE, data, selectQuery, null);

        // Now Do PassTable
        data = new ContentValues();
        data.put(KEY_PASSCOUNT,"0");
        data.put(KEY_LASTSTREET,"-1");
        data.put(KEY_LASTMETER,"-1");
        Integer RowsUpdated2 = db.update(KEY_PASSLOGTABLE, data, selectQuery, null);

        if (RowsUpdated == 1) {
            return true;
        } else {
            return false;
        }
    }

    /*  ------------------------------------------------
    * DELETE ALL Routes in Both RouteTable & PassTable
    */
    public Boolean deleteAllRoutes() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = KEY_ID + " > -1";

        // Do RouteTable
        Integer RowsDeleted = db.delete(KEY_ROUTETABLE, selectQuery, null);
        // Now Do PassTable
        Integer RowsDeleted2 = db.delete(KEY_PASSLOGTABLE, selectQuery, null);

        if (RowsDeleted > 0) {
            return true;
        } else {
            return false;
        }
    }


    /* =================================================
    *  The following are routines for the PassTable ONLY
    */

    /*  ------------------------------------------------
    * get Specific Route Record from PassTable
    */
    public String getPassTableRoute() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select distinct " + KEY_ROUTE
                + " from " + KEY_PASSLOGTABLE ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        String ResultStr = "";
        if (cursor.moveToFirst()) {
            ResultStr = "^";
            do {
                ResultStr = ResultStr + cursor.getString(cursor.getColumnIndex(KEY_ROUTE)) + "^";
            } while (cursor.moveToNext());
        }
        cursor.close();

        return ResultStr;
    }


    /*  ------------------------------------------------
    *  Add New Route Record (Empty) To PassTable
    */
    public Boolean addPassTableRouteRecord() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ROUTE, RouteName); // Route
        values.put(KEY_PASSCOUNT, "0"); // Pass Counter = 0
        values.put(KEY_LASTSTREET, "-1"); // Street
        values.put(KEY_LASTMETER, "-1"); // Meter

        // Insert Route Record into PassTable
        long id = db.insert(KEY_PASSLOGTABLE, null, values);
        db.close(); // Closing database connection

        if (id > -1) {
            return true;
        } else {
            return false;
        }
    }


    /*  ------------------------------------------------
    * get Specific Route Record from PassTable
    */
    public String getPassCount() {
        Context cntxt = Defines.contextGlobal;
        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select * "
                + " from " + KEY_PASSLOGTABLE
                + " where route = '" + RouteName + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        WorkingStorage.StoreCharVal(Defines.ResumeItemCounter, "-1", cntxt);
        String ResultStr = "";
        String LastPassIndex = "";
        if (cursor.moveToFirst()) {
            do {
                ResultStr = cursor.getString(cursor.getColumnIndex(KEY_PASSCOUNT));
                LastPassIndex = cursor.getString(cursor.getColumnIndex(KEY_LASTSTREET));

                if (!cursor.getString(cursor.getColumnIndex(KEY_LASTSTREET)).equals("-1")
                        || !cursor.getString(cursor.getColumnIndex(KEY_LASTMETER)).equals("-1")) {
                    // Capture RESUME Values
                    WorkingStorage.StoreCharVal(Defines.ResumeRoute, RouteName, cntxt);
                    WorkingStorage.StoreCharVal(Defines.ResumeItemCounter, LastPassIndex, cntxt);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();

        if (ResultStr.equals("")) {
            // Route # Record Not In Table Yet, Add It Now
            addPassTableRouteRecord();
            ResultStr = "";
        }

        return ResultStr;
    }


    /*  ------------------------------------------------
    * Clear PassTable for Specific Route AND This Pass
    */
    public boolean FinalizeRoutePass() {
        Context cntxt = Defines.contextGlobal;
        WorkingStorage.StoreCharVal(Defines.ResumeRoute, "", cntxt);

        String RouteName = WorkingStorage.GetCharVal(Defines.RouteName, cntxt);
        String ThisPass = WorkingStorage.GetCharVal(Defines.PassCounter, cntxt);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = KEY_ROUTE + " = '" + RouteName + "'";

        ContentValues data = new ContentValues();
        data.put(KEY_PASSCOUNT, ThisPass);
        data.put(KEY_LASTSTREET, "-1");
        data.put(KEY_LASTMETER, "-1");
        Integer RowsUpdated = db.update(KEY_PASSLOGTABLE, data, selectQuery, null);
        if (RowsUpdated == 1) {
            return true;
        } else {
            return false;
        }
    }


    /*  ------------------------------------------------
    * Initialize PassTable for All Routes
    */
    public boolean InitPassTable() {
        Context cntxt = Defines.contextGlobal;

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = KEY_ID + " > -1";

        ContentValues data = new ContentValues();
        data.put(KEY_PASSCOUNT, "0");
        data.put(KEY_LASTSTREET, "-1");
        data.put(KEY_LASTMETER, "-1");
        Integer RowsUpdated = db.update(KEY_PASSLOGTABLE, data, selectQuery, null);
        if (RowsUpdated >= 1) {
            return true;
        } else {
            return false;
        }
    }


    // ============================================
    /* -------------------------------------------
    * The following routeins are used primarily for Development/Testing purposes
    */
    public String getTableFields() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "PRAGMA table_info(" + KEY_ROUTETABLE + ")";

        Cursor ti = db.rawQuery(selectQuery, null);
        String ResultStr = "";
        if (ti.moveToFirst() ) {
            ResultStr = "|";
            do {
                ResultStr = ResultStr + ti.getString(1) + "|";
            } while (ti.moveToNext());
        }
        ti.close();
        return ResultStr;
    }

    public String ReadTableContents() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "select * "
                + " from " + KEY_ROUTETABLE
                + " where "
                + " " + KEY_ID + " > -1";

        Cursor cursor1 = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        String ResultStr = "";
        if (cursor1.moveToFirst()) {
            ResultStr = "^";
            do {
                String ThisRecord = cursor1.getString(cursor1.getColumnIndex(KEY_ROUTE))
                        + "#"
                        + cursor1.getString(cursor1.getColumnIndex(KEY_STREET))
                        + "#"
                        + cursor1.getString(cursor1.getColumnIndex(KEY_METER))
                        + "||"
                        + cursor1.getString(cursor1.getColumnIndex(KEY_PASSDATA))
                        + "--"
                        + cursor1.getString(cursor1.getColumnIndex(KEY_LASTUPDATE))
                        + "^";
                ResultStr = ResultStr + ThisRecord;
            } while (cursor1.moveToNext());
        }

        selectQuery = "select * "
                + " from " + KEY_PASSLOGTABLE
                + " where "
                + " " + KEY_ID + " > -1";

        Cursor cursor2 = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        String ResultStr2 = "";
        if (cursor2.moveToFirst()) {
            ResultStr2 = "^";
            do {
                String ThisRecord = cursor2.getString(cursor2.getColumnIndex(KEY_ROUTE))
                        + "--"
                        + cursor2.getString(cursor2.getColumnIndex(KEY_PASSCOUNT))
                        + "||"
                        + cursor2.getString(cursor2.getColumnIndex(KEY_LASTSTREET))
                        + "#"
                        + cursor2.getString(cursor2.getColumnIndex(KEY_LASTMETER))
                        + "^";
                ResultStr2 = ResultStr2 + ThisRecord;
            } while (cursor2.moveToNext());
        }

        cursor1.close();
        cursor2.close();

        return ResultStr;
    }


    public void kill_n_create_Database() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + KEY_ROUTETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_PASSLOGTABLE);
        onCreate(db);
        getTableFields();
    }

   public void checktables() {
       SQLiteDatabase db = this.getReadableDatabase();

       Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata' order by name", null);

       String ResultStr = "";
       if (c.moveToFirst()) {
           while ( !c.isAfterLast() ) {
               ResultStr = ResultStr + "|" + c.getString( c.getColumnIndex("name"));
               c.moveToNext();
           }
       }
       c.close();
   }
}