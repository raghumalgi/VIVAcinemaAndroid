package com.movie.movieticketbooking;

import java.util.HashSet;
import java.util.Iterator;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MovieApp extends Application {

	private static MovieApp instance;
	public static DisplayImageOptions options;
	
	public HashSet<Activity> activities = new HashSet<Activity>();
	
	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
	}
	public MovieApp() {
		instance = this;
		
		
	}
	
	
	private static final DefaultHttpClient client = createClient();


    static DefaultHttpClient getClient(){
            return client;
    }
    private static DefaultHttpClient createClient(){
            BasicHttpParams params = new BasicHttpParams();
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
            schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
            ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
            DefaultHttpClient httpclient = new DefaultHttpClient(cm, params);
            httpclient.getCookieStore().getCookies();
            return httpclient;
    }
	
	public static Context getContext() {
		return instance.getApplicationContext();
	}
	
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheExtraOptions(800, 800, CompressFormat.PNG, 100, null)
				.writeDebugLogs()
				.build();
		 options = new DisplayImageOptions.Builder()
			//.cacheInMemory(true)
			.cacheOnDisc(true)
			.cacheInMemory(false)
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
	        .bitmapConfig(Bitmap.Config.RGB_565)
	        .build();
		ImageLoader.getInstance().init(config);
	}
	public void clearAll(Activity activity) {
		Iterator<Activity> iter = activities.iterator();
		while (iter.hasNext()) {
			iter.next().finish();
		}
		activity.finish();
		
		
	}
	
	

}
