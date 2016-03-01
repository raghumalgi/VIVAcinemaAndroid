package com.movie.movieticketbooking.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.movie.movieticketbooking.R;

public class CustomDialogClass extends Dialog implements
    android.view.View.OnClickListener {

  public Activity c;
  public Dialog d;
  public String  text;
  public Button yes, no;

  public CustomDialogClass(Activity a,String text) {
    super(a);
    // TODO Auto-generated constructor stub
    this.c = a;
    this.text = text;
    getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.custom_dialog);
    TextView textview = (TextView) findViewById(R.id.text);
	textview.setText(text);
    yes = (Button) findViewById(R.id.dialogButtonOK);
    yes.setOnClickListener(this);
    
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.dialogButtonOK:
      dismiss();
      break;
    default:
      break;
    }
    dismiss();
  }
}