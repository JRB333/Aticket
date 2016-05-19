package com.clancy.aticket;

/**
 * This Class is Here for Debug Reasons ONLY
 */

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.widget.ImageView.*;


public class ConfirmImageFileForm extends Activity {

    private Bitmap bMap;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmimagefileform);

        Button Save = (Button) findViewById(R.id.SaveBtn);
        Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Leave the Already Saved License Photo file alone
                //finish();
                bMap.recycle();
                if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
                {
                    Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }
        });

        Button NoSave = (Button) findViewById(R.id.NoSaveBtn);
        NoSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Delete the Already Saved License Photo file
                String ImgFileName = WorkingStorage.GetCharVal(Defines.SignatureFile, getApplicationContext());
                File ImgFile = new File(Environment.getExternalStorageDirectory(),ImgFileName);
                ImgFile.delete();
                bMap.recycle();
                WorkingStorage.StoreCharVal(Defines.SignatureFile, "", getApplicationContext());
                //finish();
                Defines.clsClassName = null;
                if (ProgramFlow.GetNextForm("",getApplicationContext()) != "ERROR")
                {
                    Intent myIntent = new Intent(getApplicationContext(), SwitchForm.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }
        });

        // Get PNG Image and Display It
        ImageView ImgView = (ImageView) findViewById(R.id.image_review);

        // Build Signature File Pathed Reference
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_WORLD_READABLE);
        String FileDir = getApplicationContext().getFilesDir().getPath();  // "/data/data/com.clancy.aticket/files/"

        String ImgFileName = WorkingStorage.GetCharVal(Defines.SignatureFile, getApplicationContext());
        String ImgFileStr = FileDir +  "/" + ImgFileName;
        File ImgFile = new File(getApplicationContext().getFilesDir().getPath(), ImgFileName);

        bMap = BitmapFactory.decodeFile(FileDir +  "/" + ImgFileName);
        ImgView.setImageBitmap(bMap);

/*      FileInputStream in;
        BufferedInputStream buf;
        try {
            in = new FileInputStream(ImgFileStr);
            buf = new BufferedInputStream(in);
            Bitmap bMap = BitmapFactory.decodeStream(buf);
            ImgView.setImageBitmap(bMap);
            if (in != null) {
                in.close();
            }
            if (buf != null) {
                buf.close();
            }
        } catch (Exception e) {
            Log.e("Error reading file", e.toString());
        }  */
    }
}
