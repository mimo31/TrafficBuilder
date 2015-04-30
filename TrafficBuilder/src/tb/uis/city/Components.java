package tb.uis.city;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;

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
			
			lineComponents = new LineComponentGraphics[0];
			int minXpos = (int) Math.floor((Main.city.mapPosition.x / 64)) - 1;
			int minYpos = (int) Math.floor((Main.city.mapPosition.y / 64)) - 1;
			int maxXpos = (int) Math.floor(((Main.city.mapPosition.x + Main.guiWidth) / 64)) + 1;
			int maxYpos = (int) Math.floor(((Main.city.mapPosition.y + Main.guiHeight - controlPanel.height) / 64)) + 1;
			Rectangle mapRect = new Rectangle(- 64, controlPanel.height - 64, Main.guiWidth + 128, Main.guiHeight - controlPanel.height + 128);
			for(int i = 0; i < Main.city.lines.length; i++){
				for(int j = 0; j < Main.city.lines[i].trace.length; j++){
					boolean insideXbounds = (Main.city.lines[i].trace[j].x >= minXpos) && (Main.city.lines[i].trace[j].x <= maxXpos);
					boolean insideYbounds = (Main.city.lines[i].trace[j].y >= minYpos) && (Main.city.lines[i].trace[j].y <= maxYpos);
					if (insideXbounds && insideYbounds) {
						Area area = Interface.getStationRing(Main.city.lines[i].trace[j]);
						LineComponentGraphics component = new LineComponentGraphics(area, i, 2 * j);
						addLineComponent(component);
					}
				}
				for(int j = 0; j < Main.city.lines[i].trace.length - 1; j++){
					Point line1 = Interface.convertMapCoorsToScreenCoors(Main.city.lines[i].trace[j]);
					line1.x = line1.x + 32;
					line1.y = line1.y + 32;
					Point line2 = Interface.convertMapCoorsToScreenCoors(Main.city.lines[i].trace[j + 1]);
					line2.x = line2.x + 32;
					line2.y = line2.y + 32;
					if (mapRect.intersectsLine(line1.x, line1.y, line2.x, line2.y)) {
						Area area = getTunnelArea(line1, line2);
						LineComponentGraphics component = new LineComponentGraphics(area, i, 2 * j + 1);
						addLineComponent(component);
					}
				}
			}
			
			if (Interface.makingLine) {
				makingLineComponents = new Area[0];
				for(int i = 0; i < Interface.line.trace.length; i++) {
					boolean insideXbounds = (Interface.line.trace[i].x >= minXpos) && (Interface.line.trace[i].x <= maxXpos);
					boolean insideYbounds = (Interface.line.trace[i].y >= minYpos) && (Interface.line.trace[i].y <= maxYpos);
					if (insideXbounds && insideYbounds) {
						addMakingLineComponent(Interface.getStationRing(Interface.line.trace[i]));
					}
				}
				for(int i = 0; i < Interface.line.trace.length - 1; i++){
					Point line1 = Interface.convertMapCoorsToScreenCoors(Interface.line.trace[i]);
					line1.x = line1.x + 32;
					line1.y = line1.y + 32;
					Point line2 = Interface.convertMapCoorsToScreenCoors(Interface.line.trace[i + 1]);
					line2.x = line2.x + 32;
					line2.y = line2.y + 32;
					if (mapRect.intersectsLine(line1.x, line1.y, line2.x, line2.y)) {
						addMakingLineComponent(getTunnelArea(line1, line2));
					}
				}
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
	
	public static void addLineComponent(LineComponentGraphics component){
		LineComponentGraphics[] temp = new LineComponentGraphics[lineComponents.length + 1];
		for(int i = 0; i < lineComponents.length; i++){
			temp[i] = lineComponents[i];
		}
		temp[temp.length - 1] = component;
		lineComponents = temp;
	}
	
	public static void addMakingLineComponent(Area component){
		
	}
	
	public static Area getTunnelArea(Point start, Point end){
		if(start.y != end.y){
			final double tempA = -(end.x - start.x) / (double) (end.y - start.y);
			final double tempB = Math.sqrt(1024 + 1024 * tempA * tempA) / (double) (2 * tempA * tempA + 2);
			final Point p1 = new Point((int) (start.x + tempB), (int) (start.y + tempB * tempA));
			final Point p2 = new Point((int) (start.x - tempB), (int) (start.y - tempB * tempA));
			final Point p3 = new Point((int) (end.x - tempB), (int) (end.y - tempB * tempA));
			final Point p4 = new Point((int) (end.x + tempB), (int) (end.y + tempB * tempA));
			return new Area(new Polygon(new int[]{p1.x, p2.x, p3.x, p4.x}, new int[]{p1.y, p2.y, p3.y, p4.y}, 4));
		} else {
			if (start.x < end.x) {
				return new Area(new Rectangle(start.x, start.y - 16, end.x - start.x, 32));
			} else {
				return new Area(new Rectangle(end.x, start.y - 16, start.x - end.x, 32));
			}
		}
	}
}
