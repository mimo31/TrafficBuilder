package screens.City;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.Timer;

import mainPackage.Functions;
import mainPackage.Variables;

public class city {
	public static CityType theCity;
	static long lastTime;
	public static boolean paused;
	public static boolean inPauseMenu;
	static long powerLineState;
	static boolean showTCW;
	static Point TCWposition;
	static int TCWmapX;
	static int TCWmapY;
	static int TCWwidth;
	static boolean TCWframeBlack;
	static long endTCWBlinking;
	static boolean draggingTCW;
	static boolean makingLine;
	static Line line;
	static boolean expandingLineStart;
	final static double sqrt2 = Math.round(Math.sqrt(2) * 100) / (double) (100);
	static String errorText;
	static long errorDisappearTime;
	static boolean makingHistory[];
	
	private static ActionListener timerAction = new ActionListener(){
		@Override
		public final void actionPerformed(final ActionEvent arg0){
			Variables.myGui.repaint();
			if(errorDisappearTime <= System.currentTimeMillis()){
				errorText = "";
			}
		}
	};
	public final static Timer repaint = new Timer(25, timerAction);

	public static void load(final CityType city){
		Variables.InCity = true;
		Variables.myGui.setMinimumSize(new Dimension(300, 300));
		TCWframeBlack = true;
		showTCW = false;
		inPauseMenu = false;
		makingLine = false;
		errorText = "";
		theCity = city;
		lastTime = System.currentTimeMillis();
		repaint.start();
	}

	public static void close(){
		Variables.InCity = false;
		theCity.save();
		repaint.stop();
		paused = false;
		powerLineState = 0;
	}
	
	public static void paint(final Graphics g){
		final Graphics2D graph2 = (Graphics2D)g;
		PaintCity.paint(graph2);
	}
	
	public static void mousePressed(MouseEvent event){
		if(showTCW){
			final Rectangle TCW = new Rectangle(TCWposition.x, TCWposition.y, TCWwidth, TCWwidth * 2);
			if(TCW.contains(event.getPoint())){
				draggingTCW = true;
			}
		}
	}
	
	public static void mouseRelesed(MouseEvent event){
		draggingTCW = false;
	}
	
	public static int getControlPHeight(){
		if(Variables.height > 800){
			return 140;
		}
		else{
			return Variables.height / 5 - 20;
		}
	}
	
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
	
