
package com.movie.movieticketbooking.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.vos.MovieItems;

public class MovieDetailActivity extends BaseActivity implements
YouTubePlayer.OnInitializedListener  {
	private MovieItems item;
	YouTubePlayer YPlayer;
	private static final int RECOVERY_DIALOG_REQUEST = 10;
@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SampleModel.getInstance().activities_nav.add(MovieDetailActivity.this);
		//setContentView(R.layout.movie_details);
		//activity = this;
		getLayoutInflater().inflate(R.layout.movie_details, frameLayout);
		item = SampleModel.getInstance().getCurrentMovie();
		((TextView) findViewById(R.id.txtTitle)).setText(item.MovieTitle);
		((TextView) findViewById(R.id.imdb)).setText(item.imdbRating);
		((TextView) findViewById(R.id.txtDesc)).setText(item.Description);
		((TextView) findViewById(R.id.tvStars)).setText(item.Cast);
		((TextView) findViewById(R.id.length)).setText(item.Cast);
		((TextView) findViewById(R.id.language)).setText(item.LanguageDesc);
		((TextView) findViewById(R.id.classification)).setText(item.movieCertifacte);
		((TextView) findViewById(R.id.director)).setText(item.director);
		((TextView) findViewById(R.id.genre)).setText(item.Genre);
		
	//	YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
	  //  youTubeView.initialize("AIzaSyDVPHTspusDKMJK6WpeUe48JBG52V5QGWw", this);
		

	findViewById(R.id.timings).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MovieDetailActivity.this,TheatreListActivityNew.class);
				startActivity(i);
				SampleModel.getInstance().activities.add(MovieDetailActivity.this);


				//finish();
				
			}
		});
	Log.d("VIVA","Youtube url "+item.videourl);
	Log.d("VIVA","Image url "+SampleModel.getInstance().getBanner());
	
	if(item.movieBannerImage!=null&&item.movieBannerImage.length()>0){
		Log.d("VIVA","Image not null url "+item.movieBannerImage);
		imageLoader.displayImage("http://37.131.68.76/"+item.movieBannerImage,   ((ImageView) findViewById(R.id.bannerIv)), options, null);
		}else{
			MovieYoutubeBaseActivity.displayImage(SampleModel.getInstance().getBanner(),   ((ImageView) findViewById(R.id.bannerIv)), this);
			
		}
		//imageLoader.displayImage("http://37.131.68.76/"+item.movieBannerImage,   ((ImageView) findViewById(R.id.bannerIv)), options, null);
	
		
	}
public static String getYouTubeIdFromIdOrUrl(String idOrUrl) {
    Uri uri = Uri.parse(idOrUrl);
    if (uri.getScheme() != null) {
        return uri.getLastPathSegment();
    } else {
        return idOrUrl;
    }
}


public void youtube(View v){
	Intent intent = new Intent(this,FullscreenDemoActivity.class);
	startActivity(intent);
}

	public void backClicked(View v){
		finish();
	}
	

	public void moreClicked(View v){
		//showActionSheet();
	}
	@Override
	public void onInitializationFailure(YouTubePlayer.Provider provider,
			YouTubeInitializationResult errorReason) {
		if (errorReason.isUserRecoverableError()) {
			errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
		} else {
			String errorMessage = String.format("YouTube Error (%1$s)",
					errorReason.toString());
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
		}

	}

	protected YouTubePlayer.Provider getYouTubePlayerProvider() {
		return (YouTubePlayerView) findViewById(R.id.aboutus);
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
       	 YPlayer = player;
           YPlayer.loadVideo(getYouTubeIdFromIdOrUrl(item.videourl));
           YPlayer.play();
           if(YPlayer.isPlaying()){
        	   YPlayer.pause();
           }
       }
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RECOVERY_DIALOG_REQUEST) {
			// Retry initialization if user performed a recovery action
			getYouTubePlayerProvider().initialize("AIzaSyDVPHTspusDKMJK6WpeUe48JBG52V5QGWw", this);
		}
	}

}
