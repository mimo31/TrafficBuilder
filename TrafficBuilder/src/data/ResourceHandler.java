package data;
	import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import mainPackage.Functions;
import mainPackage.Variables;

public class ResourceHandler {

		public static void start(){

			try {
				Variables.ResourcePackName = Functions.readTextFile(System.getenv("APPDATA") + "\\TrafficBuilder\\SmallData\\LatestResources.txt");
				final InputStream isG = new FileInputStream(new File(System.getenv("APPDATA") + "\\TrafficBuilder\\Resources\\" + Variables.ResourcePackName + "\\Font.ttf"));
				Variables.nowUsingFont =  (Font.createFont(0, isG)).deriveFont(0, 50f);
			} catch (Exception e) {
				
			}
		}

}
