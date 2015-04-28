package tb.uis.newCity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import tb.ActionResult;
import tb.ExtendedGraphics2D;
import tb.Main;
import tb.Textbox;
import tb.UI;
import tb.cityType.City;

public class Interface extends UI{
	
	public static Textbox nameTextbox;
	public static boolean showBlankWarning;
	
	public static ActionListener repaint = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Main.gui.repaint();
		}
		
	};
	
	public static Timer autoRepaint = new Timer(500, repaint);
	
	@Override
	public void close(){
		autoRepaint.stop();
	}
	
	@Override
	public ActionResult load(){
		autoRepaint.start();
		showBlankWarning = false;
		nameTextbox = new Textbox();
		nameTextbox.active = true;
		nameTextbox.clicked = true;
		return null;
	}
	
	public ActionResult createNewCity(){
		if (nameTextbox.text.equals("")) {
			showBlankWarning = true;
			return new ActionResult(false, 0, true);
		} else {
			Main.city = new City(nameTextbox.text);
			return new ActionResult(true, 3, true);
		}
	}
	
	@Override
	public ActionResult mouseClicked(MouseEvent event){
		ActionResult result = null;
		if (new Rectangle(nameTextbox.position, nameTextbox.size).contains(event.getPoint())){
			nameTextbox.clicked = true;
			result = new ActionResult(false, 0, true);
		} else {
			nameTextbox.clicked = false;
			result = new ActionResult(false, 0, true);
		}
		if (Components.backButton.contains(event.getPoint())){
			result = new ActionResult(true, -1, true);
		} else if (Components.okButton.contains(event.getPoint())){
			result = createNewCity();
		}
		return result;
	}
	
	@Override
	public ActionResult keyReleased(KeyEvent event){
		if (nameTextbox.clicked) {
			if (event.getKeyCode() == 10) {
				return createNewCity();
			} else {
				showBlankWarning = false;
				nameTextbox.keyPressed(event);
				return new ActionResult(false, 0, true);
			}
		} else {
			return null;
		}
	}
	
	@Override
	public ActionResult paint(Graphics2D graph2, ExtendedGraphics2D exGraph){
		Components.updateComponents();
		exGraph.setColor(Color.black);
		exGraph.drawMaxString("Name your city", Components.titleBounds);
		exGraph.drawChangingRect(Components.backButton, Color.black, new Color(40, 40, 40));
		exGraph.setColor(Color.white);
		exGraph.drawMaxString("<<", Components.backBounds);
		exGraph.drawChangingRect(Components.okButton, Color.red, new Color(200, 0, 0));
		if(Components.okButton.contains(Main.mousePosition)){
			graph2.setColor(new Color(0, 130, 130));
		} else {
			graph2.setColor(Color.blue);
		}
		graph2.fill(Components.insideOkButton);
		exGraph.setColor(Color.white);
		exGraph.drawMaxString("OK", Components.OKbounds, Font.BOLD);
		if(showBlankWarning){
			exGraph.setColor(Color.red);
			exGraph.drawMaxString("Type the name for your city please.", Components.blankBounds);
		}
		nameTextbox.paint(graph2, exGraph);
		return null;
	}
	
}
