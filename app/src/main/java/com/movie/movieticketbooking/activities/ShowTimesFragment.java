package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.MovieApp;
import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchMovieTask;
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
public class ShowTimesFragment extends ListFragment {
	private NetworkTask<Void, Void, List<ShowTimemodel>> fetchTask;
	List<ShowTimemodel> movieList = new ArrayList<ShowTimemodel>();
	SimpleSectionedListAdapter mAdapter;
	CinemaAdress addressAdapter;
	private LinearLayout progressLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fetchTimings(MovieParams.SHOWTIMES_MOVIE_API);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("InlinedApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		addressAdapter = new CinemaAdress(getActivity(), movieList);
		setListAdapter(addressAdapter);
		getListView().setDivider(null);
	}

	TextView shows;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.show_time_list,
				container, false);
		progressLayout = (LinearLayout) root.findViewById(R.id.progress_ll);
		progressLayout.setVisibility(View.VISIBLE);
		if(movieList.size()>0){
			progressLayout.setVisibility(View.GONE);
		}
		((TextView) root.findViewById(R.id.date))
				.setText(MovieParams.getDate());
		MovieItems item = SampleModel.getInstance().getCurrentMovie();
		((TextView) root.findViewById(R.id.title)).setText("" + item.MovieTitle
				+ " " + "(" + item.moviecer + ")");
		shows = ((TextView) root.findViewById(R.id.noShows));
		//getListView().setEmptyView(shows);
		return root;
	}

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
							Toast.makeText(getActivity(),
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

			TableRow ll = new TableRow(context);

			for (int i = 0; i < shows.size(); i++) {
				boolean addRow = false;
				Log.d("VIVA",
						"size Time " + i + " Shows size is " + shows.size());
				final Button tv = new Button(context);
				tv.setBackgroundResource(getResources().getColor(
						android.R.color.transparent));
				tv.setTextColor(getResources().getColor(android.R.color.black));
				tv.setPadding(20, 10, 20, 20);
				tv.setTypeface(Typeface.DEFAULT_BOLD);
				tv.setText(shows.get(i).showTime);
				tv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Show show = (Show) v.getTag();
						SampleModel.getInstance().setCurrentShowTime(show);
						((MovieApp) getActivity().getApplication()).activities
								.add(getActivity());
						Intent i = new Intent(getActivity(),
								SelectClassActivity.class);
						startActivity(i);
						Log.d("VIVA", "Show Time " + show.showTime);
					}
				});
				tv.setTag(shows.get(i));
				if (i == 3) {
					ll = new TableRow(context);
					addRow = true;
				} else if (i == 6) {
					ll = new TableRow(context);
					addRow = true;
				} else if (i == 9) {
					ll = new TableRow(context);
					addRow = true;
				}

				ll.addView(tv, new android.widget.TableRow.LayoutParams(-2, -2));
				if (i == 0 || addRow)
					holder.tableLayout.addView(ll);

			}

			return convertView;
		}

		private class Holder {
			private TextView title;
			private TableLayout tableLayout;
		}

	}

}
