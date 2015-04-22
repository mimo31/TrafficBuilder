package tb.uis.loadCity;

import java.awt.Polygon;
import java.awt.Rectangle;

import tb.Main;

public class Components {
	
	public static Rectangle titleRect;
	public static Rectangle scrollBar;
	public static Polygon upScrollTriangle;
	public static Polygon downScrollTriangle;
	
	public static void updateComponents(){
		titleRect = new Rectangle(0, 0, Main.guiWidth, Main.guiHeight / 6);
		scrollBar = new Rectangle(Main.guiWidth - Main.guiWidth / 40, Main.guiHeight / 6, Main.guiWidth / 40, Main.guiHeight - Main.guiHeight / 6);
		upScrollTriangle = new Polygon(new int[]{Main.guiWidth - Main.guiWidth / 48, Main.guiWidth - Main.guiWidth / 80, Main.guiWidth - Main.guiWidth / 240},
				new int[]{(int) (Main.guiWidth / 240 + Main.guiHeight / 6 + Math.sqrt(3 * Math.pow(Main.guiWidth / 120, 2))), Main.guiWidth / 240 + Main.guiHeight / 6, (int) (Main.guiWidth / 240 + Main.guiHeight / 6 + Math.sqrt(3 * Math.pow(Main.guiWidth / 120, 2)))}, 3);
		downScrollTriangle = new Polygon(new int[]{Main.guiWidth - Main.guiWidth / 48, Main.guiWidth - Main.guiWidth / 80, Main.guiWidth - Main.guiWidth / 240},
				new int[]{(int) (Main.guiHeight - Main.guiWidth / 240 - Math.sqrt(3 * Math.pow(Main.guiWidth / 120, 2))), Main.guiHeight - Main.guiWidth / 240, (int) (Main.guiHeight - Main.guiWidth / 240 - Math.sqrt(3 * Math.pow(Main.guiWidth / 120, 2)))}, 3);
	}
	
}
