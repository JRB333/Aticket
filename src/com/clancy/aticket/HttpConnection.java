package com.clancy.aticket;

import java.io.*;

import org.apache.http.*;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
 
import android.graphics.*;
import android.os.*;
import android.util.Base64;

public class HttpConnection implements Runnable {
	 
    public static final int DID_START = 0;
    public static final int DID_ERROR = 1;
    public static final int DID_SUCCEED = 2;
 
    private static final int GET = 0;
    private static final int POST = 1;
    private static final int PUT = 2;
    private static final int DELETE = 3;
    private static final int PUTBITMAP = 4;
    private static final int BITMAP = 5;
        
 
    private String url;
    private int method;
    private Handler handler;
    private String data;
    private String fileName;
    private byte[] byteBuffer;
 
    private HttpClient httpClient;
 
    public HttpConnection() {
        this(new Handler());
    }
 
    public HttpConnection(Handler _handler) {
        handler = _handler;
    }
 
    public void create(int method, String url, String data, byte[] byteBuffer, String fileName) {
        this.method = method;
        this.url = url;
        this.data = data;
        this.byteBuffer = byteBuffer;
        this.fileName = fileName;
        ConnectionManager.getInstance().push(this);
    }
 
    public void get(String url) {
        create(GET, url, null, null, "");
    }
 
    public void post(String url, String data) {
        create(POST, url, data, null, "");
    }
 
    public void put(String url, String data, String fileName) {
        create(PUT, url, data, null, fileName);
    }
 
    public void putBitmap(String url, String data, byte[] byteBuffer, String fileName) {
        create(PUTBITMAP, url, data, byteBuffer, fileName);
    }
    
    public void delete(String url) {
        create(DELETE, url, null, null, "");
    }
 
    public void bitmap(String url) {
        create(BITMAP, url, null, null, "");
    }
 
    public void run() {
        handler.sendMessage(Message.obtain(handler, HttpConnection.DID_START));
        httpClient = new DefaultHttpClient();
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 25000);
        try {
            HttpResponse response = null;
            switch (method) {
            case GET:
            	HttpGet httpGet = new HttpGet(url);  // 2-27-2013 mod
                if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, Defines.contextGlobal).substring(0,11).equals("107.1.38.45"))
            	{
                    // added 2-27-2013 for new server basic versus anonymous authentication
            		httpGet.setHeader("Authorization", "Basic "+Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, Defines.contextGlobal).getBytes(), Base64.NO_WRAP));
            	}
                //response = httpClient.execute(new HttpGet(url)); 2-27-2013 mod
            	response = httpClient.execute(httpGet);
                break;
            case POST:
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new StringEntity(data));
                response = httpClient.execute(httpPost);
                break;
            case PUT:
                HttpPut httpPut = new HttpPut(url);   
                if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, Defines.contextGlobal).substring(0,11).equals("107.1.38.45"))
            	{               
            		httpPut.setHeader("Authorization", "Basic "+Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, Defines.contextGlobal).getBytes(), Base64.NO_WRAP));
            	}
                httpPut.setEntity(new StringEntity(data));
                response = httpClient.execute(httpPut);
                break;
            case PUTBITMAP:
                HttpPut httpPutBitmap = new HttpPut(url);
                //httpPutBitmap.setEntity(new StringEntity(data));
                if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, Defines.contextGlobal).substring(0,11).equals("107.1.38.45"))
            	{               
            		httpPutBitmap.setHeader("Authorization", "Basic "+Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, Defines.contextGlobal).getBytes(), Base64.NO_WRAP));
            	}               
                httpPutBitmap.setEntity(new ByteArrayEntity(byteBuffer));
                response = httpClient.execute(httpPutBitmap);
                break;                
            case DELETE:
            	HttpDelete  httpDelete = new HttpDelete(url); // 2-27-2013 mod
            	if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, Defines.contextGlobal).substring(0,11).equals("107.1.38.45"))
            	{
            		httpDelete.setHeader("Authorization", "Basic "+Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, Defines.contextGlobal).getBytes(), Base64.NO_WRAP));
            	}
                //response = httpClient.execute(new HttpDelete(url)); // 2-27-2013 mod
            	response = httpClient.execute(httpDelete); // 2-27-2013 mod
                break;
            case BITMAP:
                response = httpClient.execute(new HttpGet(url));
                processBitmapEntity(response.getEntity());
                break;
            }
            if (method < BITMAP)
                processEntity(response.getEntity(), fileName);
            
        } catch (Exception e) {
            handler.sendMessage(Message.obtain(handler,
                    HttpConnection.DID_ERROR, e));
        }
        ConnectionManager.getInstance().didComplete(this);
    }
 
    private void processEntity(HttpEntity entity, String extraMessage) throws IllegalStateException,
            IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(entity
                .getContent()));
        String line, result = "";
        while ((line = br.readLine()) != null)
            result += line;
        Message message = Message.obtain(handler, DID_SUCCEED, result + extraMessage);
        handler.sendMessage(message);
    }
 
    private void processBitmapEntity(HttpEntity entity) throws IOException {
        BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
        Bitmap bm = BitmapFactory.decodeStream(bufHttpEntity.getContent());
        handler.sendMessage(Message.obtain(handler, DID_SUCCEED, bm));
    }
 
}