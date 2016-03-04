package com.movie.movieticketbooking.activities;

import com.movie.movieticketbooking.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AboutUsActivity extends Activity {
	//INtent with four spaces
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		View  view1 = findViewById(R.id.home);
		View  view2 = findViewById(R.id.account);
		View  view3 = findViewById(R.id.history);
		view1.setOnClickListener(new BottomTabListener(this));
		view2.setOnClickListener(new BottomTabListener(this));
		view3.setOnClickListener(new BottomTabListener(this));
		findViewById(R.id.location).setVisibility(View.GONE);
		findViewById(R.id.back_btn).setVisibility(View.GONE);
	}

}
