package com.clancy.aticket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
 

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

public class HTTPFileTransfer {
	   
	 	private final String PATH = "/data/data/com.clancy.aticket/files/";
	  //private final String PATH = "";
        public void DownloadFileBinary(String fileName, String URLFile, Context dan) {
        		String URLString = "HTTP://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan)+"/";
                try {
                		//URL url = new URL("http://173.164.42.142/ClancyDemo/Comm/" + URLFile);
                		URL url = new URL(URLString+"DemoTickets/" + URLFile);
                        File file = new File(PATH+fileName);
                        /* Open a connection to that URL. */
                        URLConnection ucon = url.openConnection();
                        ucon.setUseCaches(false); 
                        ucon.setRequestProperty("Cache-Control", "no-cache");
                        // added 2-27-2013 for new server basic versus anonymous authentication
                        if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan).substring(0,11).equals("107.1.38.45"))
                        {
                        	ucon.addRequestProperty("Authorization", "Basic " +Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, dan).getBytes(), Base64.NO_WRAP));
                        }

                        InputStream is = ucon.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
 
                        ByteArrayBuffer baf = new ByteArrayBuffer(50);
                        int current = 0;
                        while ((current = bis.read()) != -1) {
                                baf.append((byte) current);
                        }
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(baf.toByteArray());
                        fos.close(); 
                } 
        		catch(FileNotFoundException e) { 
        			//Toast.makeText(dan, "Ex: "+e.toString(), Toast.LENGTH_LONG).show();
        			//Do not display anything for this exception
        			//Toast.makeText(dan, "Ex: "+e.toString(), 2000).show();
        		}               
        		catch(Throwable t) { 
        			//Toast.makeText(dan, "Ex: "+t.toString(), 2000).show();
        		}   
        }

        public String DownloadHBoxFile(String fileName, String URLFile, Context dan) {
    		String URLString = "HTTP://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan)+"/";
    		String Success = "FAILED";
            try {
            		//URL url = new URL("http://173.164.42.142/ClancyDemo/Comm/" + URLFile);
            		URL url = new URL(URLString+"DemoTickets/" + URLFile);
                    File file = new File(PATH+fileName);
                    /* Open a connection to that URL. */
                    URLConnection ucon = url.openConnection();
                    ucon.setUseCaches(false); 
                    ucon.setRequestProperty("Cache-Control", "no-cache");
                    // added 2-27-2013 for new server basic versus anonymous authentication
                    if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan).substring(0,11).equals("107.1.38.45"))
                    {
                    	ucon.addRequestProperty("Authorization", "Basic " +Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, dan).getBytes(), Base64.NO_WRAP));
                    }

                    InputStream is = ucon.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);

                    ByteArrayBuffer baf = new ByteArrayBuffer(50);
                    int current = 0;
                    while ((current = bis.read()) != -1) {
                            baf.append((byte) current);
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(baf.toByteArray());
                    fos.close(); 
                    Success = "SUCCESS";
            } 
    		catch(FileNotFoundException e) { 
    			//Toast.makeText(dan, "Ex: "+e.toString(), 2000).show();
    			//Do not display anything for this exception
    			//Toast.makeText(dan, "Ex: "+e.toString(), 2000).show();
    			Success = "NEW";
    		}               
    		catch(Throwable t) { 
    			//Toast.makeText(dan, "Ex: "+t.toString(), 2000).show();
    			Success = "ERROR";
    		}  
    		return Success;
    }

        public void GetNewClancyJ(String UnitNumber, Context dan) {
        		String URLString = "HTTP://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan)+"/";
                try {
       
                		//URL url = new URL("http://173.164.42.142/ClancyDemo/Comm/" + URLFile);
                		URL url = new URL(URLString+"DemoTickets/" + UnitNumber + "/CLANCY.J");
                        File file = new File(PATH+"CLANCY.J");
 
                        /* Open a connection to that URL. */
                        URLConnection ucon = url.openConnection();
                        ucon.setUseCaches(false); 
                        ucon.setRequestProperty("Cache-Control", "no-cache");
                        // added 2-27-2013 for new server basic versus anonymous authentication
                        if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan).substring(0,11).equals("107.1.38.45"))
                        {
                        	ucon.addRequestProperty("Authorization", "Basic " +Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, dan).getBytes(), Base64.NO_WRAP));
                        }
                        
 
                        /*
                         * Define InputStreams to read from the URLConnection.
                         */
                        InputStream is = ucon.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
 
                        /*
                         * Read bytes to the Buffer until there is nothing more to read(-1).
                         */
                        ByteArrayBuffer baf = new ByteArrayBuffer(50);
                        int current = 0;
                        while ((current = bis.read()) != -1) {
                                baf.append((byte) current);
                        }
 
                        /* Convert the Bytes read to a String. */
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(baf.toByteArray());
                        fos.close();
                
                } 
        		catch(FileNotFoundException e) { 
        			Toast.makeText(dan, "Ex: "+e.toString(), 2000).show();
        			Toast.makeText(dan, "Unable to locate a new range of citations for this unit number", 2000).show();
        			//Do not display anything for this exception
        		}               
        		catch(Throwable t) { 
        			//Toast.makeText(dan, "Ex: "+t.toString(), 2000).show();
        		}   
 
        }    
        public static void DeleteClancyJ(String UnitNumber, Context dan) 
        {
    		String URLString = "HTTP://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan)+"/";
    		
    		
            /*try {
            		URI url = new URI(URLString+"DemoTickets/" + UnitNumber + "/CLANCY.J");
            		HttpDelete httpDel = new HttpDelete(url);

            } 
    		catch(Throwable t) { 
    			Toast.makeText(dan, "Ex: "+t.toString(), 2000).show();
    		} */  

        }    

        public static Boolean UploadJPGBinary(String localFile, String uploadUrl, Context dan)
        {
        	Boolean SentSuccess = false;
        	if (SearchData.GetFileSize(localFile)==0) //Local File Not Found
        	{
        		return SentSuccess;
        	}
        	final String username = WorkingStorage.GetCharVal(Defines.RemoteUsernameVal,dan);
        	final String password = WorkingStorage.GetCharVal(Defines.RemotePasswordVal,dan);
        	if (username.equals(""))
        	{
            	Authenticator.setDefault(new Authenticator() {
            		String nPassword = "096224276";
            		protected PasswordAuthentication getPasswordAuthentication() {
            		return new PasswordAuthentication("clancy"+(char)92+"clancy", nPassword.toCharArray());
            		} 
            	});
        	}
        	else
        	{
            	Authenticator.setDefault(new Authenticator() {
            		protected PasswordAuthentication getPasswordAuthentication() {
            		return new PasswordAuthentication(username, password.toCharArray());
            		} 
            	});
        	}
        	
        	
        	HttpURLConnection connection = null;
        	DataOutputStream outputStream = null;

        	String pathToOurFile = localFile;
        	String urlServer = uploadUrl;
        	String boundary =  "*****";
        	
        	int bytesRead, bytesAvailable, bufferSize;
        	byte[] buffer;
        	int maxBufferSize = 1*1024*1024;

        	try
        	{
        		FileInputStream fileInputStream = new FileInputStream(new File("/data/data/com.clancy.aticket/files/"+pathToOurFile) );

        		URL url = new URL(urlServer);
        		connection = (HttpURLConnection) url.openConnection();

        		// Allow Inputs & Outputs
        		connection.setDoInput(true);
        		connection.setDoOutput(true);
        		connection.setUseCaches(false);
        		connection.setRequestMethod("PUT");        	
        		connection.setRequestProperty("Connection", "Keep-Alive");
        		connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

        		outputStream = new DataOutputStream( connection.getOutputStream() );

        		bytesAvailable = fileInputStream.available();
        		bufferSize = Math.min(bytesAvailable, maxBufferSize);
        		buffer = new byte[bufferSize];

        		// Read file
        		bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        		while (bytesRead > 0)
        		{
        			outputStream.write(buffer, 0, bufferSize);
        			bytesAvailable = fileInputStream.available();
        			bufferSize = Math.min(bytesAvailable, maxBufferSize);
        			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        		}

        		fileInputStream.close();
        		outputStream.flush();
        		outputStream.close();
        		// Responses from the server (code and message)
        		//need some sort of timeout here to give the server time to write the file and return 200
        		
        		
        		int serverResponseCode = connection.getResponseCode();
        		String serverResponseMessage = connection.getResponseMessage();
        		if (serverResponseCode == 200 && serverResponseMessage.equals("OK"))
        		{
        			//Don't do anything, but call the three lines above us to give IIS time to 
        			//write and close the file.
        		}
        		connection.disconnect();
        		// Server response code may not be reliable
        		if ((serverResponseCode == 200 || serverResponseCode == 201) && (serverResponseMessage.equals("OK") || serverResponseMessage.equals("Created")))
        		{
        			SentSuccess = true;
        		}
        		else
        		{
        			SentSuccess = false;
        		}
        	}catch (Exception ex)
        	{
        		//Toast.makeText(dan, "Upload Ex: "+ex.toString(), 2000).show();
        	}

        	return SentSuccess;
        }  
        
        public static Boolean UploadFileBinary(String localFile, String uploadUrl, Context dan)
        {
        	Boolean SentSuccess = false;
        	if (SearchData.GetFileSize(localFile)==0) //Local File Not Found
        	{
        		return SentSuccess;
        	}
        	final String username = WorkingStorage.GetCharVal(Defines.RemoteUsernameVal,dan);
        	final String password = WorkingStorage.GetCharVal(Defines.RemotePasswordVal,dan);
        	if (username.equals(""))
        	{
            	Authenticator.setDefault(new Authenticator() {
            		String nPassword = "096224276";
            		protected PasswordAuthentication getPasswordAuthentication() {
            		return new PasswordAuthentication("clancy"+(char)92+"clancy", nPassword.toCharArray());
            		} 
            	});
        	}
        	else
        	{
            	Authenticator.setDefault(new Authenticator() {
            		protected PasswordAuthentication getPasswordAuthentication() {
            		return new PasswordAuthentication(username, password.toCharArray());
            		} 
            	});
        	}
        	
        	
        	HttpURLConnection connection = null;
        	DataOutputStream outputStream = null;

        	String pathToOurFile = localFile;
        	String urlServer = uploadUrl;
        	String boundary =  "*****";
        	
        	int bytesRead, bytesAvailable, bufferSize;
        	byte[] buffer;
        	int maxBufferSize = 1*1024*1024;

        	try
        	{
        		FileInputStream fileInputStream = new FileInputStream(new File("/data/data/com.clancy.aticket/files/"+pathToOurFile) );

        		URL url = new URL(urlServer);
        		connection = (HttpURLConnection) url.openConnection();

        		// Allow Inputs & Outputs
        		connection.setDoInput(true);
        		connection.setDoOutput(true);
        		connection.setUseCaches(false);
        		connection.setRequestMethod("PUT");        	
        		connection.setRequestProperty("Connection", "Keep-Alive");
        		connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

        		outputStream = new DataOutputStream( connection.getOutputStream() );

        		bytesAvailable = fileInputStream.available();
        		bufferSize = Math.min(bytesAvailable, maxBufferSize);
        		buffer = new byte[bufferSize];

        		// Read file
        		bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        		while (bytesRead > 0)
        		{
        			outputStream.write(buffer, 0, bufferSize);
        			bytesAvailable = fileInputStream.available();
        			bufferSize = Math.min(bytesAvailable, maxBufferSize);
        			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        		}

        		fileInputStream.close();
        		outputStream.flush();
        		outputStream.close();
        		// Responses from the server (code and message)
        		//need some sort of timeout here to give the server time to write the file and return 200
        		
        		
        		int serverResponseCode = connection.getResponseCode();
        		String serverResponseMessage = connection.getResponseMessage();
        		if (serverResponseCode == 200 && serverResponseMessage.equals("OK"))
        		{
        			//Don't do anything, but call the three lines above us to give IIS time to 
        			//write and close the file.
        		}
        		connection.disconnect();

        		long WebServerFileSize = HTTPGetFileSize(uploadUrl, dan);
        		long LocalFileSize = SearchData.GetFileSize(localFile);
        		if (WebServerFileSize == LocalFileSize)
        		{
        			SentSuccess = true;
        		}
        		else
        		{
        			SentSuccess = false;
        		}
        		// Server response code may not be reliable
        		/*if (serverResponseCode == 200 && serverResponseMessage.equals("OK"))
        		{
        			SentSuccess = true;
        		}
        		else
        		{
        			Messagebox.Message("Upload Error: "+ serverResponseCode + " "+ serverResponseMessage + " "+ localFile , dan);
        		}*/
        	}catch (Exception ex)
        	{
        		//Toast.makeText(dan, "Upload Ex: "+ex.toString(), 2000).show();
        	}

        	return SentSuccess;
        }

        public static long HTTPGetFileSize(String URLFile, Context dan) 
        {
        	long HTTPFileSize = 0;
            try {
            		URL url = new URL(URLFile);
                    URLConnection ucon = url.openConnection();
                    ucon.setUseCaches(false); 
                    ucon.setRequestProperty("Cache-Control", "no-cache");
                    // added 2-27-2013 for new server basic versus anonymous authentication
                    if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan).substring(0,11).equals("107.1.38.45"))
                    {
                    	ucon.addRequestProperty("Authorization", "Basic " +Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, dan).getBytes(), Base64.NO_WRAP));
                    }
                    InputStream is = ucon.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);

                    ByteArrayBuffer baf = new ByteArrayBuffer(50);
                    int current = 0;
                    while ((current = bis.read()) != -1) {
                            baf.append((byte) current);
                    }
                    HTTPFileSize = baf.length();

                   // FileOutputStream fos = new FileOutputStream(file);
                   // fos.write(baf.toByteArray());
                   // fos.close(); 
            } 
    		catch(FileNotFoundException e) { 
    			//Toast.makeText(dan, "Ex: "+e.toString(), 2000).show();
    			//Do not display anything for this exception
    		}               
    		catch(Throwable t) { 
    			//Toast.makeText(dan, "Ex: "+t.toString(), 2000).show();
    		}   
    		return HTTPFileSize;
    }
        
        
        public static void RefreshFile(String Filename, Context dan)
        {
    		String URLString = "HTTP://"+WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan)+"/";
            try {
   
            		//URL url = new URL("http://173.164.42.142/ClancyDemo/Comm/" + URLFile);
            		//URL url = new URL(URLString+"comm/" + Filename);
            		URL url = new URL(URLString+"DemoTickets/" + Filename);
                    File file = new File("/data/data/com.clancy.aticket/files/"+Filename);

                    /* Open a connection to that URL. */
                    URLConnection ucon = url.openConnection();
                    ucon.setUseCaches(false); 
                    ucon.setRequestProperty("Cache-Control", "no-cache");
                    // added 2-27-2013 for new server basic versus anonymous authentication
                    if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan).substring(0,11).equals("107.1.38.45"))
                    {
                    	ucon.addRequestProperty("Authorization", "Basic " +Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, dan).getBytes(), Base64.NO_WRAP));
                    }

                    /*
                     * Define InputStreams to read from the URLConnection.
                     */
                    InputStream is = ucon.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);

                    /*
                     * Read bytes to the Buffer until there is nothing more to read(-1).
                     */
                    ByteArrayBuffer baf = new ByteArrayBuffer(50);
                    int current = 0;
                    while ((current = bis.read()) != -1) {
                            baf.append((byte) current);
                    }

                    /* Convert the Bytes read to a String. */
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(baf.toByteArray());
                    fos.close();
            
            } 
    		catch(FileNotFoundException e) { 
    			//Toast.makeText(dan, "Ex: "+e.toString(), 2000).show();
    			//Toast.makeText(dan, "Unable to refresh file:"+Filename, 2000).show();
    			//Do not display anything for this exception
    		}               
    		catch(Throwable t) { 
    			//Toast.makeText(dan, "Ex: "+t.toString(), 2000).show();
    		}   
        	
        }
        
        public static String HTTPGetPageContent(String URLFile, Context dan) {
    			//    		
    			String inLine = "FAILED";
    			HttpClient httpclient = new DefaultHttpClient();
    			
    	        HttpGet httpget = new HttpGet(URLFile); 
    	        httpget.setHeader("Cache-Control", "no-cache");
                if (WorkingStorage.GetCharVal(Defines.UploadIPAddress, dan).substring(0,11).equals("107.1.38.45"))
            	{
                    // added 2-27-2013 for new server basic versus anonymous authentication
            		httpget.setHeader("Authorization", "Basic "+Base64.encodeToString(WorkingStorage.GetCharVal(Defines.MobilePutDelVal, dan).getBytes(), Base64.NO_WRAP));
            	}   
    	        HttpResponse response;
    	        try {
    	            response = httpclient.execute(httpget);
    	            HttpEntity entity = response.getEntity();
    	 
    	            if (entity != null) {
    	                InputStream instream = entity.getContent();
    	                inLine = convertStreamToString(instream);
    	                instream.close();
    	            }
    	        } catch(Throwable t) { 
        			//Toast.makeText(dan, "Ex: "+t.toString(), 2000).show();
        		}   
    	        return inLine;
        }
        
        
        
        private static String convertStreamToString(InputStream is) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
     
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    //sb.append(line + "\n");
                	sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
        
        

}