package screens.city;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import mainPackage.Variables;

public class City extends Components {
	public static CityType theCity;
	public static boolean paused;
	public static boolean inPauseMenu;
	static long powerLineState;
	static boolean TCWframeBlack;
	static long endTCWBlinking;
	static boolean draggingTCW;
	static int TCWmapX;
	static int TCWmapY;
	static Line line;
	static boolean expandingLineStart;
	final static double sqrt2 = Math.round(Math.sqrt(2) * 100) / (double) (100);
	static String errorText;
	static long errorDisappearTime;
	static boolean makingHistory[];

	public static void addHistoryValue(boolean value){
		boolean[] temp = new boolean[makingHistory.length + 1];
		int counter = 0;
		while(counter < makingHistory.length){
			temp[counter] = makingHistory[counter];
			counter++;
		}
		temp[temp.length - 1] = value;
		makingHistory = temp;
	}

	public static void close(){
		Variables.InCity = false;
		theCity.save();
		Tick.ticker.stop();
		paused = false;
		powerLineState = 0;
	}

	public static Point convertMapCoorsToScreenCoors(Point mapCoors){
		return new Point(mapCoors.x * 64 - theCity.mapPosition.x, mapCoors.y * 64 - theCity.mapPosition.y + controlPanel.height + 39);
	}

	public static Point convertScreenCoorsToMapCoors(Point screenCoors){
		screenCoors.y = screenCoors.y - 39 - controlPanel.height;
		final Point mapCoors = new Point((int) Math.floor((screenCoors.x + theCity.mapPosition.x) / (double) 64), (int) Math.floor((screenCoors.y + theCity.mapPosition.y) / (double) 64));
		return mapCoors;
	}

	public static Area getStationRing(Point stationCoors){
		stationCoors = convertMapCoorsToScreenCoors(stationCoors);
		Area circle = new Area(new Ellipse2D.Double((int) (stationCoors.x - (sqrt2 * 64 - 64) / 2), (int) (stationCoors.y - (sqrt2 * 64 - 64) / 2), (int) (sqrt2 * 64), (int) (sqrt2 * 64)));
		final Area smallEll = new Area(new Ellipse2D.Double(stationCoors.x, stationCoors.y, 64, 64));
		circle.subtract(smallEll);
		return circle;
	}

	public static void keyReleased(KeyEvent event){
		if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			if(makingLine){
				undoMakingStep();
			}
		}
	}

	public static void load(final CityType city){
		Variables.InCity = true;
		Variables.myGui.setMinimumSize(new Dimension(300, 300));
		TCWframeBlack = true;
		showTCW = false;
		inPauseMenu = false;
		makingLine = false;
		errorText = "";
		theCity = city;
		Tick.ticker.start();
	}

	public static void mouseClicked(final MouseEvent event){
		MouseEvents.mouseClicked(event);
	}

	public static void mouseDragged(final MouseEvent event){
		MouseEvents.mouseDragged(event);
	}

	public static void mousePressed(MouseEvent event){
		MouseEvents.mousePressed(event);
	}

	public static void mouseRelesed(MouseEvent event){
		MouseEvents.mouseRelesed(event);
	}

	public static void paint(final Graphics g){
		final Graphics2D graph2 = (Graphics2D)g;
		PaintCity.paint(graph2);
	}

	public static boolean stationPlaceAllowed(Point station){
		int counter = 0;
		boolean stationExists = false;
		while(counter < line.trace.length){
			if(line.trace[counter].equals(station)){
				stationExists = true;
				break;
			}
			counter++;
		}
		return (stationExists == false);
	}

	public static void undoMakingStep(){
		if(makingHistory.length > 0){
			if(makingHistory[makingHistory.length - 1] == true){
				line.removeStartStation();
			}
			else{
				line.removeEndStation();
			}
			expandingLineStart = makingHistory[makingHistory.length - 1];
			boolean[] temp = new boolean[makingHistory.length - 1];
			int counter = 0;
			while(counter < temp.length){
				temp[counter] = makingHistory[counter];
				counter++;
			}
			makingHistory = temp;
		}
	}
}
