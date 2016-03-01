package com.movie.movieticketbooking.business;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.Registration;
import com.movie.movieticketbooking.net.ReadRegisterCommand;
import com.movie.movieticketbooking.net.Registerparams;

public class FetchRegisterTask extends NetworkTask<Void, Void, Registration> {
	
	private Registerparams params;
	private ReadRegisterCommand command;
	public FetchRegisterTask(Registerparams params) {
		this.params = params;
	}
	
	@Override
	public void abort() {
		super.abort();
		if (command != null) command.cancel();
	}
	
	@Override
	protected Registration doNetworkAction() throws IOException, SAXException,JsonParseException,JsonSyntaxException {
		command = new ReadRegisterCommand(params);
		return command.execute();
	}

	@Override
	protected void onPostSuccess(Registration result) {
		
	}

		

}
