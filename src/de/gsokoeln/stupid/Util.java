package de.gsokoeln.stupid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Util {

	public static List<String[]> loadDataFromURL(URL source) {
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
