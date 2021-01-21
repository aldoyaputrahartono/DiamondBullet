package com.games;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StagePanel extends JPanel {
	private Image img, heart;
	public Player player;
	public static int last_code, playerY, screenX, musicShootDuration;
	private int fontSize = 0, fontState = 0, ammoState = 1, jumpState = 0;
	private int wallX = 1520, hole1Left = 820, hole1Right = 970, hole2Left = 1220, hole2Right = 1370;
	private int ammoAssault, ammoShotgun, ammoSniper;
	private final JLabel titleLabel, ammoLabel, loseLabel;
	private List<Ammo> Ammos, enemyAmmos;
	private List<Enemy> Enemies;
	private List<String> AmmosSound;
	private PlayMusic AmmoSoundShot;
	private PlayMusic bgStage;
	
	public StagePanel(String img, int ammoAssault, int ammoShotgun, int ammoSniper, String title) {
		this.img = new ImageIcon(img).getImage();
		this.ammoAssault = ammoAssault;
		this.ammoShotgun = ammoShotgun;
		this.ammoSniper = ammoSniper;
		screenX = 0; //posisi x dari bg
		last_code = 1; //cek apakah player menghadap kanan (1) atau kiri (2)
		this.heart = new ImageIcon("assets/Images/Character Assets/Player Character/heart.png").getImage();
		Dimension size = new Dimension(800, 600);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setSize(size);
		this.setLayout(null);
		
		//declare player, enemy, ammo player, dan ammo enemy
		this.Enemies = new ArrayList<Enemy>();
		if(title.equals("STAGE 1")) {
			playerY = 470;
			Enemies.add(new Enemy(1850,370,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",20,0));
			Enemies.add(new Enemy(400,playerY,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",10,0));
			Enemies.add(new Enemy(650,playerY,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",11,0));
			Enemies.add(new Enemy(1000,playerY,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",11,0));
		}
		else if(title.equals("STAGE 2")) {
			playerY = 450;
			Enemies.add(new Enemy(400,playerY,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",10,0));
			Enemies.add(new Enemy(650,playerY,2,"assets/Images/Character Assets/Enemies/assault rifle thug/assault_thug_left_walk.gif",15,1));
			Enemies.add(new Enemy(750,playerY,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",11,0));
			Enemies.add(new Enemy(1000,playerY,2,"assets/Images/Character Assets/Enemies/assault rifle thug/assault_thug_left_walk.gif",15,1));
			Enemies.add(new Enemy(1200,playerY,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",11,0));
			Enemies.add(new Enemy(1400,playerY,2,"assets/Images/Character Assets/Enemies/assault rifle thug/assault_thug_left_walk.gif",15,1));
		}
		else if(title.equals("STAGE 3")) {
			playerY = 420;
			Enemies.add(new Enemy(400,playerY,2,"assets/Images/Character Assets/Enemies/shotgun cop/shotgun_cop_walk_left.gif",30,2));
			Enemies.add(new Enemy(650,playerY,2,"assets/Images/Character Assets/Enemies/assault rifle thug/assault_thug_left_walk.gif",15,1));
			Enemies.add(new Enemy(750,playerY,2,"assets/Images/Character Assets/Enemies/shotgun cop/shotgun_cop_walk_left.gif",30,2));
			Enemies.add(new Enemy(1000,playerY,2,"assets/Images/Character Assets/Enemies/assault rifle thug/assault_thug_left_walk.gif",15,1));
			Enemies.add(new Enemy(1200,playerY,2,"assets/Images/Character Assets/Enemies/shotgun cop/shotgun_cop_walk_left.gif",30,2));
			Enemies.add(new Enemy(1400,playerY,2,"assets/Images/Character Assets/Enemies/assault rifle thug/assault_thug_left_walk.gif",15,1));
			Enemies.add(new Enemy(1600,playerY,2,"assets/Images/Character Assets/Enemies/shotgun cop/shotgun_cop_walk_left.gif",30,2));
		}
		player = new Player(-100,playerY,5,100);
		//Spawn Enemy
		//Enemies.add(new Enemy(400,playerY,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",10,0));
		//Enemies.add(new Enemy(650,playerY,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",11,0));
		//Enemies.add(new Enemy(1000,playerY,2,"assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif",11,0));
		this.Ammos = new ArrayList<Ammo>();
		this.enemyAmmos = new ArrayList<Ammo>();
		
		titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("SERIF", Font.BOLD, fontSize));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBounds(0, 0, 800, 250);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titleLabel);
		
		loseLabel = new JLabel("<html><center>YOU LOSE<br/>Press Enter to Back to Main Menu</center></html>");
		loseLabel.setFont(new Font("SERIF", Font.BOLD, 30));
		loseLabel.setForeground(Color.RED);
		loseLabel.setBounds(0, 0, 800, 250);
		loseLabel.setHorizontalAlignment(JLabel.CENTER);
		loseLabel.setVisible(false);
		this.add(loseLabel);
		
		//menambah sound ammo player
		this.AmmoSoundShot = new PlayMusic();
		this.AmmosSound = new ArrayList<String>();
		AmmosSound.add("assets/Sounds/SFX/pistol_sound.wav"); //pistol sound
		AmmosSound.add("assets/Sounds/SFX/assault_rifle_sound.wav"); //assault sound
		AmmosSound.add("assets/Sounds/SFX/shotgun_sound.wav"); //shotgun sound
		AmmosSound.add("assets/Sounds/SFX/sniper_sound.wav"); //sniper sound
		//initially
		AmmoSoundShot.playMusic(AmmosSound.get(0));
		AmmoSoundShot.stopMusic();
		//////
		//menambah music stage
		this.bgStage = new PlayMusic();
		if(title.equals("STAGE 1")) {
			bgStage.playMusic("assets/Sounds/Music/stage_1_music.wav");
		}
		else if(title.equals("STAGE 2")) {
			bgStage.playMusic("assets/Sounds/Music/stage_2_music.wav");
		}
		else if(title.equals("STAGE 3")){
			bgStage.playMusic("assets/Sounds/Music/stage_3_music.wav");
		}
		
		//menampilkan jmlh ammo tersisa dari senjata yg dipilih (default: pistol)
		ammoLabel = new JLabel("INF");
		ammoLabel.setFont(new Font("SERIF", Font.BOLD, 40));
		ammoLabel.setForeground(Color.YELLOW);
		ammoLabel.setBounds(170, 10, 100, 50);
		ammoLabel.setHorizontalAlignment(JLabel.LEFT);
		this.add(ammoLabel);
		
		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(player.getX() < 0) player.moveRight();
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
		
		//key binding for character movement
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0), "move right");
		this.getActionMap().put("move right", new MoveAction(1));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0), "move left");
		this.getActionMap().put("move left", new MoveAction(2));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,true), "move right released");
		this.getActionMap().put("move right released", new MoveAction(3));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,true), "move left released");
		this.getActionMap().put("move left released", new MoveAction(4));
//		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0), "move down");
//		this.getActionMap().put("move down", new MoveAction(3));
//		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,true), "move down released");
//		this.getActionMap().put("move down released", new MoveAction(4));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0), "move up");
		this.getActionMap().put("move up", new MoveAction(5));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0), "shoot");
		this.getActionMap().put("shoot", new MoveAction(6));
		
		//key binding for choosing weapon
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1,0), "pistol");
		this.getActionMap().put("pistol", new MoveAction(10));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2,0), "assault");
		this.getActionMap().put("assault", new MoveAction(11));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3,0), "shotgun");
		this.getActionMap().put("shotgun", new MoveAction(12));
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4,0), "sniper");
		this.getActionMap().put("sniper", new MoveAction(13));
		
		startThread();
	}
	
	private class MoveAction extends AbstractAction{
		int code;
		
		MoveAction(int code){
			this.code = code;
		}
		
		public void actionPerformed(ActionEvent e) {
			//move right
			if(this.code == 1) {
				if(jumpState == 0) {
					if(ammoState == 1) {
						player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player walking/pistol/pistol_walk_right.gif").getImage());
					}
					else if(ammoState == 2) {
						player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player walking/assault rifle/assault_rifle_walk_right.gif").getImage());
					}
					else if(ammoState == 3) {
						player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player walking/shotgun/shotgun_walk_right.gif").getImage());
					}
					else if(ammoState == 4) {
						player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player walking/sniper rifle/sniper_walk_right.gif").getImage());
					}
				}
				
				if(player.getX() < 350 || StagePanel.screenX < -1100) { //player geser
					//tembok di stage 1
					if(player.getX() == wallX && titleLabel.getText().equals("STAGE 1")) {
						if(player.getY() >= 370) ;
						else {
							StagePanel.screenX -= player.getSpeedX();
							wallX -= player.getSpeedX();
							playerY = 370;
							for(Enemy enemy : Enemies) enemy.setX(enemy.getX() - player.getSpeedX());
						}
					}
					else player.moveRight();
				}
				else if(player.getX() >= 350 && StagePanel.screenX >= -1100) { //screen geser
					//jurang di stage 3
					if(titleLabel.getText().equals("STAGE 3")) {
						if((hole1Left <= player.getX() && player.getX()+100 <= hole1Right) || (hole2Left <= player.getX() && player.getX()+100 <= hole2Right)) {
							playerY = 520;
							if(jumpState == 0) {
								Timer down = new Timer(60, new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										player.moveDown(); repaint();
										if (player.getY() >= playerY) ((Timer) e.getSource()).stop();
									}
								});
								down.start();
								
								StagePanel.this.loseLabel.setVisible(true);
								
								StagePanel.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).clear();
								StagePanel.this.getActionMap().clear();
								StagePanel.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enter");
								StagePanel.this.getActionMap().put("enter", new MoveAction(15));
							}
						}
						else playerY = 420;
					}
					
					StagePanel.screenX -= player.getSpeedX();
					wallX -= player.getSpeedX();
					hole1Left -= player.getSpeedX();
					hole1Right -= player.getSpeedX();
					hole2Left -= player.getSpeedX();
					hole2Right -= player.getSpeedX();
					for(Enemy enemy : Enemies) enemy.setX(enemy.getX() - player.getSpeedX());
				}
				StagePanel.last_code = 1;
				//if player finishes stage then go to boss stage
				if(player.getX() >= 820) {
					bgStage.stopMusic();
					Ammos.clear(); enemyAmmos.clear();
					if(titleLabel.getText().equals("STAGE 1")) Main.frame.setContentPane(new BossStagePanel("assets/Images/Level Assets/Stage 1/bg_stage_1_boss.png", ammoAssault, ammoShotgun, ammoSniper, "BOSS STAGE 1", "assets/Images/Level Assets/Stage 1/floor_stage_1_boss.png", player.getHealthPoint()));
					else if(titleLabel.getText().equals("STAGE 2")) Main.frame.setContentPane(new BossStagePanel("assets/Images/Level Assets/Stage 2/bg_stage_2_boss.png", ammoAssault, ammoShotgun, ammoSniper, "BOSS STAGE 2", "assets/Images/Level Assets/Stage 2/floor_stage_2.png", player.getHealthPoint()));
					else if(titleLabel.getText().equals("STAGE 3")) Main.frame.setContentPane(new BossStagePanel("assets/Images/Level Assets/Stage 3/bg_stage_3_boss.png", ammoAssault, ammoShotgun, ammoSniper, "BOSS STAGE 3", "assets/Images/Level Assets/Stage 3/floor_stage_3_boss.png", player.getHealthPoint()));
				}
			}
			
			//move left
			else if(this.code == 2) {
				if(player.getX() > 0) {
					if(jumpState == 0) {
						if(ammoState == 1) {
							player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player walking/pistol/pistol_walk_left.gif").getImage());
						}
						else if(ammoState == 2) {
							player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player walking/assault rifle/assault_rifle_walk_left.gif").getImage());
						}
						else if(ammoState == 3) {
							player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player walking/shotgun/shotgun_walk_left.gif").getImage());
						}
						else if(ammoState == 4) {
							player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player walking/sniper rifle/sniper_walk_left.gif").getImage());
						}
					}
					
					if(player.getX() > 350 || StagePanel.screenX >= 0) { //player geser
						//tembok di stage 1
						if(player.getX() == wallX && titleLabel.getText().equals("STAGE 1") && player.getY() <= 370) {
							playerY = 470;
							if(jumpState == 0) {
								Timer down = new Timer(60, new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										player.moveDown(); repaint();
										if (player.getY() >= playerY) ((Timer) e.getSource()).stop();
									}
								});
								down.start();
							}
						}
						player.moveLeft();
					}
					else if(player.getX() <= 350 && StagePanel.screenX < 0) { //screen geser
						//jurang di stage 3
						if(titleLabel.getText().equals("STAGE 3")) {
							if((hole1Left <= player.getX() && player.getX()+100 <= hole1Right) || (hole2Left <= player.getX() && player.getX()+100 <= hole2Right)) {
								playerY = 520;
								if(jumpState == 0) {
									Timer down = new Timer(60, new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											player.moveDown(); repaint();
											if (player.getY() >= playerY) ((Timer) e.getSource()).stop();
										}
									});
									down.start();
									
									StagePanel.this.loseLabel.setVisible(true);
									
									StagePanel.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).clear();
									StagePanel.this.getActionMap().clear();
									StagePanel.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enter");
									StagePanel.this.getActionMap().put("enter", new MoveAction(15));
								}
							}
							else playerY = 420;
						}
						
						StagePanel.screenX += player.getSpeedX();
						wallX += player.getSpeedX();
						hole1Left += player.getSpeedX();
						hole1Right += player.getSpeedX();
						hole2Left += player.getSpeedX();
						hole2Right += player.getSpeedX();
						for(Enemy enemy : Enemies) enemy.setX(enemy.getX() + player.getSpeedX());
					}
					StagePanel.last_code = 2;
				}
			}
			
			//move down
