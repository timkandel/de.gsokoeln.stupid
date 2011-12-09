/**
 * @author Georg Rode
 * @author Matthias Nagler
 * @author Sebastian Drewke
 * @author Tim Kandel
 * @author Viktor Hamm
 */
package de.gsokoeln.stupid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Util {

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static List<String[]> loadDataFromURL(URL source) {
		List<String[]> completeSchedule = null;
		try {
			BufferedReader in = new BufferedReader(
				new InputStreamReader(source.openStream()), '\t');
			String inputLine;
		    
		    completeSchedule = new ArrayList<String[]>();
			while ((inputLine = in.readLine()) != null)
		    		completeSchedule.add(inputLine.split("\t")); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return completeSchedule;
	}
	
	/**
	 * 
	 * @param textView
	 */
	public static void hideTextView(TextView textView) {
		//textView.setHeight(0);
		textView.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 
	 * @param textView
	 */
	public static void showTextView(TextView textView) {
		//textView.setHeight(pixels);
		textView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 
	 * @param progressBar
	 */
	public static void hideProgressBar(ProgressBar progressBar) {
		progressBar.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 
	 * @param progressBar
	 */
	public static void showProgressBar(ProgressBar progressBar) {
		progressBar.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static String getSchoolClass(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences("STUPID.prefs", 0);
        String schoolClass = settings.getString("SchoolClass", "");

		return schoolClass;
	}
	
	/**
	 * 
	 * @param schoolClass
	 * @param activity
	 */
	public static void setSchoolClass(String schoolClass, Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences("STUPID.prefs", 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString("SchoolClass", schoolClass);

    	editor.commit();
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getWeekOfYear() {
		Calendar today = new GregorianCalendar();
		return today.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getDayOfWeek() {
		Calendar today = new GregorianCalendar();
		return today.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * 
	 * @return
	 */
	public static boolean isOnline(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (null != netInfo && netInfo.isConnected()) {
			return true;
		}
		
		return false;
	}
}
