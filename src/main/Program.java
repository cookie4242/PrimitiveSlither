package main;

import java.net.MalformedURLException;

public class Program
{
    public static void main(String[] args) throws InterruptedException, MalformedURLException 
    {
    	PrimitiveSlither game = new PrimitiveSlither();
    	Canvas.initialize(game);
    	game.run();
	}
}