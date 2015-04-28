package tb.cityType;

import java.awt.Point;

import tb.Mathematics;


public class IntMap {
public Chunk[] chunks = new Chunk[0];
	
	public IntMap(){
		
	}
	
	public DoubleMap toDoubleMap(){
		DoubleChunk[] tempChunks = new DoubleChunk[chunks.length];
		int counter = 0;
		while(counter < tempChunks.length){
			tempChunks[counter] = chunks[counter].toDoubleChunk();
			counter++;
		}
		return new DoubleMap(tempChunks);
	}
	
	public void setValue(Point position, int value){
		final Point chunkPos = new Point((int) Math.floor(position.x / 4d), (int) Math.floor(position.y / 4d));
		final Point posInChunk = new Point(Mathematics.modulo(position.x, 4), Mathematics.modulo(position.y, 4));
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
			Chunk newChunk = new Chunk(chunkPos);
			newChunk.setPopulation(value, posInChunk.x, posInChunk.y);
			addChunk(newChunk);
		}
	}
	
	public void increaseValue(Point position, int increaseBy){
		final Point chunkPos = new Point((int) Math.floor(position.x / 4d), (int) Math.floor(position.y / 4d));
		final Point posInChunk = new Point(Mathematics.modulo(position.x, 4), Mathematics.modulo(position.y, 4));
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
			chunks[counter].setPopulation(chunks[counter].getPopulation(posInChunk.x, posInChunk.y) + increaseBy, posInChunk.x, posInChunk.y);
		}
		else{
			Chunk newChunk = new Chunk(chunkPos);
			newChunk.setPopulation(increaseBy, posInChunk.x, posInChunk.y);
			addChunk(newChunk);
		}
	}
	
	public void add(IntMap map){
		int counter = 0;
		while(counter < map.chunks.length){
			boolean chunkFound = false;
			int counter2 = 0;
			while(counter2 < this.chunks.length){
				if(map.chunks[counter].position.equals(this.chunks[counter2])){
					chunkFound = true;
					break;
				}
				counter2++;
			}
			if(chunkFound){
				int counter3 = 0;
				int counter4 = 0;
				while(counter2 < 4){
					while(counter3 < 4){
						final int popSum = map.chunks[counter].getPopulation(counter3, counter4) + this.chunks[counter2].getPopulation(counter3, counter4);
						this.chunks[counter2].setPopulation(popSum, counter3, counter4);
						counter3++;
					}
					counter2++;
				}
				
			}
			else{
				this.addChunk(map.chunks[counter]);
			}
			counter++;
		}
	}
	
	public void addChunk(Chunk chunkToAdd){
		Chunk[] temp = new Chunk[this.chunks.length + 1];
		int counter = 0;
		while(counter < this.chunks.length){
			temp[counter] = this.chunks[counter];
			counter++;
		}
		temp[temp.length - 1] = chunkToAdd;
		this.chunks = temp;
	}
	
	public int getValue(Point position){
		final Point chunkPos = new Point((int) Math.floor(position.x / 4d), (int) Math.floor(position.y / 4d));
		final Point posInChunk = new Point(Mathematics.modulo(position.x, 4), Mathematics.modulo(position.y, 4));
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
