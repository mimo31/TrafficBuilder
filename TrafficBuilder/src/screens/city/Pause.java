package screens.city;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import mainPackage.Functions;
import mainPackage.StringDraw;
import mainPackage.Variables;
import screens.Title;

public class Pause extends City{
	public static void paint(Graphics2D graph2){
		graph2.setColor(new Color(255, 255, 255, 127));
		graph2.fillRect(0, 0, Variables.width, Variables.height);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), backToCity);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), enterSettings);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), goToTitle);
		final int borderSize = Variables.height / 128;
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, borderSize, "Back to the city", backToCity);
		StringDraw.drawMaxString(graph2, borderSize, "Settings", enterSettings);
		StringDraw.drawMaxString(graph2, borderSize, "Go to title", goToTitle);
	}

	public static void mouseClicked(final MouseEvent event){
		if(backToCity.contains(event.getPoint())){
			inPauseMenu = false;
			if(makingLine == false){
				paused = false;
			}
		}
		else if(enterSettings.contains(event.getPoint())){

		}
		else if(goToTitle.contains(event.getPoint())){
			City.close();
			Title.load();
		}
	}
}
