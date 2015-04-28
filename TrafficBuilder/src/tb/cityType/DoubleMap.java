package tb.cityType;

import java.awt.Point;

import tb.Mathematics;

public class DoubleMap {
public DoubleChunk[] chunks = new DoubleChunk[0];
	
	public DoubleMap(){
		
	}
	
	public DoubleMap(DoubleChunk[] chunks){
		this.chunks = chunks;
	}
	
	public void add(DoubleMap map){
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
						final double popSum = map.chunks[counter].getValue(counter3, counter4) + this.chunks[counter2].getValue(counter3, counter4);
						this.chunks[counter2].setValue(popSum, counter3, counter4);
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
	
	public void addChunk(DoubleChunk chunkToAdd){
		DoubleChunk[] temp = new DoubleChunk[this.chunks.length + 1];
		int counter = 0;
		while(counter < this.chunks.length){
			temp[counter] = this.chunks[counter];
			counter++;
		}
		temp[temp.length - 1] = chunkToAdd;
		this.chunks = temp;
	}
	
	public double getValue(Point position){
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
			return this.chunks[counter].getValue(posInChunk.x, posInChunk.y);
		}
		else{
			return 0;
		}
	}
	
	public void increaseValue(Point position, double increaseBy){
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
			chunks[counter].setValue(chunks[counter].getValue(posInChunk.x, posInChunk.y) + increaseBy, posInChunk.x, posInChunk.y);
		}
		else{
			DoubleChunk newChunk = new DoubleChunk(chunkPos);
			newChunk.setValue(increaseBy, posInChunk.x, posInChunk.y);
			addChunk(newChunk);
		}
	}
	
	public void setValue(Point position, double value){
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
			chunks[counter].setValue(value, posInChunk.x, posInChunk.y);
		}
		else{
			DoubleChunk newChunk = new DoubleChunk(chunkPos);
			newChunk.setValue(value, posInChunk.x, posInChunk.y);
			addChunk(newChunk);
		}
	}
}
