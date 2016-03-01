package com.movie.movieticketbooking.activities;

import java.util.Calendar;
import java.util.List;

import com.movie.movieticketbooking.R;
import com.movie.movieticketbooking.activities.FindMoviesActivity.MyAdapter;
import com.movie.movieticketbooking.business.FetchRegisterTask;
import com.movie.movieticketbooking.business.NetworkTask;
import com.movie.movieticketbooking.models.Registration;
import com.movie.movieticketbooking.models.SampleModel;
import com.movie.movieticketbooking.net.Registerparams;
import com.movie.movieticketbooking.utils.MovieParams;
import com.movie.movieticketbooking.utils.UIUtilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserRegistrationActivity extends Activity {
	
	EditText etEmail;
	EditText etPassword;
	EditText etFirstName;
	EditText etLastName;
	EditText etMobileNo;
	EditText etConfirmPassword;
	DatePicker dtDob;
	protected String strEmail;
	protected String strPassword;
	protected String strConfirmPassword;
	protected String strFirstName;
	protected String strLastName;
	protected String strMobile;
	protected String strDob;
	ProgressDialog pd;
	private NetworkTask<Void, Void, Registration> fetchTask;
	private int year;
	private int month;
	private int day;
	private String selectedGender;
	
	private String selectedNationality;
	private TextView dob;
	private String [] nationalities = new String[]{"Arab Expat","Bahraini","GCC","Non Arab Expat","Others"};
	private String [] genderStr = new String[]{"Male","Female"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		etEmail = (EditText) findViewById(R.id.email);
		etPassword = (EditText) findViewById(R.id.pwd);
		etMobileNo = (EditText) findViewById(R.id.mobileNo);
		etFirstName = (EditText) findViewById(R.id.firstname);
		etLastName = (EditText) findViewById(R.id.lastname);
		etConfirmPassword = (EditText) findViewById(R.id.confirmPwd);
	//	dtDob = (DatePicker) findViewById(R.id.dob);
		//findViewById(R.id.location).setVisibility(View.GONE);
		 dob = (TextView) findViewById(R.id.birth);
		dob.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(999);
			}
		});
