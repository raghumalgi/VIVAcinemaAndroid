package com.movie.movieticketbooking.business;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.Coupon;
import com.movie.movieticketbooking.net.ReadCouponsCommand;

public class FetchCouponsTask extends NetworkTask<Void, Void, Coupon> {
	
	private String movieUrl;
	private String mallId;
	private ReadCouponsCommand command;
	
	
	
	public FetchCouponsTask(String movieUrl,String mallId) {
		this.movieUrl = movieUrl;
		this.mallId = mallId;
	}
	

	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected Coupon doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadCouponsCommand(movieUrl,mallId);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(Coupon result) {
		
	}

		

}
