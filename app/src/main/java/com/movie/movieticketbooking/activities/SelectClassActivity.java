package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.MovieApp;
import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchClassCategory;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.models.Category;
import com.movie.movieticketbooking.models.CategoryDto;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;

public class SelectClassActivity extends ActionSheetBaseActivity {
	private NetworkTask<Void, Void, Category> fetchTask;

	List<CategoryDto> categories = new ArrayList<CategoryDto>();
	protected LinearLayout progressLayout;
	protected ListView lv;
	CategoryAdapter c;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_category);
		activity = this;
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);

		c = new CategoryAdapter(this, categories);
		SampleModel model = SampleModel.getInstance();
		MovieItems item = model.getCurrentMovie();
		((TextView) findViewById(R.id.title)).setText("" + item.MovieTitle
				+ " " + "(" + item.moviecer + ")");
		lv = ((ListView) findViewById(R.id.lv));
		lv.setAdapter(c);
		fetchMovieList(MovieParams.SELECT_CATEGORY_API);
		//imageLoader.displayImage("http://37.131.68.76/"+item.MovieImage,   ((ImageView) findViewById(R.id.bannerIv)), options, null);
		
	}
	
	public void backClicked(View v){
		finish();
	}
	

	public void moreClicked(View v){
		showActionSheet();
	}


	class CategoryAdapter extends ArrayAdapter<CategoryDto> {
		private Context context;

		public CategoryAdapter(Context context, List<CategoryDto> objects) {
			super(context, 0, objects);
			this.context = context;

		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
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
			final CategoryDto screen = getItem(position);
			holder.title.setText(screen.className);

			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(SelectClassActivity.this,
							SeatLayoutActivityNew.class);
					((MovieApp) getApplication()).activities
							.add(SelectClassActivity.this);
					screen.index = position+1;
					SampleModel.getInstance().setCategory(screen);
					;
					SampleModel.getInstance().activities.add(SelectClassActivity.this);
					startActivity(i);

				}
			});

			return convertView;
		}

		private class Holder {
			private TextView title;
		}

	}

	private void fetchMovieList(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		fetchTask = createTask(rssFeed, "");

		fetchTask
				.setOnCompleteListener(new NetworkTask.OnCompleteListener<Category>() {
					@Override
					public void onComplete(Category result) {
						progressLayout.setVisibility(View.GONE);
						c.addAll(result.categories);
						c.notifyDataSetChanged();
					}
				});
		fetchTask
				.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
					@Override
					public void onException(Exception exception) {
						progressLayout.setVisibility(View.GONE);
						exception.printStackTrace();
						Toast.makeText(SelectClassActivity.this,
								MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG)
								.show();
					}
				});
		fetchTask.execute();
	}

	private NetworkTask<Void, Void, Category> createTask(String url, String tId) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchClassCategory(url, tId);
	}
}
