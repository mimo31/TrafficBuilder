package screens.City;

import java.awt.Color;
import java.awt.Point;

import mainPackage.Functions;

public class Line {

	public Point[] trace;
	public boolean upgraded;
	public Color lineColor;
	
	public Line(Point[] trace){
		this.trace = trace;
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
				totalLength = (float) (totalLength + Functions.getPointsDistance(trace[counter], trace[counter - 1]));
				counter++;
			}
		}
		return totalLength;
	}
	
	public int getPrice(){
		return (int) (trace.length * 1000 + this.getLength() * 500);
	}
}
