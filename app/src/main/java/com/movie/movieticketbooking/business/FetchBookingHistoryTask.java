package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.BookingItems;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.net.ReadBookingHistoryCommand;
import com.movie.movieticketbooking.net.ReadMovieCommand;
import com.movie.movieticketbooking.vos.Movie;
import com.movie.movieticketbooking.vos.MovieItems;

public class FetchBookingHistoryTask extends NetworkTask<Void, Void, List<BookingItems>> {
	
	private String movieUrl;
	private String bookingid;
	private ReadBookingHistoryCommand command;
	
	public FetchBookingHistoryTask(String movieUrl,String theatreid) {
		this.movieUrl = movieUrl;
		this.bookingid = theatreid;
	}
	
	public FetchBookingHistoryTask(String url) {
		this(url,null);
	}

	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected List<BookingItems> doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadBookingHistoryCommand(movieUrl,bookingid);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(List<BookingItems> result) {
	}

}
