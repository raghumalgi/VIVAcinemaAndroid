package com.movie.movieticketbooking.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchCouponsTask;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.models.Coupon;
import com.movie.movieticketbooking.models.Order;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.net.ReadUserIdCommand;
import com.movie.movieticketbooking.utils.MovieParams;

public class FinalSummaryActivity extends Activity {
	Order order ;
	boolean selectedDebit = false;
	boolean selectedCredit = true;
	private LinearLayout progressLayout;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_details);
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		order = SampleModel.getInstance().getOrderSummary();
		order.paymentType = "CREDIT";
		order.creditCardText = "Credit Card";
		((TextView) findViewById(R.id.txtTitle)).setText(order.movie);
		String s = order.showTime;
		s= s.substring(s.indexOf("<b>")+3, s.indexOf("</b>"));
		
		
	final TextView debit = 	((TextView) findViewById(R.id.debit));
	final TextView credit = 	((TextView) findViewById(R.id.credit));
	final ImageView debitTick = 	((ImageView) findViewById(R.id.debit_tick));
	final ImageView creditTick = 	((ImageView) findViewById(R.id.credit_tick));

	
	debit.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			order.creditCardText = "Debit Card";
			if(selectedCredit){
				credit.setBackgroundColor(Color.parseColor("#E6E7E8")); 
				creditTick.setVisibility(View.GONE);
			}
			debit.setBackgroundColor(Color.parseColor("#DD0D7E")); 
			debitTick.setVisibility(View.VISIBLE);
			selectedDebit = true;
			selectedCredit=false;
			order.paymentType = "DEBIT";
		}
	});
	credit.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			order.creditCardText = "Credit Card";
			if(selectedDebit){
				debit.setBackgroundColor(Color.parseColor("#E6E7E8")); 
				debitTick.setVisibility(View.GONE);
			}
				credit.setBackgroundColor(Color.parseColor("#DD0D7E")); 
				creditTick.setVisibility(View.VISIBLE);
			selectedCredit=true;
			selectedDebit=false;
			order.paymentType = "CREDIT";
		}
	});
		
		((TextView) findViewById(R.id.one)).setText(SampleModel.getInstance().getScreenDetail().screenName);
		((TextView) findViewById(R.id.two)).setText(s);
		((TextView) findViewById(R.id.three)).setText(""+order.bookedSeats);
		((TextView) findViewById(R.id.four)).setText("BD "+order.amount);
		findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SampleModel.getInstance().setOrderSummary(order);
				FinalLoginActivity.taskType = ReadUserIdCommand.USER_LOGIN;
				Intent intent = new Intent(FinalSummaryActivity.this,FinalLoginActivity.class);
				startActivity(intent);
				SampleModel.getInstance().activities.add(FinalSummaryActivity.this);
			}
		});
		
		findViewById(R.id.guest).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SampleModel.getInstance().setOrderSummary(order);
				FinalLoginActivity.taskType = ReadUserIdCommand.GUEST_LOGIN;
				Intent intent = new Intent(FinalSummaryActivity.this,FinalLoginActivity.class);
				startActivity(intent);
				SampleModel.getInstance().activities.add(FinalSummaryActivity.this);
				
			}
		});
		
		fetchCoupons();
		
	}
	private NetworkTask<Void, Void,Coupon> fetchTask;
	protected ProgressDialog pd;
	
	public void backClicked(View v){
		finish();
	}
	
	private void fetchCoupons() {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		fetchTask = createTask(MovieParams.GET_COUPON_ID);
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<Coupon>() {
			@Override
			public void onComplete(Coupon result) {
				SampleModel.getInstance().setCouponList(result);
				//pd.dismiss();
				progressLayout.setVisibility(View.GONE);
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
			//	pd.dismiss();
			progressLayout.setVisibility(View.GONE);
				exception.printStackTrace();
				Toast.makeText(FinalSummaryActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchTask.execute();
	}
	
	private NetworkTask<Void, Void, Coupon> createTask(String url) {
		pd  = new ProgressDialog(this);
		pd.setMessage("Loading ...");
		//pd.show();
		progressLayout.setVisibility(View.VISIBLE);
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchCouponsTask(url,SampleModel.getInstance().getTheatreId());
	}
	
	
	

}
