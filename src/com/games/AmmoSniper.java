package com.games;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class AmmoSniper extends Ammo {
	
	public AmmoSniper(float x, float y, int reverse) {
		super(x,y,reverse*75,30);
		if(reverse == 1) this.setImage(new ImageIcon("assets/Images/Ammo Assets/sniper_bullet_right.png").getImage());
		else if(reverse == -1) this.setImage(new ImageIcon("assets/Images/Ammo Assets/sniper_bullet_left.png").getImage());
	}
	
	public void draw(Graphics g) {
		g.drawImage(getImage(), (int)this.getX(), (int)this.getY()+40, 34, 19, null);
	}
}
