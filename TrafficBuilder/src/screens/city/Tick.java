package screens.city;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import mainPackage.Variables;

public class Tick extends PaintCity{
	
	
	static ActionListener timerAction = new ActionListener(){
		@Override
		public final void actionPerformed(final ActionEvent arg0){
			tick();
		} 
	};
	public final static Timer ticker = new Timer(25, timerAction);
	
	public static void tick(){
		if(errorDisappearTime <= System.currentTimeMillis()){
			errorText = "";
		}
		if(paused == false){
			theCity.time = theCity.time + 25;
			powerLineState = powerLineState + 25;
			changePopAndAddMoney();
		}
		if(inPauseMenu == false){
			endStationUpdateTime = endStationUpdateTime + 25;
		}
		if(endTCWBlinking < System.currentTimeMillis()){
			TCWframeBlack = true;
		}
		else{
			TCWframeBlack = (TCWframeBlack == false);
		}
		Variables.myGui.repaint();
	}
	
	public static void changePopAndAddMoney(){
		final DoubleMap maxPops = getMaxPopulations();
		final boolean addMoney;
		if(theCity.time % 10000 == 0){
			addMoney = true;
		}
		else{
			addMoney = false;
		}
		int counter = 0;
		while(counter < maxPops.chunks.length){
			int counter2 = 0;
			while(counter2 < 4){
				int counter3 = 0;
				while(counter3 < 4){
					final int boundedMaxPops;
					final Point position = new Point(maxPops.chunks[counter].position.x * 4 + counter2, maxPops.chunks[counter].position.y * 4 + counter3);
					if(maxPops.chunks[counter].getValue(counter2, counter3) > theCity.getMaxPopulation()){
						boundedMaxPops = theCity.getMaxPopulation();
					}
					else{
						boundedMaxPops = (int) Math.floor(maxPops.chunks[counter].getValue(counter2, counter3));
					}
					if(boundedMaxPops >= theCity.getPopulation(position)){
						final double boundsForSuccess = (1d / 16384d) * (boundedMaxPops - theCity.getPopulation(position));
						final double random = Math.random();
						if(boundsForSuccess > random){
							theCity.popMap.increaseValue(position, 1);
						}
						if(addMoney){
							theCity.money = (float) (theCity.money + theCity.getPopulation(position) / 8d);
						}
					}
					counter3++;
				}
				counter2++;
			}
			counter++;
		}
	}
	
	public static DoubleMap getMaxPopulations(){
		DoubleMap firstGradeComfort = applyLines(theCity.popMap.toDoubleMap());
		firstGradeComfort.add(theCity.popMap.toDoubleMap());
		return applyLines(firstGradeComfort);
	}
	
	public static DoubleMap applyLines(DoubleMap mapBase){
		DoubleMap comfortMap = new DoubleMap();
		int counter = 0;
		while(counter < theCity.lines.length){
			int counter2 = 0;
			double lineComfort = linePerStationComfort(counter, mapBase);
			while(counter2 < theCity.lines[counter].trace.length){
				comfortMap = applyStation(comfortMap, theCity.lines[counter].trace[counter2], lineComfort);
				counter2++;
			}
			counter++;
		}
		return comfortMap;
	}
	
	public static DoubleMap applyStation(DoubleMap comfortMap, Point position, double comfort){
		comfortMap.increaseValue(position, (int) comfort);
		int counter = 1;
		while(counter < 5){
			Point lastAddedNumber = new Point(position.x - counter, position.y - counter);
			while(lastAddedNumber.equals(new Point(position.x + counter , position.y - counter)) == false){
				comfortMap.increaseValue(new Point(lastAddedNumber.x + 1, lastAddedNumber.y), comfort / (counter + 1));
				lastAddedNumber = new Point(lastAddedNumber.x + 1, lastAddedNumber.y);
			}
			while(lastAddedNumber.equals(new Point(position.x + counter, position.y + counter)) == false){
				comfortMap.increaseValue(new Point(lastAddedNumber.x, lastAddedNumber.y + 1), comfort / (counter + 1));
				lastAddedNumber = new Point(lastAddedNumber.x, lastAddedNumber.y + 1);
			}
			while(lastAddedNumber.equals(new Point(position.x - counter, position.y + counter)) == false){
				comfortMap.increaseValue(new Point(lastAddedNumber.x - 1, lastAddedNumber.y), comfort / (counter + 1));
				lastAddedNumber = new Point(lastAddedNumber.x - 1, lastAddedNumber.y);
			}
			while(lastAddedNumber.equals(new Point(position.x - counter, position.y - counter)) == false){
				comfortMap.increaseValue(new Point(lastAddedNumber.x, lastAddedNumber.y - 1), comfort / (counter + 1));
				lastAddedNumber = new Point(lastAddedNumber.x, lastAddedNumber.y - 1);
			}
			counter++;
		}
		return comfortMap;
	}
	
	public static double linePerStationComfort(int lineNumber, DoubleMap popBase){
		double sumOfStationComforts = 0;
		int counter = 0;
		while(counter < theCity.lines[lineNumber].trace.length){
			sumOfStationComforts = sumOfStationComforts + stationGenComfort(theCity.lines[lineNumber].trace[counter], popBase);
			counter++;
		}
		return sumOfStationComforts / theCity.lines[lineNumber].trace.length;
	}
	
	public static double stationGenComfort(Point position, DoubleMap popBase){
		double valToSqrt = 0.0d;
		valToSqrt = theCity.getPopulation(position);
		int counter = 2;
		while(counter < 6){
			double circleValue = 0;
			Point lastAddedNumber = new Point(position.x - (counter - 1), position.y - (counter - 1));
			while(lastAddedNumber.equals(new Point(position.x + (counter - 1), position.y - (counter - 1))) == false){
				circleValue = circleValue + popBase.getValue(new Point(lastAddedNumber.x + 1, lastAddedNumber.y));
				lastAddedNumber = new Point(lastAddedNumber.x + 1, lastAddedNumber.y);
			}
			while(lastAddedNumber.equals(new Point(position.x + (counter - 1), position.y + (counter - 1))) == false){
				circleValue = circleValue + popBase.getValue(new Point(lastAddedNumber.x + 1, lastAddedNumber.y));
				lastAddedNumber = new Point(lastAddedNumber.x, lastAddedNumber.y + 1);
			}
			while(lastAddedNumber.equals(new Point(position.x - (counter - 1), position.y + (counter - 1))) == false){
				circleValue = circleValue + popBase.getValue(new Point(lastAddedNumber.x + 1, lastAddedNumber.y));
				lastAddedNumber = new Point(lastAddedNumber.x - 1, lastAddedNumber.y);
			}
			while(lastAddedNumber.equals(new Point(position.x - (counter - 1), position.y - (counter - 1))) == false){
				circleValue = circleValue + popBase.getValue(new Point(lastAddedNumber.x + 1, lastAddedNumber.y));
				lastAddedNumber = new Point(lastAddedNumber.x, lastAddedNumber.y - 1);
			}
			valToSqrt = circleValue / counter / (counter - 1) / 8d + valToSqrt;
			counter++;
		}
		return 1 + Math.sqrt(valToSqrt + 1);
	}
}
