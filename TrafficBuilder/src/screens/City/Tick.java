package screens.City;

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
}
