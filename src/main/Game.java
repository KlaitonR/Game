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
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.imageio.ImageIO;
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
	static public Spritsheet spritButton;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public Menu menu;
	
	public int [] pixels;
	//public int xx, yy;
	
	public BufferedImage lightmap;
	public int [] lightMapPixels;
	public static int [] minimapaPixels;
	
	public static BufferedImage minimapa;
	
	public static UI ui;
	
	double mx, my;
	
//	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
//	public Font newfont;
	
	public boolean saveGame;
	
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	
	private int CUR_LEVEL = 1, MAX_LVL  = 2;
	private boolean restartGame;
	
	private double timer;
	public static int hour = 6, minute = 50, second, darken;
	private boolean dusk, dawn;
	private int controlDarken;
	
	public Game() {
	
		Sound.Clips.music.loop();
		
		rand = new Random();
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		spritButton =  new Spritsheet("/button.png");
		ui = new UI(spritButton);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		try {
			lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		lightMapPixels =  new int [lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0 , lightmap.getWidth());
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities =  new ArrayList<Entity>();
		enemies =  new ArrayList<Enemy>();
		bulletShootes = new  ArrayList<BulletShoot>();
		spritesheet =  new Spritsheet("/spritesheet.png");
		player  = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		world =  new World("/level1.png");
		
		minimapa = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapaPixels = ((DataBufferInt)minimapa.getRaster().getDataBuffer()).getData();
		
		menu = new Menu();
		
//		try {
//			newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(70f);
//		}catch (FontFormatException f) {
//			f.printStackTrace();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
		
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
		//alterar Cursor
//		Image image = toolkit.getImage("C:\\Users\\klait\\eclipse-workspace\\Game\\res\\mira.png");
//		Cursor c = toolkit.createCustomCursor(image , new Point(frame.getX() + 10, frame.getY() + 15), "C:\\Users\\klait\\eclipse-workspace\\Game\\res\\mira.png");
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("/mira.png"));
		Cursor c = toolkit.createCustomCursor(image , new Point(0,0), "img");
		frame.setCursor (c);
		//Alterar icone da janela
		Image icon = null;
		try {
			icon = ImageIO.read(getClass().getResource("/icon.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(icon);
		frame.setAlwaysOnTop(true);
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
		
		if(this.saveGame) {
			this.saveGame = false;
			String[] opt1 = {"levelRoom", "vida", "levelPlayer", "exp"};
			int[] opt2 = {this.CUR_LEVEL, (int)player.life, (int)player.levelPlayer, (int)player.exp};
			Menu.saveGame(opt1,opt2,10);
			System.out.println("Jogo salvo com sucesso!");
		}
		
		if(gameState.equals("NORMAL")) {
			
			//xx++;
			restartGame = false;
			
			for(int i = 0; i<entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			for(int i = 0; i<bulletShootes.size(); i++) {
				bulletShootes.get(i).tick();
			}

			//Up Niveis
			if (enemies.size() == 0) {
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_LVL) {
					CUR_LEVEL = 1;
				}
				
				String newWorld = "level" + CUR_LEVEL + ".png";
				player.levelRoom = newWorld;
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
				player.levelRoom = newWorld;
				World.restarGame(newWorld);
			}
		} else if(gameState.equals("MENU")) {
			player.updateCamera();
			menu.tick();
		}
		
		if(Game.gameState.equals("NORMAL")) {
			
			System.out.println(timer);
			
			if(timer >= 1)
				second++;
				
			if(second == 60) {
				minute++;
				second = 0;
			}
				
			if(minute == 59) {
				minute = 0;
				hour++;
			}
				
			if(hour == 24)
				hour = 0;
				
			if(hour >= 18 || hour < 5) {
				controlDarken ++;
				if(controlDarken == 15) {
					darken++;
					controlDarken = 0;
				}
				dusk = true;
				dawn = false;
					
			}else if(hour >= 5 && hour <= 7){
				controlDarken ++;
				if(controlDarken == 15) {
					darken ++;
					controlDarken = 0;
				}
				dawn = true;
				dusk = false;
					
			}else{
				dawn = false;
				dusk = false;
			}	
		}
	}
	
//	public void drawRectangleExemple(int xoff, int yoff) {
//		for (int xx = 0; xx<32; xx++) {
//			for(int yy = 0; yy<32; yy++) {
//				
//				int xOff = xx + xoff;
//				int yOff = yy + yoff;
//				
//				if(xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT)
//					continue;
//				
//				pixels[xOff + (yOff*WIDTH)] = 0xFF0000;
//			}
//		}
//	}
	
	public void applayLight() {
		for (int xx = 0; xx< Game.WIDTH; xx++) {
			for(int yy = 0; yy < Game.HEIGHT; yy++) {
				
				if(lightMapPixels[xx + (yy * Game.WIDTH)] == 0xFFFFFFFF) {
					pixels[xx+ (yy*Game.WIDTH)] = 0;
				}
				
			}
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
		
		Collections.sort(entities, Entity.nodeSorter);
		
		for(int i = 0; i<entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		for(int i = 0; i<bulletShootes.size(); i++) {
			bulletShootes.get(i).render(g);
		}
		
		if(Game.player.useLighter)
			applayLight();
		
		ui.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		
		//drawRectangleExemple(xx, yy);
		
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.yellow);
		g.drawString("Munição: " + player.ammo, 580, 20);
//		g.setFont(newfont);
//		g.setColor(Color.red);
//		g.drawString("teste", 90, 90);
		g.setColor(Color.darkGray);
		g.drawString("Level " + CUR_LEVEL, 10, 470);
		
		if(gameState.equals("NORMAL") || Menu.pause) {
			World.renderMiniMap();
			g.drawImage(minimapa, 605, 30, World.WIDTH, World.HEIGHT, null);
		}
		
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
		
		//Rotacionar objetos
//		Graphics2D g2 = (Graphics2D) g;
//		double angleMouse = Math.atan2((200+25) - my, (200+25) - mx);
//		g2.rotate(angleMouse, 200+25, 200+25);
//		g.setColor(Color.red);
//		g.fillRect(200, 200, 50, 50);
		
		if(gameState.equals("NORMAL") || Menu.pause) {
			
			Graphics2D g2 = (Graphics2D) g;
			
			if(dusk && !player.useLighter) { // se estiver anoitecendo 
				if (darken < 235) {
					g2.setColor(new Color(0,0,0, darken));
				}else {
					g2.setColor(new Color(0,0,0,235));
				}
				g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			
			}
			
			if(dawn && !player.useLighter) { // se estiver amanhecendo 
				if(235 - darken >= 0) {
					g2.setColor(new Color(0,0,0, 235 - darken));
				}else {
					g2.setColor(new Color(0,0,0,0));
				}
		
				g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
			}
		}
		
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amoutOfTicks =60.0;
		double ns = 1000000000/ amoutOfTicks;
		double delta = 0;
//		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
//				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
//				System.out.println("FPS: " + frames);
//				frames = 0;
				timer += 1000;
				this.timer = timer;
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
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P) {
			if(gameState.equals("NORMAL")) {
				this.saveGame =  true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R) {
			player.useItem = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F){
			player.dropItem = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_G){
			player.getItem = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Q){
			player.scrollItemLef = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) {
			player.scrollItemDir = true;
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
				Menu.pause =  true;
			}else if(gameState.equals("MENU")) {
				gameState = "NORMAL";			
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R) { 
			player.useItem = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F){
			player.dropItem = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_G){
			player.getItem = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Q){
			player.scrollItemLef = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) { 
			player.scrollItemDir = false;
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
		
		if(!Game.player.openLvls && (e.getX()/ 3) >= 2 && (e.getX()/ 3) <= 78 && (e.getY()/ 3) >= 40 && (e.getY()/ 3) <= 45) {
			player.openLvls = true;
			player.offLvls = false;
			player.mouseShoot =  false;
			
		}else if(!Game.player.offLvls && (e.getX()/ 3) >= 2 && (e.getX()/ 3) <= 78 && (e.getY()/ 3) >= 40 && (e.getY()/ 3) <= 49) {
			player.offLvls = true;
			player.openLvls = false;
			player.mouseShoot =  false;
		}
		
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
		
		mx = e.getX();
		my = e.getY();
		
	}

}
