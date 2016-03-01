package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchMovieBytheatresTask;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchMovieTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.listAdapters.MovieItemAdapter;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MovieListFragment extends ListFragment {
	private NetworkTask<Void, Void, List<MovieItems>> fetchTask;
	List<MovieItems> movieList ;
	MovieItemAdapter adapter;
	ProgressDialog pd;
	LinearLayout progressLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Movie","Current Date "+MovieParams.getCurrentDate());
		movieList= new ArrayList<MovieItems>();
		adapter = new MovieItemAdapter(getActivity(), movieList);
		pd  = new ProgressDialog(getActivity());
		pd.setMessage("Loading ...");
		setListAdapter(adapter);
		fetchMovieList(MovieParams.MOVIE_LIST_API);
		/*adapter = new MovieItemAdapter(getActivity(), movieList);
		pd  = new ProgressDialog(getActivity());
		pd.setMessage("Loading ...");
		setListAdapter(adapter);
		movieList= new ArrayList<MovieItems>();
		fetchMovieList(MovieParams.MOVIE_LIST_API);*/
		
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("InlinedApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.movie_list_frag, container, false);
		progressLayout = (LinearLayout) root.findViewById(R.id.progress_ll);
		progressLayout.setVisibility(View.VISIBLE);
		if(movieList!=null&&movieList.size()>0){
			progressLayout.setVisibility(View.GONE);
		}
		return root;
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		final MovieItems item = adapter.getItem(position);
		SampleModel.getInstance().setCurrentMovie(null);
		SampleModel.getInstance().setCurrentMovie(item);
		String tid = SampleModel.getInstance().getTheatreId();
		String tName = SampleModel.getInstance().getTheatreForHome();
		if(tName==null){
			Intent i  = new Intent(getActivity(),TheatreListActivity.class);
			i.putExtra("fetchMovie", true);
			startActivity(i);
		}else {
			Intent i  = new Intent(getActivity(),ShowTimesActivity.class);
			startActivity(i);
		}
	}
		
	
	private void fetchMovieList(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		
		//fetchTask = createTask(rssFeed,"29");
		String id = SampleModel.getInstance().getTheatreId();
		if(id!=null){
		fetchTask = createTask(rssFeed,id);
		}else{
			fetchTask = createTask(rssFeed,null);
		}
		
		
		
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<MovieItems>>() {
			@Override
			public void onComplete(List<MovieItems> result) {
				progressLayout.setVisibility(View.GONE);
				//Log.d("Movie","Result "+result.get(0).Cast);
				adapter.addAll(result);
				adapter.notifyDataSetChanged();
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception)  {
				progressLayout.setVisibility(View.GONE);
				exception.printStackTrace();
				Toast.makeText(getActivity(), MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchTask.execute();
	}
	
	private NetworkTask<Void, Void, List<MovieItems>> createTask(String url,String tId) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchMovieTask(url,tId);
	}

	public void update() {
		adapter.clear();
		progressLayout.setVisibility(View.VISIBLE);		
		fetchMovieByTheatre(MovieParams.GET_MOVIES_BY_SCREENS_API);
		
	}
	private void fetchMovieByTheatre(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		String tId  = SampleModel.getInstance().getTheatreId();
		
		
		fetchTask = createMoviesTask(rssFeed,tId);
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<MovieItems>>() {
			@Override
			public void onComplete(List<MovieItems> result) {
				progressLayout.setVisibility(View.GONE);
				adapter.addAll(result);
				adapter.notifyDataSetChanged();
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				try {
					progressLayout.setVisibility(View.GONE);
					exception.printStackTrace();
					Toast.makeText(getActivity(), MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		fetchTask.execute();
	}
	
	private NetworkTask<Void, Void, List<MovieItems>> createMoviesTask(String url,String tId) {
		progressLayout.setVisibility(View.VISIBLE);
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchMovieBytheatresTask(url,tId);
	}
}
