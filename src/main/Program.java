package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.MalformedURLException;

import graphics2D.Canvas;

public class Program
{
    public static void main(String[] args) throws InterruptedException, MalformedURLException 
    {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int width = (int)(screenSize.getWidth() * 1);
    	int height = (int)(screenSize.getHeight() * 1);
    	PrimitiveSlither game = new PrimitiveSlither(width, height);
    	
    	Canvas.setKeyListener(game);
    	Canvas.setMouseListener(game);
    	Canvas.setMouseMotionListener(game);
    	Canvas.initialize(width, height);
    	Canvas.NormalizeOrigin = false;
    	
    	game.run();
	}
}