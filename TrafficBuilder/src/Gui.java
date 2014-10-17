import javax.swing.*;

@SuppressWarnings("serial")
public class Gui extends JFrame {

	public Gui() {
		this.setVisible(true);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setTitle("Traffic Builder");
	}
	
}
