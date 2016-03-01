package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.models.MallDetails;
import com.movie.movieticketbooking.models.ShowTimemodel;
import com.movie.movieticketbooking.net.ReadMovieCommand;
import com.movie.movieticketbooking.net.ReadShowTimingsCommand;
import com.movie.movieticketbooking.net.ReadTheatresCommand;
import com.movie.movieticketbooking.vos.Movie;
import com.movie.movieticketbooking.vos.MovieItems;

public class FetchShowTimingTask extends NetworkTask<Void, Void, List<ShowTimemodel>> {
	
	private String movieUrl;
	private ReadShowTimingsCommand command;
	private String theatreId;
	private String movieId;
	
	public FetchShowTimingTask(String movieUrl,String theatreId,String movieId) {
		this.movieUrl = movieUrl;
		this.theatreId = theatreId;
		this.movieId = movieId;
		
	}
	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected List<ShowTimemodel> doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadShowTimingsCommand(movieUrl,theatreId,movieId);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(List<ShowTimemodel> result) {
//		/SampleModel.getInstance().setCurrentMovieItems(result);
	}

}
