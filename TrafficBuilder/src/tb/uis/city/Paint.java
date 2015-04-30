package tb.uis.city;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import tb.ExtendedGraphics2D;
import tb.Main;
import tb.Mathematics;

public class Paint {
	
	static long endStationUpdateTime;
	
	public static void paint(Graphics2D graph2, ExtendedGraphics2D exGraph){
		Components.updateComponents();
		if(Interface.inViewSettings){
			paintViewSettings(graph2, exGraph);
		}
		else{
			drawMap(graph2, exGraph);
			if(Interface.showTCW){
				drawTCW(graph2, exGraph);
			}
			drawPowerLine(graph2);
			drawControlPanel(graph2, exGraph);
			if(Interface.inPauseMenu){
				paintPauseMenu(graph2, exGraph);
			}
		}
	}
	
	public static void paintPauseMenu(Graphics2D graph2, ExtendedGraphics2D exGraph){
		
	}
	
	public static void paintViewSettings(Graphics2D graph2, ExtendedGraphics2D exGraph){
		
	}
	
	public static void drawLines(Graphics2D graph2){
		int indexToHighlight = -1;
		if (Interface.makingLine == false) {
			for(int i = 0; i < Components.lineComponents.length; i++){
				if (Components.lineComponents[i].area.contains(Main.mousePosition)) {
					indexToHighlight = i;
				}
			}
		}
		for(int i = 0; i < Components.lineComponents.length; i++){
			if (i == indexToHighlight){
				graph2.setColor(Main.city.lines[Components.lineComponents[i].lineIndex].lineColor.darker());
			} else {
				graph2.setColor(Main.city.lines[Components.lineComponents[i].lineIndex].lineColor);
			}
			graph2.fill(Components.lineComponents[i].area);
		}
		if (Interface.makingLine) {
			graph2.setColor(Interface.line.lineColor);
			for(int i = 0; i < Components.makingLineComponents.length; i++){
				graph2.fill(Components.makingLineComponents[i]);
			}
			if (Interface.expandingLineStart) {
				drawEndStation(graph2, Interface.line.trace[0]);
			} else {
				drawEndStation(graph2, Interface.line.trace[Interface.line.trace.length - 1]);
			}
		}
	}

	public static void drawControlPanel(Graphics2D graph2, ExtendedGraphics2D exGraph){
		graph2.setColor(Color.black);
		graph2.fill(Components.controlPanel);
		graph2.setColor(Color.blue);
		graph2.fill(Components.controlPanelInside);
		graph2.setColor(Color.white);
		exGraph.drawMaxString(longTimeToDate(Main.city.time), ExtendedGraphics2D.UpRight, Components.timeTextBounds);
		if(Components.pauseButton.contains(Main.mousePosition) && Interface.inPauseMenu == false){
			graph2.setColor(new Color(255, 246, 143));
		}
		else{
			graph2.setColor(Color.white);
		}
		graph2.fill(Components.pauseButton);
		graph2.setColor(Color.blue);
		graph2.fill(Components.pauseStrip1);
		graph2.fill(Components.pauseStrip2);
		exGraph.setColor(new Color(255, 215, 0));
		exGraph.drawMaxString(String.format("%.2f", Main.city.money) + " TBC", ExtendedGraphics2D.Left, Components.moneyBounds);
		if(Components.CCFTButton.contains(Main.mousePosition) && (Interface.inPauseMenu == false)){
			graph2.setColor(new Color(72, 118, 255));
		}
		else{
			graph2.setColor(new Color(99, 184, 255));
		}
		graph2.fill(Components.CCFTButton);
		graph2.setColor(Color.black);
		exGraph.drawMaxString("CCFT", Components.CCFTButtonText);
		if(Components.researchLabButton.contains(Main.mousePosition) && (Interface.inPauseMenu == false)){
			graph2.setColor(new Color(72, 118, 255));
		}
		else{
			graph2.setColor(new Color(155, 48, 255));
		}
		graph2.fill(Components.researchLabButton);
		exGraph.setColor(Color.yellow);
		exGraph.drawMaxString("Research", ExtendedGraphics2D.Down, Components.researchLabButtonText1);
		exGraph.drawMaxString("Lab", ExtendedGraphics2D.Up, Components.researchLabButtonText2);
		if(Components.viewSettingsButton.contains(Main.mousePosition) && (Interface.inPauseMenu == false)){
			graph2.setColor(new Color(210, 210, 210));
		}
		else{
			graph2.setColor(Color.white);
		}
		graph2.fill(Components.viewSettingsButton);
		exGraph.setColor(Color.black);
		exGraph.drawMaxString("View Settings", Components.viewSettingsButtonText);
		if(Interface.makingLine){
			drawPrice(graph2, exGraph);
		}
	}

