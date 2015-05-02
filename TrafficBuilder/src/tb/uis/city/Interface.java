package tb.uis.city;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import tb.ActionResult;
import tb.ExtendedGraphics2D;
import tb.Main;
import tb.UI;
import tb.cityType.Line;

public class Interface extends UI{
	
	static boolean paused;
	static long powerLineState;
	static boolean TCWframeBlack;
	static long endTCWBlinking;
	static boolean draggingTCW;
	static int TCWmapX;
	static int TCWmapY;
	static Line line;
	static boolean expandingLineStart;
	static String errorText = "";
	static long errorDisappearTime;
	static boolean makingHistory[];
	final static double sqrt2 = Math.round(Math.sqrt(2) * 128) / (double) (128);

	static boolean makingLine;
	static boolean inPauseMenu;
	static boolean inViewSettings;
	static boolean showTCW;
	static Point TCWposition;
	static int TCWwidth;

	public static void addHistoryValue(boolean value){
		boolean[] temp = new boolean[makingHistory.length + 1];
		for(int i = 0; i < makingHistory.length; i++){
			temp[i] = makingHistory[i];
		}
		temp[temp.length - 1] = value;
		makingHistory = temp;
	}

	public void close() {
		Tick.ticker.stop();
	}

	public static Point convertMapCoorsToScreenCoors(Point mapCoors){
		return new Point(mapCoors.x * 64 - Main.city.mapPosition.x, mapCoors.y * 64 - Main.city.mapPosition.y + Components.controlPanel.height + 39);
	}
	
	public static Point convertScreenCoorsToMapCoors(Point screenCoors){
		screenCoors.y = screenCoors.y - 39 - Components.controlPanel.height;
		final Point mapCoors = new Point((int) Math.floor((screenCoors.x + Main.city.mapPosition.x) / (double) 64), (int) Math.floor((screenCoors.y + Main.city.mapPosition.y) / (double) 64));
		return mapCoors;
	}
	
	public static Area getStationRing(Point stationCoors){
		stationCoors = convertMapCoorsToScreenCoors(stationCoors);
		Area circle = new Area(new Ellipse2D.Double((int) (stationCoors.x - (sqrt2 * 64 - 64) / 2), (int) (stationCoors.y - (sqrt2 * 64 - 64) / 2), (int) (sqrt2 * 64), (int) (sqrt2 * 64)));
		final Area smallEll = new Area(new Ellipse2D.Double(stationCoors.x, stationCoors.y, 64, 64));
		circle.subtract(smallEll);
		return circle;
	}
	
	public ActionResult load(){
		Tick.ticker.start();
		return null;
	}
	
	public ActionResult mouseClicked(MouseEvent event){
		return MouseHandlers.mouseClicked(event);
	}
	
	public ActionResult mouseDragged(MouseEvent event){
		MouseHandlers.mouseDragged(event);
		return null;
	}
	
	public ActionResult mousePressed(MouseEvent event){
		MouseHandlers.mousePressed(event);
		return null;
	}
	
	public ActionResult mouseReleased(MouseEvent event){
		MouseHandlers.mouseReleased(event);
		return null;
	}

	public ActionResult paint(Graphics2D graph2, ExtendedGraphics2D exGraph){
		Paint.paint(graph2, exGraph);
		return null;
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
