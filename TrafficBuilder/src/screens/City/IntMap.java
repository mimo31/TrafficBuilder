package screens.City;

import java.awt.Point;

import mainPackage.Functions;

public class IntMap {
	public chunk[] chunks = new chunk[0];
	
	public IntMap(){
		
	}
	
	public void setValue(Point position, int value){
		final Point chunkPos = new Point((int) Math.floor(position.x / 4), (int) Math.floor(position.y / 4));
		final Point posInChunk = new Point(Functions.modulo(position.x, 4), Functions.modulo(position.y, 4));
		boolean chunkFound = false;
		int counter = 0;
		while(counter < chunks.length){
			if(chunks[counter].position.equals(chunkPos)){
				chunkFound = true;
				break;
			}
			counter++;
		}
		if(chunkFound){
			chunks[counter].setPopulation(value, posInChunk.x, posInChunk.y);
		}
		else{
			chunk newChunk = new chunk(chunkPos);
			newChunk.setPopulation(value, posInChunk.x, posInChunk.y);
			addChunk(newChunk);
		}
	}
	
	public void addChunk(chunk chunkToAdd){
		chunk[] temp = new chunk[this.chunks.length + 1];
		int counter = 0;
		while(counter < this.chunks.length){
			temp[counter] = this.chunks[counter];
			counter++;
		}
		temp[temp.length - 1] = chunkToAdd;
		this.chunks = temp;
	}
	
	public int getValue(Point position){
		final Point chunkPos = new Point((int) Math.floor(position.x / 4), (int) Math.floor(position.y / 4));
		final Point posInChunk = new Point(Functions.modulo(position.x, 4), Functions.modulo(position.y, 4));
		boolean chunkFound = false;
		int counter = 0;
		while(counter < this.chunks.length){
			if(this.chunks[counter].position.equals(chunkPos)){
				chunkFound = true;
				break;
			}
			counter++;
		}
		if(chunkFound){
			return this.chunks[counter].getPopulation(posInChunk.x, posInChunk.y);
		}
		else{
			return 0;
		}
	}
}
