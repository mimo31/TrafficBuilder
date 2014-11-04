package screens.City;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import mainPackage.Variables;

public class city {
	private static ActionListener timerAction = new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
    	Variables.myGui.repaint();
    }
};
	public static Timer repaint = new Timer(33, timerAction);
	
	public static void load(){
		Variables.InCity = true;
	}
	
	public static void close(){
		Variables.InCity = false;
	}
	
	public static void paint(Graphics g){
		Graphics2D graph2 = (Graphics2D)g;
	}
	
	
}
