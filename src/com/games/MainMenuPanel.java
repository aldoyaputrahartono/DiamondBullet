package com.games;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainMenuPanel extends JPanel implements ActionListener {
	private Image img, logo, clouds;
	private final JLabel titleLabel;
	private final JButton startButton;
	private final JButton continueButton;
	private final JButton creditButton;
	private final JButton exitButton;
	private final PlayMusic mainMenuMusic = new PlayMusic();
	
	public MainMenuPanel(String img, String musicLocation) {
		this.img = new ImageIcon(img).getImage();
		Dimension size = new Dimension(800, 600);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setSize(size);
		this.setLayout(null);
		
		//logo diamond bullet
		this.logo = new ImageIcon("assets/Images/Menu/diamond_bullet_logo_icon.png").getImage();
		
		//jalankan musik
		mainMenuMusic.playMusic(musicLocation);
		mainMenuMusic.musicLoop();
		
		//tambah gif awan
		this.clouds = new ImageIcon("assets/Images/Menu/cloud.gif").getImage();
		
		//judul game
		titleLabel = new JLabel("DIAMOND BULLET");
//		titleLabel.setIcon(icon);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("", Font.BOLD, 40));
		titleLabel.setBounds(10, 10, 800, 110);
		titleLabel.setIconTextGap(0);
		this.add(titleLabel);
		
		//tombol continue
		continueButton = new JButton("CONTINUE");
		continueButton.setBackground(new Color(255, 238, 88));
		continueButton.setForeground(Color.WHITE);
		continueButton.setFont(new Font("", Font.BOLD, 18));
		continueButton.setBounds(getWidth()/2-200/2, 270, 200, 50); //posisi tombol center secara horizontal
		//If you want the Buttons to have transparent background
		continueButton.setOpaque(false);
		continueButton.setContentAreaFilled(false);
		continueButton.setBorderPainted(false);
		this.add(continueButton);
		
		//tombol start
		startButton = new JButton("START");
		startButton.setBackground(new Color(255, 238, 88));
		startButton.setForeground(Color.WHITE);
		startButton.setFont(new Font("", Font.BOLD, 18));
		startButton.setBounds(getWidth()/2-200/2, 330, 200, 50); //posisi tombol center secara horizontal
		//If you want the Buttons to have transparent background
		startButton.setOpaque(false);
		startButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		this.add(startButton);
		
		//tombol credit
		creditButton = new JButton("CREDIT");
		creditButton.setBackground(new Color(255, 238, 88));
		creditButton.setForeground(Color.WHITE);
		creditButton.setFont(new Font("", Font.BOLD, 18));
		creditButton.setBounds(getWidth()/2-200/2, 390, 200, 50); //posisi tombol center secara horizontal
		//If you want the Buttons to have transparent background
		creditButton.setOpaque(false);
		creditButton.setContentAreaFilled(false);
		creditButton.setBorderPainted(false);
		this.add(creditButton);
		
		//tombol exit
		exitButton = new JButton("EXIT");
		exitButton.setBackground(new Color(255, 238, 88));
		exitButton.setForeground(Color.WHITE);
		exitButton.setFont(new Font("", Font.BOLD, 18));
		exitButton.setBounds(getWidth()/2-200/2, 450, 200, 50); //posisi tombol center secara horizontal
		//If you want the Buttons to have transparent background
		exitButton.setOpaque(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		this.add(exitButton);
		
		startButton.addActionListener(this);
		continueButton.addActionListener(this);
		creditButton.addActionListener(this);
		exitButton.addActionListener(this);
		startThread();
	}
	
	public void actionPerformed(ActionEvent e) {
		mainMenuMusic.stopMusic();
		if(e.getSource() == startButton) {
			//menuju story panel sblm stage 1
			Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
					"<html>Nero&nbsp&nbsp: You’ve done well, Blake. Three more to go.<br/>"
					+ "Blake&nbsp: ...<br/>"
					+ "Nero&nbsp&nbsp: Now-now, you do remember why you’re doing this right?<br/>"
					+ "Blake&nbsp: ...<br/>"
					+ "Nero&nbsp&nbsp: Those diamond bullets aren’t going to shot themselves. So get going if you really want to retire from this job. <i>Veloce</i>.<br/>"
					+ "Blake&nbsp: Yes, <i>capo</i>.</html>",
					"Store","Stage1"));
		}
		else if(e.getSource() == continueButton) {
			String code = JOptionPane.showInputDialog(null, "Insert Code to Continue", "Continue Code", JOptionPane.INFORMATION_MESSAGE);
			if(code == null) mainMenuMusic.startMusicFromStop();
			else if(code.equals("stage1")) Main.frame.setContentPane(new StorePanel("assets/Images/Store Panel Assets/Store Page.png","Stage1"));
			else if(code.equals("stage2")) Main.frame.setContentPane(new StorePanel("assets/Images/Store Panel Assets/Store Page.png","Stage2"));
			else if(code.equals("stage3")) Main.frame.setContentPane(new StorePanel("assets/Images/Store Panel Assets/Store Page.png","Stage3"));
			else {
				JOptionPane.showMessageDialog(null, "Wrong Code", "Wrong Code", JOptionPane.WARNING_MESSAGE);
				mainMenuMusic.startMusicFromStop();
			}
		}
		else if(e.getSource() == creditButton) {
			//menuju credit panel
			Main.frame.setContentPane(new CreditPanel("assets/Images/Start Panel Assets/gunstore.jpg",0));
		}
		else if(e.getSource() == exitButton) {
			Main.frame.dispose();
			System.exit(0);
		}
	}
	
	//thread untuk awan bergerak
	public void startThread() {
		Thread gameThread = new Thread() {
			public void run() {
				while(true) {
					repaint();
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException ex) { }
				}
			}
		};
		gameThread.start();
	}
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.img, 0, 0, 800, 600, null);
		g.drawImage(this.clouds, 0, 25, 800, 150, null);
		g.drawImage(this.logo, 300, 75, 200, 200, null);
	}
}
