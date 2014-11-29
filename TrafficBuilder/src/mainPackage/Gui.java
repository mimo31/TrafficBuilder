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
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (Variables.InCity == true){
		        	screens.City.city.close();
		        }
		    }
		});
		this.getContentPane().addMouseMotionListener(new InterfaceMouseEvents());
		this.add(new paintIt());
	}
	
	public static void updateGui(){
		Container Test = Variables.myGui.getContentPane();
		Variables.height = Test.getHeight();
		Variables.width = Test.getWidth();
		Variables.myGui.repaint();
	}
	
	public static class paintIt extends JComponent{
		public void paint(Graphics g){
			if(Variables.InStart){
				screens.title.paint(g);
			}
			else if(Variables.InNewCity) {
				screens.newCity.paint(g);
			}
			else if(Variables.InCity){
				screens.City.city.paint(g);
			}
			else if(Variables.InLoadCity){
				screens.loadCity.paint(g);
			}
		}
	}
	
	public class InterfaceKeyEvents implements KeyListener{
		
		public void keyPressed(KeyEvent event) {
			
		}
		
		public void keyReleased(KeyEvent event) {
			if(Variables.InNewCity){
				screens.newCity.keyReleased(event);
			}
		}
		
		public void keyTyped(KeyEvent event) {
			
		}
		
	}
	
	public class InterfaceComponentEvents implements ComponentListener{

		public void componentMoved(ComponentEvent e) {

			
		}
		
		public void componentResized(ComponentEvent e) {
			updateGui();
		}
		
		public void componentShown(ComponentEvent e) {
			
			
		}

		public void componentHidden(ComponentEvent e) {
			
			
		}
		
	}
	
	private class InterfaceMouseEvents extends MouseInputAdapter {
		public void mouseClicked(MouseEvent event) {
			if(Variables.InStart){
				screens.title.mouseClicked(event);
			}
			else if(Variables.InNewCity){
				screens.newCity.mouseClicked(event);
			}
			else if(Variables.InLoadCity){
				screens.loadCity.mouseClicked(event);
			}
		}

		public void mouseDragged(MouseEvent event) {
			Variables.lastMousePosition = event.getPoint();
			updateGui();
			event.consume();
		}

		public void mouseMoved(MouseEvent event) {
			Variables.lastMousePosition = event.getPoint();
			updateGui();
			event.consume();
		}
		
		public void mousePressed(MouseEvent event) {
			if(Variables.InLoadCity){
				screens.loadCity.mousePressed(event);
			}
		}
		
		public void mouseReleased(MouseEvent event) {
			if(Variables.InLoadCity){
				screens.loadCity.mouseReleased(event);
			}
		}
	}
}
