package tb.uis.city;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import tb.Main;


public class Tick {
	
	static ActionListener timerAction = new ActionListener(){
		@Override
		public final void actionPerformed(final ActionEvent arg0){
			tick();
		} 
	};
	public final static Timer ticker = new Timer(25, timerAction);
	
	public static void tick(){
		if(Interface.errorDisappearTime <= System.currentTimeMillis()){
			Interface.errorText = "";
		}
		if(Interface.paused == false){
			Main.city.time = Main.city.time + 25;
			Interface.powerLineState = Interface.powerLineState + 25;
		}
		if(Interface.inPauseMenu == false){
			Paint.endStationUpdateTime = Paint.endStationUpdateTime + 25;
		}
		if(Interface.endTCWBlinking < System.currentTimeMillis()){
			Interface.TCWframeBlack = true;
		} else {
			Interface.TCWframeBlack = (Interface.TCWframeBlack == false);
		}
		Main.gui.repaint();
	}
}
