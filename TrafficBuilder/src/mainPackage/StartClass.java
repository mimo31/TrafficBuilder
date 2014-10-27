package mainPackage;
	import data.ResourceHandler;

public class StartClass {
	
	public static void main(String[] args){
		ResourceHandler.start();
		Variables.myGui = new Gui();
		screens.title.load();
		Gui.updateGui();
	}
}
