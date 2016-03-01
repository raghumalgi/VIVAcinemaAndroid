package com.movie.movieticketbooking.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.net.ReadUserIdCommand;
import com.movie.movieticketbooking.utils.NetworkUtil;


@SuppressLint("SetJavaScriptEnabled")
public class PaymentActivity extends Activity{
	
	private WebView mWebView;
	private View mLoadingSpinner;
	String paymentUrl ="http://vivabahraincinema.com/PaymentProcess.aspx?BookingID=";
	String taskType;
	private Context ctx;
	private String bookingId = "";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_webview_with_spinner);
		Bundle b = getIntent().getExtras();
		if(b!=null){
			bookingId = b.getString("bookingId");
			taskType = b.getString("task");
			String userId = SampleModel.getInstance().getCurrentUserId();
			
			if(taskType.equals(ReadUserIdCommand.GUEST_LOGIN)){
				userId ="9C1471E7-28C4-41C3-AA4B-04830CF53C35";
			}
			
	paymentUrl = "http://37.131.68.76/devcinema/Payment/Process.aspx?BookingID="+bookingId+"&"+"UserID="+userId+"&"+"Source=mobile";

			Log.d("Movie","Payment url is "+paymentUrl);
		}
		mLoadingSpinner = findViewById(R.id.loading_spinner);
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setWebViewClient(new WebViewClient(){
			 @Override
			 public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				 
			     handler.proceed(); // Ignore SSL certificate errors
			 }
		});
		mWebView.setWebViewClient(mWebViewClient);
		new WebViewTask().execute();
		

	}
	
	
	

	
	
	
	@SuppressWarnings("unused")
	private final WebChromeClient mWebChromeClient = new WebChromeClient() {
		@Override
		public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
			Log.e("Movie", "JS Console message: (" + consoleMessage.sourceId() + ": "
					+ consoleMessage.lineNumber() + ") " + consoleMessage.message());
			return false;
		}
		
	};

	private final WebViewClient mWebViewClient = new WebViewClient() {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			mLoadingSpinner.setVisibility(View.VISIBLE);
			mWebView.setVisibility(View.INVISIBLE);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			mLoadingSpinner.setVisibility(View.GONE);
			mWebView.setVisibility(View.VISIBLE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description,
				String failingUrl) {
			Log.e("Movie", "Error " + errorCode + ": " + description);
			Toast.makeText(view.getContext(), "Error " + errorCode + ": " + description,
					Toast.LENGTH_LONG).show();
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;

		};
		
	};

	private class WebViewTask extends AsyncTask<Void, Void, Boolean> {
		String sessionCookie;
		CookieManager cookieManager;

		@Override
		protected void onPreExecute() {
			mLoadingSpinner.setVisibility(View.VISIBLE);
			CookieSyncManager.createInstance(PaymentActivity.this);
			cookieManager = CookieManager.getInstance();
			cookieManager.setAcceptCookie(true);
			sessionCookie = cookieManager.getCookie(paymentUrl);
			if (sessionCookie != null) {
				// delete old cookies
				cookieManager.removeSessionCookie(); 
			}
			super.onPreExecute();
		}
		protected Boolean doInBackground(Void... param) {
			// this is very important - THIS IS THE HACK
			SystemClock.sleep(1000);
			return false;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			mLoadingSpinner.setVisibility(View.GONE);
			mWebView.getSettings().setJavaScriptEnabled(true);
			 if(!NetworkUtil.hasInternetAccess(getApplicationContext())){
				 mWebView.loadUrl("about:blank");
		    		Toast.makeText(ctx, "network not available", Toast.LENGTH_LONG).show();

			 }else{			
				 mWebView.loadUrl(paymentUrl);}
		}
	}

	/* to set cookies */
		public void setCookies(String URL,String name,String value){
			
			CookieManager cookieManager = CookieManager.getInstance();
			CookieSyncManager.getInstance().sync();
			cookieManager.setAcceptCookie(true);
			cookieManager.setCookie(URL, name+"="+value+";");
		}


}

