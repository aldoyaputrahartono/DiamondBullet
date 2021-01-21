package com.games;

import java.awt.Graphics;
import java.awt.Image;

abstract class Character {
	private Image image;
	private float x, y;
	private float speedX;
	private int healthPoint;
	
	public Character(float x, float y, float speedX, int healthPoint) {
		this.setX(x);
		this.setY(y);
		this.setSpeedX(speedX);
		this.setHealthPoint(healthPoint);
	}
	
	public void moveRight() {
		this.setX(this.getX() + this.getSpeedX());
	}
	
	public void moveLeft() {
		this.setX(this.getX() - this.getSpeedX());
	}
	
	public void moveUp() {
		this.setY(this.getY() - 5);
	}
	
	public void moveDown() {
		this.setY(this.getY() + 5);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}
	
	public int getHealthPoint() {
		return healthPoint;
	}

	public void setHealthPoint(int healthPoint) {
		this.healthPoint = healthPoint;
	}
	
	abstract public void draw(Graphics g);
}
