package game.tankwar;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class Eagle 
{
   private static final int WIDTH = 32;
   private static final int HIGH = 32;
   private int x , y;
   Toolkit tk = Toolkit.getDefaultToolkit();
   Image img = tk.getImage(Explode.class.getClassLoader().getResource("images/eagle.png"));
   private boolean live = true ;
   
   public Eagle(int x,int y)
   {
	   this.x = x;
	   this.y = y;
   }
	public boolean isLive() 
	{
		return live;
	}
	public void setLive(boolean live) 
	{
		this.live = live;
	}
	public void draw(Graphics g)
	{
		g.drawImage(img, x, y, null);
	}
	public Rectangle getRec() 
	{
	    return new Rectangle(x, y, WIDTH, HIGH);
	}
   
}
