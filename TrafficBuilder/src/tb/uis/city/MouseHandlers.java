package tb.uis.city;

import java.awt.Point;
import java.awt.event.MouseEvent;

import tb.cityType.Line;
import tb.Main;

public class MouseHandlers {
	
	public static void mouseClicked(final MouseEvent event){
		if(Interface.inViewSettings){
			viewSettingsClicked(event);
		} else {
			if(Interface.inPauseMenu){
				pauseMenuClicked(event);
			} else {
				if(event.getY() > Components.controlPanel.height + 39){
					mapClicked(event);	
				} else {
					controlPanelClicked(event);
				}
			}
		}
	}
	
	public static void pauseMenuClicked(MouseEvent event){
		
	}
	
	public static void viewSettingsClicked(MouseEvent event){
		
	}
	
	public static void controlPanelClicked(MouseEvent event){
		if(Components.pauseButton.contains(event.getPoint())){
			Interface.inPauseMenu = true;
			Interface.paused = true;
		}
		else if(Components.viewSettingsButton.contains(event.getPoint())){
			Interface.inViewSettings = true;
			Interface.paused = true;
		}
	}
	
	public static void mapClicked(MouseEvent event){
		if(Interface.showTCW){
			if(Components.TCW.contains(event.getPoint())){
				if(Components.TCWClose.contains(event.getPoint())){
					Interface.showTCW = false;
				}
				else if(Components.TCWCreateLine.contains(event.getPoint())){
					Interface.paused = true;
					Interface.makingLine = true;
					Interface.makingHistory = new boolean[0];
					final int codeNum = Main.city.getNewLineCodeNumber();
					Interface.line = new Line(new Point[]{new Point(Interface.TCWmapX, Interface.TCWmapY)}, Main.city.getNewLineCodeChar(), codeNum);
					Interface.line.lineColor = Main.city.getNewLineColor();
					Interface.showTCW = false;
				}
			}
			else{
				Interface.showTCW = false;
			}
		}
		else{
			if(Interface.makingLine == false){
				Interface.showTCW = true;
				if(Main.guiWidth > Main.guiHeight){
					Interface.TCWwidth = Main.guiHeight / 4;
				}
				else{
					Interface.TCWwidth = Main.guiWidth / 4;
				}
				Interface.TCWposition = event.getPoint();
				Interface.TCWposition.y = Interface.TCWposition.y - Components.controlPanel.height;
				Point TCWmap = Interface.convertScreenCoorsToMapCoors(event.getPoint());
				Interface.TCWmapX = TCWmap.x;
				Interface.TCWmapY = TCWmap.y;
			}
			else{
				if(Components.cancelButton.contains(event.getPoint())){
					Interface.makingLine = false;
					Interface.paused = false;
				}
				else if(Components.createButton.contains(event.getPoint())){
					if(Interface.line.trace.length < 2){
						Interface.errorText = "Line has to contain at least two stations!";
						Interface.errorDisappearTime = System.currentTimeMillis() + 4096;
					}
					else if(Interface.line.getPrice() > Main.city.money){
						Interface.errorText = "Not enough money!";
						Interface.errorDisappearTime = System.currentTimeMillis() + 2048;
					}
					else{
						Main.city.addLine(Interface.line);
						Main.city.money = Main.city.money - Interface.line.getPrice();
						Interface.makingLine = false;
						Interface.paused = false;
					}
				}
				else if(Interface.expandingLineStart && Interface.getStationRing(Interface.line.trace[Interface.line.trace.length - 1]).contains(event.getPoint())){
					Interface.expandingLineStart = false;
				}
				else if((Interface.expandingLineStart == false) && Interface.getStationRing(Interface.line.trace[0]).contains(event.getPoint())){
					Interface.expandingLineStart = true;
				}
				else if(Interface.stationPlaceAllowed(Interface.convertScreenCoorsToMapCoors(event.getPoint())) == false){
					Interface.errorText = "Line can't go twice through one station!";
					Interface.errorDisappearTime = System.currentTimeMillis() + 2048;
				}
				else{
					if(Interface.expandingLineStart){
						Interface.line.addStartStation(Interface.convertScreenCoorsToMapCoors(event.getPoint()));
					} else {
						Interface.line.addEndStation(Interface.convertScreenCoorsToMapCoors(event.getPoint()));
					}
					Interface.addHistoryValue(Interface.expandingLineStart);
				}
			}
		}
	}
	
	public static void mouseDragged(final MouseEvent event){
		if(Interface.inPauseMenu == false){
			if(event.getY() > Components.controlPanel.height){
				if(Interface.showTCW){
					if(Interface.draggingTCW){
						Interface.TCWposition.x = (int) (Interface.TCWposition.x + event.getX() - Main.mousePosition.getX());
						Interface.TCWposition.y = (int) (Interface.TCWposition.y + event.getY() - Main.mousePosition.getY());
					} else {
						Interface.endTCWBlinking = System.currentTimeMillis() + 100;			
					}
				
				} else {
					Main.city.mapPosition.x = (int) (Main.city.mapPosition.x - event.getX() + Main.mousePosition.getX());
					Main.city.mapPosition.y = (int) (Main.city.mapPosition.y - event.getY() + Main.mousePosition.getY());
				}
			}
		}
	}
	
	public static void mousePressed(MouseEvent event){
		if(Interface.showTCW){
			if(Components.TCW.contains(event.getPoint())){
				Interface.draggingTCW = true;
			}
		}
	}

	public static void mouseRelesed(MouseEvent event){
		Interface.draggingTCW = false;
	}
}
