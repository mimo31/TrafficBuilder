package screens.City;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import data.ResourceHandler;
import mainPackage.Functions;
import mainPackage.Variables;

public class city {
	public static CityType theCity;
	static long lastTime;
	private static ActionListener timerAction = new ActionListener(){
    public final void actionPerformed(ActionEvent arg0)
    	{
    		Variables.myGui.repaint();
    	}
	};
	public final static Timer repaint = new Timer(25, timerAction);
	public static void load(CityType city){
		Variables.InCity = true;
		Variables.myGui.setMinimumSize(new Dimension(300, 300));
		theCity = city;
		lastTime = System.currentTimeMillis();
		repaint.start();
	}
	
	public static void close(){
		Variables.InCity = false;
		theCity.save();
		repaint.stop();
	}
	
	public static void paint(Graphics g){
		Graphics2D graph2 = (Graphics2D)g;
		drawMap(graph2);
		drawPowerLine(graph2);
		drawControlPanel(graph2);
	}
	
	public static void drawMap(Graphics2D graph2){
		graph2.setColor(new Color(255, 255, 255));
		final int spaceYStart;
		if(Variables.height > 800){
			spaceYStart = 179;
		}
		else{
			spaceYStart = Variables.height / 5 + 19;
		}
		graph2.fillRect(0, spaceYStart, Variables.width, Variables.height - spaceYStart);
		
		
		int spaceXUsed = 64 - Functions.modulo((int) theCity.mapPosition.getX(), 64);
		int spaceYUsed = 64 - Functions.modulo((int) theCity.mapPosition.getY(), 64) + spaceYStart;
		int axisX = (int) Math.floor(theCity.mapPosition.getX() / 64);
		int axisY = (int) Math.floor(theCity.mapPosition.getY() / 64);
		drawSquarePopulation(graph2, spaceXUsed - 64, spaceYUsed - 64, axisX, axisY);
		axisX++;
		while(spaceXUsed < Variables.width){
			drawSquarePopulation(graph2, spaceXUsed, spaceYUsed - 64, axisX, axisY);
			axisX++;
			spaceXUsed = spaceXUsed + 64;
		}
		axisX = (int) Math.floor(theCity.mapPosition.getX() / 64);
		axisY++;
		while(spaceYUsed  - 256 < Variables.height){
			spaceXUsed = 64 - Functions.modulo((int) theCity.mapPosition.getX(), 64);
			drawSquarePopulation(graph2, spaceXUsed - 64, spaceYUsed, axisX, axisY);
			axisX++;
			while(spaceXUsed < Variables.width){
				drawSquarePopulation(graph2, spaceXUsed, spaceYUsed, axisX, axisY);
				axisX++;
				spaceXUsed = spaceXUsed + 64;
			}
			axisX = (int) Math.floor(theCity.mapPosition.getX() / 64);
			axisY++;
			spaceYUsed = spaceYUsed + 64;
		}
		
		graph2.setColor(Color.lightGray);
		int spaceUsed = 256 - Functions.modulo((int) theCity.mapPosition.getX(), 256);
		graph2.drawLine(spaceUsed, spaceYStart, spaceUsed, Variables.height);
		while(spaceUsed < Variables.width){
			graph2.drawLine(spaceUsed, spaceYStart, spaceUsed, Variables.height);
			spaceUsed = spaceUsed + 256;
		}
		spaceUsed = 256 - Functions.modulo((int) theCity.mapPosition.getY(), 256) + spaceYStart;
		graph2.drawLine(0, spaceUsed, Variables.width, spaceUsed);
		while(spaceUsed < Variables.height){
			graph2.drawLine(0, spaceUsed, Variables.width, spaceUsed);
			spaceUsed = spaceUsed + 256;
		}
	}
	
	public static void mouseDragged(MouseEvent event){
		final int spaceYStart;
		if(Variables.height > 800){
			spaceYStart = 179;
		}
		else{
			spaceYStart = Variables.height / 5 + 19;
		}
		if(event.getX() > 0 && event.getX() < Variables.width && event.getY() > spaceYStart && event.getY() < Variables.height){
			theCity.mapPosition.x = (int) (theCity.mapPosition.x - event.getX() + Variables.lastMousePosition.getX());
			theCity.mapPosition.y = (int) (theCity.mapPosition.y - event.getY() + Variables.lastMousePosition.getY());
		}
		
	}
	
	public static void drawSquarePopulation(Graphics2D graph2, int pixelX, int pixelY, int squareX, int squareY){
		if(theCity.getPopulation(squareX, squareY) != 0){
			final Image texture = ResourceHandler.getPopulationImage(theCity.getPopulation(squareX, squareY));
			graph2.drawImage(texture, pixelX, pixelY, null);
		}
	}
	
