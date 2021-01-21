package com.games;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BossStagePanel extends JPanel {
	private Image img, heart, floor;
	public Player player;
	public static int last_code, playerY, musicShootDuration;
	private int fontSize = 0, fontState = 0, ammoState = 1, jumpState = 0;
	private int ammoAssault, ammoShotgun, ammoSniper;
	private final JLabel titleLabel, ammoLabel, loseLabel;
	private List<Ammo> Ammos, enemyAmmos;
	private List<Enemy> Enemies;
	private List<String> AmmosSound;
	private PlayMusic AmmoSoundShot;
	private PlayMusic bgMusic;
	
	public BossStagePanel(String img, int ammoAssault, int ammoShotgun, int ammoSniper, String title, String floorPath, int healthPoint) {
		this.img = new ImageIcon(img).getImage();
		this.ammoAssault = ammoAssault;
		this.ammoShotgun = ammoShotgun;
		this.ammoSniper = ammoSniper;
		last_code = 1; //cek apakah player menghadap kanan (1) atau kiri (2)
		this.floor = new ImageIcon(floorPath).getImage();
		this.heart = new ImageIcon("assets/Images/Character Assets/Player Character/heart.png").getImage();
		Dimension size = new Dimension(800, 600);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setSize(size);
		this.setLayout(null);
		this.bgMusic = new PlayMusic();
		//declare player, enemy, ammo player, dan ammo enemy, serta lagu mana yang diputar
		this.Enemies = new ArrayList<Enemy>();
		if(title.equals("BOSS STAGE 1")) {
			bgMusic.playMusic("assets/Sounds/Music/stage_1_music.wav");
			playerY = 420;
			Enemies.add(new Enemy(400,playerY,5,"assets/Images/Character Assets/Enemies/boss 1/boss_1_right.gif",100,4));
		}
		else if(title.equals("BOSS STAGE 2")) {
			bgMusic.playMusic("assets/Sounds/Music/stage_2_music.wav");
			playerY = 450;
			Enemies.add(new Enemy(400,playerY,5,"assets/Images/Character Assets/Enemies/boss 2/boss_2_left.gif",150,5));
		}
		else if(title.equals("BOSS STAGE 3")) {
			bgMusic.playMusic("assets/Sounds/Music/stage_3_music.wav");
			playerY = 420;
			Enemies.add(new Enemy(400,playerY,5,"assets/Images/Character Assets/Enemies/boss 3/boss_3_left.gif",200,6));
		}
		player = new Player(-100,playerY,5,healthPoint);
		//Enemies.add(new Enemy(400,playerY,5,"assets/Images/Character Assets/Enemies/boss 1/boss_1_right.gif",100,4));
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
				
				if(player.getX() < 700 || (player.getX() < 820 && Enemies.isEmpty())) player.moveRight();
				BossStagePanel.last_code = 1;
				//if player finishes boss stage then go to story panel
				if(player.getX() >= 820 && Enemies.isEmpty()) {
					bgMusic.stopMusic();
					Ammos.clear(); enemyAmmos.clear();
					//story after stage 1 finished
					if(titleLabel.getText().equals("BOSS STAGE 1"))
						Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
								"<html>Cain&nbsp&nbsp: Curse you Nero! *cough* <i>mano del Diavolo</i>, you are doing this for your family right? Spare me and I’ll make sure you can retire and stay with your family!<br/>"
								+ "Blake&nbsp: ... No, you do not have the power to do that.<br/>"
								+ "Cain&nbsp&nbsp: No! NO!! NOO!!!! PLEASE SPAR- *BOOM*<br/>"
								+ "Blake&nbsp: I’m sorry. Two more and I can finally quit this job.</html>",
								"Story","Stage2"));
					//story after stage 2 finished
					else if(titleLabel.getText().equals("BOSS STAGE 2"))
						Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
								"<html>Desdemona&nbsp: Hand of the Devil... I guess this is it.<br/>"
								+ "Blake&nbsp&nbsp&nbsp&nbsp&nbsp: Nero said Hello.<br/>"
								+ "Desdemona&nbsp: Did he now? *cough* Is it worth it? All this just for you to get a boring life with your family.<br/>"
								+ "Blake&nbsp&nbsp&nbsp&nbsp&nbsp: It is.<br/>"
								+ "Desdemona&nbsp: Well, I just hope that Nero will keep his words. He is a backstabbing <i>serpente</i>. He promised that he and I will rule the criminal underworld together.<br/>"
								+ "Blake&nbsp&nbsp&nbsp&nbsp&nbsp: He will rule it, and you will become one of his foundations. *BOOM*</html>",
								"Story","Stage3"));
					//story after stage 3 finished
					else if(titleLabel.getText().equals("BOSS STAGE 3"))
						Main.frame.setContentPane(new StoryPanel("assets/Images/Story Panel Assets/story_panel.jpg",
								"<html>Tada Michio&nbsp: Heh, you are as good as, no, better than what your legend says about you.<br/>"
								+ "Blake&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: I only did what I had to do.<br/>"
								+ "Tada Michio&nbsp: <i>Sodesu ka</i>, I see... *cough* I used to be just like you. Doing everything it takes for my family, not caring about anything else.<br/>"
								+ "Blake&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: ...<br/>"
								+ "Tada Michio&nbsp: Not a talkative fellow, eh?<br/>"
								+ "Blake&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp: ...</html>",
								"Story","Stage3Ending"));
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
					
					player.moveLeft();
					BossStagePanel.last_code = 2;
				}
			}
			
			//move down
