package com.movie.movieticketbooking.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.xml.sax.SAXException;

import android.net.Uri;

import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.SeatLayout;
import com.movie.movieticketbooking.parsers.ParseResponse;
import com.movie.movieticketbooking.utils.MovieParams;

public class ReadLayoutCommand extends JsonRequestCommand {
	
	private String rssFeed;
	private String showId;
	private String classId;
	
	public ReadLayoutCommand(String rssFeed, String showId, String classId) {
		this.rssFeed = rssFeed;
		this.showId = showId;
		this.classId = classId;
	}
	
	public SeatLayout execute() throws IOException, SAXException,JsonSyntaxException {
		Uri.Builder builder = Uri.parse(rssFeed).buildUpon();
		builder.appendQueryParameter("ShowID", showId);
		//builder.appendQueryParameter("classid", classId);
		//builder.appendQueryParameter("sdt", MovieParams.getCurrentDate());
		
		InputStream inStream = requestStream(builder.toString());
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		String s =  b.readLine();
		System.out.println("Seat layout "+s);
		SeatLayout items1  =  ParseResponse.getMeTktList(s);
		return items1;
	}
}