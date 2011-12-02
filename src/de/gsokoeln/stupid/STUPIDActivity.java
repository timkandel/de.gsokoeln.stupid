package de.gsokoeln.stupid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class STUPIDActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int currentDay = getDayofWeek();
		int currentWeek = getWeekofYear();
		
		URL scheduleURL = null;
		try {
			scheduleURL = new URL("http://www.gso-koeln.de/infos/kalender/stupid/lesson.txt");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String[]> completeSchedule = loadDataFromURL(scheduleURL);
		
		URL timeURL = null;
		try {
			timeURL = new URL("http://www.gso-koeln.de/infos/kalender/stupid/time.txt");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String[]> timeData = loadDataFromURL(timeURL);
		int currentPeriod = getCurrentPeriod(timeData);
		String className = "FIA91";
		List<String> LessonInformation = getLessonInformation(currentDay, currentWeek, completeSchedule,
				currentPeriod, className);

		printLessonInformation(LessonInformation);
	}

	private void printLessonInformation(List<String> lessonInformation) {
	    TextView tv = new TextView(this);
		tv.setText("aktueller raum: " + lessonInformation.get(0) + '\n' +
					"aktueller lehrer: " + lessonInformation.get(1)+ '\n' +
					"aktueller klasse: " + lessonInformation.get(2));
	    setContentView(tv);
	}

	private List getLessonInformation(int currentDay, int currentWeek,
			List<String[]> completeSchedule, int currentPeriod, String className) {
		
		List<String> LessonInformation = new ArrayList<String>();
		
		for(String[] currentSchedule : completeSchedule) {
			if(currentSchedule[7].equals(className)) {
				if(currentSchedule[8].charAt(currentWeek-1)=='1') {
					if(currentSchedule[1].equals(new Integer(currentDay).toString())) {
						if(currentSchedule[2].equals(new Integer(currentPeriod).toString())) {
							LessonInformation.add(currentSchedule[4]);
							LessonInformation.add(currentSchedule[0]);
							LessonInformation.add(currentSchedule[7]);
						}
					}
				}
			}
		}
		
		return LessonInformation;
	}

	private int getCurrentPeriod(List<String[]> timeData) {
		Calendar today = new GregorianCalendar();
		
		for (int i=0; i<16; i++ )
		{
			String[] curHour = (String[]) timeData.get(i);
			int curStartHour = Integer.parseInt(curHour[3].substring(0, 2));
			int curStartMin = Integer.parseInt(curHour[3].substring(2, 4));
			int curStopHour = Integer.parseInt(curHour[4].substring(0, 2));
			int curStopMin = Integer.parseInt(curHour[4].substring(2, 4));
			
			Calendar currentHourStart = new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), curStartHour, curStartMin);
			Calendar currentHourStop = new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), curStopHour, curStopMin);
			
			if(today.after(currentHourStart)) {
				if(today.before(currentHourStop))
					return(Integer.parseInt(curHour[1]));
			}
		}
		return 0;
	}

	private int getWeekofYear() {
		Calendar today = new GregorianCalendar();
		return today.get(Calendar.WEEK_OF_YEAR);
	}

	private int getDayofWeek() {
		Calendar today = new GregorianCalendar();
		return today.get(Calendar.DAY_OF_WEEK)-1;
	}

	private List<String[]> loadDataFromURL(URL source) {
		List<String[]> completeSchedule = null;
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							source.openStream()), '\t');
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
}