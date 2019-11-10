import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable, MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	private static final int NUM_ENEMY = 20;
	private static final int NUM_SHOT = 20;

	private static final int FIRE_INTERVAL = 100;

	private Player player;
	private Enemy[] enemies;
	private Shot[] shots;

	private int score = 0;
	private int hiscore = 0;
	
	private String scoreFilePath = ".\\score.txt";

	private int x = 0;
	private int y = 0;

	private long lastFire = 0;

	private boolean leftPressed = false;

	private Thread gameLoop;

	public MainPanel(){
		loadScore();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);

		initGame();

		addMouseListener(this);
		addMouseMotionListener(this);

		gameLoop = new Thread(this);
		gameLoop.start();
	}

	public void run(){
		while(true){	
			player.move(x, y);

			if(leftPressed){
				tryToFire();
			}

			if(Math.random()*100 < 40){
				for(int i = 0; i < NUM_ENEMY; i++){
					if(enemies[i].isInStorage()){
						switch((int)(Math.random()*3)){
						case 0:	enemies[i].setPos(0, (int)(Math.random()*360), (int)(Math.random()*360)); break;
						case 1:	enemies[i].setPos(640, (int)(Math.random()*360), (int)(Math.random()*360)); break;
						case 2:	enemies[i].setPos((int)(Math.random()*360), 0, (int)(Math.random()*360)); break;
						case 3:	enemies[i].setPos((int)(Math.random()*360), 480, (int)(Math.random()*360)); break;						
						}
						break;
					}
				}
			}

			for(int i = 0; i < NUM_ENEMY; i++){
				enemies[i].move();
			}
			for(int i = 0; i < NUM_SHOT; i++){
				shots[i].move();
			}

			collisionDetection();

			repaint();

			try{
				Thread.sleep(20);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	private void initGame(){
		saveScore();
		
		player = new Player(0, 0, this);

		enemies = new Enemy[NUM_ENEMY];
		for(int i = 0; i < NUM_ENEMY; i++){
			enemies[i] = new Enemy(this);
		}

		shots = new Shot[NUM_SHOT];
		for(int i = 0; i < NUM_SHOT; i++){
			shots[i] = new Shot(this);
		}

		score = 0;
	}

	private void tryToFire(){
		if(System.currentTimeMillis()-lastFire < FIRE_INTERVAL){
			return;
		}

		lastFire = System.currentTimeMillis();
		for(int i = 0; i < NUM_SHOT; i++){
			if(shots[i].isInStorage()){
				Point pos = player.getPos();		
				shots[i].setPos(pos.x, pos.y, player.getAngle());
				break;
			}

		}
	}

	private void collisionDetection(){
		for(int i = 0; i < NUM_ENEMY; i++){
			for(int j = 0; j < NUM_SHOT; j++){
				if(shots[j].collideWith(enemies[i]) && !shots[j].isInStorage() && !enemies[i].isInStorage()){
					shots[j].store();
					enemies[i].store();
					score += 100;
					hiscore = (score > hiscore)? score : hiscore;
					break;
				}
			}
		}
		for(int i = 0; i < NUM_ENEMY; i++){
			if(enemies[i].collideWith(player)){
				initGame();
				break;
			}
		}
		for(int i = 0; i < NUM_SHOT; i++){
			if(shots[i].collideWith(player) && shots[i].isReturn()){
				initGame();
				break;
			}
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.WHITE);
		g.drawString("SCORE "+String.valueOf(score), 200, 10);
		g.drawString("HISCORE "+String.valueOf(hiscore), 340, 10);

		player.draw(g);
		for(int i = 0; i < NUM_SHOT; i++){
			shots[i].draw(g);
		}
		for(int i = 0; i < NUM_ENEMY; i++){
			enemies[i].draw(g);
		}

	}

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		int mouse = e.getButton();
		if( mouse == MouseEvent.BUTTON1){
			leftPressed = true;
		}		
	}

	public void mouseReleased(MouseEvent e) {
		int mouse = e.getButton();
		if( mouse == MouseEvent.BUTTON1){
			leftPressed = false;
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseDragged(MouseEvent e){
		Point p = e.getPoint();
		this.x = p.x;
		this.y = p.y;
	}

	public void mouseMoved(MouseEvent e){
		Point p = e.getPoint();
		this.x = p.x;
		this.y = p.y;
	}

	private void loadScore(){
		File scoreFile = new File(scoreFilePath);

		try {
			if(scoreFile.createNewFile()){
				hiscore = 0;
			}else{
				BufferedReader br = new BufferedReader(new FileReader(scoreFilePath));

				String buf = br.readLine();
				if(buf != null){
					hiscore = Integer.parseInt(buf);
				}else{
					hiscore = 0;
				}
				br.close();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveScore(){
		FileWriter fw;
		try {
			fw = new FileWriter(new File(scoreFilePath));
			fw.write(Integer.toString(hiscore));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
