package screens.City;

import java.awt.Point;

public class DoubleChunk {
	double[][] values;
	Point position;

	public double getValue(final int x, final int y){
		return this.values[x][y];
	}

	public void setValue(final double population, final int x, final int y){
		this.values[x][y] = population;
	}

	public DoubleChunk(final Point position) {
		this.values = new double[4][4];
		this.position = position;
	}

	public DoubleChunk(final double[][] chunkLands, final Point position) {
		this.values = chunkLands;
		this.position = position;
	}
}
