package f2.spw;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SpaceShip extends Sprite{
	private Image spshipImage;
	private Image explodeImage;

	int step = 8;
	int life = 5;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		try {
			File spshipImagesource = new File("f2/spw/Icons/JediStarFighter.png");
			spshipImage = ImageIO.read(spshipImagesource);
			File explodeImagesource = new File("f2/spw/Icons/explode.png");
			explodeImage = ImageIO.read(explodeImagesource);
		}catch (IOException e) {
			e.printStackTrace();
        }
	}

	@Override
	public void draw(Graphics2D g) {
		if(life <= 0){
			g.drawImage(explodeImage, x, y, width, height, null);
		}else{
			g.drawImage(spshipImage, x, y, width, height, null);
		}		
	}

	public void move(int directionX, int directionY){
		x += (step * directionX);		
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
		
		y -= (step * directionY);
		if(y < 0)
			y = 0;
		if(y > 600 - height)
			y = 600 - height;
	}
	
	public void shot() {
		life -= 1;
	}
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}

}
