package mainPackage;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class StringDraw {
	public static final int Middle = 0;
	public static final int Up = 1;
	public static final int UpRight = 2;
	public static final int Right = 3;
	public static final int DownRight = 4;
	public static final int Down = 5;
	public static final int DownLeft = 6;
	public static final int Left = 7;
	public static final int UpLeft = 8;

	static int getSpaceSize(final Graphics2D graph2){
		return getStringBounds(graph2, "h h", 0, 0).width - getStringBounds(graph2, "hh", 0, 0).width;
	}

	public static void drawMaxString(final Graphics2D graph2, final int borderSize, final String str, final int align, Rectangle bounds, final int fontType){
		bounds.x = bounds.x + borderSize;
		bounds.y = bounds.y + borderSize;
		bounds.width = bounds.width - borderSize * 2;
		bounds.height = bounds.height - borderSize * 2;
		if(bounds.width > 0 && bounds.height > 0){
			graph2.setFont(Variables.nowUsingFont.deriveFont(fontType, 101f));
			Rectangle s1Size = getStringBounds(graph2, str, 0, 0);
			final Double s1Per1Width = ((double) s1Size.width) / 101;
			final Double s1Per1Height = ((double) s1Size.height) / 101;
			if(s1Per1Width / s1Per1Height > bounds.width / bounds.height){
				graph2.setFont(Variables.nowUsingFont.deriveFont(fontType, (float) (bounds.width / s1Per1Width)));
			}
			else{
				graph2.setFont(Variables.nowUsingFont.deriveFont(fontType, (float) (bounds.height / s1Per1Height)));
			}
			s1Size = getStringBounds(graph2, str, 0, 0);
			final int up = bounds.y - s1Size.y;
			final int down = bounds.y + bounds.height - s1Size.height - s1Size.y;
			final int left = bounds.x;
			final int right = bounds.x + bounds.width - s1Size.width;
			final int xMiddle = bounds.x + bounds.width / 2 - s1Size.width / 2;
			final int yMiddle = bounds.y + bounds.height / 2 + s1Size.height / 2 - (s1Size.height + s1Size.y);
			switch(align){
			case 1:
				graph2.drawString(str, xMiddle, up);
				break;
			case 2:
				graph2.drawString(str, right, up);
				break;
			case 3:
				graph2.drawString(str, right, yMiddle);
				break;
			case 4:
				graph2.drawString(str, right, down);
				break;
			case 5:
				graph2.drawString(str, xMiddle, down);
				break;
			case 6:
				graph2.drawString(str, left, down);
				break;
			case 7:
				graph2.drawString(str, left, yMiddle);
				break;
			case 8:
				graph2.drawString(str, left, up);
				break;
			default:
				graph2.drawString(str, xMiddle, yMiddle);
			}
			s1Size = null;
		}
	}
	
	public static void drawMaxString(final Graphics2D graph2, final int borderSize, final String str, final int align, Rectangle bounds){
		drawMaxString(graph2, borderSize, str, align, bounds, Font.PLAIN);
	}

	public static void drawMaxString(final Graphics2D graph2, final String str, final int align, Rectangle bounds){
		drawMaxString(graph2, 0, str, align, bounds, Font.PLAIN);
	}
	
	public static void drawMaxString(final Graphics2D graph2, final int borderSize, final String str, Rectangle bounds){
		drawMaxString(graph2, borderSize, str, 0, bounds, Font.PLAIN);
	}
	
	public static void drawMaxString(final Graphics2D graph2, final String str, Rectangle bounds){
		drawMaxString(graph2, 0, str, 0, bounds, Font.PLAIN);
	}

	public static void drawMaxString(final Graphics2D graph2, final String str, Rectangle bounds, final int fontType){
		drawMaxString(graph2, 0, str, 0, bounds, fontType);
	}
	
	public static Rectangle getStringBounds(final Graphics2D g2, final String str, final float x, final float y) {
		if(str.length() != 0){
			final FontRenderContext frc = g2.getFontRenderContext();
			final GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
			final Rectangle result = gv.getPixelBounds(null, x, y);
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

}
