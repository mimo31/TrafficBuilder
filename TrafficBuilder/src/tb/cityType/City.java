package tb.cityType;

import java.awt.Color;
import java.awt.Point;
import java.nio.file.Files;
import java.nio.file.Paths;

import tb.Mathematics;

public class City {
	public long time;
	public String name;
	public String folderName;
	public Point mapPosition;
	public float money;
	public IntMap popMap;
	public Line[] lines = new Line[0];
	
	public void addLine(final Line line){
		final Line[] temp = new Line[this.lines.length + 1];
		int counter = 0;
		while(counter < this.lines.length){
			temp[counter] = this.lines[counter];
			counter++;
		}
		temp[counter] = line;
		this.lines = temp;
	}
	
	public int getMaxPopulation(){
		return 3;
	}
	
	public char getNewLineCodeChar(){
		if(lines.length == 0){
			return 'A';
		}
		else{
			final char lastLineChar = lines[lines.length - 1].codeChar;
			if(lastLineChar == 'Z'){
				return 'A';
			}
			else{
				return (char) (lastLineChar + 1);
			}
		}
	}
	
	public int getNewLineCodeNumber(){
		if(lines.length == 0){
			return 0;
		}
		else{
			final char lastLineChar = lines[lines.length - 1].codeChar;
			if(lastLineChar == 'Z'){
				return lines[lines.length - 1].codeNumber + 1;
			}
			else{
				return lines[lines.length - 1].codeNumber;
			}
		}
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
				if(lineColor.equals(colors[counter2])){
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
		return colors[smallestColCouColors[Mathematics.genRandom(0, smallestCCC)]];
	}
	
	byte randomByte(){
		return (byte) Math.floor(Math.random() * 256);
	}

	public City(final String cityName){
		this.name = cityName;
		this.folderName = getNewCityFolderName(cityName);
		this.time = 0;
		this.mapPosition = new Point(0, 0);
		this.money = 65000;
		this.popMap = new IntMap();
		this.popMap.setValue(new Point(0, 0), 1);
		tb.data.Cities.createCity(this.folderName, this.name);
	}

	public int getPopulation(final Point position){
		return popMap.getValue(position);
	}

	public City(final long cityTime, final String cityName, final String cityFolderName, final Point cityMapPosition, final IntMap popMap, final float cityMoney, final Line[] cityLines){
		this.time = cityTime;
		this.name = cityName;
		this.folderName = cityFolderName;
		this.mapPosition = cityMapPosition;
		this.popMap = popMap;
		this.money = cityMoney;
		this.lines = cityLines;
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
