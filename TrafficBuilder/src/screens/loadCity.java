package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;

import mainPackage.Functions;
import mainPackage.Variables;

public class loadCity {
	static String[] names;
	static Calendar[] lastPlays;
	static String[] folders;
	static double listPosition;
	
	public static void paint(Graphics g){
		Graphics2D graph2 = (Graphics2D) g;
		graph2.setColor(new Color(40, 40, 40));
		graph2.fillRect(0, 0, Variables.width, Variables.height / 6);
		graph2.setColor(Color.WHITE);
		Functions.drawMaxString(graph2, "Load your city", new Rectangle(Variables.width / 8, Variables.height / 48, Variables.width / 4 * 3, Variables.height / 8));
		Functions.drawChangRect(graph2, Color.white, Color.lightGray, Variables.width - Variables.width / 40, Variables.height / 6, Variables.width / 40, Variables.height - Variables.height / 6);
		final Polygon theUpPolygon = new Polygon(new int[]{Variables.width - Variables.width / 48, Variables.width - Variables.width / 80, Variables.width - Variables.width / 240},
				new int[]{(int) (Variables.width / 240 + Variables.height / 6 + Math.sqrt(3 * Math.pow(Variables.width / 120, 2))), Variables.width / 240 + Variables.height / 6, (int) (Variables.width / 240 + Variables.height / 6 + Math.sqrt(3 * Math.pow(Variables.width / 120, 2)))}, 3);
		if(theUpPolygon.contains(Variables.lastMousePosition)){
			graph2.setColor(Color.white);
		}
		else{
			graph2.setColor(Color.DARK_GRAY);
		}
		graph2.fillPolygon(theUpPolygon);
		final Polygon theDownPolygon = new Polygon(new int[]{Variables.width - Variables.width / 48, Variables.width - Variables.width / 80, Variables.width - Variables.width / 240},
				new int[]{(int) (Variables.height - Variables.width / 240 - Math.sqrt(3 * Math.pow(Variables.width / 120, 2))), Variables.height - Variables.width / 240, (int) (Variables.height - Variables.width / 240 - Math.sqrt(3 * Math.pow(Variables.width / 120, 2)))}, 3);
		if(theDownPolygon.contains(Variables.lastMousePosition)){
			graph2.setColor(Color.white);
		}
		else{
			graph2.setColor(Color.DARK_GRAY);
		}
		graph2.fillPolygon(theDownPolygon);
		if(names.length == 0){
			
		}
		else{
			final int gapSize;
			final int cityBlockWidth;
			final boolean fullBlocks;
			if(Variables.width - Variables.width / 40 > 900){
				cityBlockWidth = (Variables.width - Variables.width / 40) / 2;
				gapSize = cityBlockWidth / 3 / 10;
				fullBlocks = false;
			}
			else{
				gapSize = (Variables.width - Variables.width / 40) / 32;
				cityBlockWidth = (Variables.width - Variables.width / 40) - 2 * gapSize;
				fullBlocks = true;
			}
			int lastPainted = (int) Math.floor(listPosition);
			int spaceUsed = (int) ((cityBlockWidth / 3 + gapSize) * (1 - (listPosition % 1)));
			if(fullBlocks == true){
				graph2.fillRect(gapSize, -1 * (cityBlockWidth / 3 - spaceUsed) + Variables.height / 6, cityBlockWidth, cityBlockWidth / 3);
			}
			else{
				graph2.fillRect(cityBlockWidth / 2, -1 * (cityBlockWidth / 3 - spaceUsed) + Variables.height / 6, cityBlockWidth, cityBlockWidth / 3);
			}
		}
	}
	
	public static void mouseClicked(MouseEvent event){
		
	}
	
	public static void load(){
		Variables.InLoadCity = true;
		File directory = new File(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves");
		File[] listed = directory.listFiles();
		listPosition = 0;
		if(listed.length == 0){
			names = new String[0];
			lastPlays = new Calendar[0];
			folders = new String[0];
		}
		else{
			names = new String[listed.length];
			lastPlays = new Calendar[listed.length];
			folders = new String[listed.length];
			String[] readedNames = new String[listed.length];
			Calendar[] readedDates = new Calendar[listed.length];
			String[] readedFolders = new String[listed.length];
			int[] used = new int[listed.length];
			int amountOfUsed = 0;
			int counter = 0;
			while(used.length > counter){
				used[counter] = used.length;
				counter++;
			}
			counter = 0;
			while(listed.length > counter){
				readedNames[counter] = Functions.readTextFile(listed[counter].toString() + "\\name.txt");
				try {
					readedDates[counter] = bytToCalendar(Functions.readBytes(listed[counter].toString() + "\\lastPlay.byt"));
				} catch (Throwable e) {
					e.printStackTrace();
				}
				int counter2 = listed[counter].toString().length() - 1;
				while(listed[counter].toString().toCharArray()[counter2] == '\\'){
					counter2--;
				}
				readedFolders[counter] = listed[counter].toString().substring(counter2 + 1);
				counter++;
			}
			while(amountOfUsed < used.length){
				counter = 1;
				int biggestFound = 0;
				long biggestFoundValue = readedDates[0].getTimeInMillis();
				while(readedDates.length > counter){
					if(Arrays.asList(used).contains(counter) == false){
						if(readedDates[counter].getTimeInMillis() > biggestFoundValue){
							biggestFoundValue = readedDates[counter].getTimeInMillis();
							biggestFound = counter;
						}
					}
					counter++;
				}
				names[amountOfUsed] = readedNames[biggestFound];
				lastPlays[amountOfUsed] = readedDates[biggestFound];
				folders[amountOfUsed] = readedFolders[biggestFound];
				used[amountOfUsed] = biggestFound;
				amountOfUsed++;
			}
		}
	}
	
	static Calendar bytToCalendar(byte[] bytes){
		Calendar result = Calendar.getInstance();
		result.set(Calendar.YEAR, bytes[0]);
		result.set(Calendar.MONTH, bytes[1]);
		result.set(Calendar.DAY_OF_MONTH, bytes[2]);
		result.set(Calendar.HOUR_OF_DAY, bytes[3]);
		result.set(Calendar.MINUTE, bytes[4]);
		result.set(Calendar.SECOND, bytes[5]);
		return result;
	}
	
	public static void close(){
		
	}
}
