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
	int lastViewPosition;
	int lastViewLenght;
	
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
		//code up to this is final
		
		int viewPosition;
		int counter;
		if(this.text == ""){
			System.out.println("Option-1");
			viewPosition = 0;
		}else if(this.cursorPosition == this.text.length()){
			System.out.println("Option0");
			counter = this.cursorPosition - 1;
			while(counter >= 0 && Functions.getStringBounds(graph2, this.text.toCharArray()[counter] + textToPaint, 0, 0).width < this.size.width - borderSize * 2){
				textToPaint = this.text.toCharArray()[counter] + textToPaint;
				counter--;
			}
			System.out.println(Functions.getStringBounds(graph2, textToPaint, 0, 0).width);
			System.out.println(this.size.width - borderSize * 2);
			viewPosition = counter + 1;
		}else if(this.lastViewPosition <= this.cursorPosition && this.cursorPosition <= this.lastViewLenght + this.lastViewPosition){
			System.out.println("LastViewPosition:" + this.lastViewPosition);
			System.out.println("LastViewLength:" + this.lastViewLenght);
			System.out.println("Cursor:" + this.cursorPosition);
			if(this.cursorPosition != 0){
				counter = this.cursorPosition - 1;
				while(counter >= this.lastViewPosition){
					if(Functions.getStringBounds(graph2, this.text.toCharArray()[counter] + textToPaint, 0, 0).width < this.size.width - borderSize * 2){
					textToPaint = this.text.toCharArray()[counter] + textToPaint;
					counter--;
					}else{break;}
				}
				viewPosition = counter + 1;
			}else{viewPosition = 0;}
			System.out.println("FirstPart:" + textToPaint);
			counter = this.cursorPosition;
			while(counter < this.lastViewLenght + this.lastViewPosition && text.length() > counter){
				System.out.println("Counter:" + counter);
				if(Functions.getStringBounds(graph2, textToPaint + this.text.toCharArray()[counter], 0, 0).width < this.size.width - borderSize * 2){
					textToPaint = textToPaint + this.text.toCharArray()[counter];
					counter++;
				}else{break;}
			}
			System.out.println("SecondPart:" + textToPaint);
			counter = viewPosition - 1;
			while(counter >= 0 && Functions.getStringBounds(graph2, this.text.toCharArray()[counter] + textToPaint, 0, 0).width < this.size.width - borderSize * 2){
				textToPaint = this.text.toCharArray()[counter] + textToPaint;
				counter--;
			}
			viewPosition = counter + 1;
		}else if(this.cursorPosition < this.lastViewPosition){
			counter = this.cursorPosition;
			System.out.println("Option2" + "Lenght: " + this.text.length());
			System.out.println("Counter:" + counter);
			while(counter < this.text.length() &&  Functions.getStringBounds(graph2, textToPaint + this.text.toCharArray()[counter], 0, 0).width < this.size.width - borderSize * 2){
				textToPaint = textToPaint + this.text.toCharArray()[counter];
				System.out.println("Op2Counter:" + counter);
				counter++;
			}
			viewPosition = this.cursorPosition;
		}else{
			System.out.println("Option3");
			counter = this.cursorPosition - 1;
			while(counter >= 0 && Functions.getStringBounds(graph2, this.text.toCharArray()[counter] + textToPaint, 0, 0).width < this.size.width - borderSize * 2){
				textToPaint = this.text.toCharArray()[counter] + textToPaint;
				counter--;
			}
			viewPosition = counter + 1;
		}
		this.lastViewPosition = viewPosition;
		this.lastViewLenght = textToPaint.length();
		
		//code under this is final
		graph2.setColor(Color.BLACK);
		final Rectangle finalStringSize = Functions.getStringBounds(graph2, this.text, 0, 0);
		graph2.drawString(textToPaint, this.position.x + borderSize, (this.position.y + this.size.height - borderSize) - finalStringSize.height - finalStringSize.y);
	}
	
	public void keyPressed(KeyEvent event){
		switch(event.getKeyCode()){
		case(37):
			if(this.cursorPosition != 0){
				this.cursorPosition--;
			}
			break;
		case(39):
			if(this.text.length() != this.cursorPosition){
				this.cursorPosition++;
			}
			break;
		case(8):
			if(this.cursorPosition > 0){
				this.cursorPosition--;
				this.text = this.text.substring(0, this.cursorPosition) + this.text.substring(this.cursorPosition + 1, this.text.length());
			}
			break;
		case(32):
			this.text = this.text.substring(0, this.cursorPosition) + " " + this.text.substring(this.cursorPosition, this.text.length());
			this.cursorPosition++;
			break;
		}
		if(event.getKeyCode() > 64 && event.getKeyCode() < 91){
			this.text = this.text.substring(0, this.cursorPosition) + event.getKeyChar() + this.text.substring(this.cursorPosition, this.text.length());
			this.cursorPosition++;
		}else if(event.getKeyCode() > 47 && event.getKeyCode() < 66){
			System.out.println("number");
			this.text = this.text.substring(0, this.cursorPosition) + event.getKeyChar() + this.text.substring(this.cursorPosition, this.text.length());
			this.cursorPosition++;
		}
	}
}
