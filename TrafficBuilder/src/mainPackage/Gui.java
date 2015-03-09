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
public class Gui extends Variables{
	public static void initializeGraphics() {
		gui = new JFrame();
		gui.setSize(200, 200);
		gui.setExtendedState(gui.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setTitle("Traffic Builder");
		gui.getContentPane().addMouseListener(mouseEvents);
		gui.addComponentListener(componentEvents);
		gui.addKeyListener(keyEvents);
		gui.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(InCity){
					screens.city.City.close();
				}
			}
		});
		gui.getContentPane().addMouseMotionListener(mouseEvents);
		gui.add(new paintIt());
	}

	public static void updateGui(){
		final Container Test = gui.getContentPane();
		height = Test.getHeight();
		width = Test.getWidth();
		gui.repaint();
	}

	public static class paintIt extends JComponent{
		@Override
		public void paint(final Graphics g){
			if(InStart){
				screens.Title.paint(g);
			}
			else if(InNewCity) {
				screens.NewCity.paint(g);
			}
			else if(InCity){
				screens.city.City.paint(g);
			}
			else if(InLoadCity){
				screens.LoadCity.paint(g);
			}
		}
	}
	
	private static KeyListener keyEvents = new KeyListener(){
		
		@Override
		public void keyPressed(final KeyEvent event) {

		}

		@Override
		public void keyReleased(final KeyEvent event) {
			if(InNewCity){
				screens.NewCity.keyReleased(event);
			}
			else if(InCity){
				screens.city.City.keyReleased(event);
			}
		}

		@Override
		public void keyTyped(final KeyEvent event) {

		}

	};
	
	private static ComponentListener componentEvents = new ComponentListener(){

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
		
	};
	
	private static MouseInputAdapter mouseEvents = new MouseInputAdapter(){
		@Override
		public void mouseClicked(final MouseEvent event) {
			if(InStart){
				screens.Title.mouseClicked(event);
			}
			else if(InNewCity){
				screens.NewCity.mouseClicked(event);
			}
			else if(InLoadCity){
				screens.LoadCity.mouseClicked(event);
			}
			else if(InCity){
				screens.city.City.mouseClicked(event);
			}
		}

		@Override
		public void mouseDragged(final MouseEvent event) {
			if(InCity){
				screens.city.City.mouseDragged(event);
			}
			lastMousePosition = event.getPoint();
			updateGui();
			event.consume();
		}

		@Override
		public void mouseMoved(final MouseEvent event) {
			lastMousePosition = event.getPoint();
			updateGui();
			event.consume();
		}

		@Override
		public void mousePressed(final MouseEvent event) {
			if(InLoadCity){
				screens.LoadCity.mousePressed(event);
			}
			else if(InCity){
				screens.city.City.mousePressed(event);
			}
		}

		@Override
		public void mouseReleased(final MouseEvent event) {
			if(InLoadCity){
				screens.LoadCity.mouseReleased(event);
			}
			else if(InCity){
				screens.city.City.mouseRelesed(event);
			}
		}
	};
}
