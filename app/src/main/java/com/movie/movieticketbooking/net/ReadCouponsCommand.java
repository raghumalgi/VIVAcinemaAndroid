package com.movie.movieticketbooking.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.xml.sax.SAXException;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.movie.movieticketbooking.models.Coupon;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.parsers.ParseResponse;

public class ReadCouponsCommand extends JsonRequestCommand {
	
	private String rssFeed;
	private String mallId;
	
	public ReadCouponsCommand(String rssFeed,String mallId) {
		this.rssFeed = rssFeed;
		this.mallId = mallId;
	}
	
	public Coupon execute() throws IOException, SAXException,JsonSyntaxException {
		Uri.Builder builder = Uri.parse(rssFeed).buildUpon();
		mallId = SampleModel.getInstance().getTheatreCouponId();
		builder.appendQueryParameter("mallid", mallId);
		Log.d("Movie","Movie formed url is "+builder.toString());
		InputStream inStream = requestStream(builder.toString());
		 BufferedReader b  = new BufferedReader(new InputStreamReader(inStream));
		String s =  b.readLine();
		Log.d("Movie","ouput from coupson"+s);
		Coupon  items1  =  ParseResponse.getCoupons(s);
		return items1;
	}
}