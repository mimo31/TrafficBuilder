package mainPackage;
	import java.awt.Point;

import data.ResourceHandler;

public class StartClass {
	
	public static void main(String[] args){
		Variables.lastMousePosition = new Point(0, 0);
		ResourceHandler.start();
		Variables.myGui = new Gui();
		screens.title.load();
		Gui.updateGui();
	}
}
