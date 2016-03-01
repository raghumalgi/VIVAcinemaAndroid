package com.movie.movieticketbooking.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.xml.sax.SAXException;

import android.net.Uri;

import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.Category;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.models.SeatLayout;
import com.movie.movieticketbooking.parsers.ParseResponse;
import com.movie.movieticketbooking.utils.MovieParams;

public class ReadCategoryCommand extends JsonRequestCommand {
	
	private String rssFeed;
	private String showId;
	
	public ReadCategoryCommand(String rssFeed,String showId) {
		this.rssFeed = rssFeed;
		this.showId = showId;
	}
	
	public Category execute() throws IOException, SAXException,JsonSyntaxException {
		Uri.Builder builder = Uri.parse(rssFeed).buildUpon();
		showId = SampleModel.getInstance().getShowId();
		builder.appendQueryParameter("ShowID", showId);
		//builder.appendQueryParameter("sdt", MovieParams.getCurrentDate());
		
		InputStream inStream = requestStream(builder.toString());
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		String s =  b.readLine();
		System.out.println("Class is "+s);
		Category items1  =  ParseResponse.getMeCategories(s);
		return items1;
	}
}