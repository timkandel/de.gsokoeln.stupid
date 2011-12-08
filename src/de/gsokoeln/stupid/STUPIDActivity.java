/**
 * @author Sebastian Drewke
 * @author Tim Kandel
 * @author Viktor Hamm
 */
package de.gsokoeln.stupid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class STUPIDActivity extends Activity {
	
	List<Calendar> startTimes = new ArrayList<Calendar>();
	
	/**
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		setContentView(R.layout.main);
		Button preferencesButton = (Button)findViewById(R.id.preferencesButton);
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), STUPIDSettingsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        Button reloadButton = (Button)findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                displaySchedule();
            }
        });
	}
	
	/**
	 * 
	 */
	@Override
	public void onStart() {
		super.onStart();
		
		if (0 == Util.getSchoolClass(this).length()) {
			setContentView(R.layout.settings);
			
			Intent myIntent = new Intent(this, STUPIDSettingsActivity.class);
            startActivityForResult(myIntent, 0);	
		} else {
			Button reloadButton = (Button)findViewById(R.id.reloadButton);
			Button preferencesButton = (Button)findViewById(R.id.preferencesButton);
			ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
			
			reloadButton.setClickable(false);
			preferencesButton.setClickable(false);
			Util.showProgressBar(progressBar);
			
			displaySchedule();
		}
	}
	
	/**
	 * 
	 */
	private void displaySchedule() {
		createdHardcodedPeriodStartTimes();
		int currentDay = Util.getDayOfWeek();
		int currentWeek = Util.getWeekOfYear();
		
		URL scheduleURL = null;
		try {
			scheduleURL = new URL("http://www.gso-koeln.de/infos/kalender/stupid/lesson.txt");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		List<String[]> completeSchedule = Util.loadDataFromURL(scheduleURL);
		
		int currentPeriod = getCurrentPeriod();
		String className = Util.getSchoolClass(this);
		List<String> LessonInformation = getLessonInformation(currentDay, currentWeek, completeSchedule, currentPeriod, className);
		
		printLessonInformation(LessonInformation, currentPeriod);
	}

	/**
	 * 
	 */
	private void createdHardcodedPeriodStartTimes() {
		Calendar today = new GregorianCalendar();
		
		startTimes.clear();
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 7, 45));		// 1. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 8, 30));		// 2. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 9, 35));		// 3. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 10, 20));		// 4. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 11, 25));		// 5. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 12, 10));		// 6. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 13, 15));		// 7. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 14, 00));		// 8. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 15, 05));		// 9. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 15, 50));		// 10. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 16, 55));		// 11. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 17, 40));		// 12. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 18, 45));		// 13. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 19, 30));		// 14. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 20, 35));		// 15. Stunde
		startTimes.add(new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 21, 20));		// 16. Stunde
	}

	/**
	 * 
	 * @param lessonInformation
	 * @param currentPeriod
	 */
	private void printLessonInformation(List<String> lessonInformation, int currentPeriod) {
		Button reloadButton = (Button)findViewById(R.id.reloadButton);
		Button preferencesButton = (Button)findViewById(R.id.preferencesButton);
		ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
		
		reloadButton.setClickable(true);
		preferencesButton.setClickable(true);
		Util.hideProgressBar(progressBar);
	    
	    if (0 < lessonInformation.size()) {
	    	String currentRoom = "---";
    		String currentTeacher = "---";
    		String currentPeriodNumber = "---";
    		String currentSubject = "---";
    		
    		String nextRoom = "---";
    		String nextTeacher = "---";
    		String nextPeriodNumber = "---";
    		String nextSubject = "---";
	    	
	    	if (lessonInformation.get(0).equals(new Integer(currentPeriod).toString())) {
	    		currentRoom = lessonInformation.get(1);
	    		currentTeacher = lessonInformation.get(2);
	    		currentPeriodNumber = lessonInformation.get(0);
	    		currentSubject = lessonInformation.get(3);
	    		
	    		if (lessonInformation.size() > 4) {
	    			nextRoom = lessonInformation.get(5);
		    		nextTeacher = lessonInformation.get(6);
		    		nextPeriodNumber = lessonInformation.get(4);
		    		nextSubject = lessonInformation.get(7);
	    		} else {
	    			hideNextLesson();
	    		}
	    	} else {
	    		currentRoom = lessonInformation.get(5);
	    		currentTeacher = lessonInformation.get(6);
	    		currentPeriodNumber = lessonInformation.get(4);
	    		currentSubject = lessonInformation.get(7);
	    		
    			nextRoom = lessonInformation.get(1);
	    		nextTeacher = lessonInformation.get(2);
	    		nextPeriodNumber = lessonInformation.get(0);
	    		nextSubject = lessonInformation.get(3);
	    	}
	    	
	    	printCurrentLesson(currentRoom, currentTeacher, currentPeriodNumber, currentSubject);
	    	printNextLesson(nextRoom, nextTeacher, nextPeriodNumber, nextSubject);
	    } else {
		    hideCurrentLesson();
	    }
	}
	
	/**
	 * 
	 */
	private void hideCurrentLesson() {
		TextView currentRoomTextView = (TextView) findViewById(R.id.currentRoomTextView);
	    TextView currentTeacherTextView = (TextView) findViewById(R.id.currentTeacherTextView);
	    TextView currentPeriodTextView = (TextView) findViewById(R.id.currentPeriodTextView);
	    TextView currentSubjectTextView = (TextView) findViewById(R.id.currentSubjectTextView);
	    
	    currentRoomTextView.setText("Zur Zeit kein Unterricht");
    	Util.hideTextView(currentPeriodTextView);
    	Util.hideTextView(currentTeacherTextView);
    	Util.hideTextView(currentSubjectTextView);
    	
    	hideNextLesson();
	}
	
	/**
	 * 
	 */
	private void hideNextLesson() {
		TextView spacerTextView = (TextView) findViewById(R.id.spacerTextView);
	    TextView nextRoomTextView = (TextView) findViewById(R.id.nextRoomTextView);
	    TextView nextTeacherTextView = (TextView) findViewById(R.id.nextTeacherTextView);
	    TextView nextPeriodTextView = (TextView) findViewById(R.id.nextPeriodTextView);
	    TextView nextSubjectTextView = (TextView) findViewById(R.id.nextSubjectTextView);
		
		Util.hideTextView(spacerTextView);
    	Util.hideTextView(nextRoomTextView);
    	Util.hideTextView(nextTeacherTextView);
    	Util.hideTextView(nextPeriodTextView);
    	Util.hideTextView(nextSubjectTextView);
	}
	
	/**
	 * 
	 * @param room
	 * @param teacher
	 * @param period
	 * @param subject
	 */
	private void printCurrentLesson(String room, String teacher, String period, String subject) {
		TextView currentRoomTextView = (TextView) findViewById(R.id.currentRoomTextView);
	    TextView currentTeacherTextView = (TextView) findViewById(R.id.currentTeacherTextView);
	    TextView currentPeriodTextView = (TextView) findViewById(R.id.currentPeriodTextView);
	    TextView currentSubjectTextView = (TextView) findViewById(R.id.currentSubjectTextView);
	    
	    currentRoomTextView.setText("Raum: " + room);
		currentPeriodTextView.setText(period + ". Stunde");
		currentSubjectTextView.setText("Fach: " + subject);
		currentTeacherTextView.setText("Lehrer: " + teacher);
	}
	
	/**
	 * 
	 * @param room
	 * @param teacher
	 * @param period
	 * @param subject
	 */
	private void printNextLesson(String room, String teacher, String period, String subject) {
		TextView nextRoomTextView = (TextView) findViewById(R.id.nextRoomTextView);
	    TextView nextTeacherTextView = (TextView) findViewById(R.id.nextTeacherTextView);
	    TextView nextPeriodTextView = (TextView) findViewById(R.id.nextPeriodTextView);
	    TextView nextSubjectTextView = (TextView) findViewById(R.id.nextSubjectTextView);
	    
	    nextRoomTextView.setText("Raum: " + room);
		nextPeriodTextView.setText(period + ". Stunde");
		nextSubjectTextView.setText("Fach: " + subject);
		nextTeacherTextView.setText("Lehrer: " + teacher);
	}
	
	/**
	 * 
	 * @param currentDay
	 * @param currentWeek
	 * @param completeSchedule
	 * @param currentPeriod
	 * @param className
	 * @return
	 */
	private List<String> getLessonInformation(int currentDay, int currentWeek, List<String[]> completeSchedule, int currentPeriod, String className) {
		List<String> lessonInformation = new ArrayList<String>();
				
		if (0 != currentPeriod) {
			for (String[] currentSchedule : completeSchedule) {
				// Überprüfe Klasse
				if (currentSchedule[7].equals(className)) {
					// Überprüfe Woche
					if (currentSchedule[8].charAt(currentWeek - 1) == '1') {
						// Überprüfe Tag
						if (currentSchedule[1].equals(new Integer(currentDay).toString())) {
							// Überprüfe Stunde
							if (currentSchedule[2].equals(new Integer(currentPeriod).toString())
							|| currentSchedule[2].equals(new Integer(currentPeriod + 1).toString())) {
								lessonInformation.add(currentSchedule[2]); 		// Welche Stunde
								lessonInformation.add(currentSchedule[4]); 		// Welcher Raum
								lessonInformation.add(currentSchedule[0]); 		// Welcher Lehrer
								lessonInformation.add(currentSchedule[3]);		// Welches Fach
								//lessonInformation.add(currentSchedule[6]);		// Stundenplanänderung ?
							}
						}
					}
				}
			}
		}
		
		return lessonInformation;
	}
	
	/**
	 * 
	 * @return
	 */
	private int getCurrentPeriod() {
		Calendar today = new GregorianCalendar();
		today = new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 16, 25);
		
		int currentPeriod = 0;
		int i = 1;
		for (Calendar currentStartTime : startTimes) {
			Calendar endTime = (Calendar) currentStartTime.clone();
			endTime.add(Calendar.MINUTE, 45);
			
			if (((today.after(currentStartTime) && today.before(endTime)) || (today.equals(currentStartTime) && today.before(endTime))) || today.before(currentStartTime)) {
				currentPeriod = i;
				break;
			}
			
			i++;
		}
		
		return currentPeriod;
	}
}