package screens.City;

import java.awt.Point;

import mainPackage.Functions;

public class chunk {
	int[][] Lands;
	Point position;

	public int getPopulation(final int x, final int y){
		return this.Lands[x][y];
	}

	public void setPopulation(final int population, final int x, final int y){
		this.Lands[x][y] = population;
	}

	public chunk(final Point position) {
		Lands = new int[4][4];
		this.position = position;
	}

	protected chunk(final int[][] chunkLands, final Point position) {
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

	public static chunk load(final Point position, final String folderName) {
		final int chunkLands[][] = new int[4][4];
		byte[] readedBytes = null;
		try {
			readedBytes = Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\map\\chunks\\" +
					position.x + "," + position.y);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		int counter = 0;
		int counter2;
		while(counter < 4){
			counter2 = 0;
			while(counter2 < 4){
				final byte[] bytes = new byte[4];
				bytes[0] = readedBytes[counter * 16 + counter2 * 4];
				bytes[1] = readedBytes[counter * 16 + counter2 * 4 + 1];
				bytes[2] = readedBytes[counter * 16 + counter2 * 4 + 2];
				bytes[3] = readedBytes[counter * 16 + counter2 * 4 + 3];
				chunkLands[counter2][counter] = Functions.bytesToInt(bytes);
				counter2++;
			}
			counter++;
		}
		return new chunk(chunkLands, position);
	}

	public void save() {
		final byte[] bytesToWrite = new byte[64];
		int counter = 0;
		int counter2;
		while(counter < 4){
			counter2 = 0;
			while(counter2 < 4){
				final byte[] intBytes = Functions.intToBytes(this.Lands[counter2][counter]);
				bytesToWrite[counter * 16 + counter2 * 4] = intBytes[0];
				bytesToWrite[counter * 16 + counter2 * 4 + 1] = intBytes[1];
				bytesToWrite[counter * 16 + counter2 * 4 + 2] = intBytes[2];
				bytesToWrite[counter * 16 + counter2 * 4 + 3] = intBytes[3];
				counter2++;
			}
			counter++;
		}
		Functions.writeBytesToFile(bytesToWrite, System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + city.theCity.folderName + "\\map\\chunks\\" +
				this.position.x + "," + this.position.y, false);
	}
}
