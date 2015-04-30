package tb.uis.city;

import java.awt.geom.Area;

public class LineComponentGraphics {
	
	public Area area;
	public int lineIndex;
	public int componentIndex;
	
	public LineComponentGraphics(Area area, int lineIndex, int componentIndex){
		this.area = area;
		this.lineIndex = lineIndex;
		this.componentIndex = componentIndex;
	}
	
}
