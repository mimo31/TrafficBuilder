import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class StartClass {
	public static void main(String[] args){
		Variables.InStart = true;
		new Gui();
	}
	
	public static class paintIt extends JComponent{
		public void paint(Graphics g){
			Graphics2D graph2 = (Graphics2D)g;
			graph2.fill(new Rectangle(100, 100, 100, 100));
		}
	}
}
