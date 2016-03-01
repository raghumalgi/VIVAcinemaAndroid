package com.movie.movieticketbooking.activities;

import java.util.prefs.Preferences;

import com.movie.movieticketbooking.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class BottomTabListener implements android.view.View.OnClickListener {

	private Boolean isLoggedIn;
	private Preferences pref;
	private Activity sendingActivity;

	public BottomTabListener() {
		sendingActivity = null;
	}

	public BottomTabListener(Activity activity) {
		sendingActivity = activity;
	}

	public void onClick(View view) {
		int i;
		Context context;
		i = view.getId();
		context = view.getContext();
		// pref = new Preferences(context);
		// isLoggedIn = pref.getBlnLoginStatus();
		// if(i != 2131296288) goto _L2; else goto _L1
		// if(isLoggedIn.booleanValue()) goto _L4; else goto _L3
		if (i == R.id.account) {
			Intent intent3 = new Intent(context, UserLoginActivity.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			context.startActivity(intent3);
			if (sendingActivity != null) {
				sendingActivity.finish();
				return;
			}
		}
		/*
		 * Intent intent4 = new Intent(context, UserProfile.class);
		 * intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		 * context.startActivity(intent4); if (sendingActivity != null) {
		 * sendingActivity.finish(); return; }
		 */
		// continue;
		if (i == R.id.aboutus) {
			Intent intent = new Intent(context, AboutUsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			context.startActivity(intent);
			if (sendingActivity != null) {
				sendingActivity.finish();
				return;
			}
		}
		if (i == R.id.history) {
			Intent intent1 = new Intent(context, BookingHistory.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			context.startActivity(intent1);
			if (sendingActivity != null) {
				sendingActivity.finish();
				return;
			}
		}
		/*if (i == R.id.home) {
			Intent intent2 = new Intent(context, MovieListActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent2);
			if (sendingActivity != null) {
				sendingActivity.finish();
				return;
			}
		}*/
	}

}
