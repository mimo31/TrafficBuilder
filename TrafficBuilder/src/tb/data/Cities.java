package tb.data;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

import tb.CityInfo;
import tb.cityType.Chunk;
import tb.cityType.City;
import tb.cityType.IntMap;
import tb.cityType.Line;

public class Cities {

	static Calendar bytesToCalendar(byte[] bytes) {
		Calendar result = Calendar.getInstance();
		result.set(Calendar.YEAR, bytes[0] + 2000);
		result.set(Calendar.MONTH, bytes[1]);
		result.set(Calendar.DAY_OF_MONTH, bytes[2]);
		result.set(Calendar.HOUR_OF_DAY, bytes[3]);
		result.set(Calendar.MINUTE, bytes[4]);
		result.set(Calendar.SECOND, bytes[5]);
		return result;
	}

	public static void createCity(String folderName, String name){
		try {
			Files.createDirectory(Paths.get(IO.getInGameDir("\\Saves\\" + folderName)));
			Files.createDirectory(Paths.get(IO.getInGameDir("\\Saves\\" + folderName + "\\map")));
			Files.createDirectory(Paths.get(IO.getInGameDir("\\Saves\\" + folderName + "\\map\\chunks")));
			Files.createDirectory(Paths.get(IO.getInGameDir("\\Saves\\" + folderName + "\\map\\lines")));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		IO.writeTextToFile(name, IO.getInGameDir("\\Saves\\" + folderName + "\\name.txt"), true);
	}
	
	public static CityInfo[] getCityInfos() {
		File[] listedCities = IO.getInGameFile("\\Saves").listFiles();
		CityInfo[] result = new CityInfo[listedCities.length];
		if (listedCities.length == 0) {
			result = new CityInfo[0];
		} else {
			CityInfo[] readedInfos = new CityInfo[listedCities.length];
			for(int i = 0; i < listedCities.length; i++){
				readedInfos[i] = readCityInfo(listedCities[i]);
			}
			int[] order = new int[readedInfos.length];
			boolean[] alreadyOrdered = new boolean[readedInfos.length];
			int youngestDateFoundIndex = -1;
			int lastIndexedIndex = -1;
			while (lastIndexedIndex + 1 < order.length) {
				for (int i = 0; i < readedInfos.length; i++) {
					if (alreadyOrdered[i] == false) {
						if (youngestDateFoundIndex == -1) {
							youngestDateFoundIndex = i;
						} else {
							if (readedInfos[youngestDateFoundIndex].lastPlayed.getTimeInMillis() < 
									readedInfos[i].lastPlayed.getTimeInMillis()) {
								youngestDateFoundIndex = i;
							}
						}
					}
				}
				order[lastIndexedIndex + 1] = youngestDateFoundIndex;
				alreadyOrdered[youngestDateFoundIndex] = true;
				lastIndexedIndex++;
				youngestDateFoundIndex = -1;
			}
			for(int i = 0; i < result.length; i++){
				result[i] = readedInfos[order[i]];
			}
		}
		return result;
	}

	public static Chunk loadChunk(Point position, String folderName){
		int chunkLands[][] = new int[4][4];
		byte[] readedBytes = null;
		try {
			readedBytes = IO.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\map\\chunks\\" +
					position.x + "," + position.y);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		int counter = 0;
		int counter2;
		while(counter < 4){
			counter2 = 0;
			while(counter2 < 4){
				final byte[] bytes = new byte[4];
				bytes[0] = readedBytes[counter * 16 + counter2 * 4];
				bytes[1] = readedBytes[counter * 16 + counter2 * 4 + 1];
				bytes[2] = readedBytes[counter * 16 + counter2 * 4 + 2];
				bytes[3] = readedBytes[counter * 16 + counter2 * 4 + 3];
				chunkLands[counter2][counter] = IO.bytesToInt(bytes);
				counter2++;
			}
			counter++;
		}
		return new Chunk(chunkLands, position);
	}
	
	public static City loadCity(String folderName) throws Exception{
		byte[] mapPosBytes = IO.readBytes(IO.getInGameDir("\\Saves\\" + folderName + "\\mapPosition.byt"));
		int mapX = IO.bytesToInt(IO.getFirst4ofByte(mapPosBytes));
		int mapY = IO.bytesToInt(IO.getLast4ofByte(mapPosBytes));
		float money = IO.bytesToFloat(IO.readBytes(IO.getInGameFile("\\Saves\\" + folderName + "\\money.byt")));
		File directory = IO.getInGameFile("\\Saves\\" + folderName + "\\map\\chunks");
		File[] listed = directory.listFiles();
		IntMap popMap = new IntMap();
		for(int i = 0; i < listed.length; i++){
			int counter = listed[i].toString().length() - 1;
			while(listed[counter].toString().charAt(counter) != '\\'){
				counter--;
			}
			String fileName = listed[counter].toString().substring(counter + 1);
			int chunkX;
			int chunkY;
			counter = 0;
			while(fileName.charAt(counter) != ','){
				counter++;
			}
			chunkX = Integer.parseInt(fileName.substring(0, counter));
			chunkY = Integer.parseInt(fileName.substring(counter + 1));
			popMap.addChunk(loadChunk(new Point(chunkX, chunkY), folderName));
		}
		File linesDirectory = IO.getInGameFile("\\Saves\\" + folderName + "\\map\\lines");
		File[] listedLines = linesDirectory.listFiles();
		Line[] lines = new Line[listedLines.length];
		for(int i = 0; i < lines.length; i++){
			String filePath = listedLines[i].toString();
			int counter = filePath.length() - 1;
			while(filePath.charAt(counter) == '\\'){
				counter--;
			}
			String lineCodeName = filePath.substring(counter + 1);
			char lineCodeChar = lineCodeName.charAt(0);
			int lineCodeNumber = Integer.parseInt(lineCodeName.substring(1));
			lines[counter] = new Line(IO.readBytes(listedLines[counter]), lineCodeChar, lineCodeNumber);
			counter++;
		}
		return new City(
				IO.bytesToLong(IO.readBytes(IO.getInGameFile("\\Saves\\" + folderName + "\\time.byt"))),
				IO.readTextFile(IO.getInGameDir("\\Saves\\" + folderName + "\\name.txt")),
				folderName,
				new Point(mapX, mapY),
				popMap,
				money,
				lines);
	}
	
	public static CityInfo readCityInfo(File directory) {
		String name;
		String folder;
		Calendar lastPlayed = null;
		name = IO.readTextFile(directory.toString() + "\\name.txt");
		int counter = directory.toString().length() - 1;
		while (directory.toString().charAt(counter) != '\\') {
			counter--;
		}
		folder = directory.toString().substring(counter + 1);
		try {
			lastPlayed = bytesToCalendar(IO.readBytes(directory.toString()
					+ "\\lastPlay.byt"));
		} catch (final Throwable e) {
			e.printStackTrace();
		}
		return new CityInfo(name, folder, lastPlayed);
	}
	
	public static void saveChunk(Chunk chunk, String folderName) {
		final byte[] bytesToWrite = new byte[64];
		int counter = 0;
		int counter2;
		while(counter < 4){
			counter2 = 0;
			while(counter2 < 4){
				final byte[] intBytes = IO.intToBytes(chunk.Lands[counter2][counter]);
				bytesToWrite[counter * 16 + counter2 * 4] = intBytes[0];
				bytesToWrite[counter * 16 + counter2 * 4 + 1] = intBytes[1];
				bytesToWrite[counter * 16 + counter2 * 4 + 2] = intBytes[2];
				bytesToWrite[counter * 16 + counter2 * 4 + 3] = intBytes[3];
				counter2++;
			}
			counter++;
		}
		IO.writeBytesToFile(bytesToWrite, IO.getInGameDir("\\Saves\\" + folderName + "\\map\\chunks\\" +
				chunk.position.x + "," + chunk.position.y), false);
	}
	public static void saveCity(City city){
		IO.writeBytesToFile(IO.longToBytes(city.time), IO.getInGameDir("\\Saves\\" + city.folderName + "\\time.byt"), false);
		Calendar now = Calendar.getInstance();
		byte[] dateData = {(byte) (now.get(Calendar.YEAR) - 2000), (byte) now.get(Calendar.MONTH), (byte) now.get(Calendar.DAY_OF_MONTH), (byte) now.get(Calendar.HOUR_OF_DAY), (byte) now.get(Calendar.MINUTE), (byte) now.get(Calendar.SECOND)};
		IO.writeBytesToFile(dateData, IO.getInGameDir("\\Saves\\" + city.folderName + "\\lastPlay.byt"), false);
		IO.writeBytesToFile(IO.concatenateTwo4bytesArrays(IO.intToBytes(city.mapPosition.x), IO.intToBytes(city.mapPosition.y)),
				IO.getInGameDir("\\Saves\\" + city.folderName + "\\mapPosition.byt"), false);
		IO.writeBytesToFile(IO.floatToBytes(city.money), System.getenv("APPDATA")  + "\\TrafficBuilder\\Saves\\" + city.folderName + "\\money.byt", false);
		for(int i = 0; i < city.popMap.chunks.length; i++){
			saveChunk(city.popMap.chunks[i], city.folderName);
		}
		File linesDirectory = IO.getInGameFile("\\Saves\\" + city.folderName + "\\map\\lines");
		File[] listedLineFiles = linesDirectory.listFiles();
		for(int i = 0; i < listedLineFiles.length; i++){
			listedLineFiles[i].delete();
		}
		for(int i = 0; i < city.lines.length; i++){
			String path = IO.getInGameDir("\\Saves\\" + city.folderName + "\\map\\lines\\" + String.valueOf(city.lines[i].codeChar) + String.valueOf(city.lines[i].codeNumber) + ".byt");
			IO.writeBytesToFile(city.lines[i].toBytes(), path, false);
		}
	}
}
