package screens;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

import mainPackage.Functions;
import mainPackage.Textbox;
import mainPackage.Variables;

public class newCity {
	static Textbox nameTextbox = new Textbox();
	static Timer repaint;
    
	public static void load(){
		Variables.InNewCity = true;
		nameTextbox.active = true;
		nameTextbox.clicked = true;
		repaint = new Timer(500, timerAction);
		repaint.start();
	}
	
	public static void close(){
		Variables.InNewCity = false;
	}

	public static void paint(Graphics g){
		Graphics2D graph2 = (Graphics2D)g;
		Functions.drawMaxString(graph2, "Name your city", new Rectangle(Variables.width / 4, Variables.height / 8, Variables.width / 2, Variables.height / 4));
		nameTextbox.size = new Dimension(Variables.width / 2, Variables.height / 8);
		nameTextbox.position = new Point(Variables.width / 4, Variables.height / 2);
		nameTextbox.paint(graph2);
	}
	
	public static void keyReleased(KeyEvent event){
		if(nameTextbox.clicked == true){
			nameTextbox.keyPressed(event);
			Variables.myGui.repaint();
		}
	}
	private static ActionListener timerAction = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
        	Variables.myGui.repaint();
        }
    };
}
