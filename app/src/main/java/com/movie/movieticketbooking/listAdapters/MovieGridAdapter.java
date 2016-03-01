package com.movie.movieticketbooking.listAdapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.activities.ActionSheetBaseActivity;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MovieGridAdapter extends ArrayAdapter<MovieItems> {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	//private String IMAGE_URL = "http://vivabahraincinema.com/";
	private DisplayImageOptions options;
	private Context context;
	public MovieGridAdapter(Context context, List<MovieItems> objects) {
		super(context, 0, objects);
		this.context = context;
		initOptions();
		
	}
	
	
	private void initOptions() {
		options = new DisplayImageOptions.Builder()
		.cacheOnDisc(true)
		.showStubImage(android.R.drawable.gallery_thumb)
		.showImageForEmptyUri(android.R.drawable.gallery_thumb)
		.showImageOnFail(android.R.drawable.gallery_thumb)
		.cacheInMemory(false)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
        .bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
	}
	
	public ImageLoader getLoader(){
		return imageLoader;
	}

	public DisplayImageOptions getOptions(){
		return options;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = View.inflate(getContext(), R.layout.movie_item_grid, null);
			holder = new Holder();
			holder.movieicon = (ImageView)convertView.findViewById(R.id.imgPicture);
			holder.title = (TextView)convertView.findViewById(R.id.txtTitle);
			holder.imdb = (TextView)convertView.findViewById(R.id.imdb);
			convertView.setTag(holder);
		} else {
			holder = (Holder)convertView.getTag();
		}
		
		final MovieItems vo = getItem(position);
		Log.d("Movie","Imge url is "+vo.MovieImage);
		if(vo.MovieImage!=null){
			imageLoader.displayImage("http://37.131.68.76/"+vo.MovieImage,  holder.movieicon,options);
			
		}
		holder.title.setText(vo.MovieTitle);
		holder.imdb.setText(vo.imdbRating);
	/*
		holder.movieicon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + MovieParams.getYouTubeVideoId(vo.videourl)));
				context.startActivity(intent);
				
			}
		});*/
		
		return convertView;
	}
	
	private class Holder {
		private TextView title;
		private TextView imdb;
		private ImageView movieicon;
	}
	
	

}