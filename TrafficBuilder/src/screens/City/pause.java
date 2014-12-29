package screens.City;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import screens.title;
import mainPackage.Functions;
import mainPackage.Variables;

public class pause {
	public static void paint(Graphics2D graph2){
		graph2.setColor(new Color(255, 255, 255, 127));
		graph2.fillRect(0, 0, Variables.width, Variables.height);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16);
		final int borderSize = Variables.height / 128;
		graph2.setColor(Color.white);
		Functions.drawMaxString(graph2, "Back to the city", new Rectangle(Variables.width / 4 + borderSize, Variables.height / 4 + borderSize, Variables.width / 2 - 2 * borderSize, Variables.height / 16 - 2 * borderSize));
		Functions.drawMaxString(graph2, "Settings", new Rectangle(Variables.width / 4 + borderSize, Variables.height / 32 * 11 + borderSize, Variables.width / 2 - 2 * borderSize, Variables.height / 16 - 2 * borderSize));
		Functions.drawMaxString(graph2, "Go to title", new Rectangle(Variables.width / 4 + borderSize, Variables.height / 16 * 7 + borderSize, Variables.width / 2 - 2 * borderSize, Variables.height / 16 - 2 * borderSize));
	}
	
	public static void mouseClicked(MouseEvent event){
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
