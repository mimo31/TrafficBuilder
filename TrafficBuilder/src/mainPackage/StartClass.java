package mainPackage;
import java.awt.Font;
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
		Variables.myGui = new Gui(true);
		Gui.updateGui();
	}
	
	public static class paintIt extends JComponent{
		public void paint(Graphics g){
			Graphics2D graph2 = (Graphics2D)g;
			graph2.fill(new Rectangle(Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16));
			graph2.fill(new Rectangle(Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16));
			graph2.fill(new Rectangle(Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16));
			graph2.setFont(Variables.nowUsingFont.deriveFont(Font.BOLD, 8f));
			Rectangle s1Size = new Rectangle(Functions.getStringBounds(graph2, "Traffic Builder", 0, 0));
			graph2.setFont(Variables.nowUsingFont.deriveFont(Font.BOLD, (float) (Variables.width / 2) / s1Size.width * 8));
			graph2.drawString("Traffic Builder", Variables.width / 4 , 100);
		}
	}
}
