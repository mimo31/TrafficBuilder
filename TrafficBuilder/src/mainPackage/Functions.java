package mainPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
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
	
	public static int modulo(int d, final int mod){
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

	public static void drawPauseButton(final Graphics2D graph2){
		drawPauseButton(graph2, new Color(40, 40, 40));
	}

	public static void drawPauseButton(final Graphics2D graph2, final Color onMouseColor){
		Functions.drawChangRect(graph2, Color.black, onMouseColor, Variables.width / 200, Variables.height / 200, Variables.width / 16, Variables.height / 24);
		graph2.setColor(Color.WHITE);
		StringDraw.drawMaxString(graph2, "<<", new Rectangle(Variables.width / 200 + Variables.width / 100, Variables.height / 200 + Variables.height / 100, Variables.width / 16 * 1 / 2, Variables.height / 24 / 2));
	}
	
	public static String readTextFile(final String path){
		String[] ReadedLines = null;
		final Path filePath = Paths.get(path);
		try {
			ReadedLines =  Files.readAllLines(filePath, Charset.defaultCharset()).toArray(new String[0]);
		} catch (final IOException e) {
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

	public static void writeBytesToFile(final byte[] Bytes, final String path, final boolean append){
		FileOutputStream out;
		try {
			out = new FileOutputStream(path);
			out.write(Bytes);
			out.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] readBytes(final String path) throws Exception{
		InputStream input;
		input = new FileInputStream(new File(path));
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		final byte[] data = new byte[1024];
		int nRead;


		while ((nRead = input.read(data, 0, data.length)) != -1){
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		input.close();
		return buffer.toByteArray();
	}

	public static void writeTextToFile(final String text, final String path, final boolean append){
		try {
			final BufferedWriter bufw = new BufferedWriter(new FileWriter(path, true));
			bufw.write(text);
			bufw.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean buttonClicked(final MouseEvent event, final int x, final int y, final int width, final int height){
		return (event.getX() >= x && event.getY() >= y && event.getX() <= x + width && event.getY() <= y + height);
	}

	public static boolean isOnButton(final int x, final int y, final int width, final int height){
		return (Variables.lastMousePosition.getX() >= x && Variables.lastMousePosition.getY() >= y && Variables.lastMousePosition.getX() <= x + width && Variables.lastMousePosition.getY() <= y + height);
	}

	public static void drawChangRect(final Graphics2D graph2, final Color normalColor, final Color onMouseColor, final int x, final int y, final int width, final int height){
		if(Functions.isOnButton(x, y, width, height)){
			graph2.setColor(onMouseColor);
		}else{
			graph2.setColor(normalColor);
		}
		graph2.fillRect(x, y, width, height);
	}

	public static void drawChangRect(final Graphics2D graph2, final Color normalColor, final Color onMouseColor, final Rectangle rectangle){
		drawChangRect(graph2, normalColor, onMouseColor, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public static byte[] longToBytes(final long number) {
		final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(number);
		return buffer.array();
	}

	public static long bytesToLong(final byte[] bytes) {
		final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getLong();
	}
	
	public static byte[] floatToBytes(final float number) {
		final ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putFloat(number);
		return buffer.array();
	}
	
	public static float bytesToFloat(final byte[] bytes) {
		final ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getFloat();
	}
	
	public static byte[] intToBytes(final int number) {
		final ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(number);
		return buffer.array();
	}

	public static int bytesToInt(final byte[] bytes) {
		final ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getInt();
	}

	public static boolean contains(final int[] array, final int value){
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
