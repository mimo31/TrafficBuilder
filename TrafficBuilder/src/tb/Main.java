package tb;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

public class Main {

	public static JFrame gui;
	public static int guiWidth;
	public static int guiHeight;
	public static Point mousePosition = new Point(0, 0);
	public static Font usingFont;
	public static String resourcePackName;
	static UI[] interfaces = new UI[4];
	static int activeInterfaceId;
	static int lastInterfaceId = 0;

	public static void main(String arg0[]) {
		interfaces[0] = new tb.uis.title.Interface();
		interfaces[2] = new tb.uis.loadCity.Interface();
		activeInterfaceId = 0;
		tb.data.Initialization.initializeData();
		initializeGui();
	}

	public static void processResult(ActionResult result) {
		if (result != null) {
			if (result.close) {
				if (result.closeId == -1) {
					int closingInterfaceId = activeInterfaceId;
					activeInterfaceId = lastInterfaceId;
					lastInterfaceId = closingInterfaceId;
				} else {
					lastInterfaceId = activeInterfaceId;
					activeInterfaceId = result.closeId;
				}
			}
		}
	}

	public static void initializeGui() {
		gui = new JFrame();
		gui.setSize(200, 200);
		gui.setExtendedState(gui.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setTitle("Traffic Builder");
		gui.getContentPane().addMouseListener(mouseEvents);
		gui.addComponentListener(componentEvents);
		gui.addKeyListener(keyEvents);
		gui.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				interfaces[activeInterfaceId].close();
			}
		});
		gui.getContentPane().addMouseMotionListener(mouseEvents);
		gui.add(new paintGui());
		gui.setVisible(true);
	}

	@SuppressWarnings("serial")
	public static class paintGui extends JComponent {
		@Override
		public void paint(Graphics g) {
			Graphics2D graph2 = (Graphics2D) g;
			ExtendedGraphics2D exGraph = new ExtendedGraphics2D(graph2);
			ActionResult result = interfaces[activeInterfaceId].paint(graph2, exGraph);
			processResult(result);
		}
	}

	static KeyListener keyEvents = new KeyListener() {

		@Override
		public void keyPressed(final KeyEvent event) {

		}

		@Override
		public void keyReleased(final KeyEvent event) {
			processResult(interfaces[activeInterfaceId].keyReleased(event));
		}

		@Override
		public void keyTyped(final KeyEvent event) {

		}

	};

	static ComponentListener componentEvents = new ComponentListener() {

		@Override
		public void componentMoved(ComponentEvent e) {

		}

		@Override
		public void componentResized(final ComponentEvent e) {
			Container content = gui.getContentPane();
			guiHeight = content.getHeight();
			guiWidth = content.getWidth();
			gui.repaint();
		}

		@Override
		public void componentShown(final ComponentEvent e) {

		}

		@Override
		public void componentHidden(final ComponentEvent e) {

		}

	};

	static MouseInputAdapter mouseEvents = new MouseInputAdapter() {
		@Override
		public void mouseClicked(final MouseEvent event) {
			processResult(interfaces[activeInterfaceId].mouseClicked(event));
		}

		@Override
		public void mouseDragged(final MouseEvent event) {
			processResult(interfaces[activeInterfaceId].mouseDragged(event));
		}

		@Override
		public void mouseMoved(final MouseEvent event) {
			mousePosition = event.getPoint();
			gui.repaint();
		}

		@Override
		public void mousePressed(final MouseEvent event) {
			processResult(interfaces[activeInterfaceId].mousePressed(event));
		}

		@Override
		public void mouseReleased(final MouseEvent event) {
			processResult(interfaces[activeInterfaceId].mouseReleased(event));
		}
	};
}
