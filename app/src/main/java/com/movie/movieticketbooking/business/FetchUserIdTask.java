package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.BookingDetails;
import com.movie.movieticketbooking.models.ContinueGuest;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.net.ReadBookedSeatsCommand;
import com.movie.movieticketbooking.net.ReadMovieByTheatresCommand;
import com.movie.movieticketbooking.net.ReadMovieCommand;
import com.movie.movieticketbooking.net.ReadUserIdCommand;
import com.movie.movieticketbooking.vos.Movie;
import com.movie.movieticketbooking.vos.MovieItems;

public class FetchUserIdTask extends NetworkTask<Void, Void, ContinueGuest> {
	
	private String movieUrl;
	private String taskType;
	private String param1;
	private String param2;
	private ReadUserIdCommand command;
	public FetchUserIdTask(String movieUrl,String taskType,String param1,String param2) {
		this.movieUrl = movieUrl;
		this.taskType = taskType;
		this.param1 = param1;
		this.param2 = param2;
	}
	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected ContinueGuest doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadUserIdCommand(movieUrl,taskType,param1,param2);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(ContinueGuest result) {
		
	}

		

}
