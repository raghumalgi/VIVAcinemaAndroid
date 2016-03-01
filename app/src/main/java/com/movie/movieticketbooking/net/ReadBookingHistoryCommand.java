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
import com.movie.movieticketbooking.parsers.ParseResponse;

public class ReadBookingHistoryCommand extends JsonRequestCommand {
	
	private String rssFeed;
	private String booking;
	
	public ReadBookingHistoryCommand(String rssFeed, String booking) {
		this.rssFeed = rssFeed;
		this.booking = booking;
	}
	
	public List<BookingItems> execute() throws IOException, SAXException,JsonSyntaxException {
		Uri.Builder builder = Uri.parse(rssFeed).buildUpon();
		//builder.appendQueryParameter("bookingid", booking);
		builder.appendQueryParameter("userid", booking);
		
		Log.d("VIVA","Url for booking history "+builder.toString());
		InputStream inStream = requestStream(builder.toString());
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		String s =  b.readLine();
		Log.d("VIVA","json for booking history "+s);
		List<BookingItems> items1  =  ParseResponse.getBookingHistory(s);
		return items1;
	}
}