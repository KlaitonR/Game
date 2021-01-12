package main;

import java.applet.Applet;
import java.applet.AudioClip;

@SuppressWarnings("deprecation")
public class Sound {
	
	private AudioClip clip;
	
	public static final Sound musicBackground = new Sound("/music.wav");
	public static final Sound hurtEffect = new Sound("/hurt.wav");
	public static final Sound passosGrama = new Sound("/passosGrama.wav");
	public static final Sound shootRifle = new Sound("/shootRifle.wav");
	public static final Sound ReloadRifle = new Sound("/reloadRifle.wav");
	public static final Sound missAmo = new Sound("/missAmo.wav");
	public static final Sound dropItem = new Sound("/dropItem.wav");
	public static final Sound lighter = new Sound("/lighter.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
			
		}catch (Throwable e) {
			System.out.println("Erro ao carregar arquivo de som");
		}
	}
	
	public void play () {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
			
		}catch (Throwable e) {
			System.out.println("Erro ao carregar arquivo de som");
		}
	}
	
	public void stop() {
		try {
			new Thread() {
				public void run() {
					clip.stop();;
				}
			}.start();
			
		}catch (Throwable e) {
			System.out.println("Erro ao pausar arquivo de som");
		}
	}
	
	public void loop () {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
			
		}catch (Throwable e) {
			System.out.println("Erro ao carregar arquivo de som");
		}
	}	

}
