package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.activities.TheatreList;
import com.movie.movieticketbooking.net.ReadAllTheatresCommand;

public class FetchAllTheatresTask extends NetworkTask<Void, Void, List<TheatreList>> {
	
	private String movieUrl;
	private ReadAllTheatresCommand command;
	
	public FetchAllTheatresTask(String movieUrl) {
		this.movieUrl = movieUrl;
	}
	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected List<TheatreList> doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadAllTheatresCommand(movieUrl);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(List<TheatreList> result) {
//		/SampleModel.getInstance().setCurrentMovieItems(result);
	}

}
