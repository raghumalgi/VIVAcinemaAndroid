package com.movie.movieticketbooking.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.utils.UIUtilities;

public class ForgotPasswordActivity extends Activity {
	
	EditText etCurrent;
	EditText etNew;
	EditText etConfirm;
	protected String strCurrent;
	protected String strNew;
	protected String strConfirm;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password);
		View  view1 = findViewById(R.id.home);
		View  view3 = findViewById(R.id.history);
		View  view4 = findViewById(R.id.aboutus);
		view1.setOnClickListener(new BottomTabListener(this));
		view3.setOnClickListener(new BottomTabListener(this));
		view4.setOnClickListener(new BottomTabListener(this));
		findViewById(R.id.location).setVisibility(View.GONE);
		findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Intent i = new Intent(ForgotPasswordActivity.this,UserLoginActivity.class);
				startActivity(i);*/
				finish();
				
			}
		});
		
		etCurrent = (EditText) findViewById(R.id.current);
		etNew = (EditText) findViewById(R.id.new_pass);
		etConfirm= (EditText) findViewById(R.id.confirm);
		setUpViews(	(Button) findViewById(R.id.submit));
		Button register = 	(Button) findViewById(R.id.location);
		register.setVisibility(View.GONE);
		findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	}

	void setUpViews(View btnSubmit){
		 btnSubmit.setOnClickListener(new android.view.View.OnClickListener() {

	            public void onClick(View view4)
	            {
	                strCurrent = etCurrent.getText().toString();
	                strNew = etNew.getText().toString();
	                strConfirm = etConfirm.getText().toString();
	                if(strCurrent == null || strCurrent.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(ForgotPasswordActivity.this, "Please Enter Your Current Password");
	                    etCurrent.setText("");
	                    etCurrent.requestFocus();
	                    return;
	                }
	                if(strNew == null || strNew.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(ForgotPasswordActivity.this, "Please Enter Your New Password");
	                    etNew.setText("");
	                    etNew.requestFocus();
	                    return;
	                }
	                if(strConfirm == null || strConfirm.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(ForgotPasswordActivity.this, "Please Confirm Your Password");
	                    etConfirm.setText("");
	                    etConfirm.requestFocus();
	                    return;
	                } else
	                {
	                   
	                }
	            }
	
});
	}
}
		 
