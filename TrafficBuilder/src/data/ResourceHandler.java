package data;
	import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import mainPackage.Functions;
import mainPackage.Variables;

public class ResourceHandler {
		static Image[] pops = new Image[128];
		static int[] popLevels = new int[128];
		static int nextIndex = 0;
		public static void start(){
			int counter = 0;
			while(counter < 128){
				popLevels[counter] = 10000;
				counter++;
			}
			try {
				Variables.ResourcePackName = Functions.readTextFile(System.getenv("APPDATA") + "\\TrafficBuilder\\SmallData\\LatestResources.txt");
				final InputStream isG = new FileInputStream(new File(System.getenv("APPDATA") + "\\TrafficBuilder\\Resources\\" + Variables.ResourcePackName + "\\Font.font"));
				Variables.nowUsingFont =  (Font.createFont(Font.TRUETYPE_FONT, isG));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public static Image getPopulationImage(int level){
			int counter = 0;
			while(counter < 128){
				if(popLevels[counter] == level){
					return pops[counter];
				}
				counter++;
			}
			Image image = null;
			try {
				image = ImageIO.read(new File(System.getenv("APPDATA") + "\\TrafficBuilder\\Resources\\Default\\pop" + String.valueOf(level) + ".png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			pops[nextIndex] = image;
			popLevels[nextIndex] = level;
			nextIndex = (nextIndex + 1) % 128;
			return image;
		}
}
