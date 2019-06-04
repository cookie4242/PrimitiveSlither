package graphics2D;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Canvas
{
	public static boolean NormalizeOrigin = false;
    private static Canvas canvas;
    private static KeyListener keyListener;
    private static MouseListener mouseListener;
    private static MouseMotionListener mouseMotionListener;

    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    private BufferedImage background;
    private JFrame frame;
    private static CanvasComponent component;
    private static final int LOCATION_OFFSET = 120;
    
    public double width;
    public double height;
    
    public static void initialize(double width, double height)
    {
    	canvas = new Canvas(width, height);
    }
    
    private Canvas(double width, double height)
    {
    	this.width = width;
    	this.height = height;
        component = new CanvasComponent();
        
        if (keyListener != null)
        {
        	component.addKeyListener(keyListener);
	    	component.setFocusable(true);
	    }
        
        if (mouseListener != null)
        {
        	component.addMouseListener(mouseListener);
        	component.setFocusable(true);
        }
        
        if (mouseMotionListener != null)
        {
        	component.addMouseMotionListener(mouseMotionListener);
        	component.setFocusable(true);
        }

        frame = new JFrame();
        if (!System.getProperty("java.class.path").contains("bluej"))
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(component);
        frame.pack();
        if (width + height == 0)
        {
        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        }
        else
        {
            frame.setSize((int)width, (int)height);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(LOCATION_OFFSET, LOCATION_OFFSET);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setTitle("Java Graphics");
        frame.setLocation(0, 0);

        Dimension drawableDimensions = frame.getContentPane().getSize();
        this.width = drawableDimensions.getWidth();
        this.height = drawableDimensions.getHeight();
    	System.out.println("Drawable Screen Size: Width=" + width + " Height=" + height);
    }
    
    public static void setKeyListener(KeyListener listener)
    {
    	keyListener = listener;
    }
    
    public static void setMouseListener(MouseListener listener)
    {
    	mouseListener = listener;
    }
    
    public static void setMouseMotionListener(MouseMotionListener listener)
    {
    	mouseMotionListener = listener;
    }

    public static Canvas getInstance()
    {
        return canvas;
    }
    
    public static JComponent getComponent()
    {
    	return component;
    }

    public void add(Shape s)
    {
        if (!shapes.contains(s))
            shapes.add(s);
    }
    
    public void remove(Shape s)
    {
        shapes.remove(s);
    }

    public void repaint()
    {
        if (frame == null)
        	return;
        
        frame.repaint();
    }
    
    @SuppressWarnings("serial")
	private class CanvasComponent extends JComponent
    {
        public void paintComponent(Graphics g)
        {
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(java.awt.Color.BLACK);
            if (background != null)
            {
                g.drawImage(background, 0, 0, null);
            }               
            for (Shape s : new ArrayList<Shape>(shapes))
            {
                Graphics2D g2 = (Graphics2D) g.create();
                s.paintShape(g2);
                g2.dispose();
            }
        }
    }
}
