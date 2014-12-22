package screens.City;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

import mainPackage.Functions;

public class CityType {
	public long time;
	public String name;
	public String folderName;
	public Point mapPosition;
	chunk[] Chunks = new chunk[0]; 
	
	protected void addChunk(chunk theChunk){
		chunk[] temp = new chunk[Chunks.length + 1];
		int counter = 0;
		while(counter < Chunks.length){
			temp[counter] = Chunks[counter];
			counter++;
		}
		temp[counter] = theChunk;
		Chunks = temp;
	}
	
	public CityType(String cityName){
		this.name = cityName;
		this.folderName = getNewCityFolderName(cityName);
		this.time = 0;
		this.mapPosition = new Point(0, 0);
		try {
			Files.createDirectory(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName));
			Files.createDirectory(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\map"));
			Files.createDirectory(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\map\\chunks"));
			Files.createDirectory(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\map\\lines"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Functions.writeTextToFile(name, System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\name.txt", true);
		chunk firstChunk = new chunk(0, 0);
		firstChunk.setPopulation(1, 0, 0);
		addChunk(firstChunk);
	}
	
	public int getPopulation(int x, int y){
		final int chunkX;
		final int chunkY;
		final int XInChunk;
		final int YInChunk;
		if(x >= 0){
			chunkX = (int) Math.floor(x / 4);
		}
		else{
			chunkX = (int) Math.ceil(x / 4);
		}
		if(y >= 0){
			chunkY = (int) Math.floor(y / 4);
		}
		else{
			chunkY = (int) Math.ceil(y / 4);
		}
		XInChunk = x % 4;
		YInChunk = y % 4;
		int counter = 0;
		while(counter < Chunks.length){
			if(Chunks[counter].positionX == chunkX && Chunks[counter].positionY == chunkY){
				return Chunks[counter].getPopulation(XInChunk, YInChunk);
			}
		}
		return 0;
	}
	
	public void save(){
		Functions.writeBytesToFile(Functions.longToBytes(this.time), System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\time.byt", false);
		Calendar now = Calendar.getInstance();
		final byte[] dateData = {(byte) (now.get(Calendar.YEAR) - 2000), (byte) now.get(Calendar.MONTH), (byte) now.get(Calendar.DAY_OF_MONTH), (byte) now.get(Calendar.HOUR_OF_DAY), (byte) now.get(Calendar.MINUTE), (byte) now.get(Calendar.SECOND)};
		Functions.writeBytesToFile(dateData, System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\lastPlay.byt", false);
		Functions.writeBytesToFile(concatenateTwo4bytesArrays(Functions.intToBytes(this.mapPosition.x), Functions.intToBytes(this.mapPosition.y)),
		System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\mapPosition.byt", false);
		int counter = 0;
		while(counter < Chunks.length){
			Chunks[counter].save();
			counter++;
		}
	}
	
	
	public static CityType load(String folderName) throws Exception{
		final byte[] mapBytes = Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\mapPosition.byt");
		final int mapX = Functions.bytesToInt(getFirst4ofByte(mapBytes));
		final int mapY = Functions.bytesToInt(getLast4ofByte(mapBytes));
		return new CityType(
				Functions.bytesToLong(Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\time.byt")),
				Functions.readTextFile(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\name.txt"),
				folderName,
				new Point(mapX, mapY));
	}
	
	protected CityType(long cityTime, String cityName, String cityFolderName, Point cityMapPosition){
		this.time = cityTime;
		this.name = cityName;
		this.folderName = cityFolderName;
		this.mapPosition = cityMapPosition;
	}
	
	static String getNewCityFolderName(String name){
		int counter = 0;
		char[] nameChars = name.toCharArray();
		while(counter < nameChars.length){
			if(nameChars[counter] == '?' | nameChars[counter] == '/' | nameChars[counter] == '<' | nameChars[counter] == '>' | nameChars[counter] == '\\' | nameChars[counter] == ':' | nameChars[counter] == '*' | nameChars[counter] == '.'){
				nameChars[counter] = '_';
			}
			counter++;
		}
		name = new String(nameChars);
		if(name.equals("com1") | name.equals("com2") | name.equals("com3") | name.equals("com4") | name.equals("com5") | name.equals("com6") | name.equals("com7") | name.equals("com8") | name.equals("com9") | name.equals("lpt1") | name.equals("lpt2") | name.equals("lpt3") | name.equals("lpt4") | name.equals("lpt5") | name.equals("lpt6") | name.equals("lpt7") | name.equals("lpt8") | name.equals("lpt9") | name.equals("con") | name.equals("nul") | name.equals("prn")){
			name = "IllegalName";
		}
		if(name.length() == 0){
			name = "IllegalName";
		}
		if(name.length() > 31){
			name = name.substring(0, 31);
		}
		if(Files.exists(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + name))){
			counter = 2;
			while(Files.exists(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + name + Integer.toString(counter)))){
				counter++;
			}
			name = name + Integer.toString(counter);
		}
		return name;
	}
	
	static byte[] concatenateTwo4bytesArrays(byte[] bytes1, byte[] bytes2){
		return new byte[]{bytes1[0], bytes1[1], bytes1[2], bytes1[3], bytes2[0], bytes2[1], bytes2[2], bytes2[3]};
	}
	
	static byte[] getFirst4ofByte(byte[] bytes){
		return new byte[]{bytes[0], bytes[1], bytes[2], bytes[3]};
	}
	
	static byte[] getLast4ofByte(byte[] bytes){
		return new byte[]{bytes[4], bytes[5], bytes[6], bytes[7]};
	}
}
