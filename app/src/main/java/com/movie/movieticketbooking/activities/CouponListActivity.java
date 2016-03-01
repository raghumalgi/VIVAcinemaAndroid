
package com.movie.movieticketbooking.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.Coupon;
import com.movie.movieticketbooking.models.Coupons;
import com.movie.movieticketbooking.models.Order;
import com.movie.movieticketbooking.models.SampleModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class CouponListActivity extends Activity {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	int count = 0;
	int selectedSeatSize;
	private Coupon coup;
	private MyAdapter myAdapter;
	public List<Coupons> list = new ArrayList<Coupons>();
	private float amount;
	private DisplayImageOptions options;
	private TextView offer;
	protected int newCount;
	private RelativeLayout rv;
	private RelativeLayout rv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon);

		final TextView couponDesc = (TextView) findViewById(R.id.couponDesc);
		final TextView couponText = (TextView) findViewById(R.id.txt);
		
		
		/*final ListView lv = (ListView) findViewById(R.id.list);
		myAdapter = new MyAdapter(this, 0, list);

		lv.setAdapter(myAdapter);
*/
		coup = SampleModel.getInstance().getCouponList();

		final Order order = SampleModel.getInstance().getOrderSummary();
		selectedSeatSize = SampleModel.getInstance().selectedSeats.size();
		couponText.setText("You can select upto "+selectedSeatSize+" coupons");
		 amount = order.amount;

		couponDesc.setText(coup.desc);
		 offer = (TextView) findViewById(R.id.textViewOfferCount);
		
		
		findViewById(R.id.booking).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				
				if(myAdapter!=null&&!myAdapter.isEmpty()){
					for(int i = 0;i<list.size();i++){
					//	amount = amount+list.get(i).price;
						Log.d("|||||Damn||||","amount "+amount);
						order.comboIds.append(list.get(i).comboId);
						if(i!=list.size()-1){
							order.comboIds.append(",");
						}
						
					}
					Log.d("|||||Damn||||","combos "+order.comboIds.toString());
				}
				
				
				//order.amount= amount;
				SampleModel.getInstance().setOrderSummary(order);
				Intent intent = new Intent(CouponListActivity.this, FinalSummaryActivity.class);
				startActivity(intent);
				SampleModel.getInstance().activities.add(CouponListActivity.this);
			}
		});

		findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				rv.setVisibility(View.VISIBLE);
				rv1.setVisibility(View.VISIBLE);
				
				
				newCount = newCount+1;
				offer.setText(""+newCount);
				if(newCount==selectedSeatSize){
					findViewById(R.id.select).setEnabled(false);
					findViewById(R.id.select).setBackgroundColor(getResources().getColor(R.color.grey));
				}else{
					findViewById(R.id.select).setEnabled(true);
					findViewById(R.id.select).setBackgroundColor(getResources().getColor(R.color.red));
				}
				/*if(selectedSeatSize>myAdapter.getCount()){
				Coupons coupon = coup.list.get(0);
				//list.add(coupon);
				myAdapter.add(coupon);
				int newCount = myAdapter.getCount();
				if(newCount==0){
					offer.setText("");
					}else{
						offer.setText(""+newCount);
					}
				
				}
			}*/
			}
		});
		
		loadCoupons();
	}

	private void initOptions() {
		options = new DisplayImageOptions.Builder()
		.showStubImage(android.R.drawable.gallery_thumb)
		.showImageForEmptyUri(android.R.drawable.gallery_thumb)
		.showImageOnFail(android.R.drawable.gallery_thumb)
		.cacheOnDisc(true)
		.cacheInMemory(false)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
        .bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
	}
	
	void loadCoupons(){
		
		final TextView first = (TextView) findViewById(R.id.first_coupon);
		final ImageView fClose = (ImageView) findViewById(R.id.first_close);
	 rv = (RelativeLayout) findViewById(R.id.textcount);
		 rv1 = (RelativeLayout) findViewById(R.id.close);
		
		first.setText(coup.shortDesc);
		fClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				newCount= newCount-1;
				Log.d("BOOK","New count "+newCount);
				if(newCount<=0){
					offer.setText("");
					rv.setVisibility(View.GONE);
					rv1.setVisibility(View.GONE);
					findViewById(R.id.select).setEnabled(true);
					findViewById(R.id.select).setBackgroundColor(getResources().getColor(R.color.red));
					newCount = 0;
					}else{
						findViewById(R.id.select).setEnabled(true);
						findViewById(R.id.select).setBackgroundColor(getResources().getColor(R.color.red));
						offer.setText(""+newCount);
					
					}

			}
		});
		
	}
	public class MyAdapter extends ArrayAdapter<Coupons> {
		public MyAdapter(Context ctx, int txtViewResourceId, List<Coupons> objects) {
			super(ctx, txtViewResourceId, objects);
		}

		
		
		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}

		public View getCustomView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.coupon_list_item, parent, false);
			final TextView first = (TextView) mySpinner.findViewById(R.id.first_coupon);
			final ImageView fClose = (ImageView) mySpinner.findViewById(R.id.first_close);
			final int count = getCount();
			
			first.setText(coup.shortDesc);
			fClose.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					myAdapter.remove(list.remove(position));
					int newCount = getCount();
					if(newCount==0){
						offer.setText("");
						}else{
							offer.setText(""+newCount);
						}

				}
			});

			return mySpinner;
		}
	}

}
