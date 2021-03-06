package com.clancy.aticket;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Signature;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.clancy.aticket.R.drawable.signatureborder;

public class SignatureCaptureForm extends Activity {

    LinearLayout mContent;
    signature mSignature;
    Button mClear, mSaveSignature, mEscape;
    public static String tempDir;
    public int count = 1;
    public String SigFileName = null;
    public String FileDir = null;
    private Bitmap mBitmap;
    View mView;
    File SigFile;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signaturecaptureform);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        WorkingStorage.StoreCharVal(Defines.SignatureFile, "", getApplicationContext());

        //tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        FileDir = getApplicationContext().getFilesDir().getPath();  // "/data/data/com.clancy.aticket/files/"
        String BadgeNo = WorkingStorage.GetCharVal(Defines.PrintBadgeVal,getApplicationContext());
        SigFileName = "Sig" + BadgeNo.trim() + ".png";   // Fixed Signature Image file name
        SigFile = new File(FileDir, SigFileName);

        mContent = (LinearLayout) findViewById(R.id.capturearea);
        int captureWidth = mContent.getWidth();
        int captureHeight = mContent.getHeight();

        Drawable SignatureBorder = getApplicationContext().getResources().getDrawable(R.drawable.signatureborder);
        SignatureBorder.setBounds(0, 0, captureWidth, captureHeight);
        mContent.setBackgroundDrawable(SignatureBorder);

        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mClear = (Button)findViewById(R.id.clear);
        mSaveSignature = (Button)findViewById(R.id.savesignature);
        mSaveSignature.setEnabled(false);
        mEscape = (Button)findViewById(R.id.buttonESC);
        mView = mContent;

        if (WorkingStorage.GetCharVal(Defines.LanguageType,getApplicationContext()).equals("SPANISH"))
        {
            // If Spanish, Change Default Wording on Layout.
            TextView txtPrompt = (TextView) findViewById(R.id.textView3);
            txtPrompt.setText("Por Favor Firme Abajo...");  // Please Sign Below...
            mSaveSignature.setText("Guardar");  // Save Button
            mClear.setText("Claro");  // Clear Button
        }

        mClear.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                mSaveSignature.setEnabled(false);
            }
        });

        mSaveSignature.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Saved");
                boolean error = captureSignature();
                if(!error){
                    if (SigFile.exists()) {
                        // Delete Any Previous Signature File
                        SigFile.delete();
                    }

                    mView.setDrawingCacheEnabled(true);
                    mSignature.save(mView);
                    Bundle b = new Bundle();
                    b.putString("status", "done");

                    // The Following is for DEBUG ONLY  #####
                    /*Defines.clsClassName = ConfirmImageFileForm.class ;
                    Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                    overridePendingTransition(0, 0);*/

                    WorkingStorage.StoreCharVal(Defines.PreviousMenu, "", getApplicationContext());
                    if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
                    {
                        Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                        startActivityForResult(myIntent, 0);
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }
            }
        });

        mEscape.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                // Start Officer Data Acquisition All Over Again
                WorkingStorage.StoreLongVal(Defines.CurrentOffOrderVal, 0, getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.MenuReturnVal, "T", getApplicationContext());
                GetDate.GetDateTime(getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.TLogTimeVal, WorkingStorage.GetCharVal(Defines.SaveTimeVal, getApplicationContext()), getApplicationContext());
                WorkingStorage.StoreCharVal(Defines.TLogDateVal, WorkingStorage.GetCharVal(Defines.PrintTimeVal, getApplicationContext()), getApplicationContext());
                if (ProgramFlow.GetNextSetupForm(getApplicationContext()) != "ERROR")
                {
                    Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.w("GetSignature", "onDestroy");
        super.onDestroy();
    }

    private boolean captureSignature() {

        boolean error = false;
        String errorMessage = "";

        if(error){
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 105, 50);
            toast.show();
        }

        return error;
    }

    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate =     (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:",String.valueOf(todaysDate));
        return(String.valueOf(todaysDate));
    }

    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));
        return(String.valueOf(currentTime));
    }

    private boolean prepareDirectory()
    {
        try
        {
            if (makedirs())
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", 1000).show();
            return false;
        }
    }

    private boolean makedirs()
    {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory())
        {
            File[] files = tempdir.listFiles();
            for (File file : files)
            {
                if (!file.delete())
                {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }

    public class signature extends View
    {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v)
        {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mBitmap);
            try
            {
                FileOutputStream mFileOutStream = new FileOutputStream(SigFile);

                v.draw(canvas);
                // Save PNG file
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);

                // The following is for DEBUG ONLY - make 2nd JPG copy of Image File    #####
                /*File debugFile = new File(Environment.getExternalStorageDirectory(), SigFileName.replace("png","JPG"));
                FileOutputStream mDebugOutStream = new FileOutputStream(debugFile);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, mDebugOutStream);*/

                //Log.v("log_tag","url: " + url);
                //In case you want to delete the file
                //boolean deleted = SigFile.delete();
                //Log.v("log_tag","deleted: " + SigFile.toString() + deleted);
                //If you want to convert the image to string use base64 converter
                mBitmap.recycle();
                WorkingStorage.StoreCharVal(Defines.SignatureFile, SigFileName, getApplicationContext());
            }
            catch(Exception e)
            {
                Log.v("log_tag", e.toString());
            }
        }

        public void clear()
        {
            path.reset();
            WorkingStorage.StoreCharVal(Defines.SignatureFile, "", getApplicationContext());
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float eventX = event.getX();
            float eventY = event.getY();
            mSaveSignature.setEnabled(true);

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++)
                    {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string){
        }

        private void expandDirtyRect(float historicalX, float historicalY)
        {
            if (historicalX < dirtyRect.left)
            {
                dirtyRect.left = historicalX;
            }
            else if (historicalX > dirtyRect.right)
            {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top)
            {
                dirtyRect.top = historicalY;
            }
            else if (historicalY > dirtyRect.bottom)
            {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY)
        {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
}

