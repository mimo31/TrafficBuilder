package mainPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Functions {
	public static int modulo(int d, int mod){
		if(d >= 0){
			return d % mod;
		}
		else{
			while(d < 0){
				d = d + mod;
			}
			return d;
		}
	}
	
	public static void drawPauseButton(Graphics2D graph2){
		drawPauseButton(graph2, new Color(40, 40, 40));
	}
	
	public static void drawPauseButton(Graphics2D graph2, Color onMouseColor){
		Functions.drawChangRect(graph2, Color.black, onMouseColor, Variables.width / 200, Variables.height / 200, Variables.width / 16, Variables.height / 24);
		graph2.setColor(Color.WHITE);
		Functions.drawMaxString(graph2, "<<", new Rectangle(Variables.width / 200 + Variables.width / 100, Variables.height / 200 + Variables.height / 100, Variables.width / 16 * 1 / 2, Variables.height / 24 / 2));
	}
	
	public static void drawMaxString(Graphics2D g2, String str, Rectangle bounds, int fontType){
		if(bounds.width > 0 && bounds.height > 0){
			g2.setFont(Variables.nowUsingFont.deriveFont(fontType, 101f));
			Rectangle s1Size = Functions.getStringBounds(g2, str, 0, 0);
			Double s1Per1Width = ((double) s1Size.width) / 101;
			Double s1Per1Height = ((double) s1Size.height) / 101;
			if(s1Per1Width / s1Per1Height > bounds.width / bounds.height){
				g2.setFont(Variables.nowUsingFont.deriveFont(fontType, (float) (bounds.width / s1Per1Width)));
			}
			else{
				g2.setFont(Variables.nowUsingFont.deriveFont(fontType, (float) (bounds.height / s1Per1Height)));
			}
			s1Size = Functions.getStringBounds(g2, str, 0, 0);
			g2.drawString(str, bounds.x + bounds.width / 2 - s1Size.width / 2 , bounds.y + bounds.height / 2 + s1Size.height / 2 - (s1Size.height + s1Size.y));
			s1Size = null;
		}
	}
	
	public static void drawMaxString(Graphics2D g2, String str, Rectangle bounds){
		if(bounds.width > 0 && bounds.height > 0){
			g2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, 101f));
			Rectangle s1Size = Functions.getStringBounds(g2, str, 0, 0);
			Double s1Per1Width = ((double) s1Size.width) / 101;
			Double s1Per1Height = ((double) s1Size.height) / 101;
			if(s1Per1Width / s1Per1Height > bounds.width / bounds.height){
				g2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, (float) (bounds.width / s1Per1Width)));
			}	
			else{
				g2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, (float) (bounds.height / s1Per1Height)));
			}
			s1Size = Functions.getStringBounds(g2, str, 0, 0);
			g2.drawString(str, bounds.x + bounds.width / 2 - s1Size.width / 2 , bounds.y + bounds.height / 2 + s1Size.height / 2 - (s1Size.height + s1Size.y));
			s1Size = null;
		}
	}
	
	public static String readTextFile(String path){
		String[] ReadedLines = null;
		Path filePath = Paths.get(path);
		try {
			ReadedLines =  Files.readAllLines(filePath, Charset.defaultCharset()).toArray(new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String output = ReadedLines[0];

		int counter = 1;
		while(counter < ReadedLines.length){
			output = output + System.getProperty("line.separator") + ReadedLines[counter];
			counter++;
		}
		return output;
	}
	
	public static void writeBytesToFile(byte[] Bytes, String path, boolean append){
		FileOutputStream out;
		try {
			out = new FileOutputStream(path);
			out.write(Bytes);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] readBytes(String path) throws Exception{
		InputStream input;
			input = new FileInputStream(new File(path));
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int nRead;
		
		
		while ((nRead = input.read(data, 0, data.length)) != -1){
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		input.close();
		return buffer.toByteArray();
	}
	
	public static Rectangle getStringBounds(Graphics2D g2, String str, float x, float y) {
        if(str.length() != 0){
        	FontRenderContext frc = g2.getFontRenderContext();
        	GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        	Rectangle result = gv.getPixelBounds(null, x, y);
        	result.x = 0;
        	if(str.toCharArray()[0] == ' ' || str.toCharArray()[str.length() - 1] == ' '){
        		int counter = 0; 
        		while(counter < str.length()){
        			if(str.toCharArray()[counter] != ' '){
        				result.width = result.width + counter * getSpaceSize(g2);
        				counter = str.length() - 1;
        				while(counter >= 0){
        					if(str.toCharArray()[counter] != ' '){
        						result.width = result.width + (str.length() - 1 - counter) * getSpaceSize(g2);
        						return result;
        					}
        					counter--;
        				}
        			}
        			counter++;
        		}
	        	if(counter == str.length()){
        			return new Rectangle(0, 0, getSpaceSize(g2) * str.length(), 0);
        		}
        	}
        	return result;
        }else{return new Rectangle(0, 0, 0, 0);}
    }

	public static void writeTextToFile(String text, String path, boolean append){
		try {
			BufferedWriter bufw = new BufferedWriter(new FileWriter(path, true));
			bufw.write(text);
			bufw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static int getSpaceSize(Graphics2D graph2){
		return getStringBounds(graph2, "h h", 0, 0).width - getStringBounds(graph2, "hh", 0, 0).width;
	}
	
	public static boolean buttonClicked(MouseEvent event, int x, int y, int width, int height){
		return (event.getX() >= x && event.getY() >= y && event.getX() <= x + width && event.getY() <= y + height);
	}
	
	public static boolean isOnButton(int x, int y, int width, int height){
		return (Variables.lastMousePosition.getX() >= x && Variables.lastMousePosition.getY() >= y && Variables.lastMousePosition.getX() <= x + width && Variables.lastMousePosition.getY() <= y + height);
	}
	
	public static void drawChangRect(Graphics2D graph2, Color normalColor, Color onMouseColor, int x, int y, int width, int height){
		if(Functions.isOnButton(x, y, width, height)){
			graph2.setColor(onMouseColor);
		}else{
			graph2.setColor(normalColor);
		}
		graph2.fillRect(x, y, width, height);
	}
	
	public static void drawChangRect(Graphics2D graph2, Color normalColor, Color onMouseColor, Rectangle rectangle){
		drawChangRect(graph2, normalColor, onMouseColor, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	public static byte[] longToBytes(long number) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(number);
	    return buffer.array();
	}

	public static long bytesToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();
	    return buffer.getLong();
	}
	
	public static byte[] intToBytes(int number) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(number);
		return buffer.array();
	}
	
	public static int bytesToInt(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(4);
	    buffer.put(bytes);
	    buffer.flip();
	    return buffer.getInt();
	}
	
	public static boolean contains(int[] array, int value){
		int counter = 0;
		while(counter < array.length){
			if(array[counter] == value){
				return true;
			}
			counter++;
		}
		return false;
	}
}
