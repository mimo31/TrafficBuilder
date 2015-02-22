package screens.City;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import data.ResourceHandler;
import mainPackage.Functions;
import mainPackage.StringDraw;
import mainPackage.Variables;

public class PaintCity extends city {
	public static void paint(Graphics2D graph2){
		drawMap(graph2);
		if(showTCW){
			drawTCW(graph2);
		}
		drawPowerLine(graph2);
		drawControlPanel(graph2);
		if(inPauseMenu){
			pause.paint(graph2);
		}
		lastTime = System.currentTimeMillis();
	}

	public static void drawMap(final Graphics2D graph2){
		graph2.setColor(new Color(255, 255, 255));
		final int spaceYStart;
		if(Variables.height > 800){
			spaceYStart = 179;
		}
		else{
			spaceYStart = Variables.height / 5 + 19;
		}
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
	}

	public static void drawTCW(Graphics2D graph2){
		final int controlPanelHeight = getControlPHeight();
		if(TCWframeBlack){
			graph2.setColor(Color.black);
		}
		else{
			graph2.setColor(Color.white);
		}
		final int borderSize = TCWwidth / 16;
		graph2.fillRect(TCWposition.x, TCWposition.y + controlPanelHeight, TCWwidth, TCWwidth * 2);
		graph2.setColor(Color.CYAN);
		graph2.fillRect(TCWposition.x + borderSize, TCWposition.y + controlPanelHeight + borderSize, TCWwidth - 2 * borderSize, TCWwidth * 2 - 2 * borderSize);
		graph2.setColor(Color.red);
		graph2.fillRect(TCWposition.x + TCWwidth - 3 * borderSize, TCWposition.y + controlPanelHeight + borderSize, 2 * borderSize, 2 * borderSize);
		graph2.setColor(new Color(205, 133, 0));
		StringDraw.drawMaxString(graph2, 2, "Population lvl. " + theCity.getPopulation(TCWmapX, TCWmapY), StringDraw.Left, new Rectangle(TCWposition.x + borderSize, TCWposition.y + 4 * borderSize + controlPanelHeight, TCWwidth - 2 * borderSize, TCWwidth / 8));
		graph2.setColor(Color.green);
		final Rectangle CLButton = new Rectangle(TCWposition.x + borderSize, TCWposition.y + controlPanelHeight + TCWwidth * 2 - borderSize - TCWwidth / 8, TCWwidth - 2 * borderSize, TCWwidth / 8);
		Functions.drawChangRect(graph2, Color.green, new Color(0, 200, 0), CLButton);
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, TCWwidth / 64, "Create Line", CLButton);
	}

	public static void drawSquarePopulation(final Graphics2D graph2, final int pixelX, final int pixelY, final int squareX, final int squareY){
		if(theCity.getPopulation(squareX, squareY) != 0){
			final Image texture = ResourceHandler.getPopulationImage(theCity.getPopulation(squareX, squareY));
			graph2.drawImage(texture, pixelX, pixelY, null);
		}
	}

	public static void drawControlPanel(Graphics2D graph2){
		final int controlPanelHeight = getControlPHeight();
		final int borderSize = controlPanelHeight / 10;
		graph2.setColor(Color.black);
		graph2.fillRect(0, 0, Variables.width, controlPanelHeight);
		graph2.setColor(Color.blue);
		graph2.fillRect(borderSize, borderSize, Variables.width - borderSize * 2, controlPanelHeight - borderSize * 2);
		if(paused == false){
			theCity.time = theCity.time + System.currentTimeMillis() - lastTime;
		}
		graph2.setColor(Color.white);
		final Rectangle timeBounds = new Rectangle();
		timeBounds.x = borderSize + (Variables.width - borderSize * 2) / 3 * 2;
		timeBounds.y = borderSize;
		timeBounds.width = (Variables.width - borderSize * 2) / 3;
		timeBounds.height = (controlPanelHeight - borderSize * 2) / 3;
		StringDraw.drawMaxString(graph2, 3, longTimeToDate(theCity.time), StringDraw.UpRight, timeBounds);
		final int pauseSize = (controlPanelHeight - 2 * borderSize) / 2;
		final Rectangle pauseButton = new Rectangle(Variables.width - borderSize - pauseSize,
				controlPanelHeight - borderSize - pauseSize,
				pauseSize,
				pauseSize);
		if(pauseButton.contains(Variables.lastMousePosition) && inPauseMenu == false){
			graph2.setColor(new Color(255, 246, 143));
		}
		else{
			graph2.setColor(Color.white);
		}
		graph2.fill(pauseButton);
		graph2.setColor(Color.blue);
		graph2.fillRect(pauseButton.x + pauseSize / 5, pauseButton.y + pauseSize / 5, pauseSize / 5, pauseSize * 3 / 5);
		graph2.fillRect(pauseButton.x + pauseSize - pauseSize * 2 / 5, pauseButton.y + pauseSize / 5, pauseSize / 5, pauseSize * 3 / 5);
		graph2.setColor(new Color(255, 215, 0));
		final Rectangle moneyBounds = new Rectangle();;
		final int moneyRectBorder = (controlPanelHeight - 2 * borderSize) / 16;
		moneyBounds.x = borderSize;
		moneyBounds.y = borderSize;
		moneyBounds.width = Variables.width / 2 - borderSize;
		moneyBounds.height = controlPanelHeight / 2 - borderSize;
		StringDraw.drawMaxString(graph2, moneyRectBorder, String.format("%.2f", theCity.money) + " TBC", StringDraw.Left, moneyBounds);
		if(makingLine){
			inMakingLine(graph2);
		}
	}
	
	public static void inMakingLine(Graphics2D graph2){
		final int controlPanelHeight = getControlPHeight();
		drawPrice(graph2);
		graph2.setColor(new Color(255, 255, 255, 127));
		graph2.fillRect(0, controlPanelHeight + 39, Variables.width, Variables.height - controlPanelHeight + 39);
		final Rectangle cancelButton = new Rectangle(0, Variables.height - Variables.width / 32, Variables.width / 16, Variables.width / 32);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), cancelButton);
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, Variables.width / 128, "Cancel", cancelButton);
		final Rectangle createButton = new Rectangle(Variables.width - Variables.width / 16, Variables.height - Variables.width / 32, Variables.width / 16, Variables.width / 32);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), createButton);
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, Variables.width / 128, "Create!", createButton);
		final Point pointCoors;
		if(expandingLineStart){
			pointCoors = new Point(line.trace[0].x * 64 - theCity.mapPosition.x, line.trace[0].y * 64 - theCity.mapPosition.y + controlPanelHeight + 39);
		}
		else{
			pointCoors = new Point(line.trace[line.trace.length - 1].x * 64 - theCity.mapPosition.x, line.trace[line.trace.length - 1].y * 64 - theCity.mapPosition.y + controlPanelHeight + 39);
		}
		graph2.setColor(line.lineColor);
		Area circle = new Area(new Ellipse2D.Double((int) (pointCoors.x - (Math.sqrt(2) * 64 - 64) / 2), (int) (pointCoors.y - (Math.sqrt(2) * 64 - 64) / 2), (int) (Math.sqrt(2) * 64), (int) (Math.sqrt(2) * 64)));
		final Area smallEll = new Area(new Ellipse2D.Double(pointCoors.x, pointCoors.y, 64, 64));
		circle.subtract(smallEll);
		graph2.fill(circle);
	}

	public static void drawPrice(Graphics2D graph2){
		final int controlPanelHeight = getControlPHeight();
		final int borderSize = controlPanelHeight / 10;
		final int linePrice = line.getPrice();
		final Rectangle priceBounds = Functions.addBorders(new Rectangle(borderSize, controlPanelHeight / 2, Variables.width / 2 - borderSize, controlPanelHeight / 2 - borderSize), (controlPanelHeight / 2 - borderSize) / 16);
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
	
	public static void drawPowerLine(final Graphics2D graph2){
		int linePosition;
		if(Variables.height > 800){
			linePosition = 160;
		}
		else{
			linePosition = Variables.height / 5;
		}
		graph2.setColor(Color.white);
		graph2.fillRect(0, linePosition - 15, Variables.width, 30);
		if(paused == false){
			powerLineState = powerLineState + System.currentTimeMillis() - lastTime;
		}
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
