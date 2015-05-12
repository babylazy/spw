package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import sun.audio.*;

import java.io.*;

public class Main extends Thread{
	public void run(){
		AudioPlayer MGP = AudioPlayer.player;
        AudioStream BGM;

        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream audioSource = new FileInputStream("f2/spw/bgm/bgm.wav");
            BGM = new AudioStream(audioSource);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
	}
	public static void main(String[] args){
		String wMessage = "Welcome to spacewar";
		
		JOptionPane.showMessageDialog(null, wMessage, "Welcome!!", JOptionPane.INFORMATION_MESSAGE );
		
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
		
		Thread bgm = new Main();
		
		bgm.start();
		engine.start();
	}
}