//			else if(this.code == 3) {
//				if(StagePanel.last_code == 1) {
//					player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/move_down_right.png").getImage());
//				}
//				else if(StagePanel.last_code == 2) {
//					player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/move_down_left.png").getImage());
//				}
//			}
			
			//move down released
//			else if(this.code == 4) {
//				if(StagePanel.last_code == 1) {
//					player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/move_right.png").getImage());
//				}
//				else if(StagePanel.last_code == 2) {
//					player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/move_left.png").getImage());
//				}
//			}
			
			//move right released
			else if(this.code == 3) {
				if(ammoState == 1) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/pistol/pistol_idle_right.gif").getImage());
				else if(ammoState == 2) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/assault rifle/assault_rifle_idle_right.gif").getImage());
				else if(ammoState == 3) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/shotgun/shotgun_idle_right.gif").getImage());
				else if(ammoState == 4) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/sniper rifle/sniper_idle_right.gif").getImage());
			}
			
			//move left released
			else if(this.code == 4) {
				if(ammoState == 1) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/pistol/pistol_idle_left.gif").getImage());
				else if(ammoState == 2) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/assault rifle/assault_rifle_idle_left.gif").getImage());
				else if(ammoState == 3) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/shotgun/shotgun_idle_left.gif").getImage());
				else if(ammoState == 4) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/sniper rifle/sniper_idle_left.gif").getImage());
			}
			
			//move up (jump)
			else if(this.code == 5) {
				Timer down = new Timer(30, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/jumping_1.png").getImage());
						player.moveDown();
						
						repaint();
						if (player.getY() >= playerY) {
							jumpState = 0;
							if(StagePanel.last_code == 1) {
								if(ammoState == 1) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/pistol/pistol_idle_right.gif").getImage());
								else if(ammoState == 2) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/assault rifle/assault_rifle_idle_right.gif").getImage());
								else if(ammoState == 3) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/shotgun/shotgun_idle_right.gif").getImage());
								else if(ammoState == 4) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/sniper rifle/sniper_idle_right.gif").getImage());
							}
							else if(StagePanel.last_code == 2) {
								if(ammoState == 1) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/pistol/pistol_idle_left.gif").getImage());
								else if(ammoState == 2) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/assault rifle/assault_rifle_idle_left.gif").getImage());
								else if(ammoState == 3) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/shotgun/shotgun_idle_left.gif").getImage());
								else if(ammoState == 4) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/sniper rifle/sniper_idle_left.gif").getImage());
							}
							((Timer) e.getSource()).stop();
						}	
					}
				});
				
				Timer jump = new Timer(30, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/jumping_1.png").getImage());
						player.moveUp();
						
						repaint();
						if (player.getY() <= playerY - 130) {
							((Timer) e.getSource()).stop();
							down.start();
						}
					}
				});
				
				if(jumpState == 0) {
					jumpState = 1;
					jump.start();
				}
			}
			
			//shoot
			else if(this.code == 6) {
				Timer shoot = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						musicShootDuration--;
						if (musicShootDuration <= 0) {
							AmmoSoundShot.stopMusic();
							((Timer) e.getSource()).stop();
						}	
					}
				});
				shoot.stop();
				AmmoSoundShot.stopMusic();
				musicShootDuration = 10;
				if(ammoState == 1) {
					if(StagePanel.last_code == 1 && Ammos.size() < 6) Ammos.add(new AmmoPistol(player.getX(), player.getY(), 1));
					else if(StagePanel.last_code == 2 && Ammos.size() < 6) Ammos.add(new AmmoPistol(player.getX(), player.getY(), -1));
					AmmoSoundShot.playMusic(AmmosSound.get(0));
				}
				else if(ammoState == 2 && ammoAssault > 0) {
					if(StagePanel.last_code == 1 && Ammos.size() < 10) Ammos.add(new AmmoAssault(player.getX(), player.getY(), 1));
					else if(StagePanel.last_code == 2 && Ammos.size() < 10) Ammos.add(new AmmoAssault(player.getX(), player.getY(), -1));
					ammoAssault--;
					ammoLabel.setText(Integer.toString(ammoAssault));
					AmmoSoundShot.playMusic(AmmosSound.get(1));
				}
				else if(ammoState == 3 && ammoShotgun > 0) {
					if(StagePanel.last_code == 1 && Ammos.size() < 5) Ammos.add(new AmmoShotgun(player.getX(), player.getY(), 1));
					else if(StagePanel.last_code == 2  && Ammos.size() < 5) Ammos.add(new AmmoShotgun(player.getX(), player.getY(), -1));
					ammoShotgun--;
					ammoLabel.setText(Integer.toString(ammoShotgun));
					AmmoSoundShot.playMusic(AmmosSound.get(2));
				}
				else if(ammoState == 4 && ammoSniper > 0) {
					if(StagePanel.last_code == 1 && Ammos.size() < 1) Ammos.add(new AmmoSniper(player.getX(), player.getY(), 1));
					else if(StagePanel.last_code == 2 && Ammos.size() < 1) Ammos.add(new AmmoSniper(player.getX(), player.getY(), -1));
					ammoSniper--;
					ammoLabel.setText(Integer.toString(ammoSniper));
					AmmoSoundShot.playMusic(AmmosSound.get(3));
				}
				shoot.start();
			}
			
			//choose pistol
			else if(this.code == 10) {
				ammoState = 1;
				ammoLabel.setText("INF");
				if(StagePanel.last_code == 1) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/pistol/pistol_idle_right.gif").getImage());
				else if(StagePanel.last_code == 2) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/pistol/pistol_idle_left.gif").getImage());
			}
			
			//choose assault
			else if(this.code == 11) {
				ammoState = 2;
				ammoLabel.setText(Integer.toString(ammoAssault));
				if(StagePanel.last_code == 1) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/assault rifle/assault_rifle_idle_right.gif").getImage());
				else if(StagePanel.last_code == 2) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/assault rifle/assault_rifle_idle_left.gif").getImage());
			}
			
			//choose shotgun
			else if(this.code == 12) {
				ammoState = 3;
				ammoLabel.setText(Integer.toString(ammoShotgun));
				if(StagePanel.last_code == 1) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/shotgun/shotgun_idle_right.gif").getImage());
				else if(StagePanel.last_code == 2) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/shotgun/shotgun_idle_left.gif").getImage());
			}
			
			//choose sniper
			else if(this.code == 13) {
				ammoState = 4;
				ammoLabel.setText(Integer.toString(ammoSniper));
				if(StagePanel.last_code == 1) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/sniper rifle/sniper_idle_right.gif").getImage());
				else if(StagePanel.last_code == 2) player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/sniper rifle/sniper_idle_left.gif").getImage());
			}
			
			//if player lose go to main menu when enter is pressed
			else if(this.code == 15) {
				bgStage.stopMusic();
				Main.frame.setContentPane(new MainMenuPanel("assets/Images/Menu/bg_mainmenu1.jpg",
						"assets/Sounds/Music/main_menu_music.wav"));
			}
			repaint();
		}
	}
	
	public void startThread() {
		Thread gameThread = new Thread() {
			public void run() {
				while(true) {
					//render ammo player
					for(Iterator<Ammo> iter = Ammos.listIterator(); iter.hasNext(); ) {
					    Ammo a = iter.next();
					    a.shoot();
					    //kalau enemy habis
					    if(Enemies.isEmpty()) {
					    	if(a.collide()) iter.remove();
					    }
					    //kalau ada enemy
					    else {					    	
					    	for(Enemy enemy : Enemies) {
					    		if(a.collide(enemy)) {
					    			iter.remove();
					    			break;
					    		}
					    	}
					    }
					}
					//render ammo enemy
					for(Iterator<Ammo> iter = enemyAmmos.listIterator(); iter.hasNext(); ) {
					    Ammo a = iter.next();
					    a.shoot();
					    if(a.collide(player)) iter.remove();
					}
					//check enemy HP and moving, randomize enemy shoot
					Random rand = new Random();
					for(Iterator<Enemy> iter = Enemies.listIterator(); iter.hasNext(); ) {
						Enemy enemy = iter.next();
						if(enemy.getHealthPoint() <= 0) iter.remove();
						else {
							int enemyAction = rand.nextInt(100);
							if(enemy.getEnemyLeftState() < 50) {
								switch (enemy.getEnemyCode()) {
								case 0 :
									enemy.setImage(new ImageIcon("assets/Images/Character Assets/Enemies/melee thug/melee_left_walk1.gif").getImage());
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) enemyAmmos.add(new AmmoPistol(enemy.getX(), enemy.getY(), -1));
									break;
								case 1 : 
									enemy.setImage(new ImageIcon("assets/Images/Character Assets/Enemies/assault rifle thug/assault_thug_left_walk.gif").getImage());
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) enemyAmmos.add(new AmmoAssault(enemy.getX(), enemy.getY(), -1));
									break;
								case 2 :
									enemy.setImage(new ImageIcon("assets/Images/Character Assets/Enemies/shotgun cop/shotgun_cop_walk_left.gif").getImage());
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) enemyAmmos.add(new AmmoShotgun(enemy.getX(), enemy.getY(), -1));
									break;
								}
								enemy.moveLeft();
								enemy.setEnemyLeftState(enemy.getEnemyLeftState() + 1);
								if(enemy.getEnemyLeftState() == 50) enemy.setEnemyRightState(0);
							}
							else if(enemy.getEnemyRightState() < 50){
								switch (enemy.getEnemyCode()) {
								case 0 :
									enemy.setImage(new ImageIcon("assets/Images/Character Assets/Enemies/melee thug/melee_right_walk1.gif").getImage());
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) enemyAmmos.add(new AmmoPistol(enemy.getX(), enemy.getY(), 1));
									break;
								case 1 :
									enemy.setImage(new ImageIcon("assets/Images/Character Assets/Enemies/assault rifle thug/assault_thug_right_walk.gif").getImage());
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) enemyAmmos.add(new AmmoAssault(enemy.getX(), enemy.getY(), 1));
									break;
								case 2 :
									enemy.setImage(new ImageIcon("assets/Images/Character Assets/Enemies/shotgun cop/shotgun_cop_walk_right.gif").getImage());
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) enemyAmmos.add(new AmmoShotgun(enemy.getX(), enemy.getY(), 1));
									break;
								}
								enemy.moveRight();
								enemy.setEnemyRightState(enemy.getEnemyRightState() + 1);
								if(enemy.getEnemyRightState() == 50) enemy.setEnemyLeftState(0);
							}
						}
					}
					
					repaint();
					
					try {
						if(player.getX() >= 800) return;
						if(player.getHealthPoint() <= 0) {
							StagePanel.this.loseLabel.setVisible(true);
							
							StagePanel.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).clear();
							StagePanel.this.getActionMap().clear();
							StagePanel.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enter");
							StagePanel.this.getActionMap().put("enter", new MoveAction(15));
							
							return;
						}
						Thread.sleep(34);
					}
					catch (InterruptedException ex) { }
				}
			}
		};
		gameThread.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.img, screenX, 0, 2000, 600, null);
		g.setColor(Color.RED);
		g.drawRoundRect(10, 25, 150, 25, 30, 30);
		g.fillRoundRect(10, 25, player.getHealthPoint()*150/100, 25, 30, 30);
		g.drawImage(this.heart, 0, 18, 40, 40, null);
		player.draw(g);
		for(Enemy enemy : Enemies) enemy.draw(g);
		for(Ammo ammo : Ammos) ammo.draw(g);
		for(Ammo ammo : enemyAmmos) ammo.draw(g);
	}
}