	public static void drawEndStation(Graphics2D graph2, Point coors){
		coors = Interface.convertMapCoorsToScreenCoors(coors);
		double state = getEndStationState();
		Area circle = new Area(new Ellipse2D.Double(coors.x, coors.y, 64, 64));
		final Area smallEll = new Area(new Ellipse2D.Double(coors.x + (1 - state) * 32, coors.y + (1 - state) * 32, 64 * state, 64 * state));
		circle.subtract(smallEll);
		graph2.setColor(Color.black);
		graph2.fill(circle);
	}

	public static void drawMap(final Graphics2D graph2, ExtendedGraphics2D exGraph){
		graph2.setColor(Color.white);
		final int spaceYStart = Components.controlPanel.height + 39;
		graph2.fillRect(0, spaceYStart, Main.guiWidth, Main.guiHeight - spaceYStart);
		int spaceXUsed = 64 - Mathematics.modulo((int) Main.city.mapPosition.getX(), 64);
		int spaceYUsed = 64 - Mathematics.modulo((int) Main.city.mapPosition.getY(), 64) + spaceYStart;
		int axisX = (int) Math.floor(Main.city.mapPosition.getX() / 64);
		int axisY = (int) Math.floor(Main.city.mapPosition.getY() / 64);
		drawSquarePopulation(graph2, spaceXUsed - 64, spaceYUsed - 64, axisX, axisY);
		axisX++;
		while(spaceXUsed < Main.guiWidth){
			drawSquarePopulation(graph2, spaceXUsed, spaceYUsed - 64, axisX, axisY);
			axisX++;
			spaceXUsed = spaceXUsed + 64;
		}
		axisX = (int) Math.floor(Main.city.mapPosition.getX() / 64);
		axisY++;
		while(spaceYUsed  - 256 < Main.guiHeight){
			spaceXUsed = 64 - Mathematics.modulo((int) Main.city.mapPosition.getX(), 64);
			drawSquarePopulation(graph2, spaceXUsed - 64, spaceYUsed, axisX, axisY);
			axisX++;
			while(spaceXUsed < Main.guiWidth){
				drawSquarePopulation(graph2, spaceXUsed, spaceYUsed, axisX, axisY);
				axisX++;
				spaceXUsed = spaceXUsed + 64;
			}
			axisX = (int) Math.floor(Main.city.mapPosition.getX() / 64);
			axisY++;
			spaceYUsed = spaceYUsed + 64;
		}

		graph2.setColor(Color.lightGray);
		int spaceUsed = 256 - Mathematics.modulo((int) Main.city.mapPosition.getX(), 256);
		graph2.drawLine(spaceUsed, spaceYStart, spaceUsed, Main.guiHeight);
		while(spaceUsed < Main.guiWidth){
			graph2.drawLine(spaceUsed, spaceYStart, spaceUsed, Main.guiHeight);
			spaceUsed = spaceUsed + 256;
		}
		spaceUsed = 256 - Mathematics.modulo((int) Main.city.mapPosition.getY(), 256) + spaceYStart;
		graph2.drawLine(0, spaceUsed, Main.guiWidth, spaceUsed);
		while(spaceUsed < Main.guiHeight){
			graph2.drawLine(0, spaceUsed, Main.guiWidth, spaceUsed);
			spaceUsed = spaceUsed + 256;
		}
		drawLines(graph2);
		if(Interface.makingLine){
			inMakingLineNonCP(graph2, exGraph);
		}
		graph2.setColor(Color.red);
		exGraph.drawMaxString(Components.errorTextBounds.height / 8, Interface.errorText, Components.errorTextBounds);
	}

