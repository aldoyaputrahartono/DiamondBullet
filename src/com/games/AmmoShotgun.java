package com.games;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class AmmoShotgun extends Ammo {
	
	public AmmoShotgun(float x, float y, int reverse) {
		super(x,y,reverse*25,20);
		if(reverse == 1) this.setImage(new ImageIcon("assets/Images/Ammo Assets/shotgun_bullet_right.png").getImage());
		else if(reverse == -1) this.setImage(new ImageIcon("assets/Images/Ammo Assets/shotgun_bullet_left.png").getImage());
	}
	
	public void draw(Graphics g) {
		g.drawImage(getImage(), (int)this.getX(), (int)this.getY()+40, 34, 19, null);
	}
}
