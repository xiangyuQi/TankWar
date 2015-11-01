package game.tankwar;
import java.awt.*;

import javax.swing.ImageIcon;



// ��ը�� ͨ�����ܶ��Բ��ͣ���ػ�ģ�ⱬը
public class Explode 
{
	int x, y;
	// boolean live = true;
	int index = 0;
    
	private boolean init = false; //ͼƬ�Ƿ�����  �����һ�α�ըû��ͼƬ 
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
		if(!init)   //��ͼƬȫ������
		{   
			for(int i = 0;i<images.length;i++)
			g.drawImage(images[i], -100, -100, null);
			init = true;
		}
		if (index == images.length) // �Ѿ����� ��ը����
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
