package com.movie.movieticketbooking.activities;

import java.util.List;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.models.SampleModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.movie.movieticketbooking.R.color.purple;

/**
 * @author dipenp
 *
 * This activity will add Navigation Drawer for our application and all the code related to navigation drawer.
 * We are going to extend all our other activites from this BaseActivity so that every activity will have Navigation Drawer in it.
 * This activity layout contain one frame layout in which we will add our child activity layout.   
 */
@SuppressWarnings("deprecation")
public class BaseActivity extends ActionBarActivity {

 /**
  *  Frame layout: Which is going to be used as parent layout for child activity layout.
  *  This layout is protected so that child activity can access this 
  *  */
 protected FrameLayout frameLayout;
 
 protected ImageLoader imageLoader = ImageLoader.getInstance();
 
 protected DisplayImageOptions options;
 
 /**
  * ListView to add navigation drawer item in it.
  * We have made it protected to access it in child class. We will just use it in child class to make item selected according to activity opened. 
  */
 
 protected ListView mDrawerList;
 
 /**
  * List item array for navigation drawer items.
  * */
 protected String[] listArray = { "Login", "Register","My Tickets", "Home Screen", "Find Movies", "Contact Us","Terms & Conditions" };
 
 /**
  * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list. 
  * */
 protected static int position;
 
 /**
  *  This flag is used just to check that launcher activity is called first time
  *  so that we can open appropriate Activity on launch and make list item position selected accordingly.   
  * */
 private static boolean isLaunch = true;
 
 /**
  *  Base layout node of this Activity.   
  * */
 private DrawerLayout mDrawerLayout;
 
 /**
  * Drawer listner class for drawer open, close etc.
  */
 private ActionBarDrawerToggle actionBarDrawerToggle;

private Toolbar toolbar;
    private CinemaAdress adapter;


