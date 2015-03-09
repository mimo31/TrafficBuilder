package screens.city;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import mainPackage.Functions;
import mainPackage.StringDraw;
import mainPackage.Variables;
import data.ResourceHandler;

public class PaintCity extends City {
	
	static long endStationUpdateTime;
	
	public static void drawAllLines(Graphics2D graph2){
		int counter = 0;
		while(counter < theCity.lines.length){
			drawLine(graph2, theCity.lines[counter]);
			counter++;
		}
	}

	public static void drawControlPanel(Graphics2D graph2){
		graph2.setColor(Color.black);
		graph2.fill(controlPanel);
		graph2.setColor(Color.blue);
		graph2.fill(controlPanelInside);
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, longTimeToDate(theCity.time), StringDraw.UpRight, timeTextBounds);
		if(pauseButton.contains(Variables.lastMousePosition) && inPauseMenu == false){
			graph2.setColor(new Color(255, 246, 143));
		}
		else{
			graph2.setColor(Color.white);
		}
		graph2.fill(pauseButton);
		graph2.setColor(Color.blue);
		graph2.fill(pauseStrip1);
		graph2.fill(pauseStrip2);
		graph2.setColor(new Color(255, 215, 0));
		StringDraw.drawMaxString(graph2, String.format("%.2f", theCity.money) + " TBC", StringDraw.Left, moneyBounds);
		if(CCFTButton.contains(Variables.lastMousePosition) && (inPauseMenu == false)){
			graph2.setColor(new Color(72, 118, 255));
		}
		else{
			graph2.setColor(new Color(99, 184, 255));
		}
		graph2.fill(CCFTButton);
		graph2.setColor(Color.black);
		StringDraw.drawMaxString(graph2, "CCFT", CCFTButtonText);
		if(researchLabButton.contains(Variables.lastMousePosition) && (inPauseMenu == false)){
			graph2.setColor(new Color(72, 118, 255));
		}
		else{
			graph2.setColor(new Color(155, 48, 255));
		}
		graph2.fill(researchLabButton);
		graph2.setColor(Color.yellow);
		StringDraw.drawMaxString(graph2, "Research", StringDraw.Down, researchLabButtonText1);
		StringDraw.drawMaxString(graph2, "Lab", StringDraw.Up, researchLabButtonText2);
		if(viewSettingsButton.contains(Variables.lastMousePosition) && (inPauseMenu == false)){
			graph2.setColor(new Color(210, 210, 210));
		}
		else{
			graph2.setColor(Color.white);
		}
		graph2.fill(viewSettingsButton);
		graph2.setColor(Color.black);
		StringDraw.drawMaxString(graph2, "View Settings", viewSettingsButtonText);
		if(makingLine){
			drawPrice(graph2);
		}
	}

	public static void drawEndStation(Graphics2D graph2, Point coors, Color lineColor){
		drawStation(graph2, coors, lineColor);
		coors = convertMapCoorsToScreenCoors(coors);
		double state = getEndStationState();
		Area circle = new Area(new Ellipse2D.Double(coors.x, coors.y, 64, 64));
		final Area smallEll = new Area(new Ellipse2D.Double(coors.x + (1 - state) * 32, coors.y + (1 - state) * 32, 64 * state, 64 * state));
		circle.subtract(smallEll);
		graph2.setColor(Color.black);
		graph2.fill(circle);
	}

	public static void drawLine(Graphics2D graph2, Line lineToDraw){
		int counter = 1;
		if(isStationInWindow(lineToDraw.trace[0])){
			drawStation(graph2, lineToDraw.trace[0], lineToDraw.lineColor);
		}
		while(counter < lineToDraw.trace.length){
			if(isStationInWindow(lineToDraw.trace[counter])){
				drawStation(graph2, lineToDraw.trace[counter], lineToDraw.lineColor);
			}
			if(haveToDrawConnection(lineToDraw.trace[counter], lineToDraw.trace[counter - 1])){
				drawStationConnection(graph2, lineToDraw.trace[counter], lineToDraw.trace[counter - 1], lineToDraw.lineColor);
			}
			counter++;
		}
	}

	public static void drawMap(final Graphics2D graph2){
		graph2.setColor(new Color(255, 255, 255));
		final int spaceYStart = controlPanel.height + 39;
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
		drawAllLines(graph2);
		if(makingLine){
			inMakingLineNonCP(graph2);
		}
		graph2.setColor(Color.red);
		StringDraw.drawMaxString(graph2, errorTextBounds.height / 8, errorText, errorTextBounds);
	}

	public static void drawPowerLine(final Graphics2D graph2){
		int linePosition = controlPanel.height + 20;
		graph2.setColor(Color.white);
		graph2.fillRect(0, linePosition - 15, Variables.width, 30);
		int counter = 0;
		graph2.setColor(Color.red);
		double sin;
		while(counter < Variables.width){
			sin = (int) (Math.sin(powerLineState / 50 + counter / 10) * 5);
			graph2.drawRect(counter, (int) (linePosition - sin), 1, (int) (sin + 15));
			counter++;
		}
		graph2.setColor(Color.black);
		graph2.fillRect(0, linePosition - 20, Variables.width, 5);
		graph2.fillRect(0, linePosition + 14, Variables.width, 5);
		int blockHeight = 0;
		if(powerLineState % 3000 > 1000){
			blockHeight = (int) (((Math.abs((powerLineState % 3000) - 2000) - 500) * -1 + 500) * 0.03);
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

	public static void drawPrice(Graphics2D graph2){
		final int borderSize = controlPanel.height / 10;
		final int linePrice = line.getPrice();
		final Rectangle priceBounds = Functions.addBorders(new Rectangle(borderSize, controlPanel.height / 2, Variables.width / 2 - borderSize, controlPanel.height / 2 - borderSize), (controlPanel.height / 2 - borderSize) / 16);
		graph2.setFont(Variables.nowUsingFont.deriveFont(101f));
		final Rectangle stringBounds = StringDraw.getStringBounds(graph2, "Total price: " + String.valueOf(linePrice) + "TBC", 0, 0);
		final float size1Height = stringBounds.height / 101f;
		final float size1Width = stringBounds.width / 101f;
		final float priceBoundsCoeficient = priceBounds.height / (float) priceBounds.width;
		final float textBoundsCoeficient = size1Height / size1Width;
		if(priceBoundsCoeficient > textBoundsCoeficient){
			graph2.setFont(Variables.nowUsingFont.deriveFont(priceBounds.width / size1Width));
		}
		else {
			graph2.setFont(Variables.nowUsingFont.deriveFont(priceBounds.height / size1Height));
		}
		final Rectangle totalPriceTextRect = StringDraw.getStringBounds(graph2, "Total price: ", 0, 0);
		graph2.setColor(Color.black);
		graph2.drawString("Total price: ", priceBounds.x, priceBounds.y + priceBounds.height - totalPriceTextRect.height - totalPriceTextRect.y);
		if(theCity.money < linePrice){
			graph2.setColor(Color.red);
		}
		else{
			graph2.setColor(Color.green);
		}
		graph2.drawString(String.valueOf(linePrice) + "TBC", priceBounds.x + totalPriceTextRect.width, priceBounds.y + priceBounds.height - totalPriceTextRect.height - totalPriceTextRect.y);
	}

	public static void drawSquarePopulation(final Graphics2D graph2, final int pixelX, final int pixelY, final int squareX, final int squareY){
		if(theCity.getPopulation(new Point(squareX, squareY)) != 0){
			final Image texture = ResourceHandler.getPopulationImage(theCity.getPopulation(new Point(squareX, squareY)));
			graph2.drawImage(texture, pixelX, pixelY, null);
		}
	}

	public static void drawStation(Graphics2D graph2, Point coors, Color lineColor){
		graph2.setColor(lineColor);
		graph2.fill(getStationRing(coors));
	}

	public static void drawStationConnection(Graphics2D graph2, final Point mapCoors1, final Point mapCoors2, final Color lineColor){
		Point screenCoors1 = convertMapCoorsToScreenCoors(mapCoors1);
		screenCoors1.x = screenCoors1.x + 32;
		screenCoors1.y = screenCoors1.y + 32;
		Point screenCoors2 = convertMapCoorsToScreenCoors(mapCoors2);
		screenCoors2.x = screenCoors2.x + 32;
		screenCoors2.y = screenCoors2.y + 32;
		Area tunnelPolygon;
		if(screenCoors1.y != screenCoors2.y){
			final double tempA = -(screenCoors2.x - screenCoors1.x) / (double) (screenCoors2.y - screenCoors1.y);
			final double tempB = Math.sqrt(1024 + 1024 * tempA * tempA) / (double) (2 * tempA * tempA + 2);
			final Point p1 = new Point((int) (screenCoors1.x + tempB), (int) (screenCoors1.y + tempB * tempA));
			final Point p2 = new Point((int) (screenCoors1.x - tempB), (int) (screenCoors1.y - tempB * tempA));
			final Point p3 = new Point((int) (screenCoors2.x - tempB), (int) (screenCoors2.y - tempB * tempA));
			final Point p4 = new Point((int) (screenCoors2.x + tempB), (int) (screenCoors2.y + tempB * tempA));
			tunnelPolygon = new Area(new Polygon(new int[]{p1.x, p2.x, p3.x, p4.x}, new int[]{p1.y, p2.y, p3.y, p4.y}, 4));
		}
		else{
			if(screenCoors1.x < screenCoors2.x){
				tunnelPolygon = new Area(new Rectangle(screenCoors1.x, screenCoors1.y - 16, screenCoors2.x - screenCoors1.x, 32));
			}
			else{
				tunnelPolygon = new Area(new Rectangle(screenCoors2.x, screenCoors1.y - 16, screenCoors1.x - screenCoors2.x, 32));
			}
		}
		screenCoors1.x = screenCoors1.x - 32;
		screenCoors1.y = screenCoors1.y - 32;
		screenCoors2.x = screenCoors2.x - 32;
		screenCoors2.y = screenCoors2.y - 32;
		final Area circle1 = new Area(new Ellipse2D.Double(screenCoors1.x, screenCoors1.y, 64, 64));
		final Area circle2 = new Area(new Ellipse2D.Double(screenCoors2.x, screenCoors2.y, 64, 64));
		tunnelPolygon.subtract(circle1);
		tunnelPolygon.subtract(circle2);
		graph2.setColor(lineColor);
		graph2.fill(tunnelPolygon);
	}

	public static void drawTCW(Graphics2D graph2){
		if(TCWframeBlack){
			graph2.setColor(Color.black);
		}
		else{
			graph2.setColor(Color.white);
		}
		graph2.fill(TCW);
		graph2.setColor(Color.CYAN);
		graph2.fill(TCWInside);
		graph2.setColor(Color.red);
		graph2.fill(TCWClose);
		graph2.setColor(new Color(205, 133, 0));
		StringDraw.drawMaxString(graph2, "Population lvl. " + theCity.getPopulation(new Point(TCWmapX, TCWmapY)), StringDraw.Left, TCWPopText);
		graph2.setColor(Color.green);
		Functions.drawChangRect(graph2, Color.green, new Color(0, 200, 0), TCWCreateLine);
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, "Create Line", TCWCreateLineText);
	}

	public static double getEndStationState(){
		final int thousands = (int) (endStationUpdateTime * 4 % (2000 * Math.PI));
		final double sinus = Math.round(Math.sin(thousands / (double) (1000)) * 100) / (double) (100);
		return (sinus + 1) / 2;
	}

	public static boolean haveToDrawConnection(Point station1, Point station2){
		final Rectangle viewRect = new Rectangle(theCity.mapPosition.x - 32, theCity.mapPosition.y - 32, Variables.width + 64, Variables.height - controlPanel.height - 39 + 64);
		final Point screenCoors1 = new Point(station1.x * 64 + 32, station1.y * 64 + 32);
		final Point screenCoors2 = new Point(station2.x * 64 + 32, station2.y * 64 + 32);
		return viewRect.intersectsLine(new Line2D.Double(screenCoors1, screenCoors2));
	}

	public static void inMakingLineNonCP(Graphics2D graph2){
		graph2.setColor(new Color(255, 255, 255, 127));
		graph2.fill(makingLineCovering);
		if(isStationInWindow(line.trace[0])){
			if(expandingLineStart){
				drawEndStation(graph2, line.trace[0], line.lineColor);
			}
			else{
				if(line.trace.length == 1){
					drawEndStation(graph2, line.trace[0], line.lineColor);
				}
				else{
					drawStation(graph2, line.trace[0], line.lineColor);
				}
			}
		}
		int lastStationDrawn = 0;
		while(lastStationDrawn + 1 < line.trace.length){
			if(isStationInWindow(line.trace[lastStationDrawn + 1])){
				if(lastStationDrawn + 2 == line.trace.length && expandingLineStart == false){
					drawEndStation(graph2, line.trace[lastStationDrawn + 1], line.lineColor);
				}
				else{
					drawStation(graph2, line.trace[lastStationDrawn + 1], line.lineColor);
				}
			}
			if(haveToDrawConnection(line.trace[lastStationDrawn], line.trace[lastStationDrawn + 1])){
				drawStationConnection(graph2, line.trace[lastStationDrawn + 1], line.trace[lastStationDrawn], line.lineColor);
			}
			lastStationDrawn++;
		}
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), cancelButton);
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, Variables.width / 128, "Cancel", cancelButton);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), createButton);
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, Variables.width / 128, "Create!", createButton);
	}

	public static boolean isStationInWindow(Point mapCoors){
		final Point allowedRectStart = new Point((int) Math.floor(theCity.mapPosition.x / 64) - 1, (int) Math.floor(theCity.mapPosition.y / 64) - 1);
		final Point allowedRectEnd = new Point((int) Math.floor((theCity.mapPosition.x + Variables.width) / 64) + 1, (int) Math.floor((theCity.mapPosition.y + Variables.height) / 64) + 1);
		return (mapCoors.x >= allowedRectStart.x && mapCoors.y >= allowedRectStart.y && mapCoors.x <= allowedRectEnd.x && mapCoors.y <= allowedRectEnd.y);
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

	public static void paint(Graphics2D graph2){
		updateComponents();
		if(inViewSettings){
			ViewSettings.paint(graph2);
		}
		else{
			drawMap(graph2);
			if(showTCW){
				drawTCW(graph2);
			}
			drawPowerLine(graph2);
			drawControlPanel(graph2);
			if(inPauseMenu){
				Pause.paint(graph2);
			}
		}
	}
}
