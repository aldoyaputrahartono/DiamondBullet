package com.games;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StorePanel extends JPanel implements ActionListener {
	private Image img, coin;
	private String next;
	private int fontSize = 0, fontState = 0, coins;
	private int ammoAssault = 0, ammoShotgun = 0, ammoSniper = 0;
	private final JLabel titleLabel, nextLabel, coinLabel, ammoLabel, sellerLabel;
	private final JButton assault, shotgun, sniper;
	
	public StorePanel(String img, String next) {
		this.img = new ImageIcon(img).getImage();
		this.next = next;
		this.coin = new ImageIcon("assets/Images/Character Assets/Player Character/coin.png").getImage();
		Dimension size = new Dimension(800, 600);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setSize(size);
		this.setLayout(null);
		
		titleLabel = new JLabel("STORE");
		titleLabel.setFont(new Font("SERIF", Font.BOLD, fontSize));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(0, 0, 800, 600);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titleLabel);
		
		nextLabel = new JLabel("");
		nextLabel.setFont(new Font("Courier New", Font.BOLD, 20));
		nextLabel.setForeground(Color.WHITE);
		nextLabel.setBounds(450, 550, 800, 50);
		this.add(nextLabel);
		
		sellerLabel = new JLabel("");
		sellerLabel.setFont(new Font("Consolas", Font.BOLD, 20));
		sellerLabel.setForeground(Color.BLACK);
		sellerLabel.setBounds(90, 50, 400, 200);
		sellerLabel.setHorizontalAlignment(JLabel.CENTER);
		sellerLabel.setVerticalAlignment(JLabel.CENTER);
		this.add(sellerLabel);
		
		//label jmlh koin saat ini
		coinLabel = new JLabel("");
		coinLabel.setFont(new Font("SERIF", Font.BOLD, 40));
		coinLabel.setForeground(Color.YELLOW);
		coinLabel.setBounds(70, 10, 50, 50);
		this.add(coinLabel);
		
		//label jmlh ammo saat ini
		ammoLabel = new JLabel("0 0 0");
		ammoLabel.setFont(new Font("SERIF", Font.BOLD, 40));
		ammoLabel.setForeground(Color.RED);
		ammoLabel.setBounds(130, 10, 200, 50);
		ammoLabel.setHorizontalAlignment(JLabel.LEFT);
		this.add(ammoLabel);
		
		//tombol beli ammo assault
		assault = new JButton("BUY");
		assault.setBackground(new Color(255, 238, 88));
		assault.setForeground(Color.BLACK);
		assault.setFont(new Font("", Font.BOLD, 18));
		assault.setBounds(70, 360, 80, 45);
		this.add(assault);
		
		//tombol beli ammo shotgun
		shotgun = new JButton("BUY");
		shotgun.setBackground(new Color(255, 238, 88));
		shotgun.setForeground(Color.BLACK);
		shotgun.setFont(new Font("", Font.BOLD, 18));
		shotgun.setBounds(365, 360, 80, 45);
		this.add(shotgun);
		
		//tombol beli ammo sniper
		sniper = new JButton("BUY");
		sniper.setBackground(new Color(255, 238, 88));
		sniper.setForeground(Color.BLACK);
		sniper.setFont(new Font("", Font.BOLD, 18));
		sniper.setBounds(220, 500, 80, 45);
		this.add(sniper);
		
		//set isi dari nextLabel, sellerLabel, dan coins sesuai stage tujuan
		if(this.next.equals("Stage1")) {
			nextLabel.setText("Press Enter to Start Stage 1");
			sellerLabel.setText("<html><center>Hello sir. You can use the arrow keys to move, space to shoot, and 1-4 to select your weapon.<br/><br/>Code: stage1</center></html>");
			coins = 30;
		}
		else if(this.next.equals("Stage2")) {
			nextLabel.setText("Press Enter to Start Stage 2");
			sellerLabel.setText("<html><center>Always nice to have business with you, <i>mano del Diavolo</i>.<br/><br/>Code: stage2</center></html>");
			coins = 60;
		}
		else if(this.next.equals("Stage3")) {
			nextLabel.setText("Press Enter to Start Stage 3");
			sellerLabel.setText("<html><center>I guess this is it then? It’s been a pleasure, Sir.<br/><br/>Code: stage3</center></html>");
			coins = 90;
		}
		coinLabel.setText(Integer.toString(coins));
		
		//animasi dari titleLabel
		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fontState == 0) {
					fontSize += 10;
					if(fontSize == 100) fontState = 1;
				}
				else if(fontState == 1) {
					fontSize -= 10;
					if(fontSize < 0) fontSize = 0;
				}
				titleLabel.setFont(new Font("SERIF", Font.BOLD, fontSize));
				repaint();
				if (fontSize == 0) ((Timer) e.getSource()).stop();
			}
		});
		timer.start();
		
		assault.addActionListener(this);
		shotgun.addActionListener(this);
		sniper.addActionListener(this);
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enter");
		this.getActionMap().put("enter", new MoveAction(this.next));
	}
	
	private class MoveAction extends AbstractAction{
		String next;
		
		MoveAction(String next){
			this.next = next;
		}
		
		public void actionPerformed(ActionEvent e) {
			//menuju stage tujuannya masing2
			if(this.next.equals("Stage1")) Main.frame.setContentPane(new StagePanel("assets/Images/Level Assets/Stage 1/bg_stage_1.png", ammoAssault, ammoShotgun, ammoSniper, "STAGE 1"));
			else if(this.next.equals("Stage2")) Main.frame.setContentPane(new StagePanel("assets/Images/Level Assets/Stage 2/bg_stage_2.png", ammoAssault, ammoShotgun, ammoSniper, "STAGE 2"));
			else if(this.next.equals("Stage3")) Main.frame.setContentPane(new StagePanel("assets/Images/Level Assets/Stage 3/bg_stage_3.png", ammoAssault, ammoShotgun, ammoSniper, "STAGE 3"));
		}
	}
	
	//proses membeli ammo tiap senjata
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == assault) {
			if(coins >= 10) {
				coins -= 10;
				ammoAssault += 30;
			}
		}
		else if(e.getSource() == shotgun) {
			if(coins >= 15) {
				coins -= 15;
				ammoShotgun += 10;
			}
		}
		else if(e.getSource() == sniper) {
			if(coins >= 20) {
				coins -= 20;
				ammoSniper += 10;
			}
		}
		coinLabel.setText(Integer.toString(coins));
		ammoLabel.setText(Integer.toString(ammoAssault) + " " + Integer.toString(ammoShotgun) + " " + Integer.toString(ammoSniper));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.img, 0, 0, 800, 600, null);
		g.drawImage(this.coin, 10, 10, 50, 50, null);
		//draw the coins for every weapon
		g.drawImage(this.coin, 60, 265, 25, 25, null);
		g.drawImage(this.coin, 363, 265, 25, 25, null);
		g.drawImage(this.coin, 200, 400, 25, 25, null);
	}
}
