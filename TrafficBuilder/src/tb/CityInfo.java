package tb;

import java.util.Calendar;

public class CityInfo {
	
	public String name;
	public String folder;
	public Calendar lastPlayed;
	
	public CityInfo(String name, String folder, Calendar lastPlayed){
		this.name = name;
		this.folder = folder;
		this.lastPlayed = lastPlayed;
	}
	
}
