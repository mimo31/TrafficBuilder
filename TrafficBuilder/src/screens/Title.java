package screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import mainPackage.Functions;
import mainPackage.Gui;
import mainPackage.StringDraw;
import mainPackage.Variables;

public class Title {
	static Point mousePosition;

	public static void mouseClicked(final MouseEvent event){
		if(new Rectangle(Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16).contains(event.getPoint())){
			Title.close();
			NewCity.load(true);
			Gui.updateGui();
		}
		else if(new Rectangle(Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16).contains(event.getPoint())){
			Title.close();
			LoadCity.load();
			Gui.updateGui();
		}
	}

	public static void load(){
		Variables.InStart = true;
		Variables.myGui.setMinimumSize(new Dimension(100, 100));
	}

	public static void close(){
		Variables.InStart = false;
	}

	public static void paint(final Graphics g){
		final Graphics2D graph2 = (Graphics2D)g;
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16);
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16);
		graph2.setColor(Color.black);
		StringDraw.drawMaxString(graph2, "Traffic Builder", new Rectangle(Variables.width / 4,  Variables.height / 32, Variables.width / 2, Variables.height / 16 * 3));
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, "New City", new Rectangle(Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16));
		StringDraw.drawMaxString(graph2, "Load City", new Rectangle(Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16));
		StringDraw.drawMaxString(graph2, "Settings", new Rectangle(Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16));
	}
}
