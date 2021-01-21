package com.games;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class AmmoBoss3 extends Ammo {
	
	public AmmoBoss3(float x, float y, int reverse) {
		super(x,y,reverse*50,15);
		if(reverse == 1) this.setImage(new ImageIcon("assets/Images/Ammo Assets/sword_boss_3.png").getImage());
		else if(reverse == -1) this.setImage(new ImageIcon("assets/Images/Ammo Assets/sword_boss_3_left.png").getImage());
	}
	
	public void draw(Graphics g) {
		g.drawImage(getImage(), (int)this.getX(), (int)this.getY()+40, 89, 54, null);
	}
}
