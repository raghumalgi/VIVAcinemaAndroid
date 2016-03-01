package com.movie.movieticketbooking.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;

import com.movie.movieticketbooking.vos.Movie;
import com.movie.movieticketbooking.vos.MovieItems;

public class SampleModel {

	public static SampleModel instance;
	private static String currentTheatre;
	private  String theatreId;
	public ArrayList<String> selectedSeats = new ArrayList<String>();
	private  String theatreCouponId;
	private  String price;
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}



	private  BookingItems currentBookingItem;
	public BookingItems getCurrentBookingItem() {
		return currentBookingItem;
	}

	public void setCurrentBookingItem(BookingItems currentBookingItem) {
		this.currentBookingItem = currentBookingItem;
	}



	private List<BookingItems>	bList =new ArrayList<BookingItems>();
	public List<BookingItems> getbList() {
		return bList;
	}

	public void setbList(List<BookingItems> bList) {
		this.bList = bList;
	}

	public String getTheatreCouponId() {
		return theatreCouponId;
	}

	public void setTheatreCouponId(String theatreCouponId) {
		this.theatreCouponId = theatreCouponId;
	}



	private  CategoryDto category;
	private boolean isLoggedIn;
	private String showTime;
	private Coupon couponList;
	public  List<Activity> activities = new ArrayList<Activity>();
	public  List<Activity> activities_nav = new ArrayList<Activity>();
	public Coupon getCouponList() {
		return couponList;
	}

	public void setCouponList(Coupon couponList) {
		this.couponList = couponList;
	}


	public void clearAll(){
		if(!activities.isEmpty()){
			for (Activity element : activities) {
				element.finish();
			}
		}
	}
    public void clearNavigationActivities(){
        if(!activities_nav.isEmpty()){
            for (Activity element : activities_nav) {
                element.finish();
            }
        }
    }



	private String paymentType;
	
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public String getTheatreId() {
		return theatreId;
	}

	public void setTheatreId(String theatreId) {
		this.theatreId = theatreId;
	}

	public static String getCurrentTheatre() {
		return currentTheatre;
	}
	
	
	
	public static void setCurrentTheatre(String currentTheatre) {
		SampleModel.currentTheatre = currentTheatre;
	}





	private List<MovieItems> currentMovieItems;
	private MovieItems item;
	private Show show;
	private String showId;
	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}





	private MallDetails screen;
	private ScreenDetail screenDetail;
	public ScreenDetail getScreenDetail() {
		return screenDetail;
	}

	public void setScreenDetail(ScreenDetail screenDetail) {
		this.screenDetail = screenDetail;
	}





	private Order order;
	private String tName;
	private String userId;
	private String userName;
	private String banner;
	public List<MovieItems> getCurrentMovieItems() { return currentMovieItems; }
	
	public void setCurrentMovieItems(List<MovieItems> currentRssItems) {
		this.currentMovieItems = currentRssItems;
	}
	
	
	private SampleModel() {
		
	}

	public String getCurrentUserId(){
		return userId;
	}
	
	
	
	public static SampleModel getInstance() {
		if (instance != null) return instance;
		instance = new SampleModel();
		return instance;
	}

	public void setCurrentMovie(MovieItems item) {
		this.item = item;
		
	}
	public MovieItems getCurrentMovie( ) {
		return item;
		
	}

	public void setCurrentShowTime(Show show) {
		this.show = show;
		
	}
	
	public Show getCurrentShowTime(){
		return show;
	}

	public void setCurrentMall(MallDetails screen) {
		this.screen  = screen;
		
	}
	
	public void setCurrentTheatreForHome(String tName) {
		this.tName  = tName;
		
	}
	
	public String getTheatreForHome(){
		return tName;
	}
	public MallDetails getCurrentMall(){
		return screen;
	}

	public void setOrderSummary(Order order) {
		this.order  = order;
		
	}
	
	public Order getOrderSummary() {
		return order;
		
	}

	public void setCurrentUserId(String userId) {
		this.userId = userId;
		
	}

	public void setCurrentUserName(String userName) {
		this.userName = userName;
		
	}
	public String getCurrentUserName() {
		return userName;
		
	}

	public void setBanner(String banner) {
		this.banner = banner;
		
	}
	
	public String getBanner() {
		return banner;
		
	}

	
}
