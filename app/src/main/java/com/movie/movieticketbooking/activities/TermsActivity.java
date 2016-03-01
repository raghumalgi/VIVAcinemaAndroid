package com.movie.movieticketbooking.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.movie.movieticketbooking.R;

public class TermsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.terms);
		TextView tv = (TextView) findViewById(R.id.text);
		String first   = "Online Bookings are open for todays shows only.Booking closes 30 minutes before show starts.Should you need further assistance, call us at  ";
		String next = "<font color='#DD0D7E'>33188188</font>";
		tv.setText(Html.fromHtml(first + next));
	}


	public void backClicked(View v){
		finish();
	}
	
}
