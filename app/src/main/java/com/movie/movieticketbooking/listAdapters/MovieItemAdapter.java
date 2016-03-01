package com.movie.movieticketbooking.listAdapters;

import java.util.List;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.MovieDetails;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.Movie;
import com.movie.movieticketbooking.vos.MovieItems;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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

public class MovieItemAdapter extends ArrayAdapter<MovieItems> {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	//private String IMAGE_URL = "http://vivabahraincinema.com/";
	private DisplayImageOptions options;
	private Context context;
	public MovieItemAdapter(Context context, List<MovieItems> objects) {
		super(context, 0, objects);
		this.context = context;
		initOptions();
		
	}
	
	
	private void initOptions() {
		options = new DisplayImageOptions.Builder()
		.showStubImage(android.R.drawable.gallery_thumb)
		.showImageForEmptyUri(android.R.drawable.gallery_thumb)
		.showImageOnFail(android.R.drawable.gallery_thumb)
		.cacheOnDisc(true)
		.cacheInMemory(false)
		.imageScaleType(ImageScaleType.EXACTLY) // default
        .bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = View.inflate(getContext(), R.layout.movie_item_times, null);
			holder = new Holder();
			holder.movieicon = (ImageView)convertView.findViewById(R.id.imgPicture);
			holder.title = (TextView)convertView.findViewById(R.id.txtTitle);
			holder.genre = (TextView)convertView.findViewById(R.id.txtGenre);
			holder.language = (TextView)convertView.findViewById(R.id.txtLang);
			holder.length = (TextView)convertView.findViewById(R.id.txtLen);
			holder.actors = (TextView)convertView.findViewById(R.id.txtActr);
			convertView.setTag(holder);
		} else {
			holder = (Holder)convertView.getTag();
		}
		
		final MovieItems vo = getItem(position);
		Log.d("Movie","Imge url is "+vo.MovieImage);
		if(vo.MovieImage!=null){
			imageLoader.displayImage(vo.MovieImage,  holder.movieicon, options, null);
			
		}
		holder.title.setText(""+vo.MovieTitle+" "+"("+vo.moviecer+")");
		holder.genre.setText("Genre : "+vo.Genre);
		holder.length.setText("Duration : ");
		holder.language.setText("Language : "+vo.LanguageDesc);
		holder.actors.setText("Cast & Crew : "+vo.Cast);
	
		holder.movieicon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + MovieParams.getYouTubeVideoId(vo.videourl)));
				context.startActivity(intent);
				
			}
		});
		
		return convertView;
	}
	
	private class Holder {
		private TextView title, genre, language,length,actors;
		private ImageView movieicon;
	}
	
	

}