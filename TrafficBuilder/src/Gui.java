import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Gui extends JFrame {
	public Gui(boolean fullscreen) {
		if(fullscreen){
			this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			}
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Traffic Builder");
		this.addMouseListener(new InterfaceMouseEvents());
		this.addComponentListener(new InterfaceComponentEvents());
		this.getContentPane().setSize(250, 250);;
	}
	
	public static void updateGui(){
		if(Variables.InStart){
			Variables.myGui.add(new StartClass.paintIt());
		}
		Variables.height = Variables.myGui.getHeight();
		Variables.width = Variables.myGui.getWidth();
		System.out.println(Variables.height);
		System.out.println(Variables.width);
		Variables.a++;
		System.out.println(Variables.a);
	}
	
	public class InterfaceMouseEvents implements MouseListener{

		public void mouseClicked(MouseEvent event) {
			System.out.println("Breakpoint1");
			
		}

		public void mouseEntered(MouseEvent event) {
			
			
		}

		public void mouseExited(MouseEvent event) {
			
			
		}

		public void mousePressed(MouseEvent event) {
			
			
		}

		public void mouseReleased(MouseEvent event) {
			
			
		}
}
	public class InterfaceComponentEvents implements ComponentListener{

		public void componentMoved(ComponentEvent e) {
			updateGui();
			System.out.println("Breakpoint2");
		}
		
		public void componentResized(ComponentEvent e) {
			updateGui();
			System.out.println("Breakpoint3");
		}
		
		public void componentShown(ComponentEvent e) {
			
			
		}

		public void componentHidden(ComponentEvent e) {
			
			
		}
		
	}
}
