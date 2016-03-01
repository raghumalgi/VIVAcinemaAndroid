package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchBookingHistoryTask;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.models.BookingItems;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.utils.MovieParams;


public class BookingHistory extends ListActivity {
	private BookingListAdapter adapter;
	private NetworkTask<Void, Void, List<BookingItems>> fetchTask;
	List<BookingItems> bookingItems = new ArrayList<BookingItems>();
	private LinearLayout progressLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_history);
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		adapter = new BookingListAdapter(this, bookingItems);
		setListAdapter(adapter);
		
		fetchMovieList(MovieParams.BOOKING_HISTORY_API);
	}
	private class BookingListAdapter extends ArrayAdapter<BookingItems> {
		private Context context;
		public BookingListAdapter(Context context, List<BookingItems> objects) {
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
			/*if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.booking_items, null);
				holder = new Holder();
				holder.title = (TextView)convertView.findViewById(R.id.txtTitle);
				holder.bookingId = (TextView)convertView.findViewById(R.id.booking_id);
				holder.venue = (TextView)convertView.findViewById(R.id.venue);
				holder.location = (TextView)convertView.findViewById(R.id.location);
				holder.date = (TextView)convertView.findViewById(R.id.date);
				holder.quantity = (TextView)convertView.findViewById(R.id.quantity);
				holder.seatInfo = (TextView)convertView.findViewById(R.id.seatinfo);
				holder.showtime = (TextView)convertView.findViewById(R.id.showtime);
				holder.amount = (TextView)convertView.findViewById(R.id.amount);
				convertView.setTag(holder);
			} else {
				holder = (Holder)convertView.getTag();
			}
			*/
			final BookingItems vo = getItem(position);
			
			
			if (vo.isSection) {
				convertView.setBackgroundColor(getResources().getColor(R.color.purple)); 
				holder.title.setText("Valid Tickets");
			}else{
				holder.title.setText(vo.movieTitle);
			}
			
			
			/*} else {
				convertView.setBackgroundColor(Color.parseColor("#E6E7E8"));  
			}*/
			/*holder.bookingId.setText("BOOKING ID : "+vo.bookingid);
			holder.venue.setText("Venue : "+vo.venue);
			holder.location.setText("Location : "+vo.location);
			holder.date.setText("Date & Time : "+vo.date);
			holder.quantity.setText("Quantity : "+vo.quantity);
			holder.seatInfo.setText("Seat Info : "+vo.seatInfo);
			holder.showtime.setText("ShowTime : "+vo.showTime);
			holder.amount.setText("Amount : "+vo.amount);*/
		
			
			return convertView;
		}
		
		private class Holder {
			private TextView title, bookingId, venue,location,quantity,showtime,date,seatInfo,amount;
		}
	}
	private void fetchMovieList(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		String userId  = SampleModel.getInstance().getCurrentUserId();
		if(userId !=null){
		progressLayout .setVisibility(View.VISIBLE);
		fetchTask = createTask(rssFeed,userId);
	
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<List<BookingItems>>() {
			@Override
			public void onComplete(List<BookingItems> result) {
				progressLayout.setVisibility(View.GONE);
				
				if(result!=null){
					List<BookingItems>	bList =new ArrayList<BookingItems>();
					for (int i = 0; i < result.size(); i++) {
						if(i==0){
							result.get(i).isSection = true;
						}
						bList.add(result.get(i));
						
					}
					adapter.addAll(bList);
				adapter.notifyDataSetChanged();
				}
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				progressLayout.setVisibility(View.GONE);
				exception.printStackTrace();
				Toast.makeText(BookingHistory.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchTask.execute();
		}
	}
	
	private NetworkTask<Void, Void, List<BookingItems>> createTask(String url,String tId) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchBookingHistoryTask(url,tId);
	}
	

}
