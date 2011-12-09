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
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class STUPIDSettingsActivity extends Activity {

	Spinner spinner = null;
	List<String> classes = null;
	
	/**
	 * Generelle Initialisierung der Activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * Ausführen des Klassencodes
	 */
	@Override
	public void onStart() {
		super.onStart();
		
		if (!Util.isOnline(this)) {
			Toast.makeText(this, "Kein Internet", Toast.LENGTH_LONG).show();
		} 
		// Laden der Klassen
		else {
			setContentView(R.layout.settings);
			
			URL classURL = null;
			try {
				classURL = new URL("http://www.gso-koeln.de/infos/kalender/stupid/class.txt");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			List<String[]> completeClass = Util.loadDataFromURL(classURL);
			classes = getClasses(completeClass);
			createSpinner(classes);
		}
	}

	/**
	 * impliziertes Speichern beim Verlassen der Activity
	 */
	@Override
	public void onPause() {
		super.onPause();
		
		if (Util.isOnline(this)) {
			Util.setSchoolClass(classes.get(spinner.getSelectedItemPosition()), this);
		}
	}
	
	/**
	 * Füllen des Spinners mit Klassennamen
	 * 
	 * @param classes
	 */
	private void createSpinner(List<String> classes) {
		spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classes);
		spinner.setAdapter(adapter);
				
		if (0 < Util.getSchoolClass(this).length()) {
			int position = getPositionOfSchoolClass(classes);
			spinner.setSelection(position);
		}
	}
	
	/**
	 * Parsen der Klassendatei
	 * 
	 * @param completeClass Inhalt der Klassendatei
	 * @return Klassenliste
	 */
	private List<String> getClasses(List<String[]> completeClass) {
		List<String> classes = new ArrayList<String>();
		for(String[]curClassString : completeClass) {
			classes.add(curClassString[0]);
		}
		
		return classes;
	}
	
	/**
	 * Position der gespeicherten Klasse abfragen
	 * 
	 * @param classes Liste aller Klassen
	 * @return Position der Klasse
	 */
	public int getPositionOfSchoolClass(List<String> classes) {
		int i = 0;
		for (String currentClass : classes) {
			if (currentClass.equals(Util.getSchoolClass(this))) {
				return i;
			}
			
			i++;
		}
		
		return 0;
	}
}