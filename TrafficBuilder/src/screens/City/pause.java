package screens.City;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import mainPackage.Functions;
import mainPackage.StringDraw;
import mainPackage.Variables;
import screens.title;

public class pause extends city{
	public static void paint(final Graphics2D graph2){
		graph2.setColor(new Color(255, 255, 255, 127));
		graph2.fillRect(0, 0, Variables.width, Variables.height);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16);
		final int borderSize = Variables.height / 128;
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, borderSize, "Back to the city", new Rectangle(Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16));
		StringDraw.drawMaxString(graph2, borderSize, "Settings", new Rectangle(Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16));
		StringDraw.drawMaxString(graph2, borderSize, "Go to title", new Rectangle(Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16));
	}

	public static void mouseClicked(final MouseEvent event){
		if(Functions.buttonClicked(event, Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16)){
			city.unpause();
		}
		else if(Functions.buttonClicked(event, Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16)){

		}
		else if(Functions.buttonClicked(event, Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16)){
			city.close();
			title.load();
		}
	}
}
