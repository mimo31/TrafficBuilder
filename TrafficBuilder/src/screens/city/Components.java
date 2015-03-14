package screens.city;

import java.awt.Point;
import java.awt.Rectangle;

import mainPackage.Functions;
import mainPackage.Variables;

public class Components {

	static boolean makingLine;
	static boolean inPauseMenu;
	static boolean inViewSettings;
	static boolean showTCW;
	static Point TCWposition;
	static int TCWwidth;
	
	static Rectangle controlPanel;
	static Rectangle controlPanelInside;
	static Rectangle pauseButton;
	static Rectangle pauseStrip1;
	static Rectangle pauseStrip2;
	static Rectangle moneyBounds;
	static Rectangle errorTextBounds;
	static Rectangle cancelButton;
	static Rectangle cancelButtonText;
	static Rectangle createButton;
	static Rectangle createButtonText;
	static Rectangle makingLineCovering;
	static Rectangle CCFTButton;
	static Rectangle CCFTButtonText;
	static Rectangle researchLabButton;
	static Rectangle researchLabButtonText1;
	static Rectangle researchLabButtonText2;
	static Rectangle viewSettingsButton;
	static Rectangle viewSettingsButtonText;
	static Rectangle timeTextBounds;
	static Rectangle TCW;
	static Rectangle TCWInside;
	static Rectangle TCWClose;
	static Rectangle TCWCreateLine;
	static Rectangle TCWCreateLineText;
	static Rectangle TCWPopText;
	
	static Rectangle backToCity;
	static Rectangle enterSettings;
	static Rectangle goToTitle;
	
	static Rectangle VSLines;
	static Rectangle VSBack;
	
	public static void updateComponents(){
		if(inViewSettings){
			VSLines = new Rectangle(0, Variables.height / 6, Variables.width, Variables.height - Variables.height / 6);
			VSBack = new Rectangle(Variables.width / 200, Variables.height / 200, Variables.width / 16, Variables.height / 24);
		}
		else{
			if(Variables.height > 800){
				controlPanel = new Rectangle(0, 0, Variables.width, 140);
			}
			else{
				controlPanel = new Rectangle(0, 0, Variables.width, Variables.height / 5 - 20);
			}
			int controlPanelBorder = controlPanel.height / 10;
			controlPanelInside = Functions.addBorders(controlPanel, controlPanelBorder);
			
			int pauseButtonSize = controlPanelInside.height / 2;
			pauseButton = new Rectangle(controlPanelInside.x + controlPanelInside.width - pauseButtonSize, controlPanelInside.y + pauseButtonSize, pauseButtonSize, pauseButtonSize);
			
			pauseStrip1 = new Rectangle(pauseButton.x + pauseButton.width / 5, pauseButton.y + pauseButton.width / 5, pauseButton.width / 5, pauseButton.width / 5 * 3);
			pauseStrip2 = new Rectangle(pauseButton.x + pauseButton.width / 5 * 3, pauseButton.y + pauseButton.width / 5, pauseButton.width / 5, pauseButton.width / 5 * 3);
			
			moneyBounds = Functions.addBorders(new Rectangle(controlPanelBorder, controlPanelBorder, controlPanelInside.width / 2, controlPanelInside.height / 2), controlPanelInside.height / 16);
			
			errorTextBounds = new Rectangle(Variables.width / 16, Variables.height * 5 / 6, Variables.width - Variables.width / 8, Variables.height / 6);
			errorTextBounds = Functions.addBorders(errorTextBounds, errorTextBounds.height / 8);
			
			if(makingLine){
				cancelButton = new Rectangle(0, Variables.height - Variables.width / 32, Variables.width / 16, Variables.width / 32);
				cancelButtonText = Functions.addBorders(cancelButton, Variables.width / 128);
				createButton = new Rectangle(Variables.width - Variables.width / 16, Variables.height - Variables.width / 32, Variables.width / 16, Variables.width / 32);
				createButtonText = Functions.addBorders(createButton, Variables.width / 128);
				makingLineCovering = new Rectangle(0, controlPanel.height + 39, Variables.width, Variables.height - controlPanel.height + 39);
			}
			
			int controlButtonsSize = controlPanelInside.height * 3 / 4;
			CCFTButton = new Rectangle(Variables.width / 2 - controlButtonsSize, controlPanelBorder, controlButtonsSize, controlButtonsSize);
			CCFTButtonText = Functions.addBorders(CCFTButton, controlButtonsSize / 8);
			researchLabButton = new Rectangle(Variables.width / 2, controlPanelBorder, controlButtonsSize, controlButtonsSize);
			researchLabButtonText1 = Functions.addBorders(new Rectangle(Variables.width / 2, controlPanelBorder, controlButtonsSize, controlButtonsSize / 2), controlButtonsSize / 16);
			researchLabButtonText2 = Functions.addBorders(new Rectangle(Variables.width / 2, controlPanelBorder + controlButtonsSize / 2, controlButtonsSize, controlButtonsSize / 2), controlButtonsSize / 16);
			viewSettingsButton = new Rectangle(Variables.width / 2 - controlButtonsSize, controlPanelBorder + controlButtonsSize, controlButtonsSize * 2, controlPanelInside.height / 4);
			viewSettingsButtonText = Functions.addBorders(viewSettingsButton, viewSettingsButton.height / 8);
			
			timeTextBounds = new Rectangle(controlPanelBorder + controlPanelInside.width * 2 / 3, controlPanelBorder, controlPanelInside.width / 3, controlPanelInside.height / 3);
			timeTextBounds = Functions.addBorders(timeTextBounds, 3);
			
			if(showTCW){
				int TCWBorderSize = TCWwidth / 16;
				TCW = new Rectangle(TCWposition.x, TCWposition.y + controlPanel.height, TCWwidth, TCWwidth * 2);
				TCWInside = Functions.addBorders(TCW, TCWBorderSize);
				TCWClose = new Rectangle(TCW.x + TCWwidth - 3 * TCWBorderSize, TCW.y + TCWBorderSize, TCWBorderSize * 2, TCWBorderSize * 2);
				TCWCreateLine = new Rectangle(TCWInside.x, TCW.y + TCW.height - 3 * TCWBorderSize, TCWInside.width, TCWwidth / 8);
				TCWCreateLineText = Functions.addBorders(TCWCreateLine, TCWwidth / 64);
				TCWPopText = new Rectangle(TCWInside.x, TCW.y + TCWBorderSize * 4, TCWInside.width, TCWBorderSize * 2);
				TCWPopText = Functions.addBorders(TCWPopText, 2);
			}
			
			if(inPauseMenu){
				backToCity = new Rectangle(Variables.width / 4, Variables.height / 4, Variables.width / 2, Variables.height / 16);
				enterSettings = new Rectangle(Variables.width / 4, Variables.height / 32 * 11, Variables.width / 2, Variables.height / 16);
				goToTitle = new Rectangle(Variables.width / 4, Variables.height / 16 * 7, Variables.width / 2, Variables.height / 16);
			}
		}
	}
}
