package game.tankwar;
import java.awt.*;

import javax.swing.ImageIcon;



// 爆炸类 通过画很多个圆不停的重画模拟爆炸
public class Explode 
{
	int x, y;
	// boolean live = true;
	int index = 0;
    
	private boolean init = false; //图片是否被载入  避免第一次爆炸没有图片 
	TankClient tc;

	public static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private   Image[] images =
   {
		tk.getImage(Explode.class.getClassLoader().getResource("images/boom.png")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/boom02.png")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/boom03.png")),

   };

	public Explode(int x, int y, TankClient tc) 
	{
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g)
	{
		if(!init)   //将图片全部载入
		{   
			for(int i = 0;i<images.length;i++)
			g.drawImage(images[i], -100, -100, null);
			init = true;
		}
		if (index == images.length) // 已经画完 爆炸结束
		{
			tc.explodes.remove(this);
			return;
		}
//		Color c = g.getColor();
//		g.setColor(Color.WHITE);
//		g.fillOval(x, y, radius[index], radius[index]);
//		g.setColor(c);
		g.drawImage(images[index], x, y, null);
		index++;
	}

}
