
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
//用于存储tank的数据以及战绩，生命值
class Node{
	int x,y,direct;
	public Node(int x,int y,int direct){
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
}
class Recorder{
	static int myLife=2;
	static int otherLife=5;
	static int shotLife=0;
	//便于将我的tank的数据传进来
	static int x,y,direct;
	public int getX() {
		return x;
	}
	@SuppressWarnings("static-access")
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	@SuppressWarnings("static-access")
	public void setY(int y) {
		this.y = y;
	}
	public int getDirect() {
		return direct;
	}
	@SuppressWarnings("static-access")
	public void setDirect(int direct) {
		this.direct = direct;
	}
	Vector<Node> nodes=new Vector<Node>();
	Vector<OtherTank> otherTanks=new Vector<OtherTank>();
	public Vector<OtherTank> getOtherTanks() {
		return otherTanks;
	}
	public void setOtherTanks(Vector<OtherTank> otherTanks) {
		this.otherTanks = otherTanks;
	}
	//将数据写入到文件夹中
	public void keepRecorderTank(){
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(new File("c:/Recorder/recoder.txt"));
			String string=myLife+" "+otherLife+" "+shotLife+"\r\n";
			String string2=x+" "+y+" "+direct+"\r\n";
			fos.write(string.getBytes());
			fos.write(string2.getBytes());
			for (int i = 0; i < otherTanks.size(); i++) {
				OtherTank otherTank=otherTanks.get(i);
				String string3=otherTank.x+" "+otherTank.y+" "+otherTank.direct+"\r\n";
				fos.write(string3.getBytes());
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(fos!=null) fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//将保存的数据传输到node中，并存储在nodes中
	public Vector<Node> readRecorder(){
		BufferedReader bis=null;
		try {
			bis = new BufferedReader(new FileReader(new File("c:/a/recoder.txt")));
			String string=new String();
			while ((string=bis.readLine())!=null) {
				String[] string2=string.split(" ");
				Node node=new Node(Integer.parseInt(string2[0]), Integer.parseInt(string2[1]), Integer.parseInt(string2[2]));
				nodes.add(node);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(bis!=null) bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}
}
