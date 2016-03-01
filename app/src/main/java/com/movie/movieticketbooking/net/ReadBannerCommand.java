package com.movie.movieticketbooking.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.xml.sax.SAXException;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.parsers.Banner;
import com.movie.movieticketbooking.parsers.ParseResponse;

public class ReadBannerCommand extends JsonRequestCommand {
		
		private String url;
		
		public ReadBannerCommand(String url) {
			this.url = url;
		}
		
		public Banner execute() throws IOException, SAXException,JsonSyntaxException {
			
			Uri.Builder builder = Uri.parse(url).buildUpon();
			
			InputStream inStream = requestStream(builder.toString());
			 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
			 String s =  b.readLine();
			 Log.d("VIVA","Banner sting "+s);
				 Banner banner  =  ParseResponse.getBannerImage(s);
			 return banner;
		}
	}