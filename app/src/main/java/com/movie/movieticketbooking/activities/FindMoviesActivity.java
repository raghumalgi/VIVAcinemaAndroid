package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchAllTheatresTask;
import com.movie.movieticketbooking.business.FetchMovieBytheatresTask;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchMovieTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;

public class FindMoviesActivity extends Activity {
	private NetworkTask<Void, Void, List<MovieItems>> fetchTask;
	private NetworkTask<Void, Void, List<TheatreList>> fetchTheatreTask;
	List<MovieItems> movieList ;
	CinemaAdress adapter;
	private ListView view;
	Spinner filterMovie;
	ProgressDialog dialog;
	public ArrayList<TheatreList> theatreList = new ArrayList<TheatreList>();
	private NetworkTask<Void, Void, List<MovieItems>> fetchMoviesTask;
	private MyAdapter myAdapter;
	protected String tid;
	private LinearLayout progressLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_movie);
		 view = (ListView) findViewById(R.id.list);
		 filterMovie = (Spinner) findViewById(R.id.filter_movie);
		 progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
			
		 TextView emptyText = (TextView)findViewById(R.id.empty);
		 final TextView filterTv = (TextView)findViewById(R.id.filter_movie_text);
		
		 view.setEmptyView(emptyText);
		movieList= new ArrayList<MovieItems>();
		adapter = new CinemaAdress(this, movieList);
		myAdapter = new MyAdapter(this, 0, theatreList);
		
		view.setAdapter(adapter);
		dialog = new ProgressDialog(this);
		dialog.setMessage("Loading ...");
		fetchMovieList(MovieParams.MOVIE_LIST_API);
		fetchAllTheatre(MovieParams.GET_ALL_THEATRES);
		onGridItemClick();
		filterMovie.setAdapter(myAdapter);
		 filterTv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					new AlertDialog.Builder(FindMoviesActivity.this)
					  .setTitle("Filter movies by cinema")
					  .setAdapter(myAdapter, new DialogInterface.OnClickListener() {

					    @Override
					    public void onClick(DialogInterface dialog, int position) {
					    	 tid  = theatreList.get(position).theatreId;
					    	 filterTv.setText( theatreList.get(position).theatreName);
					    	 fetchMovieByTheatre(MovieParams.GET_MOVIES_BY_SCREENS_API);
					     

					      dialog.dismiss();
					    }
					  }).create().show();
					
				}
			});
		
		//myAdapter.setDropDownViewResource(R.layout.theatres);
		
		/*filterMovie.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	if(!theatreList.get(position).isHint){
		    	 tid  = theatreList.get(position).theatreId;
		    	 fetchMovieByTheatre(MovieParams.GET_MOVIES_BY_SCREENS_API);
		    	}
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }

		});*/
	}
	
	
	private void loadTheatreObject() {
		TheatreList theate = new TheatreList();
		theate.theatreId = "";
		theate.theatreName = "Filter movies By cinema";
		
	}

	public void backClicked(View v){
		finish();
	}

	public class MyAdapter extends ArrayAdapter<TheatreList> {
		public MyAdapter(Context ctx, int txtViewResourceId, List<TheatreList>objects) {
			super(ctx, txtViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}
		
		  @Override
	        public int getCount() {
	            return super.getCount(); 
	        }

		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.custom_spiiner, parent,
					false);
			TextView main_text = (TextView) mySpinner
					.findViewById(R.id.txt_details);
			main_text.setText(theatreList.get(position).theatreName);
			return mySpinner;
		}
	}
	
	public void onGridItemClick() {
		
		  view.setOnItemClickListener(new OnItemClickListener() {
		         public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		        	 final MovieItems item = adapter.getItem(position);
						SampleModel.getInstance().setCurrentMovie(null);
						SampleModel.getInstance().setCurrentMovie(item);
						Intent intent = new Intent(FindMoviesActivity.this,MovieDetailActivity.class);
						startActivity(intent);
		         }
		      });
		
		
	}
		
	
	private void fetchMovieList(String rssFeed) {
		progressLayout.setVisibility(View.VISIBLE);
		//dialog.show();
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		
		fetchTask = createTask(rssFeed,null);
		
		
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<MovieItems>>() {
			@Override
			public void onComplete(List<MovieItems> result) {
				progressLayout.setVisibility(View.GONE);
				try {
					adapter.addAll(result);
					adapter.notifyDataSetChanged();
					if(dialog.isShowing()){
						dialog.dismiss();
					}
				} catch (Exception e) {
					// TODO Auto-genefrated catch block
					e.printStackTrace();
				}
				
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception)  {
				progressLayout.setVisibility(View.GONE);
				if(dialog.isShowing()){
					dialog.dismiss();
				}
				exception.printStackTrace();
				Toast.makeText(FindMoviesActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
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

	private void fetchMovieByTheatre(String rssFeed) {
		/*if(!dialog.isShowing()){
			dialog.show();
		}*/
		progressLayout.setVisibility(View.VISIBLE);
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		String tId  = tid;
		
		
		fetchTask = createMoviesTask(rssFeed,tId);
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<MovieItems>>() {
			@Override
			public void onComplete(List<MovieItems> result) {
				progressLayout.setVisibility(View.GONE);
				if(dialog.isShowing()){
					dialog.dismiss();
				}
				adapter.clear();
				
				adapter.addAll(result);
				adapter.notifyDataSetChanged();
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				progressLayout.setVisibility(View.GONE);
				if(dialog.isShowing()){
					dialog.dismiss();
				}
				try {
					exception.printStackTrace();
					Toast.makeText(FindMoviesActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		fetchTask.execute();
	}
	
	
	
	
	private NetworkTask<Void, Void, List<MovieItems>> createMoviesTask(String url,String tId) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchMovieBytheatresTask(url,tId);
	}
	
	private void fetchAllTheatre(String rssFeed) {
		
		if (fetchTheatreTask != null && !fetchTheatreTask.isComplete()) {
			fetchTheatreTask.abort();
		}
		
		fetchTheatreTask = createTheatesTask(rssFeed);
		fetchTheatreTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<TheatreList>>() {
			@Override
			public void onComplete(List<TheatreList> result) {
				progressLayout.setVisibility(View.GONE);
				/*TheatreList theatreList1 = new TheatreList();
				theatreList1.theatreName = "Filter movies by cinema";
				theatreList1.isHint = true;
				result.add(theatreList1);*/
				myAdapter.addAll(result);
				myAdapter.notifyDataSetChanged();
				//filterMovie.setSelection(result.size() - 1);
			}
		});
		fetchTheatreTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				progressLayout.setVisibility(View.GONE);
				try {
					
					exception.printStackTrace();
					Toast.makeText(FindMoviesActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		fetchTheatreTask.execute();
	}
	
	
	private NetworkTask<Void, Void, List<TheatreList>> createTheatesTask(String url) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchAllTheatresTask(url);
	}
	
	class CinemaAdress extends ArrayAdapter<MovieItems> {
		private Context context;

		public CinemaAdress(Context context, List<MovieItems> objects) {
			super(context, 0, objects);
			this.context = context;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.movies,
						null);
				holder = new Holder();
				holder.title = (TextView) convertView
						.findViewById(R.id.txt_details);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			
			if (position % 2 == 1) {
				convertView.setBackgroundColor(Color.parseColor("#F1F2F2"));  
			} else {
				convertView.setBackgroundColor(Color.parseColor("#E6E7E8"));  
			}
			final MovieItems screen = getItem(position);
			holder.title.setText(screen.MovieTitle);
			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(FindMoviesActivity.this,MovieDetailActivity.class);
						SampleModel.getInstance().setCurrentMovie(screen);
						SampleModel.getInstance().clearAll();
						startActivity(i);
					finish();
					SampleModel.getInstance().clearNavigationActivities();
				}
			});

			return convertView;
		}

		private class Holder {
			private TextView title;
		}

	}
	
	
}
