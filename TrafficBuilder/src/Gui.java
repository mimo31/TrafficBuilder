import javax.swing.*;
import java.awt.*;
public class Gui extends JFrame {

	public Gui() {
		this.setVisible(true);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
	
}
