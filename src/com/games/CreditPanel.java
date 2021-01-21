package com.games;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreditPanel extends JPanel implements ActionListener {
	private int code, heightLabel, logoY;
	private Image img;
	private final JButton backButton;
	private final JLabel creditLabel;
	private final Image diamondBullet;
	private PlayMusic creditMusic;
	
	public CreditPanel(String img, int code) {
		this.img = new ImageIcon(img).getImage();
		Dimension size = new Dimension(800, 600);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setSize(size);
		this.setLayout(null);
		this.code = code;
		
		//Image Diamond Bullet
		this.diamondBullet = new ImageIcon("assets/Images/Menu/diamond_bullet_logo_icon.png").getImage();
		this.logoY = 1100;
		
		//isi credit
		this.heightLabel = 600;
		creditLabel = new JLabel();
		creditLabel.setText(
				"<html><center><p style='font-family:Courier New;font-style:bold;font-size:45;'>PROVISO - Diamond Bullet</p><br/>"
				+ "Made By<br/>"
				+ "Aldo Yaputra Hartono &nbsp&nbsp&nbsp&nbsp&nbsp 05111940000084<br/>"
				+ "Dicksen Alfersius Novian &nbsp 05111940000076<br/><br/><br/>"
				+ "Special Thanks<br/>"
				+ "Rizky Januar Akbar, S.Kom., M.Eng.<br/><br/><br/>"
				+ "and YOU, the Player<br/>"
				+ "Thank you for playing this game!!</center>"
				+ "</html>"
				);
		creditLabel.setFont(new Font("Courier New", Font.BOLD, 30));
		creditLabel.setForeground(Color.WHITE);
		creditLabel.setBounds(0,heightLabel,800,600);
		creditLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(creditLabel);
		
		//Musik credit
		creditMusic = new PlayMusic();
		creditMusic.playMusic("assets/Sounds/Music/credits_music.wav");
		
		//button utk kembali ke main menu, not visible if credit not finished
		backButton = new JButton("Back to Main Menu");
		backButton.setBackground(new Color(255, 238, 88));
		backButton.setForeground(Color.BLACK);
		backButton.setBounds(getWidth()/2-200/2, getHeight()/2-30/2, 200, 30);
		backButton.setVisible(false);
		this.add(backButton);
		
		backButton.addActionListener(this);
		
		startThread();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			creditMusic.stopMusic();
			Main.frame.setContentPane(new MainMenuPanel("assets/Images/Menu/bg_mainmenu1.jpg",
					"assets/Sounds/Music/main_menu_music.wav"));
		}
	}
	
	//animasi credit naik dari bawah panel
	public void startThread() {
		Thread creditThread = new Thread() {
			public void run() {
				while(true) {
					heightLabel -= 1;
					logoY -= 1;
					creditLabel.setBounds(0,heightLabel,800,600);
					repaint();
					try {
						if(heightLabel <= -650) {
							if(CreditPanel.this.code == 0) backButton.setVisible(true);
							else if(CreditPanel.this.code == 1) {
								creditMusic.stopMusic();
								Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/secret_ending_panel.png",
										"<html>Nero&nbsp&nbsp: Commence operation devil dismemberment.</html>",
										"Store","MainMenu"));
							}
							return;
						}
						Thread.sleep(10);
					}
					catch (InterruptedException ex) { }
				}
			}
		};
		creditThread.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.img, 0, 0, 800, 600, null);
		g.drawImage(this.diamondBullet, 325, logoY, 150, 150, null);
	}
}