//			else if(this.code == 3) {
//				if(BossStagePanel.last_code == 1) {
//					player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/move_down_right.png").getImage());
//				}
//				else if(BossStagePanel.last_code == 2) {
//					player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/move_down_left.png").getImage());
//				}
//			}
			
			//move down released
//			else if(this.code == 4) {
//				if(BossStagePanel.last_code == 1) {
//					player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/move_right.png").getImage());
//				}
//				else if(BossStagePanel.last_code == 2) {
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
						if (player.getY() == playerY) {
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
						if (player.getY() == playerY - 130) {
							((Timer) e.getSource()).stop();
							down.start();
						}
					}
				});
				
				if(jumpState == 0) {
					jumpState = 1;
					player.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/jumping_1.png").getImage());
					jump.start();
				}
			}
			
			//shoot, Ammos.size adalah maks ammo dalam screen
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
					if(BossStagePanel.last_code == 1 && Ammos.size() < 6) Ammos.add(new AmmoPistol(player.getX(), player.getY(), 1));
					else if(BossStagePanel.last_code == 2 && Ammos.size() < 6) Ammos.add(new AmmoPistol(player.getX(), player.getY(), -1));
					AmmoSoundShot.playMusic(AmmosSound.get(0));
				}
				else if(ammoState == 2 && ammoAssault > 0) {
					if(BossStagePanel.last_code == 1 && Ammos.size() < 10) Ammos.add(new AmmoAssault(player.getX(), player.getY(), 1));
					else if(BossStagePanel.last_code == 2 & Ammos.size() < 10) Ammos.add(new AmmoAssault(player.getX(), player.getY(), -1));
					ammoAssault--;
					ammoLabel.setText(Integer.toString(ammoAssault));
					AmmoSoundShot.playMusic(AmmosSound.get(1));
				}
				else if(ammoState == 3 && ammoShotgun > 0) {
					if(BossStagePanel.last_code == 1 && Ammos.size() < 5) Ammos.add(new AmmoShotgun(player.getX(), player.getY(), 1));
					else if(BossStagePanel.last_code == 2 && Ammos.size() < 5) Ammos.add(new AmmoShotgun(player.getX(), player.getY(), -1));
					ammoShotgun--;
					ammoLabel.setText(Integer.toString(ammoShotgun));
					AmmoSoundShot.playMusic(AmmosSound.get(2));
				}
				else if(ammoState == 4 && ammoSniper > 0) {
					if(BossStagePanel.last_code == 1 && Ammos.size() < 1) Ammos.add(new AmmoSniper(player.getX(), player.getY(), 1));
					else if(BossStagePanel.last_code == 2 && Ammos.size() < 1) Ammos.add(new AmmoSniper(player.getX(), player.getY(), -1));
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
				bgMusic.stopMusic();
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
								case 4 :
									enemy.setImage(new ImageIcon("assets/Images/Character Assets/Enemies/boss 1/boss_1_left.gif").getImage());
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) {
										enemyAmmos.add(new AmmoPistol(enemy.getX(), enemy.getY(), -1));
									}
									enemy.moveLeft();
									enemy.setEnemyLeftState(enemy.getEnemyLeftState() + 1);
									if(enemy.getEnemyLeftState() == 50) enemy.setEnemyRightState(0);
									break;
								case 5 :
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) {
										enemyAmmos.add(new AmmoAssault(enemy.getX(), enemy.getY(), -1));
									}
									break;
								case 6 : 
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) {
										enemyAmmos.add(new AmmoBoss3(enemy.getX(), enemy.getY(), -1));
									}
									break;
								}
							}
							else if(enemy.getEnemyRightState() < 50){
								switch (enemy.getEnemyCode()) {
								case 4 :
									enemy.setImage(new ImageIcon("assets/Images/Character Assets/Enemies/boss 1/boss_1_right.gif").getImage());
									if(enemyAction < 5 && player.getX() >= 0 && enemy.getX() > 0 && enemy.getX() < 600) enemyAmmos.add(new AmmoPistol(enemy.getX(), enemy.getY(), 1));
									enemy.moveRight();
									enemy.setEnemyRightState(enemy.getEnemyRightState() + 1);
									if(enemy.getEnemyRightState() == 50) enemy.setEnemyLeftState(0);
									break;
								case 5 : 
									break;
								case 6 : 
									break;
								}
							}
						}
					}
					
					repaint();
					
					try {
						if(player.getX() >= 800) return;
						if(player.getHealthPoint() <= 0) {
							BossStagePanel.this.loseLabel.setVisible(true);
							
							BossStagePanel.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).clear();
							BossStagePanel.this.getActionMap().clear();
							BossStagePanel.this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enter");
							BossStagePanel.this.getActionMap().put("enter", new MoveAction(15));
							
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
		g.drawImage(this.img, 0, 0, 800, 600, null);
		g.setColor(Color.RED);
		g.drawRoundRect(10, 25, 150, 25, 30, 30);
		g.fillRoundRect(10, 25, player.getHealthPoint()*150/100, 25, 30, 30);
		g.drawImage(this.heart, 0, 18, 40, 40, null);
		g.drawImage(this.floor, 0, 0, 800, 600, null);
		player.draw(g);
		for(Enemy enemy : Enemies) enemy.draw(g);
		for(Ammo ammo : Ammos) ammo.draw(g);
		for(Ammo ammo : enemyAmmos) ammo.draw(g);
	}
}
