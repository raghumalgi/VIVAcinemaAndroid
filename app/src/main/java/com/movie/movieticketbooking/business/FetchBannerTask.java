package com.movie.movieticketbooking.business;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.net.ReadBannerCommand;
import com.movie.movieticketbooking.parsers.Banner;

public class FetchBannerTask extends NetworkTask<Void, Void, Banner> {
	
	private String movieUrl;
	private ReadBannerCommand command;
	public FetchBannerTask(String movieUrl) {
		this.movieUrl = movieUrl;
	}
	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected Banner doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadBannerCommand(movieUrl);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(Banner result) {
		
	}

		

}
