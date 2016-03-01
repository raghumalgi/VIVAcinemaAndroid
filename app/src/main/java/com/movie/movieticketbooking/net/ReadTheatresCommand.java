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
import com.movie.movieticketbooking.models.ScreenDetail;
import com.movie.movieticketbooking.parsers.ParseResponse;

public class ReadTheatresCommand extends JsonRequestCommand {
	
	private String url;
	private String movieId;
	
	public ReadTheatresCommand(String url, String movieId) {
		this.url = url;
		this.movieId  = movieId;
	}
	
	public List<ScreenDetail> execute() throws IOException, SAXException,JsonSyntaxException {
		
		Uri.Builder builder = Uri.parse(url).buildUpon();
		if(movieId!=null)
		builder.appendQueryParameter("movieid", movieId);
		
		 List<ScreenDetail> items1 = null;
		InputStream inStream = requestStream(builder.toString());
		Log.d("Movie"," input stream formed is "+inStream.toString());
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		 String s =  b.readLine();
		 Log.d("Movie"," theatre list formed is "+s);
		
			// items1  =  ParseResponse.getMeTheatresByMovie(s);
			 items1  =  ParseResponse.getMeScreenId(s);
		 return items1;
	}
}





