package screens.city;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import mainPackage.StringDraw;
import mainPackage.Variables;

public class ViewSettings extends City{

	public static void paint(Graphics2D graph2){
		Rectangle titleRect = new Rectangle(0, 0, Variables.width, Variables.height / 6);
		graph2.setColor(Color.black);
		graph2.fill(titleRect);
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, Variables.height / 48, "View Settigns", titleRect);
	}
	
	public static void mouseClicked(MouseEvent event){
		
	}
}
