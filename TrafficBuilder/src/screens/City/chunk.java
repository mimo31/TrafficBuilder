package screens.City;

import mainPackage.Functions;

public class chunk {
	int[][] Lands;
	int positionX;
	int positionY;

	public int getPopulation(final int x, final int y){
		return this.Lands[x][y];
	}

	public void setPopulation(final int population, final int x, final int y){
		this.Lands[x][y] = population;
	}

	public chunk(final int x, final int y) {
		Lands = new int[4][4];
		positionX = x;
		positionY = y;
	}

	protected chunk(final int[][] chunkLands, final int x, final int y) {
		Lands = chunkLands;
		positionX = x;
		positionY = y;
	}

	public static chunk load(final int x, final int y, final String folderName) {
		final int chunkLands[][] = new int[4][4];
		byte[] readedBytes = null;
		try {
			readedBytes = Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + folderName + "\\map\\chunks\\" +
					x + "," + y);
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
		return new chunk(chunkLands, x, y);
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
				this.positionX + "," + this.positionY, false);
	}
}
