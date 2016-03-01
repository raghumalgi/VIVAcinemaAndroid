package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.net.ReadMovieByTheatresCommand;
import com.movie.movieticketbooking.net.ReadMovieCommand;
import com.movie.movieticketbooking.vos.Movie;
import com.movie.movieticketbooking.vos.MovieItems;

public class FetchMovieBytheatresTask extends NetworkTask<Void, Void, List<MovieItems>> {
	
	private String movieUrl;
	private String theatreid;
	private ReadMovieByTheatresCommand command;
	
	public FetchMovieBytheatresTask(String movieUrl,String theatreid) {
		this.movieUrl = movieUrl;
		this.theatreid = theatreid;
	}
	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected List<MovieItems> doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadMovieByTheatresCommand(movieUrl,theatreid);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(List<MovieItems> result) {
		SampleModel.getInstance().setCurrentMovieItems(result);
	}

}
