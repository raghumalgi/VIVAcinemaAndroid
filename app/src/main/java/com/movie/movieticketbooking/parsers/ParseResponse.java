package com.movie.movieticketbooking.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.movie.movieticketbooking.activities.TheatreList;
import com.movie.movieticketbooking.models.BookingDetails;
import com.movie.movieticketbooking.models.BookingItems;
import com.movie.movieticketbooking.models.Category;
import com.movie.movieticketbooking.models.CategoryDto;
import com.movie.movieticketbooking.models.ClassLayout;
import com.movie.movieticketbooking.models.ContinueGuest;
import com.movie.movieticketbooking.models.Coupon;
import com.movie.movieticketbooking.models.Coupons;
import com.movie.movieticketbooking.models.MallDetails;
import com.movie.movieticketbooking.models.Registration;
import com.movie.movieticketbooking.models.ScreenDetail;
import com.movie.movieticketbooking.models.SeatLayout;
import com.movie.movieticketbooking.models.ShowTimemodel;
import com.movie.movieticketbooking.vos.MovieItems;

public class ParseResponse {
	public static final String ACTOR_NAME  = "ActorName";
	public static final String 	CAST =   "CastOnly";
	public static final String DESCRIPTION  = "Description";
	public static final String GENRE =  "Genre";
	public static final String LANGUAGE_DESC="LanguageDesc";
	public static final String  MOVIE_ID="MovieID";
	public static final String  MOVIE_TITLE="MovieTitle";
	public static final String  MOVIE_IMAGE = "MovieImage";
	public static final String  GETMOVIES =  "GetJSONMoviesByTheaterIDResult";
	public static final String  GETMOVIES_BY_THEATRES =  "GetMoviesByTheater";
	public static final String  ITEMS = "Items";
	public static final String  VIDEO_URL = "VideoURL";
	public static final String  MOVIE_CERTIFICATE = "MovieCertificate";
	public static final String  MOVIE_RATING = "Rating";
	public static final String  IMDB_MOVIE_RATING = "IMDBRating";
	public static final String  MOVIE_BANNER_IMAGE = "MovieBannerImage";
	public static final String  BOOKING_DETAILS= "GetJSONPaymentHistoryByIDResult";
	public static final String  COUPONS= "GETJSONGetCombosResult";
	public static final String  GUEST_LOGIN_CHECK= "GuestJSONLoginCheckResult";
	public static final String  USER_LOGIN_CHECK= "UserJSONLoginCheckResult";
	
	public static final String  CREATE_USER= "CreateJSONUserResult";
	//Theatre Detilas
	public static final String ADDRESS  = "Address";
	public static final String 	IMAGE_PATH =   "ImagePath";
	public static final String THEATRE_ID  = "TheatreID";
	public static final String THEATRE_NAME =  "TheatreName";
	public static final String THEATRE_TAG ="GetJSONAllTheatersResult";
	public static final String THEATRE_TAG_BY_MOVIE ="GetJSONTheatersByMovieIDResult";
	public static final String SCREEN_ID ="GetJSONScreenByMovieIDResult";
	
	public static final String SCREEN_MOVIE_ID ="ScreenID";
	public static final String SCREEN_NAME ="ScreenName";
	
	public static final String SHOWTIME_TAG ="GetJSONShowByScreenMoviesIDResult";
	public static final String SHOWTIME_TAG_NEW= "GetJSONScreenShowTimingsByMovieIDResult";
	public static final String SHOWTIME ="ShowTime";
	public static final String MOVIE_NAME ="MovieName";
	public static final String SHOW_ID ="ShowID";
	private static final String SHOWS = "shows";
	private static final String CLASS_LAYOUT = "ClassLayout";
	
