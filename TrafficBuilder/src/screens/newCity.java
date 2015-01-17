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

import javax.swing.Timer;

import mainPackage.Functions;
import mainPackage.StringDraw;
import mainPackage.Textbox;
import mainPackage.Variables;
import screens.City.CityType;
import screens.City.city;

public class newCity {
	static Textbox nameTextbox;
	static boolean showBlankWarning;
	private static ActionListener timerAction = new ActionListener()
	{
		@Override
		public void actionPerformed(final ActionEvent arg0)
		{
			Variables.myGui.repaint();
		}
	};
	static Timer repaint = new Timer(500, timerAction);

	public static void load(){
		Variables.InNewCity = true;
		showBlankWarning = false;
		nameTextbox = new Textbox();
		nameTextbox.active = true;
		nameTextbox.clicked = true;
		repaint.start();
	}

	public static void close(){
		Variables.InNewCity = false;
		repaint.stop();
	}

	public static void paint(final Graphics g){
		final Graphics2D graph2 = (Graphics2D)g;
		Functions.drawPauseButton(graph2);
		graph2.setColor(Color.black);
		StringDraw.drawMaxString(graph2, "Name your city", new Rectangle(Variables.width / 4, Variables.height / 8, Variables.width / 2, Variables.height / 4));
		nameTextbox.size = new Dimension(Variables.width / 2, Variables.height / 8);
		nameTextbox.position = new Point(Variables.width / 4, Variables.height / 2);
		nameTextbox.paint(graph2);
		final Rectangle okButton = new Rectangle(Variables.width / 4 * 3 - Variables.width / 12, Variables.height / 8 * 5 + Variables.height / 16, Variables.width / 12, Variables.height / 16);
		Functions.drawChangRect(graph2, Color.red, new Color(200, 0, 0), okButton.x, okButton.y, okButton.width, okButton.height);
		final double borderSize = (Variables.width / 12 + Variables.height / 16) / 32;
		if(okButton.contains(Variables.lastMousePosition)){
			graph2.setColor(new Color(0, 130, 130));
		}
		else{
			graph2.setColor(Color.blue);
		}
		graph2.fillRect((int) (Variables.width / 4 * 3 - Variables.width / 12 + borderSize),  (int) (Variables.height / 8 * 5 + Variables.height / 16 + borderSize), (int) (Variables.width / 12 - borderSize * 2), (int) (Variables.height / 16 - borderSize * 2));
		graph2.setColor(Color.white);
		StringDraw.drawMaxString(graph2, "OK", new Rectangle((int) (Variables.width / 4 * 3 - Variables.width / 12 + borderSize * 2),  (int) (Variables.height / 8 * 5 + Variables.height / 16 + borderSize * 2), (int) (Variables.width / 12 - borderSize * 4), (int) (Variables.height / 16 - borderSize * 4)), Font.BOLD);
		if(showBlankWarning){
			final Rectangle boundsWithoutBorders = new Rectangle(0, Variables.height / 8 * 7, Variables.width, Variables.height / 8);
			final int warnBorderSize = boundsWithoutBorders.height / 8;
			final Rectangle warnFinalBounds = new Rectangle(boundsWithoutBorders.x + warnBorderSize, boundsWithoutBorders.y + warnBorderSize, boundsWithoutBorders.width - 2 * warnBorderSize, boundsWithoutBorders.height - 2 * warnBorderSize);
			graph2.setColor(Color.red);
			StringDraw.drawMaxString(graph2, "Type the name for your city please.", warnFinalBounds);
		}
	}

	public static void keyReleased(final KeyEvent event){
		if(nameTextbox.clicked == true){
			if(event.getKeyCode() == 10){
				goToNewWorld();
			}
			else{
				showBlankWarning = false;
				nameTextbox.keyPressed(event);
				Variables.myGui.repaint();
			}
		}
	}

	public static void goToNewWorld(){
		if(nameTextbox.text.equals("")){
			showBlankWarning = true;
		}
		else{
			close();
			city.load(new CityType(nameTextbox.text));
			Variables.myGui.repaint();
		}
	}

	public static void mouseClicked(final MouseEvent event){
		Variables.myGui.repaint();
		if(Functions.buttonClicked(event, Variables.width / 200, Variables.height / 200, Variables.width / 16, Variables.height / 24)){
			close();
			title.load();
		}
		else if(Functions.buttonClicked(event, Variables.width / 4 * 3 - Variables.width / 12, Variables.height / 8 * 5 + Variables.height / 16, Variables.width / 12, Variables.height / 16)){
			goToNewWorld();
		}
		else{
			nameTextbox.clicked = Functions.buttonClicked(event, nameTextbox.position.x, nameTextbox.position.y, nameTextbox.size.width, nameTextbox.size.height);
		}
		if(nameTextbox.clicked == true){
			repaint.start();
		}
		else{
			repaint.stop();
		}
	}
}
