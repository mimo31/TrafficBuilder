package mainPackage;

public class CityType {
	public long time;
	public String folder;
	
	public CityType(){
		
	}
	
	public void load(String folderName){
		try {
		folder = folderName;
			time = Functions.byteToLong(Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\time.byt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
