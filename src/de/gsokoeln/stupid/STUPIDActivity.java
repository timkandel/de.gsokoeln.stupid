package de.gsokoeln.stupid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class STUPIDActivity extends Activity {
	/** Called when the activity is first created. */
	
	List<Calendar> startTimes = new ArrayList<Calendar>();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		createdHardcodedPeriodValues();
		
		System.out.println("read in 1:" +readClass());
		if(readClass().length() == 0 ) {
			setContentView(R.layout.settings);
			Intent myIntent = new Intent(this, STUPIDSettingsActivity.class);
            startActivityForResult(myIntent, 0);	
		}
		else {
			System.out.println("read else in 1:" +readClass());
		}

		setContentView(R.layout.main);

		int currentDay = getDayofWeek();
		int currentWeek = getWeekofYear();
		
	    
		URL scheduleURL = null;
		try {
			scheduleURL = new URL("http://www.gso-koeln.de/infos/kalender/stupid/lesson.txt");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String[]> completeSchedule = Util.loadDataFromURL(scheduleURL);
		
		
		
		URL timeURL = null;
		try {
			timeURL = new URL("http://www.gso-koeln.de/infos/kalender/stupid/time.txt");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String[]> timeData = Util.loadDataFromURL(timeURL);
		int currentPeriod = getCurrentPeriod();
		String className = readClass();
		List<String> LessonInformation = getLessonInformation(currentDay, currentWeek, completeSchedule,
				currentPeriod, className);

		
		printLessonInformation(LessonInformation);
	}

	private void createdHardcodedPeriodValues() {
		Calendar today = new GregorianCalendar();
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 7, 45));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 8, 30));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 9, 35));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 10, 20));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 11, 25));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 12, 10));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 13, 15));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 14, 00));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 15, 05));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 15, 50));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 16, 55));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 17, 40));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 18, 45));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 19, 30));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 20, 35));
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 21, 20));
	}

	private void printLessonInformation(List<String> lessonInformation) {
		
	    TextView room = (TextView) findViewById(R.id.textView1);
	    TextView teacher = (TextView) findViewById(R.id.textView2);
	    TextView klass = (TextView) findViewById(R.id.textView3);

//	    room.setText(lessonInformation.get(0));
//	    teacher.setText(lessonInformation.get(1));
//	    klass.setText(lessonInformation.get(2));
	    
	}
	
	private List getLessonInformation(int currentDay, int currentWeek,
			List<String[]> completeSchedule, int currentPeriod, String className) {
		
		List<String> lessonInformation = new ArrayList<String>();
		
		for(String[] currentSchedule : completeSchedule) {
			if(currentSchedule[7].equals(className)) {
				if(currentSchedule[8].charAt(currentWeek-1)=='1') {
					if(currentSchedule[1].equals(new Integer(currentDay).toString())) {
						lessonInformation.add(currentSchedule[1]); // Welche Stunde
						lessonInformation.add(currentSchedule[4]); // Welcher Raum
						lessonInformation.add(currentSchedule[0]); // Welcher Lehrer
					}
				}
			}
		}
		
		return lessonInformation;
	}
	
	private int getCurrentPeriod() {
		Calendar today = new GregorianCalendar();
		
		int curPeriod=0;
		int i=1;
		for (Calendar curStartTime : startTimes)
		{
			Calendar endTime = curStartTime;
			endTime.add(Calendar.MINUTE, 45);
			if(today.after(curStartTime) && today.before(endTime)) {
					curPeriod=i;
			}
			//if(curStartTime.after(today) && )
			
			i++;
		}
		return curPeriod;
	}
	
	

	private int getWeekofYear() {
		Calendar today = new GregorianCalendar();
		return today.get(Calendar.WEEK_OF_YEAR);
	}

	
	// Ermittelt den Wochentag, z.b. Donnerstag = 4
	private int getDayofWeek() {
		Calendar today = new GregorianCalendar();
		return today.get(Calendar.DAY_OF_WEEK)-1;
	}

	private String readClass()
    {
    	SharedPreferences settings = getSharedPreferences("STUPID.prefs", 0);
        String  klass = settings.getString("Class", "");

		return klass;
    }
	
}