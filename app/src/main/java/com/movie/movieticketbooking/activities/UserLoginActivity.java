package com.movie.movieticketbooking.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchUserIdTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.models.ContinueGuest;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.net.ReadUserIdCommand;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.utils.UIUtilities;

public class UserLoginActivity extends Activity {
	
	EditText etEmail;
	EditText etPassword;
	TextView tvSignIn;
	TextView tvChangePassword;
	TextView tvLogout;
	protected String strEmail;
	protected String strPassword;
	private Button register;
	private NetworkTask<Void, Void, ContinueGuest> fetchTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		etEmail = (EditText) findViewById(R.id.email);
		etPassword = (EditText) findViewById(R.id.password);
		tvChangePassword = (TextView) findViewById(R.id.changePass);
		tvLogout = (TextView) findViewById(R.id.logout);
		tvSignIn = (TextView) findViewById(R.id.signin);
		setUpViews(	(TextView) findViewById(R.id.submit));
		/* register = 	(Button) findViewById(R.id.location);
		 //findViewById(R.id.back_btn).setVisibility(View.GONE);
		register.setText("");
		register.setBackgroundResource(R.drawable.register_bt);
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(UserLoginActivity.this,UserRegistrationActivity.class);
				startActivity(i);
				finish();
			}
		});*/
		/*findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});*/
		
		tvChangePassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(UserLoginActivity.this,ForgotPasswordActivity.class);
				startActivity(i);
				finish();
				
			}
		});
		tvLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SampleModel.getInstance().setCurrentUserId(null);
				SampleModel.getInstance().setCurrentUserName(null);
				finish();
				
			}
		});
		;
		
	}
	
	public void onRegister(View v){
		Intent intent = new Intent(this,UserRegistrationActivity.class);
		startActivity(intent);
	}

	public void backClicked(View v){
		finish();
	}

	private void checkForLogin() {
		String userId = SampleModel.getInstance().getCurrentUserId();
		if(userId!=null){
			etEmail.setVisibility(View.GONE);
			etPassword.setVisibility(View.GONE);
		//	findViewById(R.id.forgot_password_btn).setVisibility(View.GONE);
			tvLogout.setVisibility(View.VISIBLE);
			tvChangePassword.setVisibility(View.VISIBLE);
			tvSignIn.setVisibility(View.GONE);
			//register.setVisibility(View.GONE);
			findViewById(R.id.submit).setVisibility(View.GONE);
			TextView tvUser  = (TextView) findViewById(R.id.userName);
			tvUser.setVisibility(View.VISIBLE);
			tvUser.setText("Hi, "+SampleModel.getInstance().getCurrentUserName());
			
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkForLogin();
	}
	void setUpViews(View btnSubmit){
		 btnSubmit.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View view4)
	            {
	                strEmail = etEmail.getText().toString();
	                strPassword = etPassword.getText().toString();
	                if(strEmail == null || strEmail.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserLoginActivity.this, "Please Enter a Valid Email Address");
	                    etEmail.setText("");
	                    etEmail.requestFocus();
	                    return;
	                }
	                if(strPassword == null || strPassword.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserLoginActivity.this, "Please Enter Your Password");
	                    etPassword.setText("");
	                    etPassword.requestFocus();
	                    return;
	                }
	                if(UIUtilities.checkEmailValidity(strEmail))
	                {
	                    UIUtilities.showAlertDialog(UserLoginActivity.this, "Please Enter the Correct Email Address");
	                    etEmail.setText("");
	                    etEmail.requestFocus();
	                    return;
	                } else
	                {
	                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	                	imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
	                	fetchUserId(MovieParams.LOGIN_API);
	                	
	                }
	            }
	
});
	}
	
	private void fetchUserId(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		fetchTask = createTask(rssFeed,strEmail,strPassword);
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<ContinueGuest>() {
			@Override
			public void onComplete(ContinueGuest result) {
				if(result.isValidUser){
					SampleModel.getInstance().setCurrentUserId(result.userId);
					SampleModel.getInstance().setCurrentUserName(result.name);
					checkForLogin();
				}else{
				Toast.makeText(UserLoginActivity.this, result.status, Toast.LENGTH_LONG).show();
				}
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				exception.printStackTrace();
				Toast.makeText(UserLoginActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchTask.execute();
	}

	
	private NetworkTask<Void, Void,ContinueGuest> createTask(String url,String param1,String param2) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchUserIdTask(url,ReadUserIdCommand.USER_LOGIN,param1,param2);
	}
	
}
		 
