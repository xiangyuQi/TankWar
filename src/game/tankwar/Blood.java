package game.tankwar;

import java.awt.*;

public class Blood 
{
	private static final int W = 32;
	private static final int H = 32;
	int x, y;
	TankClient tc;
	boolean live = true;
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	int step = 0;
//    int [][] pos = 
//    	{{10,10},{64,64},{32,10},{100,32},{32,32},{64,10},{16,64},{128,36},{64,128},{128,128},{128,64}};
	 int [][] pos = 
    	{{10,400},{15,390},{20,380},{25,360},{30,340},{40,320,},{50,300},{60,270},{70,250},{80,270},{100,200},
			 {120,180},{140,150,},{160,130},{200,100},{230,80},{250,70},{300,50},{350,25},{400,10},{400,30},
			 {370,50},{350,80},{400,100},{420,150},{430,170},{450,200},
    	{470,210},{500,250},{500,280},{550,290},{600,300},{550,350},{500,400},{450,450},{400,500},{380,490},{370,480},
    	{360,460},{340,460},{300,400},{270,500},{80,500},{50,550},{30,420},{10,400}};
    
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	Image img = tk.getImage(Explode.class.getClassLoader().getResource("images/blinkExp.png"));
	
	public Blood(int x,int y,TankClient tc)
	{
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	public void draw(Graphics g)
	{   

		if(!live) return ;
		g.drawImage(img, x, y, null);
		move();
	}
	private void move()
	{
		step ++;
		if(step == pos.length)
		{
			step =0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
	public Rectangle getRect()
	{
		return new Rectangle(x,y,W,H);
	}
	
}
