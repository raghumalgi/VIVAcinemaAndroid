package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.vos.Movie;

public class FetchMovieErrorTask extends NetworkTask<Void, Void, List<Movie>> {
	
	public FetchMovieErrorTask() {
		
	}
	
	@Override
	protected List<Movie> doNetworkAction() throws IOException, SAXException {
		throw new IOException("could not find feed");
	}

	@Override
	protected void onPostFault(Exception e) {
		super.onPostFault(e);
		SampleModel.getInstance().setCurrentMovieItems(null);
	}

}