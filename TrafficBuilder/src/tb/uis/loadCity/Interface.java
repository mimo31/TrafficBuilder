package tb.uis.loadCity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.Timer;

import tb.ActionResult;
import tb.CityInfo;
import tb.ExtendedGraphics2D;
import tb.Main;
import tb.UI;

public class Interface extends UI {

	static CityInfo[] cityInfos;
	static double listPosition;

	static ActionListener scrollUpAction = new ActionListener(){
		@Override
		public void actionPerformed(final ActionEvent arg0)
		{
			listPosition = listPosition - 0.05;
			if(Components.upScrollTriangle.contains(Main.mousePosition) == false){
				scrollUp.stop();
			}
			if(listPosition < 0){
				listPosition = 0;
				scrollUp.stop();
			}
			Main.gui.repaint();
		}
	};
	static Timer scrollUp = new Timer(25, scrollUpAction);
	private static ActionListener scrollDownAction = new ActionListener(){
		@Override
		public void actionPerformed(final ActionEvent arg0)
		{
			listPosition = listPosition + 0.05;
			if(Components.downScrollTriangle.contains(Main.mousePosition) == false){
				scrollDown.stop();
			}
			if(listPosition > cityInfos.length - 0.5){
				listPosition = cityInfos.length - 0.5;
				scrollUp.stop();
			}
			Main.gui.repaint();
		}
	};
	static Timer scrollDown = new Timer(25, scrollDownAction);

