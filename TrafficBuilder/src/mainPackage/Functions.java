package mainPackage;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Functions {
	public static void drawMaxString(Graphics2D g2, String str, Rectangle bounds, int fontType){
		g2.setFont(Variables.nowUsingFont.deriveFont(fontType, 101f));
		Rectangle s1Size = new Rectangle(Functions.getStringBounds(g2, str, 0, 0));
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
	
	public static void drawMaxString(Graphics2D g2, String str, Rectangle bounds){
		g2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, 101f));
		Rectangle s1Size = new Rectangle(Functions.getStringBounds(g2, str, 0, 0));
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
	
	public static byte[] readBytes(String path) throws Exception{
		InputStream input = new FileInputStream(new File(path));
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
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, x, y);
    }
}
