package mainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Textbox {
	public Point position;
	public Dimension size;
	public boolean clicked;
	public boolean active;
	public String text;
	public int cursorPosition;
	
	public Textbox(){
		this.text = "";
	}
	
	public Textbox(Point position, Dimension size){
		this.position = position;
		this.size = size;
		this.text = "";
	}

	public void paint(Graphics2D graph2){
		graph2.setColor(Color.BLACK);
		graph2.fillRect(this.position.x, this.position.y, this.size.width, this.size.height);
		graph2.setColor(Color.WHITE);
		int borderSize = (this.size.width / 20 + this.size.height / 20) / 2;
		if(borderSize > this.size.height / 4){
			borderSize = this.size.height / 4;
		}
		if(borderSize > this.size.width / 4){
			borderSize = this.size.width / 4;
		}
		graph2.fillRect(this.position.x + borderSize, this.position.y + borderSize, this.size.width - borderSize * 2, this.size.height - borderSize * 2);
		graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, 100f));
		final double s1HeightPer1 = ((double) Functions.getStringBounds(graph2, this.text, 0, 0).height) / 100;
		graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, (float) ((this.size.height - borderSize * 2) / s1HeightPer1)));
		String textToPaint = "";
		int counter = this.text.length() - 1;
		while(counter >= 0 && Functions.getStringBounds(graph2, this.text.substring(counter, counter + 1) + textToPaint, 0, 0).width < this.size.width - borderSize * 2){
			textToPaint = this.text.substring(counter, counter + 1) + textToPaint;
			counter--;
		}
		graph2.setColor(Color.BLACK);
		final Rectangle finalStringSize = Functions.getStringBounds(graph2, this.text, 0, 0);
		graph2.drawString(textToPaint, this.position.x + borderSize, (this.position.y + this.size.height - borderSize) - finalStringSize.height - finalStringSize.y);
	}
	
	public void buttonPressed(KeyEvent event){
		if(event.getKeyCode() == KeyEvent.VK_LEFT){
			
		}
	}
}
