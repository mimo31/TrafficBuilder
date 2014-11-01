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
		System.out.println(this.clicked);
		if(this.clicked){
			graph2.setColor(Color.WHITE);
		}
		else{
			graph2.setColor(Color.lightGray);
		}
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
		
		int viewPosition;
		int counter;
		if(this.text == ""){
			viewPosition = 0;
		}else if(this.cursorPosition == this.text.length()){
			counter = this.cursorPosition - 1;
			while(counter >= 0 && Functions.getStringBounds(graph2, this.text.toCharArray()[counter] + textToPaint, 0, 0).width < this.size.width - borderSize * 2){
				textToPaint = this.text.toCharArray()[counter] + textToPaint;
				counter--;
			}
			viewPosition = counter + 1;
		}else if(this.lastViewPosition <= this.cursorPosition && this.cursorPosition <= this.lastViewLenght + this.lastViewPosition){
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
			counter = this.cursorPosition;
			while(counter < this.lastViewLenght + this.lastViewPosition && this.text.length() > counter){
				if(Functions.getStringBounds(graph2, textToPaint + this.text.toCharArray()[counter], 0, 0).width < this.size.width - borderSize * 2){
					textToPaint = textToPaint + this.text.toCharArray()[counter];
					counter++;
				}else{break;}
			}
			counter = viewPosition - 1;
			while(counter >= 0 && Functions.getStringBounds(graph2, this.text.toCharArray()[counter] + textToPaint, 0, 0).width < this.size.width - borderSize * 2){
				textToPaint = this.text.toCharArray()[counter] + textToPaint;
				counter--;
			}
			viewPosition = counter + 1;
			counter = viewPosition + textToPaint.length();
			while(this.text.length() > counter){
				if(Functions.getStringBounds(graph2, textToPaint + this.text.toCharArray()[counter], 0, 0).width < this.size.width - borderSize * 2){
					textToPaint = textToPaint + this.text.toCharArray()[counter];
					counter++;
				}else{break;}
			}
		}else if(this.cursorPosition < this.lastViewPosition){
			counter = this.cursorPosition;
			while(counter < this.text.length() &&  Functions.getStringBounds(graph2, textToPaint + this.text.toCharArray()[counter], 0, 0).width < this.size.width - borderSize * 2){
				textToPaint = textToPaint + this.text.toCharArray()[counter];
				counter++;
			}
			viewPosition = this.cursorPosition;
		}else{
			counter = this.cursorPosition - 1;
			while(counter >= 0 && Functions.getStringBounds(graph2, this.text.toCharArray()[counter] + textToPaint, 0, 0).width < this.size.width - borderSize * 2){
				textToPaint = this.text.toCharArray()[counter] + textToPaint;
				counter--;
			}
			viewPosition = counter + 1;
		}
		this.lastViewPosition = viewPosition;
		this.lastViewLenght = textToPaint.length();
		
		graph2.setColor(Color.BLACK);
		final Rectangle finalStringSize = Functions.getStringBounds(graph2, this.text, 0, 0);
		graph2.drawString(textToPaint, this.position.x + borderSize, (this.position.y + this.size.height - borderSize) - finalStringSize.height - finalStringSize.y);
		if(this.clicked){
			graph2.setColor(Color.darkGray);
			if(System.currentTimeMillis() % 1000 < 500){
				int cursorX = this.size.width / 80 + this.position.x + borderSize + Functions.getStringBounds(graph2, this.text.substring(viewPosition, this.cursorPosition), 0, 0).width;
				if(cursorX > this.position.x + this.size.width - borderSize - 3){
					cursorX = this.position.x + this.size.width - borderSize - 3;
				}
				graph2.fillRect(cursorX, this.position.y + borderSize, 2, this.size.height - borderSize * 2);
			}
		}
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
		case(127):
			if(this.cursorPosition != this.text.length()){
				this.text = this.text.substring(0, this.cursorPosition) + this.text.substring(this.cursorPosition + 1, this.text.length());
			}
			break;
		default:
			if(event.getKeyCode() != 16 && event.getKeyCode() != 10 && event.getKeyCode() != 40 && event.getKeyCode() != 38 && event.getKeyCode() != 17){
				this.text = this.text.substring(0, this.cursorPosition) + event.getKeyChar() + this.text.substring(this.cursorPosition, this.text.length());
				this.cursorPosition++;
			}
		}
		
	}
}
