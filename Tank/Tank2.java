
import java.util.Vector;

class Tank_1{
	boolean isLive=true;
	int x,y,direct,type,speed=4;
	Shot shot=null;
	Vector<Shot> shots=new Vector<Shot>();
	public Tank_1(int x,int y,int direct){
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	public void shotTank(){
		//创建子弹对象
		switch (direct) {
		case 0:
			shot = new Shot(x+17, y, direct);
			shots.add(shot);
			break;
		case 1:
			shot = new Shot(x+35, y+17, direct);
			shots.add(shot);
			break;
		case 2:
			shot = new Shot(x+17, y+35, direct);
			shots.add(shot);
			break;
		case 3:
			shot = new Shot(x, y+17, direct);
			shots.add(shot);
			break;
		default:
			break;
		}
		Thread t=new Thread(shot);
		t.start();
	}
}
class MyTank extends Tank_1{
	int speed=5;
	final int type=0;
	public MyTank(int x, int y, int direct) {
		super(x, y, direct);
	}
	
}
class OtherTank extends Tank_1 implements Runnable{
	static int speed=5;
	final int type=1;
	public OtherTank(int x, int y, int direct) {
		super(x, y, direct);
	}
	@SuppressWarnings("static-access")
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(this.speed!=0) direct=(int)(Math.random()*4);
			switch (direct) {
			case 0:
				for (int i = 0; i < 25; i++) {
					if (y > 0) y -= speed;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				shotTank();
				break;
			case 1:
				for (int i = 0; i < 30; i++) {
					if(x<541) x+=speed;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				shotTank();
				break;
			case 2:
				for (int i = 0; i < 20; i++) {
					if(y<464) y+=speed;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				shotTank();
				break;
			case 3:
				for (int i = 0; i < 27; i++) {
					if(x>0) x-=speed;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				shotTank();
				break;
			default:
				break;
			}
		}
		
	}
	
}
class Shot implements Runnable{
	int x,y,direct;
	static int speed=12;
	boolean isLive=true;
	public Shot(int x,int y,int direct){
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			switch (direct) {
			case 0:
				y-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y+=speed;
				break;
			case 3:
				x-=speed;
				break;
			default:
				break;
			}
			if (x<0||y<0||x>600||y>500) {
				this.isLive=false;
				break;
			}
		}	
	}
}