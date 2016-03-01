package com.movie.movieticketbooking.business;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.BookingDetails;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.net.ReadBookedSeatsCommand;
import com.movie.movieticketbooking.net.ReadMovieByTheatresCommand;
import com.movie.movieticketbooking.net.ReadMovieCommand;
import com.movie.movieticketbooking.vos.Movie;
import com.movie.movieticketbooking.vos.MovieItems;

public class FetchBookingDetailsTask extends NetworkTask<Void, Void, BookingDetails> {
	
	private String movieUrl;
	private float amount;
	private ReadBookedSeatsCommand command;
	private String bookedSeats;
	private String classType;
	private String userId;
	private int tickets;
	private String show;
	
	private String task;
	private String pType;
	private String emailId;
	private String mobile;
	private String comboIds;
	
	public FetchBookingDetailsTask(String movieUrl,float amount,
			String bookedSeats,String classType,String show,String userId,int tickets) {
		this.movieUrl = movieUrl;
		this.amount = amount;
		this.bookedSeats = bookedSeats;
		this.show = show;
		this.classType = classType;
		this.userId = userId;
		this.tickets = tickets;
	}
	
	public FetchBookingDetailsTask(String movieUrl,float amount,
			String bookedSeats,String classType,String show,String userId,int tickets,String task,String pType
			,String emailId,String mobile,String comboIds) {
		this.movieUrl = movieUrl;
		this.amount = amount;
		this.bookedSeats = bookedSeats;
		this.show = show;
		this.classType = classType;
		this.userId = userId;
		this.tickets = tickets;
		this.task = task;
		this.pType = pType;
		this.emailId = emailId;
		this.mobile = mobile;
	}
	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected BookingDetails doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadBookedSeatsCommand(movieUrl,amount,bookedSeats,classType,show,userId,tickets,task,pType,emailId,mobile,comboIds);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(BookingDetails result) {
		
	}

		

}
