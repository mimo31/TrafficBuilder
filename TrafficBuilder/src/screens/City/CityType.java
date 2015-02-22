package screens.City;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
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
	public float money;
	chunk[] Chunks = new chunk[0];
	Line[] lines = new Line[0];

	protected void addChunk(final chunk theChunk){
		final chunk[] temp = new chunk[Chunks.length + 1];
		int counter = 0;
		while(counter < Chunks.length){
			temp[counter] = Chunks[counter];
			counter++;
		}
		temp[counter] = theChunk;
		Chunks = temp;
	}
	
	public Color getNewLineColor(){
		int[] colorCounts = new int[6];
		int counter = 0;
		while(counter < 6){
			colorCounts[counter] = 0;
			counter++;
		}
		counter = 0;
		Color[] colors = new Color[6];
		colors[0] = new Color(0, 204, 0);
		colors[1] = new Color(0, 0, 255);
		colors[2] = new Color(255, 0, 0);
		colors[3] = new Color(255, 255, 0);
		colors[4] = new Color(153, 76, 0);
		colors[5] = new Color(255, 128, 0);
		while(counter < lines.length){
			final Color lineColor = lines[counter].lineColor;
			int counter2 = 0;
			while(counter2 < 6){
				if(lineColor == colors[counter2]){
					colorCounts[counter2]++;
				}
				counter2++;
			}
			counter++;
		}
		counter = 1;
		int smallestColorCount = colorCounts[0];
		while(counter < 6){
			if(colorCounts[counter] < smallestColorCount){
				smallestColorCount = colorCounts[counter];
			}
			counter++;
		}
		counter = 0;
		int smallestCCC = 0;
		while(counter < 6){
			if(colorCounts[counter] == smallestColorCount){
				smallestCCC++;
			}
			counter++;
		}
		int[] smallestColCouColors = new int[smallestCCC];
		counter = 0;
		int nextIndex = 0;
		while(counter < 6){
			if(colorCounts[counter] == smallestColorCount){
				smallestColCouColors[nextIndex] = counter;
				nextIndex++;
			}
			counter++;
		}
		return colors[smallestColCouColors[Functions.genRandom(0, smallestColorCount)]];
	}
	
	byte randomByte(){
		return (byte) Math.floor(Math.random() * 256);
	}

	public CityType(final String cityName){
		this.name = cityName;
		this.folderName = getNewCityFolderName(cityName);
		this.time = 0;
		this.mapPosition = new Point(0, 0);
		this.money = 65000;
		try {
			Files.createDirectory(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName));
			Files.createDirectory(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\map"));
			Files.createDirectory(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\map\\chunks"));
			Files.createDirectory(Paths.get(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\map\\lines"));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		Functions.writeTextToFile(name, System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\name.txt", true);
		final chunk firstChunk = new chunk(0, 0);
		firstChunk.setPopulation(1, 0, 0);
		addChunk(firstChunk);
	}

	public int getPopulation(final int x, final int y){
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
		XInChunk = Functions.modulo(x, 4);
		YInChunk = Functions.modulo(y, 4);
		int counter = 0;
		while(counter < Chunks.length){
			if(Chunks[counter].positionX == chunkX && Chunks[counter].positionY == chunkY){
				return Chunks[counter].getPopulation(XInChunk, YInChunk);
			}
			counter++;
		}
		return 0;
	}

	public void save(){
		Functions.writeBytesToFile(Functions.longToBytes(this.time), System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\time.byt", false);
		final Calendar now = Calendar.getInstance();
		final byte[] dateData = {(byte) (now.get(Calendar.YEAR) - 2000), (byte) now.get(Calendar.MONTH), (byte) now.get(Calendar.DAY_OF_MONTH), (byte) now.get(Calendar.HOUR_OF_DAY), (byte) now.get(Calendar.MINUTE), (byte) now.get(Calendar.SECOND)};
		Functions.writeBytesToFile(dateData, System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\lastPlay.byt", false);
		Functions.writeBytesToFile(concatenateTwo4bytesArrays(Functions.intToBytes(this.mapPosition.x), Functions.intToBytes(this.mapPosition.y)),
				System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\mapPosition.byt", false);
		Functions.writeBytesToFile(Functions.floatToBytes(this.money), System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + this.folderName + "\\money.byt", false);
		int counter = 0;
		while(counter < Chunks.length){
			Chunks[counter].save();
			counter++;
		}
	}


	public static CityType load(final String folderName) throws Exception{
		final byte[] mapBytes = Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\mapPosition.byt");
		final int mapX = Functions.bytesToInt(getFirst4ofByte(mapBytes));
		final int mapY = Functions.bytesToInt(getLast4ofByte(mapBytes));
		final float money = Functions.bytesToFloat(Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\money.byt"));
		chunk[] chunks;
		final File directory = new File(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\map\\chunks");
		final File[] listed = directory.listFiles();
		chunks = new chunk[listed.length];
		int counter = 0;
		while(counter < chunks.length){
			int counter2 = listed[counter].toString().length() - 1;
			while(listed[counter].toString().toCharArray()[counter2] != '\\'){
				counter2--;
			}
			final String fileName = listed[counter].toString().substring(counter2 + 1);
			final int chunkX;
			final int chunkY;
			counter2 = 0;
			while(fileName.toCharArray()[counter2] != ','){
				counter2++;
			}
			chunkX = Integer.parseInt(fileName.substring(0, counter2));
			chunkY = Integer.parseInt(fileName.substring(counter2 + 1));
			chunks[counter] = chunk.load(chunkX, chunkY, folderName);
			counter++;
		}
		return new CityType(
				Functions.bytesToLong(Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\time.byt")),
				Functions.readTextFile(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\name.txt"),
				folderName,
				new Point(mapX, mapY),
				chunks,
				money);
	}

	protected CityType(final long cityTime, final String cityName, final String cityFolderName, final Point cityMapPosition, final chunk[] chunks, final float cityMoney){
		this.time = cityTime;
		this.name = cityName;
		this.folderName = cityFolderName;
		this.mapPosition = cityMapPosition;
		this.Chunks = chunks;
		this.money = cityMoney;
	}

	static String getNewCityFolderName(String name){
		int counter = 0;
		final char[] nameChars = name.toCharArray();
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

	static byte[] concatenateTwo4bytesArrays(final byte[] bytes1, final byte[] bytes2){
		return new byte[]{bytes1[0], bytes1[1], bytes1[2], bytes1[3], bytes2[0], bytes2[1], bytes2[2], bytes2[3]};
	}

	static byte[] getFirst4ofByte(final byte[] bytes){
		return new byte[]{bytes[0], bytes[1], bytes[2], bytes[3]};
	}

	static byte[] getLast4ofByte(final byte[] bytes){
		return new byte[]{bytes[4], bytes[5], bytes[6], bytes[7]};
	}
}
