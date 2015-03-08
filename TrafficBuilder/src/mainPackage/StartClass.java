package mainPackage;
import java.awt.Point;

import data.ResourceHandler;

public class StartClass {

	public static void main(final String[] args){
		Variables.lastMousePosition = new Point(0, 0);
		ResourceHandler.start();
		Gui.initializeGraphics();
		screens.Title.load();
		Gui.updateGui();
	}
}
