package com.broll.godfight.damage;

import com.badlogic.gdx.math.Vector2;

public class AttackDamage {

	private float damage;
	private float power;
	private Vector2 impulseVector;
	
	public AttackDamage(float damage, float power, float impulseAngle) {
		this.damage=damage;
		this.power=power;
		float angle=(float) Math.toRadians(impulseAngle-90);
		float x=(float) Math.cos(angle);
		float y=(float) Math.sin(angle);
		this.impulseVector=new Vector2(x, -y);
	}
	
	public float getDamage() {
		return damage;
	}
	
	public Vector2 getImpulseVector() {
		return impulseVector;
	}
	
	public float getPower() {
		return power;
	}

	public void swapImpulseVector() {
		impulseVector.x=-impulseVector.x;
	}
}