	public static void drawControlPanel(Graphics2D graph2){
		int controlPanelHeight;
		int borderSize;
		if(Variables.height > 800){
			controlPanelHeight = 140;
		}
		else{
			controlPanelHeight = Variables.height / 5 - 20;
		}
		borderSize = controlPanelHeight / 10;
		graph2.setColor(Color.black);
		graph2.fillRect(0, 0, Variables.width, controlPanelHeight);
		graph2.setColor(Color.blue);
		graph2.fillRect(borderSize, borderSize, Variables.width - borderSize * 2, controlPanelHeight - borderSize * 2);
		theCity.time = theCity.time + System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		graph2.setColor(Color.white);
		graph2.setFont(Variables.nowUsingFont.deriveFont(101f));
		Rectangle s1Size = Functions.getStringBounds(graph2, longTimeToDate(theCity.time), 0, 0);
		Double s1Per1Height = ((double) s1Size.height) / 101;
		graph2.setFont(Variables.nowUsingFont.deriveFont((float) ((controlPanelHeight - 2 * borderSize) / 4 / s1Per1Height)));
		s1Size = Functions.getStringBounds(graph2, longTimeToDate(theCity.time), 0, 0);
		graph2.drawString(longTimeToDate(theCity.time),Variables.width - borderSize - 3 - s1Size.width, borderSize + 3 - s1Size.y);
	}
	
	public static void drawPowerLine(Graphics2D graph2){
		int linePosition;
		if(Variables.height > 800){
			linePosition = 160;
		}
		else{
			linePosition = Variables.height / 5;
		}
		graph2.setColor(Color.white);
		graph2.fillRect(0, linePosition - 15, Variables.width, 30);
		final long time = System.currentTimeMillis();
		int counter = 0;
		graph2.setColor(Color.red);
		double sin;
		while(counter < Variables.width){
			sin = (int) (Math.sin(time / 50 + counter / 10) * 5);
			graph2.drawRect(counter, (int) (linePosition - sin), 1, (int) (sin + 15));
			counter++;
		}
		graph2.setColor(Color.black);
		graph2.fillRect(0, linePosition - 20, Variables.width, 5);
		graph2.fillRect(0, linePosition + 14, Variables.width, 5);
		int blockHeight = 0;
		if(time % 3000 > 1000){
			blockHeight = (int) (((Math.abs((time % 3000) - 2000) - 500) * -1 + 500) * 0.03);
		}
		graph2.fillRect(0, linePosition - 15, Variables.width % 20 / 2, blockHeight);
		int blockSpaceFilled = Variables.width % 20 / 2;
		boolean nextUp = false;
		while(blockSpaceFilled < Variables.width){
			if(nextUp){
				graph2.fillRect(blockSpaceFilled, linePosition - 15, 10, blockHeight);
				nextUp = false;
			}
			else{
				graph2.fillRect(blockSpaceFilled, linePosition + 15 - blockHeight, 10, blockHeight);
				nextUp = true;
			}
			blockSpaceFilled = blockSpaceFilled + 10;
		}
	}
	
	static String longTimeToDate(long longTime){
		longTime = longTime / 250 * 3;
		int year;
		String month;
		String day;
		String hour;
		String minute;
		year = (int) (2000 + Math.floor(longTime / 525600));
		longTime = longTime % 525600;
		if(longTime < 44640){
			month = "January";
		}else if(longTime < 84960){
			longTime = longTime - 44640;
			month = "February";
		}else if(longTime < 129600){
			longTime = longTime - 84960;
			month = "March";
		}else if(longTime < 172800){
			longTime = longTime - 129600;
			month = "April";
		}else if(longTime < 217440){
			longTime = longTime - 172800;
			month = "May";
		}else if(longTime < 260640){
			longTime = longTime - 217440;
			month = "June";
		}else if(longTime < 305280){
			longTime = longTime - 260640;
			month = "July";
		}else if(longTime < 349920){
			longTime = longTime - 305280;
			month = "August";
		}else if(longTime < 393120){
			longTime = longTime - 349920;
			month = "September";
		}else if(longTime < 437760){
			longTime = longTime - 393120;
			month = "October";
		}else if(longTime < 480960){
			longTime = longTime - 437760;
			month = "November";
		}else{
			longTime = longTime - 480960;
			month = "December";
		}
		final byte monthDay = (byte) (Math.floor(longTime / 1440) + 1);
		if(monthDay == 1 || monthDay == 21 || monthDay == 31){
			day = monthDay + "st";
		}else if(monthDay == 2 || monthDay == 22){
			day = monthDay + "nd";
		}else if(monthDay == 3 || monthDay == 23){
			day = monthDay + "rd";
		}else{
			day = monthDay + "th";
		}
		longTime = longTime % 1440;
		if(Math.floor(longTime / 60) < 10){
			hour = "0" + String.valueOf((int) Math.floor(longTime / 60));
		}
		else{
			hour = String.valueOf((int) Math.floor(longTime / 60));
		}
		if(longTime % 60 < 10){
			minute = "0" + String.valueOf(longTime % 60);
		}
		else{
			minute = String.valueOf(longTime % 60);
		}
		return day + " " + month + ", " + year + " " + hour + ":" + minute;
	}
}
