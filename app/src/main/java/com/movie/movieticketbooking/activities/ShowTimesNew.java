package com.movie.movieticketbooking.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.FetchShowTimingTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.listAdapters.SimpleSectionedListAdapter;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.models.ScreenDetail;
import com.movie.movieticketbooking.models.ShowTimemodel;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;

public class ShowTimesNew extends BaseActivity {
	private NetworkTask<Void, Void, List<ShowTimemodel>> fetchTask;
	SimpleSectionedListAdapter mAdapter;
	CinemaAdress addressAdapter;
	private LinearLayout progressLayout;
	private static final String AVAILABLE = "Available";
	private static final String BOOKING_CLOSED = "Booking Closed";
	
	private GridView gv;
	List<ShowTimemodel> movieList = new ArrayList<ShowTimemodel>();
	HashMap<Integer,Boolean> clickable = new HashMap<Integer,Boolean>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.theatre_list_timings);
		getLayoutInflater().inflate(R.layout.theatre_list_timings, frameLayout);
		SampleModel.getInstance().activities_nav.add(ShowTimesNew.this);
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		gv = (GridView) findViewById(R.id.grid);
		//activity = this;
		progressLayout.setVisibility(View.VISIBLE);
		fetchTimings(MovieParams.SHOWTIMES_API);
		addressAdapter = new CinemaAdress(this, movieList);
		gv.setAdapter(addressAdapter);
		MovieItems item = SampleModel.getInstance().getCurrentMovie();
		TextView tv = (TextView) findViewById(R.id.txtTitle);
		tv.setText(item.MovieTitle);
		TextView name = (TextView) findViewById(R.id.theatreName);
		name.setText( SampleModel.getInstance().getScreenDetail().screenName);
		((TextView) findViewById(R.id.imdb)).setText(item.imdbRating);
		
		if(item.movieBannerImage!=null&&item.movieBannerImage.length()>0){
			imageLoader.displayImage("http://37.131.68.76/"+item.movieBannerImage,   ((ImageView) findViewById(R.id.bannerIv)), options, null);
			}else{
				//imageLoader.displayImage("http://37.131.68.76/"+SampleModel.getInstance().getBanner(),   ((ImageView) findViewById(R.id.bannerIv)), options, null);
				MovieYoutubeBaseActivity.displayImage(SampleModel.getInstance().getBanner(),   ((ImageView) findViewById(R.id.bannerIv)), this);
			}
		onGridItemClick();

	}
	
	public void backClicked(View v){
		finish();
	}
	

	public void moreClicked(View v){
		//showActionSheet();
	}


	public void onGridItemClick() {
		
		  gv.setOnItemClickListener(new OnItemClickListener() {
		         public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		        	 final ShowTimemodel item = addressAdapter.getItem(position);{
		        	 boolean click = clickable.get(position);
		        	 if(click){
						SampleModel.getInstance().setShowId(item.showId);
						SampleModel.getInstance().setShowTime(item.showTime);
						SampleModel.getInstance().setPrice(item.price);
						Intent i = new Intent(ShowTimesNew.this,
								SeatLayoutActivityNew.class);
						startActivity(i);
						SampleModel.getInstance().activities.add(ShowTimesNew.this);
		        	 }
		        	 }
		         }
		      });
		
		
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
						if(result==null){
							Toast.makeText(ShowTimesNew.this, "No shows found", Toast.LENGTH_SHORT).show();
							finish();
							return;
						}
						addressAdapter.addAll(result);
						addressAdapter.notifyDataSetChanged();
						boolean isShowTimeNotAvailable = true;
						
						for (ShowTimemodel showss : result) {
							if (showss.shows.size() > 0) {
								isShowTimeNotAvailable = false;
							}
						}
						
					}
				});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
					@Override
					public void onException(Exception exception) {
						try {
							progressLayout.setVisibility(View.GONE);
							exception.printStackTrace();
							Toast.makeText(ShowTimesNew.this,
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
		ScreenDetail detail = SampleModel.getInstance().getScreenDetail();
		return new FetchShowTimingTask(url,detail.screenId, item.MovieID);
	}

	class CinemaAdress extends ArrayAdapter<ShowTimemodel> {
		private Context context;

		public CinemaAdress(Context context, List<ShowTimemodel> objects) {
			super(context, 0, objects);
			this.context = context;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.show_time,
						null);
				holder = new Holder();
				holder.title = (TextView) convertView
						.findViewById(R.id.txt_details);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			final ShowTimemodel screen = getItem(position);
			String s = screen.showTime;
			s= s.substring(s.indexOf("<b>")+3, s.indexOf("</b>"));
			holder.title.setText(s);
			
			SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");

		    Date now = new Date();

		    String strTime = sdfTime.format(now);
		    
		    System.out.println("Class Details: " + screen.classDetails.substring(screen.classDetails.indexOf("<b>")+3,screen.classDetails.indexOf("</b>")));
		    
		    
		    String status = screen.classDetails.substring(screen.classDetails.indexOf("<b>")+3,screen.classDetails.indexOf("</b>"));
		    
		    if(status.equals(AVAILABLE)){
		    	clickable.put(position, true);
		    }
		    else if(status.equals(BOOKING_CLOSED)){
		    	convertView.setBackgroundColor(context.getResources().getColor(R.color.grey));
				 System.out.println("position true" + " = " + position);
				clickable.put(position, false);
		    }

		    boolean midNight  = false;
			SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		       SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
		       Date date = null;
		       System.out.println("Time is "+s);
		       if(s.contains("12.00 Midnight ")){
		    	   midNight = true;
		    	   s = "12:00 AM";
		       }
		       else if(s.contains("12.00 Noon ")){
		    	   s = "12:00 PM";
		       }
		       
			try {
				date = parseFormat.parse(s);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			 Date date1= null;
			try {
				date1 = parseFormat.parse(strTime);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

		    
			long diff = date.getTime() - date1.getTime();//as given
			long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
			
		/*	if(minutes>=30){
				clickable.put(position, true);
				 System.out.println("position false" + " = " + position);
			}else{
				convertView.setBackgroundColor(context.getResources().getColor(R.color.grey));
				 System.out.println("position true" + " = " + position);
				clickable.put(position, false);
			}
		*/	 System.out.println("Time in minutes" + " = " + minutes);
		       System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
		      String trying =  displayFormat.format(date);
			try {
				String[] time = trying.split(":");
				int hr1=Integer.parseInt(time[0]);
				Drawable img = getContext().getResources().getDrawable( R.drawable.halfsun );
				if(hr1<=12)
				{
					 if(midNight){
						 img = getContext().getResources().getDrawable( R.drawable.moon );
						 holder.title.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
						 midNight = false;
				       }
				else{
				  holder.title.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
				}
				}else if(hr1>12&& hr1<=18)
				{
					img = getContext().getResources().getDrawable( R.drawable.sun );
					 holder.title.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
				 
				}else if(hr1>18&& hr1<24)
				{
					
					
					img = getContext().getResources().getDrawable( R.drawable.moon );
					 holder.title.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			return convertView;
		}

		private class Holder {
			private TextView title;
		}

	}
}
