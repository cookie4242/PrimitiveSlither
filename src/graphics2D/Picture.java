package graphics2D;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Picture implements Shape
{
	private BufferedImage image;
	private JLabel label = new JLabel();    
	private String source;
	private double x;
	private double y;
	private double xGrow;
	private double yGrow;

   public Picture(double width, double height)
   {
      image = new BufferedImage((int) Math.round(width), 
         (int) Math.round(height), BufferedImage.TYPE_INT_RGB);
      label.setIcon(new ImageIcon(image));
      label.setText("");      
   }

    public Picture(String source)
    {
        load(source);
    }

    public void load(String source)
    {
        try
        {
            this.source = source;
            if (source.startsWith("http://"))
                image = ImageIO.read(new URL(source).openStream());
            else
                image = ImageIO.read(new File(source));

            label.setIcon(new ImageIcon(image));
            label.setText("");
        }
        catch (Exception ex)
        {
            image = null;
            label.setIcon(null);
            ex.printStackTrace();
        }
    }
    
    public int getX()
    {
       return (int) Math.round(x - xGrow);
    }

    public int getY()
    {
       return (int) Math.round(y - yGrow);
    }

    public int getMaxX()
    {
       return getX() + getWidth();
    }

    public int getMaxY()
    {
       return getY() + getHeight();
    }

    public int getWidth()
    {
       return (int) Math.round(
          (image == null ? 0 : image.getWidth()) + 2 * xGrow);
    }

    public int getHeight()
    {
       return (int) Math.round(
          (image == null ? 0 : image.getHeight()) + 2 * yGrow);
    }

    public int pixels()
    {
        if (image == null)
        {
            return 0;
        }
        else
        {
            return image.getWidth() * image.getHeight();
        }
    }

    public int[][] getGrayLevels()
    {
        if (image == null) return new int[0][0];
        int[][] grayLevels = new int[getHeight()][getWidth()];
      
        for (int i = 0; i < grayLevels.length; i++)
            for (int j = 0; j < grayLevels[i].length; j++)
            {
                int rgb = image.getRGB(j, i);
                // Use NTSC/PAL algorithm to convert RGB to gray level
                grayLevels[i][j] = (int)(0.2989 * ((rgb >> 16) & 0xFF) + 0.5866 * ((rgb >> 8) & 0xFF) + 0.1144 * (rgb & 0xFF));	       
            }
        return grayLevels;
    }

    public Picture(int[][] grayLevels)
    {
        image = new BufferedImage(grayLevels[0].length, grayLevels.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getWidth(); i++)
            for (int j = 0; j < image.getHeight(); j++)
            {
                int gray = grayLevels[j][i];
                if (gray < 0) gray = 0;
                if (gray > 255) gray = 255;
                int rgb = gray * (65536 + 256 + 1);
                image.setRGB(i, j, rgb);
            }
        label.setIcon(new ImageIcon(image));
        label.setText("");      
    }

    public String toString()
    {
       return "Picture[x=" + getX() + ",y=" + getY() + ",width=" + getWidth() + ",height=" + getHeight() + ",source=" + source + "]";
    }

    public Color getColorAt(int i)
    {
        if (image == null || i < 0 || i >= pixels())
        {
           throw new IndexOutOfBoundsException("" + i);
        }
        else
        {
            return getColorAt(i % image.getWidth(), i / image.getWidth());
        }
    }

    public void setColorAt(int i, Color color)
    {
        if (image == null || i < 0 || i >= pixels())
        {
           throw new IndexOutOfBoundsException("" + i);
        }
        else
        {
            setColorAt(i % image.getWidth(), i / image.getWidth(), color);
        }
    }

    public Color getColorAt(int x, int y)
    {
        if (image == null || x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight())
        {
           throw new IndexOutOfBoundsException("(" + x + "," + y + ")");
        }
        else
        {
            int rgb = image.getRGB(x, y) & 0xFFFFFF;
            return new Color(rgb / 65536, (rgb / 256) % 256, rgb % 256);
        }
    }

    public void setColorAt(int x, int y, Color color)
    {
        if (image == null || x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight())
        {
           throw new IndexOutOfBoundsException("(" + x + "," + y + ")");
        }
        else
        {
            image.setRGB(x, y, ((int) color.getRed()) * 65536 + ((int) color.getGreen()) * 256 + (int) color.getBlue());
        }
    }

    public void translate(double dx, double dy)
    {
        x += dx;
        y += dy;
    }

    public void grow(double dw, double dh)
    {
        xGrow += dw;
        yGrow += dh;
    }

    public void paintShape(Graphics2D g2)
    {
        if (image != null)
        {
            Dimension dim = label.getPreferredSize();
            if (dim.width > 0 && dim.height > 0)
            {
                label.setBounds(0, 0, dim.width, dim.height);
                g2.translate(getX(), getY());
                g2.scale((image.getWidth() + 2 * xGrow) / dim.width, 
                    (image.getHeight() + 2 * yGrow) / dim.height);
                label.paint(g2);
            }
        }
    }
}
