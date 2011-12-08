package de.gsokoeln.stupid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class STUPIDSettingsActivity extends Activity {

	Spinner spinner = null;
	List<String> classes = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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

	@Override
	public void onStop() {
		System.out.println("Jepp, funktioniert in 2" + classes.get(spinner.getSelectedItemPosition()));
		saveClass(classes.get(spinner.getSelectedItemPosition()));
	}

	
	private void createSpinner(List<String> classes) {
		spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, classes);
		spinner.setAdapter(adapter);
		
		System.out.println("read:" +readClass());
		
		if(readClass().length() > 0 ) {
			int position = getPosOfClass(classes);
			spinner.setSelection(position);
		}
	}
	
	private List<String> getClasses(List<String[]> completeClass) {
		List<String> classes = new ArrayList<String>();
		for(String[]curClassString : completeClass) {
			classes.add(curClassString[0]);
		}
		return classes;
	}
	
	private void saveClass(String savedClassDescriptor)
    {
		SharedPreferences settings = getSharedPreferences("STUPID.prefs", 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString("Class", savedClassDescriptor);

	      // Commit the edits!
	      editor.commit();
    }

	private String readClass()
    {
    	
    	SharedPreferences settings = getSharedPreferences("STUPID.prefs", 0);
        String  klass = settings.getString("Class", "");

		return klass;
    }
	
	public int getPosOfClass(List<String> classes) {
		int i=0;
		for(String curClass : classes) {
			if(curClass.equals(readClass())) {
				return i;
			}
			i++;
		}
		return 0;
	}
}