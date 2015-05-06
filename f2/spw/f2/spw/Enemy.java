package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Enemy extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private Image eImage;
	
	private int step = 12;
	private boolean alive = true;
	
	public Enemy(int x, int y) {
		super(x, y, 80, 80);
		try {
			File source = new File("f2/spw/Icons/RepublicAssaultShip.png");
			eImage = ImageIO.read(source);
		}catch (IOException e) {
			e.printStackTrace();
        }
	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		g.drawImage(eImage, x, y, 40, 50, null);
		
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}	
	
	public boolean isAlive(){
		return alive;
	}
	
	public void remove() {
		alive = false;
	}
}