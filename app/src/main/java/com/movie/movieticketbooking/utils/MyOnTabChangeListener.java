package com.movie.movieticketbooking.utils;


import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.OnTabChangeListener;

public class MyOnTabChangeListener implements OnTabChangeListener {
	final TabHost tabHost;
	final int id;

	public MyOnTabChangeListener(TabHost tabHost) {
		this(tabHost, 0);
	}

	public MyOnTabChangeListener(TabHost tabHost, int id) {
		this.tabHost = tabHost;
		this.id = id;
		onTabChanged(tabHost.getCurrentTabTag());
	}

	@Override
	public void onTabChanged(String tabId) {
		int cur = tabHost.getCurrentTab();

		TabWidget widget = tabHost.getTabWidget();
		int cnt = widget.getChildCount();
		for (int i = 0; i < cnt; i++){
			View v = widget.getChildAt(i);
			if(id!=0) v = v.findViewById(id);
			v.setBackgroundColor(Color.parseColor(i == cur ? "#F20056" : "#800080"));
		}
	}
}