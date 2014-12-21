package screens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.*;

import screens.City.CityType;
import screens.City.city;
import mainPackage.Functions;
import mainPackage.Textbox;
import mainPackage.Variables;

public class newCity {
	static Textbox nameTextbox;
	private static ActionListener timerAction = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
        	Variables.myGui.repaint();
        }
    };
	static Timer repaint = new Timer(500, timerAction);
    
	public static void load(){
		Variables.InNewCity = true;
		nameTextbox = new Textbox();
		nameTextbox.active = true;
		nameTextbox.clicked = true;
		repaint.start();
	}
	
	public static void close(){
		Variables.InNewCity = false;
		repaint.stop();
	}

	public static void paint(Graphics g){
		Graphics2D graph2 = (Graphics2D)g;
		graph2.setColor(Color.black);
		Functions.drawMaxString(graph2, "Name your city", new Rectangle(Variables.width / 4, Variables.height / 8, Variables.width / 2, Variables.height / 4));
		Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), Variables.width / 200, Variables.height / 200, Variables.width / 16, Variables.height / 24);
		graph2.setColor(Color.WHITE);
		Functions.drawMaxString(graph2, "<<", new Rectangle(Variables.width / 200 + Variables.width / 100, Variables.height / 200 + Variables.height / 100, Variables.width / 16 * 1 / 2, Variables.height / 24 / 2));
		nameTextbox.size = new Dimension(Variables.width / 2, Variables.height / 8);
		nameTextbox.position = new Point(Variables.width / 4, Variables.height / 2);
		nameTextbox.paint(graph2);
		Functions.drawChangRect(graph2, Color.red, new Color(200, 0, 0), Variables.width / 4 * 3 - Variables.width / 12, Variables.height / 8 * 5 + Variables.height / 16, Variables.width / 12, Variables.height / 16);
		double borderSize = (Variables.width / 12 + Variables.height / 16) / 32;
		graph2.setColor(Color.blue);
		Functions.drawChangRect(graph2, Color.blue, new Color(0, 130, 130), (int) (Variables.width / 4 * 3 - Variables.width / 12 + borderSize),  (int) (Variables.height / 8 * 5 + Variables.height / 16 + borderSize), (int) (Variables.width / 12 - borderSize * 2), (int) (Variables.height / 16 - borderSize * 2));
		graph2.setColor(Color.white);
		Functions.drawMaxString(graph2, "OK", new Rectangle((int) (Variables.width / 4 * 3 - Variables.width / 12 + borderSize * 2),  (int) (Variables.height / 8 * 5 + Variables.height / 16 + borderSize * 2), (int) (Variables.width / 12 - borderSize * 4), (int) (Variables.height / 16 - borderSize * 4)), Font.BOLD);
	}
	
	public static void keyReleased(KeyEvent event){
		if(nameTextbox.clicked == true){
			if(event.getKeyCode() == 10){
				close();
				city.load(new CityType(nameTextbox.text));
				Variables.myGui.repaint();
			}
			else{
				nameTextbox.keyPressed(event);
				Variables.myGui.repaint();
			}
		}
	}
	
	public static void mouseClicked(MouseEvent event){
		nameTextbox.clicked = Functions.buttonClicked(event, nameTextbox.position.x, nameTextbox.position.y, nameTextbox.size.width, nameTextbox.size.height);
		if(nameTextbox.clicked == true){
			repaint.start();
		}
		else{
			repaint.stop();
		}
		Variables.myGui.repaint();
		if(Functions.buttonClicked(event, Variables.width / 200, Variables.height / 200, Variables.width / 16, Variables.height / 24)){
			close();
			title.load();
		}
		else if(Functions.buttonClicked(event, Variables.width / 4 * 3 - Variables.width / 12, Variables.height / 8 * 5 + Variables.height / 16, Variables.width / 12, Variables.height / 16)){
			close();
			city.load(new CityType(nameTextbox.text));
			Variables.myGui.repaint();
		}
	}
}
