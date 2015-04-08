package f2.spw;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SpaceShip extends Sprite{
	private Image spshipImage;

	int step = 8;
	int life = 5;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		try {
			File source = new File("f2/spw/Icons/JediStarFighter.png");
			spshipImage = ImageIO.read(source);
		}catch (IOException e) {
			e.printStackTrace();
        }
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(spshipImage, x, y, width, height, null);
		
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

}
