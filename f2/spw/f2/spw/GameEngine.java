package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Upgradebullet> upgradebullets = new ArrayList<Upgradebullet>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Boss> bosses = new ArrayList<Boss>();
	private SpaceShip v;
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.001;
	private double difficulty2 = 0.1;
	private double difficulty3 = 0.001;
	private boolean upgBullet = false;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateItem(){
		Bonusscore bs = new Bonusscore((int)(Math.random()*390), 30);		
		Bonuslife pl = new Bonuslife((int)(Math.random()*390), 30);		
		gp.sprites.add(bs);
		items.add(bs);		
		gp.sprites.add(pl);
		items.add(pl);
	}
	
	private void generateUpgradebullet(){
		Upgradebullet ub = new Upgradebullet((int)(Math.random()*390), 30);
		gp.sprites.add(ub);
		upgradebullets.add(ub);		
	}
	
	private void generateBullet(){
		Bullet b = new Bullet(v.getX() + 35, v.getY());
		gp.sprites.add(b);
		bullets.add(b);
	}
	
	private void generateUpgBullet(){
		Bullet b1 = new Bullet(v.getX() + 15, v.getY());
		Bullet b2 = new Bullet(v.getX() + 55, v.getY());
		gp.sprites.add(b1);
		bullets.add(b1);
		gp.sprites.add(b2);
		bullets.add(b2);
	}
	
	private void generateBoss(){
		Boss boss = new Boss((int)(Math.random()*390), 30);
		gp.sprites.add(boss);
		bosses.add(boss);
	}
	
	private void process(){	
		if(Math.random() < difficulty){
			generateEnemy();
		}

		if(Math.random() < difficulty2){
			generateItem();
			generateUpgradebullet();
		}
		
		if(Math.random() < difficulty3){
			generateBoss();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}

		Iterator<Item> i_iter = items.iterator();		
		while(i_iter.hasNext()){
			Item i = i_iter.next();
			i.proceed();
			
			if(!i.isAlive()){
				i_iter.remove();
				gp.sprites.remove(i);
			}
		}
		
		Iterator<Upgradebullet> ub_iter = upgradebullets.iterator();		
		while(ub_iter.hasNext()){
			Upgradebullet ub = ub_iter.next();
			ub.proceed();
			
			if(!ub.isAlive()){
				ub_iter.remove();
				gp.sprites.remove(ub);
			}
		}
		
		Iterator<Bullet> b_iter = bullets.iterator();
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
		
		Iterator<Boss> boss_iter = bosses.iterator();
		while(boss_iter.hasNext()){
			Boss boss = boss_iter.next();
			boss.proceed();
			
			if(boss.getHp() < 0){
				boss_iter.remove();
				gp.sprites.remove(boss);
				score += 5000;
			}
		}
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double bossr;
		//Rectangle2D.Double bsr;
		//Rectangle2D.Double smr;
		//Rectangle2D.Double plr;
		Rectangle2D.Double br;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				v.shot();
				if(v.getLife() < 0)
					die();
				e.remove();
				return;
			}
		}
		
		for(Item i : items){
			er = i.getRectangle();			
			if(er.intersects(vr)){
				if(i instanceof Addscore){
					Addscore as = (Addscore)i;
					score = as.addScore(score);					
				}
				if(i instanceof Addlife){
					Addlife al = (Addlife)i;
					v.setLife(al.addLife(v.getLife()));
				}
				//upgBullet = true;
				i.remove();
				gp.sprites.remove(i);
				return;
			}
		}
		
		for(Upgradebullet ub : upgradebullets){
			er = ub.getRectangle();
			if(er.intersects(vr)){
				upgBullet = true;
			}
		}
		
		for(Bullet b : bullets){
			br = b.getRectangle();
			for(Enemy e : enemies){
				er = e.getRectangle();
				if(br.intersects(er)){
					e.remove();
					gp.sprites.remove(e);
					return;
				}
			}
			
			for(Boss boss : bosses){
				bossr = boss.getRectangle();
				if(br.intersects(bossr)){
					boss.remove();
					gp.sprites.remove(boss);
					return;
				}
			}
			/*bossr = boss.getRectangle();
			if(br.intersects(bossr)){
				boss.shot();
				return;
			}*/
		}		
		
		gp.updateGameUI(this, v.getLife());
	}
	
	public void bonusScore(){
		score += 1000;
	}
	
	public void reduceScore(){
		score -= 100;
	}

	public void die(){
		timer.stop();
		gameOver();
	}
	
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1, 0);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		case KeyEvent.VK_UP:
			v.move(0, 1);
			break;
		case KeyEvent.VK_DOWN:
			v.move(0, -1);
			break;
		case KeyEvent.VK_X:
			if(upgBullet){
				generateUpgBullet();
			}else{
				generateBullet();
			}
		}
	}

	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
	
	public void gameOver() {
		JOptionPane.showMessageDialog(null, "Your score is " + score, "GAME OVER!!", JOptionPane.INFORMATION_MESSAGE );
	}
}
