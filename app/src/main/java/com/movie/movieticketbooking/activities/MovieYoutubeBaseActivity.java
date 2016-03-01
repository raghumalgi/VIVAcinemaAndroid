package com.movie.movieticketbooking.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.SampleModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MovieYoutubeBaseActivity extends YouTubeBaseActivity {
	
	private static final String tag = "ActionSheetDemoActivity";
	private int width;
	private WindowManager.LayoutParams params;
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected Activity activity;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOptions();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        Log.i(tag, "Height : "+metrics.heightPixels+" Width : "+width);
        
        params = getWindow().getAttributes();
		params.width = width;
        
    }
    
    public ImageLoader getLoader(){
		return imageLoader;
	}
    
    public static void displayImage(final String imageUrl, final ImageView imageView,final Context context) {
		
		
		 Glide.with(context)
         .load(imageUrl)
         .into(imageView);

	}
    
    public void showActionSheet() {
    	final Dialog myDialog = new Dialog(MovieYoutubeBaseActivity.this, R.style.CustomTheme);
		myDialog.setContentView(R.layout.actionsheet);

		myDialog.setCanceledOnTouchOutside(true);
		myDialog.getWindow().getAttributes().width = width;
		Log.i(tag, "Action Sheet created");
		
		myDialog.show();
		myDialog.getWindow().setGravity(Gravity.BOTTOM);
		myDialog.findViewById(R.id.di).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				myDialog.dismiss();
				
			}
		});
		
		
		
		myDialog.findViewById(R.id.terms).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MovieYoutubeBaseActivity.this,TermsActivity.class);
				startActivity(intent);
				myDialog.dismiss();
				
			}
		});
		
		
		myDialog.findViewById(R.id.contactus).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MovieYoutubeBaseActivity.this,ContactUsActivity.class);
				startActivity(intent);
				myDialog.dismiss();
				
			}
		});
		
		
		
		myDialog.findViewById(R.id.find).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(activity!=null){
					SampleModel.getInstance().activities.add(activity);
					}
				Intent intent = new Intent(MovieYoutubeBaseActivity.this,FindMoviesActivity.class);
				startActivity(intent);
				myDialog.dismiss();
				
			}
		});
		
		
		
		myDialog.findViewById(R.id.homescreen).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MovieYoutubeBaseActivity.this,MovieGridActivity.class);
				startActivity(intent);
				myDialog.dismiss();
				
			}
		});
		
		myDialog.findViewById(R.id.mytkts).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userId  = SampleModel.getInstance().getCurrentUserId();
				if(userId==null){
					Toast.makeText(MovieYoutubeBaseActivity.this, "Please login to see your ticket details ", Toast.LENGTH_SHORT).show();
					myDialog.dismiss();
					return;
				}
				Intent intent = new Intent(MovieYoutubeBaseActivity.this,MyTickets.class);
				startActivity(intent);
				myDialog.dismiss();
				
			}
		});
		
		myDialog.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MovieYoutubeBaseActivity.this,UserLoginActivity.class);
				startActivity(intent);
				myDialog.dismiss();
				
			}
		});
		
		
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
}