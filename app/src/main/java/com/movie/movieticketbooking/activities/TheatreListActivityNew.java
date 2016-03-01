package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchTheatresTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.listAdapters.SimpleSectionedListAdapter;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.models.ScreenDetail;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TheatreListActivityNew extends BaseActivity {
	private NetworkTask<Void, Void, List<ScreenDetail>> fetchTask;
	List<ScreenDetail> movieList;
	SimpleSectionedListAdapter mAdapter;
	CinemaAdress addressAdapter;
	private LinearLayout progressLayout;
	private boolean fromButton;
	private ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.theatre_list);
		getLayoutInflater().inflate(R.layout.theatre_list, frameLayout);
		SampleModel.getInstance().activities_nav.add(TheatreListActivityNew.this);
		//activity = this;
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		progressLayout.setVisibility(View.VISIBLE);
		fetchTheatreList(MovieParams.THEATRES_API);
		movieList = new ArrayList<ScreenDetail>();
		addressAdapter = new CinemaAdress(this, movieList);
		 lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(addressAdapter);
		MovieItems item = SampleModel.getInstance().getCurrentMovie();
		TextView tv = (TextView) findViewById(R.id.txtTitle);
		tv.setText(item.MovieTitle);
		((TextView) findViewById(R.id.imdb)).setText(item.imdbRating);
		
		if(item.movieBannerImage!=null&&item.movieBannerImage.length()>0){
		imageLoader.displayImage("http://37.131.68.76/"+item.movieBannerImage,   ((ImageView) findViewById(R.id.bannerIv)), options, null);
		}else{
			//imageLoader.displayImage("http://37.131.68.76/"+SampleModel.getInstance().getBanner(),   ((ImageView) findViewById(R.id.bannerIv)), options, null);
			MovieYoutubeBaseActivity.displayImage(SampleModel.getInstance().getBanner(),   ((ImageView) findViewById(R.id.bannerIv)), this);
		}

	}
	
	public void backClicked(View v){
		finish();
	}
	

	public void moreClicked(View v){
		//showActionSheet();
	}

	private void fetchTheatreList(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
			MovieItems items = SampleModel.getInstance().getCurrentMovie();
			fetchTask = createTask(MovieParams.THEATRES_API,items.MovieID);
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<ScreenDetail>>() {
					@Override
					public void onComplete(List<ScreenDetail> result) {
						progressLayout.setVisibility(View.GONE);
						addressAdapter.addAll(result);
						addressAdapter.notifyDataSetChanged();

					}
				});
		fetchTask
				.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
					@Override
					public void onException(Exception exception) {
						progressLayout.setVisibility(View.GONE);
						exception.printStackTrace();
						Toast.makeText(TheatreListActivityNew.this,
								MovieParams.MESSAGE_TXT,
								Toast.LENGTH_LONG).show();
					}
				});
		fetchTask.execute();
	}

	private NetworkTask<Void, Void, List<ScreenDetail>> createTask(String url, String movieID) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchTheatresTask(url,movieID);
	}

	class CinemaAdress extends ArrayAdapter<ScreenDetail> {
		private Context context;

		public CinemaAdress(Context context, List<ScreenDetail> objects) {
			super(context, 0, objects);
			this.context = context;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.theatres,
						null);
				holder = new Holder();
				holder.title = (TextView) convertView
						.findViewById(R.id.txt_details);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			final ScreenDetail screen = getItem(position);
			holder.title.setText(screen.screenName);
			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(TheatreListActivityNew.this,ShowTimesNew.class);
						 SampleModel.getInstance().setTheatreId(screen.screenId);
						 Log.d("Movie","Th id.. "+screen.theatreIdCoupons);
						 SampleModel.getInstance().setTheatreCouponId(screen.theatreIdCoupons);
						 SampleModel.getInstance().setScreenDetail(screen);
						// SampleModel.getInstance().setCurrentTheatreForHome(screen.theatreName);
						i.putExtra("tid", screen.screenId);
						startActivity(i);
						SampleModel.getInstance().activities.add(TheatreListActivityNew.this);
						//finish();
					
				}
			});

			return convertView;
		}

		private class Holder {
			private TextView title;
		}

	}
}
