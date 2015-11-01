package game.tankwar;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class Wall
{
   private int x,y;
   public static final int W=16,H=16; //每个小墙的长度和宽度
   TankClient tc;
   private String wName;
   private boolean live = true;
   
   public static Toolkit tk = Toolkit.getDefaultToolkit();
   private static Image[] Wimg ;
   private static Map<String,Image> img = new HashMap<String,Image>();
	static
	{
		Wimg = new Image[]
		{
				tk.getImage(Explode.class.getClassLoader().getResource("images/lbrick.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/lake.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/steel.png"))
				
		};
		img.put("lbrick", Wimg[0]);
		img.put("lake", Wimg[1]);
		img.put("steel", Wimg[2]);
	}
	public Wall(String name ,int xx,int yy)
	{
		wName = name;
		x= xx;
		y = yy;
	}
	public boolean isLive()
	{
		return live;
	}
	public void setLive(boolean live)
	{
		this.live = live;
	}
	public String getName()
	{
		return wName;
	}
   
//   public Wall(String wName,int x,int y,int width,int high,TankClient tc)
//   {   
//	   this.wName = wName;
//	   this.x=x;
//	   this.y=y;
//	   this.width = width;
//	   this.high = high;
//	   this.tc = tc;
//   }
   public void draw(Graphics g)
   {
//	   Color c = g.getColor();
//	   g.setColor(Color.DARK_GRAY);
//	   g.fillRect(x, y, width, high);
//	   g.setColor(c);
//	   for(int i = 0;i<width/W;i++)
//	   {
//		   for(int j = 0;j<high/H;j++)
//		     g.drawImage(img.get(wName),x+W*i ,y+H*j ,null);
//	   }
       
	    g.drawImage(img.get(wName),x ,y ,null);
	   
   }
   public Rectangle getRec()
   {
	   return new Rectangle(x,y,W,H);
   }
}
