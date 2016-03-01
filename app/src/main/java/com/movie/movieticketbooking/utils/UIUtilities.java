package com.movie.movieticketbooking.utils;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.PDF417;

public class UIUtilities {

	public static void showCustomAlertDialog(Context context, String s) {
		(new android.app.AlertDialog.Builder(context))
				.setTitle("Viva")
				.setMessage(s)
				.setPositiveButton("OK",
						new android.content.DialogInterface.OnClickListener() {

							public void onClick(
									DialogInterface dialoginterface, int i) {
							}

						}).create().show();
	}
	public static void showAlertDialog(Activity context, String s) {
		/*final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.custom_dialog);

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(s);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		
		dialogButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});

		dialog.show();*/
		
		CustomDialogClass cdd=new CustomDialogClass(context,s);
		cdd.show(); 
	  }
	
	
	public static boolean checkEmailValidity(String s)
    {
            boolean flag = false;
            if(s != null)
            {
                if(!Pattern.compile("([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+").matcher(s).matches()){
                flag = true;
                }
            }
            return flag;
    }
	
	 private static void testPDF417(Canvas canvas) throws Exception
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
	        barcode.setData("112233445566");

	        // PDF 417 Error Correction Level
	        barcode.setEcl(PDF417.ECL_2);
	        barcode.setRowCount(30);
	        barcode.setColumnCount(5);
	        barcode.setDataMode(PDF417.M_AUTO);

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
		    RectF bounds = new RectF(30, 30, 0, 0);
	        barcode.drawBarcode(canvas, bounds);
	    }
}