//		findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(UserRegistrationActivity.this,UserLoginActivity.class);
//				startActivity(i);
//				finish();
//				
//			}
//		});
		setUpViews(findViewById(R.id.register));
		
		final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        final TextView gender = (TextView) findViewById(R.id.gender);
        final MyAdapter myAdapter = new MyAdapter(this, 0, genderStr);
        final NationalityAdapter nationalityAdapter = new NationalityAdapter(this, 0, nationalities);
        gender.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new AlertDialog.Builder(UserRegistrationActivity.this)
				  .setTitle("Gender")
				  .setAdapter(myAdapter, new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog, int position) {
				    	 gender.setText(genderStr[position]);
				    	 selectedGender = genderStr[position];
				      dialog.dismiss();
				    }
				  }).create().show();
				
			}
		});
        final TextView nationality = (TextView) findViewById(R.id.nationality);
        nationality.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new AlertDialog.Builder(UserRegistrationActivity.this)
				  .setTitle("Nationality")
				  .setAdapter(nationalityAdapter, new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog, int position) {
				    	nationality.setText(nationalities[position]);
				    	selectedNationality = nationalities[position];
				      dialog.dismiss();
				    }
				  }).create().show();
				
			}
		});


		
    }  
	
	
	public class MyAdapter extends ArrayAdapter<String> {
		public MyAdapter(Context ctx, int txtViewResourceId, String[] objects) {
			super(ctx, txtViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}
		
		  @Override
	        public int getCount() {
	            return super.getCount(); 
	        }

		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.custom_spiiner, parent,
					false);
			TextView main_text = (TextView) mySpinner
					.findViewById(R.id.txt_details);
			main_text.setText(genderStr[position]);
			return mySpinner;
		}
	}
	
	public class NationalityAdapter extends ArrayAdapter<String> {
		public NationalityAdapter(Context ctx, int txtViewResourceId, String [] objects) {
			super(ctx, txtViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}
		
		  @Override
	        public int getCount() {
	            return super.getCount(); 
	        }

		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.custom_spiiner, parent,
					false);
			TextView main_text = (TextView) mySpinner
					.findViewById(R.id.txt_details);
			main_text.setText(nationalities[position]);
			return mySpinner;
		}
	}
	
    public String getCurrentDate(){  
    	

        StringBuilder builder=new StringBuilder();  
        //builder.append("Current Date: ");  
        builder.append((dtDob.getMonth() + 1)+"/");//month is 0 based  
        builder.append(dtDob.getDayOfMonth()+"/");  
        builder.append(dtDob.getYear());  
        strDob  = builder.toString();
        return builder.toString();  
    }  
		
    
	
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
           // TODO Auto-generated method stub
           // arg1 = year
           // arg2 = month
           // arg3 = day
           showDate(arg1, arg2+1, arg3);
        }
     };
     @Override
     protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
           return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
     }
     
     private void showDate(int year, int month, int day) {
    	 strDob =  new StringBuilder().append(day).append("/")
         .append(month).append("/").append(year).toString();
    	
    	dob.setText(strDob);
    	
        // dateView.setText(new StringBuilder().append(day).append("/")
         //.append(month).append("/").append(year));
    	 
    	 
      }
    
    
	void setUpViews(View btnSubmit){
		 btnSubmit.setOnClickListener(new android.view.View.OnClickListener() {

	            public void onClick(View view4)
	            {
	            	strFirstName = etFirstName.getText().toString();
	                strLastName = etLastName.getText().toString();
	                strPassword = etPassword.getText().toString();
	                strEmail = etEmail.getText().toString();
	                strMobile = etMobileNo.getText().toString();
	                strConfirmPassword = etConfirmPassword.getText().toString();
	                
	                
	                if(strFirstName == null || strFirstName.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Enter Your FirstName");
	                    etFirstName.setText("");
	                    etFirstName.requestFocus();
	                    return;
	                }
	                if(strLastName == null || strLastName.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Enter Your LastName");
	                    etLastName.setText("");
	                    etLastName.requestFocus();
	                    return;
	                }
	                if(selectedGender == null || selectedGender.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Select Gender");
	                    return;
	                }
	                if(selectedNationality == null || selectedNationality.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Select Nationality");
	                    return;
	                }
	                if(strPassword == null || strPassword.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Enter Your Password");
	                    etPassword.setText("");
	                    etPassword.requestFocus();
	                    return;
	                }
	                if(strConfirmPassword == null || strConfirmPassword.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Enter Your Confirm Password");
	                    etPassword.setText("");
	                    etPassword.requestFocus();
	                    return;
	                }
	                if(strConfirmPassword != null && !strConfirmPassword.equals(strPassword))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Password Mismatch");
	                    etConfirmPassword.setText("");
	                    etConfirmPassword.requestFocus();
	                    return;
	                }
	                
	                if(strEmail == null || strEmail.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Enter a Valid Email Address");
	                    etEmail.setText("");
	                    etEmail.requestFocus();
	                    return;
	                }
	                
	                if(strDob == null || strDob.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Enter Your Date Of Birth");
	                    return;
	                }
	                if(strMobile == null || strMobile.contentEquals(""))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Enter a Valid Mobile Number");
	                    etMobileNo.setText("");
	                    etMobileNo.requestFocus();
	                    return;
	                }
	               
	                if(UIUtilities.checkEmailValidity(strEmail))
	                {
	                    UIUtilities.showAlertDialog(UserRegistrationActivity.this, "Please Enter the Correct Email Address");
	                    etEmail.setText("");
	                    etEmail.requestFocus();
	                    return;
	                } else
	                {
	                   registerUser();
	                	
	                }
	            }
	
});
	}
	
	private void registerUser() {
		if (fetchTask != null && !fetchTask.isComplete()) {
			fetchTask.abort();
		}
		fetchTask = createTask();
		fetchTask.setOnCompleteListener(new NetworkTask.OnCompleteListener<Registration>() {
			@Override
			public void onComplete(Registration result) {
				pd.dismiss();
				Toast.makeText(UserRegistrationActivity.this, result.msg, Toast.LENGTH_LONG).show();
				if(result.isValid){
					SampleModel.getInstance().setCurrentUserId(result.userId);
					SampleModel.getInstance().setCurrentUserName(result.userName);
					finish();
				}
				
			}
		});
		fetchTask.setOnGenericExceptionListener(new NetworkTask.OnExceptionListener() {
			@Override
			public void onException(Exception exception) {
				pd.dismiss();
				exception.printStackTrace();
				Toast.makeText(UserRegistrationActivity.this, MovieParams.MESSAGE_TXT, Toast.LENGTH_LONG).show();
			}
		});
		fetchTask.execute();
	}

	
	private NetworkTask<Void, Void,Registration> createTask() {
		pd = new ProgressDialog(this);
		pd.setMessage("Registering...");
		pd.setCancelable(false);
		pd.show();
		Registerparams params = new Registerparams();
		params.url =MovieParams.REGISTRATION_API; 
		params.firstName =strFirstName;
		params.email =strEmail;
		params.pwd =strPassword;
		params.mobile =strMobile;
		params.lastName =strLastName;
		params.dob =strDob;
		params.gender =selectedGender;
		params.nationality =selectedNationality;
		return new FetchRegisterTask(params);
	}
	

}


