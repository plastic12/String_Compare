package main;

import javafx.scene.paint.Color;

public class Input 
{
	String line;
	InputColor color;
	public Input(String line)
	{
		this.line=line;
		color=InputColor.UNCHECKED;
	}
	public String getLine()
	{
		return line;
	}
	public String getcolor() 
	{
		switch(color)
		{
		case UNCHECKED:
			return "white";
		case MATCH:
			return "green";
		case UNMATCH:
			return "red";
		default:
			throw new NullPointerException();
		}
	}
	public void setChecek(InputColor color)
	{
		this.color=color;
	}
	public static enum InputColor
	{
		UNCHECKED,MATCH,UNMATCH;
	}
}
