package tb;

import java.awt.Point;
import java.awt.Rectangle;

public class Mathematics {


	public static Rectangle addBorders(Rectangle rect, int border){
		return new Rectangle(rect.x + border, rect.y + border, rect.width - 2 * border, rect.height - 2 * border);
	}
	
	public static int genRandom(int downBoundsInclusive, int upBoundsExclusive){
		return (int) (downBoundsInclusive + Math.floor(Math.random() * (upBoundsExclusive - downBoundsInclusive)));
	}
	
	public static double getPointsDistance(Point p1, Point p2){
		final int xDist = (int) (p1.getX() - p2.getX());
		final int yDist = (int) (p1.getY() - p2.getY());
		return Math.sqrt(xDist * xDist + yDist * yDist);
	}
	
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
}
