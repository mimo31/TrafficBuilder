package screens.City;

import mainPackage.Functions;

public class chunk {
	squareLand[][] Lands = new squareLand[4][4];
	int positionX;
	int positionY;
	
	public int getPopulation(int x, int y){
		return this.Lands[x][y].population;
	}
	
	public void setPopulation(int population, int x, int y){
		this.Lands[x][y].population = population;
	}
	
	public chunk(int x, int y) {
		positionX = x;
		positionY = y;
	}
	
	protected chunk(squareLand[][] chunkLands, int x, int y) {
		Lands = chunkLands;
		positionX = x;
		positionY = y;
	}
	
	public chunk load(int x, int y) {
		squareLand chunkLands[][] = new squareLand[4][4];
		byte[] readedBytes = null;
		try {
			readedBytes = Functions.readBytes(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves\\" + city.theCity.folderName + "\\map\\chunks\\" + 
			x + "," + y);
		} catch (Exception e) {
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
				chunkLands[counter2][counter] = new squareLand(Functions.bytesToInt(bytes));
				counter2++;
			}
			counter++;
		}
		return new chunk(chunkLands, x, y);
	}
	
	public void save() {
		byte[] bytesToWrite = new byte[64];
		int counter = 0;
		int counter2;
		while(counter < 4){
			counter2 = 0;
			while(counter2 < 4){
				final byte[] intBytes = Functions.intToBytes(this.Lands[counter2][counter].population);
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
