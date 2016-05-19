package com.clancy.aticket;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;



public class GoogleMapsForm extends ActivityGroup {

	 
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemapsform);
       
        Button second = (Button) findViewById(R.id.buttonESC);
        second.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               finish();
               overridePendingTransition(0, 0);
          }          
        });
        setupWebView2();

	 }
	 
 
	  private void setupWebView2()
	  {
		  String MAP_URL = "http://173.164.42.142/ClancyGoogleMaps.html";

		  final String centerURL = "javascript:centerAt(" +
		    WorkingStorage.GetCharVal(Defines.LatitudeVal, getApplicationContext()) + "," +
		    WorkingStorage.GetCharVal(Defines.LongitudeVal, getApplicationContext())+ ")";
		    final WebView webView = (WebView) findViewById(R.id.webView1);
		    webView.getSettings().setJavaScriptEnabled(true);
		    //Wait for the page to load then send the location information		    
		    //webView.setWebViewClient(new WebViewClient());
		    webView.setWebViewClient(new WebViewClient(){
		        @Override
		        public void onPageFinished(WebView view, String url){
		          webView.loadUrl(centerURL);
		        }
		      });
		    
		    webView.loadUrl(MAP_URL);
	  }	 

}