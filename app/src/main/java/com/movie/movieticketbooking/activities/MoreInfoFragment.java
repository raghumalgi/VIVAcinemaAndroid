package com.movie.movieticketbooking.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.vos.MovieItems;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MoreInfoFragment extends Fragment {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.more, container,false);
		initOptions();
		setMovieDetails(root);
		return root;
	}

	private void setMovieDetails(ViewGroup root) {
		MovieItems item = SampleModel.getInstance().getCurrentMovie();
		
		imageLoader.displayImage(item.movieBannerImage, ((ImageView) root.findViewById(R.id.movie_img)), options, null);
		((TextView) root.findViewById(R.id.title)).setText(""+item.MovieTitle+" "+"("+item.moviecer+")");
		((TextView) root.findViewById(R.id.dir_txt)).setText(item.director);
		((TextView) root.findViewById(R.id.genre_txt)).setText(item.Genre);
		((TextView) root.findViewById(R.id.cast_text)).setText(item.Cast);
		((TextView) root.findViewById(R.id.language_txt)).setText(item.LanguageDesc);
		((TextView) root.findViewById(R.id.synopsis_txt)).setText(item.Description);
		
		RatingBar rb  = ((RatingBar) root.findViewById(R.id.ratingbar));
		rb.setIsIndicator(true);
		Log.d("VIVA","Rating is "+item.rating);
		((Button) root.findViewById(R.id.rating_txt)).setText(item.rating);
		int i =Integer.parseInt(item.rating);
		rb.setRating(i);
		
	}
	
	private void initOptions() {
		options = new DisplayImageOptions.Builder()
		/*.showImageForEmptyUri(android.R.drawable.gallery_thumb)
		.showImageOnFail(android.R.drawable.gallery_thumb)*/
		.showImageOnLoading(android.R.drawable.progress_indeterminate_horizontal)
		.cacheOnDisc(true)
		.cacheInMemory(false)
		.imageScaleType(ImageScaleType.EXACTLY) // default
        .bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
	}
	
	
	
	
	

}
