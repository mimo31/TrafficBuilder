package tb.uis.loadCity;

import java.awt.Color;
import java.awt.Graphics2D;

import tb.ActionResult;
import tb.ExtendedGraphics2D;
import tb.Main;
import tb.UI;

public class Interface extends UI{
	
	public ActionResult paint(Graphics2D graph2, ExtendedGraphics2D exGraph){
		Components.updateComponents();
		graph2.setColor(new Color(40, 40, 40));
		graph2.fill(Components.titleRect);
		exGraph.setColor(Color.WHITE);
		exGraph.drawMaxString(Main.guiHeight / 48, "Load your city", Components.titleRect);
		exGraph.drawChangingRect(Components.scrollBar, Color.white, Color.lightGray);
		if(Components.upScrollTriangle.contains(Main.mousePosition)){
			graph2.setColor(Color.white);
		}
		else{
			graph2.setColor(Color.darkGray);
		}
		graph2.fill(Components.upScrollTriangle);
		if(Components.downScrollTriangle.contains(Main.mousePosition)){
			graph2.setColor(Color.white);
		}
		else{
			graph2.setColor(Color.darkGray);
		}
		graph2.fill(Components.downScrollTriangle);
		return null;
	}
	
}
