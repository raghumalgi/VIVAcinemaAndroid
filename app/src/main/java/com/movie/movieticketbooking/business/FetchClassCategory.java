package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.Category;
import com.movie.movieticketbooking.net.ReadCategoryCommand;

public class FetchClassCategory extends NetworkTask<Void, Void,Category> {
	
	private String movieUrl;
	private String showId;
	private ReadCategoryCommand command;
	
	public FetchClassCategory(String movieUrl,String showId) {
		this.movieUrl = movieUrl;
		this.showId = showId;
	}
	

	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected Category doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadCategoryCommand(movieUrl,showId);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(Category result) {
	}

}
