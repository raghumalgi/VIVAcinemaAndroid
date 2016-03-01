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
import com.movie.movieticketbooking.activities.TheatreList;
import com.movie.movieticketbooking.parsers.ParseResponse;

public class ReadAllTheatresCommand extends JsonRequestCommand {
		
		private String url;
		
		public ReadAllTheatresCommand(String url) {
			this.url = url;
		}
		
		public List<TheatreList> execute() throws IOException, SAXException,JsonSyntaxException {
			
			Uri.Builder builder = Uri.parse(url).buildUpon();
			
			 List<TheatreList> items1 = null;
			InputStream inStream = requestStream(builder.toString());
			Log.d("Movie"," input stream formed is "+inStream.toString());
			 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
			 String s =  b.readLine();
			
				 items1  =  ParseResponse.getMeAllTheatreList(s);
			 return items1;
		}
	}