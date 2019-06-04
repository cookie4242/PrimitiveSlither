package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import graphics2D.Canvas;
import graphics2D.Color;
import graphics2D.Text;

public class PrimitiveSlither implements KeyListener, MouseListener, MouseMotionListener
{
	private int canvasWidth;
	private int canvasHeight;
	private int score = 0;
	private int numOfEggs = 7;
	private int time = 9000;
	private Egg ball;
	private Random randomGenerator = new Random();
	private ArrayList<Egg> eggs = new  ArrayList<Egg>();
	
	public PrimitiveSlither(int width, int height)
	{
		this.canvasWidth = width;
		this.canvasHeight = height;
	}
	
	public void run() throws InterruptedException, MalformedURLException
	{
		ball = new Egg(50, 50, 100, 100);
		ball.setColor(Color.RED);
		ball.fill();
		Canvas.getInstance().add(ball);
		
		for (int i = 0; i < numOfEggs; i++)
			newEgg();
		
		Text timeleft = new Text(0 , 20 , "time left =" + time);
		Canvas.getInstance().add(timeleft);
		Text eggseaten = new Text(0, -5, "score =" + score);
		Canvas.getInstance().add(eggseaten);
		
		for (int i = 0;time > i; time--) 
		{
			timeleft.setText("time left =" + time);
			
			if (time > 0)
			{
				Thread.sleep(10);
			}
			
			for	(int i1 = 0; i1 < eggs.size(); i1++)
			{
				if (playerOnTarget(i1))
				{
					score += getScoreForColor(i1);
					
					eggseaten.setText("score =" + score);
					
					eggs.get(i1).grow(-100, -100);
					eggs.remove(i1);
					newEgg();
				}
			}
			
			Canvas.getInstance().repaint();
		}
	}

	private int getScoreForColor(int i1) 
	{
		if (eggs.get(i1).color == Color.BLUE)
		{
			return 10;
		}
		else if (eggs.get(i1).color == Color.GREEN)
		{
			return 25;
		}
		else if (eggs.get(i1).color == Color.ORANGE)
		{
			return 50;
		}
		else if (eggs.get(i1).color == Color.RED)
		{
			return 100;
		}
		else if (eggs.get(i1).color == Color.BLACK)
		{
			return 250;
		}
		
		return 0;
	}

	private boolean playerOnTarget(int i1) 
	{
		return ball.getX() == eggs.get(i1).getX() && ball.getY() == eggs.get(i1).getY();
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{		
		String pressedKey = KeyEvent.getKeyText(e.getKeyCode());
		System.out.println("keyPressed="+pressedKey);
		if (pressedKey.equals("S"))
		{
			ball.setY(ball.getY() + 5);
		}
		else if(pressedKey.equals("W"))
		{
			ball.setY(ball.getY() - 5);
		}
		else if(pressedKey.equals("A"))
		{
			ball.setX(ball.getX() - 5);
		}
		else if (pressedKey.equals("D"))
		{
			ball.setX(ball.getX() + 5);
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		
	}
	
	public void newEgg() 
	{
		int eggColor = randomGenerator.nextInt(5);
		int spawnX = randomGenerator.nextInt(180);
		int spawnY = randomGenerator.nextInt(100);
		Egg egg = new Egg(spawnX * 10, spawnY * 10, 100, 100);
		
		if (eggColor == 1)
		{
			egg.setColor(Color.BLUE);
		}
		else if(eggColor == 2)
		{
			egg.setColor(Color.GREEN);
		}
		else if (eggColor == 3)
		{
			egg.setColor(Color.ORANGE);
		}
		else if (eggColor == 4)
		{
			egg.setColor(Color.RED);
		}
		else
		{
			egg.setColor(Color.BLACK);
		}
		
		Canvas.getInstance().add(egg);
		eggs.add(egg);
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}
}
