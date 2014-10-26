package mainPackage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

import data.ResourceHandler;

@SuppressWarnings("serial")
public class StartClass {
	
	public static void main(String[] args){
		ResourceHandler.start();
		Variables.InStart = true;
		Variables.myGui = new Gui();
		Variables.myGui.setMinimumSize(new Dimension(100, 100));
		Gui.updateGui();
	}
	
	public static class paintIt extends JComponent{
		public void paint(Graphics g){
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
	}
}
