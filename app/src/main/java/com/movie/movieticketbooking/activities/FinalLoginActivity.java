package com.movie.movieticketbooking.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchBookingDetailsTask;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchUserIdTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.models.BookingDetails;
import com.movie.movieticketbooking.models.CategoryDto;
import com.movie.movieticketbooking.models.ContinueGuest;
import com.movie.movieticketbooking.models.Order;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.net.ReadUserIdCommand;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.utils.UIUtilities;

public class FinalLoginActivity extends Activity {
	Order order ;
	private EditText etPassword;
	private EditText etGuestEmail;
	private EditText etMobileNo;
	
	private	String strPassword;
	private String strGuestEmail;
	private String strMobileNo;
	private String strEmail;
	public static String taskType;
	private NetworkTask<Void, Void, ContinueGuest> fetchTask;
	public String selectedSeats;
	private LinearLayout progressLayout;
	
	public void backClicked(View v){
		finish();
	}
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.final_login_details);
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		order = SampleModel.getInstance().getOrderSummary();
		((TextView) findViewById(R.id.txtTitle)).setText(order.movie);
		String s = order.showTime;
		s= s.substring(s.indexOf("<b>")+3, s.indexOf("</b>"));
		((TextView) findViewById(R.id.one)).setText(SampleModel.getInstance().getScreenDetail().screenName);
		((TextView) findViewById(R.id.two)).setText(s);
		((TextView) findViewById(R.id.three)).setText(""+order.bookedSeats);
		((TextView) findViewById(R.id.four)).setText("BD "+order.amount);
		final TextView creditText = 	((TextView) findViewById(R.id.credit_card));
		creditText.setText(order.creditCardText);
		SampleModel sm = SampleModel.getInstance();
