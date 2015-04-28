package tb.cityType;

import java.awt.Point;


public class Chunk {
	public int[][] Lands;
	public Point position;

	public int getPopulation(final int x, final int y){
		return this.Lands[x][y];
	}

	public void setPopulation(final int population, final int x, final int y){
		this.Lands[x][y] = population;
	}

	public Chunk(final Point position) {
		Lands = new int[4][4];
		this.position = position;
	}

	public Chunk(final int[][] chunkLands, final Point position) {
		Lands = chunkLands;
		this.position = position;
	}
	
	public DoubleChunk toDoubleChunk(){
		double[][] tempValues = new double[4][4];
		int counter = 0;
		while(counter < 4){
			int counter2 = 0;
			while(counter2 < 4){
				tempValues[counter][counter2] = Lands[counter][counter2];
				counter2++;
			}
			counter++;
		}
		return new DoubleChunk(tempValues, position);
	}
}
