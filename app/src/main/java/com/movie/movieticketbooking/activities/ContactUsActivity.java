package com.movie.movieticketbooking.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchContactUsTask;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.utils.UIUtilities;

public class ContactUsActivity extends Activity {

	private NetworkTask<Void, Void, String> fetchTask;
	private String email;
	private String name;
	private String message;
	  private EditText etEmail;
	private EditText etName;
	private EditText etMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_us);
		TextView tv = (TextView) findViewById(R.id.text);
		String first   = "Should you need further assistance, call us at ";
		String next = "<font color='#DD0D7E'>33188188</font>";
		tv.setText(Html.fromHtml(first + next));
		etEmail = (EditText) findViewById(R.id.email);
		etName = (EditText) findViewById(R.id.name);
		etMsg = (EditText) findViewById(R.id.message);
		setUpViews(	(TextView) findViewById(R.id.submit));
		
	}

	void setUpViews(View btnSubmit){
		 btnSubmit.setOnClickListener(new View.OnClickListener() {

	          

				public void onClick(View view4)
	            {
					 email = etEmail.getText().toString();
	                message = etMsg.getText().toString();
	                name = etName.getText().toString();
	                if(email == null || email.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(ContactUsActivity.this, "Please Enter a Valid Email Address");
	                    etEmail.setText("");
	                    etEmail.requestFocus();
	                    return;
	                }
	                if(name == null || name.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(ContactUsActivity.this, "Please Enter Your Name");
	                    etName.setText("");
	                    etName.requestFocus();
	                    return;
	                }
	                if(message == null || message.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(ContactUsActivity.this, "Please Enter a Message");
	                    etMsg.setText("");
	                    etMsg.requestFocus();
	                    return;
	                }
	                if(UIUtilities.checkEmailValidity(email))
	                {
	                    UIUtilities.showAlertDialog(ContactUsActivity.this, "Please Enter the Correct Email Address");
	                    etEmail.setText("");
	                    etEmail.requestFocus();
	                    return;
	                } else
	                {
	                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	                	imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
	                	fetchUserId();
	                	
	                }
	            }
	
});
	}
	

	
	private void fetchUserId() {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		fetchTask = createTask(MovieParams.CONTACT_API);
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<String>() {
			@Override
			public void onComplete(String result) {
				Toast.makeText(ContactUsActivity.this, result, Toast.LENGTH_LONG).show();
				finish();
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				exception.printStackTrace();
				Toast.makeText(ContactUsActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchTask.execute();
	}

	
	private NetworkTask<Void, Void,String> createTask(String url) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchContactUsTask(email,name,url,message);
	}
}
