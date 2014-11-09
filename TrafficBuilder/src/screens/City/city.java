package screens.City;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import mainPackage.CityType;
import mainPackage.Variables;

public class city {
	public static CityType theCity;
	static long lastCityTime;
	
	private static ActionListener timerAction = new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent arg0)
    	{
    		Variables.myGui.repaint();
    	}
	};
	public static Timer repaint = new Timer(25, timerAction);
	
	public static void load(CityType city){
		Variables.InCity = true;
		Variables.myGui.setMinimumSize(new Dimension(300, 300));
		theCity = city;
		lastCityTime = theCity.time;
		repaint.start();
	}
	
	public static void close(){
		Variables.InCity = false;
		repaint.stop();
	}
	
	public static void paint(Graphics g){
		Graphics2D graph2 = (Graphics2D)g;
		drawPowerLine(graph2);
		drawControlPanel(graph2);
	}
	
	public static void drawControlPanel(Graphics2D graph2){
		int controlPanelHeight;
		int borderSize;
		if(Variables.height > 800){
			controlPanelHeight = 140;
		}
		else{
			controlPanelHeight = Variables.height / 5 - 20;
		}
		borderSize = controlPanelHeight / 10;
		graph2.setColor(Color.black);
		graph2.fillRect(0, 0, Variables.width, controlPanelHeight);
		graph2.setColor(Color.blue);
		graph2.fillRect(borderSize, borderSize, Variables.width - borderSize * 2, controlPanelHeight - borderSize * 2);
	}
	
	public static void drawPowerLine(Graphics2D graph2){
		int linePosition;
		if(Variables.height > 800){
			linePosition = 160;
		}
		else{
			linePosition = Variables.height / 5;
		}
		graph2.setColor(Color.white);
		graph2.fillRect(0, linePosition - 15, Variables.width, 30);
		final long time = System.currentTimeMillis();
		int counter = 0;
		graph2.setColor(Color.red);
		double sin;
		while(counter < Variables.width){
			sin = (int) (Math.sin(time / 50 + counter / 10) * 5);
			graph2.drawRect(counter, (int) (linePosition - sin), 1, (int) (sin + 15));
			counter++;
		}
		graph2.setColor(Color.black);
		graph2.fillRect(0, linePosition - 20, Variables.width, 5);
		graph2.fillRect(0, linePosition + 14, Variables.width, 5);
		int blockHeight = 0;
		if(time % 3000 > 1000){
			blockHeight = (int) (((Math.abs((time % 3000) - 2000) - 500) * -1 + 500) * 0.03);
		}
		graph2.fillRect(0, linePosition - 15, Variables.width % 20 / 2, blockHeight);
		int blockSpaceFilled = Variables.width % 20 / 2;
		boolean nextUp = false;
		while(blockSpaceFilled < Variables.width){
			if(nextUp){
				graph2.fillRect(blockSpaceFilled, linePosition - 15, 10, blockHeight);
				nextUp = false;
			}
			else{
				graph2.fillRect(blockSpaceFilled, linePosition + 15 - blockHeight, 10, blockHeight);
				nextUp = true;
			}
			blockSpaceFilled = blockSpaceFilled + 10;
		}
	}
	
	/*static String longTimeToDate(long longTime){
		longTime = longTime / 250 * 3;
		int year;
		String month;
		String day;
		int hour;
		int minute;
		year = (int) (2000 + Math.floor(longTime / 525600));
		longTime = longTime % 525600;
	}*/
}
