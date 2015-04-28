package tb.uis.newCity;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import tb.Main;
import tb.Mathematics;


public class Components {

	public static Rectangle titleBounds;
	public static Rectangle okButton;
	public static Rectangle insideOkButton;
	public static Rectangle OKbounds;
	public static Rectangle blankBounds;
	public static Rectangle backButton;
	public static Rectangle backBounds;
	
	public static void updateComponents(){
		Interface.nameTextbox.size = new Dimension(Main.guiWidth / 2, Main.guiHeight / 8);
		Interface.nameTextbox.position = new Point(Main.guiWidth / 4, Main.guiHeight / 2);
		titleBounds = new Rectangle(Main.guiWidth / 4, Main.guiHeight / 8, Main.guiWidth / 2, Main.guiHeight / 4);
		okButton = new Rectangle(Main.guiWidth / 4 * 3 - Main.guiWidth / 12, Main.guiHeight / 8 * 5 + Main.guiHeight / 16, Main.guiWidth / 12, Main.guiHeight / 16);
		int okBorder = (Main.guiWidth / 12 + Main.guiHeight / 16) / 32;
		insideOkButton = Mathematics.addBorders(okButton, okBorder);
		OKbounds = Mathematics.addBorders(insideOkButton, okBorder);
		if(Interface.showBlankWarning){
			blankBounds = Mathematics.addBorders(new Rectangle(0, Main.guiHeight / 8 * 7, Main.guiWidth, Main.guiHeight / 8), Main.guiHeight / 64);
		}
		backButton = new Rectangle(Main.guiWidth / 200, Main.guiHeight / 200, Main.guiWidth / 16, Main.guiHeight / 24);
		backBounds = new Rectangle(Main.guiWidth / 200 + Main.guiWidth / 100, Main.guiHeight / 200 + Main.guiHeight / 100, Main.guiWidth / 16 / 2, Main.guiHeight / 24 / 2);
	}
	
}
