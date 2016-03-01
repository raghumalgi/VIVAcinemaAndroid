package com.movie.movieticketbooking.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import org.xml.sax.SAXException;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.BookingDetails;
import com.movie.movieticketbooking.models.CategoryDto;
import com.movie.movieticketbooking.models.Order;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.parsers.ParseResponse;
import com.movie.movieticketbooking.utils.MovieParams;

public class ReadBookedSeatsCommand extends JsonRequestCommand {
	
	private String rssFeed;
	private float amount;
	private String bookedSeats;
	private String show;
	private int tickets;
	private String classType;
	private String userId;
	private String task;
	private String pType;
	private String emailId;
	private String mobile;
	private String comboIds;
	private String build;
	private String comboStr;
	
	
	
	public ReadBookedSeatsCommand(String rssFeed,float amount,
			String bookedSeats,String classType,String show,String userId,int tickets) {
		this.rssFeed = rssFeed;
		this.amount = amount;
		this.bookedSeats = bookedSeats;
		this.show = show;
		this.classType = classType;
		this.userId = userId;
		this.tickets = tickets;
	}
	

	public ReadBookedSeatsCommand(String rssFeed,float amount,
			String bookedSeats,String classType,String show,String userId,int tickets,String task,String pType
			,String emailId,String mobile,String comboIds) {
		this.rssFeed = rssFeed;
		this.amount = amount;
		this.bookedSeats = bookedSeats;
		this.show = show;
		this.classType = classType;
		this.userId = userId;
		this.tickets = tickets;
		this.task =task;
		this.pType =pType;
		this.emailId =emailId;
		this.mobile =mobile;
		this.comboIds =comboIds;
		
	}
	
	public BookingDetails execute() throws IOException, SAXException,JsonSyntaxException {
		
		Uri.Builder builder = Uri.parse(rssFeed).buildUpon();
		CategoryDto cat = SampleModel.getInstance().getCategory();
		Order order = SampleModel.getInstance().getOrderSummary();
		builder.appendQueryParameter("Tickts", ""+tickets);
		builder.appendQueryParameter("amnt", ""+amount);
		builder.appendQueryParameter("class", classType);
		if(task.equalsIgnoreCase(ReadUserIdCommand.GUEST_LOGIN)){
			builder.appendQueryParameter("Userid", "=");
		}else{
		builder.appendQueryParameter("Userid", userId);
		}
		
		builder.appendQueryParameter("show", show);
		builder.appendQueryParameter("Sdt", MovieParams.getCurrentDate());
		builder.appendQueryParameter("booked", bookedSeats);
		builder.appendQueryParameter("src", "Mobile");
		if(task.equalsIgnoreCase(ReadUserIdCommand.GUEST_LOGIN)){
			builder.appendQueryParameter("isGuest", "true");
			builder.appendQueryParameter("ipaddr", "127.0.0.1");
			builder.appendQueryParameter("browertype", "Chrome");
			builder.appendQueryParameter("deviceos", "Android");
			builder.appendQueryParameter("paymenttype", pType);
			builder.appendQueryParameter("mobile", mobile);
			builder.appendQueryParameter("emailid", emailId);
		
			
		}else{
			builder.appendQueryParameter("isGuest", "false");
			builder.appendQueryParameter("ipaddr", "127.0.0.1");
			builder.appendQueryParameter("browertype", "Chrome");
			builder.appendQueryParameter("deviceos", "Android");
			builder.appendQueryParameter("paymenttype", pType);
			
		}
		String builderStr = URLDecoder.decode(builder.build().toString());
		
		if(task.equalsIgnoreCase(ReadUserIdCommand.USER_LOGIN)){
			 build = builderStr.toString();
			 boolean combos = order.comboIds.length()>0;
			 if(combos){
				 comboStr ="comboids="; 
			 }else{
				 comboStr ="comboids="+order.comboIds; 
			 }
			build = build+"&mobile=&"+"emailid=&"+comboStr;
			
		}else{
			 build = builderStr.toString();
			 boolean combos = order.comboIds.length()>0;
			 Log.d("VIVA"," combos  "+combos);
			 if(combos){
				 comboStr ="&comboids="; 
			 }else{
				 comboStr ="&comboids="+order.comboIds; 
			 }
			 Log.d("VIVA"," combos str "+comboStr);
			build = build+comboStr;
		}
		 Log.d("VIVA"," url for booking formed is "+build);
		 InputStream inStream = requestStream(build);
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		 String s =  b.readLine();
		 Log.d("VIVA"," Bookin formed is "+s);
		 BookingDetails items1  =  ParseResponse.getMeBookingId(s);
		 
      
		return items1;
	}
}