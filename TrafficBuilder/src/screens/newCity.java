package screens;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import mainPackage.Variables;

public class newCity {
	
	public static void load(){
		Variables.InNewCity = true;
	}
	
	public static void close(){
		Variables.InNewCity = false;
	}
	
	public static void paint(Graphics g){
		Graphics2D graph2 = (Graphics2D)g;
		graph2.fill(new Rectangle(Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16));
		System.out.println("painted");
	}
}
