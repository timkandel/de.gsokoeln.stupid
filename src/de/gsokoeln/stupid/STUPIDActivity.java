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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class STUPIDActivity extends Activity {
	/** Called when the activity is first created. */
	
	List<Calendar> startTimes = new ArrayList<Calendar>();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
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
		
		Button next = (Button) findViewById(R.id.button1);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), STUPIDSettingsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

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
		
		
		/*
		URL timeURL = null;
		try {
			timeURL = new URL("http://www.gso-koeln.de/infos/kalender/stupid/time.txt");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String[]> timeData = Util.loadDataFromURL(timeURL);*/
		int currentPeriod = getCurrentPeriod();
		String className = readClass();
		List<String> LessonInformation = getLessonInformation(currentDay, currentWeek, completeSchedule,
				currentPeriod, className);

		
		printLessonInformation(LessonInformation, currentPeriod);
	}

	private void createdHardcodedPeriodValues() {
		Calendar today = new GregorianCalendar();
		startTimes.clear();
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

	private void printLessonInformation(List<String> lessonInformation, int currentPeriod) {
		
	    TextView room = (TextView) findViewById(R.id.textView1);
	    TextView teacher = (TextView) findViewById(R.id.textView2);
	    TextView klass = (TextView) findViewById(R.id.textView3);
	    
	    TextView nextRoom = (TextView) findViewById(R.id.textView4);
	    TextView nextTeacher = (TextView) findViewById(R.id.textView5);
	    TextView nextKlass = (TextView) findViewById(R.id.textView6);
	    
	    if (0 < lessonInformation.size()) {
	    	teacher.setVisibility(teacher.VISIBLE);
	    	klass.setVisibility(klass.VISIBLE);
	    	
	    	if (lessonInformation.get(0).equals(new Integer(currentPeriod).toString())) { 
	    		room.setText("Stunde: " + lessonInformation.get(0));
	    		teacher.setText("Raum: " + lessonInformation.get(1));
	    		klass.setText("Lehrer: " + lessonInformation.get(2));
	    		
	    		if (lessonInformation.size() > 3) {
	    		nextRoom.setText("Stunde: " + lessonInformation.get(3));
	    		nextTeacher.setText("Raum: " + lessonInformation.get(4));
	    		nextKlass.setText("Lehrer: " + lessonInformation.get(5));
	    		}
	    	} else {
	    		room.setText("Stunde: " + lessonInformation.get(3));
	    		teacher.setText("Raum: " + lessonInformation.get(4));
	    		klass.setText("Lehrer: " + lessonInformation.get(5));
	    		
	    		if (lessonInformation.size() > 3) {
	    		nextRoom.setText("Stunde: " + lessonInformation.get(0));
	    		nextTeacher.setText("Raum: " + lessonInformation.get(1));
	    		nextKlass.setText("Lehrer: " + lessonInformation.get(2));
	    		}
	    	}
	    } else {
	    	room.setText("Zur Zeit kein Unterricht");
	    	teacher.setVisibility(teacher.INVISIBLE);
	    	klass.setVisibility(klass.INVISIBLE);
	    }
	}
	
	private List getLessonInformation(int currentDay, int currentWeek,
			List<String[]> completeSchedule, int currentPeriod, String className) {
		
		List<String> lessonInformation = new ArrayList<String>();
		
		for(String[] currentSchedule : completeSchedule) {
			if(currentSchedule[7].equals(className)) {
				if(currentSchedule[8].charAt(currentWeek-1)=='1') {
					if(currentSchedule[1].equals(new Integer(currentDay).toString())) {
						System.out.println(currentSchedule[2] + " = " + new Integer(currentPeriod).toString());
						if(currentSchedule[2].equals(new Integer(currentPeriod).toString())
						|| currentSchedule[2].equals(new Integer(currentPeriod + 1).toString())) {
							lessonInformation.add(currentSchedule[2]); // Welche Stunde
							lessonInformation.add(currentSchedule[4]); // Welcher Raum
							lessonInformation.add(currentSchedule[0]); // Welcher Lehrer
						}
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
			Calendar endTime = (Calendar) curStartTime.clone();
			endTime.add(Calendar.MINUTE, 45);
			if(today.after(curStartTime) && today.before(endTime)) {
					curPeriod=i;
			}
			
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