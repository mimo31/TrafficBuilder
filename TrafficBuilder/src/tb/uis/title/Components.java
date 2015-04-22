package tb.uis.title;

import java.awt.Rectangle;

import tb.Main;

public class Components {
	
	public static Rectangle TBtextBounds;
	public static Rectangle newCityButton;
	public static Rectangle loadCityButton;
	public static Rectangle settingsButton;
	
	public static void updateComponents(){
		TBtextBounds = new Rectangle(Main.guiWidth / 4,  Main.guiHeight / 32, Main.guiWidth / 2, Main.guiHeight / 16 * 3);
		newCityButton = new Rectangle(Main.guiWidth / 4, Main.guiHeight / 4, Main.guiWidth / 2, Main.guiHeight / 16);
		loadCityButton = new Rectangle(Main.guiWidth / 4, Main.guiHeight / 32 * 11, Main.guiWidth / 2, Main.guiHeight / 16);
		settingsButton = new Rectangle(Main.guiWidth / 4, Main.guiHeight / 16 * 7, Main.guiWidth / 2, Main.guiHeight / 16);
	}
}
