package Graphs;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;


public final class DrawKit  {
    private static double xmin, ymin, xmax, ymax;
    private static BufferedImage Image1;
    private static Graphics2D display;
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel draw; 
    public static JButton start;
    ActionListener startbtn;
   static
   {
	   init();
	   }

   
   
    private static void init() {
       
        frame = new JFrame();
        Image1 = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        display = Image1.createGraphics();       
        setX(0.0,1.0);
        setY(0.0,1.0);
        display.fillRect(0, 0, 512,512);
        setColor(Color.BLACK);
        setRadius(0.002);
        ImageIcon icon = new ImageIcon(Image1);
        draw = new JLabel(icon);
        panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        start=new JButton("Start");
        panel.add(draw);
        panel.add(start);
        frame.setContentPane(panel);      
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                        
        frame.setTitle("Standard Draw");        
        frame.pack();
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

  public static void setX(double min, double max) {
        double size = max - min;
        xmin = min - 0.05 * size;
        xmax = max + 0.05 * size;
    }
  public static void setY(double min, double max) {
        double size = max - min;
        ymin = min - 0.01 * size;
        ymax = max + 0.05 * size;
    }
 public static void setScale(double min, double max) {
        setX(min, max);
        setY(min, max);
    }
 
  private static double  scaleX(double x) { 
    	return 512  * (x - xmin) / (xmax - xmin); 
    	}
  
  private static double  scaleY(double y) { 
    	return 512 * (ymax - y) / (ymax - ymin); 
    	}
  
  private static double factorX(double w) { 
    	return w * 512  / Math.abs(xmax - xmin);  
    	}
  
   private static double factorY(double h) {
    	return h * 512 / Math.abs(ymax - ymin);  
    	}
    
    public static void setRadius(double r) {
        if (r < 0) throw new RuntimeException("pen radius must be positive");
        BasicStroke stroke = new BasicStroke((float) (r*512), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        display.setStroke(stroke);
    }
   public static void setColor(Color color) {
       
        display.setColor(color);
    }
   public static void drawLine(double x0, double y0, double x1, double y1) {
        display.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
        draw();
    }

    public static void drawCircle(double x, double y, double r) {
        if (r < 0) throw new RuntimeException("circle radius can't be negative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2*r);
        double hs = factorY(2*r);
         display.fill(new Ellipse2D.Double(xs - ws/2, ys - hs/2, ws, hs));
        draw();
    }
  public static void show(int t) {
        
        draw();
        try { Thread.currentThread();
		Thread.sleep(t); }
        catch (InterruptedException e) { System.out.println("Error sleeping"); }
       
    }

    public static void show() {
      
        draw();
    }
    private static void draw() {
      
        display.drawImage(Image1, 0, 0, null);
        frame.repaint();
    }

}