	public static final String JSON_LOAD_LAYOUT ="JSONLoadLayoutResult";
	public static final String COLUMN_ID ="ColumnID";
	public static final String ROW_NAME ="RowName";
	public static final String ROW_NUMBER ="RowNumber";
	public static final String SEAT_NUMBER ="SeatNumber";
	public static final String SEAT_STATUS_ID ="SeatStatusID";
	private static final String ROWS = "Rows";
	private static final String COLUMNS = "Columns";
	private static final String PRICE = "Price";
	private static final String DIRECTOR = "Director";
	private static final String CLASS_TAG = "GetJSONLayoutResult";
	
	private static final String BOOKING_TAG = "GetJSONSaveBookingResult";
	private static Banner banner;
	
	public static  List<MovieItems> getMeMovies(String input) {
		List<MovieItems> list = new ArrayList<MovieItems>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(GETMOVIES);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
				for (int i = 0; i < movieitems.length(); i++) {
					MovieItems movieitem = new MovieItems();
					JSONObject jmovie = movieitems.getJSONObject(i);
					movieitem.ActorName = jmovie.getString(ACTOR_NAME);
					movieitem.Cast = jmovie.getString(CAST);
					movieitem.Description = jmovie.getString(DESCRIPTION);
					movieitem.director = jmovie.getString(DIRECTOR);
					
					movieitem.Genre = jmovie.getString(GENRE);
					movieitem.imdbRating = jmovie.getString(IMDB_MOVIE_RATING);
					movieitem.LanguageDesc = jmovie.getString(LANGUAGE_DESC);
					movieitem.MovieID = jmovie.getString(MOVIE_ID);
					
					movieitem.MovieTitle = jmovie.getString(MOVIE_TITLE);
					movieitem.MovieImage = jmovie.getString(MOVIE_IMAGE);
					movieitem.movieBannerImage = jmovie.getString(MOVIE_BANNER_IMAGE);
					movieitem.movieCertifacte = jmovie.getString(MOVIE_CERTIFICATE);
					movieitem.rating = jmovie.getString(MOVIE_RATING);
					movieitem.videourl = jmovie.getString(VIDEO_URL);
					
					
					movieitem.moviecer = jmovie.getString(MOVIE_CERTIFICATE);
					list.add(movieitem);
					
				}
				
				
			}
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return list;
		
	}

	public static  List<MallDetails> getMeTheatres(String input) {
		List<MallDetails> list = new ArrayList<MallDetails>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(THEATRE_TAG);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
				for (int i = 0; i < movieitems.length(); i++) {
					MallDetails movieitem = new MallDetails();
					JSONObject jmovie = movieitems.getJSONObject(i);
					movieitem.address = jmovie.getString(ADDRESS);
					movieitem.cityName = jmovie.getString("CityName");
					movieitem.theatreId = jmovie.getString(THEATRE_ID);
					movieitem.imagePath = jmovie.getString(IMAGE_PATH);
					movieitem.theatreName = jmovie.getString(THEATRE_NAME);
					movieitem.latitude = jmovie.getString("Latitude");
					movieitem.longitude = jmovie.getString("Longitude");
					list.add(movieitem);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return list;
		
	}
	
	
	public static Banner getBannerImage(String input) {
		try {
			 banner  = new Banner();
			JSONObject getmovies = new JSONObject(input);
		
			banner.valid  =  getmovies.getJSONObject("JSONHomeBannerResult").getBoolean("Valid");
			banner.bannerImgPath  =  getmovies.getJSONObject("JSONHomeBannerResult").getJSONObject("Item").getString("BannerImgPath");
			banner.bannerTitle  =  getmovies.getJSONObject("JSONHomeBannerResult").getJSONObject("Item").getJSONObject("MovieDeatils").getString(MOVIE_TITLE);
			banner.imdb  =  getmovies.getJSONObject("JSONHomeBannerResult").getJSONObject("Item").getJSONObject("MovieDeatils").getString(IMDB_MOVIE_RATING);
			banner.movieId  =  getmovies.getJSONObject("JSONHomeBannerResult").getString(MOVIE_ID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return banner;
		
	}
	
	public static  List<MallDetails> getMeTheatresByMovie(String input) {
		List<MallDetails> list = new ArrayList<MallDetails>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(THEATRE_TAG_BY_MOVIE);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
				for (int i = 0; i < movieitems.length(); i++) {
					MallDetails movieitem = new MallDetails();
					JSONObject jmovie = movieitems.getJSONObject(i);
					movieitem.address = jmovie.getString(ADDRESS);
					movieitem.cityName = jmovie.getString("CityName");
					movieitem.theatreId = jmovie.getString(THEATRE_ID);
					movieitem.imagePath = jmovie.getString(IMAGE_PATH);
					movieitem.theatreName = jmovie.getString(THEATRE_NAME);
					movieitem.latitude = jmovie.getString("Latitude");
					movieitem.longitude = jmovie.getString("Longitude");
					list.add(movieitem);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return list;
		
	}
	public static  List<MallDetails> geScreensByMovie(String input) {
		List<MallDetails> list = new ArrayList<MallDetails>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(THEATRE_TAG_BY_MOVIE);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
				for (int i = 0; i < movieitems.length(); i++) {
					MallDetails movieitem = new MallDetails();
					JSONObject jmovie = movieitems.getJSONObject(i);
					movieitem.address = jmovie.getString(ADDRESS);
					movieitem.cityName = jmovie.getString("CityName");
					movieitem.theatreId = jmovie.getString(THEATRE_ID);
					movieitem.imagePath = jmovie.getString(IMAGE_PATH);
					movieitem.theatreName = jmovie.getString(THEATRE_NAME);
					movieitem.latitude = jmovie.getString("Latitude");
					movieitem.longitude = jmovie.getString("Longitude");
					list.add(movieitem);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return list;
		
	}
	public static  List<ShowTimemodel> getMeShowTimes(String input) {
		List<ShowTimemodel> list = new ArrayList<ShowTimemodel>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(SHOWTIME_TAG);
			if(movie.has(ITEMS)){
				JSONArray showTimeitems = 	movie.getJSONArray(ITEMS);
				for (int i = 0; i < showTimeitems.length(); i++) {
					ShowTimemodel showitem = new ShowTimemodel();
					JSONObject jmovie = showTimeitems.getJSONObject(i);
					//showitem.movieName = jmovie.getString(MOVIE_NAME);
					showitem.showTime = jmovie.getString(SHOWTIME);
					showitem.showId = jmovie.getString(SHOW_ID);
					showitem.classDetails = jmovie.getString("ClassDetails");
					showitem.price = jmovie.getString("Price");
					/*JSONArray shows = 	jmovie.getJSONArray(SHOWS);
					for (int j = 0; j < shows.length(); j++) {
						Show s = new Show();
						JSONObject jshow = shows.getJSONObject(j);
						s.showId = jshow.getString(SHOW_ID);
						s.showTime = jshow.getString(SHOWTIME);
						showitem.shows.add(s);
					}
					*/
					list.add(showitem);
					
				}
				
			}
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return list;
		
	}


	
	public static  List<ScreenDetail> getMeScreenId(String input) {
		List<ScreenDetail> list = new ArrayList<ScreenDetail>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(SCREEN_ID);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
				for (int i = 0; i < movieitems.length(); i++) {
					ScreenDetail movieitem = new ScreenDetail();
					JSONObject jmovie = movieitems.getJSONObject(i);
					movieitem.screenId = jmovie.getString(SCREEN_MOVIE_ID);
					movieitem.screenName = jmovie.getString(SCREEN_NAME);
					movieitem.theatreIdCoupons = jmovie.getString(THEATRE_ID);
					
					list .add(movieitem);
					
				}
				
				
			}
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return list;
		
	}
	public static  List<String> getMeTheatreList(String input) {
		List<String> list = new ArrayList<String>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(THEATRE_TAG);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
				for (int i = 0; i < movieitems.length(); i++) {
					JSONObject jmovie = movieitems.getJSONObject(i);
					String theatreName = jmovie.getString(THEATRE_NAME);
					list.add(theatreName);
					
				}
			}
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return list;
		
	}
	
	public static  List<TheatreList> getMeAllTheatreList(String input) {
		List<TheatreList> list = new ArrayList<TheatreList>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(THEATRE_TAG);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
				for (int i = 0; i < movieitems.length(); i++) {
					JSONObject jmovie = movieitems.getJSONObject(i);
					TheatreList tl = new TheatreList();
					tl.theatreName = jmovie.getString(THEATRE_NAME);
					tl.theatreId = jmovie.getString(THEATRE_ID);
					list.add(tl);
					
				}
			}
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return list;
		
	}
	
	public static  SeatLayout getMeTktList(String input) {
		SeatLayout s = null;
		List<ClassLayout> classList = new ArrayList<ClassLayout>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(JSON_LOAD_LAYOUT);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
			
				for(int i=0;i<movieitems.length();i++){
					 JSONObject jsonnew=movieitems.getJSONObject(i);
					 JSONArray classArray = jsonnew.getJSONArray("ClassLayout");
						for (int j = 0; j < classArray.length(); j++) {
						ClassLayout classlayout = new ClassLayout();
						JSONObject json = 	classArray.getJSONObject(j);
						classlayout.classId = json.getInt("ClassID");
						classlayout.columnID = json.getInt(COLUMN_ID);
						classlayout.rowName = json.getString(ROW_NAME);
						classlayout.rowNumber = json.getString(ROW_NUMBER);
						classlayout.seatNumber = json.getString(SEAT_NUMBER);
						classlayout.seatStatusID = json.getInt(SEAT_STATUS_ID);
						classList.add(classlayout);
							
						}
						JSONArray screenClass = jsonnew.getJSONArray("ScreenClass");
						 s = new SeatLayout();
						JSONObject j = 	screenClass.getJSONObject(0);
						s.rows  = j.getInt(ROWS);
						s.columns  = j.getInt(COLUMNS);
						s.price  = j.getInt(PRICE);
						s.classlayoutList.addAll(classList);
					 
					  
				}
				
				
			
				
			}
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return s;
		
	}

	public static BookingDetails getMeBookingId(String s) {
		BookingDetails details = new BookingDetails();
		try {
			JSONObject getBookingDetails  = new JSONObject(s);
			JSONObject booking = 	getBookingDetails.getJSONObject(BOOKING_TAG);
			details.bookingId = booking.getJSONObject("Item").getString("BookingID");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return details;
	}

	public static Category getMeCategories(String s) {
		Category category = null;
		try {
			JSONObject getmovies = new JSONObject(s);
			JSONObject movie =  getmovies.getJSONObject(CLASS_TAG);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
				category = new Category();
				//for(int i=0;i<movieitems.length();i++){
					 JSONObject jsonnew=movieitems.getJSONObject(0);
					 
					 JSONArray screenlayoutItems = jsonnew.getJSONArray("ScreenLayoutDTO");
					 
					 for (int i = 0; i < screenlayoutItems.length(); i++) {
						 CategoryDto dto  = new CategoryDto();
						 JSONObject json = 	screenlayoutItems.getJSONObject(i);
						dto.classId =  json.getString("ClassID");
						dto.className =  json.getString("ClassName");
						dto.price =  json.getString("Price");
						 category.categories.add(dto);
					}
			}
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return category;
		
	}
	
	public static Coupon getCoupons(String input){
		Coupon classList = new Coupon();
		List<Coupons> couponList  = new ArrayList<Coupons>();
		
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(COUPONS);
			if(movie.has(ITEMS)){
				JSONArray items = 	movie.getJSONArray(ITEMS);
				JSONObject jsonObj = items.getJSONObject(0);
				classList.desc = jsonObj.getString("ComboDesc");
				classList.imgUrl = jsonObj.getString("ComboImageUrl");
				classList.shortDesc = jsonObj.getString("ComboShortDesc");
				JSONArray movieitems =jsonObj.getJSONArray("LstComboDet");
				for(int i=0;i<movieitems.length();i++){
					Coupons booking = new Coupons();
					 JSONObject jsonnew=movieitems.getJSONObject(i);
					 booking.comboId = jsonnew.getInt("ComboId");
					 //booking.comboDesc = jsonnew.getString("ComboDesc");
					 booking.price = jsonnew.getInt("Price");
					 couponList.add(booking);
				}
				
				classList.list.addAll(couponList);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return classList;
		
	}
	public static List<BookingItems> getBookingHistory(String input){
		List<BookingItems> classList = new ArrayList<BookingItems>();
		try {
			JSONObject getmovies = new JSONObject(input);
			JSONObject movie =  getmovies.getJSONObject(BOOKING_DETAILS);
			if(movie.has(ITEMS)){
				JSONArray movieitems = 	movie.getJSONArray(ITEMS);
				for(int i=0;i<movieitems.length();i++){
					BookingItems booking  = new BookingItems();
					 JSONObject jsonnew=movieitems.getJSONObject(i);
					 booking.amount = jsonnew.getString("AmountPaid");
					 booking.bookingid = jsonnew.getString("BookingID");
					 booking.location = jsonnew.getString("City");
					 booking.movieTitle = jsonnew.getString("Movie_Title");
					 booking.quantity = jsonnew.getString("NumberTicketsBooked");
					// String venue = jsonnew.getString("TheaterName")+"("+jsonnew.getString("ScreenName")+")";
					 booking.venue = jsonnew.getString("ScreenName");
					 booking.venue = jsonnew.getString("TheaterName")+"("+booking.venue+")";
					 booking.showTime = jsonnew.getString("ShowTime");
					 booking.seatInfo = jsonnew.getString("UserBookedSeats");
					 booking.transaction = jsonnew.getString("TransactionDetails");
					 classList.add(booking);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return classList;
		
	}
	public static ContinueGuest getBookingId(String input,boolean isUserLogin){
		ContinueGuest guest = new ContinueGuest();
		JSONArray object = null;
		try {
			JSONObject getBookingId = new JSONObject(input);
			if(!isUserLogin){
			 object = getBookingId.getJSONArray(GUEST_LOGIN_CHECK);
			}else{
				 object = getBookingId.getJSONArray(USER_LOGIN_CHECK);
			}
			for (int i = 0; i < object.length(); i++) {
				 JSONObject jsonnew=object.getJSONObject(i);
					guest.userId = jsonnew.getString("User_ID");
					Log.d("Movie","User id is "+guest.userId);
					guest.status = jsonnew.getString("Status");
					guest.name = jsonnew.getString("User_Name");
					guest.isValidUser = jsonnew.getBoolean("IsValidUser");
			}
		
			
		}
		 catch (JSONException e) {
				e.printStackTrace();
			}
			return guest;
			
		}
	public static Registration getRegisteredUserId(String input){
		Registration guest = new Registration();
		try {
			JSONObject getBookingId = new JSONObject(input);
			JSONArray createUser = getBookingId.getJSONArray(CREATE_USER);
			for (int i = 0; i < createUser.length(); i++) {
				 JSONObject jsonnew=createUser.getJSONObject(i);
					guest.isValid = jsonnew.getBoolean("IsValidUser");
					guest.msg = jsonnew.getString("Status");
					guest.userId =  jsonnew.getString("User_ID");
					guest.userName =  jsonnew.getString("User_Name");
			}
			
			
		}
		 catch (JSONException e) {
				e.printStackTrace();
			}
			return guest;
			
		}
	
	public static String contactUs(String input){
		String msg=  "";
		try {
			JSONObject getBookingId = new JSONObject(input);
			JSONObject createUser = getBookingId.getJSONObject("GetJSONContactUsResult");
			msg =  createUser.getString("Msg");
			
		}
		 catch (JSONException e) {
				e.printStackTrace();
			}
			return msg;
			
		}
}
