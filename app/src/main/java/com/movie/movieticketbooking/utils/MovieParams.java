package com.movie.movieticketbooking.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.util.Log;

import com.movie.movieticketbooking.models.FormattedDate;

public class MovieParams {
	
	
	
	//public static final String BASE_API = "http://vivabahraincinema.com/services/";
	public static final String BASE_API = "http://37.131.68.76/services/";
	
	public static final String MOVIE_LIST_API = BASE_API+"GetMovies.svc/json/";
	public static final String BANNER_API = BASE_API+"GetHomeBanner.svc/json/";
	public static final String GET_MOVIES_BY_SCREENS_API =BASE_API+"GetMoviesByTheater.svc/json/";
	public static final String SAVE_BOOKING_API =BASE_API+"SaveBook.svc/json/";	
	public static final String THEATRES_API = BASE_API+"GetScreens.svc/json/";
	public static final String SHOWTIMES_API = BASE_API+"GetShowsByMovie.svc/json/";
	public static final String CONTACT_API = BASE_API+"ContactUs.svc/json/";
	public static final String ERROR_MOVIE_API = "";
	public static final String SHOWTIMES_MOVIE_API = BASE_API +
			"GetScreensWithShowTimings.svc/json/";
	public static final String SEAT_LAYOUT_API = BASE_API +"ScreenLayout.svc/json/?"; /*+
			"ShowID=887&classid=52&sdt=01/09/2014";*/
	public static final String SELECT_CATEGORY_API = BASE_API + "GetClassesByScreens.svc/json/";
	public static final String BOOKING_HISTORY_API = BASE_API + "GetBookingDetails.svc/json/";
	public static final String GET_THEATRES_BY_MOVIE = BASE_API + "GetTheatersByMovie.svc/json/";
	
	public static final String GET_ALL_THEATRES = BASE_API + "GetAllTheater.svc/json/";
	
	public static final String GET_COUPON_ID = BASE_API+"GetComboByMallID.svc/json/";
	
	public static final String TEST_GUEST ="71006ad3-bf0c-4be3-a3e8-7b86d7e1e925";
	
	public static final String CONTINUE_GUEST_API =BASE_API +"GuestLogin.svc/json/";
	public static final String LOGIN_API =BASE_API +"CheckLogin.svc/json/";

	public static final String PAYMENT_HISTORY_API =BASE_API + "PaymentHistory.svc/json/";
	public static final CharSequence MESSAGE_TXT = "Please check your Internet Connection";
	
	public static final String REGISTRATION_API =BASE_API +"UserRegistration.svc/json/";

			
	public static String getYouTubeVideoId(String video_url) {

        if (video_url != null && video_url.length() > 0) {
        	 Log.d("VYoutube","video url is "+video_url);
            Uri video_uri = Uri.parse(video_url);
            String video_id = video_uri.getQueryParameter("v");
           
            if (video_id == null){
            	Log.d("VYoutube","video id is null");
            	
                video_id = parseYoutubeVideoId(video_url);
            }

            return video_id;
        }
        return null;
    }
	public static String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String currentDateandTime = sdf.format(new Date());
		return currentDateandTime;
	}
	
	public static String  getDate(){
		Date date  = new Date();
		String dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE", date);//Thursday
		String stringMonth = (String) android.text.format.DateFormat.format("MMM", date);
		String day = (String) android.text.format.DateFormat.format("dd", date);
		return dayOfTheWeek+" "+day+" "+stringMonth;
	}

    public static String parseYoutubeVideoId(String youtubeUrl)
    {
        String video_id = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 &&
                youtubeUrl.startsWith("http"))
        {
            // ^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/
            String expression = "^.*((youtu.be" + "\\/)"
                    + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches())
            {
                // Regular expression some how doesn't work with id with "v" at
                // prefix
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    video_id = groupIndex1;
                else if (groupIndex1 != null && groupIndex1.length() == 10)
                    video_id = "v" + groupIndex1;
            }
        }
        return video_id;
    }
   

}
