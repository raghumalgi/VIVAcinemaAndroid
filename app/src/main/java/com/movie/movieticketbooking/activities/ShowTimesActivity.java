package com.movie.movieticketbooking.activities;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.utils.MyOnTabChangeListener;
import com.movie.movieticketbooking.vos.Movie;

@SuppressLint("NewApi")
public class ShowTimesActivity extends FragmentActivity implements
OnPageChangeListener, TabListener {

	private NetworkTask<Void, Void, List<Movie>> fetchTask;
	private ViewPager mViewPager;
	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movielist);
		findViewById(R.id.location).setVisibility(View.GONE);
		View  view1 = findViewById(R.id.home);
		View  view2 = findViewById(R.id.account);
		View  view3 = findViewById(R.id.history);
		View  view4 = findViewById(R.id.aboutus);
		view1.setOnClickListener(new BottomTabListener());
		view2.setOnClickListener(new BottomTabListener());
		view3.setOnClickListener(new BottomTabListener());
		view4.setOnClickListener(new BottomTabListener());
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		LayoutInflater inflater = getLayoutInflater();
		mTabHost.addTab(mTabHost.newTabSpec("ShowTimes").setIndicator(makeTabView(inflater , "ShowTimes")),
				ShowTimesFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("More").setIndicator(makeTabView(inflater, "More")),
				MoreInfoFragment.class, null);
		
		mTabHost.setOnTabChangedListener(new MyOnTabChangeListener(mTabHost, R.id.mainTab));

		findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	View makeTabView(LayoutInflater inflater, String s){
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.tab1, null);
		((TextView) view.findViewById(R.id.mainTabText)).setText(s);
		return view;
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
}