//		CategoryDto dto = sm.getCategory();
		StringBuilder builder = new StringBuilder();
		for(int i = 0;i<sm.selectedSeats.size();i++){
			builder.append(1+sm.selectedSeats.get(i));
			if(i!=sm.selectedSeats.size()-1){
				builder.append(",");
			}
			
		}
		selectedSeats = builder.toString();
		//etEmail = (EditText) findViewById(R.id.etUn);
		etPassword = (EditText) findViewById(R.id.etPass);
		etGuestEmail = (EditText) findViewById(R.id.etUn);
		etMobileNo = (EditText) findViewById(R.id.etMobile);
	
		if(taskType.equalsIgnoreCase(ReadUserIdCommand.GUEST_LOGIN)){
			((TextView) findViewById(R.id.un)).setText("Email");
			etPassword.setVisibility(View.GONE);
			((TextView) findViewById(R.id.pass)).setVisibility(View.GONE);
			setUpForGuest((TextView) findViewById(R.id.purchase));
		}else{
			((TextView) findViewById(R.id.mobile)).setVisibility(View.GONE);
			etMobileNo.setVisibility(View.GONE);
			setUpForLogin((TextView) findViewById(R.id.purchase));
		}
		
		
		
	}
	void setUpForLogin(View btnSubmit){
		 btnSubmit.setOnClickListener(new android.view.View.OnClickListener() {

	            public void onClick(View view4)
	            {
	            	//etGuestEmail.setText("georgemikfly@gmail.com");
	            	//etPassword.setText("manashanti@81");
	                strEmail = etGuestEmail.getText().toString();
	                strPassword = etPassword.getText().toString();
	                if(strEmail == null || strEmail.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(FinalLoginActivity.this, "Please Enter a Valid Email Address");
	                    etGuestEmail.setText("");
	                    etGuestEmail.requestFocus();
	                    return;
	                }
	                if(strPassword == null || strPassword.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(FinalLoginActivity.this, "Please Enter Your Password");
	                    etPassword.setText("");
	                    etPassword.requestFocus();
	                    return;
	                }
	                if(UIUtilities.checkEmailValidity(strEmail))
	                {
	                    UIUtilities.showAlertDialog(FinalLoginActivity.this, "Please Enter the Correct Email Address");
	                    etGuestEmail.setText("");
	                    etGuestEmail.requestFocus();
	                    return;
	                } else
	                {
	                	taskType = ReadUserIdCommand.USER_LOGIN;
		                  fetchUserId(MovieParams.LOGIN_API);
	                   
	                }
	            }
	            
	      
	});
}
	public void setUpForGuest(View btnSubmit){
		 btnSubmit.setOnClickListener(new android.view.View.OnClickListener() {

	            public void onClick(View view4)
	            {
	            	strGuestEmail = etGuestEmail.getText().toString();
	                strMobileNo = etMobileNo.getText().toString();
	                
	                if(strGuestEmail == null || strGuestEmail.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(FinalLoginActivity.this, "Please Enter a Valid Email Address");
	                    etGuestEmail.setText("");
	                    etGuestEmail.requestFocus();
	                    return;
	                }
	                if(strMobileNo == null || strMobileNo.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(FinalLoginActivity.this, "Please Enter your Mobile Number");
	                    etMobileNo.setText("");
	                    etMobileNo.requestFocus();
	                    return;
	                }
	                if(UIUtilities.checkEmailValidity(strGuestEmail))
	                {
	                    UIUtilities.showAlertDialog(FinalLoginActivity.this, "Please Enter the Correct Email Address");
	                    etGuestEmail.setText("");
	                    etGuestEmail.requestFocus();
	                    return;
	                } else
	                {
	                	Log.d("Movie","Mobile no "+strGuestEmail+" , "+strMobileNo);
	                	taskType = ReadUserIdCommand.GUEST_LOGIN;
	                	fetchBookingId();
	                   //fetchUserId(MovieParams.CONTINUE_GUEST_API);
	                }
	            }
	            
	      
	});
		 
		 
}
	private NetworkTask<Void, Void,BookingDetails> fetchBookingTask;
	private String comboIds;
	
	private void fetchBookingId() {
		if (fetchBookingTask != null && !fetchBookingTask.isComplete()) {
			fetchBookingTask.abort();
		}
		fetchBookingTask = createTask(MovieParams.SAVE_BOOKING_API);
		fetchBookingTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<BookingDetails>() {
			@Override
			public void onComplete(BookingDetails result) {
				if(result.bookingId!=null&&result.bookingId.length()>0){
					Intent i = new Intent(FinalLoginActivity.this,PaymentActivity.class);
					i.putExtra("bookingId", result.bookingId);
					i.putExtra("task", taskType);
					startActivity(i);
					SampleModel.getInstance().activities.add(FinalLoginActivity.this);
					SampleModel.getInstance().clearAll();
					SampleModel.getInstance().setOrderSummary(null);
				}
				//pd.dismiss();
			}
		});
		fetchBookingTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				progressLayout.setVisibility(View.GONE);
			//	pd.dismiss();
				exception.printStackTrace();
				Toast.makeText(FinalLoginActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchBookingTask.execute();
	}
	
	private NetworkTask<Void, Void, BookingDetails> createTask(String url) {
		progressLayout.setVisibility(View.VISIBLE);
		/*pd  = new ProgressDialog(this);
		pd.setMessage("Loading ...");
		pd.show();*/
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchBookingDetailsTask(url,order.amount,selectedSeats, order.classId, 
				order.show,order.userId ,order.tickets,taskType,order.paymentType,strGuestEmail,strMobileNo,comboIds);
	}
	
	
	
	
	private void fetchUserId(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		String param1;
		String param2;
		if(taskType.equals(ReadUserIdCommand.USER_LOGIN)){
			param1 = strEmail;
			param2 = strPassword;
		}else {
			param1 = strGuestEmail;
			param2 = strMobileNo;
		}
		
		fetchTask = createTask(rssFeed,param1,param2);
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<ContinueGuest>() {
			@Override
			public void onComplete(ContinueGuest result) {
				if(result.isValidUser){
					Order order = SampleModel.getInstance().getOrderSummary();
					if(taskType.equals(ReadUserIdCommand.USER_LOGIN)){
						SampleModel.getInstance().setCurrentUserId(result.userId);
						SampleModel.getInstance().setCurrentUserName(result.name);
					}
					order.userId = result.userId;
					fetchBookingId();
					//Intent i = new Intent(FinalLoginActivity.this,PaymentActivity.class);
					//startActivity(i);
				}else{
					Toast.makeText(FinalLoginActivity.this, result.status, Toast.LENGTH_LONG).show();
				}
				
				//pd.dismiss();
				progressLayout.setVisibility(View.GONE);
				
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				progressLayout.setVisibility(View.GONE);
				//pd.dismiss();
				exception.printStackTrace();
				Toast.makeText(FinalLoginActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchTask.execute();
	}
	
	private NetworkTask<Void, Void,ContinueGuest> createTask(String url,String param1,String param2) {
		/*pd  = new ProgressDialog(this);
		pd.setMessage("Loading ...");
		pd.setCancelable(false);
		pd.show();*/
		progressLayout.setVisibility(View.VISIBLE);
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchUserIdTask(url,taskType,param1,param2);
	}
	
	
	

}
