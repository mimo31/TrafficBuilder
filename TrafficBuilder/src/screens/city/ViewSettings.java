package screens.city;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import mainPackage.Functions;
import mainPackage.StringDraw;
import mainPackage.Variables;

public class ViewSettings extends City{

	static boolean linesSettings;
	
	public static void paint(Graphics2D graph2){
		Rectangle titleRect = new Rectangle(0, 0, Variables.width, Variables.height / 6);
		new Color(40, 40, 40);
		graph2.fill(titleRect);
		Functions.drawChangRect(graph2, Color.black, new Color(100, 100, 100), VSBack);
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, VSBack.height / 4, "<<", VSBack);
		graph2.setColor(Color.white);
		if(linesSettings == false){
			final int titleWidthBounds = titleRect.width / 8;
			titleRect = new Rectangle(titleRect.x + titleWidthBounds, titleRect.y, titleRect.width - 2 * titleWidthBounds, titleRect.height);
			StringDraw.drawMaxString(graph2, Variables.height / 48, "View Settigns", titleRect);
			Functions.drawChangRect(graph2, Color.orange, Color.red, VSLines);
			graph2.setColor(Color.white);
			StringDraw.drawMaxString(graph2, VSLines.height / 4, "Lines", VSLines);
		}
		else{
			StringDraw.drawMaxString(graph2, Variables.height / 48, "View Settigns - Lines", titleRect);
		}
	}
	
	public static void mouseClicked(MouseEvent event){
		if(linesSettings == false){
			if(VSLines.contains(event.getPoint())){
				linesSettings = true;
			}
			else if(VSBack.contains(event.getPoint())){
				inViewSettings = false;
				paused = false;
			}
		}
		else{
			if(VSBack.contains(event.getPoint())){
				linesSettings = false;
			}
		}
	}
}
