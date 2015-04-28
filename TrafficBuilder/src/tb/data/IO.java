package tb.data;

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

public class IO {

	public static float bytesToFloat(final byte[] bytes) {
		final ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getFloat();
	}
	
	public static int bytesToInt(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getInt();
	}
	
	public static long bytesToLong(final byte[] bytes) {
		final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getLong();
	}

	public static byte[] concatenateTwo4bytesArrays(final byte[] bytes1, final byte[] bytes2){
		return new byte[]{bytes1[0], bytes1[1], bytes1[2], bytes1[3], bytes2[0], bytes2[1], bytes2[2], bytes2[3]};
	}

	public static byte[] floatToBytes(final float number) {
		final ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putFloat(number);
		return buffer.array();
	}
	
	public static byte[] getFirst4ofByte(final byte[] bytes){
		return new byte[]{bytes[0], bytes[1], bytes[2], bytes[3]};
	}
	
	public static String getInGameDir(String dir){
		return System.getenv("APPDATA") + "\\TrafficBuilder" + dir;
	}
	
	public static File getInGameFile(String dir){
		return new File(System.getenv("APPDATA") + "\\TrafficBuilder" + dir);
	}
	
	public static byte[] getLast4ofByte(final byte[] bytes){
		return new byte[]{bytes[4], bytes[5], bytes[6], bytes[7]};
	}
	
	public static byte[] intToBytes(final int number) {
		final ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(number);
		return buffer.array();
	}

	public static byte[] longToBytes(final long number) {
		final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(number);
		return buffer.array();
	}

	public static byte[] readBytes(File path) throws Exception {
		InputStream input = new FileInputStream(path);
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

	public static byte[] readBytes(final String path) throws Exception{
		return readBytes(new File(path));
	}

	public static String readTextFile(final String path){
		String[] ReadedLines = null;
		Path filePath = Paths.get(path);
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

	public static void writeBytesToFile(final byte[] bytes, final String path, final boolean append){
		FileOutputStream out;
		try {
			out = new FileOutputStream(path);
			out.write(bytes);
			out.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeTextToFile(final String text, final String path, final boolean append){
		try {
			BufferedWriter bufw = new BufferedWriter(new FileWriter(path, true));
			bufw.write(text);
			bufw.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
