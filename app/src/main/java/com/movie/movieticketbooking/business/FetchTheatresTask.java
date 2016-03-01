package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.ScreenDetail;
import com.movie.movieticketbooking.net.ReadTheatresCommand;

public class FetchTheatresTask extends NetworkTask<Void, Void, List<ScreenDetail>> {
	
	private String movieUrl;
	private String movieId;
	private ReadTheatresCommand command;
	
	public FetchTheatresTask(String movieUrl,String movieId) {
		this.movieUrl = movieUrl;
		this.movieId = movieId;
	}
	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected List<ScreenDetail> doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadTheatresCommand(movieUrl,movieId);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(List<ScreenDetail> result) {
//		/SampleModel.getInstance().setCurrentMovieItems(result);
	}

}
