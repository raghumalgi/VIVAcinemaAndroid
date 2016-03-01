
package com.movie.movieticketbooking.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.Coupon;
import com.movie.movieticketbooking.models.Order;
import com.movie.movieticketbooking.models.SampleModel;

public class CouponActivity extends Activity {
	int count = 0;
	protected boolean firstSelected;
	protected boolean secondSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon);

		final TextView first = (TextView) findViewById(R.id.first_coupon);
		final TextView second = (TextView) findViewById(R.id.first_coupon);
		final TextView couponDesc = (TextView) findViewById(R.id.couponDesc);
		
		final Button fClose = (Button) findViewById(R.id.first_close);
		final Button sClose = (Button) findViewById(R.id.first_coupon);
		
		final Coupon coup = SampleModel.getInstance().getCouponList();
		final Order order = SampleModel.getInstance().getOrderSummary();
		final float amount = order.amount;
		
		couponDesc.setText(coup.desc);
findViewById(R.id.booking).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(firstSelected&&secondSelected){
					order.comboIds.append(amount+coup.list.get(0).comboId+","+amount+coup.list.get(1).comboId);
				}
				else if(firstSelected){
					order.comboIds.append(amount+coup.list.get(0).comboId);
				}
				else if(secondSelected){
					order.comboIds.append(amount+coup.list.get(1).comboId);
				}
				
				SampleModel.getInstance().setOrderSummary(order);
				
				Intent intent = new Intent(CouponActivity.this,FinalSummaryActivity.class);
				startActivity(intent);
				SampleModel.getInstance().activities.add(CouponActivity.this);
			}
		});

		findViewById(R.id.first_close).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (first.getVisibility() == View.VISIBLE) {
							first.setVisibility(View.GONE);
							count--;
							order.amount = 	amount-coup.list.get(0).price;
							//order.comboIds.delete(amount+coup.list.get(0).comboId);
							
							 SampleModel.getInstance().setOrderSummary(order);
							 firstSelected=false;
						}

					}
				});

		findViewById(R.id.first_coupon).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (second.getVisibility() == View.VISIBLE) {
							second.setVisibility(View.GONE);
							count--;
							order.amount = 	amount-coup.list.get(1).price;
							 SampleModel.getInstance().setOrderSummary(order);
							 secondSelected=false;
						}
						

					}
				});

		findViewById(R.id.select).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (count == 2) {
							return;
						} else {
							if (first.getVisibility() == View.GONE) {
								first.setVisibility(View.VISIBLE);
								first.setText(coup.shortDesc);
							order.amount = 	amount+coup.list.get(0).price;
							order.comboIds.append(amount+coup.list.get(0).comboId);
							 SampleModel.getInstance().setOrderSummary(order);
								count++;
								firstSelected = true;
							}

							else if (second.getVisibility() == View.GONE) {
								second.setVisibility(View.VISIBLE);
								second.setText(coup.shortDesc);
								count++;
								order.amount = 	amount+coup.list.get(1).price;
							
								 SampleModel.getInstance().setOrderSummary(order);
								 secondSelected = true;
							}
						}

					}
				});
	}

}