	public static void keyReleased(KeyEvent event){
		if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			if(makingLine){
				undoMakingStep();
			}
		}
	}
	
	public static void mouseClicked(final MouseEvent event){
		if(inPauseMenu == false){
			final int controlPanelHeight = getControlPHeight();
			final int borderSize = controlPanelHeight / 10;
			final int pauseSize = (controlPanelHeight - 2 * borderSize) / 2;
			final Rectangle pauseButton = new Rectangle(Variables.width - borderSize - pauseSize,
					controlPanelHeight - borderSize - pauseSize,
					pauseSize,
					pauseSize);
			if(pauseButton.contains(event.getPoint())){
				inPauseMenu = true;
				pause();
			}
			else{
				if(event.getY() > controlPanelHeight + 39){
					if(showTCW){
						if(Functions.buttonClicked(event, TCWposition.x, TCWposition.y + controlPanelHeight, TCWwidth, TCWwidth * 2)){
							final int TCWborderSize = TCWwidth / 16;
							final Rectangle CLButton = new Rectangle(TCWposition.x + TCWborderSize, TCWposition.y + controlPanelHeight + TCWwidth * 2 - TCWborderSize - TCWwidth / 8, TCWwidth - 2 * TCWborderSize, TCWwidth / 8);
							if(Functions.buttonClicked(event, TCWposition.x + TCWwidth - 3 * TCWborderSize, TCWposition.y + controlPanelHeight + TCWborderSize, 2 * TCWborderSize, 2 * TCWborderSize)){
								showTCW = false;
							}
							else if(CLButton.contains(event.getPoint())){
								pause();
								makingLine = true;
								makingHistory = new boolean[0];
								line = new Line(new Point[]{new Point(TCWmapX, TCWmapY)});
								line.lineColor = theCity.getNewLineColor();
								showTCW = false;
							}
						}
						else{
							showTCW = false;
						}
					}
					else{
						if(makingLine == false){
							showTCW = true;
							if(Variables.width > Variables.height){
								TCWwidth = Variables.height / 4;
							}
							else{
								TCWwidth = Variables.width / 4;
							}
							TCWposition = event.getPoint();
							TCWposition.y = TCWposition.y - controlPanelHeight;
							final int mapMoveFromLastPointX = Functions.modulo((int) theCity.mapPosition.getX(), 64);
							final int mapMoveFromLastPointY = Functions.modulo((int) theCity.mapPosition.getY(), 64);
							final int lastPointX = (int) Math.floor(theCity.mapPosition.getX() / 64);
							final int lastPointY = (int) Math.floor(theCity.mapPosition.getY() / 64);
							TCWmapX = (int) (lastPointX + Math.floor((mapMoveFromLastPointX + event.getX()) / 64));
							TCWmapY = (int) (lastPointY + Math.floor((mapMoveFromLastPointY + event.getY() - controlPanelHeight - 39) / 64));
						}
						else{
							final Rectangle cancelButton = new Rectangle(0, Variables.height - Variables.width / 32, Variables.width / 16, Variables.width / 32);
							final Rectangle createButton = new Rectangle(Variables.width - Variables.width / 16, Variables.height - Variables.width / 32, Variables.width / 16, Variables.width / 32);
							if(cancelButton.contains(event.getPoint())){
								makingLine = false;
								unpause();
							}
							else if(createButton.contains(event.getPoint())){
								if(line.trace.length < 2){
									errorText = "Line has to contain at least two stations!";
									errorDisappearTime = System.currentTimeMillis() + 4096;
								}
								else if(line.getPrice() > theCity.money){
									errorText = "Not enough money!";
									errorDisappearTime = System.currentTimeMillis() + 2048;
								}
								else{
									theCity.addLine(line);
									theCity.money = line.getPrice();
									makingLine = false;
									unpause();
								}
							}
							else if(expandingLineStart && getStationRing(line.trace[line.trace.length - 1]).contains(event.getPoint())){
								expandingLineStart = false;
							}
							else if((expandingLineStart == false) && getStationRing(line.trace[0]).contains(event.getPoint())){
								expandingLineStart = true;
							}
							else if(stationPlaceAllowed(convertScreenCoorsToMapCoors(event.getPoint())) == false){
								errorText = "Line can't go twice through one station!";
								errorDisappearTime = System.currentTimeMillis() + 2048;
							}
							else{
								if(expandingLineStart){
									line.addStartStation(convertScreenCoorsToMapCoors(event.getPoint()));
								}
								else{
									line.addEndStation(convertScreenCoorsToMapCoors(event.getPoint()));
								}
								addHistoryValue(expandingLineStart);
							}
						}
					}
				}
			}
		}
		else{
			pause.mouseClicked(event);
		}
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
	
	public static Area getStationRing(Point stationCoors){
		stationCoors = convertMapCoorsToScreenCoors(stationCoors);
		Area circle = new Area(new Ellipse2D.Double((int) (stationCoors.x - (sqrt2 * 64 - 64) / 2), (int) (stationCoors.y - (sqrt2 * 64 - 64) / 2), (int) (sqrt2 * 64), (int) (sqrt2 * 64)));
		final Area smallEll = new Area(new Ellipse2D.Double(stationCoors.x, stationCoors.y, 64, 64));
		circle.subtract(smallEll);
		return circle;
	}
	
	public static Point convertMapCoorsToScreenCoors(Point mapCoors){
		return new Point(mapCoors.x * 64 - theCity.mapPosition.x, mapCoors.y * 64 - theCity.mapPosition.y + getControlPHeight() + 39);
	}
	
	public static Point convertScreenCoorsToMapCoors(Point screenCoors){
		screenCoors.y = screenCoors.y - 39 - getControlPHeight();
		final Point mapCoors = new Point((int) Math.floor((screenCoors.x + theCity.mapPosition.x) / (double) 64), (int) Math.floor((screenCoors.y + theCity.mapPosition.y) / (double) 64));
		return mapCoors;
	}

	public static void pause(){
		paused = true;
		//repaint.stop();
		Variables.myGui.repaint();
	}

	public static void unpause(){
		paused = false;
		//repaint.start();
		Variables.myGui.repaint();
	}
	
	public static void mouseDragged(final MouseEvent event){
		if(inPauseMenu == false){
			final int spaceYStart;
			if(Variables.height > 800){
				spaceYStart = 179;
			}
			else{
				spaceYStart = Variables.height / 5 + 19;
			}
			if(showTCW){
				if(event.getY() > spaceYStart){
					if(draggingTCW){
						TCWposition.x = (int) (TCWposition.x + event.getX() - Variables.lastMousePosition.getX());
						TCWposition.y = (int) (TCWposition.y + event.getY() - Variables.lastMousePosition.getY());
					}
					else{
						blinkTCW();					
					}
				}
			}
			else{
				if(event.getX() > 0 && event.getX() < Variables.width && event.getY() > spaceYStart && event.getY() < Variables.height){
					theCity.mapPosition.x = (int) (theCity.mapPosition.x - event.getX() + Variables.lastMousePosition.getX());
					theCity.mapPosition.y = (int) (theCity.mapPosition.y - event.getY() + Variables.lastMousePosition.getY());
				}
			}
		}
	}
	
	private static ActionListener blink = new ActionListener(){
		@Override
		public final void actionPerformed(final ActionEvent arg0)
		{
			TCWframeBlack = (TCWframeBlack == false);
			if(System.currentTimeMillis() >= endTCWBlinking){
				blinking.stop();
				TCWframeBlack = true;
			}
		}
	};
	public final static Timer blinking = new Timer(25, blink);
	
	public static void blinkTCW() {
		endTCWBlinking = System.currentTimeMillis() + 100;
		blinking.start();
	}
}
