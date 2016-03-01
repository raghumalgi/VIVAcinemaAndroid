package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchShowTimingTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.listAdapters.SimpleSectionedListAdapter;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.models.Show;
import com.movie.movieticketbooking.models.ShowTimemodel;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;
import com.nostra13.universalimageloader.core.ImageLoader;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MovieTimingsActivity extends ListActivity {
	private NetworkTask<Void, Void, List<ShowTimemodel>> fetchTask;
	List<ShowTimemodel> movieList = new ArrayList<ShowTimemodel>();
	SimpleSectionedListAdapter mAdapter;
	CinemaAdress addressAdapter;
	private LinearLayout progressLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_time_list);
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		progressLayout.setVisibility(View.VISIBLE);
		fetchTimings(MovieParams.SHOWTIMES_MOVIE_API);
		addressAdapter = new CinemaAdress(this, movieList);
		setListAdapter(addressAdapter);
		getListView().setDivider(null);
	}

	
	TextView shows;

	

	private void fetchTimings(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}

		fetchTask = createTask(rssFeed);
		fetchTask
				.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<ShowTimemodel>>() {
					@Override
					public void onComplete(List<ShowTimemodel> result) {
						progressLayout.setVisibility(View.GONE);
						addressAdapter.addAll(result);
						addressAdapter.notifyDataSetChanged();
						boolean isShowTimeNotAvailable = true;
						for (ShowTimemodel showss : result) {
							if (showss.shows.size() > 0) {
								isShowTimeNotAvailable = false;
							}
						}
						if (isShowTimeNotAvailable) {
							shows.setVisibility(View.VISIBLE);
						}

					}
				});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
					@Override
					public void onException(Exception exception) {
						try {
							progressLayout.setVisibility(View.GONE);
							exception.printStackTrace();
							Toast.makeText(MovieTimingsActivity.this,
									MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG)
									.show();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		fetchTask.execute();
	}

	private NetworkTask<Void, Void, List<ShowTimemodel>> createTask(String url) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		MovieItems item = SampleModel.getInstance().getCurrentMovie();
		return new FetchShowTimingTask(url, SampleModel.getInstance()
				.getTheatreId(), item.MovieID);
	}

	class CinemaAdress extends ArrayAdapter<ShowTimemodel> {
		protected ImageLoader imageLoader = ImageLoader.getInstance();
		private Context context;

		public CinemaAdress(Context context, List<ShowTimemodel> objects) {
			super(context, 0, objects);
			this.context = context;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(),
						R.layout.showtime_item, null);
				holder = new Holder();
				holder.title = (TextView) convertView
						.findViewById(R.id.txtTitle);
				holder.tableLayout = (TableLayout) convertView
						.findViewById(R.id.lytTimeView);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			ShowTimemodel screen = getItem(position);
			holder.title.setText(screen.screenName);
			List<Show> shows = screen.shows;
			if (shows.size() == 0) {
				holder.title.setVisibility(View.GONE);
			}

			return convertView;
		}

		private class Holder {
			private TextView title;
			private TableLayout tableLayout;
		}

	}

}
