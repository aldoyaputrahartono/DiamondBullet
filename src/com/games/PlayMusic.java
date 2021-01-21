package com.games;

import java.io.File;
import javax.sound.sampled.*;

//Class to import and play music or sound effect
public class PlayMusic {
	int clicked=0;
	private File musicPath;
	private AudioInputStream audioInput;
	Clip clip;
	FloatControl volume;
	
	public void playMusic(String musicLocation) {
		
		try {
			musicPath = new File(musicLocation);
			
			if(musicPath.exists()) {
				audioInput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(-5.0f); //reduce volume by 5 decibels
				clip.start();
			}
			else {
				System.out.println("Sorry, can't find the music");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public void stopMusic() {
		clip.stop();
	}
	
	public void startMusicFromStop() {
		clip.start();
	}
	
	public void musicLoop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public boolean isRunning() {
		return clip.isRunning();
	}
}