    @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
     Window window = getWindow();
     window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
     window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         window.setStatusBarColor(getResources().getColor(purple));
     }
  initOptions();
  
  toolbar = (Toolbar) findViewById(R.id.toolbar);
  setSupportActionBar(toolbar);
  toolbar.setNavigationIcon(R.drawable.menu_icon);
  
  /*getSupportActionBar().setDisplayShowCustomEnabled(true);
  View view = getLayoutInflater().inflate(R.layout.tool_bar, null);
  getSupportActionBar().setCustomView(view);
  getSupportActionBar().setHomeButtonEnabled(true);
  getSupportActionBar().setDisplayShowCustomEnabled(true);
  */
  frameLayout = (FrameLayout)findViewById(R.id.content_frame);
  mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
  mDrawerList = (ListView) findViewById(R.id.left_drawer);
  
  // set a custom shadow that overlays the main content when the drawer opens
  //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
      
  // set up the drawer's list view with items and click listener
     adapter = new CinemaAdress(this, listArray);
  mDrawerList.setAdapter(new CinemaAdress(this, listArray));
  mDrawerList.setOnItemClickListener(new OnItemClickListener() {

   @Override
   public void onItemClick(AdapterView<?> parent, View view,
     int position, long id) {
       String userId  = SampleModel.getInstance().getCurrentUserId();
       if(userId!=null) {

           openActivityWhenLoggedIn(position);
       }else{
           openActivity(position);
       }
   }
  });
  
  // enable ActionBar app icon to behave as action to toggle nav drawer
  //getActionBar().setDisplayHomeAsUpEnabled(true);
  //getActionBar().setHomeButtonEnabled(true);
  
  actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,0,0){
	  
      @Override
      public void onDrawerOpened(View drawerView) {
          super.onDrawerOpened(drawerView);
          // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
          // open I am not going to put anything here)
      }

      @Override
      public void onDrawerClosed(View drawerView) {
          super.onDrawerClosed(drawerView);
          // Code here will execute once drawer is closed
      }



};
  mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
  actionBarDrawerToggle.syncState();
  

  /**
   * As we are calling BaseActivity from manifest file and this base activity is intended just to add navigation drawer in our app.
   * We have to open some activity with layout on launch. So we are checking if this BaseActivity is called first time then we are opening our first activity.
   * */
  if(isLaunch){
    /**
     *Setting this flag false so that next time it will not open our first activity.
     *We have to use this flag because we are using this BaseActivity as parent activity to our other activity.
     *In this case this base activity will always be call when any child activity will launch.
     */
   isLaunch = false;

   openActivity(3);
  }
 }

    @Override
    protected void onResume() {
        super.onResume();

        String userId  = SampleModel.getInstance().getCurrentUserId();
        if(userId!=null){
            listArray = new String[]{ "My Account","My Tickets", "Home Screen", "Find Movies", "Contact Us","Terms & Conditions" };
                adapter.notifyDataSetChanged();
        }

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
 /**
  * @param position
  *
  * Launching activity when any list item is clicked.
  */
 protected void openActivity(int position) {
  
  /**
   * We can set title & itemChecked here but as this BaseActivity is parent for other activity,
   * So whenever any activity is going to launch this BaseActivity is also going to be called and
   * it will reset this value because of initialization in onCreate method.
   * So that we are setting this in child activity.   
   */
//  mDrawerList.setItemChecked(position, true);
//  setTitle(listArray[position]);
  mDrawerLayout.closeDrawer(mDrawerList);
  BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.
  
  switch (position) {
  case 0:
	  Intent intent = new Intent(this,UserLoginActivity.class);
		startActivity(intent);
   //startActivity(new Intent(this, Item1Activity.class));
   break;
  case 1:
	  startActivity(new Intent(this, UserRegistrationActivity.class));
   break;
  case 2:
	  String userId  = SampleModel.getInstance().getCurrentUserId();
		if(userId==null){
			Toast.makeText(BaseActivity.this, "Please login to see your ticket details ", Toast.LENGTH_SHORT).show();
		}else{
	  startActivity(new Intent(this, MyTickets.class));
		}
   break;
  case 3:
	 // startActivity(new Intent(this, MovieGridActivity.class));
      SampleModel.getInstance().clearNavigationActivities();;
      finish();
   break;
  case 4:
      Intent findMovies = new Intent(this, FindMoviesActivity.class);
      startActivity(findMovies);
	  
   break;
  case 5:

	  startActivity(new Intent(this, ContactUsActivity.class));
   break;
  case 6:
	  startActivity(new Intent(this, TermsActivity.class));
   break;

  default:
   break;
  }
  
 }

    protected void openActivityWhenLoggedIn(int position) {

        /**
         * We can set title & itemChecked here but as this BaseActivity is parent for other activity,
         * So whenever any activity is going to launch this BaseActivity is also going to be called and
         * it will reset this value because of initialization in onCreate method.
         * So that we are setting this in child activity.
         */
//  mDrawerList.setItemChecked(position, true);
//  setTitle(listArray[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                Intent intent = new Intent(this,UserLoginActivity.class);
                startActivity(intent);
                //startActivity(new Intent(this, Item1Activity.class));
                break;
            case 1:
                String userId  = SampleModel.getInstance().getCurrentUserId();
                if(userId==null){
                    Toast.makeText(BaseActivity.this, "Please login to see your ticket details ", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(this, MyTickets.class));
                }
                break;
            case 2:
                // startActivity(new Intent(this, MovieGridActivity.class));
                SampleModel.getInstance().clearNavigationActivities();;
                finish();
                break;
            case 3:
                Intent findMovies = new Intent(this, FindMoviesActivity.class);
                startActivity(findMovies);

                break;
            case 4:

                startActivity(new Intent(this, ContactUsActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, TermsActivity.class));
                break;

            default:
                break;
        }

    }

/* @Override
 public boolean onCreateOptionsMenu(Menu menu) {

  getMenuInflater().inflate(R.menu.main, menu);
  return super.onCreateOptionsMenu(menu);
 }

 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
  
  // The action bar home/up action should open or close the drawer.
  // ActionBarDrawerToggle will take care of this.
  if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
  
  switch (item.getItemId()) {
  case R.id.action_settings:
   return true;
  
  default:
   return super.onOptionsItemSelected(item);
  }
 }
 
  Called whenever we call invalidateOptionsMenu() 
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
  */
    /* We can override onBackPressed method to toggle navigation drawer*/
 @Override
 public void onBackPressed() {
  if(mDrawerLayout.isDrawerOpen(mDrawerList)){
   mDrawerLayout.closeDrawer(mDrawerList);
  }else {
   finish();
  }
 }
 
 
 class CinemaAdress extends ArrayAdapter<String> {
		private Context context;

		public CinemaAdress(Context context, String[] objects) {
			super(context, 0, listArray);
			this.context = context;

		}

     @Override
     public int getCount() {
         return listArray.length;
     }

     @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(), R.layout.row_item_nav,
						null);
				holder = new Holder();
				holder.title = (TextView) convertView
						.findViewById(R.id.txt_details);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			
			
			holder.title.setText(listArray[position]);
			

			return convertView;
		}

		private class Holder {
			private TextView title;
		}

	}
}



