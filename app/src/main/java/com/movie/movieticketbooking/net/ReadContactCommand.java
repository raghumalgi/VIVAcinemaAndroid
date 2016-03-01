package com.movie.movieticketbooking.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.xml.sax.SAXException;

import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.Coupon;
import com.movie.movieticketbooking.parsers.ParseResponse;

import android.net.Uri;
import android.util.Log;

public class ReadContactCommand extends JsonRequestCommand {
	
	private String rssFeed;
	private String email;
	private String message;
	private String name;
	
	public ReadContactCommand(String rssFeed,String email,String message,String name) {
		this.rssFeed = rssFeed;
		this.email = email;
		this.message = message;
		this.name = name;
	}
	
	public String execute() throws IOException, SAXException,JsonSyntaxException {
		Uri.Builder builder = Uri.parse(rssFeed).buildUpon();
		builder.appendQueryParameter("email", email);
		builder.appendQueryParameter("name", name);
		builder.appendQueryParameter("msg", message);
		Log.d("Movie","Movie formed url is "+builder.toString());
		InputStream inStream = requestStream(builder.toString());
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		String s =  b.readLine();
		//Log.d("Movie","ouput from coupson"+s);
		String  msg  =  ParseResponse.contactUs(s);
		return msg;
	}
}