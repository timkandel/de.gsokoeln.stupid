/**
 * @author Georg Rode
 * @author Matthias Nagler
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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class STUPIDActivity extends Activity {
	
	List<Calendar> startTimes = new ArrayList<Calendar>();
	
	/**
	 * Generelle Initialisierung der Activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Einstellungsseite bei Klick auf "Einstellungen"-Button anzeigen
		setContentView(R.layout.main);
		Button preferencesButton = (Button)findViewById(R.id.preferencesButton);
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), STUPIDSettingsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        // Daten bei Klick auf "Neu laden"-Button neu laden
        Button reloadButton = (Button)findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                displaySchedule();
            }
        });
	}
	
	/**
	 * Ausführen des Klassencodes
	 */
	@Override
	public void onStart() {
		super.onStart();
		
		// Zeige Einstellungsseite wenn keine Klasse gewählt ist
		if (0 == Util.getSchoolClass(this).length()) {
			setContentView(R.layout.settings);
			
			Intent myIntent = new Intent(this, STUPIDSettingsActivity.class);
            startActivityForResult(myIntent, 0);
		} 
		// Überprüfe ob Internetverbindung vorhanden ist und zeige dann Stundenplan
		else {
			if (!Util.isOnline(this)) {
				Toast.makeText(this, "Kein Internet", Toast.LENGTH_LONG).show();
			} else {
				displaySchedule();
			}
		}
	}
	
	/**
	 * Rückgabe der Activity (Hilfsmethode für Thread)
	 * 
	 * @return Activity
	 */
	private Activity getActivity() {
		return this;
	}
	
	/**
	 * GUI sperren und Laden / Ausführen in die Wege leiten
	 */
	private void displaySchedule() {
		// Hole Buttons und ProgressBar / Buttons sperren und ProgressBar anzeigen
		Button reloadButton = (Button)findViewById(R.id.reloadButton);
		Button preferencesButton = (Button)findViewById(R.id.preferencesButton);
		ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
		
		reloadButton.setClickable(false);
		preferencesButton.setClickable(false);
		Util.showProgressBar(progressBar);
		
		// Generiere Zeiten und lade Thread zum asynchronen Ausführen des Ladens / Auswertens der Daten
		createdHardcodedPeriodStartTimes();
		new DisplayScheduleTask().execute();
	}

	/**
	 * Generieren der Schulzeiten
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
	 * Auswerten der anzuzeigenden Stunden
	 * 
	 * @param lessonInformation Liste der anzuzeigenden Stunden
	 * @param currentPeriod Nummer der aktuellen Stunde
	 */
	private void printLessonInformation(List<String> lessonInformation, int currentPeriod) {
		// Hole Buttons und ProgressBar / Buttons freigeben und ProgressBar verstecken 
		Button reloadButton = (Button)findViewById(R.id.reloadButton);
		Button preferencesButton = (Button)findViewById(R.id.preferencesButton);
		ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
		
		reloadButton.setClickable(true);
		preferencesButton.setClickable(true);
		Util.hideProgressBar(progressBar);
	    
		// Wenn Stunden vorhanden sind
	    if (0 < lessonInformation.size()) {
	    	String currentRoom = "---";
    		String currentTeacher = "---";
    		String currentPeriodNumber = "---";
    		String currentSubject = "---";
    		boolean currentLessonIsChanged = false;
    		
    		String nextRoom = "---";
    		String nextTeacher = "---";
    		String nextPeriodNumber = "---";
    		String nextSubject = "---";
    		boolean nextLessonIsChanged = false;
	    	
    		// Wenn die aktuelle Stunde an erster Position ist
	    	if (lessonInformation.get(0).equals(new Integer(currentPeriod).toString())) {
	    		currentRoom = lessonInformation.get(1);
	    		currentTeacher = lessonInformation.get(2);
	    		currentPeriodNumber = lessonInformation.get(0);
	    		currentSubject = lessonInformation.get(3);
	    		if (lessonInformation.get(4).equals("2"))
	    			currentLessonIsChanged = true;
	    		
	    		// Wenn es eine weitere Stunde gibt
	    		if (lessonInformation.size() > 5) {
	    			nextRoom = lessonInformation.get(6);
		    		nextTeacher = lessonInformation.get(7);
		    		nextPeriodNumber = lessonInformation.get(5);
		    		nextSubject = lessonInformation.get(8);
		    		if (lessonInformation.get(9).equals("2"))
		    			nextLessonIsChanged = true;
	    		} else {
	    			hideNextLesson();
	    		}
	    	} 
	    	// Wenn aktuelle Stunde an 2ter Stelle ist
	    	else {
	    		currentRoom = lessonInformation.get(6);
	    		currentTeacher = lessonInformation.get(7);
	    		currentPeriodNumber = lessonInformation.get(5);
	    		currentSubject = lessonInformation.get(8);
	    		if (lessonInformation.get(9).equals("2"))
	    			currentLessonIsChanged = true;
	    		
    			nextRoom = lessonInformation.get(1);
	    		nextTeacher = lessonInformation.get(2);
	    		nextPeriodNumber = lessonInformation.get(0);
	    		nextSubject = lessonInformation.get(3);
	    		if (lessonInformation.get(4).equals("2"))
	    			nextLessonIsChanged = true;
	    	}
	    	
	    	// Stunden ausgeben
	    	printCurrentLesson(currentRoom, currentTeacher, currentPeriodNumber, currentSubject, currentLessonIsChanged);
	    	printNextLesson(nextRoom, nextTeacher, nextPeriodNumber, nextSubject, nextLessonIsChanged);
	    } else {
		    hideCurrentLesson();
	    }
	}
	
	/**
	 * versteckt die aktuelle Stunde, wenn kein Unterricht ist 
	 */
	private void hideCurrentLesson() {
		TextView currentRoomTextView = (TextView) findViewById(R.id.currentRoomTextView);
	    TextView currentTeacherTextView = (TextView) findViewById(R.id.currentTeacherTextView);
	    TextView currentPeriodTextView = (TextView) findViewById(R.id.currentPeriodTextView);
	    TextView currentSubjectTextView = (TextView) findViewById(R.id.currentSubjectTextView);
	    
	    currentRoomTextView.setTextColor(Color.WHITE);
	    
	    currentRoomTextView.setText("Zur Zeit kein Unterricht");
    	Util.hideTextView(currentPeriodTextView);
    	Util.hideTextView(currentTeacherTextView);
    	Util.hideTextView(currentSubjectTextView);
    	
    	hideNextLesson();
	}
	
	/**
	 * versteckt die nächste Stunde, wenn kein Unterricht ist
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
	 * Ausgeben der aktuellen Stunde
	 * 
	 * @param room Raumkürzel
	 * @param teacher Lehrerkürzel
	 * @param period Nummer der Stunde
	 * @param subject Fachkürzel
	 */
	private void printCurrentLesson(String room, String teacher, String period, String subject, boolean lessonIsChanged) {
		TextView currentRoomTextView = (TextView) findViewById(R.id.currentRoomTextView);
	    TextView currentTeacherTextView = (TextView) findViewById(R.id.currentTeacherTextView);
	    TextView currentPeriodTextView = (TextView) findViewById(R.id.currentPeriodTextView);
	    TextView currentSubjectTextView = (TextView) findViewById(R.id.currentSubjectTextView);
	    
	    currentRoomTextView.setText("Raum: " + room);
		currentPeriodTextView.setText(period + ". Stunde");
		currentSubjectTextView.setText("Fach: " + subject);
		currentTeacherTextView.setText("Lehrer: " + teacher);
		
		if (lessonIsChanged) {
			currentRoomTextView.setTextColor(Color.RED);
			currentTeacherTextView.setTextColor(Color.RED);
			currentSubjectTextView.setTextColor(Color.RED);
			currentPeriodTextView.setTextColor(Color.RED);
		} else {
			currentRoomTextView.setTextColor(Color.WHITE);
			currentTeacherTextView.setTextColor(Color.WHITE);
			currentSubjectTextView.setTextColor(Color.WHITE);
			currentPeriodTextView.setTextColor(Color.WHITE);
		}
		
		Util.showTextView(currentPeriodTextView);
    	Util.showTextView(currentTeacherTextView);
    	Util.showTextView(currentSubjectTextView);
	}
	
	/**
	 * Ausgeben der nächsten Stunde
	 * 
	 * @param room Raumkürzel
	 * @param teacher Lehrerkürzel
	 * @param period Nummer der Stunde
	 * @param subject Fachkürzel
	 */
	private void printNextLesson(String room, String teacher, String period, String subject, boolean lessonIsChanged) {
		TextView spacerTextView = (TextView)findViewById(R.id.spacerTextView);
		TextView nextRoomTextView = (TextView) findViewById(R.id.nextRoomTextView);
	    TextView nextTeacherTextView = (TextView) findViewById(R.id.nextTeacherTextView);
	    TextView nextPeriodTextView = (TextView) findViewById(R.id.nextPeriodTextView);
	    TextView nextSubjectTextView = (TextView) findViewById(R.id.nextSubjectTextView);
	    
	    nextRoomTextView.setText("Raum: " + room);
		nextPeriodTextView.setText(period + ". Stunde");
		nextSubjectTextView.setText("Fach: " + subject);
		nextTeacherTextView.setText("Lehrer: " + teacher);
		
		if (lessonIsChanged) {
			nextRoomTextView.setTextColor(Color.RED);
			nextTeacherTextView.setTextColor(Color.RED);
			nextSubjectTextView.setTextColor(Color.RED);
			nextPeriodTextView.setTextColor(Color.RED);
		} else {
			nextRoomTextView.setTextColor(Color.WHITE);
			nextTeacherTextView.setTextColor(Color.WHITE);
			nextSubjectTextView.setTextColor(Color.WHITE);
			nextPeriodTextView.setTextColor(Color.WHITE);
		}
		
		Util.showTextView(spacerTextView);
    	Util.showTextView(nextRoomTextView);
    	Util.showTextView(nextTeacherTextView);
    	Util.showTextView(nextPeriodTextView);
    	Util.showTextView(nextSubjectTextView);
	}
	
	/**
	 * Auswerten der lesson.txt auf anzuzeigende Stunden
	 * 
	 * @param currentDay Nummer des aktuellen Tages
	 * @param currentWeek Kalenderwoche
	 * @param completeSchedule Gesamter Stundenplan
	 * @param currentPeriod Nummer der aktuellen Stunde
	 * @param className Klassenkürzel
	 * @return anzuzeigende Stunden
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
								lessonInformation.add(currentSchedule[6]);		// Stundenplanänderung
							}
						}
					}
				}
			}
		}
		
		return lessonInformation;
	}
	
	/**
	 * Überprüfen welche Schulstunde gerade läuft bzw. als nächstes sofern Pause / vor Unterrichtsbeginn
	 * 
	 * @return Nummer der aktuellen Stunde
	 */
	private int getCurrentPeriod() {
		Calendar today = new GregorianCalendar();
		
		int currentPeriod = 0;
		int i = 1;
		for (Calendar currentStartTime : startTimes) {
			// Ende der Stunde auf Start + 45 Minuten setzen
			Calendar endTime = (Calendar) currentStartTime.clone();
			endTime.add(Calendar.MINUTE, 45);
			
			// aktuelle Stunde ist wenn: Uhrzeit zwischen Stundenbeginn und Stundenende oder Uhrzeit gleich Stundenbeginn
			// sollte beides nicht eintreffen ist es die aktuelle Stunde falls Uhrzeit vor Stundenbeginn (Pause oder vor 1. Stunde)
			if (((today.after(currentStartTime) && today.before(endTime)) || today.equals(currentStartTime)) || today.before(currentStartTime)) {
				currentPeriod = i;
				break;
			}
			
			i++;
		}
		
		return currentPeriod;
	}
	
	/**
	 * Thread zum asynchronen Aufrufen und Auswerten des Stundenplans
	 */
	private class DisplayScheduleTask extends AsyncTask<Void, Void, List<String>> {
		
		/**
		 * Laden und Auswerten des Stundenplans
		 */
	     protected List<String> doInBackground(Void... voids) {
	 		int currentDay = Util.getDayOfWeek();
	 		int currentWeek = Util.getWeekOfYear();
	 		
	 		// laden der Stundenplan Datei
	 		URL scheduleURL = null;
	 		try {
	 			scheduleURL = new URL("http://www.gso-koeln.de/infos/kalender/stupid/lesson.txt");
	 		} catch (MalformedURLException e) {
	 			e.printStackTrace();
	 		}
	 		List<String[]> completeSchedule = Util.loadDataFromURL(scheduleURL);
	 		
	 		// auswerten
	 		int currentPeriod = getCurrentPeriod();
			String className = Util.getSchoolClass(getActivity());
			List<String> lessonInformation = getLessonInformation(currentDay, currentWeek, completeSchedule, currentPeriod, className);
	 		
	 		return lessonInformation;
	     }

	     /**
	      * Ausgabe des Stundenplans
	      */
	     protected void onPostExecute(List<String> result) {
	    	 printLessonInformation(result, getCurrentPeriod());
	     }
	 }
}