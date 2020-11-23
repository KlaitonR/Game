package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import entities.BulletShoot;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import graficos.Spritsheet;
import graficos.UI;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	static public final int WIDTH = 240;
	static public final int HEIGHT = 160; 
	static public final int SCALE  = 3;
	
	private BufferedImage image;
	
	static public java.util.List<Entity> entities;
	static public java.util.List<Enemy> enemies;
	static public java.util.List<BulletShoot> bulletShootes;
	static public Spritsheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public Menu menu;
	
	public static UI ui;
	
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	
	private int CUR_LEVEL = 1, MAX_LVL  = 2;
	private boolean restartGame;
	
	public Game() {
		
		Sound.musicBackground.loop();
		
		rand = new Random();
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities =  new ArrayList<Entity>();
		enemies =  new ArrayList<Enemy>();
		bulletShootes = new  ArrayList<BulletShoot>();
		
		spritesheet =  new Spritsheet("/spritesheet.png");
		player  = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		world =  new World("/level1.png");
		
		menu = new Menu();
		
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);

	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	public void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("C:\\Users\\klait\\eclipse-workspace\\Game\\res\\mira.png");
		Cursor c = toolkit.createCustomCursor(image , new Point(frame.getX() + 10, frame.getY() + 15), "C:\\Users\\klait\\eclipse-workspace\\Game\\res\\mira.png");
		frame.setCursor (c);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning =  true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		
		if(gameState.equals("NORMAL")) {
			
			restartGame = false;
			
			for(int i = 0; i<entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			for(int i = 0; i<bulletShootes.size(); i++) {
				bulletShootes.get(i).tick();
			}
			
			//Niveis
			if (enemies.size() == 0) {
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_LVL) {
					CUR_LEVEL = 1;
				}
				
				String newWorld = "level" + CUR_LEVEL + ".png";
				player.level = newWorld;
				World.restarGame(newWorld);
			}
			
		}else if (gameState.equals("GAME OVER")) {
			framesGameOver++;
			if(framesGameOver == 30) {
				framesGameOver = 0;
				if(showMessageGameOver)
					showMessageGameOver = false;
				else
					showMessageGameOver =  true;
			}
			
			if(restartGame) {
				restartGame = false;
				gameState =  "NORMAL";
				// CUR_LEVEL = 1;
				String newWorld = "level" + CUR_LEVEL + ".png";
				player.level = newWorld;
				World.restarGame(newWorld);
			}
		} else if(gameState.equals("MENU")) {
			menu.tick();
		}
	
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		
		for(int i = 0; i<entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		for(int i = 0; i<bulletShootes.size(); i++) {
			bulletShootes.get(i).render(g);
		}
		
		ui.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.yellow);
		g.drawString("Munição: " + player.ammo, 600, 20);
		g.setColor(Color.darkGray);
		g.drawString("Level " + CUR_LEVEL, 10, 470);
		
		if(gameState.equals("GAME OVER")) {
			
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(0,0,0,150));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.setColor(Color.white);
			g.drawString("GAME OVER", (WIDTH*SCALE)/2 - 105, (HEIGHT*SCALE)/2 + 20);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.setColor(Color.white);
			if(showMessageGameOver)
				g.drawString("Pressione ENTER para reiniciar", (WIDTH*SCALE)/2 - 130, (HEIGHT*SCALE)/2 + 45 );
			
		}else if (gameState.equals("MENU")) {
			menu.render(g);
		}
		
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amoutOfTicks =60.0;
		double ns = 1000000000/ amoutOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
			
		}

		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.rigth = true;
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			
			if(gameState.equals("MENU"))
				menu.up = true;
			
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			
			if(gameState.equals("MENU"))
				menu.down = true;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.rigth = false;
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) { 
			restartGame =  true;
			
			if(gameState.equals("MENU"))
				menu.enter = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { 
			
			if(gameState.equals("NORMAL")) {
				gameState = "MENU";
				menu.pause =  true;
			}else if(gameState.equals("MENU")) {
				gameState = "NORMAL";			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot =  true;
		player.mx = (e.getX() / 3);
		player.my = (e.getY() / 3);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		player.moveMx = (e.getX() / 3);
		player.moveMy = (e.getY() / 3);
		
	}

}
