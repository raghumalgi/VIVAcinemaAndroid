package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.models.SeatLayout;
import com.movie.movieticketbooking.net.ReadLayoutCommand;
import com.movie.movieticketbooking.net.ReadMovieCommand;
import com.movie.movieticketbooking.vos.Movie;
import com.movie.movieticketbooking.vos.MovieItems;

public class FetchLayoutTask extends NetworkTask<Void, Void, SeatLayout> {
	
	private String movieUrl;
	private String showId;
	private String classId;
	private ReadLayoutCommand command;
	
	public FetchLayoutTask(String movieUrl, String showId, String classId) {
		this.movieUrl = movieUrl;
		this.showId = showId;
		this.classId = classId;
	}
	

	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected SeatLayout doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadLayoutCommand(movieUrl,showId,classId);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(SeatLayout result) {
	}

}
