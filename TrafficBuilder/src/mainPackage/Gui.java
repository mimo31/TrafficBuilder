package mainPackage;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

@SuppressWarnings("serial")
public class Gui extends JFrame{
	public Gui() {
		this.setSize(200, 200);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Traffic Builder");
		this.getContentPane().addMouseListener(new InterfaceMouseEvents());
		this.addComponentListener(new InterfaceComponentEvents());
		this.addKeyListener(new InterfaceKeyEvents());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent windowEvent) {
				if (Variables.InCity == true){
					screens.city.City.close();
				}
			}
		});
		this.getContentPane().addMouseMotionListener(new InterfaceMouseEvents());
		this.add(new paintIt());
	}

	public static void updateGui(){
		final Container Test = Variables.myGui.getContentPane();
		Variables.height = Test.getHeight();
		Variables.width = Test.getWidth();
		Variables.myGui.repaint();
	}

	public static class paintIt extends JComponent{
		@Override
		public void paint(final Graphics g){
			if(Variables.InStart){
				screens.Title.paint(g);
			}
			else if(Variables.InNewCity) {
				screens.NewCity.paint(g);
			}
			else if(Variables.InCity){
				screens.city.City.paint(g);
			}
			else if(Variables.InLoadCity){
				screens.LoadCity.paint(g);
			}
		}
	}

	public class InterfaceKeyEvents implements KeyListener{

		@Override
		public void keyPressed(final KeyEvent event) {

		}

		@Override
		public void keyReleased(final KeyEvent event) {
			if(Variables.InNewCity){
				screens.NewCity.keyReleased(event);
			}
			else if(Variables.InCity){
				screens.city.City.keyReleased(event);
			}
		}

		@Override
		public void keyTyped(final KeyEvent event) {

		}

	}

	public class InterfaceComponentEvents implements ComponentListener{

		@Override
		public void componentMoved(final ComponentEvent e) {


		}

		@Override
		public void componentResized(final ComponentEvent e) {
			updateGui();
		}

		@Override
		public void componentShown(final ComponentEvent e) {


		}

		@Override
		public void componentHidden(final ComponentEvent e) {


		}

	}

	private class InterfaceMouseEvents extends MouseInputAdapter {
		@Override
		public void mouseClicked(final MouseEvent event) {
			if(Variables.InStart){
				screens.Title.mouseClicked(event);
			}
			else if(Variables.InNewCity){
				screens.NewCity.mouseClicked(event);
			}
			else if(Variables.InLoadCity){
				screens.LoadCity.mouseClicked(event);
			}
			else if(Variables.InCity){
				screens.city.City.mouseClicked(event);
			}
		}

		@Override
		public void mouseDragged(final MouseEvent event) {
			if(Variables.InCity){
				screens.city.City.mouseDragged(event);
			}
			Variables.lastMousePosition = event.getPoint();
			updateGui();
			event.consume();
		}

		@Override
		public void mouseMoved(final MouseEvent event) {
			Variables.lastMousePosition = event.getPoint();
			updateGui();
			event.consume();
		}

		@Override
		public void mousePressed(final MouseEvent event) {
			if(Variables.InLoadCity){
				screens.LoadCity.mousePressed(event);
			}
			else if(Variables.InCity){
				screens.city.City.mousePressed(event);
			}
		}

		@Override
		public void mouseReleased(final MouseEvent event) {
			if(Variables.InLoadCity){
				screens.LoadCity.mouseReleased(event);
			}
			else if(Variables.InCity){
				screens.city.City.mouseRelesed(event);
			}
		}
	}
}
