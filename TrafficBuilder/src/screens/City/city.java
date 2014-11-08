package screens.City;

import java.awt.Color;
import java.awt.Dimension;
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
		Variables.myGui.setMinimumSize(new Dimension(300, 300));
		repaint.start();
	}
	
	public static void close(){
		Variables.InCity = false;
		repaint.stop();
	}
	
	public static void paint(Graphics g){
		Graphics2D graph2 = (Graphics2D)g;
		drawPowerLine(graph2);
	}
	
	public static void drawPowerLine(Graphics2D graph2){
		graph2.setColor(Color.white);
		graph2.fillRect(0, Variables.height / 5 - 15, Variables.width, 30);
		graph2.setColor(Color.black);
		graph2.fillRect(0, Variables.height / 5 - 20, Variables.width, 5);
		graph2.fillRect(0, Variables.height / 5 + 15, Variables.width, 5);
		int blockHeight = 0;
		if(System.currentTimeMillis() % 3000 > 1000){
			blockHeight = (int) (((Math.abs((System.currentTimeMillis() % 3000) - 2000) - 500) * -1 + 500) * 0.03);
		}
		graph2.fillRect(0, Variables.height / 5 - 15, Variables.width % 20 / 2, blockHeight);
		int blockSpaceFilled = Variables.width % 20 / 2;
		boolean nextUp = false;
		while(blockSpaceFilled + 20 < Variables.width){
			if(nextUp){
				graph2.fillRect(blockSpaceFilled, Variables.height / 5 - 15, 10, blockHeight);
			}
			else{
				
			}
			blockSpaceFilled = blockSpaceFilled + 10;
		}
	}
}
