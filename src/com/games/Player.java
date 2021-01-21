package com.games;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Player extends Character {
	//make a player spawn at (x,y)
	public Player(float x, float y, float speedX, int healthPoint) {
		super(x,y,speedX,healthPoint);
		this.setImage(new ImageIcon("assets/Images/Character Assets/Player Character/player idle/pistol/pistol_idle_right.gif").getImage());
	}
	
	public void draw(Graphics g) {
		g.drawImage(getImage(), (int)this.getX(), (int)this.getY(), 100, 100, null);
	}
}
