package f2.spw;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class Boss extends Sprite{
	/*public static final int X_TO_OVER = 320;
	public static final int Y_TO_OVER = 520;*/
	
	private Image bImage;
	private boolean alive = true;
	private int hp = 3;
	
	public Boss(int x, int y) {
		super(x, y, 80, 80);
		try {
			File source = new File("f2/spw/Icons/TFCoreShip.png");
			bImage = ImageIO.read(source);
		}catch (IOException e) {
			e.printStackTrace();
        }
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(bImage, x, y, 100, 100, null);
		
	}
	
	public void proceed(){
		int direction = (int)(Math.random()*2);;
		if(direction == 1 && x < 390 && y < 590){
			x += (int)(Math.random()*5);
			y += (int)(Math.random()*5);
		}else{
			x -= (int)(Math.random()*5);
			y -= (int)(Math.random()*5);
		}
	}
	
	public void shot() {
		hp -= 1;
	}
	
	public void remove() {
		alive = false;
	}
	
	public int getHp() {
		return hp;
	}
	
	

}
