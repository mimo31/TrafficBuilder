package tb.cityType;

import java.awt.Color;
import java.awt.Point;

import tb.Mathematics;
import tb.data.IO;

public class Line {
	
	public Point[] trace;
	public boolean upgraded;
	public Color lineColor;
	public char codeChar;
	public int codeNumber;
	
	public Line(Point[] trace, char codeChar, int codeNumber){
		this.trace = trace;
		this.codeChar = codeChar;
		this.codeNumber = codeNumber;
	}
	
	public void addStartStation(Point station){
		Point[] temp = new Point[trace.length + 1];
		int counter = 0;
		while(counter < trace.length){
			temp[counter + 1] = trace[counter];
			counter++;
		}
		temp[0] = station;
		this.trace = temp;
	}
	
	public void addEndStation(Point station){
		Point[] temp = new Point[trace.length + 1];
		int counter = 0;
		while(counter < trace.length){
			temp[counter] = trace[counter];
			counter++;
		}
		temp[temp.length - 1] = station;
		this.trace = temp;
	}
	
	public void removeStartStation(){
		Point[] temp = new Point[trace.length - 1];
		int counter = 0;
		while(counter < temp.length){
			temp[counter] = trace[counter + 1];
			counter++;
		}
		trace = temp;
	}
	
	public void removeEndStation(){
		Point[] temp = new Point[trace.length - 1];
		int counter = 0;
		while(counter < temp.length){
			temp[counter] = trace[counter];
			counter++;
		}
		trace = temp;
	}
	
	public float getLength(){
		float totalLength = 0;
		if(trace.length > 1){
			int counter = 1;
			while(counter < trace.length){
				totalLength = (float) (totalLength + Mathematics.getPointsDistance(trace[counter], trace[counter - 1]));
				counter++;
			}
		}
		return totalLength;
	}
	
	public Line(byte[] bytes, char codeChar, int codeNumber){
		if(bytes[0] == 127){
			this.upgraded = true;
		}
		else{
			this.upgraded = false;
		}
		this.lineColor = new Color(bytes[1] + 128, bytes[2] + 128, bytes[3] + 128);
		this.trace = new Point[(bytes.length - 4) / 8];
		int counter = 0;
		while(counter < trace.length){
			final byte[] xBytes = new byte[]{bytes[4 + counter * 8], bytes[4 + counter * 8 + 1], bytes[4 + counter * 8 + 2], bytes[4 + counter * 8 + 3]};
			final byte[] yBytes = new byte[]{bytes[4 + counter * 8 + 4], bytes[4 + counter * 8 + 5], bytes[4 + counter * 8 + 6], bytes[4 + counter * 8 + 7]};
			this.trace[counter] = new Point(IO.bytesToInt(xBytes), IO.bytesToInt(yBytes));
			counter++;
		}
		this.codeChar = codeChar;
		this.codeNumber = codeNumber;
	}
	
	public byte[] toBytes(){
		byte[] bytes = new byte[trace.length * 8 + 4];
		if(this.upgraded){
			bytes[0] = 127;
		}
		else{
			bytes[0] = -128;
		}
		bytes[1] = (byte) (lineColor.getRed() - 128);
		bytes[2] = (byte) (lineColor.getGreen() - 128);
		bytes[3] = (byte) (lineColor.getBlue() - 128);
		int counter = 0;
		while(counter < this.trace.length){
			final byte[] pointXBytes = IO.intToBytes(this.trace[counter].x);
			final byte[] pointYBytes = IO.intToBytes(this.trace[counter].y);
			bytes[4 + counter * 8] = pointXBytes[0];
			bytes[4 + counter * 8 + 1] = pointXBytes[1];
			bytes[4 + counter * 8 + 2] = pointXBytes[2];
			bytes[4 + counter * 8 + 3] = pointXBytes[3];
			bytes[4 + counter * 8 + 4] = pointYBytes[0];
			bytes[4 + counter * 8 + 5] = pointYBytes[1];
			bytes[4 + counter * 8 + 6] = pointYBytes[2];
			bytes[4 + counter * 8 + 7] = pointYBytes[3];
			counter++;
		}
		return bytes;
	}
	
	public int getPrice(){
		return (int) (trace.length * 1000 + this.getLength() * 500);
	}
}
