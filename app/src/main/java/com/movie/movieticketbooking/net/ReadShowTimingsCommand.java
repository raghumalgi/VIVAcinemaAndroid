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
import com.movie.movieticketbooking.models.MallDetails;
import com.movie.movieticketbooking.models.ShowTimemodel;
import com.movie.movieticketbooking.parsers.ParseResponse;
import com.movie.movieticketbooking.utils.MovieParams;

public class ReadShowTimingsCommand extends JsonRequestCommand {
	
	private String url;
	private String theatreId;
	private String movieId;
	
	public ReadShowTimingsCommand(String url,String theatreId,String movieId) {
		this.url = url;
		this.theatreId = theatreId;
		this.movieId = movieId;
	}
	
	public List<ShowTimemodel> execute() throws IOException, SAXException,JsonSyntaxException {
		Uri.Builder builder = Uri.parse(url).buildUpon();
		builder.appendQueryParameter("ScreenID", theatreId);
		builder.appendQueryParameter("movieid", movieId);
		//builder.appendQueryParameter("curdate",MovieParams.getCurrentDate());
		Log.d("VIVA","Url built is "+builder.toString());
		
		InputStream inStream = requestStream(builder.toString());
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		 String s =  b.readLine();
		 Log.d("VIVA","json i is "+s);
		 List<ShowTimemodel> items1  =  ParseResponse.getMeShowTimes(s);
		 return items1;
	}
}