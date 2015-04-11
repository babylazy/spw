package f2.spw;

import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args){
		String name;
		String wMessage = "Welcome to spacewar";
		
		JOptionPane.showMessageDialog(null, wMessage, "Welcome!!", JOptionPane.INFORMATION_MESSAGE );
		//name = JOptionPane.showInputDialog("Enter your name here :");
		
		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		
		SpaceShip v = new SpaceShip(150, 520, 80, 80);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v);
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		
		engine.start();
	}
}
