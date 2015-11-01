package game.tankwar;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile
{
	private int x, y;
	Direction dir;

	public static final int XPREED = 10;
	public static final int YPREED = 10;

	public static final int WIDTH = 10; // 子弹宽度
	public static final int HIGH = 10;

	private TankClient tc;
	
	private boolean good ;// 子弹是地方还是我方
	
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] mimgs ;
	private static Map<String,Image> img = new HashMap<String,Image>();
	
	static 
	{
		mimgs = new Image[]
		{
				tk.getImage(Explode.class.getClassLoader().getResource("images/bullet_l.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/bullet_lu.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/bullet_u.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/bullet_ru.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/bullet_r.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/bullet_rd.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/bullet_d.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/bullet_ld.png"))
		};
		img.put("L", mimgs[0]);
		img.put("LU", mimgs[1]);
		img.put("U", mimgs[2]);
		img.put("RU", mimgs[3]);
		img.put("R", mimgs[4]);
		img.put("RD", mimgs[5]);
		img.put("D", mimgs[6]);
		img.put("LD", mimgs[7]);
	}

	private boolean live = true; // 子弹是否存活

	public boolean isLive() 
	{
		return live;
	}

	public void setLive(boolean live) 
	{
		this.live = live;
	}

	public Missile(int x, int y, Direction dir)
	{
		this.x = x;
		this.y = y;
		this.dir = dir;

	}

	public Missile(int x, int y, Direction dir, boolean good ,TankClient tc)
	{
		this(x,y,dir);
		this.tc = tc;
		this.good = good;

	}

	public void draw(Graphics g) // 画子弹
	{
		if (!live) // 子弹死去则不画
		{
			tc.missiles.remove(this);
			return;
		}
		switch (dir) 
		{
			case L:
				g.drawImage(img.get("L"), x,y,null);
				break;
			case LU:
				g.drawImage(img.get("LU"), x,y,null);
				break;
			case U:
				g.drawImage(img.get("U"), x,y,null);
				break;
			case RU:
				g.drawImage(img.get("RU"), x,y,null);
				break;
			case R:
				g.drawImage(img.get("R"), x,y,null);
				break;
			case RD:
				g.drawImage(img.get("RD"), x,y,null);
				break;
			case D:
				g.drawImage(img.get("D"), x,y,null);
				break;
			case LD:
				g.drawImage(img.get("LD"), x,y,null);
				break;

		}

		move();
	}

	void move() 
	{
		switch (dir)
		{
			case L:
				x -= XPREED;
				break;
			case LU:
				x -= XPREED;
				y -= YPREED;
				break;
			case U:
				y -= YPREED;
				break;
			case RU:
				x += XPREED;
				y -= YPREED;
				break;
			case R:
				x += XPREED;
				break;
			case RD:
				x += XPREED;
				y += YPREED;
				break;
			case D:
				y += XPREED;
				break;
			case LD:
				x -= XPREED;
				y += YPREED;
				break;

		}
		if (x < 0 || y < 0 || x > TankClient.GAME_WIDETH-100|| y > TankClient.GAME_HIGH) 
		{
			this.setLive(false);
		}
	}

	public Rectangle getRec() 
	{
		return new Rectangle(x, y, WIDTH, HIGH);

	}
	
	boolean hitEagle(Eagle e)
	{
		if (this.live&&e.isLive() && this.getRec().intersects(e.getRec())) // 当前坦克活着 且 被碰撞
		{
			live = false;
			e.setLive(false);
			
			return true;
		}
		return false;
	}

	boolean hitTank(Tank t)
	{
		if (this.live&&t.isLive() && this.getRec().intersects(t.getRec())&&this.good != t.isGood()) // 当前坦克活着 且 被碰撞
		{
			if(t.isGood())
			{
				t.setLife(t.getLife()-1);
				if(t.getLife()<=0) t.setLive(false);
			}
			else t.setLive(false);
			this.live = false;
//			Explode ex = new Explode(x, y, tc);
//			tc.explodes.add(ex);
			return true;
		}
		return false;
	}

	boolean hitTanks(List<Tank> t) 
	{
		for (int i = 0; i < t.size(); i++)
		{
			if (hitTank(t.get(i)))
				return true;
		}
		return false;
	}
	boolean hitWall(Wall w)
	{
		if(live &&w.isLive()&&this.getRec().intersects(w.getRec()))
		{   
			if(w.getName()=="lbrick")
		    {
				w.setLive(false);
				live = false;
		    }
			if(w.getName()=="steel") live = false;
			return true;
		}
		return false;
	}
	boolean hitWalls(List<Wall> w) 
	{
		for (int i = 0; i < w.size(); i++)
		{
			if (hitWall(w.get(i)))
				return true;
		}
		return false;
	}

}
