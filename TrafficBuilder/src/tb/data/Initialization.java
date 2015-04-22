package tb.data;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import tb.Main;

public class Initialization {
	
	public static void initializeData(){
		try {
			Main.resourcePackName = IO.readTextFile(System.getenv("APPDATA") + "\\TrafficBuilder\\SmallData\\LatestResources.txt");
			InputStream isG = new FileInputStream(new File(System.getenv("APPDATA") + "\\TrafficBuilder\\Resources\\" + Main.resourcePackName + "\\Font.font"));
			Main.usingFont =  (Font.createFont(Font.TRUETYPE_FONT, isG));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
