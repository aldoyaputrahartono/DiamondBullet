package com.games;

import java.awt.Graphics;
import java.awt.Image;

abstract class Ammo {
	private Image image;
	private float x, y;
	private float speedX, speedY;
	private int damage;
	
	public Ammo(float x, float y, float speedX, int damage) {
		this.setX(x);
		this.setY(y);
		this.setSpeedX(speedX);
		this.setSpeedY(0);
		this.setDamage(damage);
	}
	
	public void shoot() {
		this.setX(this.getX() + this.getSpeedX());
		this.setY(this.getY() + this.getSpeedY());
	}
	
	public boolean collide(Enemy enemy) {
		//Hilang kalau ujung layar
		if(this.getX() < 0 || this.getX() > 800 || this.getY() < 0 || this.getY() > 600) return true;
		else if(this.getY() < enemy.getY()-50 || this.getY() > enemy.getY()+50) return false;
	    else if(this.getSpeedX() < 0 && this.getX() < enemy.getX()+50 && this.getX() > enemy.getX()) {
	    	enemy.setHealthPoint(enemy.getHealthPoint() - this.damage);
	    	return true;
	    }
	    else if(this.getSpeedX() > 0 && this.getX() > enemy.getX()-50 && this.getX() < enemy.getX()) {
	    	enemy.setHealthPoint(enemy.getHealthPoint() - this.damage);
	    	return true;
	    }
		return false;
	}
	
	public boolean collide(Player player) {
		//Hilang kalau ujung layar
		if(this.getX() < 0 || this.getX() > 800 || this.getY() < 0 || this.getY() > 600) return true;
		if(this.getY() < player.getY()-50 || this.getY() > player.getY()+50) return false;
	    else if(this.getSpeedX() < 0 && this.getX() < player.getX()+50 && this.getX() > player.getX()) {
	    	player.setHealthPoint(player.getHealthPoint() - this.damage);
	    	return true;
	    }
	    else if(this.getSpeedX() > 0 && this.getX() > player.getX()-50 && this.getX() < player.getX()) {
	    	player.setHealthPoint(player.getHealthPoint() - this.damage);
	    	return true;
	    }
		return false;
	}
	
	//Fungsi collide khusus dengan ujung screen
	public boolean collide() {
		if(this.getX() < 0 || this.getX() > 800 || this.getY() < 0 || this.getY() > 600) return true;
		else return false;
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
	
	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	abstract public void draw(Graphics g);
}
