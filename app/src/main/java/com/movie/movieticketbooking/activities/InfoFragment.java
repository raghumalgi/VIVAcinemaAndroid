package com.movie.movieticketbooking.activities;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.models.MallDetails;
import com.movie.movieticketbooking.vos.MovieItems;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoFragment extends Fragment {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.info, container,false);
		initOptions();
		setMovieDetails(root);
		return root;
	}

	private void setMovieDetails(ViewGroup root) {
		final MallDetails item = SampleModel.getInstance().getCurrentMall();
		imageLoader.displayImage(item.imagePath, ((ImageView) root.findViewById(R.id.movie_img)), options, null);
		((TextView) root.findViewById(R.id.title)).setText(item.theatreName);
		((TextView) root.findViewById(R.id.address)).setText(item.address);
		((TextView) root.findViewById(R.id.theatre_details)).setText(item.desc);
		/*((Button) root.findViewById(R.id.locate)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	                    startActivity(new Intent("android.intent.action.VIEW", 
	                    		Uri.parse((new StringBuilder("http://maps.google.com/maps?saddr=")).
	                    	append(strLatitude).append(",").
	                    	append(strLongitude).append("&daddr=").
	                    	append(currVenue.getStrLatitude()).append(",").
	                    	append(currVenue.getStrLongitude()).toString())));
				
			}
		});*/
		
	}
	
	private void initOptions() {
		options = new DisplayImageOptions.Builder()
		.showStubImage(android.R.drawable.gallery_thumb)
		.showImageForEmptyUri(android.R.drawable.gallery_thumb)
		.showImageOnFail(android.R.drawable.gallery_thumb)
		.showImageOnLoading(android.R.drawable.progress_indeterminate_horizontal)
		.cacheOnDisc(true)
		.cacheInMemory(false)
		.imageScaleType(ImageScaleType.EXACTLY) // default
        .bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
	}
	

}
