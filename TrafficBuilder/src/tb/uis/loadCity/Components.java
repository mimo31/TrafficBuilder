package tb.uis.loadCity;

import java.awt.Polygon;
import java.awt.Rectangle;

import tb.Main;

public class Components {
	
	public static Rectangle titleRect;
	public static Rectangle scrollBar;
	public static Polygon upScrollTriangle;
	public static Polygon downScrollTriangle;
	public static Rectangle didntCreateBounds;
	public static Rectangle createOneButton;
	public static Rectangle[] cityBlocks;
	public static int firstCityBlockIndex;
	public static Rectangle backButton;
	public static Rectangle backBounds;
	
	public static void updateComponents(){
		titleRect = new Rectangle(0, 0, Main.guiWidth, Main.guiHeight / 6);
		scrollBar = new Rectangle(Main.guiWidth - Main.guiWidth / 40, Main.guiHeight / 6, Main.guiWidth / 40, Main.guiHeight - Main.guiHeight / 6);
		upScrollTriangle = new Polygon(new int[]{Main.guiWidth - Main.guiWidth / 48, Main.guiWidth - Main.guiWidth / 80, Main.guiWidth - Main.guiWidth / 240},
				new int[]{(int) (Main.guiWidth / 240 + Main.guiHeight / 6 + Math.sqrt(3 * Math.pow(Main.guiWidth / 120, 2))), Main.guiWidth / 240 + Main.guiHeight / 6, (int) (Main.guiWidth / 240 + Main.guiHeight / 6 + Math.sqrt(3 * Math.pow(Main.guiWidth / 120, 2)))}, 3);
		downScrollTriangle = new Polygon(new int[]{Main.guiWidth - Main.guiWidth / 48, Main.guiWidth - Main.guiWidth / 80, Main.guiWidth - Main.guiWidth / 240},
				new int[]{(int) (Main.guiHeight - Main.guiWidth / 240 - Math.sqrt(3 * Math.pow(Main.guiWidth / 120, 2))), Main.guiHeight - Main.guiWidth / 240, (int) (Main.guiHeight - Main.guiWidth / 240 - Math.sqrt(3 * Math.pow(Main.guiWidth / 120, 2)))}, 3);
		if(Interface.cityInfos.length == 0){
			didntCreateBounds = new Rectangle(Main.guiWidth / 8, Main.guiHeight * 3 / 12, Main.guiWidth * 3 / 4, Main.guiHeight / 6);
			createOneButton = new Rectangle(Main.guiWidth / 2 - Main.guiWidth / 12, Main.guiHeight * 5 / 12, Main.guiWidth / 6, Main.guiHeight / 16);
			cityBlocks = new Rectangle[0];
		} else {
			int gapSize;
			int cityBlockWidth;
			boolean fullBlocks;
			if(Main.guiWidth - Main.guiWidth / 40 > 900){
				cityBlockWidth = (Main.guiWidth - Main.guiWidth / 40) / 2;
				gapSize = cityBlockWidth / 3 / 10;
				fullBlocks = false;
			} else {
				gapSize = (Main.guiWidth - Main.guiWidth / 40) / 32;
				cityBlockWidth = (Main.guiWidth - Main.guiWidth / 40) - 2 * gapSize;
				fullBlocks = true;
			}
			int spaceUsed = (int) ((cityBlockWidth / 3 + gapSize) * (1 - (Interface.listPosition % 1)));
			firstCityBlockIndex = (int) Math.floor(Interface.listPosition);
			cityBlocks = new Rectangle[1];
			if(fullBlocks){
				cityBlocks[0] = new Rectangle(gapSize, titleRect.height + spaceUsed - cityBlockWidth / 3, cityBlockWidth, cityBlockWidth / 3);
			} else {
				cityBlocks[0] = new Rectangle(cityBlockWidth / 2, titleRect.height + spaceUsed - cityBlockWidth / 3, cityBlockWidth, cityBlockWidth / 3);
			}
			while(spaceUsed < Main.guiHeight - titleRect.height &&  firstCityBlockIndex + cityBlocks.length < Interface.cityInfos.length){
				spaceUsed = spaceUsed + gapSize;
				if(fullBlocks){
					addCityBlock(new Rectangle(gapSize, titleRect.height + spaceUsed, cityBlockWidth, cityBlockWidth / 3));
				} else {
					addCityBlock(new Rectangle(cityBlockWidth / 2, titleRect.height + spaceUsed, cityBlockWidth, cityBlockWidth / 3));
				}
				spaceUsed = spaceUsed + cityBlockWidth / 3;
			}
		}
		backButton = new Rectangle(Main.guiWidth / 200, Main.guiHeight / 200, Main.guiWidth / 16, Main.guiHeight / 24);
		backBounds = new Rectangle(Main.guiWidth / 200 + Main.guiWidth / 100, Main.guiHeight / 200 + Main.guiHeight / 100, Main.guiWidth / 16 / 2, Main.guiHeight / 24 / 2);
	}
	
	public static void addCityBlock(Rectangle block){
		Rectangle[] temp = new Rectangle[cityBlocks.length + 1];
		for(int i = 0; i < cityBlocks.length; i++){
			temp[i] = cityBlocks[i];
		}
		temp[temp.length - 1] = block;
		cityBlocks = temp;
	}
	
}
