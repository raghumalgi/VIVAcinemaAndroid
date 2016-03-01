package com.movie.movieticketbooking.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class LiberationRegular extends TextView {
	private static Typeface tf;
	

    public LiberationRegular(final Context context) {
        super(context);
        style(context);
    }

    public LiberationRegular(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        style(context);
    }

    public LiberationRegular(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        style(context);
    }

    private void style(final Context context) {
    	if(tf == null){
    		tf = Typeface.createFromAsset(context.getAssets(),"fonts/roboto_medium.ttf");
    	}
        setTypeface(tf);
    }
    
    /**
     * apply new style. Font remains same. 
     * @param context
     * @param resId - resource id of the style
     */
    public void setStyle(final Context context, final int resId) {
    	this.setTextAppearance(context, resId);
    	style(context);
    }
}