package tb.uis.city;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import tb.Main;
import tb.Mathematics;

public class Components {
	
	static Rectangle controlPanel;
	static Rectangle controlPanelInside;
	static Rectangle pauseButton;
	static Rectangle pauseStrip1;
	static Rectangle pauseStrip2;
	static Rectangle moneyBounds;
	static Rectangle errorTextBounds;
	static Rectangle cancelButton;
	static Rectangle cancelButtonText;
	static Rectangle createButton;
	static Rectangle createButtonText;
	static Rectangle makingLineCovering;
	static Rectangle CCFTButton;
	static Rectangle CCFTButtonText;
	static Rectangle researchLabButton;
	static Rectangle researchLabButtonText1;
	static Rectangle researchLabButtonText2;
	static Rectangle viewSettingsButton;
	static Rectangle viewSettingsButtonText;
	static Rectangle timeTextBounds;
	static Rectangle TCW;
	static Rectangle TCWInside;
	static Rectangle TCWClose;
	static Rectangle TCWCreateLine;
	static Rectangle TCWCreateLineText;
	static Rectangle TCWPopText;
	
	static Rectangle backToCity;
	static Rectangle enterSettings;
	static Rectangle goToTitle;
	
	static Rectangle VSLines;
	static Rectangle VSBack;
	
	static LineComponentGraphics[] lineComponents;
	static Area[] makingLineComponents;
	
	public static void addLineComponent(LineComponentGraphics component){
		LineComponentGraphics[] temp = new LineComponentGraphics[lineComponents.length + 1];
		for(int i = 0; i < lineComponents.length; i++){
			temp[i] = lineComponents[i];
		}
		temp[temp.length - 1] = component;
		lineComponents = temp;
	}
	
	public static void addMakingLineComponent(Area component){
		Area[] temp = new Area[makingLineComponents.length + 1];
		for(int i = 0; i < makingLineComponents.length; i++){
			temp[i] = makingLineComponents[i];
		}
		temp[temp.length - 1] = component;
		makingLineComponents = temp;
	}
	
	public static void addMLstation(int stationIndex, int minXpos, int minYpos, int maxXpos, int maxYpos) {
		if (haveToDrawStation(Interface.line.trace[stationIndex], minXpos, minYpos, maxXpos, maxYpos)) {
			Area area = Interface.getStationRing(Interface.line.trace[stationIndex]);
			addMakingLineComponent(area);
		}
	}
	
	public static void addMLtunnel(int stationIndex, Rectangle mapRect) {
		Point line1 = convertStationToMiddlePoint(Interface.line.trace[stationIndex]);
		Point line2 = convertStationToMiddlePoint(Interface.line.trace[stationIndex + 1]);
		if (mapRect.intersectsLine(line1.x, line1.y, line2.x, line2.y)) {
			Area area = getTunnelArea(line1, line2);
			addMakingLineComponent(area);
		}
	}
	
	public static void addNonMLstation(int lineIndex, int stationIndex, int minXpos, int minYpos, int maxXpos, int maxYpos) {
		if (haveToDrawStation(Main.city.lines[lineIndex].trace[stationIndex], minXpos, minYpos, maxXpos, maxYpos)) {
			Area area = Interface.getStationRing(Main.city.lines[lineIndex].trace[stationIndex]);
			LineComponentGraphics component = new LineComponentGraphics(area, lineIndex, 2 * stationIndex);
			addLineComponent(component);
		}
	}
	
	public static void addNonMLtunnel(int lineIndex, int stationIndex, Rectangle mapRect) {
		Point line1 = convertStationToMiddlePoint(Main.city.lines[lineIndex].trace[stationIndex]);
		Point line2 = convertStationToMiddlePoint(Main.city.lines[lineIndex].trace[stationIndex + 1]);
		if (mapRect.intersectsLine(line1.x, line1.y, line2.x, line2.y)) {
			Area area = getTunnelArea(line1, line2);
			LineComponentGraphics component = new LineComponentGraphics(area, lineIndex, 2 * stationIndex + 1);
			addLineComponent(component);
		}
	}
	
	public static Point convertStationToMiddlePoint(Point station){
		Point result = Interface.convertMapCoorsToScreenCoors(station);
		result.x = result.x + 32;
		result.y = result.y + 32;
		return result;
	}
	
