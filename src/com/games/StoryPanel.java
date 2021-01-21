package com.games;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class StoryPanel extends JPanel implements ActionListener {
	private Image img;
	private String next, nextnext;
	private final JLabel narationLabel, nextLabel;
	private final JButton killButton, goHomeButton;
	private int charIndex = 0, musicDuration = 0;
	private PlayMusic sfx;
	private PlayMusic bgMusic;
	
	public StoryPanel(String img, String text, String next, String nextnext) {
		this.img = new ImageIcon(img).getImage();
		this.next = next;
		this.nextnext = nextnext;
		Dimension size = new Dimension(800, 600);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setSize(size);
		this.setLayout(null);
		
		//untuk sfx
		sfx = new PlayMusic();
		//initially
		sfx.playMusic("assets/Sounds/Music/main_menu_music.wav");
		sfx.stopMusic();
		
		//untuk background music
		bgMusic = new PlayMusic();
		bgMusic.playMusic("assets/Sounds/Music/story_panel_music.wav");
		
		//warna bg hitam dan animation text nya
		this.setBackground(Color.BLACK);
		narationLabel = new JLabel();
		narationLabel.setFont(new Font("Courier New", Font.BOLD, 20));
		narationLabel.setForeground(Color.WHITE);
		narationLabel.setBounds(10, 300, 800, 250);
		narationLabel.setVerticalAlignment(JLabel.NORTH);
		this.add(narationLabel);
		
		//perintah tekan enter utk melanjutkan story atau menuju stage
		nextLabel = new JLabel();
		nextLabel.setFont(new Font("Courier New", Font.BOLD, 20));
		nextLabel.setForeground(Color.WHITE);
		nextLabel.setVisible(false);
		this.add(nextLabel);
		
		//tombol kill
		killButton = new JButton("KILL NERO");
		killButton.setBackground(new Color(255, 238, 88));
		killButton.setForeground(Color.WHITE);
		killButton.setFont(new Font("", Font.BOLD, 18));
		killButton.setBounds(getWidth()/3-200/2, 550, 200, 50); //posisi tombol center secara horizontal
		killButton.setVisible(false);
		//If you want the Buttons to have transparent background
		killButton.setOpaque(false);
		killButton.setContentAreaFilled(false);
		killButton.setBorderPainted(false);
		this.add(killButton);
		
		//tombol go home
		goHomeButton = new JButton("GO HOME");
		goHomeButton.setBackground(new Color(255, 238, 88));
		goHomeButton.setForeground(Color.WHITE);
		goHomeButton.setFont(new Font("", Font.BOLD, 18));
		goHomeButton.setBounds(getWidth()/3*2-200/2, 550, 200, 50); //posisi tombol center secara horizontal
		goHomeButton.setVisible(false);
		//If you want the Buttons to have transparent background
		goHomeButton.setOpaque(false);
		goHomeButton.setContentAreaFilled(false);
		goHomeButton.setBorderPainted(false);
		this.add(goHomeButton);
		
		if(this.nextnext.equals("Ending")) {
			killButton.addActionListener(this);
			goHomeButton.addActionListener(this);
		}
		else {
			if(this.next.equals("Story")) {
				nextLabel.setText("Next...");
				nextLabel.setBounds(700, 550, 800, 50);
			}
			else {
				nextLabel.setText("Press Enter to Continue...");
				nextLabel.setBounds(475, 550, 800, 50);
			}
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "ENTER");
			this.getActionMap().put("ENTER", new NextAction(this.next, this.nextnext));
		}
		
		//timer utk animation text
		Timer timer = new Timer(75, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String labelText = narationLabel.getText();
				if(text.charAt(charIndex) == '<') {
					while(text.charAt(charIndex) != '>') {
						labelText += text.charAt(charIndex);
						charIndex++;
					}
				}
				else if(text.charAt(charIndex) == '&') {
					while(text.charAt(charIndex) != 'p') {
						labelText += text.charAt(charIndex);
						charIndex++;
					}
				}
				else if(text.charAt(charIndex) == '*' && text.charAt(charIndex+1) == 'S') {
					sfx.playMusic("assets/Sounds/SFX/stab_sound.wav");
					musicDuration = 10;
				}
				else if(text.charAt(charIndex) == '*' && text.charAt(charIndex+1) == 'B') {
					sfx.playMusic("assets/Sounds/SFX/revolver_sound.wav");
					musicDuration = 10;
				}
				else if(text.charAt(charIndex) == '*' && text.charAt(charIndex+1) == 'E' && text.charAt(charIndex+2) == 'X'
						&& text.charAt(charIndex+3) == 'P' && text.charAt(charIndex+4) == 'L') {
					sfx.playMusic("assets/Sounds/SFX/explosion_sound.wav");
					musicDuration = 40;
				}
				else if(musicDuration <= 0 && sfx.isRunning()) {
					sfx.stopMusic();
				}
				labelText += text.charAt(charIndex);
				narationLabel.setText(labelText);
				charIndex += 1;
				musicDuration -= 1;
				if (charIndex >= text.length()) {
					if(StoryPanel.this.nextnext.equals("Ending")) {
						killButton.setVisible(true);
						goHomeButton.setVisible(true);
					}
					else nextLabel.setVisible(true);
					if(musicDuration > 0) sfx.stopMusic();
					((Timer) e.getSource()).stop();
					
				}
				
			}
		});
		timer.start();
	}
	
	private class NextAction extends AbstractAction{
		String next, nextnext;
		
		NextAction(String next, String nextnext){
			this.next = next;
			this.nextnext = nextnext;
		}
		
		public void actionPerformed(ActionEvent e) {
			bgMusic.stopMusic();
			//menuju ke store utk stage tersebut dan ending juga
			if(this.next.equals("Store")) {
				bgMusic.stopMusic();
				if(this.nextnext.equals("Stage1")) Main.frame.setContentPane(new StorePanel("assets/Images/Store Panel Assets/Store Page.png","Stage1"));
				else if(this.nextnext.equals("Stage2")) Main.frame.setContentPane(new StorePanel("assets/Images/Store Panel Assets/Store Page.png","Stage2"));
				else if(this.nextnext.equals("Stage3")) Main.frame.setContentPane(new StorePanel("assets/Images/Store Panel Assets/Store Page.png","Stage3"));
				else if(this.nextnext.equals("CreditBadEnding")) Main.frame.setContentPane(new CreditPanel("assets/Images/Start Panel Assets/gunstore.jpg",0));
				else if(this.nextnext.equals("CreditHappyEnding")) Main.frame.setContentPane(new CreditPanel("assets/Images/Start Panel Assets/gunstore.jpg",1));
				else if(this.nextnext.equals("MainMenu")) Main.frame.setContentPane(new MainMenuPanel("assets/Images/Menu/bg_mainmenu1.jpg", "assets/Sounds/Music/main_menu_music.wav"));
			}
			
			//melanjutkan story yg terputus krn keterbatasan tempat dan story utk sblm masuk ke stage tertentu
			else if(this.next.equals("Story")) {
				//story sblm stage 2
				if(this.nextnext.equals("Stage2"))
					Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
							"<html>Nero&nbsp&nbsp: HAHAHA!!! You finally got what you deserve Cain. <i>Vaffanculo Caino</i>.<br/>"
							+ "Blake&nbsp: ...<br/>"
							+ "Nero&nbsp&nbsp: That’s what you get for messing with me back in the States.<br/>"
							+ "Blake&nbsp: ...<br/>"
							+ "Nero&nbsp&nbsp: Heheh, a revolver with six diamond bullets for the six <i>Grande Capo</i>, poetic right?<br/>"
							+ "Blake&nbsp: ...<br/>"
							+ "Nero&nbsp&nbsp: Two more to go, <i>fra</i>. Your family is waiting.<br/>"
							+ "Blake&nbsp: DON’T YOU DARE TOUCH THEM!!</html>",
							"Story","Stage2Again"));
				//lanjutan story sblm stage 2
				else if(this.nextnext.equals("Stage2Again"))
					Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
							"<html>Nero&nbsp&nbsp: Relax, I am just keeping an eye on them, that’s all. Your wife, your two kids. <i>Stanno benne</i> they’re fine.<br/>"
							+ "Blake&nbsp: ... I’m heading out now.<br/>"
							+ "Nero&nbsp&nbsp: Yes-yes, say hello to that <i>cagna</i> for me alright.</html>",
							"Store","Stage2"));
				//story sblm stage 3
				else if(this.nextnext.equals("Stage3"))
					Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
							"<html>Blake&nbsp: It is done, Nero.<br/>"
							+ "Nero&nbsp&nbsp: Yes... It’s a shame that she had to die. But well, let’s move on, shall we?<br/>"
							+ "Blake&nbsp: ...<br/>"
							+ "Nero&nbsp&nbsp: The final target for your final mission. Tada Michio.<br/>"
							+ "Blake&nbsp: The most influential <i>Grande Capo</i>.<br/>"
							+ "Nero&nbsp&nbsp: He IS one of the founders of this seven <i>Grande Capo</i> system. The system that was thought to bring a balance of power amongst the Criminal Lords in the world.<br/>"
							+ "Blake&nbsp: ...</html>",
							"Story","Stage3Again"));
				//lanjutan story sblm stage 3
				else if(this.nextnext.equals("Stage3Again"))
					Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
							"<html>Nero&nbsp&nbsp: And now, WE are going to eliminate him, and I will become the boss of all bosses, <i>capo di tutti i capi</i>.<br/>"
							+ "Blake&nbsp: There will be no bosses left to rule.<br/>"
							+ "Nero&nbsp&nbsp: HAHAHAHAHA! Such a small mind Blake. Just do your job, and your ties with the criminal underworld will be officially severed.<br/>"
							+ "Blake&nbsp: ...</html>",
							"Store","Stage3"));
				//lanjutan story setelah stage 3
				else if(this.nextnext.equals("Stage3Ending"))
					Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
							"<html>Tada Michio&nbsp: Well, whatever happens from now on, protect your family. You have dived this deep, the world will be in a state of chaos when he’s the one controlling everything.<br/>"
							+ "Blake&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: ...<br/>"
							+ "Tada Michio&nbsp: I have lived with honor, I shall die with honor. <i>Sayonara</i>, hand of the devil. *STABS*<br/>"
							+ "Blake&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: Goodbye Mr. Michio.<br/><br/>"
							+ "What do you want to do to now?</html>",
							"Story","Ending"));
				//lanjutan story bad ending
				else if(this.nextnext.equals("BadEnding"))
					Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
							"<html>Nero&nbsp&nbsp: *cough* Well played Blake... But everything comes with a price. *EXPLOSION* Now your family is dead....<br/>"
							+ "Blake&nbsp: NOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!!!!</html>",
							"Store","CreditBadEnding"));
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		bgMusic.stopMusic();
		if(e.getSource() == killButton) {
			Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
					"<html>Nero&nbsp&nbsp: Blake! You’re back.<br/>"
					+ "Blake&nbsp: ...<br/>"
					+ "Nero&nbsp&nbsp: Blake? What’s with that look on your face?<br/>"
					+ "Blake&nbsp: ...<br/>"
					+ "Nero&nbsp&nbsp: You’re free to go Blake. You can go back to your family now.<br/>"
					+ "Blake&nbsp: Nero, I have thought about this. I think you should leave this world.<br/>"
					+ "Nero&nbsp&nbsp: W-what do you mean? Blake?!<br/>"
					+ "Blake&nbsp: I am the hand of the devil, and you are my devil no longer. *BOOM*</html>",
					"Story","BadEnding"));
		}
		else if(e.getSource() == goHomeButton) {
			Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
					"<html>Blake&nbsp: Nero, Tada Michio is dead.<br/>"
					+ "Nero&nbsp&nbsp: Wow, to be honest, I didn’t expect you to be able to finish this mission.<br/>"
					+ "Blake&nbsp: I’m leaving now, for good.<br/>"
					+ "Nero&nbsp&nbsp: ... Yeah-yeah, I will uphold my words to you. I, the boss of bosses, declare you officially out. Nobody is allowed to touch you or your family. Goodbye, Blake.<br/>"
					+ "Blake&nbsp: Goodbye, Nero.</html>",
					"Store","CreditHappyEnding"));
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.img, 0, 0, 800, 300, null);
	}
}
