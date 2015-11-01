package game.tankwar;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
public class Tank 
{


	public static final int XPREED = 6; // X轴方向的移动速度
	public static final int YPREED = 6; // Y轴方向的移动速度

	public static final int WIDTH = 28; // 坦克宽度
	public static final int HIGH = 28; //
	
	private boolean bL = false, bU = false, bR = false, bD = false;// 是否按下上下左右4各键
	private Direction dir = Direction.STOP;// 当前方向
	private Direction ptDir = Direction.U;// 炮筒方向
	
	private int x, y; // 坦克位置坐标
	private int oldX,oldY;
	
	private int life = 5; //我方坦克人头
	//private BloodBar bb = new BloodBar();
	public int getLife()
	{
		return life;
	}
    
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	//我方坦克图片
	private static Image[] Mimages ;
	private static Map<String,Image> mimg = new HashMap<String,Image>();
	
	//敌方坦克图片
	private static Image[] Eimages ;
	private static Map<String,Image> eimg = new HashMap<String,Image>();
	
	//用于读取当前坦克图片
	private static Map<String,Image> img ;
	static 
	{
		Mimages = new Image[]
		{
				tk.getImage(Explode.class.getClassLoader().getResource("images/myTank_l.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/myTank_lu.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/myTank_u.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/myTank_ru.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/myTank_r.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/myTank_rd.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/myTank_d.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/myTank_ld.png"))
		};
		Eimages = new Image[]
			{
				tk.getImage(Explode.class.getClassLoader().getResource("images/light_tank_l.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/light_tank_lu.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/light_tank_u.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/light_tank_ru.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/light_tank_r.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/light_tank_rd.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/light_tank_d.png")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/light_tank_ld.png"))
			};
		mimg.put("L", Mimages[0]);
		mimg.put("LU", Mimages[1]);
		mimg.put("U", Mimages[2]);
		mimg.put("RU", Mimages[3]);
		mimg.put("R", Mimages[4]);
		mimg.put("RD", Mimages[5]);
		mimg.put("D", Mimages[6]);
		mimg.put("LD", Mimages[7]);
		
		eimg.put("L", Eimages[0]);
		eimg.put("LU", Eimages[1]);
		eimg.put("U", Eimages[2]);
		eimg.put("RU", Eimages[3]);
		eimg.put("R", Eimages[4]);
		eimg.put("RD", Eimages[5]);
		eimg.put("D", Eimages[6]);
		eimg.put("LD", Eimages[7]);
	}
	public void setLife(int life) {
		this.life = life;
	}

	TankClient tc;

	private Random r = new Random(); // 产生随机数
	private int step = r.nextInt(12) + 5; // 地方坦克朝一个方向移动的步数

	private boolean good; // 区分敌我

	public boolean isGood() {
		return good;
	}

	private boolean live = true; // 是否活着

	public boolean isLive()
	{
		return live;
	}

	public void setLive(boolean live) 
	{
		this.live = live;
	}



	public Tank(int x, int y, boolean good) 
	{
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}

	public Tank(int x, int y, boolean good, TankClient t)
	{
		this(x, y, good);
		this.tc = t;
	}

	void move() 
	{   
		oldX = x;
		oldY = y;
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
				y -= XPREED;
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
				y += YPREED;
				break;
			case LD:
				x -= XPREED;
				y += YPREED;
				break;

		}
		if (dir != Direction.STOP) ptDir = dir;
		// 避免坦克越界
		if (x < 0) x = 0;
		if (y < 20) y = 20;
		if (x + Tank.WIDTH + 30 >= TankClient.GAME_WIDETH-80)
			x = TankClient.GAME_WIDETH - Tank.WIDTH - 30-80;
		if (y + Tank.HIGH + 30 >= TankClient.GAME_HIGH)
			y = TankClient.GAME_HIGH - Tank.HIGH - 30;

		// 敌方坦克改变方向
		if (!good) 
		{
			Direction[] dirs = Direction.values();
			if (step == 0)
			{
				step = r.nextInt(16) + 6;
				int rn = r.nextInt(dirs.length);
				this.dir = dirs[rn];
			}
			step--;
			if(r.nextInt(34)>30) this.fire();

		}
	}

