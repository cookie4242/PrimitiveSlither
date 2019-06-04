package main;

import graphics2D.Color;
import graphics2D.Ellipse;

public class Egg extends Ellipse
{
	Color color;

	public Egg(double x, double y, double width, double height) 
	{
		super(x, y, width, height);
	}

	@Override
	public void setColor(Color color)
	{
		super.setColor(color);
		this.color = color;
	}
}
