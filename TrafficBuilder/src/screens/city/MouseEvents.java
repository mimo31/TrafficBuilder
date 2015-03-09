package screens.city;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import mainPackage.Functions;
import mainPackage.Variables;

public class MouseEvents extends City {

	public static void mouseClicked(final MouseEvent event){
		if(inPauseMenu){
			Pause.mouseClicked(event);
		}
		
		else{
			if(event.getY() > controlPanel.height + 39){
				mapClicked(event);	
			}
			else{
				controlPanelClicked(event);
			}
		}
	}
	
	public static void controlPanelClicked(MouseEvent event){
		if(pauseButton.contains(event.getPoint())){
			inPauseMenu = true;
			paused = true;
		}
		else if(viewSettingsButton.contains(event.getPoint())){
			inViewSettings = true;
			paused = true;
		}
	}
	
	public static void mapClicked(MouseEvent event){
		if(showTCW){
			if(TCW.contains(event.getPoint())){
				if(TCWClose.contains(event.getPoint())){
					showTCW = false;
				}
				else if(TCWCreateLine.contains(event.getPoint())){
					paused = true;
					makingLine = true;
					makingHistory = new boolean[0];
					final int codeNum = theCity.getNewLineCodeNumber();
					line = new Line(new Point[]{new Point(TCWmapX, TCWmapY)}, theCity.getNewLineCodeChar(), codeNum);
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
				TCWposition.y = TCWposition.y - controlPanel.height;
				final int mapMoveFromLastPointX = Functions.modulo((int) theCity.mapPosition.getX(), 64);
				final int mapMoveFromLastPointY = Functions.modulo((int) theCity.mapPosition.getY(), 64);
				final int lastPointX = (int) Math.floor(theCity.mapPosition.getX() / 64);
				final int lastPointY = (int) Math.floor(theCity.mapPosition.getY() / 64);
				TCWmapX = (int) (lastPointX + Math.floor((mapMoveFromLastPointX + event.getX()) / 64));
				TCWmapY = (int) (lastPointY + Math.floor((mapMoveFromLastPointY + event.getY() - controlPanel.height - 39) / 64));
			}
			else{
				if(cancelButton.contains(event.getPoint())){
					makingLine = false;
					paused = false;
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
						theCity.money = theCity.money - line.getPrice();
						makingLine = false;
						paused = false;
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
						endTCWBlinking = System.currentTimeMillis() + 100;			
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
}
