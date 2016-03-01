package com.movie.movieticketbooking.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.vos.MovieItems;

public class YoutubeActivity extends Activity {
	YouTubePlayer YPlayer;
	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youtube);
		final MovieItems item = SampleModel.getInstance().getCurrentMovie();
	    YouTubePlayerFragment youTubePlayerFragment =
	        (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
	    
	    youTubePlayerFragment.initialize("AIzaSyDVPHTspusDKMJK6WpeUe48JBG52V5QGWw", new OnInitializedListener() {

            @Override
            public void onInitializationSuccess(Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                	 YPlayer = youTubePlayer;
                    YPlayer.loadVideo(getYouTubeIdFromIdOrUrl(item.videourl));
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });

	}
	
	
	public static String getYouTubeIdFromIdOrUrl(String idOrUrl) {
	    Uri uri = Uri.parse(idOrUrl);
	    if (uri.getScheme() != null) {
	        return uri.getLastPathSegment();
	    } else {
	        return idOrUrl;
	    }
	}


}
