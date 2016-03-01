package com.movie.movieticketbooking;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.PDF417;

public class AndroidBarcodeView extends View
{

	public String data;
	private Canvas canvas;
	
	
	public AndroidBarcodeView(Context context) {
	    super(context);
	    init(context);
	}

	public AndroidBarcodeView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    init(context);
	}

	public AndroidBarcodeView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    init(context);
	}

	private void init(Context context) {
	    //do stuff that was in your original constructor...
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		this.canvas = canvas;
		try {
			testPDF417(canvas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 public  void testPDF417(Canvas canvas) throws Exception
	    {
	        PDF417 barcode = new PDF417();
	      

	        /*
	           PDF-417 Valid data char set:
	               1. Text Compaction mode permits all printable ASCII characters to be encoded,
	                i.e. values 32 - 126 inclusive in accordance with ISO/IEC 646 (IRV),
	                as well as selected control characters.
	               2. Byte Compaction mode permits all 256 possible 8-bit byte values to be encoded.
	                This includes all ASCII characters value 0 to 127 inclusive and provides for international character set support.
	               3. Numeric Compaction mode permits efficient encoding of numeric data strings.
	               4. Up to 811 800 different character sets or data interpretations.
	               5. Various function codewords for control purposes.
	        */
	        Log.d("Movie","Barcode "+data);
	        barcode.setData(data);
	    //    barcode.setData("29467181");
	      //  barcode.setData("112233445566");
	        // PDF 417 Error Correction Level
	        barcode.setEcl(PDF417.ECL_2);
	        barcode.setRowCount(90);
	        barcode.setColumnCount(10);
	        barcode.setDataMode(PDF417.M_AUTO);
	       // barcode.setBarcodeHeight(f);
	        //barcode.setBarcodeWidth(200f);
	        barcode.setTruncated(false);

	        //  Set the processTilde property to true, if you want use the tilde character "~" to specify special characters in the input data. Default is false.
	        //  1-byte character: ~ddd (character value from 0 ~ 255)
	        //  ASCII (with EXT): from ~000 to ~255
	        //  2-byte character: ~6ddddd (character value from 0 ~ 65535)
	        //  Unicode: from ~600000 to ~665535
	        //  ECI: from ~7000000 to ~7999999
	        barcode.setProcessTilde(true);

	        /*
	        // for macro PDF 417
	        barcode.setMacro(false);
	        barcode.setMacroSegmentIndex(0);
	        barcode.setMacroSegmentCount(0);
	        barcode.setMacroFileIndex(0);
	        */

	        // unit of measure for X, Y, LeftMargin, RightMargin, TopMargin, BottomMargin
	        barcode.setUom(IBarcode.UOM_PIXEL);
	        // barcode module width in pixel
	        barcode.setX(1f);
	        barcode.setXtoYRatio(0.3f);

	        barcode.setLeftMargin(10f);
	        barcode.setRightMargin(10f);
	        barcode.setTopMargin(10f);
	        barcode.setBottomMargin(10f);
	        // barcode image resolution in dpi
	        barcode.setResolution(72);
	        
	        // barcode bar color and background color in Android device
	        barcode.setForeColor(AndroidColor.black);
	        barcode.setBackColor(AndroidColor.white);

	        /*
	        specify your barcode drawing area
		    */
		    RectF bounds = new RectF(10, 10, 90,90);
	        barcode.drawBarcode(canvas, bounds);
	    }
}
