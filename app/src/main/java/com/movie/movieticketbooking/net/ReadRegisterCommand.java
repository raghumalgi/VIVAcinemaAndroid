package com.movie.movieticketbooking.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.xml.sax.SAXException;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.Registration;
import com.movie.movieticketbooking.parsers.ParseResponse;
import com.movie.movieticketbooking.utils.MovieParams;

public class ReadRegisterCommand extends JsonRequestCommand {
	
	Registerparams params;
	
	public ReadRegisterCommand(Registerparams params) {
		this.params = params;
	}
	
	public Registration execute() throws IOException, SAXException,JsonSyntaxException {
		Uri.Builder builder = Uri.parse(params.url).buildUpon();
		builder.appendQueryParameter("firstname", params.firstName);
		builder.appendQueryParameter("lastname", params.lastName);
		builder.appendQueryParameter("gender", params.gender);
		builder.appendQueryParameter("nationality", params.nationality);
		builder.appendQueryParameter("email",params.email);
		builder.appendQueryParameter("pwd", params.pwd);
		builder.appendQueryParameter("mobileno", params.mobile);
		
		//if(params.dob!=null){
			builder.appendQueryParameter("dob",MovieParams.getCurrentDate());
		//}
		builder.appendQueryParameter("src", "Android");
		Log.d("Movie","request for registration is "+builder.toString());
		InputStream inStream = requestStream(builder.toString());
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		String s =  b.readLine();
		Log.d("Movie","Response for registration is "+s);
		return ParseResponse.getRegisteredUserId(s);
	}
}