	public void draw(Graphics g) // 画坦克
	{
		if (!live) 
		{
			Explode ex = new Explode(x, y, tc);
			tc.explodes.add(ex);
			if(!good &&tc.waitT>0)
			{
			  if(tc.waitT%2==0) tc.t.add(new Tank(32+32*5*(tc.waitT%4)+1,32,false, tc));
			  else tc.t.add(new Tank(32,32+32*5*(tc.waitT%4)+1,false, tc));
			   tc.waitT--;
			}
		    tc.t.remove(this); // 移除已经死去的坦克
			return;
		}
//		Color c = g.getColor();
//		if (this.good)
//			g.setColor(Color.RED);
//		else
//			g.setColor(Color.blue);
//		g.fillOval(x, y, WIDTH, HIGH);
//		g.setColor(c);
        
		if(good)
		{ 
		   //bb.draw(g); //我方坦克 画血条
		   img = mimg;
		}
		else img = eimg;
		switch (ptDir) 
		{
			case L:
				g.drawImage(img.get("L"), x,y,WIDTH,HIGH,null);
				break;
			case LU:
				g.drawImage(img.get("LU"), x,y,WIDTH,HIGH,null);
				break;
			case U:
				g.drawImage(img.get("U"), x,y,WIDTH,HIGH,null);
				break;
			case RU:
				g.drawImage(img.get("RU"), x,y,WIDTH,HIGH,null);
				break;
			case R:
				g.drawImage(img.get("R"), x,y,WIDTH,HIGH,null);
				break;
			case RD:
				g.drawImage(img.get("RD"), x,y,WIDTH,HIGH,null);
				break;
			case D:
				g.drawImage(img.get("D"), x,y,WIDTH,HIGH,null);
				break;
			case LD:
				g.drawImage(img.get("LD"), x,y,WIDTH,HIGH,null);
				break;

		}

		move();
	}

	public void keyReleased(KeyEvent e) // 键盘松开事件
	{
		int key = e.getKeyCode();
		switch (key)
		{

			case KeyEvent.VK_CONTROL: // ctrl 键发射子弹 避免一直出子弹 放开ctrl出子弹
				fire();
				break;
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
		}
		locationDirection();
	}

	public void keyPressed(KeyEvent e) // 控制坦克
	{
		int key = e.getKeyCode();
		switch (key) 
		{

			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;
			case KeyEvent.VK_A:
				superFire();
				break;
			case KeyEvent.VK_F2:
			    if(!live||!tc.eagle.isLive()||tc.map.flag==4)
			    {
			    	tc.Init();
			    	
			    }
			    break;
		}
		locationDirection();
	}

	void locationDirection() // 通过按下哪两个键判断移动方向

	{

		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	public void fire() // 生成子弹
	{   
		if(!live) return;
		int xx = x + this.WIDTH / 2 - Missile.WIDTH / 2;
		int yy = y + this.HIGH / 2 - Missile.HIGH / 2;
		Missile mis = new Missile(xx, yy, ptDir, this.good,tc);
		tc.missiles.add(mis);
	}
	
	public void fire(Direction d) // 生成方向d的子弹
	{   
		if(!live) return;
		int xx = x + this.WIDTH / 2 - Missile.WIDTH / 2;
		int yy = y + this.HIGH / 2 - Missile.HIGH / 2;
		Missile mis = new Missile(xx, yy, d, this.good,tc);
		tc.missiles.add(mis);
	}

	public Rectangle getRec() // 返回辅助类Rectangle 判断是否碰撞
	{

		return new Rectangle(x, y, WIDTH, HIGH);

	}
	public boolean collideWithWall(Wall w)
	{
		if(this.live && this.getRec().intersects(w.getRec()))
		{
			stay();
			return true;
		}
		return false;
	}
	
	public boolean collideWithEagle(Eagle e)
	{  
		if(live &&this.getRec().intersects(e.getRec()))
		{
			stay();
			return true;
		}
		return false;
	}
	public boolean collideWithWalls(List<Wall> w)
	{
		for (int i = 0; i < w.size(); i++)
		{
			if (collideWithWall(w.get(i)))
				return true;
		}
		return false;
	}
	public void stay()
	{
		x = oldX;
		y =oldY;
	}
	public boolean collideWithTank(List<Tank> tanks)
	{   
		
		for(int i =0;i<tanks.size();i++)
		{
			Tank t = tanks.get(i);
			if(this!=t)
			{
				if( this.live && t.isLive()&&this.getRec().intersects(t.getRec()))
				{
					stay();
					return true;
				}
			}
		}
		if(!good) //敌方坦克不能穿越我方坦克
		{
			if( this.live && tc.mytank.isLive()&&this.getRec().intersects(tc.mytank.getRec()))
			{
				stay();
				return true;
			}
		}
		return false;
	}
	public void superFire()
	{
		Direction dirs[] = Direction.values();
		for(int i =0;i<8;i++)
		{
			fire(dirs[i]);
		}
	}
	public boolean eat(Blood b)
	{
		if(live &&b.isLive()&&this.getRec().intersects(b.getRect()))
		{
			life +=3;
			b.setLive(false);
			return true ;
		}
		return false;
	}
//	private class BloodBar
//	{
//		public void draw(Graphics g)
//		{
//		  Color c = g.getColor();
//		  g.setColor(Color.red);
//		  g.drawRect(x, y-10, WIDTH, 10);
//		  int w = WIDTH*life/100;
//		  g.fillRect(x, y-10, w, 10);
//		  g.setColor(c);
//		}
//	}
}
