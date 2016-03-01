package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchBannerTask;
import com.movie.movieticketbooking.business.FetchMovieBytheatresTask;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchMovieTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.listAdapters.MovieGridAdapter;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.parsers.Banner;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;

public class MovieGridActivity extends BaseActivity {
	private NetworkTask<Void, Void, List<MovieItems>> fetchTask;
	private NetworkTask<Void, Void, Banner> fetchBannerTask;
	List<MovieItems> movieList ;
	MovieGridAdapter adapter;
	ProgressDialog pd;
	LinearLayout progressLayout;
	private TextView imdb;
	private TextView title;
	private GridView view;
	protected Banner banner;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.movie_grid);
		getLayoutInflater().inflate(R.layout.movie_grid, frameLayout);
		  /**
		   * Setting title and itemChecked 
		   */
		  mDrawerList.setItemChecked(position, true);
		  setTitle(listArray[position]);
		 view = (GridView) findViewById(R.id.grid);
		Log.d("Movie","Current Date "+MovieParams.getCurrentDate());
		movieList= new ArrayList<MovieItems>();
		adapter = new MovieGridAdapter(this, movieList);
		pd  = new ProgressDialog(this);
		pd.setMessage("Loading ...");
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		 imdb = (TextView) findViewById(R.id.imdb);
		 title = (TextView) findViewById(R.id.txtTitle);
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		progressLayout.setVisibility(View.VISIBLE);
		view.setAdapter(adapter);
		//setListAdapter(adapter);
		fetchMovieList(MovieParams.MOVIE_LIST_API);
		
		
		onGridItemClick();
		
	}
	
	
	
	
	public void onGridItemClick() {
		
		  view.setOnItemClickListener(new OnItemClickListener() {
		         public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		        	 final MovieItems item = adapter.getItem(position);
						SampleModel.getInstance().setCurrentMovie(null);
						SampleModel.getInstance().setCurrentMovie(item);
						Intent intent = new Intent(MovieGridActivity.this,MovieDetailActivity.class);
						startActivity(intent);
		         }
		      });
		
		
	}
		
	
	private void fetchMovieList(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		
		//String id = SampleModel.getInstance().getTheatreId();
		/*if(id!=null){
		fetchTask = createTask(rssFeed,id);
		}else{
			fetchTask = createTask(rssFeed,null);
		}*/
		
		fetchTask = createTask(rssFeed,null);
		
		
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<MovieItems>>() {
			@Override
			public void onComplete(final List<MovieItems> result) {
				try {
					//progressLayout.setVisibility(View.GONE);
					fetchBanner(MovieParams.BANNER_API);
					//Log.d("Movie","Result "+result.get(0).Cast);
					adapter.addAll(result);
					adapter.notifyDataSetChanged();
					//ImageView image = (ImageView) findViewById(R.id.bannerIv);
				//	adapter.getLoader().displayImage("http://37.131.68.76/"+result.get(0).movieBannerImage,  image, adapter.getOptions(), null);
					title.setText(result.get(0).MovieTitle);
					imdb.setText(result.get(0).imdbRating);
					findViewById(R.id.bannerIv).setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if(banner!=null){
							SampleModel.getInstance().setCurrentMovie(null);
							String movieId = banner.bannerTitle;
							for (MovieItems movie : result) {
								if(movie.MovieTitle.equals(movieId)){
									 SampleModel.getInstance().setCurrentMovie(movie);
									 break;
								}
							}
							Intent intent = new Intent(MovieGridActivity.this,MovieDetailActivity.class);
							startActivity(intent);
							}
							
						}
					});
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
				exception.printStackTrace();
				Toast.makeText(MovieGridActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchTask.execute();
	}
	
	public void backClicked(View v) {
		String userId  = SampleModel.getInstance().getCurrentUserId();
		if(userId==null){
			Toast.makeText(MovieGridActivity.this, "Please login to see your ticket details ", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(MovieGridActivity.this,MyTickets.class);
		startActivity(intent);
	}

	public void moreClicked(View v) {
		//showActionSheet();
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
			
			//	progressLayout.setVisibility(View.GONE);
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
					Toast.makeText(MovieGridActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		fetchTask.execute();
	}
	
	private void fetchBanner(String rssFeed) {
		if (fetchBannerTask != null && !fetchBannerTask.isComplete()) {
			fetchBannerTask.abort();
		}
		Log.d("BOOK",",Banner image string ");
		
		fetchBannerTask = createBannerTask(rssFeed);
		fetchBannerTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<Banner>() {
			@Override
			public void onComplete(Banner result) {
				banner = result;
				progressLayout.setVisibility(View.GONE);
				Log.d("BOOK",",Banner image "+result.bannerImgPath);
					ImageView image = (ImageView) findViewById(R.id.bannerIv);
					adapter.getLoader().displayImage("http://37.131.68.76/"+result.bannerImgPath,  image, adapter.getOptions(), null);
					SampleModel.getInstance().setBanner("http://37.131.68.76/"+result.bannerImgPath);
					title.setText(result.bannerTitle);
					imdb.setText(result.imdb);
			}
		});
		fetchBannerTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				try {
					progressLayout.setVisibility(View.GONE);
					exception.printStackTrace();
					Toast.makeText(MovieGridActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		fetchBannerTask.execute();
	}
	
	private NetworkTask<Void, Void, List<MovieItems>> createMoviesTask(String url,String tId) {
		progressLayout.setVisibility(View.VISIBLE);
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchMovieBytheatresTask(url,tId);
	}
	private NetworkTask<Void, Void, Banner> createBannerTask(String url) {
		progressLayout.setVisibility(View.VISIBLE);
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchBannerTask(url);
	}
}
