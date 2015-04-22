package tb.uis.title;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import tb.ActionResult;
import tb.ExtendedGraphics2D;
import tb.Main;
import tb.UI;

public class Interface extends UI{
	
	public ActionResult load(){
		Main.gui.setMinimumSize(new Dimension(100, 100));
		return null;
	}
	
	public ActionResult mouseClicked(MouseEvent event){
		ActionResult result;
		if(Components.newCityButton.contains(event.getPoint())){
			result = new ActionResult(true, 1);
		}
		else if(Components.loadCityButton.contains(event.getPoint())){
			result = new ActionResult(true, 2);
		}
		else{
			result = null;
		}
		return result;
	}

	public ActionResult paint(Graphics2D graph2, ExtendedGraphics2D exGraph) {
		Components.updateComponents();
		exGraph.drawChangingRect(Components.newCityButton, Color.black, new Color(40, 40, 40));
		exGraph.drawChangingRect(Components.loadCityButton, Color.black, new Color(40, 40, 40));
		exGraph.drawChangingRect(Components.settingsButton, Color.black, new Color(40, 40, 40));
		exGraph.setColor(Color.black);
		exGraph.drawMaxString("Traffic Builder", Components.TBtextBounds);
		exGraph.setColor(Color.white);
		exGraph.drawMaxString("New City", Components.newCityButton);
		exGraph.drawMaxString("Load City", Components.loadCityButton);
		exGraph.drawMaxString("Settings", Components.settingsButton);
		return null;
	}
	
}
