
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.*;

import java.util.*;

@SuppressWarnings("serial")
public class Tank extends JFrame implements ActionListener{
	
	MyPanel mp=null;
	MyStartPanel msp=null;
	JMenuBar jmb=null;
	JMenu jm=null;
	JMenuItem jmi=null;
	JMenuItem jmi2=null;
	JMenuItem jmi3=null;
	JMenuItem jmi4=null;
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Tank tank=new Tank();
	}
	public Tank(){
		File file=new File("c:/Recorder");
		if(!file.exists()){
			try {
				file.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		msp=new MyStartPanel();
		jmb=new JMenuBar();
		jmb.setBackground(Color.gray);
		jm=new JMenu("��ʼ");
		jmi=new JMenuItem("��ʼ����Ϸ");
		jmi.addActionListener(this);
		jmi.setActionCommand("startNewGame");
		jmi2=new JMenuItem("������Ϸ");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("continueGame");
		jmi3=new JMenuItem("�����˳���Ϸ");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveGame");
		jmi4=new JMenuItem("�˳���Ϸ");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("exitGame");
		jmb.add(jm);
		jm.add(jmi);
		jm.add(jmi2);
		jm.add(jmi3);
		jm.add(jmi4);
		this.add(jmb,BorderLayout.NORTH);
		this.add(msp);
				
		this.setVisible(true);
		this.setSize(600, 700);
		this.setLocation(600, 100);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Thread t=new Thread(msp);
		t.start();

	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("startNewGame")){
			if (mp==null) {
				mp = new MyPanel("startNewGame");
				this.add(mp);
				this.addKeyListener(mp);
				Thread t = new Thread(mp);
				t.start();
				this.remove(msp);
				this.setVisible(true);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		}else if (e.getActionCommand().equals("continueGame")) {
			if (mp==null) {
				mp = new MyPanel("continueGame");
				this.add(mp);
				this.addKeyListener(mp);
				Thread t = new Thread(mp);
				t.start();
				this.remove(msp);
				this.setVisible(true);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		}else if (e.getActionCommand().equals("saveGame")) {
			Recorder recorder=new Recorder();
			recorder.setX(mp.myTank.x);
			recorder.setY(mp.myTank.y);
			recorder.setDirect(mp.myTank.direct);
			recorder.setOtherTanks(mp.otherTanks);
			recorder.keepRecorderTank();
			System.exit(0);
		}else if (e.getActionCommand().equals("exitGame")) {
			System.exit(0);
		}
	}

}
@SuppressWarnings("serial")
class MyStartPanel extends JPanel implements Runnable{
	
	int time=0;
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0, 0, 600, 500);
		if (time%2==0) {
			g.setColor(Color.red);
			Font myFont = new Font("����", Font.BOLD, 40);
			g.setFont(myFont);
			g.drawString("̹�˴�ս", 200, 300);
		}
	}
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			time++;
			if (time == 20)
				time = 0;
			this.repaint();
		}
	}
	
}
@SuppressWarnings("serial")
class MyPanel extends JPanel implements Runnable,KeyListener{
	MyTank myTank=null;
	int size=5;
	int flag=0;
	Vector<OtherTank> otherTanks=new Vector<OtherTank>();
	@SuppressWarnings("static-access")
	public MyPanel(String string){
		if (string.equals("startNewGame")) {
			Recorder.myLife=2;
			Recorder.otherLife=5;
			Recorder.shotLife=0;
			myTank = new MyTank(250, 464, 0);
			for (int i = 0; i < size; i++) {
				OtherTank otherTank = new OtherTank(100+i*100, 0, 2);
				otherTanks.add(otherTank);
				Thread t=new Thread(otherTank);
				t.start();
			}
		}else if (string.equals("continueGame")) {
			Recorder recorder=new Recorder();
			Vector<Node> nodes=recorder.readRecorder();
			recorder.myLife=nodes.get(0).x;
			recorder.otherLife=nodes.get(0).y;
			recorder.shotLife=nodes.get(0).direct;
			myTank = new MyTank(nodes.get(1).x, nodes.get(1).y, nodes.get(1).direct);
			for (int i = 0; i < nodes.size()-2; i++) {
				OtherTank otherTank = new OtherTank(nodes.get(2+i).x, nodes.get(2+i).y, nodes.get(2+i).direct);
				otherTanks.add(otherTank);
				Thread t=new Thread(otherTank);
				t.start();
			}
		}
	}
	//�м�һ��:��ͼ�е����ݾ�����Ϊ��̬�Ļ��������������ı������������Ҫ�ٴ˷����ж�̬��������
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0, 0, 600, 500);
		Font myfFont=new Font("����", Font.BOLD, 20);
		g.setFont(myfFont);
		g.drawString("�ҷ�����ֵ:", 20, 530);
		g.drawString(Recorder.myLife+"", 140, 530);
		g.drawString("�з�̹������:", 20, 560);
		g.drawString(Recorder.otherLife+"", 160, 560);
		g.drawString("��ɱ����:", 20, 590);
		g.drawString(Recorder.shotLife+"", 120, 590);
		g.drawString("1�����ո����ͣ�뿪ʼ", 250, 530);
		g.drawString("2��W�������ƶ�", 250, 560);
		g.drawString(" D�������ƶ�", 400, 560);
		g.drawString("   S�������ƶ�", 250, 590);
		g.drawString(" A�������ƶ�", 400, 590);
		g.drawString("3��J�������ӵ�", 250, 610);

		//�����ҷ�̹��
		if(myTank.isLive==true) drawTank(myTank.x, myTank.y, g, 0, myTank.direct);
		g.setColor(Color.RED);
		//�����ҷ��ӵ�
		for (int i = 0; i < myTank.shots.size(); i++) {
			Shot myShot=myTank.shots.get(i);
			if(myShot.isLive) g.fill3DRect(myShot.x, myShot.y, 2, 2, true);
			else myTank.shots.remove(myShot);
		}
		//�����з�̹��
		for (int i = 0; i <otherTanks.size(); i++) {
			OtherTank otherTank=otherTanks.get(i);
			if (otherTank.isLive) drawTank(otherTank.x, otherTank.y, g, 1, otherTank.direct);
			for (int j = 0; j < otherTank.shots.size(); j++) {
				Shot otherShot=otherTank.shots.get(j);
				if(otherShot.isLive) g.fill3DRect(otherShot.x, otherShot.y, 2, 2, true);
				else myTank.shots.remove(otherShot);
			}
		}
	}
	public void drawTank(int x,int y,Graphics g,int type,int direct){
		//����̹�˵���ɫ
		if(type==0) g.setColor(Color.red);
		else if(type==1) g.setColor(Color.yellow);
		
		switch (direct) {
		case 0:
			g.fill3DRect(x, y, 9, 36, true);
			g.fill3DRect(x+9, y+9, 18, 18, true);
			g.fill3DRect(x+27, y, 9, 36, true);
			g.fill3DRect(x+17, y, 2, 18, true);
			break;
		case 1:
			g.fill3DRect(x, y, 36, 9, true);
			g.fill3DRect(x+9, y+9, 18, 18, true);
			g.fill3DRect(x, y+27, 36, 9, true);
			g.fill3DRect(x+17, y+17, 18, 2, true);
			break;
		case 2:
			g.fill3DRect(x, y, 9, 36, true);
			g.fill3DRect(x+9, y+9, 18, 18, true);
			g.fill3DRect(x+27, y, 9, 36, true);
			g.fill3DRect(x+17, y+18, 2, 18, true);
			break;
		case 3:
			g.fill3DRect(x, y, 36, 9, true);
			g.fill3DRect(x+9, y+9, 18, 18, true);
			g.fill3DRect(x, y+27, 36, 9, true);
			g.fill3DRect(x, y+17, 18, 2, true);
			break;
		default:
			break;
		}
	}
	public void keyTyped(KeyEvent e) {
		
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			flag++;
			if(flag==20) flag=0;
		}
		if (flag%2==0) {
			OtherTank.speed=5;
			Shot.speed=12;
			if (e.getKeyCode() == KeyEvent.VK_W) {
				if (myTank.y > 0)
					myTank.y -= myTank.speed;
				myTank.direct = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				if (myTank.x < 541)
					myTank.x += myTank.speed;
				myTank.direct = 1;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				if (myTank.y < 464)
					myTank.y += myTank.speed;
				myTank.direct = 2;
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				if (myTank.x > 0)
					myTank.x -= myTank.speed;
				myTank.direct = 3;
			}
			if (e.getKeyCode() == KeyEvent.VK_J) {
				if (myTank.shots.size() < 3) {
					myTank.shotTank();
				}
			}
		}else {
			OtherTank.speed=0;
			Shot.speed=0;
		}
	}
	public void tankDead(Shot shot,Tank_1 tank_1){
		if(shot.x>tank_1.x&&shot.y>tank_1.y&&shot.x<tank_1.x+36&&shot.y<tank_1.y+36){
			if(tank_1==myTank){
				Recorder.myLife--;
				shot.isLive=false;
				if(Recorder.myLife==0) tank_1.isLive=false;
			}else{
				Recorder.otherLife--;
				Recorder.shotLife++;
				shot.isLive=false;
				tank_1.isLive=false;
			}
		}
	}
	public void hitMyTank(){
		for (int i = 0; i < otherTanks.size(); i++) {
			OtherTank otherTank=otherTanks.get(i);
			for (int j = 0; j < otherTank.shots.size(); j++) {
				Shot otherShot=otherTank.shots.get(j);
				if(myTank.isLive) tankDead(otherShot, myTank);
				if(otherShot.isLive==false) otherTank.shots.remove(otherShot);
			}
		}
	}
	public void hitOtherTank(){
		for (int i = 0; i < myTank.shots.size(); i++) {
			Shot myShot=myTank.shots.get(i);
			for (int j = 0; j < otherTanks.size(); j++) {
				OtherTank otherTank=otherTanks.get(j);
				if(otherTank.isLive) tankDead(myShot, otherTank);
				if(myShot.isLive==false) myTank.shots.remove(myShot);
				if(otherTank.isLive==false) otherTanks.remove(otherTank);
			}
		}
	}
	public void keyReleased(KeyEvent e) {
		
	}
	public void run() {
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
			hitOtherTank();
			hitMyTank();
		}
	}
}
