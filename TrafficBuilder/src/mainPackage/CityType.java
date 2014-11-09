package mainPackage;

public class CityType {
	public long time;
	public String name;
	
	public CityType(String cityName){
		this.name = cityName;
		this.time = 0;
	}
	
	public CityType load(String folderName) throws Exception{
		return new CityType(Functions.byteToLong(Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\time.byt")),
					Functions.readTextFile(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "name.txt"));
	}
	
	protected CityType(long cityTime, String cityName){
		this.time = cityTime;
		this.name = cityName;
	}
}
