package com.movie.movieticketbooking.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.utils.NetworkUtil;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 2000;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(1);
	//	Log.d("Movie ","Calender wise"+MovieParams.getDate());
		setContentView(R.layout.splash);
		if (!NetworkUtil.hasInternetAccess(getApplicationContext())) {
			{
				(new android.app.AlertDialog.Builder(this))
						.setTitle("Movie Ticket")
						.setMessage("No Internet Connection")
						.setCancelable(false)
						.setPositiveButton(
								"OK",
								new android.content.DialogInterface.OnClickListener() {

									public void onClick(
											DialogInterface dialoginterface,
											int i) {
										finish();
										Process.killProcess(Process.myPid());
									}

								}).create().show();
				return;
			}

		

	}else{
		checkReady();
	}
	}
	 /**
     * Check that Splash screen is time out to launch to Rootactivity.
     */
    private void checkReady() {
        long delay = Math.max(0, SPLASH_DISPLAY_LENGHT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchToRootActivity();
            }
        }, delay);
    }

  
    private void switchToRootActivity() {
        Intent intent = new Intent(SplashActivity.this, MovieGridActivity.class);
        startActivity(intent);
        finish();
    }

}
