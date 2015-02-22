package screens.City;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

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
	
	private static ActionListener timerAction = new ActionListener(){
		@Override
		public final void actionPerformed(final ActionEvent arg0)
		{
			Variables.myGui.repaint();
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
							final Rectangle CLButton = new Rectangle(TCWposition.x + borderSize, TCWposition.y + controlPanelHeight + TCWwidth * 2 - borderSize - TCWwidth / 8, TCWwidth - 2 * borderSize, TCWwidth / 8);
							if(Functions.buttonClicked(event, TCWposition.x + TCWwidth - 3 * TCWborderSize, TCWposition.y + controlPanelHeight + TCWborderSize, 2 * TCWborderSize, 2 * TCWborderSize)){
								showTCW = false;
							}
							else if(CLButton.contains(event.getPoint())){
								pause();
								makingLine = true;
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
								makingLine = false;
								unpause();
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
