package screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import mainPackage.Functions;
import mainPackage.Variables;
import mainPackage.Gui;

public class title {
	static Point mousePosition;
	
	public static void mouseClicked(MouseEvent event){
		if(new Rectangle(Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16).contains(event.getPoint())){
			title.close();
			newCity.load();
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
	
	public static void paint(Graphics g){
		Graphics2D graph2 = (Graphics2D)g;
		graph2.fill(new Rectangle(Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16));
		graph2.fill(new Rectangle(Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16));
		graph2.fill(new Rectangle(Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16));
		Functions.drawMaxString(graph2, "Traffic Builder", new Rectangle(Variables.width / 4,  Variables.height / 32, Variables.width / 2, Variables.height / 16 * 3));
		graph2.setColor(Color.white);
		Functions.drawMaxString(graph2, "New City", new Rectangle(Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16));
		Functions.drawMaxString(graph2, "Load City", new Rectangle(Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16));
		Functions.drawMaxString(graph2, "Settings", new Rectangle(Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16));
	}
	
	public static void mouseMove(MouseEvent event){
		mousePosition = event.getPoint();
	}
}
