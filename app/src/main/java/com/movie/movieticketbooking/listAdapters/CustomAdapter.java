package com.movie.movieticketbooking.listAdapters;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.activities.FindMoviesActivity;
import com.movie.movieticketbooking.activities.MovieDetailActivity;
import com.movie.movieticketbooking.activities.TicketDetailActivity;
import com.movie.movieticketbooking.models.BookingItems;
import com.movie.movieticketbooking.models.SampleModel;

public class CustomAdapter extends BaseAdapter {

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;

	private ArrayList<String> mData = new ArrayList<String>();
	private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

	private LayoutInflater mInflater;
	private Context context;

	public CustomAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	public void addItem(final String item) {
		mData.add(item);
		notifyDataSetChanged();
	}

	public void addSectionHeaderItem(final String item) {
		mData.add(item);
		sectionHeader.add(mData.size() - 1);
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public String getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int rowType = getItemViewType(position);

		if (convertView == null) {
			holder = new ViewHolder();
			switch (rowType) {
			case TYPE_ITEM:
				convertView = mInflater.inflate(R.layout.movies, null);
				convertView.setBackgroundColor(Color.parseColor("#E6E7E8"));  
				holder.textView = (TextView) convertView.findViewById(R.id.txt_details);
				break;
			case TYPE_SEPARATOR:
				convertView = mInflater.inflate(R.layout.header, null);
				convertView.setBackgroundColor(context.getResources().getColor(R.color.purple)); 
				holder.textView = (TextView) convertView.findViewById(R.id.txt_details);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(mData.get(position));

		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int rowType = getItemViewType(position);
				switch (rowType) {
				case TYPE_ITEM:
					String movie =mData.get(position);
					List<BookingItems>	bList =SampleModel.getInstance().getbList();
					for(BookingItems b : bList){
						if(b.movieTitle.equals(movie)){
							b.position = position;
							SampleModel.getInstance().setCurrentBookingItem(b);
							break;
						}
					}
					Intent i = new Intent(context,TicketDetailActivity.class);
					context.startActivity(i);
					break;
				case TYPE_SEPARATOR:
					
					break;
				}
			
				
			}
		});
		
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}

}