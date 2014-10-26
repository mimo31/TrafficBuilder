package mainPackage;
import java.awt.Dimension;
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
			graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, 101f));
			Rectangle s1Size = new Rectangle(Functions.getStringBounds(graph2, "Traffic Builder", 0, 0));
			Double s1Per1Width = ((double) s1Size.width) / 101;
			Double s1Per1Height = ((double) s1Size.height) / 101;
			s1Size = null;
			if(s1Per1Width / s1Per1Height > Variables.width / 2 / (Variables.height / 16 * 3)){
				graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, (float) (Variables.width / 2 / s1Per1Width)));
			}
			else{
				graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, (float) (Variables.height / 16 * 3 / s1Per1Height)));
			}
			graph2.drawString("Traffic Builder", (Variables.width - Functions.getStringBounds(graph2, "Traffic Builder", 0, 0).width) / 2 , Variables.height / 8 + Functions.getStringBounds(graph2, "Traffic Builder", 0, 0).height / 2);
		}
	}
}
