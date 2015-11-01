package game.tankwar;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

public class TankClient extends JFrame {
	public static final int GAME_WIDETH = 800;
	public static final int GAME_HIGH = 600;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image imgover = tk.getImage(Explode.class.getClassLoader().getResource("images/GameOver.jpg"));
	Image imgwin = tk.getImage(Explode.class.getClassLoader().getResource("images/win.png"));
	Eagle eagle;
	Tank mytank;
	
	List<Missile> missiles ;// ���ӵ�
	List<Explode> explodes ;
	List<Tank> t ;//�з�̹��
	int  waitT = 15; //�ȴ���̹������
	
	Blood blood ;
    
	Map map ;
	
	public TankClient()
	{
		Init();
	}
	public void lanchFrame() {

		JPanel mypanel = new Mypanle(this); // ��JPanle���ػ�

		//mypanel.setDoubleBuffered(true);
		mypanel.setBackground(Color.BLACK); // �������ɫ

		this.add(mypanel);

		this.setLocation(200, 100);
		this.setTitle("TankWar");
		this.setSize(GAME_WIDETH, GAME_HIGH);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();

	}
   
	public void Init()  //��ʼ��
	{
		missiles = new ArrayList<Missile>();
		explodes = new ArrayList<Explode>();
		t = new ArrayList<Tank>();
		blood = new Blood(10,10,this);
		map = new Map(this);
	}
	
	public static void main(String[] args) {
		TankClient tank = new TankClient();
		tank.lanchFrame();
	}

	public class PaintThread implements Runnable 
	{

		public void run() 
		{
			while (true) 
			{
				repaint();
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	class Mypanle extends JPanel //
	{
		// private static final long serialVersionUID = 1147736728548400926L;
		TankClient tc;
		JLabel hpIcon ;
		JLabel hptext;
		JLabel text;
		JLabel ehpIcon ;
		JLabel ehptext;
		JLabel roundflag;
		JLabel roundtext ;
		
		Font font = new Font("Dialog",1,20);
		void InitPanle()
		{
			
		    text = new JLabel();
			text.setBounds(new Rectangle(300,380,250,32));	
			text.setOpaque(false);
			text.setForeground(Color.RED);
			text.setFont(font);
			text.setText("��F2���¿�ʼ");
			this.add(text);
			
			//��ʾHP
		    hptext = new JLabel();
			hptext.setBounds(new Rectangle(700,300,50,32));	
			hptext.setOpaque(false);
			hptext.setForeground(Color.RED);
			hptext.setFont(font);
			hptext.setText("HP");
			
			hpIcon = new JLabel();
			hpIcon.setBounds(new Rectangle(700,350,50,32));	
			hpIcon.setOpaque(false);
			hpIcon.setForeground(Color.RED);
			hpIcon.setFont(font);
			hpIcon.setText(String.valueOf(mytank.getLife()));
			ImageIcon selfIcon = new ImageIcon(TankClient.class.getClassLoader().getResource("images/self_icon.png"));
			hpIcon.setIcon(selfIcon);
			
			//��ʾ�з�̹������
			ehptext = new JLabel();
			ehptext.setBounds(new Rectangle(700,100,100,32));	
			ehptext.setOpaque(false);
			ehptext.setForeground(Color.RED);
			ehptext.setFont(font);
			ehptext.setText("�з�̹��");
			
			ehpIcon = new JLabel();
			ehpIcon.setBounds(new Rectangle(700,150,50,32));	
			ehpIcon.setOpaque(false);
			ehpIcon.setForeground(Color.RED);
			ehpIcon.setFont(font);
			ehpIcon.setText(String.valueOf(t.size()));
			ImageIcon eselfIcon = 
					new ImageIcon(TankClient.class.getClassLoader().getResource("images/mini-tank.png"));
			ehpIcon.setIcon(eselfIcon);
			
			//����
			roundflag = new JLabel();
			roundflag.setBounds(new Rectangle(700,450,50,40));		
			ImageIcon icon = new ImageIcon(TankClient.class.getClassLoader().getResource("images/flag.png"));
			roundflag.setIcon(icon);
			this.add(roundflag);
			
		    roundtext = new JLabel();
			roundtext.setBounds(new Rectangle(750,450,50,50));	
			roundtext.setOpaque(false);
			roundtext.setForeground(Color.RED);
			font = new Font("Dialog",1,20);
			roundtext.setFont(font);
			roundtext.setText(String.valueOf(map.flag));
			
			this.add(roundtext);
			this.add(hpIcon);
			this.add(hptext);
			this.add(ehpIcon);
			this.add(ehptext);
			
		}
		public Mypanle(TankClient tc)
		{
			this.tc = tc;
	        this.setLayout(null);
			InitPanle();
		}
		public void paintComponent(Graphics g) // ��д
		{
			super.paintComponent(g);
			text.setVisible(false);
			 //map.draw(g);
//			g.drawString("missiles's counts:" + missiles.size(), 10, 50);// �ڵ�����
//			g.drawString("Explodes's counts:" + explodes.size(), 10, 70);// ��ը����
//			g.drawString("Tanks's counts:" + t.size(), 10, 90);// ̹������
//			g.drawString("Tanks's life:" + mytank.getLife(), 10, 110);// ̹������
			blood.draw(g);
			if(!mytank.isLive()||!eagle.isLive()) //ʧ������
			{
				
				g.drawImage(imgover, 150, 100, null);
				text.setVisible(true);
				return ;
			}

			if(t.size()<=0) //ʤ������
			{  
				if(map.flag<=3)map.flag++;
				if(map.flag == 4)
				{
	                g.drawImage(imgwin, 200,100 , null);
	                text.setVisible(true);
	               
	                return ;
				}
				map.Init();
				blood = new Blood(10,10,tc);
			}
			map.draw(g);
			mytank.eat(blood);
			for (int i = 0; i < missiles.size(); i++) 
			{
				Missile m = missiles.get(i);
				m.hitTanks(t);
				m.hitTank(mytank);
				m.hitWalls(map.walls);
				m.hitEagle(eagle);
				m.draw(g);
			}
			for (int i = 0; i < explodes.size(); i++) 
			{
				Explode m = explodes.get(i);
				m.draw(g);
			}
			mytank.collideWithWalls(map.walls);
			mytank.collideWithTank(t);
			mytank.collideWithEagle(eagle);
			mytank.draw(g);
			for (int i = 0; i < t.size(); i++) //���з�̹��
			{ 
				Tank m = t.get(i);
				m.collideWithWalls(map.walls);
				m.collideWithTank(t);
				m.collideWithEagle(eagle);
				m.draw(g);
			}
			//w.draw(g);
			eagle.draw(g);
			//��������
			hpIcon.setText(String.valueOf(mytank.getLife()));//��ʾ����
			ehpIcon.setText(String.valueOf(t.size()));
			roundtext.setText(String.valueOf(map.flag));
		}

	}

	public class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO �Զ����ɵķ������
			mytank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			mytank.keyPressed(e);
		}
	}
}