	public static void drawPowerLine(final Graphics2D graph2){
		int linePosition = Components.controlPanel.height + 20;
		graph2.setColor(Color.white);
		graph2.fillRect(0, linePosition - 15, Main.guiWidth, 30);
		int counter = 0;
		graph2.setColor(Color.red);
		double sin;
		while(counter < Main.guiWidth){
			sin = (int) (Math.sin(Interface.powerLineState / 50 + counter / 10) * 5);
			graph2.drawRect(counter, (int) (linePosition - sin), 1, (int) (sin + 15));
			counter++;
		}
		graph2.setColor(Color.black);
		graph2.fillRect(0, linePosition - 20, Main.guiWidth, 5);
		graph2.fillRect(0, linePosition + 14, Main.guiWidth, 5);
		int blockHeight = 0;
		if(Interface.powerLineState % 3000 > 1000){
			blockHeight = (int) (((Math.abs((Interface.powerLineState % 3000) - 2000) - 500) * -1 + 500) * 0.03);
		}
		graph2.fillRect(0, linePosition - 15, Main.guiWidth % 20 / 2, blockHeight);
		int blockSpaceFilled = Main.guiWidth % 20 / 2;
		boolean nextUp = false;
		while(blockSpaceFilled < Main.guiWidth){
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

	public static void drawPrice(Graphics2D graph2, ExtendedGraphics2D exGraph){
		final int borderSize = Components.controlPanel.height / 10;
		final int linePrice = Interface.line.getPrice();
		final Rectangle priceBounds = Mathematics.addBorders(new Rectangle(borderSize, Components.controlPanel.height / 2, Main.guiWidth / 2 - borderSize, Components.controlPanel.height / 2 - borderSize), (Components.controlPanel.height / 2 - borderSize) / 16);
		graph2.setFont(Main.usingFont.deriveFont(101f));
		final Rectangle stringBounds = exGraph.getStringBounds("Total price: " + String.valueOf(linePrice) + "TBC", 0, 0);
		final float size1Height = stringBounds.height / 101f;
		final float size1Width = stringBounds.width / 101f;
		final float priceBoundsCoeficient = priceBounds.height / (float) priceBounds.width;
		final float textBoundsCoeficient = size1Height / size1Width;
		if(priceBoundsCoeficient > textBoundsCoeficient){
			graph2.setFont(Main.usingFont.deriveFont(priceBounds.width / size1Width));
		}
		else {
			graph2.setFont(Main.usingFont.deriveFont(priceBounds.height / size1Height));
		}
		final Rectangle totalPriceTextRect = exGraph.getStringBounds("Total price: ", 0, 0);
		graph2.setColor(Color.black);
		graph2.drawString("Total price: ", priceBounds.x, priceBounds.y + priceBounds.height - totalPriceTextRect.height - totalPriceTextRect.y);
		if(Main.city.money < linePrice){
			graph2.setColor(Color.red);
		}
		else{
			graph2.setColor(Color.green);
		}
		graph2.drawString(String.valueOf(linePrice) + "TBC", priceBounds.x + totalPriceTextRect.width, priceBounds.y + priceBounds.height - totalPriceTextRect.height - totalPriceTextRect.y);
	}

	public static void drawSquarePopulation(final Graphics2D graph2, final int pixelX, final int pixelY, final int squareX, final int squareY){
		if(Main.city.getPopulation(new Point(squareX, squareY)) != 0){
			final Image texture = tb.data.Cities.getPopulationImage(Main.city.getPopulation(new Point(squareX, squareY)));
			graph2.drawImage(texture, pixelX, pixelY, null);
		}
	}

	public static void drawTCW(Graphics2D graph2, ExtendedGraphics2D exGraph){
		if(Interface.TCWframeBlack){
			graph2.setColor(Color.black);
		}
		else{
			graph2.setColor(Color.white);
		}
		graph2.fill(Components.TCW);
		graph2.setColor(Color.CYAN);
		graph2.fill(Components.TCWInside);
		graph2.setColor(Color.red);
		graph2.fill(Components.TCWClose);
		exGraph.setColor(new Color(205, 133, 0));
		exGraph.drawMaxString("Population lvl. " + Main.city.getPopulation(new Point(Interface.TCWmapX, Interface.TCWmapY)), ExtendedGraphics2D.Left, Components.TCWPopText);
		exGraph.drawChangingRect(Components.TCWCreateLine, Color.green, new Color(0, 200, 0));
		exGraph.setColor(Color.white);
		exGraph.drawMaxString("Create Line", Components.TCWCreateLineText);
	}

	public static double getEndStationState(){
		final int thousands = (int) (endStationUpdateTime * 4 % (2000 * Math.PI));
		final double sinus = Math.round(Math.sin(thousands / (double) (1000)) * 100) / (double) (100);
		return (sinus + 1) / 2;
	}

	public static void inMakingLineNonCP(Graphics2D graph2, ExtendedGraphics2D exGraph){
		graph2.setColor(new Color(255, 255, 255, 127));
		graph2.fill(Components.makingLineCovering);
		exGraph.drawChangingRect(Components.cancelButton, Color.black, new Color(40, 40, 40));
		exGraph.setColor(Color.white);
		exGraph.drawMaxString(Main.guiWidth / 128, "Cancel", Components.cancelButton);
		exGraph.drawChangingRect(Components.createButton, Color.black, new Color(40, 40, 40));
		exGraph.setColor(Color.white);
		exGraph.drawMaxString(Main.guiWidth / 128, "Create!", Components.createButton);
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
