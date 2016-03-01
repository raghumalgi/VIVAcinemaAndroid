package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.business.FetchCouponsTask;
import com.movie.movieticketbooking.business.FetchLayoutTask;
import com.movie.movieticketbooking.business.FetchMovieErrorTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.models.ClassLayout;
import com.movie.movieticketbooking.models.Coupon;
import com.movie.movieticketbooking.models.Order;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.models.SeatLayout;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.vos.MovieItems;

public class SeatLayoutActivityNew extends BaseActivity implements OnClickListener {
	private NetworkTask<Void, Void, SeatLayout> fetchTask;
	 private android.widget.TableLayout.LayoutParams parTblLayout;
	   private android.widget.TableRow.LayoutParams parTblRow;
		private TableLayout tablelayout;
		private TableRow tablerow;
		private ImageView image;
		//ProgressDialog pd;
		private ImageView screen;
		private Button next;
		private HashMap<Integer, Seat> seatMap = new HashMap<Integer, Seat>();
		private LinearLayout progressLayout;
		
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.seat_selection_new);
		//setContentView(R.layout.theatre_list);
				getLayoutInflater().inflate(R.layout.seat_selection_new, frameLayout);
		SampleModel.getInstance().activities_nav.add(SeatLayoutActivityNew.this);
		//pd = new ProgressDialog(this)
		//pd.setMessage("Loading ...");
		progressLayout = (LinearLayout) findViewById(R.id.progress_ll);
		
		MovieItems item = SampleModel.getInstance().getCurrentMovie();
		TextView tv = (TextView) findViewById(R.id.txtTitle);
		tv.setText(item.MovieTitle);
		TextView name = (TextView) findViewById(R.id.theatreName);
		name.setText( SampleModel.getInstance().getScreenDetail().screenName);
		
		TextView sTv = (TextView) findViewById(R.id.time);
		 screen = (ImageView) findViewById(R.id.screen);
		 next = (Button) findViewById(R.id.next);
		String s = SampleModel.getInstance().getShowTime();
		s= s.substring(s.indexOf("<b>")+3, s.indexOf("</b>"));
		sTv.setText(s);
		((TextView) findViewById(R.id.imdb)).setText(item.imdbRating);
		if(item.movieBannerImage!=null&&item.movieBannerImage.length()>0){
			imageLoader.displayImage("http://37.131.68.76/"+item.movieBannerImage,   ((ImageView) findViewById(R.id.bannerIv)), options, null);
			}else{
				//imageLoader.displayImage("http://37.131.68.76/"+SampleModel.getInstance().getBanner(),   ((ImageView) findViewById(R.id.bannerIv)), options, null);
				MovieYoutubeBaseActivity.displayImage(SampleModel.getInstance().getBanner(),   ((ImageView) findViewById(R.id.bannerIv)), this);
			}
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					if(selectedSeats.size()>0){
						fetchCoupons();
						//processOrderSummary();
					}else{
						Toast.makeText(SeatLayoutActivityNew.this, "Please select the seats ", Toast.LENGTH_SHORT).show();
					}
					
					
			}
		});
		
		tablelayout = (TableLayout)findViewById(R.id.layout);;
		
        parTblRow = new android.widget.TableRow.LayoutParams(-2, -2);
        parTblLayout = new android.widget.TableLayout.LayoutParams(-1, -1);
		fetchSeatLayout(MovieParams.SEAT_LAYOUT_API);
		
	}
	
	public void backClicked(View v){
		finish();
	}
	

	public void moreClicked(View v){
		//showActionSheet();
	}

	
	private void processOrderSummary() {
		SampleModel sm = SampleModel.getInstance();
		StringBuilder builder = new StringBuilder();
		sm.selectedSeats = selectedSeats;
		for(int i = 0;i<selectedSeats.size();i++){
			builder.append(selectedSeats.get(i));
			if(i!=selectedSeats.size()-1){
				builder.append(",");
			}
			
		}
		Order d  = new Order();
		d.show = sm.getShowId();
		d.showTime = sm.getShowTime();
		//d.classId = sm.getCategory().classId;
		d.classId = ""+classId;
		//Log.d("VIVA","Price is "+sm.getCategory().price);
		float price = Float.parseFloat(sm.getPrice());
		d.amount = selectedSeats.size()*price;
		for(int i = 0;i<selectedSeats.size();i++){
			d.amount = 0.3f+d.amount;
		}
		String amt = String.format("%.2f", d.amount);
		Log.d("VIVA","amount is "+amt);
		d.amount = Float.parseFloat(amt);;
		d.tickets=selectedSeats.size();
		d.userId = "0";
		d.price = ""+price;
		d.bookedSeats = builder.toString();
		d.movie = sm.getCurrentMovie().MovieTitle;
		SampleModel.getInstance().setOrderSummary(d);
		if(couponList!=null&&!couponList.list.isEmpty()){
			SampleModel.getInstance().setCouponList(couponList);
			Intent i = new Intent(SeatLayoutActivityNew.this,CouponListActivity.class);
			startActivity(i);
			SampleModel.getInstance().activities.add(SeatLayoutActivityNew.this);
		
			}else{
				Intent intent = new Intent(SeatLayoutActivityNew.this,FinalSummaryActivity.class);
				startActivity(intent);
				SampleModel.getInstance().activities.add(SeatLayoutActivityNew.this);
			}
		//}
		
	}

	private void fetchSeatLayout(String rssFeed) {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		//pd.show();
		progressLayout.setVisibility(View.VISIBLE);
		String showId = SampleModel.getInstance().getShowId();
		//String classId = SampleModel.getInstance().getCategory().classId; 	
		
		fetchTask = createTask(rssFeed,showId,"");
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<SeatLayout>() {
			@Override
			public void onComplete(SeatLayout result) {
				parseAndAddViews(result);
			//	pd.dismiss();
				progressLayout.setVisibility(View.GONE);
				//progressLayout.setVisibility(View.GONE);
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
			//	pd.dismiss();
				progressLayout.setVisibility(View.GONE);
				//progressLayout.setVisibility(View.GONE);
				exception.printStackTrace();
				Toast.makeText(SeatLayoutActivityNew.this, "Please check your Internet Connection", Toast.LENGTH_LONG).show();
			}
		});
		fetchTask.execute();
	}
	android.widget.TableRow.LayoutParams lp;
	protected int pColn;
	protected String pRow;
	protected int classId;
	protected void parseAndAddViews(SeatLayout result) {
		List<ClassLayout> list = result.classlayoutList;
		//alternateMethod(list);
		int prevColumnid = 0;
		
		int dimen  = (int) getResources().getDimension(R.dimen.seatheight);
		
		 lp = 	new android.widget.TableRow.LayoutParams(dimen,dimen);
		 lp.setMargins(8, 0, 8, 0);
		for (int i = 0; i < list.size(); i++) {
			boolean addrow = false;
			
			ClassLayout seat  =	list.get(i);
			Log.d("VIVA","Column id is "+seat.columnID);
				if((seat.columnID)<prevColumnid){
					addrow = true;
					addTableRow(seat);
				}
			else if(prevColumnid==0){
				addTableRow(seat);
				addrow = true;
				
			}else{
				 image = new ImageView(this);
				 //image.setPadding(20,20, 20,20);
				 
				if(seat.seatStatusID==0){
					image.setBackgroundResource(R.drawable.seat_red);
				}

				else if(seat.seatStatusID==1){
					image.setBackgroundResource(R.drawable.seat_gray);
				}

				else if(seat.seatStatusID==2){
					image.setBackgroundResource(R.drawable.seat_green);
				}
				//For Gaps
				if((seat.columnID-prevColumnid)!=1){
					int value =  seat.columnID-prevColumnid;
					for (int j = 0; j < value-1; j++){
						
						tablerow.addView(new ImageView(this), lp);
					}
				}
					tablerow.addView(image, lp);
			}
				prevColumnid = seat.columnID;
				 image.setOnClickListener(new OnClickListener() {
				       public void onClick(final View v) {
				    		ClassLayout seat = (ClassLayout) v.getTag();
				    		Log.d("VIVA","Clicked selected seat "+seat.seatNumber);
							 if(seat.seatStatusID==1){
								 screen.setVisibility(View.GONE);
								 next.setVisibility(View.VISIBLE);
								 
								if(selectedSeats.size()>0){
									if(selectedSeats.contains(seat.seatNumber)){
										/*for (String selectedSeat : selectedSeats) {
											selectedSeats.remove(selectedSeat);
										}*/
										selectedSeats.clear();
										
										Iterator<Entry<Integer, Seat>> it = seatMap.entrySet().iterator();
									    while (it.hasNext()) {
									        Map.Entry<Integer,Seat> pair = it.next();
									        pair.getValue().seatView.setBackgroundResource(R.drawable.seat_gray);;
									        it.remove(); // avoids a ConcurrentModificationException
									    }
									
										seatMap.clear();
										
									}else{
										if(selectedSeats.size()==5){
							    			Toast.makeText(SeatLayoutActivityNew.this, 
							    					"You are allowed to book upto 5 seats per session ", Toast.LENGTH_SHORT).show();
							    			return;
							    		}
										Log.d("VIVA","pColn seat " +pColn+"Row name " +pRow+"Seat column id "+seat.columnID);
										
										int besides1 = seat.columnID-1;
										int besides2 = seat.columnID+1;
										Log.d("VIVA","Last Row name "+pRow+" Selected Seat row "+seat.rowName);
										if((pRow.equalsIgnoreCase(seat.rowName))&&((seatMap.get(besides1)!=null)||(seatMap.get(besides2)!=null))){
										//if((pRow.equalsIgnoreCase(seat.rowName))&&(pColn+1==seat.columnID||pColn-1==seat.columnID)){
											selectedSeats.add(seat.seatNumber);
											Seat seatM =  new Seat();
											seatM.seatNumber = seat.seatNumber;
											seatM.seatView = v;
											seatMap.put(seat.columnID, seatM);
											v.setBackgroundResource(R.drawable.seat_pink);
											pColn=seat.columnID;
											pRow = seat.rowName;
										}else{
											Toast.makeText(SeatLayoutActivityNew.this, 
							    					"Please select a seat beside the selected seat(s) ", Toast.LENGTH_SHORT).show();
							    			
										}
										
									}
								}else{
									classId = seat.classId;
									selectedSeats.add(seat.seatNumber);
									Seat seatM =  new Seat();
									seatM.seatNumber = seat.seatNumber;
									seatM.seatView = v;
									seatMap.put(seat.columnID, seatM);
									v.setBackgroundResource(R.drawable.seat_pink);
									pColn=seat.columnID;
									pRow = seat.rowName;
								}
								
							}

							
				    		
				        }
				    });
				image.setTag(seat);
				if(addrow){
					tablelayout.addView(tablerow, parTblLayout);
				}
				

		}
		
		
		
	}
	
	
	
	ArrayList<String> selectedSeats = new ArrayList<String>();
	
	private void addTableRow(ClassLayout seat) {
		tablerow = new TableRow(this);
		tablerow.setPadding(10, 10, 10, 20);
		image = new ImageView(this);
		
		if(seat.seatStatusID==0){
			image.setBackgroundResource(R.drawable.seat_red);
		}

		else if(seat.seatStatusID==1){
			image.setBackgroundResource(R.drawable.seat_gray);
		}

		else if(seat.seatStatusID==2){
			image.setBackgroundResource(R.drawable.seat_green);
			
		}
		
		//For Gaps
		if(seat.columnID!=1){
			int value =  seat.columnID-1;
			for (int j = 0; j < value; j++){
				tablerow.addView(new ImageView(this), lp);
			}
			
			
		}
		tablerow.addView(image,lp);
		
		
	}
	
	private NetworkTask<Void, Void,Coupon> fetchCouponsTask;
	protected Coupon couponList;
	
	
	private void fetchCoupons() {
		if (fetchCouponsTask != null && !fetchCouponsTask.isComplete()) {
			fetchCouponsTask.abort();
		}
		progressLayout.setVisibility(View.VISIBLE);
		fetchCouponsTask = createTask(MovieParams.GET_COUPON_ID);
		fetchCouponsTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<Coupon>() {
			@Override
			public void onComplete(Coupon result) {
				couponList  = result;
				processOrderSummary();
				progressLayout.setVisibility(View.GONE);
				//pd.dismiss();
			}
		});
		fetchCouponsTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
			//	pd.dismiss();
				progressLayout.setVisibility(View.GONE);
				exception.printStackTrace();
				Toast.makeText(SeatLayoutActivityNew.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchCouponsTask.execute();
	}
	
	private NetworkTask<Void, Void, Coupon> createTask(String url) {
		//pd  = new ProgressDialog(this);
		//pd.setMessage("Loading ...");
		//pd.show();
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchCouponsTask(url,SampleModel.getInstance().getTheatreCouponId());
	}
	
	

	private NetworkTask<Void, Void, SeatLayout> createTask(String url, String showId, String classId) {
		if (url.equals(MovieParams.ERROR_MOVIE_API)) {
			new FetchMovieErrorTask();
		}
		return new FetchLayoutTask(url,showId,classId);
	}

	@Override
	public void onClick(View v) {
		Log.d("VIVA","Clicked selected ");
		ClassLayout seat = (ClassLayout) v.getTag();
		Log.d("VIVA","Clicked selected seat "+seat.seatNumber);
		
		
	}
	
	class Seat{
		public String seatNumber;
		public View seatView;
	}

}
