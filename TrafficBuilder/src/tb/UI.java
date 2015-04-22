package tb;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class UI {
	
	public ActionResult paint(Graphics2D graph2, ExtendedGraphics2D exGraph){return null;};
	public ActionResult mouseClicked(MouseEvent event){return null;};
	public ActionResult mouseDragged(MouseEvent event){return null;};
	public ActionResult mousePressed(MouseEvent event){return null;};
	public ActionResult mouseReleased(MouseEvent event){return null;};
	public ActionResult keyReleased(KeyEvent event){return null;};
	public ActionResult load(){return null;};
	public void close(){};
	
}