	public static String calendarToString(final Calendar data) {
		final String hour;
		final String minute;
		final String second;
		if (data.get(Calendar.HOUR_OF_DAY) < 10) {
			hour = "0" + String.valueOf(data.get(Calendar.HOUR_OF_DAY));
		} else {
			hour = String.valueOf(data.get(Calendar.HOUR_OF_DAY));
		}
		if (data.get(Calendar.MINUTE) < 10) {
			minute = "0" + String.valueOf(data.get(Calendar.MINUTE));
		} else {
			minute = String.valueOf(data.get(Calendar.MINUTE));
		}
		if (data.get(Calendar.SECOND) < 10) {
			second = "0" + String.valueOf(data.get(Calendar.SECOND));
		} else {
			second = String.valueOf(data.get(Calendar.SECOND));
		}
		return String.valueOf(data.get(Calendar.MONTH) + 1) + "/" + String.valueOf(data.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(data.get(Calendar.YEAR)) + " " + hour + ":" + minute + ":" + second;
	}

	public static void drawCityBlock(Graphics2D graph2, ExtendedGraphics2D exGraph, int index){
		CityInfo cityInfo = cityInfos[index + Components.firstCityBlockIndex];
		if (Main.mousePosition.y > Components.titleRect.height) {
			exGraph.drawChangingRect(Components.cityBlocks[index], Color.black, new Color(40, 40, 40));
		} else {
			graph2.setColor(Color.black);
			graph2.fill(Components.cityBlocks[index]);
		}
		exGraph.setFont(Main.usingFont.deriveFont(101f));
		Rectangle s1Size = exGraph.getStringBounds(cityInfo.name, 0, 0);
		Double s1Per1Height = ((double) s1Size.height) / 101;
		exGraph.setFont(Main.usingFont.deriveFont((float) (Components.cityBlocks[index].height / 2 / s1Per1Height)));
		String textToPaint;
		int availableBlockWidth = Components.cityBlocks[index].width - Components.cityBlocks[index].width / 18;
		if (exGraph.getStringBounds(cityInfo.name, 0, 0).width <= availableBlockWidth) {
			textToPaint = cityInfo.name;
		} else {
			int counter = 1;
			while(exGraph.getStringBounds(cityInfo.name.substring(0, counter + 1) + "...", 0, 0).width <= availableBlockWidth){
				counter++;
				if (counter + 1 > cityInfo.name.length()) {
					break;
				}
			}
			textToPaint = cityInfo.name.substring(0, counter) + "...";
		}
		s1Size = exGraph.getStringBounds(textToPaint, 0, 0);
		int drawX = Components.cityBlocks[index].x + Components.cityBlocks[index].width / 36;
		int drawY = Components.cityBlocks[index].y + Components.cityBlocks[index].width / 36 - s1Size.y;
		exGraph.setColor(Color.white);
		exGraph.drawString(textToPaint, drawX, drawY);
		Rectangle lastPlayedBounds = new Rectangle(Components.cityBlocks[index].x, Components.cityBlocks[index].y + 2 * Components.cityBlocks[index].height / 3, Components.cityBlocks[index].width, Components.cityBlocks[index].height / 3);
		exGraph.drawMaxString(Components.cityBlocks[index].width / 72, "Last played: " + calendarToString(cityInfo.lastPlayed), ExtendedGraphics2D.Left, lastPlayedBounds);
	}

	@Override
	public void close() {
		scrollUp.stop();
		scrollDown.stop();
	}

	@Override
	public ActionResult load() {
		cityInfos = tb.data.Cities.getCityInfos();
		listPosition = 0;
		return null;
	}

	@Override
	public ActionResult mouseClicked(MouseEvent event) {
		if (Components.cityBlocks.length != 0) {
			if (event.getY() > Components.titleRect.height) {
				for (int i = 0; i < Components.cityBlocks.length; i++) {
					if (Components.cityBlocks[i].contains(event.getPoint())) {
						try {
							tb.data.Cities.loadCity(cityInfos[Components.firstCityBlockIndex + i].folder);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return new ActionResult(true, 3, true);
					}
				}
			}
		} else {
			if (Components.createOneButton.contains(event.getPoint())) {
				return new ActionResult(true, 1, true);
			}
		}
		if (Components.backButton.contains(event.getPoint())) {
			return new ActionResult(true, 0, true);
		}
		return null;
	}

	@Override
	public ActionResult mousePressed(MouseEvent event){
		if(Components.upScrollTriangle.contains(event.getPoint())){
			scrollUp.start();
		}
		else if(Components.downScrollTriangle.contains(event.getPoint())){
			scrollDown.start();
		}
		return null;
	}

	@Override
	public ActionResult mouseReleased(MouseEvent event){
		scrollUp.stop();
		scrollDown.stop();
		return null;
	}

	@Override
	public ActionResult paint(Graphics2D graph2, ExtendedGraphics2D exGraph) {
		Components.updateComponents();
		if (Components.cityBlocks.length != 0) {
			for (int i = 0; i < Components.cityBlocks.length; i++) {
				drawCityBlock(graph2, exGraph, i);
			}
		} else {
			exGraph.setColor(Color.black);
			exGraph.drawMaxString("You did not create any city yet", Components.didntCreateBounds);
			exGraph.drawChangingRect(Components.createOneButton, Color.black, new Color(40, 40, 40));
			exGraph.setColor(Color.white);
			exGraph.drawMaxString(Main.guiWidth / 128, "Create one", Components.createOneButton);
		}
		graph2.setColor(new Color(40, 40, 40));
		graph2.fill(Components.titleRect);
		exGraph.setColor(Color.WHITE);
		exGraph.drawMaxString(Main.guiHeight / 48, "Load your city",
				Components.titleRect);
		exGraph.drawChangingRect(Components.scrollBar, Color.white,
				Color.lightGray);
		if (Components.upScrollTriangle.contains(Main.mousePosition)) {
			graph2.setColor(Color.white);
		} else {
			graph2.setColor(Color.darkGray);
		}
		graph2.fill(Components.upScrollTriangle);
		if (Components.downScrollTriangle.contains(Main.mousePosition)) {
			graph2.setColor(Color.white);
		} else {
			graph2.setColor(Color.darkGray);
		}
		graph2.fill(Components.downScrollTriangle);
		exGraph.drawChangingRect(Components.backButton, Color.black, new Color(100, 100, 100));
		exGraph.setColor(Color.white);
		exGraph.drawMaxString("<<", Components.backBounds);
		return null;
	}
}
