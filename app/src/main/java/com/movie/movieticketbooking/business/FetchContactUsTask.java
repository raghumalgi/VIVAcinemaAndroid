package com.movie.movieticketbooking.business;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.Registration;
import com.movie.movieticketbooking.net.ReadContactCommand;
import com.movie.movieticketbooking.net.ReadRegisterCommand;
import com.movie.movieticketbooking.net.Registerparams;

public class FetchContactUsTask extends NetworkTask<Void, Void, String> {
	
	private ReadContactCommand command;
	private String name;
	private String message;
	private String url;
	private String email;
	public FetchContactUsTask(String email,String name,String url,String message ) {
		this.email = email;
		this.url = url;
		this.message = message;
		this.name = name;
	}
	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected String doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadContactCommand(url,email,message,name);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(String result) {
		
	}

		

}