	public static Area getTunnelArea(Point start, Point end){
		Area tunnel;
		if(start.y != end.y){
			final double tempA = -(end.x - start.x) / (double) (end.y - start.y);
			final double tempB = Math.sqrt(1024 + 1024 * tempA * tempA) / (double) (2 * tempA * tempA + 2);
			final Point p1 = new Point((int) (start.x + tempB), (int) (start.y + tempB * tempA));
			final Point p2 = new Point((int) (start.x - tempB), (int) (start.y - tempB * tempA));
			final Point p3 = new Point((int) (end.x - tempB), (int) (end.y - tempB * tempA));
			final Point p4 = new Point((int) (end.x + tempB), (int) (end.y + tempB * tempA));
			tunnel =  new Area(new Polygon(new int[]{p1.x, p2.x, p3.x, p4.x}, new int[]{p1.y, p2.y, p3.y, p4.y}, 4));
		} else {
			if (start.x < end.x) {
				tunnel = new Area(new Rectangle(start.x, start.y - 16, end.x - start.x, 32));
			} else {
				tunnel =  new Area(new Rectangle(end.x, start.y - 16, start.x - end.x, 32));
			}
		}
		Area station1 = new Area(new Ellipse2D.Double(start.x - 32, start.y - 32, 64, 64));
		Area station2 = new Area(new Ellipse2D.Double(end.x - 32, end.y - 32, 64, 64));
		tunnel.subtract(station1);
		tunnel.subtract(station2);
		return tunnel;
	}
	
	public static boolean haveToDrawStation(Point station, int minXpos, int minYpos, int maxXpos, int maxYpos) {
		boolean insideXbounds = (station.x >= minXpos) && (station.x <= maxXpos);
		boolean insideYbounds = (station.y >= minYpos) && (station.y <= maxYpos);
		return insideXbounds && insideYbounds;
	}
	
