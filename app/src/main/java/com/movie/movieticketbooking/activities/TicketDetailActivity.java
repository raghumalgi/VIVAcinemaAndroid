package com.movie.movieticketbooking.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.movie.movieticketbooking.AndroidBarcodeView;
import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.BookingItems;
import com.movie.movieticketbooking.models.SampleModel;

public class TicketDetailActivity extends ActionSheetBaseActivity {
	private BookingItems item;
	private AndroidBarcodeView barcode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ticketdetails);

		item = SampleModel.getInstance().getCurrentBookingItem();
		((TextView) findViewById(R.id.txtTitle)).setText(item.movieTitle);
		((TextView) findViewById(R.id.one)).setText(item.seatInfo);
		((TextView) findViewById(R.id.two)).setText(item.venue);
		((TextView) findViewById(R.id.three)).setText(item.showTime);
		
		
		barcode = 	(AndroidBarcodeView) findViewById(R.id.ticketbar);
		barcode.data = item.bookingid+item.position;
		
		barcode.invalidate();
		
		

	}

	public void backClicked(View v) {
		finish();
	}

	public void moreClicked(View v) {
		showActionSheet();
	}

}
