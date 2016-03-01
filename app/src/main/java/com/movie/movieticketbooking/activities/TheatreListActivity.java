package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchTheatresTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.listAdapters.SimpleSectionedListAdapter;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.models.MallDetails;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;
import com.nostra13.universalimageloader.core.ImageLoader;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TheatreListActivity extends ListActivity {
	private NetworkTask<Void, Void, List<MallDetails>> fetchTask;
	List<MallDetails> movieList;
	SimpleSectionedListAdapter mAdapter;
	CinemaAdress addressAdapter;
	private LinearLayout progressLayout;
	private boolean fromButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theate_list);
		View  view1 = findViewById(R.id.home);
		View  view2 = findViewById(R.id.account);
		View  view3 = findViewById(R.id.history);
		View  view4 = findViewById(R.id.aboutus);
		view1.setOnClickListener(new BottomTabListener());
		view2.setOnClickListener(new BottomTabListener());
		view3.setOnClickListener(new BottomTabListener());
		view4.setOnClickListener(new BottomTabListener());
		Bundle b = getIntent().getExtras();
		if(b!=null){
			fromButton = b.getBoolean("fromButton");
		}
		findViewById(R.id.location).setVisibility(View.GONE);
		findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		progressLayout.setVisibility(View.VISIBLE);
		fetchTheatreList(MovieParams.THEATRES_API);
		movieList = new ArrayList<MallDetails>();
		addressAdapter = new CinemaAdress(this, movieList);
		setListAdapter(addressAdapter);

	}

	private void fetchTheatreList(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		/*if(fromButton){
		fetchTask = createTask(rssFeed,null);
		}else{
			MovieItems items = SampleModel.getInstance().getCurrentMovie();
			fetchTask = createTask(MovieParams.GET_THEATRES_BY_MOVIE,items.MovieID);
		}*/
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<MallDetails>>() {
					@Override
					public void onComplete(List<MallDetails> result) {
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
						Toast.makeText(TheatreListActivity.this,
								MovieParams.MESSAGE_TXT,
								Toast.LENGTH_LONG).show();
					}
				});
		fetchTask.execute();
	}

	/*private NetworkTask<Void, Void, List<MallDetails>> createTask(String url, String movieID) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchTheatresTask(url,movieID);
	}*/

	class CinemaAdress extends ArrayAdapter<MallDetails> {
		private Context context;

		public CinemaAdress(Context context, List<MallDetails> objects) {
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
			final MallDetails screen = getItem(position);
			holder.title.setText(screen.theatreName);
			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(fromButton){
						 setResult(RESULT_OK, (new Intent()).putExtra("tid",  screen.theatreId).
								 putExtra("tname", screen.theatreName));
						 SampleModel.getInstance().setTheatreId(screen.theatreId);
						 SampleModel.getInstance().setCurrentMall(screen);
						 SampleModel.getInstance().setCurrentTheatreForHome(screen.theatreName);
				          finish();
					}else if(!fromButton){
						Intent i = new Intent(TheatreListActivity.this,ShowTimesActivity.class);
						 SampleModel.getInstance().setTheatreId(screen.theatreId);
						 SampleModel.getInstance().setCurrentMall(screen);
						// SampleModel.getInstance().setCurrentTheatreForHome(screen.theatreName);
						i.putExtra("tid", screen.theatreId);
						startActivity(i);
						finish();
					}
					else{
						setResult(RESULT_CANCELED);
						finish();
					}
				}
			});

			return convertView;
		}

		private class Holder {
			private TextView title;
		}

	}
}