	public static void updateComponents(){
		if(Interface.inViewSettings){
			VSLines = new Rectangle(0, Main.guiHeight / 6, Main.guiWidth, Main.guiHeight - Main.guiHeight / 6);
			VSBack = new Rectangle(Main.guiWidth / 200, Main.guiHeight / 200, Main.guiWidth / 16, Main.guiHeight / 24);
		} else {
			if(Main.guiHeight > 800){
				controlPanel = new Rectangle(0, 0, Main.guiWidth, 140);
			}
			else{
				controlPanel = new Rectangle(0, 0, Main.guiWidth, Main.guiHeight / 5 - 20);
			}
			int controlPanelBorder = controlPanel.height / 10;
			controlPanelInside = Mathematics.addBorders(controlPanel, controlPanelBorder);
			
			int pauseButtonSize = controlPanelInside.height / 2;
			pauseButton = new Rectangle(controlPanelInside.x + controlPanelInside.width - pauseButtonSize, controlPanelInside.y + pauseButtonSize, pauseButtonSize, pauseButtonSize);
			
			pauseStrip1 = new Rectangle(pauseButton.x + pauseButton.width / 5, pauseButton.y + pauseButton.width / 5, pauseButton.width / 5, pauseButton.width / 5 * 3);
			pauseStrip2 = new Rectangle(pauseButton.x + pauseButton.width / 5 * 3, pauseButton.y + pauseButton.width / 5, pauseButton.width / 5, pauseButton.width / 5 * 3);
			
			moneyBounds = Mathematics.addBorders(new Rectangle(controlPanelBorder, controlPanelBorder, controlPanelInside.width / 2, controlPanelInside.height / 2), controlPanelInside.height / 16);
			
			errorTextBounds = new Rectangle(Main.guiWidth / 16, Main.guiHeight * 5 / 6, Main.guiWidth - Main.guiWidth / 8, Main.guiHeight / 6);
			errorTextBounds = Mathematics.addBorders(errorTextBounds, errorTextBounds.height / 8);
			
			if(Interface.makingLine){
				cancelButton = new Rectangle(0, Main.guiHeight - Main.guiWidth / 32, Main.guiWidth / 16, Main.guiWidth / 32);
				cancelButtonText = Mathematics.addBorders(cancelButton, Main.guiWidth / 128);
				createButton = new Rectangle(Main.guiWidth - Main.guiWidth / 16, Main.guiHeight - Main.guiWidth / 32, Main.guiWidth / 16, Main.guiWidth / 32);
				createButtonText = Mathematics.addBorders(createButton, Main.guiWidth / 128);
				makingLineCovering = new Rectangle(0, controlPanel.height + 39, Main.guiWidth, Main.guiHeight - controlPanel.height + 39);
			}
			
			
			int controlButtonsSize = controlPanelInside.height * 3 / 4;
			CCFTButton = new Rectangle(Main.guiWidth / 2 - controlButtonsSize, controlPanelBorder, controlButtonsSize, controlButtonsSize);
			CCFTButtonText = Mathematics.addBorders(CCFTButton, controlButtonsSize / 8);
			researchLabButton = new Rectangle(Main.guiWidth / 2, controlPanelBorder, controlButtonsSize, controlButtonsSize);
			researchLabButtonText1 = Mathematics.addBorders(new Rectangle(Main.guiWidth / 2, controlPanelBorder, controlButtonsSize, controlButtonsSize / 2), controlButtonsSize / 16);
			researchLabButtonText2 = Mathematics.addBorders(new Rectangle(Main.guiWidth / 2, controlPanelBorder + controlButtonsSize / 2, controlButtonsSize, controlButtonsSize / 2), controlButtonsSize / 16);
			viewSettingsButton = new Rectangle(Main.guiWidth / 2 - controlButtonsSize, controlPanelBorder + controlButtonsSize, controlButtonsSize * 2, controlPanelInside.height / 4);
			viewSettingsButtonText = Mathematics.addBorders(viewSettingsButton, viewSettingsButton.height / 8);
			
			timeTextBounds = new Rectangle(controlPanelBorder + controlPanelInside.width * 2 / 3, controlPanelBorder, controlPanelInside.width / 3, controlPanelInside.height / 3);
			timeTextBounds = Mathematics.addBorders(timeTextBounds, 3);
			
			makingLineComponents = new Area[0];
			lineComponents = new LineComponentGraphics[0];
			int minXpos = (int) Math.floor((Main.city.mapPosition.x / 64)) - 1;
			int minYpos = (int) Math.floor((Main.city.mapPosition.y / 64)) - 1;
			int maxXpos = (int) Math.floor(((Main.city.mapPosition.x + Main.guiWidth) / 64)) + 1;
			int maxYpos = (int) Math.floor(((Main.city.mapPosition.y + Main.guiHeight - controlPanel.height) / 64)) + 1;
			Rectangle mapRect = new Rectangle(- 64, controlPanel.height - 64, Main.guiWidth + 128, Main.guiHeight - controlPanel.height + 128);
			for(int i = 0; i < Main.city.lines.length; i++){
				for(int j = 0; j < Main.city.lines[i].trace.length - 1; j++){
					addNonMLstation(i, j, minXpos, minYpos, maxXpos, maxYpos);
					addNonMLtunnel(i, j, mapRect);
				}
				addNonMLstation(i, Main.city.lines[i].trace.length - 1, minXpos, minYpos, maxXpos, maxYpos);
			}
			if (Interface.makingLine) {
				for(int i = 0; i < Interface.line.trace.length - 1; i++){
					addMLstation(i, minXpos, minYpos, maxXpos, maxYpos);
					addMLtunnel(i, mapRect);
				}
				addMLstation(Interface.line.trace.length - 1, minXpos, minYpos, maxXpos, maxYpos);
			}
			if(Interface.showTCW){
				int TCWBorderSize = Interface.TCWwidth / 16;
				TCW = new Rectangle(Interface.TCWposition.x, Interface.TCWposition.y + controlPanel.height, Interface.TCWwidth, Interface.TCWwidth * 2);
				TCWInside = Mathematics.addBorders(TCW, TCWBorderSize);
				TCWClose = new Rectangle(TCW.x + Interface.TCWwidth - 3 * TCWBorderSize, TCW.y + TCWBorderSize, TCWBorderSize * 2, TCWBorderSize * 2);
				TCWCreateLine = new Rectangle(TCWInside.x, TCW.y + TCW.height - 3 * TCWBorderSize, TCWInside.width, Interface.TCWwidth / 8);
				TCWCreateLineText = Mathematics.addBorders(TCWCreateLine, Interface.TCWwidth / 64);
				TCWPopText = new Rectangle(TCWInside.x, TCW.y + TCWBorderSize * 4, TCWInside.width, TCWBorderSize * 2);
				TCWPopText = Mathematics.addBorders(TCWPopText, 2);
			}
			
			if(Interface.inPauseMenu){
				backToCity = new Rectangle(Main.guiWidth / 4, Main.guiHeight / 4, Main.guiWidth / 2, Main.guiHeight / 16);
				enterSettings = new Rectangle(Main.guiWidth / 4, Main.guiHeight / 32 * 11, Main.guiWidth / 2, Main.guiHeight / 16);
				goToTitle = new Rectangle(Main.guiWidth / 4, Main.guiHeight / 16 * 7, Main.guiWidth / 2, Main.guiHeight / 16);
			}
		}
	}
}
