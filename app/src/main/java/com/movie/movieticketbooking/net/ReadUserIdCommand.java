package com.movie.movieticketbooking.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.xml.sax.SAXException;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.BookingItems;
import com.movie.movieticketbooking.models.ContinueGuest;
import com.movie.movieticketbooking.parsers.ParseResponse;

public class ReadUserIdCommand extends JsonRequestCommand {
	
	private String rssFeed;
	private String booking;
	private String taskType;
	private String param1;
	private String param2;
	
	public static final String USER_LOGIN="user_login";
	public static final String GUEST_LOGIN="guest_login";
	
	public ReadUserIdCommand(String rssFeed, String taskType,String param1,String param2) {
		this.rssFeed = rssFeed;
		this.taskType = taskType;
		this.param1 = param1;
		this.param2 = param2;
		
	}
	
	public ContinueGuest execute() throws IOException, SAXException,JsonSyntaxException {
		boolean isUserLogin = false;
		Uri.Builder builder = Uri.parse(rssFeed).buildUpon();
		builder.appendQueryParameter("username", param1);
		if(taskType.equals(USER_LOGIN)){
			isUserLogin = true;
			builder.appendQueryParameter("password", param2);
		}else{
			builder.appendQueryParameter("mobileNo", param2);
			builder.appendQueryParameter("source", "Android");
		}
		Log.d("VIVA","Url for user id "+builder.toString());
		InputStream inStream = requestStream(builder.toString());
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		String s =  b.readLine();
		Log.d("VIVA","response for user id "+s);
		ContinueGuest items1  =  ParseResponse.getBookingId(s,isUserLogin);
		return items1;
	}
}