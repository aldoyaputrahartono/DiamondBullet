package com.games;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Enemy extends Character {
	private int enemyLeftState, enemyRightState;
	private int enemyCode;
	//make an enemy spawn at (x,y) and use asset img
	public Enemy(float x, float y, float speedX, String img, int healthPoint, int enemyCode) {
		super(x,y,speedX,healthPoint);
		this.setEnemyLeftState(0);
		this.setEnemyRightState(50);
		this.setImage(new ImageIcon(img).getImage());
		this.enemyCode = enemyCode;
	}
	
	public int getEnemyCode() {
		return enemyCode;
	}

	public void setEnemyCode(int enemyCode) {
		this.enemyCode = enemyCode;
	}

	public void draw(Graphics g) {
		g.drawImage(getImage(), (int)this.getX(), (int)this.getY(), 100, 100, null);
	}

	public int getEnemyLeftState() {
		return enemyLeftState;
	}

	public void setEnemyLeftState(int enemyLeftState) {
		this.enemyLeftState = enemyLeftState;
	}

	public int getEnemyRightState() {
		return enemyRightState;
	}

	public void setEnemyRightState(int enemyRightState) {
		this.enemyRightState = enemyRightState;
	}
}
