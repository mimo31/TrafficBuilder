package tb.data;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IO {

	public static byte[] readBytes(File path) throws Exception {
		InputStream input = new FileInputStream(path);
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
	
	public static byte[] readBytes(final String path) throws Exception{
		return readBytes(new File(path));
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

	public static void writeTextToFile(final String text, final String path, final boolean append){
		try {
			final BufferedWriter bufw = new BufferedWriter(new FileWriter(path, true));
			bufw.write(text);
			bufw.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
